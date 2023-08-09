package it.nextworks.nfvmano.catalogue.engine;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.nextworks.nfvmano.catalogue.auth.AuthUtilities;
import it.nextworks.nfvmano.catalogue.auth.projectmanagement.ProjectResource;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.*;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.CatalogueMessageType;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.PathType;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.ScopeType;
import it.nextworks.nfvmano.catalogue.common.ConfigurationParameters;
import it.nextworks.nfvmano.catalogue.common.enums.DataModelSpec;
import it.nextworks.nfvmano.catalogue.common.enums.DescriptorType;
import it.nextworks.nfvmano.catalogue.engine.elements.ContentType;
import it.nextworks.nfvmano.catalogue.engine.resources.NotificationResource;
import it.nextworks.nfvmano.catalogue.engine.resources.VnfPkgInfoResource;
import it.nextworks.nfvmano.catalogue.nbi.sol005.nsdmanagement.elements.KeyValuePairs;
import it.nextworks.nfvmano.catalogue.nbi.sol005.vnfpackagemanagement.elements.*;
import it.nextworks.nfvmano.catalogue.plugins.PluginsManager;
import it.nextworks.nfvmano.catalogue.plugins.catalogue2catalogue.C2COnboardingStateType;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.PluginType;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.MANO;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.MANOPlugin;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.repos.MANORepository;
import it.nextworks.nfvmano.catalogue.repos.ProjectRepository;
import it.nextworks.nfvmano.catalogue.repos.UserRepository;
import it.nextworks.nfvmano.catalogue.repos.VnfPkgInfoRepository;
import it.nextworks.nfvmano.catalogue.storage.FileSystemStorageService;
import it.nextworks.nfvmano.catalogue.translators.tosca.ArchiveParser;
import it.nextworks.nfvmano.catalogue.translators.tosca.DescriptorsParser;
import it.nextworks.nfvmano.catalogue.translators.tosca.elements.CSARInfo;
import it.nextworks.nfvmano.libs.common.elements.KeyValuePair;
import it.nextworks.nfvmano.libs.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.common.exceptions.*;
import it.nextworks.nfvmano.libs.descriptors.sol006.ExtCpd;
import it.nextworks.nfvmano.libs.descriptors.sol006.NetworkInterfaceRequirementsSchema;
import it.nextworks.nfvmano.libs.descriptors.sol006.VirtualNetworkInterfaceRequirementSchema;
import it.nextworks.nfvmano.libs.descriptors.sol006.Vnfd;
import it.nextworks.nfvmano.libs.descriptors.templates.DescriptorTemplate;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VNF.VNFNode;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VnfExtCp.VnfExtCpNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static it.nextworks.nfvmano.catalogue.engine.Utilities.*;

@Service
public class VnfPackageManagementService implements VnfPackageManagementInterface {

    private static final Logger log = LoggerFactory.getLogger(VnfPackageManagementService.class);

    @Autowired
    private VnfPkgInfoRepository vnfPkgInfoRepository;

    @Autowired
    private FileSystemStorageService storageService;

    @Autowired
    private NotificationManager notificationManager;

    @Autowired
    private NsdManagementService nsdMgmtService;

    @Autowired
    private MANORepository MANORepository;

    @Autowired
    private ArchiveParser archiveParser;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PluginsManager pluginManger;

    private Map<String, Map<String, NotificationResource>> operationIdToConsumersAck = new HashMap<>();

    @Value("${catalogue.storageRootDir}")
    private String rootDir;

    @Value("${keycloak.enabled:true}")
    private boolean keycloakEnabled;

    @Value("${mano.startup.sync}")
    private boolean startupSync;

    @PostConstruct
    private void startSync(){
        if(startupSync) {
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            Runnable syncTask = this::startupSync;
            scheduler.schedule(syncTask, 10, TimeUnit.SECONDS);
        }
    }

    @Override
    public void startupSync(){
        log.info("Starting MANO VNF synchronization");
        for (MANOPlugin manoPlugin : pluginManger.manoDrivers.values()) {
            List<ProjectResource> projectResourceList = projectRepository.findAll();
            for (ProjectResource project : projectResourceList) {
                Map<String, List<String>> vnfFromManoList = manoPlugin.getAllVnfd(project.getProjectId());
                if (vnfFromManoList == null) {
                    log.error("Project " + project.getProjectId() + " - Cannot retrieve VNFDs list from MANO with ID " + manoPlugin.getPluginId());
                    continue;
                }
                List<VnfPkgInfoResource> vnfPkgInfoResourceList = vnfPkgInfoRepository.findByProjectId(project.getProjectId());
                for (Map.Entry<String, List<String>> vnfFromMano : vnfFromManoList.entrySet()) {
                    String vnfdId = vnfFromMano.getKey();
                    for(String vnfdVersion : vnfFromMano.getValue()) {
                        if (!Utilities.isUUID(vnfdId)) {
                            log.error("Project " + project.getProjectId() + " - Cannot retrieve VNFD with ID " + vnfdId + " and version " + vnfdVersion + " from MANO with ID " + manoPlugin.getPluginId() + ", ID is not in UUID format");
                            continue;
                        }
                        Optional<VnfPkgInfoResource> optionalVnfPkgInfoResource = vnfPkgInfoRepository.findByVnfdIdAndVnfdVersionAndProjectId(UUID.fromString(vnfdId), vnfdVersion, project.getProjectId());
                        if (!optionalVnfPkgInfoResource.isPresent()) {
                            if (manoPlugin.getManoType().toString().startsWith("OSM")) {//Avoid sync among projects of VNFs uploaded from NBI, OSM plugin doesn't handle projects
                                boolean isRetrievedFromMano = true;
                                List<VnfPkgInfoResource> infoResources = vnfPkgInfoRepository.findByVnfdIdAndVnfdVersion(UUID.fromString(vnfdId), vnfdVersion);
                                for (VnfPkgInfoResource infoResource : infoResources)
                                    if (!infoResource.isRetrievedFromMANO()) {
                                        isRetrievedFromMano = false;
                                        break;
                                    }
                                if (!isRetrievedFromMano)
                                    continue;
                            }
                            log.info("Project {} - Uploading VNFD with ID {} and version {} retrieved from MANO with ID {}", project.getProjectId(), vnfdId, vnfdVersion, manoPlugin.getPluginId());
                            KeyValuePair vnfPath = manoPlugin.getTranslatedPkgPath(vnfdId, vnfdVersion, project.getProjectId());
                            if (vnfPath == null) {
                                log.error("Project " + project.getProjectId() + " - Cannot retrieve VNFD with ID " + vnfdId + " and version " + vnfdVersion + " from MANO with ID " + manoPlugin.getPluginId());
                                continue;
                            }
                            CreateVnfPkgInfoRequest request = new CreateVnfPkgInfoRequest();
                            KeyValuePairs userDefinedData = new KeyValuePairs();
                            userDefinedData.put("isRetrievedFromMANO", "yes");
                            request.setUserDefinedData(userDefinedData);
                            try {
                                VnfPkgInfo vnfPkgInfo = createVnfPkgInfo(request, project.getProjectId(), true);
                                File vnfPkg = null;
                                if (vnfPath.getValue().equals(PathType.LOCAL.toString())) {
                                    vnfPkg = new File(vnfPath.getKey());
                                } else {
                                    log.error("Path Type not currently supported");
                                    continue;
                                }
                                MultipartFile multipartFile = Utilities.createMultiPartFromFile(vnfPkg);
                                uploadVnfPkg(vnfPkgInfo.getId().toString(), multipartFile, ContentType.ZIP, false, project.getProjectId());
                                updateVnfPkgInfoOperationStatus(vnfPkgInfo.getId().toString(), manoPlugin.getPluginId(), OperationStatus.SUCCESSFULLY_DONE, CatalogueMessageType.VNFPKG_ONBOARDING_NOTIFICATION);
                                manoPlugin.notifyOnboarding(vnfPkgInfo.getId().toString(), vnfdId, vnfdVersion, project.getProjectId(), OperationStatus.SUCCESSFULLY_DONE);
                            } catch (Exception e) {
                                log.error(e.getMessage());
                                log.debug(null, e);
                                manoPlugin.notifyOnboarding(null, vnfdId, vnfdVersion, project.getProjectId(), OperationStatus.FAILED);
                            }
                        } else {
                            log.info("Project {} - VNFD with ID {} and version {} retrieved from MANO with ID {} is already present", project.getProjectId(), vnfdId, vnfdVersion, manoPlugin.getPluginId());
                            VnfPkgInfo vnfPkgInfo = buildVnfPkgInfo(optionalVnfPkgInfoResource.get());
                            if (vnfPkgInfo.getManoIdToOnboardingStatus().get(manoPlugin.getPluginId()) == null || !vnfPkgInfo.getManoIdToOnboardingStatus().get(manoPlugin.getPluginId()).equals(PackageOnboardingStateType.ONBOARDED)) {
                                try {
                                    updateVnfPkgInfoOperationStatus(vnfPkgInfo.getId().toString(), manoPlugin.getPluginId(), OperationStatus.SUCCESSFULLY_DONE, CatalogueMessageType.VNFPKG_ONBOARDING_NOTIFICATION);
                                    manoPlugin.notifyOnboarding(vnfPkgInfo.getId().toString(), vnfdId, vnfdVersion, project.getProjectId(), OperationStatus.SUCCESSFULLY_DONE);
                                } catch (NotExistingEntityException e) {
                                    log.error(e.getMessage());
                                    log.debug(null, e);
                                    manoPlugin.notifyOnboarding(null, vnfdId, vnfdVersion, project.getProjectId(), OperationStatus.FAILED);
                                }
                            }
                            vnfPkgInfoResourceList.removeIf(t -> t.getId().equals(optionalVnfPkgInfoResource.get().getId()));
                        }
                    }
                }
                for (VnfPkgInfoResource vnfPkgInfoResource : vnfPkgInfoResourceList) {
                    if(vnfPkgInfoResource.getAcknowledgedOnboardOpConsumers().get(manoPlugin.getPluginId()) != null && vnfPkgInfoResource.getAcknowledgedOnboardOpConsumers().get(manoPlugin.getPluginId()).getOpStatus().equals(OperationStatus.SUCCESSFULLY_DONE)) {
                        log.info("Project {} - VNFD with ID {} and version {} no longer present in MANO with ID {}", project.getProjectId(), vnfPkgInfoResource.getVnfdId(), vnfPkgInfoResource.getVnfdVersion(), manoPlugin.getPluginId());
                        if (vnfPkgInfoResource.isRetrievedFromMANO()) {
                            try {
                                updateVnfPkgInfoOperationStatus(vnfPkgInfoResource.getId().toString(), manoPlugin.getPluginId(), OperationStatus.RECEIVED, CatalogueMessageType.VNFPKG_ONBOARDING_NOTIFICATION);
                                Optional<VnfPkgInfoResource> targetVnfPkgInfoResource = vnfPkgInfoRepository.findById(vnfPkgInfoResource.getId());
                                if (targetVnfPkgInfoResource.isPresent() && !targetVnfPkgInfoResource.get().getOnboardingState().equals(PackageOnboardingStateType.ONBOARDED)) {//VNF was onboarded only on the current MANO plugin
                                    log.info("Project {} - Going to delete VNFD with ID {} and version {}", project.getProjectId(), vnfPkgInfoResource.getVnfdId(), vnfPkgInfoResource.getVnfdVersion());
                                    VnfPkgInfoModifications request = new VnfPkgInfoModifications();
                                    request.setOperationalState(PackageOperationalStateType.DISABLED);
                                    updateVnfPkgInfo(request, vnfPkgInfoResource.getId().toString(), project.getProjectId(), true);
                                    deleteVnfPkgInfo(vnfPkgInfoResource.getId().toString(), project.getProjectId(), true);
                                }
                                manoPlugin.notifyDelete(vnfPkgInfoResource.getId().toString(), vnfPkgInfoResource.getVnfdId().toString(), vnfPkgInfoResource.getVnfdVersion(), project.getProjectId(), OperationStatus.SUCCESSFULLY_DONE);
                            }catch (NotPermittedOperationException e){
                                log.error("Project {} - Failed to delete VNFD with ID {} and version {}, it is not deletable", project.getProjectId(), vnfPkgInfoResource.getVnfdId(), vnfPkgInfoResource.getVnfdVersion());
                                manoPlugin.notifyDelete(vnfPkgInfoResource.getId().toString(), vnfPkgInfoResource.getVnfdId().toString(), vnfPkgInfoResource.getVnfdVersion(), project.getProjectId(), OperationStatus.FAILED);
                                log.info("Project {} - Sending VNF Onboarding notification for VNFD with ID {} and version {} to MANO with ID {}", project.getProjectId(), vnfPkgInfoResource.getVnfdId(), vnfPkgInfoResource.getVnfdVersion(), manoPlugin.getPluginId());
                                List<String> siteOrManoIds = new ArrayList<>();
                                siteOrManoIds.add(manoPlugin.getPluginId());
                                VnfPkgOnBoardingNotificationMessage msg =
                                        new VnfPkgOnBoardingNotificationMessage(vnfPkgInfoResource.getId().toString(), vnfPkgInfoResource.getVnfdId().toString(), vnfPkgInfoResource.getVnfdVersion(), project.getProjectId(), UUID.randomUUID(), ScopeType.LOCAL, OperationStatus.SENT, siteOrManoIds, new KeyValuePair(rootDir + ConfigurationParameters.storageVnfpkgsSubfolder + "/" + project.getProjectId() + "/" + vnfPkgInfoResource.getVnfdId() + "/" + vnfPkgInfoResource.getVnfdVersion(), PathType.LOCAL.toString()));
                                try {
                                    notificationManager.sendVnfPkgOnBoardingNotification(msg);
                                } catch (FailedOperationException e1) {
                                    log.error(e1.getMessage());
                                    log.debug(null, e1);
                                }
                            } catch (Exception e) {
                                log.error("Project {} - Failed to delete VNFD with ID {} and version {}", project.getProjectId(), vnfPkgInfoResource.getVnfdId(), vnfPkgInfoResource.getVnfdVersion());
                                log.debug(null, e);
                                manoPlugin.notifyDelete(vnfPkgInfoResource.getId().toString(), vnfPkgInfoResource.getVnfdId().toString(), vnfPkgInfoResource.getVnfdVersion(), project.getProjectId(), OperationStatus.FAILED);
                            }
                        } else {
                            log.info("Project {} - Sending VNF Onboarding notification for VNFD with ID {} and version {} to MANO with ID {}", project.getProjectId(), vnfPkgInfoResource.getVnfdId(), vnfPkgInfoResource.getVnfdVersion(), manoPlugin.getPluginId());
                            manoPlugin.notifyDelete(vnfPkgInfoResource.getId().toString(), vnfPkgInfoResource.getVnfdId().toString(), vnfPkgInfoResource.getVnfdVersion(), project.getProjectId(), OperationStatus.FAILED);
                            List<String> siteOrManoIds = new ArrayList<>();
                            siteOrManoIds.add(manoPlugin.getPluginId());
                            VnfPkgOnBoardingNotificationMessage msg =
                                    new VnfPkgOnBoardingNotificationMessage(vnfPkgInfoResource.getId().toString(), vnfPkgInfoResource.getVnfdId().toString(), vnfPkgInfoResource.getVnfdVersion(), project.getProjectId(), UUID.randomUUID(), ScopeType.LOCAL, OperationStatus.SENT, siteOrManoIds, new KeyValuePair(rootDir + ConfigurationParameters.storageVnfpkgsSubfolder + "/" + project.getProjectId() + "/" + vnfPkgInfoResource.getVnfdId() + "/" + vnfPkgInfoResource.getVnfdVersion(), PathType.LOCAL.toString()));
                            try {
                                notificationManager.sendVnfPkgOnBoardingNotification(msg);
                            } catch (FailedOperationException e) {
                                log.error(e.getMessage());
                                log.debug(null, e);
                            }
                        }
                    }
                }
            }
        }
        log.info("MANO VNF synchronization completed");

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        Runnable syncTask = () -> nsdMgmtService.startupSync();
        scheduler.schedule(syncTask, 1, TimeUnit.SECONDS);
    }

