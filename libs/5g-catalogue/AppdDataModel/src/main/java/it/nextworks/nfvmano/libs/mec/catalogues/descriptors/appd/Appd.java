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
package it.nextworks.nfvmano.libs.mec.catalogues.descriptors.appd;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An application Descriptor (AppD) is a part of application package, 
 * and describes application requirements and rules required by application provider.
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.2.1.2
 * 
 * @author nextworks
 *
 */
@Entity
public class Appd implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	private String appDId;
	private String appName;
	private String appProvider;
	private String appSoftVersion;
	private String appDVersion;
	
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> mecVersion = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String appInfoName;
	
	private String appDescription;
	
	@OneToOne(fetch= FetchType.EAGER, mappedBy = "appd", cascade= CascadeType.ALL, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private VirtualComputeDesc virtualComputeDescriptor;
	
	@Embedded
	private SwImageDesc swImageDescriptor;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<VirtualStorageDesc> virtualStorageDescriptor = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "appd", cascade= CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<AppExternalCpd> appExtCpd = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "appdRequired", cascade= CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MecServiceDependency> appServiceRequired = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "appdOptional", cascade= CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MecServiceDependency> appServiceOptional = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "appd", cascade= CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MecServiceDescriptor> appServiceProduced = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch= FetchType.EAGER)
	@CollectionTable(name="APP_REQ", joinColumns=@JoinColumn(name="afr_app_id"))
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<MecFeatureDependency> appFeatureRequired = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch= FetchType.EAGER)
	@CollectionTable(name="APP_OPT", joinColumns=@JoinColumn(name="afo_app_id"))
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<MecFeatureDependency> appFeatureOptional = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "appd", cascade= CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TransportDependency> transportDependencies = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "appd", cascade= CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TrafficRuleDescriptor> appTrafficRule = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<DnsRuleDescriptor> appDNSRule = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Embedded
	private LatencyDescriptor appLatency;
	
	@Embedded
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private TerminateAppOpConfig terminateAppInstanceOpConfig;
	
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="minGracefulStopTimeout",column=@Column(name="minGracefulStopTimeoutOp")),
	    @AttributeOverride(name="maxRecommendedGracefulStopTimeout",column=@Column(name="maxRecommendedGracefulStopTimeoutOp"))
	  })
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private TerminateAppOpConfig changeAppInstanceStateOpConfig;
	
	/**
	 * Default Constructor
	 * 
	 */
	public Appd() {
		//JPA only
	}
	
	public Appd(String appDId,
			    String appName,
			    String appProvider,
			    String appSoftVersion,
			    String appDVersion,
			    List<String> mecVersion,
			    String appInfoName,
			    String appDescription, 
			    SwImageDesc swImageDescriptor,
			    List<VirtualStorageDesc> virtualStorageDescriptor,
			    List<MecFeatureDependency> appFeatureRequired,
			    List<MecFeatureDependency> appFeatureOptional,
			    List<DnsRuleDescriptor> appDNSRule,
			    LatencyDescriptor appLatency,
			    TerminateAppOpConfig terminateAppInstanceOpConfig,
			    TerminateAppOpConfig changeAppInstanceStateOpConfig) {
		this.appDId = appDId;
		this.appName = appName;
		this.appProvider = appProvider;
		this.appSoftVersion = appSoftVersion;
		this.appDVersion = appDVersion;
		if (mecVersion != null) this.mecVersion = mecVersion;
		this.appInfoName = appInfoName;
		this.appDescription = appDescription;
		this.swImageDescriptor = swImageDescriptor;
		if (virtualStorageDescriptor != null) this.virtualStorageDescriptor = virtualStorageDescriptor;
		if (appFeatureRequired != null) this.appFeatureRequired = appFeatureRequired;
		if (appFeatureOptional != null) this.appFeatureOptional = appFeatureOptional;
		if (appDNSRule != null) this.appDNSRule = appDNSRule;
		this.appLatency = appLatency;
		this.terminateAppInstanceOpConfig = terminateAppInstanceOpConfig;
		this.changeAppInstanceStateOpConfig = changeAppInstanceStateOpConfig;
	}

	/**
	 * @return the appDId
	 */
	@JsonProperty("appDId")
	public String getAppDId() {
		return appDId;
	}



	/**
	 * @return the appName
	 */
	@JsonProperty("appName")
	public String getAppName() {
		return appName;
	}



	/**
	 * @return the appProvider
	 */
	@JsonProperty("appProvider")
	public String getAppProvider() {
		return appProvider;
	}



	/**
	 * @return the appSoftVersion
	 */
	@JsonProperty("appSoftVersion")
	public String getAppSoftVersion() {
		return appSoftVersion;
	}



	/**
	 * @return the appDVersion
	 */
	@JsonProperty("appDVersion")
	public String getAppDVersion() {
		return appDVersion;
	}



	/**
	 * @return the mecVersion
	 */
	@JsonProperty("mecVersion")
	public List<String> getMecVersion() {
		return mecVersion;
	}



	/**
	 * @return the appInfoName
	 */
	@JsonProperty("appInfoName")
	public String getAppInfoName() {
		return appInfoName;
	}



	/**
	 * @return the appDescription
	 */
	@JsonProperty("appDescription")
	public String getAppDescription() {
		return appDescription;
	}



	/**
	 * @return the virtualComputeDescriptor
	 */
	@JsonProperty("virtualComputeDescriptor")
	public VirtualComputeDesc getVirtualComputeDescriptor() {
		return virtualComputeDescriptor;
	}



	/**
	 * @return the swImageDescriptor
	 */
	@JsonProperty("swImageDescriptor")
	public SwImageDesc getSwImageDescriptor() {
		return swImageDescriptor;
	}



	/**
	 * @return the virtualStorageDescriptor
	 */
	@JsonProperty("virtualStorageDescriptor")
	public List<VirtualStorageDesc> getVirtualStorageDescriptor() {
		return virtualStorageDescriptor;
	}



	/**
	 * @return the appExtCpd
	 */
	@JsonProperty("appExtCpd")
	public List<AppExternalCpd> getAppExtCpd() {
		return appExtCpd;
	}



	/**
	 * @return the appServiceRequired
	 */
	@JsonProperty("appServiceRequired")
	public List<MecServiceDependency> getAppServiceRequired() {
		return appServiceRequired;
	}



	/**
	 * @return the appServiceOptional
	 */
	@JsonProperty("appServiceOptional")
	public List<MecServiceDependency> getAppServiceOptional() {
		return appServiceOptional;
	}



	/**
	 * @return the appServiceProduced
	 */
	@JsonProperty("appServiceProduced")
	public List<MecServiceDescriptor> getAppServiceProduced() {
		return appServiceProduced;
	}



	/**
	 * @return the appFeatureRequired
	 */
	@JsonProperty("appFeatureRequired")
	public List<MecFeatureDependency> getAppFeatureRequired() {
		return appFeatureRequired;
	}



	/**
	 * @return the appFeatureOptional
	 */
	@JsonProperty("appFeatureOptional")
	public List<MecFeatureDependency> getAppFeatureOptional() {
		return appFeatureOptional;
	}



	/**
	 * @return the transportDependencies
	 */
	@JsonProperty("transportDependencies")
	public List<TransportDependency> getTransportDependencies() {
		return transportDependencies;
	}



	/**
	 * @return the appTrafficRule
	 */
	@JsonProperty("appTrafficRule")
	public List<TrafficRuleDescriptor> getAppTrafficRule() {
		return appTrafficRule;
	}



	/**
	 * @return the appDNSRule
	 */
	@JsonProperty("")
	public List<DnsRuleDescriptor> getAppDNSRule() {
		return appDNSRule;
	}



	/**
	 * @return the appLatency
	 */
	@JsonProperty("appLatency")
	public LatencyDescriptor getAppLatency() {
		return appLatency;
	}



	/**
	 * @return the terminateAppInstanceOpConfig
	 */
	@JsonProperty("terminateAppInstanceOpConfig")
	public TerminateAppOpConfig getTerminateAppInstanceOpConfig() {
		return terminateAppInstanceOpConfig;
	}



	/**
	 * @return the changeAppInstanceStateOpConfig
	 */
	@JsonProperty("changeAppInstanceStateOpConfig")
	public TerminateAppOpConfig getChangeAppInstanceStateOpConfig() {
		return changeAppInstanceStateOpConfig;
	}



	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (appDId == null) throw new MalformattedElementException("Appd without ID");
		if (appName == null) throw new MalformattedElementException("Appd without name");
		if (appProvider == null) throw new MalformattedElementException("Appd without provider");
		if (appSoftVersion == null) throw new MalformattedElementException("Appd without sw version");
		if (appDVersion == null) throw new MalformattedElementException("Appd without appd version");
		if ((mecVersion == null) || (mecVersion.isEmpty())) throw new MalformattedElementException("Appd without MEC version");
		if (appDescription == null) throw new MalformattedElementException("Appd without description");
		if (virtualComputeDescriptor == null) throw new MalformattedElementException("Appd without virtual compute descriptor");
		else virtualComputeDescriptor.isValid();
		if (swImageDescriptor == null) throw new MalformattedElementException("Appd without sw image info");
		else swImageDescriptor.isValid();
		for (VirtualStorageDesc vsd : virtualStorageDescriptor) vsd.isValid();
		for (AppExternalCpd cp : appExtCpd) cp.isValid();
		for (MecServiceDependency asr : appServiceRequired) asr.isValid();
		for (MecServiceDependency aso : appServiceOptional) aso.isValid();
		for (MecServiceDescriptor asp : appServiceProduced) asp.isValid();
		for (MecFeatureDependency afr : appFeatureRequired) afr.isValid();
		for (MecFeatureDependency afo : appFeatureOptional) afo.isValid();
		for (TransportDependency td : transportDependencies) td.isValid();
		for (TrafficRuleDescriptor tfd : appTrafficRule) tfd.isValid();
		for (DnsRuleDescriptor drd : appDNSRule) drd.isValid();
		if (appLatency != null) appLatency.isValid();
		if (terminateAppInstanceOpConfig != null) terminateAppInstanceOpConfig.isValid();
		if (changeAppInstanceStateOpConfig != null) changeAppInstanceStateOpConfig.isValid();
	}
	
}
