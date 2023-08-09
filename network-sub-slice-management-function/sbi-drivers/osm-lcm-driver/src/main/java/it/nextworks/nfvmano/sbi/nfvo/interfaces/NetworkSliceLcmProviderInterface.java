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
package it.nextworks.nfvmano.sbi.nfvo.interfaces;


import it.nextworks.nfvmano.libs.vs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.sbi.nfvo.messages.*;
import it.nextworks.nfvmano.sbi.nfvo.osm.rest.model.NsInstance;
import it.nextworks.nfvmano.sbi.nfvo.osm.rest.model.VnfInstanceInfo;

public interface NetworkSliceLcmProviderInterface {


    String createNetworkSliceIdentifier(CreateNetworkSliceIdentifierRequestInternal request) throws FailedOperationException;

    String instantiateNetworkSlice(InstantiateNetworkSliceRequestInternal request) throws FailedOperationException;


    NsInstance getNetworkSliceInstance(String nsInstanceId) throws  FailedOperationException;


    OperationStatus getOperationStatus(String operationId) throws FailedOperationException;

    String terminateNetworkSlice(String networkSliceInstanceId) throws FailedOperationException;
}
