package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class Interface {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer position;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("mgmt-interface")
    private boolean mgmtInterface;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("mac-address")
    private String macAddress;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("internal-connection-point-ref")
    private String intConnPointRef;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("external-connection-point-ref")
    private String extConnPointRef;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("virtual-interface")
    private VirtualInterface virtualInterface;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public boolean isMgmtInterface() {
        return mgmtInterface;
    }

    public void setMgmtInterface(boolean mgmtInterface) {
        this.mgmtInterface = mgmtInterface;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getIntConnPointRef() {
        return intConnPointRef;
    }

    public void setIntConnPointRef(String intConnPointRef) {
        this.intConnPointRef = intConnPointRef;
    }

    public String getExtConnPointRef() {
        return extConnPointRef;
    }

    public void setExtConnPointRef(String extConnPointRef) {
        this.extConnPointRef = extConnPointRef;
    }

    public VirtualInterface getVirtualInterface() {
        return virtualInterface;
    }

    public void setVirtualInterface(VirtualInterface virtualInterface) {
        this.virtualInterface = virtualInterface;
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return otherProperties;
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        otherProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Interface{" +
                "name='" + name + '\'' +
                ", position=" + position +
                ", mgmtInterface=" + mgmtInterface +
                ", type='" + type + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", intConnPointRef='" + intConnPointRef + '\'' +
                ", extConnPointRef='" + extConnPointRef + '\'' +
                ", virtualInterface=" + virtualInterface +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
