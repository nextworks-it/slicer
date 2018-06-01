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
package it.nextworks.nfvmano.sebastian.translator;

public class NfvNsInstantiationInfo {

	private String nfvNsdId;
	private String nsdVersion;
	private String deploymentFlavourId;
	private String instantiationLevelId;
	
	/**
	 * Constructor
	 * 
	 * @param nfvNsdId NSD ID
	 * @param nsdVersion NSD version
	 * @param deploymentFlavourId NS Deployment Flavour ID
	 * @param instantiationLevelId NS Instantiation Level ID 
	 */
	public NfvNsInstantiationInfo(String nfvNsdId,
			String nsdVersion,
			String deploymentFlavourId,
			String instantiationLevelId) {
		this.nfvNsdId = nfvNsdId;
		this.nsdVersion = nsdVersion;
		this.deploymentFlavourId = deploymentFlavourId;
		this.instantiationLevelId = instantiationLevelId;
	}
	
	/**
	 * @return the nfvNsdId
	 */
	public String getNfvNsdId() {
		return nfvNsdId;
	}
	
	
	
	/**
	 * @return the nsdVersion
	 */
	public String getNsdVersion() {
		return nsdVersion;
	}

	/**
	 * @return the deploymentFlavourId
	 */
	public String getDeploymentFlavourId() {
		return deploymentFlavourId;
	}
	
	/**
	 * @return the instantiationLevelId
	 */
	public String getInstantiationLevelId() {
		return instantiationLevelId;
	}
	
}
