package it.nextworks.nfvmano.sebastian.catalogue.elements;

import javax.persistence.Embeddable;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

@Embeddable
public class VsdSla implements DescriptorInformationElement {

	private ServiceCreationTimeRange serviceCreationTime;
	private AvailabilityCoverageRange availabilityCoverage;
	private boolean lowCostRequired;
	
	public VsdSla() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param serviceCreationTime 
	 * @param availabilityCoverage
	 * @param lowCostRequired
	 */
	public VsdSla(ServiceCreationTimeRange serviceCreationTime,
			AvailabilityCoverageRange availabilityCoverage,
			boolean lowCostRequired) {
		this.serviceCreationTime = serviceCreationTime;
		this.availabilityCoverage = availabilityCoverage;
		this.lowCostRequired = lowCostRequired;
	}

	
	
	/**
	 * @return the serviceCreationTime
	 */
	public ServiceCreationTimeRange getServiceCreationTime() {
		return serviceCreationTime;
	}

	/**
	 * @return the availabilityCoverage
	 */
	public AvailabilityCoverageRange getAvailabilityCoverage() {
		return availabilityCoverage;
	}

	/**
	 * @return the lowCostRequired
	 */
	public boolean isLowCostRequired() {
		return lowCostRequired;
	}

	@Override
	public void isValid() throws MalformattedElementException {	}

}
