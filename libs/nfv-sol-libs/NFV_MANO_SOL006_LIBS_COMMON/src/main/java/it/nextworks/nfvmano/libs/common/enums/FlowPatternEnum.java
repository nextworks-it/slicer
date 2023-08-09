package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Identifies the flow pattern of the connectivity (Line, Tree, Mesh).
 */
public enum FlowPatternEnum {
    LINE("line"),

    TREE("tree"),

    MESH("mesh");

    private String value;

    FlowPatternEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static FlowPatternEnum fromValue(String text) {
        for (FlowPatternEnum b : FlowPatternEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}