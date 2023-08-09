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
package it.nextworks.nfvmano.catalogue.plugins;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import it.nextworks.nfvmano.catalogue.engine.NsdManagementService;
import it.nextworks.nfvmano.catalogue.engine.VnfPackageManagementInterface;
import it.nextworks.nfvmano.catalogue.plugins.catalogue2catalogue.Catalogue;
import it.nextworks.nfvmano.catalogue.plugins.catalogue2catalogue.Catalogue2CataloguePlugin;
import it.nextworks.nfvmano.catalogue.plugins.catalogue2catalogue.api.nsd.DefaultApi;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.PluginOperationalState;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.PluginType;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.*;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.dummy.DummyMano;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.fivegrowth.FIVEGROWTH;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.onap.ONAP;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.osm.OSM;
import it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.repos.MANORepository;
import it.nextworks.nfvmano.catalogue.plugins.mano.DummyMANOPlugin;
import it.nextworks.nfvmano.catalogue.plugins.mano.onapCataloguePlugin.OnapPlugin;
import it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.repos.FivegrowthObjectRepository;
import it.nextworks.nfvmano.catalogue.plugins.mano.fivegrowthCataloguePlugin.SOPlugin;
import it.nextworks.nfvmano.catalogue.plugins.mano.onapCataloguePlugin.repos.OnapObjectRepository;
import it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.plugins.r10.OpenSourceMANOR10Plugin;
import it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.plugins.r4plus.OpenSourceMANOR4PlusPlugin;
import it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.repos.OsmInfoObjectRepository;
import it.nextworks.nfvmano.catalogue.plugins.mano.osmCataloguePlugin.repos.TranslationInformationRepository;
import it.nextworks.nfvmano.catalogue.plugins.siteInventory.SiteInventoryDriver;
import it.nextworks.nfvmano.catalogue.plugins.siteInventory.model.Credentials;
import it.nextworks.nfvmano.catalogue.plugins.siteInventory.model.NfvOrchestrator;
import it.nextworks.nfvmano.catalogue.plugins.vim.VIM;
import it.nextworks.nfvmano.catalogue.repos.*;
import it.nextworks.nfvmano.catalogue.translators.tosca.DescriptorsParser;
import it.nextworks.nfvmano.libs.common.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Service
public class PluginsManager {

    private static final Logger log = LoggerFactory.getLogger(PluginsManager.class);

    public Map<String, MANOPlugin> manoDrivers = new HashMap<>();

    public Map<String, Catalogue2CataloguePlugin> catalogueDrivers = new HashMap<>();

    private Resource[] resources;

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${catalogue.dummyMANO:false}")
    private boolean dummyMANO;

    @Value("${catalogue.manoPluginsConfigurations}")
    private String configurationsDir;

    @Value("${catalogue.vimPluginsConfiguration}")
    private String vimConfigurationsDir;

    @Value("${catalogue.mano.localPluginsConfig}")
    private boolean localMANOConfig;

    @Value("${catalogue.mano.siteInventoryPluginsConfig}")
    private boolean siteInventoryMANOConfig;

    @Value("${siteInventory.url}")
    private String siteInventoryUrl;

    @Value("${catalogue.skipDescriptorsLoad}")
    private boolean skipDescriptorsLoad;

    @Value("${kafkatopic.local}")
    private String localNotificationTopic;

    @Value("${kafkatopic.remote}")
    private String remoteNotificationTopic;

    @Value("${catalogue.mano.localDir}")
    private String manoDir;

    @Value("${environment.tmpDir}")
    private String tmpDir;

    @Value("${catalogue.logo}")
    private Path logo;

    @Value("${catalogue.scope}")
    private String catalogueScope;

    @Value("${catalogue.catalogueConfiguration}")
    private String catalogueConfigurationsDir;

    @Value("${mano.runtime.sync}")
    private boolean runtimeSync;

    @Value("${osm.vim.network.name.enabled}")
    private boolean useOsmVimNetworkName;

