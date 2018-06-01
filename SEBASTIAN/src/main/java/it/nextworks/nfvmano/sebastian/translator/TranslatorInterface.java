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
package it.nextworks.nfvmano.sebastian.translator;

import java.util.List;
import java.util.Map;

import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;

public interface TranslatorInterface {
	
	/**
	 * This method provides the list of NFV Network Services to be allocated
	 * for the given set of Vertical Service Descriptors. The returned list
	 * includes information about the NSD of each NFV Network Service to be 
	 * allocated together with its deployment flavour and instantiation level.
	 * 
	 * @param vsdIds List of VSDs defining the vertical services to be instantiated.
	 * @return A map with key VSD ID and value the NFV NS to be instantiated to meet the VSD requirements.
	 * @throws FailedOperationException if the operation fails.
	 * @throws NotExistingEntityException if the VSD is not available in the DB.
	 */
	public Map<String, NfvNsInstantiationInfo> translateVsd(List<String> vsdIds)
		throws FailedOperationException, NotExistingEntityException;
	
}
