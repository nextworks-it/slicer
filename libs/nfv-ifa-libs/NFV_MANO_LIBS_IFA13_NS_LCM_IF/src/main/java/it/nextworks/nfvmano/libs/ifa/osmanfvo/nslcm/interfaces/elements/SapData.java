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
package it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

import java.util.HashMap;
import java.util.Map;

/**
 * The SapData information element defines information related to a SAP of an NS.
 * Ref. IFA 013 v2.3.1 section 8.3.4.2
 * 
 * @author nextworks
 *
 */
public class SapData implements InterfaceInformationElement {

	private String sapdId; 
	private String sapName;
	private String description;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String address;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocationInfo locationInfo;


	//OUT OF THE STANDARD
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("slice_parameters")
	private Map<String, Object> sliceParameters= new HashMap<>();


	
	public SapData() {	}
	
	/**
	 * Constructor
	 * 
	 * @param sapdId	Reference to the SAPD for this SAP.
	 * @param sapName Human readable name for the SAP.
	 * @param description Human readable description for the SAP.
	 * @param address Address for this SAP.
	 * @param locationInfo Location for this SAP.
	 */
	public SapData(String sapdId, String sapName, String description, String address, LocationInfo locationInfo) {
		this.sapdId = sapdId;
		this.sapName = sapName;
		this.description = description;
		this.address = address;
		this.locationInfo = locationInfo;  //This is an extensions to the standard
	}

	/**
	 * Constructor
	 * 
	 * @param sapdId	Reference to the SAPD for this SAP.
	 * @param sapName Human readable name for the SAP.
	 * @param description Human readable description for the SAP.
	 * @param address Address for this SAP.
	 * @param locationInfo Location for this SAP.
	 * @param sliceParameters Slice Parameters
	 */
	public SapData(String sapdId, String sapName, String description, String address, LocationInfo locationInfo, Map<String, Object> sliceParameters) {
		this.sapdId = sapdId;
		this.sapName = sapName;
		this.description = description;
		this.address = address;
		this.locationInfo = locationInfo;  //This is an extensions to the standard
		if(sliceParameters!=null) this.sliceParameters= sliceParameters;
	}

	public Map<String, Object> getSliceParameters() {
		return sliceParameters;
	}
	/**
	 * @return the sapId
	 */
	public String getSapdId() {
		return sapdId;
	}

	/**
	 * @return the sapName
	 */
	public String getSapName() {
		return sapName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the locationInfo
	 */
	public LocationInfo getLocationInfo() { return locationInfo; }

	@Override
	public void isValid() throws MalformattedElementException {
		if (this.sapdId == null) throw new MalformattedElementException("SAPD without SAP ID");
		if (this.sapName == null) throw new MalformattedElementException("SAPD without SAP name");
		if (this.description == null) throw new MalformattedElementException("SAPD without description");
	}

}
