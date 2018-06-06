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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class VerticalServiceInstance {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	private String vsiId;
	private String vsdId;
	private String tenantId;
	private String name;
	private String description;
	private VerticalServiceStatus status;
	
	private String networkSliceId;
	
	private String errorMessage; //this field gets a value only in case of failure
	
	public VerticalServiceInstance() {}

	
	/**
	 * Constructor
	 * 
	 * @param vsiId ID of the Vertical Service instance
	 * @param vsdId ID of the VSD
	 * @param tenantId ID of the tenant owning the VS instance
	 * @param name name of the VS instance
	 * @param description description of the VS instance
	 * @param networkSliceId ID of the network slice implementing the VS instance
	 */
	public VerticalServiceInstance(String vsiId, String vsdId, String tenantId, String name, String description,
			String networkSliceId) {
		this.vsiId = vsiId;
		this.vsdId = vsdId;
		this.tenantId = tenantId;
		this.name = name;
		this.description = description;
		this.networkSliceId = networkSliceId;
		this.status = VerticalServiceStatus.INSTANTIATING;
	}



	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}



	/**
	 * @return the vsiId
	 */
	public String getVsiId() {
		return vsiId;
	}

	/**
	 * @return the vsdId
	 */
	public String getVsdId() {
		return vsdId;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
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
	 * @return the networkSliceId
	 */
	public String getNetworkSliceId() {
		return networkSliceId;
	}


	/**
	 * @return the status
	 */
	public VerticalServiceStatus getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(VerticalServiceStatus status) {
		this.status = status;
	}


	/**
	 * @param vsiId the vsiId to set
	 */
	public void setVsiId(String vsiId) {
		this.vsiId = vsiId;
	}


	/**
	 * @param vsdId the vsdId to set
	 */
	public void setVsdId(String vsdId) {
		this.vsdId = vsdId;
	}


	/**
	 * @param networkSliceId the networkSliceId to set
	 */
	public void setNetworkSliceId(String networkSliceId) {
		this.networkSliceId = networkSliceId;
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
	 * @param errorMessage
	 */
	public void setFailureState(String errorMessage) {
		this.status = VerticalServiceStatus.FAILED;
		this.errorMessage = errorMessage;
	}
	
}
