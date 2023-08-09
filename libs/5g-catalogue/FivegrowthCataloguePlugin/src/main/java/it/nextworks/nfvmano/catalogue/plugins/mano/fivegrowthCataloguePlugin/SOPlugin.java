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
package it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.*;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.PathType;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.ScopeType;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.PluginOperationalState;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.*;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.common.ManoObjectType;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.fivegrowth.FIVEGROWTH;
import it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.elements.SoNsInfoObject;
import it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.elements.SoObject;
import it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.elements.SoVnfInfoObject;
import it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.repos.FivegrowthObjectRepository;
import it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.translators.IfaToSolTranslator;
import it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.translators.SolToIfaTranslator;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.common.ToscaArchiveBuilder;
import it.nextworks.nfvmano.libs.common.elements.KeyValuePair;
import it.nextworks.nfvmano.libs.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.common.exceptions.*;
import it.nextworks.nfvmano.libs.descriptors.templates.DescriptorTemplate;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnboardNsdRequest;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.libs.ifa.descriptors.vnfd.Vnfd;
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

/*NOTES: - WORKS IF DESCRIPTORS ARE ONBOARDED ONLY IN ONE CATALOGUE PROJECT
         - DOESN'T HANDLE DESCRIPTORS WITH SAME ID BUT DIFFERENT VERSION
* */
public class SOPlugin extends MANOPlugin {

    private static final Logger log = LoggerFactory.getLogger(SOPlugin.class);
    private Path soDirPath;
    private File soDir;
    private Path tmpDirPath;
    private final FIVEGROWTH so;
    private SODriver soClient;
    private long syncPeriod;

    private FivegrowthObjectRepository fivegrowthObjectRepository;

    public SOPlugin(MANOType manoType, MANO mano, String kafkaBootstrapServers, String localTopic, String remoteTopic,
                    KafkaTemplate<String, String> kafkaTemplate, FivegrowthObjectRepository fivegrowthObjectRepository, Path soDirPath, Path tmpDir, boolean manoSync, long syncPeriod) {
        super(manoType, mano, kafkaBootstrapServers, localTopic, remoteTopic, kafkaTemplate, manoSync);
        if (MANOType.SO_5GROWTH != manoType) {
            throw new IllegalArgumentException("5GROWTH SO plugin requires a SO_5GROWTH type MANO");
        }
        this.so = (FIVEGROWTH) mano;
        this.soDirPath = soDirPath;
        this.syncPeriod = syncPeriod;
        this.tmpDirPath = tmpDir;
        this.fivegrowthObjectRepository = fivegrowthObjectRepository;
    }

    @Override
    public void init() {
        super.init();
        try {
            soDirPath = Paths.get(soDirPath.toString(), "/" + so.getManoId());
            Files.createDirectories(soDirPath);
            soDir = soDirPath.toFile();
        } catch (IOException e) {
            log.error("Could not initialize tmp directory: " + e.getMessage());
        }
        initSoConnection();
    }

    private void initSoConnection() {
        soClient = new SODriver(so.getIpAddress(), so.getPort());
        log.info("{} - 5GROWTH SO instance addr {}:{} connected", so.getManoId(), so.getIpAddress(), so.getPort());

        if(isManoSync()) {
            log.info("{} - Starting runtime synchronization, sync period every {} minutes", so.getManoId(), syncPeriod);
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            Runnable syncTask = this::RuntimeSynchronization;
            scheduler.scheduleAtFixedRate(syncTask, syncPeriod, syncPeriod, TimeUnit.MINUTES);
        }
    }

    @Override
    public Map<String, List<String>> getAllVnfd(String project){
        log.info("{} - Startup synchronization, started retrieving 5GROWTH SO Vnf Pkgs from project {}", so.getManoId(), project);
        Long startSync = Instant.now().getEpochSecond();

        try {
            updateDB(true, false);
        } catch(FailedOperationException e){
            log.error("{} - {}", so.getManoId(), e.getMessage());
            return null;
        }

        //Delete Vnf Pkg no longer present in SO and add to ids list the others
        Map<String, List<String>> ids = new HashMap<>();

        List<SoObject> soObjectList = fivegrowthObjectRepository.findBySoIdAndType(so.getManoId(), ManoObjectType.VNF);
        for(SoObject soObject : soObjectList){
            if(soObject.getEpoch().compareTo(startSync) < 0){
                log.info("{} - Vnf Pkg with descriptor ID {} and version {} no longer present in project {}", so.getManoId(), soObject.getDescriptorId(), soObject.getVersion(), project);
                fivegrowthObjectRepository.delete(soObject);
            }else{
                ids.computeIfAbsent(soObject.getDescriptorId(), k -> new ArrayList<>()).add(soObject.getVersion());
            }
        }

        log.info("{} - Startup synchronization, finished retrieving Vnf Pkgs from project {}", so.getManoId(), project);
        return ids;
    }

