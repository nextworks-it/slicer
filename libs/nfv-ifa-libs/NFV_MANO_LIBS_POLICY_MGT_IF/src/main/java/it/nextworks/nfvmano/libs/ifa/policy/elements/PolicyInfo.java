package it.nextworks.nfvmano.libs.ifa.policy.elements;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.policy.enums.ActivationStatus;

/**
 * The PolicyInfo element defines policy related information
 *
 * REF IFA 013 v3.2.1 - 8.8.2.2
 *
 * @author nextworks
 *
 */
@Entity
public class PolicyInfo implements InterfaceInformationElement {

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
	
    private String policyInfoId;
    private String designer;
    private String name;
    private String version;
    
    @Embedded
    private Policy policy;
    
    private ActivationStatus activationStatus;

    public PolicyInfo() { }

    /**
     * Constructor
     *
     * @param policyInfoId Identifier of policy information
     * @param designer Human readable name of designer of the policy
     * @param name Human readable name of the policy
     * @param version Version of the policy
     * @param policy Specifies the policy
     * @param activationStatus Status of the policy on whether it is activated or deactivated
     */

    public PolicyInfo(String policyInfoId, String designer, String name, String version, Policy policy, ActivationStatus activationStatus){

        this.policyInfoId = policyInfoId;
        this.designer = designer;
        this.name = name;
        this.version = version;
        this.policy = policy;
        this.activationStatus = activationStatus;
    }

    /**
     *
     * @return policyInfoId
     */
    public String getPolicyInfoId() {
        return policyInfoId;
    }

    /**
     *
     * @return designer
     */
    public String getDesigner() {
        return designer;
    }

    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /**
     *
     * @return policy
     */
    public Policy getPolicy() {
        return policy;
    }

    /**
     *
     * @return activationStatus
     */
    public ActivationStatus getActivationStatus() {
        return activationStatus;
    }
    
    

    /**
	 * @param activationStatus the activationStatus to set
	 */
	public void setActivationStatus(ActivationStatus activationStatus) {
		this.activationStatus = activationStatus;
	}
	
	

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param policyInfoId the policyInfoId to set
	 */
	public void setPolicyInfoId(String policyInfoId) {
		this.policyInfoId = policyInfoId;
	}

	@Override
    public void isValid() throws MalformattedElementException {
        if (this.policyInfoId == null) throw new MalformattedElementException("PolicyInfo without policyInfoId");
        if (this.designer == null) throw new MalformattedElementException("PolicyInfo without designer");
        if (this.name == null) throw new MalformattedElementException("PolicyInfo without name");
        if (this.version == null) throw new MalformattedElementException("PolicyInfo without version");
        if (this.policy == null) throw new MalformattedElementException("PolicyInfo without policy");
        if (this.activationStatus == null) throw new MalformattedElementException("PolicyInfo without activationStatus");
    }
}
