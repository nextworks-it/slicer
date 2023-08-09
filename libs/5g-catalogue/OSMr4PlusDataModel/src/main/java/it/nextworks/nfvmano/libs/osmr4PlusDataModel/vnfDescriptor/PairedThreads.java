package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PairedThreads {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("num-paired-threads")
    private Integer numPairedThreads;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("paired-thread-ids")
    private List<PairedThreadIds> pairedThreadIdsList;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public Integer getNumPairedThreads() {
        return numPairedThreads;
    }

    public void setNumPairedThreads(Integer numPairedThreads) {
        this.numPairedThreads = numPairedThreads;
    }

    public List<PairedThreadIds> getPairedThreadIdsList() {
        return pairedThreadIdsList;
    }

    public void setPairedThreadIdsList(List<PairedThreadIds> pairedThreadIdsList) {
        this.pairedThreadIdsList = pairedThreadIdsList;
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
        return "PairedThreads{" +
                "numPairedThreads=" + numPairedThreads +
                ", pairedThreadIdsList=" + pairedThreadIdsList +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
