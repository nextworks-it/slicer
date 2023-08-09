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
package it.nextworks.nfvmano.libs.mec.catalogues.interfaces.elements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import it.nextworks.nfvmano.libs.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.common.enums.OperationalState;
import it.nextworks.nfvmano.libs.common.enums.UsageState;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.mec.catalogues.descriptors.appd.Appd;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Details of an on-boarded application packages.
 * This data type is not specified in the present document.
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.3.3.2
 * 
 * @author nextworks
 *
 */
@Entity
public class AppPackageInfo implements InterfaceInformationElement {
	
	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	private String appPackageInfoId;
	private String appdId;
	private String version;
	private String provider;
	private String name;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Appd appd;
	
	private OperationalState operationalState;
	private UsageState usageState;
	private boolean deletionPending;
	
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	@JsonIgnore
	private List<String> nsInstanceId = new ArrayList<>();		//Active NS instances that are using the given AppD
	

	public AppPackageInfo() { }
	
	/**
	 * Constructor
	 * 
	 * @param appPackageInfoId unique ID of the application package info
	 * @param appdId ID of the AppD
	 * @param version version of the AppD
	 * @param provider provider of the AppD
	 * @param name name of the app package
	 * @param appd Application Descriptor
	 * @param operationalState the operational state of the application package
	 * @param usageState the usage state of the application package
	 * @param deletionPending indicates if the application package is in deletion pending state
	 */
	public AppPackageInfo(String appPackageInfoId,
                          String appdId,
                          String version,
                          String provider,
                          String name,
                          Appd appd,
                          OperationalState operationalState,
                          UsageState usageState,
                          boolean deletionPending) {
		this.appPackageInfoId = appPackageInfoId;
		this.appdId = appdId;
		this.version = version;
		this.provider = provider;
		this.name = name;
		this.appd = appd;
		this.operationalState = operationalState;
		this.usageState = usageState;
		this.deletionPending = deletionPending;
	}
	
	

	/**
	 * @return the appPackageInfoId
	 */
	public String getAppPackageInfoId() {
		return appPackageInfoId;
	}

	/**
	 * @return the appdId
	 */
	public String getAppdId() {
		return appdId;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the appd
	 */
	public Appd getAppd() {
		return appd;
	}

	/**
	 * @return the operationalState
	 */
	public OperationalState getOperationalState() {
		return operationalState;
	}

	/**
	 * @return the usageState
	 */
	public UsageState getUsageState() {
		return usageState;
	}

	/**
	 * @return the deletionPending
	 */
	public boolean isDeletionPending() {
		return deletionPending;
	}
	
	

	/**
	 * @param appd the appd to set
	 */
	public void setAppd(Appd appd) {
		this.appd = appd;
	}
	
	/**
	 * 
	 * @param nsId NS instance ID to be added in the list of instances available for this NSD
	 */
	public void addNsInstanceId(String nsId) {
		this.nsInstanceId.add(nsId);
	}
	
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the nsInstanceId
	 */
	public List<String> getNsInstanceId() {
		return nsInstanceId;
	}

	/**
	 * 
	 * @param nsId NS instance ID to be removed from the list of instances available for this NSD 
	 */
	public void removeNsInstanceId(String nsId) {
		nsInstanceId.remove(nsId);
	}
	
	

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	

	/**
	 * @param operationalState the operationalState to set
	 */
	public void setOperationalState(OperationalState operationalState) {
		this.operationalState = operationalState;
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
	 * @param appPackageInfoId the appPackageInfoId to set
	 */
	public void setAppPackageInfoId(String appPackageInfoId) {
		this.appPackageInfoId = appPackageInfoId;
	}
	
	

	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (appPackageInfoId == null) throw new MalformattedElementException("Application package info without ID");
		if (appdId == null) throw new MalformattedElementException("Application package info without AppD ID");
		if (version == null) throw new MalformattedElementException("Application package info without version");
		if (provider == null) throw new MalformattedElementException("Application package info without provider");
		if (appd == null) throw new MalformattedElementException("Application package info without AppD");
	}

}
