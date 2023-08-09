package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ServiceAvailabilityLevelEnum {

    LEVEL1("level-1"),

    LEVEL2("level-2"),

    LEVEL3("level-3");

    private String value;

    ServiceAvailabilityLevelEnum(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static ServiceAvailabilityLevelEnum fromValue(String text) {
        for (ServiceAvailabilityLevelEnum b : ServiceAvailabilityLevelEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
