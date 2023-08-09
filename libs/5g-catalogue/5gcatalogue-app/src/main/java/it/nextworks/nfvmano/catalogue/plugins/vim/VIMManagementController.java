package it.nextworks.nfvmano.catalogue.plugins.vim;

import io.swagger.annotations.ApiParam;
import it.nextworks.nfvmano.catalogue.plugins.PluginsManager;
import it.nextworks.nfvmano.libs.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.common.exceptions.NotExistingEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/catalogue/vimManagement")
public class VIMManagementController {

    private static final Logger log = LoggerFactory.getLogger(VIMManagementController.class);

    @Autowired
    PluginsManager pluginsManager;

    public VIMManagementController() {

    }

    @RequestMapping(value = "/vims", method = RequestMethod.POST)
    public ResponseEntity<?> createVIM(@ApiParam(value = "", required = true) @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization, @RequestBody VIM vim) throws MethodNotImplementedException {

        log.debug("Received request for new VIM loading");
        if ((vim == null) || (vim.getVimId() == null)) {
            log.error("Malformatted VIM - Not acceptable");
            return new ResponseEntity<String>("VIM or VIM ID null", HttpStatus.BAD_REQUEST);
        } else {
            if (vim.getVimType() == null) {
                log.error("Malformatted VIM - Not acceptable");
                return new ResponseEntity<String>("VIM TYPE null", HttpStatus.BAD_REQUEST);
            }
        }

        String createdVimId;

        try {
            createdVimId = pluginsManager.createVIMPlugin(vim);
        } catch (AlreadyExistingEntityException e) {
            return new ResponseEntity<String>("VIM already present in DB", HttpStatus.CONFLICT);
        } catch (MethodNotImplementedException e) {
            return new ResponseEntity<String>("Unsupported VIM type", HttpStatus.BAD_REQUEST);
        } catch (MalformattedElementException e) {
            return new ResponseEntity<String>("Malformatted VIM: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>(createdVimId, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/vims", method = RequestMethod.GET)
    public ResponseEntity<List<VIM>> getVIMs(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) throws MethodNotImplementedException {

        log.debug("Received request for getting VIM plugins");

        List<VIM> vims = pluginsManager.getAllVIMPlugins();

        return new ResponseEntity<List<VIM>>(vims, HttpStatus.OK);
    }

    @RequestMapping(value = "/vims/{vimId}", method = RequestMethod.GET)
    public ResponseEntity<?> getVIM(@ApiParam(value = "", required = true) @PathVariable("vimId") String vimId, @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) throws MethodNotImplementedException {

        log.debug("Received request for getting VIM plugin with vimId {}", vimId);

        VIM vim = null;

        try {
            vim = pluginsManager.getVIMPlugin(vimId);
        } catch (NotExistingEntityException e) {
            return new ResponseEntity<String>("VIM Plugin with vimId " + vimId + " not present in DB", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<VIM>(vim, HttpStatus.OK);
    }
}
