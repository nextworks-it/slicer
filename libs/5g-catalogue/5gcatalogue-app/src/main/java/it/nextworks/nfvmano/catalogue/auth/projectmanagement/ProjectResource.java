package it.nextworks.nfvmano.catalogue.auth.projectmanagement;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.nextworks.nfvmano.libs.common.DescriptorInformationElement;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class ProjectResource implements DescriptorInformationElement {

    String projectId;
    String projectDescription;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    List<String> users = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    List<String> nsds = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    List<String> pnfds = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    List<String> vnfPackages = new ArrayList<>();
    @Id
    @GeneratedValue
    private UUID id;

    public ProjectResource() {
    }

    public ProjectResource(String projectId) {
        this.projectId = projectId;
    }

    public ProjectResource(String projectId, String projectDescription) {
        this.projectId = projectId;
        this.projectDescription = projectDescription;
    }

    public ProjectResource(String projectId, String projectDescription, List<String> users) {
        this.projectId = projectId;
        this.projectDescription = projectDescription;
        this.users = users;
    }

    public UUID getId() {
        return id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<String> getNsds() {
        return nsds;
    }

    public void setNsds(List<String> nsds) {
        this.nsds = nsds;
    }

    public List<String> getPnfds() {
        return pnfds;
    }

    public void setPnfds(List<String> pnfds) {
        this.pnfds = pnfds;
    }

    public List<String> getVnfPackages() {
        return vnfPackages;
    }

    public void setVnfPackages(List<String> vnfPackages) {
        this.vnfPackages = vnfPackages;
    }

    public void addUser(String userName) {
        this.users.add(userName);
    }

    public void delUser(String userName) {
        this.users.remove(userName);
    }

    public boolean isDeletable() {
        if (!nsds.isEmpty() || !pnfds.isEmpty() || !vnfPackages.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.projectId == null)
            throw new MalformattedElementException("ProjectResource without projectId");
    }
}
