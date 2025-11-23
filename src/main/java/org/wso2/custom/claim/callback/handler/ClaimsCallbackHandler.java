package org.wso2.custom.claim.callback.handler;

import com.nimbusds.jwt.JWTClaimsSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.authz.OAuthAuthzReqMessageContext;
import org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext;
import org.wso2.carbon.identity.openidconnect.CustomClaimsCallbackHandler;
import org.wso2.carbon.identity.openidconnect.DefaultOIDCClaimsCallbackHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * This ClaimsCallbackHandler handler is responsible for appending custom claims to the self contained access token and ID token.
 */
public class ClaimsCallbackHandler extends DefaultOIDCClaimsCallbackHandler implements CustomClaimsCallbackHandler {

    private static final Log log = LogFactory.getLog(ClaimsCallbackHandler.class);

    @Override
    public JWTClaimsSet handleCustomClaims(JWTClaimsSet.Builder builder, OAuthTokenReqMessageContext request) throws IdentityOAuth2Exception {

        if (log.isDebugEnabled()) {
            log.debug("Handling custom claims in OAuth token request.");
        }

        super.handleCustomClaims(builder, request);
        Map<String, Object> claims = createJWTClaimSet(null, request);
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            builder.claim(entry.getKey(), entry.getValue());
        }

        return builder.build();

    }


    @Override
    public JWTClaimsSet handleCustomClaims(JWTClaimsSet.Builder builder, OAuthAuthzReqMessageContext request) throws IdentityOAuth2Exception {

        if (log.isDebugEnabled()) {
            log.debug("Handling custom claims in Authorization request.");
        }

        super.handleCustomClaims(builder, request);
        Map<String, Object> claims = createJWTClaimSet(request, null);
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            builder.claim(entry.getKey(), entry.getValue());
        }
        return builder.build();

    }

    private Map<String, Object> createJWTClaimSet(OAuthAuthzReqMessageContext authAuthzReqMessageContext,
                                                  OAuthTokenReqMessageContext tokenReqMessageContext)
            throws IdentityOAuth2Exception {

        Map<String, Object> returnObject = new HashMap<>();

        returnObject.put(Constants.USER_CLAIM_1, "value1");
        returnObject.put(Constants.USER_CLAIM_2, "value2");

        return returnObject;
    }

    private static final class Constants {
        private static final String USER_CLAIM_1 = "userClaim1";
        private static final String USER_CLAIM_2 = "userClaim2";
    }

}
