package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PortSecurityDisableStrategyEnum {

    FULL("full"),

    ALLOW_ADDRESS_PAIRS("allow-address-pairs");

    private String value;

    PortSecurityDisableStrategyEnum(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static PortSecurityDisableStrategyEnum fromValue(String text) {
        for(PortSecurityDisableStrategyEnum b : PortSecurityDisableStrategyEnum.values()) {
            if(String.valueOf(b.value).equals(text))
                return b;
        }

        return null;
    }
}
