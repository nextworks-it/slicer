package it.nextworks.nfvmano.nsmf.nfvcatalogue.elements;

public class VnfdMapping {

    private String id;
    private String vnfdId;

    public VnfdMapping(){}

    public VnfdMapping(String id, String vnfdId){
        this.id=id;
        this.vnfdId =vnfdId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVnfdId() {
        return vnfdId;
    }

    public void setVnfdId(String vnfdId) {
        this.vnfdId = vnfdId;
    }
}
