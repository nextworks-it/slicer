package it.nextworks.nfvmano.sebastian.arbitrator.elements;


import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

public class NetworkSliceInstanceAction {

    //network slice instance id
    private String nsiId;
    private NsiActionType nsiActionType;


    //NS DF to which the NS should be scaled
    private String nsDf;

    //NS Instantiation level to which scale
    private String nsInstantiationLevel;


    public NetworkSliceInstanceAction(){

    }

    public NetworkSliceInstanceAction(String nsiId, NsiActionType nsiActionType, String nsDf, String nsInstantiationLevel) {
        this.nsiId = nsiId;
        this.nsDf=nsDf;

        this.nsInstantiationLevel = nsInstantiationLevel;
        this.nsiActionType = nsiActionType;
    }

    public String getNsiId() {
        return nsiId;
    }


    public NsiActionType getNsiActionType() {
        return nsiActionType;
    }


    public String getNsDf() {
        return nsDf;
    }

    public String getNsInstantiationLevel() {
        return nsInstantiationLevel;
    }


    public void isValid() throws MalformattedElementException{
        if(nsDf==null) throw new MalformattedElementException("NSI action without deployment flavor");
        if(nsInstantiationLevel==null) throw new MalformattedElementException("NSI action without instantiation level");
    }
}
