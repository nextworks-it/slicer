package it.nextworks.nfvmano.libs.osmr4PlusDataModel.osmManagement;

import com.fasterxml.jackson.annotation.JsonInclude;

public class IdObject {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IdObject{" +
                "id='" + id + '\'' +
                '}';
    }
}
