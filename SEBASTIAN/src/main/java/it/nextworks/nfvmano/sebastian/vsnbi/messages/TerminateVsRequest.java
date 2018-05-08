package it.nextworks.nfvmano.sebastian.vsnbi.messages;

import it.nextworks.nfvmano.libs.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

/**
 * Request to terminate a Vertical Service instance.
 * 
 * 
 * @author nextworks
 *
 */
public class TerminateVsRequest implements InterfaceMessage {

	private String vsiId;
	private String tenantId;
	
	public TerminateVsRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param vsiId ID of the VS instance to be terminated
	 * @param tenantId ID of the tenant owning the VS instance
	 */
	public TerminateVsRequest(String vsiId, 
			String tenantId) { 
		this.vsiId = vsiId;
		this.tenantId = tenantId;
	}
	
	
	
	/**
	 * @return the vsiId
	 */
	public String getVsiId() {
		return vsiId;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vsiId == null) throw new MalformattedElementException("Terminate VS request without VSI ID");
		if (tenantId == null) throw new MalformattedElementException("Terminate VS request without tenant ID");
	}

}
