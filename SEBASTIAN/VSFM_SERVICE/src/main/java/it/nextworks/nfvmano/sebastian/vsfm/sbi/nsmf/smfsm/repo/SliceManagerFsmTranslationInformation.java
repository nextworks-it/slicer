package it.nextworks.nfvmano.sebastian.vsfm.sbi.nsmf.smfsm.repo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SliceManagerFsmTranslationInformation {
    @Id
    @GeneratedValue
    private Long id;

    String nsiId;
    String sliceManagerNestId;
    String sliceManagerId;
    String sliceManagerNsInstanceId;
    String sliceManagerNetworkServiceInstanceId;
    String sliceManagerNsdId;
    String tenant;
    String transactionId;
    String productId;
    String cellularMetricId;
    String cellId;
    private boolean monitoringConfigured=false;

    public SliceManagerFsmTranslationInformation() {
    }

    public boolean isMonitoringConfigured() {
        return monitoringConfigured;
    }

    public void setMonitoringConfigured(boolean monitoringConfigured) {
        this.monitoringConfigured = monitoringConfigured;
    }

    public SliceManagerFsmTranslationInformation(String nsiId, String sliceManagerNestId) {
        this.nsiId = nsiId;
        this.sliceManagerNestId = sliceManagerNestId;

    }

    public String getCellularMetricId() {
        return cellularMetricId;
    }

    public void setCellularMetricId(String cellularMetricId) {
        this.cellularMetricId = cellularMetricId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSliceManagerNsInstanceId() {
        return sliceManagerNsInstanceId;
    }

    public void setSliceManagerNsInstanceId(String sliceManagerNsInstanceId) {
        this.sliceManagerNsInstanceId = sliceManagerNsInstanceId;
    }

    public String getNsiId() {
        return nsiId;
    }

    public String getSliceManagerNestId() {
        return sliceManagerNestId;
    }

    public String getSliceManagerId() {
        return sliceManagerId;
    }

    public void setSliceManagerId(String sliceManagerId) {
        this.sliceManagerId = sliceManagerId;
    }

    public String getSliceManagerNsdId() {
        return sliceManagerNsdId;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public void setSliceManagerNsdId(String sliceManagerNsdId) {
        this.sliceManagerNsdId = sliceManagerNsdId;
    }

    public String getSliceManagerNetworkServiceInstanceId() {
        return sliceManagerNetworkServiceInstanceId;
    }

    public void setSliceManagerNetworkServiceInstanceId(String sliceManagerNetworkServiceInstanceId) {
        this.sliceManagerNetworkServiceInstanceId = sliceManagerNetworkServiceInstanceId;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }
}