    @Override
    public Map<String, List<String>> getAllNsd(String project) {
        log.info("{} - Startup synchronization, started retrieving 5GROWTH SO Ns Pkgs from project {}", so.getManoId(), project);
        Long startSync = Instant.now().getEpochSecond();

        try {
            updateDB(false, true);
        } catch(FailedOperationException e){
            log.error("{} - {}", so.getManoId(), e.getMessage());
            return null;
        }

        //Delete Ns Pkg no longer present in SO and add to ids list the others
        Map<String, List<String>> ids = new HashMap<>();
        List<SoObject> soObjectList = fivegrowthObjectRepository.findBySoIdAndType(so.getManoId(), ManoObjectType.NS);
        for(SoObject soObject : soObjectList){
            if(soObject.getEpoch().compareTo(startSync) < 0){
                log.info("{} - Ns Pkg with descriptor ID {} and version {} no longer present in project {}", so.getManoId(), soObject.getDescriptorId(), soObject.getVersion(), project);
                fivegrowthObjectRepository.delete(soObject);
            }else{
                ids.computeIfAbsent(soObject.getDescriptorId(), k -> new ArrayList<>()).add(soObject.getVersion());
            }
        }

        log.info("{} - Startup synchronization, finished retrieving Ns Pkgs from project {}", so.getManoId(), project);
        return ids;
    }

    @Override
    public KeyValuePair getTranslatedPkgPath(String descriptorId, String descriptorVersion, String project){
        log.info("{} - Translating 5GROWTH SO Pkg with descriptor ID {} and version {} from project {}", so.getManoId(), descriptorId, descriptorVersion, project);
        String pkgPath = null;

        Optional<SoObject> soObject = fivegrowthObjectRepository.findByDescriptorIdAndVersionAndSoId(descriptorId, descriptorVersion, so.getManoId());

        try{
            if(soObject.isPresent()){
                if(soObject.get().getType().equals(ManoObjectType.VNF))
                    pkgPath = createVnfPkgTosca(soObject.get());
                else
                    pkgPath = createNsPkgTosca(soObject.get());
            }
        }catch(Exception e){
            log.error("{} - Unable to generate TOSCA Pkg with descriptor ID {} and version {} for project {}: {}", so.getManoId(), descriptorId, descriptorVersion, project, e.getMessage());
            log.debug(null, e);
            return  null;
        }

        log.info("{} - Uploading TOSCA Pkg with descriptor ID {} and version {} to project {}", so.getManoId(), descriptorId, descriptorVersion, project);
        return new KeyValuePair(pkgPath, PathType.LOCAL.toString());
    }

    @Override
    public void notifyOnboarding(String infoId, String descriptorId, String descriptorVersion, String project, OperationStatus opStatus){
        log.info("{} - Received Sync Pkg onboarding notification for Descriptor with ID {} and version {} for project {} : {}", so.getManoId(), descriptorId, descriptorVersion, project, opStatus.toString());
        Optional<SoObject> soObjectOptional = fivegrowthObjectRepository.findByDescriptorIdAndVersionAndSoId(descriptorId, descriptorVersion, so.getManoId());
        if(soObjectOptional.isPresent()){
            SoObject soObject = soObjectOptional.get();
            if(opStatus.equals(OperationStatus.SUCCESSFULLY_DONE)){
                soObject.setCatalogueId(infoId);
                fivegrowthObjectRepository.saveAndFlush(soObject);
            }
        }
    }

    @Override
    public void notifyDelete(String infoId, String descriptorId, String descriptorVersion, String project, OperationStatus opStatus){
        log.info("{} - Received Sync Pkg deletion notification for Descriptor with ID {} and version {} for project {} : {}", so.getManoId(), descriptorId, descriptorVersion, project, opStatus.toString());
    }

    @Override
    public String getManoPkgInfoId(String cataloguePkgInfoId){
        Optional<SoObject> soObjectOptional = fivegrowthObjectRepository.findByCatalogueIdAndSoId(cataloguePkgInfoId, so.getManoId());
        return soObjectOptional.map(SoObject::getDescriptorId).orElse(null);
    }

