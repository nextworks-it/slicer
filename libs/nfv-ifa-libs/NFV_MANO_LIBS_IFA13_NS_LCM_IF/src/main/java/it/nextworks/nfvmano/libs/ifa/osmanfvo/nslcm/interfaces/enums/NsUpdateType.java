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
package it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.enums;

/**
 * REF IFA 013 v2.3.1 - 7.3.5
 * 
 * @author nextworks
 *
 */
public enum NsUpdateType {

	ADD_VNF,
	REMOVE_VNF,
	INSTANTIATE_VNF,
	CHANGE_VNF_DF,
	OPERATE_VNF,
	MODIFY_VNF_INFORMATION,
	MODIFY_VNF_CONFIG,
	CHANGE_EXT_VNF_CONNECTIVITY,
	ADD_SAP,
	REMOVE_SAP,
	ADD_NESTED_NS,
	REMOVE_NESTED_NS,
	ASSOC_NEW_NSD_VERSION,
	MOVE_VNF,
	ADD_VNFFG,
	REMOVE_VNFFG,
	UPDATE_VNFFG,
	CHANGE_NS_DF
	
}
