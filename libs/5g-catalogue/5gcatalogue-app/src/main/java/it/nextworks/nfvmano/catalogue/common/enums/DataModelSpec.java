package it.nextworks.nfvmano.catalogue.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DataModelSpec {

    SOL001("SOL001"),

    SOL006("SOL006");

    private final String value;

    DataModelSpec(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static DataModelSpec fromValue(String text) {
        for(DataModelSpec dms : DataModelSpec.values()) {
            if(String.valueOf(dms.value).equals(text))
                return dms;
        }

        return null;
    }
}
