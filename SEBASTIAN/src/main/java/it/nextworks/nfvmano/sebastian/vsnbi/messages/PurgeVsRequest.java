package it.nextworks.nfvmano.sebastian.vsnbi.messages;

import it.nextworks.nfvmano.libs.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

/**
 * Request to remove a terminated vertical service instance from the system
 * 
 * @author nextworks
 *
 */
public class PurgeVsRequest implements InterfaceMessage {

	private String vsiId;
	private String tenantId;
	
	public PurgeVsRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param vsiId ID of the vertical service instance to be purged
	 * @param tenantId ID of the tenant owning the VS instance
	 */
	public PurgeVsRequest(String vsiId,
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
		if (vsiId == null) throw new MalformattedElementException("Purge VS request without VSI ID");
		if (tenantId == null) throw new MalformattedElementException("Purge VS request without tenant ID");
	}

}
