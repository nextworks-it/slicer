package it.nextworks.nfvmano.sebastian.nsmf.test;

import it.nextworks.nfvmano.sebastian.nsmf.sbi.FlexRanService;
import it.nextworks.nfvmano.sebastian.nsmf.sbi.LlMecService;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class LlMecWorkflowTest {

    FlexRanService flexRanService = new FlexRanService();
    LlMecService llMecService = new LlMecService();
    private UUID sliceId = UUID.fromString("bf5cbb04-345c-479f-8511-f6f01b2b822d");

    @Test
    public void createLLMecSliceAndConfigureIt(){
        //NST nst = assebleNst();
        //List<JSONObject> qosConst = rqos.ranProfileToQoSConstraints(nst);

        llMecService.setLlMecURL("10.8.205.24:");
        llMecService.setLlMecAdapterUrl("10.8.205.131:");
        HttpStatus httpStatus = llMecService.createLlMecSlice(sliceId);
        llMecService.mapIdsRemotely(sliceId);
        // pause here!!!!
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        llMecService.removeRemoteMapping(sliceId);
        llMecService.terminateLlMecSlice(sliceId);
    }

}

