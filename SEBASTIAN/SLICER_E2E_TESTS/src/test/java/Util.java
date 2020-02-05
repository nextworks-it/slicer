import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class  Util {

    private static final Logger log = LoggerFactory.getLogger(Util.class);

    public static ResponseEntity<?> performHttpRequest(Class classObjectsRetrieved, Object request, String url, HttpMethod httpMethod, String cookies) {
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        header.add("Cookie", cookies);
        HttpEntity<?> httpEntity = new HttpEntity<>(request, header);

        try {
            log.info("URL performing the request to: " + url);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<?> httpResponse =
                    restTemplate.exchange(url, httpMethod, httpEntity, classObjectsRetrieved);
            HttpStatus code = httpResponse.getStatusCode();
            log.info("Response code: " + httpResponse.getStatusCode().toString());
            if(httpResponse.getBody()!=null)
                log.info("Body response: " + httpResponse.getBody().toString());
            return httpResponse;
        } catch (RestClientException e) {
            e.printStackTrace();
            log.info(e.getLocalizedMessage());
            return null;
        }
    }
}
