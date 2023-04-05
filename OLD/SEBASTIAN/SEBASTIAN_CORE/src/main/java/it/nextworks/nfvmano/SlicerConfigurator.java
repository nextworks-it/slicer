package it.nextworks.nfvmano;

import javax.annotation.PostConstruct;


import it.nextworks.nfvmano.catalogue.blueprint.services.VsBlueprintCatalogueService;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.catalogue.domainLayer.*;

import it.nextworks.nfvmano.catalogue.template.interfaces.NsTemplateCatalogueInterface;
import it.nextworks.nfvmano.catalogues.domainLayer.services.DomainCatalogueService;
import it.nextworks.nfvmano.catalogues.template.services.NsTemplateCatalogueService;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.common.VirtualResourceCalculatorService;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.NsmfInteractionHandler;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.CsmfType;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.VsmfInteractionHandler;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.drivers.EveVsmfDriver;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.drivers.VsmfLevelLoggingDriver;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.polling.VsmfLcmOperationPollingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import it.nextworks.nfvmano.sebastian.nsmf.NsLcmService;
import it.nextworks.nfvmano.sebastian.vsfm.VsLcmService;
import it.nextworks.nfvmano.sebastian.vsfm.VsmfUtils;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to link the different components of the slicer 
 * after their initialization
 * 
 * @author nextworks
 *
 */
@Service
public class SlicerConfigurator {
	private static final Logger log = LoggerFactory.getLogger(SlicerConfigurator.class);


	@Autowired
	private VirtualResourceCalculatorService virtualResourceCalculatorService;

	@Autowired
	private AdminService adminService;
	@Autowired
	private VsBlueprintCatalogueService vsBlueprintCatalogueService;

	@Autowired
	private NfvoCatalogueService nfvoCatalogueService;

	@Autowired
	private NsTemplateCatalogueService nsTemplateCatalogueService;

	@Autowired
	private DomainCatalogueService domainCatalogueService;

	@Autowired
	private NsmfInteractionHandler nsmfInteractionHandler;

	@Autowired
	private VsmfInteractionHandler vsmfInteractionHandler;

	@Autowired
	private VsmfLcmOperationPollingManager vsmfLcmOperationPollingManager;

	@Autowired
	private NsLcmService nsLcmService;
	
	@Autowired
	private VsLcmService vsLcmService;
	
	@Autowired
    private VsmfUtils vsmfUtils;

	@Autowired
	private VsDescriptorCatalogueService vsDescriptorCatalogueService;
	
	@Value("${nsmf.local_domain_id:LOCAL}")
	private String localDomainId;


	
	@PostConstruct
	public void init() {
		//in the all-in-one version the consumer of the NSMF notification is the VSMF service
		nsLcmService.setNotificationDispatcher(vsLcmService);
		//vsLcmService.setNsmfLcmProvider(nsLcmService);
		vsmfUtils.setNsmfLcmProvider(nsLcmService);


		//vsmfInteractionHandler.addDriver("test", new VsmfLevelLoggingDriver("5GEVE", CsmfType.LOGGING, vsmfLcmOperationPollingManager));
		nsmfInteractionHandler.init();
		vsmfInteractionHandler.init();
		vsBlueprintCatalogueService.setNsTemplateCatalogueService(nsTemplateCatalogueService);
		//vsDescriptorCatalogueInteractionHandler.addVsdCatalogueDriver("5GEVE", new VsdCatalogueLoggingDriver());
		virtualResourceCalculatorService.setNfvoCatalogueService(nfvoCatalogueService);
		virtualResourceCalculatorService.setVnfPackageManagementProviderInterface(nfvoCatalogueService);
		log.debug("Adding local Sebastian NSP domain");
		//HERE we should configure the available domains. For the moment only the local domain is configures
		
		
			

		
			nsmfInteractionHandler.setDefaultDriver(nsLcmService);
			vsLcmService.setNsmfLcmProvider(nsmfInteractionHandler);
		
	}




	
}
