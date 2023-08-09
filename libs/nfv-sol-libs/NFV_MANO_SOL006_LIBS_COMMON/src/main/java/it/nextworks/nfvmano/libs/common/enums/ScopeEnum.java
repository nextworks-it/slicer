package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Specifies the scope of the affinity or anti-affinity relationship e.g. a NFVI node, an NFVI PoP, etc.
 */
public enum ScopeEnum {
    NFVI_NODE("nfvi-node"),

    ZONE_GROUP("zone-group"),

    ZONE("zone"),

    NFVI_POP("nfvi-pop");

    private String value;

    ScopeEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static ScopeEnum fromValue(String text) {
        for (ScopeEnum b : ScopeEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}