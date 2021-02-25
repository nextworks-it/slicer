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
package it.nextworks.nfvmano.sebastian.record.elements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class NetworkSliceSubnetInstance {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;


    @ManyToOne
    @JsonIgnore
    private VerticalServiceInstance verticalServiceInstance;


    private String nssiId;
    private String nsstId;
    private String nsDeploymentFlavorId;
    private String nsInstantiationLevelId;
    private String domainId;
    private NetworkSliceStatus status;


    //Indicates the High-level vnf placement information requested to the NSMF used in 5Growth
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, NetworkSliceVnfPlacement> vnfPlacement= new HashMap<>();

    public NetworkSliceSubnetInstance() {
    }

    public NetworkSliceSubnetInstance(String nssiId, String nsstId, String domainId, String nsDeploymentFlavorId, String nsInstantiationLevelId, NetworkSliceStatus status, Map<String, NetworkSliceVnfPlacement> vnfPlacement) {
        this.nssiId = nssiId;
        this.nsstId = nsstId;
        this.domainId = domainId;
        this.status = status;
        if(vnfPlacement!=null) this.vnfPlacement=vnfPlacement;
        this.nsDeploymentFlavorId=nsDeploymentFlavorId;
        this.nsInstantiationLevelId = nsInstantiationLevelId;
    }

    /**
     * @return the nssiId
     */
    public String getNssiId() {
        return nssiId;
    }

    public String getNsDeploymentFlavorId() {
        return nsDeploymentFlavorId;
    }

    public String getNsInstantiationLevelId() {
        return nsInstantiationLevelId;
    }

    /**
     * @return the nsstId
     */
    public String getNsstId() {
        return nsstId;
    }

    /**
     * @return the domainId
     */
    public String getDomainId() {
        return domainId;
    }

    /**
     * @return the status
     */
    public NetworkSliceStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(NetworkSliceStatus status) {
        this.status = status;
    }


    public Map<String, NetworkSliceVnfPlacement> getVnfPlacement(){
        return vnfPlacement;
    }

    public void setVnfPlacement(Map<String, NetworkSliceVnfPlacement> vnfPlacement){
        this.vnfPlacement= vnfPlacement;
    }

    public void setNsDeploymentFlavorId(String nsDeploymentFlavorId) {
        this.nsDeploymentFlavorId = nsDeploymentFlavorId;
    }

    public void setNsInstantiationLevelId(String nsInstantiationLevelId) {
        this.nsInstantiationLevelId = nsInstantiationLevelId;
    }
}
