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
package it.nextworks.nfvmano.libs.ifa.descriptors.vnfd;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.ifa.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.descriptors.common.elements.TerminateVnfOpConfig;

/**
 * This information element is a container for all attributes 
 * that affect the invocation of the VNF Lifecycle Management
 * operations, structured by operation.
 * 
 * REF. IFA-011 v2.3.1 - section 7.1.5.2
 * 
 * 
 * @author nextworks
 *
 */
@Entity
public class VnfLcmOperationsConfiguration implements DescriptorInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	@JsonIgnore
	@JoinColumn(name="vnfd_df_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	VnfDf df;
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy = "config", cascade=CascadeType.ALL, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private InstantiateVnfOpConfig instantiateVnfOpConfig;
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy = "config", cascade=CascadeType.ALL, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ScaleVnfOpConfig scaleVnfOpConfig;
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy = "config", cascade=CascadeType.ALL, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ScaleVnfToLevelOpConfig scaleVnfToLevelOpConfig;
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy = "config", cascade=CascadeType.ALL, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ChangeVnfFlavourOpConfig changeVnfFlavourOpConfig;
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy = "config", cascade=CascadeType.ALL, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private HealVnfOpConfig healVnfOpConfig;
	
	@Embedded
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@AttributeOverrides({
	    @AttributeOverride(name="minGracefulStopTimeout",column=@Column(name="minGracefulStopTimeoutOp")),
	    @AttributeOverride(name="maxRecommendedGracefulStopTimeout",column=@Column(name="maxRecommendedGracefulStopTimeoutOp"))
	  })
	private TerminateVnfOpConfig operateVnfOpConfig;
	
	@Embedded
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private TerminateVnfOpConfig terminateVnfOpConfig;
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy = "config", cascade=CascadeType.ALL, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ChangeExtVnfConnectivityOpConfig changeExtVnfConnectivityOpConfig;
	
	public VnfLcmOperationsConfiguration() { }
	
	/**
	 * Constructor
	 * 
	 * @param vnfDf VNF deployment flavour this config options belong to
	 * @param operateVnfOpConfig Configuration parameters for the OperateVnf operation.
	 * @param terminateVnfOpConfig Configuration parameters for the TerminateVnf operation.
	 */
	public VnfLcmOperationsConfiguration(VnfDf vnfDf,
			TerminateVnfOpConfig operateVnfOpConfig,
			TerminateVnfOpConfig terminateVnfOpConfig) {
		this.operateVnfOpConfig = operateVnfOpConfig;
		this.terminateVnfOpConfig = terminateVnfOpConfig;
	}
	
	

	/**
	 * @return the instantiateVnfOpConfig
	 */
	public InstantiateVnfOpConfig getInstantiateVnfOpConfig() {
		return instantiateVnfOpConfig;
	}

	/**
	 * @return the scaleVnfOpConfig
	 */
	public ScaleVnfOpConfig getScaleVnfOpConfig() {
		return scaleVnfOpConfig;
	}

	/**
	 * @return the scaleVnfToLevelOpConfig
	 */
	public ScaleVnfToLevelOpConfig getScaleVnfToLevelOpConfig() {
		return scaleVnfToLevelOpConfig;
	}

	/**
	 * @return the healVnfOpConfig
	 */
	public HealVnfOpConfig getHealVnfOpConfig() {
		return healVnfOpConfig;
	}

	/**
	 * @return the operateVnfOpConfig
	 */
	public TerminateVnfOpConfig getOperateVnfOpConfig() {
		return operateVnfOpConfig;
	}

	/**
	 * @return the terminateVnfOpConfig
	 */
	public TerminateVnfOpConfig getTerminateVnfOpConfig() {
		return terminateVnfOpConfig;
	}
	
	/**
	 * @return the changeVnfFlavourOpConfig
	 */
	public ChangeVnfFlavourOpConfig getChangeVnfFlavourOpConfig() {
		return changeVnfFlavourOpConfig;
	}
	
	/**
	 * @return the changeExtVnfConnectivityOpConfig
	 */
	public ChangeExtVnfConnectivityOpConfig getChangeExtVnfConnectivityOpConfig() {
		return changeExtVnfConnectivityOpConfig;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (operateVnfOpConfig != null) operateVnfOpConfig.isValid();
		if (instantiateVnfOpConfig != null) instantiateVnfOpConfig.isValid();
		if (terminateVnfOpConfig != null) terminateVnfOpConfig.isValid();
		if (scaleVnfOpConfig != null) scaleVnfOpConfig.isValid();
		if (scaleVnfToLevelOpConfig != null) scaleVnfToLevelOpConfig.isValid();
		if (healVnfOpConfig != null) healVnfOpConfig.isValid();
		if (changeVnfFlavourOpConfig != null) changeVnfFlavourOpConfig.isValid();
		if (changeExtVnfConnectivityOpConfig != null) changeExtVnfConnectivityOpConfig.isValid();
	}

}
