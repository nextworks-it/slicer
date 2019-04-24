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
package it.nextworks.nfvmano.sebastian.catalogue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.catalogues.interfaces.elements.AppPackageInfo;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnBoardVnfPackageRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnBoardVnfPackageResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnboardAppPackageRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnboardAppPackageResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.OnboardNsdRequest;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.QueryNsdResponse;
import it.nextworks.nfvmano.libs.catalogues.interfaces.messages.QueryOnBoardedVnfPkgInfoResponse;
import it.nextworks.nfvmano.libs.common.elements.Filter;
import it.nextworks.nfvmano.libs.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsBlueprint;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsBlueprintInfo;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsComponent;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsbLink;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsdNsdTranslationRule;
import it.nextworks.nfvmano.sebastian.catalogue.messages.OnBoardVsBlueprintRequest;
import it.nextworks.nfvmano.sebastian.catalogue.messages.QueryVsBlueprintResponse;
import it.nextworks.nfvmano.sebastian.catalogue.repo.VsBlueprintInfoRepository;
import it.nextworks.nfvmano.sebastian.catalogue.repo.VsBlueprintRepository;
import it.nextworks.nfvmano.sebastian.catalogue.repo.VsComponentRepository;
import it.nextworks.nfvmano.sebastian.catalogue.repo.VsbLinkRepository;
import it.nextworks.nfvmano.sebastian.common.Utilities;
import it.nextworks.nfvmano.sebastian.nfvodriver.NfvoService;
import it.nextworks.nfvmano.sebastian.translator.TranslationRuleRepository;

@Service
public class VsBlueprintCatalogueService implements VsBlueprintCatalogueInterface {

	private static final Logger log = LoggerFactory.getLogger(VsBlueprintCatalogueService.class);
	
	@Autowired
	private VsBlueprintRepository vsBlueprintRepository;
	
	@Autowired
	private VsBlueprintInfoRepository vsBlueprintInfoRepository;
	
	@Autowired
	private VsbLinkRepository vsbLinkRepository;
	
	@Autowired
	private VsComponentRepository vsComponentRepository;
	
	@Autowired
	private TranslationRuleRepository translationRuleRepository;
	
	@Autowired
	private NfvoService nfvoService;
	
	public VsBlueprintCatalogueService() {	}
	
