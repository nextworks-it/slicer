package it.nextworks.nfvmano.sebastian.translator;

import java.util.List;
import java.util.Map;

import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;

public interface TranslatorInterface {
	
	/**
	 * This method provides the list of NFV Network Services to be allocated
	 * for the given set of Vertical Service Descriptors. The returned list
	 * includes information about the NSD of each NFV Network Service to be 
	 * allocated together with its deployment flavour and instantiation level.
	 * 
	 * @param vsdIds List of VSDs defining the vertical services to be instantiated.
	 * @return A map with key VSD ID and value the NFV NS to be instantiated to meet the VSD requirements.
	 * @throws FailedOperationException if the operation fails.
	 * @throws NotExistingEntityException if the VSD is not available in the DB.
	 */
	public Map<String, NfvNsInstantiationInfo> translateVsd(List<String> vsdIds)
		throws FailedOperationException, NotExistingEntityException;
	
}
