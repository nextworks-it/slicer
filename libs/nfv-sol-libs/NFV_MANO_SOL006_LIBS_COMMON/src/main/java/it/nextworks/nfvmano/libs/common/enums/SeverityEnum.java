package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SeverityEnum {

    LOW("LOW"),

    MODERATE("MODERATE"),

    CRITICAL("CRITICAL");

    private String value;

    SeverityEnum(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static SeverityEnum fromValue(String text) {
        for (SeverityEnum b : SeverityEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }

        return null;
    }
}
