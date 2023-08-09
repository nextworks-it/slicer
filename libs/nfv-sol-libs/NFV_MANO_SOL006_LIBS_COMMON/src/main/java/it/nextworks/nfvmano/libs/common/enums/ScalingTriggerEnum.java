package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ScalingTriggerEnum {

    PRE_SCALE_IN("pre-scale-in"),

    POST_SCALE_IN("post-scale-in"),

    PRE_SCALE_OUT("pre-scale-out"),

    POST_SCALE_OUT("post-scale-out");

    private String value;

    ScalingTriggerEnum(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static ScalingTriggerEnum fromValue(String text) {
        for(ScalingTriggerEnum b : ScalingTriggerEnum.values()) {
            if(String.valueOf(b.value).equals(text)) {
                return b;
            }
        }

        return null;
    }
}
