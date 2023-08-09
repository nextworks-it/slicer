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

/**
 * Identifies the action of the ME host data plane, when a packet matches the
 * trafficFilter
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.2.1.9
 * 
 * @author nextworks
 *
 */
public enum MeHostPacketAction {

	DROP, FORWARD, DECAPSULATED, FORWARD_AS_IS, PASSTHROUGH, DUPLICATED_DECAPSULATED, DUPLICATE_AS_IS

}
