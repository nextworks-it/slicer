package it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf;

import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.catalogue.domainLayer.*;
import it.nextworks.nfvmano.catalogue.domainLayer.customDomainLayer.EvePortalDomainLayer;
import it.nextworks.nfvmano.catalogue.domainLayer.customDomainLayer.OsmNspDomainLayer;
import it.nextworks.nfvmano.catalogue.domainLayer.customDomainLayer.SonataNspDomainLayer;
import it.nextworks.nfvmano.catalogues.domainLayer.services.DomainCatalogueService;
import it.nextworks.nfvmano.catalogues.domainLayer.services.DomainCatalogueSubscriptionService;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.vsfm.interfaces.VsLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.vsfm.messages.*;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.drivers.EveVsmfDriver;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.polling.VsmfLcmOperationPollingManager;
import org.junit.internal.runners.statements.Fail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.ws.Action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VsmfInteractionHandler implements VsLcmProviderInterface {


    private static final Logger log = LoggerFactory.getLogger(VsmfInteractionHandler.class);

    // domainId -> Interface
    private Map<String, VsLcmProviderInterface> drivers = new HashMap<String, VsLcmProviderInterface>();

    @Autowired
    private VsmfLcmOperationPollingManager vsmfLcmOperationPollingManager;

    @Autowired
    private VsDescriptorCatalogueService vsDescriptorCatalogueService;

    @Autowired
    private AdminService adminService;

    @Value("${vsmf.notifications.url}")
    private String notificationCallbackUri;

    @Autowired
    private DomainCatalogueService domainCatalogueService;
    @Autowired
    private DomainCatalogueSubscriptionService domainCatalogueSubscriptionService;

    public void addDriver(String domain, VsLcmProviderInterface driver){
        log.debug("Adding driver VSMF for domain:"+domain);
        drivers.put(domain, driver);
    }

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

    public void removeDomainDriver(Domain domain){
        String domainId = domain.getDomainId();
        log.info("Removing driver for domain with Id {}", domainId);
        this.drivers.remove(domainId);
    }
    
    public void addDomainDriver(Domain domain){
        String domainId = domain.getDomainId();
        DomainInterface domainInterface = domain.getDomainInterface();
        String completeUrl = domainInterface.getUrl() + ":" + domainInterface.getPort();
        log.info("Adding driver for domain with Id {}", domainId);
        if (domain.getDomainStatus().equals(DomainStatus.ACTIVE)) {
            if (domainInterface.getInterfaceType().equals(InterfaceType.HTTP)) {
                for (DomainLayer domainLayer : domain.getOwnedLayers()) {
                    if (domainLayer.getDomainLayerType().equals(DomainLayerType.VERTICAL_SERVICE_PROVIDER)) {
                       if(domainLayer instanceof EvePortalDomainLayer){
                           EvePortalDomainLayer evePortalDomainLayer =(EvePortalDomainLayer) domainLayer;
                           drivers.put(domainId, new EveVsmfDriver(domainId,vsmfLcmOperationPollingManager,
                                   adminService,
                                   domainCatalogueService,
                                   evePortalDomainLayer.getElcmUrl() ,
                                   evePortalDomainLayer.getCatalogueUrl(),
                                   evePortalDomainLayer.getRbacUrl(),
                                   vsDescriptorCatalogueService, evePortalDomainLayer.getUsername(), evePortalDomainLayer.getPassword() ));
                       }
                    }else if(domainLayer.getDomainLayerType().equals(DomainLayerType.NETWORK_SLICE_PROVIDER)){

                        if(domainLayer instanceof SonataNspDomainLayer){
                            log.debug("Configuring SONATA NSP DOMAIN ");
                        }else if(domainLayer instanceof OsmNspDomainLayer){
                            log.debug("Configuring OSM NSP DOMAIN ");
                        }
                    }
                }
            } else
                log.debug("Domain with ID {} has not a REST interface", domainId);
        } else
            log.debug("Domain with ID {} is not ACTIVE", domainId);
    }

    @Override
    public String instantiateVs(InstantiateVsRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        if (domainId == null || !drivers.containsKey(domainId)) throw  new FailedOperationException("No driver for domain:"+domainId);
        return drivers.get(domainId).instantiateVs(request, domainId);
    }

    @Override
    public QueryVsResponse queryVs(GeneralizedQueryRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        if (domainId == null || !drivers.containsKey(domainId)) throw  new FailedOperationException("No driver for domain:"+domainId);
        return drivers.get(domainId).queryVs(request, domainId);
    }

    @Override
    public List<String> queryAllVsIds(GeneralizedQueryRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        if (domainId == null || !drivers.containsKey(domainId)) throw  new FailedOperationException("No driver for domain:"+domainId);
        return drivers.get(domainId).queryAllVsIds(request, domainId);
    }

    @Override
    public List<VerticalServiceInstance> queryAllVsInstances(GeneralizedQueryRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        if (domainId == null || !drivers.containsKey(domainId)) throw  new FailedOperationException("No driver for domain:"+domainId);
        return drivers.get(domainId).queryAllVsInstances(request, domainId);
    }

    @Override
    public void terminateVs(TerminateVsRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        if (domainId == null || !drivers.containsKey(domainId)) throw  new FailedOperationException("No driver for domain:"+domainId);
        drivers.get(domainId).terminateVs(request, domainId);
    }

    @Override
    public void modifyVs(ModifyVsRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        if (domainId == null || !drivers.containsKey(domainId)) throw  new FailedOperationException("No driver for domain:"+domainId);
        drivers.get(domainId).modifyVs(request, domainId);
    }

    @Override
    public void purgeVs(PurgeVsRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        if (domainId == null || !drivers.containsKey(domainId)) throw  new FailedOperationException("No driver for domain:"+domainId);
        drivers.get(domainId).purgeVs(request, domainId);
    }
}