    @Value("${mano.runtime.sync.period.in.minute}")
    private long manoSyncPeriod;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private NsdManagementService nsdService;

    @Autowired
    private VnfPackageManagementInterface vnfdService;

    @Autowired
    private DescriptorsParser descriptorsParser;

    @Autowired
    private MANORepository MANORepository;

    @Autowired
    private VIMRepository vimRepository;

    @Autowired
    private CatalogueRepository catalogueRepository;

    @Autowired
    private NsdInfoRepository nsdInfoRepo;

    @Autowired
    private PnfdInfoRepository pnfdInfoRepo;

    @Autowired
    private VnfPkgInfoRepository vnfPkgInfoRepository;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private OsmInfoObjectRepository osmInfoObjectRepository;

    @Autowired
    private TranslationInformationRepository translationInformationRepository;

    @Autowired
    private OnapObjectRepository onapObjectRepository;

    @Autowired
    private FivegrowthObjectRepository fivegrowthObjectRepository;

    public PluginsManager() {

    }

    @PostConstruct
    public void initPlugins() {
        if (dummyMANO) {
            MANO dummy = new DummyMano("DUMMY", MANOType.DUMMY);
            dummy.setPluginOperationalState(PluginOperationalState.ENABLED);
            MANORepository.saveAndFlush(dummy);
            try {
                addMANO(dummy);
            } catch (MalformattedElementException e) {
                log.error("Unable to instantiate DUMMY MANO Plugin. Malformatted MANO: " + e.getMessage());
            }
        } else {
            if (localMANOConfig) {
                log.info("Going to retrieve MANO Plugin configurations from local folder");
                resources = loadMANOConfigurations();
                ObjectMapper mapper = new ObjectMapper();
                if (resources != null) {
                    for (int i = 0; i < resources.length; i++) {
                        if (resources[i].isFile() || resources[i] instanceof ClassPathResource) {
                            try {
                                InputStream resource = resources[i].getInputStream();
                                String tmp;
                                try (final Reader reader = new InputStreamReader(resource)) {
                                    tmp = CharStreams.toString(reader);
                                }
                                log.debug("Loading MANO configuration from config file #" + i + "");
                                MANO newMano = mapper.readValue(tmp, MANO.class);
                                log.debug("Successfully loaded configuration for MANO with manoId: " + newMano.getManoId());
                                try {
                                    log.debug("Creating MANO Plugin with manoId " + newMano.getManoId() + " from configuration file");
                                    createMANOPlugin(newMano, true);
                                } catch (AlreadyExistingEntityException e) {
                                    log.debug("MANO with manoId " + newMano.getManoId() + " already present in DB");
                                } catch (MethodNotImplementedException e) {
                                    log.error("Unsupported MANO type for MANO with manoId: " + newMano.getManoId());
                                } catch (MalformattedElementException e) {
                                    log.error("Malformatted MANO with manoId " + newMano.getManoId() + ": "
                                            + e.getMessage());
                                }
                            } catch (IOException e) {
                                log.error("Unable to retrieve MANO configuration file: " + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            if(siteInventoryMANOConfig){
                log.info("Going to retrieve MANO plugin configurations from Site Inventory");
                List<NfvOrchestrator> nfvOrchestratorList = null;
                try {
                    SiteInventoryDriver siteInventoryDriver = new SiteInventoryDriver(siteInventoryUrl);
                    nfvOrchestratorList = siteInventoryDriver.getNFVOs();
                }catch(FailedOperationException e) {
                    log.error("Unable to retrieve MANO configurations from site inventory: " + e.getMessage());
                }
                String newManoId = null;

                if(nfvOrchestratorList != null) {
                    for (NfvOrchestrator nfvOrchestrator : nfvOrchestratorList) {
                        try {
                            MANO newMano = null;
                            newManoId = nfvOrchestrator.getName();
                            if (newManoId == null)
                                log.error("Unable to retrieve MANO plugin configuration: MANO ID is null");
                            else if (nfvOrchestrator.getType() == null || nfvOrchestrator.getCredentials() == null || nfvOrchestrator.getSite() == null || nfvOrchestrator.getVersion() == null || nfvOrchestrator.getOperationalState() == null || nfvOrchestrator.getCredentials().getHost() == null)
                                log.error("Unable to retrieve MANO plugin configuration for MANO with ID {}", newManoId);
                            else {
                                Credentials manoCredentials = nfvOrchestrator.getCredentials();
                                if (nfvOrchestrator.getType().equalsIgnoreCase("OSM"))
                                    newMano = new OSM(newManoId, manoCredentials.getHost(), String.valueOf(manoCredentials.getPort()), manoCredentials.getUsername(), manoCredentials.getPassword(), manoCredentials.getProject(), MANOType.valueOf(nfvOrchestrator.getType().toUpperCase() + nfvOrchestrator.getVersion().toUpperCase()), nfvOrchestrator.getSite().getName(), null);
                                else if (nfvOrchestrator.getType().equalsIgnoreCase("ONAP"))
                                    newMano = new ONAP(newManoId, manoCredentials.getHost(), String.valueOf(manoCredentials.getPort()), MANOType.valueOf(nfvOrchestrator.getType().toUpperCase()), nfvOrchestrator.getSite().getName());
                                else if (nfvOrchestrator.getType().equalsIgnoreCase("5gr-so"))
                                    newMano = new FIVEGROWTH(newManoId, manoCredentials.getHost(), String.valueOf(manoCredentials.getPort()), MANOType.SO_5GROWTH, nfvOrchestrator.getSite().getName());
                                else
                                    throw new MethodNotImplementedException("Unsupported MANO type");
                                newMano.setPluginOperationalState(PluginOperationalState.valueOf(nfvOrchestrator.getOperationalState().toUpperCase()));
                                log.debug("Creating MANO Plugin with manoId " + newMano.getManoId() + " from site inventory configuration");
                                createMANOPlugin(newMano, true);
                            }
                        }catch (AlreadyExistingEntityException e) {
                            log.debug("MANO with manoId " + newManoId + " already present in DB");
                        } catch (MethodNotImplementedException e) {
                            log.error("Unsupported MANO type for MANO with manoId: " + newManoId);
                        } catch (MalformattedElementException e) {
                            log.error("Malformed MANO with manoId " + newManoId + ": " + e.getMessage());
                        }
                    }
                }
            }

            log.debug("Loading MANO info from DB");
            List<MANO> manos = MANORepository.findAll();

            if (manos.isEmpty())
                log.debug("No MANO info stored in DB");

            for (MANO mano : manos) {
                try {
                    log.debug("Instantiating MANO with manoId: " + mano.getManoId());
                    if (mano.getPluginOperationalState() != PluginOperationalState.DELETING) {
                        addMANO(mano);
                        log.debug("MANO with manoId " + mano.getManoId() + " successfully instantiated");
                    } else {
                        log.debug("Found MANO with manoId " + mano.getManoId() + " in DELETING state: removing it");
                        MANORepository.deleteById(mano.getId());
                        log.debug("MANO with manoId " + mano.getManoId() + " successfully deleted");
                    }

                } catch (MalformattedElementException e) {
                    log.error("Malformatted MANO: " + e.getMessage() + ". Skipping");
                }
            }

        }

        if (catalogueScope.equalsIgnoreCase("private")) {
            log.debug("Instantiating 5G Catalogue drivers...");

            log.debug("Loading Catalogue info from DB");
            List<Catalogue> catalogues = catalogueRepository.findAll();

            if (catalogues.isEmpty())
                log.debug("No Catalogue info stored in DB");

            for (Catalogue catalogue : catalogues) {
                try {
                    log.debug("Instantiating Catalogue with catalogueId: " + catalogue.getCatalogueId());
                    if (catalogue.getPluginOperationalState() != PluginOperationalState.DELETING) {
                        log.debug("Instantiating Catalogue with catalogueId: " + catalogue.getCatalogueId());
                        create5GCatalogue(catalogue, true);
                        log.debug("Catalogue with catalogueId " + catalogue.getCatalogueId() + " successfully instantiated");
                    } else {
                        log.debug("Found Catalogue with catalogueId " + catalogue.getCatalogueId() + " in DELETING state: removing it");
                        catalogueRepository.deleteById(catalogue.getId());
                        log.debug("Catalogue with catalogueId " + catalogue.getCatalogueId() + " successfully deleted");
                    }

                } catch (AlreadyExistingEntityException e) {
                    log.warn("Catalogue with catalogueId " + catalogue.getCatalogueId() + " already exists");
                } catch (FailedOperationException e) {
                    log.warn(e.getMessage());
                }
            }

            log.debug("Loading Catalogue info from configuration files");

            resources = load5GCatalogueConfigurations();

            ObjectMapper mapper = new ObjectMapper();

            if (resources != null) {
                for (int i = 0; i < resources.length; i++) {
                    if (resources[i].isFile() || resources[i] instanceof ClassPathResource) {
                        try {
                            InputStream resource = resources[i].getInputStream();
                            String tmp;
                            try (final Reader reader = new InputStreamReader(resource)) {
                                tmp = CharStreams.toString(reader);
                            }
                            log.debug("Loading 5G Catalogue configuration from config file #" + i + "");
                            Catalogue newCatalogue = mapper.readValue(tmp, Catalogue.class);
                            log.debug("Successfully loaded configuration for 5G Catalogue with catalogueId: "
                                    + newCatalogue.getCatalogueId());
                            try {
                                log.debug("Creating 5G Catalogue Plugin with catalogueId " + newCatalogue.getCatalogueId()
                                        + " from configuration file");
                                create5GCatalogue(newCatalogue, false);
                            } catch (AlreadyExistingEntityException e) {
                                log.warn("5G Catalogue with catalogueId " + newCatalogue.getCatalogueId() + " already present in DB");
                            } catch (FailedOperationException e) {
                                log.warn(e.getMessage());
                            }
                        } catch (IOException e) {
                            log.error("Unable to parse 5G Catalogue configuration file: " + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    public void addMANO(MANO mano) throws MalformattedElementException {
        try {
            MANOPlugin manoPlugin = buildMANOPlugin(mano);

            // Init kafka consumer and enable plugin
            manoPlugin.init();
            manoDrivers.put(mano.getManoId(), manoPlugin);

            log.debug("Loaded plugin for MANO " + mano.getManoId());

            // TODO: notify MANO plugin creation
        } catch (Exception e) {
            log.error("Failed to add MANO plugin: " + e.getMessage());
        }
    }

    private MANOPlugin buildMANOPlugin(MANO mano) throws MalformattedElementException {
        if (mano.getManoType().equals(MANOType.DUMMY)) {
            return new DummyMANOPlugin(mano.getManoType(), mano, bootstrapServers, nsdService, vnfdService, descriptorsParser,
                    localNotificationTopic, remoteNotificationTopic, kafkaTemplate, runtimeSync);
        } else if (mano.getManoType().equals(MANOType.OSMR3)) {
            Path osmr3Dir = Paths.get(manoDir, "/" + MANOType.OSMR3.toString().toLowerCase());
            return null;
            //TODO activate R3
            /*return new OpenSourceMANOR3Plugin(mano.getManoType(), mano, bootstrapServers, nsdService, vnfdService, descriptorsParser,
                    localNotificationTopic, remoteNotificationTopic, kafkaTemplate, osmr3Dir, logo);*/
        } else if (mano.getManoType().equals(MANOType.OSMR4) || mano.getManoType().equals(MANOType.OSMR5) || mano.getManoType().equals(MANOType.OSMR6) || mano.getManoType().equals(MANOType.OSMR7) || mano.getManoType().equals(MANOType.OSMR8)) {
            Path osmr4PlusDir = Paths.get(manoDir, "/" + mano.getManoType().toString().toLowerCase());
            return new OpenSourceMANOR4PlusPlugin(mano.getManoType(), mano, bootstrapServers, osmInfoObjectRepository, translationInformationRepository, localNotificationTopic, remoteNotificationTopic, kafkaTemplate, osmr4PlusDir, Paths.get(tmpDir), logo, runtimeSync, manoSyncPeriod, useOsmVimNetworkName);
        } else if (mano.getManoType().equals(MANOType.OSMR9) || mano.getManoType().equals(MANOType.OSMR10)) {
            Path osmr10Dir = Paths.get(manoDir, "/" + mano.getManoType().toString().toLowerCase());
            return new OpenSourceMANOR10Plugin(mano.getManoType(), mano, bootstrapServers, localNotificationTopic,
                    remoteNotificationTopic, kafkaTemplate, runtimeSync, osmInfoObjectRepository,
                    translationInformationRepository, osmr10Dir, logo, manoSyncPeriod, Paths.get(tmpDir), useOsmVimNetworkName);
        } else if (mano.getManoType().equals(MANOType.ONAP)) {
            Path onapDir = Paths.get(manoDir, "/" + mano.getManoType().toString().toLowerCase());
            return new OnapPlugin(mano.getManoType(), mano, bootstrapServers, localNotificationTopic, remoteNotificationTopic, kafkaTemplate, onapObjectRepository, onapDir, Paths.get(tmpDir), runtimeSync, manoSyncPeriod);
        } else if (mano.getManoType().equals(MANOType.SO_5GROWTH)) {
                Path soDir = Paths.get(manoDir, "/" + mano.getManoType().toString().toLowerCase());
                return new SOPlugin(mano.getManoType(), mano, bootstrapServers, localNotificationTopic, remoteNotificationTopic, kafkaTemplate, fivegrowthObjectRepository, soDir, Paths.get(tmpDir), runtimeSync, manoSyncPeriod);
        } else {
            throw new MalformattedElementException("Unsupported MANO type. Skipping");
        }
    }

    public String createMANOPlugin(MANO mano, boolean isStartingPhase)
            throws AlreadyExistingEntityException, MethodNotImplementedException, MalformattedElementException {
        String manoId = mano.getManoId();
        if (MANORepository.findByManoId(manoId).isPresent()) {
            log.debug("A MANO with the same ID is already available in DB - Skipping");
            throw new AlreadyExistingEntityException(
                    "A MANO with the same ID is already available in DB - Skipping");
        }

        if (mano.getPluginOperationalState() == null) {
            mano.setPluginOperationalState(PluginOperationalState.ENABLED);
        }
        MANOType type = mano.getManoType();
        log.debug("RECEIVED MANO:\nMANO ID: " + manoId + "\nMANO TYPE: " + type);

        if (type == MANOType.OSMR3 || type == MANOType.OSMR4 || type == MANOType.OSMR5 || type == MANOType.OSMR6 ||
                type == MANOType.OSMR7 || type == MANOType.OSMR8 || type == MANOType.OSMR9 || type == MANOType.OSMR10) {
            log.debug("Processing request for creating " + type + " Plugin");
            OSM osm = (OSM) mano;
            OSM targetOsm = new OSM(
                    osm.getManoId(),
                    osm.getIpAddress(),
                    osm.getPort(),
                    osm.getUsername(),
                    osm.getPassword(),
                    osm.getProject(),
                    type,
                    osm.getManoSite(),
                    osm.getVimAccounts()
            );
            targetOsm.setPluginOperationalState(PluginOperationalState.ENABLED);
            targetOsm.isValid();
            log.debug("Persisting OSM MANO with manoId: " + manoId);
            OSM createdMano = MANORepository.saveAndFlush(targetOsm);
            log.debug("OSM MANO with manoId " + manoId + " successfully persisted");
            if(!isStartingPhase) {
                log.debug("Instantiating OSM MANO with manoId: " + manoId);
                try {
                    addMANO(createdMano);
                } catch (MalformattedElementException e) {
                    log.error("Unsupported MANO type");
                }
                log.debug("OSM MANO with manoId " + manoId + " successfully instantiated");
            }
            return String.valueOf(createdMano.getId());
        } else if (type == MANOType.ONAP) {
            log.debug("Processing request for creating " + type + " Plugin");
            ONAP onap = (ONAP) mano;
            ONAP targetOnap = new ONAP(
                    onap.getManoId(),
                    onap.getIpAddress(),
                    onap.getPort(),
                    type,
                    onap.getManoSite()
            );
            targetOnap.setPluginOperationalState(PluginOperationalState.ENABLED);
            targetOnap.isValid();
            log.debug("Persisting ONAP MANO with manoId: " + manoId);
            ONAP createdMano = MANORepository.saveAndFlush(targetOnap);
            log.debug("Onap MANO with manoId " + manoId + " successfully persisted");
            if(!isStartingPhase) {
                log.debug("Instantiating ONAP MANO with manoId: " + manoId);
                try {
                    addMANO(createdMano);
                } catch (MalformattedElementException e) {
                    log.error("Unsupported MANO type");
                }
                log.debug("ONAP MANO with manoId " + manoId + " successfully instantiated");
            }
            return String.valueOf(createdMano.getId());
        } else if (type == MANOType.SO_5GROWTH) {
            log.debug("Processing request for creating " + type + " Plugin");
            FIVEGROWTH so = (FIVEGROWTH) mano;
            FIVEGROWTH targetSo = new FIVEGROWTH(
                    so.getManoId(),
                    so.getIpAddress(),
                    so.getPort(),
                    type,
                    so.getManoSite()
            );
            targetSo.setPluginOperationalState(PluginOperationalState.ENABLED);
            targetSo.isValid();
            log.debug("Persisting 5GROWTH MANO with manoId: " + manoId);
            FIVEGROWTH createdMano = MANORepository.saveAndFlush(targetSo);
            log.debug("5GROWTH MANO with manoId " + manoId + " successfully persisted");
            if(!isStartingPhase) {
                log.debug("Instantiating 5GROWTH MANO with manoId: " + manoId);
                try {
                    addMANO(createdMano);
                } catch (MalformattedElementException e) {
                    log.error("Unsupported MANO type");
                }
                log.debug("5GROWTH MANO with manoId " + manoId + " successfully instantiated");
            }
            return String.valueOf(createdMano.getId());
        } else if (type == MANOType.DUMMY) {
            log.debug("Processing request for creating " + type + " Plugin");
            DummyMano dummyMano = (DummyMano) mano;
            log.debug("Persisting DUMMY MANO with manoId: " + manoId);
            DummyMano createdMano = MANORepository.saveAndFlush(dummyMano);
            log.debug("DUMMY MANO with manoId " + manoId + " successfully persisted");
            log.debug("Instantiating DUMMY MANO with manoId: " + manoId);
            try {
                addMANO(createdMano);
            } catch (MalformattedElementException e) {
                log.error("Unsupported MANO type");
            }
            log.debug("DUMMY MANO with manoId " + manoId + " successfully instantiated");
            return String.valueOf(createdMano.getId());
        } else {
            log.error("Unsupported MANO type");
            throw new MethodNotImplementedException("Unsupported MANO type");
        }
    }

    public MANO getMANOPlugin(String manoId) throws NotExistingEntityException {

        log.debug("Processing request for retrieving MANO Plugin with manoId {}", manoId);

        Optional<MANO> optionalMANO = MANORepository.findByManoId(manoId);

        MANO mano = null;

        if (optionalMANO.isPresent()) {
            mano = optionalMANO.get();
        } else {
            throw new NotExistingEntityException("MANO Plugin with manoId " + manoId + " not present in DB");
        }

        return mano;
    }

    public List<MANO> getAllMANOPlugins() {

        log.debug("Processing request for retrieving all MANO Plugins");

        List<MANO> manos = new ArrayList<>();
        manos = MANORepository.findAll();
        return manos;
    }

    public MANO updateMANOPlugin(String manoId, MANO mano) throws NotExistingEntityException, FailedOperationException {

        log.debug("Processing request for updating MANO Plugin with manoId {}", manoId);

        Optional<MANO> optionalMANO = MANORepository.findByManoId(manoId);

        MANO manoInfo = null;

        if (optionalMANO.isPresent()) {
            manoInfo = optionalMANO.get();

            if (manoInfo.getPluginOperationalState() == mano.getPluginOperationalState()) {
                throw new FailedOperationException("MANO Plugin with manoId " + manoId + " already in " + manoInfo.getPluginOperationalState() + " operational state");
            } else {
                manoInfo.setPluginOperationalState(mano.getPluginOperationalState());
                MANORepository.saveAndFlush(manoInfo);

                MANOPlugin manoPlugin = manoDrivers.get(manoInfo.getManoId());
                manoPlugin.setPluginOperationalState(manoInfo.getPluginOperationalState());
                manoDrivers.replace(manoInfo.getManoId(), manoPlugin);
            }
        } else {
            throw new NotExistingEntityException("MANO Plugin with manoId " + manoId + " not present in DB");
        }

        return manoInfo;
    }

    private Resource[] loadMANOConfigurations() {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource[] resources = null;
        try {
            log.debug("Loading MANO configuration files from: " + configurationsDir);
            resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                    .getResources(configurationsDir + "/*.json");
            log.debug("MANO configuration files successfully loaded");
        } catch (IOException e) {
            log.error("Unable to load MANO configuration files: " + e.getMessage());
        }

        return resources;
    }

    public String createVIMPlugin(VIM vim)
            throws AlreadyExistingEntityException, MethodNotImplementedException, MalformattedElementException {

        return null;
    }

    public VIM getVIMPlugin(String vimId) throws NotExistingEntityException {

        log.debug("Processing request for retrieving VIM Plugin with vimId {}", vimId);

        Optional<VIM> optionalVIM = vimRepository.findByVimId(vimId);

        VIM vim = null;

        if (optionalVIM.isPresent()) {
            vim = optionalVIM.get();
        } else {
            throw new NotExistingEntityException("VIM Plugin with vimId " + vimId + " not present in DB");
        }

        return vim;
    }

    public List<VIM> getAllVIMPlugins() {

        log.debug("Processing request for retrieving all VIM Plugins");

        List<VIM> vims = new ArrayList<>();
        vims = vimRepository.findAll();
        return vims;
    }

    public void addCatalogue(Catalogue catalogue) throws FailedOperationException {
        try {
            Catalogue2CataloguePlugin catalogue2CataloguePlugin = buildCataloguePlugin(catalogue);

            // Init kafka consumer
            catalogue2CataloguePlugin.init();

            catalogueDrivers.put(catalogue.getCatalogueId(), catalogue2CataloguePlugin);

            log.debug("Loaded plugin for 5G Catalogue " + catalogue.getCatalogueId());

            // TODO: notify 5G Catalogue plugin creation
        } catch (Exception e) {
            log.error("Failed to add 5G Catalogue plugin: " + e.getMessage());
            catalogueRepository.deleteById(catalogue.getId());
            throw new FailedOperationException("Failed to add 5G Catalogue plugin: " + e.getMessage());
        }
    }

    private Catalogue2CataloguePlugin buildCataloguePlugin(Catalogue catalogue) {
        return new Catalogue2CataloguePlugin(
                catalogue.getCatalogueId(),
                PluginType.C2C,
                catalogue,
                bootstrapServers,
                nsdService,
                vnfdService,
                new DefaultApi(catalogue),
                new it.nextworks.nfvmano.catalogue.plugins.catalogue2catalogue.api.vnf.DefaultApi(catalogue),
                nsdInfoRepo,
                pnfdInfoRepo,
                vnfPkgInfoRepository,
                localNotificationTopic,
                remoteNotificationTopic,
                kafkaTemplate,
                skipDescriptorsLoad
        );
    }

    public String create5GCatalogue(Catalogue catalogue, boolean isInternal)
            throws AlreadyExistingEntityException, FailedOperationException {
        String catalogueId = catalogue.getCatalogueId();

        if (catalogueRepository.findByCatalogueId(catalogueId).isPresent() && !isInternal) {
            log.error("A 5G Catalogue with the same ID is already available in DB - Not acceptable");
            throw new AlreadyExistingEntityException(
                    "A 5G Catalogue with the same ID is already available in DB - Not acceptable");
        }
        ObjectMapper mapper = new ObjectMapper();
        log.debug("Persisting 5G Catalogue with catalogueId: " + catalogueId);
        if (catalogue.getPluginOperationalState() == null) {
            catalogue.setPluginOperationalState(PluginOperationalState.ENABLED);
        }

        Catalogue createdCatalogue = catalogueRepository.saveAndFlush(catalogue);
        log.debug("5G Catalogue with catalogueId " + catalogueId + " successfully persisted");

        log.debug("Instantiating 5G Catalogue with catalogueId: " + catalogueId);
        addCatalogue(catalogue);
        log.debug("5G Catalogue with catalogueId " + catalogueId + " successfully instantiated");

        return String.valueOf(createdCatalogue.getId());
    }

    public Catalogue get5GCataloguePlugin(String catalogueId) throws NotExistingEntityException {

        log.debug("Processing request for retrieving 5G Catalogue Plugin with catalogueId {}", catalogueId);

        Optional<Catalogue> optionalCatalogue = catalogueRepository.findByCatalogueId(catalogueId);

        Catalogue catalogue = null;

        if (optionalCatalogue.isPresent()) {
            catalogue = optionalCatalogue.get();
        } else {
            throw new NotExistingEntityException("Catalogue Plugin with catalogueId " + catalogueId + " not present in DB");
        }

        return catalogue;
    }

    public List<Catalogue> getAll5GCataloguePlugins() {

        log.debug("Processing request for retrieving all 5G Catalogue Plugins");

        List<Catalogue> catalogues = new ArrayList<>();
        catalogues = catalogueRepository.findAll();
        return catalogues;
    }

    public Catalogue update5GCataloguePlugin(String catalogueId, Catalogue catalogue) throws NotExistingEntityException, FailedOperationException {

        log.debug("Processing request for updating 5G Catalogue Plugin with catalogueId {}", catalogueId);

        Optional<Catalogue> optionalCatalogue = catalogueRepository.findByCatalogueId(catalogueId);

        Catalogue catalogueInfo = null;

        if (optionalCatalogue.isPresent()) {
            catalogueInfo = optionalCatalogue.get();

            if (catalogueInfo.getPluginOperationalState() == catalogue.getPluginOperationalState()) {
                throw new FailedOperationException("Catalogue Plugin with catalogueId " + catalogueId + " already in " + catalogueInfo.getPluginOperationalState() + " operational state");
            } else {
                catalogueInfo.setPluginOperationalState(catalogue.getPluginOperationalState());
                catalogueRepository.saveAndFlush(catalogueInfo);

                Catalogue2CataloguePlugin catalogue2CataloguePlugin = catalogueDrivers.get(catalogueInfo.getCatalogueId());
                catalogue2CataloguePlugin.setPluginOperationalState(catalogueInfo.getPluginOperationalState());
                catalogueDrivers.replace(catalogueInfo.getCatalogueId(), catalogue2CataloguePlugin);
            }
        } else {
            throw new NotExistingEntityException("Catalogue Plugin with catalogueId " + catalogueId + " not present in DB");
        }

        return catalogueInfo;
    }

    private Resource[] load5GCatalogueConfigurations() {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource[] resources = null;
        try {
            log.debug("Loading 5G Catalogue configuration files from: " + catalogueConfigurationsDir);
            resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                    .getResources(catalogueConfigurationsDir + "/*.json");
            log.debug("5G Catalogue configuration files successfully loaded");
        } catch (IOException e) {
            log.error("Unable to load 5G Catalogue configuration files: " + e.getMessage());
        }

        return resources;
    }
}
