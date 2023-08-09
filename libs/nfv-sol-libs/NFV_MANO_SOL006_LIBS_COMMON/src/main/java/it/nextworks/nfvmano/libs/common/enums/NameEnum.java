package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Identifies an interface produced by the VNF. Valid values: - VNF_CONFIGURATION - VNF_INDICATOR
 */
public enum NameEnum {
    CONFIGURATION("vnf-configuration"),

    INDICATOR("vnf-indicator");

    private String value;

    NameEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static NameEnum fromValue(String text) {
        for (NameEnum b : NameEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}