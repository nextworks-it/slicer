package it.nextworks.nfvmano.sebastian.nstE2eComposer.service;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.templates.*;
import it.nextworks.nfvmano.sebastian.nstE2eComposer.repository.BucketRepository;
import it.nextworks.nfvmano.sebastian.nste2eComposer.IM.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BucketService {

    private static final Logger log = LoggerFactory.getLogger(BucketService.class);

    @Autowired
    BucketRepository bucketRepository;

    public BucketService(){

    }

    @EventListener
    public void appReady(ApplicationReadyEvent event){
        initBuckets();
    }
        private void initBuckets(){
            //16 empty buckets are created in order to differentiate the advertised NSTs
            EMBBPerfReq embbPerfReqUrbanMacro = new EMBBPerfReq(50,25,100000,50000,10000,20,120,"Full network");
            BucketEMBB urbanMacroBucket = new BucketEMBB(BucketScenario.URBAN_MACRO, embbPerfReqUrbanMacro);
             bucketRepository.saveAndFlush(urbanMacroBucket);

            EMBBPerfReq embbPerfReqRuralMacro = new EMBBPerfReq(50,25,1000,500,100,20,120,"Full network");
            BucketEMBB ruralMacroBucket = new BucketEMBB(BucketScenario.RURAL_MACRO, embbPerfReqRuralMacro);
            bucketRepository.saveAndFlush(ruralMacroBucket);

            EMBBPerfReq embbPerfReqBroadbandCrowd = new EMBBPerfReq(25,50,3750000,7500000,500000,30,5,"Confined area");
            BucketEMBB broadbandCrowdBucket = new BucketEMBB(BucketScenario.BROADBAND_ACCESS_CROWD, embbPerfReqBroadbandCrowd);
           bucketRepository.saveAndFlush(broadbandCrowdBucket);

            EMBBPerfReq embbPerfReqDenseUrban = new EMBBPerfReq(300,50,750000000,125000,25000,10,60,"Downtown");
            BucketEMBB denseUrbanBucket = new BucketEMBB(BucketScenario.DENSE_URBAN, embbPerfReqDenseUrban);
            bucketRepository.saveAndFlush(denseUrbanBucket);

            //Analyise better below case TODO (activityFactor)
            EMBBPerfReq embbPerfReqIndoor = new EMBBPerfReq(1000,500,15000000,2000000,250000,30,5,"Office and residential");
            BucketEMBB indoorHotspotBucket = new BucketEMBB(BucketScenario.INDOOR_HOTSPOT, embbPerfReqIndoor);
           bucketRepository.saveAndFlush(indoorHotspotBucket);

            //Analyise better below case TODO (userDensity)
            EMBBPerfReq embbPerfBroadcast = new EMBBPerfReq(200,1,200,1,100,-1,500,"Full network");
            BucketEMBB broadcastBucket = new BucketEMBB(BucketScenario.BROADCAST_LIKE_SERVICE, embbPerfBroadcast);
            bucketRepository.saveAndFlush(broadcastBucket);

            EMBBPerfReq embbPerfReqHighSpeedVehicle = new EMBBPerfReq(50,25,100000,50000,4000,50,250,"Along roads");
            BucketEMBB hSpeedVehicleBucket = new BucketEMBB(BucketScenario.HIGH_SPEED_VEHICLE, embbPerfReqHighSpeedVehicle);
           bucketRepository.saveAndFlush(hSpeedVehicleBucket);

            //Supposed one train
            EMBBPerfReq embbPerfReqHighSpeedTrain = new EMBBPerfReq(50,25,15000,7500000,1000,30,500,"Along railways");
            BucketEMBB hSpeedTrainBucket = new BucketEMBB(BucketScenario.HIGH_SPEED_TRAIN, embbPerfReqHighSpeedTrain);
            bucketRepository.saveAndFlush(hSpeedTrainBucket);

            //Supposed one plane
            EMBBPerfReq embbPerfReqAirplane = new EMBBPerfReq(15,8,1200,600,400,20,1000,"...");
            BucketEMBB airplaneBucket = new BucketEMBB(BucketScenario.AIR_PLANE_CONNECTIVITY, embbPerfReqAirplane);
           bucketRepository.saveAndFlush(airplaneBucket);


            //below URLCC buckets
            URLLCPerfReq discrAutomURLLCCPerf = new URLLCPerfReq(10,0,0,99.99f,99.99f,10,"Small to big",1000000,100000,"1000 x 1000 x 30 m");
            BucketURLLC bucketDiscreteAutomation = new BucketURLLC(BucketScenario.DISCRETE_AUTOMATION, discrAutomURLLCCPerf);
           bucketRepository.saveAndFlush(bucketDiscreteAutomation);

            URLLCPerfReq prAutomControlURLLCCPerf = new URLLCPerfReq(60,0,100,99.9999f,999.999f,100,"Small to big",100000,1000,"300 x 300 x 50 m");
            BucketURLLC bucketprAutom = new BucketURLLC(BucketScenario.PROCESS_AUTOMATION_REMOTE_CONTROL, prAutomControlURLLCCPerf);
             bucketRepository.saveAndFlush(bucketprAutom);

            URLLCPerfReq prAutomMonitURLLCCPerf = new URLLCPerfReq(60,0,100,99.9f,99.9f,1,"Small",10000,10000,"300 x 300 x 50 m");
            BucketURLLC bucketPrAutomMonit = new BucketURLLC(BucketScenario.PROCESS_AUTOMATION_MONITORING, prAutomMonitURLLCCPerf);
           bucketRepository.saveAndFlush(bucketPrAutomMonit);

            URLLCPerfReq electDistrMediumURLLCCPerf = new URLLCPerfReq(40,0,25,99.9f,99.9f,10,"Small to big",10000,1000,"100 km along power line");
            BucketURLLC bucketElectrDistrMedium = new BucketURLLC(BucketScenario.ELECTRICITY_DISTR_MEDIUM_VOLR, electDistrMediumURLLCCPerf);
           bucketRepository.saveAndFlush(bucketElectrDistrMedium);

            URLLCPerfReq electDistrHighURLLCCPerf = new URLLCPerfReq(5,0,10,99.9999f,99.999f,10,"Small",100000,1000,"200 km along power line");
            BucketURLLC bucketElectrDistrHigh = new BucketURLLC(BucketScenario.ELECTRICITY_DISTR_HIGH_VOLR, electDistrHighURLLCCPerf);
            bucketRepository.saveAndFlush(bucketElectrDistrHigh);

            URLLCPerfReq intellTranspostSysURLLCCPerf = new URLLCPerfReq(30,-0,100,99.9999f,99.999f,10,"Small to big",10000,1000,"2 km along road");
            BucketURLLC bucketIntellTranspostSys = new BucketURLLC(BucketScenario.INTELLIGENT_TR_SYS, intellTranspostSysURLLCCPerf);
            bucketRepository.saveAndFlush(bucketIntellTranspostSys);
        }


    public void bucketizeNst(NST nst, String ipAddress) throws MalformattedElementException, FailedOperationException, AlreadyExistingEntityException {
        log.debug("Received request to advertise a NS Template from "+ipAddress);
        SliceType sliceType= nst.getNstServiceProfile().getsST();
        String nstId = nst.getNstId();
        if(nstId==null){
            throw new MalformattedElementException("NST UUID is missing");
        }
        boolean isBucketized=false;
        if(sliceType==SliceType.EMBB){
            log.info("NST has slice type EMBB");
            List<Bucket> bucketEMBBList=bucketRepository.findByBucketType(BucketType.EMBB);
            if(nst.getNstServiceProfile().geteMBBPerfReq()==null &&  nst.getNstServiceProfile().geteMBBPerfReq().get(0)!=null)
                throw new MalformattedElementException("EMBB performance requirements missing.");

            EMBBPerfReq embbPerfReq=nst.getNstServiceProfile().geteMBBPerfReq().get(0); //TODO: supposing only one EMBB in the perfreq list. To be generalized ?
            log.info("NST with UUID "+nstId+" has "+nst.getNstServiceProfile().geteMBBPerfReq().size()+" EMBB performance requirements. Taking the first one.");

            for(Bucket bucket: bucketEMBBList){
                    BucketEMBB bucketEMBB = (BucketEMBB) bucket;
                    if(bucketEMBB.areRequirementsSatisfied(embbPerfReq)) {
                        //Added locally and into the DB
                        if(bucketEMBB.addNstId(nstId,ipAddress)==false) {
                            log.info("NST with UUID " + nstId + " advertised from " + ipAddress + "already available.");
                            throw new AlreadyExistingEntityException("NST with UUID " + nstId + " advertised from " + ipAddress + "already available.");
                        }
                        bucketRepository.saveAndFlush(bucketEMBB);
                        isBucketized=true;
                        log.info("Added NST with UUID " + nstId + " into bucket " + bucketEMBB.getBucketScenario().toString());
                    }
                    else
                        log.info("Cannot add NST with UUID "	+nstId+ " into bucket "+bucketEMBB.getBucketScenario().toString());
            }
        }

        else if(sliceType==SliceType.URLLC){
            log.info("NST has slice type URLLC");
            List<Bucket> bucketURLLCList=bucketRepository.findByBucketType(BucketType.URLLC);
            if(nst.getNstServiceProfile().getuRLLCPerfReq()==null && nst.getNstServiceProfile().getuRLLCPerfReq().get(0)!=null)
                throw new MalformattedElementException("URLLC performance requirements missing");
            URLLCPerfReq urllcPerfReq = nst.getNstServiceProfile().getuRLLCPerfReq().get(0);//TODO: supposing only one URLCC in the perfreq list. To be generalized ?
            log.info("NST with UUID "+nstId+" has "+nst.getNstServiceProfile().getuRLLCPerfReq().size()+" URLCC performance requirements. Taking the first one.");

            for(Bucket bucket: bucketURLLCList){
                BucketURLLC bucketURLLC = (BucketURLLC) bucket;
                if(bucketURLLC.areRequirementsSatisfied(urllcPerfReq)){
                    bucketURLLC.addNstId(nstId,ipAddress);
                    log.info("Added NST with UUID "	+nstId+ " into bucket "+bucketURLLC.getBucketScenario().toString());
                    bucketRepository.saveAndFlush(bucketURLLC);
                    isBucketized=true;
                }
                else
                    log.info("Cannot add NST with UUID "	+nstId+ " into bucket "+bucketURLLC.getBucketScenario().toString());
            }
        }
        if(isBucketized==false){
            throw new FailedOperationException("Cannot put NST into any buckets. No buckets requirements are satisfied.");
        }
    }

    public void removeFromBucket(String nstId, String ipAddress) throws NotExistingEntityException {
        log.debug("Received request to remove an advertised NST from "+nstId);
        List<Bucket> bucketList=bucketRepository.findAll();
        boolean found=false;
        for(Bucket bucket: bucketList){
            if(bucket.getNstIdNspMap().get(nstId)!=null && bucket.getNstIdNspMap().get(nstId).equals(ipAddress)){
                found=true;
                bucket.removeNstId(nstId);
                log.info("Going to remove NST advertised with UUID "+ nstId +" announced by "+ ipAddress + " from bucket "+ bucket.getBucketScenario().toString());
                bucketRepository.saveAndFlush(bucket);
            }
        }
        if(found==false) {
            log.info("NST advertised with UUID "+ nstId +" announced by "+ ipAddress +" not found.");
            throw new NotExistingEntityException("NST with UUID" + nstId + " not found");
        }
    }
}
