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

import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.nsmf.messages.CreateNsiUuidRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.InstantiateNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.ModifyNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.TerminateNsiRequest;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class used to manage the interaction with external NSMF when this is exposed via REST.
 * At the moment only single domain is supported. Service to be extended for managing
 * multiple domains with multiple drivers.
 * 
 * @author nextworks
 *
 */
@Service
public class NsmfInteractionHandler implements NsmfLcmProviderInterface {

	private NsmfRestClient nsmfRestClient;

	@Autowired
	private AdminService adminService;

	public void setNsmfClientConfiguration(String nsmfRestServerUrl) {
		this.nsmfRestClient = new NsmfRestClient(nsmfRestServerUrl, adminService);
	}
	
	@Override
	public String createNetworkSliceIdentifier(CreateNsiUuidRequest request, String tenantId)
			throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
			MalformattedElementException, NotPermittedOperationException {
		return nsmfRestClient.createNetworkSliceIdentifier(request, tenantId);
	}

	@Override
	public void instantiateNetworkSlice(InstantiateNsiRequest request, String tenantId)
			throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
			MalformattedElementException, NotPermittedOperationException {
		nsmfRestClient.instantiateNetworkSlice(request, tenantId);
	}

	@Override
	public void modifyNetworkSlice(ModifyNsiRequest request, String tenantId)
			throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
			MalformattedElementException, NotPermittedOperationException {
		nsmfRestClient.modifyNetworkSlice(request, tenantId);
	}

	@Override
	public void terminateNetworkSliceInstance(TerminateNsiRequest request, String tenantId)
			throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
			MalformattedElementException, NotPermittedOperationException {
		nsmfRestClient.terminateNetworkSliceInstance(request, tenantId);
	}

	@Override
	public List<NetworkSliceInstance> queryNetworkSliceInstance(GeneralizedQueryRequest request, String tenantId)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException {
		return nsmfRestClient.queryNetworkSliceInstance(request, tenantId);
	}

}
