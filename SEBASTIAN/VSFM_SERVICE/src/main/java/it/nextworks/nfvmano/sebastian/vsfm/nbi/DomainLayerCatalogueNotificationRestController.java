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
package it.nextworks.nfvmano.sebastian.vsfm.nbi;

import it.nextworks.nfvmano.catalogue.domainLayer.DomainCatalogueSubscriptionNotification;
import it.nextworks.nfvmano.catalogue.domainLayer.NotificationType;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.NsmfInteractionHandler;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.VsmfInteractionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;


@RestController
@CrossOrigin
public class DomainLayerCatalogueNotificationRestController {

	private static final Logger log = LoggerFactory.getLogger(DomainLayerCatalogueNotificationRestController.class);

	@Autowired
	NsmfInteractionHandler nsmfInteractionHandler;

	@Autowired
	VsmfInteractionHandler vsmfInteractionHandler;

	public DomainLayerCatalogueNotificationRestController() { }

	@RequestMapping(value = "/domainLayerCatalogue/notifications", method = RequestMethod.POST)
	public ResponseEntity<?> notifyFromDomainLayerCatalogue(@RequestBody DomainCatalogueSubscriptionNotification notification) {
		log.debug("Received notification from Domain Layer Catalogue");
		try {
			notification.isValid();
			if(notification.getType().equals(NotificationType.DOMAIN_ONBOARDING)){
				vsmfInteractionHandler.addDomainDriver(notification.getDomain());
				nsmfInteractionHandler.addDomainDriver(notification.getDomain());
			}else if (notification.getType().equals(NotificationType.DOMAIN_REMOVAL)) {
				nsmfInteractionHandler.removeDomainDriver(notification.getDomain());
				vsmfInteractionHandler.removeDomainDriver(notification.getDomain());
			}else				throw new MalformattedElementException("Notification type currently not supported");
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("Received malformed notification");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
