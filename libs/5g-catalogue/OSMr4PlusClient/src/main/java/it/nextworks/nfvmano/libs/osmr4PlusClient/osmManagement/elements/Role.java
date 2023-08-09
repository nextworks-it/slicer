package it.nextworks.nfvmano.libs.osmr4PlusClient.osmManagement.elements;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Role {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }
}

