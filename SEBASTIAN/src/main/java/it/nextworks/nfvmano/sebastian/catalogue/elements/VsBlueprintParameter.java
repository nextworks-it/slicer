package it.nextworks.nfvmano.sebastian.catalogue.elements;

import javax.persistence.Embeddable;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

@Embeddable
public class VsBlueprintParameter implements DescriptorInformationElement {

	private String parameterId;
	private String parameterName;
	private String parameterType;
	private String parameterDescription;
	private String applicabilityField;
	
	public VsBlueprintParameter() {	}
	
	/**
	 * @param parameterId
	 * @param parameterName
	 * @param parameterType
	 * @param parameterDescription
	 * @param applicabilityField
	 */
	public VsBlueprintParameter(String parameterId, String parameterName, String parameterType,
			String parameterDescription, String applicabilityField) {
		this.parameterId = parameterId;
		this.parameterName = parameterName;
		this.parameterType = parameterType;
		this.parameterDescription = parameterDescription;
		this.applicabilityField = applicabilityField;
	}

	/**
	 * @return the parameterId
	 */
	public String getParameterId() {
		return parameterId;
	}

	/**
	 * @return the parameterName
	 */
	public String getParameterName() {
		return parameterName;
	}

	/**
	 * @return the parameterType
	 */
	public String getParameterType() {
		return parameterType;
	}

	/**
	 * @return the parameterDescription
	 */
	public String getParameterDescription() {
		return parameterDescription;
	}

	/**
	 * @return the applicabilityField
	 */
	public String getApplicabilityField() {
		return applicabilityField;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (parameterId == null) throw new MalformattedElementException("VS blueprint parameter without ID");
	}

}
