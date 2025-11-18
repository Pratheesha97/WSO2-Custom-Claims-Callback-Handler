package org.wso2.custom.claim.callback.handler.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.*;
import org.osgi.service.component.ComponentContext;
import org.wso2.custom.claim.callback.handler.ClaimsCallbackHandler;

@Component(
        name = "identity.handler",
        immediate = true
)
public class ClaimsCallbackHandlerServiceComponent {

    private static final Log log = LogFactory.getLog(ClaimsCallbackHandlerServiceComponent.class);

    @Activate
    protected void activate(ComponentContext componentContext) {

        try {
            BundleContext bundleContext = componentContext.getBundleContext();
            bundleContext.registerService(ClaimsCallbackHandler.class.getName(),
                    new ClaimsCallbackHandler(), null);
            log.info("ClaimsCallbackHandler handler activated.");
        } catch (Throwable e) {
            log.error("Error while activating id-token-custom-claims.", e);
        }
    }

}
