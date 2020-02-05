package it.nextworks.nfvmano.sebastian.nsmf.test;

import it.nextworks.nfvmano.sebastian.nsmf.sbi.CPSService;
import it.nextworks.nfvmano.sebastian.nsmf.sbi.CSPTypes;
import org.junit.Test;

import java.util.UUID;

//@ContextConfiguration
//@RunWith(SpringRunner.class)
public class RestClientTest {

    CPSService cpsService;

    @Test
    public void getTest() throws Exception{
        cpsService = new CPSService();
        cpsService.setTargetUrl("http://10.30.8.76:8080");
        cpsService.setCspType(CSPTypes.QOS_CP);
        String res = cpsService.retrieveCpsUri(UUID.fromString("bf5cbb04-345c-479f-8511-f6f01b2b822d"));
        if(res == null) System.out.println("Noooooooo");
        else System.out.println(res);
    }

    @Test
    public void getTestFailure() throws Exception{
        cpsService = new CPSService();
        cpsService.setTargetUrl("http://10.30.8.76:8080");
        cpsService.setCspType(CSPTypes.QOS_CP);
        String res = cpsService.retrieveCpsUri(UUID.fromString("bf5cbb04-345c-479f-8511-f6f01b2b822e"));
        if(res == null)
            System.out.println("Noooooooo");

    }
}

