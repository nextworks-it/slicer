package io.swagger.client.slice_manager.v2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class RadioChunkTopology {
    @JsonProperty("selectedPhys")
    private List<RadioSelectedPhys> selectedPhys = new ArrayList<>();


    public List<RadioSelectedPhys> getSelectedPhys() {
        return selectedPhys;
    }

    public void setSelectedPhys(List<RadioSelectedPhys> selectedPhys) {
        this.selectedPhys = selectedPhys;
    }
}
