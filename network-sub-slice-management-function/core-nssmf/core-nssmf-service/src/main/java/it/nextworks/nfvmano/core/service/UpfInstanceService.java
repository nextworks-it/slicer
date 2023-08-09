package it.nextworks.nfvmano.core.service;

import it.nextworks.nfvmano.core.recordIM.UpfInstanceInfo;
import it.nextworks.nfvmano.core.repo.*;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UpfInstanceService {

    @Autowired
    private UpfInstanceInfoRepository upfInstanceInfoRepository;

    private static final Logger LOG = LoggerFactory.getLogger(UpfInstanceService.class);

    private int upfCount = 0;

    public UpfInstanceService(){ }

    public UpfInstanceInfo getUpfInstanceInfoByName(String upfInstanceName) throws NotExistingEntityException {
        LOG.info("Getting information for UPF instance with name "+upfInstanceName);
        for(UpfInstanceInfo upfInstanceInfo: getAllUpfInstanceInfo()){
            if(upfInstanceInfo.getUpfName().equals(upfInstanceName)){
                return upfInstanceInfo;
            }
        }
        throw new NotExistingEntityException("UPF instance with name "+upfInstanceName+ "not found");
    }

    public UpfInstanceInfo getUpfInstanceInfoById(String upfInstanceById) throws NotExistingEntityException {
        LOG.info("Getting information for UPF instance with ID "+upfInstanceById);
        for(UpfInstanceInfo upfInstanceInfo: getAllUpfInstanceInfo()){
            if(upfInstanceInfo.getUpfInstanceId().equals(upfInstanceById)){
                return upfInstanceInfo;
            }
        }
        throw new NotExistingEntityException("UPF instance with ID "+upfInstanceById+ "not found");
    }

    public void storeCoreCncInfo(String upfInstanceId, String ip, int port) throws NotExistingEntityException {
        LOG.info("Storing CNC Client info for core instance "+upfInstanceId);
        UpfInstanceInfo upfInstanceInfo = getUpfInstanceInfoById(upfInstanceId);
        upfInstanceInfo.setIpCnc(ip);
        upfInstanceInfo.setPortCnc(port);
        upfInstanceInfoRepository.saveAndFlush(upfInstanceInfo);
    }

    public void storeUpfInstanceInfo(String upfInstanceId, String coreNetworkSliceId, String upfName) {
        UpfInstanceInfo upfInstanceInfo = new UpfInstanceInfo();
        LOG.info("Storing Network slice UPF instance information");
        try {
            upfInstanceInfo = getUpfInstanceInfoById(upfInstanceId);
            upfInstanceInfo.addCoreNetworkSliceInfo(coreNetworkSliceId);
            if(upfName!=null)
                upfInstanceInfo.setUpfName(upfName);

            LOG.info("Added network slice ID ("+coreNetworkSliceId+") for UPF instance "+upfInstanceId);
        }
        catch (NotExistingEntityException e) {
            LOG.info("UPF Instance with ID " + upfInstanceId + "not existing. Creating a new one");
            Set<String> setCoreNetworkSlice = new HashSet<>();
            if(coreNetworkSliceId!=null)
                setCoreNetworkSlice.add(coreNetworkSliceId);
            upfInstanceInfo.setUpfInstanceId(upfInstanceId);
            upfInstanceInfo.setUpfNetworkSliceId(new ArrayList<>(setCoreNetworkSlice));
        }
        LOG.info("Saving network core slice information into database");
        upfInstanceInfoRepository.saveAndFlush(upfInstanceInfo);
    }

    public void setUpfInstanceNsdId(String upfInstanceId, String nsdId) throws NotExistingEntityException {
        LOG.info("Setting NSD ID ("+nsdId+") for UPF instance "+upfInstanceId);
        UpfInstanceInfo upfInstanceInfo = getUpfInstanceInfoById(upfInstanceId);
        upfInstanceInfo.setNsdIdCore(nsdId);
        upfInstanceInfoRepository.saveAndFlush(upfInstanceInfo);
    }

    public void removeCoreNetworkSliceFromUpfInstance(String upfInstanceId, String coreNetworkSlice) throws NotExistingEntityException {
        LOG.info("Removing core slice instance with ID "+coreNetworkSlice+" from UPF instance "+upfInstanceId);
        UpfInstanceInfo upfInstanceInfo = getUpfInstanceInfoById(upfInstanceId);
        upfInstanceInfo.getUpfNetworkSliceId().remove(coreNetworkSlice);
        upfInstanceInfoRepository.saveAndFlush(upfInstanceInfo);
    }

    public void storeGnBInfo(String coreInstanceId, String gNBip) throws NotExistingEntityException {
        LOG.info("Storing gNB info for core instance "+coreInstanceId);
        UpfInstanceInfo upfInstanceInfo = getUpfInstanceInfoById(coreInstanceId);
        upfInstanceInfo.getgNBList().add(gNBip);
        upfInstanceInfoRepository.saveAndFlush(upfInstanceInfo);
    }


    public List<UpfInstanceInfo> getAllUpfInstanceInfo() {
        return upfInstanceInfoRepository.findAll();
    }

    public int getUpfCount() {
        return upfCount;
    }

    public void setUpfCount(int upfCount) {
        this.upfCount = upfCount;
    }
}
