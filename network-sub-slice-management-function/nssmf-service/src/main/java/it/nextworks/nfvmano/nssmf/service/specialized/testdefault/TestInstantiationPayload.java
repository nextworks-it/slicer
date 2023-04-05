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

package it.nextworks.nfvmano.nssmf.service.specialized.testdefault;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.vs.common.nssmf.messages.provisioning.NssmfBaseProvisioningMessage;

import java.util.UUID;

public class TestInstantiationPayload extends NssmfBaseProvisioningMessage {
    @JsonProperty("instantiationDetail1")
    private String instantiationDetail1;
    @JsonProperty("instantiationDetail2")
    private String instantiationDetail2;

    public TestInstantiationPayload() {
        super();
    }

    public TestInstantiationPayload(UUID nssiId, String instantiationDetail1, String instantiationDetail2) {
        super(nssiId);
        this.instantiationDetail1 = instantiationDetail1;
        this.instantiationDetail2 = instantiationDetail2;
    }

    public String getInstantiationDetail1() {
        return instantiationDetail1;
    }

    public void setInstantiationDetail1(String instantiationDetail1) {
        this.instantiationDetail1 = instantiationDetail1;
    }

    public String getGetInstantiationDetail2() {
        return instantiationDetail2;
    }

    public void setGetInstantiationDetail2(String instantiationDetail2) {
        this.instantiationDetail2 = instantiationDetail2;
    }
}

