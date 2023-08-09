package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class ServicePrimitiveParameter {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("data-type")
    private String dataType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean mandatory;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("default-value")
    private String defaultValue;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("parameter-pool")
    private String parameterPool;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("read-only")
    private boolean readOnly ;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean hidden;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getParameterPool() {
        return parameterPool;
    }

    public void setParameterPool(String parameterPool) {
        this.parameterPool = parameterPool;
    }

    public boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
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
        return "ServicePrimitiveParameter{" +
                "name='" + name + '\'' +
                ", dataType='" + dataType + '\'' +
                ", mandatory=" + mandatory +
                ", defaultValue='" + defaultValue + '\'' +
                ", parameterPool='" + parameterPool + '\'' +
                ", readOnly=" + readOnly +
                ", hidden=" + hidden +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
