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
package it.nextworks.nfvmano.catalogue.plugins.mano.onapCataloguePlugin;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.*;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.PathType;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.ScopeType;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.*;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.common.ManoObjectType;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.common.ToscaArchiveBuilder;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.onap.ONAP;
import it.nextworks.nfvmano.catalogue.plugins.mano.onapCataloguePlugin.elements.*;
import it.nextworks.nfvmano.catalogue.plugins.mano.onapCataloguePlugin.repos.OnapObjectRepository;
import it.nextworks.nfvmano.catalogue.plugins.mano.onapCataloguePlugin.translators.OnapToSolTranslator;
import it.nextworks.nfvmano.libs.common.elements.KeyValuePair;
import it.nextworks.nfvmano.libs.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.common.exceptions.*;
import it.nextworks.nfvmano.libs.descriptors.templates.DescriptorTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class OnapPlugin extends MANOPlugin {

    private static final Logger log = LoggerFactory.getLogger(OnapPlugin.class);
    private Path onapDirPath;
    private File onapDir;
    private Path tmpDirPath;
    private final ONAP onap;
    private OnapDriver onapClient;
    private long syncPeriod;

    private OnapObjectRepository onapObjectRepository;

    private Long startSync;

    public OnapPlugin(MANOType manoType, MANO mano, String kafkaBootstrapServers, String localTopic, String remoteTopic,
                                      KafkaTemplate<String, String> kafkaTemplate, OnapObjectRepository onapObjectRepository, Path onapDirPath, Path tmpDir, boolean manoSync, long syncPeriod) {
        super(manoType, mano, kafkaBootstrapServers, localTopic, remoteTopic, kafkaTemplate, manoSync);
        if (MANOType.ONAP != manoType) {
            throw new IllegalArgumentException("ONAP plugin requires an ONAP type MANO");
        }
        this.onap = (ONAP) mano;
        this.onapDirPath = onapDirPath;
        this.syncPeriod = syncPeriod;
        this.tmpDirPath = tmpDir;
        this.onapObjectRepository = onapObjectRepository;
    }

    @Override
    public void init() {
        super.init();
        try {
            onapDirPath = Paths.get(onapDirPath.toString(), "/" + onap.getManoId());
            Files.createDirectories(onapDirPath);
            onapDir = onapDirPath.toFile();
        } catch (IOException e) {
            log.error("Could not initialize tmp directory: " + e.getMessage());
        }
        initOnapConnection();
    }

    private void initOnapConnection() {
        onapClient = new OnapDriver(onap.getIpAddress(), onap.getPort());
        log.info("{} - ONAP instance addr {}:{} connected", onap.getManoId(), onap.getIpAddress(), onap.getPort());

        if(isManoSync()) {
            log.info("{} - Starting runtime synchronization, sync period every {} minutes", onap.getManoId(), syncPeriod);
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            Runnable syncTask = this::RuntimeSynchronization;
            scheduler.scheduleAtFixedRate(syncTask, syncPeriod, syncPeriod, TimeUnit.MINUTES);
        }
    }

    @Override
    public Map<String, List<String>> getAllVnfd(String project){
        log.info("{} - Startup synchronization, started retrieving ONAP Vnf Pkgs from project {}", onap.getManoId(), project);
        this.startSync = Instant.now().getEpochSecond();

        try {
            updateDB();
        } catch(FailedOperationException e){
            log.error("{} - {}", onap.getManoId(), e.getMessage());
            return null;
        }

        //Delete ONAP Pkg no longer present in ONAP and add to ids list the others
        Map<String, List<String>> ids = new HashMap<>();
        List<OnapObject> onapObjectList = onapObjectRepository.findByOnapIdAndType(onap.getManoId(), ManoObjectType.VNF);
        for(OnapObject onapObject : onapObjectList){
            if(onapObject.getEpoch().compareTo(startSync) < 0){
                log.info("{} - Onap Vnf Pkg with descriptor ID {} and version {} no longer present in project {}", onap.getManoId(), onapObject.getDescriptorId(), onapObject.getVersion(), project);
                onapObjectRepository.delete(onapObject);
            }else{
                ids.computeIfAbsent(onapObject.getDescriptorId(), k -> new ArrayList<>()).add(onapObject.getVersion());//ids.put(onapObject.getDescriptorId(), onapObject.getVersion());
            }
        }

        log.info("{} - Startup synchronization, finished retrieving ONAP Vnf Pkgs from project {}", onap.getManoId(), project);

        return ids;
    }

    @Override
    public Map<String, List<String>> getAllNsd(String project) {
        log.info("{} - Startup synchronization, started retrieving ONAP Ns Pkgs from project {}", onap.getManoId(), project);
        //Delete Onap Pkg no longer present in Onap and add to ids list the others
        Map<String, List<String>> ids = new HashMap<>();
        List<OnapObject> onapObjectList = onapObjectRepository.findByOnapIdAndType(onap.getManoId(), ManoObjectType.NS);
        for(OnapObject onapObject : onapObjectList){
            if(onapObject.getEpoch().compareTo(this.startSync) < 0){
                log.info("{} - Onap Ns Pkg with descriptor ID {} and version {} no longer present in project {}", onap.getManoId(), onapObject.getDescriptorId(), onapObject.getVersion(), project);
                onapObjectRepository.delete(onapObject);
            }else{
                ids.computeIfAbsent(onapObject.getDescriptorId(), k -> new ArrayList<>()).add(onapObject.getVersion());//ids.put(onapObject.getDescriptorId(), onapObject.getVersion());
            }
        }
        log.info("{} - Startup synchronization, finished retrieving ONAP Ns Pkgs from project {}", onap.getManoId(), project);

        return ids;
    }

    @Override
    public KeyValuePair getTranslatedPkgPath(String descriptorId, String descriptorVersion, String project){
        log.info("{} - Translating ONAP Pkg with descriptor ID {} and version {} from project {}", onap.getManoId(), descriptorId, descriptorVersion, project);

        Optional<OnapObject> onapObject = onapObjectRepository.findByDescriptorIdAndVersionAndOnapId(descriptorId, descriptorVersion, onap.getManoId());
        String pkgPath = null;
        try{
            if(onapObject.isPresent()){
                if(onapObject.get().getType().equals(ManoObjectType.VNF))
                    pkgPath = createVnfPkgTosca(onapObject.get());
                else
                    pkgPath = createNsPkgTosca(onapObject.get());
            }
        }catch(Exception e){
            log.error("{} - Unable to generate TOSCA Pkg with descriptor ID {} and version {} for project {}: {}", onap.getManoId(), descriptorId, descriptorVersion, project, e.getMessage());
            log.debug(null, e);
            return  null;
        }

        log.info("{} - Uploading TOSCA Pkg with descriptor ID {} and version {} to project {}", onap.getManoId(), descriptorId, descriptorVersion, project);

        return new KeyValuePair(pkgPath, PathType.LOCAL.toString());
    }

    @Override
    public void notifyOnboarding(String infoId, String descriptorId, String descriptorVersion, String project, OperationStatus opStatus){
        log.info("{} - Received Sync Pkg onboarding notification for Descriptor with ID {} and version {} for project {} : {}", onap.getManoId(), descriptorId, descriptorVersion, project, opStatus.toString());
        Optional<OnapObject> onapObjectOptional = onapObjectRepository.findByDescriptorIdAndVersionAndOnapId(descriptorId, descriptorVersion, onap.getManoId());
        if(onapObjectOptional.isPresent()){
            OnapObject onapObject = onapObjectOptional.get();
            if(opStatus.equals(OperationStatus.SUCCESSFULLY_DONE)){
                onapObject.setCatalogueId(infoId);
                onapObjectRepository.saveAndFlush(onapObject);
            }
            /*
            else
                onapObjectRepository.delete(onapObject);
             */
        }
    }

    @Override
    public void notifyDelete(String infoId, String descriptorId, String descriptorVersion, String project, OperationStatus opStatus){
        log.info("{} - Received Sync Pkg deletion notification for Descriptor with ID {} and version {} for project {} : {}", onap.getManoId(), descriptorId, descriptorVersion, project, opStatus.toString());
    }

    @Override
    public String getManoPkgInfoId(String cataloguePkgInfoId){
        Optional<OnapObject> onapObjectOptional = onapObjectRepository.findByCatalogueIdAndOnapId(cataloguePkgInfoId, onap.getManoId());
        return onapObjectOptional.map(OnapObject::getDescriptorId).orElse(null);
    }

    @Override
    public void RuntimeSynchronization(){
        log.info("{} - Runtime synchronization, started retrieving ONAP Vnf and Ns Pkgs", onap.getManoId());
        Long startSync = Instant.now().getEpochSecond();

        List<OnapObject> oldOnapObjectList = onapObjectRepository.findByOnapId(onap.getManoId());
        List<String> oldOnapIdList = oldOnapObjectList.stream().map(OnapObject::getDescriptorId).collect(Collectors.toList());

        try {
            updateDB();
        } catch(FailedOperationException e){
            log.error("{} - {}", onap.getManoId(), e.getMessage());
            return;
        }

        List<OnapObject> onapVnfList = onapObjectRepository.findByOnapIdAndType(onap.getManoId(), ManoObjectType.VNF);
        List<OnapObject> onapNsList = onapObjectRepository.findByOnapIdAndType(onap.getManoId(), ManoObjectType.NS);
        UUID operationId;
        String pkgPath;

        for(OnapObject onapVnf : onapVnfList) {
            //upload new Vnf Pkgs
            if(!oldOnapIdList.contains(onapVnf.getDescriptorId())){
                operationId = UUID.randomUUID();
                try {
                    pkgPath = createVnfPkgTosca(onapVnf);
                }catch(Exception e){
                    log.error("{} - Unable to generate TOSCA Vnf Pkg with descriptor ID {} and version {}: {}", onap.getManoId(), onapVnf.getDescriptorId(), onapVnf.getVersion(), e.getMessage());
                    log.debug(null, e);
                    continue;
                }
                log.info("{} - Uploading TOSCA Vnf Pkg with descriptor ID {} and version {}", onap.getManoId(), onapVnf.getDescriptorId(), onapVnf.getVersion());
                sendNotification(new VnfPkgOnBoardingNotificationMessage(null, onapVnf.getDescriptorId(), onapVnf.getVersion(), "all",
                            operationId, ScopeType.SYNC, OperationStatus.SENT, onap.getManoId(), null, new KeyValuePair(pkgPath, PathType.LOCAL.toString())));
            }
        }

        for(OnapObject onapNs : onapNsList){
            //upload new Ns Pkgs
            if(!oldOnapIdList.contains(onapNs.getDescriptorId())){
                operationId = UUID.randomUUID();
                try {
                    pkgPath = createNsPkgTosca(onapNs);
                }catch(Exception e){
                    log.error("{} - Unable to generate TOSCA Ns Pkg with descriptor ID {} and version {}: {}", onap.getManoId(), onapNs.getDescriptorId(), onapNs.getVersion(), e.getMessage());
                    log.debug(null, e);
                    continue;
                }
                log.info("{} - Uploading TOSCA Ns Pkg with descriptor ID {} and version {}", onap.getManoId(), onapNs.getDescriptorId(), onapNs.getVersion());
                sendNotification(new NsdOnBoardingNotificationMessage(null, onapNs.getDescriptorId(), onapNs.getVersion(), "all",
                            operationId, ScopeType.SYNC, OperationStatus.SENT, onap.getManoId(), null, new KeyValuePair(pkgPath, PathType.LOCAL.toString())));
            }
            //Delete Onap Ns Pkg no longer present
            if(onapNs.getEpoch().compareTo(startSync) < 0){
                log.info("{} - Onap Ns Pkg with descriptor ID {} and version {} no longer present, deleting it", onap.getManoId(), onapNs.getDescriptorId(), onapNs.getVersion());
                operationId = UUID.randomUUID();
                sendNotification(new NsdDeletionNotificationMessage(null, onapNs.getDescriptorId(), onapNs.getVersion(), "all",
                            operationId, ScopeType.SYNC, OperationStatus.SENT, onap.getManoId(), null));
                onapObjectRepository.delete(onapNs);
            }
        }

        for(OnapObject onapVnf : onapVnfList) {
            //Delete Onap Vnf Pkg no longer present
            if(onapVnf.getEpoch().compareTo(startSync) < 0){
                log.info("{} - Onap Vnf Pkg with descriptor ID {} and version {} no longer present, deleting it", onap.getManoId(), onapVnf.getDescriptorId(), onapVnf.getVersion());
                operationId = UUID.randomUUID();
                sendNotification(new VnfPkgDeletionNotificationMessage(null, onapVnf.getDescriptorId(), onapVnf.getVersion(), "all",
                            operationId, ScopeType.SYNC, OperationStatus.SENT, onap.getManoId(), null));
                onapObjectRepository.delete(onapVnf);
            }
        }

        log.info("{} - Runtime synchronization, finished retrieving ONAP Vnf and Ns Pkgs", onap.getManoId());
    }

    private void updateDB() throws FailedOperationException{
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<OnapServiceSpecification> nsSpecificationList;
        List<File> nsPackages = new ArrayList<>();
        nsSpecificationList = onapClient.getServicesSpecification();
        for(OnapServiceSpecification nsSpecification : nsSpecificationList){
            try {
                File nsPackage = onapClient.getNsPackage(nsSpecification, onapDir);
                File unzippedNsPackage = new File(onapDir, nsSpecification.getNsName());
                Utilities.unzip(nsPackage, unzippedNsPackage);
                nsPackages.add(unzippedNsPackage);
            }catch (IOException e) {
                throw new FailedOperationException("Failed to unzip the file: " + e.getMessage());
            }
        }
        for(File nsPackage : nsPackages){
            File nsDescriptorFile = Utilities.getNsDescriptorFile(nsPackage);
            OnapNsDescriptor nsDescriptor;
            try {
                nsDescriptor = mapper.readValue(nsDescriptorFile, OnapNsDescriptor.class);
            }catch (Exception e){
                throw new FailedOperationException("Cannot deserialize ONAP NS descriptor");
            }
            String nsdId = nsDescriptor.getNsdId();
            Optional<OnapObject> onapObjectOptional = onapObjectRepository.findByDescriptorIdAndTypeAndOnapId(nsdId, ManoObjectType.NS, onap.getManoId());
            OnapObject onapObject;
            if(onapObjectOptional.isPresent()){
                onapObject = onapObjectOptional.get();
                log.info("{} - Onap Ns Pkg with descriptor ID {} and version {} already present", onap.getManoId(), onapObject.getDescriptorId(), onapObject.getVersion());
                onapObject.setEpoch(Instant.now().getEpochSecond());
            }else{
                onapObject = new OnapObject();
                onapObject.setVersion("1.0");
                log.info("{} - Found new Ns Pkg with descriptor ID {} and version {}", onap.getManoId(), nsdId, onapObject.getVersion());
                onapObject.setDescriptorId(nsdId);
                onapObject.setOnapId(onap.getManoId());
                onapObject.setType(ManoObjectType.NS);
                onapObject.setEpoch(Instant.now().getEpochSecond());
                onapObject.setPath(nsDescriptorFile.toPath().toString());
            }
            onapObjectRepository.saveAndFlush(onapObject);
            List<File> vnfDescriptorFiles = Utilities.getVnfDescriptorFiles(nsPackage, nsDescriptor);
            for(File vnfDescriptorFile : vnfDescriptorFiles){
                OnapVnfDescriptor vnfDescriptor;
                try {
                    vnfDescriptor = mapper.readValue(vnfDescriptorFile, OnapVnfDescriptor.class);
                }catch (Exception e){
                    throw new FailedOperationException("Cannot deserialize ONAP VNF descriptor");
                }
                String vnfdId = vnfDescriptor.getVnfdId();
                onapObjectOptional = onapObjectRepository.findByDescriptorIdAndTypeAndOnapId(vnfdId, ManoObjectType.VNF, onap.getManoId());
                if(onapObjectOptional.isPresent()){
                    onapObject = onapObjectOptional.get();
                    log.info("{} - Onap Vnf Pkg with descriptor ID {} and version {} already present", onap.getManoId(), onapObject.getDescriptorId(), onapObject.getVersion());
                    onapObject.setEpoch(Instant.now().getEpochSecond());
                }else{
                    onapObject = new OnapObject();
                    onapObject.setVersion("1.0");
                    log.info("{} - Found new Vnf Pkg with descriptor ID {} and version {}", onap.getManoId(), vnfdId, onapObject.getVersion());
                    onapObject.setDescriptorId(vnfdId);
                    onapObject.setOnapId(onap.getManoId());
                    onapObject.setType(ManoObjectType.VNF);
                    onapObject.setEpoch(Instant.now().getEpochSecond());
                    onapObject.setPath(vnfDescriptorFile.toPath().toString());
                }
                onapObjectRepository.saveAndFlush(onapObject);
            }
        }
    }

    private String createVnfPkgTosca (OnapObject onapObject) throws MalformattedElementException, IllegalStateException, IOException, IllegalArgumentException{
        log.info("{} - Creating TOSCA VNF Descriptor with ID {} and version {}", onap.getManoId(), onapObject.getDescriptorId(), onapObject.getVersion());
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        OnapVnfDescriptor vnfdOnap = mapper.readValue(new File(onapObject.getPath()), OnapVnfDescriptor.class);
        DescriptorTemplate vnfdSol = OnapToSolTranslator.generateVnfDescriptor(vnfdOnap);
        log.info("{} - Creating TOSCA VNF Pkg with descriptor ID {} and version {}", onap.getManoId(), onapObject.getDescriptorId(), onapObject.getVersion());
        return ToscaArchiveBuilder.createVNFCSAR(vnfdSol.getMetadata().getDescriptorId(), vnfdSol, tmpDirPath.toString(), null);
    }

    private String createNsPkgTosca (OnapObject onapObject) throws MalformattedElementException, IllegalStateException, IOException, IllegalArgumentException{
        log.info("{} - Creating TOSCA NS Descriptor with ID {} and version {}", onap.getManoId(), onapObject.getDescriptorId(), onapObject.getVersion());
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        OnapNsDescriptor nsdOnap = mapper.readValue(new File(onapObject.getPath()), OnapNsDescriptor.class);
        DescriptorTemplate nsdSol = OnapToSolTranslator.generateNsDescriptor(nsdOnap, onapObject.getPath());
        log.info("{} - Creating TOSCA NS Pkg with descriptor ID {} and version {}", onap.getManoId(), onapObject.getDescriptorId(), onapObject.getVersion());
        return ToscaArchiveBuilder.createNSCSAR(nsdSol.getMetadata().getDescriptorId(), nsdSol, tmpDirPath.toString());
    }

    @Override
    public void acceptNsdOnBoardingNotification(NsdOnBoardingNotificationMessage notification) {
        log.debug("Body: {}", notification);
        if (notification.getScope() == ScopeType.LOCAL){
            log.info("{} - Received NSD onboarding notification for Nsd with ID {} and version {} for project {}", onap.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject());
            if(Utilities.isTargetMano(notification.getSiteOrManoIds(), onap)) {//No need to send notification back if the plugin is not in the target MANOs
                log.debug("{} - NSD onboarding skipped", onap.getManoId());
                sendNotification(new NsdOnBoardingNotificationMessage(notification.getNsdInfoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(),
                        notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                        onap.getManoId(), null, null));
            }
        }else if(notification.getScope() == ScopeType.SYNC){
            log.info("{} - Received Sync Pkg onboarding notification for NSD with ID {} and version {} for project {} : {}", onap.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(), notification.getOpStatus().toString());
            if(notification.getPluginId().equals(onap.getManoId())) {
                Optional<OnapObject> onapObjectOptional = onapObjectRepository.findByDescriptorIdAndVersionAndOnapId(notification.getNsdId(), notification.getNsdVersion(), onap.getManoId());
                if (onapObjectOptional.isPresent()) {
                    OnapObject onapObject = onapObjectOptional.get();
                    if (notification.getOpStatus().equals(OperationStatus.SUCCESSFULLY_DONE)) {
                        onapObject.setCatalogueId(notification.getNsdInfoId());
                        onapObjectRepository.saveAndFlush(onapObject);
                    }
                    /*
                    else
                        onapObjectRepository.delete(onapObject);
                     */
                }
            }
        }
    }

    @Override
    public void acceptNsdChangeNotification(NsdChangeNotificationMessage notification) {
        log.debug("Body: {}", notification);
        log.info("{} - Received Nsd change notification", onap.getManoId());
    }

    @Override
    public void acceptNsdDeletionNotification(NsdDeletionNotificationMessage notification) {
        log.debug("Body: {}", notification);
        if (notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received Nsd deletion notification for Nsd with ID {} and version {} for project {}", onap.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject());
            //Onboarding from NBI is not enabled, thus there is no need to delete the package and send notification
        } else if(notification.getScope() == ScopeType.SYNC){
            log.info("{} - Received Sync Pkg deletion notification for NSD with ID {} and version {} for project {} : {}", onap.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(), notification.getOpStatus().toString());
            //TODO handle notification
        }
    }

    @Override
    public void acceptVnfPkgOnBoardingNotification(VnfPkgOnBoardingNotificationMessage notification) {
        log.debug("Body: {}", notification);
        if (notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received Vnfd onboarding notification for Vnfd with ID {} and version {} for project {}", onap.getManoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject());
            if (Utilities.isTargetMano(notification.getSiteOrManoIds(), onap)) {//No need to send notification back if the plugin is not in the target MANOs
                log.debug("{} - VNF Pkg onboarding skipped", onap.getManoId());
                sendNotification(new VnfPkgOnBoardingNotificationMessage(notification.getVnfPkgInfoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject(),
                        notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                        onap.getManoId(), null,null));
            }
        }else if(notification.getScope() == ScopeType.SYNC){
            log.info("{} - Received Sync Pkg onboarding notification for Vnfd with ID {} and version {} for project {} : {}", onap.getManoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject(), notification.getOpStatus().toString());
            if(notification.getPluginId().equals(onap.getManoId())) {
                Optional<OnapObject> onapObjectOptional = onapObjectRepository.findByDescriptorIdAndVersionAndOnapId(notification.getVnfdId(), notification.getVnfdVersion(), onap.getManoId());
                if (onapObjectOptional.isPresent()) {
                    OnapObject onapObject = onapObjectOptional.get();
                    if (notification.getOpStatus().equals(OperationStatus.SUCCESSFULLY_DONE)) {
                        onapObject.setCatalogueId(notification.getVnfPkgInfoId());
                        onapObjectRepository.saveAndFlush(onapObject);
                    }
                    /*
                    else
                        onapObjectRepository.delete(onapObject);
                     */
                }
            }
        }
    }

    @Override
    public void acceptVnfPkgChangeNotification(VnfPkgChangeNotificationMessage notification) {
        log.debug("Body: {}", notification);
        log.info("{} - Received VNF Pkg change notification", onap.getManoId());
    }

    @Override
    public void acceptVnfPkgDeletionNotification(VnfPkgDeletionNotificationMessage notification) {
        log.debug("Body: {}", notification);
        if (notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received Vnfd deletion notification for Vnfd with ID {} and version {} for project {}", onap.getManoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject());
            //Onboarding from NBI is not enabled, thus there is no need to delete the package and send notification
        }else if(notification.getScope() == ScopeType.SYNC){
            log.info("{} - Received Sync Pkg deletion notification for Vnfd with ID {} and version {} for project {} : {}", onap.getManoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject(), notification.getOpStatus().toString());
            //TODO handle notification
        }
    }

    @Override
    public void acceptPnfdOnBoardingNotification(PnfdOnBoardingNotificationMessage notification) {
        log.debug("Body: {}", notification);
        log.info("{} - Received PNFD onboarding notification", onap.getManoId());
    }

    @Override
    public void acceptPnfdDeletionNotification(PnfdDeletionNotificationMessage notification) {
        log.debug("Body: {}", notification);
        log.info("{} - Received PNFD deletion notification", onap.getManoId());
    }
}
