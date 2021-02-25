/*
 * Copyright (c) 2019 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.sebastian.nsmf.interfaces;

import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceFailureNotification;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceStatusChangeNotification;

/**
 * Interface used to model the NBI of the Network Slice Management Function to provide asynchronous notifications
 * for the lifecycle management of network slices.
 * <p>
 * This interface should be implemented on the server side by the VSMF (or any client of the NSMF interested in
 * receiving notifications about network slice LCM events) and should be invoked by the NSMF.
 * <p>
 * If NSMF and VSMF are de-coupled and they interact via REST API,
 * the NSMF should implement a REST client implementing this interface, while
 * the VSMF should implement a REST server that invokes this interface on an internal service
 * implementing this interface.
 *
 * @author nextworks
 
 */
public interface NsmfLcmConsumerInterface {

	/**
	 * Method to notify about a change in the status of a network slice instance
	 * 
	 * @param notification message with the details of the status change
	 */
	public void notifyNetworkSliceStatusChange(NetworkSliceStatusChangeNotification notification);
	
	/**
	 * Method to notify a failure in a network slice
	 * 
	 * @param notification message with the details of the failure
	 */
	public void notifyNetworkSliceFailure(NetworkSliceFailureNotification notification);
	
}
