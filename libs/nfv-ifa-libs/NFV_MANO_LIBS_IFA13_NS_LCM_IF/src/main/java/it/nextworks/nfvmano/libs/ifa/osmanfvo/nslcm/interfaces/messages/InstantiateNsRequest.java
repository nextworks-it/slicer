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
package it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.elements.AffinityRule;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.ParamsForVnf;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.SapData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.VnfInstanceData;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.VnfLocationConstraints;
import it.nextworks.nfvmano.libs.ifa.records.nsinfo.PnfInfo;

/**
 * Request to instantiate a new NS
 * Ref. IFA 013 v2.3.1 section 7.3.3.2
 * 
 * @author nextworks
 *
 */
public class InstantiateNsRequest implements InterfaceMessage {

	private String nsInstanceId;
	private String flavourId;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<SapData> sapData = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<PnfInfo> pnfInfo = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<VnfInstanceData> vnfInstanceData = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<String> nestedNsInstanceId = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<VnfLocationConstraints> locationConstraints = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Map<String, String> additionalParamForNs = new HashMap<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<ParamsForVnf> additionalParamForVnf = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Date startTime;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String nsInstantiationLevelId;


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<AffinityRule> additionalAffinityOrAntiAffinityRule = new ArrayList<>();
	
	//not standard
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private String serviceType;
	
