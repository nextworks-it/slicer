package it.nextworks.nfvmano.libs.descriptors.nsd.nodes.NS;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.descriptors.interfaces.Nslcm;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class NSInterfaces implements DescriptorInformationElement {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private NSNode nsNode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "nsInterfaces", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Nslcm nslcm;

    public NSInterfaces() {
    }

    public NSInterfaces(NSNode vnfNode, Nslcm nslcm) {
        this.nsNode = nsNode;
        this.nslcm = nslcm;
    }

    public Long getId() {
        return id;
    }

    @JsonIgnore
    public NSNode getNSNode() {
        return nsNode;
    }

    @JsonProperty("nslcm")
    public Nslcm getNslcm() {
        return nslcm;
    }

    @Override
    public void isValid() throws MalformattedElementException {

    }
}
