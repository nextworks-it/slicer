package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Specifies the scope of the rule, possible values are 'NFVI-PoP', 'Zone', 'ZoneGroup', 'NFVI-node'.
 */
public enum AffinityScopeEnum {
    NFVI_NODE("nfvi-node"),

    ZONE_GROUP("zone-group"),

    ZONE("zone"),

    NFVI_POP("nfvi-pop");

    private String value;

    AffinityScopeEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static AffinityScopeEnum fromValue(String text) {
        for (AffinityScopeEnum b : AffinityScopeEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}