package it.nextworks.nfvmano.core.service;

import it.nextworks.nfvmano.core.recordIM.*;
import it.nextworks.nfvmano.core.repo.*;

import it.nextworks.nfvmano.core.rest.CoreInstanceRestController;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sbi.cnc.GNbConfiguration;
import it.nextworks.nfvmano.sbi.cnc.rest.CNCrestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GnbService {

    @Autowired
    private CoreInstanceService coreInstanceService;
    private static final Logger LOG = LoggerFactory.getLogger(CoreInstanceRestController.class);

    public GnbService(){}

    public void storegNBInfo(GNbConfiguration gNbConfiguration) throws MalformattedElementException, NotExistingEntityException {
       CoreInstanceInfo coreInstanceInfo = coreInstanceService.getAllCoreInstanceInfo().get(0);
       String coreInstanceId = coreInstanceInfo.getCoreInstanceId();
       LOG.info("Configuring gNB to the first instance of 5GC whose ID is "+coreInstanceId);

       CNCrestClient cnCrestClient = new CNCrestClient(coreInstanceInfo.getIpCnc(), coreInstanceInfo.getPortCnc());
       String bodyPayload = cnCrestClient.addgNB(gNbConfiguration);

        if(bodyPayload==null)
            throw new MalformattedElementException("Bad request");

       coreInstanceInfo.getgNBList().add(gNbConfiguration.getgNB_IPv4());
       coreInstanceService.storeGnBInfo(coreInstanceId, gNbConfiguration.getgNB_IPv4());
    }

}