    @Override
    public void RuntimeSynchronization(){
        log.info("{} - Runtime synchronization, started retrieving 5GROWTH SO Vnf and Ns Pkgs", so.getManoId());
        Long startSync = Instant.now().getEpochSecond();

        List<SoObject> oldSoObjectList = fivegrowthObjectRepository.findBySoId(so.getManoId());
        List<String> oldSoIdList = oldSoObjectList.stream().map(SoObject::getDescriptorId).collect(Collectors.toList());

        try {
            updateDB(true, true);
        } catch(FailedOperationException e){
            log.error("{} - {}", so.getManoId(), e.getMessage());
            return;
        }

        List<SoObject> soVnfList = fivegrowthObjectRepository.findBySoIdAndType(so.getManoId(), ManoObjectType.VNF);
        List<SoObject> soNsList = fivegrowthObjectRepository.findBySoIdAndType(so.getManoId(), ManoObjectType.NS);
        UUID operationId;
        String pkgPath;

        for(SoObject soVnf : soVnfList) {
            //upload new Vnf Pkgs
            if(!oldSoIdList.contains(soVnf.getDescriptorId())){
                operationId = UUID.randomUUID();
                try {
                    pkgPath = createVnfPkgTosca(soVnf);
                }catch(Exception e){
                    log.error("{} - Unable to generate TOSCA Vnf Pkg with descriptor ID {} and version {}: {}", so.getManoId(), soVnf.getDescriptorId(), soVnf.getVersion(), e.getMessage());
                    log.debug(null, e);
                    continue;
                }
                log.info("{} - Uploading TOSCA Vnf Pkg with descriptor ID {} and version {}", so.getManoId(), soVnf.getDescriptorId(), soVnf.getVersion());
                sendNotification(new VnfPkgOnBoardingNotificationMessage(null, soVnf.getDescriptorId(), soVnf.getVersion(), "all",
                            operationId, ScopeType.SYNC, OperationStatus.SENT, so.getManoId(), null, new KeyValuePair(pkgPath, PathType.LOCAL.toString())));
            }
        }

        for(SoObject soNs : soNsList){
            //upload new Ns Pkgs
            if(!oldSoIdList.contains(soNs.getDescriptorId())){
                operationId = UUID.randomUUID();
                try {
                    pkgPath = createNsPkgTosca(soNs);
                }catch(Exception e){
                    log.error("{} - Unable to generate TOSCA Ns Pkg with descriptor ID {} and version {}: {}", so.getManoId(), soNs.getDescriptorId(), soNs.getVersion(), e.getMessage());
                    log.debug(null, e);
                    continue;
                }
                log.info("{} - Uploading TOSCA Ns Pkg with descriptor ID {} and version {}", so.getManoId(), soNs.getDescriptorId(), soNs.getVersion());
                sendNotification(new NsdOnBoardingNotificationMessage(null, soNs.getDescriptorId(), soNs.getVersion(), "all",
                            operationId, ScopeType.SYNC, OperationStatus.SENT, so.getManoId(), null, new KeyValuePair(pkgPath, PathType.LOCAL.toString())));
            }
            //Delete Ns Pkg no longer present
            if(soNs.getEpoch().compareTo(startSync) < 0){
                log.info("{} - Ns Pkg with descriptor ID {} and version {} no longer present, deleting it", so.getManoId(), soNs.getDescriptorId(), soNs.getVersion());
                operationId = UUID.randomUUID();
                sendNotification(new NsdDeletionNotificationMessage(null, soNs.getDescriptorId(), soNs.getVersion(), "all",
                            operationId, ScopeType.SYNC, OperationStatus.SENT, so.getManoId(), null));
                fivegrowthObjectRepository.delete(soNs);
            }
        }

        for(SoObject soVnf : soVnfList) {
            //Delete Vnf Pkg no longer present
            if(soVnf.getEpoch().compareTo(startSync) < 0){
                log.info("{} - Vnf Pkg with descriptor ID {} and version {} no longer present, deleting it", so.getManoId(), soVnf.getDescriptorId(), soVnf.getVersion());
                operationId = UUID.randomUUID();
                sendNotification(new VnfPkgDeletionNotificationMessage(null, soVnf.getDescriptorId(), soVnf.getVersion(), "all",
                            operationId, ScopeType.SYNC, OperationStatus.SENT, so.getManoId(), null));
                fivegrowthObjectRepository.delete(soVnf);
            }
        }

        log.info("{} - Runtime synchronization, finished retrieving 5GROWTH SO Vnf and Ns Pkgs", so.getManoId());
    }

