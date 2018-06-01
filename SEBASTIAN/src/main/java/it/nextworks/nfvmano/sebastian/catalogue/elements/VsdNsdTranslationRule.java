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
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;

@Entity
public class VsdNsdTranslationRule implements InterfaceInformationElement {

	@Id
	@GeneratedValue
    @JsonIgnore
    private Long id;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<VsdParameterValueRange> input = new ArrayList<>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String vsbId;
	
	private String nsdId; 
	private String nsdVersion;
	private String nsFlavourId;
	private String nsInstantiationLevelId;
	
	@JsonIgnore
	private String nsdInfoId;
	
	public VsdNsdTranslationRule() { }
	
	
	
	/**
	 * @param input
	 * @param nsdId
	 * @param nsdVersion
	 * @param nsFlavourId
	 * @param nsInstantiationLevelId
	 */
	public VsdNsdTranslationRule(List<VsdParameterValueRange> input, String nsdId, String nsdVersion,
			String nsFlavourId, String nsInstantiationLevelId) {
		if (input!= null) this.input = input;
		this.nsdId = nsdId;
		this.nsdVersion = nsdVersion;
		this.nsFlavourId = nsFlavourId;
		this.nsInstantiationLevelId = nsInstantiationLevelId;
	}



	/**
	 * @return the nsdInfoId
	 */
	public String getNsdInfoId() {
		return nsdInfoId;
	}

	/**
	 * @param nsdInfoId the nsdInfoId to set
	 */
	public void setNsdInfoId(String nsdInfoId) {
		this.nsdInfoId = nsdInfoId;
	}

	/**
	 * @return the input
	 */
	public List<VsdParameterValueRange> getInput() {
		return input;
	}

	/**
	 * @return the nsdId
	 */
	public String getNsdId() {
		return nsdId;
	}

	/**
	 * @return the nsdVersion
	 */
	public String getNsdVersion() {
		return nsdVersion;
	}

	/**
	 * @return the nsFlavourId
	 */
	public String getNsFlavourId() {
		return nsFlavourId;
	}

	/**
	 * @return the nsInstantiationLevelId
	 */
	public String getNsInstantiationLevelId() {
		return nsInstantiationLevelId;
	}
	
	
	
	/**
	 * @return the vsbId
	 */
	public String getVsbId() {
		return vsbId;
	}



	/**
	 * @param vsbId the vsbId to set
	 */
	public void setVsbId(String vsbId) {
		this.vsbId = vsbId;
	}



	@JsonIgnore
	public boolean matchesNsdIdAndNsdVersion(String id, String v) {
		if (nsdId.equals(id) && nsdVersion.equals(v)) return true;
		else return false;
	}
	
	/**
	 * This method verifies if a given set of vsd parameters match the rule.
	 * At the moment only integer parameters are supported.
	 * 
	 * @param vsdParameters VSD parameters to be verified
	 * @return true if all the given VSD parameters match the rule 
	 */
	@JsonIgnore
	public boolean matchesVsdParameters(Map<String, String> vsdParameters) {
		for (Map.Entry<String, String> entry : vsdParameters.entrySet()) {
			String parameterId = entry.getKey();
			try {
				VsdParameterValueRange vr = getVsdParameterValueRange(parameterId);
				String parameterValue = entry.getValue();
				if (!(vr.matchesVsdParameter(parameterValue))) return false;
			} catch (NotExistingEntityException e) {
				return false;
			}
		}
		return true;
	}
	
	@JsonIgnore
	private VsdParameterValueRange getVsdParameterValueRange (String parameterId) throws NotExistingEntityException {
		for (VsdParameterValueRange p : input) {
			if (p.getParameterId().equals(parameterId)) return p;
		}
		throw new NotExistingEntityException("VSD parameter not found in the rule");
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if ((input == null) || (input.isEmpty())) throw new MalformattedElementException("VSD NSD translation rule without matching conditions");
		else for (VsdParameterValueRange vr : input) vr.isValid();
		if (nsdId == null) throw new MalformattedElementException("VSD NSD translation rule without NSD ID");
		if (nsdVersion == null) throw new MalformattedElementException("VSD NSD translation rule without NSD version");
		if (nsFlavourId == null) throw new MalformattedElementException("VSD NSD translation rule without NS flavour ID");
		if (nsInstantiationLevelId == null) throw new MalformattedElementException("VSD NSD translation rule without NS Instantiation Level ID");
	}

}
