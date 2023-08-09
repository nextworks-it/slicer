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
package it.nextworks.nfvmano.catalogue.engine;

import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.NsdDeletionNotificationMessage;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.NsdOnBoardingNotificationMessage;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.VnfPkgDeletionNotificationMessage;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.VnfPkgOnBoardingNotificationMessage;
import it.nextworks.nfvmano.catalogue.engine.elements.ContentType;
import it.nextworks.nfvmano.catalogue.nbi.sol005.nsdmanagement.elements.*;
import it.nextworks.nfvmano.libs.common.exceptions.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface NsdManagementInterface {

    void startupSync();

    void runtimeNsDeletion(NsdDeletionNotificationMessage notification);

    void runtimeNsOnBoarding(NsdOnBoardingNotificationMessage notification);

    NsdInfo createNsdInfo(CreateNsdInfoRequest request, String project, boolean isInternalRequest) throws FailedOperationException, MalformattedElementException, MethodNotImplementedException, NotPermittedOperationException, NotAuthorizedOperationException;

    void deleteNsdInfo(String nsdInfoId, String project, boolean isInternalRequest) throws FailedOperationException, NotExistingEntityException, MalformattedElementException, NotPermittedOperationException, MethodNotImplementedException, NotAuthorizedOperationException;

    NsdInfoModifications updateNsdInfo(NsdInfoModifications nsdInfoModification, String nsdInfoId, String project, boolean isInternalRequest) throws FailedOperationException, NotExistingEntityException, MalformattedElementException, NotPermittedOperationException, NotAuthorizedOperationException;

    Object getNsdFile(String nsdInfoId, boolean isInternalRequest, String project) throws FailedOperationException, NotExistingEntityException, MalformattedElementException, NotPermittedOperationException, MethodNotImplementedException, NotAuthorizedOperationException;

    Object getNsd(String nsdInfoId, boolean isInternalRequest, String project, String accept) throws FailedOperationException, NotExistingEntityException, MalformattedElementException, NotPermittedOperationException, MethodNotImplementedException, NotAuthorizedOperationException;

    NsdInfo getNsdInfo(String nsdInfoId, String project, String extraData) throws FailedOperationException, NotPermittedOperationException, NotExistingEntityException, MalformattedElementException, MethodNotImplementedException, NotAuthorizedOperationException;

    List<NsdInfo> getAllNsdInfos(String project, String extraData, UUID nsdId) throws FailedOperationException, MethodNotImplementedException, NotAuthorizedOperationException;

    void uploadNsd(String nsdInfoId, MultipartFile nsd, ContentType contentType, boolean isInternalRequest, String project) throws Exception, FailedOperationException, AlreadyExistingEntityException, NotExistingEntityException, MalformattedElementException, NotPermittedOperationException, MethodNotImplementedException;

    void updateNsd(String nsdInfoId, MultipartFile nsd, ContentType contentType, boolean isInternalRequest, String originalPluginId, String project) throws Exception, FailedOperationException, NotExistingEntityException, AlreadyExistingEntityException, MalformattedElementException, NotPermittedOperationException, MethodNotImplementedException;

    PnfdInfo createPnfdInfo(CreatePnfdInfoRequest request, String project) throws FailedOperationException, MalformattedElementException, MethodNotImplementedException, NotAuthorizedOperationException;

    void deletePnfdInfo(String pnfdInfoId, String project) throws FailedOperationException, NotExistingEntityException, MalformattedElementException, NotPermittedOperationException, MethodNotImplementedException, NotAuthorizedOperationException;

    PnfdInfoModifications updatePnfdInfo(PnfdInfoModifications pnfdInfoModifications, String pnfdInfoId, String project) throws NotExistingEntityException, MalformattedElementException, NotPermittedOperationException;

    Object getPnfd(String pnfdInfoId, boolean isInternalRequest, String project) throws FailedOperationException, NotExistingEntityException, MalformattedElementException, NotPermittedOperationException, MethodNotImplementedException, NotAuthorizedOperationException;

    PnfdInfo getPnfdInfo(String pnfdInfoId, String project) throws FailedOperationException, NotPermittedOperationException, NotExistingEntityException, MalformattedElementException, MethodNotImplementedException, NotAuthorizedOperationException;

    List<PnfdInfo> getAllPnfdInfos(String project, UUID pnfdId) throws FailedOperationException, MethodNotImplementedException, NotAuthorizedOperationException;

    void uploadPnfd(String pnfdInfoId, MultipartFile pnfd, ContentType contentType, boolean isInternalRequest, String project) throws Exception, FailedOperationException, AlreadyExistingEntityException, NotExistingEntityException, MalformattedElementException, NotPermittedOperationException, MethodNotImplementedException;

}
