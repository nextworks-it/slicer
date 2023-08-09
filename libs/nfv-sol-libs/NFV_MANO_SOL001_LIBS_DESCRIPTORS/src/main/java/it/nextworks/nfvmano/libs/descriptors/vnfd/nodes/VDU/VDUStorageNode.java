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
package it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VDU;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.templates.Node;
import it.nextworks.nfvmano.libs.descriptors.templates.TopologyTemplate;

//@Entity
public class VDUStorageNode /* extends Node implements DescriptorInformationElement */ {

	/*
	 * private String type;
	 * 
	 * @JsonInclude(JsonInclude.Include.NON_NULL)
	 * 
	 * @OneToOne(fetch = FetchType.EAGER, mappedBy = "vduStorageNode", cascade =
	 * CascadeType.ALL, orphanRemoval = true)
	 * 
	 * @OnDelete(action = OnDeleteAction.CASCADE) private VDUStorageProperties
	 * properties;
	 * 
	 * @JsonInclude(JsonInclude.Include.NON_NULL)
	 * 
	 * @OneToOne(fetch = FetchType.EAGER, mappedBy = "vduStorageNode", cascade =
	 * CascadeType.ALL, orphanRemoval = true)
	 * 
	 * @OnDelete(action = OnDeleteAction.CASCADE) private VDUStorageCapabilities
	 * capabilities;
	 * 
	 * public VDUStorageNode() {
	 * 
	 * }
	 * 
	 * public VDUStorageNode(TopologyTemplate topologyTemplate, String type,
	 * VDUStorageProperties properties, VDUStorageCapabilities capabilities) {
	 * super(topologyTemplate); this.type = type; this.properties = properties;
	 * this.capabilities = capabilities; }
	 * 
	 * @JsonProperty("type") public String getType() { return type; }
	 * 
	 * @JsonProperty("properties") public VDUStorageProperties getProperties() {
	 * return properties; }
	 * 
	 * @JsonProperty("capabilities") public VDUStorageCapabilities getCapabilities()
	 * { return capabilities; }
	 * 
	 * @Override public void isValid() throws MalformattedElementException { if
	 * (this.type == null) throw new
	 * MalformattedElementException("VDUStorage Node without type"); if
	 * (this.properties == null) throw new
	 * MalformattedElementException("VDUStorage Node without properties"); }
	 */
}
