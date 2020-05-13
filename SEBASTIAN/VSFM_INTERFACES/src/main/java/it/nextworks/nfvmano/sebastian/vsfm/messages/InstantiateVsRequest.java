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
package it.nextworks.nfvmano.sebastian.vsfm.messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.LocationInfo;
import it.nextworks.nfvmano.sebastian.record.elements.ImsiInfo;

/**
 * Request to instantiate a new Vertical Service instance.
 * 
 * @author nextworks
 *
 */
public class InstantiateVsRequest implements InterfaceMessage {

	private String name;
	private String description;
	private String vsdId;
	private String tenantId;
	private String notificationUrl;
	private Map<String, String> userData = new HashMap<>();

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<LocationInfo> locationsConstraints;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<ImsiInfo> imsiInfoList;
	public InstantiateVsRequest() {	}
	
	
	/**
	 * Constructor
	 * 
	 * @param name Name of the Vertical Service instance
	 * @param description Description of the Vertical Service instance 
	 * @param vsdId ID of the VSD to be used to instantiate the Vertical Service instance
	 * @param tenantId ID of the tenant instantiating the Vertical Service instance
	 * @param notificationUrl URL where the tenant wants to receive notifications about the status of or events related to the Vertical Service instance.
	 * @param userData Additional data, like config parameters provided by the vertical
	 * @param locationsConstraints constraints about the geographical coverage of the service
	 */
	public InstantiateVsRequest(String name, String description, String vsdId, String tenantId, String notificationUrl,
			Map<String, String> userData, List<LocationInfo> locationsConstraints, List<ImsiInfo> imsiInfoList) {
		this.name = name;
		this.description = description;
		this.vsdId = vsdId;
		this.tenantId = tenantId;
		this.notificationUrl = notificationUrl;
		if (userData != null) this.userData = userData;
		this.locationsConstraints=locationsConstraints;
		if(imsiInfoList!=null)  this.imsiInfoList = imsiInfoList;
	}

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @return the vsdId
	 */
	public String getVsdId() {
		return vsdId;
	}


	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}



	/**
	 * @return the notificationUrl
	 */
	public String getNotificationUrl() {
		return notificationUrl;
	}


	/**
	 * @return the userData
	 */
	public Map<String, String> getUserData() {
		return userData;
	}

	
	/**
	 * @return the locationsConstraints
	 */
	public List<LocationInfo> getLocationsConstraints() {
		return locationsConstraints;
	}

	public List<ImsiInfo> getImsiInfoList() {
		return imsiInfoList;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (name == null) throw new MalformattedElementException("Instantiate VS request without VS instance name");
		if (description == null) throw new MalformattedElementException("Instantiate VS request without VS instance description");
		if (vsdId == null) throw new MalformattedElementException("Instantiate VS request without VSD ID");
		//if (tenantId == null) throw new MalformattedElementException("Instantiate VS request without tenant ID");
		if(locationsConstraints!=null){
			for(LocationInfo locationInfo: locationsConstraints){
				locationInfo.isValid();
			}
		}
	}

}
