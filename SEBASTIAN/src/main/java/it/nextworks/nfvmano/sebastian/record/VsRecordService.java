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
package it.nextworks.nfvmano.sebastian.record;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.common.exceptions.NotPermittedOperationException;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceStatus;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceStatus;
import it.nextworks.nfvmano.sebastian.record.repo.NetworkSliceInstanceRepository;
import it.nextworks.nfvmano.sebastian.record.repo.VerticalServiceInstanceRepository;

/**
 * Wrapper of the internal Vertical Slicer DB, with records about
 * vertical services, network slices and network slice subnets.
 * 
 * @author nextworks
 *
 */
@Service
public class VsRecordService {

	private static final Logger log = LoggerFactory.getLogger(VsRecordService.class);
	
	@Autowired
	private VerticalServiceInstanceRepository vsInstanceRepository;
	
	@Autowired
	private NetworkSliceInstanceRepository nsInstanceRepository;
	
	//Methods about vertical services

	// TODO add method to populate sub-VS-instances of given instance
	
	/**
	 * This methods creates a new Vertical Service instance, assigning it an ID, and it stores it in the DB.
	 * 
	 * @param name name of the VSI
	 * @param description description of the VSI
	 * @param vsdId ID of the VSD
	 * @param tenantId ID owning the VSI
	 * @return the ID assigned to the Vertical Service instance
	 */
	public synchronized String createVsInstance(String name, String description, String vsdId, String tenantId) {
		log.debug("Creating a new VS instance in DB.");
		VerticalServiceInstance vsi = new VerticalServiceInstance(null, vsdId, tenantId, name, description, null);
		vsInstanceRepository.saveAndFlush(vsi);
		String vsiId = vsi.getId().toString();
		log.debug("Created Vertical Service instance with ID " + vsiId);
		vsi.setVsiId(vsiId);
		vsInstanceRepository.saveAndFlush(vsi);
		return vsiId;
	}
	
	/**
	 * This method removes a terminated VS instance from the DB.
	 * 
	 * @param vsiId ID of the VS instance to be removed
	 * @throws NotExistingEntityException if the VS instance is not present in the DB
	 * @throws NotPermittedOperationException if the VS instance is not in terminated status
	 */
	public synchronized void removeVsInstance(String vsiId) throws NotExistingEntityException, NotPermittedOperationException {
		log.debug("Removing VS instance " + vsiId + " from DB.");
		VerticalServiceInstance vsi = getVsInstance(vsiId);
		if (vsi.getStatus() != VerticalServiceStatus.TERMINATED) throw new NotPermittedOperationException("VS instance " + vsiId + " not in terminated status. Impossible to remove it from DB.");
		vsInstanceRepository.delete(vsi);
		log.debug("VS instance " + vsiId + " removed from DB.");
	}
	
	/**
	 * This method updates the VSI in DB, setting it in failure state and filling its error message.  
	 * 
	 * @param vsiId ID of the VSI to be modified in the DB
	 * @param errorMessage error message to be set for the VSI
	 */
	public synchronized void setVsFailureInfo(String vsiId, String errorMessage) {
		log.debug("Adding failure info to VS instance " + vsiId + " in DB.");
		try {
			VerticalServiceInstance vsi = getVsInstance(vsiId);
			vsi.setFailureState(errorMessage);
			vsInstanceRepository.saveAndFlush(vsi);
			log.debug("Set failure info in DB.");
		} catch (NotExistingEntityException e) {
			log.error("VSI with ID " + vsiId + " not present in DB. Impossible to set VSI failure info." );
		}
	}
	
	/**
	 * This method updates the VSI in DB, setting its status
	 * 
	 * @param vsiId ID of the VSI to be modified in the DB
	 * @param status new status of the VSI
	 * @throws NotExistingEntityException if the VSI does not exist in DB.
	 */
	public synchronized void setVsStatus(String vsiId, VerticalServiceStatus status) throws NotExistingEntityException {
		log.debug("Setting status in VS instance " + vsiId + " in DB.");
		VerticalServiceInstance vsi = getVsInstance(vsiId);
		vsi.setStatus(status);
		vsInstanceRepository.saveAndFlush(vsi);
		log.debug("Set VS status in DB.");
	}
	
