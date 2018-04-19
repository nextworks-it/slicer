package it.nextworks.nfvmano.sebastian.catalogue.elements;

import java.util.ArrayList;
import java.util.List;

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
