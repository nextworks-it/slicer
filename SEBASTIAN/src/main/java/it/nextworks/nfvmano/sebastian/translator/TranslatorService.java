package it.nextworks.nfvmano.sebastian.translator;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.catalogue.repo.VsDescriptorRepository;

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
	public List<NfvNsInstantiationInfo> translateVsd(List<String> vsdIds)
			throws FailedOperationException, NotExistingEntityException {
		return translator.translateVsd(vsdIds);
	}
}
