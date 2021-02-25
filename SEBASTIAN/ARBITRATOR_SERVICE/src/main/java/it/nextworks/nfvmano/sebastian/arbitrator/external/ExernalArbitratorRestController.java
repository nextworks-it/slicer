package it.nextworks.nfvmano.sebastian.arbitrator.external;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Arbitrator notification API")
@RestController
@CrossOrigin
@RequestMapping("/arbitrator")
public class ExernalArbitratorRestController {

    private static final Logger log = LoggerFactory.getLogger(ExernalArbitratorRestController.class);

    @Autowired
    private ExternalArbitratorService externalArbitratorService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")})

    @RequestMapping(value = "/notifyArbitrationResponse/{operationId}", method = RequestMethod.POST)

    public ResponseEntity<?> notifyArbirationResponse(@PathVariable(value="operationId") String operationId,@RequestBody ArbitratorResponse response) {
        log.debug("Received Arbitration Response");
        try {
            externalArbitratorService.processArbitratorRepsonse(operationId, response);
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (NotExistingEntityException e) {
           log.error(e.getMessage(), e);
           return new ResponseEntity<>("Arbitration operation with id:"+operationId+" not found", HttpStatus.NOT_FOUND);
        }

    }


}
