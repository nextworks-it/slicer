package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ScalingCriteriaOperationEnum {

    AND("AND"),

    OR("OR");

    private String value;

    ScalingCriteriaOperationEnum(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static ScalingCriteriaOperationEnum fromValue(String text) {
        for(ScalingCriteriaOperationEnum b: ScalingCriteriaOperationEnum.values()) {
            if(String.valueOf(b.value).equals(text)) {
                return b;
            }
        }

        return null;
    }
}
