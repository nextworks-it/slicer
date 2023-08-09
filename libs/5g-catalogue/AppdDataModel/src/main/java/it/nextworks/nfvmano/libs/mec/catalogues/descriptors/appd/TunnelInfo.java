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

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.enums.TunnelType;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.Embeddable;

/**
 * The TunnelInfo data type supports the specification of ME application requirements related to traffic rules.
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.2.1.12
 * 
 * @author nextworks
 *
 */
@Embeddable
public class TunnelInfo implements DescriptorInformationElement {

	private TunnelType tunnelType;
	
	private String tunnelDstAddress;
	
	private String tunnelSrcAddress;
	
	private String tunnelSpecificData;
	
	public TunnelInfo() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param tunnelType Type of tunnel
	 * @param tunnelDstAddress Destination address of the tunnel
	 * @param tunnelSrcAddress Source address of the tunnel 
	 * @param tunnelSpecificData Parameters specific to the tunnel
	 */
	public TunnelInfo(TunnelType tunnelType,
			String tunnelDstAddress,
			String tunnelSrcAddress,
			String tunnelSpecificData) {
		this.tunnelType = tunnelType;
		this.tunnelDstAddress = tunnelDstAddress;
		this.tunnelSrcAddress = tunnelSrcAddress;
		this.tunnelSpecificData = tunnelSpecificData;
	}
	
	
	
	/**
	 * @return the tunnelType
	 */
	@JsonProperty("tunnelType")
	public TunnelType getTunnelType() {
		return tunnelType;
	}

	/**
	 * @return the tunnelDstAddress
	 */
	@JsonProperty("tunnelDstAddress")
	public String getTunnelDstAddress() {
		return tunnelDstAddress;
	}

	/**
	 * @return the tunnelSrcAddress
	 */
	@JsonProperty("tunnelSrcAddress")
	public String getTunnelSrcAddress() {
		return tunnelSrcAddress;
	}

	/**
	 * @return the tunnelSpecificData
	 */
	@JsonProperty("tunnelSpecificData")
	public String getTunnelSpecificData() {
		return tunnelSpecificData;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (tunnelDstAddress == null) throw new MalformattedElementException("Tunnel info without dst address");
		if (tunnelSrcAddress == null) throw new MalformattedElementException("Tunnel info without src address");
	}

}
