package org.wso2.carbon.identity.handler;

import com.etisalat.credential.encryption.module.EtisalatEncryptor;
import com.etisalat.credential.encryption.module.exception.EtisalatEncryptionException;
import com.nimbusds.jwt.JWTClaimsSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.authz.OAuthAuthzReqMessageContext;
import org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext;
import org.wso2.carbon.identity.openidconnect.CustomClaimsCallbackHandler;
import org.wso2.carbon.identity.openidconnect.DefaultOIDCClaimsCallbackHandler;

import java.util.Arrays;
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
                EtisalatEncryptor etisalatEncryptor = new EtisalatEncryptor();
                try {
                    String encryptedClaimValue = etisalatEncryptor.generateEncryptedCredential(claimValue);
                    builder.claim(claimEntry.getKey(), encryptedClaimValue);
                } catch (EtisalatEncryptionException e) {
                    throw new IdentityOAuth2Exception("Error while encrypting claim values", e);
                }
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
                EtisalatEncryptor etisalatEncryptor = new EtisalatEncryptor();
                try {
                    String encryptedClaimValue = etisalatEncryptor.generateEncryptedCredential(claimValue);
                    builder.claim(claimEntry.getKey(), encryptedClaimValue);
                } catch (EtisalatEncryptionException e) {
                    throw new IdentityOAuth2Exception("Error while encrypting claim values", e);
                }
            }
        }

        return builder.build();
    }

}
