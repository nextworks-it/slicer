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

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotPermittedOperationException;
import it.nextworks.nfvmano.nfvodriver.guidrivers.NfvoGuiConnector;
import it.nextworks.nfvmano.nfvodriver.guidrivers.StubConnector;
import it.nextworks.nfvmano.nfvodriver.guidrivers.TimeoGUIConnector;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceStatus;
import it.nextworks.nfvmano.sebastian.record.repo.NetworkSliceInstanceRepository;

/**
 * Wrapper of the internal Vertical Slicer DB for the NSMF side, with records about
 * network slices and network slice subnets.
 * 
 * @author nextworks
 *
 */
@Service
public class NsRecordService {

	private static final Logger log = LoggerFactory.getLogger(NsRecordService.class);
	
	@Autowired
	private NetworkSliceInstanceRepository nsInstanceRepository;

	@Value("${nfvogui.type}")
	private String nfvoGuiType;

	@Value("${nfvogui.address}")
	private String nfvoGuiAddress;

	@Value("${nfvogui.port}")
	private int nfvoGuiPort;

	private NfvoGuiConnector nfvoGuiConnector;

	@PostConstruct
	private void setupGuiConnector() {
		switch (nfvoGuiType) {
			case "NONE":
				nfvoGuiConnector = new StubConnector();
				break;
			case "TIMEO":
				nfvoGuiConnector = new TimeoGUIConnector(nfvoGuiAddress, nfvoGuiPort);
				break;
			default:
				throw new IllegalArgumentException(String.format("Unknown NFVO GUI type %s", nfvoGuiType));
		}
	}
	
	public synchronized String createNetworkSliceInstanceEntry(
			String nstId,
			String nsdId,
			String nsdVersion,
			String dfId,
			String ilId,
			String nfvNsId,
			List<String> networkSliceSubnetInstances,
			String tenantId,
			String name,
			String description,
			boolean soManaged
	) {
		log.debug("Creating a new Network Slice instance");
		NetworkSliceInstance nsi = new NetworkSliceInstance(null, nstId, nsdId, nsdVersion, dfId, ilId, nfvNsId, networkSliceSubnetInstances, tenantId, name, description, soManaged);
		nsInstanceRepository.saveAndFlush(nsi);
		String nsiId = nsi.getId().toString();
		log.debug("Created Network Slice instance with ID " + nsiId);
		nsi.setNsiId(nsiId);
		nsInstanceRepository.saveAndFlush(nsi);
		return nsiId;
	}
	
	/**
	 * This method updated a network slice instance in DB setting the information associated to its instantiation
	 * 
	 * @param nsiId ID of the network slice instance to be updated
	 * @param dfId ID of the flavour of associated NFV Network Service
	 * @param ilId ID of the instantiation level of associated NFV Network Service
	 * @param networkSliceSubnetInstances ID of the network slice instances nested in the slice
	 * @throws NotExistingEntityException if the network slice is not present in DB
	 */
	public synchronized void setNsiInstantiationInfo(String nsiId, String dfId, String ilId, List<String> networkSliceSubnetInstances) throws NotExistingEntityException {
		log.debug("Setting instantiation info for Network Slice instance " + nsiId + " in NSI DB record.");
		NetworkSliceInstance nsi = getNsInstance(nsiId);
		nsi.setDfId(dfId);
		nsi.setInstantiationLevelId(ilId);
		nsi.setNetworkSliceSubnetInstances(networkSliceSubnetInstances);
		nsInstanceRepository.saveAndFlush(nsi);
		log.debug("Updated Network Slice instance " + nsiId + " in NSI DB record.");
	}

	/**
	 * This method update the Network Slice Instance in DB, setting the associated NFV Network Service instance
	 *
	 * @param nsiId    Network Slice instance to be updated
	 * @param nfvNsiId NFV Network Service instance ID to be associated to the network slice instance
	 * @throws NotExistingEntityException if the network slice instance is not present in DB.
	 */
	public synchronized void setNfvNsiInNsi(String nsiId, String nfvNsiId) throws NotExistingEntityException {
		log.debug("Adding NFV Network Service instance " + nfvNsiId + " to Network Slice instance " + nsiId + " in NSI DB record.");
		NetworkSliceInstance nsi = getNsInstance(nsiId);
		nsi.setNfvNsId(nfvNsiId);
		Optional<String> guiUrl = Optional.empty();
		if (nfvoGuiConnector != null) {
			guiUrl = nfvoGuiConnector.makeNfvNsUrl(nfvNsiId);
		}
		guiUrl.ifPresent(nsi::setNfvNsUrl);
		nsInstanceRepository.saveAndFlush(nsi);
		log.debug("NSI with ID {} updated with NFV NSI {}", nsiId, nfvNsiId);
		guiUrl.ifPresent(
				(url) -> log.debug("NSI with ID {} updated with NFV NSI GUI URL {}", nsiId, url)
		);
	}

