package it.nextworks.nfvmano.sebastian.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.common.elements.Filter;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.policy.elements.PolicyInfo;
import it.nextworks.nfvmano.libs.policy.enums.ActivationStatus;
import it.nextworks.nfvmano.libs.policy.interfaces.PolicyManagementProviderInterface;
import it.nextworks.nfvmano.libs.policy.interfaces.messages.ActivatePolicyResponse;
import it.nextworks.nfvmano.libs.policy.interfaces.messages.AssociatePolicyRequest;
import it.nextworks.nfvmano.libs.policy.interfaces.messages.AssociatePolicyResponse;
import it.nextworks.nfvmano.libs.policy.interfaces.messages.DeactivatePolicyResponse;
import it.nextworks.nfvmano.libs.policy.interfaces.messages.DeletePolicyResponse;
import it.nextworks.nfvmano.libs.policy.interfaces.messages.GenericPolicyListRequest;
import it.nextworks.nfvmano.libs.policy.interfaces.messages.QueryPolicyResponse;
import it.nextworks.nfvmano.libs.policy.interfaces.messages.TransferPolicyRequest;
import it.nextworks.nfvmano.sebastian.admin.repo.PolicyRepository;

/**
 * The Policy Management Service handles the management of policies.
 * It is invoked by the AdminRestController.
 *  
 * @author nextworks
 *
 */
@Service
public class PolicyManagementService implements PolicyManagementProviderInterface {

	private static final Logger log = LoggerFactory.getLogger(PolicyManagementService.class);
	
	@Autowired
	private PolicyRepository policyRepository;
	
	public PolicyManagementService() { }

	@Override
	public synchronized String transferPolicy(TransferPolicyRequest request)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException {
		log.debug("Received request to transfer a new policy");
		request.isValid();
		PolicyInfo policyInfo = new PolicyInfo(null, 
				request.getDesigner(), 
				request.getName(), 
				request.getVersion(), 
				request.getPolicy(), 
				ActivationStatus.ACTIVE);
		policyRepository.saveAndFlush(policyInfo);
		Long id = policyInfo.getId();
		String policyInfoId = String.valueOf(id);
		policyInfo.setPolicyInfoId(policyInfoId);
		policyRepository.saveAndFlush(policyInfo);
		log.debug("Transferred new policy. Assigned ID: " + policyInfoId);
		//TODO: transfer to NFVO.
		return policyInfoId;
	}

	@Override
	public synchronized DeletePolicyResponse deletePolicy(GenericPolicyListRequest request)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException {
		log.debug("Received request to delete policies");
		//TODO: in the spec it is not specified if disabled policies can be deleted or not. To be better analyzed
		//TODO: also on the NFVO?
		request.isValid();
		List<String> toBeDeleted = request.getPolicyInfoId();
		List<String> deleted = new ArrayList<>();
		for (String s : toBeDeleted) {
			log.debug("Removing policy with ID " + s);
			Optional<PolicyInfo> pOptional = policyRepository.findByPolicyInfoId(s);
			if (pOptional.isPresent()) {
				policyRepository.delete(pOptional.get());
				deleted.add(s);
				log.debug("Removed policy with ID " + s);
			} else {
				log.warn("Unable to remove policy with ID " + s + ". Policy not found.");
			}
		}
		if (deleted.isEmpty()) {
			log.debug("Unable to remove any policy!");
			throw new FailedOperationException("Unable to remove any policy");
		}
		if (deleted.size() != toBeDeleted.size()) log.warn("Some policies have not been removed");
		return new DeletePolicyResponse(deleted);
	}

