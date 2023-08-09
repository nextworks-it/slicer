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

import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.common.messages.SubscribeRequest;
import it.nextworks.nfvmano.libs.ifa.monit.interfaces.messages.GetAlarmListResponse;


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
 * REF IFA 013 v2.3.1 - 7.6 --> This if implements the provider side and it
 * must be implemented by the NFVO monitoring service and invoked by the 
 * plugins that manage the NFVO external interface (e.g. a REST controller)
 * 
 * REF IFA 007 v2.3.1 - 7.5 --> This if implements the provider side and it
 * must be implemented by the VNFM monitoring service and invoked by the NFVO or
 * the plugins that manage the VNFM external interfaces
 * 
 * REF IFA 006 v2.3.1 - 7.6 --> This if implements the provider side and it
 * must be implemented by the VIM monitoring service and invoked by the NFVO or
 * the plugins that manage the VIM external interfaces
 * 
 * @author nextworks
 *
 */
public interface FaultManagementProviderInterface {

	/**
	 * This operation enables the monitoring consumer to subscribe 
	 * with a filter for the notifications related to alarms. 
	 * 
	 *  REF IFA 013 v2.3.1 - 7.6.2
	 *  REF IFA 007 v2.3.1 - 7.5.2
	 *  REF IFA 005 v2.3.1 - 7.6.2
	 * 
	 * @param request subscription request
	 * @param consumer the entity which has to receive the notifications
	 * @return the ID of the subscription
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws FailedOperationException if the operation fails.
	 */
	public String subscribe(SubscribeRequest request, FaultManagementConsumerInterface consumer)
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException;

	/**
	 * This method allows to remove a previous subscription.
	 * 
	 * REF IFA 013 v2.3.1 - 7.6.2
	 * REF IFA 007 v2.3.1 - 7.5.5
	 * REF IFA 005 v2.3.1 - 7.6.2
	 * 
	 * @param subscriptionId the ID of the subscription to be deleted
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws NotExistingEntityException if the subscription does not exist.
	 * @throws FailedOperationException if the operation fails.
	 */
	public void unsubscribe(String subscriptionId) 
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
	
	/**
	 * This operation enables the service consumer to query the active alarms.
	 * 
	 * REF IFA 013 v2.3.1 - 7.6.4
	 * REF IFA 007 v2.3.1 - 7.5.4
	 * REF IFA 005 v2.3.1 - 7.6.4
	 * 
	 * @param request alarm request
	 * @return list of active alarms matching the filter of the request
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 * @throws NotExistingEntityException if no matching alarms are found
	 */
	public GetAlarmListResponse getAlarmList(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotExistingEntityException;
	
	/**
	 * This operation enables the consumer to query information about subscriptions.
	 * TODO: still to be defined the format of the request
	 * 
	 * REF IFA 007 v2.3.1 - 7.5.6
	 * 
	 * @param request subscription query
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the subscription does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void queryFailureMonitoringSubscription(GeneralizedQueryRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
}
