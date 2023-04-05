/*
 * Copyright (c) 2019 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.sebastian.vsfm.sbi;

import java.util.*;

import it.nextworks.nfvmano.catalogue.domainLayer.*;
import it.nextworks.nfvmano.catalogue.domainLayer.customDomainLayer.NHNspDomainLayer;
import it.nextworks.nfvmano.catalogue.domainLayer.customDomainLayer.OsmNfvoDomainLayer;

import it.nextworks.nfvmano.catalogue.domainLayer.customDomainLayer.OsmNspDomainLayer;
import it.nextworks.nfvmano.catalogue.domainLayer.customDomainLayer.SonataNspDomainLayer;
import it.nextworks.nfvmano.catalogues.domainLayer.services.DomainCatalogueService;
import it.nextworks.nfvmano.catalogues.domainLayer.services.DomainCatalogueSubscriptionService;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
//import it.nextworks.nfvmano.sebastian.nsmf.NsLcmService;
import it.nextworks.nfvmano.sebastian.vsfm.VsLcmService;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.dummy.DummyRestClient;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.dummy.repos.DummyTranslationInformationRepository;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.neutralhost.NeutralHostingRestClient;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.neutralhost.repos.NHTranslationInformationRepository;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.OsmRestClient;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.repos.OsmTranslationInformationRepository;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.SonataRestClient;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.repos.SonataTranslationInformationRepository;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.nsmf.messages.CreateNsiIdRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.InstantiateNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.ModifyNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.TerminateNsiRequest;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.nsmf.NsmfRestClient;

/**
 * Class used to manage the interaction with external NSMF when this is exposed via REST.
 *
 * @author nextworks
 */
@Service
public class NsmfInteractionHandler implements NsmfLcmProviderInterface {

    private static final Logger log = LoggerFactory.getLogger(NsmfInteractionHandler.class);

    // domainId -> Interface
    private Map<String, NsmfLcmProviderInterface> drivers = new HashMap<String, NsmfLcmProviderInterface>();

    
    private NsmfRestClient nsmfRestClient;

    @Autowired
    private AdminService adminService;

    @Autowired
    private DomainCatalogueService domainCatalogueService;

    @Autowired
    private DomainCatalogueSubscriptionService domainCatalogueSubscriptionService;

    @Autowired
    private CommonUtils utils;

    @Autowired
    private NHTranslationInformationRepository nhTranslationInformationRepository;

    @Autowired
    private SonataTranslationInformationRepository sonataTranslationInformationRepository;

    @Autowired
    private OsmTranslationInformationRepository osmTranslationInformationRepository;

    @Autowired
    private DummyTranslationInformationRepository dummyTranslationInformationRepository;

    @Autowired
    private NsmfLcmOperationPollingManager nsmfLcmOperationPollingManager;

    @Autowired
    private VsLcmService vsLcmService;

    @Value("${dummy.multidomain.enabled:false}")
    private boolean dummyMultidomainMode;

    @Value("${vsm.sbi.nsmf.defaultDomain:defaultDomain}")
    private String defaultDomain;

    @Value("${vsmf.notifications.url}")
    private String notificationCallbackUri;

    /**
     * This method initializes the NSMF interaction handler with the drivers to interact
     * with the different NSMFs.
     * It reads the configured NSMFs from the domain inventory and for each of them
     * instantiate the related driver.
     */
    public void init() {
        List<Domain> domains = domainCatalogueService.getAllDomains();
        log.info("Getting domains information from Domain Layer Catalogue");
        for (Domain domain : domains)
            addDomainDriver(domain);
        log.info("Subscribing to Domain Layer Catalogue");
        DomainCatalogueSubscriptionRequest request = new DomainCatalogueSubscriptionRequest();
        request.setSubscriptionType(SubscriptionType.DOMAIN_SUBSCRIPTION);
        request.setCallbackURI(String.format(notificationCallbackUri + "%s", "/domainLayerCatalogue/notifications"));
        try {
            domainCatalogueSubscriptionService.subscribe(request);
        }catch (FailedOperationException e){
            log.error("Subscription to Domain Layer Catalogue failed: {}", e.getMessage());
        }
    }

