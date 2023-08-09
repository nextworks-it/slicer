package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OperationEnum {

    GE("GE"),

    LE("LE"),

    GT("GT"),

    LT("LT"),

    EQ("EQ");

    private String value;

    OperationEnum(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static OperationEnum fromValue(String text) {
        for (OperationEnum b : OperationEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }

        return null;
    }
}
