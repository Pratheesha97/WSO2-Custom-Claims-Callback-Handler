package org.wso2.carbon.identity.handler.internal;

import com.etisalat.credential.encryption.module.EtisalatEncryptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.*;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.identity.handler.IDTokenCustomClaims;

@Component(
        name = "identity.handler",
        immediate = true
)
public class IDTokenCustomClaimsServiceComponent {

    private static final Log log = LogFactory.getLog(IDTokenCustomClaimsServiceComponent.class);

    @Activate
    protected void activate(ComponentContext componentContext) {

        try {
            BundleContext bundleContext = componentContext.getBundleContext();
            bundleContext.registerService(IDTokenCustomClaims.class.getName(),
                    new IDTokenCustomClaims(), null);
            log.info("IDTokenCustomClaims handler activated.");
        } catch (Throwable e) {
            log.error("Error while activating id-token-custom-claims.", e);
        }
    }

    @Reference(
            name = "com.etisalat.credential.encryption.client.manager.component",
            service = EtisalatEncryptor.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetEncryptionService")
    protected void setEncryptionService(EtisalatEncryptor encryptionService) {

        IDTokenCustomClaimsDataHolder.getInstance().setEncryptionService(encryptionService);

        if (log.isDebugEnabled()) {
            log.debug("Setting the Encryption Service.");
        }
    }

    protected void unsetEncryptionService(EtisalatEncryptor encryptionService) {

        IDTokenCustomClaimsDataHolder.getInstance().setEncryptionService(null);

        if (log.isDebugEnabled()) {
            log.debug("Unset the Encryption Service.");
        }
    }


}
