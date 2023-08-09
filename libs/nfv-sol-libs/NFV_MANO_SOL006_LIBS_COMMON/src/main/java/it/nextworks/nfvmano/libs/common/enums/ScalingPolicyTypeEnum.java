package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ScalingPolicyTypeEnum {

    MANUAL("manual"),

    AUTOMATIC("automatic");

    private String value;

    ScalingPolicyTypeEnum(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static ScalingPolicyTypeEnum fromValue(String text) {
        for(ScalingPolicyTypeEnum b : ScalingPolicyTypeEnum.values()) {
            if(String.valueOf(b.value).equals(text)) {
                return b;
            }
        }

        return null;
    }
}
