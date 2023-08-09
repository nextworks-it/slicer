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
 * This class models transports and serialization formats supported made available 
 * to the service-consuming application
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.2.1.7
 * 
 * @author nextworks
 *
 */
@Entity
public class MecServiceTransport implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private MecServiceDescriptor msd;
	
	@OneToOne(fetch= FetchType.EAGER, mappedBy = "mst", cascade= CascadeType.ALL, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private TransportDescriptor transport;
	
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<SerializerType> serializers = new ArrayList<>();
	
	public MecServiceTransport() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param msd MEC service descriptor this transport descriptor refers to.
	 * @param serializers Information about the serializers in this binding
	 */
	public MecServiceTransport(MecServiceDescriptor msd,
			List<SerializerType> serializers) {
		this.msd = msd;
		if (serializers != null) this.serializers = serializers; 
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

	@Override
	public void isValid() throws MalformattedElementException {
		if (transport == null) throw new MalformattedElementException("MEC service transport without transport");
		else transport.isValid();
		if ((serializers == null) || (serializers.isEmpty())) throw new MalformattedElementException("MEC service transport without serializers.");
	}

}
