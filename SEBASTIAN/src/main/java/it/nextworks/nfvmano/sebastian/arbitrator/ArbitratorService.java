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

import it.nextworks.nfvmano.sebastian.catalogue.VsDescriptorCatalogueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.nfvodriver.NfvoService;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.translator.TranslatorService;

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
	private AdminService adminService;
	
	@Autowired
	private TranslatorService translatorService;
	
	@Autowired
	private VsRecordService vsRecordService;

	@Autowired
	private VsDescriptorCatalogueService vsDescriptorCatalogueService;
	@Autowired
	private NfvoService nfvoService;
	
	private AbstractArbitrator arbitrator;
	
	public ArbitratorService() { }
	
	@PostConstruct
	public void initTranslatorService() {
		log.debug("Initializing arbitrator");
		if (arbitratorType.equals("BASIC")) {
			log.debug("The Vertical Slicer is configured to operate with a basic arbitrator.");
			arbitrator = new BasicArbitrator(adminService, vsRecordService, vsDescriptorCatalogueService,
					translatorService, nfvoService);
		} else {
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
			throws FailedOperationException, NotExistingEntityException {
		return arbitrator.arbitrateVsScaling(requests);
	}

}
