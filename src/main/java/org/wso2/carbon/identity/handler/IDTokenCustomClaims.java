package org.wso2.carbon.identity.handler;

import com.nimbusds.jwt.JWTClaimsSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.authz.OAuthAuthzReqMessageContext;
import org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext;
import org.wso2.carbon.identity.openidconnect.CustomClaimsCallbackHandler;
import org.wso2.carbon.identity.openidconnect.DefaultOIDCClaimsCallbackHandler;
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

        for (Map.Entry<String, Object> claimEntry: claims.entrySet()) {
            String claimValue = claimEntry.getValue().toString();
            builder.claim(claimEntry.getKey(), claimValue);
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

        for (Map.Entry<String, Object> claimEntry: claims.entrySet()) {
            String claimValue = claimEntry.getValue().toString();
            builder.claim(claimEntry.getKey(), claimValue);
        }

        return builder.build();
    }

}
