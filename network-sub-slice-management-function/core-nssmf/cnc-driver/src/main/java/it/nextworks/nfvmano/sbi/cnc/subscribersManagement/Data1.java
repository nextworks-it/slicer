package it.nextworks.nfvmano.sbi.cnc.subscribersManagement;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data1 {
    @JsonProperty("Imsi")
    public String imsi;
    public String msisdn;
    public String k;
    public String opc;
    public String sqn;
    public String imsisv;
    public int network_access_mode;
    public int ue_ambr_ul;
    public int ue_ambr_dl;
    public String apn_io_replacement;
    public String charging_characteristics;
    public String auth_type;
    @JsonProperty("slice_name")
    public String slice_name;
}
