/*
 * Copyright (c) 2021 Nextworks s.r.l
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


package it.nextworks.nfvmano.libs.ifa.templates.gst;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UplinkThroughputNS {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private int guaranteedUplinkThroughput=0;
    private Boolean additionalUplink=false;
    private int maxUplinkThroughput;

    public UplinkThroughputNS(){}

    public UplinkThroughputNS(int guaranteedUplinkThroughput, Boolean additionalUplink, int maxUplinkThroughput){
        this.guaranteedUplinkThroughput=guaranteedUplinkThroughput;
        this.additionalUplink=additionalUplink;
        this.maxUplinkThroughput=maxUplinkThroughput;
    }

    public void setGuaranteedUplinkThroughput(int guaranteedUplinkThroughput) {
        this.guaranteedUplinkThroughput = guaranteedUplinkThroughput;
    }

    public void setAdditionalUplink(Boolean additionalUplink) {
        this.additionalUplink = additionalUplink;
    }

    public void setMaxUplinkThroughput(int maxUplinkThroughput) {
        this.maxUplinkThroughput = maxUplinkThroughput;
    }

    public int getGuaranteedUplinkThroughput() {
        return guaranteedUplinkThroughput;
    }

    public Boolean getAdditionalUplink() {
        return additionalUplink;
    }

    public int getMaxUplinkThroughput() {
        return maxUplinkThroughput;
    }
}
