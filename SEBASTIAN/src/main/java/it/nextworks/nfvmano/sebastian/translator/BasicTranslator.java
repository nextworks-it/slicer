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
package it.nextworks.nfvmano.sebastian.translator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsDescriptor;
import it.nextworks.nfvmano.sebastian.catalogue.elements.VsdNsdTranslationRule;
import it.nextworks.nfvmano.sebastian.catalogue.repo.VsDescriptorRepository;

public class BasicTranslator extends AbstractTranslator {
	
	private static final Logger log = LoggerFactory.getLogger(BasicTranslator.class);
	
	private TranslationRuleRepository ruleRepo;

	public BasicTranslator(VsDescriptorRepository vsdRepo,
			TranslationRuleRepository ruleRepo) {
		super(TranslatorType.BASIC_TRANSLATOR, vsdRepo);
		this.ruleRepo = ruleRepo;
	}
	
	@Override
	public Map<String, NfvNsInstantiationInfo> translateVsd(List<String> vsdIds)
			throws FailedOperationException, NotExistingEntityException {
		log.debug("VSD->NSD translation at basic translator.");
		if (vsdIds.size() > 1) throw new FailedOperationException("Processing of multiple VSDs not supported by the basic translator");
		Map<String, NfvNsInstantiationInfo> nfvNsInfo = new HashMap<>();
		Map<String, VsDescriptor> vsds = retrieveVsDescriptors(vsdIds);
		for (Map.Entry<String, VsDescriptor> entry : vsds.entrySet()) {
			String vsdId = entry.getKey();
			VsDescriptor vsd = entry.getValue();
			VsdNsdTranslationRule rule = findMatchingTranslationRule(vsd);
			NfvNsInstantiationInfo info = new NfvNsInstantiationInfo(rule.getNsdId(), rule.getNsdVersion(), rule.getNsFlavourId(), rule.getNsInstantiationLevelId());
			nfvNsInfo.put(vsdId, info);
			log.debug("Added NS instantiation info for VSD " + vsdId + " - NSD ID: " + rule.getNsdId() + " - NSD version: " + rule.getNsdVersion() + " - DF ID: " 
					+ rule.getNsFlavourId() + " - IL ID: " + rule.getNsInstantiationLevelId());
		}
		return nfvNsInfo;
	}
	
	private VsdNsdTranslationRule findMatchingTranslationRule(VsDescriptor vsd) throws FailedOperationException, NotExistingEntityException {
		String vsbId = vsd.getVsBlueprintId();
		Map<String, String> vsdParameters = vsd.getQosParameters();
		if ((vsbId == null) || (vsdParameters.isEmpty())) throw new NotExistingEntityException("Impossible to translate VSD into NSD because of missing parameters");
		List<VsdNsdTranslationRule> rules = ruleRepo.findByVsbId(vsbId);
		for (VsdNsdTranslationRule rule : rules) {
			if (rule.matchesVsdParameters(vsdParameters)) {
				log.debug("Found translation rule for VSD " + vsd.getVsDescriptorId());
				return rule;
			}
		}
		log.debug("Impossible to find a translation rule matching the VSD parameters for VSD " + vsd.getVsDescriptorId());
		throw new FailedOperationException("Impossible to find a translation rule matching the VSD parameters for VSD " + vsd.getVsDescriptorId());
	}
	
	
	/**
	 * This internal method returns a map with key VSD ID and value VSD, where the VSDs
	 * are retrieved from the DB.
	 * 
	 * @param vsdIds IDs of the VSDs to be retrieved
	 * @return Map with key VSD ID and value VSD.
	 * @throws NotExistingEntityException if one or more VSDs are not found.
	 */
	private Map<String, VsDescriptor> retrieveVsDescriptors(List<String> vsdIds) throws NotExistingEntityException {
		log.debug("Retrieving VS descriptors from DB.");
		Map<String, VsDescriptor> vsds = new HashMap<>();
		for (String vsdId : vsdIds) {
			Optional<VsDescriptor> vsd = vsdRepo.findByVsDescriptorId(vsdId);
			if (vsd.isPresent()) vsds.put(vsdId, vsd.get());
			else throw new NotExistingEntityException("Unable to find VSD with ID " + vsdId + " in DB.");
		}
		return vsds;
	}

}
