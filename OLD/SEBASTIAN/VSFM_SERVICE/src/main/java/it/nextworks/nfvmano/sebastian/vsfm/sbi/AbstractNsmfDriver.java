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

import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;


public abstract class AbstractNsmfDriver implements NsmfLcmProviderInterface {

    private NsmfType type;

    private String domainId;

    public AbstractNsmfDriver(NsmfType type,
                              String domainId) {
        this.type = type;
        this.domainId = domainId;
    }

    /**
     * @return the type
     */
    public NsmfType getType() {
        return type;
    }

    /**
     * @return the domainId
     */
    public String getDomainId() {
        return domainId;
    }


}
