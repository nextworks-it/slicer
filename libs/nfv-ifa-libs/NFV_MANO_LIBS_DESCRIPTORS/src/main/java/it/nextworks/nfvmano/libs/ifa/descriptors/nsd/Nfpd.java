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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.elements.QoS;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.Rule;


/**
 * The Nfpd (Network Forwarding Path Descriptor) 
 * information element associates traffic 
 * flow criteria to a list of descriptors associated 
 * to the connection points and service access points 
 * to be visited by traffic flows matching these criteria.
 * 
 * Ref. IFA 014 v2.3.1 - 6.4.3
 * 
 * @author nextworks
 *
 */
@Entity
public class Nfpd implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Vnffgd fg;
	
	private String nfpId;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@OneToOne(fetch=FetchType.EAGER, mappedBy = "nfpd", cascade=CascadeType.ALL, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Rule nfpRule;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> cpd = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Embedded
	private QoS qos;		//This is an extensions to the standard
	
	public Nfpd() {
		// JPA only
	}
	
	/**
	 * Constructor
	 * 
	 * @param fg NS forwarding graph this nfpd belongs to
	 * @param nfpId Identifies this nfpd information element within a VNFFGD.
	 * @param nfpRule Provides an NFP classification and selection rule. The rule may be expressed as a criteria constructed out of atomic assertions linked by Boolean operators AND, OR and NOT. Examples of atomic assertions are assertions on packet header fields’ values, date and time ranges, etc.
	 * @param cpd References the descriptor of a connection point to be traversed by the traffic flows matching the criteria. When multiple values are provided, the order is significant and specifies the sequence of connection points to be traversed
	 * @param qos QoS to represent the e2e delay across a VNF path.
	 */
	public Nfpd(Vnffgd fg,
			String nfpId,
			Rule nfpRule,
			List<String> cpd,
			QoS qos) {
		this.fg  = fg;
		this.nfpId = nfpId;
		this.nfpRule = nfpRule;
		if (cpd != null) this.cpd = cpd;
		this.qos = qos;
	}
	
	/**
	 * Constructor
	 * 
	 * @param fg NS forwarding graph this nfpd belongs to
	 * @param nfpId Identifies this nfpd information element within a VNFFGD.
	 * @param cpd References the descriptor of a connection point to be traversed by the traffic flows matching the criteria. When multiple values are provided, the order is significant and specifies the sequence of connection points to be traversed
	 * @param qos QoS to represent the e2e delay across a VNF path.
	 */
	public Nfpd(Vnffgd fg,
			String nfpId,
			List<String> cpd,
			QoS qos) {
		this.fg  = fg;
		this.nfpId = nfpId;
		if (cpd != null) this.cpd = cpd;
		this.qos = qos;
	}
	
	

	/**
	 * @return the nfpId
	 */
	@JsonProperty("nfpId")
	public String getNfpId() {
		return nfpId;
	}

	/**
	 * @return the nfpRule
	 */
	@JsonProperty("nfpRule")
	public Rule getNfpRule() {
		return nfpRule;
	}

	/**
	 * @return the cpd
	 */
	@JsonProperty("cpd")
	public List<String> getCpd() {
		return cpd;
	}
	
	

	/**
	 * @return the qos
	 */
	@JsonProperty("qos")
	public QoS getQos() {
		return qos;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.nfpId == null) throw new MalformattedElementException("NFDP without ID");
		if (this.nfpRule != null) this.nfpRule.isValid();
		if ((this.cpd == null) || (this.cpd.isEmpty())) {
			throw new MalformattedElementException("NFDP without CPD");
		}
		if (this.qos != null) this.qos.isValid();
	}

	public void setFg(Vnffgd fg) {
		this.fg = fg;
	}

	public void setNfpId(String nfpId) {
		this.nfpId = nfpId;
	}

	public void setNfpRule(Rule nfpRule) {
		this.nfpRule = nfpRule;
	}

	public void setCpd(List<String> cpd) {
		this.cpd = cpd;
	}

	public void setQos(QoS qos) {
		this.qos = qos;
	}
}