	/**
	 * This method update the VSI in DB, setting the associated network slice instance
	 * 
	 * @param vsiId ID of the VSI to be updated
	 * @param nsiId ID of the NSI to be associated to the VSI
	 * @throws NotExistingEntityException if the VSI does not exist
	 */
	public synchronized void setNsiInVsi(String vsiId, String nsiId) throws NotExistingEntityException {
		log.debug("Adding Network Slice instance " + nsiId + " to Vertical Service instance " + vsiId + " in VSI DB record.");
		VerticalServiceInstance vsi = getVsInstance(vsiId);
		vsi.setNetworkSliceId(nsiId);
		vsInstanceRepository.saveAndFlush(vsi);
		log.debug("VSI with ID " + vsiId + " updated with NSI " + nsiId);
	}
	
	/**
	 * This method returns the VSI stored in DB.
	 * 
	 * @param vsiId ID of the VSI to be returned
	 * @return VSI stored in DB
	 * @throws NotExistingEntityException if the VSI is not present in DB
	 */
	public VerticalServiceInstance getVsInstance(String vsiId) throws NotExistingEntityException {
		log.debug("Retrieving VSI with ID " + vsiId + " from DB.");
		Optional<VerticalServiceInstance> vsi = vsInstanceRepository.findByVsiId(vsiId);
		if (vsi.isPresent()) return vsi.get();
		else throw new NotExistingEntityException("VSI with ID " + vsiId + " not present in DB.");
	}
	
	/**
	 * This method returns all the VS instances stored in DB.
	 * 
	 * @return all the VS instances stored in DB.
	 */
	public List<VerticalServiceInstance> getAllVsInstances() {
		return vsInstanceRepository.findAll();
	}
	
	/**
	 * This method returns all the VS instances owned by a given tenant stored in DB.
	 * 
	 * @return all the VS instances owned by the given tenant, as stored in DB.
	 */
	public List<VerticalServiceInstance> getAllVsInstances(String tenantId) {
		return vsInstanceRepository.findByTenantId(tenantId);
	}
	
	/**
	 * This method returns all the VS instances associated to a slice with a given ID.
	 * 
	 * @param sliceId ID of the slice where the vertical services are mapped
	 * @return the vertical services associated to the slice
	 */
	public List<VerticalServiceInstance> getVsInstancesFromNetworkSlice(String sliceId) {
		return vsInstanceRepository.findByNetworkSliceId(sliceId);
	}

	/**
	 * This method adds nested VSI into parent VSI
	 *
	 * @param parentVsiId ID of the parent VSI
	 * @param nestedVsi VS instance to be addedd
	 */
	public synchronized void addNestedVsInVerticalServiceInstance(String parentVsiId, VerticalServiceInstance nestedVsi) {
		log.debug("Adding nested VSI into parent slice " + parentVsiId + " in DB.");
		try {
			VerticalServiceInstance vsi = getVsInstance(parentVsiId);
			vsi.addNestedVsi(nestedVsi);
			log.debug("Nested VSI added. Id: {}", nestedVsi.getId());
			vsInstanceRepository.saveAndFlush(vsi);
			log.debug("Nestded VSI for VSI {} added.", parentVsiId);
		} catch (NotExistingEntityException e) {
			log.error("NSI not present in DB. Impossible to complete the subnets updates.");
		}
	}
	
	//Methods about network slices
	
	/**
	 * This method creates a new network slice in the DB and returns its ID. 
	 * The network slice must be associated to a vertical service instance, which is also updated in the DB.
	 * 
	 * @param vsiId ID of the vertical service instance to which the network slice is associated
	 * @param nsdId NSD of the NFV NS that will implement the network slice
	 * @param nsdVersion version of the NSD of the NFV NS that will implement the network slice
	 * @param dfId ID of NFV NS deployment flavour that is used to implement the network slice
	 * @param ilId ID of NFV NS instantiation level that is used to implement the network slice
	 * @param tenantId ID of the tenant owning the network slice
	 * @return the ID of the network slice
	 * @throws NotExistingEntityException if the Vertical Service instance associated to the network slice does not exist
	 * @throws FailedOperationException if the operation fails, e.g. because the VSI is already associated to a network slice instance
	 */
	public synchronized String createNetworkSliceForVsi(String vsiId, String nsdId, String nsdVersion, String dfId, String ilId, List<String> networkSliceSubnetInstances, String tenantId, String name, String description)
			throws NotExistingEntityException, FailedOperationException {
		log.debug("Creating a new Network Slice instance associated to a vertical service in DB.");
		VerticalServiceInstance vsi = getVsInstance(vsiId);
		if (vsi.getNetworkSliceId() != null) {
			log.error("The VSI " + vsiId + " is already associated to a network slice instance. Impossible to create a new one");
			throw new FailedOperationException("The VSI " + vsiId + " is already associated to a network slice instance. Impossible to create a new one");
		}
		NetworkSliceInstance nsi = new NetworkSliceInstance(null, nsdId, nsdVersion, dfId, ilId, null, networkSliceSubnetInstances, tenantId, name, description, false);
		nsInstanceRepository.saveAndFlush(nsi);
		String nsiId = nsi.getId().toString();
		log.debug("Created Network Slice instance with ID " + nsiId);
		nsi.setNsiId(nsiId);
		nsInstanceRepository.saveAndFlush(nsi);
		vsi.setNetworkSliceId(nsiId);
		vsInstanceRepository.saveAndFlush(vsi);
		log.debug("Info about network slice instance updated in DB.");
		return nsiId;
	}

