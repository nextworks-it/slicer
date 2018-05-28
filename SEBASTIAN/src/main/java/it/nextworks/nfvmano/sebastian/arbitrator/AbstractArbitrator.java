package it.nextworks.nfvmano.sebastian.arbitrator;

import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.nfvodriver.NfvoService;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.sebastian.translator.TranslatorService;

/**
 * This is the abstract class for the arbitrator.
 * It must be extended through the specific arbitrators implementing the specific algorithms.
 * 
 * @author nextworks
 *
 */
public abstract class AbstractArbitrator implements ArbitratorInterface {

	AdminService adminService;
	
	VsRecordService vsRecordService;
	
	TranslatorService translatorService;
	
	NfvoService nfvoService;
	
	ArbitratorType type;
	
	

	/**
	 * @param adminService
	 * @param vsRecordService
	 * @param translatorService
	 * @param nfvoService
	 * @param type
	 */
	public AbstractArbitrator(AdminService adminService, VsRecordService vsRecordService,
			TranslatorService translatorService, NfvoService nfvoService, ArbitratorType type) {
		this.adminService = adminService;
		this.vsRecordService = vsRecordService;
		this.translatorService = translatorService;
		this.nfvoService = nfvoService;
		this.type = type;
	}

	/**
	 * @return the adminService
	 */
	public AdminService getAdminService() {
		return adminService;
	}

	/**
	 * @return the vsRecordService
	 */
	public VsRecordService getVsRecordService() {
		return vsRecordService;
	}

	/**
	 * @return the translatorService
	 */
	public TranslatorService getTranslatorService() {
		return translatorService;
	}

	/**
	 * @return the type
	 */
	public ArbitratorType getType() {
		return type;
	}
	
	
	
}
