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

public enum VnfLcmOperation {

	INSTATIATE_VNF, QUERY_VNF, TERMINATE_VNF, SCALE_IN_VNF, SCALE_OUT_VNF, SCALE_UP_VNF, SCALE_DOWN_VNF,
	SCALE_VNF_TO_LEVEL, CHANGE_VNF_FLAVOUR, OPERATE_VNF, UPDATE_VNF, MODIFY_VNF, HEAL_VNF, CHANGE_EXT_VNF_CONNECTIVITY

}
