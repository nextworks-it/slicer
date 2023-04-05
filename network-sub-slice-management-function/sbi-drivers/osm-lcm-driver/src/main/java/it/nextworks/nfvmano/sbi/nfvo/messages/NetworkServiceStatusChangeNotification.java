package it.nextworks.nfvmano.sbi.nfvo.messages;


public class NetworkServiceStatusChangeNotification {

    private String nsiId;


    private NetworkServiceStatusChange statusChange;


    public String getNsiId() {
        return nsiId;
    }

    public NetworkServiceStatusChange getStatusChange() {
        return statusChange;
    }



    public NetworkServiceStatusChangeNotification(String nsiId,  NetworkServiceStatusChange statusChange) {
        this.nsiId = nsiId;

        this.statusChange = statusChange;
    }
}
