package org.wso2.carbon.identity.handler.internal;

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

}
