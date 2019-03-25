package it.nextworks.nfvmano.sebastian.nfvodriver.guidrivers;

import java.util.Optional;

/**
 * Created by Marco Capitani on 20/03/19.
 *
 * @author Marco Capitani <m.capitani AT nextworks.it>
 */
public interface NfvoGuiConnector {

    public Optional<String> makeNfvNsUrl(String nfvNsId);
}
