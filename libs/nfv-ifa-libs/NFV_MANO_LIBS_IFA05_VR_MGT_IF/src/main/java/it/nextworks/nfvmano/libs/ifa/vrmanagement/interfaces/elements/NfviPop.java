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




import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * The NfviPop information element contains basic data to identify 
 * an NFVI-PoP in a VIM. 
 * It provides geographic location information of the NFVI resources 
 * that the VIM manages, as well as other attributes which help consumer
 * functional blocks build topological information relative to NFVI-PoP 
 * connectivity to other NFVI-PoP or N-PoP.
 * 
 * REF IFA 005 v2.3.1 - 8.10.3
 * 
 * 
 * @author nextworks
 *
 */
public class NfviPop implements InterfaceInformationElement {

	private String nfviPopId;
	private String vimId;
	private String geographicalLocationInfo;
	private String networkConnectivityEndpoint;	//The format is not specified
	
	public NfviPop() {	}
	
	/**
	 * Constructor
	 * 
	 * @param nfviPopId Identification of the NFVI-PoP.
	 * @param vimId Identification of the VIM.
	 * @param geographicalLocationInfo It provides information about the geographic location of the NFVI resources that the VIM manages.
	 * @param networkConnectivityEndpoint Information about network connectivity endpoints to the NFVI-PoP that the VIM manages which helps build topology information relative to NFVI-PoP connectivity to other NFVI-PoP or N-PoP.
	 */
	public NfviPop(String nfviPopId,
			String vimId,
			String geographicalLocationInfo, 
			String networkConnectivityEndpoint) {
		this.nfviPopId = nfviPopId;
		this.vimId = vimId;
		this.geographicalLocationInfo = geographicalLocationInfo;
		this.networkConnectivityEndpoint = networkConnectivityEndpoint;
	}

	
	
	/**
	 * @return the nfviPopId
	 */
	public String getNfviPopId() {
		return nfviPopId;
	}

	/**
	 * @return the vimId
	 */
	public String getVimId() {
		return vimId;
	}

	/**
	 * @return the geographicalLocationInfo
	 */
	public String getGeographicalLocationInfo() {
		return geographicalLocationInfo;
	}

	/**
	 * @return the networkConnectivityEndpoint
	 */
	public String getNetworkConnectivityEndpoint() {
		return networkConnectivityEndpoint;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (nfviPopId == null) throw new MalformattedElementException("NFVI PoP without ID");
		if (vimId == null) throw new MalformattedElementException("NFVI PoP without VIM ID");
		if (geographicalLocationInfo == null) throw new MalformattedElementException("NFVI PoP without location");
		if (networkConnectivityEndpoint == null) throw new MalformattedElementException("NFVI PoP without endpoint");
	}

}
