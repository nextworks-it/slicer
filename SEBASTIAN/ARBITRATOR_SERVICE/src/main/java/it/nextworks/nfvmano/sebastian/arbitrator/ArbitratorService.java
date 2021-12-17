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
package it.nextworks.nfvmano.sebastian.arbitrator;

import java.util.List;

import javax.annotation.PostConstruct;

import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.catalogues.template.services.NsTemplateCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.sebastian.arbitrator.external.ExternalArbitrator;
import it.nextworks.nfvmano.sebastian.arbitrator.external.ExternalArbitratorService;
import it.nextworks.nfvmano.sebastian.arbitrator.interfaces.ArbitratorInterface;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorRequest;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorResponse;
import it.nextworks.nfvmano.sebastian.common.VirtualResourceCalculatorService;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.catalogue.translator.TranslatorService;

/**
 * This is the service that implements the Vertical Slicer Arbitrator functions.
 * It loads the specific arbitrator algorithm specified in the configuration.
 * 
 * @author nextworks
 *
 */
@Service
public class ArbitratorService implements ArbitratorInterface {

    private static final Logger log = LoggerFactory.getLogger(ArbitratorService.class);

    @Value("${arbitrator.type}")
     private String arbitratorType;

	@Autowired
	private VirtualResourceCalculatorService virtualResourceCalculatorService;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private TranslatorService translatorService;

	@Autowired
	private ExternalArbitratorService externalArbitratorService;

	@Value("${arbitrator.url:http://localhost:8088}")
	private String externalArbitratorBaseUrl;

	@Autowired
	private VsRecordService vsRecordService;

	@Autowired
	private VsDescriptorCatalogueService vsDescriptorCatalogueService;
	
	//Check with Pietro. 
	//In theory, if the arbitrator belongs to the VSMF it should be not able to read the NFVO catalogue.
	@Autowired
	private NfvoCatalogueService nfvoCatalogueService;

	@Autowired
    	private NsTemplateCatalogueService nsTemplateCatalogueService;
	
	private AbstractArbitrator arbitrator;
	
	public ArbitratorService() { }
	
	 @PostConstruct
    public void initTranslatorService() {
		 log.debug("Initializing arbitrator");
		 if (arbitratorType.equals("BASIC")) {
			 log.debug("The Vertical Slicer is configured to operate with a basic arbitrator.");
			 arbitrator = new BasicArbitrator(
					 adminService,
					 vsRecordService,
					 vsDescriptorCatalogueService,
					 translatorService,
					 nfvoCatalogueService,
					 nsTemplateCatalogueService,
					 virtualResourceCalculatorService,
					 null
			 );
		 } else if (arbitratorType.equals("MULTIDOMAIN")) {
			 log.debug("The Vertical Slicer is configured to operate with a multi-domain arbitrator.");
			 arbitrator = new MultiDomainBasicArbitrator(
					 adminService,
					 vsRecordService,
					 vsDescriptorCatalogueService,
					 translatorService,
					 nfvoCatalogueService,
					 nsTemplateCatalogueService,
					 virtualResourceCalculatorService,
					 null
			 );
		 }else if (arbitratorType.equals("NO_ARBITRATOR")) {
				 log.debug("The Vertical Slicer is configured to operate with NO_ABRITRATOR arbitrator.");
				 arbitrator = new NoArbitrator(
						 adminService,
						 vsRecordService,
						 vsDescriptorCatalogueService,
						 translatorService,
						 nfvoCatalogueService,
						 nsTemplateCatalogueService,
						 virtualResourceCalculatorService,
						 null
				 );

		 } else if(arbitratorType.equals("EXTERNAL")){
			 log.debug("The Vertical Slicer is configured to operate with an EXTERNAL arbitrator.");
			 arbitrator = new ExternalArbitrator(externalArbitratorService, externalArbitratorBaseUrl);
		 }	else {
			 log.error("Arbitrator not configured!");
		 }
	 }

	@Override
	public List<ArbitratorResponse> computeArbitratorSolution(List<ArbitratorRequest> requests)
			throws FailedOperationException, NotExistingEntityException {
		return arbitrator.computeArbitratorSolution(requests);
	}

	@Override
	public List<ArbitratorResponse> arbitrateVsScaling(List<ArbitratorRequest> requests)
			throws FailedOperationException, NotExistingEntityException, MethodNotImplementedException {
		return arbitrator.arbitrateVsScaling(requests);
	}

	public void setNsmfLcmProvider(NsmfLcmProviderInterface nsmfLcmProvider) {
		arbitrator.setNsmfLcmProvider(nsmfLcmProvider);
	}

	
	
}
