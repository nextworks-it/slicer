package it.nextworks.nfvmano.test.tests;

public class SlicerTestConfiguration {

    private String dspAddress;
    private String nspOneAddress;
    private String nspTwoAddress;

    private boolean performMultidomainInstantiation;

    private boolean performVerticalServiceTermination;

    private boolean performHandoverActuation;
    private boolean performQoSActuation;
    private boolean performRedirectActuation;
    private boolean performSetSliceRanPriorityActuation;

    private int numberOfInstantiateTerminateIterations;

    private InstantiationScenario instantiationScenario;

    private int waitingTimeAfterNstOnBoarding;

    private boolean dellScenario;

    public SlicerTestConfiguration(String dspAddress,
                                   String nspOneAddress,
                                   String nspTwoAddress,
                                   boolean performMultidomainInstantiation,
                                   boolean performVerticalServiceTermination,
                                   boolean performHandoverActuation,
                                   boolean performQoSActuation,
                                   boolean performRedirectActuation,
                                   boolean performSetSliceRanPriorityActuation,
                                   int numberOfInstantiateTerminateIterations,
                                   InstantiationScenario instantiationScenario,
                                   int waitingTimeAfterNstOnBoarding,
                                   boolean dellScenario) {
        this.dspAddress = dspAddress;
        this.nspOneAddress = nspOneAddress;
        this.nspTwoAddress = nspTwoAddress;
        this.performMultidomainInstantiation = performMultidomainInstantiation;
        this.performVerticalServiceTermination = performVerticalServiceTermination;
        this.performHandoverActuation = performHandoverActuation;
        this.performQoSActuation = performQoSActuation;
        this.performRedirectActuation = performRedirectActuation;
        this.performSetSliceRanPriorityActuation = performSetSliceRanPriorityActuation;
        this.numberOfInstantiateTerminateIterations = numberOfInstantiateTerminateIterations;
        this.instantiationScenario = instantiationScenario;
        this.waitingTimeAfterNstOnBoarding = waitingTimeAfterNstOnBoarding;
        this.dellScenario = dellScenario;
    }

    public String getDspAddress() {
        return dspAddress;
    }

    public String getNspOneAddress() {
        return nspOneAddress;
    }

    public String getNspTwoAddress() {
        return nspTwoAddress;
    }

    public boolean isPerformMultidomainInstantiation() {
        return performMultidomainInstantiation;
    }

    public boolean isPerformVerticalServiceTermination() {
        return performVerticalServiceTermination;
    }

    public boolean isPerformHandoverActuation() {
        return performHandoverActuation;
    }

    public boolean isPerformQoSActuation() {
        return performQoSActuation;
    }

    public boolean isPerformRedirectActuation() {
        return performRedirectActuation;
    }

    public int getNumberOfInstantiateTerminateIterations() {
        return numberOfInstantiateTerminateIterations;
    }

    public InstantiationScenario getInstantiationScenario() {        return instantiationScenario;    }

    public int getWaitingTimeAfterNstOnBoarding() {
        return waitingTimeAfterNstOnBoarding;
    }

    public boolean isPerformSetSliceRanPriorityActuation() {
        return performSetSliceRanPriorityActuation;
    }

    public boolean isDellScenario() {
        return dellScenario;
    }
}
