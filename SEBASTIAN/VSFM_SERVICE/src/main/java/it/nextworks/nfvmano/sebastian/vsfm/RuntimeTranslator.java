package it.nextworks.nfvmano.sebastian.vsfm;

import it.nextworks.nfvmano.catalogue.blueprint.elements.SliceServiceType;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsDescriptor;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.LocationInfo;
import it.nextworks.nfvmano.libs.ifa.templates.EMBBPerfReq;
import it.nextworks.nfvmano.libs.ifa.templates.GeographicalAreaInfo;
import it.nextworks.nfvmano.libs.ifa.templates.URLLCPerfReq;
import it.nextworks.nfvmano.libs.ifa.templates.plugAndPlay.PpFunction;
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

    public Map<String, NstAdvertisedInfo> setNstAdvertisedMap(ArrayList<Long> filteredBucketsId){
        nstAdvertisedMap = new HashMap<String, NstAdvertisedInfo>();
        for(Long bucketId: filteredBucketsId){
            log.info("Getting NST from bucket with ID "+bucketId);
            Bucket bucket=bucketRepository.findById(bucketId).get();

            for(NstAdvertisedInfo nstAdvertisedInfo: bucket.getNstAdvertisedInfoList()){
                String nstUUID = nstAdvertisedInfo.getNstId();
                if(bucket.getBucketType()!=BucketType.PP) {
                    String  compositeNstUuid[] = nstUUID.split("_");
                    nstUUID = compositeNstUuid[0];
                }
                if(nstAdvertisedMap.get(nstUUID)==null){
                    NstAdvertisedInfo nstAdvertisedInfoTmp
                            = new NstAdvertisedInfo(nstUUID, nstAdvertisedInfo.getDomainId(), nstAdvertisedInfo.getGeographicalAreaInfoList(), nstAdvertisedInfo.getPpFunctionList(), nstAdvertisedInfo.getKpiList());
                    nstAdvertisedMap.put(nstUUID,nstAdvertisedInfoTmp);
                }
            }
        }

        return nstAdvertisedMap;
    }



    private synchronized List<NstAdvertisedInfo> getNsstFromNstParentUuid(String nstUuid, BucketType bucketType){
        List<NstAdvertisedInfo> nstAdvertisedInfos = new ArrayList<NstAdvertisedInfo>();
        List<Bucket> bucketList = bucketRepository.findByBucketType(bucketType);
        for(Bucket bucket: bucketList){
            for(NstAdvertisedInfo nstAdvertisedInfo: bucket.getNstAdvertisedInfoList()){
                if(nstAdvertisedInfo.getNstId().contains(nstUuid)){
                    nstAdvertisedInfos.add(nstAdvertisedInfo);
                }
            }
        }
        return nstAdvertisedInfos;
    }

    //It selects all the NSTs that satisfies the geographical constraints info.
    public HashMap<String,NetworkSliceInternalInfo> filterByLocationConstraints(Map<String, NstAdvertisedInfo> mapNstAdverisedInfo, LocationInfo locationInfo, BucketType bucketType){
        if(bucketType==null){
            log.warn("Not able to find geographical location. Please check the Slicetype Into VSD");
            return new HashMap<String,NetworkSliceInternalInfo>();
        }
        List<NstAdvertisedInfo> nsstAdvertisedInfos = new ArrayList<>();

        for(String nstUuid: mapNstAdverisedInfo.keySet()) {
            nsstAdvertisedInfos.addAll(getNsstFromNstParentUuid(nstUuid, bucketType));
        }

        HashMap<String,NetworkSliceInternalInfo> networkSliceInternalInfoMapGeoFilter = new HashMap<String,NetworkSliceInternalInfo>();
        log.info("There are "+nsstAdvertisedInfos.size()+ " NST(s) advertised to filter against the geographical constraints.");

        //Step #2: for each advertised NST are checked the geographic constraints.
            for(NstAdvertisedInfo nstAdvertisedInfo: nsstAdvertisedInfos){
                String nstUUID = nstAdvertisedInfo.getNstId();
            String domainId = nstAdvertisedInfo.getDomainId();
            log.info("Checking geographical info about NST with UUID: "+nstUUID +" It has "+nstAdvertisedInfo.getGeographicalAreaInfoList().size() + " location.");
            List<GeographicalAreaInfo> geographicalAreaInfo = nstAdvertisedInfo.getGeographicalAreaInfoList();

            //Step #2.5: For each geo info into the NST, is checked whether the condition is satisfied or not
            for(int i=0; i<geographicalAreaInfo.size(); i++){
                GeographicalAreaInfo geo = geographicalAreaInfo.get(i);
                //double distanceMeter=geo.computeDistanceMeter(locationInfo.getLatitude(), locationInfo.getLongitude());
                //log.debug("The distance between VSI center and NST with composite UUID "+nstUUID+" at geolocation #"+i+" is " +distanceMeter+ " meters.");
                Map<String,Double> distanceToTheEdges=geo.getDistanceToTheEdgesInMeter(locationInfo.getLatitude(), locationInfo.getLongitude());
                for(String edgeName: distanceToTheEdges.keySet()){
                    double distanceToTheEdge = distanceToTheEdges.get(edgeName);
                    //Step #3: if the geo constraint are satisfied, it is put into the map the nstID, the domainID and the location info of VSI.
                    if(distanceToTheEdge<locationInfo.getRange() && networkSliceInternalInfoMapGeoFilter.get(domainId)==null){
                        log.info("NST with composite UUID "+nstUUID+"  satisfies the geographical constraints.");
                        nstUUID = nstUUID.split("_")[0];
                        networkSliceInternalInfoMapGeoFilter.put(domainId, new NetworkSliceInternalInfo(domainId, nstUUID, locationInfo));
                    }
                }
            }
        }
        if(networkSliceInternalInfoMapGeoFilter.size()==0)
            log.warn("The geographical filter did not return any NST.");

        return networkSliceInternalInfoMapGeoFilter;
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
       ;
        if(Integer.valueOf(params.get("latency"))> bucketUrlccPerfReq.getE2eLatency()){
            return false;
        }
        if(Integer.valueOf(params.get("trafficDensity"))> bucketUrlccPerfReq.getTrafficDensity()){
            return false;
        }
        if(Integer.valueOf(params.get("reliability"))< bucketUrlccPerfReq.getReliability()){
            return false;
        }
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
                    log.info("Bucket "+bucketName+" fits the requirements. There are "+bucket.getNstAdvertisedInfoList().size() +" NST(s) available.");
                    bucketsId.add(bucket.getId());
                }
            }
        }
        log.info(Arrays.toString(bucketsId.toArray()));
        return bucketsId;
    }


    private void printNstAdvertisedFiltered(Map<String,NstAdvertisedInfo> nstFiltered){
        if(nstFiltered.size()==0){
            log.info("No elements found");
        }
        for(String nstUuid: nstFiltered.keySet()){
            NstAdvertisedInfo nstAdvertisedInfo= nstFiltered.get(nstUuid);
            log.info("nstUuid "+nstAdvertisedInfo.getNstId() +
                    " domainId "+nstAdvertisedInfo.getDomainId() +
                    " P&P f.count "+nstAdvertisedInfo.getPpFunctionList().size()+
                    " geo is null {}",nstAdvertisedInfo.getGeographicalAreaInfoList()==null);
        }
    }

    private Map<String,NstAdvertisedInfo> filterAgainstKpiInVsd(VsDescriptor vsDescriptor, Map<String,NstAdvertisedInfo> nstAdvertisedFiltered){
        List<String> kpiListVsd=vsDescriptor.getKpiList();

        if(kpiListVsd!=null && kpiListVsd.size()>0){
            Set<String> nsstToRemoveSet = new HashSet<String>();
            for(String nsstUuid: nstAdvertisedFiltered.keySet()){
                NstAdvertisedInfo nsstAdvertisedInfo = nstAdvertisedFiltered.get(nsstUuid);
                List<String> nsstKpiList = nsstAdvertisedInfo.getKpiList();

                if(nsstKpiList==null || nsstKpiList.size()==0){
                    log.info("NSST with UUID "+nsstUuid+" does not contain any KPI.");
                    nsstToRemoveSet.add(nsstUuid);
                    continue;
                }

                for(String kpiVsd: kpiListVsd){
                    if(!nsstKpiList.contains(kpiVsd)){
                        log.info("NSST with UUID "+nsstUuid+" does not satisfy KPI "+kpiVsd+".");
                        nsstToRemoveSet.add(nsstUuid);
                        continue;
                    }
                }
            }
            log.info("Before removal: "+ nstAdvertisedFiltered.size());
            for(String nsstUuidToRemove: nsstToRemoveSet){
                nstAdvertisedFiltered.remove(nsstUuidToRemove);
            }

            log.info("Number of NSST that satisfies the KPI filtering: "+ nstAdvertisedFiltered.size());
        }

        else{
            log.info("No KPI found into VSD. Skipping KPI filtering.");
        }
        return nstAdvertisedFiltered;
    }

    public Map<String,NstAdvertisedInfo> translateVsdToNst(VsDescriptor vsDescriptor) throws NotExistingEntityException {
        String vsdId = vsDescriptor.getVsDescriptorId();
        //Filter #1: Performance requirements.
        //Given the Vsd ID, it gets the list of suitable buckets that satisfies the requirements, then
        //A map (key: uuid of nst advertised, value the nst advertised itself) is set. In the case of NST with only P&P function, the only ID returned is the P&P bucket.
        ArrayList<Long> suitableBuckets = findSuitableBucketsId(vsdId);
        Map<String,NstAdvertisedInfo> nstAdvertisedFiltered = new HashMap<String,NstAdvertisedInfo>();

        if(suitableBuckets==null){
            log.warn("Either no suitable bucket or NST into buckets found.");
            return nstAdvertisedFiltered;
        }

        nstAdvertisedFiltered = setNstAdvertisedMap(suitableBuckets);
        log.info("Filtering against perf req");
        printNstAdvertisedFiltered(nstAdvertisedFiltered);

        //Filter #2: KPI if any
        nstAdvertisedFiltered= filterAgainstKpiInVsd(vsDescriptor, nstAdvertisedFiltered);
        log.info("Filtering against KPI");
        printNstAdvertisedFiltered(nstAdvertisedFiltered);

        if(nstAdvertisedFiltered.size()==0){
            log.warn("KPI results in no element.");
            return nstAdvertisedFiltered;
        }

        // nstAdvertisedFiltered contains the filtered advertised nst by performance requirements or P&P function, depending on SsT into VSD.
        // nstAdvertisedFilteredByConstraintsMap contains the filtered advertised nst by PP.
        //Filter #3: P&P function
        Map<String,NstAdvertisedInfo> nstAdvertisedFilteredByPPFunctions  = getFilteredAdvNstByPPFunction(vsDescriptor);
        log.info("PP filtering");
        printNstAdvertisedFiltered(nstAdvertisedFilteredByPPFunctions);

        //The maps are intersected for having all the advertised nst that satisfies the requirements in terms of performance, KPI (if any) and P&P functions
        Map<String,NstAdvertisedInfo> mapsIntersection = new HashMap<String,NstAdvertisedInfo>();
        for(String nstUuid: nstAdvertisedFiltered.keySet()){
            if(nstAdvertisedFilteredByPPFunctions.get(nstUuid)!=null){
                mapsIntersection.put(nstUuid,nstAdvertisedFiltered.get(nstUuid));
            }
        }
        log.info("The VS Descriptor to Network Service Template translation results in "+mapsIntersection.size()+" element(s).");
        printNstAdvertisedFiltered(mapsIntersection);

        return mapsIntersection;
    }

    public Map<String,NstAdvertisedInfo> getFilteredAdvNstByPPFunction(VsDescriptor vsDescriptor){
        //Filter advertised NST based on P&P functions
        Map<String,NstAdvertisedInfo> nstAdvertisedInfoHashMap = new HashMap<String,NstAdvertisedInfo>();
        List<PpFunction> ppFunctionListVsd = vsDescriptor.getPpFunctionList();
        log.info("Vsd with ID "+vsDescriptor.getId()+ "has "+ppFunctionListVsd.size()+ " pp functions");
        if(ppFunctionListVsd!=null && ppFunctionListVsd.size()>=0){
            List<Bucket> ppBuckets= bucketRepository.findByBucketType(BucketType.PP);
            Bucket ppBucket = ppBuckets.get(0);

            List<NstAdvertisedInfo> nstAdvertisedInfoList = ppBucket.getNstAdvertisedInfoList();
            //Despite there are three for cycles, the computational cost would not be high because the number of ppFunctions is generally low.
            log.info("There are "+nstAdvertisedInfoList.size()+ " NSTs into PP bucket");
            for(NstAdvertisedInfo nstAdvertisedInfo:  nstAdvertisedInfoList){
                List<PpFunction> ppFunctionListAdvNst = nstAdvertisedInfo.getPpFunctionList();
                log.info("Nst with UUID "+nstAdvertisedInfo.getNstId()+ " has "+ppFunctionListAdvNst.size()+ "pp functions");

                for(PpFunction ppFunctionAdvNst: ppFunctionListAdvNst){
                    for(PpFunction ppFunctionVsd: ppFunctionListVsd) {
                        if(ppFunctionAdvNst.equals(ppFunctionVsd)){
                            log.info("Found PP function into NST");
                            nstAdvertisedInfoHashMap.put(nstAdvertisedInfo.getNstId(),nstAdvertisedInfo);
                        }
                    }
                }
            }
        }
        else{
            log.warn("No P&P function into VSD.");
        }
        return nstAdvertisedInfoHashMap;
    }

    //It translates the requirements available into VSD into a list of the IDs of the buckets where there could be NSTs.
    public ArrayList<Long> findSuitableBucketsId(String vsdId) throws NotExistingEntityException {
        VsDescriptor vsd = vsDescriptorCatalogueService.getVsd(vsdId);
        SliceServiceType sliceType=vsd.getSst();
        //Case #1: the vertical service instantiation request, requires EMBB NST type.
        if(sliceType== SliceServiceType.EMBB){
            return translateToNstEmbb(vsd);
        }
        //Case #2: the vertical service instantiation request, requires URLL NST type.
        if(sliceType==SliceServiceType.URLLC){
            return translateToNstUrllc(vsd);
        }

        //Case #2: the vertical service instantiation request, requires NONE type. It means to have only P&P functions for the slice.
        if(sliceType==SliceServiceType.NONE){
            List<Bucket> ppBuckets= bucketRepository.findByBucketType(BucketType.PP);
            Long ppBlucketId = ppBuckets.get(0).getId();
            ArrayList<Long> suitableBuckets = new ArrayList<Long>();
            suitableBuckets.add(ppBlucketId);
            return suitableBuckets;
        }
        log.error("Slice Service type not specified into Vertical Slice Descriptor.");
        return null;
    }


}
