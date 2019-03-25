package it.nextworks.nfvmano.sebastian.nfvodriver.guidrivers;

import java.util.Optional;

/**
 * Created by Marco Capitani on 21/03/19.
 *
 * @author Marco Capitani <m.capitani AT nextworks.it>
 */
public class TimeoGUIConnector implements NfvoGuiConnector {

    private String address;
    private int port;

    public TimeoGUIConnector(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public TimeoGUIConnector(String address) {
        this.address = address;
        this.port = 80;
    }

    @Override
    public Optional<String> makeNfvNsUrl(String nfvNsId) {
        return Optional.of(String.format(
                "http://%s:%s/timeo_web_gui/pages/ns/nsi_details.html?nsiId=%s",
                address,
                port,
                nfvNsId
        ));
    }
}
