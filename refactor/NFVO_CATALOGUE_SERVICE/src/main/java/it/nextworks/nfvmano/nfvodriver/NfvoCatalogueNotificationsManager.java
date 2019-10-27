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
package it.nextworks.nfvmano.nfvodriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.AppPackageOnBoardingNotification;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.AppPackageStateChangeNotification;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.NsdChangeNotification;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.NsdOnBoardingNotification;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.VnfPackageChangeNotification;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.VnfPackageOnboardingNotification;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;


/**
 * This class handles all the notifications received from the NFVO.
 * 
 * @author nextworks
 *
 */
@Service
public class NfvoCatalogueNotificationsManager implements NfvoCatalogueNotificationsConsumerInterface {

	private static final Logger log = LoggerFactory.getLogger(NfvoCatalogueNotificationsManager.class);
	
	@Autowired
	private NfvoCatalogueNotificationsConsumerInterface engine;
	

	
	public NfvoCatalogueNotificationsManager() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void notify(NsdOnBoardingNotification notification) throws MethodNotImplementedException {
		// TODO Auto-generated method stub


		throw new MethodNotImplementedException();
	}

	@Override
	public void notify(NsdChangeNotification notification) throws MethodNotImplementedException {
		// TODO Auto-generated method stub
		throw new MethodNotImplementedException();
	}

	@Override
	public void notify(VnfPackageChangeNotification notification) throws MethodNotImplementedException {
		// TODO Auto-generated method stub
		throw new MethodNotImplementedException();
	}

	@Override
	public void notify(VnfPackageOnboardingNotification notification) throws MethodNotImplementedException {
		// TODO Auto-generated method stub
		throw new MethodNotImplementedException();
	}

	@Override
	public void notify(AppPackageOnBoardingNotification notification) 
			throws MethodNotImplementedException {
		// TODO Auto-generated method stub
		throw new MethodNotImplementedException();
	}

	@Override
	public void notify(AppPackageStateChangeNotification notification) 
			throws MethodNotImplementedException {
		// TODO Auto-generated method stub
		throw new MethodNotImplementedException();
	}

}
