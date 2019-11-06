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
package it.nextworks.nfvmano.catalogue.blueprint.elements;

import javax.persistence.Embeddable;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

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
