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
import it.nextworks.nfvmano.libs.ifa.monit.interfaces.messages.CreatePmJobRequest;
import it.nextworks.nfvmano.libs.ifa.monit.interfaces.messages.CreateThresholdRequest;
import it.nextworks.nfvmano.libs.ifa.monit.interfaces.messages.DeletePmJobRequest;
import it.nextworks.nfvmano.libs.ifa.monit.interfaces.messages.DeletePmJobResponse;
import it.nextworks.nfvmano.libs.ifa.monit.interfaces.messages.DeleteThresholdsRequest;
import it.nextworks.nfvmano.libs.ifa.monit.interfaces.messages.DeleteThresholdsResponse;
import it.nextworks.nfvmano.libs.ifa.monit.interfaces.messages.QueryPmJobResponse;
import it.nextworks.nfvmano.libs.ifa.monit.interfaces.messages.QueryThresholdResponse;

/**
 * This interface allows providing of performance information 
 * (measurement results collection and notifications) related to
 * NFV or VNF entities.
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
 * • Create PM Job operation.
 * • Delete PM Jobs operation.
 * • Subscribe operation.
 * • Query PM Job operation.
 * • Create Threshold operation.
 * • Delete Thresholds operation.
 * • Query Threshold operation.
 * 
 * REF IFA 013 v2.3.1 - 7.5 --> This if implements the provider side and it
 * must be implemented by the NFVO monitoring service and invoked by the 
 * plugins that manage the NFVO external interface (e.g. a REST controller)
 * 
 * REF IFA 007 v2.3.1 - 7.4 --> This if implements the provider side and it
 * must be implemented by the VNFM monitoring service and invoked by the 
 * NFVO (or a plugin that handles the external if of the VNFM).
 * 
 * REF IFA 005 v2.3.1 - 7.7 --> This if implements the provider side and it
 * must be implemented by the VIM monitoring service and invoked by the 
 * NFVO. 
 * 
 * @author nextworks
 *
 */
public interface PerformanceManagementProviderInterface {

	/**
	 * This operation will create a PM job, enabling a consumer of the monitoring service
	 * to specify one or more NFV entities (e.g. a NS or set of NSs) for which it wants 
	 * to receive performance information. 
	 * 
	 * This will allow the requesting consumer to specify its performance information 
	 * requirements with the monitoring service.
	 * 
	 * The consumer needs to issue a Subscribe request for PerformanceInformationAvailable 
	 * notifications in order to know when new collected performance information is available.
	 * 
	 * REF IFA 013 v2.3.1 - 7.5.2
	 * REF IFA 007 v2.3.1 - 7.4.2
	 * REF IFA 005 v2.3.1 - 7.7.2
	 * 
	 * @param request PM job creation request
	 * @return Identifier of the created PM job.
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws FailedOperationException if the operation fails.
	 * @throws MalformattedElementException if the request is malformatted.
	 */
	public String createPmJob(CreatePmJobRequest request)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation will delete one or more PM job(s).
	 * 
	 * REF IFA 013 v2.3.1 - 7.5.3
	 * REF IFA 007 v2.3.1 - 7.4.3
	 * REF IFA 005 v2.3.1 - 7.7.4
	 * 
	 * @param request the PM job delete request
	 * @return the list of the PM jobs which have been deleted
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws FailedOperationException if the operation fails.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws NotExistingEntityException if the PM job does not exist.
	 */
	public DeletePmJobResponse deletePmJob(DeletePmJobRequest request)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotExistingEntityException;
	
	/**
	 * This operation enables a consumer of the monitoring service 
	 * to subscribe with a filter for the notifications related to performance information.
	 * 
	 * REF IFA 013 v2.3.1 - 7.5.4
	 * REF IFA 007 v2.3.1 - 7.4.4
	 * REF IFA 005 v2.3.1 - 7.7.5
	 * 
	 * @param request subscription request
	 * @param consumer the entity which has to receive the notifications
	 * @return the ID of the subscription
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws FailedOperationException if the operation fails.
	 */
	public String subscribe(SubscribeRequest request, PerformanceManagementConsumerInterface consumer) 
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException;
	
	/**
	 * This method allows to remove a previous subscription.
	 * 
	 * REF IFA 013 v2.3.1 - 7.5.4
	 * REF IFA 007 v2.3.1 - 7.4.10
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
	 * This operation will enable the OSS/BSS to solicit from the monitoring service
	 * the details of one or more PM job(s). This operation does not return 
	 * performance reports.
	 * 
	 * REF IFA 013 v2.3.1 - 7.5.6
	 * REF IFA 007 v2.3.1 - 7.4.6
	 * REF IFA 005 v2.3.1 - 7.7.3
	 * 
	 * @param request the PM job query request
	 * @return the requested PM job
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws FailedOperationException if the operation fails.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws NotExistingEntityException if the PM job does not exist.
	 */
	public QueryPmJobResponse queryPmJob(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotExistingEntityException;
	
	/**
	 * This operation will allow a monitoring consumer to create a threshold 
	 * and specify threshold levels on specified performance metrics for 
	 * which notifications will be generated when crossed.
	 * 
	 * Creating a threshold does not trigger collection of metrics. 
	 * In order for the threshold to be active, there needs to be a PM job 
	 * collecting the needed metric for the selected entities.
	 * 
	 * 
	 * REF IFA 013 v2.3.1 - 7.5.7
	 * REF IFA 007 v2.3.1 - 7.4.7
	 * REF IFA 005 v2.3.1 - 7.7.7
	 * 
	 * @param request the request to create the threshold
	 * @return the ID of the created threshold
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws FailedOperationException if the operation fails.
	 * @throws MalformattedElementException if the request is malformatted.
	 */
	public String createThreshold(CreateThresholdRequest request)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation will allow a monitoring consumer to delete 
	 * one or more existing threshold(s).
	 * 
	 * REF IFA 013 v2.3.1 - 7.5.8
	 * REF IFA 007 v2.3.1 - 7.4.8
	 * REF IFA 005 v2.3.1 - 7.7.9
	 * 
	 * @param request threshold delete request
	 * @return the list of the thresholds that have been deleted
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws NotExistingEntityException if the threshold does not exist.
	 * @throws FailedOperationException if the operation fails.
	 * @throws MalformattedElementException if the request is malformatted.
	 */
	public DeleteThresholdsResponse deleteThreshold(DeleteThresholdsRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation will allow a monitoring consumer to query the details 
	 * of an existing threshold.
	 * 
	 * REF IFA 013 v2.3.1 - 7.5.9
	 * REF IFA 007 v2.3.1 - 7.4.9
	 * REF IFA 005 v2.3.1 - 7.7.8
	 * 
	 * @param request threshold query request
	 * @return the threshold
	 * @throws MethodNotImplementedException if the method is not implemented.
	 * @throws NotExistingEntityException if the threshold does not exist.
	 * @throws MalformattedElementException if the request is malformatted.
	 * @throws FailedOperationException if the operation fails.
	 */
	public QueryThresholdResponse queryThreshold(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException, FailedOperationException;
	
	/**
	 * This operation enables the consumer to query information about subscriptions.
	 * TODO: still to be defined the format of the request
	 * 
	 * REF IFA 007 v2.3.1 - 7.4.11
	 * 
	 * @param request subscription query
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the subscription does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void queryPerformanceMonitoringSubscription(GeneralizedQueryRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
}
