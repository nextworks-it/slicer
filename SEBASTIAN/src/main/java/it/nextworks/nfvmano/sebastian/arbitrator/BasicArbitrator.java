package it.nextworks.nfvmano.sebastian.arbitrator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.translator.TranslatorService;

/**
 * This is a basic arbitrator that only checks if the request VSI is compliant with 
 * the SLA of the tenant and does not support network slice sharing.
 * 
 * @author nextworks
 *
 */
public class BasicArbitrator extends AbstractArbitrator {

	private static final Logger log = LoggerFactory.getLogger(BasicArbitrator.class);
	
	public BasicArbitrator(AdminService adminService, VsRecordService vsRecordService,
			TranslatorService translatorService) {
		super(adminService, vsRecordService, translatorService, ArbitratorType.BASIC_ARBITRATOR);
	}

	@Override
	public List<ArbitratorResponse> computeArbitratorSolution(List<ArbitratorRequest> requests) 
			throws FailedOperationException, NotExistingEntityException {
		log.debug("Received request at the arbitrator. At the moment dummy reply.");
		//TODO: check SLAs
		ArbitratorResponse response = new ArbitratorResponse(requests.get(0).getRequestId(), 
				true, 								//acceptableRequest 
				true, 								//newSliceRequired, 
				null, 								//existingCompositeSlice, 
				false, 								//existingCompositeSliceToUpdate, 
				null, 
				null);
		List<ArbitratorResponse> responses = new ArrayList<>();
		responses.add(response);
		return responses;
	}

}
