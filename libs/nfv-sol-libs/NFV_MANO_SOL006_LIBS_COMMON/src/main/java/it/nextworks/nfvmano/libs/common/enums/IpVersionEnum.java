package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Specifies IP version of this L3 protocol. Value: • IPV4. • IPV6.
 */
public enum IpVersionEnum {
    IPV4("ipv4"),

    IPV6("ipv6");

    private String value;

    IpVersionEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static IpVersionEnum fromValue(String text) {
        for (IpVersionEnum b : IpVersionEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}