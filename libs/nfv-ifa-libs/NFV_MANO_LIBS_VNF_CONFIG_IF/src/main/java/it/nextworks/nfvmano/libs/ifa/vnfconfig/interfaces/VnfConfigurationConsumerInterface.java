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
package it.nextworks.nfvmano.libs.ifa.vnfconfig.interfaces;

import it.nextworks.nfvmano.libs.ifa.common.enums.ResponseCode;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.vnfconfig.interfaces.messages.SetConfigurationResponse;

public interface VnfConfigurationConsumerInterface {

	/**
	 * Method used to notify the results of the modification of a VNF configuration
	 * 
	 * @param respondeCode		result of the configuration modification
	 * @param responseMessage	configuration details
	 * @throws MethodNotImplementedException if the method is not implemented
	 */
	public void notifySetConfigurationResult(ResponseCode respondeCode, 
			SetConfigurationResponse responseMessage)
					throws MethodNotImplementedException;
	
}