    private void updateDB(boolean updateVNF, boolean updateNS) throws FailedOperationException{
        if(!updateVNF && !updateNS)
            return;

        List<SoVnfInfoObject> vnfPkgs = new ArrayList<>();
        List<SoNsInfoObject> nsPkgs = new ArrayList<>();
        Optional<SoObject> infoObjectOptional;
        SoObject infoObject;
        ObjectMapper mapper = new ObjectMapper();

        if(updateVNF) {
            log.info("{} - Updating Vnfs DB", so.getManoId());
            //Retrieve VNFPkgInfos
            vnfPkgs = soClient.queryVnfPackagesInfo().getQueryResult();
        }
        if(updateNS){
            log.info("{} - Updating Nss DB", so.getManoId());
            //Retrieve NSPkgInfos
            nsPkgs = soClient.queryNsds().getQueryResult();
        }

        for(SoVnfInfoObject vnfInfoObject : vnfPkgs){
            try {
                String vnfdId = vnfInfoObject.getVnfd().getVnfdId();
                String vnfdVersion = vnfInfoObject.getVnfd().getVnfdVersion();
                infoObjectOptional = fivegrowthObjectRepository.findByDescriptorIdAndVersionAndSoId(vnfdId, vnfdVersion, so.getManoId());
                if (infoObjectOptional.isPresent()) {
                    log.info("{} - Vnf Pkg with descriptor ID {} and version {} already present", so.getManoId(), vnfdId, vnfdVersion);
                    infoObject = infoObjectOptional.get();
                    infoObject.setEpoch(Instant.now().getEpochSecond());
                }else{
                    log.info("{} - Found new Vnf Pkg with descriptor ID {} and version {}", so.getManoId(), vnfdId, vnfdVersion);
                    //Store descriptor file
                    File descriptor = new File(soDir, vnfdId + ".json");
                    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    mapper.writeValue(descriptor, vnfInfoObject.getVnfd());
                    infoObject = new SoObject();
                    infoObject.setVersion(vnfdVersion);
                    infoObject.setDescriptorId(vnfdId);
                    infoObject.setSoId(so.getManoId());
                    infoObject.setType(ManoObjectType.VNF);
                    infoObject.setEpoch(Instant.now().getEpochSecond());
                    infoObject.setPath(descriptor.toPath().toString());
                }
                fivegrowthObjectRepository.saveAndFlush(infoObject);
            }catch (IllegalStateException | IllegalArgumentException | IOException e) {
                log.error("{} - Sync error: {}", so.getManoId(), e.getMessage());
                log.debug(null, e);
            }
        }

        for(SoNsInfoObject nsInfoObject : nsPkgs){
            try {
                String nsdId = nsInfoObject.getNsd().getNsdIdentifier();
                String nsdVersion = nsInfoObject.getNsd().getVersion();
                infoObjectOptional = fivegrowthObjectRepository.findByDescriptorIdAndVersionAndSoId(nsdId, nsdVersion, so.getManoId());
                if (infoObjectOptional.isPresent()) {
                    log.info("{} - Ns Pkg with descriptor ID {} and version {} already present", so.getManoId(), nsdId, nsdVersion);
                    infoObject = infoObjectOptional.get();
                    infoObject.setEpoch(Instant.now().getEpochSecond());
                }else{
                    log.info("{} - Found new Ns Pkg with descriptor ID {} and version {}", so.getManoId(), nsdId, nsdVersion);
                    //Store descriptor file
                    File descriptor = new File(soDir, nsdId + ".json");
                    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    mapper.writeValue(descriptor, nsInfoObject.getNsd());
                    infoObject = new SoObject();
                    infoObject.setVersion(nsdVersion);
                    infoObject.setDescriptorId(nsdId);
                    infoObject.setSoId(so.getManoId());
                    infoObject.setType(ManoObjectType.NS);
                    infoObject.setEpoch(Instant.now().getEpochSecond());
                    infoObject.setPath(descriptor.toPath().toString());
                }
                fivegrowthObjectRepository.saveAndFlush(infoObject);
            }catch (IllegalStateException | IllegalArgumentException | IOException e) {
                log.error("{} - Sync error: {}", so.getManoId(), e.getMessage());
                log.debug(null, e);
            }
        }
    }

