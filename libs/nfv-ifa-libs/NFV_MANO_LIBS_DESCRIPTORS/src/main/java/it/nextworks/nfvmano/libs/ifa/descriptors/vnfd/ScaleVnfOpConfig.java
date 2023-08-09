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
 * This information element defines attributes that affect 
 * the invocation of the ScaleVnf operation.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.5.4
 * 
 * @author nextworks
 *
 */
@Entity
public class ScaleVnfOpConfig implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	@JsonIgnore
	@JoinColumn(name="scale_op_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private VnfLcmOperationsConfiguration config;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> parameter = new ArrayList<>();
	
	private boolean scalingByMoreThanOneStepSupported;
	
	public ScaleVnfOpConfig() { }
	
	/**
	 * Constructor
	 * 
	 * @param config Configuration option this element belongs to
	 * @param parameter VNF-specific parameter to be passed when invoking the ScaleVnf operation.
	 * @param scalingByMoreThanOneStepSupported Signals whether passing a value larger than one in the numScalingSteps parameter of the ScaleVnf operation is supported by this VNF.
	 */
	public ScaleVnfOpConfig(VnfLcmOperationsConfiguration config,
			List<String> parameter,
			boolean scalingByMoreThanOneStepSupported) {
		this.config = config;
		if (parameter != null) this.parameter = parameter;
		this.scalingByMoreThanOneStepSupported = scalingByMoreThanOneStepSupported;
	}
	
	/**
	 * @return the parameter
	 */
	@JsonProperty("parameter")
	public List<String> getParameter() {
		return parameter;
	}
	
	/**
	 * @return the scalingByMoreThanOneStepSupported
	 */
	@JsonProperty("scalingByMoreThanOneStepSupported")
	public boolean isScalingByMoreThanOneStepSupported() {
		return scalingByMoreThanOneStepSupported;
	}

	@Override
	public void isValid() throws MalformattedElementException {	}

}
