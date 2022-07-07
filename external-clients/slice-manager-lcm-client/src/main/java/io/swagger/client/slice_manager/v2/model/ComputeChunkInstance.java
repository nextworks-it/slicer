package io.swagger.client.slice_manager.v2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComputeChunkInstance {

      private String name;
      private String id;
      private String description;
    @JsonProperty("compute_id")
      private String computeId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("os_project_id")
        private String osProjectId;
    @JsonProperty("os_user_id")
    private String osUserId;
    @JsonProperty("osm_vim_id")
        private String osmVimId;
        private String username;
        private String password;


    public ComputeChunkInstance() {
    }

    public ComputeChunkInstance(String name, String id, String description, String computeId, String userId, String osProjectId, String osUserId, String osmVimId, String username, String password) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.computeId = computeId;
        this.userId = userId;
        this.osProjectId = osProjectId;
        this.osUserId = osUserId;
        this.osmVimId = osmVimId;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getComputeId() {
        return computeId;
    }

    public String getUserId() {
        return userId;
    }

    public String getOsProjectId() {
        return osProjectId;
    }

    public String getOsUserId() {
        return osUserId;
    }

    public String getOsmVimId() {
        return osmVimId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
