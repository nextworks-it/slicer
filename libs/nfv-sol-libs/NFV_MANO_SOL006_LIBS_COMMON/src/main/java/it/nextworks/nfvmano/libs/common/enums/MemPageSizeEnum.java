package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MemPageSizeEnum {

    LARGE("LARGE"),

    SMALL("SMALL"),

    SIZE_2MB("SIZE_2MB"),

    SIZE_1GB("SIZE_1GB"),

    PREFER_LARGE("PREFER_LARGE");

    private String value;

    MemPageSizeEnum(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static MemPageSizeEnum fromValue(String text) {
        for (MemPageSizeEnum b : MemPageSizeEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }

        return null;
    }
}
