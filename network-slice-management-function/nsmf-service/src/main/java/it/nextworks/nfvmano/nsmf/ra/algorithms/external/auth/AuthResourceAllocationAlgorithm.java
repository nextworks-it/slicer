package it.nextworks.nfvmano.nsmf.ra.algorithms.external.auth;

import it.nextworks.nfvmano.libs.vs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.RAAlgorithmType;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeRequest;
import it.nextworks.nfvmano.nsmf.nfvcatalogue.NfvoCatalogueClient;
import it.nextworks.nfvmano.nsmf.ra.ResourceAllocationComputeService;
import it.nextworks.nfvmano.nsmf.ra.algorithms.BaseResourceAllocationAlgorithm;
import it.nextworks.nfvmano.nsmf.ra.algorithms.external.auth.driver.AuthRequestTranslator;
import it.nextworks.nfvmano.nsmf.ra.algorithms.external.auth.elements.AuthExternalAlgorithmRequest;
import it.nextworks.nfvmano.nsmf.ra.algorithms.rest.client.ResourceAllocationRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

public class AuthResourceAllocationAlgorithm extends BaseResourceAllocationAlgorithm {
    private static final RAAlgorithmType type =RAAlgorithmType.EXTERNAL;
    private static final Logger log = LoggerFactory.getLogger(AuthResourceAllocationAlgorithm.class);
    private String baseUrl;
    private AuthRequestTranslator translator;
    private ResourceAllocationRestClient restClient;
    private Environment env;

    public AuthResourceAllocationAlgorithm(){super();}

    public AuthResourceAllocationAlgorithm(ResourceAllocationComputeService resourceAllocationComputeService, Environment env, NfvoCatalogueClient nfvoCatalogueClient) {
        super(resourceAllocationComputeService, type);
        this.env=env;
        this.baseUrl= env.getProperty("base.url.auth");
        this.translator=new AuthRequestTranslator(nfvoCatalogueClient);
        this.restClient=new ResourceAllocationRestClient(baseUrl);
    }

    /**
     * Method required in all EXTERNAL Resource allocation algorithm to set parameters after dynamic instantiation of the algorithm
     *
     * @param resourceAllocationComputeService service to be pass to superclass
     * @param env Environment to get value from application properties
     * @param nfvoCatalogueClient NFVO Catalogue Client to retrieve NSD and VNFD
     */
    public void setParameters(ResourceAllocationComputeService resourceAllocationComputeService, Environment env, NfvoCatalogueClient nfvoCatalogueClient){
        setResourceAllocationComputeService(resourceAllocationComputeService);
        setType(type);
        this.env=env;
        this.baseUrl= env.getProperty("base.url.auth");
        this.translator=new AuthRequestTranslator(nfvoCatalogueClient);
        this.restClient=new ResourceAllocationRestClient(baseUrl);
    }

    @Override
    public void computeResources(ResourceAllocationComputeRequest resourceAllocationComputeRequest) throws NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.debug("Received request to compute new resource allocation with AUTH external algorithm");

        AuthExternalAlgorithmRequest authRequest=translator.translateRAComputeRequest(resourceAllocationComputeRequest);

        String responseCode=restClient.computeResourceAllocation(authRequest);
    }
}
