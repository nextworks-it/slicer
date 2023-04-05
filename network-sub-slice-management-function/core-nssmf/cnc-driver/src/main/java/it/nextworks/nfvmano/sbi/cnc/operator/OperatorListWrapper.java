package it.nextworks.nfvmano.sbi.cnc.operator;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class OperatorListWrapper {
    @JsonProperty("Data")
    public ArrayList<Operator> data;
    public int status;

    public OperatorListWrapper(){
        data = new ArrayList<>();
    }
}
