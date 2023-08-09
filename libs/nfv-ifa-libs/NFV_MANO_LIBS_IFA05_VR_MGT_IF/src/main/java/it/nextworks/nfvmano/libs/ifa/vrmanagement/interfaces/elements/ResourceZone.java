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
package it.nextworks.nfvmano.libs.ifa.vrmanagement.interfaces.elements;

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The ResourceZone information element contains information about the Resource Zone.
 * 
 * REF IFA 005 v2.3.1 - 8.10.2
 * 
 * @author nextworks
 *
 */
public class ResourceZone implements InterfaceInformationElement {

	private String zoneId;
	private String zoneName;
	private String zoneState;
	private String nfviPopId;
	private Map<String, String> zoneProperty = new HashMap<String, String>();
	private Map<String, String> metadata = new HashMap<String, String>();
	
	public ResourceZone() {	}
	
	/**
	 * Constructor
	 * 
	 * @param zoneId The identifier of the Resource Zone.
	 * @param zoneName The name of the Resource Zone.
	 * @param zoneState Information about the current state of the Resource Zone, e.g. if the Resource Zone is available.
	 * @param nfviPopId The identifier of the NFVI-PoP the Resource Zone belongs to.
	 * @param zoneProperty Set of properties that define the capabilities associated to the Resource Zone.
	 * @param metadata Other metadata associated to the Resource Zone.
	 */
	public ResourceZone(String zoneId,
			String zoneName,
			String zoneState,
			String nfviPopId, 
			Map<String, String> zoneProperty,
			Map<String, String> metadata) {	
		this.zoneId = zoneId;
		this.zoneName = zoneName;
		this.zoneState = zoneState;
		this.nfviPopId = nfviPopId;
		if (zoneProperty != null) this.zoneProperty = zoneProperty;
		if (metadata != null) this.metadata = metadata;
	}
	
	

	/**
	 * @return the zoneId
	 */
	public String getZoneId() {
		return zoneId;
	}

	/**
	 * @return the zoneName
	 */
	public String getZoneName() {
		return zoneName;
	}

	/**
	 * @return the zoneState
	 */
	public String getZoneState() {
		return zoneState;
	}

	/**
	 * @return the nfviPopId
	 */
	public String getNfviPopId() {
		return nfviPopId;
	}

	/**
	 * @return the zoneProperty
	 */
	public Map<String, String> getZoneProperty() {
		return zoneProperty;
	}

	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (zoneId == null) throw new MalformattedElementException("Resource zone without ID");
		if (zoneName == null) throw new MalformattedElementException("Resource zone without name");
		if (zoneState == null) throw new MalformattedElementException("Resource zone without state");
		if (nfviPopId == null) throw new MalformattedElementException("Resource zone without NFVI POP ID");
		if ((zoneProperty == null) || (zoneProperty.isEmpty())) throw new MalformattedElementException("Resource zone without property");
	}

}
