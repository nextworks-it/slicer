package it.nextworks.nfvmano.test;


import it.nextworks.nfvmano.test.tests.InstantiationScenario;
import it.nextworks.nfvmano.test.tests.SlicerE2ETest;
import it.nextworks.nfvmano.test.tests.SlicerTestConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;




@Service
public class ServiceStartup {

    @Value( "${dsp.address}" )
    private String dspAddress;

    @Value( "${nspOne.address}" )
    private String nspOneAddress;

    @Value( "${nspTwo.address}" )
    private String nspTwoAddress;

    @Value( "${multidomainInstantiation}" )
    private boolean multidomainInstantiation;

    @Value( "${verticalSliceTermination}" )
    private boolean verticalSliceTermination;

    @Value( "${handoverActuation}" )
    private boolean handoverActuation;

    @Value( "${qosActuation}" )
    private boolean qosActuation;

    @Value( "${redirectActuation}" )
    private boolean redirectActuation;

    @Value( "${setRanSlicePriorityActuation}" )
    private boolean setRanSlicePriorityActuation;

    @Value( "${instantiationScenario}" )
    private InstantiationScenario instantiationScenario;

    @Value( "${numberOfInstantiateAndTerminateCycles}" )
    private int numberOfInstantiateAndTerminateCycles;

    @Value( "${waitingTimeAfterNstOnBoarding}" )
    private int waitingTimeAfterNstOnBoarding;

    @Value( "${isDellScenario}" )
    private boolean isDellScenario;
    private static final Logger log = LoggerFactory.getLogger(ServiceStartup.class);

    @PostConstruct
    public void configComService() {
        //Once start, it executes tests
        log.info("\n\n");
        log.info("Test: configurations: ");
        log.info("dspAddress: "+dspAddress);
        log.info("nspOneAddress: "+nspOneAddress);
        log.info("nspTwoAddress: "+nspTwoAddress);
        log.info("multidomainInstantiation: "+multidomainInstantiation);
        log.info("verticalSliceTermination: "+verticalSliceTermination);
        log.info("handoverActuation: "+handoverActuation);
        log.info("qosActuation: "+qosActuation);
        log.info("redirectActuation: "+redirectActuation);
        log.info("setRanSlicePriorityActuation: "+setRanSlicePriorityActuation);
        log.info("instantiationScenario: "+instantiationScenario);
        log.info("waitingTimeAfterNstOnBoarding: "+waitingTimeAfterNstOnBoarding);
        log.info("numberOfInstantiateAndTerminateCycles: "+numberOfInstantiateAndTerminateCycles+"\n");

        SlicerTestConfiguration slicerTestConfiguration = new SlicerTestConfiguration(
                dspAddress,
                nspOneAddress,
                nspTwoAddress,
                multidomainInstantiation,
                verticalSliceTermination,

                handoverActuation,
                qosActuation,
                redirectActuation,
                setRanSlicePriorityActuation,

                numberOfInstantiateAndTerminateCycles,
                instantiationScenario,
                waitingTimeAfterNstOnBoarding,
                isDellScenario);
       SlicerE2ETest slicerE2ETest = new SlicerE2ETest(slicerTestConfiguration);

        slicerE2ETest.testVerticalServiceInstanceLifeCycle();
    }
}
