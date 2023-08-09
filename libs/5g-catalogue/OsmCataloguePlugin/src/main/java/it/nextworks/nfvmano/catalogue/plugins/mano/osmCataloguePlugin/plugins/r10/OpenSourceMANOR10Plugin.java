package it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.plugins.r10;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.*;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.PathType;
import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.elements.ScopeType;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.PluginOperationalState;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.MANO;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.MANOPlugin;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.MANOType;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.common.ToscaArchiveBuilder;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.osm.OSM;
import it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.common.OsmArchiveBuilder;
import it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.common.Utilities;
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
import it.nextworks.nfvmano.libs.descriptors.sol006.Cpd;
import it.nextworks.nfvmano.libs.descriptors.sol006.Nsd;
import it.nextworks.nfvmano.libs.descriptors.sol006.Pnfd;
import it.nextworks.nfvmano.libs.descriptors.sol006.Vnfd;
import it.nextworks.nfvmano.libs.osmr10DataModels.nsd.OsmNsWrapper;
import it.nextworks.nfvmano.libs.osmr10DataModels.vnfd.OsmVnfdSol006Wrapper;
import it.nextworks.nfvmano.libs.osmr4PlusClient.OSMr4PlusClient;
import it.nextworks.nfvmano.libs.osmr4PlusClient.utilities.OSMHttpResponse;
import it.nextworks.nfvmano.libs.osmr4PlusDataModel.osmManagement.IdObject;
import it.nextworks.nfvmano.libs.osmr4PlusDataModel.osmManagement.OsmInfoObject;
import it.nextworks.nfvmano.libs.osmr4PlusDataModel.osmManagement.OsmObjectType;
import it.nextworks.nfvmano.libs.osmr4PlusDataModel.osmManagement.RecordState;
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

public class OpenSourceMANOR10Plugin extends MANOPlugin {

    private static final Logger log = LoggerFactory.getLogger(OpenSourceMANOR10Plugin.class);

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

    public OpenSourceMANOR10Plugin(MANOType manoType,
                                   MANO mano,
                                   String kafkaBootstrapServers,
                                   String localTopic,
                                   String remoteTopic,
                                   KafkaTemplate<String, String> kafkaTemplate,
                                   boolean manoSync,
                                   OsmInfoObjectRepository osmInfoObjectRepository,
                                   TranslationInformationRepository translationInformationRepository,
                                   Path osmDirPath,
                                   Path logoPath,
                                   long syncPeriod,
                                   Path tmpDir,
                                   boolean useVimNetworkName) {

        super(manoType, mano, kafkaBootstrapServers, localTopic, remoteTopic, kafkaTemplate, manoSync);

        if(manoType != MANOType.OSMR9 && manoType != MANOType.OSMR10)
            throw new IllegalArgumentException("OSM R10 requires an OSM R9 or R10 type MANO");

        osm = (OSM) mano;
        this.osmInfoObjectRepository = osmInfoObjectRepository;
        this.translationInformationRepository = translationInformationRepository;
        this.osmDirPath = osmDirPath;
        this.logo = new File(logoPath.toUri());
        this.syncPeriod = syncPeriod;
        this.tmpDirPath = tmpDir;
        this.useVimNetworkName = useVimNetworkName;
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
        osmClient = new OSMr4PlusClient(osm.getIpAddress(), osm.getPort(),
                osm.getUsername(), osm.getPassword(), osm.getProject());
        log.info("{} - OSM R10 instance addr {}:{}, user {}, project {} connected",
                osm.getManoId(), osm.getIpAddress(), osm.getPort(), osm.getUsername(), osm.getProject());

        if(isManoSync()) {
            log.info("{} - Starting runtime synchronization, sync period every {} minutes", osm.getManoId(), syncPeriod);
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            Runnable syncTask = this::RuntimeSynchronization;
            scheduler.scheduleAtFixedRate(syncTask, syncPeriod, syncPeriod, TimeUnit.MINUTES);
        }
    }

    private String onBoardNsPackage(File file, String opId) throws FailedOperationException {
        log.info("{} - Onboarding ns package, opId: {}", osm.getManoId(), opId);

        OSMHttpResponse httpResponse = osmClient.createNsd();
        IdObject content = parseResponse(httpResponse, opId, IdObject.class).get(0);
        String osmNsdInfoId = content.getId();

        httpResponse = osmClient.uploadNsdContent(osmNsdInfoId, file);
        try {
            parseResponse(httpResponse, opId, null);
        } catch(FailedOperationException e){
            osmClient.deleteNsd(osmNsdInfoId);
            throw new FailedOperationException(e);
        }

        httpResponse = osmClient.getNsdInfo(osmNsdInfoId);
        OsmInfoObject osmInfoObject = parseResponse(httpResponse, opId, OsmInfoObject.class).get(0);
        osmInfoObject.setEpoch(Instant.now().getEpochSecond());
        osmInfoObject.setOsmId(osm.getManoId());
        osmInfoObject.setType(OsmObjectType.NS);
        osmInfoObjectRepository.saveAndFlush(osmInfoObject);

        log.info("{} - Package onboarding successful. OpId: {}", osm.getManoId(), opId);
        return osmNsdInfoId;
    }

    @Override
    public void acceptNsdOnBoardingNotification(NsdOnBoardingNotificationMessage notification) throws MethodNotImplementedException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        try {
            log.debug("RECEIVED MESSAGE: " + mapper.writeValueAsString(notification));
        } catch (JsonProcessingException e) {
            log.error("Unable to parse received nsdOnboardingNotificationMessage: " + e.getMessage());
        }

        String manoId = osm.getManoId();
        String nsdInfoId = notification.getNsdInfoId();
        String nsdId = notification.getNsdId();
        String version = notification.getNsdVersion();
        String project = notification.getProject();

        if (notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received NSD onboarding notification for Nsd with ID {} and version {} for project {}",
                    manoId, nsdId, version, project);

            boolean forceOnboard = false;
            if (nsdInfoId.endsWith("_update")) {
                forceOnboard = true;
                nsdInfoId = nsdInfoId.replace("_update", "");
            }

