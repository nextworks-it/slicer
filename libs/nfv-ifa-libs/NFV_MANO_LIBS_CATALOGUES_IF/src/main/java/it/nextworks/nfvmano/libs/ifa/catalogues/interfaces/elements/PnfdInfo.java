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

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.enums.UsageState;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Pnfd;

/**
 * This information element provides the details of an on-boarded PNFD.
 *  Ref. IFA 013 v2.3.1 - 8.2.4
 * 
 * @author nextworks
 *
 */
@Entity
public class PnfdInfo {
	
	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	private String pnfdInfoId;
	private String pnfdId;
	private String name;
	private String version;
	private String provider;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Pnfd pnfd;
	
	private String previousPnfdVersionId;
	private UsageState usageState;
	private boolean deletionPending;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, String> userDefinedData = new HashMap<>();
	

	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	@JsonIgnore
	private List<String> nsInstanceId = new ArrayList<>();		//Active NS instances that are using the given PNFD
	
	public PnfdInfo() {	}
	
	/**
	 * Constructor
	 * 
	 * @param pnfdInfoId Identifier of the on-boarded instance of the PNFD.
	 * @param pnfdId Identifier of (reference to) the PNFD that is on-boarded.
	 * @param name Name of the on-boarded PNFD.
	 * @param version Version of the on-boarded PNFD.
	 * @param provider Provider of the on-boarded PNFD.
	 * @param pnfd PNFD details.
	 * @param previousPnfdVersionId Reference to the previous version if any of this PNFD.
	 * @param usageState Usage state of the on-boarded instance of the PNFD.
	 * @param deletionPending Indicates if deletion of this instance of the PNFD has been requested but the PNFD is still being used by instantiated NSs.
	 * @param userDefinedData User defined data for the PNFD.
	 */
	public PnfdInfo(String pnfdInfoId,
			String pnfdId, 
			String name,
			String version,
			String provider,
			Pnfd pnfd,
			String previousPnfdVersionId,
			UsageState usageState,
			boolean deletionPending,
			Map<String, String> userDefinedData) {
		this.pnfdInfoId = pnfdInfoId;
		this.pnfdId = pnfdId;
		this.name = name;
		this.version = version;
		this.provider = provider;
		this.pnfd = pnfd;
		this.previousPnfdVersionId = previousPnfdVersionId;
		this.usageState = usageState;
		this.deletionPending = deletionPending;
		if (userDefinedData != null) this.userDefinedData = userDefinedData;
	}
	
	

	/**
	 * @return the pnfdInfoId
	 */
	@JsonProperty("pnfdInfoId")
	public String getPnfdInfoId() {
		return pnfdInfoId;
	}

	/**
	 * @return the pnfdId
	 */
	@JsonProperty("pnfdId")
	public String getPnfdId() {
		return pnfdId;
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
	 * @return the provider
	 */
	@JsonProperty("provider")
	public String getProvider() {
		return provider;
	}

	/**
	 * @return the pnfd
	 */
	@JsonProperty("pnfd")
	public Pnfd getPnfd() {
		return pnfd;
	}

	/**
	 * @return the previousPnfdVersionId
	 */
	@JsonProperty("previousPnfdVersionId")
	public String getPreviousPnfdVersionId() {
		return previousPnfdVersionId;
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

	
	
	/**
	 * @param pnfd the pnfd to set
	 */
	public void setPnfd(Pnfd pnfd) {
		this.pnfd = pnfd;
	}
	
	

	/**
	 * @param usageState the usageState to set
	 */
	public void setUsageState(UsageState usageState) {
		this.usageState = usageState;
	}

	/**
	 * @param deletionPending the deletionPending to set
	 */
	public void setDeletionPending(boolean deletionPending) {
		this.deletionPending = deletionPending;
	}

	/**
	 * @param userDefinedData the userDefinedData to set
	 */
	public void setUserDefinedData(Map<String, String> userDefinedData) {
		this.userDefinedData = userDefinedData;
	}

	/**
	 * 
	 * @param update new user defined data to be added or updated in the current ones
	 */
	public void mergeUserDefinedData(Map<String,String> update) {
		for (Map.Entry<String, String> e : update.entrySet()) {
			this.userDefinedData.put(e.getKey(), e.getValue());
		}
	}
	
	/**
	 * @return the nsInstanceId
	 */
	@JsonIgnore
	public List<String> getNsInstanceId() {
		return nsInstanceId;
	}

	public void isValid() throws MalformattedElementException {	}
	
	/**
	 * 
	 * @param nsId NS instance ID to be added in the list of instances available for this NSD
	 */
	public void addNsInstanceId(String nsId) {
		this.nsInstanceId.add(nsId);
	}
	
	/**
	 * 
	 * @param nsId NS instance ID to be removed from the list of instances available for this NSD 
	 */
	public void removeNsInstanceId(String nsId) {
		nsInstanceId.remove(nsId);
	}
	
}
