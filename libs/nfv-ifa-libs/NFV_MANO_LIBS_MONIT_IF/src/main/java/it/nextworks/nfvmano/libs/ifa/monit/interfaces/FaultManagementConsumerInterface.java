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

import it.nextworks.nfvmano.libs.ifa.monit.interfaces.messages.AlarmClearedNotification;
import it.nextworks.nfvmano.libs.ifa.monit.interfaces.messages.AlarmNotification;

/**
 * This interface shall allow an NFV MANO component to provide alarms 
 * related to the NFV entities visible to the consumer.
 * 
 * For example, an alarm on a NS results from either a collected virtualised resource 
 * fault impacting the connectivity of the NS instance or a VNF alarm, resulting from 
 * a virtualised resource alarm, issued by the VNFM for a VNF that is part of this NS 
 * instance.
 * 
 * The fault management interface shall support the following operations:
 * • Subscribe operation: Subscription for the notifications related to the alarms.
 * • Notify operation: Notifications of alarms or alarm state change.
 * • Get alarm list operation: Accessing active alarms.
 * 
 * REF IFA 013 v2.3.1 - 7.6 --> This if implements the consumer side and it
 * must be implemented by the plugins that manage the NFVO external interface 
 * and invoked by the NFVO monitoring service
 * 
 * REF IFA 007 v2.3.1 - 7.5.3 --> This if implements the consumer side and it
 * must be implemented by the NFVO and invoked by the VNFM
 * 
 * REF IFA 005 v2.3.1 - 7.6.3 --> This if implements the consumer side and it
 * must be implemented by the NFVO and invoked by the VIM plugin
 * 
 * @author nextworks
 *
 */
public interface FaultManagementConsumerInterface {

	/**
	 * This notification informs the receiver of new alarms. 
	 * 
	 * REF IFA 013 v2.3.1 - 7.6.3 - 8.5.2
	 * REF IFA 007 v2.3.1 - 7.5.3 - 8.8.2
	 * REF IFA 005 v2.3.1 - 7.6.3 - 8.6.2
	 * 
	 * @param notification
	 */
	public void notify(AlarmNotification notification);
	
	/**
	 * This notification informs the receiver of the clearing of an alarm.
	 * The alarm perceived severity shall be set to "cleared" since the 
	 * corresponding fault has been solved.
	 * 
	 * REF IFA 013 v2.3.1 - 7.6.3 - 8.5.3
	 * REF IFA 007 v2.3.1 - 7.5.3 - 8.8.3
	 * REF IFA 005 v2.3.1 - 7.6.3 - 8.6.3
	 * 
	 * @param notification
	 */
	public void notify(AlarmClearedNotification notification);
	
	/**
	 * This notification informs the receiver that the active alarm list 
	 * has been rebuilt by the VNFM. Upon receipt of this notification, 
	 * the receiver needs to use the "Get Alarm List" operation to synchronize 
	 * its view on current active alarms with that of the VNFM.
	 * 
	 * REF IFA 007 v2.3.1 - 7.5.3 - 8.8.6
	 * 
	 */
	public void notifyAlarmListRebuiltNotification();
	
}
