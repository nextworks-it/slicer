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
	
	/**
	 * This method verifies if a given parameter is compliant with the given range.
	 * 
	 * @param parameterValue parameter to be validated
	 * @return true if the given parameter is compliant with the given range
	 * @throws NumberFormatException if the given parameter is not in int format
	 */
	public boolean matchesVsdParameter (String parameterValue) throws NumberFormatException {
		int value = Integer.parseInt(parameterValue);
		if ( (value >= minValue) && (value <= maxValue) ) return true;
		else return false;
	}
	
	@Override
	public void isValid() throws MalformattedElementException {
		if (parameterId == null) throw new MalformattedElementException("VSD parameter value range without ID.");
	}

}
