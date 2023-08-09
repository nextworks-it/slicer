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
package it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.interfaces;


import it.nextworks.nfvmano.catalogue.catalogueNotificaton.messages.*;
import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;

/**
 * This interface represents the consumer of the "notify" operation (IFA 013 v3.1.1 - 8.6)
 */
public interface VnfPkgNotificationsConsumerInterface {

    /**
     * This method is called when a new VNF Pkg is on-boarded, after all the steps are done.
     * REF IFA 013 v3.1.1 - 8.6.8
     *
     * @param notification notification about the on-boarding of a new VNF Pkg
     */
    void acceptVnfPkgOnBoardingNotification(VnfPkgOnBoardingNotificationMessage notification) throws MethodNotImplementedException;

    /**
     * This method is called when there is a change of state in an on-boarded VNF Pkg.
     * Only a change in operational state will be reported. A change in usage state will not be reported.
     * REF IFA 013 v3.1.1 - 8.6.9
     *
     * @param notification notification about the state change of an VNF Pkg
     * @throws MethodNotImplementedException
     */
    void acceptVnfPkgChangeNotification(VnfPkgChangeNotificationMessage notification) throws MethodNotImplementedException;

    /**
     * This method is called when an on-boarded VNF Pkg is deleted.
     * REF IFA 013 v3.1.1 - 8.6.10
     *
     * @param notification notification about the deletion of an existing VNF Pkg
     * @throws MethodNotImplementedException
     */
    void acceptVnfPkgDeletionNotification(VnfPkgDeletionNotificationMessage notification) throws MethodNotImplementedException;
}
