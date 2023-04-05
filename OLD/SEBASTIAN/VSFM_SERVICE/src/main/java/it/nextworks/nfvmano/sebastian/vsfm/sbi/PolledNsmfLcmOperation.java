/*
 * Copyright 2018 Nextworks s.r.l.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.sebastian.vsfm.sbi;

import it.nextworks.nfvmano.catalogue.domainLayer.NspNbiType;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;

public class PolledNsmfLcmOperation {

    String operationId;
    OperationStatus expectedStatus;
    String nsiId;

    //acceptable values for operation: NSI_CREATION, NSI_TERMINATION
    String operationType;

    String domainId;

    NspNbiType domainType;

    /**
     * Constructor
     *
     * @param operationId    ID of the operation to be polled
     * @param expectedStatus expected status of the operation
     * @param nsiId          ID of the Network Slice instance this operation refers to
     * @param operationType  ID of the type of operation to be polled. Acceptable values are: NSI_CREATION, NSI_TERMINATION
     */
    public PolledNsmfLcmOperation(String operationId,
                                  OperationStatus expectedStatus,
                                  String nsiId,
                                  String operationType,
                                  String domainId,
                                  NspNbiType domainType) {
        this.operationId = operationId;
        this.expectedStatus = expectedStatus;
        this.nsiId = nsiId;
        this.operationType = operationType;
        this.domainId = domainId;
        this.domainType = domainType;
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
     * @return the nsiId
     */
    public String getNsiId() {
        return nsiId;
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

    /**
     * @return the domainType
     */
    public NspNbiType getDomainType() {
        return domainType;
    }
}