    public void runtimeVnfOnBoarding(VnfPkgOnBoardingNotificationMessage notification){
        List<String> projects = new ArrayList<>();
        if(notification.getProject() == null || notification.getProject().equals("all")){
            List<ProjectResource> projectResourceList = projectRepository.findAll();
            for(ProjectResource projectResource : projectResourceList)
                projects.add(projectResource.getProjectId());
        }else
            projects.add(notification.getProject());

        for(String project : projects){
            Optional<VnfPkgInfoResource> optionalVnfPkgInfoResource = vnfPkgInfoRepository.findByVnfdIdAndVnfdVersionAndProjectId(UUID.fromString(notification.getVnfdId()), notification.getVnfdVersion(), project);
            if (!optionalVnfPkgInfoResource.isPresent()) {
                log.info("Project {} - Uploading VNFD with ID {} and version {} retrieved from MANO with ID {}", project, notification.getVnfdId(), notification.getVnfdVersion(), notification.getPluginId());
                CreateVnfPkgInfoRequest request = new CreateVnfPkgInfoRequest();
                KeyValuePairs userDefinedData = new KeyValuePairs();
                userDefinedData.put("isRetrievedFromMANO", "yes");
                request.setUserDefinedData(userDefinedData);
                try {
                    VnfPkgInfo vnfPkgInfo = createVnfPkgInfo(request, project, true);
                    File vnfPkg;
                    if (notification.getPackagePath().getValue().equals(PathType.LOCAL.toString())) {
                        vnfPkg = new File(notification.getPackagePath().getKey());
                    } else {
                        throw new MethodNotImplementedException("Path Type not currently supported");
                    }
                    MultipartFile multipartFile = Utilities.createMultiPartFromFile(vnfPkg);
                    uploadVnfPkg(vnfPkgInfo.getId().toString(), multipartFile, ContentType.ZIP, false, project);
                    updateVnfPkgInfoOperationStatus(vnfPkgInfo.getId().toString(), notification.getPluginId(), OperationStatus.SUCCESSFULLY_DONE, CatalogueMessageType.VNFPKG_ONBOARDING_NOTIFICATION);
                    notificationManager.sendVnfPkgOnBoardingNotification(new VnfPkgOnBoardingNotificationMessage(vnfPkgInfo.getId().toString(), notification.getVnfdId(), notification.getVnfdVersion(), project, notification.getOperationId(), ScopeType.SYNC, OperationStatus.SUCCESSFULLY_DONE, notification.getPluginId(), null,null));
                } catch (Exception e) {
                    log.error(e.getMessage());
                    log.debug(null, e);
                    try {
                        notificationManager.sendVnfPkgOnBoardingNotification(new VnfPkgOnBoardingNotificationMessage(null, notification.getVnfdId(), notification.getVnfdVersion(), project, notification.getOperationId(), ScopeType.SYNC, OperationStatus.FAILED, notification.getPluginId(), null, null));
                    }catch(FailedOperationException e1){
                        log.error(e1.getMessage());
                        log.error(e1.getMessage());
                    }
                }
            }else {
                log.info("Project {} - VNFD with ID {} and version {} retrieved from MANO with ID {} is already present", project, notification.getVnfdId(), notification.getVnfdVersion(), notification.getPluginId());
                try {
                    updateVnfPkgInfoOperationStatus(optionalVnfPkgInfoResource.get().getId().toString(), notification.getPluginId(), OperationStatus.SUCCESSFULLY_DONE, CatalogueMessageType.VNFPKG_ONBOARDING_NOTIFICATION);
                    notificationManager.sendVnfPkgOnBoardingNotification(new VnfPkgOnBoardingNotificationMessage(optionalVnfPkgInfoResource.get().getId().toString(), notification.getVnfdId(), notification.getVnfdVersion(), project, notification.getOperationId(), ScopeType.SYNC, OperationStatus.SUCCESSFULLY_DONE, notification.getPluginId(), null,null));
                } catch (Exception e) {
                    log.error(e.getMessage());
                    log.debug(null, e);
                    try {
                        notificationManager.sendVnfPkgOnBoardingNotification(new VnfPkgOnBoardingNotificationMessage(optionalVnfPkgInfoResource.get().getId().toString(), notification.getVnfdId(), notification.getVnfdVersion(), project, notification.getOperationId(), ScopeType.SYNC, OperationStatus.FAILED, notification.getPluginId(), null, null));
                    }catch(FailedOperationException e1){
                        log.error(e1.getMessage());
                        log.error(e1.getMessage());
                    }
                }
            }
        }
    }

    public void runtimeVnfDeletion(VnfPkgDeletionNotificationMessage notification){
        List<String> projects = new ArrayList<>();
        if(notification.getProject() == null || notification.getProject().equals("all")){
            List<ProjectResource> projectResourceList = projectRepository.findAll();
            for(ProjectResource projectResource : projectResourceList)
                projects.add(projectResource.getProjectId());
        }else
            projects.add(notification.getProject());

        for(String project : projects) {
            log.info("Project {} - VNFD with ID {} and version {} no longer present in MANO with ID {}", project, notification.getVnfdId(), notification.getVnfdVersion(), notification.getPluginId());
            Optional<VnfPkgInfoResource> optionalVnfPkgInfoResource = vnfPkgInfoRepository.findByVnfdIdAndVnfdVersionAndProjectId(UUID.fromString(notification.getVnfdId()), notification.getVnfdVersion(), project);
            if (optionalVnfPkgInfoResource.isPresent()) {
                VnfPkgInfoResource vnfPkgInfoResource = optionalVnfPkgInfoResource.get();
                if (vnfPkgInfoResource.isRetrievedFromMANO()) {
                    try {
                        updateVnfPkgInfoOperationStatus(vnfPkgInfoResource.getId().toString(), notification.getPluginId(), OperationStatus.RECEIVED, CatalogueMessageType.VNFPKG_ONBOARDING_NOTIFICATION);
                        Optional<VnfPkgInfoResource> targetVnfPkgInfoResource = vnfPkgInfoRepository.findById(vnfPkgInfoResource.getId());
                        if (targetVnfPkgInfoResource.isPresent() && !targetVnfPkgInfoResource.get().getOnboardingState().equals(PackageOnboardingStateType.ONBOARDED)) {
                            log.info("Project {} - Going to delete VNFD with ID {} and version {}", project, vnfPkgInfoResource.getVnfdId(), vnfPkgInfoResource.getVnfdVersion());
                            VnfPkgInfoModifications request = new VnfPkgInfoModifications();
                            request.setOperationalState(PackageOperationalStateType.DISABLED);
                            updateVnfPkgInfo(request, vnfPkgInfoResource.getId().toString(), project, true);
                            deleteVnfPkgInfo(vnfPkgInfoResource.getId().toString(), project, true);
                        }
                        notificationManager.sendVnfPkgDeletionNotification(new VnfPkgDeletionNotificationMessage(vnfPkgInfoResource.getId().toString(), notification.getVnfdId(), notification.getVnfdVersion(), project, notification.getOperationId(), ScopeType.SYNC, OperationStatus.SUCCESSFULLY_DONE, notification.getPluginId(), null));
                    }catch (NotPermittedOperationException e){
                        log.error("Project {} - Failed to delete VNFD with ID {} and version {}, it is not deletable", project, vnfPkgInfoResource.getVnfdId(), vnfPkgInfoResource.getVnfdVersion());
                        VnfPkgDeletionNotificationMessage msg = new VnfPkgDeletionNotificationMessage(vnfPkgInfoResource.getId().toString(), notification.getVnfdId(), notification.getVnfdVersion(), project, notification.getOperationId(), ScopeType.SYNC, OperationStatus.FAILED, notification.getPluginId(), null);
                        List<String> siteOrManoIds = new ArrayList<>();
                        siteOrManoIds.add(notification.getPluginId());
                        VnfPkgOnBoardingNotificationMessage msg1 = new VnfPkgOnBoardingNotificationMessage(vnfPkgInfoResource.getId().toString(), vnfPkgInfoResource.getVnfdId().toString(), vnfPkgInfoResource.getVnfdVersion(), project, UUID.randomUUID(), ScopeType.LOCAL, OperationStatus.SENT, siteOrManoIds, new KeyValuePair(rootDir + ConfigurationParameters.storageVnfpkgsSubfolder + "/" + project + "/" + vnfPkgInfoResource.getVnfdId() + "/" + vnfPkgInfoResource.getVnfdVersion(), PathType.LOCAL.toString()));
                        try {
                            notificationManager.sendVnfPkgDeletionNotification(msg);
                            notificationManager.sendVnfPkgOnBoardingNotification(msg1);
                        } catch (FailedOperationException e1) {
                            log.error(e1.getMessage());
                            log.debug(null, e1);
                        }
                    } catch (Exception e) {
                        log.error("Project {} - Failed to delete VNFD with ID {} and version {}", project, vnfPkgInfoResource.getVnfdId(), vnfPkgInfoResource.getVnfdVersion());
                        log.debug(null, e);
                        try {
                            notificationManager.sendVnfPkgDeletionNotification(new VnfPkgDeletionNotificationMessage(vnfPkgInfoResource.getId().toString(), notification.getVnfdId(), notification.getVnfdVersion(), project, notification.getOperationId(), ScopeType.SYNC, OperationStatus.FAILED, notification.getPluginId(), null));
                        }catch(FailedOperationException e1){
                            log.error(e1.getMessage());
                            log.error(e1.getMessage());
                        }
                    }
                } else {
                    log.info("Project {} - Sending VNF Onboarding notification for VNFD with ID {} and version {} to MANO with ID {}", project, vnfPkgInfoResource.getVnfdId(), vnfPkgInfoResource.getVnfdVersion(), notification.getPluginId());
                    try {
                        notificationManager.sendVnfPkgDeletionNotification(new VnfPkgDeletionNotificationMessage(vnfPkgInfoResource.getId().toString(), notification.getVnfdId(), notification.getVnfdVersion(), project, notification.getOperationId(), ScopeType.SYNC, OperationStatus.FAILED, notification.getPluginId(), null));
                        List<String> siteOrManoIds = new ArrayList<>();
                        siteOrManoIds.add(notification.getPluginId());
                        notificationManager.sendVnfPkgOnBoardingNotification(new VnfPkgOnBoardingNotificationMessage(vnfPkgInfoResource.getId().toString(), vnfPkgInfoResource.getVnfdId().toString(), vnfPkgInfoResource.getVnfdVersion(), project, UUID.randomUUID(), ScopeType.LOCAL, OperationStatus.SENT, siteOrManoIds, new KeyValuePair(rootDir + ConfigurationParameters.storageVnfpkgsSubfolder + "/" + project + "/" + vnfPkgInfoResource.getVnfdId() + "/" + vnfPkgInfoResource.getVnfdVersion(), PathType.LOCAL.toString())));
                    } catch (FailedOperationException e) {
                        log.error(e.getMessage());
                        log.debug(null, e);
                    }
                }
            }else{
                try {
                    notificationManager.sendVnfPkgDeletionNotification(new VnfPkgDeletionNotificationMessage(null, notification.getVnfdId(), notification.getVnfdVersion(), project, notification.getOperationId(), ScopeType.SYNC, OperationStatus.FAILED, notification.getPluginId(), null));
                }catch(FailedOperationException e1){
                    log.error(e1.getMessage());
                    log.error(e1.getMessage());
                }
            }
        }
    }

