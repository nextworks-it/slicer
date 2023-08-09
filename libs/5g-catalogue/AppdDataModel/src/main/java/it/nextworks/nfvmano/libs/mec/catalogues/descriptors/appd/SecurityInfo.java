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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.enums.OAuth20GrantType;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This type represents security information related to a transport.
 * 
 * Ref. ETSI GS MEC 011 V1.1.1 (2017-07) - 6.5.4
 * 
 * @author nextworks
 *
 */
@Entity
public class SecurityInfo implements DescriptorInformationElement {
	
	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@OneToOne(fetch= FetchType.LAZY, cascade= CascadeType.REMOVE)
	@JsonIgnore
	@JoinColumn(name="td_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private TransportDescriptor td;
	
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<OAuth20GrantType> grantTypes = new ArrayList<>();
	
	private String tokenEndpoint;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, String> extensions = new HashMap<String, String>();
	
	
	public SecurityInfo() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param td Transport Descriptor this security info belongs to.
	 * @param extensions Extensions for alternative transport mechanisms.
	 * @param grantTypes List of supported OAuth 2.0 grant types.
	 * @param tokenEndpoint The token endpoint. Shall be present unless the grant type is OAUTH2_IMPLICIT_GRANT.
	 */
	public SecurityInfo(TransportDescriptor td,
			Map<String,String> extensions,
			List<OAuth20GrantType> grantTypes,
			String tokenEndpoint) {
		this.td = td;
		if (extensions != null) this.extensions = extensions;
		if (grantTypes != null) this.grantTypes = grantTypes;
		this.tokenEndpoint = tokenEndpoint;
	}
	
	
	
	/**
	 * @return the grantTypes
	 */
	@JsonProperty("grantTypes")
	public List<OAuth20GrantType> getGrantTypes() {
		return grantTypes;
	}

	/**
	 * @return the tokenEndpoint
	 */
	@JsonProperty("tokenEndpoint")
	public String getTokenEndpoint() {
		return tokenEndpoint;
	}

	/**
	 * @return the extensions
	 */
	@JsonProperty("extensions")
	public Map<String, String> getExtensions() {
		return extensions;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if ((grantTypes == null) || (grantTypes.isEmpty())) throw new MalformattedElementException("Security info without OAUTH grant type");
	}

}
