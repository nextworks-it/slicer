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
import it.nextworks.nfvmano.libs.common.enums.CpRole;
import it.nextworks.nfvmano.libs.common.enums.LayerProtocol;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The AppExternalCpd data type supports the specification of ME application 
 * requirements related to external connection point.
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.2.1.6
 * 
 * @author nextworks
 *
 */
@Entity
@DiscriminatorValue("APPEXTCPD")
public class AppExternalCpd extends Cpd {
	
	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

	@JsonIgnore
	@ManyToOne
	private Appd appd;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<VirtualNetworkInterfaceRequirements> virtualNetworkInterfaceRequirements = new ArrayList<>();
	
	public AppExternalCpd() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param appd AppD this connection point belongs to
	 * @param cpdId ID of the connection point
	 * @param layerProtocol Layer protocol
	 * @param cpRole Role of the connection point
	 * @param description Description of the connection point
	 * @param addressData Address data associated to the connection point
	 * @param virtualNetworkInterfaceRequirements Specifies requirements on a virtual network interface realising the CPs instantiated from this CPD.
	 */
	public AppExternalCpd(Appd appd, 
			String cpdId, 
			LayerProtocol layerProtocol,
			CpRole cpRole,
			String description, 
			List<AddressData> addressData,
			List<VirtualNetworkInterfaceRequirements> virtualNetworkInterfaceRequirements) {
		super(cpdId, layerProtocol, cpRole, description, addressData);
		this.appd = appd;
		if (virtualNetworkInterfaceRequirements != null) this.virtualNetworkInterfaceRequirements = virtualNetworkInterfaceRequirements;
	}
	
	/**
	 * @return the virtualNetworkInterfaceRequirements
	 */
	@JsonProperty("virtualNetworkInterfaceRequirements")
	public List<VirtualNetworkInterfaceRequirements> getVirtualNetworkInterfaceRequirements() {
		return virtualNetworkInterfaceRequirements;
	}
	
	@Override
	public void isValid() throws MalformattedElementException {
		super.isValid();
		for (VirtualNetworkInterfaceRequirements vnir : virtualNetworkInterfaceRequirements) vnir.isValid();
	}

}
