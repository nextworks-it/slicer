package it.nextworks.nfvmano.libs.vs.common.ra.elements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RanNssResourceAllocation extends NssResourceAllocation {

    private int amfs;
    private int cuUps;
    private int dus;
    private int cells;

    @JsonCreator
    public RanNssResourceAllocation(@JsonProperty String nsstId){
        super(nsstId);
        allocationType=NssResourceAllocationType.RAN;
    }

    @JsonCreator
    public RanNssResourceAllocation(
            @JsonProperty("nsstId") String nsstId,
            @JsonProperty("amfs") int amfs,
            @JsonProperty("cuUps") int cuUps,
            @JsonProperty("dus") int dus,
            @JsonProperty("cells") int cells ) {
        super(nsstId);
        allocationType=NssResourceAllocationType.RAN;
        this.amfs=amfs;
        this.cuUps=cuUps;
        this.dus=dus;
        this.cells=cells;
    }

    public int getAmfs() {
        return amfs;
    }

    public void setAmfs(int amfs) {
        this.amfs = amfs;
    }

    public int getCuUps() {
        return cuUps;
    }

    public void setCuUps(int cuUps) {
        this.cuUps = cuUps;
    }

    public int getDus() {
        return dus;
    }

    public void setDus(int dus) {
        this.dus = dus;
    }

    public int getCells() {
        return cells;
    }

    public void setCells(int cells) {
        this.cells = cells;
    }
}
