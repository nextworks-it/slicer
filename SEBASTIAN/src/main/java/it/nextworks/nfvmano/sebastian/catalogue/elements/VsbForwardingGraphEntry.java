package it.nextworks.nfvmano.sebastian.catalogue.elements;

import javax.persistence.Embeddable;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

@Embeddable
public class VsbForwardingGraphEntry implements DescriptorInformationElement {

	private String vsComponentId;
	private String endPointId;
	
	public VsbForwardingGraphEntry() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param vsComponentId ID of the VS atomic component
	 * @param endPointId ID of the end point within the atomic component
	 */
	public VsbForwardingGraphEntry(String vsComponentId, String endPointId) {
		this.vsComponentId = vsComponentId;
		this.endPointId = endPointId;
	}
	
	

	/**
	 * @return the vsComponentId
	 */
	public String getVsComponentId() {
		return vsComponentId;
	}

	/**
	 * @return the endPointId
	 */
	public String getEndPointId() {
		return endPointId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vsComponentId == null) throw new MalformattedElementException("VS Forwarding Graph element without VS component");
		if (endPointId == null) throw new MalformattedElementException("VS Forwarding Graph element without end point");
	}

}
