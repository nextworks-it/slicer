package io.swagger.client.slice_manager.v2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigurableInputParameter {
    @JsonProperty("parameter_name")
    private String parameterName = null;



    public ConfigurableInputParameter() {
    }



    public String getParameterName() {
        return parameterName;
    }


    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }


}
