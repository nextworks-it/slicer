package it.nextworks.nfvmano.sebastian.admin.elements;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

@Entity
public class SlaVirtualResourceConstraint implements InterfaceInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
	@ManyToOne
	@JsonIgnore
	private Sla sla;
	
	private VirtualResourceUsage maxResourceLimit;
	private SlaScope scope;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String location;	//used only in case of MEC resources
	
	public SlaVirtualResourceConstraint() { }
	
	public SlaVirtualResourceConstraint(Sla sla,
			VirtualResourceUsage maxResourceLimit,
			SlaScope scope,
			String location) {
		this.sla = sla;
		if (maxResourceLimit != null) this.maxResourceLimit = maxResourceLimit;
		else this.maxResourceLimit = new VirtualResourceUsage(0, 0, 0);
		this.scope = scope;
		this.location = location;
	}

	/**
	 * @return the maxResourceLimit
	 */
	public VirtualResourceUsage getMaxResourceLimit() {
		return maxResourceLimit;
	}

	/**
	 * @param maxResourceLimit the maxResourceLimit to set
	 */
	public void setMaxResourceLimit(VirtualResourceUsage maxResourceLimit) {
		this.maxResourceLimit = maxResourceLimit;
	}

	/**
	 * @return the scope
	 */
	public SlaScope getScope() {
		return scope;
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(SlaScope scope) {
		this.scope = scope;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override
	public void isValid() throws MalformattedElementException {	}
	
}