    private String createVnfPkgTosca (SoObject soObject) throws IllegalStateException, IOException, IllegalArgumentException{
        log.info("{} - Creating TOSCA VNF Descriptor with ID {} and version {}", so.getManoId(), soObject.getDescriptorId(), soObject.getVersion());
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        Vnfd vnfdIfa = mapper.readValue(new File(soObject.getPath()), Vnfd.class);
        DescriptorTemplate vnfdSol = IfaToSolTranslator.generateVnfDescriptor(vnfdIfa);
        log.info("{} - Creating TOSCA VNF Pkg with descriptor ID {} and version {}", so.getManoId(), soObject.getDescriptorId(), soObject.getVersion());
        return ToscaArchiveBuilder.createVNFCSAR(vnfdSol.getMetadata().getDescriptorId(), vnfdSol, tmpDirPath.toString(), null);
    }

    private String createNsPkgTosca (SoObject soObject) throws IllegalStateException, IOException, IllegalArgumentException {
        log.info("{} - Creating TOSCA NS Descriptor with ID {} and version {}", so.getManoId(), soObject.getDescriptorId(), soObject.getVersion());
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        Nsd nsdIfa = mapper.readValue(new File(soObject.getPath()), Nsd.class);
        DescriptorTemplate nsdSol = IfaToSolTranslator.generateNsDescriptor(nsdIfa, soClient);
        log.info("{} - Creating TOSCA NS Pkg with descriptor ID {} and version {}", so.getManoId(), soObject.getDescriptorId(), soObject.getVersion());
        return ToscaArchiveBuilder.createNSCSAR(nsdSol.getMetadata().getDescriptorId(), nsdSol, tmpDirPath.toString());
    }

    @Override
    public void acceptNsdOnBoardingNotification(NsdOnBoardingNotificationMessage notification) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        try {
            String json = mapper.writeValueAsString(notification);
            log.debug("RECEIVED MESSAGE: " + json);
        } catch (JsonProcessingException e) {
            log.error("Unable to parse received nsdOnboardingNotificationMessage: " + e.getMessage());
        }

