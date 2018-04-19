package it.nextworks.nfvmano.sebastian.nfvodriver;

import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.catalogues.interfaces.MecAppPackageManagementConsumerInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.NsdManagementConsumerInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.VnfPackageManagementConsumerInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.AppPackageOnBoardingNotification;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.AppPackageStateChangeNotification;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.NsdChangeNotification;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.NsdOnBoardingNotification;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.VnfPackageChangeNotification;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.VnfPackageOnboardingNotification;
import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.NsLcmConsumerInterface;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.NsIdentifierCreationNotification;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.NsIdentifierDeletionNotification;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.NsLifecycleChangeNotification;
import it.nextworks.nfvmano.nfvodriver.NfvoNotificationInterface;

/**
 * This class handles all the notifications received from the NFVO.
 * 
 * @author nextworks
 *
 */
@Service
public class NfvoNotificationsManager implements NfvoNotificationInterface {

	public NfvoNotificationsManager() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void notifyNsLifecycleChange(NsLifecycleChangeNotification notification)
			throws MethodNotImplementedException {
		// TODO Auto-generated method stub
		throw new MethodNotImplementedException();
	}

	@Override
	public void notifyNsIdentifierCreation(NsIdentifierCreationNotification notification)
			throws MethodNotImplementedException {
		// TODO Auto-generated method stub
		throw new MethodNotImplementedException();
	}

	@Override
	public void notifyNsIdentifierObjectDeletion(NsIdentifierDeletionNotification notification)
			throws MethodNotImplementedException {
		// TODO Auto-generated method stub
		throw new MethodNotImplementedException();
	}

	@Override
	public void notify(NsdOnBoardingNotification notification) throws MethodNotImplementedException {
		// TODO Auto-generated method stub
		throw new MethodNotImplementedException();
	}

	@Override
	public void notify(NsdChangeNotification notification) throws MethodNotImplementedException {
		// TODO Auto-generated method stub
		throw new MethodNotImplementedException();
	}

	@Override
	public void notify(VnfPackageChangeNotification notification) throws MethodNotImplementedException {
		// TODO Auto-generated method stub
		throw new MethodNotImplementedException();
	}

	@Override
	public void notify(VnfPackageOnboardingNotification notification) throws MethodNotImplementedException {
		// TODO Auto-generated method stub
		throw new MethodNotImplementedException();
	}

	@Override
	public void notify(AppPackageOnBoardingNotification notification) 
			throws MethodNotImplementedException {
		// TODO Auto-generated method stub
		throw new MethodNotImplementedException();
	}

	@Override
	public void notify(AppPackageStateChangeNotification notification) 
			throws MethodNotImplementedException {
		// TODO Auto-generated method stub
		throw new MethodNotImplementedException();
	}

}
