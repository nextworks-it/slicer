package io.swagger.client.slice_manager.v2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ChunkObject {

    @JsonProperty("compute_chunk")
    private List<ComputeChunkInstance> computeChunk = new ArrayList<>();


    public ChunkObject() {
    }

    public ChunkObject(List<ComputeChunkInstance> computeChunk) {
        this.computeChunk = computeChunk;
    }

    public List<ComputeChunkInstance> getComputeChunk() {
        return computeChunk;
    }
}
