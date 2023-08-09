package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

public class XPathInputParameter {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String xpath;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String label;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("default-value")
    private String defaultValue;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
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
        return "XPathInputParameter{" +
                "xpath='" + xpath + '\'' +
                ", label='" + label + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
