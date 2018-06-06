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
package it.nextworks.nfvmano.nfvodriver.logging;

import it.nextworks.nfvmano.libs.catalogues.interfaces.MecAppPackageManagementConsumerInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.NsdManagementConsumerInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.VnfPackageManagementConsumerInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.DeleteNsdRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.DeleteNsdResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.DeletePnfdRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.DeletePnfdResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.DeleteVnfPackageRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.DisableNsdRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.DisableVnfPackageRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.EnableNsdRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.EnableVnfPackageRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.FetchOnboardedVnfPackageArtifactsRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnBoardVnfPackageRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnBoardVnfPackageResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnboardAppPackageRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnboardAppPackageResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnboardNsdRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnboardPnfdRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.QueryNsdResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.QueryOnBoadedAppPkgInfoResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.QueryOnBoardedVnfPkgInfoResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.QueryPnfdResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.UpdateNsdRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.UpdatePnfdRequest;
import it.nextworks.nfvmano.libs.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.common.messages.SubscribeRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.NsLcmConsumerInterface;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.CreateNsIdentifierRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.HealNsRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.InstantiateNsRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.QueryNsResponse;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.ScaleNsRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.TerminateNsRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.UpdateNsRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.UpdateNsResponse;
import it.nextworks.nfvmano.nfvodriver.NfvoAbstractDriver;
import it.nextworks.nfvmano.nfvodriver.NfvoDriverType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Created by Marco Capitani on 20/04/18.
 *
 * @author Marco Capitani <m.capitani AT nextworks.it>
 */
public class LoggingDriver extends NfvoAbstractDriver {

    private static final Logger log = LoggerFactory.getLogger(LoggingDriver.class);

    public LoggingDriver() {
        super(NfvoDriverType.LOGGING, "", null);
    }

