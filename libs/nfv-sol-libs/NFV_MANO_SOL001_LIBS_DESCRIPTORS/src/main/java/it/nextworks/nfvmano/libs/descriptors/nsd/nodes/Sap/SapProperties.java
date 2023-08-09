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
package it.nextworks.nfvmano.libs.descriptors.nsd.nodes.Sap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.nextworks.nfvmano.libs.common.enums.CpRole;
import it.nextworks.nfvmano.libs.common.enums.LayerProtocol;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.elements.CpProtocolData;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.Cp.CpProperties;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("SAP")
public class SapProperties extends CpProperties {

    @OneToOne
    @JsonIgnore
    private SapNode sapNode;

    public SapProperties() {

    }

    public SapProperties(List<LayerProtocol> layerProtocol, CpRole role, String description,
                         ArrayList<CpProtocolData> protocolData, boolean trunkMode) {
        super(null, layerProtocol, role, description, protocolData, trunkMode);
    }

    public SapProperties(SapNode sapNode, List<LayerProtocol> layerProtocol, CpRole role, String description,
                         ArrayList<CpProtocolData> protocolData, boolean trunkMode) {
        super(null, layerProtocol, role, description, protocolData, trunkMode);
        this.sapNode = sapNode;
    }

    public SapNode getSapNode() {
        return sapNode;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        super.isValid();
    }
}
