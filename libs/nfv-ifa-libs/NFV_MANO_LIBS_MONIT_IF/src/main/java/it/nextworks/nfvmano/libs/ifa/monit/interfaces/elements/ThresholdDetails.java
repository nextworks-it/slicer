package it.nextworks.nfvmano.libs.ifa.monit.interfaces.elements;

import it.nextworks.nfvmano.libs.ifa.monit.interfaces.enums.ThresholdFormat;

/**
 * Created by Marco Capitani on 20/06/19.
 *
 * @author Marco Capitani <m.capitani AT nextworks.it>
 */
public abstract class ThresholdDetails {

    private ThresholdFormat format;

    public ThresholdDetails(ThresholdFormat format) {
        this.format = format;
    }

    public ThresholdFormat getFormat() {
        return format;
    }
}
