/*
 * Copyright (c) 2020 Nextworks s.r.l
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

package it.nextworks.nfvmano.sebastian.nsmf.sbi;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QoSService extends CPSService{

    private String qosCpBaseURl;

    public QoSService() {
        this.setCspType(CSPTypes.QOS_CP);
    }


    public void getQoSInfo(UUID sliceId){
        this.qosCpBaseURl = this.retrieveCpsUri(sliceId);
    }

    public void setQoS(UUID sliceId){

    }

    public void updateQoS(UUID sliceId){

    }
}
