package it.nextworks.nfvmano.sebastian.nsmf.test;

import it.nextworks.nfvmano.libs.ifa.templates.*;
import it.nextworks.nfvmano.sebastian.nsmf.sbi.RanQoSTranslator;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RanTranslatorTest {
    private RanQoSTranslator rqos = new RanQoSTranslator();
    private NST nst = new NST();
    private NstServiceProfile nstServiceProfile;
    private EMBBPerfReq embbPerfReq;
    private URLLCPerfReq urllcPerfReq;

    @Test
    public void translationEMBBTest(){

        nst.setNstName("test_slice");
        nst.setNstProvider("NXW");

        this.nstServiceProfile = new NstServiceProfile();
        this.nstServiceProfile.setsST(SliceType.EMBB);
        this.embbPerfReq = new EMBBPerfReq(100,
                90,
                110,
                95,
                6,
                9,
                1000,
                "coverage");

        List<EMBBPerfReq> embblist = new ArrayList<>();
        embblist.add(this.embbPerfReq);
        this.nstServiceProfile.seteMBBPerfReq(embblist);
        nst.setNstServiceProfile(this.nstServiceProfile);

        List<JSONObject> qosConst = rqos.ranProfileToQoSConstraints(nst);
        System.out.println(qosConst);

    }

    @Test
    public void translationURLLCTest(){

        nst.setNstName("test_slice");
        nst.setNstProvider("NXW");

        this.nstServiceProfile = new NstServiceProfile();
        this.nstServiceProfile.setsST(SliceType.URLLC);
        this.urllcPerfReq = new URLLCPerfReq(
                200,
                2,
                100,
                2,
                2,
                96,
                "apayloadsize",
                5,
                6,
                "bellagrande");
        List<URLLCPerfReq> urllclist = new ArrayList<>();
        urllclist.add(this.urllcPerfReq);
        this.nstServiceProfile.setuRLLCPerfReq(urllclist);
        nst.setNstServiceProfile(this.nstServiceProfile);

        List<JSONObject> qosConst = rqos.ranProfileToQoSConstraints(nst);
        System.out.println(qosConst);

    }
}
