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

import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.vnfconfig.interfaces.messages.SetConfigurationRequest;

/**
 * Interface exposing the methods to configure a VNF.
 * It must be implemented by the plug-ins implementing the actual 
 * interaction with the VNF or the EM (via REST API).
 * It must be invoked by the VNFM to request the configuration
 * of a VNF.
 * 
 * REF IFA 007 v2.3.1 - 6.2
 * 
 * @author nextworks
 *
 */
public interface VnfConfigurationProviderInterface {

	/**
	 * This operation enables a VNFM to set the configuration parameters of a 
	 * VNF instance and its VNFC instance(s) or individual VNFC instances.
	 * 
	 * @param request configuration request
	 * @param consumer the entity that will receive a notification when the configuration has been applied
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void setConfiguration(SetConfigurationRequest request, VnfConfigurationConsumerInterface consumer)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;
	
}