    public void addDomainDriver(Domain domain){
        String domainId = domain.getDomainId();
        DomainInterface domainInterface = domain.getDomainInterface();
        String completeUrl = domainInterface.getUrl() + ":" + domainInterface.getPort();
        log.info("Adding driver for domain with Id {}", domainId);
        if (domain.getDomainStatus().equals(DomainStatus.ACTIVE)) {
            if (domainInterface.getInterfaceType().equals(InterfaceType.HTTP)) {
                for (DomainLayer domainLayer : domain.getOwnedLayers()) {
                    if (domainLayer.getDomainLayerType().equals(DomainLayerType.NETWORK_SLICE_PROVIDER)) {
                        NspDomainLayer nspDomainLayer = (NspDomainLayer) domainLayer;
                        NsmfLcmProviderInterface restClient = null;
                        if (nspDomainLayer.getNspNbiType().equals(NspNbiType.NEUTRAL_HOSTING)) {
                            NHNspDomainLayer nhNspDomainLayer = (NHNspDomainLayer) domainLayer;
                            OsmNfvoDomainLayer osmNfvoDomainLayer = getOsmDomainLayer(domain);
                            if (!dummyMultidomainMode) {
                                restClient = new NeutralHostingRestClient(domainId, completeUrl, nhNspDomainLayer.getUserId(), nhNspDomainLayer.getTenantId(), osmNfvoDomainLayer, nhTranslationInformationRepository, utils, nsmfLcmOperationPollingManager);
                            } else {
                                restClient = new DummyRestClient(domainId, utils, vsLcmService, dummyTranslationInformationRepository);
                            }
                            log.info("Rest Client for NEUTRAL_HOSTING NSP instantiated");
                        } else if (nspDomainLayer.getNspNbiType().equals(NspNbiType.SONATA)) {
                            SonataNspDomainLayer sonataNspDomainLayer = (SonataNspDomainLayer) domainLayer;
                            if (!dummyMultidomainMode) {
                                restClient = new SonataRestClient(domainId, completeUrl, sonataNspDomainLayer.getUsername(), sonataNspDomainLayer.getPassword(), sonataTranslationInformationRepository, utils, nsmfLcmOperationPollingManager);
                            } else {
                                restClient = new DummyRestClient(domainId, utils, vsLcmService, dummyTranslationInformationRepository);
                            }
                            log.info("Rest Client for SONATA NSP instantiated");
                        } else if (nspDomainLayer.getNspNbiType().equals(NspNbiType.THREE_GPP)) {
                            restClient = new NsmfRestClient(domainId, completeUrl, adminService);
                            log.info("Rest Client for 3GPP like NSP instantiated");
                        } else if (nspDomainLayer.getNspNbiType().equals(NspNbiType.OSM)){
                            OsmNspDomainLayer osmNspDomainLayer = (OsmNspDomainLayer) domainLayer;
                            if (!dummyMultidomainMode) {
                                restClient = new OsmRestClient(domainId, completeUrl, osmNspDomainLayer.getUsername(), osmNspDomainLayer.getPassword(), osmNspDomainLayer.getProject(), osmNspDomainLayer.getVimAccount(), osmTranslationInformationRepository, utils, nsmfLcmOperationPollingManager);
                            } else {
                                restClient = new DummyRestClient(domainId, utils, vsLcmService, dummyTranslationInformationRepository);
                            }
                            log.info("Rest Client for OSM NSP instantiated");
                        }
                        if (restClient != null)
                            this.drivers.put(domainId, restClient);
                        else
                            log.debug("NspNbiType not supported");
                    }
                }
            } else
                log.debug("Domain with ID {} has not a REST interface", domainId);
        } else
            log.debug("Domain with ID {} is not ACTIVE", domainId);
    }

    public void removeDomainDriver(Domain domain){
        String domainId = domain.getDomainId();
        log.info("Removing driver for domain with Id {}", domainId);
        this.drivers.remove(domainId);
    }

