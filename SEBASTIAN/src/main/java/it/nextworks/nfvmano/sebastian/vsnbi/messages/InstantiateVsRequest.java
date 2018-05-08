package it.nextworks.nfvmano.sebastian.vsnbi.messages;

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.libs.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

/**
 * Request to instantiate a new Vertical Service instance.
 * 
 * @author nextworks
 *
 */
public class InstantiateVsRequest implements InterfaceMessage {

	private String name;
	private String description;
	private String vsdId;
	private String tenantId;
	private String notificationUrl;
	private Map<String, String> userData = new HashMap<>();
	
	public InstantiateVsRequest() {	}
	
	
	/**
	 * Constructor
	 * 
	 * @param name Name of the Vertical Service instance
	 * @param description Description of the Vertical Service instance 
	 * @param vsdId ID of the VSD to be used to instantiate the Vertical Service instance
	 * @param tenantId ID of the tenant instantiating the Vertical Service instance
	 * @param notificationUrl URL where the tenant wants to receive notifications about the status of or events related to the Vertical Service instance.
	 * @param userData Additional data, like config parameters provided by the vertical
	 */
	public InstantiateVsRequest(String name, String description, String vsdId, String tenantId, String notificationUrl,
			Map<String, String> userData) {
		this.name = name;
		this.description = description;
		this.vsdId = vsdId;
		this.tenantId = tenantId;
		this.notificationUrl = notificationUrl;
		if (userData != null) this.userData = userData;
	}

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @return the vsdId
	 */
	public String getVsdId() {
		return vsdId;
	}


	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}


	/**
	 * @return the notificationUrl
	 */
	public String getNotificationUrl() {
		return notificationUrl;
	}


	/**
	 * @return the userData
	 */
	public Map<String, String> getUserData() {
		return userData;
	}


	@Override
	public void isValid() throws MalformattedElementException {
		if (name == null) throw new MalformattedElementException("Instantiate VS request without VS instance name");
		if (description == null) throw new MalformattedElementException("Instantiate VS request without VS instance description");
		if (vsdId == null) throw new MalformattedElementException("Instantiate VS request without VSD ID");
		if (tenantId == null) throw new MalformattedElementException("Instantiate VS request without tenant ID");
	}

}
