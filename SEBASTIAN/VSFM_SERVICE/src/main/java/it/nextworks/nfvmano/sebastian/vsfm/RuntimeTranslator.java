package it.nextworks.nfvmano.sebastian.vsfm;

import it.nextworks.nfvmano.catalogue.blueprint.elements.SliceServiceType;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsDescriptor;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.LocationInfo;
import it.nextworks.nfvmano.libs.ifa.templates.EMBBPerfReq;
import it.nextworks.nfvmano.libs.ifa.templates.URLLCPerfReq;
import it.nextworks.nfvmano.sebastian.nstE2eComposer.repository.BucketRepository;
import it.nextworks.nfvmano.sebastian.nste2eComposer.IM.Bucket;
import it.nextworks.nfvmano.sebastian.nste2eComposer.IM.BucketEMBB;
import it.nextworks.nfvmano.sebastian.nste2eComposer.IM.BucketType;
import it.nextworks.nfvmano.sebastian.nste2eComposer.IM.BucketURLLC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static it.nextworks.nfvmano.catalogue.blueprint.elements.AvailabilityCoverageRange.AVAILABILITY_COVERAGE_HIGH;

public class RuntimeTranslator {

    private BucketRepository bucketRepository;
    private VsDescriptorCatalogueService vsDescriptorCatalogueService;

    private static final Logger log = LoggerFactory.getLogger(RuntimeTranslator.class);

    public RuntimeTranslator(BucketRepository bucketRepository,VsDescriptorCatalogueService vsDescriptorCatalogueService){
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


    //Given the filtered bucket id list, it selects all the NSTs that satisfies the geographical constrain info expressed into the locationInfo input.
    public ArrayList<Long> filterByLocationConstraints(ArrayList<Long> filteredBucketsId, LocationInfo locationInfo){

        for(Long bucketId: filteredBucketsId){
            Bucket bucket=bucketRepository.findById(bucketId).get();
            for(String nstId: bucket.getNstIdDomainIdMap().keySet()){
                //TODO implement logic to filter NST by location constraints
            }
        }
        return filteredBucketsId;
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
                if(bucket.getNstIdDomainIdMap().size()==0){
                    log.warn("Bucket "+bucketName+" fits the requirements but no NST were found.");
                }
                else {
                    log.warn("Bucket "+bucketName+" fits exactly the requirements. There are "+bucket.getNstIdDomainIdMap().size() +" NST(s) available.");
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
                if(bucket.getNstIdDomainIdMap().size()==0){
                    log.warn("Bucket "+bucketName+" fits the requirements but no NST were found.");
                }
                else {
                    log.warn("Bucket "+bucketName+" fits the requirements. There are "+bucket.getNstIdDomainIdMap().size() +" NST(s) available.");
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


    //Basically it takes the first two NSTs belonging to the first two different domains.
    public HashMap<String, String> naiveArbitrator(ArrayList<Long> suitableBuckets) {
        log.info("Started naive arbitrator.");
        HashMap<String, String> domainIdNstIdMap = new HashMap<>();
        for(Long bucketId: suitableBuckets){
            Bucket bucket=bucketRepository.findById(bucketId).get();
            Map<String, String> nstIdDomainIdMap=bucket.getNstIdDomainIdMap();
            for(String nstId: nstIdDomainIdMap.keySet()){
                String domainId=nstIdDomainIdMap.get(nstId);
                if(domainIdNstIdMap.get(domainId)==null){
                    domainIdNstIdMap.put(domainId,nstId);
                }
            }
        }
        return domainIdNstIdMap;
    }


}
