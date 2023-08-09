package it.nextworks.nfvmano.catalogue.plugins.catalogue2catalogue;

import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.NsdOnBoardingNotificationMessage;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.PnfdOnBoardingNotificationMessage;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.VnfPkgOnBoardingNotificationMessage;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.CatalogueMessageType;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.ScopeType;
import it.nextworks.nfvmano.catalogue.engine.NotificationManager;
import it.nextworks.nfvmano.catalogue.engine.resources.NotificationResource;
import it.nextworks.nfvmano.catalogue.engine.resources.NsdInfoResource;
import it.nextworks.nfvmano.catalogue.engine.resources.PnfdInfoResource;
import it.nextworks.nfvmano.catalogue.engine.resources.VnfPkgInfoResource;
import it.nextworks.nfvmano.catalogue.nbi.sol005.nsdmanagement.elements.NsdOnboardingStateType;
import it.nextworks.nfvmano.catalogue.nbi.sol005.nsdmanagement.elements.PnfdOnboardingStateType;
import it.nextworks.nfvmano.catalogue.nbi.sol005.vnfpackagemanagement.elements.PackageOnboardingStateType;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.PluginType;
import it.nextworks.nfvmano.catalogue.repos.CatalogueRepository;
import it.nextworks.nfvmano.catalogue.repos.NsdInfoRepository;
import it.nextworks.nfvmano.catalogue.repos.PnfdInfoRepository;
import it.nextworks.nfvmano.catalogue.repos.VnfPkgInfoRepository;
import it.nextworks.nfvmano.libs.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Cat2CatOperationService {

    private static final Logger log = LoggerFactory.getLogger(Cat2CatOperationService.class);

    @Autowired
    NotificationManager notificationManager;

    @Autowired
    CatalogueRepository catalogueRepository;

    @Autowired
    private NsdInfoRepository nsdInfoRepo;

    @Autowired
    private PnfdInfoRepository pnfdInfoRepo;

    @Autowired
    private VnfPkgInfoRepository vnfPkgInfoRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Map<String, Map<String, NotificationResource>> operationIdToConsumersAck = new HashMap<>();

    public void updateOperationInfoInConsumersMap(UUID operationId, OperationStatus opStatus, String catalogueId,
                                                  String nsdInfoId, CatalogueMessageType messageType) {
        Map<String, NotificationResource> catalogueIdToOpAck = new HashMap<>();
        if (operationIdToConsumersAck.containsKey(operationId.toString())) {
            catalogueIdToOpAck = operationIdToConsumersAck.get(operationId.toString());
        }
        NotificationResource notificationResource = new NotificationResource(nsdInfoId, messageType, opStatus, PluginType.C2C);
        catalogueIdToOpAck.put(catalogueId, notificationResource);

        operationIdToConsumersAck.put(operationId.toString(), catalogueIdToOpAck);
    }

    private UUID insertOperationInfoInConsumersMap(String nsdInfoId, CatalogueMessageType messageType,
                                                   OperationStatus opStatus) {
        UUID operationId = UUID.randomUUID();
        log.debug("Updating consumers internal mapping for operationId {}", operationId);
        List<Catalogue> catalogues = catalogueRepository.findAll();
        Map<String, NotificationResource> pluginToOperationState = new HashMap<>();
        for (Catalogue catalogue : catalogues) {
            pluginToOperationState.put(catalogue.getCatalogueId(), new NotificationResource(nsdInfoId, messageType, opStatus, PluginType.C2C));

        }
        operationIdToConsumersAck.put(operationId.toString(), pluginToOperationState);
        return operationId;
    }

    public void exportNsd(String nsdInfoId) throws FailedOperationException {

        UUID operationId = insertOperationInfoInConsumersMap(nsdInfoId,
                CatalogueMessageType.NSD_ONBOARDING_NOTIFICATION, OperationStatus.SENT);

        NsdOnBoardingNotificationMessage msg = new NsdOnBoardingNotificationMessage(nsdInfoId, null, null, null,
                operationId, ScopeType.C2C, OperationStatus.SENT, null, null,null);

        try {
            notificationManager.sendNsdOnBoardingNotification(msg);
        } catch (FailedOperationException e) {
            log.error("Error while posting NsdOnBoardingNotificationMessage to Kafka bus");
            throw new FailedOperationException(e.getMessage());
        }
    }

    public void exportPnfd(String pnfdInfoId) throws FailedOperationException {

        UUID operationId = insertOperationInfoInConsumersMap(pnfdInfoId,
                CatalogueMessageType.PNFD_ONBOARDING_NOTIFICATION, OperationStatus.SENT);

        PnfdOnBoardingNotificationMessage msg = new PnfdOnBoardingNotificationMessage(pnfdInfoId, null, null, null,
                operationId, ScopeType.C2C, OperationStatus.SENT, null, null);

        try {
            notificationManager.sendPnfdOnBoardingNotification(msg);
        } catch (FailedOperationException e) {
            log.error("Error while posting PnfdOnBoardingNotificationMessage to Kafka bus");
            throw new FailedOperationException(e.getMessage());
        }
    }

    public void exportVnfPkg(String vnfPkgInfoId) throws FailedOperationException {

        UUID operationId = insertOperationInfoInConsumersMap(vnfPkgInfoId,
                CatalogueMessageType.VNFPKG_ONBOARDING_NOTIFICATION, OperationStatus.SENT);

        VnfPkgOnBoardingNotificationMessage msg = new VnfPkgOnBoardingNotificationMessage(vnfPkgInfoId, null, null, null,
                operationId, ScopeType.C2C, OperationStatus.SENT,  null, null,null);

        try {
            notificationManager.sendVnfPkgOnBoardingNotification(msg);
        } catch (FailedOperationException e) {
            log.error("Error while posting VnfPkgOnBoardingNotificationMessage to Kafka bus");
            throw new FailedOperationException(e.getMessage());
        }
    }

    public synchronized void updateNsdInfoOperationStatus(String nsdInfoId, String catalogueId, OperationStatus opStatus,
                                                          CatalogueMessageType messageType) throws NotExistingEntityException {

        log.debug("Retrieving nsdInfoResource {} from DB for updating with onboarding status info for plugin {}",
                nsdInfoId, catalogueId);
        Optional<NsdInfoResource> optionalNsdInfoResource = nsdInfoRepo.findById(UUID.fromString(nsdInfoId));

        if (optionalNsdInfoResource.isPresent()) {
            try {
                NsdInfoResource nsdInfoResource = optionalNsdInfoResource.get();

                Map<String, NotificationResource> ackMap = new HashMap<>();
                if (nsdInfoResource.getAcknowledgedOnboardOpConsumers() != null) {
                    ackMap = nsdInfoResource.getAcknowledgedOnboardOpConsumers();
                }
                ackMap.put(catalogueId, new NotificationResource(nsdInfoId, messageType, opStatus, PluginType.C2C));
                nsdInfoResource.setAcknowledgedOnboardOpConsumers(ackMap);

                if (messageType == CatalogueMessageType.NSD_ONBOARDING_NOTIFICATION) {
                    log.debug("Checking NSD with nsdInfoId {} onboarding state", nsdInfoId);
                    nsdInfoResource.setNsdOnboardingState(checkNsdOnboardingState(nsdInfoId, ackMap));
                }

                log.debug("Updating NsdInfoResource {} with onboardingState {}", nsdInfoId,
                        nsdInfoResource.getNsdOnboardingState());
                nsdInfoRepo.saveAndFlush(nsdInfoResource);
            } catch (Exception e) {
                log.error("Error while updating NsdInfoResource with nsdInfoId: " + nsdInfoId);
                log.error("Details: ", e);
            }
        } else {
            throw new NotExistingEntityException("NsdInfoResource " + nsdInfoId + " not present in DB");
        }
    }

    private NsdOnboardingStateType checkNsdOnboardingState(String nsdInfoId, Map<String, NotificationResource> ackMap) {

        for (Map.Entry<String, NotificationResource> entry : ackMap.entrySet()) {
            if (entry.getValue().getOperation() == CatalogueMessageType.NSD_ONBOARDING_NOTIFICATION
                    && entry.getValue().getPluginType() == PluginType.C2C) {
                if (entry.getValue().getOpStatus() == OperationStatus.FAILED) {
                    log.error("NSD with nsdInfoId {} onboarding failed for 5G Catalogue with catalogueId {}", nsdInfoId,
                            entry.getKey());

                    // TODO: Decide how to handle 5G Catalogue onboarding failures.
                    return NsdOnboardingStateType.LOCAL_ONBOARDED;
                } else if (entry.getValue().getOpStatus() == OperationStatus.SENT
                        || entry.getValue().getOpStatus() == OperationStatus.RECEIVED
                        || entry.getValue().getOpStatus() == OperationStatus.PROCESSING) {
                    log.debug("NSD with nsdInfoId {} onboarding still in progress for 5G Catalogue with catalogueId {}");
                    return NsdOnboardingStateType.LOCAL_ONBOARDED;
                }
            }
        }
        log.debug("NSD with nsdInfoId " + nsdInfoId + " successfully onboarded by all expected consumers");
        return NsdOnboardingStateType.ONBOARDED;
    }

    public synchronized void updatePnfdInfoOperationStatus(String pnfdInfoId, String catalogueId, OperationStatus opStatus,
                                                           CatalogueMessageType messageType) throws NotExistingEntityException {

        log.debug("Retrieving pnfdInfoResource {} from DB for updating with onboarding status info for plugin {}",
                pnfdInfoId, catalogueId);
        Optional<PnfdInfoResource> optionalPnfdInfoResource = pnfdInfoRepo.findById(UUID.fromString(pnfdInfoId));

        if (optionalPnfdInfoResource.isPresent()) {
            try {
                PnfdInfoResource pnfdInfoResource = optionalPnfdInfoResource.get();

                Map<String, NotificationResource> ackMap = new HashMap<>();
                if (pnfdInfoResource.getAcknowledgedOnboardOpConsumers() != null) {
                    ackMap = pnfdInfoResource.getAcknowledgedOnboardOpConsumers();
                }
                ackMap.put(catalogueId, new NotificationResource(pnfdInfoId, messageType, opStatus, PluginType.C2C));
                pnfdInfoResource.setAcknowledgedOnboardOpConsumers(ackMap);

                if (messageType == CatalogueMessageType.PNFD_ONBOARDING_NOTIFICATION) {
                    log.debug("Checking PNFD with pnfdInfoId {} onboarding state", pnfdInfoId);
                    pnfdInfoResource.setPnfdOnboardingState(checkPnfdOnboardingState(pnfdInfoId, ackMap));
                }

                log.debug("Updating PnfdInfoResource {} with onboardingState {}", pnfdInfoId,
                        pnfdInfoResource.getPnfdOnboardingState());
                pnfdInfoRepo.saveAndFlush(pnfdInfoResource);
            } catch (Exception e) {
                log.error("Error while updating PnfdInfoResource with pnfdInfoId: " + pnfdInfoId);
                log.error("Details: ", e);
            }
        } else {
            throw new NotExistingEntityException("PnfdInfoResource " + pnfdInfoId + " not present in DB");
        }
    }

    private PnfdOnboardingStateType checkPnfdOnboardingState(String pnfdInfoId, Map<String, NotificationResource> ackMap) {

        for (Map.Entry<String, NotificationResource> entry : ackMap.entrySet()) {
            if (entry.getValue().getOperation() == CatalogueMessageType.PNFD_ONBOARDING_NOTIFICATION
                    && entry.getValue().getPluginType() == PluginType.C2C) {
                if (entry.getValue().getOpStatus() == OperationStatus.FAILED) {
                    log.error("PNFD with pnfdInfoId {} onboarding failed for 5G Catalogue with catalogueId {}", pnfdInfoId,
                            entry.getKey());

                    // TODO: Decide how to handle 5G Catalogue onboarding failures.
                    return PnfdOnboardingStateType.LOCAL_ONBOARDED;
                } else if (entry.getValue().getOpStatus() == OperationStatus.SENT
                        || entry.getValue().getOpStatus() == OperationStatus.RECEIVED
                        || entry.getValue().getOpStatus() == OperationStatus.PROCESSING) {
                    log.debug("PNFD with pnfdInfoId {} onboarding still in progress for5G Catalogue with catalogueId {}");
                    return PnfdOnboardingStateType.LOCAL_ONBOARDED;
                }
            }
        }
        log.debug("PNFD with pnfdInfoId " + pnfdInfoId + " successfully onboarded by all expected consumers");
        return PnfdOnboardingStateType.ONBOARDED;
    }

    public void updateVnfPkgInfoOperationStatus(String vnfPkgInfoId, String pluginId, OperationStatus opStatus, CatalogueMessageType type) throws NotExistingEntityException {
        log.debug("Retrieving vnfPkgInfoResource {} from DB for updating with onboarding status info for plugin {}",
                vnfPkgInfoId, pluginId);
        Optional<VnfPkgInfoResource> optionalVnfPkgInfoResource = vnfPkgInfoRepository.findById(UUID.fromString(vnfPkgInfoId));

        if (optionalVnfPkgInfoResource.isPresent()) {
            try {
                VnfPkgInfoResource vnfPkgInfoResource = optionalVnfPkgInfoResource.get();

                Map<String, NotificationResource> ackMap = new HashMap<>();
                if (vnfPkgInfoResource.getAcknowledgedOnboardOpConsumers() != null) {
                    ackMap = vnfPkgInfoResource.getAcknowledgedOnboardOpConsumers();
                }
                ackMap.put(pluginId, new NotificationResource(vnfPkgInfoId, type, opStatus, PluginType.C2C));
                vnfPkgInfoResource.setAcknowledgedOnboardOpConsumers(ackMap);

                if (type == CatalogueMessageType.VNFPKG_ONBOARDING_NOTIFICATION) {
                    log.debug("Checking VNF Pkg with vnfPkgInfoId {} onboarding state", vnfPkgInfoId);
                    vnfPkgInfoResource.setOnboardingState(checkVnfPkgOnboardingState(vnfPkgInfoId, ackMap));
                }

                log.debug("Updating VnfPkgInfoResource {} with onboardingState {}", vnfPkgInfoId,
                        vnfPkgInfoResource.getOnboardingState());
                vnfPkgInfoRepository.saveAndFlush(vnfPkgInfoResource);
            } catch (Exception e) {
                log.error("Error while updating VnfPkgInfoResource with vnfPkgInfoId: " + vnfPkgInfoId);
                log.error("Details: ", e);
            }
        } else {
            throw new NotExistingEntityException("VnfPkgInfoResource " + vnfPkgInfoId + " not present in DB");
        }
    }

    private PackageOnboardingStateType checkVnfPkgOnboardingState(String vnfPkgInfoId, Map<String, NotificationResource> ackMap) {

        for (Map.Entry<String, NotificationResource> entry : ackMap.entrySet()) {
            if (entry.getValue().getOperation() == CatalogueMessageType.VNFPKG_ONBOARDING_NOTIFICATION
                    && entry.getValue().getPluginType() == PluginType.C2C) {
                if (entry.getValue().getOpStatus() == OperationStatus.FAILED) {
                    log.error("VNF Pkg with vnfPkgInfoId {} onboarding failed for 5G Catalogue with catalogueId {}", vnfPkgInfoId,
                            entry.getKey());

                    // TODO: Decide how to handle 5G Catalogue onboarding failures.
                    return PackageOnboardingStateType.LOCAL_ONBOARDED;
                } else if (entry.getValue().getOpStatus() == OperationStatus.SENT
                        || entry.getValue().getOpStatus() == OperationStatus.RECEIVED
                        || entry.getValue().getOpStatus() == OperationStatus.PROCESSING) {
                    log.debug("VNF Pkg with vnfPkgInfoId {} onboarding still in progress for 5G Catalogue with catalogueId {}");
                    return PackageOnboardingStateType.LOCAL_ONBOARDED;
                }
            }
        }
        log.debug("VNF Pkg with vnfPkgInfoId " + vnfPkgInfoId + " successfully onboarded by all expected consumers");
        return PackageOnboardingStateType.ONBOARDED;
    }
}
