package io.swagger.client.slice_manager.v2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StringConfigurableInputParameter extends ConfigurableInputParameter{

    @JsonProperty("parameter_value")
    private String parameterValue = null;

    public StringConfigurableInputParameter(){

    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }
}
