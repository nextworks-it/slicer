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
package it.nextworks.nfvmano.nssmf.handler.ran.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.ifa.templates.nst.SliceProfile;
import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;

import java.util.Map;

public class ORanPayloadWrapper {

    @JsonProperty("sliceProfile")
    private SliceProfile sliceProfile;
    /**
     * it should contain the ueId, groupId, sliceId, qosId and cellId as key
     * here they are used as string but then are to be converted into integer excepted for ueId
     */
    @JsonProperty("additionalParams")
    private Map<String, String> additionalParams;

    public ORanPayloadWrapper(){}

    public ORanPayloadWrapper(SliceProfile sliceProfile, Map<String,String> additionalParams){
        this.sliceProfile=sliceProfile;
        this.additionalParams=additionalParams;
    }

    public void isValid() throws MalformattedElementException {
        if(sliceProfile==null || additionalParams==null)
            throw new MalformattedElementException("Slice profile and additional parameters cannot be null");

        for(String key: additionalParams.keySet()) {
            switch (key) {
                case "ueId":
                case "groupId":
                case "sliceId":
                case "qosId":
                case "cellId":
                    break;
                default:
                    throw new MalformattedElementException("Not permitted parameters");
            }
        }
    }

    public SliceProfile getSliceProfile() {
        return sliceProfile;
    }

    public void setSliceProfile(SliceProfile sliceProfile) {
        this.sliceProfile = sliceProfile;
    }

    public Map<String, String> getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(Map<String, String> additionalParams) {
        this.additionalParams = additionalParams;
    }
}
