package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MemPolicyEnum {

    STRICT("STRICT"),

    PREFERRED("PREFERRED");

    private String value;

    MemPolicyEnum(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static MemPolicyEnum fromValue(String text) {
        for (MemPolicyEnum b : MemPolicyEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }

        return null;
    }
}
