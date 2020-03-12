package it.nextworks.nfvmano.sebastian.vsfm.runtimetranslator;

import it.nextworks.nfvmano.libs.ifa.templates.*;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.NsmfRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

@Service
public class BucketService {

    private List<BucketEMBB> bucketEMBBList;
    private List<BucketURLLC> bucketURLLCList;
    private static final Logger log = LoggerFactory.getLogger(BucketService.class);

    public BucketService(){
        bucketEMBBList=new ArrayList<>();
        bucketURLLCList=new ArrayList<>();
        initBuckets();
        log.info(" Going to test a couple of NST");
        testNstURLCC();
        testNstEMBB();
    }

        private void initBuckets(){
            //16 empty buckets are created in order to differentiate the advertised NSTs
            EMBBPerfReq embbPerfReqUrbanMacro = new EMBBPerfReq(50,25,100000,50000,10000,20,120,"Full network");
            BucketEMBB urbanMacroBucket = new BucketEMBB(BucketType.URBAN_MACRO, embbPerfReqUrbanMacro);

            EMBBPerfReq embbPerfReqRuralMacro = new EMBBPerfReq(50,25,1000,500,100,20,120,"Full network");
            BucketEMBB ruralMacroBucket = new BucketEMBB(BucketType.RURAL_MACRO, embbPerfReqRuralMacro);

            EMBBPerfReq embbPerfReqBroadbandCrowd = new EMBBPerfReq(25,50,3750000,7500000,500000,30,5,"Confined area");
            BucketEMBB broadbandCrowdBucket = new BucketEMBB(BucketType.BROADBAND_ACCESS_CROWD, embbPerfReqBroadbandCrowd);

            EMBBPerfReq embbPerfReqDenseUrban = new EMBBPerfReq(300,50,750000000,125000,25000,10,60,"Downtown");
            BucketEMBB denseUrbanBucket = new BucketEMBB(BucketType.DENSE_URBAN, embbPerfReqDenseUrban);

            //Analyise better below case TODO (activityFactor)
            EMBBPerfReq embbPerfReqIndoor = new EMBBPerfReq(1000,500,15000000,2000000,250000,30,5,"Office and residential");
            BucketEMBB indoorHotspotBucket = new BucketEMBB(BucketType.INDOOR_HOTSPOT, embbPerfReqIndoor);

            //Analyise better below case TODO (userDensity)
            EMBBPerfReq embbPerfBroadcast = new EMBBPerfReq(200,1,200,1,100,-1,500,"Full network");
            BucketEMBB broadcastBucket = new BucketEMBB(BucketType.BROADCAST_LIKE_SERVICE, embbPerfBroadcast);

            EMBBPerfReq embbPerfReqHighSpeedVehicle = new EMBBPerfReq(50,25,100000,50000,4000,50,250,"Along roads");
            BucketEMBB hSpeedVehicleBucket = new BucketEMBB(BucketType.HIGH_SPEED_VEHICLE, embbPerfReqHighSpeedVehicle);

            //Supposed one train
            EMBBPerfReq embbPerfReqHighSpeedTrain = new EMBBPerfReq(50,25,15000,7500000,1000,30,500,"Along railways");
            BucketEMBB hSpeedTrainBucket = new BucketEMBB(BucketType.HIGH_SPEED_TRAIN, embbPerfReqHighSpeedTrain);

            //Supposed one plane
            EMBBPerfReq embbPerfReqAirplane = new EMBBPerfReq(15,8,1200,600,400,20,1000,"...");
            BucketEMBB airplaneBucket = new BucketEMBB(BucketType.AIR_PLANE_CONNECTIVITY, embbPerfReqAirplane);

            bucketEMBBList.add(urbanMacroBucket);
            bucketEMBBList.add(ruralMacroBucket);
            bucketEMBBList.add(broadbandCrowdBucket);
            bucketEMBBList.add(denseUrbanBucket);
            bucketEMBBList.add(indoorHotspotBucket);
            bucketEMBBList.add(broadcastBucket);
            bucketEMBBList.add(hSpeedVehicleBucket);
            bucketEMBBList.add(hSpeedTrainBucket);
            bucketEMBBList.add(airplaneBucket);

            URLLCPerfReq discrAutomURLLCCPerf = new URLLCPerfReq(10,0,0,99.99f,99.99f,10,"Small to big",1000000,100000,"1000 x 1000 x 30 m");
            BucketURLLC bucketDiscreteAutomation = new BucketURLLC(BucketType.DISCRETE_AUTOMATION, discrAutomURLLCCPerf);

            URLLCPerfReq prAutomControlURLLCCPerf = new URLLCPerfReq(60,0,100,99.9999f,999.999f,100,"Small to big",100000,1000,"300 x 300 x 50 m");
            BucketURLLC bucketprAutom = new BucketURLLC(BucketType.PROCESS_AUTOMATION_REMOTE_CONTROL, prAutomControlURLLCCPerf);

            URLLCPerfReq prAutomMonitURLLCCPerf = new URLLCPerfReq(60,0,100,99.9f,99.9f,1,"Small",10000,10000,"300 x 300 x 50 m");
            BucketURLLC bucketPrAutomMonit = new BucketURLLC(BucketType.PROCESS_AUTOMATION_MONITORING, prAutomMonitURLLCCPerf);

            URLLCPerfReq electDistrMediumURLLCCPerf = new URLLCPerfReq(40,0,25,99.9f,99.9f,10,"Small to big",10000,1000,"100 km along power line");
            BucketURLLC bucketElectrDistrMedium = new BucketURLLC(BucketType.ELECTRICITY_DISTR_MEDIUM_VOLR, electDistrMediumURLLCCPerf);

            URLLCPerfReq electDistrHighURLLCCPerf = new URLLCPerfReq(5,0,10,99.9999f,99.999f,10,"Small",100000,1000,"200 km along power line");
            BucketURLLC bucketElectrDistrHigh = new BucketURLLC(BucketType.ELECTRICITY_DISTR_HIGH_VOLR, electDistrHighURLLCCPerf);


            URLLCPerfReq intellTranspostSysURLLCCPerf = new URLLCPerfReq(30,-0,100,99.9999f,99.999f,10,"Small to big",10000,1000,"2 km along road");
            BucketURLLC bucketIntellTranspostSys = new BucketURLLC(BucketType.INTELLIGENT_TR_SYS, intellTranspostSysURLLCCPerf);

            bucketURLLCList.add(bucketDiscreteAutomation);
            bucketURLLCList.add(bucketprAutom);
            bucketURLLCList.add(bucketPrAutomMonit);
            bucketURLLCList.add(bucketElectrDistrMedium);
            bucketURLLCList.add(bucketElectrDistrHigh);
            bucketURLLCList.add(bucketIntellTranspostSys);
        }