	public InstantiateNsRequest() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor
	 * 
	 * @param nsInstanceId Identifier of the instance of the NS.
	 * @param flavourId Flavour of the NSD used to instantiate this NS.
	 * @param sapData Create data concerning the SAPs of this NS.
	 * @param pnfInfo Information on the PNF(s) that are part of this NS.
	 * @param vnfInstanceData Specify an existing VNF instance to be used in the NS. If needed, the VNF Profile to be used for this VNF instance is also provided.
	 * @param nestedNsInstanceId Specify an existing NS instance to be used as a nested NS within the NS.
	 * @param locationConstraints Defines the location constraints for the VNF to be instantiated as part of the NS instantiation.
	 * @param additionalParamForNs Allows the OSS/BSS to provide additional parameter(s) at the NS level (as opposed to the VNF level, which is covered in additionalParamForVnf).
	 * @param additionalParamForVnf Allows the OSS/BSS to provide additional parameter(s) per VNF instance. This is for VNFs that are to be created by the NFVO as part of the NS instantiation and not for existing VNF that are referenced for reuse.
	 * @param startTime Timestamp indicating the earliest time to instantiate the NS. Cardinality "0" indicates the NS instantiation takes place immediately.
	 * @param nsInstantiationLevelId Identifies one of the NS instantiation levels declared in the DF applicable to this NS instance. If not present, the default NS instantiation level as declared in the NSD shall be used.
	 * @param additionalAffinityOrAntiAffinityRule Specifies additional affinity or anti-affinity constraint for the VNF instances to be instantiated as part of the NS instantiation. Shall not conflict with rules already specified in the NSD.
	 * @param serviceType Service Type (EMBB; URLLC
	 */
	public InstantiateNsRequest(String nsInstanceId, 
			String flavourId, 
			List<SapData> sapData,
			List<PnfInfo> pnfInfo, 
			List<VnfInstanceData> vnfInstanceData, 
			List<String> nestedNsInstanceId,
			List<VnfLocationConstraints> locationConstraints,
			Map<String, String> additionalParamForNs,
			List<ParamsForVnf> additionalParamForVnf,
			Date startTime,
			String nsInstantiationLevelId,
			List<AffinityRule> additionalAffinityOrAntiAffinityRule) {
		this.nsInstanceId = nsInstanceId;
		this.flavourId = flavourId;
		if (sapData != null) this.sapData = sapData;
		if (pnfInfo != null) this.pnfInfo = pnfInfo;
		if (vnfInstanceData != null) this.vnfInstanceData = vnfInstanceData;
		if (nestedNsInstanceId != null) this.nestedNsInstanceId = nestedNsInstanceId;
		if (locationConstraints != null) this.locationConstraints = locationConstraints;
		if (additionalParamForNs != null) this.additionalParamForNs = additionalParamForNs;
		if (additionalParamForVnf != null) this.additionalParamForVnf = additionalParamForVnf;
		this.startTime = startTime;
		this.nsInstantiationLevelId = nsInstantiationLevelId;
		if (additionalAffinityOrAntiAffinityRule != null) this.additionalAffinityOrAntiAffinityRule = additionalAffinityOrAntiAffinityRule;

	}
	
	/**
	 * Constructor
	 * 
	 * @param nsInstanceId Identifier of the instance of the NS.
	 * @param flavourId Flavour of the NSD used to instantiate this NS.
	 * @param sapData Create data concerning the SAPs of this NS.
	 * @param pnfInfo Information on the PNF(s) that are part of this NS.
	 * @param vnfInstanceData Specify an existing VNF instance to be used in the NS. If needed, the VNF Profile to be used for this VNF instance is also provided.
	 * @param nestedNsInstanceId Specify an existing NS instance to be used as a nested NS within the NS.
	 * @param locationConstraints Defines the location constraints for the VNF to be instantiated as part of the NS instantiation.
	 * @param additionalParamForNs Allows the OSS/BSS to provide additional parameter(s) at the NS level (as opposed to the VNF level, which is covered in additionalParamForVnf).
	 * @param additionalParamForVnf Allows the OSS/BSS to provide additional parameter(s) per VNF instance. This is for VNFs that are to be created by the NFVO as part of the NS instantiation and not for existing VNF that are referenced for reuse.
	 * @param startTime Timestamp indicating the earliest time to instantiate the NS. Cardinality "0" indicates the NS instantiation takes place immediately.
	 * @param nsInstantiationLevelId Identifies one of the NS instantiation levels declared in the DF applicable to this NS instance. If not present, the default NS instantiation level as declared in the NSD shall be used.
	 * @param additionalAffinityOrAntiAffinityRule Specifies additional affinity or anti-affinity constraint for the VNF instances to be instantiated as part of the NS instantiation. Shall not conflict with rules already specified in the NSD.
	 * @param serviceType type of service (e.g. EMBB, URLLC, MTC)
	 */
	public InstantiateNsRequest(String nsInstanceId, 
			String flavourId, 
			List<SapData> sapData,
			List<PnfInfo> pnfInfo, 
			List<VnfInstanceData> vnfInstanceData, 
			List<String> nestedNsInstanceId,
			List<VnfLocationConstraints> locationConstraints,
			Map<String, String> additionalParamForNs,
			List<ParamsForVnf> additionalParamForVnf,
			Date startTime,
			String nsInstantiationLevelId,
			List<AffinityRule> additionalAffinityOrAntiAffinityRule,
			String serviceType) {
		this.nsInstanceId = nsInstanceId;
		this.flavourId = flavourId;
		if (sapData != null) this.sapData = sapData;
		if (pnfInfo != null) this.pnfInfo = pnfInfo;
		if (vnfInstanceData != null) this.vnfInstanceData = vnfInstanceData;
		if (nestedNsInstanceId != null) this.nestedNsInstanceId = nestedNsInstanceId;
		if (locationConstraints != null) this.locationConstraints = locationConstraints;
		if (additionalParamForNs != null) this.additionalParamForNs = additionalParamForNs;
		if (additionalParamForVnf != null) this.additionalParamForVnf = additionalParamForVnf;
		this.startTime = startTime;
		this.nsInstantiationLevelId = nsInstantiationLevelId;
		if (additionalAffinityOrAntiAffinityRule != null) this.additionalAffinityOrAntiAffinityRule = additionalAffinityOrAntiAffinityRule;
		this.serviceType = serviceType;
	}
	
	

	/**
	 * @return the nsInstanceId
	 */
	public String getNsInstanceId() {
		return nsInstanceId;
	}

	/**
	 * @return the flavourId
	 */
	public String getFlavourId() {
		return flavourId;
	}

	/**
	 * @return the sapData
	 */
	public List<SapData> getSapData() {
		return sapData;
	}

	/**
	 * @return the pnfInfo
	 */
	public List<PnfInfo> getPnfInfo() {
		return pnfInfo;
	}

	/**
	 * @return the vnfInstanceData
	 */
	public List<VnfInstanceData> getVnfInstanceData() {
		return vnfInstanceData;
	}

	/**
	 * @return the nestedNsInstanceId
	 */
	public List<String> getNestedNsInstanceId() {
		return nestedNsInstanceId;
	}

	/**
	 * @return the locationConstraints
	 */
	public List<VnfLocationConstraints> getLocationConstraints() {
		return locationConstraints;
	}

	/**
	 * @return the additionalParamForNs
	 */
	public Map<String, String> getAdditionalParamForNs() {
		return additionalParamForNs;
	}

	/**
	 * @return the additionalParamForVnf
	 */
	public List<ParamsForVnf> getAdditionalParamForVnf() {
		return additionalParamForVnf;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @return the nsInstantiationLevelId
	 */
	public String getNsInstantiationLevelId() {
		return nsInstantiationLevelId;
	}

	/**
	 * @return the additionalAffinityOrAntiAffinityRule
	 */
	public List<AffinityRule> getAdditionalAffinityOrAntiAffinityRule() {
		return additionalAffinityOrAntiAffinityRule;
	}
	
	
	
	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * 
	 * @return the overall list of configuration parameters, for both VNFs and NS
	 */
	@JsonIgnore
	public Map<String, String> getConfigurationParameterList() {
		Map<String, String> configurationParameters = new HashMap<>();
		if ((additionalParamForNs != null) && (!(additionalParamForNs.isEmpty()))) {
			configurationParameters.putAll(additionalParamForNs);
		}
		for (ParamsForVnf vnfParams : additionalParamForVnf) {
			if ((vnfParams.getAdditionalParam() != null) && (!(vnfParams.getAdditionalParam().isEmpty()))) {
				configurationParameters.putAll(vnfParams.getAdditionalParam());
			}
		}
		return configurationParameters;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (nsInstanceId == null) throw new MalformattedElementException("Instantiate NS request without NS instance ID");
		if (flavourId == null) throw new MalformattedElementException("Instantiate NS request without flavour ID");
		if (sapData != null) {
			for (SapData s: sapData) s.isValid();
		}
		if (pnfInfo != null) {
			for (PnfInfo p: pnfInfo) p.isValid();
		}
		if (vnfInstanceData != null) {
			for (VnfInstanceData d: vnfInstanceData) d.isValid();
		}
		if (locationConstraints != null) {
			for (VnfLocationConstraints l: locationConstraints) l.isValid();
		}
		if (additionalParamForVnf != null) {
			for (ParamsForVnf s: additionalParamForVnf) s.isValid();
		}
		if (additionalAffinityOrAntiAffinityRule != null) {
			for (AffinityRule r: additionalAffinityOrAntiAffinityRule) r.isValid();
		}
	}

}
