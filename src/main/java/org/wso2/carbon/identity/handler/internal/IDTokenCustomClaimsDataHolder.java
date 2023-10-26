package org.wso2.carbon.identity.handler.internal;

import org.wso2.carbon.identity.application.mgt.ApplicationManagementService;


/**
 * This is the DataHolder class of IDTokenCustomClaims bundle. This holds a reference to the
 * ApplicationManagementService.
 */
public class  IDTokenCustomClaimsDataHolder {

    private static IDTokenCustomClaimsDataHolder thisInstance = new IDTokenCustomClaimsDataHolder();


    private IDTokenCustomClaimsDataHolder() {
    }

    public static IDTokenCustomClaimsDataHolder getInstance() {
        return thisInstance;
    }

}
