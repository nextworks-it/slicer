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
package it.nextworks.nfvmano.sebastian.record.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import it.nextworks.nfvmano.catalogue.translator.NfvNsInstantiationInfo;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
public class NetworkSliceInstance {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	private String name;

	private String description;

	private String nsiId;	//ID of the network slice

	private String nstId;	//ID of the network slice template

	private String nsdId;	//ID of the descriptor of the NFV network service that implements the network slice

	private String nsdVersion;	//version of the descriptor of the NFV network service that implements the network slice

	private String dfId; 	//ID of the deployment flavour in the NFV network service

	private String instantiationLevelId;	//ID of the instantiation level in the NFV network service

	@JsonIgnore
	private String oldInstantiationLevelId; //ID of the previous instantiation level when the NFV network service is scaled

	private String nfvNsId;	//ID of the NFV network service that implements the network slice

	private boolean soManaged;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> networkSliceSubnetInstances = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, NetworkSliceVnfPlacement> vnfPlacement = new HashMap<>();

	private String tenantId;	//owner of the slice

	private NetworkSliceStatus status;

	private String errorMessage; //this field gets a value only in case of failure

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String nfvNsUrl;

	public NetworkSliceInstance() {	}

	/**
	 * @param nsiId ID of the network slice
	 * @param nstId ID of the network slice template
	 * @param nsdId ID of the descriptor of the NFV network service that implements the network slice
	 * @param nsdVersion Version of the descriptor of the NFV network service that implements the network slice
	 * @param dfId ID of the deployment flavour in the NFV network service
	 * @param instantiationLevelId ID of the instantiation level in the NFV network service
	 * @param nfvNsId ID of the NFV network service that implements the network slice
	 * @param networkSliceSubnetInstances in case of composite network slice, the ID of its network slice subnets
	 * @param tenantId owner of the slice
	 * @param vnfPlacement requested vnf placement key: vnfdId, value: EDGE/CLOUD
	 */
	public NetworkSliceInstance(String nsiId, String nstId, String nsdId, String nsdVersion, String dfId, String instantiationLevelId, String nfvNsId,
			List<String> networkSliceSubnetInstances, String tenantId, String name, String description, boolean soManaged, Map<String, NetworkSliceVnfPlacement> vnfPlacement) {
		this.nsiId = nsiId;
		this.nstId = nstId;
		this.nsdId = nsdId;
		this.nsdVersion = nsdVersion;
		this.dfId = dfId;
		this.instantiationLevelId = instantiationLevelId;
		this.nfvNsId = nfvNsId;
		if (networkSliceSubnetInstances != null) this.networkSliceSubnetInstances = networkSliceSubnetInstances;
		this.tenantId = tenantId;
		this.status = NetworkSliceStatus.INSTANTIATING;
		this.name = name;
		this.description = description;
		this.soManaged = soManaged;
		if(vnfPlacement!=null) this.vnfPlacement= vnfPlacement;

	}

	public NfvNsInstantiationInfo getNsInstantiationInfo(boolean current) {
		if (current)
		return new NfvNsInstantiationInfo(
				nsdId,
				nsdVersion,
				dfId,
				instantiationLevelId,
				new ArrayList<String>(),
				new HashMap<String, String>(),
				null);
		else return new NfvNsInstantiationInfo(
			nsdId,
			nsdVersion,
			dfId,
			oldInstantiationLevelId,
			new ArrayList<String>(),
			new HashMap<String, String>(), null);
	}
	
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the nsiId
	 */
	public String getNsiId() {
		return nsiId;
	}

	/**
	 * @param nsiId the nsiId to set
	 */
	public void setNsiId(String nsiId) {
		this.nsiId = nsiId;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	public void setDfId(String dfId) {
		this.dfId = dfId;
	}

	public void setNetworkSliceSubnetInstances(List<String> networkSliceSubnetInstances) {
		this.networkSliceSubnetInstances = networkSliceSubnetInstances;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 *
	 * @return soManaged: if true, NS is created and managed by SO
	 */
	public boolean getSoManaged() {
		return soManaged;
	}

	/**
	 *
	 * @param soManaged if true, NS is created and managed by SO
	 */
	public void setSoManaged(boolean soManaged) {
		this.soManaged = soManaged;
	}

	/**
	 * @return the nsdId
	 */
	public String getNsdId() {
		return nsdId;
	}

	/**
	 * @return the dfId
	 */
	public String getDfId() {
		return dfId;
	}

	/**
	 * @return the instantiationLevelId
	 */
	public String getInstantiationLevelId() {
		return instantiationLevelId;
	}

	/**
	 *
 	 * @param instantiationLevelId
	 */
	public void setInstantiationLevelId(String instantiationLevelId) {
		this.instantiationLevelId = instantiationLevelId;
	}

	/**
	 * @return the nfvNsId
	 */
	public String getNfvNsId() {
		return nfvNsId;
	}

	/**
	 * @return the networkSliceSubnetInstances
	 */
	public List<String> getNetworkSliceSubnetInstances() {
		return networkSliceSubnetInstances;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @return the nsdVersion
	 */
	public String getNsdVersion() {
		return nsdVersion;
	}

	/**
	 * @return the status
	 */
	public NetworkSliceStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(NetworkSliceStatus status) {
		this.status = status;
	}

	/**
	 * @param nfvNsId the nfvNsId to set
	 */
	public void setNfvNsId(String nfvNsId) {
		this.nfvNsId = nfvNsId;
	}
	
	/**
	 * This method adds a new slice subnet into the main slice
	 * 
	 * @param subnetId ID of the subnet to be added
	 */
	public void addSubnet(String subnetId) {
		this.networkSliceSubnetInstances.add(subnetId);
	}
	
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	public String getNfvNsUrl() {
		return nfvNsUrl;
	}

	public void setNfvNsUrl(String nfvNsUrl) {
		this.nfvNsUrl = nfvNsUrl;
	}
	
	public String getNstId() {
		return nstId;
	}

	@JsonIgnore
	public void updateInstantiationLevelAfterScaling(String newInstantiationLevel) {
		oldInstantiationLevelId = this.instantiationLevelId;
		instantiationLevelId = newInstantiationLevel;
	}
	
	

	/**
	 * @return the oldInstantiationLevelId
	 */
	@JsonIgnore
	public String getOldInstantiationLevelId() {
		return oldInstantiationLevelId;
	}

	/**
	 * @param oldInstantiationLevelId the oldInstantiationLevelId to set
	 */
	@JsonIgnore
	public void setOldInstantiationLevelId(String oldInstantiationLevelId) {
		this.oldInstantiationLevelId = oldInstantiationLevelId;
	}

	public Map<String, NetworkSliceVnfPlacement> getVnfPlacement() {
		return vnfPlacement;
	}



	public void setVnfPlacement(Map<String, NetworkSliceVnfPlacement> vnfPlacement){
		this.vnfPlacement= vnfPlacement;
	}
	/**
	 * This method fills the failure related fields
	 * 
	 * @param errorMessage the cause of the slice failure
	 */
	public void setFailureState(String errorMessage) {
		this.status = NetworkSliceStatus.FAILED;
		this.errorMessage = errorMessage;
	}


}
