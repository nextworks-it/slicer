package it.nextworks.nfvmano.libs.descriptors.sol006;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class VnfdVip {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("paired-interfaces")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "vnfd_vip_fk", referencedColumnName = "uuid")
    private List<VnfdVipPairedInterfaces> pairedInterfaces = null;

    public VnfdVip name(String VnfdVip) {
        this.name = name;
        return this;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public VnfdVip pairedInterfaces(List<VnfdVipPairedInterfaces> pairedInterfaces) {
        this.pairedInterfaces = pairedInterfaces;
        return this;
    }

    public VnfdVip addPairedInterfacesItem(VnfdVipPairedInterfaces pairedInterface) {
        if(this.pairedInterfaces == null)
            this.pairedInterfaces = new ArrayList<>();

        this.pairedInterfaces.add(pairedInterface);
        return this;
    }

    public List<VnfdVipPairedInterfaces> getPairedInterfaces() { return pairedInterfaces; }

    public void setPairedInterfaces(List<VnfdVipPairedInterfaces> pairedInterfaces) {
        this.pairedInterfaces = pairedInterfaces;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VnfdVip vnfdVip = (VnfdVip) o;

        return Objects.equals(this.name, vnfdVip.name) &&
                Objects.equals(this.pairedInterfaces, vnfdVip.pairedInterfaces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pairedInterfaces);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("class VnfdVip {\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    pairedInterfaces: ").append(toIndentedString(pairedInterfaces)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
