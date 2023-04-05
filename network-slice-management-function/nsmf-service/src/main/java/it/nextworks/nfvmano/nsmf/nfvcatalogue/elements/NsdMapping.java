package it.nextworks.nfvmano.nsmf.nfvcatalogue.elements;

public class NsdMapping {

    private String id;
    private String nsdId;

    public NsdMapping(){}

    public NsdMapping(String id, String nsdId){
        this.id=id;
        this.nsdId=nsdId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNsdId() {
        return nsdId;
    }

    public void setNsdId(String nsdId) {
        this.nsdId = nsdId;
    }
}
