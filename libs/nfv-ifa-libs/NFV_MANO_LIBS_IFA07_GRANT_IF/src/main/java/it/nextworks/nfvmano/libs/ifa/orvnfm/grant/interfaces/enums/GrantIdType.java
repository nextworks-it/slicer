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
package it.nextworks.nfvmano.libs.ifa.orvnfm.grant.interfaces.enums;

/**
 * REF IFA 007 v2.3.1 - 8.3.8
 * 
 * @author nextworks
 *
 */
public enum GrantIdType {

	RES_MGMT,	//Resource-management-level identifier; this identifier is managed by the VIM in direct mode and is managed by the NFVO	in indirect mode
	GRANT		//reference to identifier in the ResourceDefinition in the grant request
	
}
