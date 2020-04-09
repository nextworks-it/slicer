package it.nextworks.nfvmano.sebastian.vsfm;

import it.nextworks.nfvmano.catalogue.blueprint.elements.SliceServiceType;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsDescriptor;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.catalogues.template.repo.NsTemplateRepository;
import it.nextworks.nfvmano.catalogues.template.services.NsTemplateCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.LocationInfo;
import it.nextworks.nfvmano.libs.ifa.templates.EMBBPerfReq;
import it.nextworks.nfvmano.libs.ifa.templates.GeographicalAreaInfo;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
import it.nextworks.nfvmano.libs.ifa.templates.URLLCPerfReq;
import it.nextworks.nfvmano.sebastian.nstE2eComposer.repository.BucketRepository;
import it.nextworks.nfvmano.sebastian.nste2eComposer.IM.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static it.nextworks.nfvmano.catalogue.blueprint.elements.AvailabilityCoverageRange.AVAILABILITY_COVERAGE_HIGH;

public class RuntimeTranslator {

    private BucketRepository bucketRepository;
    private VsDescriptorCatalogueService vsDescriptorCatalogueService;
    private HashMap<String, NstAdvertisedInfo> nstAdvertisedMap;

    private static final Logger log = LoggerFactory.getLogger(RuntimeTranslator.class);

    public RuntimeTranslator(BucketRepository bucketRepository, VsDescriptorCatalogueService vsDescriptorCatalogueService){
        this.bucketRepository=bucketRepository;
        this.vsDescriptorCatalogueService = vsDescriptorCatalogueService;
    }



