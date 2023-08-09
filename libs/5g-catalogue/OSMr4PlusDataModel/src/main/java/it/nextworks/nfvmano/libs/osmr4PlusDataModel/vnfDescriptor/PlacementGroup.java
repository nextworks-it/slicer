package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlacementGroup {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String requirement;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String strategy;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("member-vdus")
    private List<MemberVDUReference> memberVDUs;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getRequirement() { return requirement; }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public List<MemberVDUReference> getMemberVDUs() {
        return memberVDUs;
    }

    public void setMemberVDUs(List<MemberVDUReference> memberVDUs) {
        this.memberVDUs = memberVDUs;
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
        return "PlacementGroup{" +
                "name='" + name + '\'' +
                ", requirement='" + requirement + '\'' +
                ", strategy='" + strategy + '\'' +
                ", memberVDUs=" + memberVDUs +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