    private OsmNfvoDomainLayer getOsmDomainLayer(Domain domain) {
        for (DomainLayer domainLayer : domain.getOwnedLayers()) {
            if (domainLayer.getDomainLayerType().equals(DomainLayerType.NETWORK_SERVICE_PROVIDER)) {
                NfvoDomainLayer nfvoDomainLayer = (NfvoDomainLayer) domainLayer;
                if (nfvoDomainLayer.getManoNbiType().equals(ManoNbiType.OSM_DRIVER))
                    return (OsmNfvoDomainLayer) domainLayer;
            }
        }
        return null;
    }

    /**
     * Just for backward compatibility issues with the single domain case. To be removed.
     *
     * @param nsmfRestServerUrl
     */
    public void setNsmfClientConfiguration(NsmfType nsmfType, String nsmfRestServerUrl, String username, String password, String project, String vimAccount) {//TODO always load default from application.properties??
        AbstractNsmfDriver nsmfRestClient;
        switch(nsmfType){
            case NSMF_3GPP_LIKE:
                nsmfRestClient = new NsmfRestClient("defaultDomain", nsmfRestServerUrl, adminService);
                break;
            case OSM:
                nsmfRestClient = new OsmRestClient("defaultDomain", nsmfRestServerUrl, username, password, project, vimAccount, osmTranslationInformationRepository, utils, nsmfLcmOperationPollingManager);
                break;
            default:
                throw new IllegalArgumentException("NsmfType " + nsmfType.toString() + " not supported as default domain");

        }
        
        this.drivers.put(defaultDomain, nsmfRestClient);
        log.info("Default rest client instantiated for {} NSP", nsmfType.toString());
    }

    @Override
    public String createNetworkSliceIdentifier(CreateNsiIdRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
            MalformattedElementException, NotPermittedOperationException {
		NsmfLcmProviderInterface domainDriver = null;
		        
		if (domainId == null){
		 	domainId = defaultDomain;
		}
		domainDriver= drivers.get(domainId);
		
        return domainDriver.createNetworkSliceIdentifier(request, domainId, tenantId);
    }

    @Override
    public void instantiateNetworkSlice(InstantiateNsiRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
            MalformattedElementException, NotPermittedOperationException {
        
		NsmfLcmProviderInterface domainDriver = null;
			
		if (domainId == null){
		 	domainId = defaultDomain;
		}
		domainDriver= drivers.get(domainId);
		domainDriver.instantiateNetworkSlice(request, domainId, tenantId);
    }

    @Override
    public void modifyNetworkSlice(ModifyNsiRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
            MalformattedElementException, NotPermittedOperationException {
          
		NsmfLcmProviderInterface domainDriver = null;
		        
		if (domainId == null){
		 	domainId = defaultDomain;
		}
		domainDriver= drivers.get(domainId);
         	domainDriver.modifyNetworkSlice(request, domainId, tenantId);
    }

    @Override
    public void terminateNetworkSliceInstance(TerminateNsiRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
            MalformattedElementException, NotPermittedOperationException {
           
		NsmfLcmProviderInterface domainDriver = null;
		        
		if (domainId == null){
		 	domainId = defaultDomain;
		}
		domainDriver= drivers.get(domainId);
         domainDriver.terminateNetworkSliceInstance(request, domainId, tenantId);
    }

    @Override
    public List<NetworkSliceInstance> queryNetworkSliceInstance(GeneralizedQueryRequest request, String domainId, String tenantId)
            throws MethodNotImplementedException, FailedOperationException, MalformattedElementException {
         
		NsmfLcmProviderInterface domainDriver = null;
		        
		if (domainId == null){
		 	domainId = defaultDomain;
		}
		domainDriver= drivers.get(domainId);
        return domainDriver.queryNetworkSliceInstance(request, domainId, tenantId);
    }

	

	public void setDefaultDriver(NsmfLcmProviderInterface defaultDriver){
		this.drivers.put(defaultDomain, defaultDriver);
	}

}
