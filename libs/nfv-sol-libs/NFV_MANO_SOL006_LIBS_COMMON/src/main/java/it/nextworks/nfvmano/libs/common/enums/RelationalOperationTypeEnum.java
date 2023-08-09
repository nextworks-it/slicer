package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RelationalOperationTypeEnum {

    GE("GE"),

    LE("LE"),

    GT("GT"),

    LT("LT"),

    EQ("EQ");

    private String value;

    RelationalOperationTypeEnum(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static RelationalOperationTypeEnum fromValue(String text) {
        for(RelationalOperationTypeEnum b : RelationalOperationTypeEnum.values()) {
            if(String.valueOf(b.value).equals(text)) {
                return b;
            }
        }

        return null;
    }
}
