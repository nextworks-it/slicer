package it.nextworks.nfvmano.sebastian.catalogue.elements;

import javax.persistence.Embeddable;

import it.nextworks.nfvmano.libs.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

@Embeddable
public class VsdParameterValueRange implements InterfaceInformationElement {

	private String parameterId;
	private int minValue;
	private int maxValue;
	
	public VsdParameterValueRange() { }

	/**
	 * @param parameterId
	 * @param minValue
	 * @param maxValue
	 */
	public VsdParameterValueRange(String parameterId, int minValue, int maxValue) {
		this.parameterId = parameterId;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	/**
	 * @return the parameterId
	 */
	public String getParameterId() {
		return parameterId;
	}

	/**
	 * @return the minValue
	 */
	public int getMinValue() {
		return minValue;
	}

	/**
	 * @return the maxValue
	 */
	public int getMaxValue() {
		return maxValue;
	}
	
	@Override
	public void isValid() throws MalformattedElementException {
		if (parameterId == null) throw new MalformattedElementException("VSD parameter value range without ID.");
	}

}
