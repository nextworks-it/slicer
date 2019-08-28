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
package it.nextworks.nfvmano.nfvodriver;

import it.nextworks.nfvmano.libs.catalogues.interfaces.*;
import it.nextworks.nfvmano.libs.catalogues.interfaces.elements.NsdInfo;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.*;
import it.nextworks.nfvmano.libs.common.elements.Filter;
import it.nextworks.nfvmano.libs.common.exceptions.*;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.common.messages.SubscribeRequest;
import it.nextworks.nfvmano.libs.descriptors.nsd.Nsd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;



/**
 * This entity handles all the client-server catalogue interaction with the NFVO..
 * It forwards the request to the configured NFVO using the related driver.
 * 
 * @author nextworks
 *
 */
@Service
public class NfvoCatalogueService implements MecAppPackageManagementProviderInterface, NsdManagementProviderInterface, VnfPackageManagementProviderInterface {
	
	private static final Logger log = LoggerFactory.getLogger(NfvoCatalogueService.class);
	

	private NfvoCatalogueAbstractDriver nfvoDriver;
	
	@Autowired
	NfvoCatalogueNotificationsManager nfvoCatalogueNotificationManager;
	

	public NfvoCatalogueService() {	}


	@Override
	public File fetchOnboardedApplicationPackage(String onboardedAppPkgId)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		return nfvoDriver.fetchOnboardedApplicationPackage(onboardedAppPkgId);
	}

	@Override
	public QueryOnBoadedAppPkgInfoResponse queryApplicationPackage(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		return nfvoDriver.queryApplicationPackage(request);
	}

	@Override
	public String subscribeMecAppPackageInfo(SubscribeRequest request,
			MecAppPackageManagementConsumerInterface consumer)
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
		return nfvoDriver.subscribeMecAppPackageInfo(request, nfvoCatalogueNotificationManager);
	}

	@Override
	public void unsubscribeMecAppPackageInfo(String subscriptionId)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		nfvoDriver.unsubscribeMecAppPackageInfo(subscriptionId);
	}

	@Override
	public OnboardAppPackageResponse onboardAppPackage(OnboardAppPackageRequest request)
			throws MethodNotImplementedException, AlreadyExistingEntityException, FailedOperationException,
			MalformattedElementException {
		return nfvoDriver.onboardAppPackage(request);
	}

	@Override
	public void enableAppPackage(String onboardedAppPkgId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.enableAppPackage(onboardedAppPkgId);
	}

	@Override
	public void disableAppPackage(String onboardedAppPkgId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.disableAppPackage(onboardedAppPkgId);
	}

	@Override
	public void deleteAppPackage(String onboardedAppPkgId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.deleteAppPackage(onboardedAppPkgId);
	}

	@Override
	public void abortAppPackageDeletion(String onboardedAppPkgId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.abortAppPackageDeletion(onboardedAppPkgId);
	}

	@Override
	public String onboardNsd(OnboardNsdRequest request) throws MethodNotImplementedException,
			MalformattedElementException, AlreadyExistingEntityException, FailedOperationException {

		return nfvoDriver.onboardNsd(request);
	}

	@Override
	public void enableNsd(EnableNsdRequest request) throws MethodNotImplementedException, MalformattedElementException,
			NotExistingEntityException, FailedOperationException {
		nfvoDriver.enableNsd(request);
	}

	@Override
	public void disableNsd(DisableNsdRequest request) throws MethodNotImplementedException,
			MalformattedElementException, NotExistingEntityException, FailedOperationException {
		nfvoDriver.disableNsd(request);
	}

	@Override
	public String updateNsd(UpdateNsdRequest request)
			throws MethodNotImplementedException, MalformattedElementException, AlreadyExistingEntityException,
			NotExistingEntityException, FailedOperationException {
		return nfvoDriver.updateNsd(request);
	}

	@Override
	public DeleteNsdResponse deleteNsd(DeleteNsdRequest request) throws MethodNotImplementedException,
			MalformattedElementException, NotExistingEntityException, FailedOperationException {
		return nfvoDriver.deleteNsd(request);
	}

	@Override
	public QueryNsdResponse queryNsd(GeneralizedQueryRequest request) throws MethodNotImplementedException,
			MalformattedElementException, NotExistingEntityException, FailedOperationException {
		return nfvoDriver.queryNsd(request);
	}

	@Override
	public String subscribeNsdInfo(SubscribeRequest request, NsdManagementConsumerInterface consumer)
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
		return nfvoDriver.subscribeNsdInfo(request, nfvoCatalogueNotificationManager);
	}

	@Override
	public void unsubscribeNsdInfo(String subscriptionId) throws MethodNotImplementedException,
			MalformattedElementException, NotExistingEntityException, FailedOperationException {
		nfvoDriver.unsubscribeNsdInfo(subscriptionId);
	}

	@Override
	public String onboardPnfd(OnboardPnfdRequest request) throws MethodNotImplementedException,
			MalformattedElementException, AlreadyExistingEntityException, FailedOperationException {
		return nfvoDriver.onboardPnfd(request);
	}

	@Override
	public String updatePnfd(UpdatePnfdRequest request)
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException,
			AlreadyExistingEntityException, FailedOperationException {
		return nfvoDriver.updatePnfd(request);
	}

	@Override
	public DeletePnfdResponse deletePnfd(DeletePnfdRequest request) throws MethodNotImplementedException,
			MalformattedElementException, NotExistingEntityException, FailedOperationException {
		return nfvoDriver.deletePnfd(request);
	}

	@Override
	public QueryPnfdResponse queryPnfd(GeneralizedQueryRequest request) throws MethodNotImplementedException,
			MalformattedElementException, NotExistingEntityException, FailedOperationException {
		return nfvoDriver.queryPnfd(request);
	}

	@Override
	public OnBoardVnfPackageResponse onBoardVnfPackage(OnBoardVnfPackageRequest request)
			throws MethodNotImplementedException, AlreadyExistingEntityException, FailedOperationException,
			MalformattedElementException {
		return nfvoDriver.onBoardVnfPackage(request);
	}

	@Override
	public void enableVnfPackage(EnableVnfPackageRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.enableVnfPackage(request);
	}

	@Override
	public void disableVnfPackage(DisableVnfPackageRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.disableVnfPackage(request);
	}

	@Override
	public void deleteVnfPackage(DeleteVnfPackageRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.deleteVnfPackage(request);
	}

	@Override
	public QueryOnBoardedVnfPkgInfoResponse queryVnfPackageInfo(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		return nfvoDriver.queryVnfPackageInfo(request);
	}

	@Override
	public String subscribeVnfPackageInfo(SubscribeRequest request, VnfPackageManagementConsumerInterface consumer)
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
		return nfvoDriver.subscribeVnfPackageInfo(request, nfvoCatalogueNotificationManager);
	}

	@Override
	public void unsubscribeVnfPackageInfo(String subscriptionId)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		nfvoDriver.unsubscribeVnfPackageInfo(subscriptionId);
	}

	@Override
	public File fetchOnboardedVnfPackage(String onboardedVnfPkgInfoId)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		return nfvoDriver.fetchOnboardedVnfPackage(onboardedVnfPkgInfoId);
	}

	@Override
	public List<File> fetchOnboardedVnfPackageArtifacts(FetchOnboardedVnfPackageArtifactsRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		return nfvoDriver.fetchOnboardedVnfPackageArtifacts(request);
	}

	@Override
	public void abortVnfPackageDeletion(String onboardedVnfPkgInfoId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.abortVnfPackageDeletion(onboardedVnfPkgInfoId);
	}

	@Override
	public void queryVnfPackageSubscription(GeneralizedQueryRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.queryVnfPackageSubscription(request);
	}

	public void setNfvoCatalogueDriver(NfvoCatalogueAbstractDriver driver){

		log.debug("The Vertical Slicer is configured to operate over the "+driver.getNfvoDriverType()+" Catalogue driver");

		this.nfvoDriver=driver;
		this.nfvoDriver.setNfvoCatalogueNotificationManager(nfvoCatalogueNotificationManager);
	}



	public  Nsd queryNsdAssumingOne(Filter nsdFilter) throws Exception {
		//QueryNsdResponse nsdRep = this.queryNsd(new GeneralizedQueryRequest(BlueprintCatalogueUtilities.buildNsdFilter(nsdId, nsdVersion), null));
		QueryNsdResponse nsdRep = this.queryNsd(new GeneralizedQueryRequest(nsdFilter, null));
		List<NsdInfo> nsds = nsdRep.getQueryResult();
		if (nsds.size() == 0) {
			throw new NotExistingEntityException(
					String.format("No nsd with the specified Filter:%s", nsdFilter)
			);
		}
		if (nsds.size() > 1) {
			throw new FailedOperationException(
					String.format("More than one nsd with the specified filter:%s",nsdFilter)
			);
		}
		return nsdRep.getQueryResult().get(0).getNsd();
	}

}
