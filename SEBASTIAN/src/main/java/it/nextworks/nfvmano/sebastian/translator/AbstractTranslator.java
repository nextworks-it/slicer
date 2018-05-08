package it.nextworks.nfvmano.sebastian.translator;

import it.nextworks.nfvmano.sebastian.catalogue.repo.VsDescriptorRepository;

public abstract class AbstractTranslator implements TranslatorInterface {

	TranslatorType type;
	VsDescriptorRepository vsdRepo;
	
	
	/**
	 * Constructor
	 * 
	 * @param type Type of translator
	 * @param vsdRepo Repository of VS descriptors
	 * 
	 */
	public AbstractTranslator(TranslatorType type,
			VsDescriptorRepository vsdRepo) {
		this.type = type;
		this.vsdRepo = vsdRepo;
	}

	/**
	 * @return the type
	 */
	public TranslatorType getType() {
		return type;
	}

	/**
	 * @return the vsdRepo
	 */
	public VsDescriptorRepository getVsdRepo() {
		return vsdRepo;
	}
	
}
