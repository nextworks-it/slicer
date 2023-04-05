package it.nextworks.nfvmano.nsmf.ra.algorithms.stat.record.elements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.nextworks.nfvmano.libs.ifa.templates.nst.SliceType;


import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class StaticRaResponseRecord {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private String responseId;
    private SliceType sliceType;



    public StaticRaResponseRecord(){}

    public StaticRaResponseRecord(String responseId, SliceType type){
        this.responseId=responseId;
        this.sliceType=type;

    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public SliceType getSliceType() {
        return sliceType;
    }

    public void setSliceType(SliceType sliceType) {
        this.sliceType = sliceType;
    }


}
