package it.nextworks.nfvmano.sebastian.arbitrator;

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.sebastian.catalogue.elements.VsDescriptor;
import it.nextworks.nfvmano.sebastian.translator.NfvNsInstantiationInfo;

/**
 * This class models a request to the arbitrator.
 * 
 * It includes:
 * 1. the tenant ID --> used to retrieve the tenant SLAs + the used resources + the associated network slices
 * 2. the VSD --> used to get specific constraints (future work)
 * 3. the NSDs + DFs + ILs returned from the translator --> used to check the amount of resources that are needed and evaluate if existing NSIs can be re-used
 * 
 * @author nextworks
 *
 */
public class ArbitratorRequest {

	private String requestId;
	private String tenantId;
	private VsDescriptor vsd;
	private Map<String, NfvNsInstantiationInfo> instantiationNsd = new HashMap<>();
	
	/**
	 * @param tenantId
	 * @param vsd
	 * @param instantiationNsd
	 */
	public ArbitratorRequest(String requestId, String tenantId, VsDescriptor vsd, Map<String, NfvNsInstantiationInfo> instantiationNsd) {
		this.requestId = requestId;
		this.tenantId = tenantId;
		this.vsd = vsd;
		if (instantiationNsd != null) this.instantiationNsd = instantiationNsd;
	}

	
	
	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}



	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}
	
	/**
	 * @return the vsd
	 */
	public VsDescriptor getVsd() {
		return vsd;
	}
	
	/**
	 * @return the instantiationNsd
	 */
	public Map<String, NfvNsInstantiationInfo> getInstantiationNsd() {
		return instantiationNsd;
	}
	
}
