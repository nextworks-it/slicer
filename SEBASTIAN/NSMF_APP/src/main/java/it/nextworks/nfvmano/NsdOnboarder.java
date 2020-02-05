package it.nextworks.nfvmano;

import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnboardNsdRequest;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.SecurityParameters;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

//Class used only for SS-O NMR-O integration purposes.
//It on boards an existing NSD available on OSM.
// Please consider to use only for this purpose.
// It will be deleted once The catalogue interface to OSM (via NMR-O) will be implemented.

@Service
public class NsdOnboarder {

    @Autowired
    private NfvoCatalogueService nfvoCatalogueService;

    private Nsd nsd;
    private static final Logger log = LoggerFactory.getLogger(NfvoCatalogueService.class);

    public void onBoardCustomNsd(){
        nsd = new Nsd("09f0f070-11a8-4fc3-8a47-253508673a99",
                "SSSA-NXW","1.0",
                "nsName","09f0f070-11a8-4fc3-8a47-253508673a99",
                new ArrayList<String>(),  new ArrayList<String>(),
                new ArrayList<String>(), new SecurityParameters());
        try {
            log.info("On boarding custom Nsd with id: "+nsd.getId());
            log.info("nfvoCatalogueService is null {}",nfvoCatalogueService==null);
            String nsdId=nfvoCatalogueService.onboardNsd(new OnboardNsdRequest(nsd, new HashMap<>()));
            log.info("nsd UIID generated "+nsdId);
        } catch (MethodNotImplementedException e) {
            e.printStackTrace();
        } catch (MalformattedElementException e) {
            e.printStackTrace();
        } catch (AlreadyExistingEntityException e) {
            e.printStackTrace();
        } catch (FailedOperationException e) {
            e.printStackTrace();
        }
    }
}
