package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CpuThreadPinningPolicyEnum {

    AVOID("AVOID"),

    SEPARATE("SEPARATE"),

    ISOLATE("ISOLATE"),

    PREFER("PREFER");

    private String value;

    CpuThreadPinningPolicyEnum(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static CpuThreadPinningPolicyEnum fromValue(String text) {
        for (CpuThreadPinningPolicyEnum b : CpuThreadPinningPolicyEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }

        return null;
    }
}
