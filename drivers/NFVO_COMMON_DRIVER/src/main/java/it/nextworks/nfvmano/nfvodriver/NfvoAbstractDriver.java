package it.nextworks.nfvmano.nfvodriver;

import it.nextworks.nfvmano.libs.catalogues.interfaces.MecAppPackageManagementProviderInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.NsdManagementProviderInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.VnfPackageManagementProviderInterface;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.NsLcmProviderInterface;

/**
 * This abstract class must be extended to implement the specific NFVO driver.
 * The implementation of the methods must include the interaction with the NFVO to send it the 
 * messages generated from the VS. In order to send notifications to the VS, the driver
 * must invoke the methods of the nfvoNotificationManager.
 * 
 * @author nextworks
 *
 */
public abstract class NfvoAbstractDriver implements NsLcmProviderInterface,
	MecAppPackageManagementProviderInterface, NsdManagementProviderInterface, VnfPackageManagementProviderInterface {

	NfvoDriverType nfvoDriverType;
	String nfvoAddress;
	NfvoNotificationInterface nfvoNotificationManager;
	
	public NfvoAbstractDriver(NfvoDriverType nfvoDriverType,
			String nfvoAddress,
			NfvoNotificationInterface nfvoNotificationManager) {
		this.nfvoDriverType = nfvoDriverType;
		this.nfvoAddress = nfvoAddress;
		this.nfvoNotificationManager = nfvoNotificationManager;
	}

	/**
	 * @return the nfvoDriverType
	 */
	public NfvoDriverType getNfvoDriverType() {
		return nfvoDriverType;
	}

	/**
	 * @return the nfvoAddress
	 */
	public String getNfvoAddress() {
		return nfvoAddress;
	}
	
	

}
