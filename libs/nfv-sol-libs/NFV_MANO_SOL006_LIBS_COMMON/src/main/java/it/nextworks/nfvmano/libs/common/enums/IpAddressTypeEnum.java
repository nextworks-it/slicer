package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Define address type. The address type should be aligned with the address type supported by the layerProtocol attribute of the parent VnfExtCpd.
 */
public enum IpAddressTypeEnum {
    IPV4("ipv4"),

    IPV6("ipv6");

    private String value;

    IpAddressTypeEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static IpAddressTypeEnum fromValue(String text) {
        for (IpAddressTypeEnum b : IpAddressTypeEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}