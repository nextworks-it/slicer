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
package it.nextworks.nfvmano.libs.common.enums;

public enum LcmEventType {

	// REF. IFA-011 v2.3.1 - section 7.1.13 note 1

	// VNF lifecycle event
	START_VNF_INSTANTIATION, END_VNF_INSTANTIATION, START_VNF_SCALING, END_VNF_SCALING, START_VNF_HEALING,
	END_VNF_HEALING, START_VNF_TERMINATION, END_VNF_TERMINATION, START_VNF_FLAVOUR_CHANGE, END_VNF_FLAVOUR_CHANGE,
	START_VNF_OP_STATE_CHANGE, END_VNF_OP_STATE_CHANGE, START_CHANGE_VNF_EXTERNAL_CONNECTIVITY,
	END_CHANGE_VNF_EXTERNAL_CONNECTIVITY,

	// REF. IFA-011 v2.3.1 - section 7.1.13 note 2

	// external trigger detected on a VNFM reference point
	RECEIVED_MSG_VNF_INSTANTIATE, RECEIVED_MSG_VNF_SCALE, RECEIVED_MSG_VNF_HEAL, RECEIVED_MSG_VNF_TERMINATE,
	RECEIVED_MSG_CHANGE_VNF_FLAVOUR, RECEIVED_MSG_CHANGE_VNF_EXTERNAL_CONNECTIVITY, RECEIVED_MSG_CHANGE_VNF_OP_STATE,
	RECEIVED_MSG_VNF_CHANGE_INDICATION,

	// REF. IFA-014 v2.3.1 - section 6.2.9.2 note 1
	// NS lifecycle event
	START_NS_INSTANTIATION, END_NS_INSTANTIATION, START_NS_SCALING, END_NS_SCALING, START_NS_HEALING, END_NS_HEALING,
	START_NS_TERMINATION, END_NS_TERMINATION, START_NS_UPDATE, END_NS_UPDATE,

	// REF. IFA-014 v2.3.1 - section 6.2.9.2 note 2
	// external triggers
	RECEIVED_MSG_NS_INSTANTIATE, RECEIVED_MSG_NS_SCALE, RECEIVED_MSG_NS_HEAL, RECEIVED_MSG_NS_TERMINATE,
	RECEIVED_MSG_NS_UPDATE

}
