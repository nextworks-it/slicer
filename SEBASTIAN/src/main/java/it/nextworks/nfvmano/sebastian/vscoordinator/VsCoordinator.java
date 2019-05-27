/*
 * Copyright (c) 2019 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.nextworks.nfvmano.sebastian.vscoordinator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.common.VsAction;
import it.nextworks.nfvmano.sebastian.common.VsActionType;
import it.nextworks.nfvmano.sebastian.engine.Engine;
import it.nextworks.nfvmano.sebastian.engine.messages.*;
import it.nextworks.nfvmano.sebastian.vsnbi.messages.TerminateVsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class VsCoordinator {
    private static final Logger log = LoggerFactory.getLogger(VsCoordinator.class);
    private String vsiCoordinatorId;
    private Engine engine;
    private Map<String, VsAction> candidateVsis;
    private VsCoordinatorStatus internalStatus;

    public VsCoordinator() {

    }

    public VsCoordinator(String vsiCoordinatorId, Engine engine){
        this.vsiCoordinatorId = vsiCoordinatorId;
        this.engine = engine;
        this.internalStatus = VsCoordinatorStatus.READY;
    }

    /**
     * Method used to receive messages about VSIs to be updated/terminated from the Rabbit MQ
     *
     * @param message received message
     */
    public void receiveMessage(String message) {
        log.debug("Received message for VSI " + vsiCoordinatorId + "\n" + message);

        try {
            ObjectMapper mapper = new ObjectMapper();
            EngineMessage em = mapper.readValue(message, EngineMessage.class);
            EngineMessageType type = em.getType();

            switch (type) {
                case COORDINATE_VSI_REQUEST: {

                    log.debug("Processing VSI coordination request.");
                    CoordinateVsiRequest coordinateVsiRequest = (CoordinateVsiRequest) em;
                    processCoordinateRequest(coordinateVsiRequest);
                    break;
                }
                case NOTIFY_TERMINATION:{
                    log.debug("Processing VSI termination notification.");
                    VsiTerminationNotificationMessage vsiTerminationNotificationMessage = (VsiTerminationNotificationMessage) em;
                    processTerninationNofification(vsiTerminationNotificationMessage);
                    break;
                }

                default:
                    log.error("Received message with not supported type. Skipping.");
                    break;
            }

        } catch (JsonParseException e) {
            manageVsCoordinatorError("Error while parsing message: " + e.getMessage());
        } catch (JsonMappingException e) {
            manageVsCoordinatorError("Error in Json mapping: " + e.getMessage());
        } catch (IOException e) {
            manageVsCoordinatorError("IO error when receiving json message: " + e.getMessage());
        } catch (Exception e){
            manageVsCoordinatorError("Generic exception occurred: " + e.getMessage());
        }

    }

    synchronized void processCoordinateRequest(CoordinateVsiRequest msg){
        if (internalStatus != VsCoordinatorStatus.READY) {
            manageVsCoordinatorError("Received coordinate request in wrong status. Skipping message.");
            return;
        }
        candidateVsis = msg.getCandidateVsis();
        internalStatus = VsCoordinatorStatus.COORDINATION_IN_PROGRESS;
        try{
            for(Map.Entry<String, VsAction> candidateVsi: candidateVsis.entrySet()) {
                VsAction action = candidateVsi.getValue();
                if (action.getActionType() == VsActionType.TERMINATE) {
                    engine.terminateVs(action.getVsiId(), new TerminateVsRequest(action.getVsiId(), "coordinator"));
                }

            }
            internalStatus = VsCoordinatorStatus.FINISHED;
        } catch (NotExistingEntityException e){
            manageVsCoordinatorError("Error while terminating VSI by Coordinator: " + e.getMessage());
        }
    }

    synchronized void processTerninationNofification(VsiTerminationNotificationMessage msg) throws Exception{
        String vsiId = msg.getVsiId();
        if(candidateVsis.containsKey(vsiId)) {
            candidateVsis.remove(vsiId);
            if (candidateVsis.isEmpty()) {
                engine.notifyVsCoordinationEnd(vsiCoordinatorId);
            }
        }
    }


    private void manageVsCoordinatorError(String errorMessage) {
        log.error(errorMessage);

    }


    public synchronized Map<String, VsAction> getCandidateVsis() {
        return candidateVsis;
    }
}
