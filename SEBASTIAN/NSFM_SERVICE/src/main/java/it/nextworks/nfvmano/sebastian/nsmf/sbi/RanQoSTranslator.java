package it.nextworks.nfvmano.sebastian.nsmf.sbi;

import it.nextworks.nfvmano.libs.ifa.templates.EMBBPerfReq;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
import it.nextworks.nfvmano.libs.ifa.templates.NstServiceProfile;
import it.nextworks.nfvmano.libs.ifa.templates.URLLCPerfReq;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RanQoSTranslator {
    private static final Logger log = LoggerFactory.getLogger(RanQoSTranslator.class);

    public RanQoSTranslator() {

    }

    private List<JSONObject> embbToQos(NstServiceProfile nstServiceProfile){
        int dl, ul;
        Map<String, String> rateParams =  new HashMap<>();
        List<JSONObject> jlist = new ArrayList<>();
        List<EMBBPerfReq> embbPerfReqs = nstServiceProfile.geteMBBPerfReq();
        for(EMBBPerfReq req: embbPerfReqs){

            dl = req.getExpDataRateDL();
            ul = req.getExpDataRateUL();
            rateParams.put("bandIncDir", "DL");
            rateParams.put("bandIncVal", Integer.toString(dl));
            rateParams.put("bandUnitScale", "MB");
            jlist.add(new JSONObject(rateParams));
            rateParams.replace("bandIncDir", "UL");
            rateParams.put("bandIncVal", Integer.toString(ul));
            jlist.add(new JSONObject(rateParams));
        }
        return jlist;
    }

    private List<JSONObject> urllcToQos(NstServiceProfile nstServiceProfile){
        int rate;
        Map<String, String> rateParams =  new HashMap<>();
        List<JSONObject> jlist = new ArrayList<>();
        List<URLLCPerfReq> urllcPerfReqs = nstServiceProfile.getuRLLCPerfReq();
        for( URLLCPerfReq req: urllcPerfReqs){

            rate = req.getExpDataRate();
            rateParams.put("bandIncDir", "DL");
            rateParams.put("bandIncVal", Integer.toString(rate));
            rateParams.put("bandUnitScale", "MB");
            jlist.add(new JSONObject(rateParams));
            rateParams.replace("bandIncDir", "UL");
            rateParams.put("bandIncVal", Integer.toString(rate));
            jlist.add(new JSONObject(rateParams));
        }
        return jlist;
    }

    public List<JSONObject> ranProfileToQoSConstraints(NST nst){
        NstServiceProfile nstServiceProfile = nst.getNstServiceProfile();
        List<JSONObject> qosConstraintsList = new ArrayList<>();
        switch(nstServiceProfile.getsST()){
            case EMBB:
                log.info("Slice Type: EMBB");
                qosConstraintsList = embbToQos(nstServiceProfile);
                break;
            case URLLC:
                log.info("Slice Type: URLLC");
                qosConstraintsList = urllcToQos(nstServiceProfile);
                break;
            default:
                log.info("Slice Type not Supported");
                break;
        }
        return qosConstraintsList;
    }

}
