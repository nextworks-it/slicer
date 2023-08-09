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
package it.nextworks.nfvmano.libs.ifa.monit.interfaces;

import it.nextworks.nfvmano.libs.ifa.monit.interfaces.messages.PerformanceInformationAvailableNotification;
import it.nextworks.nfvmano.libs.ifa.monit.interfaces.messages.ThresholdCrossedNotification;

/**
 * This interface allows providing of performance information 
 * (measurement results collection and notifications) related to
 * NFV entities.
 * 
 * Collection and reporting of performance information is controlled 
 * by a PM job that groups details of performance collection and reporting information.
 * 
 * Performance information on a given NS results from either collected performance 
 * information of the virtualised resources impacting the connectivity of this NS 
 * instance or VNF performance information, resulting from virtualised resource 
 * performance information, issued by the VNFM for the VNFs that is part of this NS instance.
 * 
 * When new performance information is available, the consumer is notified using 
 * the notification PerformanceInformationAvailableNotification. 
 * 
 * The details of the performance measurements are provided using the PerformanceReport 
 * information element.
 * 
 * The following operations are defined for this interface: 
 * • Notify operations.
 * 
 * REF IFA 013 v2.3.1 - 7.5 --> This if implements the consumer side and it
 * must be implemented by the plugins that manage the NFVO external interface 
 * and invoked by the NFVO monitoring service
 * 
 * REF IFA 007 v2.3.1 - 7.4.5 --> This if implements the consumer side and it
 * must be implemented by the NFVO and invoked by the VNFM
 * 
 * REF IFA 005 v2.3.1 - 7.7 --> This if implements the consumer side and it
 * must be implemented by the NFVO and invoked by a VIM plugin
 * 
 * @author nextworks
 *
 */
public interface PerformanceManagementConsumerInterface {

	/**
	 * This notification informs the receiver that performance information is available. 
	 * 
	 * REF IFA 013 v2.3.1 - 7.5.5 - 8.4.8
	 * REF IFA 007 v2.3.1 - 7.4.5 - 8.7.8
	 * REF IFA 005 v2.3.1 - 7.7.6 - 8.5.8
	 * 
	 * @param notification notification about the availability of a performance information
	 */
	public void notify(PerformanceInformationAvailableNotification notification);
	
	/**
	 * This notification informs the receiver that a threshold value has been crossed.
	 * 
	 * REF IFA 013 v2.3.1 - 7.5.5 - 8.4.9
	 * REF IFA 007 v2.3.1 - 7.4.5 - 8.7.9
	 * REF IFA 005 v2.3.1 - 7.7.6 - 8.5.9
	 * 
	 * @param notification notification about a threshold value that has been crossed.
	 */
	public void notify(ThresholdCrossedNotification notification);
	
	
}
