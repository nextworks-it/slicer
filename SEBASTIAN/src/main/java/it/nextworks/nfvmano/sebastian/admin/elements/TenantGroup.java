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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

@Entity
public class TenantGroup {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "group", cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Tenant> tenants = new ArrayList<>();
	
	private String name;
	
	public TenantGroup() { }
	
	public TenantGroup(String name) {
		this.name = name;
	}

	/**
	 * @return the tenants
	 */
	public List<Tenant> getTenants() {
		return tenants;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	public void isValid() throws MalformattedElementException {
		if (name == null) throw new MalformattedElementException("Group without name");
	}

}
