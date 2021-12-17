package it.nextworks.nfvmano.sebastian.vsfm.sbi.dummy;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.catalogues.template.repo.ConfigurationRuleRepository;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.*;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import it.nextworks.nfvmano.sebastian.record.elements.NsSapInfo;
import it.nextworks.nfvmano.sebastian.vsfm.VsLcmService;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.AbstractNsmfDriver;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.NsmfType;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.dummy.elements.DummyTranslationInformation;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.dummy.repos.DummyTranslationInformationRepository;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DummyRestClient extends AbstractNsmfDriver {

    private static final Logger log = LoggerFactory.getLogger(DummyRestClient.class);

    private CommonUtils utils;
    private ObjectMapper mapper;

    private VsLcmService vsLcmService;
    private DummyTranslationInformationRepository dummyTranslationInformationRepository;
    private ConfigurationRuleRepository configurationRuleRepository;

    public DummyRestClient(String domainId, CommonUtils utils, VsLcmService vsLcmService, DummyTranslationInformationRepository dummyTranslationInformationRepository, ConfigurationRuleRepository configurationRuleRepository) {
        super(NsmfType.DUMMY, domainId);
        this.utils = utils;
        this.mapper = new ObjectMapper();

        this.vsLcmService = vsLcmService;
        this.dummyTranslationInformationRepository = dummyTranslationInformationRepository;
        this.configurationRuleRepository = configurationRuleRepository;
    }

    @Override
    public String createNetworkSliceIdentifier(CreateNsiIdRequest request, String domainId, String tenantId) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("Processing request to create a new network slicer identifier");
        request.isValid();

        UUID nsiId = UUID.randomUUID();

        DummyTranslationInformation dummyTranslationInformation = new DummyTranslationInformation(nsiId.toString(), request.getNstId(), domainId);
        Long id = dummyTranslationInformationRepository.saveAndFlush(dummyTranslationInformation).getId();

        return nsiId.toString();
    }

    @Override
    public void instantiateNetworkSlice(InstantiateNsiRequest request, String domainId, String tenantId) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("Processing request to instantiate a new network slice");
        request.isValid();

        NetworkSliceStatusChangeNotification notification = new NetworkSliceStatusChangeNotification(request.getNsiId(), NetworkSliceStatusChange.NSI_CREATED, true);
        vsLcmService.notifyNetworkSliceStatusChange(notification);
    }

    @Override
    public void modifyNetworkSlice(ModifyNsiRequest request, String domainId, String tenantId) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        throw new MethodNotImplementedException("ModifyNetworkSlice operation not yet supported");
    }

    @Override
    public void terminateNetworkSliceInstance(TerminateNsiRequest request, String domainId, String tenantId) throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException, MalformattedElementException, NotPermittedOperationException {
        log.debug("Processing request to terminate a new network slice");
        request.isValid();

        Optional<DummyTranslationInformation> optional = dummyTranslationInformationRepository.findByNsiId(request.getNsiId());

        if (optional.isPresent()) {
            DummyTranslationInformation dummyTranslationInformation = optional.get();
            dummyTranslationInformationRepository.delete(dummyTranslationInformation);
        } else {
            throw new FailedOperationException("NSI with nsiId " + request.getNsiId() + " not present in DB");
        }

        NetworkSliceStatusChangeNotification notification = new NetworkSliceStatusChangeNotification(request.getNsiId(), NetworkSliceStatusChange.NSI_TERMINATED, true);
        vsLcmService.notifyNetworkSliceStatusChange(notification);
    }

    @Override
    public List<NetworkSliceInstance> queryNetworkSliceInstance(GeneralizedQueryRequest request, String domainId, String tenantId) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException {
        log.debug("Processing request to query a new network slice");
        request.isValid();

        String nsiId = null;
        List<NetworkSliceInstance> networkSliceInstances = new ArrayList<>();

        if (request.getFilter().getParameters().containsKey("NSI_ID"))
            nsiId = request.getFilter().getParameters().get("NSI_ID");
        if (nsiId != null) {
            Optional<DummyTranslationInformation> optional = dummyTranslationInformationRepository.findByNsiId(nsiId);
            if (optional.isPresent()) {
                DummyTranslationInformation dummyTranslationInformation = optional.get();
                NetworkSliceInstance networkSliceInstance = new NetworkSliceInstance();
                networkSliceInstance.setNsiId(dummyTranslationInformation.getNsiId());
                networkSliceInstance.setSoManaged(false);
                NsSapInfo nsSapInfo = new NsSapInfo(null,"prova",null,"0.0.0.0");
                List<NsSapInfo> nsSapInfos = new ArrayList<>();
                nsSapInfos.add(nsSapInfo);
                networkSliceInstance.setSapInfo(nsSapInfos);
                networkSliceInstances.add(networkSliceInstance);
            }
        }

        return networkSliceInstances;
    }

    @Override
    public void configureNetworkSliceInstance(ConfigureNsiRequest request, String domainId, String tenantId) throws MethodNotImplementedException, FailedOperationException, MalformattedElementException{
        //throw new MethodNotImplementedException("Day1 configuration currently not supported");
        log.debug("Received configuration request\nnstId: " + request.getNsiId()
        + "\nnsstId: " + request.getNsstId() + "\nparams: " + request.getParameters());

        NetworkSliceStatusChangeNotification notification = new NetworkSliceStatusChangeNotification(request.getNsiId(), NetworkSliceStatusChange.NSI_CONFIGURED, true);
        vsLcmService.notifyNetworkSliceStatusChange(notification);
    }
}
