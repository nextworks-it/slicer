package it.nextworks.nfvmano.core.service;

import it.nextworks.nfvmano.libs.ifa.templates.nst.SliceProfile;
import it.nextworks.nfvmano.libs.ifa.templates.nst.*;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.core.cumucore.Subscriber;
import it.nextworks.nfvmano.sbi.cnc.elements.*;
import it.nextworks.nfvmano.sbi.cnc.messages.NetworkSliceCNC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class NstToNetworkSliceCmcMapper {

    private final static Logger LOG = LoggerFactory.getLogger(NstToNetworkSliceCmcMapper.class);


    public NetworkSliceCNC mapCoreNetworkSliceToNetworkSliceCNC(NstServiceProfile serviceProfile, NSST nsst) {
        NetworkSliceCNC networkSliceCNC = new NetworkSliceCNC();

        Random r = new Random();
        int low =0;
        int high = 1000;
        int result = r.nextInt(high-low) + low;
        networkSliceCNC.sliceName = nsst.getNsstId()+result;
        networkSliceCNC.sliceDescription = nsst.getNsstName() + "provided by " + nsst.getNsstProvider() + " version " + nsst.getNsstVersion();

        if (nsst.isOperationalState())
            networkSliceCNC.activate_slice = 1;
        else
            networkSliceCNC.activate_slice = 0;

        LOG.info("Setting service profile of Network Slice for CNC");
        it.nextworks.nfvmano.sbi.cnc.elements.ServiceProfile serviceProfileCore = new it.nextworks.nfvmano.sbi.cnc.elements.ServiceProfile();

       serviceProfileCore.dLThptPerSlice.maxThpt = serviceProfile.getdLThptPerSlice();
       serviceProfileCore.dLThptPerUE.maxThpt = serviceProfile.getdLThptPerUE();
       serviceProfileCore.dLThptPerUE.guaThpt = Math.round(serviceProfile.getGuaThpt());

       serviceProfileCore.uLThptPerSlice.maxThpt = serviceProfile.getuLThptPerSlice();
       serviceProfileCore.uLThptPerUE.maxThpt = serviceProfile.getuLThptPerUE();
       serviceProfileCore.uLThptPerUE.guaThpt = Math.round(serviceProfile.getGuaThpt());

       serviceProfileCore.dnn = "internet";
       serviceProfileCore.latency = serviceProfile.getLatency();


        int maxDLDataVolume = serviceProfile.getdLThptPerSlice()/1000000; //From bps to Mbps
        int maxULDataVolume = serviceProfile.getuLThptPerSlice()/1000000; //From bps to Mbps

        if(maxDLDataVolume==0) {
            LOG.warn("maxDLDataVolume less than 1Mbps. Considering equal 1Mbps");
            maxDLDataVolume = 1;
        }

        if(maxULDataVolume==0) {
            LOG.warn("maxULDataVolume less than 1Mbps. Considering equal 1Mbps");
            maxULDataVolume = 1;
        }

        serviceProfileCore.maxDLDataVolume = String.valueOf(maxDLDataVolume);
        serviceProfileCore.maxULDataVolume = String.valueOf(maxULDataVolume);

        LOG.info(String.valueOf(maxDLDataVolume));
        LOG.info(String.valueOf(maxULDataVolume));

       serviceProfileCore.maxNumberofUEs = serviceProfile.getMaxNumberofUEs();

       networkSliceCNC.serviceProfile.maxULDataVolume = "99999";
       List<Subscriber> subscribersToRegister = new ArrayList<>();
        Set<String> mccMncPairs = new HashSet<>();
        for(Subscriber subscriber: subscribersToRegister){
            String mccMncPair = subscriber.getImsi().substring(0,5);
            mccMncPairs.add(mccMncPair);
        }
        ArrayList<PLMNIdList> plmnIdLists = new ArrayList<>();
        for(String mccMncPair: mccMncPairs){
            LOG.info("Setting PLMN ID");
            String mcc = mccMncPair.substring(0,3);
            String mnc = mccMncPair.substring(3,5);


            PLMNIdList plmnId = new PLMNIdList();
            plmnId.mcc = mcc;
            plmnId.mnc = mnc;
            plmnIdLists.add(plmnId);
        }
        ArrayList<SNSSAIList> snssaiLists = new ArrayList<>();
       //if(serviceProfile.getsST()==SliceType.EMBB){
           LOG.info("Setting SST");
           SNSSAIList snssaiList = new SNSSAIList();
           snssaiList.sst = 1;
           snssaiList.sd = "edf1a3";
           snssaiLists.add(snssaiList);
       //}
       serviceProfileCore.pLMNIdList = plmnIdLists;
       serviceProfileCore.sNSSAIList = snssaiLists;
       serviceProfileCore.survivalTime = serviceProfile.getSurvivalTime();

       networkSliceCNC.serviceProfile = serviceProfileCore;

        LOG.info("Setting network slice subnet");
        NetworkSliceSubnet networkSliceSubnet = new NetworkSliceSubnet();

        String nsInfo = "ns Info";
        //String nsInfo = nsst.getNsdInfo().getNsdId() + nsst.getNsdInfo().getNsdDescription() + nsst.getNsdInfo().getNsdName() + nsst.getNsdInfo().getNsdVersion();

        if(nsst.getAdministrativeState()!=null)
            networkSliceSubnet.administrativeState = nsst.getAdministrativeState().toString();
        else
            networkSliceSubnet.administrativeState = "";
        networkSliceSubnet.nsInfo = nsInfo;

        networkSliceSubnet.epTransport = new EpTransport();
        networkSliceSubnet.epTransport.ioAddress ="";
        networkSliceSubnet.epTransport.logicInterfaceId ="";
        networkSliceSubnet.epTransport.qosProfile =9;
        ArrayList<String> epApplications = new ArrayList<>();
        epApplications.add("internet");
        networkSliceSubnet.epTransport.epApplication = epApplications;
        networkSliceSubnet.managedFunction = "";



        networkSliceSubnet.sNSSAIList = snssaiLists;
        networkSliceSubnet.sliceProfile = new it.nextworks.nfvmano.sbi.cnc.elements.SliceProfile();
        LOG.warn("The Network Slice CNC supports only one Network SliceSubnet .");
        PrfReq prfReq = new PrfReq();
        SliceProfile sliceProfile = nsst.getSliceProfileList().get(0);

        networkSliceSubnet.sliceProfile.pLMNIdList = sliceProfile.getpLMNIdList();
        LOG.info("Setting network slice subnet");

        if(sliceProfile.geteMBBPerfReq()!=null && sliceProfile.geteMBBPerfReq().size()>0) {
            EMBBPerfReq embbPerfReq = sliceProfile.geteMBBPerfReq().get(0);
                    LOG.info("Setting Perf req Embb list");
                    PerfReqEmbbList perfReqEmbbList = new PerfReqEmbbList();
                    perfReqEmbbList.activityFactory = embbPerfReq.getActivityFactor();
                    perfReqEmbbList.areaTrafficCapDL = embbPerfReq.getAreaTrafficCapDL()/1000000;;
                    perfReqEmbbList.areaTrafficCapUL = embbPerfReq.getExpDataRateUL()/1000000;
                    perfReqEmbbList.expDataRateDL = embbPerfReq.getExpDataRateDL()/1000000;
                    perfReqEmbbList.expDataRateUL = embbPerfReq.getExpDataRateUL()/1000000;;
                    perfReqEmbbList.userDensity = embbPerfReq.getOverallUserDensity();

                    prfReq.perfReqEmbbList = perfReqEmbbList;
            }
            else{
                LOG.warn("No EMBB Perf req list found");
            }
            if(sliceProfile.getuRLLCPerfReq()!=null && sliceProfile.getuRLLCPerfReq().size()> 0) {
                URLLCPerfReq urllcPerfReq = sliceProfile.getuRLLCPerfReq().get(0);
                LOG.info("Setting URLCC Perf req");
                PerfReqUrllcList perfReqUrllcList = new PerfReqUrllcList();
                perfReqUrllcList.cSAvailabilityTarget = Math.round(urllcPerfReq.getCSAvailabilityTarget());
                perfReqUrllcList.cSReliabilityMeanTime = Math.round(Float.parseFloat(urllcPerfReq.getCSReliabilityMeanTime()));
                perfReqUrllcList.expDataRateDL = urllcPerfReq.getExpDataRate();
                perfReqUrllcList.msgSizeByte = urllcPerfReq.getMsgSizeByte();

                prfReq.perfReqUrllcList = perfReqUrllcList;
            }
            else{
                LOG.warn("No URLLC Perf req found");
            }
            networkSliceSubnet.sliceProfile.prfReq = prfReq;


        networkSliceCNC.networkSliceSubnet = networkSliceSubnet;
        return networkSliceCNC;
    }
}
