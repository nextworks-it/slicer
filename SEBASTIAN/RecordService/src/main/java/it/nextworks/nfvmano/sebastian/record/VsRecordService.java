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
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotPermittedOperationException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.LocationInfo;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceStatus;
import it.nextworks.nfvmano.sebastian.record.repo.VerticalServiceInstanceRepository;



/**
 * Wrapper of the internal Vertical Slicer DB for the VSMF side, 
 * with records about vertical services.
 * 
 * @author nextworks
 *
 */
@Service
public class VsRecordService {

	private static final Logger log = LoggerFactory.getLogger(VsRecordService.class);

	@Autowired
	private VerticalServiceInstanceRepository vsInstanceRepository;

	@Value("${nfvogui.type}")
	private String nfvoGuiType;

	
	//Methods about vertical services

	/**
	 * This methods creates a new Vertical Service instance, assigning it an ID, and it stores it in the DB.
	 *
	 * @param name        name of the VSI
	 * @param description description of the VSI
	 * @param vsdId       ID of the VSD
	 * @param tenantId    ID owning the VSI
	 * @param userData	  configuration parameters provided by the vertical
	 * @param locationConstraints constraints on the geographical placement of the service
	 * @param ranEndPointId ID of the connection point attached to the RAN
	 * @return the ID assigned to the Vertical Service instance
	 */
	public synchronized String createVsInstance(String name, String description, String vsdId, String tenantId, Map<String, String> userData, LocationInfo locationConstraints, String ranEndPointId) {
		log.debug("Creating a new VS instance in DB.");
		VerticalServiceInstance vsi = new VerticalServiceInstance(null, vsdId, tenantId, name, description, null, userData, locationConstraints, ranEndPointId);
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
	 * @throws NotExistingEntityException     if the VS instance is not present in the DB
	 * @throws NotPermittedOperationException if the VS instance is not in terminated status
	 */
	public synchronized void removeVsInstance(String vsiId) throws NotExistingEntityException, NotPermittedOperationException {
		log.debug("Removing VS instance " + vsiId + " from DB.");
		VerticalServiceInstance vsi = getVsInstance(vsiId);
		if (!vsi.getStatus().equals(VerticalServiceStatus.TERMINATED))
			throw new NotPermittedOperationException("VS instance " + vsiId + " not in terminated status. Impossible to remove it from DB.");
		vsInstanceRepository.delete(vsi);
		log.debug("VS instance " + vsiId + " removed from DB.");
	}

	/**
	 * This method updates the VSI in DB, setting it in failure state and filling its error message.
	 *
	 * @param vsiId        ID of the VSI to be modified in the DB
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
			log.error("VSI with ID " + vsiId + " not present in DB. Impossible to set VSI failure info.");
		}
	}

	/**
	 * This method updates the VSI in DB, setting its status
	 *
	 * @param vsiId  ID of the VSI to be modified in the DB
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
	 * @param nestedVsi   VS instance to be addedd
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

	public synchronized void setNsFailureInfoInVsInstances(String nsiId, String errorMessage) {
		List<VerticalServiceInstance> vsis = vsInstanceRepository.findByNetworkSliceId(nsiId);
		for (VerticalServiceInstance vsi : vsis) {
			vsi.setFailureState(errorMessage);
			vsInstanceRepository.saveAndFlush(vsi);
			log.debug("Set failure info in DB for VSI " + vsi.getVsiId());
		}
	}
	
	//Methods about network slices

//	/**
//	 * This method creates a new network slice in the DB and returns its ID.
//	 * The network slice must be associated to a vertical service instance, which is also updated in the DB.
//	 *
//	 * @param vsiId      ID of the vertical service instance to which the network slice is associated
//	 * @param nsdId      NSD of the NFV NS that will implement the network slice
//	 * @param nsdVersion version of the NSD of the NFV NS that will implement the network slice
//	 * @param dfId       ID of NFV NS deployment flavour that is used to implement the network slice
//	 * @param ilId       ID of NFV NS instantiation level that is used to implement the network slice
//	 * @param tenantId   ID of the tenant owning the network slice
//	 * @return the ID of the network slice
//	 * @throws NotExistingEntityException if the Vertical Service instance associated to the network slice does not exist
//	 * @throws FailedOperationException   if the operation fails, e.g. because the VSI is already associated to a network slice instance
//	 */
//	public synchronized String createNetworkSliceForVsi(
//			String vsiId,
//			String nstId,
//			String nsdId,
//			String nsdVersion,
//			String dfId,
//			String ilId,
//			List<String> networkSliceSubnetInstances,
//			String tenantId,
//			String name,
//			String description
//	) throws NotExistingEntityException, FailedOperationException {
//		log.debug("Creating a new Network Slice instance associated to a vertical service in DB.");
//		VerticalServiceInstance vsi = getVsInstance(vsiId);
//		if (vsi.getNetworkSliceId() != null) {
//			log.error("The VSI " + vsiId + " is already associated to a network slice instance. Impossible to create a new one");
//			throw new FailedOperationException("The VSI " + vsiId + " is already associated to a network slice instance. Impossible to create a new one");
//		}
//		NetworkSliceInstance nsi = new NetworkSliceInstance(null, nstId, nsdId, nsdVersion, dfId, ilId, null, networkSliceSubnetInstances, tenantId, name, description, false);
//		nsInstanceRepository.saveAndFlush(nsi);
//		String nsiId = nsi.getId().toString();
//		log.debug("Created Network Slice instance with ID " + nsiId);
//		nsi.setNsiId(nsiId);
//		nsInstanceRepository.saveAndFlush(nsi);
//		vsi.setNetworkSliceId(nsiId);
//		vsInstanceRepository.saveAndFlush(vsi);
//		log.debug("Info about network slice instance updated in DB.");
//		return nsiId;
//	}

}
