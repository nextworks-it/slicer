package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Specifies whether the rule is an affinity rule or an anti-affinity rule.
 */
public enum AffinityTypeEnum {
    AFFINITY("affinity"),

    ANTI_AFFINITY("anti-affinity");

    private String value;

    AffinityTypeEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static AffinityTypeEnum fromValue(String text) {
        for (AffinityTypeEnum b : AffinityTypeEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}