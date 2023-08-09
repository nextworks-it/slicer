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
package it.nextworks.nfvmano.libs.ifa.catalogues.interfaces;

import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.NsdChangeNotification;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.NsdOnBoardingNotification;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;

/**
 * Interface exposing the methods to notify the OSS/BSS about
 * changes of NSD sent by the NFVO.
 * It must be implemented by the plugins managing the interaction with 
 * external OSS/BSS entities.
 * It must be invoked by the core components of the NFVO.
 * 
 * REF IFA 013 v2.3.1 - 7.2.13
 * 
 * @author nextworks
 *
 */
public interface NsdManagementConsumerInterface {

	/**
	 * This method is used to notify the consumer
	 * of a new NSD on-boarding
	 * 
	 * @param notification NSD on-boarding notification
	 * @throws MethodNotImplementedException if the method is not implemented
	 */
	public void notify(NsdOnBoardingNotification notification)
			throws MethodNotImplementedException;
	
	/**
	 * This method is used to notify the consumer
	 * of an update in an existing NSD
	 * 
	 * @param notification NSD change notification
	 * @throws MethodNotImplementedException if the method is not implemented
	 */
	public void notify(NsdChangeNotification notification)
			throws MethodNotImplementedException;
}
