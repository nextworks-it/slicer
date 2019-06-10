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
package it.nextworks.nfvmano.sebastian.catalogue.elements;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

@Entity
public class VsbLink implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private VsBlueprint vsb;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> endPointIds = new ArrayList<>();
	
	private boolean external;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> connectivityProperties = new ArrayList<>();
	
	public VsbLink() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param vsb VS blueprint this link belongs to
	 * @param endPointIds list of end points attached to the link
	 * @param external if the link is used to interconnect to an external network
	 * @param connectivityProperties e.g. QoS, protection, restoration
	 */
	public VsbLink(VsBlueprint vsb,
			List<String> endPointIds,
			boolean external,
			List<String> connectivityProperties) {
		this.vsb = vsb;
		if (endPointIds != null) this.endPointIds = endPointIds;
		this.external = external;
		if (connectivityProperties != null) this.connectivityProperties = connectivityProperties;
	}
	
	

	/**
	 * @return the vsb
	 */
	public VsBlueprint getVsb() {
		return vsb;
	}

	/**
	 * @return the endPointIds
	 */
	public List<String> getEndPointIds() {
		return endPointIds;
	}

	/**
	 * @return the external
	 */
	public boolean isExternal() {
		return external;
	}

	/**
	 * @return the connectivityProperties
	 */
	public List<String> getConnectivityProperties() {
		return connectivityProperties;
	}

	@Override
	public void isValid() throws MalformattedElementException {	}

}
