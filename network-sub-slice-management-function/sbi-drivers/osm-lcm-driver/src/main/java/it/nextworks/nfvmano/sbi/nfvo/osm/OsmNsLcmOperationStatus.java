package it.nextworks.nfvmano.sbi.nfvo.osm;

public enum OsmNsLcmOperationStatus {

    FAILED,
    FAILED_TEMP,
    COMPLETED,
    ROLLING_BACK,
    ROLLED_BACK,
    PARTIALLY_COMPLETED,
    PROCESSING;


    public boolean equals(String value){
        return this.name().equalsIgnoreCase(value);
    }


}
