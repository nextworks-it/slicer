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
package it.nextworks.nfvmano.sebastian.nfvodriver;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import it.nextworks.nfvmano.libs.catalogues.interfaces.elements.NsdInfo;
import it.nextworks.nfvmano.nfvodriver.logging.LoggingDriver;
import it.nextworks.nfvmano.sebastian.admin.elements.VirtualResourceUsage;
import it.nextworks.nfvmano.sebastian.common.Utilities;
import it.nextworks.nfvmano.sebastian.nfvodriver.timeo.TimeoDriver;
import it.nextworks.nfvmano.sebastian.nfvodriver.timeo.TimeoNfvoOperationPollingManager;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.translator.NfvNsInstantiationInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.catalogues.interfaces.MecAppPackageManagementConsumerInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.MecAppPackageManagementProviderInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.NsdManagementConsumerInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.NsdManagementProviderInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.VnfPackageManagementConsumerInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.VnfPackageManagementProviderInterface;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.DeleteNsdRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.DeleteNsdResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.DeletePnfdRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.DeletePnfdResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.DeleteVnfPackageRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.DisableNsdRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.DisableVnfPackageRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.EnableNsdRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.EnableVnfPackageRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.FetchOnboardedVnfPackageArtifactsRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnBoardVnfPackageRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnBoardVnfPackageResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnboardAppPackageRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnboardAppPackageResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnboardNsdRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnboardPnfdRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.QueryNsdResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.QueryOnBoadedAppPkgInfoResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.QueryOnBoardedVnfPkgInfoResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.QueryPnfdResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.UpdateNsdRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.UpdatePnfdRequest;
import it.nextworks.nfvmano.libs.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.common.messages.SubscribeRequest;
import it.nextworks.nfvmano.libs.descriptors.common.elements.VirtualComputeDesc;
import it.nextworks.nfvmano.libs.descriptors.common.elements.VirtualStorageDesc;
import it.nextworks.nfvmano.libs.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.libs.descriptors.vnfd.InstantiationLevel;
import it.nextworks.nfvmano.libs.descriptors.vnfd.Vdu;
import it.nextworks.nfvmano.libs.descriptors.vnfd.VduLevel;
import it.nextworks.nfvmano.libs.descriptors.vnfd.VnfDf;
import it.nextworks.nfvmano.libs.descriptors.vnfd.Vnfd;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.NsLcmConsumerInterface;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.NsLcmProviderInterface;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.CreateNsIdentifierRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.HealNsRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.InstantiateNsRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.QueryNsResponse;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.ScaleNsRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.TerminateNsRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.UpdateNsRequest;
import it.nextworks.nfvmano.libs.osmanfvo.nslcm.interfaces.messages.UpdateNsResponse;
import it.nextworks.nfvmano.nfvodriver.NfvoAbstractDriver;

/**
 * This entity handles all the client-server interaction with the NFVO..
 * It forwards the request to the configured NFVO using the related driver.
 * 
 * @author nextworks
 *
 */
