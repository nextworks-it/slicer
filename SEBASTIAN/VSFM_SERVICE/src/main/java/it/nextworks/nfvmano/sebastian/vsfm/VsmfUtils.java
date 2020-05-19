package it.nextworks.nfvmano.sebastian.vsfm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${plugAndPlay.hostname}")
	private String plugAndPlayHostname;

	public NetworkSliceInstance readNetworkSliceInstanceInformation (String nsiUuid, String tenantId)
    		throws FailedOperationException, NotExistingEntityException{
    	log.debug("Interacting with NSMF service to get information about network slice with UUID " + nsiUuid);
    	Map<String, String> parameters = new HashMap<String, String>();
    	parameters.put("NSI_ID", nsiUuid);
    	Filter filter = new Filter(parameters);
    	GeneralizedQueryRequest request = new GeneralizedQueryRequest(filter, new ArrayList<String>());
    	try {
    		List<NetworkSliceInstance> nsis = nsmfLcmProvider.queryNetworkSliceInstance(request, tenantId);
    		if (nsis.isEmpty()) {
    			log.error("Network Slice " + nsiUuid + " not found in NSMF service");
    			throw new NotExistingEntityException("Network Slice " + nsiUuid + " not found in NSMF service");
    		}
    		return nsis.get(0);
    	} catch (Exception e) {
			log.error("Error while getting network slice instance " + nsiUuid + ": " + e.getMessage());
			throw new FailedOperationException("Error while getting network slice instance " + nsiUuid + ": " + e.getMessage());
		}
    }
	
	public void setNsmfLcmProvider(NsmfLcmProviderInterface nsmfLcmProvider) {
		this.nsmfLcmProvider = nsmfLcmProvider;
	}
	public String getPlugAndPlayHostname() {
		return plugAndPlayHostname;
	}
}
