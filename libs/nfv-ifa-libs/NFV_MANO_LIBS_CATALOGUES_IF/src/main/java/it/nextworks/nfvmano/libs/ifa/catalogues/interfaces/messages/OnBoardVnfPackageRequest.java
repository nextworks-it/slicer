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
package it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages;

import java.util.HashMap;
import java.util.Map;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Request to on board a VNF package in the NFVO
 * 
 * REF IFA 013 v2.3.1 - 7.7.2
 * 
 * @author nextworks
 *
 */
public class OnBoardVnfPackageRequest implements InterfaceMessage {

	private String name;
	private String version;
	private String provider;
	private String checksum;
	private Map<String, String> userDefinedData = new HashMap<String, String>();
	private String vnfPackagePath;
	
	public OnBoardVnfPackageRequest() {	}
	
	/**
	 * Constructor
	 * 
	 * @param name Name of the VNF Package to be on-boarded.
	 * @param version Version of the VNF Package to be on-boarded.
	 * @param provider Provider of the VNF Package to be on-boarded.
	 * @param checksum Checksum of the on-boarded VNF Package.
	 * @param userDefinedData User defined data for the VNF Package.
	 * @param vnfPackagePath Address information based on which the VNF Package can be obtained.
	 */
	public OnBoardVnfPackageRequest(String name,
			String version,
			String provider,
			String checksum,
			Map<String, String> userDefinedData,
			String vnfPackagePath) {	
		this.name = name;
		this.version = version;
		this.provider = provider;
		this.checksum = checksum;
		if (userDefinedData != null) this.userDefinedData = userDefinedData;
		this.vnfPackagePath = vnfPackagePath;
	}
	
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}

	/**
	 * @return the userDefinedData
	 */
	public Map<String, String> getUserDefinedData() {
		return userDefinedData;
	}

	/**
	 * @return the vnfPackagePath
	 */
	public String getVnfPackagePath() {
		return vnfPackagePath;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		if (name == null) throw new MalformattedElementException("On board VNF package request without name");
		if (version == null) throw new MalformattedElementException("On board VNF package request without version");
		if (provider == null) throw new MalformattedElementException("On board VNF package request without provider");
		if (checksum == null) throw new MalformattedElementException("On board VNF package request without checksum");
		if (vnfPackagePath == null) throw new MalformattedElementException("On board VNF package request without package path");
	}

}
