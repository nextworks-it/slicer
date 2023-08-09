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
package it.nextworks.nfvmano.libs.ifa.swimages.interfaces;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.swimages.interfaces.messages.AddImageRequest;
import it.nextworks.nfvmano.libs.ifa.swimages.interfaces.messages.AddImageResponse;
import it.nextworks.nfvmano.libs.ifa.swimages.interfaces.messages.QueryImagesResponse;
import it.nextworks.nfvmano.libs.ifa.swimages.interfaces.messages.UpdateImageRequest;

/**
 * This interface allows an authorized consumer functional block 
 * to manage the software images in a VIM.
 * 
 * REF IFA 006 - v2.3.1 - 7.2
 * 
 * @author nextworks
 *
 */
public interface SwImageManagementProviderInterface {

	/**
	 * This operation allows adding a new software image to the image repository 
	 * managed by the VIM.
	 *
	 * REF IFA 005 - v2.3.1 - 7.2.2
	 * 
	 * @param request request to add an image
	 * @return response with the metadata of the added sw image
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws AlreadyExistingEntityException if the sw image already exists
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public AddImageResponse addImage(AddImageRequest request)
			throws MethodNotImplementedException, AlreadyExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation allows querying the information of the software images 
	 * in the image repository managed by the VIM.
	 * 
	 * REF IFA 005 - v2.3.1 - 7.2.3-4
	 * 
	 * @param request query request
	 * @return the requested images
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the requested images do not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public QueryImagesResponse queryImages(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation enables the update of a software image in the VIM.
	 * 
	 * REF IFA 005 - v2.3.1 - 7.2.5
	 * 
	 * @param request update request
	 * @return the updated image
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the image to be updated does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public AddImageResponse updateImage(UpdateImageRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
	
	/**
	 * This operation enables the deletion of a software image from the VIM.
	 * 
	 * REF IFA 005 - v2.3.1 - 7.2.6
	 * 
	 * @param imageId The identifier of the software image to be deleted.
	 * @return The identifier of the software image successfully deleted.
	 * @throws MethodNotImplementedException if the method is not implemented
	 * @throws NotExistingEntityException if the image to be deleted does not exist
	 * @throws FailedOperationException if the operation fails
	 * @throws MalformattedElementException if the request is malformatted
	 */
	public String deleteImage(String imageId)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException;
}
