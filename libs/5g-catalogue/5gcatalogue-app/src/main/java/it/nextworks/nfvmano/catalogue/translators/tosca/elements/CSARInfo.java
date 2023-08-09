package it.nextworks.nfvmano.catalogue.translators.tosca.elements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.catalogue.common.enums.DataModelSpec;
import it.nextworks.nfvmano.catalogue.common.enums.DescriptorType;
import it.nextworks.nfvmano.libs.descriptors.sol006.Nsd;
import it.nextworks.nfvmano.libs.descriptors.sol006.Pnfd;
import it.nextworks.nfvmano.libs.descriptors.sol006.Vnfd;
import it.nextworks.nfvmano.libs.descriptors.templates.DescriptorTemplate;

public class CSARInfo {

    @JsonIgnore
    private DescriptorTemplate mst;

    @JsonIgnore
    private Vnfd vnfd;

    @JsonIgnore
    private Pnfd pnfd;

    @JsonIgnore
    private Nsd nsd;

    @JsonProperty("dataModelSpec")
    private DataModelSpec dataModelSpec;

    @JsonProperty("packageFilename")
    private String packageFilename;

    @JsonProperty("descriptorFilename")
    private String descriptorFilename;

    @JsonProperty("metaFilename")
    private String metaFilename;

    @JsonProperty("mfFilename")
    private String mfFilename;

    public CSARInfo() {
    }

    public DescriptorTemplate getMst() {
        return mst;
    }

    public void setMst(DescriptorTemplate mst) { this.mst = mst; }

    public Vnfd getVnfd() { return vnfd; }

    public void setVnfd(Vnfd vnfd) { this.vnfd = vnfd; }

    public Pnfd getPnfd() { return pnfd; }

    public void setPnfd(Pnfd pnfd) { this.pnfd = pnfd; }

    public Nsd getNsd() { return nsd; }

    public void setNsd(Nsd nsd) { this.nsd = nsd; }

    public DataModelSpec getDataModelSpec() { return dataModelSpec; }

    public void setDataModelSpec(DataModelSpec dataModelSpec) { this.dataModelSpec = dataModelSpec; }

    public String getPackageFilename() {
        return packageFilename;
    }

    public void setPackageFilename(String packageFilename) {
        this.packageFilename = packageFilename;
    }

    public String getDescriptorFilename() {
        return descriptorFilename;
    }

    public void setDescriptorFilename(String descriptorFilename) {
        this.descriptorFilename = descriptorFilename;
    }

    public String getMetaFilename() {
        return metaFilename;
    }

    public void setMetaFilename(String metaFilename) {
        this.metaFilename = metaFilename;
    }

    public String getMfFilename() {
        return mfFilename;
    }

    public void setMfFilename(String mfFilename) {
        this.mfFilename = mfFilename;
    }
}
