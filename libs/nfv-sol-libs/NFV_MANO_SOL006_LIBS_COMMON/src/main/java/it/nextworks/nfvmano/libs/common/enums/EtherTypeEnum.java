package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Indicates the protocol carried over the Ethernet layer. Permitted values: IPV4, IPV6. Defaults to IPV4.
 */
public enum EtherTypeEnum {
    IPV4("ipv4"),

    IPV6("ipv6");

    private String value;

    EtherTypeEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static EtherTypeEnum fromValue(String text) {
        for (EtherTypeEnum b : EtherTypeEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}