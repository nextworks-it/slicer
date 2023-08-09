package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Specifies IPv6 address mode. Possible values: • SLAAC. • DHCPV6-STATEFUL. • DHCPV6-STATELESS. May be present when the value of the ipVersion attribute is 'IPV6' and shall be absent otherwise.
 */
public enum Ipv6AddressModeEnum {
    SLAAC("slaac"),

    DHCPV6_STATEFUL("dhcpv6-stateful"),

    DHCPV6_STATELESS("dhcpv6-stateless");

    private String value;

    Ipv6AddressModeEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static Ipv6AddressModeEnum fromValue(String text) {
        for (Ipv6AddressModeEnum b : Ipv6AddressModeEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}