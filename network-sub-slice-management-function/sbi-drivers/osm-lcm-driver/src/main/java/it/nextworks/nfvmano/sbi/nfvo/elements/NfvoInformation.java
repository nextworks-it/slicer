package it.nextworks.nfvmano.sbi.nfvo.elements;

import java.util.UUID;

public class NfvoInformation {

    private String baseUrl;

    private UUID vimAccountId;

    private String username;

    private String password;

    private String project;

    private NfvoDriverType nfvoDriverType;

    private String vimExternalNetwork;

    public NfvoInformation() {
    }

    public NfvoInformation(String baseUrl, UUID vimAccountId, String username, String password,
                           NfvoDriverType nfvoDriverType, String project, String vimExternalNetwork) {
        this.baseUrl = baseUrl;
        this.vimAccountId = vimAccountId;
        this.username = username;
        this.password = password;
        this.nfvoDriverType =nfvoDriverType;
        this.project = project;
        this.vimExternalNetwork = vimExternalNetwork;
    }

    public NfvoDriverType getNfvoDriverType() {
        return nfvoDriverType;
    }

    public String getProject() {
        return project;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public UUID getVimAccountId() {
        return vimAccountId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getVimExternalNetwork() {
        return vimExternalNetwork;
    }
}
