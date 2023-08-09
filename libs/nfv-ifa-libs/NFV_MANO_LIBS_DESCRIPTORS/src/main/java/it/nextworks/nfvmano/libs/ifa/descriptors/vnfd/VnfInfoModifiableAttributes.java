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

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
 * This information element defines the VNF-specific extension and 
 * metadata attributes of the VnfInfo that are writeable via the 
 * ModifyVnfInfo operation.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.14
 * 
 * @author nextworks
 *
 */
@Entity
@Table(name="VnfInfoModifiableAttributesIfa")
public class VnfInfoModifiableAttributes implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	@JsonIgnore
	@JoinColumn(name="vnfd_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Vnfd vnfd;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Map<String, String> extension = new HashMap<String, String>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	@Column(name="VnfInfoModifiableAttributesMetadataIfa")
	private Map<String, String> metadataIfa = new HashMap<String, String>();
	
	public VnfInfoModifiableAttributes() {	}
	
	/**
	 * Constructor
	 *  
	 * @param vnfd VNFD this modifiable attributes belong to
	 * @param extension "Extension" attributes of VnfInfo that are writeable.
	 * @param metadataIfa "Metadata" attributes of VnfInfo that are writeable.
	 */
	public VnfInfoModifiableAttributes(Vnfd vnfd,
			Map<String, String> extension,
			Map<String, String> metadataIfa) {
		this.vnfd = vnfd;
		if (extension != null) this.extension = extension;
		if (metadataIfa != null) this.metadataIfa = metadataIfa;
	}

	
	
	/**
	 * @return the extension
	 */
	public Map<String, String> getExtension() {
		return extension;
	}

	/**
	 * @param extension the extension to set
	 */
	public void setExtension(Map<String, String> extension) {
		this.extension = extension;
	}

	/**
	 * @return the metadata
	 */
	@JsonProperty("metadata")
	public Map<String, String> getMetadataIfa() {
		return metadataIfa;
	}

	@Override
	public void isValid() throws MalformattedElementException {	}

}
