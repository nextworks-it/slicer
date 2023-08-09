package it.nextworks.nfvmano.libs.descriptors.vnfd.nodes.VNF;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.interfaces.Vnflcm;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class VNFInterfaces implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private VNFNode vnfNode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "vnfInterfaces", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Vnflcm vnflcm;

    public VNFInterfaces() {
    }

    public VNFInterfaces(VNFNode vnfNode, Vnflcm vnflcm) {
        this.vnfNode = vnfNode;
        this.vnflcm = vnflcm;
    }

    public Long getId() {
        return id;
    }

    public VNFNode getVnfNode() {
        return vnfNode;
    }

    @JsonProperty("vnflcm")
    public Vnflcm getVnflcm() {
        return vnflcm;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }
}
