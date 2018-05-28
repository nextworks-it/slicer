package it.nextworks.nfvmano.sebastian.admin.elements;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.common.enums.OperationalState;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;

@Entity
public class Sla implements InterfaceInformationElement {
	
	@Id
    @GeneratedValue
    private Long id;
	
	@ManyToOne
	@JsonIgnore
	private Tenant tenant;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "sla", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<SlaVirtualResourceConstraint> slaConstraints = new ArrayList<>();
	
	private OperationalState slaStatus;
	
	public Sla() { }
	
	public Sla(Tenant tenant,
			OperationalState slaStatus) {
		this.tenant = tenant;
		this.slaStatus = slaStatus;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the tenant
	 */
	public Tenant getTenant() {
		return tenant;
	}

	/**
	 * @return the slaConstraints
	 */
	public List<SlaVirtualResourceConstraint> getSlaConstraints() {
		return slaConstraints;
	}

	/**
	 * @return the slaStatus
	 */
	public OperationalState getSlaStatus() {
		return slaStatus;
	}
	
	@JsonIgnore
	public SlaVirtualResourceConstraint getGlobalConstraint() throws NotExistingEntityException {
		for (SlaVirtualResourceConstraint sc : slaConstraints) {
			if (sc.getScope() == SlaScope.GLOBAL_VIRTUAL_RESOURCE) return sc;
		}
		throw new NotExistingEntityException("Virtual resource constraint with global scope not found.");
	}
	
	@Override
	public void isValid() throws MalformattedElementException {
		if (slaConstraints.isEmpty()) throw new MalformattedElementException("SLA without constraints");
		for (SlaVirtualResourceConstraint src : slaConstraints) src.isValid();
	}

}
