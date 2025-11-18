package org.wso2.custom.claim.callback.handler.internal;


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
