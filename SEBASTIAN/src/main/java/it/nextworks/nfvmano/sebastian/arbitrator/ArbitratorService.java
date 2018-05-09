package it.nextworks.nfvmano.sebastian.arbitrator;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
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
	
	private AbstractArbitrator arbitrator;
	
	public ArbitratorService() { }
	
	@PostConstruct
	public void initTranslatorService() {
		log.debug("Initializing arbitrator");
		if (arbitratorType.equals("BASIC")) {
			log.debug("The Vertical Slicer is configured to operate with a basic arbitrator.");
			arbitrator = new BasicArbitrator(adminService, vsRecordService, translatorService);
		} else {
			log.error("Arbitrator not configured!");
		}
	}

	@Override
	public List<ArbitratorResponse> computeArbitratorSolution(List<ArbitratorRequest> requests) 
			throws FailedOperationException, NotExistingEntityException {
		return arbitrator.computeArbitratorSolution(requests);
	}

}
