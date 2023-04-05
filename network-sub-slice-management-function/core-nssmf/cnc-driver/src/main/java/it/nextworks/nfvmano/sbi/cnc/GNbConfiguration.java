package it.nextworks.nfvmano.sbi.cnc;

public class GNbConfiguration {

    private String gNB_name;
    private String gNB_IPv4;
    private float latitude;
    private float longitude;
    private String coverageArea;

    public String getgNB_name() {
        return gNB_name;
    }

    public void setgNB_name(String gNB_name) {
        this.gNB_name = gNB_name;
    }

    public String getgNB_IPv4() {
        return gNB_IPv4;
    }

    public void setgNB_IPv4(String gNB_IPv4) {
        this.gNB_IPv4 = gNB_IPv4;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCoverageArea() {
        return coverageArea;
    }

    public void setCoverageArea(String coverageArea) {
        this.coverageArea = coverageArea;
    }

    /*{
        "gNB_name":"cumucore",
            "gNB_IPv4":"127.0.0.2",
            "latitude":10.2,
            "longitude":41.5,
            "coverageArea":"16512"
    }
    */


}
