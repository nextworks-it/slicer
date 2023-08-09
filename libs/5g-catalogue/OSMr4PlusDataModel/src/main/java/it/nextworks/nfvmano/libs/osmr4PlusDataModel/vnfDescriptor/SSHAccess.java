package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SSHAccess {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean required;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("default-user")
    private String user;

    public SSHAccess() {
    }

    public SSHAccess(boolean required) {
        this.required = required;
    }

    public SSHAccess(boolean required, String user) {
        this.required = required;
        this.user = user;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
