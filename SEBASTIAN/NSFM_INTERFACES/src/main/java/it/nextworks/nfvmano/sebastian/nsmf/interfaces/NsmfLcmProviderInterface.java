/*
 * Copyright (c) 2019 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.sebastian.nsmf.interfaces;

import java.util.List;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotPermittedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.*;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;

/**
 * Interface used to model the NBI of the Network Slice Management Function for the lifecycle management
 * of network slices. 
 * 
 * This interface should be implemented on the server side by the NSMF and should be invoked by upper layer
 * clients like, e.g. the Vertical Service Management Function. 
 * 
 * If NSMF and VSMF are de-coupled and they interact via REST API, 
 * the NSMF should implement a REST controller invoking this interface, while
 * the VSMF should implement a REST client implementing this interface. 
 * 
 * @author nextworks
 *
 */
public interface NsmfLcmProviderInterface {

	/**
	 * Method to create the ID of a new network slice instance.
	 * The method does not instantiate the network slice, but it just allocate a new entry in the system.
	 * 
	 * @param request request with the details of the network slice instance to be created
	 * @param tenantId ID of the tenant owning the network slice
	 * @return ID created for the network slice instance
	 * @throws NotExistingEntityException if the given network slice template is not found
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformed
	 * @throws NotPermittedOperationException if the operation is not permitted for the given tenant
	 */
	String createNetworkSliceIdentifier(CreateNsiIdRequest request, String domainId, String tenantId)
			throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException;

	/**
	 * Method to instantiate a network slice instance. The related ID must have been previously created.
	 *
	 * @param request request with the details of the instantiation
	 * @param tenantId ID of the tenant requesting the instantiation
	 * @throws NotExistingEntityException if the network slice ID is not found
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformed
	 * @throws NotPermittedOperationException if the operation is not permitted for the given user or for the current status
	 */
	void instantiateNetworkSlice(InstantiateNsiRequest request, String domainId, String tenantId)
			throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException;

	/**
	 * Method to modify a network slice instance, providing a new <NFV NS DF; NFV NS IL> pair for the scaling 
	 * of the associated NFV Network Service.
	 * 
	 * @param request request with the details of the modification
	 * @param tenantId ID of the tenant requesting the modification
	 * @throws NotExistingEntityException if one of the elements in the request are not found
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformed
	 * @throws NotPermittedOperationException if the operation is not permitted for the given tenant or for the current status
	 */
	void modifyNetworkSlice(ModifyNsiRequest request, String domainId, String tenantId)
			throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException;
	
	/**
	 * Method to terminate a network slice instance.
	 * 
	 * @param request request with the details of the termination
	 * @param tenantId ID of the tenant requesting the termination
	 * @throws NotExistingEntityException if the network slice instance is not found
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformed
	 * @throws NotPermittedOperationException if the operation is not permitted for the given tenant or for the current status
	 */
	void terminateNetworkSliceInstance(TerminateNsiRequest request, String domainId, String tenantId)
			throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException;
	
	/**
	 * Method to query network slice instances
	 * 
	 * @param request
	 * @param tenantId ID of the tenant issuing the query
	 * @return list of network slice instances matching the filter in the request
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the request fails
	 * @throws MalformattedElementException if the query is malformed or the filter parameters are not acceptable
	 */
	List<NetworkSliceInstance> queryNetworkSliceInstance(GeneralizedQueryRequest request, String domainId, String tenantId)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;

	/**
	 * Method to configure slice instances
	 *
	 * @param request
	 * @param tenantId ID of the tenant requesting the configuration
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws FailedOperationException if the request fails
	 */
    void configureNetworkSliceInstance(ConfigureNsiRequest request, String domainId, String tenantId) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException;
}
