package it.nextworks.nfvmano.libs.vs.common.nssmf.messages.specialized.transport.mm_wave;

public class DeviceParams {
    private String deviceName;
    private double latitude;
    private double longitude;

    public DeviceParams() {
    }

    public DeviceParams(String deviceName, double latitude, double longitude) {
        this.deviceName = deviceName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
