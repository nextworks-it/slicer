package it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.drivers;

import it.nextworks.nfvmano.catalogue.blueprint.elements.VsDescriptor;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.catalogue.domainLayer.Domain;
import it.nextworks.nfvmano.catalogue.domainLayer.DomainInterface;
import it.nextworks.nfvmano.catalogue.domainLayer.DomainLayer;
import it.nextworks.nfvmano.catalogue.domainLayer.DomainLayerType;
import it.nextworks.nfvmano.catalogues.domainLayer.services.DomainCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.admin.elements.RemoteTenantInfo;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import it.nextworks.nfvmano.sebastian.admin.repo.TenantRepository;
import it.nextworks.nfvmano.sebastian.vsfm.interfaces.VsLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.CsmfType;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.polling.VsmfLcmOperationPollingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractVsmfDriver implements VsLcmProviderInterface {

    private static final Logger log = LoggerFactory.getLogger(AbstractVsmfDriver.class);
    private VsmfLcmOperationPollingManager pollingManager;
    private String domainId;

    private CsmfType csmfType;
    private DomainCatalogueService domainCatalogueService;
    private AdminService adminService;
    private VsDescriptorCatalogueService vsDescriptorCatalogueService;


    public AbstractVsmfDriver(String domain, CsmfType csmfType, VsmfLcmOperationPollingManager pollingManager){
        this.domainId = domain;
        this.csmfType= csmfType;
        this.pollingManager= pollingManager;

    }

    public AbstractVsmfDriver(String domain,
                              CsmfType csmfType,
                              VsmfLcmOperationPollingManager pollingManager,
                              AdminService adminService,
                              DomainCatalogueService domainCatalogueService,
                              VsDescriptorCatalogueService vsDescriptorCatalogueService){
        this.domainId = domain;
        this.csmfType= csmfType;
        this.pollingManager= pollingManager;
        this.adminService= adminService;
        this.domainCatalogueService= domainCatalogueService;
        this.vsDescriptorCatalogueService=vsDescriptorCatalogueService;

    }

    public String getDomainId() {
        return domainId;
    }

    public CsmfType getCsmfType() {
        return csmfType;
    }


    public VsmfLcmOperationPollingManager getPollingManager() {
        return pollingManager;
    }


    public RemoteTenantInfo getRemoteTenantInfo(String tenantId, String url) throws FailedOperationException {
        log.debug("Retrieving remoteTenantInfo for tenant:"+tenantId+" remoteUrl:"+url);

        if(adminService==null) {
            log.error("Tenant repository not set, impossible to retrieve remote tenant information");
            throw new FailedOperationException("Tenant repository not set, impossible to retrieve remote tenant information");
        }
        if(domainCatalogueService==null) {
            log.error("Domain repository not set, impossible to retrieve remote tenant information");
            throw new FailedOperationException("Tenant repository not set, impossible to retrieve remote tenant information");
        }
        try {
            Tenant tenant = adminService.getTenant(tenantId);
            log.debug("Found tenant, retrieving remote information");
            Domain domain = domainCatalogueService.getDomain(domainId);
            if(domain.getDomainInterface()==null  || domain.getDomainInterface().getUrl()==null){
                log.error("No domain interface url for domain:"+domainId);
                throw new FailedOperationException("No domain interface url for domain:"+domainId);
            }
            DomainInterface domainInterface = domain.getDomainInterface();
            String domainInterfaceUrl = domainInterface.getUrl();


            if(domain.getOwnedLayers()==null || domain.getOwnedLayers().isEmpty()){
                log.error("No domain layers for domain:"+domainId);
                throw new FailedOperationException("No domain layers for domain:"+domainId);
            }

            log.debug("Retrieving compatible DomainLayers");
            List<DomainLayer> compatibleDomainLayers = domain.getOwnedLayers().stream()
                    .filter(layer -> layer.getDomainLayerType()== DomainLayerType.VERTICAL_SERVICE_PROVIDER)
                    .collect(Collectors.toList());

            if(compatibleDomainLayers.isEmpty()){
                log.error("No compatible domain layers for domain:"+domainId);
                throw new FailedOperationException("No compatible domain layers for domain:"+domainId);
            }


            for(RemoteTenantInfo remoteTenantInfo: tenant.getRemoteTenantInfos()){
                log.debug("Processing remote tenant for host:"+remoteTenantInfo.getHost());
                String userHost = remoteTenantInfo.getHost();
                if(userHost==null){
                    log.error("Remote user without host");
                    throw new FailedOperationException("Remote user without host");
                }

                if(url.contains(domainInterfaceUrl) && url.contains(userHost)){
                    return remoteTenantInfo;
                }


            }
            throw  new FailedOperationException("Could not find remote tenant info matching the specified URL");
        } catch (NotExistingEntityException e) {
            log.error("Error", e);
            throw new FailedOperationException(e);
        }

    }

    public VsDescriptor getVsDescriptor(String vsDescriptorId) throws FailedOperationException {
        log.debug("Retrieving VSD:"+vsDescriptorId);
        try {
            return vsDescriptorCatalogueService.getVsd(vsDescriptorId);
        } catch (NotExistingEntityException e) {
            log.error("error", e);
            throw new FailedOperationException(e);
        }

    }


    public RemoteTenantInfo getRemoteTenantInfoTest(String tenantId, String url){
        return new RemoteTenantInfo("superusertest@mail.com", "123456", "");
    }
}
