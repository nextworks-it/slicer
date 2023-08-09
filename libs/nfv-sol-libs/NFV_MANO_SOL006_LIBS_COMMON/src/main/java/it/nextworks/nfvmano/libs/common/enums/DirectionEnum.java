package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The direction in which the security group rule is applied. Permitted values: INGRESS, EGRESS. Defaults to INGRESS.
 */
public enum DirectionEnum {
    INGRESS("ingress"),

    EGRESS("egress");

    private String value;

    DirectionEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static DirectionEnum fromValue(String text) {
        for (DirectionEnum b : DirectionEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}