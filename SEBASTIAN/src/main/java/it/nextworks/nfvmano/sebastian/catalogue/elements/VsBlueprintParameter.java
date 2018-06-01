/*
* Copyright 2018 Nextworks s.r.l.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
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
