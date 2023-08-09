package it.nextworks.nfvmano.libs.osmr4PlusDataModel.vnfDescriptor;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class ManagementInterface {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("ip-address")
    private String ip;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("vdu-id")
    private String vduIdentification;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer port;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("dashboard-params")
    private DashboardParameters dashboardParameters;

    private Map<String, Object> otherProperties = new HashMap<String, Object>();

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getVduIdentification() {
        return vduIdentification;
    }

    public void setVduIdentification(String vduIdentification) {
        this.vduIdentification = vduIdentification;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public DashboardParameters getDashboardParameters() {
        return dashboardParameters;
    }

    public void setDashboardParameters(DashboardParameters dashboardParameters) {
        this.dashboardParameters = dashboardParameters;
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return otherProperties;
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        otherProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "ManagementInterface{" +
                "ip='" + ip + '\'' +
                ", cp='" + cp + '\'' +
                ", vduIdentification='" + vduIdentification + '\'' +
                ", port=" + port +
                ", dashboardParameters=" + dashboardParameters +
                ", otherProperties=" + otherProperties +
                '}';
    }
}
