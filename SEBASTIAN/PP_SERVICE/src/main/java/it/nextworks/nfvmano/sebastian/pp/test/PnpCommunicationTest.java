package it.nextworks.nfvmano.sebastian.pp.test;

import it.nextworks.nfvmano.libs.ifa.templates.NST;
import it.nextworks.nfvmano.libs.ifa.templates.plugAndPlay.PpFeatureLevel;
import it.nextworks.nfvmano.libs.ifa.templates.plugAndPlay.PpFeatureType;
import it.nextworks.nfvmano.libs.ifa.templates.plugAndPlay.PpFunction;
import it.nextworks.nfvmano.sebastian.pp.service.PnPCommunicationService;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PnpCommunicationTest {

    private PnPCommunicationService pnp = new PnPCommunicationService();
    private UUID sliceId = UUID.fromString("bf5cbb04-345c-479f-8511-f6f01b2b822d");
    private NST nst;
    @Test
    public void createNst(){
        //    {
//        "seq_id": "0",
//            "feature_id":  "qoe_man_feature",
//            "feature_type": "management",
//            "feature_level": "slice"
//    }
        PpFunction ppf = new PpFunction("qoe_man_feature", PpFeatureLevel.SLICE, PpFeatureType.CONTROL);
        ppf.setSeqId(0);

        List<PpFunction> ppfl = new ArrayList<>();
        ppfl.add(ppf);

        this.nst = new NST();
        nst.setNstName("test_slice");
        nst.setNstProvider("NXW");
        nst.setPpFunctionList(ppfl);

    }
    @Test
    public void deployQos(){
        PpFunction ppf = new PpFunction("qos_ctrl_feature", PpFeatureLevel.SLICE, PpFeatureType.CONTROL);
        ppf.setSeqId(0);
        //String url = "http://10.8.202.4:50001";
        String url = "http://127.0.0.1:50001";
        List<PpFunction> ppfl = new ArrayList<>();
        ppfl.add(ppf);

        this.nst = new NST();
        nst.setNstName("test_slice");
        nst.setNstProvider("NXW");
        nst.setPpFunctionList(ppfl);
        pnp.setTargetUrl(url);
        HttpStatus httpStatus = pnp.deploySliceComponents(sliceId, nst,false);
        System.out.println(httpStatus);

    }

    @Test
    public void terminateQos(){
        String url = "http://127.0.0.1:50001";
        //String url = "http://10.8.202.4:50001";
        pnp.setTargetUrl(url);
        HttpStatus httpStatus = pnp.terminateSliceComponents(sliceId);
        System.out.println(httpStatus);
    }
}

