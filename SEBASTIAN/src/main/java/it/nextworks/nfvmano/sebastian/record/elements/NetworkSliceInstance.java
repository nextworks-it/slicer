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
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
	
	private String nsdId;	//ID of the descriptor of the NFV network service that implements the network slice
	
	private String nsdVersion;	//version of the descriptor of the NFV network service that implements the network slice
	
	private String dfId; 	//ID of the deployment flavour in the NFV network service
	
	private String instantiationLevelId;	//ID of the instantiation level in the NFV network service
	
	private String nfvNsId;	//ID of the NFV network service that implements the network slice
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> networkSliceSubnetInstances = new ArrayList<>();
	
	private String tenantId;	//owner of the slice
	
	private NetworkSliceStatus status;
	
	private String errorMessage; //this field gets a value only in case of failure
	
	public NetworkSliceInstance() {	}

	/**
	 * @param nsiId ID of the network slice
	 * @param nsdId ID of the descriptor of the NFV network service that implements the network slice
	 * @param nsdVersion Version of the descriptor of the NFV network service that implements the network slice
	 * @param dfId ID of the deployment flavour in the NFV network service
	 * @param instantiationLevelId ID of the instantiation level in the NFV network service
	 * @param nfvNsId ID of the NFV network service that implements the network slice
	 * @param networkSliceSubnetInstances in case of composite network slice, the ID of its network slice subnets
	 * @param tenantId owner of the slice
	 */
	public NetworkSliceInstance(String nsiId, String nsdId, String nsdVersion, String dfId, String instantiationLevelId, String nfvNsId,
			List<String> networkSliceSubnetInstances, String tenantId, String name, String description) {
		this.nsiId = nsiId;
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
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
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
