package it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.drivers;

import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.VerticalServiceStatus;
import it.nextworks.nfvmano.sebastian.vsfm.messages.*;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.CsmfType;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.polling.VsmfLcmOperationPollingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VsmfLevelLoggingDriver extends AbstractVsmfDriver {

    private static final Logger log = LoggerFactory.getLogger(VsmfLevelLoggingDriver.class);

    private Map<String, VerticalServiceStatus> vsiStatus= new HashMap<>();

    public VsmfLevelLoggingDriver(String domain, CsmfType type, VsmfLcmOperationPollingManager pollingManager){
        super(domain, type, pollingManager);

    }
    @Override
    public String instantiateVs(InstantiateVsRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("instantiate VS");
        String vsiId = UUID.randomUUID().toString();
        vsiStatus.put(vsiId, VerticalServiceStatus.INSTANTIATED);
        this.getPollingManager().addOperation(UUID.randomUUID().toString(), OperationStatus.SUCCESSFULLY_DONE, vsiId, "VSI_CREATION", domainId);
        return vsiId;
    }

    @Override
    public QueryVsResponse queryVs(GeneralizedQueryRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("query VS");
        Filter filter = request.getFilter();
        if(filter.getParameters().containsKey("VSI_ID")){
            return new QueryVsResponse(filter.getParameters().get("VSI_ID"), "name", "description", "vsdid",
                    VerticalServiceStatus.INSTANTIATED,
                    null, null, null, null);
        }else{
            throw new MalformattedElementException("specified filter not supported");
        }

    }

    @Override
    public List<String> queryAllVsIds(GeneralizedQueryRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
       throw new MethodNotImplementedException("methdo not implemented");
    }

    @Override
    public List<VerticalServiceInstance> queryAllVsInstances(GeneralizedQueryRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        throw new MethodNotImplementedException("methdo not implemented");
    }

    @Override
    public void terminateVs(TerminateVsRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("terminate VS");
        if(vsiStatus.containsKey(request.getVsiId())){
            vsiStatus.put(request.getVsiId(), VerticalServiceStatus.TERMINATED);
        }
    }

    @Override
    public void modifyVs(ModifyVsRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("modify VS");
    }

    @Override
    public void purgeVs(PurgeVsRequest request, String domainId) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("purge VS");
    }
}
