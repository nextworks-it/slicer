package it.nextworks.nfvmano.sbi.cnc.elements;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class CoverageAreaTAList{
    @JsonProperty("tac")
    public ArrayList<Integer> tAC;

    public CoverageAreaTAList(){
        tAC = new ArrayList<>();
    }
}
