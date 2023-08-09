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
package it.nextworks.nfvmano.libs.descriptors.elements.lcm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VNF.VNFProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class VnfLcmOperationsConfiguration implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private VNFProperties vnfProperties;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "config", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private InstantiateVnfOpConfig instantiate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "config", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ScaleVnfOpConfig scale;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "config", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ScaleVnfToLevelOpConfig scaleToLevel;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "config", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ChangeVnfFlavourOpConfig changeFlavour;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "config", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private HealVnfOpConfig heal;

    @Embedded
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OperateVnfOpConfig operate;

    @Embedded
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TerminateVnfOpConfig terminate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "config", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ChangeExtVnfConnectivityOpConfig changeExtConnectivity;

    public VnfLcmOperationsConfiguration() {
    }

    public VnfLcmOperationsConfiguration(InstantiateVnfOpConfig instantiate, ScaleVnfOpConfig scale, ScaleVnfToLevelOpConfig scaleToLevel, ChangeVnfFlavourOpConfig changeFlavour, HealVnfOpConfig heal, OperateVnfOpConfig operate, TerminateVnfOpConfig terminate, ChangeExtVnfConnectivityOpConfig changeExtConnectivity) {
        this.instantiate = instantiate;
        this.scale = scale;
        this.scaleToLevel = scaleToLevel;
        this.changeFlavour = changeFlavour;
        this.heal = heal;
        this.operate = operate;
        this.terminate = terminate;
        this.changeExtConnectivity = changeExtConnectivity;
    }

    public VnfLcmOperationsConfiguration(VNFProperties vnfProperties, InstantiateVnfOpConfig instantiate, ScaleVnfOpConfig scale, ScaleVnfToLevelOpConfig scaleToLevel, ChangeVnfFlavourOpConfig changeFlavour, HealVnfOpConfig heal, OperateVnfOpConfig operate, TerminateVnfOpConfig terminate, ChangeExtVnfConnectivityOpConfig changeExtConnectivity) {
        this.vnfProperties = vnfProperties;
        this.instantiate = instantiate;
        this.scale = scale;
        this.scaleToLevel = scaleToLevel;
        this.changeFlavour = changeFlavour;
        this.heal = heal;
        this.operate = operate;
        this.terminate = terminate;
        this.changeExtConnectivity = changeExtConnectivity;
    }

    @JsonProperty("instatiate")
    public InstantiateVnfOpConfig getInstantiate() {
        return instantiate;
    }

    @JsonProperty("scale")
    public ScaleVnfOpConfig getScale() {
        return scale;
    }

    @JsonProperty("scaleToLevel")
    public ScaleVnfToLevelOpConfig getScaleToLevel() {
        return scaleToLevel;
    }

    @JsonProperty("heal")
    public HealVnfOpConfig getHeal() {
        return heal;
    }

    @JsonProperty("operate")
    public OperateVnfOpConfig getOperate() {
        return operate;
    }

    @JsonProperty("terminate")
    public TerminateVnfOpConfig getTerminate() {
        return terminate;
    }

    @JsonProperty("changeFlavour")
    public ChangeVnfFlavourOpConfig getChangeFlavour() {
        return changeFlavour;
    }

    @JsonProperty("changeExtConnectivity")
    public ChangeExtVnfConnectivityOpConfig getChangeExtConnectivity() {
        return changeExtConnectivity;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (operate != null)
            operate.isValid();
        if (instantiate != null)
            instantiate.isValid();
        if (terminate != null)
            terminate.isValid();
        if (scale != null)
            scale.isValid();
        if (scaleToLevel != null)
            scaleToLevel.isValid();
        if (heal != null)
            heal.isValid();
        if (changeFlavour != null)
            changeFlavour.isValid();
        if (changeExtConnectivity != null)
            changeExtConnectivity.isValid();
    }

}
