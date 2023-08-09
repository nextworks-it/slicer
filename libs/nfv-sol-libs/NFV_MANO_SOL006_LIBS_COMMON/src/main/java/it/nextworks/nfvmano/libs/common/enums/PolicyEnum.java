package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The policy can take values of 'static' or 'dynamic'. In case of 'static' the virtual CPU cores are requested to be allocated to logical CPU cores according to the rules defined in virtualCpuPinningRules. In case of 'dynamic' the allocation of virtual CPU cores to logical CPU cores is decided by the VIM. (e.g. SMT (Simultaneous MultiThreading) requirements).
 */
public enum PolicyEnum {
    STATIC("static"),

    DYNAMIC("dynamic");

    private String value;

    PolicyEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static PolicyEnum fromValue(String text) {
        for (PolicyEnum b : PolicyEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}