package it.nextworks.nfvmano.sebastian.vsfm.sbi.osm.elements;


import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OsmTranslationInformation {

    @Id
    @GeneratedValue
    private Long id;

    private String nsiId;
    private String osmInstanceId;
    private String osmInfoId;
    private String nstName;
    private String nstId;
    private String nsdId;
    private String nsdVersion;
    private String dfId;
    private String ilId;

    private String instantiationOperationId;
    private String terminationOperationId;
    private NetworkSliceStatus status;

    public OsmTranslationInformation() {

    }

    public OsmTranslationInformation(String nsiId, String osmInstanceId, String osmInfoId, String nstName, String nstId, String nsdId, String nsdVersion, String dfId, String ilId) {
        this.nsiId = nsiId;
        this.osmInstanceId = osmInstanceId;
        this.osmInfoId = osmInfoId;
        this.nstName = nstName;
        this.nstId = nstId;
        this.nsdId = nsdId;
        this.nsdVersion = nsdVersion;
        this.dfId = dfId;
        this.ilId = ilId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNsiId() {
        return nsiId;
    }

    public void setNsiId(String nsiId) {
        this.nsiId = nsiId;
    }

    public String getOsmInstanceId() {
        return osmInstanceId;
    }

    public void setOsmInstanceId(String osmInstanceId) {
        this.osmInstanceId = osmInstanceId;
    }

    public String getNstName() {
        return nstName;
    }

    public void setNstName(String nstName) {
        this.nstName = nstName;
    }

    public String getNstId() {
        return nstId;
    }

    public void setNstId(String nstId) {
        this.nstId = nstId;
    }

    public String getNsdId() {
        return nsdId;
    }

    public void setNsdId(String nsdId) {
        this.nsdId = nsdId;
    }

    public String getNsdVersion() {
        return nsdVersion;
    }

    public void setNsdVersion(String nsdVersion) {
        this.nsdVersion = nsdVersion;
    }

    public String getDfId() {
        return dfId;
    }

    public void setDfId(String dfId) {
        this.dfId = dfId;
    }

    public String getIlId() {
        return ilId;
    }

    public void setIlId(String ilId) {
        this.ilId = ilId;
    }

    public String getOsmInfoId() {
        return osmInfoId;
    }

    public void setOsmInfoId(String osmInfoId) {
        this.osmInfoId = osmInfoId;
    }

    public String getInstantiationOperationId() {
        return instantiationOperationId;
    }

    public void setInstantiationOperationId(String instantiationOperationId) {
        this.instantiationOperationId = instantiationOperationId;
    }

    public String getTerminationOperationId() {
        return terminationOperationId;
    }

    public void setTerminationOperationId(String terminationOperationId) {
        this.terminationOperationId = terminationOperationId;
    }

    public NetworkSliceStatus getStatus() {
        return status;
    }

    public void setStatus(NetworkSliceStatus status) {
        this.status = status;
    }
}
