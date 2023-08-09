package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The disk format of a software image is the format of the underlying disk image.
 */
public enum DiskFormatEnum {
    AKI("aki"),

    AMI("ami"),

    ARI("ari"),

    ISO("iso"),

    QCOW2("qcow2"),

    RAW("raw"),

    VDI("vdi"),

    VHD("vhd"),

    VHDX("vhdx"),

    VMDK("vmdk");

    private String value;

    DiskFormatEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static DiskFormatEnum fromValue(String text) {
        for (DiskFormatEnum b : DiskFormatEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
