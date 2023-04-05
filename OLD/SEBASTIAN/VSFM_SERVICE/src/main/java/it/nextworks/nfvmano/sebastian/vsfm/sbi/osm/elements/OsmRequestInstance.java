package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements;


import com.fasterxml.jackson.annotation.JsonProperty;

public class OsmRequestInstance {

    @JsonProperty("id")
    private String id;

    @JsonProperty("nsilcmop_id")
    private String operationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }
}
