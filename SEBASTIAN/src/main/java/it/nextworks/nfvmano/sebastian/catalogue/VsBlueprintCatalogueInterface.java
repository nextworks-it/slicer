package it.nextworks.nfvmano.sebastian.catalogue;

import it.nextworks.nfvmano.libs.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.catalogue.messages.OnBoardVsBlueprintRequest;
import it.nextworks.nfvmano.sebastian.catalogue.messages.QueryVsBlueprintResponse;

public interface VsBlueprintCatalogueInterface {

	/**
	 * Method to create a new VS Blueprint, including all the corresponding elements on the service orchestrator side.
	 * 
	 * @param request VS Blueprint creation request
	 * @return the ID of the VS Blueprint
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws MalformattedElementException if the request is malformatted
	 * @throws AlreadyExistingEntityException if the VS blueprint already exists
	 * @throws FailedOperationException if the operation fails
	 */
	public String onBoardVsBlueprint(OnBoardVsBlueprintRequest request)
			throws MethodNotImplementedException, MalformattedElementException, AlreadyExistingEntityException, FailedOperationException;
	
	/**
	 * Method to retrieve a VS Blueprint from the catalogue.
	 * 
	 * @param request query
	 * @return the VS blueprint info
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws MalformattedElementException if the request is malformatted
	 * @throws NotExistingEntityException if the VS blueprint does not exist
	 * @throws FailedOperationException if the operation fails
	 */
	public QueryVsBlueprintResponse queryVsBlueprint(GeneralizedQueryRequest request) 
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
	
	/**
	 * Method to delete a VS blueprint from the catalogue
	 * 
	 * @param vsBlueprintId ID of the VS blueprint to be removed
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws MalformattedElementException if the ID is malformatted
	 * @throws NotExistingEntityException if the VS blueprint does not exist
	 * @throws FailedOperationException if the operation fails
	 */
	public void deleteVsBlueprint(String vsBlueprintId)
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException;
	
}
