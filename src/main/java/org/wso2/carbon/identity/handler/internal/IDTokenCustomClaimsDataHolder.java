package org.wso2.carbon.identity.handler.internal;


import com.etisalat.credential.encryption.module.EtisalatEncryptor;

/**
 * This is the DataHolder class of IDTokenCustomClaims bundle. This holds a reference to the
 * ApplicationManagementService.
 */
public class  IDTokenCustomClaimsDataHolder {

    private static IDTokenCustomClaimsDataHolder thisInstance = new IDTokenCustomClaimsDataHolder();
    private EtisalatEncryptor encryptionService = null;


    private IDTokenCustomClaimsDataHolder() {
    }

    public static IDTokenCustomClaimsDataHolder getInstance() {
        return thisInstance;
    }

    public EtisalatEncryptor getEncryptionService() {

        return encryptionService;
    }

    public void setEncryptionService(EtisalatEncryptor encryptionService) {

        this.encryptionService = encryptionService;
    }

}
