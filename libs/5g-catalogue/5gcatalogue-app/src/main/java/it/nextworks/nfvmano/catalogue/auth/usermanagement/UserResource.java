package it.nextworks.nfvmano.catalogue.auth.usermanagement;

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
public class UserResource implements DescriptorInformationElement {

    String userName;
    String firstName;
    String lastName;
    String defaultProject;
    String externalId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    List<String> projects = new ArrayList<>();
    @Id
    @GeneratedValue
    private UUID id;

    public UserResource() {
    }

    public UserResource(String userName) {
        this.userName = userName;
    }

    public UserResource(String userName, String firstName, String lastName) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserResource(String userName, String firstName, String lastName, String defaultProject) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.defaultProject = defaultProject;
    }

    public UUID getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDefaultProject() {
        return defaultProject;
    }

    public void setDefaultProject(String defaultProject) {
        this.defaultProject = defaultProject;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    public void addProject(String projectId) {
        this.projects.add(projectId);
    }

    public void delProject(String projectId) {
        this.projects.remove(projectId);
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if (this.userName == null)
            throw new MalformattedElementException("UserResource without userName");
    }
}
