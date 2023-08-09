package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatisticEnum {

    AVERAGE("AVERAGE"),

    MINIMUM("MINIMUM"),

    MAXIMUM("MAXIMUM"),

    COUNT("COUNT"),

    SUM("SUM");

    private String value;

    StatisticEnum(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static StatisticEnum fromValue(String text) {
        for (StatisticEnum b : StatisticEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }

        return null;
    }
}
