package it.nextworks.nfvmano;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.sebastian.nsmf.NsLcmService;
import it.nextworks.nfvmano.sebastian.vsfm.VsLcmService;
import it.nextworks.nfvmano.sebastian.vsfm.VsmfUtils;

/**
 * This class is used to link the different components of the slicer 
 * after their initialization
 * 
 * @author nextworks
 *
 */
@Service
public class SlicerConfigurator {

	@Autowired
	private NsLcmService nsLcmService;
	
	@Autowired
	private VsLcmService vsLcmService;
	
	@Autowired
    private VsmfUtils vsmfUtils;
	
	@PostConstruct
	public void configComService() {
		//in the all-in-one version the consumer of the NSMF notification is the VSMF service
		nsLcmService.setNotificationDispatcher(vsLcmService);
		vsLcmService.setNsmfLcmProvider(nsLcmService);
		vsmfUtils.setNsmfLcmProvider(nsLcmService);
	}

	
}
