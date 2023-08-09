package it.nextworks.nfvmano.catalogue.auth;

import org.apache.commons.codec.binary.Base64;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtilities {

    private static final Logger log = LoggerFactory.getLogger(AuthUtilities.class);

    public static void decodeJWT(String jwtToken) {
        log.debug("Going to decode JWT...");
        String[] split_string = jwtToken.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        String base64EncodedSignature = split_string[2];

        Base64 base64Url = new Base64(true);

        String header = new String(base64Url.decode(base64EncodedHeader));
        log.debug("JWT Header: " + header);

        String body = new String(base64Url.decode(base64EncodedBody));
        log.debug("JWT Body: " + body);

        String signature = new String(base64Url.decode(base64EncodedSignature));
        log.debug("JWT Signature: " + signature);
    }

    public static String getUserNameFromJWT() {

        String userName = null;

        log.debug("Going to validate received TOKEN for getting user infos...");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Authenticated user: " + authentication.getName()
                + " | Role: " + authentication.getAuthorities().toString()
                + " | Credentials: " + authentication.getCredentials().toString()
                + " | Details: " + authentication.getDetails().toString()
                + " | Principal: " + authentication.getPrincipal().toString());

        if (authentication.getPrincipal() instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
            // retrieving username here
            userName = kp.getKeycloakSecurityContext().getToken().getPreferredUsername();
        }

        return userName;
    }

    public static AccessToken getJWT() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        AccessToken token = null;

        if (authentication.getPrincipal() instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
            // retrieving username here
            token = kp.getKeycloakSecurityContext().getToken();
        }

        return token;
    }

    public static String parseAuthorizationHeader(String authorization) {
        return authorization.split(" ")[1];
    }
}
