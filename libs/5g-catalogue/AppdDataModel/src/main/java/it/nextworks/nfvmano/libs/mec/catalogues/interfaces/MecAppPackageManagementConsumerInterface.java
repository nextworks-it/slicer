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
package it.nextworks.nfvmano.libs.mec.catalogues.interfaces;

import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.mec.catalogues.interfaces.messages.AppPackageOnBoardingNotification;
import it.nextworks.nfvmano.libs.mec.catalogues.interfaces.messages.AppPackageStateChangeNotification;

/**
 * Interface exposing the methods to notify the MEPM about
 * changes of App packages sent by the MEO.
 * It must be implemented by the plugins managing the interaction with 
 * MEO's external entities.
 * It must be invoked by the core components of the MEO.
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.3.3.4
 * 
 * @author nextworks
 *
 */
public interface MecAppPackageManagementConsumerInterface {

	/**
	 * Method to notify the on-boarding of a new app package.
	 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.3.3.4 
	 * 
	 * @param notification notification
	 * @throws MethodNotImplementedException if the method is not implemented
	 */
	public void notify(AppPackageOnBoardingNotification notification)
			throws MethodNotImplementedException;
	
	/**
	 * Method to notify a change in the status of an app package.
	 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.3.3.4
	 * 
	 * @param notification notification
	 * @throws MethodNotImplementedException if the method is not implemented
	 */
	public void notify(AppPackageStateChangeNotification notification)
			throws MethodNotImplementedException;
}
