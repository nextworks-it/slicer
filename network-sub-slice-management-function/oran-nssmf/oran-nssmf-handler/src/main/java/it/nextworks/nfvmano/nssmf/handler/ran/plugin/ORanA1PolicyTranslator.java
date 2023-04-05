/*
 * Copyright (c) 2021 Nextworks s.r.l.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.nssmf.handler.ran.plugin;

import it.nextworks.nfvmano.libs.ifa.templates.nst.EMBBPerfReq;
import it.nextworks.nfvmano.libs.ifa.templates.nst.SliceProfile;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.nssmf.handler.ran.plugin.exception.ImpossibleToTranslateException;
import it.nextworks.nfvmano.oran.policies.elements.Policy;
import it.nextworks.nfvmano.oran.policies.elements.QosTarget;
import it.nextworks.nfvmano.oran.policies.elements.ScopeIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ORanA1PolicyTranslator {
    private final static Logger log = LoggerFactory.getLogger(ORanA1PolicyTranslator.class);

    public ORanA1PolicyTranslator(){}

    public Policy fromSliceProfileToPolicy(SliceProfile sliceProfile, Map<String, String> additionalParams) throws MalformattedElementException, ImpossibleToTranslateException {
        log.debug("Receive request to translate slice profile with ID {}", sliceProfile.getSliceProfileId());

        if(sliceProfile.geteMBBPerfReq()==null && sliceProfile.getuRLLCPerfReq()!=null)
            throw new ImpossibleToTranslateException("Translation for URLLC slice not permitted yet");

        ScopeIdentifier scope=new ScopeIdentifier();
        for(String key: additionalParams.keySet()){
            log.debug("the key is {}", key);
            switch (key) {
                case "ueId":
                    scope.setUeId(additionalParams.get(key));
                    break;
                case "groupId":
                    scope.setGroupId(Integer.parseInt(additionalParams.get(key)));
                    break;
                case "sliceId":
                    scope.setSliceId(Integer.parseInt(additionalParams.get(key)));
                    break;
                case "qosId":
                    scope.setQosId(Integer.parseInt(additionalParams.get(key)));
                    break;
                case "cellId":
                    scope.setCellId(Integer.parseInt(additionalParams.get(key)));
                    break;
                default:
                    throw new MalformattedElementException("Not permitted parameters");
            }
        }

        List<QosTarget> qosTargets=new ArrayList<>();
        for(EMBBPerfReq req: sliceProfile.geteMBBPerfReq()){
            QosTarget target=new QosTarget();
            target.setPdb(sliceProfile.getLatency());
            int gfbr= (req.getExpDataRateDL()+ req.getExpDataRateUL())/2;
            int mfbr=(req.getAreaTrafficCapDL()+ req.getAreaTrafficCapUL())/2;
            target.setGfbr(gfbr);
            target.setMfbr(mfbr);
            qosTargets.add(target);
        }

        Policy policy=new Policy();
        policy.setPolicyId(UUID.randomUUID().toString());
        policy.setScope(scope);
        policy.setQosObjectives(qosTargets);
        policy.isValid();

        log.debug("Generated policy with ID {}", policy.getPolicyId());
        return policy;
    }
}
