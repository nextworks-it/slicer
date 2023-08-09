package it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.policy.elements.Policy;

/**
 * Request to create a new policy
 * 
 * REF IFA 013 v3.2.1 - 7.9.2
 * 
 * @author nextworks
 *
 */
public class TransferPolicyRequest implements InterfaceMessage {

	private String designer;
	private String name;
	private String version;
	private String pfId;
	private Policy policy;
	
	public TransferPolicyRequest() {}

	/**
	 * Constructor
	 * 
	 * @param designer human readable name of the designer of the policy
	 * @param name human readable name of the policy
	 * @param version version of the policy
	 * @param pfId Identifier of the PF which enforces the policy. The PF is either a VNFM or a VIM. Cardinality of zero indicates that the PF is the NFVO itself.
	 * @param policy policy
	 */
	public TransferPolicyRequest(String designer,
			String name,
			String version,
			String pfId,
			Policy policy) {
		this.designer = designer;
		this.name = name;
		this.version = version;
		this.pfId = pfId;
		this.policy = policy;
	}
	
	
	
	/**
	 * @return the designer
	 */
	public String getDesigner() {
		return designer;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the policy
	 */
	public Policy getPolicy() {
		return policy;
	}
	
	

	/**
	 * @return the pfId
	 */
	public String getPfId() {
		return pfId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (designer == null) throw new MalformattedElementException("Transfer policy message without designer");
		if (name == null) throw new MalformattedElementException("Transfer policy message without name");
		if (version == null) throw new MalformattedElementException("Transfer policy message without version");
		if (policy == null) throw new MalformattedElementException("Transfer policy message without policy");
	}

}
