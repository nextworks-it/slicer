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
package it.nextworks.nfvmano.libs.common.elements;

import javax.persistence.Embeddable;

import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;

/**
 * This information element provides information that allows addressing a
 * resource that is used by a VNF instance.
 * 
 * Ref. IFA 013 v2.3.1 section 8.3.3.8 Ref. IFA 007 v2.3.1 section 8.5.7
 * 
 * @author nextworks
 *
 */
@Embeddable
public class ResourceHandle implements DescriptorInformationElement {

	private String vimId;
	private String resourceProviderId;
	private String resourceId;
	private String vimLevelResourceType;

	public ResourceHandle() {
	}

	/**
	 * Constructor
	 * 
	 * @param vimId              Identifier of the VIM under whose control this
	 *                           resource is placed.
	 * @param resourceProviderId Identifies the entity responsible for the
	 *                           management of the virtualised resource.
	 * @param resourceId         Identifier of the resource in the scope of the VIM
	 *                           or the resource provider.
	 */
	public ResourceHandle(String vimId, String resourceProviderId, String resourceId) {
		this.vimId = vimId;
		this.resourceId = resourceId;
		this.resourceProviderId = resourceProviderId;
		this.vimLevelResourceType = null;
	}

	/**
	 * Constructor
	 * 
	 * @param vimId                Identifier of the VIM under whose control this
	 *                             resource is placed.
	 * @param resourceProviderId   Identifies the entity responsible for the
	 *                             management of the virtualised resource.
	 * @param resourceId           Identifier of the resource in the scope of the
	 *                             VIM or the resource provider.
	 * @param vimLevelResourceType Type of the resource in the scope of the VIM or
	 *                             the resource provider. The value set of the
	 *                             "vimLevelResourceType" attribute is within the
	 *                             scope of the VIM or the resource provider and can
	 *                             be used as information that complements the
	 *                             ResourceHandle.
	 */
	public ResourceHandle(String vimId, String resourceProviderId, String resourceId, String vimLevelResourceType) {
		this.vimId = vimId;
		this.resourceId = resourceId;
		this.resourceProviderId = resourceProviderId;
		this.vimLevelResourceType = vimLevelResourceType;
	}

	/**
	 * @return the vimId
	 */
	public String getVimId() {
		return vimId;
	}

	/**
	 * @return the resourceProviderId
	 */
	public String getResourceProviderId() {
		return resourceProviderId;
	}

	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * @return the vimLevelResourceType
	 */
	public String getVimLevelResourceType() {
		return vimLevelResourceType;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (resourceId == null)
			throw new MalformattedElementException("Resource Handle without resource ID");
		if ((vimId == null) && (resourceProviderId == null))
			throw new MalformattedElementException("Resource Handle without vimID or resource provider ID");
	}

}
