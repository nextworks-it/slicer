package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class MemberVDUReference {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("member-vdu-ref")
    private String memberVduRef;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getMemberVduRef() {
        return memberVduRef;
    }

    public void setMemberVduRef(String memberVduRef) {
        this.memberVduRef = memberVduRef;
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return otherProperties;
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        otherProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "MemberVDUReference{" +
                "memberVduRef='" + memberVduRef + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
