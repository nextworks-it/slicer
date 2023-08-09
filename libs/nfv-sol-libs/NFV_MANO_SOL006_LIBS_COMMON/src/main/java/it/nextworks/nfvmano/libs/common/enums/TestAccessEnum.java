package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TestAccessEnum {

    NONE("none"),

    PASSIVE_MONITORING("passive-monitoring"),

    ACTIVE("active");

    private String value;

    TestAccessEnum(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static TestAccessEnum fromValue(String text) {
        for (TestAccessEnum b : TestAccessEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
