package it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Response to a delete policy request
 * 
 * REF IFA 013 v3.2.1 - 7.9.3
 * 
 * @author nextworks
 *
 */
public class DeletePolicyResponse implements InterfaceMessage {

	private List<String> deletedPolicyInfoId = new ArrayList<>();
	
	public DeletePolicyResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param deletedPolicyInfoId identifier(s) of removed policy information
	 */
	public DeletePolicyResponse(List<String> deletedPolicyInfoId) {
		if (deletedPolicyInfoId != null) this.deletedPolicyInfoId = deletedPolicyInfoId;
	}

	
	
	/**
	 * @return the policyInfoId
	 */
	public List<String> getDeletedPolicyInfoId() {
		return deletedPolicyInfoId;
	}

	@Override
	public void isValid() throws MalformattedElementException {	}

}
