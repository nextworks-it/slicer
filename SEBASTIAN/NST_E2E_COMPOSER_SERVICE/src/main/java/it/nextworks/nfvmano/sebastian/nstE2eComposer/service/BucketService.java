package it.nextworks.nfvmano.sebastian.nstE2eComposer.service;

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

    //private List<BucketEMBB> bucketEMBBList;
    //private List<BucketURLLC> bucketURLLCList;
    private static final Logger log = LoggerFactory.getLogger(BucketService.class);

    @Autowired
    BucketRepository bucketRepository;

    public BucketService(){

    }

    @EventListener
    public void appReady(ApplicationReadyEvent event){
        //bucketEMBBList=new ArrayList<>();
        //bucketURLLCList=new ArrayList<>();
        initBuckets();
        log.info(" Going to test a couple of NST");
        testNstURLCC();
        testNstEMBB();
    }
        private void initBuckets(){
            //16 empty buckets are created in order to differentiate the advertised NSTs
            EMBBPerfReq embbPerfReqUrbanMacro = new EMBBPerfReq(50,25,100000,50000,10000,20,120,"Full network");
            BucketEMBB urbanMacroBucket = new BucketEMBB(BucketScenario.URBAN_MACRO, embbPerfReqUrbanMacro);
            //bucketEMBBList.add(urbanMacroBucket);
            bucketRepository.saveAndFlush(urbanMacroBucket);

            EMBBPerfReq embbPerfReqRuralMacro = new EMBBPerfReq(50,25,1000,500,100,20,120,"Full network");
            BucketEMBB ruralMacroBucket = new BucketEMBB(BucketScenario.RURAL_MACRO, embbPerfReqRuralMacro);
            //bucketEMBBList.add(ruralMacroBucket);
            bucketRepository.saveAndFlush(ruralMacroBucket);

            EMBBPerfReq embbPerfReqBroadbandCrowd = new EMBBPerfReq(25,50,3750000,7500000,500000,30,5,"Confined area");
            BucketEMBB broadbandCrowdBucket = new BucketEMBB(BucketScenario.BROADBAND_ACCESS_CROWD, embbPerfReqBroadbandCrowd);
            //bucketEMBBList.add(broadbandCrowdBucket);
            bucketRepository.saveAndFlush(broadbandCrowdBucket);

            EMBBPerfReq embbPerfReqDenseUrban = new EMBBPerfReq(300,50,750000000,125000,25000,10,60,"Downtown");
            BucketEMBB denseUrbanBucket = new BucketEMBB(BucketScenario.DENSE_URBAN, embbPerfReqDenseUrban);
            //bucketEMBBList.add(denseUrbanBucket);
            bucketRepository.saveAndFlush(denseUrbanBucket);

            //Analyise better below case TODO (activityFactor)
            EMBBPerfReq embbPerfReqIndoor = new EMBBPerfReq(1000,500,15000000,2000000,250000,30,5,"Office and residential");
            BucketEMBB indoorHotspotBucket = new BucketEMBB(BucketScenario.INDOOR_HOTSPOT, embbPerfReqIndoor);
            //bucketEMBBList.add(indoorHotspotBucket);
            bucketRepository.saveAndFlush(indoorHotspotBucket);

            //Analyise better below case TODO (userDensity)
            EMBBPerfReq embbPerfBroadcast = new EMBBPerfReq(200,1,200,1,100,-1,500,"Full network");
            BucketEMBB broadcastBucket = new BucketEMBB(BucketScenario.BROADCAST_LIKE_SERVICE, embbPerfBroadcast);
            //bucketEMBBList.add(broadcastBucket);
            bucketRepository.saveAndFlush(broadcastBucket);

            EMBBPerfReq embbPerfReqHighSpeedVehicle = new EMBBPerfReq(50,25,100000,50000,4000,50,250,"Along roads");
            BucketEMBB hSpeedVehicleBucket = new BucketEMBB(BucketScenario.HIGH_SPEED_VEHICLE, embbPerfReqHighSpeedVehicle);
            //bucketEMBBList.add(hSpeedVehicleBucket);
            bucketRepository.saveAndFlush(hSpeedVehicleBucket);

            //Supposed one train
            EMBBPerfReq embbPerfReqHighSpeedTrain = new EMBBPerfReq(50,25,15000,7500000,1000,30,500,"Along railways");
            BucketEMBB hSpeedTrainBucket = new BucketEMBB(BucketScenario.HIGH_SPEED_TRAIN, embbPerfReqHighSpeedTrain);
            //bucketEMBBList.add(hSpeedTrainBucket);
            bucketRepository.saveAndFlush(hSpeedTrainBucket);

            //Supposed one plane
            EMBBPerfReq embbPerfReqAirplane = new EMBBPerfReq(15,8,1200,600,400,20,1000,"...");
            BucketEMBB airplaneBucket = new BucketEMBB(BucketScenario.AIR_PLANE_CONNECTIVITY, embbPerfReqAirplane);
            //bucketEMBBList.add(airplaneBucket);
            bucketRepository.saveAndFlush(airplaneBucket);


            //below URLCC buckets
            URLLCPerfReq discrAutomURLLCCPerf = new URLLCPerfReq(10,0,0,99.99f,99.99f,10,"Small to big",1000000,100000,"1000 x 1000 x 30 m");
            BucketURLLC bucketDiscreteAutomation = new BucketURLLC(BucketScenario.DISCRETE_AUTOMATION, discrAutomURLLCCPerf);
            //bucketURLLCList.add(bucketDiscreteAutomation);
            bucketRepository.saveAndFlush(bucketDiscreteAutomation);

            URLLCPerfReq prAutomControlURLLCCPerf = new URLLCPerfReq(60,0,100,99.9999f,999.999f,100,"Small to big",100000,1000,"300 x 300 x 50 m");
            BucketURLLC bucketprAutom = new BucketURLLC(BucketScenario.PROCESS_AUTOMATION_REMOTE_CONTROL, prAutomControlURLLCCPerf);
            //bucketURLLCList.add(bucketprAutom);
            bucketRepository.saveAndFlush(bucketprAutom);

            URLLCPerfReq prAutomMonitURLLCCPerf = new URLLCPerfReq(60,0,100,99.9f,99.9f,1,"Small",10000,10000,"300 x 300 x 50 m");
            BucketURLLC bucketPrAutomMonit = new BucketURLLC(BucketScenario.PROCESS_AUTOMATION_MONITORING, prAutomMonitURLLCCPerf);
            //bucketURLLCList.add(bucketPrAutomMonit);
            bucketRepository.saveAndFlush(bucketPrAutomMonit);

            URLLCPerfReq electDistrMediumURLLCCPerf = new URLLCPerfReq(40,0,25,99.9f,99.9f,10,"Small to big",10000,1000,"100 km along power line");
            BucketURLLC bucketElectrDistrMedium = new BucketURLLC(BucketScenario.ELECTRICITY_DISTR_MEDIUM_VOLR, electDistrMediumURLLCCPerf);
            //bucketURLLCList.add(bucketElectrDistrMedium);
            bucketRepository.saveAndFlush(bucketElectrDistrMedium);

            URLLCPerfReq electDistrHighURLLCCPerf = new URLLCPerfReq(5,0,10,99.9999f,99.999f,10,"Small",100000,1000,"200 km along power line");
            BucketURLLC bucketElectrDistrHigh = new BucketURLLC(BucketScenario.ELECTRICITY_DISTR_HIGH_VOLR, electDistrHighURLLCCPerf);
            //bucketURLLCList.add(bucketElectrDistrHigh);
            bucketRepository.saveAndFlush(bucketElectrDistrHigh);

            URLLCPerfReq intellTranspostSysURLLCCPerf = new URLLCPerfReq(30,-0,100,99.9999f,99.999f,10,"Small to big",10000,1000,"2 km along road");
            BucketURLLC bucketIntellTranspostSys = new BucketURLLC(BucketScenario.INTELLIGENT_TR_SYS, intellTranspostSysURLLCCPerf);
            //bucketURLLCList.add(bucketIntellTranspostSys);
            bucketRepository.saveAndFlush(bucketIntellTranspostSys);
        }


    private void bucketizeNst(NST nst){
        SliceType sliceType= nst.getNstServiceProfile().getsST();
        String nstId = nst.getNstId();

        if(sliceType==SliceType.EMBB){
            List<Bucket> bucketEMBBList=bucketRepository.findByBucketType(BucketType.EMBB);
            EMBBPerfReq embbPerfReq=nst.getNstServiceProfile().geteMBBPerfReq().get(0); //TODO: supponsing only one EMBB in the perfreq list. To be generalized ?
            for(Bucket bucket: bucketEMBBList){
                    BucketEMBB bucketEMBB = (BucketEMBB) bucket;
                    if(bucketEMBB.areRequirementsSatisfied(embbPerfReq)) {
                        //Added locally and into the DB
                        bucketEMBB.addNstId(nstId);
                        bucketRepository.saveAndFlush(bucketEMBB);
                        log.info("Added NST with ID " + nstId + " into bucket " + bucket.getBucketType().toString());
                    }
                    else
                        log.info("Cannot add NST with ID "	+nstId+ " into bucket "+bucket.getBucketType().toString());

            }
        }
        else if(sliceType==SliceType.URLLC){
            List<Bucket> bucketURLLCList=bucketRepository.findByBucketType(BucketType.URLLC);
            URLLCPerfReq urllcPerfReq = nst.getNstServiceProfile().getuRLLCPerfReq().get(0);//TODO: supponsing only one URLCC in the perfreq list. To be generalized ?
            for(Bucket bucket: bucketURLLCList){
                BucketURLLC bucketURLLC = (BucketURLLC) bucket;
                if(bucketURLLC.areRequirementsSatisfied(urllcPerfReq)){
                    bucketURLLC.addNstId(nstId);
                    log.info("Added NST with ID "	+nstId+ " into bucket "+bucketURLLC.getBucketType().toString());
                    bucketRepository.saveAndFlush(bucketURLLC);
                }
                else
                    log.info("Cannot add NST with ID "	+nstId+ " into bucket "+bucketURLLC.getBucketType().toString());
            }
        }
    }


    private void testNstURLCC(){
        NstServiceProfile nstServiceProfile = new NstServiceProfile();
        List<URLLCPerfReq> urllcPerfReqList = new ArrayList<URLLCPerfReq>();
        urllcPerfReqList.add(new URLLCPerfReq(1,0,0,99.99999f,99.9999f,100000,"...",10000000,100000,""));
        nstServiceProfile.setuRLLCPerfReq(urllcPerfReqList);
        nstServiceProfile.setsST(SliceType.URLLC);
        NST nst = new NST("nstIdOfURLLC","nstName","nstVersion","nstProvider",null,null,null,nstServiceProfile);
        bucketizeNst(nst);
    }

    private void testNstEMBB(){
        NstServiceProfile nstServiceProfile = new NstServiceProfile();
        List<EMBBPerfReq> embbPerfReqList = new ArrayList<EMBBPerfReq>();
        embbPerfReqList.add(new EMBBPerfReq(10000000,10000000,1000000,100000,40000,25,400,"..."));
        nstServiceProfile.seteMBBPerfReq(embbPerfReqList);
        nstServiceProfile.setsST(SliceType.EMBB);
        NST nst = new NST("nstIdOfEMBBCC","nstName","nstVersion","nstProvider",null,null,null,nstServiceProfile);
        bucketizeNst(nst);
    }


}
