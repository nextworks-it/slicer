package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CniEnum {

    CALICO("calico"),

    FLANNEL("flannel"),

    MULTUS("multus");

    private String value;

    CniEnum(String value) { this.value = value; }

    @Override
    @JsonValue
    public String toString() { return String.valueOf(value); }

    @JsonCreator
    public static CniEnum fromValue(String text) {
        for(CniEnum b : CniEnum.values()) {
            if(String.valueOf(b.value).equals(text)) {
                return b;
            }
        }

        return null;
    }
}
