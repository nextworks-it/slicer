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
import it.nextworks.nfvmano.libs.common.enums.NetworkInterfaceType;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

import javax.persistence.*;

/**
 * The InterfaceDescriptor data type describes an interface of a ME application.
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.2.1.11
 * 
 * @author nextworks
 *
 */
@Entity
public class MecAppInterfaceDescriptor implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private TrafficRuleDescriptor trd;
	
	private NetworkInterfaceType interfaceType;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Embedded
	private TunnelInfo tunnelInfo;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String srcMACAddress;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String dstMACAddress;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String dstIPAddress;
	
	public MecAppInterfaceDescriptor() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param trd Traffic rule descriptor this element belongs to 
	 * @param interfaceType Type of interface:
	 * @param tunnelInfo Included only if the destination address type is tunnel.
	 * @param srcMACAddress If the interface type is MAC, the source address identifies the MAC address of the interface.
	 * @param dstMACAddress If the interface type is MAC, the destination address identifies the MAC address of the destination. Only used for dstInterface.
	 * @param dstIPAddress If the interface type is IP, the destination address identifies the IP address of the destination. Only used for dstInterface.
	 */
	public MecAppInterfaceDescriptor(TrafficRuleDescriptor trd,
									 NetworkInterfaceType interfaceType,
									 TunnelInfo tunnelInfo,
									 String srcMACAddress,
									 String dstMACAddress,
									 String dstIPAddress) {
		this.trd = trd;
		this.interfaceType = interfaceType;
		this.tunnelInfo = tunnelInfo;
		this.srcMACAddress = srcMACAddress;
		this.dstMACAddress = dstMACAddress;
		this.dstIPAddress = dstIPAddress;
	}
	
	
	
	/**
	 * @return the interfaceType
	 */
	@JsonProperty("interfaceType")
	public NetworkInterfaceType getInterfaceType() {
		return interfaceType;
	}

	/**
	 * @return the tunnelInfo
	 */
	@JsonProperty("tunnelInfo")
	public TunnelInfo getTunnelInfo() {
		return tunnelInfo;
	}

	/**
	 * @return the srcMACAddress
	 */
	@JsonProperty("srcMACAddress")
	public String getSrcMACAddress() {
		return srcMACAddress;
	}

	/**
	 * @return the dstMACAddress
	 */
	@JsonProperty("dstMACAddress")
	public String getDstMACAddress() {
		return dstMACAddress;
	}

	/**
	 * @return the dstIPAddress
	 */
	@JsonProperty("dstIPAddress")
	public String getDstIPAddress() {
		return dstIPAddress;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (tunnelInfo != null) tunnelInfo.isValid();
	}

}
