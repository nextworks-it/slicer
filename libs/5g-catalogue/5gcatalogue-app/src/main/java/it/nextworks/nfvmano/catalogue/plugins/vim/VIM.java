package it.nextworks.nfvmano.catalogue.plugins.vim;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.nextworks.nfvmano.catalogue.plugins.vim.os.OSVim;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "vimType", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = OSVim.class, name = "OS"),
        @JsonSubTypes.Type(value = DummyVim.class, name = "DUMMY")})
public abstract class VIM {

    @Id
    @GeneratedValue
    private Long id;

    private String vimId;
    private VIMType vimType;

    public VIM() {
        // JPA only
    }

    public VIM(String vimId, VIMType vimType) {
        this.vimId = vimId;
        this.vimType = vimType;
    }

    public Long getId() {
        return id;
    }

    @JsonProperty("vimId")
    public String getVimId() {
        return vimId;
    }

    @JsonProperty("vimType")
    public VIMType getVimType() {
        return vimType;
    }

}
