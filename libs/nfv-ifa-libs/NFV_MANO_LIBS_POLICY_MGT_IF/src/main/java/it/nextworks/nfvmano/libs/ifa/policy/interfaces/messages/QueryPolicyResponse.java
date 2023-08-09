package it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages;

import java.util.ArrayList;
import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.policy.elements.PolicyInfo;

/**
 * Response to a query policy request
 * 
 * REF IFA 013 v3.2.1 - 7.9.4
 * 
 * @author nextworks
 *
 */
public class QueryPolicyResponse implements InterfaceMessage {

	private List<PolicyInfo> queryNsPolicyInfoResult = new ArrayList<>();
	
	public QueryPolicyResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param queryNsPolicyInfoResult NFV-MANO policy information matching the input filter
	 */
	public QueryPolicyResponse(List<PolicyInfo> queryNsPolicyInfoResult) {
		if (queryNsPolicyInfoResult != null) this.queryNsPolicyInfoResult = queryNsPolicyInfoResult;
	}

	/**
	 * @return the queryNsPolicyInfoResult
	 */
	public List<PolicyInfo> getQueryNsPolicyInfoResult() {
		return queryNsPolicyInfoResult;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (queryNsPolicyInfoResult != null)
			for (PolicyInfo pi : queryNsPolicyInfoResult) pi.isValid();
	}

}
