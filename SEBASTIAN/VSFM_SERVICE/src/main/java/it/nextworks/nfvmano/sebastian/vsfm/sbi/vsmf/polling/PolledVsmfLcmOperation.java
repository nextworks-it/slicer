package it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.polling;

import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;

public class PolledVsmfLcmOperation {
    String operationId;
    OperationStatus expectedStatus;
    String vsiId;

    //acceptable values for operation: VSI_CREATION, VSI_TERMINATION
    String operationType;

    String domainId;



    /**
     * Constructor
     *
     * @param operationId    ID of the operation to be polled
     * @param expectedStatus expected status of the operation
     * @param vsiId          ID of the Network Slice instance this operation refers to
     * @param operationType  ID of the type of operation to be polled. Acceptable values are: NSI_CREATION, NSI_TERMINATION
     */
    public PolledVsmfLcmOperation(String operationId,
                                  OperationStatus expectedStatus,
                                  String vsiId,
                                  String operationType,
                                  String domainId) {
        this.operationId = operationId;
        this.expectedStatus = expectedStatus;
        this.vsiId = vsiId;
        this.operationType = operationType;
        this.domainId = domainId;

    }

    /**
     * @return the operationId
     */
    public String getOperationId() {
        return operationId;
    }

    /**
     * @return the expectedStatus
     */
    public OperationStatus getExpectedStatus() {
        return expectedStatus;
    }

    /**
     * @return the vsiId
     */
    public String getVsiId() {
        return vsiId;
    }

    /**
     * @return the operationType
     */
    public String getOperationType() {
        return operationType;
    }

    /**
     * @return the domainId
     */
    public String getDomainId() {
        return domainId;
    }

}
