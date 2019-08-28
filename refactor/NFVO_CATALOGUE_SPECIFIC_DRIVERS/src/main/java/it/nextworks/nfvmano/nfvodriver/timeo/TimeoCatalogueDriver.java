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

import it.nextworks.nfvmano.libs.catalogues.interfaces.MecAppPackageManagementConsumerInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.NsdManagementConsumerInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.VnfPackageManagementConsumerInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.*;
import it.nextworks.nfvmano.libs.common.exceptions.*;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.common.messages.SubscribeRequest;
import it.nextworks.nfvmano.nfvodriver.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.List;

public class TimeoCatalogueDriver extends NfvoCatalogueAbstractDriver {

	private static final Logger log = LoggerFactory.getLogger(TimeoCatalogueDriver.class);



	private TimeoCatalogueRestClient restClient;

	private RestTemplate restTemplate = new RestTemplate();

	public TimeoCatalogueDriver(String nfvoAddress,
                                NfvoCatalogueNotificationInterface nfvoNotificationsManager) {
		super(NfvoCatalogueDriverType.TIMEO, nfvoAddress, nfvoNotificationsManager);
		String timeoUrl = "http://" + nfvoAddress + ":8081";
		this.restClient = new TimeoCatalogueRestClient(timeoUrl, restTemplate);
	}


