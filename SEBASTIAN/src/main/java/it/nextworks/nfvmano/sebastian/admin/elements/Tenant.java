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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.common.enums.OperationalState;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;

@Entity
public class Tenant {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@ManyToOne
	@JsonIgnore
	private TenantGroup group;
	
	private String username;
	private String password;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "tenant", cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Sla> sla = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> vsdId = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> vsiId = new ArrayList<>();
	
	//This is to be changed to better manage MEC vs Cloud resources
	@Embedded
	private VirtualResourceUsage allocatedResources;
	
	public Tenant() { }
	
	public Tenant(TenantGroup group,
			String username, 
			String password) {
		this.group = group;
		this.username = username;
		this.password = password;
		this.allocatedResources = new VirtualResourceUsage(0, 0, 0);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the group
	 */
	public TenantGroup getGroup() {
		return group;
	}
	
	

	/**
	 * @param group the group to set
	 */
	public void setGroup(TenantGroup group) {
		this.group = group;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the sla
	 */
	public List<Sla> getSla() {
		return sla;
	}

	/**
	 * @return the vsdId
	 */
	public List<String> getVsdId() {
		return vsdId;
	}

	/**
	 * @return the vsiId
	 */
	public List<String> getVsiId() {
		return vsiId;
	}
	
	public void addVsd(String vsdId) {
		if (!(this.vsdId.contains(vsdId)))
			this.vsdId.add(vsdId);
	}
	
	public void removeVsd(String vsdId) {
		if (this.vsdId.contains(vsdId))
			this.vsdId.remove(vsdId);
	}
	
	public void addVsi(String vsiId) {
		if (!(this.vsiId.contains(vsiId)))
			this.vsiId.add(vsiId);
	}
	
	public void removeVsi(String vsiId) {
		if (this.vsiId.contains(vsiId))
			this.vsiId.remove(vsiId);
	}
	
	/**
	 * @return the allocatedResources
	 */
	public VirtualResourceUsage getAllocatedResources() {
		return allocatedResources;
	}
	
	public void addUsedResources(int storage, int vCPU, int ram) {
		allocatedResources.addResources(storage, vCPU, ram);
	}
	
	public void removeUsedResources(int storage, int vCPU, int ram) {
		allocatedResources.removeResources(storage, vCPU, ram);
	}
	
	@JsonIgnore
	public Sla getActiveSla() throws NotExistingEntityException {
		for (Sla s : sla) {
			if (s.getSlaStatus() == OperationalState.ENABLED) return s;
		}
		throw new NotExistingEntityException("Not found enabled SLA for tenant " + username);
	}

	public void isValid() throws MalformattedElementException {
		if (username == null) throw new MalformattedElementException("Tenant without username");
		if (password == null) throw new MalformattedElementException("Tenant without password");
	}

}
