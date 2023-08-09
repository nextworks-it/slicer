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
package it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.enums.OperationalState;
import it.nextworks.nfvmano.libs.ifa.common.enums.UsageState;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Nsd;

/**
 * This information element provides the details of an on-boarded NSD.
 *  Ref. IFA 013 v2.3.1 - 8.2.2
 * 
 * @author nextworks
 *
 */
public class NsdInfo {
	
	private String nsdInfoId;
	private String nsdId;
	private String name;
	private String version;
	private String designer;
	private Nsd nsd;
	private List<String> onboardedVnfPkgInfoId = new ArrayList<>();
	private List<String> pnfdInfoId = new ArrayList<>();
	private String previousNsdVersionId;
	private OperationalState operationalState;
	private UsageState usageState;
	private boolean deletionPending;
	private Map<String, String> userDefinedData = new HashMap<>();
	

	public NsdInfo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * 
	 * @param nsdInfoId Identifier of the on-boarded instance of the NSD.
	 * @param nsdId Identifier of (reference to) the NSD that is on-boarded
	 * @param name Name of the on-boarded NSD
	 * @param version Version of the on-boarded NSD.
	 * @param designer Designer of the on-boarded NSD
	 * @param nsd NSD details
	 * @param onboardedVnfPkgInfoId Identifies the OnboardedVnfPkgInfo element for the VNFD referenced by the NSD.
	 * @param pnfdInfoId Identifies the PnfdInfo for the PNFD referenced by the NSD
	 * @param previousNsdVersionId Reference to the previous version if any of this NSD. This can be recursive if more than one previous version
	 * @param operationalState Operational state of the on-boarded instance of the NSD.
	 * @param usageState Usage state of the on-boarded instance of the NSD
	 * @param deletionPending Indicates if deletion of this instance of the NS descriptor has been requested but the NSD is still being used by instantiated NSs. This instance of the NSD will be deleted once all NSs instantiated from this descriptor are terminated.
	 * @param userDefinedData User defined data for the NSD.
	 */
	public NsdInfo( String nsdInfoId,
			String nsdId,
			String name,
			String version,
			String designer,
			Nsd nsd,
			List<String> onboardedVnfPkgInfoId,
			List<String> pnfdInfoId,
			String previousNsdVersionId,
			OperationalState operationalState,
			UsageState usageState,
			boolean deletionPending,
			Map<String, String> userDefinedData) {
		this.nsdInfoId = nsdInfoId;
		this.nsdId = nsdId;
		this.name = name;
		this.version = version;
		this.designer = designer;
		this.nsd = nsd;
		if (onboardedVnfPkgInfoId != null) this.onboardedVnfPkgInfoId = onboardedVnfPkgInfoId;
		if (pnfdInfoId != null) this.pnfdInfoId = pnfdInfoId;
		this.previousNsdVersionId = previousNsdVersionId;
		this.operationalState = operationalState;
		this.usageState = usageState;
		this.deletionPending = deletionPending;
		if (userDefinedData != null) this.userDefinedData = userDefinedData;
	}
	
	/**
	 * @return the nsdInfoId
	 */
	@JsonProperty("nsdInfoId")
	public String getNsdInfoId() {
		return nsdInfoId;
	}

	/**
	 * @return the nsdId
	 */
	@JsonProperty("nsdId")
	public String getNsdId() {
		return nsdId;
	}

	/**
	 * @return the name
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	/**
	 * @return the version
	 */
	@JsonProperty("version")
	public String getVersion() {
		return version;
	}

	/**
	 * @return the designer
	 */
	@JsonProperty("designer")
	public String getDesigner() {
		return designer;
	}

	/**
	 * @return the nsd
	 */
	@JsonProperty("nsd")
	public Nsd getNsd() {
		return nsd;
	}

	/**
	 * @return the onboardedVnfPkgInfoId
	 */
	@JsonProperty("onboardedVnfPkgInfoId")
	public List<String> getOnboardedVnfPkgInfoId() {
		return onboardedVnfPkgInfoId;
	}

	/**
	 * @return the pnfdInfoId
	 */
	@JsonProperty("pnfdInfoId")
	public List<String> getPnfdInfoId() {
		return pnfdInfoId;
	}

	/**
	 * @return the previousNsdVersionId
	 */
	@JsonProperty("previousNsdVersionId")
	public String getPreviousNsdVersionId() {
		return previousNsdVersionId;
	}

	/**
	 * @return the operationalState
	 */
	@JsonProperty("operationalState")
	public OperationalState getOperationalState() {
		return operationalState;
	}

	/**
	 * @return the usageState
	 */
	@JsonProperty("usageState")
	public UsageState getUsageState() {
		return usageState;
	}

	/**
	 * @return the deletionPending
	 */
	@JsonProperty("deletionPending")
	public boolean isDeletionPending() {
		return deletionPending;
	}

	/**
	 * @return the userDefinedData
	 */
	@JsonProperty("userDefinedData")
	public Map<String, String> getUserDefinedData() {
		return userDefinedData;
	}

	public void isValid() throws MalformattedElementException {	}
}
