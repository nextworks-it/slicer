package it.nextworks.nfvmano.nsmf.ra;

import it.nextworks.nfvmano.libs.vs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.RAAlgorithmType;
import it.nextworks.nfvmano.libs.vs.common.ra.elements.ResourceAllocationPolicy;
import it.nextworks.nfvmano.libs.vs.common.ra.interfaces.ResourceAllocationPolicyMgmt;
import it.nextworks.nfvmano.libs.vs.common.ra.interfaces.ResourceAllocationProvider;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeRequest;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.compute.ResourceAllocationComputeResponse;
import it.nextworks.nfvmano.libs.vs.common.ra.messages.policy.RAPolicyMatchRequest;
import it.nextworks.nfvmano.libs.vs.common.utils.DynamicClassManager;
import it.nextworks.nfvmano.nsmf.NsLcmService;
import it.nextworks.nfvmano.nsmf.nfvcatalogue.NfvoCatalogueClient;
import it.nextworks.nfvmano.nsmf.ra.algorithms.BaseResourceAllocationAlgorithm;
import it.nextworks.nfvmano.nsmf.ra.algorithms.file.FileResourceAllocationAlgorithm;
import it.nextworks.nfvmano.nsmf.ra.algorithms.stat.StaticAlgorithmNXW;
import it.nextworks.nfvmano.nsmf.ra.algorithms.stat.record.StaticRaResponseRepository;
import it.nextworks.nfvmano.nsmf.record.elements.NetworkSliceInstanceRecord;
import it.nextworks.nfvmano.nsmf.record.repos.NetworkSliceInstanceRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResourceAllocationComputeService implements ResourceAllocationProvider {


    private static final Logger log = LoggerFactory.getLogger(ResourceAllocationComputeService.class);

    @Autowired
    private ResourceAllocationPolicyMgmt resourceAllocationPolicyMgmt;

    @Autowired
    private NetworkSliceInstanceRepo networkSliceInstanceRepo;

    @Autowired
    private NsLcmService nsLcmService;

    @Autowired
    private StaticRaResponseRepository staticRaResponseRepository;

    @Autowired
    private Environment env;

    @Autowired
    private NfvoCatalogueClient nfvoCatalogueClient;

    @Value("${ra.external.algorithm:it.nextworks.nfvmano.nsmf.ra.algorithms.file.FileResourceAllocationAlgorithm}")
    private String externalRaAlgorithm;

    @Value("${resource_allocation.default_file.path:DefaultResourceAllocation.json}")
    private String defaultRaFilePath;

    /**
     * Instantiate a specialized event handler defined by the specializedEventHandlerClass
     * @return A generic NssLcmEventHandler object
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    private BaseResourceAllocationAlgorithm instantiateRAExternalAlgorithm() throws IllegalAccessException, InstantiationException,
            ClassNotFoundException {
        return (BaseResourceAllocationAlgorithm) DynamicClassManager.instantiateFromString(this.externalRaAlgorithm);

    }
    @PostConstruct
    private void loadConfig(){
        log.info("Startup Configuration loading");
        try{
            if(!DynamicClassManager.checkClassType(this.externalRaAlgorithm, BaseResourceAllocationAlgorithm.class)){
                log.error("Error in loading class "+ this.externalRaAlgorithm + ": NOT compatible with " +
                        BaseResourceAllocationAlgorithm.class.getName() + "\n NSMF is shutting down!");
                System.exit(0);
            }
            log.debug("Class "+ this.externalRaAlgorithm + ": is compatible with " + BaseResourceAllocationAlgorithm.class.getName());

        } catch (ClassNotFoundException e) {
            log.error("Error in loading class "+ this.externalRaAlgorithm + ": class NOT Found!\nNSMF is shutting down");
            System.exit(0);
        }

    }

    @Override
    public void computeResources(ResourceAllocationComputeRequest request) throws NotExistingEntityException, FailedOperationException, MalformattedElementException, IllegalAccessException, InstantiationException,
            ClassNotFoundException {
        log.debug("Processing request to compute RA");
        NetworkSliceInstanceRecord record = networkSliceInstanceRepo.findById(UUID.fromString(request.getNsiId())).get();
        Optional<ResourceAllocationPolicy> policy = resourceAllocationPolicyMgmt.findMatchingPolicy(new RAPolicyMatchRequest(record.getNstId(), request.getTenant(), null));
        ResourceAllocationProvider algorithm=null;

        if(policy.isPresent()){
            log.debug("Using algorithm:"+policy.get().getAlgorithmType()+" from policy");
            switch(policy.get().getAlgorithmType()){
                case STATIC:
                    algorithm = new StaticAlgorithmNXW(this, staticRaResponseRepository);
                    break;
                case FILE:
                    algorithm= new FileResourceAllocationAlgorithm(this, defaultRaFilePath);
                    break;
                case EXTERNAL:
                    algorithm= this.instantiateRAExternalAlgorithm();
                    try {
                        algorithm.getClass().getMethod("setParameters", ResourceAllocationComputeService.class, Environment.class, NfvoCatalogueClient.class).invoke(algorithm, this, env, nfvoCatalogueClient);
                    }catch (NoSuchMethodException | InvocationTargetException e){
                        log.error("No such method");
                    }
                    break;
                default:
                    throw new FailedOperationException("Unkown algorithm RA type: "+policy.get().getAlgorithmType());
            }
        }
        algorithm.computeResources(request);

    }

    @Override
    public void processResourceAllocationResponse(ResourceAllocationComputeResponse response) {
        log.debug("Processing Resource Allocation Compute response");
        nsLcmService.processResoureAllocationResponse(response);
    }


}