    private Integer parseStringToInt(String text) {
        if(text==null)
            return 0;
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    public String findScenarioInVsd(VsDescriptor vsd){
        Map<String, String> parameters=vsd.getQosParameters();
        for(String param:parameters.keySet()){
            if(param.toLowerCase().contains("scenario")){
                return parameters.get(param);
            }
        }
        return null;
    }

    private void setNstAdvertisedMap(ArrayList<Long> filteredBucketsId){
        nstAdvertisedMap = new HashMap<String, NstAdvertisedInfo>();
        for(Long bucketId: filteredBucketsId){
            Bucket bucket=bucketRepository.findById(bucketId).get();
            for(NstAdvertisedInfo nstAdvertisedInfo: bucket.getNstAdvertisedInfoList()){
                String nstUUID = nstAdvertisedInfo.getNstId();
                log.info("Nst UUID is "+nstUUID);
                if(nstAdvertisedMap.get(nstUUID)==null){
                    log.info("Nst UUID "+nstAdvertisedInfo.getNstId()+" going to be added");
                    nstAdvertisedMap.put(nstUUID,nstAdvertisedInfo);
                }
            }
        }
    }


    //It selects all the NSTs that satisfies the geographical constraints info.
    public HashMap<String,NetworkSliceInternalInfo> filterByLocationConstraints(ArrayList<Long> filteredBucketsId, LocationInfo locationInfo,  HashMap<String,NetworkSliceInternalInfo> networkSliceInternalInfoMap){
        //Step #1: all the NSTs in the different buckets, are put into a map. This process is made once per Vertical Service Instance request, regardless the number of geo constraints.
        if(nstAdvertisedMap==null)
            setNstAdvertisedMap(filteredBucketsId);

        log.info("There are "+nstAdvertisedMap.size()+ " NST(s) advertised.");

        //Step #2: for each advertised NST are checked the geographic constraints.
        if(networkSliceInternalInfoMap ==null)
            networkSliceInternalInfoMap = new HashMap<String,NetworkSliceInternalInfo>();

        for(String nstUUID: nstAdvertisedMap.keySet()){
            NstAdvertisedInfo nstAdvertisedInfo = nstAdvertisedMap.get(nstUUID);
            String domainId = nstAdvertisedInfo.getDomainId();
            //log.info("Checking geographical info about NST with UUID: "+nstUUID +" It has "+nstAdvertisedInfo.getGeographicalAreaInfoList().size() + " location.");
            List<GeographicalAreaInfo> geographicalAreaInfos = nstAdvertisedInfo.getGeographicalAreaInfoList();
            for(int i=0; i<geographicalAreaInfos.size(); i++){
                GeographicalAreaInfo geo = geographicalAreaInfos.get(i);
                double distanceMeter=geo.computeDistanceMeter(locationInfo.getLatitude(), locationInfo.getLongitude());
                //log.debug("The distance between VSI center and NST with UUID "+nstUUID+" at geolocation #"+i+" is " +distanceMeter+ " meters.");
                Map<String,Double> distanceToTheEdges=geo.getDistanceToTheEdgesInMeter(locationInfo.getLatitude(), locationInfo.getLongitude());
                for(String edgeName: distanceToTheEdges.keySet()){
                    double distanceToTheEdge = distanceToTheEdges.get(edgeName);
                    //Step #3: if the geo constraint are satisfied, it is put into the map the nstID, the domainID and the location info of VSI.
                    if(distanceToTheEdge<locationInfo.getRange() && networkSliceInternalInfoMap.get(domainId)==null){
                        log.info("NST with UUID "+nstUUID+"  satisfies the geographical constraints.");
                        networkSliceInternalInfoMap.put(domainId, new NetworkSliceInternalInfo(domainId, nstUUID, locationInfo));
                    }
                }
            }
        }
        if(networkSliceInternalInfoMap.size()==0)
            log.warn("Warning: the geographical filter did not return any NST.");

        return networkSliceInternalInfoMap;
    }

    public boolean embbBucketSelection(BucketEMBB bucketEMBB, VsDescriptor vsd) throws NotExistingEntityException {
        EMBBPerfReq bucketEmbbPerfReq = bucketEMBB.getEmbbPerfReq();
        Map<String, String> params=vsd.getQosParameters();

        if(bucketEmbbPerfReq.getExpDataRateDL()< parseStringToInt(params.get("BitrateMbpsDL"))){
            return false;
        }
        if(bucketEmbbPerfReq.getExpDataRateUL()< parseStringToInt(params.get("BitrateMbpsUL"))){
            return false;
        }
        if(bucketEmbbPerfReq.getuESpeed()< parseStringToInt(params.get("userSpeed"))){
            return false;
        }
        int maxNumberOfUsers = bucketEmbbPerfReq.getActivityFactor()*bucketEmbbPerfReq.getUserDensity()/100;
        if(maxNumberOfUsers< parseStringToInt(params.get("MaximumNumberUsers"))){
            return false;
        }
        if(vsd.getSla().getAvailabilityCoverage()==AVAILABILITY_COVERAGE_HIGH && !bucketEmbbPerfReq.getCoverage().equals("Full network")){
            return false;
        }
        return true;
    }



    public boolean urllcBucketSelection(BucketURLLC bucketURLLC, VsDescriptor vsd) throws NotExistingEntityException {
        URLLCPerfReq bucketUrlccPerfReq = bucketURLLC.getUrllcPerfReq();
        Map<String, String> params=vsd.getQosParameters();
        bucketUrlccPerfReq.getTrafficDensity();
        bucketUrlccPerfReq.getSurvivalTime();
        bucketUrlccPerfReq.getReliability();
        bucketUrlccPerfReq.getPayloadSize();
        bucketUrlccPerfReq.getJitter();
        bucketUrlccPerfReq.getE2eLatency();
        //TODO complete
        return true;
    }


    //Given as input the vsdId:
    // In first place it checks if the bucket scenario is explicitly written into the VSD, select that one to take the NST(s) from.
    // Otherwise, it takes the EMBB performance requirements translated from the QoS parameters and eventually returns the
    // bucket is where the NSTs for the NST E2E composition are available.
    public ArrayList<Long> translateToNstEmbb(VsDescriptor vsd) throws NotExistingEntityException {
        List<Bucket> bucketEMBBList = bucketRepository.findByBucketType(BucketType.EMBB);
        ArrayList<Long> bucketsId= new ArrayList<>();

        String scenarioInVsd = findScenarioInVsd(vsd);
        if(scenarioInVsd!=null){
            log.info("Found scenario in VSD: "+scenarioInVsd);
            for(Bucket bucket: bucketEMBBList){
                String bucketScenarioName = bucket.getBucketScenario().toString();
                if(scenarioInVsd.equals(bucket.getBucketScenario().toString())){
                    bucketsId.add(bucket.getId());
                    log.info("Bucket "+bucketScenarioName+" fits the requirements for VSD with ID "+vsd.getId());
                    return bucketsId;
                }
            }
        }

        for(Bucket bucket: bucketEMBBList){
            if(embbBucketSelection((BucketEMBB)bucket,vsd)){
                String bucketName = bucket.getBucketScenario().toString();
                if(bucket.getNstAdvertisedInfoList().size()==0){
                    log.warn("Bucket "+bucketName+" fits the requirements but no NST were found.");
                }
                else {
                    log.warn("Bucket "+bucketName+" fits exactly the requirements. There are "+bucket.getNstAdvertisedInfoList().size() +" NST(s) available.");
                    bucketsId.add(bucket.getId());
                }
            }
        }
        log.info(Arrays.toString(bucketsId.toArray()));
        return bucketsId;
    }

    public ArrayList<Long> translateToNstUrllc(VsDescriptor vsd) throws NotExistingEntityException {
        List<Bucket> bucketURLLCList = bucketRepository.findByBucketType(BucketType.URLLC);
        ArrayList<Long> bucketsId= new ArrayList<>();

        String scenarioInVsd = findScenarioInVsd(vsd);

        if(scenarioInVsd!=null){
            log.info("Found scenario in VSD: "+scenarioInVsd);
            for(Bucket bucket: bucketURLLCList){
                String bucketScenarioName = bucket.getBucketScenario().toString();
                if(scenarioInVsd.equals(bucket.getBucketScenario().toString())){
                    bucketsId.add(bucket.getId());
                    log.info("Bucket "+bucketScenarioName+" fits exactly the requirements for VSD with ID "+vsd.getId());
                    return bucketsId;
                }
            }
        }

        for(Bucket bucket: bucketURLLCList){
            if(urllcBucketSelection((BucketURLLC)bucket,vsd)){
                String bucketName = bucket.getBucketScenario().toString();
                if(bucket.getNstAdvertisedInfoList().size()==0){
                    log.warn("Bucket "+bucketName+" fits the requirements but no NST were found.");
                }
                else {
                    log.warn("Bucket "+bucketName+" fits the requirements. There are "+bucket.getNstAdvertisedInfoList().size() +" NST(s) available.");
                    bucketsId.add(bucket.getId());
                }
            }
        }
        log.info(Arrays.toString(bucketsId.toArray()));
        return bucketsId;
    }


    public ArrayList<Long> translate(String vsdId) throws NotExistingEntityException {
        VsDescriptor vsd = vsDescriptorCatalogueService.getVsd(vsdId);
        SliceServiceType sliceType=vsd.getSst();
        if(sliceType== SliceServiceType.EMBB){
            return translateToNstEmbb(vsd);
        }
        if(sliceType==SliceServiceType.URLLC){
            return translateToNstUrllc(vsd);
        }
        return null;
    }


}
