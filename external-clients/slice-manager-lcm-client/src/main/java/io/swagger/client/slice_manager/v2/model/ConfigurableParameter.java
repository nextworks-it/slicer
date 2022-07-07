package io.swagger.client.slice_manager.v2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigurableParameter {
    @JsonProperty("parameter_name")
    private String parameterName = null;
    @JsonProperty("parameter_type")
    private String parameterType = null;
    @JsonProperty("parameter_description")
    private String parameterDescription = null;


    public ConfigurableParameter() {
    }

    public String getParameterName() {
        return parameterName;
    }

    public String getParameterType() {
        return parameterType;
    }

    public String getParameterDescription() {
        return parameterDescription;
    }
}
