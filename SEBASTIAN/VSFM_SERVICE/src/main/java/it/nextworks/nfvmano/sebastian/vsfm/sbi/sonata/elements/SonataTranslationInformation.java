package it.nextworks.nfvmano.sebastian.vsfm.sbi.sonata.elements;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SonataTranslationInformation {

    @Id
    @GeneratedValue
    private Long id;

    private String nsiId;
    private String sonataInstanceId;
    private String nstName;
    private String nstId;
    private String nsdId;
    private String nsdVersion;
    private String dfId;
    private String ilId;
    private String requestId;

    public SonataTranslationInformation() {

    }

    public SonataTranslationInformation(String nsiId, String sonataInstanceId, String nstName, String nstId, String nsdId, String nsdVersion, String dfId, String ilId, String requestId) {
        this.nsiId = nsiId;
        this.sonataInstanceId = sonataInstanceId;
        this.nstName = nstName;
        this.nstId = nstId;
        this.nsdId = nsdId;
        this.nsdVersion = nsdVersion;
        this.dfId = dfId;
        this.ilId = ilId;
        this.requestId = requestId;
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

    public String getSonataInstanceId() {
        return sonataInstanceId;
    }

    public void setSonataInstanceId(String sonataInstanceId) {
        this.sonataInstanceId = sonataInstanceId;
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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
