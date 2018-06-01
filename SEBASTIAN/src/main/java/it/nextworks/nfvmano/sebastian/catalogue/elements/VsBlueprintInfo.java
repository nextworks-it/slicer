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
package it.nextworks.nfvmano.sebastian.catalogue.elements;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.nextworks.nfvmano.libs.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

@Entity
public class VsBlueprintInfo implements InterfaceInformationElement {
	
	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

	private String vsBlueprintId;
	private String vsBlueprintVersion;
	private String name;
	
	@Transient
	private VsBlueprint vsBlueprint;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> onBoardedNsdInfoId = new ArrayList<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> onBoardedVnfPackageInfoId = new ArrayList<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> onBoardedMecAppPackageInfoId = new ArrayList<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<String> activeVsdId = new ArrayList<>();
	
	public VsBlueprintInfo() {	}
	
	

	/**
	 * @param vsBlueprintId
	 * @param vsBlueprintVersion
	 * @param name
	 */
	public VsBlueprintInfo(String vsBlueprintId, String vsBlueprintVersion, String name) {
		this.vsBlueprintId = vsBlueprintId;
		this.vsBlueprintVersion = vsBlueprintVersion;
		this.name = name;
	}



	/**
	 * @return the id
	 */
	@JsonIgnore
	public Long getId() {
		return id;
	}



	/**
	 * @return the vsBlueprint
	 */
	public VsBlueprint getVsBlueprint() {
		return vsBlueprint;
	}

	/**
	 * @param vsBlueprint the vsBlueprint to set
	 */
	public void setVsBlueprint(VsBlueprint vsBlueprint) {
		this.vsBlueprint = vsBlueprint;
	}

	/**
	 * @return the vsBlueprintId
	 */
	public String getVsBlueprintId() {
		return vsBlueprintId;
	}

	/**
	 * @return the vsBlueprintVersion
	 */
	public String getVsBlueprintVersion() {
		return vsBlueprintVersion;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the onBoardedNsdInfoId
	 */
	public List<String> getOnBoardedNsdInfoId() {
		return onBoardedNsdInfoId;
	}

	/**
	 * @return the onBoardedVnfPackageInfoId
	 */
	public List<String> getOnBoardedVnfPackageInfoId() {
		return onBoardedVnfPackageInfoId;
	}

	/**
	 * @return the onBoardedMecAppPackageInfoId
	 */
	public List<String> getOnBoardedMecAppPackageInfoId() {
		return onBoardedMecAppPackageInfoId;
	}

	/**
	 * @return the activeVsdId
	 */
	public List<String> getActiveVsdId() {
		return activeVsdId;
	}
	
	public void addVsd(String vsdId) {
		if (!(activeVsdId.contains(vsdId)))
			activeVsdId.add(vsdId);
	}
	
	public void removeVsd(String vsdId) {
		if (activeVsdId.contains(vsdId))
			activeVsdId.remove(vsdId);
	}
	
	public void addMecAppInfoId(String appDInfoId) {
		onBoardedMecAppPackageInfoId.add(appDInfoId);
	}
	
	public void addNsdInfoId(String nsdInfoId) {
		onBoardedNsdInfoId.add(nsdInfoId);
	}

	public void addVnfPackageInfoId(String vnfPackageInfoId) {
		onBoardedVnfPackageInfoId.add(vnfPackageInfoId);
	}
	
	@Override
	public void isValid() throws MalformattedElementException {
		if (vsBlueprintId == null) throw new MalformattedElementException("VS Blueprint info without VS ID");
		if (vsBlueprintVersion == null) throw new MalformattedElementException("VS Blueprint info without VS version");
		if (name == null) throw new MalformattedElementException("VS Blueprint info without VS name");
	}
}
