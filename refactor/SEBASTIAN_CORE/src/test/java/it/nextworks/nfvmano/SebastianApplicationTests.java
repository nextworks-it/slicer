/*
* Copyright 2018 Nextworks s.r.l.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
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
