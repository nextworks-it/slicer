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
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The TrafficFilter data type supports the specification of ME application requirements related to traffic rules.
 * 
 * Ref. ETSI GS MEC 010-2 V1.1.1 (2017-07) - 6.2.1.10
 * 
 * @author nextworks
 *
 */
@Entity
public class TrafficFilter implements DescriptorInformationElement {
	
	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private TrafficRuleDescriptor trd;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> srcAddress = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> dstAddress = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> srcPort = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> dstPort = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> protocol = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> token = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> srcTunnelAddress = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> tgtTunnelAddress = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> srcTunnelPort = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch= FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> dstTunnelPort = new ArrayList<>();
	
	private int qCI;
	private int dSCP;
	private int tC;
	
	public TrafficFilter() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param trd Traffic Rule Descriptor this traffic filter belongs to
	 * @param srcAddress An IP address or a range of IP addresses.
	 * @param dstAddress An IP address or a range of IP addresses.
	 * @param srcPort A port or a range of ports.
	 * @param dstPort A port or a range of ports.
	 * @param protocol Specify the protocol of the traffic filter.
	 * @param token Used for token based traffic rule.
	 * @param srcTunnelAddress Used for GTP tunnel based traffic rule.
	 * @param tgtTunnelAddress Used for GTP tunnel based traffic rule.
	 * @param srcTunnelPort Used for GTP tunnel based traffic rule.
	 * @param dstTunnelPort Used for GTP tunnel based traffic rule.
	 * @param qCI Used to match all packets that have the same QCI.
	 * @param dSCP Used to match all IPv4 packets that have the same DSCP.
	 * @param tC Used to match all IPv6 packets that have the same TC.
	 */
	public TrafficFilter(TrafficRuleDescriptor trd,
			List<String> srcAddress,
			List<String> dstAddress,
			List<String> srcPort,
			List<String> dstPort,
			List<String> protocol,
			List<String> token,
			List<String> srcTunnelAddress,
			List<String> tgtTunnelAddress,
			List<String> srcTunnelPort,
			List<String> dstTunnelPort,
			int qCI,
			int dSCP,
			int tC) {
		this.trd = trd;
		if (srcAddress != null) this.srcAddress = srcAddress;
		if (dstAddress != null) this.dstAddress = dstAddress;
		if (srcPort != null) this.srcPort = srcPort;
		if (dstPort != null) this.dstPort = dstPort;
		if (protocol != null) this.protocol = protocol;
		if (token != null) this.token = token;
		if (srcTunnelAddress != null) this.srcTunnelAddress = srcTunnelAddress;
		if (tgtTunnelAddress != null) this.tgtTunnelAddress = tgtTunnelAddress;
		if (srcTunnelPort != null) this.srcTunnelPort = srcTunnelPort;
		if (dstTunnelPort != null) this.dstTunnelPort = dstTunnelPort;
		this.qCI = qCI;
		this.dSCP = dSCP;
		this.tC = tC;
	}
	
	
	
	/**
	 * @return the srcAddress
	 */
	@JsonProperty("srcAddress")
	public List<String> getSrcAddress() {
		return srcAddress;
	}

	/**
	 * @return the dstAddress
	 */
	@JsonProperty("dstAddress")
	public List<String> getDstAddress() {
		return dstAddress;
	}

	/**
	 * @return the srcPort
	 */
	@JsonProperty("srcPort")
	public List<String> getSrcPort() {
		return srcPort;
	}

	/**
	 * @return the dstPort
	 */
	@JsonProperty("dstPort")
	public List<String> getDstPort() {
		return dstPort;
	}

	/**
	 * @return the protocol
	 */
	@JsonProperty("protocol")
	public List<String> getProtocol() {
		return protocol;
	}

	/**
	 * @return the token
	 */
	@JsonProperty("token")
	public List<String> getToken() {
		return token;
	}

	/**
	 * @return the srcTunnelAddress
	 */
	@JsonProperty("srcTunnelAddress")
	public List<String> getSrcTunnelAddress() {
		return srcTunnelAddress;
	}

	/**
	 * @return the tgtTunnelAddress
	 */
	@JsonProperty("tgtTunnelAddress")
	public List<String> getTgtTunnelAddress() {
		return tgtTunnelAddress;
	}

	/**
	 * @return the srcTunnelPort
	 */
	@JsonProperty("srcTunnelPort")
	public List<String> getSrcTunnelPort() {
		return srcTunnelPort;
	}

	/**
	 * @return the dstTunnelPort
	 */
	@JsonProperty("dstTunnelPort")
	public List<String> getDstTunnelPort() {
		return dstTunnelPort;
	}

	/**
	 * @return the qCI
	 */
	@JsonProperty("qCI")
	public int getqCI() {
		return qCI;
	}

	/**
	 * @return the dSCP
	 */
	@JsonProperty("dSCP")
	public int getdSCP() {
		return dSCP;
	}

	/**
	 * @return the tC
	 */
	@JsonProperty("tC")
	public int gettC() {
		return tC;
	}

	@Override
	public void isValid() throws MalformattedElementException {	}

}
