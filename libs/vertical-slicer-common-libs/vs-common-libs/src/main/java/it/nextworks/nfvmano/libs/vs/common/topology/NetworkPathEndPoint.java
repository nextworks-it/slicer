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
package it.nextworks.nfvmano.libs.vs.common.topology;

public class NetworkPathEndPoint {

	private EndPointType endPointType;
	
	private String vnfdId;	
	private int vnfIndex;	
	private String vnfcId;	
	private int vnfcIndex;	
	
	private String cpdId;
	
	private String vldId;
	
	public NetworkPathEndPoint() {
		// JPA only
	}
	
	/**
	 * Constructor for a network path end point for a VNFC connection point
	 * 
	 * @param vnfdId VNFD of the VNF
	 * @param vnfIndex index of the VNF within the NS
	 * @param vnfcId ID of the VNFC within the VNF
	 * @param vnfcIndex index of the VNFC within the VNF
	 * @param cpdId ID of the connection point within the VNFC
	 */
	public NetworkPathEndPoint(String vnfdId, 
			int vnfIndex,
			String vnfcId,
			int vnfcIndex,
			String cpdId) {
		this.endPointType = EndPointType.VNFC_CP;
		this.vnfdId = vnfdId;
		this.vnfIndex = vnfIndex;
		this.vnfcId = vnfcId;
		this.vnfcIndex = vnfcIndex;
		this.cpdId = cpdId;
	}
	
	/***
	 * Constructor for a network path end point for an NS SAP
	 * 
	 * @param sapId ID of the SAP in the NSD
	 * @param vldId ID of the descriptor of the VL the SAP is attached to
	 */
	public NetworkPathEndPoint(String sapId, String vldId) {
		this.endPointType = EndPointType.NS_SAP;
		this.cpdId = sapId;
		this.vldId = vldId;
	}

	/**
	 * @return the endPointType
	 */
	public EndPointType getEndPointType() {
		return endPointType;
	}

	/**
	 * @return the vnfdId
	 */
	public String getVnfdId() {
		return vnfdId;
	}

	/**
	 * @return the vnfIndex
	 */
	public int getVnfIndex() {
		return vnfIndex;
	}

	/**
	 * @return the vnfcId
	 */
	public String getVnfcId() {
		return vnfcId;
	}

	/**
	 * @return the vnfcIndex
	 */
	public int getVnfcIndex() {
		return vnfcIndex;
	}

	/**
	 * @return the cpdId
	 */
	public String getCpdId() {
		return cpdId;
	}

	/**
	 * @return the vldId
	 */
	public String getVldId() {
		return vldId;
	}
	
	
	
	

}
