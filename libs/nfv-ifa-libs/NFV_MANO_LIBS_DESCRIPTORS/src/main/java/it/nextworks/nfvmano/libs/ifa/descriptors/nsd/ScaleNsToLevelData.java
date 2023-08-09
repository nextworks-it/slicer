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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.records.nsinfo.NsScaleInfo;

/**
 * The ScaleNsToLevelData information element describes the information 
 * needed to scale an NS instance to a target size.
 * The target size is either expressed as an NS instantiation level or 
 * as a list of NS scale levels, one per NS scaling aspect,
 * of the current DF. The NS instantiation levels, the NS scaling aspects 
 * and their corresponding NS scale levels applicable to the NS instance 
 * are declared in the NSD.
 * 
 * REF IFA 013 v2.3.1 - 8.3.4.8
 * 
 * @author nextworks
 *
 */
@Entity
public class ScaleNsToLevelData implements InterfaceInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	@JsonIgnore
	@JoinColumn(name="autoscaling_action_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private AutoscalingAction action;
	
	private String nsInstantiationLevel;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<NsScaleInfo> nsScaleInfo = new ArrayList<>();
	
	/**
	 * Constructor
	 * 
	 * @param nsInstantiationLevel Identifier of the target NS instantiation level of the current DF to which the NS instance is requested to be scaled.
	 * @param nsScaleInfo For each NS scaling aspect of the current DF, defines the target NS scale level to which the NS instance is to be scaled.
	 */
	public ScaleNsToLevelData(String nsInstantiationLevel,
			List<NsScaleInfo> nsScaleInfo) {
		if (nsScaleInfo != null) this.nsScaleInfo = nsScaleInfo;
		this.nsInstantiationLevel = nsInstantiationLevel;
	}
	
	/**
	 * Constructor
	 * 
	 * @param action Autoscaling action this element belongs to.
	 * @param nsInstantiationLevel Identifier of the target NS instantiation level of the current DF to which the NS instance is requested to be scaled.
	 * @param nsScaleInfo For each NS scaling aspect of the current DF, defines the target NS scale level to which the NS instance is to be scaled.
	 */
	public ScaleNsToLevelData(AutoscalingAction action,
			String nsInstantiationLevel,
			List<NsScaleInfo> nsScaleInfo) {
		this.action = action;
		if (nsScaleInfo != null) this.nsScaleInfo = nsScaleInfo;
		this.nsInstantiationLevel = nsInstantiationLevel;
	}
	
	/**
	 * Default constructor
	 */
	public ScaleNsToLevelData() { 
		//JPA only
	}

	
	
	/**
	 * @return the nsInstantiationLevel
	 */
	public String getNsInstantiationLevel() {
		return nsInstantiationLevel;
	}

	/**
	 * @return the nsScaleInfo
	 */
	public List<NsScaleInfo> getNsScaleInfo() {
		return nsScaleInfo;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (nsScaleInfo != null) {
			for (NsScaleInfo i : nsScaleInfo) i.isValid();
		}
	}

}
