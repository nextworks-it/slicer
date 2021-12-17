package it.nextworks.nfvmano.sebastian.vsfm.vsmanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum MetricsLogger {
    VSI_INSTANTIATION_START,
    VSI_INSTANTIATION_END,
    VSI_ARBITRATION_START,
    VSI_ARBITRATION_END,
    VSI_TRANSLATION_START,
    VSI_TRANSLATION_END,
    VSI_TERMINATION_START,
    VSI_TERMINATION_END,
    VSI_NSI_INSTANTIATION_START,
    VSI_NSI_INSTANTIATION_END,
    VSI_NSI_TERMINATION_START,
    VSI_NSI_TERMINATION_END,
    NSI_INSTANTIATION_START,
    NSI_INSTANTIATION_END,
    NSI_TERMINATION_START,
    NSI_TERMINATION_END,
    NFV_NSI_INSTANTIATION_START,
    NFV_NSI_INSTANTIATION_END,
    NFV_NSI_TERMINATION_START,
    NFV_NSI_TERMINATION_END;

    private static final Logger log = LoggerFactory.getLogger(MetricsLogger.class);

    public static String getLogMessage(String id, MetricsLogger metricsLogger, String ... args){
        String profile = null;
        String[] name = metricsLogger.name().split("_");
        switch(name[0]) {
            case "VSI":
                try{
                    String vsbName = args[0];
                    if(metricsLogger.name().equals("VSI_NSI_INSTANTIATION_START")){
                        String nsiId = args[1];
                        profile = "PROFILING VSI" + " " + id + " " + metricsLogger.name() + " " + nsiId
                                + " " + System.currentTimeMillis() + " " + vsbName;
                    }
                    else
                        profile = "PROFILING VSI" + " " + id + " " + metricsLogger.name() + " " + System.currentTimeMillis() + " " + vsbName;
                }
                catch (Exception w){
                    profile = "PROFILING VSI" + " " + id + " " + metricsLogger.name() + " " + System.currentTimeMillis();
                }
                break;
            case "NSI":
                profile = "PROFILING NSI" + " "+ id + " " +metricsLogger.name()+" " + System.currentTimeMillis();
                break;
            case "NFV":
                profile = "PROFILING NFV_NSI" + " "+ id + " " +metricsLogger.name()+" " + System.currentTimeMillis();
                break;
        }
        return profile;
    }
}
