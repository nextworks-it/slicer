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
package it.nextworks.nfvmano;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.sebastian.nsmf.NsLcmService;
import it.nextworks.nfvmano.sebastian.nsmf.nbi.NbiNotificationsHandler;

/**
 * This class is used to link the components handling the
 * interfaces towards the upper layers (e.g. VSMF)
 * 
 * @author nextworks
 *
 */
@Service
public class NsmfConfigurator {

	@Autowired
	private NsLcmService nsLcmService;
	
	@Autowired
	private NbiNotificationsHandler nbiNotificationsHandler;
	
	@Value("${vsmf.notifications.url}")
	private String vsmfNotificationsUrl;
	
	@PostConstruct
	public void configComService() {
		nbiNotificationsHandler.setNotifDestinationUrl(vsmfNotificationsUrl);
		//in the stand-alone NSMF version the consumer of the NSMF notification is a dispatcher of REST msg
		nsLcmService.setNotificationDispatcher(nbiNotificationsHandler);
	}
}
