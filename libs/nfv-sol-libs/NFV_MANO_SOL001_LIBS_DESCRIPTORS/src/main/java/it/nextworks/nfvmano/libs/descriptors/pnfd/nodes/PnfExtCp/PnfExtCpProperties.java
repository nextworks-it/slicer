package it.nextworks.nfvmano.libs.descriptors.pnfd.nodes.PnfExtCp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.nextworks.nfvmano.libs.common.enums.CpRole;
import it.nextworks.nfvmano.libs.common.enums.LayerProtocol;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.elements.CpProtocolData;
import it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.Cp.CpProperties;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@DiscriminatorValue("PNFEXTCP")
public class PnfExtCpProperties extends CpProperties {

    @OneToOne
    @JsonIgnore
    private PnfExtCpNode pnfExtCpNode;

    public PnfExtCpProperties() {

    }

    public PnfExtCpProperties(PnfExtCpNode pnfExtCpNode, List<LayerProtocol> layerProtocol, CpRole role, String description,
                              List<CpProtocolData> protocolData, boolean trunkMode) {
        super(null, layerProtocol, role, description, protocolData, trunkMode);
        this.pnfExtCpNode = pnfExtCpNode;
    }

    public PnfExtCpNode getPnfExtCpNode() {
        return pnfExtCpNode;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        super.isValid();
    }
}
