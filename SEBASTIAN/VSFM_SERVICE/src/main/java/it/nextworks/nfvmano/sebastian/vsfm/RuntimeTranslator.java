package it.nextworks.nfvmano.sebastian.vsfm;

import it.nextworks.nfvmano.catalogue.blueprint.elements.VsDescriptor;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.LocationInfo;
import it.nextworks.nfvmano.libs.ifa.templates.EMBBPerfReq;
import it.nextworks.nfvmano.sebastian.nstE2eComposer.repository.BucketRepository;
import it.nextworks.nfvmano.sebastian.nste2eComposer.IM.Bucket;
import it.nextworks.nfvmano.sebastian.nste2eComposer.IM.BucketEMBB;
import it.nextworks.nfvmano.sebastian.nste2eComposer.IM.BucketType;
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

    private Integer tryParse(String text) {
        if(text==null)
            return 0;
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    public boolean bucketSelection(BucketEMBB bucketEMBB, VsDescriptor vsd) throws NotExistingEntityException {
        EMBBPerfReq bucketEmbbPerfReq = bucketEMBB.getEmbbPerfReq();
        Map<String, String> params=vsd.getQosParameters();

        if(bucketEmbbPerfReq.getExpDataRateDL()<tryParse(params.get("BitrateMbpsDL"))){
            return false;
        }
        if(bucketEmbbPerfReq.getExpDataRateUL()<tryParse(params.get("BitrateMbpsUL"))){
            return false;
        }
        if(bucketEmbbPerfReq.getuESpeed()<tryParse(params.get("userSpeed"))){
            return false;
        }
        int maxNumberOfUsers = bucketEmbbPerfReq.getActivityFactor()*bucketEmbbPerfReq.getUserDensity()/100;
        if(maxNumberOfUsers<tryParse(params.get("MaximumNumberUsers"))){
            return false;
        }
        if(vsd.getSla().getAvailabilityCoverage()==AVAILABILITY_COVERAGE_HIGH && !bucketEmbbPerfReq.getCoverage().equals("Full network")){
            return false;
        }
        return true;
    }


    public ArrayList<Long> translate(String vsdId) throws NotExistingEntityException {
        List<Bucket> bucketEMBBList = bucketRepository.findByBucketType(BucketType.EMBB);
        VsDescriptor vsd = vsDescriptorCatalogueService.getVsd(vsdId);
        ArrayList<Long> bucketsId= new ArrayList<>();
        for(Bucket bucket: bucketEMBBList){
            if(bucketSelection((BucketEMBB)bucket,vsd)){
                String bucketName = bucket.getBucketScenario().toString();
                if(bucket.getNstIdDomainIdMap().size()==0){
                    log.warn("Bucket "+bucketName+" fits the requirements but no NST were found.");
                }
                else {
                    log.warn("Bucket "+bucketName+" fits the requirements. There are "+bucket.getNstIdDomainIdMap().size() +" available.");
                    bucketsId.add(bucket.getId());
                }
            }
        }
        log.info(Arrays.toString(bucketsId.toArray()));
        return bucketsId;
    }

    public ArrayList<Long> filterByLocationConstraints(ArrayList<Long> filteredBucketsId, LocationInfo locationInfo){

        for(Long bucketId: filteredBucketsId){
            	Bucket bucket=bucketRepository.findById(bucketId).get();
            	for(String nstId: bucket.getNstIdDomainIdMap().keySet()){
            //TODO implement logic to filter NST by location constraint
            }
        }
        return filteredBucketsId;
    }

    //Basically it takes the first two NSTs belonging to two different domains
    public HashMap<String, String> naiveArbitrator(ArrayList<Long> suitableBuckets) {
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
