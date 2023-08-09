package it.nextworks.nfvmano.catalogue.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.libs.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.NotPermittedOperationException;
import okhttp3.Response;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
@ConditionalOnProperty(value = "keycloak.enabled", matchIfMissing = true)
public class KeycloakService {

    private static final Logger log = LoggerFactory.getLogger(KeycloakService.class);

    @Value("${keycloak.auth-server-url}")
    private String basePath;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${catalogue.userGroup}")
    private String userGroupId;

    @Autowired
    private KeycloakRestTemplate keycloakRestTemplate;

    public List<UserRepresentation> getUsers() throws NotPermittedOperationException, FailedOperationException {
        log.debug("Going to retrieve users from realm " + realm + "...");

        ResponseEntity<UserRepresentation[]> response =
                keycloakRestTemplate.getForEntity(basePath + "/admin/realms/" + realm + "/users", UserRepresentation[].class);

        if (response.getStatusCode() == HttpStatus.FORBIDDEN) {
            throw new NotPermittedOperationException("Full authentication is required to access this resource");
        } else if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            throw new FailedOperationException("Your credentials have been expired, please login");
        }

        for (UserRepresentation userRepresentation : response.getBody()) {
            log.debug("Keycloak user: " + userRepresentation.getUsername());
        }

        return Arrays.asList(response.getBody());
    }

    public UserRepresentation getUser(String userId) throws FailedOperationException {
        log.debug("Going to retrieve user " + userId + " from realm " + realm + "...");

        ResponseEntity<UserRepresentation> response =
                keycloakRestTemplate.getForEntity(basePath + "/admin/realms/" + realm + "/users/" + userId, UserRepresentation.class);

        if (response.getStatusCode() == HttpStatus.FORBIDDEN) {
            throw new FailedOperationException("Full authentication is required to access this resource");
        } else if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            throw new FailedOperationException("Your credentials have been expired, please login");
        }

        log.debug("Keycloak user: " + response.getBody().getUsername());

        return response.getBody();
    }

    public UserRepresentation createUser(UserRepresentation userRepresentation) throws NotPermittedOperationException, FailedOperationException,
            AlreadyExistingEntityException, MalformattedElementException {
        log.debug("Going to create new user in realm " + realm + "...");

        ResponseEntity<Response> response =
                keycloakRestTemplate.postForEntity(basePath + "/admin/realms/" + realm + "/users", userRepresentation, Response.class);

        if (response.getStatusCode() == HttpStatus.FORBIDDEN) {
            throw new NotPermittedOperationException("Full authentication is required to access this resource");
        } else if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            throw new FailedOperationException("Your credentials have been expired, please login");
        } else if (response.getStatusCode() == HttpStatus.CONFLICT) {
            throw new AlreadyExistingEntityException("User with userName " + userRepresentation.getUsername() + " already exists");
        } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            throw new MalformattedElementException("Unable to create user with userName " + userRepresentation.getUsername() + ". Malformatted element");
        } else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            throw new FailedOperationException("An internal server error occurs");
        }

        List<UserRepresentation> userRepresentations = getUsers();
        for (UserRepresentation user : userRepresentations) {
            if (user.getUsername().equalsIgnoreCase(userRepresentation.getUsername())) {
                return user;
            }
        }
        return userRepresentation;
    }

    public void deleteUser(String userId) {
        log.debug("Going to delete user " + userId + " from realm " + realm + "...");

        keycloakRestTemplate.delete(basePath + "/admin/realms/" + realm + "/users/" + userId);
    }

    public void addUserToGroup(String userId) {
        log.debug("Going to add user " + userId + " in realm " + realm + " to group " + userGroupId + "...");

        keycloakRestTemplate.put(basePath + "/admin/realms/" + realm + "/users/" + userId + "/groups/" + userGroupId, null);
    }

    public UserRepresentation buildUserRepresentation(String userName, String firstName, String lastName) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setAttributes(new HashMap<>());
        userRepresentation.setEmailVerified(false);
        userRepresentation.setUsername(userName);
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setRealmRoles(Arrays.asList("User"));
        userRepresentation.setGroups(Arrays.asList("5G-MEDIA", "5GCity"));

        ObjectMapper mapper = new ObjectMapper();
        try {
            log.debug("New 5G Catalogue User: " + mapper.writeValueAsString(userRepresentation));
        } catch (JsonProcessingException e) {
            log.error("Unable to parse new created user: " + e.getMessage());
        }

        return userRepresentation;
    }
}
