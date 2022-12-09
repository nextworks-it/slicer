package io.swagger.client.slice_manager.v2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IntegerConfigurableParameter extends ConfigurableInputParameter{

    @JsonProperty("parameter_value")
    private float parameterValue;


    public float getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(float parameterValue) {
        this.parameterValue = parameterValue;
    }
}
