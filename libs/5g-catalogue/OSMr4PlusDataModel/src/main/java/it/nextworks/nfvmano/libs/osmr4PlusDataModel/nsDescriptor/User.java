package it.nextworks.nfvmano.libs.osmr4PlusDataModel.nsDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("user-info")
    private String userInfo;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("key-pair")
    private List<KeyPair> keyPairList;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public List<KeyPair> getKeyPairList() {
        return keyPairList;
    }

    public void setKeyPairList(List<KeyPair> keyPairList) {
        this.keyPairList = keyPairList;
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return otherProperties;
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        otherProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", userInfo='" + userInfo + '\'' +
                ", keyPairList=" + keyPairList +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
