package it.nextworks.nfvmano.sebastian.nste2eComposer.IM;

public enum BucketType {
    URBAN_MACRO(Keyword.URBAN),
    RURAL_MACRO(Keyword.URBAN),
    BROADBAND_ACCESS_CROWD(Keyword.URBAN),
    DENSE_URBAN(Keyword.URBAN),

    INDOOR_HOTSPOT(Keyword.INDOOR),
    BROADCAST_LIKE_SERVICE(Keyword.STREAMING),
    HIGH_SPEED_VEHICLE(Keyword.VEHICLE),
    HIGH_SPEED_TRAIN(Keyword.VEHICLE),
    AIR_PLANE_CONNECTIVITY(Keyword.VEHICLE),

    DISCRETE_AUTOMATION(Keyword.DISCRETE_AUTOMATION),
    PROCESS_AUTOMATION_REMOTE_CONTROL(Keyword.PROCESS_AUTOMATION),
    PROCESS_AUTOMATION_MONITORING(Keyword.PROCESS_AUTOMATION),
    ELECTRICITY_DISTR_MEDIUM_VOLR(Keyword.ELECTRICITY_DISTRIBUTION),
    ELECTRICITY_DISTR_HIGH_VOLR(Keyword.ELECTRICITY_DISTRIBUTION),
    INTELLIGENT_TR_SYS(Keyword.INTELLIGENT_TRANSPORT_SYSTEM);

    private Keyword keyword;

    BucketType(Keyword keyword){
        this.keyword=keyword;
    }

    Keyword getKeyword(){
        return this.keyword;
    }
}
