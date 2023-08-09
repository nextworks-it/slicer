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
package it.nextworks.nfvmano.libs.ifa.descriptors.nsd;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.nextworks.nfvmano.libs.ifa.common.enums.CpRole;
import it.nextworks.nfvmano.libs.ifa.common.enums.LayerProtocol;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.AddressData;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.Cpd;

/**
 * A PnfExtCpd is a type of Cpd and describes the characteristics 
 * of an external interface, a.k.an external CP, where to connect the PNF to a VL.
 * 
 * Ref. IFA 014 v2.3.1 - 6.6.4
 * 
 * @author nextworks
 *
 */
@Entity
@DiscriminatorValue("PNF_EXT")
public class PnfExtCpd extends Cpd {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Pnfd pnfd;
	
	public PnfExtCpd() {
		// JPA only
	}
	
	public PnfExtCpd(Pnfd pnfd,
			String cpdId,
			LayerProtocol layerProtocol,
			CpRole cpRole,
			String description,
			List<AddressData> addressData) {
		super(cpdId, layerProtocol, cpRole, description, addressData);
		this.pnfd = pnfd;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		super.isValid();
	}
	
}
