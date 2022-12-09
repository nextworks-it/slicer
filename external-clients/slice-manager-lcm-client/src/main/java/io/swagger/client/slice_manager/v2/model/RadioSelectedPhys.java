package io.swagger.client.slice_manager.v2.model;

public class RadioSelectedPhys {
    private String id;
    private String name;
    private String type;
    private RadioSelectedPhysConfig config;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RadioSelectedPhysConfig getConfig() {
        return config;
    }

    public void setConfig(RadioSelectedPhysConfig config) {
        this.config = config;
    }
}
