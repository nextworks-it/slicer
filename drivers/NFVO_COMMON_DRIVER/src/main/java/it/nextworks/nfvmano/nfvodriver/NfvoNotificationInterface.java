package it.nextworks.nfvmano.nfvodriver;

import it.nextworks.nfvmano.libs.catalogues.interfaces.MecAppPackageManagementConsumerInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.NsdManagementConsumerInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.VnfPackageManagementConsumerInterface;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.NsLcmConsumerInterface;

/**
 * This interface handles all the notifications from the NFVO driver to NS.
 * The NFVO driver must invoke the methods of this interface in order to send 
 * notifications to the VS.
 * 
 * @author nextworks
 *
 */
public interface NfvoNotificationInterface extends MecAppPackageManagementConsumerInterface,
		VnfPackageManagementConsumerInterface, NsdManagementConsumerInterface, NsLcmConsumerInterface {

}
