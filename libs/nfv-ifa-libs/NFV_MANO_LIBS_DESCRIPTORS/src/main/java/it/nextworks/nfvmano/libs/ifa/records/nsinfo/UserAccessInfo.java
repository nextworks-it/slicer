package it.nextworks.nfvmano.libs.ifa.records.nsinfo;

import javax.persistence.Embeddable;

@Embeddable
public class UserAccessInfo {
	
	private String sapdId;
	private String vnfdId;
	private String vnfId;
	private String vnfExtCpdId;
	private String address;

	public UserAccessInfo() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param sapdId ID of the SAPD that provides access to this user interface
	 * @param vnfdId ID of the VNFD associated to the VNF that provides access to this user interface
	 * @param vnfId ID of the VNF that provides access to this user interface
	 * @param vnfExtCpdId ID of the descriptor of the VNF external connection point that exposes this user interface  
	 * @param address IP address that has been associated to the connection point
	 */
	public UserAccessInfo(String sapdId,
			String vnfdId,
			String vnfId,
			String vnfExtCpdId,
			String address) {
		this.sapdId = sapdId;
		this.vnfdId = vnfdId;
		this.vnfId = vnfId;
		this.vnfExtCpdId = vnfExtCpdId;
		this.address = address;
	}

	/**
	 * @return the sapdId
	 */
	public String getSapdId() {
		return sapdId;
	}

	/**
	 * @return the vnfdId
	 */
	public String getVnfdId() {
		return vnfdId;
	}

	/**
	 * @return the vnfId
	 */
	public String getVnfId() {
		return vnfId;
	}

	/**
	 * @return the vnfExtCpdId
	 */
	public String getVnfExtCpdId() {
		return vnfExtCpdId;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param vnfId the vnfId to set
	 */
	public void setVnfId(String vnfId) {
		this.vnfId = vnfId;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
