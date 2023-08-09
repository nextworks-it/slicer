package it.nextworks.nfvmano.catalogue.plugins.cataloguePlugin.mano.interfaces;

import it.nextworks.nfvmano.libs.common.elements.KeyValuePair;
import it.nextworks.nfvmano.libs.common.enums.OperationStatus;

import java.util.List;
import java.util.Map;

public interface ManoSynchronizationInterface {

    /**
     * This method is called at startup to synchronize Catalogue and Mano Plugin.
     *
     * @param project Project
     *
     * @return Map <id, List<version>> of all Vnf descriptors handled by the Mano Plugin
     */
    Map<String, List<String>> getAllVnfd(String project);

    /**
     * This method is called at startup to synchronize Catalogue and Mano Plugin.
     *
     * @param project Project
     *
     * @return Map <id, List<version>> of all Ns descriptors handled by the Mano Plugin
     */
    Map<String, List<String>> getAllNsd(String project);

    /**
     * This method is called by Catalogue when a new descriptor is found.
     *
     * @param descriptorId Descriptor Id
     * @param descriptorVersion Descriptor version
     * @param project Project
     *
     * @return KeyValuePair with Pkg path and PathType
     */
    KeyValuePair getTranslatedPkgPath(String descriptorId, String descriptorVersion, String project);

    /**
     * This method is called by Catalogue to notify the descriptor on-boarding status.
     *
     * @param infoId Catalogue Pkg info Id
     * @param descriptorId Descriptor Id
     * @param descriptorVersion Descriptor version
     * @param project Project
     *
     */
    void notifyOnboarding(String infoId, String descriptorId, String descriptorVersion, String project, OperationStatus opStatus);

    /**
     * This method is called by Catalogue to notify the descriptor deletion status.
     *
     * @param infoId Catalogue Pkg info Id
     * @param descriptorId Descriptor Id
     * @param descriptorVersion Descriptor version
     * @param project Project
     *
     */
    void notifyDelete(String infoId, String descriptorId, String descriptorVersion, String project, OperationStatus opStatus);


    /**
     * This method is called by Catalogue to retrieve the Mano Pkg info Id .
     *
     * @param catalogueInfoId Catalogue Pkg info Id
     *
     * @return Mano Pkg info Id
     */
    String getManoPkgInfoId(String catalogueInfoId);

    /**
     * This method is used for the runtime synchronization of Catalogue and MANO Plugin.
     */
    void RuntimeSynchronization();
}