    public File fetchOnboardedApplicationPackage(String s) throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
        log.info("Received call to fetchOnboardedApplicationPackage, parameters {}.", s);
        return null;
    }

    public QueryOnBoadedAppPkgInfoResponse queryApplicationPackage(GeneralizedQueryRequest generalizedQueryRequest) throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
        log.info("Received call to queryApplicationPackage, parameters {}.", generalizedQueryRequest);
        return null;
    }

    public String subscribeMecAppPackageInfo(SubscribeRequest subscribeRequest, MecAppPackageManagementConsumerInterface mecAppPackageManagementConsumerInterface) throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
        log.info("Received call to subscribeMecAppPackageInfo, parameters {}; {}.", subscribeRequest, mecAppPackageManagementConsumerInterface);
        return null;
    }

    public void unsubscribeMecAppPackageInfo(String s) throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
        log.info("Received call to unsubscribeMecAppPackageInfo, parameter {}.", s);
    }

    public OnboardAppPackageResponse onboardAppPackage(OnboardAppPackageRequest onboardAppPackageRequest) throws MethodNotImplementedException, AlreadyExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to onboardAppPackage, parameter {}.", onboardAppPackageRequest);
        return null;
    }

    public void enableAppPackage(String s) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to enableAppPackage, parameter {}.", s);
    }

    public void disableAppPackage(String s) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to disableAppPackage, parameter {}.", s);
    }

    public void deleteAppPackage(String s) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to deleteAppPackage, parameter {}.", s);
    }

    public void abortAppPackageDeletion(String s) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to abortAppPackageDeletion, parameter {}.", s);
    }

    public String onboardNsd(OnboardNsdRequest onboardNsdRequest) throws MethodNotImplementedException, MalformattedElementException, AlreadyExistingEntityException, FailedOperationException {
        log.info("Received call to onboardNsd, parameter {}.", onboardNsdRequest);
        return null;
    }

    public void enableNsd(EnableNsdRequest enableNsdRequest) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to enableNsd, parameter {}.", enableNsdRequest);
    }

    public void disableNsd(DisableNsdRequest disableNsdRequest) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to disableNsd, parameter {}.", disableNsdRequest);
    }

    public String updateNsd(UpdateNsdRequest updateNsdRequest) throws MethodNotImplementedException, MalformattedElementException, AlreadyExistingEntityException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to updateNsd, parameter {}.", updateNsdRequest);
        return null;
    }

    public DeleteNsdResponse deleteNsd(DeleteNsdRequest deleteNsdRequest) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to deleteNsd, parameter {}.", deleteNsdRequest);
        return null;
    }

    public QueryNsdResponse queryNsd(GeneralizedQueryRequest generalizedQueryRequest) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to queryNsd, parameter {}.", generalizedQueryRequest);
        return null;
    }

    public String subscribeNsdInfo(SubscribeRequest subscribeRequest, NsdManagementConsumerInterface nsdManagementConsumerInterface) throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
        log.info("Received call to subscribeNsdInfo, parameters {}; {}.", subscribeRequest, nsdManagementConsumerInterface);
        return null;
    }

    public void unsubscribeNsdInfo(String s) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to unsubscribeNsdInfo, parameter {}.", s);
    }

    public String onboardPnfd(OnboardPnfdRequest onboardPnfdRequest) throws MethodNotImplementedException, MalformattedElementException, AlreadyExistingEntityException, FailedOperationException {
        log.info("Received call to onboardPnfd, parameter {}.", onboardPnfdRequest);
        return null;
    }

    public String updatePnfd(UpdatePnfdRequest updatePnfdRequest) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, AlreadyExistingEntityException, FailedOperationException {
        log.info("Received call to updatePnfd, parameter {}.", updatePnfdRequest);
        return null;
    }

    public DeletePnfdResponse deletePnfd(DeletePnfdRequest deletePnfdRequest) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to deletePnfd, parameter {}.", deletePnfdRequest);
        return null;
    }

    public QueryPnfdResponse queryPnfd(GeneralizedQueryRequest generalizedQueryRequest) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to queryPnfd, parameter {}.", generalizedQueryRequest);
        return null;
    }

    public OnBoardVnfPackageResponse onBoardVnfPackage(OnBoardVnfPackageRequest onBoardVnfPackageRequest) throws MethodNotImplementedException, AlreadyExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to onBoardVnfPackage, parameter {}.", onBoardVnfPackageRequest);
        return null;
    }

    public void enableVnfPackage(EnableVnfPackageRequest enableVnfPackageRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to enableVnfPackage, parameter {}.", enableVnfPackageRequest);
    }

    public void disableVnfPackage(DisableVnfPackageRequest disableVnfPackageRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to disableVnfPackage, parameter {}.", disableVnfPackageRequest);
    }

    public void deleteVnfPackage(DeleteVnfPackageRequest deleteVnfPackageRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to deleteVnfPackage, parameter {}.", deleteVnfPackageRequest);
    }

    public QueryOnBoardedVnfPkgInfoResponse queryVnfPackageInfo(GeneralizedQueryRequest generalizedQueryRequest) throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
        log.info("Received call to queryVnfPackageInfo, parameter {}.", generalizedQueryRequest);
        return null;
    }

    public String subscribeVnfPackageInfo(SubscribeRequest subscribeRequest, VnfPackageManagementConsumerInterface vnfPackageManagementConsumerInterface) throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
        log.info("Received call to subscribeVnfPackageInfo, parameters {}; {}.", subscribeRequest, vnfPackageManagementConsumerInterface);
        return null;
    }

    public void unsubscribeVnfPackageInfo(String s) throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
        log.info("Received call to unsubscribeVnfPackageInfo, parameter {}.", s);
    }

    public File fetchOnboardedVnfPackage(String s) throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
        log.info("Received call to fetchOnboardedVnfPackage, parameter {}.", s);
        return null;
    }

    public List<File> fetchOnboardedVnfPackageArtifacts(FetchOnboardedVnfPackageArtifactsRequest fetchOnboardedVnfPackageArtifactsRequest) throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
        log.info("Received call to fetchOnboardedVnfPackageArtifacts, parameter {}.", fetchOnboardedVnfPackageArtifactsRequest);
        return null;
    }

    public void abortVnfPackageDeletion(String s) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to abortVnfPackageDeletion, parameter {}.", s);
    }

    public void queryVnfPackageSubscription(GeneralizedQueryRequest generalizedQueryRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to queryVnfPackageSubscription, parameter {}.", generalizedQueryRequest);
    }

    public String createNsIdentifier(CreateNsIdentifierRequest createNsIdentifierRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to createNsIdentifier, parameter {}.", createNsIdentifierRequest);
        return null;
    }

    public String instantiateNs(InstantiateNsRequest instantiateNsRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to instantiateNs, parameter {}.", instantiateNsRequest);
        return null;
    }

    public String scaleNs(ScaleNsRequest scaleNsRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to scaleNs, parameter {}.", scaleNsRequest);
        return null;
    }

    public UpdateNsResponse updateNs(UpdateNsRequest updateNsRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to updateNs, parameter {}.", updateNsRequest);
        return null;
    }

    public QueryNsResponse queryNs(GeneralizedQueryRequest generalizedQueryRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to queryNs, parameter {}.", generalizedQueryRequest);
        return null;
    }

    public String terminateNs(TerminateNsRequest terminateNsRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to terminateNs, parameter {}.", terminateNsRequest);
        return null;
    }

    public void deleteNsIdentifier(String s) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to deleteNsIdentifier, parameter {}.", s);
    }

    public String healNs(HealNsRequest healNsRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to healNs, parameter {}.", healNsRequest);
        return null;
    }

    public OperationStatus getOperationStatus(String s) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to getOperationStatus, parameter {}.", s);
        return null;
    }

    public String subscribeNsLcmEvents(SubscribeRequest subscribeRequest, NsLcmConsumerInterface nsLcmConsumerInterface) throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
        log.info("Received call to subscribeNsLcmEvents, parameters {}; {}.", subscribeRequest, nsLcmConsumerInterface);
        return null;
    }

    public void unsubscribeNsLcmEvents(String s) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to unsubscribeNsLcmEvents, parameter {}.", s);
    }

    public void queryNsSubscription(GeneralizedQueryRequest generalizedQueryRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to queryNsSubscription, parameter {}.", generalizedQueryRequest);
    }
}
