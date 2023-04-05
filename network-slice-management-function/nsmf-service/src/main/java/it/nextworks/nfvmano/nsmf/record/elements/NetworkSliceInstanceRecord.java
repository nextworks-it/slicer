package it.nextworks.nfvmano.nsmf.record.elements;

import it.nextworks.nfvmano.libs.vs.common.nsmf.elements.NetworkSliceInstance;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
public class NetworkSliceInstanceRecord {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(
            cascade = CascadeType.ALL
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<NetworkSliceSubnetInstanceRecord> networkSliceSubnetInstances = new ArrayList<>();

    private String nstId;

    private String vsInstanceId;

    private NetworkSliceInstanceRecordStatus status;

    private String tenantId;

    private String errorMsg;

    private String name;

    @ElementCollection(targetClass=String.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> imsiAssociatedWith =  new ArrayList<String>();;

    private int dlDataRate;
    private int ulDataRate;

    public NetworkSliceInstanceRecord() {
        imsiAssociatedWith = new ArrayList<>();
    }

    public NetworkSliceInstanceRecord(List<NetworkSliceSubnetInstanceRecord> networkSliceSubnetInstances,
                                      String nstId,
                                      String vsInstanceId,
                                      NetworkSliceInstanceRecordStatus status,
                                      String tenantId,
                                      String name) {
        if(networkSliceSubnetInstances!=null){
            this.networkSliceSubnetInstances = networkSliceSubnetInstances;
        }

        this.nstId = nstId;
        this.vsInstanceId = vsInstanceId;
        this.status=status;
        this.tenantId= tenantId;
        this.name=name;
    }

    public NetworkSliceInstanceRecord(List<NetworkSliceSubnetInstanceRecord> networkSliceSubnetInstances,
                                      String nstId,
                                      String vsInstanceId,
                                      NetworkSliceInstanceRecordStatus status,
                                      String tenantId,
                                      String name,
                                      List<String> imsiAssociatedWith,
                                      int dlDataRate,
                                      int ulDataRate
    ) {
        if(networkSliceSubnetInstances!=null){
            this.networkSliceSubnetInstances = networkSliceSubnetInstances;
        }

        this.nstId = nstId;
        this.vsInstanceId = vsInstanceId;
        this.status=status;
        this.tenantId= tenantId;
        this.name=name;
        this.imsiAssociatedWith = imsiAssociatedWith;
        this.dlDataRate = dlDataRate;
        this.ulDataRate = ulDataRate;
    }

    public List<NetworkSliceSubnetInstanceRecord> getNetworkSliceSubnetInstanceIds() {
        return networkSliceSubnetInstances;
    }

    public NetworkSliceInstanceRecordStatus getStatus() {
        return status;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getNstId() {
        return nstId;
    }

    public String getVsInstanceId() {
        return vsInstanceId;
    }

    public UUID getId() {
        return id;
    }

    public NetworkSliceInstance getNetworkSliceInstance(){
        return new NetworkSliceInstance(this.id,
                this.getNetworkSliceSubnetInstanceIds().stream().map(e -> e.getNssiIdentifier())
                .collect(Collectors.toList()), vsInstanceId, status.asNsiStatus(),status.toString(),
                imsiAssociatedWith,
                dlDataRate,
                ulDataRate
                );
    }


    public void addNetworkSliceSubnetInstance(NetworkSliceSubnetInstanceRecord subnetInstanceRecord){
        networkSliceSubnetInstances.add(subnetInstanceRecord);
    }
    public void setStatus(NetworkSliceInstanceRecordStatus status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<String> getImsiAssociatedWith() {
        return imsiAssociatedWith;
    }
    public void setImsiAssociatedWith(List<String> imsiAssociatedWith) {
        this.imsiAssociatedWith = imsiAssociatedWith;
    }

    public int getDlDataRate() {
        return dlDataRate;
    }

    public void setDlDataRate(int dlDataRate) {
        this.dlDataRate = dlDataRate;
    }

    public int getUlDataRate() {
        return ulDataRate;
    }

    public void setUlDataRate(int ulDataRate) {
        this.ulDataRate = ulDataRate;
    }

}
