package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum InterfaceTypeEnum {

    PARAVIRT("PARAVIRT"),

    OM_MGMT("OM-MGMT"),

    PCI_PASSTHROUGH("PCI-PASSTHROUGH"),

    SR_IOV("SR-IOV"),

    VIRTIO("VIRTIO"),

    E1000("E1000"),

    RTL8139("RTL8139"),

    PCNET("PCNET");

    private String value;

    InterfaceTypeEnum(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static InterfaceTypeEnum fromValue(String text) {
        for (InterfaceTypeEnum b : InterfaceTypeEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
