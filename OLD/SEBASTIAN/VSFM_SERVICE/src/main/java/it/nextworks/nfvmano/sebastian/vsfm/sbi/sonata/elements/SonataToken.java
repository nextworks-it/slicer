package it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.elements;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;

public class SonataToken {

    @JsonProperty("token")
    private String token;

    @JsonIgnore
    private long tokenExpiresTime;

    @Transient
    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTokenExpiresTime() {
        return tokenExpiresTime;
    }

    public void setTokenExpiresTime(long tokenExpires) {
        this.tokenExpiresTime = System.currentTimeMillis() + tokenExpires;
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return otherProperties;
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        otherProperties.put(name, value);
    }
}
