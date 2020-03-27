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
package it.nextworks.nfvmano.sebastian.nsmf.nbi;

import it.nextworks.nfvmano.sebastian.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmConsumerInterface;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceFailureNotification;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceStatusChangeNotification;

/**
 * This class implements a service that dispatches notifications.
 * At the moment we assume a single receiver. 
 * To be extended for multiple receivers and subscriptions 
 * 
 * @author nextworks
 *
 */
@Service
public class NbiNotificationsHandler implements NsmfLcmConsumerInterface {

	private VsmfRestClient vsmfRestClient;

	@Autowired
	private AdminService adminService;

	@Override
	public void notifyNetworkSliceStatusChange(NetworkSliceStatusChangeNotification notification) {
		vsmfRestClient.notifyNetworkSliceStatusChange(notification);
	}
	
	@Override
	public void notifyNetworkSliceFailure(NetworkSliceFailureNotification notification) {
		vsmfRestClient.notifyNetworkSliceFailure(notification);
	}

	@Override
	public void notifyNetworkSliceActuation(NetworkSliceStatusChangeNotification notification, String endpoint){
		vsmfRestClient.notifyNetworkSliceActuation(notification, endpoint);
	}

	public void setNotifDestinationUrl(String notifDestinationUrl) {
		this.vsmfRestClient = new VsmfRestClient(notifDestinationUrl,adminService);
	}
	
	

}