        if (notification.getScope() == ScopeType.LOCAL ){
            String nsdInfoId = notification.getNsdInfoId();
            log.info("{} - Received NSD onboarding notification for Nsd with ID {} and version {} for project {}", so.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject());
            if(Utilities.isTargetMano(notification.getSiteOrManoIds(), so) && this.getPluginOperationalState() == PluginOperationalState.ENABLED) {//No need to send notification back if the plugin is not in the target MANOs
                try{
                    String packagePath = notification.getPackagePath().getKey();
                    File descriptor;
                    File metadata;
                    Set<String> metadataFileNames;
                    String metadataFileName;
                    if(notification.getPackagePath().getValue().equals(PathType.LOCAL.toString())) {
                        Set<String> files = Utilities.listFiles(packagePath);
                        if(files.size() != 1) {
                            metadataFileNames = Utilities.listFiles(packagePath + "/TOSCA-Metadata/");
                            //Consider only one metadata file is present
                            metadataFileName = metadataFileNames.stream().filter(name -> name.endsWith(".meta")).findFirst().get();
                            metadata = new File(packagePath + "/TOSCA-Metadata/" + metadataFileName);
                            descriptor = new File(packagePath + "/" + Utilities.getMainServiceTemplateFromMetadata(metadata));
                        }
                        else if (files.iterator().next().endsWith(".yaml") || files.iterator().next().endsWith(".yml"))
                            descriptor = new File(packagePath + "/" + files.iterator().next());
                        else{
                            throw new MalformattedElementException("Descriptor files not found");
                        }
                    }else{
                        //TODO support also other PathType
                        throw new MethodNotImplementedException("Path Type not currently supported");
                    }

                    mapper = new ObjectMapper(new YAMLFactory());
                    DescriptorTemplate descriptorTemplate = mapper.readValue(descriptor, DescriptorTemplate.class);

                    //Check if already present, uploaded for example from another project
                    String descriptorId = descriptorTemplate.getMetadata().getDescriptorId();
                    Optional<SoObject> soInfoObjectOptional = fivegrowthObjectRepository.findByDescriptorIdAndVersionAndSoId(descriptorId, descriptorTemplate.getMetadata().getVersion(), so.getManoId());
                    if(!soInfoObjectOptional.isPresent()){
                        Nsd nsd = SolToIfaTranslator.generateNsDescriptor(descriptorTemplate);
                        OnboardNsdRequest onboardNsdRequest = new OnboardNsdRequest(nsd, null);
                        String soInfoObjectId = soClient.onboardNsd(onboardNsdRequest); //soInfoObjectId == nsdId

                        //Store descriptor file
                        File descriptorFile = new File(soDir, nsd.getNsdIdentifier() + ".json");
                        mapper = new ObjectMapper();
                        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                        mapper.writeValue(descriptorFile, nsd);

                        SoObject infoObject = new SoObject();
                        infoObject.setVersion(nsd.getVersion());
                        infoObject.setDescriptorId(nsd.getNsdIdentifier());
                        infoObject.setSoId(so.getManoId());
                        infoObject.setType(ManoObjectType.NS);
                        infoObject.setEpoch(Instant.now().getEpochSecond());
                        infoObject.setCatalogueId(nsdInfoId);
                        infoObject.setPath(descriptorFile.toPath().toString());
                        fivegrowthObjectRepository.saveAndFlush(infoObject);
                        log.info("{} - Successfully uploaded Nsd with ID {} and version {} for project {}", so.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject());
                    }

                    sendNotification(new NsdOnBoardingNotificationMessage(nsdInfoId, notification.getNsdId(), notification.getNsdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.SUCCESSFULLY_DONE,
                            so.getManoId(), null,null));
                }catch (Exception e) {
                    log.error("{} - Could not onboard Nsd: {}", so.getManoId(), e.getMessage());
                    log.debug("Error details: ", e);
                    sendNotification(new NsdOnBoardingNotificationMessage(nsdInfoId, notification.getNsdId(), notification.getNsdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.FAILED,
                            so.getManoId(), null,null));
                }
            } else {
                if (this.getPluginOperationalState() == PluginOperationalState.DISABLED || this.getPluginOperationalState() == PluginOperationalState.DELETING) {
                    log.debug("{} - NSD onboarding skipped", so.getManoId());
                    sendNotification(new NsdOnBoardingNotificationMessage(nsdInfoId, notification.getNsdId(), notification.getNsdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                            so.getManoId(), null,null));
                }
            }
        }else if(notification.getScope() == ScopeType.SYNC){
            log.info("{} - Received Sync Pkg onboarding notification for NSD with ID {} and version {} for project {} : {}", so.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(), notification.getOpStatus().toString());
            if(notification.getPluginId().equals(so.getManoId())) {
                Optional<SoObject> soObjectOptional = fivegrowthObjectRepository.findByDescriptorIdAndVersionAndSoId(notification.getNsdId(), notification.getNsdVersion(), so.getManoId());
                if (soObjectOptional.isPresent()) {
                    SoObject soObject = soObjectOptional.get();
                    if (notification.getOpStatus().equals(OperationStatus.SUCCESSFULLY_DONE)) {
                        soObject.setCatalogueId(notification.getNsdInfoId());
                        fivegrowthObjectRepository.saveAndFlush(soObject);
                    }
                }
            }
        }
    }

    @Override
    public void acceptNsdChangeNotification(NsdChangeNotificationMessage notification) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        try {
            String json = mapper.writeValueAsString(notification);
            log.debug("RECEIVED MESSAGE: " + json);
        } catch (JsonProcessingException e) {
            log.error("Unable to parse received nsdDeletionNotificationMessage: " + e.getMessage());
        }

        if (notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received NSD change notification for Nsd with ID {} and version {} for project {}", so.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject());
            if (Utilities.isTargetMano(notification.getSiteOrManoIds(), so) && this.getPluginOperationalState() == PluginOperationalState.ENABLED) {
                try {
                    //Onboard new package
                    String packagePath = notification.getPackagePath().getKey();
                    File descriptor;
                    File metadata;
                    Set<String> metadataFileNames;
                    String metadataFileName;
                    if(notification.getPackagePath().getValue().equals(PathType.LOCAL.toString())) {
                        Set<String> files = Utilities.listFiles(packagePath);
                        if(files.size() != 1) {
                            metadataFileNames = Utilities.listFiles(packagePath + "/TOSCA-Metadata/");
                            //Consider only one metadata file is present
                            metadataFileName = metadataFileNames.stream().filter(name -> name.endsWith(".meta")).findFirst().get();
                            metadata = new File(packagePath + "/TOSCA-Metadata/" + metadataFileName);
                            descriptor = new File(packagePath + "/" + Utilities.getMainServiceTemplateFromMetadata(metadata));
                        }
                        else if (files.iterator().next().endsWith(".yaml") || files.iterator().next().endsWith(".yml"))
                            descriptor = new File(packagePath + "/" + files.iterator().next());
                        else{
                            throw new MalformattedElementException("Descriptor files not found");
                        }
                    }else{
                        //TODO support also other PathType
                        throw new MethodNotImplementedException("Path Type not currently supported");
                    }

                    mapper = new ObjectMapper(new YAMLFactory());
                    DescriptorTemplate descriptorTemplate = mapper.readValue(descriptor, DescriptorTemplate.class);
                    log.info("{} - Onboarding Nsd with ID {} and version {} for project {}", so.getManoId(), descriptorTemplate.getMetadata().getDescriptorId(), descriptorTemplate.getMetadata().getVersion(), notification.getProject());
                    Optional<SoObject> soInfoObjectOptional = fivegrowthObjectRepository.findByCatalogueIdAndSoId(notification.getNsdInfoId(), so.getManoId());
                    if(soInfoObjectOptional.isPresent()) {//It must be present if so is the target mano
                        //Delete old descriptor if id or version are different
                        SoObject infoObject = soInfoObjectOptional.get();
                        if(!infoObject.getDescriptorId().equals(descriptorTemplate.getMetadata().getDescriptorId()) || !infoObject.getVersion().equals(descriptorTemplate.getMetadata().getVersion()))
                            soClient.deleteNsd(infoObject.getDescriptorId(), infoObject.getVersion());

                        Nsd nsd = SolToIfaTranslator.generateNsDescriptor(descriptorTemplate);
                        OnboardNsdRequest onboardNsdRequest = new OnboardNsdRequest(nsd, null);
                        String soInfoObjectId = soClient.onboardNsd(onboardNsdRequest); //soInfoObjectId == nsdId

                        //Store descriptor file
                        File descriptorFile = new File(soDir, nsd.getNsdIdentifier() + ".json");
                        mapper = new ObjectMapper();
                        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                        mapper.writeValue(descriptorFile, nsd);

                        infoObject.setVersion(nsd.getVersion());
                        infoObject.setDescriptorId(nsd.getNsdIdentifier());
                        infoObject.setEpoch(Instant.now().getEpochSecond());
                        infoObject.setPath(descriptorFile.toPath().toString());
                        fivegrowthObjectRepository.saveAndFlush(infoObject);
                        log.info("{} - Successfully uploaded Nsd with ID {} and version {} for project {}", so.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject());

                        sendNotification(new NsdChangeNotificationMessage(notification.getNsdInfoId(), descriptorTemplate.getMetadata().getDescriptorId(), descriptorTemplate.getMetadata().getVersion(), notification.getProject(),
                                notification.getOperationId(), ScopeType.REMOTE, OperationStatus.SUCCESSFULLY_DONE,
                                so.getManoId(), null,null));
                    }
                }catch (Exception e) {
                    log.error("{} - Could not change Nsd: {}", so.getManoId(), e.getMessage());
                    log.debug("Error details: ", e);
                    sendNotification(new NsdChangeNotificationMessage(notification.getNsdInfoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.FAILED,
                            so.getManoId(), null, null));
                }
            }else {
                if (this.getPluginOperationalState() == PluginOperationalState.DISABLED || this.getPluginOperationalState() == PluginOperationalState.DELETING) {
                    log.debug("{} - NSD change skipped", so.getManoId());
                    sendNotification(new NsdChangeNotificationMessage(notification.getNsdInfoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                            so.getManoId(), null,null));
                }
            }
        }else if(notification.getScope() == ScopeType.SYNC){
            log.info("{} - Received Sync Pkg change notification for NSD with ID {} and version {} for project {} : {}", so.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(), notification.getOpStatus().toString());
            //NS sync disabled
        }
    }

    @Override
    public void acceptNsdDeletionNotification(NsdDeletionNotificationMessage notification) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        try {
            String json = mapper.writeValueAsString(notification);
            log.debug("RECEIVED MESSAGE: " + json);
        } catch (JsonProcessingException e) {
            log.error("Unable to parse received nsdDeletionNotificationMessage: " + e.getMessage());
        }

        if (notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received Nsd deletion notification for Nsd with ID {} and version {} for project {}", so.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject());
            Optional<SoObject> soObjectOptional = fivegrowthObjectRepository.findByDescriptorIdAndVersionAndSoId(notification.getNsdId(), notification.getNsdVersion(), so.getManoId());
            if (Utilities.isTargetMano(notification.getSiteOrManoIds(), so) && soObjectOptional.isPresent() && this.getPluginOperationalState() == PluginOperationalState.ENABLED) {
                try {
                    soClient.deleteNsd(notification.getNsdId(), notification.getNsdVersion());
                    fivegrowthObjectRepository.delete(soObjectOptional.get());
                    log.info("{} - Successfully deleted Nsd with ID {} and version {} for project {}", so.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject());
                    sendNotification(new NsdDeletionNotificationMessage(notification.getNsdInfoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.SUCCESSFULLY_DONE,
                            so.getManoId(), null));
                } catch (Exception e) {
                    log.error("{} - Could not delete Nsd: {}", so.getManoId(), e.getMessage());
                    log.debug("Error details: ", e);
                    sendNotification(new NsdDeletionNotificationMessage(notification.getNsdInfoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.FAILED,
                            so.getManoId(), null));
                }
            } else {
                if (this.getPluginOperationalState() == PluginOperationalState.DISABLED || this.getPluginOperationalState() == PluginOperationalState.DELETING) {
                    log.debug("{} - NSD deletion skipped", so.getManoId());
                    sendNotification(new NsdDeletionNotificationMessage(notification.getNsdInfoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                            so.getManoId(), null));
                }
            }
        } else if(notification.getScope() == ScopeType.SYNC){
            log.info("{} - Received Sync Pkg deletion notification for NSD with ID {} and version {} for project {} : {}", so.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(), notification.getOpStatus().toString());
            //TODO handle notification
        }
    }

    @Override
    public void acceptVnfPkgOnBoardingNotification(VnfPkgOnBoardingNotificationMessage notification) {
        log.debug("Body: {}", notification);
        if (notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received Vnfd onboarding notification for Vnfd with ID {} and version {} for project {}", so.getManoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject());
            if (Utilities.isTargetMano(notification.getSiteOrManoIds(), so)) {//No need to send notification back if the plugin is not in the target MANOs
                log.debug("{} - VNF Pkg onboarding skipped", so.getManoId());
                sendNotification(new VnfPkgOnBoardingNotificationMessage(notification.getVnfPkgInfoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject(),
                        notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                        so.getManoId(), null,null));
            }
        }else if(notification.getScope() == ScopeType.SYNC){
            log.info("{} - Received Sync Pkg onboarding notification for Vnfd with ID {} and version {} for project {} : {}", so.getManoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject(), notification.getOpStatus().toString());
            if(notification.getPluginId().equals(so.getManoId())) {
                Optional<SoObject> soObjectOptional = fivegrowthObjectRepository.findByDescriptorIdAndVersionAndSoId(notification.getVnfdId(), notification.getVnfdVersion(), so.getManoId());
                if (soObjectOptional.isPresent()) {
                    SoObject soObject = soObjectOptional.get();
                    if (notification.getOpStatus().equals(OperationStatus.SUCCESSFULLY_DONE)) {
                        soObject.setCatalogueId(notification.getVnfPkgInfoId());
                        fivegrowthObjectRepository.saveAndFlush(soObject);
                    }
                }
            }
        }
    }

    @Override
    public void acceptVnfPkgChangeNotification(VnfPkgChangeNotificationMessage notification) {
        log.debug("Body: {}", notification);
        log.info("{} - Received VNF Pkg change notification", so.getManoId());
    }

    @Override
    public void acceptVnfPkgDeletionNotification(VnfPkgDeletionNotificationMessage notification) {
        log.debug("Body: {}", notification);
        if (notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received Vnfd deletion notification for Vnfd with ID {} and version {} for project {}", so.getManoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject());
            //Onboarding from NBI is not enabled, thus there is no need to delete the package and send notification
        }else if(notification.getScope() == ScopeType.SYNC){
            log.info("{} - Received Sync Pkg deletion notification for Vnfd with ID {} and version {} for project {} : {}", so.getManoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject(), notification.getOpStatus().toString());
        }
    }

    @Override
    public void acceptPnfdOnBoardingNotification(PnfdOnBoardingNotificationMessage notification) {
        log.debug("Body: {}", notification);
        log.info("{} - Received PNFD onboarding notification", so.getManoId());
    }

    @Override
    public void acceptPnfdDeletionNotification(PnfdDeletionNotificationMessage notification) {
        log.debug("Body: {}", notification);
        log.info("{} - Received PNFD deletion notification", so.getManoId());
    }
}
