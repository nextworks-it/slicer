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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces;

import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vresources.InformationChangeNotification;

/**
 * This interface allows an authorized consumer functional block 
 * to request operations related to the information about consumable 
 * virtualised compute resources. 
 * The consumable virtualised compute resources include (not limited to) 
 * virtualised compute (virtualised CPU, virtualised memory), virtualised 
 * storage, virtualised NIC, etc. which are managed by a VIM.
 * 
 * The information elements related to consumable virtualised compute 
 * resources describe the types and characteristics of the virtualised 
 * resources that a consumer functional block can request for allocation 
 * as part of the Virtualised Compute Resource Management interface. 
 * The interface and related parameters also support the retrieval of information
 * necessary for describing the types and characteristics of the virtualised 
 * resources that are exposed over the Virtualised Compute Resource Capacity interface.
 * 
 * It must be implemented by the NFVO and invoked by a VIM plugin.
 * 
 * Note: this interface can be also used for network resources: REF IFA 005 v2.1.1 - 7.4.3
 * Note: this interface can be also used for storage resources: REF IFA 005 v2.1.1 - 7.5.3
 * 
 * REF IFA 005 v2.3.1 - 7.3.3
 * REF IFA 005 v2.3.1 - 7.4.3
 * REF IFA 005 v2.3.1 - 7.5.3
 * 
 * 
 * @author nextworks
 *
 */
public interface VirtualisedResourcesInformationManagementConsumerInterface {

	/**
	 * This operation distributes notifications to subscribers. 
	 * It is a one-way operation issued by the VIM that cannot be 
	 * invoked as an operation by the consumer (NFVO).
	 * 
	 * @param notification notification to the NFVO
	 */
	public void notify(InformationChangeNotification notification);
}
