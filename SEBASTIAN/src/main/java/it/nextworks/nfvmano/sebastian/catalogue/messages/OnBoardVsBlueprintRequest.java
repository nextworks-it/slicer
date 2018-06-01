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
package it.nextworks.nfvmano.sebastian.catalogue.messages;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnBoardVnfPackageRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnboardAppPackageRequest;
import it.nextworks.nfvmano.libs.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsBlueprint;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsdNsdTranslationRule;

public class OnBoardVsBlueprintRequest implements InterfaceMessage {

	private VsBlueprint vsBlueprint;
	private List<Nsd> nsds = new ArrayList<>();
	private List<OnBoardVnfPackageRequest> vnfPackages = new ArrayList<>();
	private List<OnboardAppPackageRequest> mecAppPackages = new ArrayList<>();
	private List<VsdNsdTranslationRule> translationRules = new ArrayList<>();
	
	public OnBoardVsBlueprintRequest() { }
	
	/**
	 * @param vsBlueprint
	 * @param nsds
	 * @param vnfPackages
	 * @param mecAppPackages
	 * @param translationRules
	 */
	public OnBoardVsBlueprintRequest(VsBlueprint vsBlueprint, List<Nsd> nsds,
			List<OnBoardVnfPackageRequest> vnfPackages, List<OnboardAppPackageRequest> mecAppPackages,
			List<VsdNsdTranslationRule> translationRules) {
		this.vsBlueprint = vsBlueprint;
		if (nsds != null) this.nsds = nsds;
		if (vnfPackages != null) this.vnfPackages = vnfPackages;
		if (mecAppPackages != null) this.mecAppPackages = mecAppPackages;
		if (translationRules != null) this.translationRules = translationRules;
	}

	/**
	 * @return the vsBlueprint
	 */
	public VsBlueprint getVsBlueprint() {
		return vsBlueprint;
	}

	/**
	 * @return the nsds
	 */
	public List<Nsd> getNsds() {
		return nsds;
	}

	/**
	 * @return the vnfPackages
	 */
	public List<OnBoardVnfPackageRequest> getVnfPackages() {
		return vnfPackages;
	}

	/**
	 * @return the mecAppPackages
	 */
	public List<OnboardAppPackageRequest> getMecAppPackages() {
		return mecAppPackages;
	}

	/**
	 * @return the translationRules
	 */
	public List<VsdNsdTranslationRule> getTranslationRules() {
		return translationRules;
	}
	
	@JsonIgnore
	public void setBlueprintIdInTranslationRules(String vsbId) {
		for (VsdNsdTranslationRule tr : translationRules) 
			tr.setVsbId(vsbId);
	}
	
	@JsonIgnore
	public void setNsdInfoIdInTranslationRules(String nsdInfoId, String nsdId, String nsdVersion) {
		for (VsdNsdTranslationRule tr : translationRules) {
			if (tr.matchesNsdIdAndNsdVersion(nsdId, nsdVersion)) tr.setNsdInfoId(nsdInfoId);
		}
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vsBlueprint == null) throw new MalformattedElementException("Onboard VS blueprint request without VS blueprint");
		else vsBlueprint.isValid();
		//if (nsds.isEmpty()) throw new MalformattedElementException("Onboard VS blueprint request without NSD");
		if (translationRules.isEmpty()) throw new MalformattedElementException("Onboard VS blueprint request without translation rules");
		for (Nsd nsd : nsds) nsd.isValid();
		for (OnBoardVnfPackageRequest vnf : vnfPackages) vnf.isValid();
		for (OnboardAppPackageRequest mecApp : mecAppPackages) mecApp.isValid();
		for (VsdNsdTranslationRule tr : translationRules) tr.isValid();
	}

}