    public void runtimeVnfChange(VnfPkgChangeNotificationMessage notification){
        List<String> projects = new ArrayList<>();
        if(notification.getProject() == null || notification.getProject().equals("all")){
            List<ProjectResource> projectResourceList = projectRepository.findAll();
            for(ProjectResource projectResource : projectResourceList)
                projects.add(projectResource.getProjectId());
        }else
            projects.add(notification.getProject());

        List<String> onbordingProjects = new ArrayList<>();
        for(String project : projects) {
            Optional<VnfPkgInfoResource> optionalVnfPkgInfoResource = vnfPkgInfoRepository.findByVnfdIdAndVnfdVersionAndProjectId(UUID.fromString(notification.getVnfdId()), notification.getVnfdVersion(), project);
            if (optionalVnfPkgInfoResource.isPresent()) {
                VnfPkgInfoResource vnfPkgInfoResource = optionalVnfPkgInfoResource.get();
                UUID vnfPkgInfoId = vnfPkgInfoResource.getId();
                if (vnfPkgInfoResource.isRetrievedFromMANO()) {
                    try {
                        log.info("Project {} - Updating VNFD with ID {} and version {} retrieved from MANO with ID {}", project, notification.getVnfdId(), notification.getVnfdVersion(), notification.getPluginId());
                        File vnfd;
                        if (notification.getPackagePath().getValue().equals(PathType.LOCAL.toString())) {
                            vnfd = new File(notification.getPackagePath().getKey());
                        } else {
                            throw new MethodNotImplementedException("Path Type not currently supported");
                        }
                        MultipartFile multipartFile = Utilities.createMultiPartFromFile(vnfd);
                        updateVnfPkg(vnfPkgInfoId.toString(), multipartFile, ContentType.ZIP, true, notification.getPluginId(), project);
                        updateVnfPkgInfoOperationStatus(vnfPkgInfoId.toString(), notification.getPluginId(), OperationStatus.SUCCESSFULLY_DONE, CatalogueMessageType.VNFPKG_CHANGE_NOTIFICATION);
                        optionalVnfPkgInfoResource = vnfPkgInfoRepository.findById(vnfPkgInfoId);
                        if(optionalVnfPkgInfoResource.isPresent()){
                            vnfPkgInfoResource = optionalVnfPkgInfoResource.get();
                            notificationManager.sendVnfPkgChangeNotification(new VnfPkgChangeNotificationMessage(vnfPkgInfoId.toString(), vnfPkgInfoResource.getVnfdId().toString(), vnfPkgInfoResource.getVnfdVersion(), project, notification.getOperationId(), ScopeType.SYNC, OperationStatus.SUCCESSFULLY_DONE, notification.getPluginId(), null, null));
                        }else
                            notificationManager.sendVnfPkgChangeNotification(new VnfPkgChangeNotificationMessage(vnfPkgInfoId.toString(), notification.getVnfdId(), notification.getVnfdVersion(), project, notification.getOperationId(), ScopeType.SYNC, OperationStatus.FAILED, notification.getPluginId(), null, null));
                    }catch (Exception e){
                        log.error("Project {} - Failed to update VNFD with ID {} and version {}", project, vnfPkgInfoResource.getVnfdId(), vnfPkgInfoResource.getVnfdVersion());
                        try {
                            updateVnfPkgInfoOperationStatus(vnfPkgInfoId.toString(), notification.getPluginId(), OperationStatus.FAILED, CatalogueMessageType.VNFPKG_CHANGE_NOTIFICATION);
                            notificationManager.sendVnfPkgChangeNotification(new VnfPkgChangeNotificationMessage(vnfPkgInfoId.toString(), notification.getVnfdId(), notification.getVnfdVersion(), project, notification.getOperationId(), ScopeType.SYNC, OperationStatus.FAILED, notification.getPluginId(), null, null));
                        } catch (Exception e1) {
                            log.error(e1.getMessage());
                            log.debug(null, e1);
                        }
                    }
                } else {
                    log.info("Project {} - Cannot update VNFD with ID {} and version {}, it is not retrieved from MANO", project, vnfPkgInfoResource.getVnfdId(), vnfPkgInfoResource.getVnfdVersion());
                    try {
                        updateVnfPkgInfoOperationStatus(vnfPkgInfoResource.getId().toString(), notification.getPluginId(), OperationStatus.FAILED, CatalogueMessageType.VNFPKG_CHANGE_NOTIFICATION);
                        notificationManager.sendVnfPkgChangeNotification(new VnfPkgChangeNotificationMessage(vnfPkgInfoResource.getId().toString(), notification.getVnfdId(), notification.getVnfdVersion(), project, notification.getOperationId(), ScopeType.SYNC, OperationStatus.FAILED, notification.getPluginId(), null, null));
                        log.debug("Project {} - Sending delete request for VNFD with ID {} and version {}", project, vnfPkgInfoResource.getVnfdId(), vnfPkgInfoResource.getVnfdVersion());
                        List<String> siteOrManoIds = new ArrayList<>();
                        siteOrManoIds.add(notification.getPluginId());
                        notificationManager.sendVnfPkgDeletionNotification(new VnfPkgDeletionNotificationMessage(vnfPkgInfoResource.getId().toString(), vnfPkgInfoResource.getVnfdId().toString(), vnfPkgInfoResource.getVnfdVersion(), project, UUID.randomUUID(), ScopeType.LOCAL, OperationStatus.SENT, siteOrManoIds));
                        onbordingProjects.add(project);
                    } catch (Exception e1) {
                        log.error(e1.getMessage());
                        log.debug(null, e1);
                    }
                }
            }else{
                try {
                    notificationManager.sendVnfPkgChangeNotification(new VnfPkgChangeNotificationMessage(null, notification.getVnfdId(), notification.getVnfdVersion(), project, notification.getOperationId(), ScopeType.SYNC, OperationStatus.FAILED, notification.getPluginId(), null, null));
                }catch(FailedOperationException e1){
                    log.error(e1.getMessage());
                    log.error(e1.getMessage());
                }
            }
        }

        for(String project : onbordingProjects) {
            try {
                Optional<VnfPkgInfoResource> optionalVnfPkgInfoResource = vnfPkgInfoRepository.findByVnfdIdAndVnfdVersionAndProjectId(UUID.fromString(notification.getVnfdId()), notification.getVnfdVersion(), project);
                if(optionalVnfPkgInfoResource.isPresent()) {
                    VnfPkgInfoResource vnfPkgInfoResource = optionalVnfPkgInfoResource.get();
                    log.debug("Project {} - Sending on-boarding request for VNFD with ID {} and version {}", project, vnfPkgInfoResource.getVnfdId(), vnfPkgInfoResource.getVnfdVersion());
                    List<String> siteOrManoIds = new ArrayList<>();
                    siteOrManoIds.add(notification.getPluginId());
                    notificationManager.sendVnfPkgOnBoardingNotification(new VnfPkgOnBoardingNotificationMessage(vnfPkgInfoResource.getId().toString() + "_update", vnfPkgInfoResource.getVnfdId().toString(), vnfPkgInfoResource.getVnfdVersion(), project, UUID.randomUUID(), ScopeType.LOCAL, OperationStatus.SENT, siteOrManoIds, new KeyValuePair(rootDir + ConfigurationParameters.storageVnfpkgsSubfolder + "/" + project + "/" + vnfPkgInfoResource.getVnfdId() + "/" + vnfPkgInfoResource.getVnfdVersion(), PathType.LOCAL.toString())));
                }
            }catch (Exception e1) {
                log.error(e1.getMessage());
                log.debug(null, e1);
            }
        }
    }

    protected void updateOperationInfoInConsumersMap(UUID operationId, OperationStatus opStatus, String manoId,
                                                     String nsdInfoId, CatalogueMessageType messageType) {
        Map<String, NotificationResource> manoIdToOpAck = new HashMap<>();
        if (operationIdToConsumersAck.containsKey(operationId.toString())) {
            manoIdToOpAck = operationIdToConsumersAck.get(operationId.toString());
        }
        NotificationResource notificationResource = new NotificationResource(nsdInfoId, messageType, opStatus, PluginType.MANO);
        manoIdToOpAck.put(manoId, notificationResource);

        operationIdToConsumersAck.put(operationId.toString(), manoIdToOpAck);
    }

    private UUID insertOperationInfoInConsumersMap(String vnfPkgInfoId, CatalogueMessageType messageType,
                                                   OperationStatus opStatus, List<String> siteOrManoIds) {
        UUID operationId = UUID.randomUUID();
        log.debug("Updating consumers internal mapping for operationId {}", operationId);
        List<MANO> manos = MANORepository.findAll();
        if(siteOrManoIds != null)
            manos.removeIf(mano -> !siteOrManoIds.contains(mano.getManoId()) && !siteOrManoIds.contains(mano.getManoSite()));
        Map<String, NotificationResource> pluginToOperationState = new HashMap<>();
        for (MANO mano : manos) {
            pluginToOperationState.put(mano.getManoId(), new NotificationResource(vnfPkgInfoId, messageType, opStatus, PluginType.MANO));
        }
        operationIdToConsumersAck.put(operationId.toString(), pluginToOperationState);
        return operationId;
    }

