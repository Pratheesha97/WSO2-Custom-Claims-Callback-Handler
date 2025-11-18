package org.wso2.custom.claim.callback.handler.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.ComponentContext;
import org.wso2.custom.claim.callback.handler.ClaimsCallbackHandler;

@Component(
        name = "org.wso2.custom.claim.callback.component",
        immediate = true)
public class ClaimsCallbackHandlerServiceComponent {
    private static final Log log = LogFactory.getLog(ClaimsCallbackHandlerServiceComponent.class);
    private ServiceRegistration<?> registration;

    @Activate
    protected void activate(ComponentContext componentContext) {
        try {
            BundleContext bundleContext = componentContext.getBundleContext();
            registration = bundleContext.registerService(
                    ClaimsCallbackHandler.class.getName(),
                    new ClaimsCallbackHandler(),
                    null
            );
            log.info("CustomClaimsCallbackHandler registered successfully.");
        } catch (Throwable e) {
            log.error("Error while registering CustomClaimsCallbackHandler.", e);
        }
    }

    @Deactivate
    protected void deactivate(ComponentContext componentContext) {
        if (registration != null) {
            registration.unregister();
            log.info("CustomClaimsCallbackHandler service unregistered successfully.");
        }
    }

}
