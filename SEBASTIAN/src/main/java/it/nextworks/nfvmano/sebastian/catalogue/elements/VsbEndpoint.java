package it.nextworks.nfvmano.sebastian.catalogue.elements;

import javax.persistence.Embeddable;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

@Embeddable
public class VsbEndpoint implements DescriptorInformationElement {

	private String endPointId;
	private boolean external;
	private boolean management;
	private boolean ranConnection;
	
	public VsbEndpoint() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param endPointId ID of the end point
	 * @param external true if it is an external connection point
	 * @param management true if it is used for management purposes
	 * @param ranConnection true if it connects to the radio segment
	 */
	public VsbEndpoint(String endPointId,
			boolean external,
			boolean management,
			boolean ranConnection) {
		this.endPointId = endPointId;
		this.external = external;
		this.management = management;
		this.ranConnection = ranConnection;
	}
	
	

	/**
	 * @return the endPointId
	 */
	public String getEndPointId() {
		return endPointId;
	}

	/**
	 * @return the external
	 */
	public boolean isExternal() {
		return external;
	}

	/**
	 * @return the management
	 */
	public boolean isManagement() {
		return management;
	}

	/**
	 * @return the ranConnection
	 */
	public boolean isRanConnection() {
		return ranConnection;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (endPointId == null) throw new MalformattedElementException("VSB end point without ID");
	}

}
