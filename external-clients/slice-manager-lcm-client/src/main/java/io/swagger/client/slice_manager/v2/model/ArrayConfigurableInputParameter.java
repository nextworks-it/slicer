package io.swagger.client.slice_manager.v2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ArrayConfigurableInputParameter extends ConfigurableInputParameter{

    @JsonProperty("parameter_value")
    private List<String> parameterValue = null;

    public ArrayConfigurableInputParameter(){

    }

    public List<String> getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(List<String> parameterValue) {
        this.parameterValue = parameterValue;
    }
}
