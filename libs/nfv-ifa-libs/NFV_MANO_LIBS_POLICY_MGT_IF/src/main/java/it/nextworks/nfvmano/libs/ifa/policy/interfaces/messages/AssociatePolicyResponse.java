package it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Response to an associate or disassociate policy request.
 * 
 * REF IFA 013 v3.2.1 - 7.9.11, 7.9.12
 * 
 * @author nextworks
 *
 */
public class AssociatePolicyResponse implements InterfaceMessage {

	private List<String> nsInstanceId = new ArrayList<>();
	
	public AssociatePolicyResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param nsInstanceId Identifier(s) of the NS instance(s) to/from which the policy has been associated/disassociated
	 */
	public AssociatePolicyResponse(List<String> nsInstanceId) { 
		if (nsInstanceId != null) this.nsInstanceId = nsInstanceId;
	}

	
	
	/**
	 * @return the nsInstanceId
	 */
	public List<String> getNsInstanceId() {
		return nsInstanceId;
	}

	@Override
	public void isValid() throws MalformattedElementException {	}

}
