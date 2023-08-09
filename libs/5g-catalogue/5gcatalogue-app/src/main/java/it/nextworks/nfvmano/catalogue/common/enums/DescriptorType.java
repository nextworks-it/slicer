package it.nextworks.nfvmano.catalogue.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DescriptorType {

    VNFD("vnfd"),

    PNFD("pnfd"),

    NSD("nsd");

    private final String value;

    DescriptorType(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static DescriptorType fromValue(String text) {
        for(DescriptorType dt : DescriptorType.values()) {
            if(String.valueOf(dt.value).equals(text))
                return dt;
        }

        return null;
    }
}
