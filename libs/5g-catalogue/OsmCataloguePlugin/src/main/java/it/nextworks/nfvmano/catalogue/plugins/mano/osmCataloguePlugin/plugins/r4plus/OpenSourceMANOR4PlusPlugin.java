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
package it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.plugins.r4plus;

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
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.MANO;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.MANOPlugin;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.MANOType;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.common.ToscaArchiveBuilder;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.osm.OSM;
import it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.common.*;
import it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.elements.OsmTranslationInformation;
import it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.repos.OsmInfoObjectRepository;
import it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.repos.TranslationInformationRepository;
import it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.translators.OsmToSolTranslator;
import it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.translators.SolToOsmTranslator;
import it.nextworks.nfvmano.libs.common.elements.KeyValuePair;
import it.nextworks.nfvmano.libs.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.descriptors.templates.DescriptorTemplate;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VNF.VNFNode;
import it.nextworks.nfvmano.libs.osmr4PlusClient.OSMr4PlusClient;
import it.nextworks.nfvmano.libs.osmr4PlusClient.utilities.OSMHttpResponse;
import it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor.OsmNSPackage;
import it.nextworks.nfvmano.libs.osmr4PlusDataModel.osmManagement.IdObject;
import it.nextworks.nfvmano.libs.osmr4PlusDataModel.osmManagement.OsmInfoObject;
import it.nextworks.nfvmano.libs.osmr4PlusDataModel.osmManagement.OsmObjectType;
import it.nextworks.nfvmano.libs.osmr4PlusDataModel.osmManagement.RecordState;
import it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor.OsmVNFPackage;
import org.rauschig.jarchivelib.ArchiveFormat;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.rauschig.jarchivelib.CompressionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class OpenSourceMANOR4PlusPlugin extends MANOPlugin {

    private static final Logger log = LoggerFactory.getLogger(OpenSourceMANOR4PlusPlugin.class);
    private Path osmDirPath;
    private File osmDir;
    private Path tmpDirPath;
    private final File logo;
    private final OSM osm;
    private OSMr4PlusClient osmClient;
    private OsmInfoObjectRepository osmInfoObjectRepository;
    private TranslationInformationRepository translationInformationRepository;
    private long syncPeriod;

    private boolean useVimNetworkName;

    public OpenSourceMANOR4PlusPlugin(MANOType manoType, MANO mano, String kafkaBootstrapServers,
                                      OsmInfoObjectRepository osmInfoObjectRepository, TranslationInformationRepository translationInformationRepository, String localTopic, String remoteTopic,
                                      KafkaTemplate<String, String> kafkaTemplate, Path osmDirPath, Path tmpDir, Path logoPath, boolean manoSync, long syncPeriod, boolean useVimNetworkName) {
        super(manoType, mano, kafkaBootstrapServers, localTopic, remoteTopic, kafkaTemplate, manoSync);
        if (MANOType.OSMR4 != manoType && MANOType.OSMR5 != manoType && MANOType.OSMR6 != manoType && MANOType.OSMR7 != manoType && MANOType.OSMR8 != manoType) {
            throw new IllegalArgumentException("OSM R4+ plugin requires an OSM R4+ type MANO");
        }
        osm = (OSM) mano;
        this.osmInfoObjectRepository = osmInfoObjectRepository;
        this.translationInformationRepository = translationInformationRepository;
        this.osmDirPath = osmDirPath;
        this.logo = new File(logoPath.toUri());
        this.syncPeriod = syncPeriod;
        this.tmpDirPath = tmpDir;
        this.useVimNetworkName = useVimNetworkName;
    }

    private static <T> List<T> parseResponse(OSMHttpResponse httpResponse, String opId, Class<T> clazz) throws FailedOperationException {
        List<T> objList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        if ((httpResponse.getCode() >= 300) || (httpResponse.getCode() < 200)) {
            log.error("Unexpected response code {}: {}. OpId: {}", httpResponse.getCode(), httpResponse.getMessage(), opId);
            log.debug("Response content: {}", httpResponse.getContent());
            throw new FailedOperationException(String.format("Unexpected code from MANO: %s", httpResponse.getCode()));
        }
        // else, code is 2XX
        if (clazz == null)
            return null;
        if (httpResponse.getFilePath() != null)
            objList.add(clazz.cast(httpResponse.getFilePath().toFile()));
        else
            try {
                Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + clazz.getName() + ";");
                objList = Arrays.asList(mapper.readValue(httpResponse.getContent(), arrayClass));
            } catch (Exception e) {
                log.error("Could not obtain objects form response: {}", e.getMessage());
                e.printStackTrace();
                throw new FailedOperationException("Could not obtain objects from MANO response");
            }
        return objList;
    }

    @Override
    public void init() {
        super.init();
        try {
            osmDirPath = Paths.get(osmDirPath.toString(), "/" + osm.getManoId());
            Files.createDirectories(osmDirPath);
            osmDir = osmDirPath.toFile();
        } catch (IOException e) {
            log.error("Could not initialize tmp directory: " + e.getMessage());
        }
        initOsmConnection();
    }

    private void initOsmConnection() {
        osmClient = new OSMr4PlusClient(osm.getIpAddress(), osm.getPort(), osm.getUsername(), osm.getPassword(), osm.getProject());
        log.info("{} - OSM R4+ instance addr {}:{}, user {}, project {} connected", osm.getManoId(), osm.getIpAddress(), osm.getPort(), osm.getUsername(),
                osm.getProject());

        //Scheduling OSM synchronization
        if(isManoSync()) {
            log.info("{} - Starting runtime synchronization, sync period every {} minutes", osm.getManoId(), syncPeriod);
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            Runnable syncTask = this::RuntimeSynchronization;
            scheduler.scheduleAtFixedRate(syncTask, syncPeriod, syncPeriod, TimeUnit.MINUTES);
        }
    }

    @Override
    public Map<String, List<String>> getAllVnfd(String project){
        log.info("{} - Startup synchronization, started retrieving Osm Vnf Pkgs from project {}", osm.getManoId(), project);
        Long startSync = Instant.now().getEpochSecond();

        try {
            updateDB(true, false, project);
        } catch(FailedOperationException e){
            log.error("{} - {}", osm.getManoId(), e.getMessage());
            return null;
        }

        //Delete OSM Pkg no longer present in OSM and add to ids list the others
        Map<String, List<String>> ids = new HashMap<>();
        List<OsmInfoObject> osmInfoObjectList = osmInfoObjectRepository.findByOsmIdAndType(osm.getManoId(), OsmObjectType.VNF);
        for(OsmInfoObject osmInfoObj : osmInfoObjectList){
            if(osmInfoObj.getEpoch().compareTo(startSync) < 0){
                log.info("{} - Osm Vnf Pkg with descriptor ID {} and version {} no longer present in project {}", osm.getManoId(), osmInfoObj.getDescriptorId(), osmInfoObj.getVersion(), project);
                osmInfoObjectRepository.delete(osmInfoObj);
                List<OsmTranslationInformation> translationInformationList = translationInformationRepository.findByOsmInfoIdAndOsmManoId(osmInfoObj.getId(), osm.getManoId());
                for(OsmTranslationInformation translationInformation : translationInformationList)
                    translationInformationRepository.delete(translationInformation);
                //TODO delete corresponding folder
            }else{
                String catDescriptorId = getCatDescriptorId(osmInfoObj.getDescriptorId(), osmInfoObj.getVersion());
                if(catDescriptorId != null)
                    ids.computeIfAbsent(catDescriptorId, k -> new ArrayList<>()).add(osmInfoObj.getVersion());//ids.put(catDescriptorId, osmInfoObj.getVersion());
                else
                    ids.computeIfAbsent(osmInfoObj.getDescriptorId(), k -> new ArrayList<>()).add(osmInfoObj.getVersion());//ids.put(osmInfoObj.getDescriptorId(), osmInfoObj.getVersion());
            }
        }
        log.info("{} - Startup synchronization, finished retrieving Osm Vnf Pkgs from project {}", osm.getManoId(), project);

        return ids;
    }

    @Override
    public Map<String, List<String>> getAllNsd(String project) {
        log.info("{} - Startup synchronization, started retrieving Osm Ns Pkgs from project {}", osm.getManoId(), project);
        Long startSync = Instant.now().getEpochSecond();

        try {
            updateDB(false, true, project);
        } catch(FailedOperationException e){
            log.error("{} - {}", osm.getManoId(), e.getMessage());
            return null;
        }

        //Delete OSM Pkg no longer present in OSM and add to ids list the others
        Map<String, List<String>> ids = new HashMap<>();
        List<OsmInfoObject> osmInfoObjectList = osmInfoObjectRepository.findByOsmIdAndType(osm.getManoId(), OsmObjectType.NS);
        for(OsmInfoObject osmInfoObj : osmInfoObjectList){
            if(osmInfoObj.getEpoch().compareTo(startSync) < 0){
                log.info("{} - Osm Ns Pkg with descriptor ID {} and version {} no longer present in project {}", osm.getManoId(), osmInfoObj.getDescriptorId(), osmInfoObj.getVersion(), project);
                osmInfoObjectRepository.delete(osmInfoObj);
                List<OsmTranslationInformation> translationInformationList = translationInformationRepository.findByOsmInfoIdAndOsmManoId(osmInfoObj.getId(), osm.getManoId());
                for(OsmTranslationInformation translationInformation : translationInformationList)
                    translationInformationRepository.delete(translationInformation);
                //TODO delete corresponding folder
            }else{
                String catDescriptorId = getCatDescriptorId(osmInfoObj.getDescriptorId(), osmInfoObj.getVersion());
                if(catDescriptorId != null)
                    ids.computeIfAbsent(catDescriptorId, k -> new ArrayList<>()).add(osmInfoObj.getVersion());//ids.put(catDescriptorId, osmInfoObj.getVersion());
                else
                    ids.computeIfAbsent(osmInfoObj.getDescriptorId(), k -> new ArrayList<>()).add(osmInfoObj.getVersion());//ids.put(osmInfoObj.getDescriptorId(), osmInfoObj.getVersion());
            }
        }
        log.info("{} - Startup synchronization, finished retrieving Osm Ns Pkgs from project {}", osm.getManoId(), project);

        return ids;
    }

    @Override
    public KeyValuePair getTranslatedPkgPath(String descriptorId, String descriptorVersion, String project){
        log.info("{} - Translating Osm Pkg with descriptor ID {} and version {} from project {}", osm.getManoId(), descriptorId, descriptorVersion, project);
        String osmDescriptorId = getOsmDescriptorId(descriptorId, descriptorVersion);
        if(osmDescriptorId == null)
            osmDescriptorId = descriptorId;
        Optional<OsmInfoObject> osmInfoObject = osmInfoObjectRepository.findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, descriptorVersion, osm.getManoId());
        String pkgPath = null;
        try{
            if(osmInfoObject.isPresent()){
                if(osmInfoObject.get().getType().equals(OsmObjectType.VNF))
                    pkgPath = createVnfPkgTosca(osmInfoObject.get());
                else
                    pkgPath = createNsPkgTosca(osmInfoObject.get());
            }
        }catch(Exception e){
            log.error("{} - Unable to generate TOSCA Pkg with descriptor ID {} and version {} for project {}: {}", osm.getManoId(), descriptorId, descriptorVersion, project, e.getMessage());
            log.debug(null, e);
            return  null;
        }
        log.info("{} - Uploading TOSCA Pkg with descriptor ID {} and version {} to project {}", osm.getManoId(), descriptorId, descriptorVersion, project);

        return new KeyValuePair(pkgPath, PathType.LOCAL.toString());
    }

    @Override
    public void notifyOnboarding(String infoId, String descriptorId, String descriptorVersion, String project, OperationStatus opStatus){
        log.info("{} - Received Sync Pkg onboarding notification for Descriptor with ID {} and version {} for project {} : {}", osm.getManoId(), descriptorId, descriptorVersion, project, opStatus.toString());
        String osmDescriptorId = getOsmDescriptorId(descriptorId, descriptorVersion);
        if(osmDescriptorId == null)
            osmDescriptorId = descriptorId;
        Optional<OsmInfoObject> osmInfoObjectOptional = osmInfoObjectRepository.findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, descriptorVersion, osm.getManoId());
        if(osmInfoObjectOptional.isPresent()){
            OsmInfoObject osmInfoObject = osmInfoObjectOptional.get();
            if(opStatus.equals(OperationStatus.SUCCESSFULLY_DONE))
                translationInformationRepository.saveAndFlush(new OsmTranslationInformation(infoId, osmInfoObject.getId(), descriptorId, osmInfoObject.getDescriptorId(), descriptorVersion, osm.getManoId()));
            /*
            else
                osmInfoObjectRepository.delete(osmInfoObject);
             */
        }
    }

    @Override
    public void notifyDelete(String infoId, String descriptorId, String descriptorVersion, String project, OperationStatus opStatus){
        log.info("{} - Received Sync Pkg deletion notification for Descriptor with ID {} and version {} for project {} : {}", osm.getManoId(), descriptorId, descriptorVersion, project, opStatus.toString());
    }

    @Override
    public String getManoPkgInfoId(String cataloguePkgInfoId){
       return  getOsmInfoId(cataloguePkgInfoId);
    }

    @Override
    public void RuntimeSynchronization(){
        log.info("{} - Runtime synchronization, started retrieving Osm Vnf and Ns Pkgs", osm.getManoId());
        Long startSync = Instant.now().getEpochSecond();

        try {
            updateDB(true, true, "all");
        } catch(FailedOperationException e){
            log.error("{} - {}", osm.getManoId(), e.getMessage());
            return;
        }

        List<OsmInfoObject> osmVnfList = osmInfoObjectRepository.findByOsmIdAndType(osm.getManoId(), OsmObjectType.VNF);
        List<OsmInfoObject> osmNsList = osmInfoObjectRepository.findByOsmIdAndType(osm.getManoId(), OsmObjectType.NS);
        UUID operationId;
        String pkgPath;

        for(OsmInfoObject osmVnf : osmVnfList){
            //Upload new OSM Vnf Pkg
            if(osmVnf.getState().equals(RecordState.NEW) && osmVnf.getEpoch().compareTo(startSync) >= 0){
                operationId = UUID.randomUUID();
                try {
                    pkgPath = createVnfPkgTosca(osmVnf);
                }catch(Exception e){
                    log.error("{} - Unable to generate TOSCA Vnf Pkg with descriptor ID {} and version {}: {}", osm.getManoId(), osmVnf.getDescriptorId(), osmVnf.getVersion(), e.getMessage());
                    log.debug(null, e);
                    continue;
                }
                log.info("{} - Uploading TOSCA Vnf Pkg with descriptor ID {} and version {}", osm.getManoId(), osmVnf.getDescriptorId(), osmVnf.getVersion());
                    sendNotification(new VnfPkgOnBoardingNotificationMessage(null, osmVnf.getDescriptorId(), osmVnf.getVersion(), "all",
                            operationId, ScopeType.SYNC, OperationStatus.SENT, osm.getManoId(), null, new KeyValuePair(pkgPath, PathType.LOCAL.toString())));
            }else if (osmVnf.getState().equals(RecordState.CHANGED) && osmVnf.getEpoch().compareTo(startSync) >= 0){//Update OSM Vnf Pkg
                operationId = UUID.randomUUID();
                try {
                    pkgPath = createVnfPkgTosca(osmVnf);
                }catch(Exception e){
                    log.error("{} - Unable to generate TOSCA Vnf Pkg with descriptor ID {} and version {}: {}", osm.getManoId(), osmVnf.getDescriptorId(), osmVnf.getVersion(), e.getMessage());
                    log.debug(null, e);
                    continue;
                }

                //In case descriptorId and/or version have been changed, use old id and version to notify the change to Catalogue
                List<OsmTranslationInformation> translationInformationList = translationInformationRepository.findByOsmInfoIdAndOsmManoId(osmVnf.getId(), osm.getManoId());
                String catDescriptorId;
                String catVersion;
                if(!translationInformationList.isEmpty()) {
                    catDescriptorId = translationInformationList.get(0).getCatDescriptorId();
                    catVersion = translationInformationList.get(0).getDescriptorVersion();
                }else{
                    catDescriptorId = osmVnf.getDescriptorId();
                    catVersion = osmVnf.getVersion();
                }

                log.info("{} - Updating TOSCA Vnf Pkg with descriptor ID {} and version {}", osm.getManoId(), osmVnf.getDescriptorId(), osmVnf.getVersion());
                sendNotification(new VnfPkgChangeNotificationMessage(null, catDescriptorId, catVersion, "all",
                        operationId, ScopeType.SYNC, OperationStatus.SENT, osm.getManoId(), null, new KeyValuePair(pkgPath, PathType.LOCAL.toString())));
            }
        }

        for(OsmInfoObject osmNs : osmNsList){
            //Delete OSM Ns Pkg no longer present in OSM
            if(osmNs.getEpoch().compareTo(startSync) < 0){
                log.info("{} - Osm Ns Pkg with descriptor ID {} and version {} no longer present, deleting it", osm.getManoId(), osmNs.getDescriptorId(), osmNs.getVersion());
                operationId = UUID.randomUUID();
                String catDescriptorId = getCatDescriptorId(osmNs.getDescriptorId(), osmNs.getVersion());
                if(catDescriptorId == null)
                    catDescriptorId = osmNs.getDescriptorId();
                sendNotification(new NsdDeletionNotificationMessage(null, catDescriptorId, osmNs.getVersion(), "all",
                        operationId, ScopeType.SYNC, OperationStatus.SENT, osm.getManoId(), null));
                osmInfoObjectRepository.delete(osmNs);
                List<OsmTranslationInformation> translationInformationList = translationInformationRepository.findByOsmInfoIdAndOsmManoId(osmNs.getId(), osm.getManoId());
                for(OsmTranslationInformation translationInformation : translationInformationList)
                    translationInformationRepository.delete(translationInformation);
                //TODO delete corresponding folder
            } else if (osmNs.getState().equals(RecordState.NEW)){
                //Upload new OSM Ns Pkg
                operationId = UUID.randomUUID();
                try {
                    pkgPath = createNsPkgTosca(osmNs);
                }catch(Exception e){
                    log.error("{} - Unable to generate TOSCA Ns Pkg with descriptor ID {} and version {}: {}", osm.getManoId(), osmNs.getDescriptorId(), osmNs.getVersion(), e.getMessage());
                    log.debug(null, e);
                    continue;
                }
                log.info("{} - Uploading TOSCA Ns Pkg with descriptor ID {} and version {}", osm.getManoId(), osmNs.getDescriptorId(), osmNs.getVersion());
                sendNotification(new NsdOnBoardingNotificationMessage(null, osmNs.getDescriptorId(), osmNs.getVersion(), "all",
                            operationId, ScopeType.SYNC, OperationStatus.SENT, osm.getManoId(), null, new KeyValuePair(pkgPath, PathType.LOCAL.toString())));
            }else if (osmNs.getState().equals(RecordState.CHANGED)){//Update OSM Ns Pkg
                operationId = UUID.randomUUID();
                try {
                    pkgPath = createNsPkgTosca(osmNs);
                }catch(Exception e){
                    log.error("{} - Unable to generate TOSCA Ns Pkg with descriptor ID {} and version {}: {}", osm.getManoId(), osmNs.getDescriptorId(), osmNs.getVersion(), e.getMessage());
                    log.debug(null, e);
                    continue;
                }

                //In case descriptorId and/or version have been changed, use old id and version to notify the change to Catalogue
                List<OsmTranslationInformation> translationInformationList = translationInformationRepository.findByOsmInfoIdAndOsmManoId(osmNs.getId(), osm.getManoId());
                String catDescriptorId;
                String catVersion;
                if(!translationInformationList.isEmpty()) {
                    catDescriptorId = translationInformationList.get(0).getCatDescriptorId();
                    catVersion = translationInformationList.get(0).getDescriptorVersion();
                }else{
                    catDescriptorId = osmNs.getDescriptorId();
                    catVersion = osmNs.getVersion();
                }

                log.info("{} - Updating TOSCA Ns Pkg with descriptor ID {} and version {}", osm.getManoId(), osmNs.getDescriptorId(), osmNs.getVersion());
                sendNotification(new NsdChangeNotificationMessage(null, catDescriptorId, catVersion, "all",
                        operationId, ScopeType.SYNC, OperationStatus.SENT, osm.getManoId(), null, new KeyValuePair(pkgPath, PathType.LOCAL.toString())));
            }
        }

        for(OsmInfoObject osmVnf : osmVnfList){
            //Delete OSM Vnf Pkg no longer present in OSM
            if(osmVnf.getEpoch().compareTo(startSync) < 0){
                log.info("{} - Osm Ns Pkg with descriptor ID {} and version {} no longer present, deleting it", osm.getManoId(), osmVnf.getDescriptorId(), osmVnf.getVersion());
                operationId = UUID.randomUUID();
                String catDescriptorId = getCatDescriptorId(osmVnf.getDescriptorId(), osmVnf.getVersion());
                if(catDescriptorId == null)
                    catDescriptorId = osmVnf.getDescriptorId();
                sendNotification(new VnfPkgDeletionNotificationMessage(null, catDescriptorId, osmVnf.getVersion(), "all",
                            operationId, ScopeType.SYNC, OperationStatus.SENT, osm.getManoId(), null));
                osmInfoObjectRepository.delete(osmVnf);
                List<OsmTranslationInformation> translationInformationList = translationInformationRepository.findByOsmInfoIdAndOsmManoId(osmVnf.getId(), osm.getManoId());
                for(OsmTranslationInformation translationInformation : translationInformationList)
                    translationInformationRepository.delete(translationInformation);
                //TODO delete corresponding folder
            }
        }
        log.info("{} - Runtime synchronization, finished retrieving Osm Vnf and Ns Pkgs", osm.getManoId());
    }

    private void updateDB(boolean updateVNF, boolean updateNS, String project) throws FailedOperationException{
        if(!updateVNF && !updateNS)
            return;

        OSMHttpResponse response;
        Optional<OsmInfoObject> osmInfoObject;
        List<OsmInfoObject> packageInfoList = new ArrayList<>();
        List<OsmInfoObject> aux;
        List<String> vnfPackageInfoIdList = new ArrayList<>();

        if(updateVNF) {
            log.info("{} - Updating Vnfs DB for project {}", osm.getManoId(), project);
            //Retrieve VNFPkgInfos
            response = osmClient.getVnfPackageList();
            aux = parseResponse(response, null, OsmInfoObject.class);
            packageInfoList.addAll(aux);
            vnfPackageInfoIdList = aux.stream().map(OsmInfoObject::getId).collect(Collectors.toList());
        }
        if(updateNS){
            log.info("{} - Updating Nss DB for project {}", osm.getManoId(), project);
            //Retrieve NSPkgInfos
            response = osmClient.getNsdInfoList();
            aux = parseResponse(response, null, OsmInfoObject.class);
            packageInfoList.addAll(aux);
        }

        for(OsmInfoObject packageInfo : packageInfoList) {
            try {
                osmInfoObject = osmInfoObjectRepository.findById(packageInfo.getId());
                if (osmInfoObject.isPresent()) {
                    log.info("{} - OSM Pkg with descriptor ID {} and version {} already present in project {}", osm.getManoId(), packageInfo.getDescriptorId(), packageInfo.getVersion(), project);
                    if (packageInfo.getAdmin().getModified().compareTo(osmInfoObject.get().getAdmin().getModified()) <= 0) {
                        osmInfoObject.get().setState(RecordState.UNCHANGED);
                        osmInfoObject.get().setEpoch(Instant.now().getEpochSecond());
                        osmInfoObjectRepository.saveAndFlush(osmInfoObject.get());
                        continue;
                    } else {
                        packageInfo.setState(RecordState.CHANGED);
                        if(!updateVNF || !updateNS) //avoid updating the Modified filed in case of startup sync, during startup sync in fact it is not possible to detect changes. If this filed is updated, during the runtime sync will be not possible to detect those changes as well
                            packageInfo.getAdmin().setModified(osmInfoObject.get().getAdmin().getModified());
                    }
                }else
                    packageInfo.setState(RecordState.NEW);

                log.info("{} - Retrieving OSM Pkg with descriptor ID {} and version {} from project {}", osm.getManoId(), packageInfo.getDescriptorId(), packageInfo.getVersion(), project);
                if(vnfPackageInfoIdList.contains(packageInfo.getId())) {
                    response = osmClient.getVnfPackageContent(packageInfo.getId(), osmDirPath.toString());
                    packageInfo.setType(OsmObjectType.VNF);
                }
                else {
                    response = osmClient.getNsdContent(packageInfo.getId(), osmDirPath.toString());
                    packageInfo.setType(OsmObjectType.NS);
                }
                parseResponse(response, null, null);
                File archive = new File(osmDirPath.toString() + "/" + packageInfo.getId() + ".tar.gz");
                Archiver archiver = ArchiverFactory.createArchiver(ArchiveFormat.TAR, CompressionType.GZIP);
                archiver.extract(archive, new File(osmDirPath.toString()));
                packageInfo.setEpoch(Instant.now().getEpochSecond());
                packageInfo.setOsmId(osm.getManoId());
                osmInfoObjectRepository.saveAndFlush(packageInfo);
            } catch (FailedOperationException | IOException | IllegalStateException | IllegalArgumentException e) {
                log.error("{} - Sync error: {}", osm.getManoId(), e.getMessage());
                log.debug(null, e);
            }
        }
    }

    private String createVnfPkgTosca (OsmInfoObject vnfPackageInfo) throws MalformattedElementException, IllegalStateException, IOException, IllegalArgumentException{
        log.info("{} - Creating TOSCA VNF Descriptor with ID {} and version {}", osm.getManoId(), vnfPackageInfo.getDescriptorId(), vnfPackageInfo.getVersion());
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        OsmVNFPackage vnfPkgOsm = mapper.readValue(new File(osmDirPath.toString() + "/" + vnfPackageInfo.getAdmin().getStorage().getDescriptor()), OsmVNFPackage.class);
        DescriptorTemplate vnfdSol = OsmToSolTranslator.generateVnfDescriptor(vnfPkgOsm);
        VNFNode vnfNode = vnfdSol.getTopologyTemplate().getVNFNodes().values().iterator().next();
        File cloudInit = null;
        if(vnfNode.getInterfaces() != null){
            cloudInit = new File(osmDirPath.toString() + "/" + vnfPackageInfo.getAdmin().getStorage().getPkgDir() + "/cloud_init/" + vnfNode.getInterfaces().getVnflcm().getInstantiate().getImplementation());
        }
        log.info("{} - Creating TOSCA VNF Pkg with descriptor ID {} and version {}", osm.getManoId(), vnfPackageInfo.getDescriptorId(), vnfPackageInfo.getVersion());
        return ToscaArchiveBuilder.createVNFCSAR(vnfPackageInfo.getId(), vnfdSol, tmpDirPath.toString(), cloudInit);
    }

    private String createNsPkgTosca (OsmInfoObject nsPackageInfo) throws MalformattedElementException, IllegalStateException, IOException, IllegalArgumentException{
        log.info("{} - Creating TOSCA NS Descriptor with ID {} and version {}", osm.getManoId(), nsPackageInfo.getDescriptorId(), nsPackageInfo.getVersion());
        List<OsmInfoObject> vnfInfoList = osmInfoObjectRepository.findByOsmIdAndType(osm.getManoId(), OsmObjectType.VNF);
        List<OsmTranslationInformation> translationInformationList = translationInformationRepository.findByOsmManoId(osm.getManoId());
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        OsmNSPackage nsPkgOsm = mapper.readValue(new File(osmDirPath.toString() + "/" + nsPackageInfo.getAdmin().getStorage().getDescriptor()), OsmNSPackage.class);
        DescriptorTemplate nsdSol = OsmToSolTranslator.generateNsDescriptor(nsPkgOsm, vnfInfoList, translationInformationList, osmDirPath);
        log.info("{} - Creating TOSCA NS Pkg with descriptor ID {} and version {}", osm.getManoId(), nsPackageInfo.getDescriptorId(), nsPackageInfo.getVersion());
        return ToscaArchiveBuilder.createNSCSAR(nsPackageInfo.getId(), nsdSol, tmpDirPath.toString());
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

        if (notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received NSD onboarding notification for Nsd with ID {} and version {} for project {}", osm.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject());
            String nsdInfoId = notification.getNsdInfoId();
            boolean forceOnboard = false;
            if(nsdInfoId.endsWith("_update")){
                forceOnboard = true;
                nsdInfoId = nsdInfoId.replace("_update", "");
            }
            if (Utilities.isTargetMano(notification.getSiteOrManoIds(), osm) && this.getPluginOperationalState() == PluginOperationalState.ENABLED) {
                try {
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
                    String osmDescriptorId = getOsmDescriptorId(descriptorTemplate.getMetadata().getDescriptorId(), descriptorTemplate.getMetadata().getVersion());
                    if(osmDescriptorId == null)
                        osmDescriptorId = descriptorTemplate.getMetadata().getDescriptorId();
                    Optional<OsmInfoObject> osmInfoObjectOptional = osmInfoObjectRepository.findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, descriptorTemplate.getMetadata().getVersion(), osm.getManoId());
                    String osmInfoObjectId;
                    if(!osmInfoObjectOptional.isPresent() || forceOnboard){//forceOnboard used in RuntimeNsChange
                        //osmInfoObjectOptional.ifPresent(osmInfoObject -> osmInfoObjectRepository.delete(osmInfoObject));
                        //OSM cannot permit to onboard descriptors with same ID but different versions
                        if(isTheFirstTranslationInformationEntry(descriptorTemplate.getMetadata().getDescriptorId()))
                            osmDescriptorId = descriptorTemplate.getMetadata().getDescriptorId();
                        else{
                            osmDescriptorId = UUID.randomUUID().toString();
                            descriptorTemplate.getMetadata().setDescriptorId(osmDescriptorId);
                            //Consider only one NSNode present
                            descriptorTemplate.getTopologyTemplate().getNSNodes().values().iterator().next().getProperties().setDescriptorId(osmDescriptorId);
                            descriptorTemplate.getTopologyTemplate().getNSNodes().values().iterator().next().getProperties().setInvariantId(osmDescriptorId);
                        }

                        List<DescriptorTemplate> includedVnfds = new ArrayList<>();
                        for (KeyValuePair vnfPathPair : notification.getIncludedVnfds().values()) {
                            if (vnfPathPair.getValue().equals(PathType.LOCAL.toString())) {
                                packagePath = vnfPathPair.getKey();
                                metadataFileNames = Utilities.listFiles(packagePath + "/TOSCA-Metadata/");
                                //Consider only one metadata file is present
                                metadataFileName = metadataFileNames.stream().filter(name -> name.endsWith(".meta")).findFirst().get();
                                metadata = new File(packagePath + "/TOSCA-Metadata/" + metadataFileName);
                                descriptor = new File(packagePath + "/" + Utilities.getMainServiceTemplateFromMetadata(metadata));
                            } else {
                                //TODO support also other PathType
                                throw new MethodNotImplementedException("Path Type not currently supported");
                            }
                            includedVnfds.add(mapper.readValue(descriptor, DescriptorTemplate.class));
                        }

                        //OSM cannot permit to onboard descriptors with same ID but different versions
                        List<VNFNode> vnfNodeList = new ArrayList<>(descriptorTemplate.getTopologyTemplate().getVNFNodes().values());
                        for(DescriptorTemplate vnfd : includedVnfds){
                            String translatedVnfdId = getOsmDescriptorId(vnfd.getMetadata().getDescriptorId(), vnfd.getMetadata().getVersion());
                            if(translatedVnfdId == null)
                                throw new FailedOperationException("Could not find the corresponding Descriptor ID in OSM");
                            for(VNFNode vnfNode : vnfNodeList){
                                if(vnfNode.getProperties().getDescriptorId().equals(vnfd.getMetadata().getDescriptorId()))
                                    vnfNode.getProperties().setDescriptorId(translatedVnfdId);
                            }
                            vnfd.getMetadata().setDescriptorId(translatedVnfdId);
                            vnfd.getTopologyTemplate().getVNFNodes().values().iterator().next().getProperties().setDescriptorId(translatedVnfdId);
                        }

                        OsmNSPackage packageData = SolToOsmTranslator.generateNsDescriptor(descriptorTemplate, includedVnfds, logo, useVimNetworkName, MANOType.OSMR4);

                        mapper = new ObjectMapper();
                        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

                        log.debug("{} - Translated NSD: {} ", osm.getManoId(), mapper.writeValueAsString(packageData));

                        packagePath = notification.getPackagePath().getKey();
                        Set<String> fileNames = Utilities.listFiles(packagePath);
                        File monitoring = null;
                        List<File> scripts = new ArrayList<>();
                        //Consider only one manifest file is present if the NS is a Pkg
                        if(fileNames.stream().filter(name -> name.endsWith(".mf")).count() != 0) {
                            String manifestPath = fileNames.stream().filter(name -> name.endsWith(".mf")).findFirst().get();
                            File mf = new File(packagePath + "/" + manifestPath);

                            String monitoringPath = Utilities.getMonitoringFromManifest(mf);

                            if (monitoringPath != null) {
                                monitoring = new File(packagePath + "/" + monitoringPath);
                            } else {
                                log.debug("{} - No monitoring file found for NSD with ID {} and version {}", osm.getManoId(), notification.getNsdId(), notification.getNsdVersion());
                            }

                            List<String> scriptPaths = Utilities.getScriptsFromManifest(mf);
                            if (scriptPaths.size() > 0) {
                                for(String scriptPath : scriptPaths)
                                    scripts.add(new File(packagePath + "/" + scriptPath));
                            } else {
                                log.debug("{} - No script files found for NS with ID {} and version {}", osm.getManoId(), notification.getNsdId(), notification.getNsdVersion());
                            }
                        }

                        OsmArchiveBuilder archiver = new OsmArchiveBuilder(osmDir, logo);
                        File archive = archiver.makeNewArchive(packageData, "Generated by NXW Catalogue", scripts, monitoring);

                        osmInfoObjectId = onBoardNsPackage(archive, notification.getOperationId().toString());

                        log.info("{} - Successfully uploaded Nsd with ID {} and version {} for project {}", osm.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject());
                    }else
                        osmInfoObjectId = osmInfoObjectOptional.get().getId();

                    translationInformationRepository.saveAndFlush(new OsmTranslationInformation(nsdInfoId, osmInfoObjectId, notification.getNsdId(), osmDescriptorId, notification.getNsdVersion(), osm.getManoId()));
                    sendNotification(new NsdOnBoardingNotificationMessage(nsdInfoId, notification.getNsdId(), notification.getNsdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.SUCCESSFULLY_DONE,
                            osm.getManoId(), null,null));
                } catch (Exception e) {
                    log.error("{} - Could not onboard Nsd: {}", osm.getManoId(), e.getMessage());
                    log.debug("Error details: ", e);
                    sendNotification(new NsdOnBoardingNotificationMessage(nsdInfoId, notification.getNsdId(), notification.getNsdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.FAILED,
                            osm.getManoId(), null,null));
                }
            } else {
                if (this.getPluginOperationalState() == PluginOperationalState.DISABLED || this.getPluginOperationalState() == PluginOperationalState.DELETING) {
                    log.debug("{} - NSD onboarding skipped", osm.getManoId());
                    sendNotification(new NsdOnBoardingNotificationMessage(nsdInfoId, notification.getNsdId(), notification.getNsdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                            osm.getManoId(), null,null));
                }
            }
        }else if(notification.getScope() == ScopeType.SYNC){
            log.info("{} - Received Sync Pkg onboarding notification for NSD with ID {} and version {} for project {} : {}", osm.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(), notification.getOpStatus().toString());
            if(notification.getPluginId().equals(osm.getManoId())) {
                String osmDescriptorId = getOsmDescriptorId(notification.getNsdId(), notification.getNsdVersion());
                if (osmDescriptorId == null)
                    osmDescriptorId = notification.getNsdId();
                Optional<OsmInfoObject> osmInfoObject = osmInfoObjectRepository.findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, notification.getNsdVersion(), osm.getManoId());
                if (osmInfoObject.isPresent()){
                    if(notification.getOpStatus().equals(OperationStatus.SUCCESSFULLY_DONE))
                        translationInformationRepository.saveAndFlush(new OsmTranslationInformation(notification.getNsdInfoId(), osmInfoObject.get().getId(), notification.getNsdId(), osmInfoObject.get().getDescriptorId(), notification.getNsdVersion(), osm.getManoId()));
                    /*
                    else
                        osmInfoObjectRepository.delete(osmInfoObject.get());
                     */
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
            log.error("Unable to parse received nsdChangeNotificationMessage: " + e.getMessage());
        }
        if (notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received NSD change notification for Nsd with ID {} and version {} for project {}", osm.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject());
            if (Utilities.isTargetMano(notification.getSiteOrManoIds(), osm) && this.getPluginOperationalState() == PluginOperationalState.ENABLED) {
                try {
                    //Delete old package
                    String osmInfoPkgId = getOsmInfoId(notification.getNsdInfoId());
                    if(osmInfoPkgId == null)
                        throw new FailedOperationException("Could not find the corresponding Info ID in OSM");
                    if(!deleteTranslationInformationEntry(notification.getNsdInfoId()))
                        throw new FailedOperationException("Could not delete the specified entry");
                    if(!translationInformationContainsOsmInfoId(osmInfoPkgId))//If pkg is not present in other projects
                        deleteNsd(osmInfoPkgId, notification.getOperationId().toString());
                    log.info("{} - Successfully deleted Nsd with ID {} and version {} for project {}", osm.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject());

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
                    log.info("{} - Onboarding Nsd with ID {} and version {} for project {}", osm.getManoId(), descriptorTemplate.getMetadata().getDescriptorId(), descriptorTemplate.getMetadata().getVersion(), notification.getProject());
                    //Check if already present, uploaded for example from another project
                    String osmDescriptorId = getOsmDescriptorId(descriptorTemplate.getMetadata().getDescriptorId(), descriptorTemplate.getMetadata().getVersion());
                    if(osmDescriptorId == null)
                        osmDescriptorId = descriptorTemplate.getMetadata().getDescriptorId();
                    Optional<OsmInfoObject> osmInfoObjectOptional = osmInfoObjectRepository.findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, descriptorTemplate.getMetadata().getVersion(), osm.getManoId());
                    String osmInfoObjectId;
                    if(!osmInfoObjectOptional.isPresent()) {
                        //OSM cannot permit to onboard descriptors with same ID but different versions
                        if(isTheFirstTranslationInformationEntry(descriptorTemplate.getMetadata().getDescriptorId()))
                            osmDescriptorId = descriptorTemplate.getMetadata().getDescriptorId();
                        else{
                            osmDescriptorId = UUID.randomUUID().toString();
                            descriptorTemplate.getMetadata().setDescriptorId(osmDescriptorId);
                            //Consider only one NSNode present
                            descriptorTemplate.getTopologyTemplate().getNSNodes().values().iterator().next().getProperties().setDescriptorId(osmDescriptorId);
                            descriptorTemplate.getTopologyTemplate().getNSNodes().values().iterator().next().getProperties().setInvariantId(osmDescriptorId);
                        }

                        List<DescriptorTemplate> includedVnfds = new ArrayList<>();
                        for (KeyValuePair vnfPathPair : notification.getIncludedVnfds().values()) {
                            if (vnfPathPair.getValue().equals(PathType.LOCAL.toString())) {
                                packagePath = vnfPathPair.getKey();
                                metadataFileNames = Utilities.listFiles(packagePath + "/TOSCA-Metadata/");
                                //Consider only one metadata file is present
                                metadataFileName = metadataFileNames.stream().filter(name -> name.endsWith(".meta")).findFirst().get();
                                metadata = new File(packagePath + "/TOSCA-Metadata/" + metadataFileName);
                                descriptor = new File(packagePath + "/" + Utilities.getMainServiceTemplateFromMetadata(metadata));
                            } else {
                                //TODO support also other PathType
                                throw new MethodNotImplementedException("Path Type not currently supported");
                            }
                            includedVnfds.add(mapper.readValue(descriptor, DescriptorTemplate.class));
                        }

                        //OSM cannot permit to onboard descriptors with same ID but different versions
                        List<VNFNode> vnfNodeList = new ArrayList<>(descriptorTemplate.getTopologyTemplate().getVNFNodes().values());
                        for(DescriptorTemplate vnfd : includedVnfds){
                            String translatedVnfdId = getOsmDescriptorId(vnfd.getMetadata().getDescriptorId(), vnfd.getMetadata().getVersion());
                            if(translatedVnfdId == null)
                                throw new FailedOperationException("Could not find the corresponding Descriptor ID in OSM");
                            for(VNFNode vnfNode : vnfNodeList){
                                if(vnfNode.getProperties().getDescriptorId().equals(vnfd.getMetadata().getDescriptorId()))
                                    vnfNode.getProperties().setDescriptorId(translatedVnfdId);
                            }
                            vnfd.getMetadata().setDescriptorId(translatedVnfdId);
                            vnfd.getTopologyTemplate().getVNFNodes().values().iterator().next().getProperties().setDescriptorId(translatedVnfdId);
                        }

                        OsmNSPackage packageData = SolToOsmTranslator.generateNsDescriptor(descriptorTemplate, includedVnfds, logo, useVimNetworkName, MANOType.OSMR4);

                        mapper = new ObjectMapper();
                        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

                        log.debug("{} - Translated NSD: {} ", osm.getManoId(), mapper.writeValueAsString(packageData));

                        packagePath = notification.getPackagePath().getKey();
                        Set<String> fileNames = Utilities.listFiles(packagePath);
                        File monitoring = null;
                        List<File> scripts = new ArrayList<>();
                        //Consider only one manifest file is present if the NS is a Pkg
                        if(fileNames.stream().filter(name -> name.endsWith(".mf")).count() != 0) {
                            String manifestPath = fileNames.stream().filter(name -> name.endsWith(".mf")).findFirst().get();
                            File mf = new File(packagePath + "/" + manifestPath);

                            String monitoringPath = Utilities.getMonitoringFromManifest(mf);

                            if (monitoringPath != null) {
                                monitoring = new File(packagePath + "/" + monitoringPath);
                            } else {
                                log.debug("{} - No monitoring file found for NSD with ID {} and version {}", osm.getManoId(), descriptorTemplate.getMetadata().getDescriptorId(), descriptorTemplate.getMetadata().getVersion());
                            }

                            List<String> scriptPaths = Utilities.getScriptsFromManifest(mf);
                            if (scriptPaths.size() > 0) {
                                for(String scriptPath : scriptPaths)
                                    scripts.add(new File(packagePath + "/" + scriptPath));
                            } else {
                                log.debug("{} - No script files found for NS with ID {} and version {}", osm.getManoId(), descriptorTemplate.getMetadata().getDescriptorId(), descriptorTemplate.getMetadata().getVersion());
                            }
                        }

                        OsmArchiveBuilder archiver = new OsmArchiveBuilder(osmDir, logo);
                        File archive = archiver.makeNewArchive(packageData, "Generated by NXW Catalogue", scripts, monitoring);

                        osmInfoObjectId = onBoardNsPackage(archive, notification.getOperationId().toString());

                        log.info("{} - Successfully uploaded Nsd with ID {} and version {} for project {}", osm.getManoId(), descriptorTemplate.getMetadata().getDescriptorId(), descriptorTemplate.getMetadata().getVersion(), notification.getProject());
                    }else
                        osmInfoObjectId = osmInfoObjectOptional.get().getId();

                    translationInformationRepository.saveAndFlush(new OsmTranslationInformation(notification.getNsdInfoId(), osmInfoObjectId, descriptorTemplate.getMetadata().getDescriptorId(), osmDescriptorId, descriptorTemplate.getMetadata().getVersion(), osm.getManoId()));
                    sendNotification(new NsdChangeNotificationMessage(notification.getNsdInfoId(), descriptorTemplate.getMetadata().getDescriptorId(), descriptorTemplate.getMetadata().getVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.SUCCESSFULLY_DONE,
                            osm.getManoId(), null,null));
                }catch (Exception e) {
                        log.error("{} - Could not change Nsd: {}", osm.getManoId(), e.getMessage());
                        log.debug("Error details: ", e);
                        sendNotification(new NsdChangeNotificationMessage(notification.getNsdInfoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(),
                                notification.getOperationId(), ScopeType.REMOTE, OperationStatus.FAILED,
                                osm.getManoId(), null, null));
                }
            }else {
                if (this.getPluginOperationalState() == PluginOperationalState.DISABLED || this.getPluginOperationalState() == PluginOperationalState.DELETING) {
                    log.debug("{} - NSD change skipped", osm.getManoId());
                    sendNotification(new NsdChangeNotificationMessage(notification.getNsdInfoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                            osm.getManoId(), null,null));
                }
            }
        }else if(notification.getScope() == ScopeType.SYNC){
            log.info("{} - Received Sync Pkg change notification for NSD with ID {} and version {} for project {} : {}", osm.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(), notification.getOpStatus().toString());
            if(notification.getPluginId().equals(osm.getManoId())) {
                String osmDescriptorId = getOsmDescriptorId(notification.getNsdId(), notification.getNsdVersion());
                if (osmDescriptorId == null)
                    osmDescriptorId = notification.getNsdId();
                Optional<OsmInfoObject> osmInfoObject = osmInfoObjectRepository.findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, notification.getNsdVersion(), osm.getManoId());
                if (osmInfoObject.isPresent()){
                    if(notification.getOpStatus().equals(OperationStatus.SUCCESSFULLY_DONE)) {
                        //Delete old record
                        Optional<OsmTranslationInformation> translationInformationOptional = translationInformationRepository.findByCatInfoIdAndOsmManoId(notification.getNsdInfoId(), osm.getManoId());
                        translationInformationOptional.ifPresent(osmTranslationInformation -> translationInformationRepository.delete(osmTranslationInformation));
                        //Save new record
                        translationInformationRepository.saveAndFlush(new OsmTranslationInformation(notification.getNsdInfoId(), osmInfoObject.get().getId(), notification.getNsdId(), osmInfoObject.get().getDescriptorId(), notification.getNsdVersion(), osm.getManoId()));
                    }
                    /*
                    else
                        osmInfoObjectRepository.delete(osmInfoObject.get());
                     */
                }
            }
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
            log.info("{} - Received Nsd deletion notification for Nsd with ID {} and version {} for project {}", osm.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject());
            if (Utilities.isTargetMano(notification.getSiteOrManoIds(), osm) && translationInformationContainsCatInfoId(notification.getNsdInfoId()) && this.getPluginOperationalState() == PluginOperationalState.ENABLED) {
                try {
                    String osmInfoPkgId = getOsmInfoId(notification.getNsdInfoId());
                    if(osmInfoPkgId == null)
                        throw new FailedOperationException("Could not find the corresponding Info ID in OSM");
                    if(!deleteTranslationInformationEntry(notification.getNsdInfoId()))
                        throw new FailedOperationException("Could not delete the specified entry");
                    if(!translationInformationContainsOsmInfoId(osmInfoPkgId))//If pkg is not present in other projects
                        deleteNsd(osmInfoPkgId, notification.getOperationId().toString());
                    log.info("{} - Successfully deleted Nsd with ID {} and version {} for project {}", osm.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject());
                    sendNotification(new NsdDeletionNotificationMessage(notification.getNsdInfoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.SUCCESSFULLY_DONE,
                            osm.getManoId(), null));
                } catch (Exception e) {
                    log.error("{} - Could not delete Nsd: {}", osm.getManoId(), e.getMessage());
                    log.debug("Error details: ", e);
                    sendNotification(new NsdDeletionNotificationMessage(notification.getNsdInfoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.FAILED,
                            osm.getManoId(), null));
                }
            } else {
                if (this.getPluginOperationalState() == PluginOperationalState.DISABLED || this.getPluginOperationalState() == PluginOperationalState.DELETING) {
                    log.debug("{} - NSD deletion skipped", osm.getManoId());
                    sendNotification(new NsdDeletionNotificationMessage(notification.getNsdInfoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                            osm.getManoId(), null));
                }
            }
        }else if(notification.getScope() == ScopeType.SYNC){
            log.info("{} - Received Sync Pkg deletion notification for NSD with ID {} and version {} for project {} : {}", osm.getManoId(), notification.getNsdId(), notification.getNsdVersion(), notification.getProject(), notification.getOpStatus().toString());
        }
    }

    @Override
    public void acceptVnfPkgOnBoardingNotification(VnfPkgOnBoardingNotificationMessage notification) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        try {
            String json = mapper.writeValueAsString(notification);
            log.debug("RECEIVED MESSAGE: " + json);
        } catch (JsonProcessingException e) {
            log.error("Unable to parse received vnfPkgOnboardingNotificationMessage: " + e.getMessage());
        }
        String vnfPkgInfoId = notification.getVnfPkgInfoId();
        boolean forceOnboard = false;
        if(vnfPkgInfoId.endsWith("_update")){
            forceOnboard = true;
            vnfPkgInfoId = vnfPkgInfoId.replace("_update", "");
        }

        if (notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received Vnfd onboarding notification for Vnfd with ID {} and version {} for project {}", osm.getManoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject());
            if (Utilities.isTargetMano(notification.getSiteOrManoIds(), osm) && this.getPluginOperationalState() == PluginOperationalState.ENABLED) {
                try {
                    String packagePath = notification.getPackagePath().getKey();
                    File descriptor;
                    if(notification.getPackagePath().getValue().equals(PathType.LOCAL.toString())) {
                        Set<String> metadataFileNames = Utilities.listFiles(packagePath + "/TOSCA-Metadata/");
                        //Consider only one metada file is present
                        String metadataFileName = metadataFileNames.stream().filter(name -> name.endsWith(".meta")).findFirst().get();
                        File metadata = new File(packagePath + "/TOSCA-Metadata/" + metadataFileName);
                        descriptor = new File(packagePath + "/" + Utilities.getMainServiceTemplateFromMetadata(metadata));
                    }else{
                        //TODO support also other PathType
                        throw new MethodNotImplementedException("Path Type not currently supported");
                    }

                    mapper = new ObjectMapper(new YAMLFactory());
                    DescriptorTemplate descriptorTemplate = mapper.readValue(descriptor, DescriptorTemplate.class);

                    //Check if already present, uploaded for example from another project
                    String osmDescriptorId = getOsmDescriptorId(descriptorTemplate.getMetadata().getDescriptorId(), descriptorTemplate.getMetadata().getVersion());
                    if(osmDescriptorId == null)
                        osmDescriptorId = descriptorTemplate.getMetadata().getDescriptorId();
                    Optional<OsmInfoObject> osmInfoObjectOptional = osmInfoObjectRepository.findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, descriptorTemplate.getMetadata().getVersion(), osm.getManoId());
                    String osmInfoObjectId;
                    if(!osmInfoObjectOptional.isPresent() || forceOnboard) {//forceOnboard used in RuntimeVnfChange
                        //OSM cannot permit to onboard descriptors with same ID but different versions
                        if(isTheFirstTranslationInformationEntry(descriptorTemplate.getMetadata().getDescriptorId()))
                            osmDescriptorId = descriptorTemplate.getMetadata().getDescriptorId();
                        else{
                            osmDescriptorId = UUID.randomUUID().toString();
                            descriptorTemplate.getMetadata().setDescriptorId(osmDescriptorId);
                            //Consider only one VNFNode present
                            descriptorTemplate.getTopologyTemplate().getVNFNodes().values().iterator().next().getProperties().setDescriptorId(osmDescriptorId);
                        }

                        OsmVNFPackage packageData = SolToOsmTranslator.generateVnfDescriptor(descriptorTemplate, logo, MANOType.OSMR4);

                        mapper = new ObjectMapper();
                        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

                        log.debug("Translated VNFD: " + mapper.writeValueAsString(packageData));

                        Set<String> fileNames = Utilities.listFiles(packagePath);
                        //TODO take cloudinit file name from descriptor?
                        //Consider only one manifest file is present
                        String manifestPath = fileNames.stream().filter(name -> name.endsWith(".mf")).findFirst().get();
                        File mf = new File(packagePath + "/" + manifestPath);

                        String cloudInitPath = Utilities.getCloudInitFromManifest(mf);
                        String monitoringPath = Utilities.getMonitoringFromManifest(mf);

                        OsmArchiveBuilder archiver = new OsmArchiveBuilder(osmDir, logo);
                        File cloudInit = null;
                        if (cloudInitPath != null) {
                            cloudInit = new File(packagePath + "/" + cloudInitPath);
                        } else {
                            log.debug("{} - No cloud-init file found for VNF with ID {} and version {}", osm.getManoId(), notification.getVnfdId(), notification.getVnfdVersion());
                        }
                        File monitoring = null;
                        if (monitoringPath != null) {
                            monitoring = new File(packagePath + "/" + monitoringPath);
                        } else {
                            log.debug("{} - No monitoring file found for VNF with ID {} and version {}", osm.getManoId(), notification.getVnfdId(), notification.getVnfdVersion());
                        }
                        File archive = archiver.makeNewArchive(packageData, "Generated by NXW Catalogue", cloudInit, monitoring);
                        osmInfoObjectId = onBoardVnfPackage(archive, notification.getOperationId().toString());
                        log.info("{} - Successfully uploaded Vnfd with ID {} and version {} for project {}", osm.getManoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject());
                    }else
                        osmInfoObjectId = osmInfoObjectOptional.get().getId();

                    translationInformationRepository.saveAndFlush(new OsmTranslationInformation(vnfPkgInfoId, osmInfoObjectId, notification.getVnfdId(), osmDescriptorId, notification.getVnfdVersion(), osm.getManoId()));
                    sendNotification(new VnfPkgOnBoardingNotificationMessage(vnfPkgInfoId, notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.SUCCESSFULLY_DONE,
                            osm.getManoId(), null,null));
                } catch (Exception e) {
                    log.error("{} - Could not onboard Vnfd: {}", osm.getManoId(), e.getMessage());
                    log.debug("Error details: ", e);
                    sendNotification(new VnfPkgOnBoardingNotificationMessage(vnfPkgInfoId, notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.FAILED,
                            osm.getManoId(), null,null));
                }
            } else {
                if (this.getPluginOperationalState() == PluginOperationalState.DISABLED || this.getPluginOperationalState() == PluginOperationalState.DELETING) {
                    log.debug("{} - VNF Pkg onboarding skipped", osm.getManoId());
                    sendNotification(new VnfPkgOnBoardingNotificationMessage(vnfPkgInfoId, notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                            osm.getManoId(), null,null));
                }
            }
        }else if(notification.getScope() == ScopeType.SYNC){
            log.info("{} - Received Sync Pkg onboarding notification for Vnfd with ID {} and version {} for project {} : {}", osm.getManoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject(), notification.getOpStatus().toString());
            if(notification.getPluginId().equals(osm.getManoId())) {
                String osmDescriptorId = getOsmDescriptorId(notification.getVnfdId(), notification.getVnfdVersion());
                if(osmDescriptorId == null)
                    osmDescriptorId = notification.getVnfdId();
                Optional<OsmInfoObject> osmInfoObject = osmInfoObjectRepository.findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, notification.getVnfdVersion(), osm.getManoId());
                if(osmInfoObject.isPresent())
                    if(notification.getOpStatus().equals(OperationStatus.SUCCESSFULLY_DONE))
                        translationInformationRepository.saveAndFlush(new OsmTranslationInformation(notification.getVnfPkgInfoId(), osmInfoObject.get().getId(), notification.getVnfdId(), osmInfoObject.get().getDescriptorId(), notification.getVnfdVersion(), osm.getManoId()));
                    /*
                    else
                        osmInfoObjectRepository.delete(osmInfoObject.get());
                     */
            }
        }
    }

    @Override
    public void acceptVnfPkgChangeNotification(VnfPkgChangeNotificationMessage notification) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        try {
            String json = mapper.writeValueAsString(notification);
            log.debug("RECEIVED MESSAGE: " + json);
        } catch (JsonProcessingException e) {
            log.error("Unable to parse received vnfPkgChangeNotificationMessage: " + e.getMessage());
        }
        if (notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received VNF Pkg change notification for Vnfd with ID {} and version {} for project {}", osm.getManoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject());
            if (Utilities.isTargetMano(notification.getSiteOrManoIds(), osm) && this.getPluginOperationalState() == PluginOperationalState.ENABLED) {
                try {
                    //Delete old package
                    String osmInfoPkgId = getOsmInfoId(notification.getVnfPkgInfoId());
                    if(osmInfoPkgId == null)
                        throw new FailedOperationException("Could not find the corresponding Info ID in OSM");
                    if(!deleteTranslationInformationEntry(notification.getVnfPkgInfoId()))
                        throw new FailedOperationException("Could not delete the specified entry");
                    if(!translationInformationContainsOsmInfoId(osmInfoPkgId))//If pkg is not present in other projects
                        deleteVnfd(osmInfoPkgId, notification.getOperationId().toString());
                    log.info("{} - Successfully deleted Vnfd with ID {} and version {} for project {}", osm.getManoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject());

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
                    log.info("{} - Onboarding Vnfd with ID {} and version {} for project {}", osm.getManoId(), descriptorTemplate.getMetadata().getDescriptorId(), descriptorTemplate.getMetadata().getVersion(), notification.getProject());
                    //Check if already present, uploaded for example from another project
                    String osmDescriptorId = getOsmDescriptorId(descriptorTemplate.getMetadata().getDescriptorId(), descriptorTemplate.getMetadata().getVersion());
                    if(osmDescriptorId == null)
                        osmDescriptorId = descriptorTemplate.getMetadata().getDescriptorId();
                    Optional<OsmInfoObject> osmInfoObjectOptional = osmInfoObjectRepository.findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, descriptorTemplate.getMetadata().getVersion(), osm.getManoId());
                    String osmInfoObjectId;
                    if(!osmInfoObjectOptional.isPresent()) {
                        //OSM cannot permit to onboard descriptors with same ID but different versions
                        if(isTheFirstTranslationInformationEntry(descriptorTemplate.getMetadata().getDescriptorId()))
                            osmDescriptorId = descriptorTemplate.getMetadata().getDescriptorId();
                        else{
                            osmDescriptorId = UUID.randomUUID().toString();
                            descriptorTemplate.getMetadata().setDescriptorId(osmDescriptorId);
                            //Consider only one NSNode present
                            descriptorTemplate.getTopologyTemplate().getVNFNodes().values().iterator().next().getProperties().setDescriptorId(osmDescriptorId);
                        }

                        OsmVNFPackage packageData = SolToOsmTranslator.generateVnfDescriptor(descriptorTemplate, logo, MANOType.OSMR4);

                        mapper = new ObjectMapper();
                        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

                        log.debug("Translated VNFD: " + mapper.writeValueAsString(packageData));

                        Set<String> fileNames = Utilities.listFiles(packagePath);
                        //TODO take cloudinit file name from descriptor?
                        //Consider only one manifest file is present
                        String manifestPath = fileNames.stream().filter(name -> name.endsWith(".mf")).findFirst().get();
                        File mf = new File(packagePath + "/" + manifestPath);

                        String cloudInitPath = Utilities.getCloudInitFromManifest(mf);
                        String monitoringPath = Utilities.getMonitoringFromManifest(mf);

                        OsmArchiveBuilder archiver = new OsmArchiveBuilder(osmDir, logo);
                        File cloudInit = null;
                        if (cloudInitPath != null) {
                            cloudInit = new File(packagePath + "/" + cloudInitPath);
                        } else {
                            log.debug("{} - No cloud-init file found for VNF with ID {} and version {}", osm.getManoId(), notification.getVnfdId(), notification.getVnfdVersion());
                        }
                        File monitoring = null;
                        if (monitoringPath != null) {
                            monitoring = new File(packagePath + "/" + monitoringPath);
                        } else {
                            log.debug("{} - No monitoring file found for VNF with ID {} and version {}", osm.getManoId(), notification.getVnfdId(), notification.getVnfdVersion());
                        }
                        File archive = archiver.makeNewArchive(packageData, "Generated by NXW Catalogue", cloudInit, monitoring);
                        osmInfoObjectId = onBoardVnfPackage(archive, notification.getOperationId().toString());
                        log.info("{} - Successfully uploaded Vnfd with ID {} and version {} for project {}", osm.getManoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject());
                    }else
                        osmInfoObjectId = osmInfoObjectOptional.get().getId();

                    translationInformationRepository.saveAndFlush(new OsmTranslationInformation(notification.getVnfPkgInfoId(), osmInfoObjectId, descriptorTemplate.getMetadata().getDescriptorId(), osmDescriptorId, descriptorTemplate.getMetadata().getVersion(), osm.getManoId()));
                    sendNotification(new VnfPkgChangeNotificationMessage(notification.getVnfPkgInfoId(), descriptorTemplate.getMetadata().getDescriptorId(), descriptorTemplate.getMetadata().getVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.SUCCESSFULLY_DONE,
                            osm.getManoId(), null,null));
                }catch (Exception e) {
                    log.error("{} - Could not change VNF Pkg: {}", osm.getManoId(), e.getMessage());
                    log.debug("Error details: ", e);
                    sendNotification(new VnfPkgChangeNotificationMessage(notification.getVnfPkgInfoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.FAILED,
                            osm.getManoId(), null, null));
                }
            }else {
                if (this.getPluginOperationalState() == PluginOperationalState.DISABLED || this.getPluginOperationalState() == PluginOperationalState.DELETING) {
                    log.debug("{} - VNF Pkg change skipped", osm.getManoId());
                    sendNotification(new VnfPkgChangeNotificationMessage(notification.getVnfPkgInfoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                            osm.getManoId(), null,null));
                }
            }
        }else if(notification.getScope() == ScopeType.SYNC){
            log.info("{} - Received Sync Pkg change notification for VNFD with ID {} and version {} for project {} : {}", osm.getManoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject(), notification.getOpStatus().toString());
            if(notification.getPluginId().equals(osm.getManoId())) {
                String osmDescriptorId = getOsmDescriptorId(notification.getVnfdId(), notification.getVnfdVersion());
                if(osmDescriptorId == null)
                    osmDescriptorId = notification.getVnfdId();
                Optional<OsmInfoObject> osmInfoObject = osmInfoObjectRepository.findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, notification.getVnfdVersion(), osm.getManoId());
                if(osmInfoObject.isPresent())
                    if(notification.getOpStatus().equals(OperationStatus.SUCCESSFULLY_DONE)) {
                        //Delete old record
                        Optional<OsmTranslationInformation> translationInformationOptional = translationInformationRepository.findByCatInfoIdAndOsmManoId(notification.getVnfPkgInfoId(), osm.getManoId());
                        translationInformationOptional.ifPresent(osmTranslationInformation -> translationInformationRepository.delete(osmTranslationInformation));
                        //Save new record
                        translationInformationRepository.saveAndFlush(new OsmTranslationInformation(notification.getVnfPkgInfoId(), osmInfoObject.get().getId(), notification.getVnfdId(), osmInfoObject.get().getDescriptorId(), notification.getVnfdVersion(), osm.getManoId()));
                    }
                        /*
                    else
                        osmInfoObjectRepository.delete(osmInfoObject.get());
                     */
            }
        }
    }

    @Override
    public void acceptVnfPkgDeletionNotification(VnfPkgDeletionNotificationMessage notification) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        try {
            String json = mapper.writeValueAsString(notification);
            log.debug("RECEIVED MESSAGE: " + json);
        } catch (JsonProcessingException e) {
            log.error("Unable to parse received vnfPkgDeletionNotificationMessage: " + e.getMessage());
        }
        if (notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received Vnfd deletion notification for Vnfd with ID {} and version {} for project {}", osm.getManoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject());
            if (Utilities.isTargetMano(notification.getSiteOrManoIds(), osm) && translationInformationContainsCatInfoId(notification.getVnfPkgInfoId()) && this.getPluginOperationalState() == PluginOperationalState.ENABLED) {
                try {
                    String osmInfoPkgId = getOsmInfoId(notification.getVnfPkgInfoId());
                    if(osmInfoPkgId == null)
                        throw new FailedOperationException("Could not find the corresponding Info ID in OSM");
                    if(!deleteTranslationInformationEntry(notification.getVnfPkgInfoId()))
                        throw new FailedOperationException("Could bot delete the specified entry");
                    if(!translationInformationContainsOsmInfoId(osmInfoPkgId))//If pkg is not present in other projects
                        deleteVnfd(osmInfoPkgId, notification.getOperationId().toString());
                    log.info("{} - Successfully deleted Vnfd with ID {} and version {} for project {}", osm.getManoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject());
                    sendNotification(new VnfPkgDeletionNotificationMessage(notification.getVnfPkgInfoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.SUCCESSFULLY_DONE,
                            osm.getManoId(), null));
                } catch (Exception e) {
                    log.error("{} - Could not delete Vnfd: {}", osm.getManoId(), e.getMessage());
                    log.debug("Error details: ", e);
                    sendNotification(new VnfPkgDeletionNotificationMessage(notification.getVnfPkgInfoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.FAILED,
                            osm.getManoId(), null));
                }
            } else {
                if (this.getPluginOperationalState() == PluginOperationalState.DISABLED || this.getPluginOperationalState() == PluginOperationalState.DELETING) {
                    log.debug("{} - VNF Pkg deletion skipped",  osm.getManoId());
                    sendNotification(new VnfPkgDeletionNotificationMessage(notification.getVnfPkgInfoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject(),
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                            osm.getManoId(), null));
                }
            }
        }else if(notification.getScope() == ScopeType.SYNC){
            log.info("{} - Received Sync Pkg deletion notification for Vnfd with ID {} and version {} for project {} : {}", osm.getManoId(), notification.getVnfdId(), notification.getVnfdVersion(), notification.getProject(), notification.getOpStatus().toString());
        }
    }

    @Override
    public void acceptPnfdOnBoardingNotification(PnfdOnBoardingNotificationMessage notification) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        try {
            String json = mapper.writeValueAsString(notification);
            log.debug("RECEIVED MESSAGE: " + json);
        } catch (JsonProcessingException e) {
            log.error("Unable to parse received pnfOnboardingNotificationMessage: " + e.getMessage());
        }
        log.info("{} - Received PNFD onboarding notification", osm.getManoId());
    }

    @Override
    public void acceptPnfdDeletionNotification(PnfdDeletionNotificationMessage notification) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        try {
            String json = mapper.writeValueAsString(notification);
            log.debug("RECEIVED MESSAGE: " + json);
        } catch (JsonProcessingException e) {
            log.error("Unable to parse received pnfDeletionNotificationMessage: " + e.getMessage());
        }
        log.info("{} - Received PNFD deletion notification", osm.getManoId());
    }

    private String onBoardNsPackage(File file, String opId) throws FailedOperationException {
        log.info("{} - Onboarding ns package, opId: {}", osm.getManoId(), opId);
        // Create the NS descriptor resource
        OSMHttpResponse httpResponse = osmClient.createNsd();
        IdObject content = parseResponse(httpResponse, opId, IdObject.class).get(0);
        String osmNsdInfoId = content.getId();
        // Upload NS descriptor content
        httpResponse = osmClient.uploadNsdContent(osmNsdInfoId, file);
        try {
            parseResponse(httpResponse, opId, null);
        }catch(FailedOperationException e){
            osmClient.deleteNsd(osmNsdInfoId);
            throw new FailedOperationException(e);
        }
        //Store OsmPkfInfo
        httpResponse = osmClient.getNsdInfo(osmNsdInfoId);
        OsmInfoObject osmInfoObject = parseResponse(httpResponse, opId, OsmInfoObject.class).get(0);
        osmInfoObject.setEpoch(Instant.now().getEpochSecond());
        osmInfoObject.setOsmId(osm.getManoId());
        osmInfoObject.setType(OsmObjectType.NS);
        osmInfoObjectRepository.saveAndFlush(osmInfoObject);
        log.info("{} - Package onboarding successful. OpId: {}", osm.getManoId(), opId);
        return osmNsdInfoId;
    }

    private void deleteNsd(String nsdInfoId, String opId) throws FailedOperationException {
        log.info("{} - Deleting nsd {}, opId: {}", osm.getManoId(), nsdInfoId, opId);
        OSMHttpResponse httpResponse = osmClient.deleteNsd(nsdInfoId);
        parseResponse(httpResponse, opId, null);
        OsmInfoObject osmInfoObject =  osmInfoObjectRepository.findById(nsdInfoId).get();
        osmInfoObjectRepository.delete(osmInfoObject);
        log.info("{} - Nsd deleting successful. OpId: {}", osm.getManoId(), opId);
    }

    List<String> getNsdIdList() throws FailedOperationException {
        log.info("{} - Getting nsd id list", osm.getManoId());
        OSMHttpResponse httpResponse = osmClient.getNsdInfoList();
        List<OsmInfoObject> objList = parseResponse(httpResponse, null, OsmInfoObject.class);
        return objList.stream().map(OsmInfoObject::getDescriptorId).collect(Collectors.toList());
    }

    private String onBoardVnfPackage(File file, String opId) throws FailedOperationException {
        log.info("{} - Onboarding vnf package, opId: {}", osm.getManoId(), opId);
        // Create the VNF descriptor resource
        OSMHttpResponse httpResponse = osmClient.createVnfPackage();
        IdObject content = parseResponse(httpResponse, opId, IdObject.class).get(0);
        String osmVnfdInfoId = content.getId();
        // Upload VNF descriptor content
        httpResponse = osmClient.uploadVnfPackageContent(osmVnfdInfoId, file);
        try {
            parseResponse(httpResponse, opId, null);
        }catch(FailedOperationException e){
            osmClient.deleteVnfPackage(osmVnfdInfoId);
            throw new FailedOperationException(e);
        }
        //Store OsmPkfInfo
        httpResponse = osmClient.getVnfPackageInfo(osmVnfdInfoId);
        OsmInfoObject osmInfoObject = parseResponse(httpResponse, opId, OsmInfoObject.class).get(0);
        osmInfoObject.setEpoch(Instant.now().getEpochSecond());
        osmInfoObject.setOsmId(osm.getManoId());
        osmInfoObject.setType(OsmObjectType.VNF);
        osmInfoObjectRepository.saveAndFlush(osmInfoObject);
        log.info("{} - Package onboarding successful. OpId: {}", osm.getManoId(), opId);
        return osmVnfdInfoId;
    }

    private void deleteVnfd(String vnfdInfoId, String opId) throws FailedOperationException {
        //TODO remove OSM info obj and files
        log.info("{} - Deleting vnfd {}, opId: {}", osm.getManoId(), vnfdInfoId, opId);
        OSMHttpResponse httpResponse = osmClient.deleteVnfPackage(vnfdInfoId);
        parseResponse(httpResponse, opId, null);
        OsmInfoObject osmInfoObject =  osmInfoObjectRepository.findById(vnfdInfoId).get();
        osmInfoObjectRepository.delete(osmInfoObject);
        log.info("{} - Vnfd deleting successful. OpId: {}", osm.getManoId(), opId);
    }

    private List<String> getVnfdIdList() throws FailedOperationException {
        log.info("{} - Getting vnfd id list", osm.getManoId());
        OSMHttpResponse httpResponse = osmClient.getVnfPackageList();
        List<OsmInfoObject> objList = parseResponse(httpResponse, null, OsmInfoObject.class);
        return objList.stream().map(OsmInfoObject::getDescriptorId).collect(Collectors.toList());
    }

    private String getCatDescriptorId(String osmDescriptorId, String descriptorVersion){
        if (osmDescriptorId == null || descriptorVersion == null)
            return null;
        List<OsmTranslationInformation> translationInformationList = translationInformationRepository.findByOsmDescriptorIdAndDescriptorVersionAndOsmManoId(osmDescriptorId, descriptorVersion, osm.getManoId());
        if(translationInformationList.size() != 0)
            return translationInformationList.get(0).getCatDescriptorId();
        return null;
    }


    private String getOsmDescriptorId(String catDescriptorId, String descriptorVersion){
        if (catDescriptorId == null || descriptorVersion == null)
            return null;
        List<OsmTranslationInformation> translationInformationList = translationInformationRepository.findByCatDescriptorIdAndDescriptorVersionAndOsmManoId(catDescriptorId, descriptorVersion, osm.getManoId());
        if(translationInformationList.size() != 0)
            return translationInformationList.get(0).getOsmDescriptorId();
        return null;

    }

    private String getOsmInfoId(String catInfoId){
        if (catInfoId == null)
            return null;
        Optional<OsmTranslationInformation> optionalTranslationInformation = translationInformationRepository.findByCatInfoIdAndOsmManoId(catInfoId, osm.getManoId());
        if(optionalTranslationInformation.isPresent())
            return optionalTranslationInformation.get().getOsmInfoId();
        return null;
    }

    private boolean translationInformationContainsCatInfoId(String catInfoId){
        if (catInfoId == null)
            return false;
        Optional<OsmTranslationInformation> optionalTranslationInformation = translationInformationRepository.findByCatInfoIdAndOsmManoId(catInfoId, osm.getManoId());
        return optionalTranslationInformation.isPresent();
    }

    private boolean translationInformationContainsOsmInfoId(String osmInfoId){
        if (osmInfoId == null)
            return false;
        List<OsmTranslationInformation> translationInformationList = translationInformationRepository.findByOsmInfoIdAndOsmManoId(osmInfoId, osm.getManoId());
        return translationInformationList.size() != 0;
    }

    private boolean deleteTranslationInformationEntry(String catInfoId){
        if (catInfoId == null)
            return false;
        Optional<OsmTranslationInformation> optionalTranslationInformation = translationInformationRepository.findByCatInfoIdAndOsmManoId(catInfoId, osm.getManoId());
        if(optionalTranslationInformation.isPresent()) {
            translationInformationRepository.delete(optionalTranslationInformation.get());
            return true;
        }
        return false;
    }

    private boolean isTheFirstTranslationInformationEntry(String catDescriptorId){
        if (catDescriptorId == null)
            return true;
        List<OsmTranslationInformation> translationInformationList = translationInformationRepository.findByCatDescriptorIdAndOsmManoId(catDescriptorId, osm.getManoId());
        if(translationInformationList.size() != 0) {
            for (OsmTranslationInformation translationInformation : translationInformationList)
                if (translationInformation.getCatDescriptorId().equals(translationInformation.getOsmDescriptorId()))
                    return false;
        }
        return true;
    }
}
