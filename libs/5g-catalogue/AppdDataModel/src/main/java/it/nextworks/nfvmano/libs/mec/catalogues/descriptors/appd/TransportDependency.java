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
package it.nextworks.nfvmano.libs.mec.catalogues.descriptors.appd;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.enums.SerializerType;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The TransportDependency data type supports the specification of requirements 
 * of a ME application related to supported transport bindings (each being a 
 * combination of a transport with one or more serializers).
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.2.1.18
 * 
 * @author nextworks
 *
 */
@Entity
public class TransportDependency implements DescriptorInformationElement {
	
	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private MecServiceDependency msd;
	
	@JsonIgnore
	@ManyToOne
	private Appd appd;
	
	@OneToOne(fetch= FetchType.EAGER, mappedBy = "td", cascade= CascadeType.ALL, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private TransportDescriptor transport;
	
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<SerializerType> serializers = new ArrayList<>();
	
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> labels = new ArrayList<>();
	
	public TransportDependency() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param msd MEC service dependency this transport dependency belongs to.
	 * @param serializers Information about the serializers in this transport binding
	 * @param labels Set of labels that allow to define groups of transport bindings. Each "labels" value identifies a group of transport bindings. In a list of TransportDependency structures, all entries that have a "labels" entry with the same value belong to the same group. Each group indicates an alternative set of transport bindings.
	 */
	public TransportDependency(MecServiceDependency msd,
			List<SerializerType> serializers,
			List<String> labels) {
		this.msd = msd;
		if (serializers != null) this.serializers = serializers;
		if (labels != null) this.labels = labels;
	}
	
	/**
	 * Constructor
	 * 
	 * @param appd MEC appD this transport dependency belongs to.
	 * @param serializers Information about the serializers in this transport binding
	 * @param labels Set of labels that allow to define groups of transport bindings. Each "labels" value identifies a group of transport bindings. In a list of TransportDependency structures, all entries that have a "labels" entry with the same value belong to the same group. Each group indicates an alternative set of transport bindings.
	 */
	public TransportDependency(Appd appd,
			List<SerializerType> serializers,
			List<String> labels) {
		this.appd = appd;
		if (serializers != null) this.serializers = serializers;
		if (labels != null) this.labels = labels;
	}
	
	
	/**
	 * @return the transport
	 */
	@JsonProperty("transport")
	public TransportDescriptor getTransport() {
		return transport;
	}

	/**
	 * @return the serializers
	 */
	@JsonProperty("serializers")
	public List<SerializerType> getSerializers() {
		return serializers;
	}

	/**
	 * @return the labels
	 */
	@JsonProperty("labels")
	public List<String> getLabels() {
		return labels;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if ((serializers == null) || (serializers.isEmpty())) throw new MalformattedElementException("MEC transport dependency without serializers.");
		if ((labels == null) || (labels.isEmpty())) throw new MalformattedElementException("MEC transport dependency without labels.");
	}

}