            if(Utilities.isTargetMano(notification.getSiteOrManoIds(), osm) &&
                    this.getPluginOperationalState() == PluginOperationalState.ENABLED) {

                try {
                    String packagePath = notification.getPackagePath().getKey();

                    Set<String> metadataFileNames;
                    String metadataFileName;

                    File metadata;
                    File descriptor;

                    if (notification.getPackagePath().getValue().equals(PathType.LOCAL.toString())) {

                        Set<String> files = Utilities.listFiles(packagePath);

                        if (files.size() != 1) {
                            metadataFileNames = Utilities.listFiles(packagePath + "/TOSCA-Metadata/");

                            Optional<String> opt = metadataFileNames.stream()
                                    .filter(name -> name.endsWith(".meta")).findFirst();
                            if (!opt.isPresent())
                                throw new MalformattedElementException("Metadata file not found in " + packagePath +
                                        "/TOSCA-Metadata/" + " for Nsd with ID " + nsdId + " and version " + version +
                                        " for project " + project + ".");
                            metadataFileName = opt.get();

                            metadata = new File(packagePath + "/TOSCA-Metadata/" + metadataFileName);
                            descriptor = new File(packagePath + "/" + Utilities.getMainServiceTemplateFromMetadata(metadata));
                        } else if (files.iterator().next().endsWith(".yaml")
                                || files.iterator().next().endsWith(".yml")
                                || files.iterator().next().endsWith(".json"))
                            descriptor = new File(packagePath + "/" + files.iterator().next());
                        else
                            throw new MalformattedElementException("Descriptor files not found");
                    } else
                        throw new MethodNotImplementedException("Path Type not currently supported");

                    if (descriptor.getName().endsWith(".yaml") || descriptor.getName().endsWith(".yml"))
                        mapper = new ObjectMapper(new YAMLFactory());

                    Nsd tmp = mapper.readValue(descriptor, Nsd.class);
                    if (!tmp.getId().equals(nsdId) || !tmp.getVersion().equals(version))
                        throw new MalformattedElementException("The NSD ID and version specified in the notification " +
                                "(" + nsdId + ", " + version + ") do not match those of the stored descriptor " +
                                "(" + tmp.getId() + ", " + tmp.getVersion() + ").");

                    String osmDescriptorId = getOsmDescriptorId(nsdId, version);
                    if (osmDescriptorId == null)
                        osmDescriptorId = nsdId;

                    String osmInfoObjectId;
                    Optional<OsmInfoObject> osmInfoObjectOptional = osmInfoObjectRepository
                            .findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, version, manoId);
                    if (!osmInfoObjectOptional.isPresent() || forceOnboard) {

                        if(isTheFirstTranslationInformationEntry(nsdId))
                            osmDescriptorId = nsdId;
                        else {
                            osmDescriptorId = UUID.randomUUID().toString();
                            tmp.setId(osmDescriptorId);
                        }

                        List<String> osmVnfdIds = new ArrayList<>();
                        for (KeyValuePair vnfPathPair : notification.getIncludedVnfds().values()) {
                            if (vnfPathPair.getValue().equals(PathType.LOCAL.toString())) {
                                packagePath = vnfPathPair.getKey();
                                metadataFileNames = Utilities.listFiles(packagePath + "/TOSCA-Metadata/");

                                Optional<String> opt = metadataFileNames.stream()
                                        .filter(name -> name.endsWith(".meta")).findFirst();
                                if (!opt.isPresent())
                                    throw new MalformattedElementException("Metadata file not found for included vnf in " +
                                            packagePath + "/TOSCA-Metadata/" + " for Nsd with ID " + nsdId +
                                            " and version " + version + " for project " + project + ".");
                                metadataFileName = opt.get();

                                metadata = new File(packagePath + "/TOSCA-Metadata/" + metadataFileName);
                                descriptor = new File(packagePath + "/" + Utilities.getMainServiceTemplateFromMetadata(metadata));
                            } else
                                throw new MethodNotImplementedException("Path Type not currently supported");

                            if (descriptor.getName().endsWith(".yaml") || descriptor.getName().endsWith(".yml"))
                                mapper = new ObjectMapper(new YAMLFactory());
                            else
                                mapper = new ObjectMapper();

                            Vnfd vnfd = mapper.readValue(descriptor, Vnfd.class);
                            String vnfdOsmDescriptorId = getOsmDescriptorId(vnfd.getId(), vnfd.getVersion());
                            if (vnfdOsmDescriptorId == null)
                                throw new FailedOperationException("Could not find the corresponding Descriptor ID in OSM");

                            osmVnfdIds.add(vnfdOsmDescriptorId);
                        }
                        tmp.setVnfdId(osmVnfdIds);

                        packagePath = notification.getPackagePath().getKey();
                        Set<String> fileNames = Utilities.listFiles(packagePath);

                        File monitoring = null;
                        List<File> scripts = new ArrayList<>();

                        if (fileNames.stream().filter(name -> name.endsWith(".mf")).count() != 0) {
                            String manifestPath = fileNames.stream().filter(name -> name.endsWith(".mf")).findFirst().get();
                            File mf = new File(packagePath + "/" + manifestPath);

                            String monitoringPath = Utilities.getMonitoringFromManifest(mf);

                            if (monitoringPath != null)
                                monitoring = new File(packagePath + "/" + monitoringPath);
                            else
                                log.debug("{} - No monitoring file found for NSD with ID {} and version {}", manoId, nsdId, version);

                            List<String> scriptPaths = Utilities.getScriptsFromManifest(mf);
                            if (scriptPaths.size() > 0) {
                                for (String scriptPath : scriptPaths)
                                    scripts.add(new File(packagePath + "/" + scriptPath));
                            } else
                                log.debug("{} - No script files found for NS with ID {} and version {}", manoId, nsdId, version);
                        }

                        OsmNsWrapper nsd = SolToOsmTranslator.generateNsDescriptor(tmp);
                        YAMLFactory yamlFactory = new YAMLFactory();
                        yamlFactory.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
                        mapper = new ObjectMapper(yamlFactory);
                        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                        log.debug("{} - Translated NSD:\n{}", manoId, mapper.writeValueAsString(nsd));

                        File archive = new OsmArchiveBuilder(osmDir, logo)
                                .makeNewArchive(nsd, "Generated by NXW Catalogue", scripts, monitoring);
                        osmInfoObjectId = onBoardNsPackage(archive, notification.getOperationId().toString());

                        log.info("{} - Successfully uploaded Nsd with ID {} and version {} for project {}", manoId, nsdId, version, project);
                    } else
                        osmInfoObjectId = osmInfoObjectOptional.get().getId();

                    translationInformationRepository.saveAndFlush(new OsmTranslationInformation(nsdInfoId,
                            osmInfoObjectId, nsdId, osmDescriptorId, version, manoId));
                    sendNotification(new NsdOnBoardingNotificationMessage(nsdInfoId, nsdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.SUCCESSFULLY_DONE,
                            manoId, null,null));
                } catch (Exception e) {
                    log.error("{} - Could not onboard Nsd: {}", manoId, e.getMessage());
                    log.debug("Error details: ", e);
                    sendNotification(new NsdOnBoardingNotificationMessage(nsdInfoId, nsdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.FAILED,
                            manoId, null,null));
                }
            } else {
                if (this.getPluginOperationalState() == PluginOperationalState.DISABLED
                        || this.getPluginOperationalState() == PluginOperationalState.DELETING) {
                    log.debug("{} - NSD onboarding skipped", manoId);
                    sendNotification(new NsdOnBoardingNotificationMessage(nsdInfoId, nsdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                            manoId, null,null));
                }
            }
        } else if (notification.getScope() == ScopeType.SYNC) {
            log.info("{} - Received Sync Pkg onboarding notification for NSD with ID {} and version {} for project {} : {}",
                    manoId, nsdId, version, project, notification.getOpStatus().toString());

            if (notification.getPluginId().equals(manoId)) {
                String osmDescriptorId = getOsmDescriptorId(nsdId, version);
                if (osmDescriptorId == null)
                    osmDescriptorId = nsdId;

                Optional<OsmInfoObject> osmInfoObject = osmInfoObjectRepository
                        .findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, version, manoId);
                if (osmInfoObject.isPresent()) {
                    if (notification.getOpStatus().equals(OperationStatus.SUCCESSFULLY_DONE))
                        translationInformationRepository.saveAndFlush(new OsmTranslationInformation(nsdInfoId,
                                osmInfoObject.get().getId(), nsdId, osmInfoObject.get().getDescriptorId(), version, manoId));
                }
            }
        }
    }

    private void deleteNsd(String nsdInfoId, String opId) throws FailedOperationException {
        log.info("{} - Deleting nsd {}, opId: {}", osm.getManoId(), nsdInfoId, opId);

        OSMHttpResponse httpResponse = osmClient.deleteNsd(nsdInfoId);
        parseResponse(httpResponse, opId, null);

        OsmInfoObject osmInfoObject =  osmInfoObjectRepository.findById(nsdInfoId).get();
        String descriptorId = osmInfoObject.getDescriptorId();
        osmInfoObjectRepository.delete(osmInfoObject);

        Utilities.deleteDir(new File(osmDir + "/" + descriptorId));
        Utilities.deleteDir(new File(osmDir + "/" + descriptorId + ".tar.gz"));

        log.info("{} - Nsd deleting successful. OpId: {}", osm.getManoId(), opId);
    }

    @Override
    public void acceptNsdChangeNotification(NsdChangeNotificationMessage notification) throws MethodNotImplementedException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        try {
            log.debug("RECEIVED MESSAGE: " + mapper.writeValueAsString(notification));
        } catch (JsonProcessingException e) {
            log.error("Unable to parse received nsdOnboardingNotificationMessage: " + e.getMessage());
        }

        String manoId = osm.getManoId();
        String nsdInfoId = notification.getNsdInfoId();
        String nsdId = notification.getNsdId();
        String version = notification.getNsdVersion();
        String project = notification.getProject();

        if (notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received NSD change notification for Nsd with ID {} and version {} for project {}",
                    manoId, nsdId, version, project);

            if(Utilities.isTargetMano(notification.getSiteOrManoIds(), osm) &&
                    this.getPluginOperationalState() == PluginOperationalState.ENABLED) {

                try {
                    String osmInfoPkgId = getOsmInfoId(nsdInfoId);
                    if(osmInfoPkgId == null)
                        throw new FailedOperationException("Could not find the corresponding Info ID in OSM");
                    if(!deleteTranslationInformationEntry(nsdInfoId))
                        throw new FailedOperationException("Could not delete the specified entry");
                    if(!translationInformationContainsOsmInfoId(osmInfoPkgId))
                        deleteNsd(osmInfoPkgId, notification.getOperationId().toString());

                    log.info("{} - Successfully deleted Nsd with ID {} and version {} for project {}", manoId, nsdId, version, project);

                    String packagePath = notification.getPackagePath().getKey();

                    Set<String> metadataFileNames;
                    String metadataFileName;

                    File metadata;
                    File descriptor;

                    if (notification.getPackagePath().getValue().equals(PathType.LOCAL.toString())) {

                        Set<String> files = Utilities.listFiles(packagePath);

                        if (files.size() != 1) {
                            metadataFileNames = Utilities.listFiles(packagePath + "/TOSCA-Metadata/");

                            Optional<String> opt = metadataFileNames.stream()
                                    .filter(name -> name.endsWith(".meta")).findFirst();
                            if (!opt.isPresent())
                                throw new MalformattedElementException("Metadata file not found in " + packagePath +
                                        "/TOSCA-Metadata/" + " for Nsd with ID " + nsdId + " and version " + version +
                                        " for project " + project + ".");
                            metadataFileName = opt.get();

                            metadata = new File(packagePath + "/TOSCA-Metadata/" + metadataFileName);
                            descriptor = new File(packagePath + "/" + Utilities.getMainServiceTemplateFromMetadata(metadata));
                        } else if (files.iterator().next().endsWith(".yaml")
                                || files.iterator().next().endsWith(".yml")
                                || files.iterator().next().endsWith(".json"))
                            descriptor = new File(packagePath + "/" + files.iterator().next());
                        else
                            throw new MalformattedElementException("Descriptor files not found");
                    } else
                        throw new MethodNotImplementedException("Path Type not currently supported");

                    if (descriptor.getName().endsWith(".yaml") || descriptor.getName().endsWith(".yml"))
                        mapper = new ObjectMapper(new YAMLFactory());

                    Nsd tmp = mapper.readValue(descriptor, Nsd.class);
                    if (!tmp.getId().equals(nsdId) || !tmp.getVersion().equals(version))
                        throw new MalformattedElementException("The NSD ID and version specified in the notification " +
                                "(" + nsdId + ", " + version + ") do not match those of the stored descriptor " +
                                "(" + tmp.getId() + ", " + tmp.getVersion() + ").");

                    log.info("{} - Onboarding Nsd with ID {} and version {} for project {}", manoId, nsdId, version, project);

                    String osmDescriptorId = getOsmDescriptorId(nsdId, version);
                    if (osmDescriptorId == null)
                        osmDescriptorId = nsdId;

                    String osmInfoObjectId;
                    Optional<OsmInfoObject> osmInfoObjectOptional = osmInfoObjectRepository
                            .findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, version, manoId);

                    if(!osmInfoObjectOptional.isPresent()) {

                        if(isTheFirstTranslationInformationEntry(nsdId))
                            osmDescriptorId = nsdId;
                        else {
                            osmDescriptorId = UUID.randomUUID().toString();
                            tmp.setId(osmDescriptorId);
                        }

                        List<String> osmVnfdIds = new ArrayList<>();
                        for (KeyValuePair vnfPathPair : notification.getIncludedVnfds().values()) {
                            if (vnfPathPair.getValue().equals(PathType.LOCAL.toString())) {
                                packagePath = vnfPathPair.getKey();
                                metadataFileNames = Utilities.listFiles(packagePath + "/TOSCA-Metadata/");

                                Optional<String> opt = metadataFileNames.stream()
                                        .filter(name -> name.endsWith(".meta")).findFirst();
                                if (!opt.isPresent())
                                    throw new MalformattedElementException("Metadata file not found for included vnf in " +
                                            packagePath + "/TOSCA-Metadata/" + " for Nsd with ID " + nsdId +
                                            " and version " + version + " for project " + project + ".");
                                metadataFileName = opt.get();

                                metadata = new File(packagePath + "/TOSCA-Metadata/" + metadataFileName);
                                descriptor = new File(packagePath + "/" + Utilities.getMainServiceTemplateFromMetadata(metadata));
                            } else
                                throw new MethodNotImplementedException("Path Type not currently supported");

                            if (descriptor.getName().endsWith(".yaml") || descriptor.getName().endsWith(".yml"))
                                mapper = new ObjectMapper(new YAMLFactory());
                            else
                                mapper = new ObjectMapper();

                            Vnfd vnfd = mapper.readValue(descriptor, Vnfd.class);
                            String vnfdOsmDescriptorId = getOsmDescriptorId(vnfd.getId(), vnfd.getVersion());
                            if (vnfdOsmDescriptorId == null)
                                throw new FailedOperationException("Could not find the corresponding Descriptor ID in OSM");

                            osmVnfdIds.add(vnfdOsmDescriptorId);
                        }
                        tmp.setVnfdId(osmVnfdIds);

                        packagePath = notification.getPackagePath().getKey();
                        Set<String> fileNames = Utilities.listFiles(packagePath);

                        File monitoring = null;
                        List<File> scripts = new ArrayList<>();

                        if (fileNames.stream().filter(name -> name.endsWith(".mf")).count() != 0) {
                            String manifestPath = fileNames.stream().filter(name -> name.endsWith(".mf")).findFirst().get();
                            File mf = new File(packagePath + "/" + manifestPath);

                            String monitoringPath = Utilities.getMonitoringFromManifest(mf);

                            if (monitoringPath != null)
                                monitoring = new File(packagePath + "/" + monitoringPath);
                            else
                                log.debug("{} - No monitoring file found for NSD with ID {} and version {}", manoId, nsdId, version);

                            List<String> scriptPaths = Utilities.getScriptsFromManifest(mf);
                            if (scriptPaths.size() > 0) {
                                for (String scriptPath : scriptPaths)
                                    scripts.add(new File(packagePath + "/" + scriptPath));
                            } else
                                log.debug("{} - No script files found for NS with ID {} and version {}", manoId, nsdId, version);
                        }

                        OsmNsWrapper nsd = SolToOsmTranslator.generateNsDescriptor(tmp);
                        YAMLFactory yamlFactory = new YAMLFactory();
                        yamlFactory.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
                        mapper = new ObjectMapper(yamlFactory);
                        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                        log.debug("{} - Translated NSD:\n{}", manoId, mapper.writeValueAsString(nsd));

                        File archive = new OsmArchiveBuilder(osmDir, logo)
                                .makeNewArchive(nsd, "Generated by NXW Catalogue", scripts, monitoring);
                        osmInfoObjectId = onBoardNsPackage(archive, notification.getOperationId().toString());

                        log.info("{} - Successfully uploaded Nsd with ID {} and version {} for project {}", manoId, nsdId, version, project);
                    } else
                        osmInfoObjectId = osmInfoObjectOptional.get().getId();

                    translationInformationRepository.saveAndFlush(new OsmTranslationInformation(nsdInfoId,
                            osmInfoObjectId, nsdId, osmDescriptorId, version, manoId));
                    sendNotification(new NsdChangeNotificationMessage(nsdInfoId, nsdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.SUCCESSFULLY_DONE,
                            manoId, null,null));
                } catch (Exception e) {
                    log.error("{} - Could not onboard Nsd: {}", manoId, e.getMessage());
                    log.debug("Error details: ", e);
                    sendNotification(new NsdChangeNotificationMessage(nsdInfoId, nsdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.FAILED,
                            manoId, null,null));
                }
            } else {
                if (this.getPluginOperationalState() == PluginOperationalState.DISABLED
                        || this.getPluginOperationalState() == PluginOperationalState.DELETING) {
                    log.debug("{} - NSD change skipped", manoId);
                    sendNotification(new NsdChangeNotificationMessage(nsdInfoId, nsdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                            manoId, null,null));
                }
            }
        } else if (notification.getScope() == ScopeType.SYNC) {
            log.info("{} - Received Sync Pkg change notification for NSD with ID {} and version {} for project {} : {}",
                    manoId, nsdId, version, project, notification.getOpStatus().toString());

            if (notification.getPluginId().equals(manoId)) {
                String osmDescriptorId = getOsmDescriptorId(nsdId, version);
                if (osmDescriptorId == null)
                    osmDescriptorId = nsdId;

                Optional<OsmInfoObject> osmInfoObject = osmInfoObjectRepository
                        .findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, version, manoId);
                if (osmInfoObject.isPresent()) {
                    if (notification.getOpStatus().equals(OperationStatus.SUCCESSFULLY_DONE)) {
                        Optional<OsmTranslationInformation> translationInformationOptional =
                                translationInformationRepository.findByCatInfoIdAndOsmManoId(nsdInfoId, manoId);
                        translationInformationOptional.ifPresent(osmTranslationInformation ->
                                translationInformationRepository.delete(osmTranslationInformation));

                        translationInformationRepository.saveAndFlush(new OsmTranslationInformation(nsdInfoId,
                                osmInfoObject.get().getId(), nsdId, osmInfoObject.get().getDescriptorId(), version, manoId));
                    }
                }
            }
        }
    }

    @Override
    public void acceptNsdDeletionNotification(NsdDeletionNotificationMessage notification) throws MethodNotImplementedException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        try {
            log.debug("RECEIVED MESSAGE: " + mapper.writeValueAsString(notification));
        } catch (JsonProcessingException e) {
            log.error("Unable to parse received nsdOnboardingNotificationMessage: " + e.getMessage());
        }

        String manoId = osm.getManoId();
        String nsdInfoId = notification.getNsdInfoId();
        String nsdId = notification.getNsdId();
        String version = notification.getNsdVersion();
        String project = notification.getProject();

        if (notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received Nsd deletion notification for Nsd with ID {} and version {} for project {}",
                    manoId, nsdId, version, project);

            if(Utilities.isTargetMano(notification.getSiteOrManoIds(), osm)
                    && translationInformationContainsCatInfoId(nsdInfoId)
                    && this.getPluginOperationalState() == PluginOperationalState.ENABLED) {

                try {
                    String osmInfoPkgId = getOsmInfoId(nsdInfoId);
                    if(osmInfoPkgId == null)
                        throw new FailedOperationException("Could not find the corresponding Info ID in OSM");
                    if(!deleteTranslationInformationEntry(nsdInfoId))
                        throw new FailedOperationException("Could not delete the specified entry");
                    if(!translationInformationContainsOsmInfoId(osmInfoPkgId))
                        deleteNsd(osmInfoPkgId, notification.getOperationId().toString());

                    log.info("{} - Successfully deleted Nsd with ID {} and version {} for project {}", manoId, nsdId, version, project);

                    sendNotification(new NsdDeletionNotificationMessage(nsdInfoId, nsdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.SUCCESSFULLY_DONE,
                            manoId, null));
                } catch (Exception e) {
                    log.error("{} - Could not delete Nsd: {}", manoId, e.getMessage());
                    log.debug("Error details: ", e);
                    sendNotification(new NsdDeletionNotificationMessage(nsdInfoId, nsdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.FAILED,
                            manoId, null));
                }
            } else {
                if (this.getPluginOperationalState() == PluginOperationalState.DISABLED
                        || this.getPluginOperationalState() == PluginOperationalState.DELETING) {
                    log.debug("{} - NSD deletion skipped", manoId);
                    sendNotification(new NsdDeletionNotificationMessage(nsdInfoId, nsdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                            manoId, null));
                }
            }
        } else if (notification.getScope() == ScopeType.SYNC)
            log.info("{} - Received Sync Pkg deletion notification for NSD with ID {} and version {} for project {} : {}",
                    manoId, nsdId, version, project, notification.getOpStatus().toString());
    }

    @Override
    public void acceptPnfdOnBoardingNotification(PnfdOnBoardingNotificationMessage notification) throws MethodNotImplementedException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        try {
            log.debug("RECEIVED MESSAGE: " + mapper.writeValueAsString(notification));
        } catch (JsonProcessingException e) {
            log.error("Unable to parse received pnfOnboardingNotificationMessage: " + e.getMessage());
        }

        String manoId = osm.getManoId();
        String pnfdInfoId = notification.getPnfdInfoId();
        String pnfdId = notification.getPnfdId();
        String version = notification.getPnfdVersion();
        String project = notification.getProject();

        if(notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received PNFD onboarding notification for Pnfd with ID {} and version {} for project {}",
                    manoId, pnfdId, version, project);

            boolean forceOnboard = false;
            if(pnfdInfoId.endsWith("_update")) {
                forceOnboard = true;
                pnfdInfoId = pnfdInfoId.replace("_update", "");
            }

            if(Utilities.isTargetMano(notification.getSiteOrManoIds(), osm) &&
                    this.getPluginOperationalState() == PluginOperationalState.ENABLED) {

                try {
                    String packagePath = notification.getPackagePath().getKey();

                    Set<String> metadataFileNames;
                    String metadataFileName;

                    File metadata;
                    File descriptor;

                    if(notification.getPackagePath().getValue().equals(PathType.LOCAL.toString())) {

                        Set<String> files = Utilities.listFiles(packagePath);

                        if(files.size() != 1) {
                            metadataFileNames = Utilities.listFiles(packagePath + "/TOSCA-Metadata/");

                            Optional<String> opt = metadataFileNames.stream()
                                    .filter(name -> name.endsWith(".meta")).findFirst();
                            if(!opt.isPresent())
                                throw new MalformattedElementException("Metadata file not found in " + packagePath +
                                        "/TOSCA-Metadata/" + " for Pnfd with ID " + pnfdId + " and version " + version +
                                        " for project " + project + ".");
                            metadataFileName = opt.get();

                            metadata = new File(packagePath + "/TOSCA-Metadata/" + metadataFileName);
                            descriptor = new File(packagePath + "/" + Utilities.getMainServiceTemplateFromMetadata(metadata));
                        } else if(files.iterator().next().endsWith(".yaml")
                                || files.iterator().next().endsWith(".yml")
                                || files.iterator().next().endsWith(".json"))
                            descriptor = new File(packagePath + "/" + files.iterator().next());
                        else
                            throw new MalformattedElementException("Descriptor files not found");
                    } else
                        throw new MethodNotImplementedException("Path Type not currently supported");

                    if(descriptor.getName().endsWith(".yaml") || descriptor.getName().endsWith(".yml"))
                        mapper = new ObjectMapper(new YAMLFactory());

                    Pnfd pnfd = mapper.readValue(descriptor, Pnfd.class);
                    if(!pnfd.getId().equals(pnfdId) || !pnfd.getVersion().equals(version))
                        throw new MalformattedElementException("The PNFD ID and version specified in the notification " +
                                "(" + pnfdId + ", " + version + ") do not match those of the stored descriptor " +
                                "(" + pnfd.getId() + ", " + pnfd.getVersion() + ").");

                    String osmDescriptorId = getOsmDescriptorId(pnfdId, version);
                    if(osmDescriptorId == null)
                        osmDescriptorId = pnfdId;

                    String osmInfoObjectId;
                    Optional<OsmInfoObject> osmInfoObjectOptional = osmInfoObjectRepository
                            .findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, version, manoId);
                    if(!osmInfoObjectOptional.isPresent() || forceOnboard) {

                        if(isTheFirstTranslationInformationEntry(pnfdId))
                            osmDescriptorId = pnfdId;
                        else {
                            osmDescriptorId = UUID.randomUUID().toString();
                            pnfd.setId(osmDescriptorId);
                        }

                        packagePath = notification.getPackagePath().getKey();
                        Set<String> fileNames = Utilities.listFiles(packagePath);
                        String mgmtCp = null;

                        if (fileNames.stream().filter(name -> name.endsWith(".mf")).count() != 0) {
                            String manifestPath = fileNames.stream().filter(name -> name.endsWith(".mf")).findFirst().get();
                            File mf = new File(packagePath + "/" + manifestPath);

                            mgmtCp = Utilities.getMgmtCpFromManifest(mf);
                        } else {
                            log.warn("Missing manifest, using the first ext-cpd as management.");
                            List<Cpd> cpds = pnfd.getExtCpd();
                            if(cpds == null || cpds.isEmpty())
                                throw new MalformattedElementException("Cannot deduce the mgmt-cp parameter, empty ext-cpd list.");

                            mgmtCp = cpds.get(0).getId();
                        }

                        OsmVnfdSol006Wrapper vnfd = SolToOsmTranslator.generateVnfDescriptor(pnfd, mgmtCp);
                        YAMLFactory yamlFactory = new YAMLFactory();
                        yamlFactory.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
                        mapper = new ObjectMapper(yamlFactory);
                        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                        log.debug("{} - Translated PNFD:\n{}", manoId, mapper.writeValueAsString(vnfd));

                        File archive = new OsmArchiveBuilder(osmDir, logo)
                                .makeNewArchive(vnfd, "Generated by NXW Catalogue");
                        osmInfoObjectId = onBoardVnfPackage(archive, notification.getOperationId().toString());

                        log.info("{} - Successfully uploaded Pnfd with ID {} and version {} for project {}", manoId, pnfdId, version, project);
                    } else
                        osmInfoObjectId = osmInfoObjectOptional.get().getId();

                    translationInformationRepository.saveAndFlush(new OsmTranslationInformation(pnfdInfoId,
                            osmInfoObjectId, pnfdId, osmDescriptorId, version, manoId));
                    sendNotification(new PnfdOnBoardingNotificationMessage(pnfdInfoId, pnfdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.SUCCESSFULLY_DONE,
                            manoId, null,null));
                } catch (Exception e) {
                    log.error("{} - Could not onboard Nsd: {}", manoId, e.getMessage());
                    log.debug("Error details: ", e);
                    sendNotification(new PnfdOnBoardingNotificationMessage(pnfdInfoId, pnfdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.FAILED,
                            manoId, null,null));
                }
            } else {
                if(this.getPluginOperationalState() == PluginOperationalState.DISABLED
                        || this.getPluginOperationalState() == PluginOperationalState.DELETING) {
                    log.debug("{} - PNFD onboarding skipped", manoId);
                    sendNotification(new PnfdOnBoardingNotificationMessage(pnfdInfoId, pnfdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                            manoId, null,null));
                }
            }
        } else if(notification.getScope() == ScopeType.SYNC) {
            log.info("{} - Received Sync Pkg onboarding notification for PNFD with ID {} and version {} for project {} : {}",
                    manoId, pnfdId, version, project, notification.getOpStatus().toString());

            if(notification.getPluginId().equals(manoId)) {
                String osmDescriptorId = getOsmDescriptorId(pnfdId, version);
                if(osmDescriptorId == null)
                    osmDescriptorId = pnfdId;

                Optional<OsmInfoObject> osmInfoObject = osmInfoObjectRepository
                        .findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, version, manoId);
                if(osmInfoObject.isPresent()) {
                    if(notification.getOpStatus().equals(OperationStatus.SUCCESSFULLY_DONE))
                        translationInformationRepository.saveAndFlush(new OsmTranslationInformation(pnfdInfoId,
                                osmInfoObject.get().getId(), pnfdId, osmInfoObject.get().getDescriptorId(), version, manoId));
                }
            }
        }
    }

    @Override
    public void acceptPnfdDeletionNotification(PnfdDeletionNotificationMessage notification) throws MethodNotImplementedException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        try {
            log.debug("RECEIVED MESSAGE: " + mapper.writeValueAsString(notification));
        } catch (JsonProcessingException e) {
            log.error("Unable to parse received PnfdDeletionNotificationMessage: " + e.getMessage());
        }

        String manoId = osm.getManoId();
        String pnfdInfoId = notification.getPnfdInfoId();
        String pnfdId = notification.getPnfdId();
        String version = notification.getPnfdVersion();
        String project = notification.getProject();

        if(notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received PNFD deletion notification for Pnfd with ID {} and version {} for project {}",
                    manoId, pnfdId, version, project);

            if(Utilities.isTargetMano(notification.getSiteOrManoIds(), osm)
                    && translationInformationContainsCatInfoId(pnfdInfoId)
                    && this.getPluginOperationalState() == PluginOperationalState.ENABLED) {

                try {
                    String osmInfoPkgId = getOsmInfoId(pnfdInfoId);
                    if(osmInfoPkgId == null)
                        throw new FailedOperationException("Could not find the corresponding Info ID in OSM");
                    if(!deleteTranslationInformationEntry(pnfdInfoId))
                        throw new FailedOperationException("Could not delete the specified entry");
                    if(!translationInformationContainsOsmInfoId(osmInfoPkgId))
                        deleteVnfd(osmInfoPkgId, notification.getOperationId().toString());

                    log.info("{} - Successfully deleted Pnfd with ID {} and version {} for project {}", manoId, pnfdId, version, project);

                    sendNotification(new PnfdDeletionNotificationMessage(pnfdInfoId, pnfdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.SUCCESSFULLY_DONE,
                            manoId, null));
                } catch (Exception e) {
                    log.error("{} - Could not delete Pnfd: {}", manoId, e.getMessage());
                    log.debug("Error details: ", e);
                    sendNotification(new PnfdDeletionNotificationMessage(pnfdInfoId, pnfdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.FAILED,
                            manoId, null));
                }
            } else {
                if(this.getPluginOperationalState() == PluginOperationalState.DISABLED
                        || this.getPluginOperationalState() == PluginOperationalState.DELETING) {
                    log.debug("{} - PNFD deletion skipped", manoId);
                    sendNotification(new PnfdDeletionNotificationMessage(pnfdInfoId, pnfdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                            manoId, null));
                }
            }
        } else if(notification.getScope() == ScopeType.SYNC) {
            log.info("{} - Received Sync Pkg deletion notification for PNFD with ID {} and version {} for project {} : {}",
                    manoId, pnfdId, version, project, notification.getOpStatus().toString());
        }
    }

    private String getOsmDescriptorId(String catDescriptorId, String descriptorVersion) {
        if(catDescriptorId == null)
            throw new IllegalArgumentException("The catalog descriptor ID specified cannot be null.");
        if(descriptorVersion == null)
            throw new IllegalArgumentException("The descriptor version specified cannot be null.");

        List<OsmTranslationInformation> translationInformationList = translationInformationRepository
                .findByCatDescriptorIdAndDescriptorVersionAndOsmManoId(catDescriptorId, descriptorVersion, osm.getManoId());
        if(translationInformationList.size() == 0)
            return null;

        return translationInformationList.get(0).getOsmDescriptorId();
    }

    private boolean isTheFirstTranslationInformationEntry(String catDescriptorId) {
        if (catDescriptorId == null)
            throw new IllegalArgumentException("The catalog descriptor ID specified cannot be null.");

        List<OsmTranslationInformation> translationInformationList =
                translationInformationRepository.findByCatDescriptorIdAndOsmManoId(catDescriptorId, osm.getManoId());
        if(translationInformationList.size() != 0) {
            for (OsmTranslationInformation translationInformation : translationInformationList)
                if (translationInformation.getCatDescriptorId().equals(translationInformation.getOsmDescriptorId()))
                    return false;
        }

        return true;
    }

    private static <T> List<T> parseResponse(OSMHttpResponse httpResponse, String opId, Class<T> clazz)
            throws FailedOperationException {
        List<T> objList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
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

    private String onBoardVnfPackage(File file, String opId) throws FailedOperationException {
        log.info("{} - Onboarding vnf package, opId: {}", osm.getManoId(), opId);

        OSMHttpResponse httpResponse = osmClient.createVnfPackage();
        IdObject content = parseResponse(httpResponse, opId, IdObject.class).get(0);
        String osmVnfdInfoId = content.getId();

        httpResponse = osmClient.uploadVnfPackageContent(osmVnfdInfoId, file);
        try {
            parseResponse(httpResponse, opId, null);
        } catch(FailedOperationException e){
            osmClient.deleteVnfPackage(osmVnfdInfoId);
            throw new FailedOperationException(e);
        }

        httpResponse = osmClient.getVnfPackageInfo(osmVnfdInfoId);
        OsmInfoObject osmInfoObject = parseResponse(httpResponse, opId, OsmInfoObject.class).get(0);
        osmInfoObject.setEpoch(Instant.now().getEpochSecond());
        osmInfoObject.setOsmId(osm.getManoId());
        osmInfoObject.setType(OsmObjectType.VNF);
        osmInfoObjectRepository.saveAndFlush(osmInfoObject);

        log.info("{} - Package onboarding successful. OpId: {}", osm.getManoId(), opId);
        return osmVnfdInfoId;
    }

    @Override
    public void acceptVnfPkgOnBoardingNotification(VnfPkgOnBoardingNotificationMessage notification) throws MethodNotImplementedException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        try {
            log.debug("RECEIVED MESSAGE: " + mapper.writeValueAsString(notification));
        } catch (JsonProcessingException e) {
            log.error("Unable to parse received vnfPkgOnboardingNotificationMessage: " + e.getMessage());
        }

        String vnfPkgInfoId = notification.getVnfPkgInfoId();
        boolean forceOnboard = false;
        if(vnfPkgInfoId.endsWith("_update")) {
            forceOnboard = true;
            vnfPkgInfoId = vnfPkgInfoId.replace("_update", "");
        }

        String manoId  = osm.getManoId();
        String vnfdId  = notification.getVnfdId();
        String version = notification.getVnfdVersion();
        String project = notification.getProject();

        if(notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received Vnfd onboarding notification for Vnfd with ID {} and version {} for project {}",
                    manoId, vnfdId, version, project);

            if(Utilities.isTargetMano(notification.getSiteOrManoIds(), osm) &&
                    this.getPluginOperationalState() == PluginOperationalState.ENABLED) {

                try {
                    String packagePath = notification.getPackagePath().getKey();
                    File descriptor;

                    if (notification.getPackagePath().getValue().equals(PathType.LOCAL.toString())) {

                        Set<String> metadataFileNames = Utilities.listFiles(packagePath + "/TOSCA-Metadata/");
                        String metadataFileName;

                        Optional<String> opt = metadataFileNames.stream()
                                .filter(name -> name.endsWith(".meta")).findFirst();
                        if(!opt.isPresent())
                            throw new MalformattedElementException("Metadata file not found in " + packagePath +
                                    "/TOSCA-Metadata/" + " for Vnfd with ID " + vnfdId + " and version " + version +
                                    " for project " + project + ".");
                        metadataFileName = opt.get();

                        File metadata = new File(packagePath + "/TOSCA-Metadata/" + metadataFileName);
                        descriptor = new File(packagePath + "/" + Utilities.getMainServiceTemplateFromMetadata(metadata));
                    } else
                        throw new MethodNotImplementedException("Path Type not currently supported");

                    if(descriptor.getName().endsWith(".yaml") || descriptor.getName().endsWith(".yml"))
                        mapper = new ObjectMapper(new YAMLFactory());

                    Vnfd tmp = mapper.readValue(descriptor, Vnfd.class);
                    if(!tmp.getId().equals(vnfdId) || !tmp.getVersion().equals(version))
                        throw new MalformattedElementException("The VNFD ID and version specified in the notification " +
                                "(" + vnfdId + ", " + version + ") do not match those of the stored descriptor " +
                                "(" + tmp.getId() + ", " + tmp.getVersion() + ").");

                    String osmDescriptorId = getOsmDescriptorId(vnfdId, version);
                    if(osmDescriptorId == null)
                        osmDescriptorId = vnfdId;

                    String osmInfoObjectId;
                    Optional<OsmInfoObject> osmInfoObjectOptional = osmInfoObjectRepository
                            .findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, version, manoId);
                    if(!osmInfoObjectOptional.isPresent() || forceOnboard) {

                        if(isTheFirstTranslationInformationEntry(vnfdId))
                            osmDescriptorId = vnfdId;
                        else {
                            osmDescriptorId = UUID.randomUUID().toString();
                            tmp.setId(osmDescriptorId);
                        }

                        Set<String> fileNames = Utilities.listFiles(packagePath);
                        String manifestPath = fileNames.stream().filter(name -> name.endsWith(".mf")).findFirst().get();
                        File mf = new File(packagePath + "/" + manifestPath);

                        Map<String, File> cloudInitMap = Utilities.getCloudInitsFromManifest(mf, packagePath);
                        if (cloudInitMap.isEmpty())
                            log.debug("{} - No cloud-init file found for VNF with ID {} and version {}", manoId, vnfdId, version);

                        String monitoringPath = Utilities.getMonitoringFromManifest(mf);
                        File monitoring = null;
                        if (monitoringPath != null)
                            monitoring = new File(packagePath + "/" + monitoringPath);
                        else
                            log.debug("{} - No monitoring file found for VNF with ID {} and version {}", manoId, vnfdId, version);

                        OsmVnfdSol006Wrapper vnfd = SolToOsmTranslator.generateVnfDescriptor(tmp, cloudInitMap);
                        YAMLFactory yamlFactory = new YAMLFactory();
                        yamlFactory.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
                        mapper = new ObjectMapper(yamlFactory);
                        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                        log.debug("Translated VNFD: \n" + mapper.writeValueAsString(vnfd));

                        File archive = new OsmArchiveBuilder(osmDir, logo)
                                .makeNewArchive(vnfd, "Generated by NXW Catalogue", packagePath, monitoring);
                        osmInfoObjectId = onBoardVnfPackage(archive, notification.getOperationId().toString());
                        log.info("{} - Successfully uploaded Vnfd with ID {} and version {} for project {}", manoId, vnfdId, version, project);
                    }
                    else
                        osmInfoObjectId = osmInfoObjectOptional.get().getId();

                    translationInformationRepository.saveAndFlush(new OsmTranslationInformation(vnfPkgInfoId,
                            osmInfoObjectId, vnfdId, osmDescriptorId, version, manoId));
                    sendNotification(new VnfPkgOnBoardingNotificationMessage(vnfPkgInfoId, vnfdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.SUCCESSFULLY_DONE,
                            manoId, null,null));
                } catch (Exception e) {
                    log.error("{} - Could not onboard Vnfd: {}", manoId, e.getMessage());
                    log.debug("Error details: ", e);
                    sendNotification(new VnfPkgOnBoardingNotificationMessage(vnfPkgInfoId, vnfdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.FAILED,
                            manoId, null,null));
                }
            } else {
                if(this.getPluginOperationalState() == PluginOperationalState.DISABLED ||
                        this.getPluginOperationalState() == PluginOperationalState.DELETING) {
                    log.debug("{} - VNF Pkg onboarding skipped", manoId);
                    sendNotification(new VnfPkgOnBoardingNotificationMessage(vnfPkgInfoId, vnfdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                            manoId, null,null));
                }
            }
        } else if(notification.getScope() == ScopeType.SYNC) {
            log.info("{} - Received Sync Pkg onboarding notification for Vnfd with ID {} and version {} for project {} : {}",
                    manoId, vnfdId, version, project, notification.getOpStatus().toString());
            if(notification.getPluginId().equals(manoId)) {
                String osmDescriptorId = getOsmDescriptorId(vnfdId, version);
                if (osmDescriptorId == null)
                    osmDescriptorId = vnfdId;

                Optional<OsmInfoObject> osmInfoObject = osmInfoObjectRepository
                        .findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, version, manoId);
                if (osmInfoObject.isPresent()) {
                    if (notification.getOpStatus().equals(OperationStatus.SUCCESSFULLY_DONE))
                        translationInformationRepository.saveAndFlush(new OsmTranslationInformation(vnfPkgInfoId,
                                osmInfoObject.get().getId(), vnfdId, osmInfoObject.get().getDescriptorId(), version, manoId));
                }
            }
        }
    }

    private String getOsmInfoId(String catInfoId) {
        if(catInfoId == null)
            throw new IllegalArgumentException("The catalog descriptor ID specified cannot be null.");

        Optional<OsmTranslationInformation> optionalTranslationInformation =
                translationInformationRepository.findByCatInfoIdAndOsmManoId(catInfoId, osm.getManoId());
        if(!optionalTranslationInformation.isPresent())
            return null;

        return optionalTranslationInformation.get().getOsmInfoId();
    }

    private boolean deleteTranslationInformationEntry(String catInfoId) {
        if(catInfoId == null)
            throw new IllegalArgumentException("The catalog descriptor ID specified cannot be null.");

        Optional<OsmTranslationInformation> optionalTranslationInformation =
                translationInformationRepository.findByCatInfoIdAndOsmManoId(catInfoId, osm.getManoId());
        if(!optionalTranslationInformation.isPresent())
            return false;

        translationInformationRepository.delete(optionalTranslationInformation.get());
        return true;
    }

    private boolean translationInformationContainsOsmInfoId(String osmInfoId) {
        if(osmInfoId == null)
            throw new IllegalArgumentException("The OSM info ID specified cannot be null.");

        List<OsmTranslationInformation> translationInformationList = translationInformationRepository.findByOsmInfoIdAndOsmManoId(osmInfoId, osm.getManoId());
        return translationInformationList.size() != 0;
    }

    private void deleteVnfd(String vnfdInfoId, String opId) throws FailedOperationException {
        log.info("{} - Deleting vnfd {}, opId: {}", osm.getManoId(), vnfdInfoId, opId);

        OSMHttpResponse httpResponse = osmClient.deleteVnfPackage(vnfdInfoId);
        parseResponse(httpResponse, opId, null);

        OsmInfoObject osmInfoObject =  osmInfoObjectRepository.findById(vnfdInfoId).get();
        String descriptorId = osmInfoObject.getDescriptorId();
        osmInfoObjectRepository.delete(osmInfoObject);

        Utilities.deleteDir(new File(osmDir + "/" + descriptorId));
        Utilities.deleteDir(new File(osmDir + "/" + descriptorId + ".tar.gz"));

        log.info("{} - Vnfd deleting successful. OpId: {}", osm.getManoId(), opId);
    }

    @Override
    public void acceptVnfPkgChangeNotification(VnfPkgChangeNotificationMessage notification) throws MethodNotImplementedException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        try {
            log.debug("RECEIVED MESSAGE: " + mapper.writeValueAsString(notification));
        } catch (JsonProcessingException e) {
            log.error("Unable to parse received vnfPkgChangeNotificationMessage: " + e.getMessage());
        }

        String manoId       = osm.getManoId();
        String vnfPkgInfoId = notification.getVnfPkgInfoId();
        String vnfdId       = notification.getVnfdId();
        String version      = notification.getVnfdVersion();
        String project      = notification.getProject();

        if(notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received VNF Pkg change notification for Vnfd with ID {} and version {} for project {}",
                    manoId, vnfdId, version, project);

            if(Utilities.isTargetMano(notification.getSiteOrManoIds(), osm) &&
                    this.getPluginOperationalState() == PluginOperationalState.ENABLED) {

                try {
                    String osmInfoPkgId = getOsmInfoId(vnfPkgInfoId);
                    if(osmInfoPkgId == null)
                        throw new FailedOperationException("Could not find the corresponding Info ID in OSM");
                    if(!deleteTranslationInformationEntry(vnfPkgInfoId))
                        throw new FailedOperationException("Could not delete the specified entry");
                    if(!translationInformationContainsOsmInfoId(osmInfoPkgId))
                        deleteVnfd(osmInfoPkgId, notification.getOperationId().toString());
                    log.info("{} - Successfully deleted Vnfd with ID {} and version {} for project {}", manoId, vnfdId, version, project);

                    String packagePath = notification.getPackagePath().getKey();
                    File descriptor;

                    if (notification.getPackagePath().getValue().equals(PathType.LOCAL.toString())) {

                        Set<String> files = Utilities.listFiles(packagePath);
                        if(files.size() != 1) {
                            Set<String> metadataFileNames = Utilities.listFiles(packagePath + "/TOSCA-Metadata/");
                            String metadataFileName;

                            Optional<String> opt = metadataFileNames.stream()
                                    .filter(name -> name.endsWith(".meta")).findFirst();
                            if(!opt.isPresent())
                                throw new MalformattedElementException("Metadata file not found in " + packagePath +
                                        "/TOSCA-Metadata/" + " for Vnfd with ID " + vnfdId + " and version " + version +
                                        " for project " + project + ".");
                            metadataFileName = opt.get();

                            File metadata = new File(packagePath + "/TOSCA-Metadata/" + metadataFileName);
                            descriptor = new File(packagePath + "/" + Utilities.getMainServiceTemplateFromMetadata(metadata));
                        } else {
                            String filename = files.iterator().next();
                            if(filename.endsWith(".yaml") || filename.endsWith("yml") || filename.endsWith("json"))
                                descriptor = new File(packagePath + "/" + filename);
                            else
                                throw new MalformattedElementException("Descriptor files not found");
                        }
                    } else
                        throw new MethodNotImplementedException("Path Type not currently supported");

                    log.info("{} - Onboarding Vnfd with ID {} and version {} for project {}", manoId, vnfdId, version, project);

                    if(descriptor.getName().endsWith(".yaml") || descriptor.getName().endsWith(".yml"))
                        mapper = new ObjectMapper(new YAMLFactory());

                    Vnfd tmp = mapper.readValue(descriptor, Vnfd.class);
                    if(!tmp.getId().equals(vnfdId) || !tmp.getVersion().equals(version))
                        throw new MalformattedElementException("The VNFD ID and version specified in the notification " +
                                "(" + vnfdId + ", " + version + ") do not match those of the stored descriptor " +
                                "(" + tmp.getId() + ", " + tmp.getVersion() + ").");

                    String osmDescriptorId = getOsmDescriptorId(vnfdId, version);
                    if(osmDescriptorId == null)
                        osmDescriptorId = vnfdId;

                    String osmInfoObjectId;
                    Optional<OsmInfoObject> osmInfoObjectOptional = osmInfoObjectRepository
                            .findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, version, manoId);
                    if(!osmInfoObjectOptional.isPresent()) {

                        if(isTheFirstTranslationInformationEntry(vnfdId))
                            osmDescriptorId = vnfdId;
                        else {
                            osmDescriptorId = UUID.randomUUID().toString();
                            tmp.setId(osmDescriptorId);
                        }

                        Set<String> fileNames = Utilities.listFiles(packagePath);
                        String manifestPath = fileNames.stream().filter(name -> name.endsWith(".mf")).findFirst().get();
                        File mf = new File(packagePath + "/" + manifestPath);

                        Map<String, File> cloudInitMap = Utilities.getCloudInitsFromManifest(mf, packagePath);
                        if (cloudInitMap.isEmpty())
                            log.debug("{} - No cloud-init file found for VNF with ID {} and version {}", manoId, vnfdId, version);

                        String monitoringPath = Utilities.getMonitoringFromManifest(mf);
                        File monitoring = null;
                        if (monitoringPath != null)
                            monitoring = new File(packagePath + "/" + monitoringPath);
                        else
                            log.debug("{} - No monitoring file found for VNF with ID {} and version {}", manoId, vnfdId, version);

                        OsmVnfdSol006Wrapper vnfd = SolToOsmTranslator.generateVnfDescriptor(tmp, cloudInitMap);
                        YAMLFactory yamlFactory = new YAMLFactory();
                        yamlFactory.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
                        mapper = new ObjectMapper(yamlFactory);
                        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                        log.debug("Translated VNFD: " + mapper.writeValueAsString(vnfd));

                        File archive = new OsmArchiveBuilder(osmDir, logo)
                                .makeNewArchive(vnfd, "Generated by NXW Catalogue", packagePath, monitoring);
                        osmInfoObjectId = onBoardVnfPackage(archive, notification.getOperationId().toString());
                        log.info("{} - Successfully uploaded Vnfd with ID {} and version {} for project {}", manoId, vnfdId, version, project);
                    }
                    else
                        osmInfoObjectId = osmInfoObjectOptional.get().getId();

                    translationInformationRepository.saveAndFlush(new OsmTranslationInformation(vnfPkgInfoId,
                            osmInfoObjectId, vnfdId, osmDescriptorId, version, manoId));
                    sendNotification(new VnfPkgChangeNotificationMessage(vnfPkgInfoId, vnfdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.SUCCESSFULLY_DONE,
                            manoId, null,null));
                } catch (Exception e) {
                    log.error("{} - Could not change Vnfd: {}", manoId, e.getMessage());
                    log.debug("Error details: ", e);
                    sendNotification(new VnfPkgChangeNotificationMessage(vnfPkgInfoId, vnfdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.FAILED,
                            manoId, null,null));
                }
            } else {
                if(this.getPluginOperationalState() == PluginOperationalState.DISABLED ||
                        this.getPluginOperationalState() == PluginOperationalState.DELETING) {
                    log.debug("{} - VNF Pkg change skipped", manoId);
                    sendNotification(new VnfPkgChangeNotificationMessage(vnfPkgInfoId, vnfdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                            manoId, null,null));
                }
            }
        } else if(notification.getScope() == ScopeType.SYNC) {
            log.info("{} - Received Sync Pkg change notification for Vnfd with ID {} and version {} for project {} : {}",
                    manoId, vnfdId, version, project, notification.getOpStatus().toString());
            if(notification.getPluginId().equals(manoId)) {
                String osmDescriptorId = getOsmDescriptorId(vnfdId, version);
                if(osmDescriptorId == null)
                    osmDescriptorId = vnfdId;

                Optional<OsmInfoObject> osmInfoObject = osmInfoObjectRepository
                        .findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, version, manoId);
                if(osmInfoObject.isPresent()) {
                    if (notification.getOpStatus().equals(OperationStatus.SUCCESSFULLY_DONE)) {
                        Optional<OsmTranslationInformation> translationInformationOptional =
                                translationInformationRepository.findByCatInfoIdAndOsmManoId(vnfPkgInfoId, manoId);
                        translationInformationOptional.ifPresent(osmTranslationInformation ->
                                translationInformationRepository.delete(osmTranslationInformation));

                        translationInformationRepository.saveAndFlush(new OsmTranslationInformation(vnfPkgInfoId,
                                osmInfoObject.get().getId(), vnfdId, osmInfoObject.get().getDescriptorId(), version, manoId));
                    }
                }
            }
        }
    }

    private boolean translationInformationContainsCatInfoId(String catInfoId) {
        if(catInfoId == null)
            throw new IllegalArgumentException("The catalog descriptor ID specified cannot be null.");

        Optional<OsmTranslationInformation> optionalTranslationInformation = translationInformationRepository.findByCatInfoIdAndOsmManoId(catInfoId, osm.getManoId());
        return optionalTranslationInformation.isPresent();
    }

    @Override
    public void acceptVnfPkgDeletionNotification(VnfPkgDeletionNotificationMessage notification) throws MethodNotImplementedException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        try {
            log.debug("RECEIVED MESSAGE: " + mapper.writeValueAsString(notification));
        } catch (JsonProcessingException e) {
            log.error("Unable to parse received vnfPkgChangeNotificationMessage: " + e.getMessage());
        }

        String manoId       = osm.getManoId();
        String vnfPkgInfoId = notification.getVnfPkgInfoId();
        String vnfdId       = notification.getVnfdId();
        String version      = notification.getVnfdVersion();
        String project      = notification.getProject();

        if(notification.getScope() == ScopeType.LOCAL) {
            log.info("{} - Received VNFD deletion notification for Vnfd with ID {} and version {} for project {}",
                    manoId, vnfdId, version, project);

            if(Utilities.isTargetMano(notification.getSiteOrManoIds(), osm) &&
                    this.getPluginOperationalState() == PluginOperationalState.ENABLED &&
                    translationInformationContainsCatInfoId(vnfPkgInfoId)) {

                try {
                    String osmInfoPkgId = getOsmInfoId(vnfPkgInfoId);
                    if(osmInfoPkgId == null)
                        throw new FailedOperationException("Could not find the corresponding Info ID in OSM");
                    if(!deleteTranslationInformationEntry(vnfPkgInfoId))
                        throw new FailedOperationException("Could not delete the specified entry");
                    if(!translationInformationContainsOsmInfoId(osmInfoPkgId))
                        deleteVnfd(osmInfoPkgId, notification.getOperationId().toString());
                    log.info("{} - Successfully deleted Vnfd with ID {} and version {} for project {}", manoId, vnfdId, version, project);

                    sendNotification(new VnfPkgDeletionNotificationMessage(vnfPkgInfoId, vnfdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.SUCCESSFULLY_DONE,
                            manoId, null));
                } catch (Exception e) {
                    log.error("{} - Could not delete Vnfd: {}", manoId, e.getMessage());
                    log.debug("Error details: ", e);
                    sendNotification(new VnfPkgDeletionNotificationMessage(vnfPkgInfoId, vnfdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.FAILED,
                            manoId, null));
                }
            } else {
                if(this.getPluginOperationalState() == PluginOperationalState.DISABLED ||
                        this.getPluginOperationalState() == PluginOperationalState.DELETING) {
                    log.debug("{} - VNF Pkg deletion skipped", manoId);
                    sendNotification(new VnfPkgDeletionNotificationMessage(vnfPkgInfoId, vnfdId, version, project,
                            notification.getOperationId(), ScopeType.REMOTE, OperationStatus.RECEIVED,
                            manoId, null));
                }
            }
        } else if(notification.getScope() == ScopeType.SYNC)
            log.info("{} - Received Sync Pkg deletion notification for Vnfd with ID {} and version {} for project {} : {}",
                    manoId, vnfdId, version, project, notification.getOpStatus().toString());
    }

    private void updateDB(boolean updateVNF, boolean updateNS, String project) throws FailedOperationException {
        if(!updateVNF && !updateNS)
            return;

        String manoId = osm.getManoId();

        OSMHttpResponse response;
        List<OsmInfoObject> packageInfoList = new ArrayList<>();
        List<OsmInfoObject> aux;
        List<String> vnfPackageInfoIdList = new ArrayList<>();

        if(updateVNF) {
            log.info("{} - Updating Vnfs DB for project {}", manoId, project);

            response = osmClient.getVnfPackageList();
            aux = parseResponse(response, null, OsmInfoObject.class);
            packageInfoList.addAll(aux);
            vnfPackageInfoIdList = aux.stream()
                    .map(OsmInfoObject::getId)
                    .collect(Collectors.toList());
        }

        if(updateNS) {
            log.info("{} - Updating Nss DB for project {}", manoId, project);

            response = osmClient.getNsdInfoList();
            aux = parseResponse(response, null, OsmInfoObject.class);
            packageInfoList.addAll(aux);
        }

        for(OsmInfoObject packageInfo : packageInfoList) {

            String pkgInfoId = packageInfo.getId();
            String descriptorId = packageInfo.getDescriptorId();
            String version = packageInfo.getVersion();

            try {
                Optional<OsmInfoObject> osmInfoObject = osmInfoObjectRepository.findById(pkgInfoId);
                if(osmInfoObject.isPresent()) {
                    log.info("{} - OSM Pkg with descriptor ID {} and version {} already present in project {}",
                            manoId, descriptorId, version, project);

                    if(packageInfo.getAdmin().getModified().compareTo(osmInfoObject.get().getAdmin().getModified()) <= 0) {
                        osmInfoObject.get().setState(RecordState.UNCHANGED);
                        osmInfoObject.get().setEpoch(Instant.now().getEpochSecond());
                        osmInfoObjectRepository.saveAndFlush(osmInfoObject.get());
                        continue;
                    } else {
                        packageInfo.setState(RecordState.CHANGED);
                        if(!updateVNF || !updateNS)
                            packageInfo.getAdmin().setModified(osmInfoObject.get().getAdmin().getModified());
                    }
                } else
                    packageInfo.setState(RecordState.NEW);

                String osmDirPath_string = osmDirPath.toString();

                log.info("{} - Retrieving OSM Pkg with descriptor ID {} and version {} from project {}",
                        manoId, descriptorId, version, project);
                if(vnfPackageInfoIdList.contains(pkgInfoId)) {
                    response = osmClient.getVnfPackageContent(pkgInfoId, osmDirPath_string);
                    packageInfo.setType(OsmObjectType.VNF);
                }
                else {
                    response = osmClient.getNsdContent(pkgInfoId, osmDirPath_string);
                    packageInfo.setType(OsmObjectType.NS);
                }
                parseResponse(response, null, null);

                File archive = new File(osmDirPath_string + "/" + packageInfo.getId() + ".tar.gz");
                Archiver archiver = ArchiverFactory.createArchiver(ArchiveFormat.TAR, CompressionType.GZIP);
                archiver.extract(archive, new File(osmDirPath_string));

                packageInfo.setEpoch(Instant.now().getEpochSecond());
                packageInfo.setOsmId(osm.getManoId());
                osmInfoObjectRepository.saveAndFlush(packageInfo);
            } catch(FailedOperationException | IOException | IllegalStateException | IllegalArgumentException e) {
                log.error("{} - Sync error: {}", osm.getManoId(), e.getMessage());
                log.debug(null, e);
            }
        }
    }

    private String getCatDescriptorId(String osmDescriptorId, String descriptorVersion) {
        if(osmDescriptorId == null)
            throw new IllegalArgumentException("The OSM descriptor ID specified cannot be null.");
        if(descriptorVersion == null)
            throw new IllegalArgumentException("The OSM descriptor version specified cannot be null.");

        List<OsmTranslationInformation> translationInformationList =
                translationInformationRepository.findByOsmDescriptorIdAndDescriptorVersionAndOsmManoId(osmDescriptorId,
                        descriptorVersion, osm.getManoId());
        if(translationInformationList.size() == 0)
            return null;

        return translationInformationList.get(0).getCatDescriptorId();
    }

    @Override
    public Map<String, List<String>> getAllVnfd(String project) {
        String manoId = osm.getManoId();
        log.info("{} - Startup synchronization, started retrieving Osm Vnf Pkgs from project {}", manoId, project);

        Long startSync = Instant.now().getEpochSecond();

        try {
            updateDB(true, false, project);
        } catch(FailedOperationException e){
            log.error("{} - {}", manoId, e.getMessage());
            return null;
        }

        Map<String, List<String>> ids = new HashMap<>();
        List<OsmInfoObject> osmInfoObjectList =
                osmInfoObjectRepository.findByOsmIdAndType(manoId, OsmObjectType.VNF);
        for(OsmInfoObject osmInfoObj : osmInfoObjectList) {
            String descriptorId = osmInfoObj.getDescriptorId();
            String version = osmInfoObj.getVersion();

            if(osmInfoObj.getEpoch().compareTo(startSync) < 0) {
                log.info("{} - Osm Vnf Pkg with descriptor ID {} and version {} no longer present in project {}",
                        manoId, descriptorId, version, project);

                osmInfoObjectRepository.delete(osmInfoObj);

                Utilities.deleteDir(new File(osmDir + "/" + descriptorId));
                Utilities.deleteDir(new File(osmDir + "/" + descriptorId + ".tar.gz"));

                List<OsmTranslationInformation> translationInformationList =
                        translationInformationRepository.findByOsmInfoIdAndOsmManoId(osmInfoObj.getId(), manoId);
                for(OsmTranslationInformation translationInformation : translationInformationList)
                    translationInformationRepository.delete(translationInformation);
            } else {
                String catDescriptorId = getCatDescriptorId(descriptorId, version);
                if(catDescriptorId != null)
                    ids.computeIfAbsent(catDescriptorId, k -> new ArrayList<>()).add(version);
                else
                    ids.computeIfAbsent(descriptorId, k -> new ArrayList<>()).add(version);
            }
        }

        log.info("{} - Startup synchronization, finished retrieving Osm Vnf Pkgs from project {}", manoId, project);

        return ids;
    }

    @Override
    public Map<String, List<String>> getAllNsd(String project) {
        String manoId = osm.getManoId();
        log.info("{} - Startup synchronization, started retrieving Osm Ns Pkgs from project {}", manoId, project);

        Long startSync = Instant.now().getEpochSecond();

        try {
            updateDB(false, true, project);
        } catch(FailedOperationException e){
            log.error("{} - {}", manoId, e.getMessage());
            return null;
        }

        Map<String, List<String>> ids = new HashMap<>();
        List<OsmInfoObject> osmInfoObjectList =
                osmInfoObjectRepository.findByOsmIdAndType(manoId, OsmObjectType.NS);
        for(OsmInfoObject osmInfoObj : osmInfoObjectList) {
            String descriptorId = osmInfoObj.getDescriptorId();
            String version = osmInfoObj.getVersion();

            if(osmInfoObj.getEpoch().compareTo(startSync) < 0) {
                log.info("{} - Osm Ns Pkg with descriptor ID {} and version {} no longer present in project {}",
                        manoId, descriptorId, version, project);

                osmInfoObjectRepository.delete(osmInfoObj);

                Utilities.deleteDir(new File(osmDir + "/" + descriptorId));
                Utilities.deleteDir(new File(osmDir + "/" + descriptorId + ".tar.gz"));

                List<OsmTranslationInformation> translationInformationList =
                        translationInformationRepository.findByOsmInfoIdAndOsmManoId(osmInfoObj.getId(), manoId);
                for(OsmTranslationInformation translationInformation : translationInformationList)
                    translationInformationRepository.delete(translationInformation);
            } else {
                String catDescriptorId = getCatDescriptorId(descriptorId, version);
                if(catDescriptorId != null)
                    ids.computeIfAbsent(catDescriptorId, k -> new ArrayList<>()).add(version);
                else
                    ids.computeIfAbsent(descriptorId, k -> new ArrayList<>()).add(version);
            }
        }

        log.info("{} - Startup synchronization, finished retrieving Osm Ns Pkgs from project {}", manoId, project);

        return ids;
    }

    private String createVnfPkgSol006(OsmInfoObject vnfPackageInfo) throws IOException {

        String manoId = osm.getManoId();
        String descriptorId = vnfPackageInfo.getDescriptorId();
        String version = vnfPackageInfo.getVersion();

        log.info("{} - Creating VNF Descriptor with ID {} and version {}",
                manoId, descriptorId, version);

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        JsonNode osmVnfdWrapper = mapper.readTree(new File(osmDirPath.toString() + "/" +
                vnfPackageInfo.getAdmin().getStorage().getDescriptor()));

        OsmToSolTranslator.GenerateVnfOutput output =
                OsmToSolTranslator.generateVnfDescriptor(osmVnfdWrapper, vnfPackageInfo, osmDirPath);

        log.info("{} - Creating VNF Pkg with descriptor ID {} and version {}", manoId, descriptorId, version);

        return ToscaArchiveBuilder.createVNFCSAR(vnfPackageInfo.getId(), output.getVnfd(),
                tmpDirPath.toString(), output.getCloudInitMap());
    }

    private String createNsPkgSol006(OsmInfoObject nsPackageInfo) throws IOException {

        String manoId = osm.getManoId();
        String descriptorId = nsPackageInfo.getDescriptorId();
        String version = nsPackageInfo.getVersion();

        log.info("{} - Creating NS Descriptor with ID {} and version {}",
                manoId, descriptorId, version);

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        JsonNode osmNsdWrapper = mapper.readTree(new File(osmDirPath.toString() + "/" +
                nsPackageInfo.getAdmin().getStorage().getDescriptor()));

        List<OsmInfoObject> vnfInfoList = osmInfoObjectRepository.findByOsmIdAndType(manoId, OsmObjectType.VNF);
        List<OsmTranslationInformation> translationInformationList = translationInformationRepository.findByOsmManoId(manoId);

        Nsd nsdSol = OsmToSolTranslator.generateNsDescriptor(osmNsdWrapper, vnfInfoList, translationInformationList, osmDirPath);

        log.info("{} - Creating TOSCA NS Pkg with descriptor ID {} and version {}", manoId, descriptorId, version);

        return ToscaArchiveBuilder.createNSCSAR(nsPackageInfo.getId(), nsdSol, tmpDirPath.toString());
    }

    @Override
    public KeyValuePair getTranslatedPkgPath(String descriptorId, String descriptorVersion, String project) {
        String manoId = osm.getManoId();
        log.info("{} - Translating Osm Pkg with descriptor ID {} and version {} from project {}",
                manoId, descriptorId, descriptorVersion, project);

        String osmDescriptorId = getOsmDescriptorId(descriptorId, descriptorVersion);
        if(osmDescriptorId == null)
            osmDescriptorId = descriptorId;
        Optional<OsmInfoObject> osmInfoObject =
                osmInfoObjectRepository.findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, descriptorVersion, manoId);

        String pkgPath = null;
        try {
            if(osmInfoObject.isPresent()){
                if(osmInfoObject.get().getType().equals(OsmObjectType.VNF))
                    pkgPath = createVnfPkgSol006(osmInfoObject.get());
                else
                    pkgPath = createNsPkgSol006(osmInfoObject.get());
            }
        } catch (Exception e) {
            log.error("{} - Unable to generate TOSCA Pkg with descriptor ID {} and version {} for project {}: {}",
                    manoId, descriptorId, descriptorVersion, project, e.getMessage());
            log.debug(null, e);
            return  null;
        }

        log.info("{} - Uploading TOSCA Pkg with descriptor ID {} and version {} to project {}",
                manoId, descriptorId, descriptorVersion, project);

        return new KeyValuePair(pkgPath, PathType.LOCAL.toString());
    }

    @Override
    public void notifyOnboarding(String infoId, String descriptorId, String descriptorVersion, String project, OperationStatus opStatus) {
        String manoId = osm.getManoId();
        log.info("{} - Received Sync Pkg onboarding notification for Descriptor with ID {} and version {} for project {} : {}",
                manoId, descriptorId, descriptorVersion, project, opStatus.toString());

        String osmDescriptorId = getOsmDescriptorId(descriptorId, descriptorVersion);
        if(osmDescriptorId == null)
            osmDescriptorId = descriptorId;

        Optional<OsmInfoObject> osmInfoObjectOptional = osmInfoObjectRepository
                .findByDescriptorIdAndVersionAndOsmId(osmDescriptorId, descriptorVersion, manoId);
        if(osmInfoObjectOptional.isPresent()){
            OsmInfoObject osmInfoObject = osmInfoObjectOptional.get();
            if(opStatus.equals(OperationStatus.SUCCESSFULLY_DONE))
                translationInformationRepository.saveAndFlush(new OsmTranslationInformation(infoId, osmInfoObject.getId(),
                        descriptorId, osmInfoObject.getDescriptorId(), descriptorVersion, manoId));
        }
    }

    @Override
    public void notifyDelete(String infoId, String descriptorId, String descriptorVersion, String project, OperationStatus opStatus) {
        log.info("{} - Received Sync Pkg deletion notification for Descriptor with ID {} and version {} for project {} : {}",
                osm.getManoId(), descriptorId, descriptorVersion, project, opStatus.toString());
    }

    @Override
    public String getManoPkgInfoId(String catalogueInfoId) {
        return  getOsmInfoId(catalogueInfoId);
    }

    @Override
    public void RuntimeSynchronization() {

        String manoId = osm.getManoId();
        log.info("{} - Runtime synchronization, started retrieving Osm Vnf and Ns Pkgs", manoId);

        Long startSync = Instant.now().getEpochSecond();

        try {
            updateDB(true, true, "all");
        } catch(FailedOperationException e){
            log.error("{} - {}", manoId, e.getMessage());
            return;
        }

        List<OsmInfoObject> osmVnfList = osmInfoObjectRepository.findByOsmIdAndType(manoId, OsmObjectType.VNF);
        List<OsmInfoObject> osmNsList = osmInfoObjectRepository.findByOsmIdAndType(manoId, OsmObjectType.NS);

        UUID operationId;
        String descriptorId;
        String version;
        String pkgPath;

        for(OsmInfoObject osmInfoObject : osmVnfList) {
            if(osmInfoObject.getState().equals(RecordState.NEW)
                     && osmInfoObject.getEpoch().compareTo(startSync) >= 0) {

                operationId = UUID.randomUUID();
                descriptorId = osmInfoObject.getDescriptorId();
                version = osmInfoObject.getVersion();

                try {
                    pkgPath = createVnfPkgSol006(osmInfoObject);
                } catch(Exception e) {
                    log.error("{} - Unable to generate TOSCA Vnf Pkg with descriptor ID {} and version {}: {}",
                            manoId, descriptorId, version, e.getMessage());
                    log.debug("error details:\n", e);
                    continue;
                }

                log.info("{} - Uploading TOSCA Vnf Pkg with descriptor ID {} and version {}", manoId, descriptorId, version);

                sendNotification(new VnfPkgOnBoardingNotificationMessage(null, descriptorId, version,
                        "all", operationId, ScopeType.SYNC, OperationStatus.SENT, manoId, null,
                        new KeyValuePair(pkgPath, PathType.LOCAL.toString())));

            } else if(osmInfoObject.getState().equals(RecordState.CHANGED)
                     && osmInfoObject.getEpoch().compareTo(startSync) >= 0) {

                operationId = UUID.randomUUID();
                descriptorId = osmInfoObject.getDescriptorId();
                version = osmInfoObject.getVersion();

                try {
                    pkgPath = createVnfPkgSol006(osmInfoObject);
                } catch(Exception e) {
                    log.error("{} - Unable to generate TOSCA Vnf Pkg with descriptor ID {} and version {}: {}",
                            manoId, descriptorId, version, e.getMessage());
                    log.debug("error details:\n", e);
                    continue;
                }

                List<OsmTranslationInformation> translationInformationList =
                        translationInformationRepository.findByOsmInfoIdAndOsmManoId(osmInfoObject.getId(), manoId);
                String catDescriptorId;
                String catVersion;

                if(!translationInformationList.isEmpty()) {
                    catDescriptorId = translationInformationList.get(0).getCatDescriptorId();
                    catVersion = translationInformationList.get(0).getDescriptorVersion();
                }else{
                    catDescriptorId = descriptorId;
                    catVersion = version;
                }

                log.info("{} - Updating TOSCA Vnf Pkg with descriptor ID {} and version {}", manoId, descriptorId, version);

                sendNotification(new VnfPkgChangeNotificationMessage(null, catDescriptorId, catVersion,
                        "all", operationId, ScopeType.SYNC, OperationStatus.SENT, manoId, null,
                        new KeyValuePair(pkgPath, PathType.LOCAL.toString())));
            }
        }

        for(OsmInfoObject osmInfoObject : osmNsList) {

            if(osmInfoObject.getEpoch().compareTo(startSync) < 0) {

                operationId = UUID.randomUUID();
                descriptorId = osmInfoObject.getDescriptorId();
                version = osmInfoObject.getVersion();

                log.info("{} - Osm Ns Pkg with descriptor ID {} and version {} no longer present, deleting it",
                        manoId, descriptorId, version);

                String catDescriptorId = getCatDescriptorId(descriptorId, version);
                if(catDescriptorId == null)
                    catDescriptorId = descriptorId;
                sendNotification(new NsdDeletionNotificationMessage(null, catDescriptorId, version,
                        "all", operationId, ScopeType.SYNC, OperationStatus.SENT, manoId, null));

                osmInfoObjectRepository.delete(osmInfoObject);

                Utilities.deleteDir(new File(osmDir + "/" + descriptorId));
                Utilities.deleteDir(new File(osmDir + "/" + descriptorId + ".tar.gz"));

                List<OsmTranslationInformation> translationInformationList =
                        translationInformationRepository.findByOsmInfoIdAndOsmManoId(osmInfoObject.getId(), manoId);
                for(OsmTranslationInformation translationInformation : translationInformationList)
                    translationInformationRepository.delete(translationInformation);

            } else if(osmInfoObject.getState().equals(RecordState.NEW)) {

                operationId = UUID.randomUUID();
                descriptorId = osmInfoObject.getDescriptorId();
                version = osmInfoObject.getVersion();

                try {
                    pkgPath = createNsPkgSol006(osmInfoObject);
                } catch(Exception e) {
                    log.error("{} - Unable to generate TOSCA Ns Pkg with descriptor ID {} and version {}: {}",
                            manoId, descriptorId, version, e.getMessage());
                    log.debug("error details:\n", e);
                    continue;
                }

                log.info("{} - Uploading TOSCA Ns Pkg with descriptor ID {} and version {}", manoId, descriptorId, version);

                sendNotification(new NsdOnBoardingNotificationMessage(null, descriptorId, version,
                        "all", operationId, ScopeType.SYNC, OperationStatus.SENT, manoId, null,
                        new KeyValuePair(pkgPath, PathType.LOCAL.toString())));

            } else if(osmInfoObject.getState().equals(RecordState.CHANGED)) {

                operationId = UUID.randomUUID();
                descriptorId = osmInfoObject.getDescriptorId();
                version = osmInfoObject.getVersion();

                try {
                    pkgPath = createNsPkgSol006(osmInfoObject);
                } catch(Exception e) {
                    log.error("{} - Unable to generate TOSCA Ns Pkg with descriptor ID {} and version {}: {}",
                            manoId, descriptorId, version, e.getMessage());
                    log.debug("error details:\n", e);
                    continue;
                }

                List<OsmTranslationInformation> translationInformationList =
                        translationInformationRepository.findByOsmInfoIdAndOsmManoId(osmInfoObject.getId(), manoId);
                String catDescriptorId;
                String catVersion;

                if(!translationInformationList.isEmpty()) {
                    catDescriptorId = translationInformationList.get(0).getCatDescriptorId();
                    catVersion = translationInformationList.get(0).getDescriptorVersion();
                }else{
                    catDescriptorId = descriptorId;
                    catVersion = version;
                }

                log.info("{} - Updating TOSCA Ns Pkg with descriptor ID {} and version {}", manoId, descriptorId, version);

                sendNotification(new NsdChangeNotificationMessage(null, catDescriptorId, catVersion,
                        "all", operationId, ScopeType.SYNC, OperationStatus.SENT, manoId, null,
                        new KeyValuePair(pkgPath, PathType.LOCAL.toString())));
            }
        }

        for(OsmInfoObject osmInfoObject : osmVnfList) {
            if (osmInfoObject.getEpoch().compareTo(startSync) < 0) {

                operationId = UUID.randomUUID();
                descriptorId = osmInfoObject.getDescriptorId();
                version = osmInfoObject.getVersion();

                log.info("{} - Osm Vnf Pkg with descriptor ID {} and version {} no longer present, deleting it",
                        manoId, descriptorId, version);

                String catDescriptorId = getCatDescriptorId(descriptorId, version);
                if (catDescriptorId == null)
                    catDescriptorId = descriptorId;
                sendNotification(new VnfPkgDeletionNotificationMessage(null, catDescriptorId, version,
                        "all", operationId, ScopeType.SYNC, OperationStatus.SENT, manoId, null));

                osmInfoObjectRepository.delete(osmInfoObject);

                Utilities.deleteDir(new File(osmDir + "/" + descriptorId));
                Utilities.deleteDir(new File(osmDir + "/" + descriptorId + ".tar.gz"));

                List<OsmTranslationInformation> translationInformationList =
                        translationInformationRepository.findByOsmInfoIdAndOsmManoId(osmInfoObject.getId(), manoId);
                for (OsmTranslationInformation translationInformation : translationInformationList)
                    translationInformationRepository.delete(translationInformation);
            }
        }

        log.info("{} - Runtime synchronization, finished retrieving Osm Vnf and Ns Pkgs", manoId);
    }
}
