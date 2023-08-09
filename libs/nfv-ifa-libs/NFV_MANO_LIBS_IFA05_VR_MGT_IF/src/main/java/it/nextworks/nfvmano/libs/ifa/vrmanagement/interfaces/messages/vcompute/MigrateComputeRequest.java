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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.messages.vcompute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.enums.MigrationType;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements.AffinityConstraint;


/**
 * Request for moving a virtualised compute resource between locations.
 * 
 * REF IFA 005 v2.3.1 - sect. 7.3.1.8
 * 
 * @author nextworks
 *
 */
public class MigrateComputeRequest implements InterfaceMessage {

	private String computeId;
	private List<AffinityConstraint> affinityConstraint = new ArrayList<>();
	private List<AffinityConstraint> antiAffinityConstraint = new ArrayList<>();
	private Map<String, String> migrationConstraint = new HashMap<>();
	private MigrationType migrationType;
	
	public MigrateComputeRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param computeId Identifier of the virtualised compute resource to migrate.
	 * @param affinityConstraint Element with affinity information of the virtualised compute resource to migrate.
	 * @param antiAffinityConstraint Element with anti-affinity information of the virtualised compute resource to migrate.
	 * @param migrationConstraint When present, the migration constraint parameter gives indications on where to migrate the resource, e.g. to a specific resource zone.
	 * @param migrationType It defines the type of migration.
	 */
	public MigrateComputeRequest(String computeId,
			List<AffinityConstraint> affinityConstraint,
			List<AffinityConstraint> antiAffinityConstraint,
			Map<String, String> migrationConstraint,
			MigrationType migrationType) {
		this.computeId = computeId;
		if (affinityConstraint != null) this.affinityConstraint = affinityConstraint;
		if (antiAffinityConstraint != null) this.antiAffinityConstraint = antiAffinityConstraint;
		if (migrationConstraint != null) this.migrationConstraint = migrationConstraint;
		this.migrationType = migrationType;
	}
	
	

	/**
	 * @return the computeId
	 */
	public String getComputeId() {
		return computeId;
	}

	/**
	 * @return the affinityConstraint
	 */
	public List<AffinityConstraint> getAffinityConstraint() {
		return affinityConstraint;
	}

	/**
	 * @return the antiAffinityConstraint
	 */
	public List<AffinityConstraint> getAntiAffinityConstraint() {
		return antiAffinityConstraint;
	}

	/**
	 * @return the migrationConstraint
	 */
	public Map<String, String> getMigrationConstraint() {
		return migrationConstraint;
	}

	/**
	 * @return the migrationType
	 */
	public MigrationType getMigrationType() {
		return migrationType;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (computeId == null) throw new MalformattedElementException("Migrate compute request without resource ID");
		for (AffinityConstraint ac : affinityConstraint) ac.isValid();
		for (AffinityConstraint ac : antiAffinityConstraint) ac.isValid();
	}

}
