package it.nextworks.nfvmano.libs.ifa.policy.interfaces;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages.ActivatePolicyResponse;
import it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages.AssociatePolicyRequest;
import it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages.AssociatePolicyResponse;
import it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages.DeactivatePolicyResponse;
import it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages.DeletePolicyResponse;
import it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages.GenericPolicyListRequest;
import it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages.QueryPolicyResponse;
import it.nextworks.nfvmano.libs.ifa.policy.interfaces.messages.TransferPolicyRequest;

/**
 * This interface allows to invoke policy management operations towards the NFVO.
 * It supports the following features:
 * 	Transfer policy
 *  Delete policy
 *  Query policy
 *  Activate policy
 *  Deactivate policy
 *  Associate policy
 *  Disassociate policy
 * 
 * It must be implemented by the core of the NFVO and invoked by the
 * plugins who manage the NFVO external interface (e.g. a REST Controller)
 * 
 * REF IFA 013 v3.2.1 - 7.9
 * 
 * @author nextworks
 *
 */
public interface PolicyManagementProviderInterface {

	/**
	 * This method allows to transfer a new policy on the NFVO.
	 * 
	 * REF IFA 013 v3.2.1 - 7.9.2
	 * 
	 * @param request request to create a new policy
	 * @return the ID of the policy
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String transferPolicy(TransferPolicyRequest request) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation enables the OSS/BSS to delete one or multiple NFV-MANO policy(ies) from the NFVO.
	 * 
	 * REF IFA 013 v3.2.1 - 7.9.3
	 * 
	 * @param request request to remove one or more policies
	 * @return the list of removed policies
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public DeletePolicyResponse deletePolicy(GenericPolicyListRequest request) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation enables the OSS/BSS to query the information from the NFVO on one 
	 * or multiple NFV-MANO policy(ies)
	 * 
	 * REF IFA 013 v3.2.1 - 7.9.4
	 * 
	 * @param request query for one or more policies
	 * @return the list of policies matching the filter in the query
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the policy does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public QueryPolicyResponse queryPolicy(GeneralizedQueryRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation enables the OSS/BSS to activate one or multiple NFV-MANO policy(ies) in the NFVO.
	 * 
	 * REF IFA 013 v3.2.1 - 7.9.5
	 * 
	 * @param request request to activate one or more policies
	 * @return the list of activated policies 
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the policy does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public ActivatePolicyResponse activatePolicy(GenericPolicyListRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation enables the OSS/BSS to deactivate one or multiple NFV-MANO policy(ies) in the NFVO.
	 * 
	 * REF IFA 013 v3.2.1 - 7.9.6
	 * 
	 * @param request request to deactivate one or more policies
	 * @return the list of deactivated policies
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the policy does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public DeactivatePolicyResponse deactivatePolicy(GenericPolicyListRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation enables the OSS/BSS to associate a MANO policy to one or multiple NS instances in the NFVO.
	 * 
	 * REF IFA 013 v3.2.1 - 7.9.11
	 * 
	 * @param request request to associate a policy to a NS instance
	 * @return the list of NS instances to which the policy has been associated
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the policy or the NS instance does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public AssociatePolicyResponse associatePolicy(AssociatePolicyRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation enables the OSS/BSS to disassociate a MANO policy from one or multiple NS instances in the NFVO.
	 * 
	 * REF IFA 013 v3.2.1 - 7.9.12
	 * 
	 * @param request request to disassociate a policy from a NS instance
	 * @return the list of NS instances from which the policy has been disassociated
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the policy or the NS instance does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public AssociatePolicyResponse disassociatePolicy(AssociatePolicyRequest request) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
}
