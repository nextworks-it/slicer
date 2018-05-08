package it.nextworks.nfvmano.sebastian.vsnbi.messages;

import it.nextworks.nfvmano.libs.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

/**
 * Request to modify a Vertical Service instance.
 * 
 * @author nextworks
 *
 */
public class ModifyVsRequest implements InterfaceMessage {

	private String vsiId;
	private String tenantId;
	private String vsdId;
	
	public ModifyVsRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param vsiId ID of the VS instance to be modified
	 * @param tenantId ID of the tenant owning the VS instance to be modified
	 * @param vsdId ID of the target VSD
	 */
	public ModifyVsRequest(String vsiId,
			String tenantId,
			String vsdId) {
		this.vsiId = vsiId;
		this.vsdId = vsdId;
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

	/**
	 * @return the vsdId
	 */
	public String getVsdId() {
		return vsdId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vsiId == null) throw new MalformattedElementException("Modify VS request without VS instance ID");
		if (vsdId == null) throw new MalformattedElementException("Modify VS request without new VSD ID");
	}

}