    @Override
    public VnfPkgInfo createVnfPkgInfo(CreateVnfPkgInfoRequest request, String project, boolean isInternalRequest) throws FailedOperationException, MalformattedElementException, MethodNotImplementedException, NotPermittedOperationException, NotAuthorizedOperationException {
        log.debug("Processing request to create a new VNF Pkg info");
        KeyValuePairs kvp = request.getUserDefinedData();
        Map<String, String> targetKvp = new HashMap<>();
        if (kvp != null) {
            for (Map.Entry<String, String> e : kvp.entrySet()) {
                targetKvp.put(e.getKey(), e.getValue());
            }
        }
        VnfPkgInfoResource vnfPkgInfoResource = new VnfPkgInfoResource(targetKvp);
        if (project != null) {
            try {
                Optional<ProjectResource> projectOptional = projectRepository.findByProjectId(project);
                if (!projectOptional.isPresent()) {
                    log.error("Project with id " + project + " does not exist");
                    throw new FailedOperationException("Project with id " + project + " does not exist");
                }
                if (isInternalRequest || !keycloakEnabled || it.nextworks.nfvmano.catalogue.engine.Utilities.checkUserProjects(userRepository, AuthUtilities.getUserNameFromJWT(), project)) {
                    vnfPkgInfoResource.setProjectId(project);
                } else {
                    throw new NotAuthorizedOperationException("Current user cannot access to the specified project with id" + project);
                }
            } catch (NotExistingEntityException e) {
                throw new NotAuthorizedOperationException(e.getMessage());
            }
        }
        if(targetKvp.containsKey("isRetrievedFromMANO") && targetKvp.get("isRetrievedFromMANO").equals("yes"))
            vnfPkgInfoResource.setRetrievedFromMANO(true);
        else
            vnfPkgInfoResource.setRetrievedFromMANO(false);
        vnfPkgInfoRepository.saveAndFlush(vnfPkgInfoResource);
        UUID vnfPkgInfoId = vnfPkgInfoResource.getId();
        log.debug("Created VNF Pkg info with ID " + vnfPkgInfoId);
        VnfPkgInfo vnfPkgInfo = buildVnfPkgInfo(vnfPkgInfoResource);
        log.debug("Translated internal VNF Pkg info resource into VNF Pkg info");
        return vnfPkgInfo;
    }

    @Override
    public void deleteVnfPkgInfo(String vnfPkgInfoId, String project, boolean isInternalRequest) throws FailedOperationException, NotExistingEntityException, MalformattedElementException, NotPermittedOperationException, MethodNotImplementedException, NotAuthorizedOperationException {
        log.debug("Processing request to delete an VNF Pkg info");

        if (vnfPkgInfoId == null)
            throw new MalformattedElementException("Invalid VNF Pkg info ID");

        try {
            UUID id = UUID.fromString(vnfPkgInfoId);

            Optional<VnfPkgInfoResource> optional = vnfPkgInfoRepository.findById(id);

            if (optional.isPresent()) {
                log.debug("Found VNF Pkg info resource with id: " + vnfPkgInfoId);

                VnfPkgInfoResource vnfPkgInfoResource = optional.get();
                if (project != null) {
                    Optional<ProjectResource> projectOptional = projectRepository.findByProjectId(project);
                    if (!projectOptional.isPresent()) {
                        log.error("Project with id " + project + " does not exist");
                        throw new FailedOperationException("Project with id " + project + " does not exist");
                    }
                }
                if (project != null && !vnfPkgInfoResource.getProjectId().equals(project)) {
                    throw new NotAuthorizedOperationException("Specified project differs from VNF Pkg info project");
                } else {
                    try {
                        if (!isInternalRequest && keycloakEnabled && !it.nextworks.nfvmano.catalogue.engine.Utilities.checkUserProjects(userRepository, AuthUtilities.getUserNameFromJWT(), project)) {
                            throw new NotAuthorizedOperationException("Current user cannot access to the specified project with id" + project);
                        }
                    } catch (NotExistingEntityException e) {
                        throw new NotAuthorizedOperationException(e.getMessage());
                    }
                }

                vnfPkgInfoResource.isDeletable();

                if(!isInternalRequest && !vnfPkgInfoResource.getOnboardingState().equals(PackageOnboardingStateType.FAILED) && vnfPkgInfoResource.isRetrievedFromMANO()){
                    throw new FailedOperationException("Cannot remove VNF Pkg info, it has been retrieved from MANO");
                }

                if(!isInternalRequest && !vnfPkgInfoResource.getOnboardingState().equals(PackageOnboardingStateType.FAILED) && vnfPkgInfoResource.getUserDefinedData().containsKey("isGeneratedFromAppD") && vnfPkgInfoResource.getUserDefinedData().get("isGeneratedFromAppD").equals("yes")){
                    throw new FailedOperationException("Cannot remove VNF Pkg info, it has been generated from AppD. Please perform operations directly on the AppD");
                }

                log.debug("The VNF Pkg info can be removed");
                if (vnfPkgInfoResource.getOnboardingState() == PackageOnboardingStateType.ONBOARDED
                        || vnfPkgInfoResource.getOnboardingState() == PackageOnboardingStateType.LOCAL_ONBOARDED
                        || vnfPkgInfoResource.getOnboardingState() == PackageOnboardingStateType.PROCESSING) {
                    log.debug("The VNF Pkg info is associated to an onboarded VNF Pkg. Removing it");
                    UUID vnfdId = vnfPkgInfoResource.getVnfdId();

                    try {
                        storageService.deleteVnfPkg(project, vnfPkgInfoResource.getVnfdId().toString(), vnfPkgInfoResource.getVnfdVersion());
                    } catch (Exception e) {
                        log.error("Unable to delete VNF Pkg with vnfdId {} from filesystem", vnfPkgInfoResource.getVnfdId().toString());
                        log.error("Details: ", e);
                    }

                    if(!vnfPkgInfoResource.isRetrievedFromMANO()) {
                        UUID operationId = insertOperationInfoInConsumersMap(vnfPkgInfoId,
                                CatalogueMessageType.NSD_DELETION_NOTIFICATION, OperationStatus.SENT, null);
                        VnfPkgDeletionNotificationMessage msg = new VnfPkgDeletionNotificationMessage(vnfPkgInfoId, vnfdId.toString(), vnfPkgInfoResource.getVnfdVersion(), project, operationId, ScopeType.LOCAL, OperationStatus.SENT, null);
                        msg.setVnfName(vnfPkgInfoResource.getVnfProductName());
                        notificationManager.sendVnfPkgDeletionNotification(msg);
                    }

                    log.debug("VNF Pkg {} locally removed. Sending vnfPkgDeletionNotificationMessage to bus", vnfdId);
                }

                vnfPkgInfoRepository.deleteById(id);
                log.debug("Deleted VNF Pkg info resource with id: " + vnfPkgInfoId);
            } else {
                log.debug("VNF Pkg info resource with id " + vnfPkgInfoId + "not found");
                throw new NotExistingEntityException("VNF Pkg info resource with id " + vnfPkgInfoId + "not found");
            }
        } catch (IllegalArgumentException e) {
            log.error("Wrong ID format: " + vnfPkgInfoId);
            throw new MalformattedElementException("Wrong ID format: " + vnfPkgInfoId);
        }
    }

