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
package it.nextworks.nfvmano.nfvodriver.logging;

import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.common.messages.SubscribeRequest;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.NsLcmConsumerInterface;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.*;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmAbstractDriver;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmDriverType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Marco Capitani on 20/04/18.
 *
 * @author Marco Capitani <m.capitani AT nextworks.it>
 */
public class NfvoLcmLoggingDriver extends NfvoLcmAbstractDriver {

    private static final Logger log = LoggerFactory.getLogger(NfvoLcmLoggingDriver.class);

    public NfvoLcmLoggingDriver() {
        super(NfvoLcmDriverType.LOGGING, "", null);
    }



    public String createNsIdentifier(CreateNsIdentifierRequest createNsIdentifierRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to createNsIdentifier, parameter {}.", createNsIdentifierRequest);
        return null;
    }

    public String instantiateNs(InstantiateNsRequest instantiateNsRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to instantiateNs, parameter {}.", instantiateNsRequest);
        return null;
    }

    public String scaleNs(ScaleNsRequest scaleNsRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to scaleNs, parameter {}.", scaleNsRequest);
        return null;
    }

    public UpdateNsResponse updateNs(UpdateNsRequest updateNsRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to updateNs, parameter {}.", updateNsRequest);
        return null;
    }

    public QueryNsResponse queryNs(GeneralizedQueryRequest generalizedQueryRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to queryNs, parameter {}.", generalizedQueryRequest);
        return null;
    }

    public String terminateNs(TerminateNsRequest terminateNsRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to terminateNs, parameter {}.", terminateNsRequest);
        return null;
    }

    public void deleteNsIdentifier(String s) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to deleteNsIdentifier, parameter {}.", s);
    }

    public String healNs(HealNsRequest healNsRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to healNs, parameter {}.", healNsRequest);
        return null;
    }

    public OperationStatus getOperationStatus(String s) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to getOperationStatus, parameter {}.", s);
        return null;
    }

    public String subscribeNsLcmEvents(SubscribeRequest subscribeRequest, NsLcmConsumerInterface nsLcmConsumerInterface) throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
        log.info("Received call to subscribeNsLcmEvents, parameters {}; {}.", subscribeRequest, nsLcmConsumerInterface);
        return null;
    }

    public void unsubscribeNsLcmEvents(String s) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to unsubscribeNsLcmEvents, parameter {}.", s);
    }

    public void queryNsSubscription(GeneralizedQueryRequest generalizedQueryRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to queryNsSubscription, parameter {}.", generalizedQueryRequest);
    }
}
