package it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.core.cumucore;

//TODO Delete or move. First check if is user by NSSMF-core
public class Subscriber {
    private String imsi;
    private String k;


    public Subscriber(){}

    public Subscriber(String imsi, String k) {
        this.imsi = imsi;
        this.k = k;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }
}