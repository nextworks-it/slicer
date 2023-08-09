package it.nextworks.nfvmano.catalogue.engine;

import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.VnfPkgDeletionNotificationMessage;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.VnfPkgOnBoardingNotificationMessage;
import it.nextworks.nfvmano.catalogue.engine.elements.ContentType;
import it.nextworks.nfvmano.catalogue.nbi.sol005.vnfpackagemanagement.elements.CreateVnfPkgInfoRequest;
import it.nextworks.nfvmano.catalogue.nbi.sol005.vnfpackagemanagement.elements.VnfPkgInfo;
import it.nextworks.nfvmano.catalogue.nbi.sol005.vnfpackagemanagement.elements.VnfPkgInfoModifications;
import it.nextworks.nfvmano.libs.common.exceptions.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface VnfPackageManagementInterface {

    void startupSync();

    void runtimeVnfDeletion(VnfPkgDeletionNotificationMessage notification);

    void runtimeVnfOnBoarding(VnfPkgOnBoardingNotificationMessage notification);

    VnfPkgInfo createVnfPkgInfo(CreateVnfPkgInfoRequest request, String project, boolean isInternalRequest) throws FailedOperationException, MalformattedElementException, MethodNotImplementedException, NotPermittedOperationException, NotAuthorizedOperationException;

    void deleteVnfPkgInfo(String vnfPkgInfoId, String project, boolean isInternalRequest) throws FailedOperationException, NotExistingEntityException, MalformattedElementException, NotPermittedOperationException, MethodNotImplementedException, NotAuthorizedOperationException;

    VnfPkgInfoModifications updateVnfPkgInfo(VnfPkgInfoModifications vnfPkgInfoModifications, String vnfPkgInfoId, String project, boolean isInternalRequest) throws FailedOperationException, NotExistingEntityException, MalformattedElementException, NotPermittedOperationException, NotAuthorizedOperationException;

    Object getVnfd(String vnfPkgInfoId, boolean isInternalRequest, String project) throws FailedOperationException, NotExistingEntityException, MalformattedElementException, NotPermittedOperationException, MethodNotImplementedException, NotAuthorizedOperationException;

    Object getVnfPkg(String vnfPkgInfoId, boolean isInternalRequest, String project) throws FailedOperationException, NotExistingEntityException, MalformattedElementException, NotPermittedOperationException, MethodNotImplementedException, NotAuthorizedOperationException;

    VnfPkgInfo getVnfPkgInfo(String vnfPkgInfoId, String project) throws FailedOperationException, NotPermittedOperationException, NotExistingEntityException, MalformattedElementException, MethodNotImplementedException, NotAuthorizedOperationException;

    List<VnfPkgInfo> getAllVnfPkgInfos(String project, String extraData, UUID vnfdId) throws FailedOperationException, MethodNotImplementedException, NotPermittedOperationException, NotAuthorizedOperationException;

    void uploadVnfPkg(String vnfPkgInfoId, MultipartFile vnfPkg, ContentType contentType, boolean isInternalRequest, String project) throws FailedOperationException, AlreadyExistingEntityException, NotExistingEntityException, MalformattedElementException, NotPermittedOperationException, NotAuthorizedOperationException;

    void updateVnfPkg(String vnfPkgInfoId, MultipartFile vnfPkg, ContentType contentType, boolean isInternalRequest, String originalManoId, String project) throws FailedOperationException, NotExistingEntityException, AlreadyExistingEntityException, MalformattedElementException, NotPermittedOperationException, NotAuthorizedOperationException;
}
