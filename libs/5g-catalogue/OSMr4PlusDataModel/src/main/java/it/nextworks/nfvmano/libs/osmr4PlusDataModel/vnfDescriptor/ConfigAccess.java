package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigAccess {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("ssh-access")
    private SSHAccess sshAccess;

    public ConfigAccess() {
    }

    public ConfigAccess(SSHAccess sshAccess) {
        this.sshAccess = sshAccess;
    }

    public SSHAccess getSshAccess() {
        return sshAccess;
    }

    public void setSshAccess(SSHAccess sshAccess) {
        this.sshAccess = sshAccess;
    }
}
