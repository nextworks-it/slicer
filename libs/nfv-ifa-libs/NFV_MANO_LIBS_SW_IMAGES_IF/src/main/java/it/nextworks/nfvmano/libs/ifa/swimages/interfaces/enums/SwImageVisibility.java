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
package it.nextworks.nfvmano.libs.ifa.swimages.interfaces.enums;

/**
 * REF IFA 006 - v2.3.1 - 7.2.2
 * 
 * @author nextworks
 *
 */
public enum SwImageVisibility {

	PRIVATE,	//the image is available only for the tenant assigned to the provided resourceGroupId and the administrator tenants of the VIM
	PUBLIC		//all tenants of the VIM can use the image
	
}
