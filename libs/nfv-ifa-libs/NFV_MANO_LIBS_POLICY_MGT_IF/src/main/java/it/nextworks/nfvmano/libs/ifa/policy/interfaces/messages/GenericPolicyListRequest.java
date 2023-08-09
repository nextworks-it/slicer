package it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Request related to a list of policies that may need to be deleted, activated or disactivated
 * 
 * REF IFA 013 v3.2.1 - 7.9.3, 7.9.5, 7.9.6
 * 
 * @author nextworks
 *
 */
public class GenericPolicyListRequest implements InterfaceMessage {

	private List<String> policyInfoId = new ArrayList<>();
	
	public GenericPolicyListRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param policyInfoId identifier(s) of policy information to be removed
	 */
	public GenericPolicyListRequest(List<String> policyInfoId) {
		if (policyInfoId != null) this.policyInfoId = policyInfoId;
	}

	
	
	/**
	 * @return the policyInfoId
	 */
	public List<String> getPolicyInfoId() {
		return policyInfoId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if ( (policyInfoId == null) || (policyInfoId.isEmpty())) throw new MalformattedElementException("Delete policy request without policy info identifiers");
	}

}
