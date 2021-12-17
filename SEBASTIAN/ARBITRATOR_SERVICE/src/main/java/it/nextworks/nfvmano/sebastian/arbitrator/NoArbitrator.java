package it.nextworks.nfvmano.sebastian.arbitrator;

import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.catalogue.translator.TranslatorService;
import it.nextworks.nfvmano.catalogues.template.services.NsTemplateCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueService;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorRequest;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorResponse;
import it.nextworks.nfvmano.sebastian.common.VirtualResourceCalculatorService;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.record.VsRecordService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NoArbitrator extends AbstractArbitrator{

    @Override
    public List<ArbitratorResponse> computeArbitratorSolution(List<ArbitratorRequest> requests) throws FailedOperationException, NotExistingEntityException {
        List<ArbitratorResponse> responses = new ArrayList<>();
        for(ArbitratorRequest request: requests){
            ArbitratorResponse response = new ArbitratorResponse(request.getRequestId(), true, true, "", false, new HashMap<>(),
                    new HashMap<>(), new HashMap<>());
            responses.add(response);
        }

        return responses;
    }

    public NoArbitrator(AdminService adminService, VsRecordService vsRecordService, VsDescriptorCatalogueService vsDescriptorCatalogueService, TranslatorService translatorService, NfvoCatalogueService nfvoCatalogueService, NsTemplateCatalogueService nsTemplateCatalogueService, VirtualResourceCalculatorService virtualResourceCalculatorService, NsmfLcmProviderInterface nsmfLcmProvider) {
        super(adminService, vsRecordService, vsDescriptorCatalogueService, translatorService, nfvoCatalogueService, nsTemplateCatalogueService, ArbitratorType.NO_ARBITRATOR, virtualResourceCalculatorService, nsmfLcmProvider);
    }

    @Override
    public List<ArbitratorResponse> arbitrateVsScaling(List<ArbitratorRequest> requests) throws FailedOperationException, NotExistingEntityException, MethodNotImplementedException {
        throw  new MethodNotImplementedException();
    }
}
