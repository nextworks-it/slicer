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
package it.nextworks.nfvmano.sebastian.arbitrator;

import java.util.HashMap;
import java.util.Map;

/**
 * This class models a response from the arbitrator.
 * 
 * The arbitrator decides the following:
 * 1. if the request for the new VS instance can be satisfied, based on 
 * the SLA
 * 2. if the new VS instance must be mapped on new network slices or
 * existing ones (at the moment only new network slices are supported)
 * 2.a. in case of re-usage of existing slices, if and how they need to be
 * modified (future feature)
 * 3. if existing VS instances needs to be scaled down (future feature)
 * 
 * @author nextworks
 *
 */
public class ArbitratorResponse {

	//indicates the request matched by this response
	private String requestId;
	
	//indicates if the request can be accommodated
	private boolean acceptableRequest;
	
	//indicates if a new slice is required to accommodate the whole VS 
	private boolean newSliceRequired;
	
	//if the parameter "newSliceRequired" is false, it indicates which existing Network Slice must be used 
	private String existingCompositeSlice;
	
	//if the parameter "newSliceRequired" is false, it indicates if the existing Network Slice must be updated
	//in this case, the request should go back to the translator, to get the NSD of the composite service
	private boolean existingCompositeSliceToUpdate;
	
	//in case of a composite slice with shared slice subnets, the key indicates the slice ID and the value indicates if the slice must be updated
	//the subnets not included in this map needs to be created from scratch
	private Map<String, Boolean> existingSliceSubnets = new HashMap<>();
	
	//in case of existing VS instances to be scaled down, 
	//the key indicates the vertical service instance ID and the value the ID of the target VSD
	private Map<String, String> impactedVerticalServiceInstances = new HashMap<>();

	
	
	/**
	 * @param requestId
	 * @param acceptableRequest
	 * @param newSliceRequired
	 * @param existingCompositeSlice
	 * @param existingCompositeSliceToUpdate
	 * @param existingSliceSubnets
	 * @param impactedVerticalServiceInstances
	 */
	public ArbitratorResponse(String requestId, boolean acceptableRequest, boolean newSliceRequired, String existingCompositeSlice,
			boolean existingCompositeSliceToUpdate, Map<String, Boolean> existingSliceSubnets,
			Map<String, String> impactedVerticalServiceInstances) {
		this.requestId = requestId;
		this.acceptableRequest = acceptableRequest;
		this.newSliceRequired = newSliceRequired;
		this.existingCompositeSlice = existingCompositeSlice;
		this.existingCompositeSliceToUpdate = existingCompositeSliceToUpdate;
		if (existingSliceSubnets != null) this.existingSliceSubnets = existingSliceSubnets;
		if (impactedVerticalServiceInstances != null) this.impactedVerticalServiceInstances = impactedVerticalServiceInstances;
	}

	
	
	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}



	/**
	 * @return the acceptableRequest
	 */
	public boolean isAcceptableRequest() {
		return acceptableRequest;
	}

	/**
	 * @return the newSliceRequired
	 */
	public boolean isNewSliceRequired() {
		return newSliceRequired;
	}

	/**
	 * @return the existingCompositeSlice
	 */
	public String getExistingCompositeSlice() {
		return existingCompositeSlice;
	}

	/**
	 * @return the existingCompositeSliceToUpdate
	 */
	public boolean isExistingCompositeSliceToUpdate() {
		return existingCompositeSliceToUpdate;
	}

	/**
	 * @return the existingSliceSubnets
	 */
	public Map<String, Boolean> getExistingSliceSubnets() {
		return existingSliceSubnets;
	}

	/**
	 * @return the impactedVerticalServiceInstances
	 */
	public Map<String, String> getImpactedVerticalServiceInstances() {
		return impactedVerticalServiceInstances;
	}
	
	
	
}
