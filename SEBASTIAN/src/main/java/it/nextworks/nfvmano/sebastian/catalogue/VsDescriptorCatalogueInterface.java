package it.nextworks.nfvmano.sebastian.catalogue;

import it.nextworks.nfvmano.libs.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.catalogue.messages.OnboardVsDescriptorRequest;
import it.nextworks.nfvmano.sebastian.catalogue.messages.QueryVsDescriptorResponse;

public interface VsDescriptorCatalogueInterface {

	/**
	 * Method to create a new VSD
	 * 
	 * @param request
	 * @return
	 * @throws MethodNotImplementedException
	 * @throws MalformattedElementException
	 * @throws AlreadyExistingEntityException
	 * @throws FailedOperationException
	 */
	public String onBoardVsDescriptor(OnboardVsDescriptorRequest request)
			throws MethodNotImplementedException, MalformattedElementException, AlreadyExistingEntityException, FailedOperationException;
	
	/**
	 * Method to request info about an existing VSD
	 * 
	 * @param request
	 * @return
	 * @throws MethodNotImplementedException
	 * @throws MalformattedElementException
	 * @throws NotExistingEntityException
	 * @throws FailedOperationException
	 */
	public QueryVsDescriptorResponse queryVsDescriptor(GeneralizedQueryRequest request) 
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
	
	/**
	 * Method to remove a VSD
	 * 
	 * @param vsDescriptorId
	 * @param tenantId
	 * @throws MethodNotImplementedException
	 * @throws MalformattedElementException
	 * @throws NotExistingEntityException
	 * @throws FailedOperationException
	 */
	public void deleteVsDescriptor(String vsDescriptorId, String tenantId)
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
}
