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
import it.nextworks.nfvmano.libs.common.enums.TransportProtocolType;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * The TransportDescriptor data type describes a transport.
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.2.1.19
 * 
 * @author nextworks
 *
 */
@Entity
public class TransportDescriptor implements DescriptorInformationElement {
	
	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@OneToOne(fetch= FetchType.LAZY, cascade= CascadeType.REMOVE)
	@JsonIgnore
	@JoinColumn(name="mst_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	MecServiceTransport mst;
	
	@OneToOne(fetch= FetchType.LAZY, cascade= CascadeType.REMOVE)
	@JsonIgnore
	@JoinColumn(name="td_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	TransportDependency td;
	
	private TransportProtocolType type;
	private String protocol;
	private String version;
	
	@OneToOne(fetch= FetchType.EAGER, mappedBy = "td", cascade= CascadeType.ALL, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	SecurityInfo security;
	

	public TransportDescriptor() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param mst Mec Service Transport this descriptor refers to
	 * @param type Type of the transport
	 * @param protocol The name of the protocol used. Shall be set to "HTTP" for a REST API.
	 * @param version The version of the protocol used.
	 */
	public TransportDescriptor(MecServiceTransport mst,
			TransportProtocolType type,
			String protocol,
			String version) {
		this.mst = mst;
		this.type = type;
		this.protocol = protocol;
		this.version = version;
	}
	
	/**
	 * Constructor
	 * 
	 * @param td Mec Transport Dependency this descriptor refers to
	 * @param type Type of the transport
	 * @param protocol The name of the protocol used. Shall be set to "HTTP" for a REST API.
	 * @param version The version of the protocol used.
	 */
	public TransportDescriptor(TransportDependency td,
			TransportProtocolType type,
			String protocol,
			String version) {
		this.td = td;
		this.type = type;
		this.protocol = protocol;
		this.version = version;
	}
	
	
	/**
	 * @return the type
	 */
	@JsonProperty("type")
	public TransportProtocolType getType() {
		return type;
	}

	/**
	 * @return the protocol
	 */
	@JsonProperty("protocol")
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @return the version
	 */
	@JsonProperty("version")
	public String getVersion() {
		return version;
	}

	/**
	 * @return the security
	 */
	@JsonProperty("security")
	public SecurityInfo getSecurity() {
		return security;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (protocol == null) throw new MalformattedElementException("Transport descriptor without protocol");
		if (version == null) throw new MalformattedElementException("Transport descriptor without version");
		if (security == null) throw new MalformattedElementException("Transport descriptor without security info");
		else security.isValid();
	}

}
