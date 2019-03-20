package it.nextworks.nfvmano.sebastian.nfvodriver.guidrivers;

/**
 * Created by Marco Capitani on 20/03/19.
 *
 * @author Marco Capitani <m.capitani AT nextworks.it>
 */
public class StubDriver implements NfvoGuiDriver {

    @Override
    public String makeNfvNsUrl(String nfvNsId) {
        return String.format("https://www.google.it/search?q=%s", nfvNsId.replace(" ", "+"));
    }
}
