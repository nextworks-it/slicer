package it.nextworks.nfvmano.sebastian.arbitrator;

import java.util.*;

import it.nextworks.nfvmano.sebastian.arbitrator.elements.NetworkSliceInstanceAction;
import it.nextworks.nfvmano.sebastian.arbitrator.elements.NsiActionType;
import it.nextworks.nfvmano.sebastian.arbitrator.interfaces.ArbitratorInterface;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorRequest;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorResponse;
import it.nextworks.nfvmano.sebastian.common.VsAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;

/**
 * This class implements an example of Arbitrator algorithm
 * 
 * 
 * @author nextworks
 *
 */
@Service
public class ArbitratorAlgorithmService {

	private static final Logger log = LoggerFactory.getLogger(ArbitratorAlgorithmService.class);
	private ArbitratorNotificationRestClient arbitratorNotificationRestClient;

	public ArbitratorAlgorithmService() {
		this.arbitratorNotificationRestClient= new ArbitratorNotificationRestClient("http://localhost:8082");
	}


	public Map<String,String>  computeArbitratorSolution(List<ArbitratorRequest> request)
			throws NotExistingEntityException, FailedOperationException{
		log.debug("Processing request.");
		log.debug("Executing in thread:"+Thread.currentThread().getName());
		Map<String, String> sampleResponse = new HashMap<>();
		sampleResponse.put(request.get(0).getRequestId(), UUID.randomUUID().toString());

		return sampleResponse;

	}


	@Async("threadPoolTaskExecutor")
	public void sendResponse(Map<String, String> generatedIds, String descriptorName){
		log.debug("Executing in thread:"+Thread.currentThread().getName());
		try {
			Thread.sleep(10000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ArbitratorResponse response=null;
		String operationId = (String) generatedIds.values().toArray()[0];
		String requestId = (String) generatedIds.keySet().toArray()[0];
		if(descriptorName.contains("shared")){
			log.debug("reply for shared service");

			response = new ArbitratorResponse( requestId,
					true,
					true,
					"",
					false,
					new HashMap<>(),
					new HashMap<>(),
					new HashMap<>());
		}else{

			log.debug("reply for second service");

			Map<String, Boolean> slicesToReuse = new HashMap<>();
			String sharedInstanceId = "59";
			slicesToReuse.put(sharedInstanceId, true);
			Map<String, NetworkSliceInstanceAction> networkSliceInstanceActionMap = new HashMap<>();
			NetworkSliceInstanceAction nsiAction = new NetworkSliceInstanceAction(sharedInstanceId, NsiActionType.SCALE, "nsEVS_df", "nsEVS_il_big");
			networkSliceInstanceActionMap.put(sharedInstanceId, nsiAction);
			response = new ArbitratorResponse( requestId,
					true,
					true,
					"",
					false,
					slicesToReuse,
					new HashMap<>(),
					networkSliceInstanceActionMap);
		}

		arbitratorNotificationRestClient.sendArbtirationResponse(operationId, response);


	}




}
