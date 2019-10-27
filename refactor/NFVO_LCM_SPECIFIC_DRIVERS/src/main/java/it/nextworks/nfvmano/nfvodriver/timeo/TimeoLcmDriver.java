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
package it.nextworks.nfvmano.nfvodriver.timeo;

import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.common.messages.SubscribeRequest;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.NsLcmConsumerInterface;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.*;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmAbstractDriver;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmDriverType;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmNotificationInterface;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmOperationPollingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class TimeoLcmDriver extends NfvoLcmAbstractDriver {
	
	private static final Logger log = LoggerFactory.getLogger(TimeoLcmDriver.class);
	
	@Autowired
	private NfvoLcmOperationPollingManager timeoNfvoOperationPollingManager;
	
	private TimeoLcmRestClient restClient;
	
	private RestTemplate restTemplate = new RestTemplate();

	public TimeoLcmDriver(String nfvoAddress,
						  NfvoLcmNotificationInterface nfvoNotificationsManager,
						  NfvoLcmOperationPollingManager timeoNfvoOperationPollingManager) {
		super(NfvoLcmDriverType.TIMEO, nfvoAddress, nfvoNotificationsManager);
		this.timeoNfvoOperationPollingManager = timeoNfvoOperationPollingManager;
		String timeoUrl = "http://" + nfvoAddress + ":8081";
		this.restClient = new TimeoLcmRestClient(timeoUrl, restTemplate);
	}

	@Override
	public String createNsIdentifier(CreateNsIdentifierRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		return restClient.createNsIdentifier(request);
	}

	@Override
	public String instantiateNs(InstantiateNsRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		String operationId = restClient.instantiateNs(request);
		timeoNfvoOperationPollingManager.addOperation(operationId, OperationStatus.SUCCESSFULLY_DONE, request.getNsInstanceId(), "NS_INSTANTIATION");
		log.debug("Added polling task for NFVO operation " + operationId);
		return operationId;
	}

	@Override
	public String scaleNs(ScaleNsRequest request) throws MethodNotImplementedException, NotExistingEntityException,
			FailedOperationException, MalformattedElementException {
		String operationId = restClient.scaleNs(request);
		timeoNfvoOperationPollingManager.addOperation(operationId, OperationStatus.SUCCESSFULLY_DONE, request.getNsInstanceId(), "NS_SCALING");
		return operationId;
	}

	@Override
	public UpdateNsResponse updateNs(UpdateNsRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		throw new MethodNotImplementedException("NFV NS updating not supported in TIMEO.");
	}

	@Override
	public QueryNsResponse queryNs(GeneralizedQueryRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		Map<String, String> filter = request.getFilter().getParameters();
		String nsId = filter.get("NS_ID");
		if (nsId == null) {
			log.error("Received NFV NS instance query without NS instance ID");
			throw new MalformattedElementException("NS instance queries are supported only with NS ID.");
		}
		return restClient.queryNs(nsId);
	}

	@Override
	public String terminateNs(TerminateNsRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		String operationId = restClient.terminateNs(request);
		timeoNfvoOperationPollingManager.addOperation(operationId, OperationStatus.SUCCESSFULLY_DONE, request.getNsInstanceId(), "NS_TERMINATION");
		log.debug("Added polling task for NFVO operation " + operationId);
		return operationId;
	}

	@Override
	public void deleteNsIdentifier(String nsInstanceIdentifier)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException {
		restClient.deleteNsIdentifier(nsInstanceIdentifier);
	}

	@Override
	public String healNs(HealNsRequest request) throws MethodNotImplementedException, NotExistingEntityException,
			FailedOperationException, MalformattedElementException {
		throw new MethodNotImplementedException("NFV NS healing not supported in TIMEO.");
	}

	@Override
	public OperationStatus getOperationStatus(String operationId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		return restClient.getOperationStatus(operationId);
	}

	@Override
	public String subscribeNsLcmEvents(SubscribeRequest request, NsLcmConsumerInterface consumer)
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
		throw new MethodNotImplementedException("Subscriptions for NFV NS LCM events not supported in TIMEO.");
	}

	@Override
	public void unsubscribeNsLcmEvents(String subscriptionId) throws MethodNotImplementedException, MalformattedElementException,
			NotExistingEntityException, FailedOperationException {
		throw new MethodNotImplementedException("Subscriptions for NFV NS LCM events not supported in TIMEO.");
	}

	@Override
	public void queryNsSubscription(GeneralizedQueryRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		throw new MethodNotImplementedException("Subscriptions for NFV NS LCM events not supported in TIMEO.");
	}


}
