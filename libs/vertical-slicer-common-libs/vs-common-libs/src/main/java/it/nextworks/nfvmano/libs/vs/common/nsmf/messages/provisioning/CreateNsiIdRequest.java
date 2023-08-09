package it.nextworks.nfvmano.libs.vs.common.nsmf.messages.provisioning;

import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.interfaces.InterfaceMessage;

public class CreateNsiIdRequest implements InterfaceMessage {
    private String nstId;
    private String name;
    private String description;
    private String vsInstanceId;

    /**
     * Constructor
     *
     * @param nstId ID of the network slice template
     * @param name name of the network slice instance
     * @param description description of the network slice instance
     * @param vsInstanceId ID of the vertical service instance
     */
    public CreateNsiIdRequest(String nstId, String name, String description, String vsInstanceId) {
        this.nstId = nstId;
        this.name = name;
        this.description = description;
        this.vsInstanceId= vsInstanceId;
    }

    public CreateNsiIdRequest(){ }
    public String getNstId() {
        return nstId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getVsInstanceId() {
        return vsInstanceId;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (nstId == null)
            throw new MalformattedElementException("Create network slice ID request without NST ID");
        if (name == null)
            throw new MalformattedElementException("Create network slice ID request without name");
    }
}
