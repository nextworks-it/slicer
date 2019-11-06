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

package it.nextworks.nfvmano.sebastian.nsmf.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModifyNsiRequestMessage extends EngineMessage {
    @JsonProperty("nsiId")
    private String nsiId;

    @JsonProperty("nfvNsdId")
    private String nfvNsdId;

    @JsonProperty("nfvNsdVersion")
    private String nfvNsdVersion;

    @JsonProperty("dfId")
    private String dfId;

    @JsonProperty("ilId")
    private String ilId;

    @JsonProperty("vsiId")
    private String vsiId;


    /**
     *  @param nsiId
     * @param nfvNsdId
     * @param nfvNsdVersion
     * @param dfId
     * @param ilId
     * @param vsiId
     */
    @JsonCreator
    public ModifyNsiRequestMessage(@JsonProperty("nsiId") String nsiId,
                                   @JsonProperty("nfvNsdId") String nfvNsdId,
                                   @JsonProperty("nfvNsdVersion") String nfvNsdVersion,
                                   @JsonProperty("dfId") String dfId,
                                   @JsonProperty("ilId") String ilId,
                                   @JsonProperty("vsiId") String vsiId) {
        this.type = EngineMessageType.MODIFY_NSI_REQUEST;
        this.nsiId = nsiId;
        this.nfvNsdId = nfvNsdId;
        this.nfvNsdVersion = nfvNsdVersion;
        this.dfId = dfId;
        this.ilId = ilId;
        this.vsiId = vsiId;

    }

    /**
     *
     * @return nsiId
     */
    public String getNsiId() {
        return nsiId;
    }

    /**
     *
     * @return nfvNsdId
     */
    public String getNfvNsdId() {
        return nfvNsdId;
    }

    /**
     *
     * @return nfvNsdVersion
     */
    public String getNfvNsdVersion() {
        return nfvNsdVersion;
    }

    /**
     *
     * @return dfId
     */
    public String getDfId() {
        return dfId;
    }

    /**
     *
     * @return ilId
     */
    public String getIlId() {
        return ilId;
    }

    /**
     *
     * @return vsiId
     */
    public String getVsiId() {
        return vsiId;
    }
}
