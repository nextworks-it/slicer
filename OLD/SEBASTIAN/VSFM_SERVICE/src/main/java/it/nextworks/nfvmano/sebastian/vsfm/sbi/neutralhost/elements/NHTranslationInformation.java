package it.nextworks.nfvmano.sebastian.vsfm.sbi.neutralhost.elements;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class NHTranslationInformation {

    @Id
    @GeneratedValue
    private Long id;

    private String nsiId;
    private String osmInfoId;
    private String smNsInstanceId;
    private String nstName;
    private String nstId;
    private String nsdId;
    private String nsdVersion;
    private String dfId;
    private String ilId;

    public NHTranslationInformation() {
    }

    public NHTranslationInformation(String nsiId, String osmInfoId, String smNsInstanceId, String nstName, String nstId, String nsdId, String nsdVersion, String dfId, String ilId) {
        this.nsiId = nsiId;
        this.osmInfoId = osmInfoId;
        this.smNsInstanceId = smNsInstanceId;
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

    public String getOsmInfoId() {
        return osmInfoId;
    }

    public void setOsmInfoId(String osmInfoId) {
        this.osmInfoId = osmInfoId;
    }

    public String getSmNsInstanceId() {
        return smNsInstanceId;
    }

    public void setSmNsInstanceId(String smNsInstanceId) {
        this.smNsInstanceId = smNsInstanceId;
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
}