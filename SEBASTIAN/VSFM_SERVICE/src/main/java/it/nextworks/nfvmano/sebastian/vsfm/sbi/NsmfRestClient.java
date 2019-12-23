/*
 * Copyright (c) 2019 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.sebastian.vsfm.sbi;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotPermittedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.nsmf.messages.CreateNsiIdRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.InstantiateNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.ModifyNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.TerminateNsiRequest;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;

/**
 * REST client to interact with a REST based NSMF exposing 3GPP inspired interface.
 * 
 * @author nextworks
 *
 */
public class NsmfRestClient implements NsmfLcmProviderInterface {

	private static final Logger log = LoggerFactory.getLogger(NsmfRestClient.class);
	
	private RestTemplate restTemplate;
	
	private String nsmfUrl;
	
	public NsmfRestClient(String baseUrl) {
		this.nsmfUrl = baseUrl + "/vs/basic/nslcm";
		this.restTemplate = new RestTemplate();
	}
	
	
	@Override
	public String createNetworkSliceIdentifier(CreateNsiIdRequest request, String tenantId)
			throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
			MalformattedElementException, NotPermittedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void instantiateNetworkSlice(InstantiateNsiRequest request, String tenantId)
			throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
			MalformattedElementException, NotPermittedOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyNetworkSlice(ModifyNsiRequest request, String tenantId)
			throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
			MalformattedElementException, NotPermittedOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void terminateNetworkSliceInstance(TerminateNsiRequest request, String tenantId)
			throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
			MalformattedElementException, NotPermittedOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<NetworkSliceInstance> queryNetworkSliceInstance(GeneralizedQueryRequest request, String tenantId)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException {
		// TODO Auto-generated method stub
		return null;
	}

}
