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
package it.nextworks.nfvmano.sebastian.record.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;

public interface NetworkSliceInstanceRepository extends JpaRepository<NetworkSliceInstance, Long> {

	Optional<NetworkSliceInstance> findByNsiId(String nsiId);
	Optional<NetworkSliceInstance> findByNfvNsId(String nfvNsId);
	List<NetworkSliceInstance> findByTenantId(String tenantId);
	List<NetworkSliceInstance> findByTenantIdAndNsdIdAndNsdVersion(String tenantId, String nsdId, String nsdVersion);
	List<NetworkSliceInstance> findByTenantIdAndNsdIdAndNsdVersionAndDfIdAndInstantiationLevelId(String tenantId, String nsdId, String nsdVersion, String dfId, String instantiationLevel);
	List<NetworkSliceInstance> findByNstId(String nstId);
	
}
