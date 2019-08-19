/*
* Copyright 2018 Nextworks s.r.l.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package it.nextworks.nfvmano.catalogue.blueprint.interfaces;

import it.nextworks.nfvmano.libs.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.catalogue.blueprint.elements.VsBlueprint;
import it.nextworks.nfvmano.catalogue.blueprint.messages.OnBoardVsBlueprintRequest;
import it.nextworks.nfvmano.catalogue.blueprint.messages.QueryVsBlueprintResponse;

import java.util.Optional;

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

	/**
	 * Method to retrieve the VsBlueprint element from the catalogue
	 * @param vsBlueprintId ID of the blueprint to retrieve
	 * @return the VS Blueprint element
	 */

	Optional<VsBlueprint> findByVsBlueprintId(String vsBlueprintId);


	/**
	 * Method to retrieve the first vsBlueprint matching the name and version specified
	 * @param name Name of the VS Blueprint
	 * @param version Version of the VS Blueprint
	 * @return the VS Blueprint element
	 */
	Optional<VsBlueprint> findByNameAndVersion(String name, String version);
	
}
