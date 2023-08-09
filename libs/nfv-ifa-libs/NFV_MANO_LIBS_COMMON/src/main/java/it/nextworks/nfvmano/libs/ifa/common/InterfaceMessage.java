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
package it.nextworks.nfvmano.libs.ifa.common;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Interface with common methods for checking the validity
 * of a message exchanged on an external interface
 * of an NFV MANO entity 
 * 
 * @author nextworks
 *
 */
public interface InterfaceMessage {

	/**
	 * Method to verify if the information element is compliant with the 
	 * standard
	 * 
	 * @throws MalformattedElementException if the information element is not valid
	 */
	public void isValid() throws MalformattedElementException;
	
}
