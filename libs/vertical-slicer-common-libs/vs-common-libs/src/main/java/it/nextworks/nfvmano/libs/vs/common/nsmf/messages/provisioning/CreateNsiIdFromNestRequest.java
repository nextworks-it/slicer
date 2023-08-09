package it.nextworks.nfvmano.libs.vs.common.nsmf.messages.provisioning;

import it.nextworks.nfvmano.libs.vs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.vs.common.interfaces.InterfaceMessage;

public class CreateNsiIdFromNestRequest implements InterfaceMessage {

    private String nestId;
    private String name;
    private String description;
    private String vsInstanceId;

    public CreateNsiIdFromNestRequest(){}

    /**
     * Constructor
     *
     * @param nestId ID of the generic network slice template
     * @param name name of the network slice instance
     * @param description description of the network slice instance
     * @param vsInstanceId ID of the vertical service instance
     */
    public CreateNsiIdFromNestRequest(String nestId, String name, String description, String vsInstanceId) {
        this.nestId = nestId;
        this.name = name;
        this.description = description;
        this.vsInstanceId = vsInstanceId;
    }

    public String getNestId() {
        return nestId;
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
    public void isValid() throws MalformattedElementException{
        if (nestId == null)
            throw new MalformattedElementException("Create network slice ID request without NEST ID");
        if (name == null)
            throw new MalformattedElementException("Create network slice ID request without name");
    }
}