    @Override
    public VnfPkgInfoModifications updateVnfPkgInfo(VnfPkgInfoModifications vnfPkgInfoModifications, String vnfPkgInfoId, String project, boolean isInternalRequest) throws FailedOperationException, NotExistingEntityException, MalformattedElementException, NotPermittedOperationException, NotAuthorizedOperationException {
        log.debug("Processing request to update VNF Pkg info: " + vnfPkgInfoId);
        if (project != null) {
            Optional<ProjectResource> projectOptional = projectRepository.findByProjectId(project);
            if (!projectOptional.isPresent()) {
                log.error("Project with id " + project + " does not exist");
                throw new FailedOperationException("Project with id " + project + " does not exist");
            }
        }
        VnfPkgInfoResource vnfPkgInfoResource = getVnfPkgInfoResource(vnfPkgInfoId);

        if (project != null && !vnfPkgInfoResource.getProjectId().equals(project)) {
            throw new NotAuthorizedOperationException("Specified project differs from VNF Pkg info project");
        } else {
            try {
                if (!isInternalRequest && keycloakEnabled && !it.nextworks.nfvmano.catalogue.engine.Utilities.checkUserProjects(userRepository, AuthUtilities.getUserNameFromJWT(), project)) {
                    throw new NotAuthorizedOperationException("Current user cannot access to the specified project with id" + project);
                }
            } catch (NotExistingEntityException e) {
                throw new NotAuthorizedOperationException(e.getMessage());
            }
        }

        if(!isInternalRequest && vnfPkgInfoResource.isRetrievedFromMANO()){
            throw new FailedOperationException("Cannot update VNF Pkg info, it has been retrieved from MANO");
        }

        if(!isInternalRequest && vnfPkgInfoResource.getUserDefinedData().containsKey("isGeneratedFromAppD") && vnfPkgInfoResource.getUserDefinedData().get("isGeneratedFromAppD").equals("yes")){
            throw new FailedOperationException("Cannot update VNF Pkg info, it has been generated from AppD. Please perform operations directly on the AppD");
        }

        //TODO add possibility to update onboarding on manos
        if (vnfPkgInfoResource.getOnboardingState() == PackageOnboardingStateType.ONBOARDED
                || vnfPkgInfoResource.getOnboardingState() == PackageOnboardingStateType.LOCAL_ONBOARDED) {
            if (vnfPkgInfoModifications.getOperationalState() != null) {
                if (vnfPkgInfoResource.getOperationalState() == vnfPkgInfoModifications.getOperationalState()) {
                    log.error("VNF Pkg operational state already "
                            + vnfPkgInfoResource.getOperationalState() + ". Cannot update VNF Pkg info");
                    throw new NotPermittedOperationException("VNF Pkg operational state already "
                            + vnfPkgInfoResource.getOperationalState() + ". Cannot update VNF Pkg info");
                } else {
                    vnfPkgInfoResource.setOperationalState(vnfPkgInfoModifications.getOperationalState());
                }
            }
            if (vnfPkgInfoModifications.getUserDefinedData() != null) {
                vnfPkgInfoResource.setUserDefinedData(vnfPkgInfoModifications.getUserDefinedData());
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

            try {
                String json = mapper.writeValueAsString(vnfPkgInfoModifications);
                log.debug("Updating vnfPkgInfoResource with vnfPkgInfoModifications: " + json);
            } catch (JsonProcessingException e) {
                log.error("Unable to parse received vnfPkgInfoModifications: " + e.getMessage());
            }
            vnfPkgInfoRepository.saveAndFlush(vnfPkgInfoResource);
            log.debug("VnfPkgInfoResource successfully updated");
        } else {
            log.error("VNF Pkg onboarding state not ONBOARDED. Cannot update VNF Pkg info");
            throw new NotPermittedOperationException("VNF Pkg onboarding state not ONBOARDED/LOCAL_ONBOARDED. Cannot update VNF Pkg info");
        }
        return vnfPkgInfoModifications;
    }

    @Override
    public Object getVnfd(String vnfPkgInfoId, boolean isInternalRequest, String project) throws FailedOperationException, NotExistingEntityException, MalformattedElementException, NotPermittedOperationException, NotAuthorizedOperationException {
        log.debug("Processing request to retrieve a VNFD content for VNF Pkg info " + vnfPkgInfoId);
        if (project != null) {
            Optional<ProjectResource> projectOptional = projectRepository.findByProjectId(project);
            if (!projectOptional.isPresent()) {
                log.error("Project with id " + project + " does not exist");
                throw new FailedOperationException("Project with id " + project + " does not exist");
            }
        }
        VnfPkgInfoResource vnfPkgInfoResource = getVnfPkgInfoResource(vnfPkgInfoId);

        if (!isInternalRequest && project != null && !vnfPkgInfoResource.getProjectId().equals(project)) {
            throw new NotAuthorizedOperationException("Specified project differs from VNF Pkg info project");
        } else {
            try {
                if (!isInternalRequest && keycloakEnabled && !it.nextworks.nfvmano.catalogue.engine.Utilities.checkUserProjects(userRepository, AuthUtilities.getUserNameFromJWT(), project)) {
                    throw new NotAuthorizedOperationException("Current user cannot access to the specified project with id" + project);
                }
            } catch (NotExistingEntityException e) {
                throw new NotAuthorizedOperationException(e.getMessage());
            }
        }

        if ((!isInternalRequest) && (vnfPkgInfoResource.getOnboardingState() != PackageOnboardingStateType.ONBOARDED
                && vnfPkgInfoResource.getOnboardingState() != PackageOnboardingStateType.LOCAL_ONBOARDED)) {
            log.error("VNF Pkg info " + vnfPkgInfoId + " does not have an onboarded VNFD yet");
            throw new NotPermittedOperationException("VNF Pkg info " + vnfPkgInfoId + " does not have an onboarded VNFD yet");
        }
        UUID vnfdId = vnfPkgInfoResource.getVnfdId();
        log.debug("Internal VNFD ID: " + vnfdId);


        String vnfdFilename = vnfPkgInfoResource.getVnfdFilename();
        if (vnfdFilename == null) {
            log.error("Found zero file for VNFD in YAML/JSON format");
            throw new FailedOperationException("Found zero file for VNFD in YAML format");
        }

        return storageService.loadFileAsResource(project, vnfPkgInfoResource.getVnfdId().toString(), vnfPkgInfoResource.getVnfdVersion(), vnfdFilename, DescriptorType.VNFD);
    }

    @Override
    public Object getVnfPkg(String vnfPkgInfoId, boolean isInternalRequest, String project) throws FailedOperationException, NotExistingEntityException, MalformattedElementException, NotPermittedOperationException, MethodNotImplementedException, NotAuthorizedOperationException {
        log.debug("Processing request to retrieve a VNF Pkg content for VNF Pkg info " + vnfPkgInfoId);
        if (project != null) {
            Optional<ProjectResource> projectOptional = projectRepository.findByProjectId(project);
            if (!projectOptional.isPresent()) {
                log.error("Project with id " + project + " does not exist");
                throw new FailedOperationException("Project with id " + project + " does not exist");
            }
        }
        VnfPkgInfoResource vnfPkgInfoResource = getVnfPkgInfoResource(vnfPkgInfoId);

        if (project != null && !vnfPkgInfoResource.getProjectId().equals(project)) {
            throw new NotAuthorizedOperationException("Specified project differs from VNF Pkg info project");
        } else {
            try {
                if (!isInternalRequest && keycloakEnabled && !it.nextworks.nfvmano.catalogue.engine.Utilities.checkUserProjects(userRepository, AuthUtilities.getUserNameFromJWT(), project)) {
                    throw new NotAuthorizedOperationException("Current user cannot access to the specified project with id" + project);
                }
            } catch (NotExistingEntityException e) {
                throw new NotAuthorizedOperationException(e.getMessage());
            }
        }

        if ((!isInternalRequest) && (vnfPkgInfoResource.getOnboardingState() != PackageOnboardingStateType.ONBOARDED
                && vnfPkgInfoResource.getOnboardingState() != PackageOnboardingStateType.LOCAL_ONBOARDED)) {
            log.error("VNF Pkg info " + vnfPkgInfoId + " does not have an onboarded VNF Pkg yet");
            throw new NotPermittedOperationException("VNF Pkg info " + vnfPkgInfoId + " does not have an onboarded VNF Pkg yet");
        }
        UUID vnfdId = vnfPkgInfoResource.getVnfdId();
        log.debug("Internal VNFD ID: " + vnfdId);

        ContentType ct = vnfPkgInfoResource.getContentType();
        switch (ct) {
            case ZIP: {
                // try {
                String vnfPkgFilename = vnfPkgInfoResource.getVnfPkgFilename();
                if (vnfPkgFilename == null) {
                    log.error("Found zero file for VNF Pkg in ZIP format");
                    throw new FailedOperationException("Found zero file for VNF Pkg in ZIP format");
                }

                return storageService.loadFileAsResource(project, vnfPkgInfoResource.getVnfdId().toString(), vnfPkgInfoResource.getVnfdVersion(), vnfPkgFilename, DescriptorType.VNFD);
            }

            default: {
                log.error("Content type not yet supported");
                throw new MethodNotImplementedException("Content type not yet supported");
            }
        }
    }

    @Override
    public VnfPkgInfo getVnfPkgInfo(String vnfPkgInfoId, String project) throws FailedOperationException, NotPermittedOperationException, NotExistingEntityException, MalformattedElementException, MethodNotImplementedException, NotAuthorizedOperationException {
        log.debug("Processing request to get a VNF Pkg info");
        if (project != null) {
            Optional<ProjectResource> projectOptional = projectRepository.findByProjectId(project);
            if (!projectOptional.isPresent()) {
                log.error("Project with id " + project + " does not exist");
                throw new FailedOperationException("Project with id " + project + " does not exist");
            }
        }
        VnfPkgInfoResource vnfPkgInfoResource = getVnfPkgInfoResource(vnfPkgInfoId);

        if (project != null && !vnfPkgInfoResource.getProjectId().equals(project)) {
            throw new NotAuthorizedOperationException("Current user cannot access to the specified project with id" + project);
        } else {
            try {
                if (keycloakEnabled && !it.nextworks.nfvmano.catalogue.engine.Utilities.checkUserProjects(userRepository, AuthUtilities.getUserNameFromJWT(), project)) {
                    throw new NotAuthorizedOperationException("Current user cannot access to the specified project");
                }
            } catch (NotExistingEntityException e) {
                throw new NotAuthorizedOperationException(e.getMessage());
            }
        }

        log.debug("Found VNF Pkg info resource with id: " + vnfPkgInfoId);
        VnfPkgInfo vnfPkgInfo = buildVnfPkgInfo(vnfPkgInfoResource);
        log.debug("Built VNF Pkg info with id: " + vnfPkgInfoId);
        return vnfPkgInfo;
    }

    @Override
    public List<VnfPkgInfo> getAllVnfPkgInfos(String project, String extraData, UUID vnfdId) throws FailedOperationException, MethodNotImplementedException, NotPermittedOperationException, NotAuthorizedOperationException {
        log.debug("Processing request to get all VNF Pkg infos");
        if (project != null && !project.equals("*")) {
            Optional<ProjectResource> projectOptional = projectRepository.findByProjectId(project);
            if (!projectOptional.isPresent()) {
                log.error("Project with id " + project + " does not exist");
                throw new FailedOperationException("Project with id " + project + " does not exist");
            }
        }
        try {
            if (project != null && !project.equals("*") && keycloakEnabled && !it.nextworks.nfvmano.catalogue.engine.Utilities.checkUserProjects(userRepository, AuthUtilities.getUserNameFromJWT(), project)) {
                throw new NotAuthorizedOperationException("Current user cannot access to the specified project with id" + project);
            }
        } catch (NotExistingEntityException e) {
            throw new NotAuthorizedOperationException(e.getMessage());
        }

        List<VnfPkgInfoResource> vnfPkgInfoResources;
        if(vnfdId == null)
            vnfPkgInfoResources = vnfPkgInfoRepository.findAll();
        else
            vnfPkgInfoResources = vnfPkgInfoRepository.findByVnfdId(vnfdId);

        List<VnfPkgInfo> vnfPkgInfos = new ArrayList<>();

        for (VnfPkgInfoResource vnfPkgInfoResource : vnfPkgInfoResources) {
            if (project != null && !project.equals("*") && !vnfPkgInfoResource.getProjectId().equals(project)) {
                continue;
            } else {
                VnfPkgInfo vnfPkgInfo = buildVnfPkgInfo(vnfPkgInfoResource);
                if(extraData != null && extraData.equals("manoInfoIds")){
                    Map<String, NotificationResource> onBoardingMap = vnfPkgInfoResource.getAcknowledgedOnboardOpConsumers();
                    List<MANOPlugin> manos = new ArrayList<>(pluginManger.manoDrivers.values());
                    for(MANOPlugin mano : manos){
                        if (onBoardingMap.containsKey(mano.getPluginId()) && onBoardingMap.get(mano.getPluginId()).getOpStatus().equals(OperationStatus.SUCCESSFULLY_DONE)) {
                            String manoInfoId = mano.getManoPkgInfoId(vnfPkgInfoResource.getId().toString());
                            if(manoInfoId != null)
                                vnfPkgInfo.getManoInfoIds().put(mano.getPluginId(), manoInfoId);
                        }
                    }
                }
                vnfPkgInfos.add(vnfPkgInfo);
                log.debug("Added VNF Pkg info " + vnfPkgInfoResource.getId());
            }
        }
        return vnfPkgInfos;
    }

    @Override
    public void uploadVnfPkg(String vnfPkgInfoId,
                             MultipartFile vnfPkg,
                             ContentType contentType,
                             boolean isInternalRequest,
                             String project)
            throws FailedOperationException, AlreadyExistingEntityException, NotExistingEntityException,
            MalformattedElementException, NotPermittedOperationException, NotAuthorizedOperationException {

        log.debug("Processing request to upload VNF Pkg content for VNFD info " + vnfPkgInfoId);

        if (project != null) {
            Optional<ProjectResource> projectOptional = projectRepository.findByProjectId(project);
            if (!projectOptional.isPresent()) {
                log.error("Project with id " + project + " does not exist");
                throw new FailedOperationException("Project with id " + project + " does not exist");
            }
        }
        VnfPkgInfoResource vnfPkgInfoResource = getVnfPkgInfoResource(vnfPkgInfoId);

        if (project != null && !vnfPkgInfoResource.getProjectId().equals(project)) {
            vnfPkgInfoResource.setOnboardingState(PackageOnboardingStateType.FAILED);
            vnfPkgInfoRepository.saveAndFlush(vnfPkgInfoResource);
            throw new NotAuthorizedOperationException("Specified project differs from VNF Pkg info project");
        } else {
            try {
                if (!vnfPkgInfoResource.isRetrievedFromMANO() && !isInternalRequest && keycloakEnabled && !it.nextworks.nfvmano.catalogue.engine.Utilities.checkUserProjects(userRepository, AuthUtilities.getUserNameFromJWT(), project)) {
                    vnfPkgInfoResource.setOnboardingState(PackageOnboardingStateType.FAILED);
                    vnfPkgInfoRepository.saveAndFlush(vnfPkgInfoResource);
                    throw new NotAuthorizedOperationException("Current user cannot access to the specified project with id" + project);
                }
            } catch (NotExistingEntityException e) {
                vnfPkgInfoResource.setOnboardingState(PackageOnboardingStateType.FAILED);
                vnfPkgInfoRepository.saveAndFlush(vnfPkgInfoResource);
                throw new NotAuthorizedOperationException(e.getMessage());
            }
        }

        if (vnfPkgInfoResource.getOnboardingState() != PackageOnboardingStateType.CREATED) {
            log.error("VNF Pkg info " + vnfPkgInfoId + " not in CREATED onboarding state");
            vnfPkgInfoResource.setOnboardingState(PackageOnboardingStateType.FAILED);
            vnfPkgInfoRepository.saveAndFlush(vnfPkgInfoResource);
            throw new NotPermittedOperationException("VNF Pkg info " + vnfPkgInfoId + " not in CREATED onboarding state");
        }

        if (contentType != ContentType.ZIP) {
            log.error("VNF Pkg upload request with wrong content-type");
            vnfPkgInfoResource.setOnboardingState(PackageOnboardingStateType.FAILED);
            vnfPkgInfoRepository.saveAndFlush(vnfPkgInfoResource);
            throw new MalformattedElementException("VNF Pkg " + vnfPkgInfoId + " upload request with wrong content-type");
        }

        it.nextworks.nfvmano.catalogue.engine.Utilities.checkZipArchive(vnfPkg);

        CSARInfo csarInfo;
        DataModelSpec dms;
        String vnfPkgFilename;

        DescriptorTemplate dt = null;
        Vnfd vnfd = null;

        String version;
        UUID vnfdId;

        try {
            csarInfo = archiveParser.archiveToCSARInfo(project, vnfPkg, DescriptorType.VNFD, true);
            dms = csarInfo.getDataModelSpec();

            if(dms == DataModelSpec.SOL001)
                dt = csarInfo.getMst();
            else
                vnfd = csarInfo.getVnfd();

            vnfPkgFilename = csarInfo.getPackageFilename();

            String vnfPkgId_string;
            if(dms == DataModelSpec.SOL001)
                vnfPkgId_string = dt.getMetadata().getDescriptorId();
            else
                vnfPkgId_string = vnfd.getId();

            if(dms == DataModelSpec.SOL001)
                version = dt.getMetadata().getVersion();
            else
                version = vnfd.getVersion();

            if (!Utilities.isUUID(vnfPkgId_string)) {
                throw new MalformattedElementException("VNFD id not in UUID format");
            }
            vnfdId = UUID.fromString(vnfPkgId_string);

            if(dms == DataModelSpec.SOL001) {
                VNFNode vnfNode = dt.getTopologyTemplate().getVNFNodes().values().iterator().next();//For the moment assume only one VNF node
                if (!version.equals(vnfNode.getProperties().getDescriptorVersion()) ||
                        !dt.getMetadata().getDescriptorId().equals(vnfNode.getProperties().getDescriptorId())) {
                    throw new MalformattedElementException("Descriptor ID and version specified into metadata are not aligned with those specified into VNFNode");
                }
            }

            if(dms == DataModelSpec.SOL001) {
                Map<String, VnfExtCpNode> vnfExtCpNodes = dt.getTopologyTemplate().getVnfExtCpNodes();
                if (vnfExtCpNodes.isEmpty())
                    throw new MalformattedElementException("No External Connection Points defined");
                List<String> mgmtCps = new ArrayList<>();
                for (Map.Entry<String, VnfExtCpNode> cpNode : vnfExtCpNodes.entrySet()) {
                    if (cpNode.getValue().getProperties().getVirtualNetworkInterfaceRequirements().isEmpty())
                        continue;
                    Map<String, String> interfaceRequirements = cpNode.getValue().getProperties().getVirtualNetworkInterfaceRequirements().get(0).getNetworkInterfaceRequirements();
                    if (interfaceRequirements.containsKey("isManagement") && interfaceRequirements.get("isManagement").equalsIgnoreCase("true"))
                        mgmtCps.add(cpNode.getKey());
                }
                if (mgmtCps.size() == 0)
                    throw new MalformattedElementException("Please define a management Connection Point");
                else if (mgmtCps.size() > 1)
                    throw new MalformattedElementException("Multiple management Connection Points are not allowed");
            } else {
                List<ExtCpd> extCpds = vnfd.getExtCpd();
                if(extCpds == null || extCpds.isEmpty())
                    throw new MalformattedElementException("No External Connection Points defined");

                String mgmtCp = vnfd.getMgmtCp();
                if(mgmtCp != null && !mgmtCp.isEmpty()) {
                    long count = extCpds.stream().filter(extCpd -> extCpd.getId().equals(mgmtCp)).count();
                    if(count == 0)
                        throw new MalformattedElementException("Please, specify an existent External Connection Point");
                    else if (count > 1)
                        throw new MalformattedElementException("Multiple External Connection Points with same ID are not allowed");
                } else {
                    List<String> mgmtCps = new ArrayList<>();
                    for(ExtCpd extCpd : extCpds) {
                        List<VirtualNetworkInterfaceRequirementSchema> virtualNetworkInterfaceRequirements =
                                extCpd.getVirtualNetworkInterfaceRequirementSchemas();
                        if(virtualNetworkInterfaceRequirements == null || virtualNetworkInterfaceRequirements.isEmpty())
                            continue;
                        for(VirtualNetworkInterfaceRequirementSchema virtualNetworkInterfaceRequirement : virtualNetworkInterfaceRequirements) {
                            List<NetworkInterfaceRequirementsSchema> networkInterfaceRequirements =
                                    virtualNetworkInterfaceRequirement.getNetworkInterfaceRequirements();
                            if(networkInterfaceRequirements == null || networkInterfaceRequirements.isEmpty())
                                continue;
                            mgmtCps = networkInterfaceRequirements
                                    .stream()
                                    .filter(nir -> nir.getKey().equals("isManagement") &&
                                            nir.getValue().equalsIgnoreCase("true"))
                                    .map(NetworkInterfaceRequirementsSchema::getValue)
                                    .collect(Collectors.toList());
                        }
                    }
                    if(mgmtCps.size() == 0)
                        throw new MalformattedElementException("Please define a management Connection Point");
                    else if (mgmtCps.size() > 1)
                        throw new MalformattedElementException("Multiple management Connection Points are not allowed");
                }
            }

            if(dms == DataModelSpec.SOL001) {
                if(vnfPkgInfoRepository.findByVnfdIdAndVnfdVersionAndProjectId(vnfdId, version, project).isPresent())
                    throw new AlreadyExistingEntityException("A VNFD with the same id and version already exists in the project");
            } else {
                if(vnfPkgInfoRepository.findByVnfdIdAndProjectId(vnfdId, project).isPresent())
                    throw new AlreadyExistingEntityException("A SOL006 VNFD with the same id already exists in the project");
            }

            vnfPkgInfoResource.setVnfdId(vnfdId);

            if(dms == DataModelSpec.SOL001)
                log.debug("VNFD in Pkg successfully parsed - its content is: \n"
                        + DescriptorsParser.descriptorTemplateToString(dt));
            else
                log.debug("VNFD in Pkg successfully parsed - its content is: \n" + vnfd.toString());

            vnfPkgInfoResource.setVnfdVersion(version);

            if(dms == DataModelSpec.SOL001)
                vnfPkgInfoResource.setVnfProvider(dt.getMetadata().getVendor());
            else
                vnfPkgInfoResource.setVnfProvider(vnfd.getProvider());

            //vnfPkgFilename = storageService.storeVnfPkg(vnfPkgInfoResource.getVnfdId().toString(), vnfPkgInfoResource.getVnfdVersion(), vnfPkg);

            log.debug("VNF Pkg file successfully stored: " + vnfPkgFilename);
        } catch (IOException e) {
            log.error("Error while parsing VNF Pkg: " + e.getMessage());
            vnfPkgInfoResource.setOnboardingState(PackageOnboardingStateType.FAILED);
            vnfPkgInfoRepository.saveAndFlush(vnfPkgInfoResource);
            throw new MalformattedElementException("Error while parsing VNF Pkg");
        } catch (MalformattedElementException e) {
            log.error("Error while parsing VNF Pkg: " + e.getMessage());
            vnfPkgInfoResource.setOnboardingState(PackageOnboardingStateType.FAILED);
            vnfPkgInfoRepository.saveAndFlush(vnfPkgInfoResource);
            throw new MalformattedElementException("Error while parsing VNF Pkg: " + e.getMessage());
        } catch (AlreadyExistingEntityException e) {
            vnfPkgInfoResource.setOnboardingState(PackageOnboardingStateType.FAILED);
            vnfPkgInfoRepository.saveAndFlush(vnfPkgInfoResource);
            throw new AlreadyExistingEntityException(e.getMessage());
        }

        if (vnfPkgFilename == null) {
            vnfPkgInfoResource.setOnboardingState(PackageOnboardingStateType.FAILED);
            vnfPkgInfoRepository.saveAndFlush(vnfPkgInfoResource);
            throw new FailedOperationException("Invalid internal structures");
        }

        log.debug("Updating VNF Pkg info");
        vnfPkgInfoResource.setOnboardingState(PackageOnboardingStateType.LOCAL_ONBOARDED);
        vnfPkgInfoResource.setOperationalState(PackageOperationalStateType.ENABLED);

        String vnfName = "";
        if(dms == DataModelSpec.SOL001) {
            Map<String, VNFNode> vnfNodes = dt.getTopologyTemplate().getVNFNodes();
            if (vnfNodes.size() == 1) {
                for (Map.Entry<String, VNFNode> vnfNode : vnfNodes.entrySet()) {
                    vnfName = vnfNode.getValue().getProperties().getProductName();
                }
            } else {
                Map<String, String> subMapsProperties = dt.getTopologyTemplate().getSubstituitionMappings().getProperties();
                for (Map.Entry<String, String> entry : subMapsProperties.entrySet()) {
                    if (entry.getKey().equalsIgnoreCase("productName")) {
                        vnfName = entry.getValue();
                    }
                }
            }
        } else
            vnfName = vnfd.getProductName();

        log.debug("VNFD name: " + vnfName);
        vnfPkgInfoResource.setVnfProductName(vnfName);
        vnfPkgInfoResource.setContentType(contentType);
        vnfPkgInfoResource.setVnfPkgFilename(vnfPkgFilename);
        vnfPkgInfoResource.setVnfdFilename(csarInfo.getDescriptorFilename());
        vnfPkgInfoResource.setMetaFilename(csarInfo.getMetaFilename());
        vnfPkgInfoResource.setManifestFilename(csarInfo.getMfFilename());

        if (isInternalRequest)
            vnfPkgInfoResource.setPublished(true);
        else
            vnfPkgInfoResource.setPublished(false);

        if(dms == DataModelSpec.SOL006)
            vnfPkgInfoResource.setDescription(vnfd.getDescription());

        // send notification over kafka bus
        if(!vnfPkgInfoResource.isRetrievedFromMANO()) {
            List<String> siteOrManoIds = new ArrayList<>();
            Map<String, String> userDefinedData = vnfPkgInfoResource.getUserDefinedData();
            if(userDefinedData == null || userDefinedData.size() == 0)
                siteOrManoIds.addAll(pluginManger.manoDrivers.keySet());
            else
                for(MANOPlugin mano : pluginManger.manoDrivers.values()) {
                    if ((userDefinedData.containsKey(mano.getPluginId()) && userDefinedData.get(mano.getPluginId()).equals("yes"))
                            || (userDefinedData.containsKey(mano.getMano().getManoSite()) && userDefinedData.get(mano.getMano().getManoSite()).equals("yes")))
                        siteOrManoIds.add(mano.getPluginId());
                }
            if(siteOrManoIds.size() == 0)
                siteOrManoIds.addAll(pluginManger.manoDrivers.keySet());

            for(MANOPlugin mano : pluginManger.manoDrivers.values())
                if(vnfPkgInfoResource.getUserDefinedData().remove(mano.getPluginId()) == null)
                    vnfPkgInfoResource.getUserDefinedData().remove(mano.getMano().getManoSite());

            UUID operationId = insertOperationInfoInConsumersMap(vnfPkgInfoId,
                    CatalogueMessageType.VNFPKG_ONBOARDING_NOTIFICATION, OperationStatus.SENT, siteOrManoIds);
            vnfPkgInfoResource.setAcknowledgedOnboardOpConsumers(operationIdToConsumersAck.get(operationId.toString()));
            VnfPkgOnBoardingNotificationMessage msg =
                    new VnfPkgOnBoardingNotificationMessage(vnfPkgInfoId, vnfdId.toString(), version, project,
                            operationId, ScopeType.LOCAL, OperationStatus.SENT, siteOrManoIds,
                            new KeyValuePair(rootDir + ConfigurationParameters.storageVnfpkgsSubfolder +
                                    "/" + project + "/" + vnfdId.toString() + "/" + vnfPkgInfoResource.getVnfdVersion(),
                                    PathType.LOCAL.toString()));
            notificationManager.sendVnfPkgOnBoardingNotification(msg);
        }

        vnfPkgInfoRepository.saveAndFlush(vnfPkgInfoResource);
        log.debug("VNF Pkg content uploaded and vnfPkgOnBoardingNotification delivered");
    }

    @Override
    public void updateVnfPkg(String vnfPkgInfoId,
                             MultipartFile vnfPkg,
                             ContentType contentType,
                             boolean isInternalRequest,
                             String originalPluginId,
                             String project)
            throws FailedOperationException, NotExistingEntityException, AlreadyExistingEntityException,
            MalformattedElementException, NotPermittedOperationException, NotAuthorizedOperationException {

        log.debug("Processing request to update VNF Package content for VNF Pkg info " + vnfPkgInfoId);

        if (project != null) {
            Optional<ProjectResource> projectOptional = projectRepository.findByProjectId(project);
            if (!projectOptional.isPresent()) {
                log.error("Project with id " + project + " does not exist");
                throw new FailedOperationException("Project with id " + project + " does not exist");
            }
        }
        VnfPkgInfoResource vnfPkgInfoResource = getVnfPkgInfoResource(vnfPkgInfoId);

        if (project != null && !vnfPkgInfoResource.getProjectId().equals(project)) {
            throw new NotAuthorizedOperationException("Specified project differs from VNF Pkg info project");
        } else {
            try {
                if (!vnfPkgInfoResource.isRetrievedFromMANO() && !isInternalRequest && keycloakEnabled && !checkUserProjects(userRepository, AuthUtilities.getUserNameFromJWT(), project))
                    throw new NotAuthorizedOperationException("Current user cannot access to the specified project with id" + project);
            } catch (NotExistingEntityException e) {
                throw new NotAuthorizedOperationException(e.getMessage());
            }
        }

        if (vnfPkgInfoResource.getOnboardingState().equals(PackageOnboardingStateType.CREATED)) {
            log.error("VNF Pkg info " + vnfPkgInfoId + " in CREATED onboarding state. Please use the PUT method to upload the content");
            throw new NotPermittedOperationException("VNF Pkg info " + vnfPkgInfoId + " in CREATED onboarding state. Please use the PUT method to upload the content");
        }else if(!vnfPkgInfoResource.getOnboardingState().equals(PackageOnboardingStateType.LOCAL_ONBOARDED) && !vnfPkgInfoResource.getOnboardingState().equals(PackageOnboardingStateType.ONBOARDED)){
            log.error("VNF Pkg info " + vnfPkgInfoId + " not in either ONBOARDED or LOCAL_ONBOARDED onboarding state.");
            throw new NotPermittedOperationException("VNF Pkg info " + vnfPkgInfoId + " not in either ONBOARDED or LOCAL_ONBOARDED onboarding state.");
        }

        if (vnfPkgInfoResource.getUsageState() != PackageUsageStateType.NOT_IN_USE)
            throw new NotPermittedOperationException("VNF Pkg info " + vnfPkgInfoId + " cannot be updated because IN USE");
        if (!vnfPkgInfoResource.getParentNsds().isEmpty())
            throw new NotPermittedOperationException("VNF Pkg info " + vnfPkgInfoId + " cannot be updated because there is at least one NS referencing it");

        if (contentType != ContentType.ZIP) {
            log.error("VNF Pkg upload request with wrong content-type");
            vnfPkgInfoResource.setOnboardingState(PackageOnboardingStateType.FAILED);
            vnfPkgInfoRepository.saveAndFlush(vnfPkgInfoResource);
            throw new MalformattedElementException("VNF Pkg " + vnfPkgInfoId + " upload request with wrong content-type");
        }

        if(!isInternalRequest && vnfPkgInfoResource.isRetrievedFromMANO()){
            throw new FailedOperationException("Cannot update VNF Pkg info, it has been retrieved from MANO");
        }

        if(!isInternalRequest && vnfPkgInfoResource.getUserDefinedData().containsKey("isGeneratedFromAppD") && vnfPkgInfoResource.getUserDefinedData().get("isGeneratedFromAppD").equals("yes")){
            throw new FailedOperationException("Cannot update VNF Pkg info, it has been generated from AppD. Please perform operations directly on the AppD");
        }

        it.nextworks.nfvmano.catalogue.engine.Utilities.checkZipArchive(vnfPkg);

        CSARInfo csarInfo;
        DataModelSpec dms;

        DescriptorTemplate dt = null;
        Vnfd vnfd = null;

        UUID oldVnfdId;
        UUID vnfdId;
        String oldVnfdVersion;
        String version;

        String vnfPkgFilename;

        try {
            csarInfo = archiveParser.archiveToCSARInfo(project, vnfPkg, DescriptorType.VNFD, false);
            dms = csarInfo.getDataModelSpec();

            if(dms == DataModelSpec.SOL001)
                dt = csarInfo.getMst();
            else
                vnfd = csarInfo.getVnfd();

            String vnfPkgId_string;
            if(dms == DataModelSpec.SOL001)
                vnfPkgId_string = dt.getMetadata().getDescriptorId();
            else
                vnfPkgId_string = vnfd.getId();

            if(dms == DataModelSpec.SOL001)
                version = dt.getMetadata().getVersion();
            else
                version = vnfd.getVersion();

            if (!Utilities.isUUID(vnfPkgId_string)) {
                throw new MalformattedElementException("VNFD id not in UUID format");
            }
            vnfdId = UUID.fromString(vnfPkgId_string);

            if(dms == DataModelSpec.SOL001) {
                VNFNode vnfNode = dt.getTopologyTemplate().getVNFNodes().values().iterator().next();//For the moment assume only one VNF node
                if (!version.equals(vnfNode.getProperties().getDescriptorVersion()) ||
                        !dt.getMetadata().getDescriptorId().equals(vnfNode.getProperties().getDescriptorId()))
                    throw new MalformattedElementException("Descriptor ID and version specified into metadata are not aligned with those specified into VNFNode");
            }

            if(dms == DataModelSpec.SOL001) {
                Map<String, VnfExtCpNode> vnfExtCpNodes = dt.getTopologyTemplate().getVnfExtCpNodes();
                if (vnfExtCpNodes.isEmpty())
                    throw new MalformattedElementException("No External Connection Points defined");
                List<String> mgmtCps = new ArrayList<>();
                for (Map.Entry<String, VnfExtCpNode> cpNode : vnfExtCpNodes.entrySet()) {
                    if (cpNode.getValue().getProperties().getVirtualNetworkInterfaceRequirements().isEmpty())
                        continue;
                    Map<String, String> interfaceRequirements = cpNode.getValue().getProperties().getVirtualNetworkInterfaceRequirements().get(0).getNetworkInterfaceRequirements();
                    if (interfaceRequirements.containsKey("isManagement") && interfaceRequirements.get("isManagement").equalsIgnoreCase("true"))
                        mgmtCps.add(cpNode.getKey());
                }
                if (mgmtCps.size() == 0)
                    throw new MalformattedElementException("Please define a management Connection Point");
                else if (mgmtCps.size() > 1)
                    throw new MalformattedElementException("Multiple management Connection Points are not allowed");
            } else {
                List<ExtCpd> extCpds = vnfd.getExtCpd();
                if(extCpds == null || extCpds.isEmpty())
                    throw new MalformattedElementException("No External Connection Points defined");

                String mgmtCp = vnfd.getMgmtCp();
                if(mgmtCp != null && !mgmtCp.isEmpty()) {
                    long count = extCpds.stream().filter(extCpd -> extCpd.getId().equals(mgmtCp)).count();
                    if(count == 0)
                        throw new MalformattedElementException("Please, specify an existent External Connection Point");
                    else if (count > 1)
                        throw new MalformattedElementException("Multiple External Connection Points with same ID are not allowed");
                } else {
                    List<String> mgmtCps = new ArrayList<>();
                    for(ExtCpd extCpd : extCpds) {
                        List<VirtualNetworkInterfaceRequirementSchema> virtualNetworkInterfaceRequirements =
                                extCpd.getVirtualNetworkInterfaceRequirementSchemas();
                        if(virtualNetworkInterfaceRequirements == null || virtualNetworkInterfaceRequirements.isEmpty())
                            continue;
                        for(VirtualNetworkInterfaceRequirementSchema virtualNetworkInterfaceRequirement : virtualNetworkInterfaceRequirements) {
                            List<NetworkInterfaceRequirementsSchema> networkInterfaceRequirements =
                                    virtualNetworkInterfaceRequirement.getNetworkInterfaceRequirements();
                            if(networkInterfaceRequirements == null || networkInterfaceRequirements.isEmpty())
                                continue;
                            mgmtCps = networkInterfaceRequirements
                                    .stream()
                                    .filter(nir -> nir.getKey().equals("isManagement") &&
                                            nir.getValue().equalsIgnoreCase("true"))
                                    .map(NetworkInterfaceRequirementsSchema::getValue)
                                    .collect(Collectors.toList());
                        }
                    }
                    if(mgmtCps.size() == 0)
                        throw new MalformattedElementException("Please define a management Connection Point");
                    else if (mgmtCps.size() > 1)
                        throw new MalformattedElementException("Multiple management Connection Points are not allowed");
                }
            }

            oldVnfdId = vnfPkgInfoResource.getVnfdId();
            oldVnfdVersion = vnfPkgInfoResource.getVnfdVersion();

            if(dms == DataModelSpec.SOL001) {
                if (!oldVnfdId.equals(vnfdId) || !oldVnfdVersion.equals(version)) {
                    Optional<VnfPkgInfoResource> optionalVnfPkgInfoResource =
                            vnfPkgInfoRepository.findByVnfdIdAndVnfdVersionAndProjectId(vnfdId, version, project);
                    if (optionalVnfPkgInfoResource.isPresent())
                        throw new AlreadyExistingEntityException("An VNF Pkg with the same id and version already exists in the project");
                }
            } else {
                if(oldVnfdId.equals(vnfdId) && !oldVnfdVersion.equals(version))
                    throw new MalformattedElementException("VNFD ID must change when the version is changed (SOL006).");

                if(!oldVnfdId.equals(vnfdId)) {
                    Optional<VnfPkgInfoResource> optionalVnfPkgInfoResource = vnfPkgInfoRepository.findByVnfdIdAndProjectId(vnfdId, project);
                    if(optionalVnfPkgInfoResource.isPresent())
                        throw new AlreadyExistingEntityException("A SOL006 VNFD with the same id already exists in the project");
                }
            }

            vnfPkgInfoResource.setVnfdId(vnfdId);
            vnfPkgInfoResource.setVnfdVersion(version);

            vnfPkgFilename = FileSystemStorageService.storePkg(project, vnfPkgId_string, version, vnfPkg, DescriptorType.VNFD);

            byte[] bytes = vnfPkg.getBytes();
            archiveParser.unzip(new ByteArrayInputStream(bytes), project, vnfPkgId_string, version, DescriptorType.VNFD);

            //Removing old files
            if(!oldVnfdId.equals(vnfdId) || !oldVnfdVersion.equals(version))
                FileSystemStorageService.deleteVnfPkg(project, oldVnfdId.toString(), oldVnfdVersion);

            if(dms == DataModelSpec.SOL001)
                log.debug("VNF Pkg successfully parsed - its content is: \n"
                        + DescriptorsParser.descriptorTemplateToString(dt));
            else
                log.debug("VNFD in Pkg successfully parsed - its content is: \n" + vnfd.toString());

            log.debug("VNF Pkg file successfully stored");
        } catch (IOException e) {
            log.error("Error while parsing VNF Pkg: " + e.getMessage());
            throw new MalformattedElementException("Error while parsing VNF Pkg");
        } catch (AlreadyExistingEntityException e) {
            log.error("Unable to onboard VNF Pkg: " + e.getMessage());
            throw new AlreadyExistingEntityException("Unable to onboard VNF Pkg: " + e.getMessage());
        } catch (MalformattedElementException e) {
            log.error("Unable to onboard VNF Pkg:" + e.getMessage());
            throw new MalformattedElementException("Unable to onboard VNF Pkg:" + e.getMessage());
        } catch (FailedOperationException e) {
            log.error("Unable to onboard VNF Pkg: " + e.getMessage());
            throw new FailedOperationException("Unable to onboard VNF Pkg: " + e.getMessage());
        }

        if (vnfPkgFilename == null) {
            vnfPkgInfoResource.setOnboardingState(PackageOnboardingStateType.FAILED);
            vnfPkgInfoRepository.saveAndFlush(vnfPkgInfoResource);
            throw new FailedOperationException("Invalid internal structures");
        }

        log.debug("Updating VNF Pkg info");
        vnfPkgInfoResource.setOnboardingState(PackageOnboardingStateType.LOCAL_ONBOARDED);
        vnfPkgInfoResource.setOperationalState(PackageOperationalStateType.ENABLED);

        if(dms == DataModelSpec.SOL001)
            vnfPkgInfoResource.setVnfProvider(dt.getMetadata().getVendor());
        else
            vnfPkgInfoResource.setVnfProvider(vnfd.getProvider());


        String vnfName = "";
        if(dms == DataModelSpec.SOL001) {
            Map<String, VNFNode> vnfNodes = dt.getTopologyTemplate().getVNFNodes();
            if (vnfNodes.size() == 1) {
                for (Map.Entry<String, VNFNode> vnfNode : vnfNodes.entrySet()) {
                    vnfName = vnfNode.getValue().getProperties().getProductName();
                }
            } else {
                Map<String, String> subMapsProperties = dt.getTopologyTemplate().getSubstituitionMappings().getProperties();
                for (Map.Entry<String, String> entry : subMapsProperties.entrySet()) {
                    if (entry.getKey().equalsIgnoreCase("productName")) {
                        vnfName = entry.getValue();
                    }
                }
            }
        } else
            vnfName = vnfd.getProductName();

        log.debug("VNF Pkg name: " + vnfName);
        vnfPkgInfoResource.setVnfProductName(vnfName);
        vnfPkgInfoResource.setContentType(contentType);
        vnfPkgInfoResource.setVnfPkgFilename(vnfPkgFilename);
        vnfPkgInfoResource.setVnfdFilename(csarInfo.getDescriptorFilename());
        vnfPkgInfoResource.setMetaFilename(csarInfo.getMetaFilename());
        vnfPkgInfoResource.setManifestFilename(csarInfo.getMfFilename());

        vnfPkgInfoResource.setPublished(isInternalRequest);

        if(dms == DataModelSpec.SOL006)
            vnfPkgInfoResource.setDescription(vnfd.getDescription());

        // send notification over kafka bus
        List<String> alreadyOnboardedManoIds = new ArrayList<>();
        List<String> failedOnboardedManoIds = new ArrayList<>();
        Map<String, String> userDefinedData = vnfPkgInfoResource.getUserDefinedData();
        if(userDefinedData != null && userDefinedData.size() != 0) {
            for (MANOPlugin mano : pluginManger.manoDrivers.values())
                if ((userDefinedData.containsKey(mano.getPluginId()) && userDefinedData.get(mano.getPluginId()).equals("yes")))
                    alreadyOnboardedManoIds.add(mano.getPluginId());
                else if ((userDefinedData.containsKey(mano.getPluginId()) && userDefinedData.get(mano.getPluginId()).equals("no")))
                    failedOnboardedManoIds.add(mano.getPluginId());
        }

        for(MANOPlugin mano : pluginManger.manoDrivers.values())
            if(vnfPkgInfoResource.getUserDefinedData().remove(mano.getPluginId()) == null)
                vnfPkgInfoResource.getUserDefinedData().remove(mano.getMano().getManoSite());

        if(alreadyOnboardedManoIds.isEmpty() && failedOnboardedManoIds.isEmpty())//in case is LocalOnboarded, send onborading request to all available MANOs
            failedOnboardedManoIds.addAll(pluginManger.manoDrivers.keySet());

        //the update request is coming from a MANO, avoid sending update notification to that MANO
        if(originalPluginId != null)
            alreadyOnboardedManoIds.remove(originalPluginId);

        UUID operationId;
        if(!failedOnboardedManoIds.isEmpty()) {
            operationId = insertOperationInfoInConsumersMap(vnfPkgInfoId,
                    CatalogueMessageType.VNFPKG_ONBOARDING_NOTIFICATION, OperationStatus.SENT, failedOnboardedManoIds);
            vnfPkgInfoResource.setAcknowledgedOnboardOpConsumers(operationIdToConsumersAck.get(operationId.toString()));
            VnfPkgOnBoardingNotificationMessage onboardingMsg = new VnfPkgOnBoardingNotificationMessage(vnfPkgInfoResource.getId().toString(), vnfdId.toString(), vnfPkgInfoResource.getVnfdVersion(), project,
                    operationId, ScopeType.LOCAL, OperationStatus.SENT, failedOnboardedManoIds, new KeyValuePair(rootDir + ConfigurationParameters.storageVnfpkgsSubfolder + "/" + project + "/" + vnfdId.toString() + "/" + vnfPkgInfoResource.getVnfdVersion(), PathType.LOCAL.toString()));
            notificationManager.sendVnfPkgOnBoardingNotification(onboardingMsg);
        }

        if(!alreadyOnboardedManoIds.isEmpty()) {
            operationId = insertOperationInfoInConsumersMap(vnfPkgInfoId,
                    CatalogueMessageType.VNFPKG_CHANGE_NOTIFICATION, OperationStatus.SENT, alreadyOnboardedManoIds);
            vnfPkgInfoResource.getAcknowledgedOnboardOpConsumers().putAll(operationIdToConsumersAck.get(operationId.toString()));
            VnfPkgChangeNotificationMessage updateMsg = new VnfPkgChangeNotificationMessage(vnfPkgInfoId, oldVnfdId.toString(), oldVnfdVersion, project,
                    operationId, ScopeType.LOCAL, OperationStatus.SENT, alreadyOnboardedManoIds, new KeyValuePair(rootDir + ConfigurationParameters.storageVnfpkgsSubfolder + "/" + project + "/" + vnfdId.toString() + "/" + vnfPkgInfoResource.getVnfdVersion(), PathType.LOCAL.toString()));
            notificationManager.sendVnfPkgChangeNotification(updateMsg);
        }

        vnfPkgInfoRepository.saveAndFlush(vnfPkgInfoResource);
        log.debug("VNF Pkg content updated");
    }


    public VnfPkgInfo buildVnfPkgInfo(VnfPkgInfoResource vnfPkgInfoResource) {
        log.debug("Building VNF Pkg info from internal repo");
        VnfPkgInfo vnfPkgInfo = new VnfPkgInfo();
        vnfPkgInfo.setId(vnfPkgInfoResource.getId());
        VnfPkgLinksType links = new VnfPkgLinksType();
        links.setSelf("/vnfpkgm/v1/vnf_packages/" + vnfPkgInfoResource.getId());
        links.setVnfd("/vnfpkgm/v1/vnf_packages/" + vnfPkgInfoResource.getId() + "/vnfd");
        links.setPackageContent("/vnfpkgm/v1/vnf_packages/" + vnfPkgInfoResource.getId() + "/package_content");
        vnfPkgInfo.setLinks(links);
        vnfPkgInfo.setChecksum(vnfPkgInfoResource.getChecksum());
        vnfPkgInfo.setOnboardingState(vnfPkgInfoResource.getOnboardingState());
        vnfPkgInfo.setOperationalState(vnfPkgInfoResource.getOperationalState());
        vnfPkgInfo.setUsageState(vnfPkgInfoResource.getUsageState());
        KeyValuePairs kvp = new KeyValuePairs();
        kvp.putAll(vnfPkgInfoResource.getUserDefinedData());
        vnfPkgInfo.setUserDefinedData(kvp);
        vnfPkgInfo.setVnfdId(vnfPkgInfoResource.getVnfdId());
        vnfPkgInfo.setVnfdVersion(vnfPkgInfoResource.getVnfdVersion());
        vnfPkgInfo.setVnfProductName(vnfPkgInfoResource.getVnfProductName());
        vnfPkgInfo.setVnfProvider(vnfPkgInfoResource.getVnfProvider());
        vnfPkgInfo.setVnfSoftwareVersion(vnfPkgInfoResource.getVnfSoftwareVersion());
        vnfPkgInfo.setProjectId(vnfPkgInfoResource.getProjectId());

        Map<String, NotificationResource> acksMap = vnfPkgInfoResource.getAcknowledgedOnboardOpConsumers();
        Map<String, PackageOnboardingStateType> manoIdToOnboardingStatus = new HashMap<>();
        for (Map.Entry<String, NotificationResource> entry : acksMap.entrySet()) {
            if (entry.getValue().getOperation() == CatalogueMessageType.VNFPKG_ONBOARDING_NOTIFICATION ||
                    entry.getValue().getOperation() == CatalogueMessageType.VNFPKG_CHANGE_NOTIFICATION) {
                PackageOnboardingStateType pkgOnboardingStateType = PackageOnboardingStateType.UPLOADING;
                switch (entry.getValue().getOpStatus()) {
                    case SENT:
                        pkgOnboardingStateType = PackageOnboardingStateType.UPLOADING;
                        break;
                    case RECEIVED:
                        pkgOnboardingStateType = PackageOnboardingStateType.SKIPPED;
                        break;
                    case PROCESSING:
                        pkgOnboardingStateType = PackageOnboardingStateType.PROCESSING;
                        break;
                    case FAILED:
                        pkgOnboardingStateType = PackageOnboardingStateType.FAILED;
                        break;
                    case SUCCESSFULLY_DONE:
                        pkgOnboardingStateType = PackageOnboardingStateType.ONBOARDED;
                }
                if (entry.getValue().getPluginType() == PluginType.MANO) {
                    manoIdToOnboardingStatus.putIfAbsent(entry.getKey(), pkgOnboardingStateType);
                }
            }
        }

        vnfPkgInfo.setManoIdToOnboardingStatus(manoIdToOnboardingStatus);

        if (vnfPkgInfoResource.isPublished()) {
            vnfPkgInfo.setC2cOnboardingState(C2COnboardingStateType.PUBLISHED);
        } else {
            vnfPkgInfo.setC2cOnboardingState(C2COnboardingStateType.UNPUBLISHED);
        }

        vnfPkgInfo.setDescription(vnfPkgInfoResource.getDescription());

        return vnfPkgInfo;
    }

    private VnfPkgInfoResource getVnfPkgInfoResource(String vnfPkgInfoId) throws MalformattedElementException, NotExistingEntityException {
        log.debug("Retrieving internal VNF Pkg info resource with ID " + vnfPkgInfoId);
        try {
            UUID id = UUID.fromString(vnfPkgInfoId);

            Optional<VnfPkgInfoResource> optional = vnfPkgInfoRepository.findById(id);

            if (optional.isPresent()) {
                VnfPkgInfoResource vnfPkgInfoResource = optional.get();
                log.debug("Found VNF Pkg info resource with id: " + vnfPkgInfoId);
                return vnfPkgInfoResource;
            } else {
                log.debug("VNF Pkg info resource with id " + vnfPkgInfoId + " not found");
                throw new NotExistingEntityException("VNF Pkg info resource with id " + vnfPkgInfoId + " not found");
            }
        } catch (IllegalArgumentException e) {
            log.error("Wrong ID format: " + vnfPkgInfoId);
            throw new MalformattedElementException("Wrong ID format: " + vnfPkgInfoId);
        }
    }

    public void updateVnfPkgInfoOperationStatus(String vnfPkgInfoId, String pluginId, OperationStatus opStatus, CatalogueMessageType messageType) throws NotExistingEntityException {
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
                ackMap.put(pluginId, new NotificationResource(vnfPkgInfoId, messageType, opStatus, PluginType.MANO));
                vnfPkgInfoResource.setAcknowledgedOnboardOpConsumers(ackMap);

                if (messageType.equals(CatalogueMessageType.VNFPKG_ONBOARDING_NOTIFICATION) || messageType.equals(CatalogueMessageType.VNFPKG_CHANGE_NOTIFICATION)) {
                        if (opStatus.equals(OperationStatus.SUCCESSFULLY_DONE))
                            optionalVnfPkgInfoResource.get().getUserDefinedData().put(pluginId, "yes");
                        else
                            optionalVnfPkgInfoResource.get().getUserDefinedData().put(pluginId, "no");
                }
                log.debug("Checking VNF Pkg with vnfPkgInfoId {} onboarding state", vnfPkgInfoId);
                vnfPkgInfoResource.setOnboardingState(checkVnfPkgOnboardingState(vnfPkgInfoId, ackMap));
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
            if ((entry.getValue().getOperation() == CatalogueMessageType.VNFPKG_ONBOARDING_NOTIFICATION || entry.getValue().getOperation() == CatalogueMessageType.VNFPKG_CHANGE_NOTIFICATION) && entry.getValue().getPluginType() == PluginType.MANO) {
                if(entry.getValue().getOpStatus() == OperationStatus.SUCCESSFULLY_DONE) {
                    log.debug("VNF Pkg with vnfPkgInfoId " + vnfPkgInfoId + " is onboarded in at least one MANO");
                    return PackageOnboardingStateType.ONBOARDED;
                }
                /*
                if (entry.getValue().getOpStatus() == OperationStatus.FAILED) {
                    log.error("VNF Pkg with vnfPkgInfoId {} onboarding failed for mano with manoId {}", vnfPkgInfoId,
                            entry.getKey());

                    // TODO: Decide how to handle MANO onboarding failures.
                    return PackageOnboardingStateType.LOCAL_ONBOARDED;
                } else if (entry.getValue().getOpStatus() == OperationStatus.SENT
                        || entry.getValue().getOpStatus() == OperationStatus.RECEIVED
                        || entry.getValue().getOpStatus() == OperationStatus.PROCESSING) {
                    log.debug("VNF Pkg with vnfPkgInfoId {} onboarding still in progress for mano with manoId {}", vnfPkgInfoId, entry.getKey());
                    return PackageOnboardingStateType.LOCAL_ONBOARDED;
                }

                 */
            }
        }
        log.debug("VNF Pkg with vnfPkgInfoId " + vnfPkgInfoId + " is not onboarded in any MANO");
        return PackageOnboardingStateType.LOCAL_ONBOARDED;
    }
}
