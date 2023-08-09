package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DataTypeEnum {

    STRING("STRING"),

    INTEGER("INTEGER"),

    BOOLEAN("BOOLEAN");

    private String value;

    DataTypeEnum(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static DataTypeEnum fromValue(String text) {
        for (DataTypeEnum b : DataTypeEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }

        return null;
    }
}