	@Override
	public File fetchOnboardedApplicationPackage(String onboardedAppPkgId)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		throw new MethodNotImplementedException("Fetching of MEC application packages not supported in TIMEO.");
	}

	@Override
	public QueryOnBoadedAppPkgInfoResponse queryApplicationPackage(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		return restClient.queryApplicationPackage(request);
	}

	@Override
	public String subscribeMecAppPackageInfo(SubscribeRequest request, MecAppPackageManagementConsumerInterface consumer)
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
		throw new MethodNotImplementedException("Subscriptions for MEC application packages not supported in TIMEO.");
	}
	
	@Override
	public void unsubscribeMecAppPackageInfo(String subscriptionId)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		throw new MethodNotImplementedException("Subscriptions for MEC application packages not supported in TIMEO.");
	}

	@Override
	public OnboardAppPackageResponse onboardAppPackage(OnboardAppPackageRequest request)
			throws MethodNotImplementedException, AlreadyExistingEntityException, FailedOperationException,
			MalformattedElementException {
		return restClient.onboardAppPackage(request);
	}

	@Override
	public void enableAppPackage(String onboardedAppPkgId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		throw new MethodNotImplementedException("MEC APP package enabling not supported in TIMEO driver.");
	}

	@Override
	public void disableAppPackage(String onboardedAppPkgId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		throw new MethodNotImplementedException("MEC APP package disabling not supported in TIMEO driver.");
	}

	@Override
	public void deleteAppPackage(String onboardedAppPkgId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		throw new MethodNotImplementedException("MEC APP package deletion not supported in TIMEO driver.");

	}

	@Override
	public void abortAppPackageDeletion(String onboardedAppPkgId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		throw new MethodNotImplementedException("Abortion of MEC application packages deletion not supported in TIMEO.");
	}

	@Override
	public String onboardNsd(OnboardNsdRequest request) throws MethodNotImplementedException,
			MalformattedElementException, AlreadyExistingEntityException, FailedOperationException {
		return restClient.onboardNsd(request);
	}

	@Override
	public void enableNsd(EnableNsdRequest request) throws MethodNotImplementedException, MalformattedElementException,
			NotExistingEntityException, FailedOperationException {
		throw new MethodNotImplementedException("NFV NSD enabling not supported in TIMEO driver.");
	}

	@Override
	public void disableNsd(DisableNsdRequest request) throws MethodNotImplementedException,
			MalformattedElementException, NotExistingEntityException, FailedOperationException {
		throw new MethodNotImplementedException("NFV NSD disabling not supported in TIMEO driver.");
	}

	@Override
	public String updateNsd(UpdateNsdRequest request)
			throws MethodNotImplementedException, MalformattedElementException, AlreadyExistingEntityException,
			NotExistingEntityException, FailedOperationException {
		throw new MethodNotImplementedException("NFV NSD updating not supported in TIMEO.");
	}

	@Override
	public DeleteNsdResponse deleteNsd(DeleteNsdRequest request) throws MethodNotImplementedException,
			MalformattedElementException, NotExistingEntityException, FailedOperationException {
		throw new MethodNotImplementedException("NFV NSD deletion not supported in TIMEO driver.");
	}

	@Override
	public QueryNsdResponse queryNsd(GeneralizedQueryRequest request) throws MethodNotImplementedException,
			MalformattedElementException, NotExistingEntityException, FailedOperationException {
		return restClient.queryNsd(request);
	}

	@Override
	public String subscribeNsdInfo(SubscribeRequest request, NsdManagementConsumerInterface consumer)
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
		throw new MethodNotImplementedException("Subscriptions for NFV NSD info not supported in TIMEO.");
	}
	
	@Override
	public void unsubscribeNsdInfo(String subscriptionId) 
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
		throw new MethodNotImplementedException("Subscriptions for NFV NSD info not supported in TIMEO.");
	}

	@Override
	public String onboardPnfd(OnboardPnfdRequest request) throws MethodNotImplementedException,
			MalformattedElementException, AlreadyExistingEntityException, FailedOperationException {
		return restClient.onboardPnfd(request);
	}

	@Override
	public String updatePnfd(UpdatePnfdRequest request)
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException,
			AlreadyExistingEntityException, FailedOperationException {
		throw new MethodNotImplementedException("PNFD updating not supported in TIMEO.");
	}

	@Override
	public DeletePnfdResponse deletePnfd(DeletePnfdRequest request) throws MethodNotImplementedException,
			MalformattedElementException, NotExistingEntityException, FailedOperationException {
		throw new MethodNotImplementedException("PNFD deletion not supported in TIMEO driver.");
	}

	@Override
	public QueryPnfdResponse queryPnfd(GeneralizedQueryRequest request) throws MethodNotImplementedException,
			MalformattedElementException, NotExistingEntityException, FailedOperationException {
		return restClient.queryPnfd(request);
	}

	@Override
	public OnBoardVnfPackageResponse onBoardVnfPackage(OnBoardVnfPackageRequest request)
			throws MethodNotImplementedException, AlreadyExistingEntityException, FailedOperationException,
			MalformattedElementException {
		return restClient.onBoardVnfPackage(request);
	}

	@Override
	public void enableVnfPackage(EnableVnfPackageRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		throw new MethodNotImplementedException("VNF package enabling not supported in TIMEO driver.");
	}

	@Override
	public void disableVnfPackage(DisableVnfPackageRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		throw new MethodNotImplementedException("VNF package disabling not supported in TIMEO driver.");
	}

	@Override
	public void deleteVnfPackage(DeleteVnfPackageRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		throw new MethodNotImplementedException("VNF package deletion not supported in TIMEO driver.");
	}

	@Override
	public QueryOnBoardedVnfPkgInfoResponse queryVnfPackageInfo(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		return restClient.queryVnfPackageInfo(request);
	}

	@Override
	public String subscribeVnfPackageInfo(SubscribeRequest request, VnfPackageManagementConsumerInterface consumer)
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
		throw new MethodNotImplementedException("Subscriptions for VNF packages info not supported in TIMEO.");
	}
	
	@Override
	public void unsubscribeVnfPackageInfo(String subscriptionId)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		throw new MethodNotImplementedException("Subscriptions for VNF packages info not supported in TIMEO.");
	}

	@Override
	public File fetchOnboardedVnfPackage(String onboardedVnfPkgInfoId)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		throw new MethodNotImplementedException("Fetching of VNF packages info not supported in TIMEO.");
	}

	@Override
	public List<File> fetchOnboardedVnfPackageArtifacts(FetchOnboardedVnfPackageArtifactsRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		throw new MethodNotImplementedException("Fetching of VNF package artifacts info not supported in TIMEO.");
	}

	@Override
	public void abortVnfPackageDeletion(String onboardedVnfPkgInfoId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		throw new MethodNotImplementedException("Abortion of VNF package deletion not supported in TIMEO.");
	}

	@Override
	public void queryVnfPackageSubscription(GeneralizedQueryRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		throw new MethodNotImplementedException("Subscriptions for VNF packages info not supported in TIMEO.");
	}

}
