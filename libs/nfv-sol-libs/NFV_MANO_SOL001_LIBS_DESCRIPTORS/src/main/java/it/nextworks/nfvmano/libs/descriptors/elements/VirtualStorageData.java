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
package it.nextworks.nfvmano.libs.descriptors.elements;

import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VDU.VDUStorageProperties;

//@Entity
public class VirtualStorageData /* implements DescriptorInformationElement */ {

	/*
	 * @Id
	 * 
	 * @GeneratedValue
	 * 
	 * @JsonIgnore private Long id;
	 * 
	 * @OneToOne
	 * 
	 * @JsonIgnore private VDUStorageProperties properties;
	 * 
	 * private String typeOfStorage; private Integer sizeOfStorage;
	 * 
	 * @ElementCollection(fetch = FetchType.EAGER)
	 * 
	 * @Fetch(FetchMode.SELECT)
	 * 
	 * @Cascade(org.hibernate.annotations.CascadeType.ALL) private Map<String,
	 * String> vduStorageRequirements = new HashMap<String, String>(); private
	 * boolean rdmaEnabled;
	 * 
	 * public VirtualStorageData() { }
	 * 
	 * public VirtualStorageData(VDUStorageProperties properties, String
	 * typeOfStorage, Integer sizeOfStorage, Map<String, String>
	 * vduStorageRequirements, boolean rdmaEnabled, String swImageDesc) {
	 * this.properties = properties; this.typeOfStorage = typeOfStorage;
	 * this.sizeOfStorage = sizeOfStorage; this.vduStorageRequirements =
	 * vduStorageRequirements; this.rdmaEnabled = rdmaEnabled; }
	 * 
	 * public Long getId() { return id; }
	 * 
	 * public VDUStorageProperties getProperties() { return properties; }
	 * 
	 * @JsonProperty("typeOfStorage") public String getTypeOfStorage() { return
	 * typeOfStorage; }
	 * 
	 * @JsonProperty("sizeOfStorage") public Integer getSizeOfStorage() { return
	 * sizeOfStorage; }
	 * 
	 * @JsonProperty("rdmaEnabled") public boolean isRdmaEnabled() { return
	 * rdmaEnabled; }
	 * 
	 * @JsonProperty("vduStorageRequirements") public Map<String, String>
	 * getVduStorageRequirements() { return vduStorageRequirements; }
	 * 
	 * @Override public void isValid() throws MalformattedElementException { if
	 * (typeOfStorage == null) throw new
	 * MalformattedElementException("VirtualStorageData without typeOfStorage"); if
	 * (this.sizeOfStorage == null) throw new
	 * MalformattedElementException("VirtualStorageData without sizeOfStorage"); }
	 */

}
