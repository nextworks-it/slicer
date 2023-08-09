package it.nextworks.nfvmano.catalogue.auth.projectmanagement;

import io.swagger.annotations.ApiParam;
import it.nextworks.nfvmano.catalogue.auth.usermanagement.UserResource;
import it.nextworks.nfvmano.catalogue.repos.ProjectRepository;
import it.nextworks.nfvmano.catalogue.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/catalogue/projectManagement")
public class ProjectController {

    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public ProjectController() {
    }

    @PostConstruct
    public void init() {
        /*try {
            List<UserRepresentation> userRepresentations = keycloakService.getUsers();
        } catch (NotPermittedOperationException e) {
            e.printStackTrace();
        } catch (FailedOperationException e) {
            e.printStackTrace();
        }*/

        ProjectResource project = new ProjectResource("admin", "Admins project");
        project.addUser("admin");
        Optional<ProjectResource> optional = projectRepository.findByProjectId(project.getProjectId());
        if (!optional.isPresent()) {
            projectRepository.saveAndFlush(project);
            log.debug("Project " + project.getProjectId() + " successfully created");
        }

        UserResource userResource = new UserResource("admin", "Admin", "Admin", "admin");
        userResource.addProject("admin");
        Optional<UserResource> optionalUserResource = userRepository.findByUserName(userResource.getUserName());
        if (!optionalUserResource.isPresent()) {
            userRepository.saveAndFlush(userResource);
            log.debug("User " + userResource.getUserName() + " successfully created");
        }
    }

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public ResponseEntity<?> createProject(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
                                           @RequestBody ProjectResource project) {

        log.debug("Received request for new Project creation");
        if ((project == null) || (project.getProjectId() == null)) {
            log.error("Malformatted Project - Not acceptable");
            return new ResponseEntity<String>("Project or Project ID null", HttpStatus.BAD_REQUEST);
        }

        ProjectResource createdProjectResource;
        Optional<ProjectResource> optional = projectRepository.findByProjectId(project.getProjectId());
        if (optional.isPresent()) {
            return new ResponseEntity<String>("Project already present in DB", HttpStatus.CONFLICT);
        } else {
            project.addUser("admin");
            createdProjectResource = projectRepository.saveAndFlush(project);
            log.debug("Project " + project.getProjectId() + " successfully created");

            log.debug("Going to update user admin with new created project " + project.getProjectId());
            Optional<UserResource> optionalUserResource = userRepository.findByUserName("admin");
            if (optionalUserResource.isPresent()) {
                UserResource userResource = optionalUserResource.get();
                userResource.addProject(createdProjectResource.getProjectId());
                userRepository.saveAndFlush(userResource);
                log.debug("User admin successfully updated with new project " + createdProjectResource.getProjectId());
            } else {
                log.warn("Unable to update user admin with new created project " + createdProjectResource.getProjectId());
            }

        }

        return new ResponseEntity<ProjectResource>(createdProjectResource, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public ResponseEntity<?> getProjects(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {

        log.debug("Received request for getting Projects");

        List<ProjectResource> projectResources = projectRepository.findAll();

        return new ResponseEntity<List<ProjectResource>>(projectResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/{projectId}", method = RequestMethod.GET)
    public ResponseEntity<?> getProject(@ApiParam(value = "", required = true)
                                        @PathVariable("projectId") String projectId,
                                        @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {

        log.debug("Received request for getting Project with Project ID " + projectId);

        Optional<ProjectResource> optional = projectRepository.findByProjectId(projectId);
        if (optional.isPresent()) {
            return new ResponseEntity<ProjectResource>(optional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Project with projectId " + projectId + " not present in DB", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/projects/{projectId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProject(@ApiParam(value = "", required = true)
                                           @PathVariable("projectId") String projectId,
                                           @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {

        log.debug("Received request for deleting Project with Project ID " + projectId);

        ProjectResource projectResource;
        Optional<ProjectResource> optional = projectRepository.findByProjectId(projectId);
        if (optional.isPresent()) {
            projectResource = optional.get();
            if (projectResource.isDeletable()) {
                projectRepository.delete(projectResource);
                log.debug("Project " + projectId + " successfully deleted");
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                log.error("Project cannot be deleted because in use");
                return new ResponseEntity<String>("Project cannot be deleted because in use", HttpStatus.CONFLICT);
            }
        } else {
            log.error("Project with sliceId " + projectId + " not present in DB");
            return new ResponseEntity<String>("Project with projectId " + projectId + " not present in DB", HttpStatus.BAD_REQUEST);
        }
    }
}
