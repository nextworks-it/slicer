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
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.elements.SwImageData;
import it.nextworks.nfvmano.libs.descriptors.elements.VirtualStorageData;

//@Entity
public class VDUStorageProperties /* implements DescriptorInformationElement */ {

	/*
	 * @Id
	 * 
	 * @GeneratedValue
	 * 
	 * @JsonIgnore private Long id;
	 * 
	 * @OneToOne
	 * 
	 * @JsonIgnore private VDUStorageNode vduStorageNode;
	 * 
	 * private String vduId;
	 * 
	 * @JsonInclude(JsonInclude.Include.NON_NULL)
	 * 
	 * @OneToOne(fetch = FetchType.EAGER, mappedBy = "properties", cascade =
	 * CascadeType.ALL, orphanRemoval = true)
	 * 
	 * @OnDelete(action = OnDeleteAction.CASCADE) private VirtualStorageData
	 * virtualStorageData;
	 * 
	 * @Embedded private SwImageData swImangeData;
	 * 
	 * public VDUStorageProperties() {
	 * 
	 * }
	 * 
	 * public VDUStorageProperties(VDUStorageNode vduStorageNode, String vduId,
	 * VirtualStorageData virtualStorageData, SwImageData swImangeData) {
	 * this.vduStorageNode = vduStorageNode; this.vduId = vduId;
	 * this.virtualStorageData = virtualStorageData; this.swImangeData =
	 * swImangeData; }
	 * 
	 * public VDUStorageNode getVduStorageNode() { return vduStorageNode; }
	 * 
	 * @JsonProperty("id") public String getVduId() { return vduId; }
	 * 
	 * @JsonProperty("virtualStorageData") public VirtualStorageData
	 * getVirtualStorageData() { return virtualStorageData; }
	 * 
	 * @JsonProperty("swImangeData") public SwImageData getSwImangeData() { return
	 * swImangeData; }
	 * 
	 * @Override public void isValid() throws MalformattedElementException { if
	 * (this.vduId == null) throw new
	 * MalformattedElementException("VDUStorage Node properties without id");
	 * 
	 * if (this.virtualStorageData == null) throw new
	 * MalformattedElementException("VDUStorage Node properties without virtualStorageData"
	 * );
	 * 
	 * }
	 */
}
