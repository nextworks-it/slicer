package io.swagger.client.slice_manager.v2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ChunkObject {

    @JsonProperty("compute_chunk")
    private List<ComputeChunkInstance> computeChunk = new ArrayList<>();

    @JsonProperty("radio_chunk")
    private List<PostRadioChunk> radioChunk = new ArrayList<>();


    public ChunkObject() {
    }

    public ChunkObject(List<ComputeChunkInstance> computeChunk) {
        this.computeChunk = computeChunk;
    }

    public List<ComputeChunkInstance> getComputeChunk() {
        return computeChunk;
    }


    public List<PostRadioChunk> getRadioChunk() {
        return radioChunk;
    }

    public void setRadioChunk(List<PostRadioChunk> radioChunk) {
        this.radioChunk = radioChunk;
    }
}