	@Override
	public synchronized String onBoardVsBlueprint(OnBoardVsBlueprintRequest request)
			throws MethodNotImplementedException, MalformattedElementException, AlreadyExistingEntityException, FailedOperationException {
		log.debug("Processing request to onboard a new VS blueprint");
		request.isValid();
		String vsbId = storeVsBlueprint(request.getVsBlueprint());
		
		VsBlueprintInfo vsBlueprintInfo;
		try {
			vsBlueprintInfo = getVsBlueprintInfo(vsbId);
		} catch (NotExistingEntityException e) {
			log.error("Impossible to retrieve vsBlueprintInfo. Error!");
			throw new FailedOperationException("Internal error: impossible to retrieve vsBlueprintInfo.");
		}
		
		request.setBlueprintIdInTranslationRules(vsbId);
		
		log.debug("Processing SO descriptors");
		try {
			log.debug("Storing VNF packages");
			List<OnBoardVnfPackageRequest> vnfPackages = request.getVnfPackages();
			for (OnBoardVnfPackageRequest vnfR : vnfPackages) {
				try {
					OnBoardVnfPackageResponse vnfReply = nfvoService.onBoardVnfPackage(vnfR);
					String vnfPackageId = vnfReply.getOnboardedVnfPkgInfoId();
					log.debug("Added VNF package for VNF " + vnfR.getName() + 
							", version " + vnfR.getVersion() + ", provider " + vnfR.getProvider() + " in NFVO catalogue. VNF package ID: " + vnfPackageId);
					vsBlueprintInfo.addVnfPackageInfoId(vnfPackageId);
				} catch (AlreadyExistingEntityException e) {
					log.debug("The VNF package is already present in the NFVO catalogue. Retrieving its ID.");
					QueryOnBoardedVnfPkgInfoResponse r = 
							nfvoService.queryVnfPackageInfo(new GeneralizedQueryRequest(Utilities.buildVnfPackageInfoFilter(vnfR.getName(), vnfR.getVersion(), vnfR.getProvider()), null));
					String oldVnfPackageId = r.getQueryResult().get(0).getOnboardedVnfPkgInfoId();
					log.debug("Retrieved VNF package ID: " + oldVnfPackageId);
					vsBlueprintInfo.addVnfPackageInfoId(oldVnfPackageId);
				}
			}

			log.debug("Storing MEC app packages");
			List<OnboardAppPackageRequest> appPackages = request.getMecAppPackages();
			for (OnboardAppPackageRequest appR : appPackages) {
				try {
					OnboardAppPackageResponse appReply = nfvoService.onboardAppPackage(appR);
					String appPackageId = appReply.getOnboardedAppPkgId();
					log.debug("Added MEC app package for app " + appR.getName() + 
							", version " + appR.getVersion() + " in NFVO catalogue. MEC app package ID: " + appPackageId);
					vsBlueprintInfo.addMecAppInfoId(appPackageId);
				} catch (AlreadyExistingEntityException e) {
					log.debug("The MEC app package is already present in the NFVO catalogue. Retrieving its ID.");
					List<AppPackageInfo> apps = 
							nfvoService.queryApplicationPackage(new GeneralizedQueryRequest(Utilities.buildMecAppPackageInfoFilter(appR.getName(), appR.getVersion()), null)).getQueryResult();
					String oldAppPackageId = apps.get(0).getAppPackageInfoId();
					log.debug("Retrieved MEC app package ID: " + oldAppPackageId);
					vsBlueprintInfo.addMecAppInfoId(oldAppPackageId);
				}
			}

			log.debug("Storing NSDs");
			List<Nsd> nsds = request.getNsds();
			for (Nsd nsd : nsds) {
				try {
					String nsdInfoId = nfvoService.onboardNsd(new OnboardNsdRequest(nsd, null));
					log.debug("Added NSD " + nsd.getNsdIdentifier() + 
							", version " + nsd.getVersion() + " in NFVO catalogue. NSD Info ID: " + nsdInfoId);
					vsBlueprintInfo.addNsdInfoId(nsdInfoId);
					request.setNsdInfoIdInTranslationRules(nsdInfoId, nsd.getNsdIdentifier(), nsd.getVersion());
				} catch (AlreadyExistingEntityException e) {
					log.debug("The NSD is already present in the NFVO catalogue. Retrieving its ID.");
					QueryNsdResponse nsdR = nfvoService.queryNsd(new GeneralizedQueryRequest(Utilities.buildNsdInfoFilter(nsd.getNsdIdentifier(), nsd.getVersion()), null));
					String oldNsdInfoId = nsdR.getQueryResult().get(0).getNsdInfoId();
					log.debug("Retrieved NSD Info ID: " + oldNsdInfoId);
					vsBlueprintInfo.addNsdInfoId(oldNsdInfoId);
					request.setNsdInfoIdInTranslationRules(oldNsdInfoId, nsd.getNsdIdentifier(), nsd.getVersion());
				}
			}
			vsBlueprintInfoRepository.saveAndFlush(vsBlueprintInfo);
			
			log.debug("Storing translation rules");
			List<VsdNsdTranslationRule> trs = request.getTranslationRules();
			for (VsdNsdTranslationRule tr : trs) {
				translationRuleRepository.saveAndFlush(tr);
			}
			log.debug("Translation rules saved in internal DB.");
			
			return vsbId;
			
		} catch (Exception e) {
			log.error("Something went wrong when processing SO descriptors.");
			throw new FailedOperationException("Internal error: something went wrong when processing SO descriptors.");
		}
	}
	
	@Override
	public QueryVsBlueprintResponse queryVsBlueprint(GeneralizedQueryRequest request) 
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
		log.debug("Processing request to query a VS blueprint");
		request.isValid();
		
		//At the moment the only filters accepted are:
		//1. VS Blueprint name and version
		//VSB_NAME & VSB_VERSION
		//2. VS Blueprint ID
		//VSB_ID
        //No attribute selector is supported at the moment
		
		List<VsBlueprintInfo> vsbs = new ArrayList<>();
		
