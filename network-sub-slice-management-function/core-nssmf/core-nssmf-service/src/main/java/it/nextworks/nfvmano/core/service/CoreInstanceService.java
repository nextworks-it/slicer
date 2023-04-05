package it.nextworks.nfvmano.core.service;

import it.nextworks.nfvmano.core.recordIM.CoreInstanceInfo;
import it.nextworks.nfvmano.core.repo.*;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CoreInstanceService {

    @Autowired
    private CoreInstanceInfoRepository coreInstanceInfoRepository;

    private static final Logger LOG = LoggerFactory.getLogger(CoreInstanceService.class);

    public CoreInstanceService(){ }

    public CoreInstanceInfo getCoreInstanceInfo(String coreInstanceId) throws NotExistingEntityException {
        LOG.info("Getting information for core instance with ID "+coreInstanceId);
        Optional<CoreInstanceInfo> coreInstanceInfoOptional = coreInstanceInfoRepository.findByCoreInstanceId(coreInstanceId);

        if(!coreInstanceInfoOptional.isPresent())
            throw new NotExistingEntityException("Core instance with ID "+coreInstanceId+ " not found");

        return coreInstanceInfoOptional.get();
    }

    public void storeCoreCncInfo(String coreInstanceId, String ip, int port) throws NotExistingEntityException {
        LOG.info("Storing CNC Client info for core instance "+coreInstanceId);
        CoreInstanceInfo coreInstanceInfo = getCoreInstanceInfo(coreInstanceId);
        coreInstanceInfo.setIpCnc(ip);
        coreInstanceInfo.setPortCnc(port);
        coreInstanceInfoRepository.saveAndFlush(coreInstanceInfo);
    }

    public void storeCoreInstanceInfo(String coreInstanceId, String coreNetworkSliceId) {
        CoreInstanceInfo coreInstanceInfo = new CoreInstanceInfo();
        LOG.info("Storing Network slice core instance Information");
        try {
            coreInstanceInfo = getCoreInstanceInfo(coreInstanceId);
            coreInstanceInfo.addCoreNetworkSliceInfo(coreNetworkSliceId);
            LOG.info("Added network slice ID ("+coreNetworkSliceId+") for core instance "+coreInstanceId);
        }
        catch (NotExistingEntityException e) {
            LOG.info("Core Instance with ID " + coreInstanceId + "not existing. Creating a new one");
            Set<String> setCoreNetworkSlice = new HashSet<>();
            if(coreNetworkSliceId!=null)
                setCoreNetworkSlice.add(coreNetworkSliceId);
            coreInstanceInfo.setCoreInstanceId(coreInstanceId);
            coreInstanceInfo.setCoreNetworkSliceId(new ArrayList<>(setCoreNetworkSlice));
        }
        LOG.info("Saving network core slice information into database");
        coreInstanceInfoRepository.saveAndFlush(coreInstanceInfo);
    }

    public void setCoreInstanceNsdId(String coreInstanceId, String nsdId) throws NotExistingEntityException {
        LOG.info("Setting NSD ID ("+nsdId+") for core instance "+coreInstanceId);
        CoreInstanceInfo coreInstanceInfo = getCoreInstanceInfo(coreInstanceId);
        coreInstanceInfo.setNsdIdCore(nsdId);
        coreInstanceInfoRepository.saveAndFlush(coreInstanceInfo);
    }

    public void removeCoreNetworkSliceFromCoreInstance(String coreInstanceId, String coreNetworkSlice) throws NotExistingEntityException {
        LOG.info("Removing core slice instance with ID "+coreNetworkSlice+" from 5GC instance "+coreInstanceId);
        CoreInstanceInfo coreInstanceInfo = getCoreInstanceInfo(coreInstanceId);
        coreInstanceInfo.getCoreNetworkSliceId().remove(coreNetworkSlice);
        coreInstanceInfoRepository.saveAndFlush(coreInstanceInfo);
    }

    public void storeGnBInfo(String coreInstanceId, String gNBip) throws NotExistingEntityException {
        LOG.info("Storing gNB info for core instance "+coreInstanceId);
        CoreInstanceInfo coreInstanceInfo = getCoreInstanceInfo(coreInstanceId);
        coreInstanceInfo.getgNBList().add(gNBip);
        coreInstanceInfoRepository.saveAndFlush(coreInstanceInfo);
    }


    public List<CoreInstanceInfo> getAllCoreInstanceInfo() {
        return coreInstanceInfoRepository.findAll();
    }
}
