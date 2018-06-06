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
package it.nextworks.nfvmano.sebastian.vsnbi.messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.nextworks.nfvmano.libs.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.records.nsinfo.SapInfo;
import it.nextworks.nfvmano.libs.records.vnfinfo.VnfExtCpInfo;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceStatus;

/**
 * Query about an existing Vertical Service instance.
 * 
 * 
 * @author nextworks
 *
 */
public class QueryVsResponse implements InterfaceMessage {

	private String vsiId;
	private String name;
	private String description;
	private String vsdId;
	private VerticalServiceStatus status;
	private String errorMessage;
	private List<SapInfo> externalInterconnections = new ArrayList<>();
	
	//Key: VNF instance ID; Value: VNF external CPs
	private Map<String, List<VnfExtCpInfo>> internalInterconnections = new HashMap<>();
	
	public QueryVsResponse() { }
	
	/**
	 * Constructor
	 * 
	 * @param vsiId ID of the Vertical Service instance
	 * @param name Name of the Vertical Service instance
	 * @param description Description of the Vertical Service instance
	 * @param vsdId ID of the VSD
	 * @param status Status of the Vertical Service instance
	 * @param externalInterconnections external connection points
	 * @param internalInterconnections internal connection points
	 */
	public QueryVsResponse(String vsiId, String name, String description, String vsdId, VerticalServiceStatus status,
			List<SapInfo> externalInterconnections, Map<String, List<VnfExtCpInfo>> internalInterconnections, String errorMessage) {
		this.vsiId = vsiId;
		this.name = name;
		this.description = description;
		this.vsdId = vsdId;
		this.status = status;
		if (externalInterconnections != null) this.externalInterconnections = externalInterconnections;
		if (internalInterconnections != null) this.internalInterconnections = internalInterconnections;
		this.errorMessage = errorMessage;
	}

	

	/**
	 * @return the vsiId
	 */
	public String getVsiId() {
		return vsiId;
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
	 * @return the status
	 */
	public VerticalServiceStatus getStatus() {
		return status;
	}

	/**
	 * @return the externalInterconnections
	 */
	public List<SapInfo> getExternalInterconnections() {
		return externalInterconnections;
	}

	/**
	 * @return the internalInterconnections
	 */
	public Map<String, List<VnfExtCpInfo>> getInternalInterconnections() {
		return internalInterconnections;
	}

	
	
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (vsiId == null) throw new MalformattedElementException("Query VS response without VS ID");
		if (vsdId == null) throw new MalformattedElementException("Query VS response without VSD ID");
		if (name == null) throw new MalformattedElementException("Query VS response without VS name");
		for (SapInfo si : externalInterconnections) si.isValid();
	}

}
