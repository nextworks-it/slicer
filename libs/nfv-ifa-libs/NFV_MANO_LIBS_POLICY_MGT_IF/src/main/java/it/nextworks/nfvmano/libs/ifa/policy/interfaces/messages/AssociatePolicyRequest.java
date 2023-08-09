package it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Request to associate or disassociate a MANO policy to one or multiple NS instances in the NFVO.
 * 
 * REF IFA 013 v3.2.1 - 7.9.11, 7.9.12
 * 
 * @author nextworks
 *
 */
public class AssociatePolicyRequest implements InterfaceMessage {

	private String policyInfoId;
	private List<String> nsInstanceId = new ArrayList<>();
	
	public AssociatePolicyRequest() { }
	
	/**
	 * Constructor
	 * 
	 * @param policyInfoId Identifier of policy information
	 * @param nsInstanceId Identifier(s) of the NS instance(s) to associate/disassociate the policy to/from.
	 */
	public AssociatePolicyRequest(String policyInfoId,
			List<String> nsInstanceId) {
		this.policyInfoId = policyInfoId;
		if (nsInstanceId != null) this.nsInstanceId = nsInstanceId;
	}
	
	/**
	 * @return the policyInfoId
	 */
	public String getPolicyInfoId() {
		return policyInfoId;
	}

	/**
	 * @return the nsInstanceId
	 */
	public List<String> getNsInstanceId() {
		return nsInstanceId;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (policyInfoId == null) throw new MalformattedElementException("Associate policy info request without policy info ID");
		if ((nsInstanceId == null) || (nsInstanceId.isEmpty())) throw new MalformattedElementException("Associate policy info request without NS instance IDs"); 
	}

}