	/**
	 * This method updates the NSI in DB, setting it in failure state and filling its error message.
	 * If the NSI is associated to the a VSI, the same is done for the VSI.
	 *
	 * @param nsiId        ID of the NSI to be modified in the DB
	 * @param errorMessage error message to be set for the NSI
	 */
	public synchronized void setNsFailureInfo(String nsiId, String errorMessage) {
		log.debug("Adding failure info to NS instance " + nsiId + " in DB.");
		try {
			NetworkSliceInstance nsi = getNsInstance(nsiId);
			nsi.setFailureState(errorMessage);
			nsInstanceRepository.saveAndFlush(nsi);
			log.debug("Set failure info in DB for NSI " + nsiId);
			
		} catch (NotExistingEntityException e) {
			log.error("NSI or VSI not present in DB. Impossible to complete the failure info setting.");
		}
	}

	/**
	 * This method updates the NSI in DB, setting its status.
	 *
	 * @param nsiId  ID of the NSI to be modified in the DB
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

	public synchronized void setNsInstantiationLevel(String nsiId, String instantiationLevelId){
		log.debug("Setting new Instantiation Level Id " + instantiationLevelId + " for network slice " + nsiId + " in DB.");
		try {
			NetworkSliceInstance nsi = getNsInstance(nsiId);
			nsi.setInstantiationLevelId(instantiationLevelId);
			nsInstanceRepository.saveAndFlush(nsi);
			log.debug("NS IL set for network slice " + nsiId);

		} catch (NotExistingEntityException e) {
			log.error("NSI not present in DB. Impossible to complete the IL setting.");
		}
	}
	
	public synchronized void updateNsInstantiationLevelAfterScaling(String nsiId, String newInstantiationLevel) {
		log.debug("Updating instantiation level after scaling: new Instantiation Level Id " + newInstantiationLevel + " for network slice " + nsiId + " in DB.");
		try {
			NetworkSliceInstance nsi = getNsInstance(nsiId);
			nsi.updateInstantiationLevelAfterScaling(newInstantiationLevel);
			nsInstanceRepository.saveAndFlush(nsi);
			log.debug("NS IL and old NS IL set for network slice " + nsiId);

		} catch (NotExistingEntityException e) {
			log.error("NSI not present in DB. Impossible to complete the IL setting.");
		}
	}
	
	public synchronized void resetOldNsInstantiationLevel(String nsiId) {
		log.debug("Resetting old instantiation level after scaling for network slice " + nsiId + " in DB.");
		try {
			NetworkSliceInstance nsi = getNsInstance(nsiId);
			nsi.setOldInstantiationLevelId(null);
			nsInstanceRepository.saveAndFlush(nsi);
			log.debug("Reset old IL for network slice " + nsiId);

		} catch (NotExistingEntityException e) {
			log.error("NSI not present in DB. Impossible to complete the old IL setting.");
		}
	}

	/**
	 * This method adds slice subnets into a network slice
	 *
	 * @param parentNsiId    ID of the parent network slice
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
		else
			throw new NotExistingEntityException("NSI associated to NFV network service with ID " + nfvNsiId + " not present in DB.");
	}

	/**
	 * This method deletes an NSI stored in DB given its ID.
	 *
	 * @param nsiId ID of the network slice to be removed
	 * @throws NotExistingEntityException     if the NSI does not exist
	 * @throws NotPermittedOperationException if the operation is not permitted
	 */
	public synchronized void deleteNsInstance(String nsiId) throws NotExistingEntityException, NotPermittedOperationException {
		log.debug("Removing NSI with ID " + nsiId + " from DB.");
		NetworkSliceInstance nsi = getNsInstance(nsiId);
		if (
				!nsi.getStatus().equals(NetworkSliceStatus.TERMINATED)
						&& !nsi.getStatus().equals(NetworkSliceStatus.FAILED)
		) {
			throw new NotPermittedOperationException(String.format(
					"NS instance %s not in terminated status. Impossible to remove it from DB.",
					nsiId
			));
		}
		nsInstanceRepository.delete(nsi);
		log.debug("NS instance " + nsiId + " removed from DB.");
	}

	/**
	 * @param tenantId           ID of the tenant
	 * @param nsdId              ID of the Network Slice
	 * @param nsdVersion         NSD Version
	 * @param dfId               Deployment Flavour ID
	 * @param instantiationLevel Instantiation Level ID
	 * @return List of NSI matching input parameters
	 */
	public List<NetworkSliceInstance> getUsableSlices(String tenantId, String nsdId, String nsdVersion, String dfId, String instantiationLevel) {
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
