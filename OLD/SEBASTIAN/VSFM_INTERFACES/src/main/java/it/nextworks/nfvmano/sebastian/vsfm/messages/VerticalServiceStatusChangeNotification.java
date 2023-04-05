package it.nextworks.nfvmano.sebastian.vsfm.messages;

public class VerticalServiceStatusChangeNotification {
    private String vsiId;
    private VerticalServiceStatusChange vsStatusChange;

    private boolean successful;

    public VerticalServiceStatusChangeNotification(String vsiId, VerticalServiceStatusChange vsiCreated, boolean successful) {
        this.successful=successful;
        this.vsiId=vsiId;
        this.vsStatusChange=vsiCreated;
    }

    /**
     *
     * @return
     */
    public String getVsiId() {
        return vsiId;
    }

    /**
     *
     * @return
     */
    public VerticalServiceStatusChange getVsStatusChange() {
        return vsStatusChange;
    }

    /**
     *
     * @return
     */
    public boolean isSuccessful() {
        return successful;
    }
}
