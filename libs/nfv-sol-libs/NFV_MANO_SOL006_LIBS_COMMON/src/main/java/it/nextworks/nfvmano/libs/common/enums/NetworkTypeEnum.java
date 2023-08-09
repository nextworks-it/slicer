package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Specifies the network type for this L2 protocol. Possible values: FLAT, VLAN, VXLAN, GRE.
 */
public enum NetworkTypeEnum {
    FLAT("flat"),

    VLAN("vlan"),

    VXLAN("vxlan"),

    GRE("gre");

    private String value;

    NetworkTypeEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static NetworkTypeEnum fromValue(String text) {
        for (NetworkTypeEnum b : NetworkTypeEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}