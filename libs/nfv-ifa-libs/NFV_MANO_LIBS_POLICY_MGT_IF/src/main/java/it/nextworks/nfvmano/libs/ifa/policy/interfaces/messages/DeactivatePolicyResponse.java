package it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Response to a deactivate policy request
 * 
 * REF IFA 013 v3.2.1 - 7.9.6
 * 
 * @author nextworks
 *
 */
public class DeactivatePolicyResponse implements InterfaceMessage {

	private List<String> deactivatedPolicyInfoId = new ArrayList<>();
	
	public DeactivatePolicyResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param deactivatedPolicyInfoId identifier(s) of removed policy information
	 */
	public DeactivatePolicyResponse(List<String> deactivatedPolicyInfoId) {
		if (deactivatedPolicyInfoId != null) this.deactivatedPolicyInfoId = deactivatedPolicyInfoId;
	}

	
	

	/**
	 * @return the deactivatedPolicyInfoId
	 */
	public List<String> getDeactivatedPolicyInfoId() {
		return deactivatedPolicyInfoId;
	}

	@Override
	public void isValid() throws MalformattedElementException {	}

}