	@Override
	public synchronized QueryPolicyResponse queryPolicy(GeneralizedQueryRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		
		log.debug("Received policy query request");
		request.isValid();
		
		//At the moment the only filters accepted are:
		//1. Policy info ID
		//2. Status
		//3. empty filter
		//No attribute selector is supported at the moment
		
		Filter filter = request.getFilter();
		List<String> attributeSelector = request.getAttributeSelector();
		
		List<PolicyInfo> pInfos = new ArrayList<>();
		
		if ((attributeSelector == null) || (attributeSelector.isEmpty())) {
			Map<String,String> fp = filter.getParameters();
			if (fp.size()==0) {
				log.debug("Received query for all the policies.");
				pInfos = policyRepository.findAll();
			} else if (fp.size()==1 && fp.containsKey("POLICY_ID")) {
				String policyId = fp.get("POLICY_ID");
				log.debug("Received query for policy with ID " + policyId);
				PolicyInfo policy = getPolicyInfo(policyId);
				log.debug("Found policy with ID " + policyId);
				pInfos.add(policy);
			} else if (fp.size()==1 && fp.containsKey("POLICY_STATUS")) {
				String policyStatus = fp.get("POLICY_STATUS");
				log.debug("Received query for policies with status " + policyStatus);
				if (policyStatus.equalsIgnoreCase("ACTIVE")) {
					List<PolicyInfo> f = policyRepository.findByActivationStatus(ActivationStatus.ACTIVE);
					pInfos.addAll(f);
				} else if (policyStatus.equalsIgnoreCase("NOT_ACTIVE")) {
					List<PolicyInfo> f = policyRepository.findByActivationStatus(ActivationStatus.NOT_ACTIVE);
					pInfos.addAll(f);
				} else throw new MalformattedElementException("Received policy query with wrong policy status");
			} else {
				log.error("Received policy query with not supported filter.");
				throw new MalformattedElementException("Received policy query with not supported filter.");
			}
		} else {
			log.error("Received policy query with attribute selector. Not supported at the moment.");
			throw new MethodNotImplementedException("Received policy query with attribute selector. Not supported at the moment.");
		}
		
		return new QueryPolicyResponse(pInfos);
	}
	
	private PolicyInfo getPolicyInfo(String policyInfoId) throws NotExistingEntityException {
		log.debug("Retrieving policy with ID " + policyInfoId);
		Optional<PolicyInfo> pOpt = policyRepository.findByPolicyInfoId(policyInfoId);
		if (pOpt.isPresent()) return pOpt.get();
		else throw new NotExistingEntityException("Policy info with ID " + policyInfoId + " not found.");
	}

	@Override
	public synchronized ActivatePolicyResponse activatePolicy(GenericPolicyListRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		log.debug("Received request to activate policies");
		request.isValid();
		List<String> policyIds = request.getPolicyInfoId();
		List<String> ret = new ArrayList<>();
		for (String s : policyIds) {
			try {
				PolicyInfo policyInfo = getPolicyInfo(s);
				if (policyInfo.getActivationStatus() == ActivationStatus.NOT_ACTIVE) {
					policyInfo.setActivationStatus(ActivationStatus.ACTIVE);
					policyRepository.saveAndFlush(policyInfo);
					ret.add(s);
					log.debug("Activated policy " + s);
				} else log.warn("Impossible to activate policy " + s + ": already active");
			} catch (NotExistingEntityException e) {
				log.warn("Impossible to activate policy " + s + ": not existing");
			}
		}
		return new ActivatePolicyResponse(ret);
	}

	@Override
	public synchronized DeactivatePolicyResponse deactivatePolicy(GenericPolicyListRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException,
			MalformattedElementException {
		log.debug("Received request to deactivate policies");
		request.isValid();
		List<String> policyIds = request.getPolicyInfoId();
		List<String> ret = new ArrayList<>();
		for (String s : policyIds) {
			try {
				PolicyInfo policyInfo = getPolicyInfo(s);
				if (policyInfo.getActivationStatus() == ActivationStatus.ACTIVE) {
					policyInfo.setActivationStatus(ActivationStatus.NOT_ACTIVE);
					policyRepository.saveAndFlush(policyInfo);
					ret.add(s);
					log.debug("Deactivated policy " + s);
				} else log.warn("Impossible to deactivate policy " + s + ": already not active");
			} catch (NotExistingEntityException e) {
				log.warn("Impossible to deactivate policy " + s + ": not existing");
			}
		}
		return new DeactivatePolicyResponse(ret);
	}

	@Override
	public AssociatePolicyResponse associatePolicy(AssociatePolicyRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		throw new MethodNotImplementedException("This method is supported only on the NBI of the NFVO");
	}

	@Override
	public AssociatePolicyResponse disassociatePolicy(AssociatePolicyRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException,
			MalformattedElementException {
		throw new MethodNotImplementedException("This method is supported only on the NBI of the NFVO");
	}

}
