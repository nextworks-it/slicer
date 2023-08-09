package it.nextworks.nfvmano.libs.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Describes VNF lifecycle event(s) or an external stimulus detected on a VNFM reference point.
 */
public enum EventEnum {
    START_INSTANTIATION("start-instantiation"),

    END_INSTANTIATION("end-instantiation"),

    START_SCALING("start-scaling"),

    END_SCALING("end-scaling"),

    START_HEALING("start-healing"),

    END_HEALING("end-healing"),

    START_TERMINATION("start-termination"),

    END_TERMINATION("end-termination"),

    START_VNF_FLAVOUR_CHANGE("start-vnf-flavour-change"),

    END_VNF_FLAVOUR_CHANGE("end-vnf-flavour-change"),

    START_VNF_OPERATION_CHANGE("start-vnf-operation-change"),

    END_VNF_OPERATION_CHANGE("end-vnf-operation-change"),

    START_VNF_EXT_CONN_CHANGE("start-vnf-ext-conn-change"),

    END_VNF_EXT_CONN_CHANGE("end-vnf-ext-conn-change"),

    START_VNFINFO_MODIFICATION("start-vnfinfo-modification"),

    END_VNFINFO_MODIFICATION("end-vnfinfo-modification");

    private String value;

    EventEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static EventEnum fromValue(String text) {
        for (EventEnum b : EventEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}