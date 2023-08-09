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
package it.nextworks.nfvmano.libs.ifa.vnfindicator.interfaces;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.common.messages.SubscribeRequest;
import it.nextworks.nfvmano.libs.ifa.vnfindicator.interfaces.messages.GetIndicatorValueResponse;

/**
 * Interface exposing the methods to request or subscribe for VNF indicators.
 * It must be implemented by the plug-ins implementing the actual 
 * interaction with the VNF or the EM (via REST API).
 * 
 * REF IFA 008 - It must be invoked by the VNFM to request or subscribe for VNF indicators.
 * REF IFA 007 - It must be invoked by the NFVO to request or subscribe for VNF indicators.
 * 
 * @author nextworks
 *
 */
public interface VnfIndicatorProviderInterface {

	/**
	 * Method to subscribe for receiving asynchronous messages about changed in VNF indicator values
	 *  
	 * REF IFA 007 v2.3.1 - 7.7.2 
	 *  
	 * @param subscription request
	 * @param consumer
	 * @return the subscription ID
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws MalformattedElementException if the request is malformatted
	 * @throws FailedOperationException if the operation fails
	 */
	public String subscribe (SubscribeRequest request, VnfIndicatorConsumerInterface consumer)
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException;
	
	/**
	 * Method to remove a previous subscription
	 * 
	 * REF IFA 007 v2.3.1 - 7.7.5
	 * 
	 * @param subscriptionId	ID of the subscription to be removed
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws MalformattedElementException if the request is malformatted
	 * @throws NotExistingEntityException if the subscription does not exist
	 * @throws FailedOperationException if the operation fails
	 */
	public void unsubscribe(String subscriptionId)
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
	
	/**
	 * Method to request the actual value of a given indicator from the VNF
	 * 
	 * REF IFA 007 v2.3.1 - 7.7.4
	 * 
	 * @param request	indicator request
	 * @return			indicator response
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the indicator does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public GetIndicatorValueResponse getIndicatorValue(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation enables the consumer to query information about subscriptions.
	 * TODO: still to be defined the format of the request
	 * 
	 * REF IFA 007 v2.3.1 - 7.7.6
	 * 
	 * @param request subscription query
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the subscription does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public void queryVnfIndicatorSubscription(GeneralizedQueryRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
}
