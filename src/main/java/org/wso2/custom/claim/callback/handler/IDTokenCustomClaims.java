package org.wso2.custom.claim.callback.handler;

import com.nimbusds.jwt.JWTClaimsSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.authz.OAuthAuthzReqMessageContext;
import org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext;
import org.wso2.carbon.identity.openidconnect.CustomClaimsCallbackHandler;
import org.wso2.carbon.identity.openidconnect.DefaultOIDCClaimsCallbackHandler;

import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This IDTokenCustomClaims handler is responsible for appending custom claims to the self contained access token.
 */
public class IDTokenCustomClaims extends DefaultOIDCClaimsCallbackHandler implements CustomClaimsCallbackHandler {

    private static final Log log = LogFactory.getLog(IDTokenCustomClaims.class);

    @Override
    public JWTClaimsSet handleCustomClaims(JWTClaimsSet.Builder builder, OAuthTokenReqMessageContext request) throws IdentityOAuth2Exception {

        if (log.isDebugEnabled()) {
            log.debug("Handling custom claims in OAuth token request.");
        }

        JWTClaimsSet jwtClaimsSet = super.handleCustomClaims(builder, request);
        Map<String, Object> claims = jwtClaimsSet.getClaims();

        List<String> exemptClaimKeys = Arrays.asList("iss", "sub", "azp", "iat", "jti", "nbf", "client_id", "scope", "aut", "exp", "aud");

        for (Map.Entry<String, Object> claimEntry : claims.entrySet()) {
            if (!exemptClaimKeys.contains(claimEntry.getKey())) {
                String claimValue = claimEntry.getValue().toString();

                    String encryptedClaimValue = "abc";
                    builder.claim(claimEntry.getKey(), encryptedClaimValue);
            }
        }

        return builder.build();
    }


    @Override
    public JWTClaimsSet handleCustomClaims(JWTClaimsSet.Builder builder, OAuthAuthzReqMessageContext request) throws IdentityOAuth2Exception {

        if (log.isDebugEnabled()) {
            log.debug("Handling custom claims in Authorization request.");
        }

        JWTClaimsSet jwtClaimsSet = super.handleCustomClaims(builder, request);
        Map<String, Object> claims = jwtClaimsSet.getClaims();

        List<String> exemptClaimKeys = Arrays.asList("iss", "sub", "azp", "iat", "jti", "nbf", "client_id", "scope", "aut", "exp", "aud");

        for (Map.Entry<String, Object> claimEntry : claims.entrySet()) {
            if (!exemptClaimKeys.contains(claimEntry.getKey())) {
                String claimValue = claimEntry.getValue().toString();
                    String encryptedClaimValue = "abc";
                    builder.claim(claimEntry.getKey(), encryptedClaimValue);
            }
        }

        return builder.build();
    }

    private Map<String, Object> createJWTClaimSet(final OAuthAuthzReqMessageContext authAuthzReqMessageContext,
                                                  final OAuthTokenReqMessageContext tokenReqMessageContext)
            throws IdentityOAuth2Exception {

        final Map<String, Object> returnObject = new HashMap<>();

        try {
            final HttpServletRequestWrapper requestWrapper;

            if (tokenReqMessageContext != null) {
                // Extracting URL parameters in the query string from the request to the token endpoint
                // e.g., https://localhost:9443/oauth2/token?param1=value1&param2=value2 -> {param1=value1, param2=value2}
                requestWrapper = tokenReqMessageContext.getOauth2AccessTokenReqDTO().getHttpServletRequestWrapper();
            } else if (authAuthzReqMessageContext != null) {
                // Extracting URL parameters in the query string from the request to the authorization endpoint
                // e.g., https://localhost:9443/oauth2/authorize?param1=value1&param2=value2 -> {param1=value1, param2=value2}
                // Important: This code branch will most likely not get executed since there's no claims returned from the authorization endpoint.
                requestWrapper = authAuthzReqMessageContext.getAuthorizationReqDTO().getHttpServletRequestWrapper();
            } else {
                log.error("Unable to extract request wrapper. Returning an empty map.");
                return returnObject;
            }

            final String clientChannel = requestWrapper.getParameter(Constants.CLIENT_CHANNEL);
            final String clientVersion = requestWrapper.getParameter(Constants.CLIENT_VERSION);

            // Only add values if they are present
            if (StringUtils.isNotBlank(clientChannel)) {
                returnObject.put(Constants.CLIENT_CHANNEL, clientChannel);
            } else {
                log.warn("Client channel is null or empty. Not adding to JWT claim set.");
            }

            if (StringUtils.isNotBlank(clientVersion)) {
                returnObject.put(Constants.CLIENT_VERSION, clientVersion);
            } else {
                log.warn("Client version is null or empty. Not adding to JWT claim set.");
            }

        } catch (Exception e) {
            log.error("Error adding custom claims to JWT claim set. Returning default values only.", e);
        }

        return returnObject;
    }

    private static final class Constants {
        private static final String CLIENT_CHANNEL = "clientChannel";
        private static final String CLIENT_VERSION = "clientVersion";
    }

}