	public synchronized String createNetworkSliceInstanceEntry(String nsdId, String nsdVersion, String dfId,
															   String ilId, String nfvNsId, List<String> networkSliceSubnetInstances,
															   String tenantId, String name, String description,
															   boolean soManaged){
		log.debug("Creating a new Network Slice instance handled by SO");
		NetworkSliceInstance nsi = new NetworkSliceInstance(null, nsdId, nsdVersion, dfId, ilId, nfvNsId, networkSliceSubnetInstances, tenantId, name, description, soManaged);
		nsInstanceRepository.saveAndFlush(nsi);
		String nsiId = nsi.getId().toString();
		log.debug("Created Network Slice instance with ID " + nsiId);
		nsi.setNsiId(nsiId);
		nsInstanceRepository.saveAndFlush(nsi);
		return nsiId;
	}
	
	/**
	 * This method update the Network Slice Instance in DB, setting the associated NFV Network Service instance
	 * 
	 * @param nsiId Network Slice instance to be updated
	 * @param nfvNsiId NFV Network Service instance ID to be associated to the network slice instance
	 * @throws NotExistingEntityException if the network slice instance is not present in DB.
	 */
	public synchronized void setNfvNsiInNsi(String nsiId, String nfvNsiId) throws NotExistingEntityException {
		log.debug("Adding NFV Network Service instance " + nfvNsiId + " to Network Slice instance " + nsiId + " in NSI DB record.");
		NetworkSliceInstance nsi = getNsInstance(nsiId);
		nsi.setNfvNsId(nfvNsiId);
		nsInstanceRepository.saveAndFlush(nsi);
		log.debug("NSI with ID " + nsiId + " updated with NFV NSI " + nfvNsiId);
	}
	
	/**
	 * This method updates the NSI in DB, setting it in failure state and filling its error message.
	 * If the NSI is associated to the a VSI, the same is done for the VSI.
	 * 
	 * @param nsiId ID of the NSI to be modified in the DB
	 * @param errorMessage error message to be set for the NSI
	 */
	public synchronized void setNsFailureInfo(String nsiId, String errorMessage) {
		log.debug("Adding failure info to NS instance " + nsiId + " in DB.");
		try {
			NetworkSliceInstance nsi = getNsInstance(nsiId);
			nsi.setFailureState(errorMessage);
			nsInstanceRepository.saveAndFlush(nsi);
			log.debug("Set failure info in DB for NSI " + nsiId);
			List<VerticalServiceInstance> vsis = vsInstanceRepository.findByNetworkSliceId(nsiId);
			for (VerticalServiceInstance vsi : vsis) {
				vsi.setFailureState(errorMessage);
				vsInstanceRepository.saveAndFlush(vsi);
				log.debug("Set failure info in DB for VSI " + vsi.getVsiId());
			}
		} catch (NotExistingEntityException e) {
			log.error("NSI or VSI not present in DB. Impossible to complete the failure info setting." );
		}
	}
	
	/**
	 * This method updates the NSI in DB, setting its status.
	 * 
	 * @param nsiId ID of the NSI to be modified in the DB
	 * @param status new status to be set
	 */
	public synchronized void setNsStatus(String nsiId, NetworkSliceStatus status) {
		log.debug("Setting status " + status + " for network slice " + nsiId + " in DB.");
		try {
			NetworkSliceInstance nsi = getNsInstance(nsiId);
			nsi.setStatus(status);
			nsInstanceRepository.saveAndFlush(nsi);
			log.debug("Status set for network slice " + nsiId);
		} catch (NotExistingEntityException e) {
			log.error("NSI not present in DB. Impossible to complete the state setting.");
		}
	}
	
