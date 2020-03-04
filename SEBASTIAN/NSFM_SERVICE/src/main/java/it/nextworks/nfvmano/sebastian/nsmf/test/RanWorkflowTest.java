package it.nextworks.nfvmano.sebastian.nsmf.test;

import it.nextworks.nfvmano.libs.ifa.templates.EMBBPerfReq;
import it.nextworks.nfvmano.libs.ifa.templates.NST;
import it.nextworks.nfvmano.libs.ifa.templates.NstServiceProfile;
import it.nextworks.nfvmano.libs.ifa.templates.SliceType;
import it.nextworks.nfvmano.sebastian.nsmf.sbi.FlexRanService;
import it.nextworks.nfvmano.sebastian.nsmf.sbi.RanQoSTranslator;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RanWorkflowTest {


    RanQoSTranslator rqos = new RanQoSTranslator();
    FlexRanService flexRanService = new FlexRanService();
    private UUID sliceId = UUID.fromString("bf5cbb04-345c-479f-8511-f6f01b2b822d");

    @Test
    public void createRanSliceAndConfigureIt(){
        //NST nst = assebleNst();
        //List<JSONObject> qosConst = rqos.ranProfileToQoSConstraints(nst);

        flexRanService.setFlexRanURL("10.8.205.26:9999");
        flexRanService.setRanAdapterUrl("10.8.202.15:9090");
        HttpStatus httpStatus = flexRanService.createRanSlice(sliceId);
        flexRanService.mapIdsRemotely(sliceId);
        // pause here!!!!
        flexRanService.removeRemoteMapping(sliceId);
        flexRanService.terminateRanSlice(sliceId);

    }

    public NST assebleNst(){
        NST nst;
        NstServiceProfile nstServiceProfile;
        EMBBPerfReq embbPerfReq;
        nst = new NST();
        nst.setNstName("test_slice");
        nst.setNstProvider("NXW");

        nstServiceProfile = new NstServiceProfile();
        nstServiceProfile.setsST(SliceType.EMBB);
        embbPerfReq = new EMBBPerfReq(100,
                90,
                110,
                95,
                6,
                9,
                1000,
                "coverage");

        List<EMBBPerfReq> embblist = new ArrayList<>();
        embblist.add(embbPerfReq);
        nstServiceProfile.seteMBBPerfReq(embblist);
        nst.setNstServiceProfile(nstServiceProfile);

        return nst;
    }
}
