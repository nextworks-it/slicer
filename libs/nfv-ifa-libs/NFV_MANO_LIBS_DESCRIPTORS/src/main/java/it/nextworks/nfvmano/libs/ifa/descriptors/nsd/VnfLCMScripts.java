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

package it.nextworks.nfvmano.libs.ifa.descriptors.nsd;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class VnfLCMScripts {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	
	@JsonIgnore
	@ManyToOne
	private VnfProfile vnfProfile;
	
	private String target;
	
	@OneToMany(mappedBy = "vnfLcmScripts", cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Map<String, VnfConfigurationScript> scripts = new HashMap<>();
	
	
	
	
	public VnfLCMScripts() {
		//JPA Only
	}
	
	
	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}
	
	/**
	 * @param target
	 */
	public VnfLCMScripts(String target) {
		super();
		this.target = target;
	}

	// Capture all other fields that Jackson do not match other members
    @JsonAnyGetter
    public Map<String, VnfConfigurationScript> getConfigurationScripts() {
        return scripts;
    }

	
	
    @JsonAnySetter
    public void addConfigurationSCript(String name, VnfConfigurationScript newScript) {
        scripts.put(name, newScript);
    }
	
	
	
}
