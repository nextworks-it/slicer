package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DeviceBusEnum {

    IDE("ide"),

    USB("usb"),

    VIRTIO("virtio"),

    SCSI("scsi");

    private String value;

    DeviceBusEnum(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static DeviceBusEnum fromValue(String text) {
        for (DeviceBusEnum b : DeviceBusEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }

        return null;
    }
}
