/*
 * Copyright (c) 2022 Nextworks s.r.l.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.nextworks.nfvmano.nssmf.service.messages;

/**
 * Types for nssmf internal messages:
 *  - Provisioning
 *  - Configuration
 * Monitoring not yet defined (12 Nov 2021)
 *
 * @author Pietro G. Giardina
 */
public enum NssmfMessageType {

    // Provisioning
    INSTANTIATE_NSSI_REQUEST,
    MODIFY_NSSI_REQUEST,
    TERMINATE_NSSI_REQUEST,
    NOTIFY_NFV_NSSI_STATUS_CHANGE,

    //Configuration
    SET_NSSI_CONFIG,
    UPDATE_NSSI_CONFIG,
    REMOVE_NSSI_CONFIG,

    //Notification
    NOTIFY_NSSI_STATUS_CHANGE

}
