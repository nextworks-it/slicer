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

import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vcompute.VirtualisedResourceChangeNotification;

/**
 * This interface allows an authorized consumer functional block 
 * to perform operations on virtualised compute resources available 
 * to the consumer functional block. 
 * 
 * The interface includes operations for allocating, querying, updating and
 * terminating virtualised compute resources as well as operations for 
 * scaling, migrating and operating the administrative status of a virtualised 
 * compute resource.
 * 
 * This interface implements the consumer side; 
 * it must be implemented by a VIM consumer (i.e. NFVO (IFA 005) 
 * or VNFM (IFA 006)) and invoked by a VIM plugin.
 * 
 * REF IFA 005 v2.3.1 - sect. 7.3.2
 * 
 * @author nextworks
 *
 */
public interface VirtualComputeResourceManagementConsumerInterface {

	/**
	 * This operation distributes notifications to subscribers. 
	 * It is a one-way operation issued by the VIM that cannot be 
	 * invoked as an operation by the consumer (NFVO).
	 * 
	 * @param notification notification
	 */
	public void notifyVirtualComputeResourceChange(VirtualisedResourceChangeNotification notification);
	
}
