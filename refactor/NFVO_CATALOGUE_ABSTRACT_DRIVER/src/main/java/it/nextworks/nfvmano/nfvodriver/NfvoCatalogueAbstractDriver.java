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
package it.nextworks.nfvmano.nfvodriver;

import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.MecAppPackageManagementProviderInterface;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.NsdManagementProviderInterface;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.VnfPackageManagementProviderInterface;


/**
 * This abstract class must be extended to implement the specific NFVO driver.
 * The implementation of the methods must include the interaction with the NFVO to send it the 
 * messages generated from the VS. In order to send notifications to the VS, the driver
 * must invoke the methods of the nfvoNotificationManager.
 * 
 * @author nextworks
 *
 */
public abstract class NfvoCatalogueAbstractDriver implements MecAppPackageManagementProviderInterface, NsdManagementProviderInterface, VnfPackageManagementProviderInterface {

	NfvoCatalogueDriverType nfvoDriverType;
	String nfvoAddress;
	NfvoCatalogueNotificationInterface nfvoCatalogueNotificationManager;
	
	public NfvoCatalogueAbstractDriver(NfvoCatalogueDriverType nfvoDriverType,
			String nfvoAddress,
			NfvoCatalogueNotificationInterface nfvoCatalogueNotificationManager) {
		this.nfvoDriverType = nfvoDriverType;
		this.nfvoAddress = nfvoAddress;
		this.nfvoCatalogueNotificationManager = nfvoCatalogueNotificationManager;
	}

	/**
	 * @return the nfvoCatalogueDriverType
	 */
	public NfvoCatalogueDriverType getNfvoDriverType() {
		return nfvoDriverType;
	}

	/**
	 * @return the nfvoAddress
	 */
	public String getNfvoAddress() {
		return nfvoAddress;
	}


	public void setNfvoCatalogueNotificationManager(NfvoCatalogueNotificationInterface nfvoCatalogueNotificationManager){
		this.nfvoCatalogueNotificationManager=nfvoCatalogueNotificationManager;
	}

	public NfvoCatalogueNotificationInterface getNfvoCatalogueNotificationManager(){
		return nfvoCatalogueNotificationManager;
	}



	

}
