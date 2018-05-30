package it.nextworks.nfvmano;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.QueryNsdResponse;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.CreateNsIdentifierRequest;
import it.nextworks.nfvmano.sebastian.common.Utilities;
import it.nextworks.nfvmano.sebastian.nfvodriver.timeo.TimeoRestClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SebastianApplicationTests {

	private RestTemplate restTemplate = new RestTemplate();
	
	@Test
	public void contextLoads() {
	}

	
//	@Test
//	public void testTimeoRestClient() throws Exception {
//		
//		String timeoUrl = "http://localhost:8081";
//		TimeoRestClient timeoClient = new TimeoRestClient(timeoUrl, restTemplate);
//		
//		String nsdId = "vCDN_v02";
//		String nsdVersion = "0.2";
//		
//		QueryNsdResponse queryNsdResponse = timeoClient.queryNsd(new GeneralizedQueryRequest(Utilities.buildNsdFilter(nsdId, nsdVersion), null));
//		
//		System.out.println("Query NSD done.");
//		
//		String nsdInfoId = queryNsdResponse.getQueryResult().get(0).getNsdInfoId();
//		
//		System.out.println("NSD info ID: " + nsdInfoId);
//		
//		CreateNsIdentifierRequest request = new CreateNsIdentifierRequest(nsdInfoId, "NS_prova", "NS di test", "tenant1");
//		String nsId = timeoClient.createNsIdentifier(request);
//		
//		System.out.println("Create NSD request sent. NS ID: " + nsId);
//	}
	
}
