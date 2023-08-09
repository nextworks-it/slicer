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
package it.nextworks.nfvmano.libs.ifa.descriptors.vnfd;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * This information element defines the configurable properties 
 * of a VNF (e.g. related to auto scaling and auto healing). 
 * For a VNF instance, the value of these properties can be modified by the VNFM.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.12
 * 
 * @author nextworks
 *
 */
@Entity
public class VnfConfigurableProperties implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	@JsonIgnore
	@JoinColumn(name="vnfd_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Vnfd vnfd;
	
	private boolean autoScalable = false;
	private boolean autoHealable = false;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> additionalConfigurableProperty = new ArrayList<>();
	
	public VnfConfigurableProperties() {}
	
	/**
	 * Constructor
	 * 
	 * @param vnfd The VNFD this configurable properties belongs to
	 * @param autoScalable It permits to enable (TRUE) / disable (FALSE) the auto-scaling functionality.
	 * @param autoHealable It permits to enable (TRUE) / disable (FALSE) the auto-healing functionality.
	 * @param additionalConfigurableProperty It provides VNF specific configurable properties that can be modified using the ModifyVnfConfiguration operation.
	 */
	public VnfConfigurableProperties(Vnfd vnfd,
			boolean autoScalable,
			boolean autoHealable,
			List<String> additionalConfigurableProperty) {
		this.vnfd = vnfd;
		this.autoHealable = autoHealable;
		this.autoScalable = autoScalable;
		if (additionalConfigurableProperty != null) this.additionalConfigurableProperty = additionalConfigurableProperty;
	}
	
	

	/**
	 * @return the autoScalable
	 */
	@JsonProperty("autoScalable")
	public boolean isAutoScalable() {
		return autoScalable;
	}

	/**
	 * @return the autoHealable
	 */
	@JsonProperty("autoHealable")
	public boolean isAutoHealable() {
		return autoHealable;
	}

	/**
	 * @return the additionalConfigurableProperty
	 */
	@JsonProperty("additionalConfigurableProperty")
	public List<String> getAdditionalConfigurableProperty() {
		return additionalConfigurableProperty;
	}

	@Override
	public void isValid() throws MalformattedElementException {	}

}
