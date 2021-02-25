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
package it.nextworks.nfvmano.sebastian.vsfm.sbi.nsmf;

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

import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceFailureNotification;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceStatusChangeNotification;
import it.nextworks.nfvmano.sebastian.vsfm.VsLcmService;

/**
 * This class implements a REST controller to receive notifications from
 * the NSMF about failures or LCM changes of network slice instances
 *
 * @author nextworks
 */
@RestController
@CrossOrigin
@RequestMapping("/vs/notifications")
public class NsmfNotificationsRestController {

    private static final Logger log = LoggerFactory.getLogger(NsmfNotificationsRestController.class);

    @Autowired
    private VsLcmService vsLcmService;

    public NsmfNotificationsRestController() {
    }

    @RequestMapping(value = "/nsilcmchange", method = RequestMethod.POST)
    public ResponseEntity<?> notifyNsiLcmChange(@RequestBody NetworkSliceStatusChangeNotification notification) {
        log.debug("Received notification about network slice instance LCM change");
        vsLcmService.notifyNetworkSliceStatusChange(notification);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/nsifailure", method = RequestMethod.POST)
    public ResponseEntity<?> notifyNsiFailure(@RequestBody NetworkSliceFailureNotification notification) {
        log.debug("Received notification about network slice instance failure");
        vsLcmService.notifyNetworkSliceFailure(notification);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