    private void classifyNst(NST nst){
        SliceType sliceType= nst.getNstServiceProfile().getsST();
        String nstId = nst.getNstId();
        if(sliceType==SliceType.EMBB){
            EMBBPerfReq embbPerfReq=nst.getNstServiceProfile().geteMBBPerfReq().get(0); //TODO: supponsing only one EMBB in the perfreq list. To be generalized ?
            for(BucketEMBB bucketEMBB: bucketEMBBList){
                if(bucketEMBB.areRequirementsSatisfied(embbPerfReq)){
                    bucketEMBB.addNstId(nstId);
                    log.info("Added NST with ID "	+nstId+ " into bucket "+bucketEMBB.getBucketType().toString());
                }
                else{
                    log.info("Cannot add NST with ID "	+nstId+ " into bucket "+bucketEMBB.getBucketType().toString());
                }
            }
        }
        else if(sliceType==SliceType.URLLC){
            URLLCPerfReq urllcPerfReq = nst.getNstServiceProfile().getuRLLCPerfReq().get(0);//TODO: supponsing only one URLCC in the perfreq list. To be generalized ?
            for(BucketURLLC bucketURLLC: bucketURLLCList){
                if(bucketURLLC.areRequirementsSatisfied(urllcPerfReq)){
                    bucketURLLC.addNstId(nstId);
                    log.info("Added NST with ID "	+nstId+ " into bucket "+bucketURLLC.getBucketType().toString());
                }
                else{
                    log.info("Cannot add NST with ID "	+nstId+ " into bucket "+bucketURLLC.getBucketType().toString());
                }
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
        classifyNst(nst);
    }

    private void testNstEMBB(){
        NstServiceProfile nstServiceProfile = new NstServiceProfile();
        List<EMBBPerfReq> embbPerfReqList = new ArrayList<EMBBPerfReq>();
        embbPerfReqList.add(new EMBBPerfReq(10000000,10000000,1000000,100000,40000,25,400,"..."));
        nstServiceProfile.seteMBBPerfReq(embbPerfReqList);
        nstServiceProfile.setsST(SliceType.EMBB);
        NST nst = new NST("nstIdOfEMBBCC","nstName","nstVersion","nstProvider",null,null,null,nstServiceProfile);
        classifyNst(nst);
    }

}
