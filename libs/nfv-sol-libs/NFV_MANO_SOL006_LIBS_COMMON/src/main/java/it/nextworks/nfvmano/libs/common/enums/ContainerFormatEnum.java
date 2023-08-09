package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The container format describes the container file format in which software image is provided.
 */
public enum ContainerFormatEnum {
    AKI("aki"),

    AMI("ami"),

    ARI("ari"),

    BARE("bare"),

    DOCKER("docker"),

    OVA("ova"),

    OVF("ovf");

    private String value;

    ContainerFormatEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static ContainerFormatEnum fromValue(String text) {
        for (ContainerFormatEnum b : ContainerFormatEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}