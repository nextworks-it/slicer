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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import it.nextworks.nfvmano.sebastian.record.elements.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotPermittedOperationException;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements.LocationInfo;
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
	public synchronized String createVsInstance(String name, String description, String vsdId, String tenantId, Map<String, String> userData, LocationInfo locationConstraints, String ranEndPointId, String domainId, String mappedInstanceId) {
		log.debug("Creating a new VS instance in DB.");
		VerticalServiceInstance vsi = new VerticalServiceInstance(null, vsdId, tenantId, name, description, null, userData, locationConstraints, ranEndPointId, domainId, mappedInstanceId);
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
	 * This method updates the VSI in DB, adding an associated network slice subnet instance
	 *
	 * @param vsiId ID of the VSI to be updated
	 * @param nssi  ID of the NSSI to be added into the VSI
	 * @throws NotExistingEntityException if the VSI does not exist
	 */
	public synchronized void addNssiInVsi(String vsiId, NetworkSliceSubnetInstance nssi) throws NotExistingEntityException {
		log.debug("Adding Network Slice Subnet instance " + nssi.getNssiId() + " into Vertical Service Instance " + vsiId);
		VerticalServiceInstance vsi = getVsInstance(vsiId);
		vsi.addNetworkSliceSubnetInstance(nssi);
		vsInstanceRepository.saveAndFlush(vsi);
		log.debug("Added NSSI with ID " + nssi.getNssiId() + " into VSI with ID ");
	}
	
	/**
	 * This method updates the NSSI status in a VSI in DB
	 *
	 * @param vsiId  ID of the VSI with the NSSI to be updated
	 * @param nssiId ID of the NSSI to be updated
	 * @param status new status of the NSSI
	 * @throws NotExistingEntityException if the VSI or the NSSI do not exist
	 */
	public synchronized void updateNssiStatusInVsi(String vsiId, String nssiId, NetworkSliceStatus status) throws NotExistingEntityException {
		log.debug("Updating status of Network Slice Subnet instance " + nssiId + " into Vertical Service Instance " + vsiId);
		VerticalServiceInstance vsi = getVsInstance(vsiId);
		vsi.setNetworkSliceSubnetStatus(nssiId, status);
		vsInstanceRepository.saveAndFlush(vsi);
		log.debug("Updated status of NSSI with ID " + nssiId + " into VSI with ID ");
	}

	/**
	 * This method checks in the DB if all the NSSIs associated to a VSI are in a given status
	 *
	 * @param vsiId  ID of the target VSI
	 * @param status target status of the NSSIs
	 * @return true if all the NSSIs associated to the target VSI are in the given status
	 * @throws NotExistingEntityException if the VSI does not exist
	 */
	public synchronized boolean allNssiStatusInVsi(String vsiId, NetworkSliceStatus status) throws NotExistingEntityException {
		log.debug("Checking if the status of all the NSSIs in VSI " + vsiId + " is equal to " + status.toString());
		VerticalServiceInstance vsi = getVsInstance(vsiId);
		return vsi.allSubnetInStatus(status);
	}




	/**
	 * This method returns all the VS instances associated to a slice with a given ID.
	 *
	 * @param sliceSubnetId ID of the slice subnet where the vertical services are mapped
	 * @return the vertical services associated to the slice
	 */
	public List<VerticalServiceInstance> getVsInstancesFromNetworkSliceSubnet(String sliceSubnetId) {
		List<VerticalServiceInstance> vsiWithNsi = vsInstanceRepository.findAll().stream()
				.filter(currentVsi -> currentVsi.getNssis().keySet().contains(sliceSubnetId))
				.collect(Collectors.toList());
		return vsiWithNsi;
	}

	/**
	 * This method returns all the VS instances associated to a slice with a given ID.
	 *
	 * @param subserviceId ID of the vertical subservice
	 * @return the vertical service associated to the subservice
	 */
	public List<VerticalServiceInstance> getVsInstancesFromVerticalSubService(String subserviceId) {
		List<VerticalServiceInstance> vsiWithNsi = vsInstanceRepository.findAll().stream()
				.filter(currentVsi -> currentVsi.getVssis().keySet().contains(subserviceId))
				.collect(Collectors.toList());
		return vsiWithNsi;
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

	/**
	 * This method updates the VSI in DB, adding an associated network slice subnet instance
	 *
	 * @param vsiId ID of the VSI to be updated
	 * @param vssi  ID of the Vertical subservice instance to be added into the VSI
	 * @throws NotExistingEntityException if the VSI does not exist
	 */
	public synchronized void addVssiInVsi(String vsiId, VerticalSubserviceInstance vssi) throws NotExistingEntityException {
		log.debug("Adding Vertical subservice instance " + vssi.getInstanceId() + " into Vertical Service Instance " + vsiId);
		VerticalServiceInstance vsi = getVsInstance(vsiId);
		vsi.addVerticalSubserviceInstance(vssi);
		vsInstanceRepository.saveAndFlush(vsi);
		log.debug("Added VSSI with ID " + vssi.getInstanceId() + " into VSI with ID ");
	}

	/**
	 * This method updates the VSI in DB, adding an associated network slice subnet instance
	 *
	 * @param vsiId ID of the VSI to be updated
	 * @param nssiId  ID of the NSSI to be added into the VSI
	 *                @param vnfPlacement  the vnf placement map of slice
	 * @throws NotExistingEntityException if the VSI does not exist
	 */
	public synchronized void updateVsiNsiVnfPlacement(String vsiId, String nssiId, Map<String, NetworkSliceVnfPlacement> vnfPlacement) throws NotExistingEntityException {
		log.debug("Updating VSI Network Slice Subnet VNF placement " + nssiId + " into Vertical Service Instance " + vsiId+ " "+vnfPlacement);
		VerticalServiceInstance vsi = getVsInstance(vsiId);
		if(vsi.getNssis().containsKey(nssiId)){
			NetworkSliceSubnetInstance nssi = vsi.getNssis().get(nssiId);
			nssi.setVnfPlacement(vnfPlacement);
			vsi.getNssis().put(nssiId, nssi);
			vsInstanceRepository.saveAndFlush(vsi);

		}else throw new NotExistingEntityException("Network slice with id:"+nssiId+" not found in VSI:"+vsiId);

	}

	public synchronized  Map<String, NetworkSliceSubnetInstance> getNssiInVsi(String vsiId) throws NotExistingEntityException {
		log.debug("Retrieving NSSI in VSI" + vsiId);
		VerticalServiceInstance vsi = getVsInstance(vsiId);
		return vsi.getNssis();

	}

	

	public synchronized void updateVssiStatusInVsi(String vsiId, String vssiId, VerticalServiceStatus status) throws NotExistingEntityException {
		log.debug("Updating status of Vertical subservce instance " + vssiId + " into Vertical Service Instance " + vsiId);
		VerticalServiceInstance vsi = getVsInstance(vsiId);
		vsi.setVerticalSubserviceStatus(vssiId, status);
		vsInstanceRepository.saveAndFlush(vsi);
		log.debug("Updated status of NSSI with ID " + vssiId + " into VSI with ID ");
	}

	

	public synchronized boolean allVssiStatusInVsi(String vsiId, VerticalServiceStatus status) throws NotExistingEntityException {
		log.debug("Checking if the status of all the NSSIs in VSI " + vsiId + " is equal to " + status.toString());
		VerticalServiceInstance vsi = getVsInstance(vsiId);
		return vsi.allVerticalSubserviceInStatus(status);
	}

    public synchronized void updateNssiNfvInstantiationInfoInVsi(String vsiId, String networkSliceInstanceId, String domain, String dfId, String instantiationLevelId) throws NotExistingEntityException {
		VerticalServiceInstance vsi = getVsInstance(vsiId);
		NetworkSliceSubnetInstance nsi = vsi.getNssis().get(networkSliceInstanceId);
		nsi.setNsDeploymentFlavorId(dfId);
		nsi.setNsInstantiationLevelId(instantiationLevelId);
		vsi.getNssis().put(networkSliceInstanceId, nsi);
		vsInstanceRepository.saveAndFlush(vsi);

    }

	public VerticalServiceInstance getVsInstanceFromMappedInstanceId(String vsiId, String domain) {
		log.debug("Retrieving VsInstances by mapped instance id and domain");

		return vsInstanceRepository.findByMappedInstanceIdAndDomainId(vsiId, domain).get(0);

	}

	/**
	 * This method returns all the VS instances owned by a given tenant stored in DB.
	 *
	 * @return all the VS instances owned by the given tenant, as stored in DB.
	 */
	public List<VerticalServiceInstance> getAllVsInstances(String tenantId) {
		log.debug("Get vs instances for tenant:"+tenantId);
		return vsInstanceRepository.findByTenantId(tenantId);
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
