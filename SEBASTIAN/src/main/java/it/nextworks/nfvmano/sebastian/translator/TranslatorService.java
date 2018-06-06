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

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.catalogue.repo.VsDescriptorRepository;

/**
 * This is the service that implements the Vertical Slicer VSD/NSD translation functions.
 * It loads the specific translator algorithm specified in the configuration.
 * 
 * @author nextworks
 *
 */
@Service
public class TranslatorService implements TranslatorInterface {

	private static final Logger log = LoggerFactory.getLogger(TranslatorService.class);

	@Value("${translator.type}")
	private String translatorType;
	
	@Autowired
	private VsDescriptorRepository vsDescriptorRepository;
	
	@Autowired
	private TranslationRuleRepository translationRuleRepository;
	
	private AbstractTranslator translator;
	
	public TranslatorService() { }
	
	@PostConstruct
	public void initTranslatorService() {
		log.debug("Initializing translator");
		if (translatorType.equals("BASIC")) {
			log.debug("The Vertical Slicer is configured to operate with a basic translator.");
			translator = new BasicTranslator(vsDescriptorRepository, translationRuleRepository);
		} else {
			log.error("Translator not configured!");
		}
	}
	
	@Override
	public Map<String, NfvNsInstantiationInfo> translateVsd(List<String> vsdIds)
			throws FailedOperationException, NotExistingEntityException {
		return translator.translateVsd(vsdIds);
	}
}