		Filter filter = request.getFilter();
        List<String> attributeSelector = request.getAttributeSelector();
        if ((attributeSelector == null) || (attributeSelector.isEmpty())) {
        	Map<String, String> fp = filter.getParameters();
            if (fp.size() == 1 && fp.containsKey("VSB_ID")) {
            	String vsbId = fp.get("VSB_ID");
            	VsBlueprintInfo vsb = getVsBlueprintInfo(vsbId);
            	vsbs.add(vsb);
            	log.debug("Added VSB info for VSB ID " + vsbId);
            } else if (fp.size() == 2 && fp.containsKey("VSB_NAME") && fp.containsKey("VSB_VERSION")) {
            	String vsbName = fp.get("VSB_NAME");
            	String vsbVersion = fp.get("VSB_VERSION");
            	VsBlueprintInfo vsb = getVsBlueprintInfo(vsbName, vsbVersion);
            	vsbs.add(vsb);
            	log.debug("Added VSB info for VSB with name " + vsbName + " and version " + vsbVersion);
            } else if (fp.isEmpty()) {
            	vsbs = getAllVsBlueprintInfos();
            	log.debug("Addes all the VSB info available in DB.");
            }
            return new QueryVsBlueprintResponse(vsbs);
        } else {
            log.error("Received query VS Bluepring with attribute selector. Not supported at the moment.");
            throw new MethodNotImplementedException("Received query VS Blueprint with attribute selector. Not supported at the moment.");
        }
	}
	
	@Override
	public synchronized void deleteVsBlueprint(String vsBlueprintId)
			throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
		log.debug("Processing request to delete a VS blueprint with ID " + vsBlueprintId);
		
		if (vsBlueprintId == null) throw new MalformattedElementException("The VS blueprint ID is null");
		
		VsBlueprintInfo vsbi = getVsBlueprintInfo(vsBlueprintId);
		
		if (!(vsbi.getActiveVsdId().isEmpty())) {
			log.error("There are some VSDs associated to the VS Blueprint. Impossible to remove it.");
			throw new FailedOperationException("There are some VSDs associated to the VS Blueprint. Impossible to remove it.");
		}
		
		vsBlueprintInfoRepository.delete(vsbi.getId());
		log.debug("Removed VSB info from DB.");
		VsBlueprint vsb = getVsBlueprint(vsBlueprintId);
		vsBlueprintRepository.delete(vsb);
		log.debug("Removed VSB from DB.");
	}
	
	public synchronized void addVsdInBlueprint(String vsBlueprintId, String vsdId) 
			throws NotExistingEntityException {
		log.debug("Adding VSD " + vsdId + " to blueprint " + vsBlueprintId);
		VsBlueprintInfo vsbi = getVsBlueprintInfo(vsBlueprintId);
		vsbi.addVsd(vsdId);
		vsBlueprintInfoRepository.saveAndFlush(vsbi);
		log.debug("Added VSD " + vsdId + " to blueprint " + vsBlueprintId);
	}
	
	public synchronized void removeVsdInBlueprint(String vsBlueprintId, String vsdId) 
			throws NotExistingEntityException {
		log.debug("Removing VSD " + vsdId + " from blueprint " + vsBlueprintId);
		VsBlueprintInfo vsbi = getVsBlueprintInfo(vsBlueprintId);
		vsbi.removeVsd(vsdId);
		vsBlueprintInfoRepository.saveAndFlush(vsbi);
		log.debug("Removed VSD " + vsdId + " from blueprint " + vsBlueprintId);
	}
	
	private String storeVsBlueprint(VsBlueprint vsBlueprint) throws AlreadyExistingEntityException {
		log.debug("Onboarding VS blueprint with name " + vsBlueprint.getName() + " and version " + vsBlueprint.getVersion());
		if ( (vsBlueprintInfoRepository.findByNameAndVsBlueprintVersion(vsBlueprint.getName(), vsBlueprint.getVersion()).isPresent()) ||
				(vsBlueprintRepository.findByNameAndVersion(vsBlueprint.getName(), vsBlueprint.getVersion()).isPresent()) ) {
			log.error("VS Blueprint with name " + vsBlueprint.getName() + " and version " + vsBlueprint.getVersion() + " already present in DB.");
			throw new AlreadyExistingEntityException("VS Blueprint with name " + vsBlueprint.getName() + " and version " + vsBlueprint.getVersion() + " already present in DB.");
		}
		
		VsBlueprint target = new VsBlueprint(null, vsBlueprint.getVersion(), vsBlueprint.getName(), vsBlueprint.getDescription(), vsBlueprint.getImgUrl(), vsBlueprint.getParameters(),
				vsBlueprint.getServiceSequence(), vsBlueprint.getEndPoints(), vsBlueprint.getConfigurableParameters());
		vsBlueprintRepository.saveAndFlush(target);
		
		Long vsbId = target.getId();
		String vsbIdString = String.valueOf(vsbId);
		target.setVsBlueprintId(vsbIdString);
		vsBlueprintRepository.saveAndFlush(target);
		log.debug("Added VS Blueprint with ID " + vsbIdString);
		
		List<VsComponent> atomicComponents = vsBlueprint.getAtomicComponents();
		if (atomicComponents != null) {
			for (VsComponent c : atomicComponents) {
				VsComponent targetComponent = new VsComponent(target, c.getComponentId(), c.getServersNumber(), c.getImagesUrls(), c.getEndPointsIds(), c.getLifecycleOperations());
				vsComponentRepository.saveAndFlush(targetComponent);
			}
			log.debug("Added atomic components in VS blueprint " + vsbIdString);
		}
		
		List<VsbLink> connectivityServices = vsBlueprint.getConnectivityServices();
		if (connectivityServices != null) {
			for (VsbLink l : connectivityServices) {
				VsbLink targetLink = new VsbLink(target, l.getEndPointIds(), l.isExternal(), l.getConnectivityProperties());
				vsbLinkRepository.saveAndFlush(targetLink);
			}
			log.debug("Added connectivity services in VS blueprint " + vsbIdString);
		}
		
		VsBlueprintInfo vsBlueprintInfo = new VsBlueprintInfo(vsbIdString, vsBlueprint.getVersion(), vsBlueprint.getName());
		vsBlueprintInfoRepository.saveAndFlush(vsBlueprintInfo);
		log.debug("Added VS Blueprint Info with ID " + vsbIdString);
		
		return vsbIdString;
	}

	private VsBlueprint getVsBlueprint(String name, String version) throws NotExistingEntityException {
		if (vsBlueprintRepository.findByNameAndVersion(name, version).isPresent()) return vsBlueprintRepository.findByNameAndVersion(name, version).get();
		else throw new NotExistingEntityException("VS Blueprint with name " + name + " and version " + version + " not found in DB.");
	}
	
	private VsBlueprintInfo getVsBlueprintInfo(String name, String version) throws NotExistingEntityException {
		VsBlueprintInfo vsBlueprintInfo;
		if (vsBlueprintInfoRepository.findByNameAndVsBlueprintVersion(name, version).isPresent()) vsBlueprintInfo = vsBlueprintInfoRepository.findByNameAndVsBlueprintVersion(name, version).get();
		else throw new NotExistingEntityException("VS Blueprint info for VSB with name " + name + " and version " + version + " not found in DB.");
		VsBlueprint vsb = getVsBlueprint(name, version);
		vsBlueprintInfo.setVsBlueprint(vsb);
		return vsBlueprintInfo;
	}
	
	private VsBlueprint getVsBlueprint(String vsbId) throws NotExistingEntityException {
		if (vsBlueprintRepository.findByVsBlueprintId(vsbId).isPresent()) return vsBlueprintRepository.findByVsBlueprintId(vsbId).get();
		else throw new NotExistingEntityException("VS Blueprint with ID " + vsbId + " not found in DB.");
	}
	
	private VsBlueprintInfo getVsBlueprintInfo(String vsbId) throws NotExistingEntityException {
		VsBlueprintInfo vsBlueprintInfo;
		if (vsBlueprintInfoRepository.findByVsBlueprintId(vsbId).isPresent()) vsBlueprintInfo = vsBlueprintInfoRepository.findByVsBlueprintId(vsbId).get();
		else throw new NotExistingEntityException("VS Blueprint info for VSB with ID " + vsbId + " not found in DB.");
		VsBlueprint vsb = getVsBlueprint(vsbId);
		vsBlueprintInfo.setVsBlueprint(vsb);
		return vsBlueprintInfo;
	}
	
	private List<VsBlueprintInfo> getAllVsBlueprintInfos() throws NotExistingEntityException {
		List<VsBlueprintInfo> vsbis = vsBlueprintInfoRepository.findAll();
		for (VsBlueprintInfo vsbi : vsbis) {
			String name = vsbi.getName();
			String version = vsbi.getVsBlueprintVersion();
			VsBlueprint vsb = getVsBlueprint(name, version);
			vsbi.setVsBlueprint(vsb);
		}
		return vsbis;
	}
	
}
