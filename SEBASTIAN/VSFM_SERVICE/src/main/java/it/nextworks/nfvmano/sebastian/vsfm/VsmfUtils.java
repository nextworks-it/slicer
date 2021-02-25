package it.nextworks.nfvmano.sebastian.vsfm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;

@Service
public class VsmfUtils {
	
	private static final Logger log = LoggerFactory.getLogger(VsmfUtils.class);
	
	private NsmfLcmProviderInterface nsmfLcmProvider;

	public NetworkSliceInstance readNetworkSliceInstanceInformation (String nsiId, String domain, String tenantId)
    		throws FailedOperationException, NotExistingEntityException{
    	log.debug("Interacting with NSMF service to get information about network slice with ID " + nsiId);
    	Map<String, String> parameters = new HashMap<String, String>();
    	parameters.put("NSI_ID", nsiId);
    	Filter filter = new Filter(parameters);
    	GeneralizedQueryRequest request = new GeneralizedQueryRequest(filter, new ArrayList<String>());
    	try {
    		List<NetworkSliceInstance> nsis = nsmfLcmProvider.queryNetworkSliceInstance(request, domain, tenantId);
    		if (nsis.isEmpty()) {
    			log.error("Network Slice " + nsiId + " not found in NSMF service");
    			throw new NotExistingEntityException("Network Slice " + nsiId + " not found in NSMF service");
    		}
    		return nsis.get(0);
    	} catch (Exception e) {
			log.error("Error while getting network slice instance " + nsiId + ": " + e.getMessage());
			throw new FailedOperationException("Error while getting network slice instance " + nsiId + ": " + e.getMessage());
		}
    }
	
	public void setNsmfLcmProvider(NsmfLcmProviderInterface nsmfLcmProvider) {
		this.nsmfLcmProvider = nsmfLcmProvider;
	}
	
}
