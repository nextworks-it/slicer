package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Describe the source of the indicator. The possible values are: • VNF. • EM. • Both. This tells the consumer where to send the subscription request.
 */
public enum SourceEnum {
    VNF("vnf"),

    EM("em"),

    BOTH("both");

    private String value;

    SourceEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static SourceEnum fromValue(String text) {
        for (SourceEnum b : SourceEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}