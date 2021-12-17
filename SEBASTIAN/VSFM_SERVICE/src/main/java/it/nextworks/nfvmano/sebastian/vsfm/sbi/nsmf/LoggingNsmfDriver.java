package it.nextworks.nfvmano.sebastian.vsfm.sbi.nsmf;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.*;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.AbstractNsmfDriver;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.NsmfType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoggingNsmfDriver extends AbstractNsmfDriver {
    private static final Logger log = LoggerFactory.getLogger(LoggingNsmfDriver.class);
    private String lastId;

    public LoggingNsmfDriver(String domainId) {

        super(NsmfType.DUMMY, domainId);
    }

    @Override
    public String createNetworkSliceIdentifier(CreateNsiIdRequest request, String domainId, String tenantId) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("createNetworkSliceIdentifier");
        String id = UUID.randomUUID().toString();
        log.debug("generated id:"+id);
        lastId=id;
        return id;
    }

    @Override
    public void instantiateNetworkSlice(InstantiateNsiRequest request, String domainId, String tenantId) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("instantiateNetworkSlice");
    }

    @Override
    public void modifyNetworkSlice(ModifyNsiRequest request, String domainId, String tenantId) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("modifyNetworkSlice");
    }

    @Override
    public void terminateNetworkSliceInstance(TerminateNsiRequest request, String domainId, String tenantId) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("terminateNetworkSliceInstance");
    }

    @Override
    public List<NetworkSliceInstance> queryNetworkSliceInstance(GeneralizedQueryRequest request, String domainId, String tenantId) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException {
        log.debug("queryNetworkSliceInstance");
        ArrayList<NetworkSliceInstance> instances = new ArrayList<>();
        instances.add(new NetworkSliceInstance(lastId,
                "nstId",
                "nsdId",
                "nsdVersion",
                "dfId",
                "ilId",
                "nfvNsId",
                null,
                tenantId,
                "logging",
                "description",
                true,
                null
                ));
        return instances;
    }

    @Override
    public void configureNetworkSliceInstance(ConfigureNsiRequest request, String domainId, String tenantId) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException{
        log.debug("configureNetworkSliceInstance");
    }
}