	/**
	 * This method adds slice subnets into a network slice
	 * 
	 * @param parentNsiId ID of the parent network slice 
	 * @param sliceSubnetIds IDs of the slice subnets to be added
	 */
	public synchronized void addNsSubnetsInNetworkSliceInstance(String parentNsiId, List<String> sliceSubnetIds) {
		log.debug("Adding slice subnets into parent slice " + parentNsiId + " in DB.");
		try {
			NetworkSliceInstance nsi = getNsInstance(parentNsiId);
			for (String s : sliceSubnetIds) {
				nsi.addSubnet(s);
				log.debug("Slice subnet {} added.", s);
			}
			nsInstanceRepository.saveAndFlush(nsi);
			log.debug("Subnets for network slice {} added.", parentNsiId);
		} catch (NotExistingEntityException e) {
			log.error("NSI not present in DB. Impossible to complete the subnets updates.");
		}
	}
	
	/**
	 * This method returns the NSI stored in DB that matches a given ID.
	 * 
	 * @param nsiId ID of the Network Slice instance to be returned
	 * @return the Network Slice instance
	 * @throws NotExistingEntityException if the network slice instance with the given ID is not present in DB.
	 */
	public NetworkSliceInstance getNsInstance(String nsiId) throws NotExistingEntityException {
		log.debug("Retrieving NSI with ID " + nsiId + " from DB.");
		Optional<NetworkSliceInstance> nsi = nsInstanceRepository.findByNsiId(nsiId);
		if (nsi.isPresent()) return nsi.get();
		else throw new NotExistingEntityException("NSI with ID " + nsiId + " not present in DB.");
	}
	
	/**
	 * This method returns the NSI stored in DB that is associated to an
	 * NFV network service with a given ID.
	 * 
	 * @param nfvNsiId ID of the NFV network service implementing the network slice
	 * @return the Network Slice instance
	 * @throws NotExistingEntityException if the network slice instance is not present in DB.
	 */
	public NetworkSliceInstance getNsInstanceFromNfvNsi(String nfvNsiId) throws NotExistingEntityException {
		log.debug("Retrieving NSI associated to NFV Network Service with ID " + nfvNsiId + " from DB.");
		Optional<NetworkSliceInstance> nsi = nsInstanceRepository.findByNfvNsId(nfvNsiId);
		if (nsi.isPresent()) return nsi.get();
		else throw new NotExistingEntityException("NSI associated to NFV network service with ID " + nfvNsiId + " not present in DB.");
	}

	/**
	 * This method deletes an NSI stored in DB given its ID.
	 * 
	 * @param nsiId ID of the network slice to be removed
	 * @throws NotExistingEntityException if the NSI does not exist
	 * @throws NotPermittedOperationException if the operation is not permitted
	 */
	public synchronized void deleteNsInstance(String nsiId) throws NotExistingEntityException, NotPermittedOperationException {
		log.debug("Removing NSI with ID " + nsiId + " from DB.");
		NetworkSliceInstance nsi = getNsInstance(nsiId);
		if (nsi.getStatus() != NetworkSliceStatus.TERMINATED)
			throw new NotPermittedOperationException("NS instance " + nsiId + " not in terminated status. Impossible to remove it from DB.");
		nsInstanceRepository.delete(nsi);
		log.debug("NS instance " + nsiId + " removed from DB.");
	}

	/**
	 *
	 * @param tenantId ID of the tenant
	 * @param nsdId ID of the Network Slice
	 * @param nsdVersion NSD Version
	 * @param dfId Deployment Flavour ID
	 * @param instantiationLevel Instantiation Level ID
	 * @return List of NSI matching input parameters
	 */
	public List<NetworkSliceInstance> getUsableSlices(String tenantId, String nsdId, String nsdVersion, String dfId, String instantiationLevel){
		return nsInstanceRepository.findByTenantIdAndNsdIdAndNsdVersionAndDfIdAndInstantiationLevelId(tenantId, nsdId, nsdVersion, dfId, instantiationLevel);
	}

	/**
	 * This method returns all the NSI stored in DB.
	 * 
	 * @return all the NS instances stored in DB.
	 */
	public List<NetworkSliceInstance> getAllNetworkSliceInstance() {
		return nsInstanceRepository.findAll();
	}
}
