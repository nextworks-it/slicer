package it.nextworks.nfvmano.sebastian.engine.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModifyNsiRequestMessage extends EngineMessage{
    @JsonProperty("nsiId")
    private String nsiId;

    @JsonProperty("nfvNsdId")
    private String nfvNsdId;

    @JsonProperty("nfvNsdVersion")
    private String nfvNsdVersion;

    @JsonProperty("dfId")
    private String dfId;

    @JsonProperty("ilId")
    private String ilId;

    @JsonProperty("vsiId")
    private String vsiId;


    /**
     *  @param nsiId
     * @param nfvNsdId
     * @param nfvNsdVersion
     * @param dfId
     * @param ilId
     * @param vsiId
     */
    @JsonCreator
    public ModifyNsiRequestMessage(@JsonProperty("nsiId") String nsiId,
                                   @JsonProperty("nfvNsdId") String nfvNsdId,
                                   @JsonProperty("nfvNsdVersion") String nfvNsdVersion,
                                   @JsonProperty("dfId") String dfId,
                                   @JsonProperty("ilId") String ilId,
                                   @JsonProperty("vsiId") String vsiId) {
        this.type = EngineMessageType.MODIFY_NSI_REQUEST;
        this.nsiId = nsiId;
        this.nfvNsdId = nfvNsdId;
        this.nfvNsdVersion = nfvNsdVersion;
        this.dfId = dfId;
        this.ilId = ilId;
        this.vsiId = vsiId;

    }

    /**
     *
     * @return nsiId
     */
    public String getNsiId() {
        return nsiId;
    }

    /**
     *
     * @return nfvNsdId
     */
    public String getNfvNsdId() {
        return nfvNsdId;
    }

    /**
     *
     * @return nfvNsdVersion
     */
    public String getNfvNsdVersion() {
        return nfvNsdVersion;
    }

    /**
     *
     * @return dfId
     */
    public String getDfId() {
        return dfId;
    }

    /**
     *
     * @return ilId
     */
    public String getIlId() {
        return ilId;
    }

    /**
     *
     * @return vsiId
     */
    public String getVsiId() {
        return vsiId;
    }
}
