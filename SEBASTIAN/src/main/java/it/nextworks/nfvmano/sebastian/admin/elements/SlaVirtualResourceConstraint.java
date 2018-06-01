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
package it.nextworks.nfvmano.sebastian.admin.elements;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

@Entity
public class SlaVirtualResourceConstraint implements InterfaceInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@ManyToOne
	@JsonIgnore
	private Sla sla;
	
	private VirtualResourceUsage maxResourceLimit;
	private SlaScope scope;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String location;	//used only in case of MEC resources
	
	public SlaVirtualResourceConstraint() { }
	
	public SlaVirtualResourceConstraint(Sla sla,
			VirtualResourceUsage maxResourceLimit,
			SlaScope scope,
			String location) {
		this.sla = sla;
		if (maxResourceLimit != null) this.maxResourceLimit = maxResourceLimit;
		else this.maxResourceLimit = new VirtualResourceUsage(0, 0, 0);
		this.scope = scope;
		this.location = location;
	}

	/**
	 * @return the maxResourceLimit
	 */
	public VirtualResourceUsage getMaxResourceLimit() {
		return maxResourceLimit;
	}

	/**
	 * @param maxResourceLimit the maxResourceLimit to set
	 */
	public void setMaxResourceLimit(VirtualResourceUsage maxResourceLimit) {
		this.maxResourceLimit = maxResourceLimit;
	}

	/**
	 * @return the scope
	 */
	public SlaScope getScope() {
		return scope;
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(SlaScope scope) {
		this.scope = scope;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override
	public void isValid() throws MalformattedElementException {	}
	
}