@Service
public class NfvoService implements NsLcmProviderInterface,
MecAppPackageManagementProviderInterface, NsdManagementProviderInterface, VnfPackageManagementProviderInterface {
	
	private static final Logger log = LoggerFactory.getLogger(NfvoService.class);
	
	@Value("${nfvo.type}")
	private String nfvoType;
	
	@Value("${nfvo.address}")
	private String nfvoAddress;
	
	private NfvoAbstractDriver nfvoDriver;
	
	@Autowired
	NfvoNotificationsManager nfvoNotificationManager;
	
	@Autowired
	TimeoNfvoOperationPollingManager timeoNfvoOperationPollingManager;

	public NfvoService() {	}

	@PostConstruct
	public void initNfvoDriver() {
		log.debug("Initializing NFVO driver");
		if (nfvoType.equals("TIMEO")) {
			log.debug("The Vertical Slicer is configured to operate over the TIMEO orchestrator.");
			nfvoDriver = new TimeoDriver(nfvoAddress, nfvoNotificationManager, timeoNfvoOperationPollingManager);
		} else if (nfvoType.equals("OSM")) {
			log.debug("The Vertical Slicer is configured to operate over the OSM orchestrator.");
			nfvoDriver = new OsmDriver(nfvoAddress, nfvoNotificationManager);
		} else if (nfvoType.equals("DUMMY")) {
			log.debug("The Vertical Slicer is configured to operate over a dummy NFVO.");
			nfvoDriver = new DummyNfvoDriver(nfvoAddress, nfvoNotificationManager);
		} else if (nfvoType.equals("LOGGING")) {
			log.debug("The Vertical Slicer is configured to operate over a logging NFVO.");
			nfvoDriver = new LoggingDriver();
		} else {
			log.error("NFVO not configured!");
		}
	}

	public VirtualResourceUsage computeVirtualResourceUsage(NetworkSliceInstance nsi) throws Exception {
		return computeVirtualResourceUsage(nsi.getNsInstantiationInfo());
	}

	/**
	 * This method computes the amount of resources (disk, vCPU, RAM) needed to instantiate an NSD with the given ID and the given deployment flavours and instantiation levels.
	 * The amount of virtual resources is derived from the NSD and the VNFD.
	 * 
	 * @param nsInstantiationInfo characteristics of the NS to be instantiated, in terms of NSD, deployment flavour and instantiation level
	 * @return the amount of virtual resources needed to instantiate the VNFs of the NSD
	 * @throws Exception if something goes wrong in the interaction with the NFVO
	 */
	public VirtualResourceUsage computeVirtualResourceUsage(NfvNsInstantiationInfo nsInstantiationInfo) throws Exception {
		log.debug("Computing the amount of resources associated to a NS instantiation.");
		
		//TODO: parse the MEC app data when available
		
		String nsdId = nsInstantiationInfo.getNfvNsdId();
		String nsdVersion = nsInstantiationInfo.getNsdVersion();
		String deploymentFlavourId = nsInstantiationInfo.getDeploymentFlavourId();
		String instantiationLevelId = nsInstantiationInfo.getInstantiationLevelId();
		
		int ram = 0;
		int vCPU = 0;
		int disk = 0;

		QueryNsdResponse nsdRep = queryNsd(new GeneralizedQueryRequest(Utilities.buildNsdFilter(nsdId, nsdVersion), null));
		Nsd nsd = nsdRep.getQueryResult().get(0).getNsd();

		//return a map with key = VNFD_ID and value a map with keys = [VNFD_ID, VNF_DF_ID, VNF_INSTANCES, VNF_INSTANTIATION_LEVEL]
		Map<String,Map<String, String>> vnfData = nsd.getVnfdDataFromFlavour(deploymentFlavourId, instantiationLevelId);
		
		for (Map.Entry<String, Map<String, String>> e : vnfData.entrySet()) {
			String vnfdId = e.getKey();
			Map<String, String> vnfCharacteristics = e.getValue();
			String vnfDfId = vnfCharacteristics.get("VNF_DF_ID");
			int vnfInstancesNumber= Integer.parseInt(vnfCharacteristics.get("VNF_INSTANCES"));
			String vnfInstantiationLevel = vnfCharacteristics.get("VNF_INSTANTIATION_LEVEL");
			
			int vnfRam = 0;
			int vnfVCpu = 0;
			int vnfDisk = 0;
			
			QueryOnBoardedVnfPkgInfoResponse vnfPkg = queryVnfPackageInfo(new GeneralizedQueryRequest(Utilities.buildVnfPackageInfoFilterFromVnfdId(vnfdId), null));
			Vnfd vnfd = vnfPkg.getQueryResult().get(0).getVnfd();
			
			VnfDf df = vnfd.getVnfDf(vnfDfId);
			InstantiationLevel il = df.getInstantiationLevel(vnfInstantiationLevel);
			List<VduLevel> vduLevel = il.getVduLevel();
			for (VduLevel vdul : vduLevel) {
				int vduInstancesNumber = vdul.getNumberOfInstances();
				String vduId = vdul.getVduId();
				Vdu vdu = vnfd.getVduFromId(vduId);
				String computeDescriptorId = vdu.getVirtualComputeDesc();
				VirtualComputeDesc vcd = vnfd.getVirtualComputeDescriptorFromId(computeDescriptorId);
				int localRam = (vcd.getVirtualMemory().getVirtualMemSize()) * vduInstancesNumber;
				int localVCpu = (vcd.getVirtualCpu().getNumVirtualCpu()) * vduInstancesNumber;
				int localDisk = 0;
				List<String> virtualStorageDescId = vdu.getVirtualStorageDesc();
				for (String vsdId : virtualStorageDescId) {
					VirtualStorageDesc vsd = vnfd.getVirtualStorageDescriptorFromId(vsdId);
					localDisk += vsd.getSizeOfStorage();
				}
				localDisk = localDisk * vduInstancesNumber;
				
				//update data for all the VDUs with a given ID in the single VNF
				vnfRam += localRam;
				vnfVCpu += localVCpu;
				vnfDisk += localDisk;
				
				log.debug("Values for all the VDUs with ID " + vduId + " - vCPU: " + localVCpu + "; RAM: " + localRam + "; Disk: " + localDisk);
			}
			
			//compute data for all the VNFs with a given Id
			vnfRam = vnfRam * vnfInstancesNumber;
			vnfVCpu = vnfVCpu * vnfInstancesNumber;
			vnfDisk = vnfDisk * vnfInstancesNumber;
			
			log.debug("Values for all the VNFs with ID " + vnfdId + " - vCPU: " + vnfVCpu + "; RAM: " + vnfRam + "; Disk: " + vnfDisk);
			
			//update data for the entire NSD
			ram += vnfRam;
			vCPU += vnfVCpu;
			disk += vnfDisk; 
		}
		
		log.debug("Values for the whole NSD with ID " + nsdId + ", DF " + deploymentFlavourId + ", IL " + instantiationLevelId + "- vCPU: " + vCPU + "; RAM: " + ram + "; Disk: " + disk);

		return new VirtualResourceUsage(disk, vCPU, ram);
	}

	public Nsd queryNsdAssumingOne(String nsdId, String nsdVersion) throws Exception {
		QueryNsdResponse nsdRep = queryNsd(new GeneralizedQueryRequest(Utilities.buildNsdFilter(nsdId, nsdVersion), null));
		List<NsdInfo> nsds = nsdRep.getQueryResult();
		if (nsds.size() == 0) {
			throw new NotExistingEntityException(
					String.format("No nsd with nsdId %s and version %s", nsdId, nsdVersion)
			);
		}
		if (nsds.size() > 1) {
			throw new FailedOperationException(
					String.format("More than one nsd with nsdId %s and version %s", nsdId, nsdVersion)
			);
		}
		return nsdRep.getQueryResult().get(0).getNsd();
	}
	
	@Override
	public String createNsIdentifier(CreateNsIdentifierRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		return nfvoDriver.createNsIdentifier(request);
	}

	@Override
	public String instantiateNs(InstantiateNsRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		return nfvoDriver.instantiateNs(request);
	}

	@Override
	public String scaleNs(ScaleNsRequest request) throws MethodNotImplementedException, NotExistingEntityException,
			FailedOperationException, MalformattedElementException {
		return nfvoDriver.scaleNs(request);
	}

	@Override
	public UpdateNsResponse updateNs(UpdateNsRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		return nfvoDriver.updateNs(request);
	}

	@Override
	public QueryNsResponse queryNs(GeneralizedQueryRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		return nfvoDriver.queryNs(request);
	}

	@Override
	public String terminateNs(TerminateNsRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		return nfvoDriver.terminateNs(request);
	}

	@Override
	public void deleteNsIdentifier(String nsInstanceIdentifier)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException {
		nfvoDriver.deleteNsIdentifier(nsInstanceIdentifier);
	}

	@Override
	public String healNs(HealNsRequest request) throws MethodNotImplementedException, NotExistingEntityException,
			FailedOperationException, MalformattedElementException {
		return nfvoDriver.healNs(request);
	}

	@Override
	public OperationStatus getOperationStatus(String operationId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		return nfvoDriver.getOperationStatus(operationId);
	}

	@Override
	public String subscribeNsLcmEvents(SubscribeRequest request, NsLcmConsumerInterface consumer)
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
		return nfvoDriver.subscribeNsLcmEvents(request, nfvoNotificationManager);
	}

	@Override
	public void unsubscribeNsLcmEvents(String subscriptionId) throws MethodNotImplementedException,
			MalformattedElementException, NotExistingEntityException, FailedOperationException {
		nfvoDriver.unsubscribeNsLcmEvents(subscriptionId);
	}

	@Override
	public void queryNsSubscription(GeneralizedQueryRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.queryNsSubscription(request);
	}

	@Override
	public File fetchOnboardedApplicationPackage(String onboardedAppPkgId)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		return nfvoDriver.fetchOnboardedApplicationPackage(onboardedAppPkgId);
	}

	@Override
	public QueryOnBoadedAppPkgInfoResponse queryApplicationPackage(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		return nfvoDriver.queryApplicationPackage(request);
	}

	@Override
	public String subscribeMecAppPackageInfo(SubscribeRequest request,
			MecAppPackageManagementConsumerInterface consumer)
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
		return nfvoDriver.subscribeMecAppPackageInfo(request, nfvoNotificationManager);
	}

	@Override
	public void unsubscribeMecAppPackageInfo(String subscriptionId)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		nfvoDriver.unsubscribeMecAppPackageInfo(subscriptionId);
	}

	@Override
	public OnboardAppPackageResponse onboardAppPackage(OnboardAppPackageRequest request)
			throws MethodNotImplementedException, AlreadyExistingEntityException, FailedOperationException,
			MalformattedElementException {
		return nfvoDriver.onboardAppPackage(request);
	}

	@Override
	public void enableAppPackage(String onboardedAppPkgId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.enableAppPackage(onboardedAppPkgId);
	}

	@Override
	public void disableAppPackage(String onboardedAppPkgId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.disableAppPackage(onboardedAppPkgId);
	}

	@Override
	public void deleteAppPackage(String onboardedAppPkgId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.deleteAppPackage(onboardedAppPkgId);
	}

	@Override
	public void abortAppPackageDeletion(String onboardedAppPkgId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.abortAppPackageDeletion(onboardedAppPkgId);
	}

	@Override
	public String onboardNsd(OnboardNsdRequest request) throws MethodNotImplementedException,
			MalformattedElementException, AlreadyExistingEntityException, FailedOperationException {
		return nfvoDriver.onboardNsd(request);
	}

	@Override
	public void enableNsd(EnableNsdRequest request) throws MethodNotImplementedException, MalformattedElementException,
			NotExistingEntityException, FailedOperationException {
		nfvoDriver.enableNsd(request);
	}

	@Override
	public void disableNsd(DisableNsdRequest request) throws MethodNotImplementedException,
			MalformattedElementException, NotExistingEntityException, FailedOperationException {
		nfvoDriver.disableNsd(request);
	}

	@Override
	public String updateNsd(UpdateNsdRequest request)
			throws MethodNotImplementedException, MalformattedElementException, AlreadyExistingEntityException,
			NotExistingEntityException, FailedOperationException {
		return nfvoDriver.updateNsd(request);
	}

	@Override
	public DeleteNsdResponse deleteNsd(DeleteNsdRequest request) throws MethodNotImplementedException,
			MalformattedElementException, NotExistingEntityException, FailedOperationException {
		return nfvoDriver.deleteNsd(request);
	}

	@Override
	public QueryNsdResponse queryNsd(GeneralizedQueryRequest request) throws MethodNotImplementedException,
			MalformattedElementException, NotExistingEntityException, FailedOperationException {
		return nfvoDriver.queryNsd(request);
	}

	@Override
	public String subscribeNsdInfo(SubscribeRequest request, NsdManagementConsumerInterface consumer)
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
		return nfvoDriver.subscribeNsdInfo(request, nfvoNotificationManager);
	}

	@Override
	public void unsubscribeNsdInfo(String subscriptionId) throws MethodNotImplementedException,
			MalformattedElementException, NotExistingEntityException, FailedOperationException {
		nfvoDriver.unsubscribeNsdInfo(subscriptionId);
	}

	@Override
	public String onboardPnfd(OnboardPnfdRequest request) throws MethodNotImplementedException,
			MalformattedElementException, AlreadyExistingEntityException, FailedOperationException {
		return nfvoDriver.onboardPnfd(request);
	}

	@Override
	public String updatePnfd(UpdatePnfdRequest request)
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException,
			AlreadyExistingEntityException, FailedOperationException {
		return nfvoDriver.updatePnfd(request);
	}

	@Override
	public DeletePnfdResponse deletePnfd(DeletePnfdRequest request) throws MethodNotImplementedException,
			MalformattedElementException, NotExistingEntityException, FailedOperationException {
		return nfvoDriver.deletePnfd(request);
	}

	@Override
	public QueryPnfdResponse queryPnfd(GeneralizedQueryRequest request) throws MethodNotImplementedException,
			MalformattedElementException, NotExistingEntityException, FailedOperationException {
		return nfvoDriver.queryPnfd(request);
	}

	@Override
	public OnBoardVnfPackageResponse onBoardVnfPackage(OnBoardVnfPackageRequest request)
			throws MethodNotImplementedException, AlreadyExistingEntityException, FailedOperationException,
			MalformattedElementException {
		return nfvoDriver.onBoardVnfPackage(request);
	}

	@Override
	public void enableVnfPackage(EnableVnfPackageRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.enableVnfPackage(request);
	}

	@Override
	public void disableVnfPackage(DisableVnfPackageRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.disableVnfPackage(request);
	}

	@Override
	public void deleteVnfPackage(DeleteVnfPackageRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.deleteVnfPackage(request);
	}

	@Override
	public QueryOnBoardedVnfPkgInfoResponse queryVnfPackageInfo(GeneralizedQueryRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		return nfvoDriver.queryVnfPackageInfo(request);
	}

	@Override
	public String subscribeVnfPackageInfo(SubscribeRequest request, VnfPackageManagementConsumerInterface consumer)
			throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
		return nfvoDriver.subscribeVnfPackageInfo(request, nfvoNotificationManager);
	}

	@Override
	public void unsubscribeVnfPackageInfo(String subscriptionId)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		nfvoDriver.unsubscribeVnfPackageInfo(subscriptionId);
	}

	@Override
	public File fetchOnboardedVnfPackage(String onboardedVnfPkgInfoId)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		return nfvoDriver.fetchOnboardedVnfPackage(onboardedVnfPkgInfoId);
	}

	@Override
	public List<File> fetchOnboardedVnfPackageArtifacts(FetchOnboardedVnfPackageArtifactsRequest request)
			throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
		return nfvoDriver.fetchOnboardedVnfPackageArtifacts(request);
	}

	@Override
	public void abortVnfPackageDeletion(String onboardedVnfPkgInfoId) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.abortVnfPackageDeletion(onboardedVnfPkgInfoId);
	}

	@Override
	public void queryVnfPackageSubscription(GeneralizedQueryRequest request) throws MethodNotImplementedException,
			NotExistingEntityException, FailedOperationException, MalformattedElementException {
		nfvoDriver.queryVnfPackageSubscription(request);
	}

	
}
