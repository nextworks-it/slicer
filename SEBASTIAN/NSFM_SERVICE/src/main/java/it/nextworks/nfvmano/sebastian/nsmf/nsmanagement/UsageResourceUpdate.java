package it.nextworks.nfvmano.sebastian.nsmf.nsmanagement;

import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.admin.elements.VirtualResourceUsage;
import it.nextworks.nfvmano.sebastian.common.VirtualResourceCalculatorService;
import it.nextworks.nfvmano.sebastian.record.NsRecordService;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsageResourceUpdate {

    @Autowired
    private AdminService adminService;

    @Autowired
    private VirtualResourceCalculatorService virtualResourceCalculatorService;

    @Autowired
    NsRecordService nsRecordService;

    private static final Logger log = LoggerFactory.getLogger(UsageResourceUpdate.class);

    public UsageResourceUpdate(){

    }

    private VirtualResourceUsage getVirtualResourceUsageByNsiId(String nsiId, boolean current) throws Exception {
        NetworkSliceInstance nsi = nsRecordService.getNsInstance(nsiId);
        //NetworkSliceInstance nsi = vsRecordService.getNsInstance(networkSliceId);
        return virtualResourceCalculatorService.computeVirtualResourceUsage( nsi, current);
    }

    public void addResourceUpdate(String nsiId, String tenantId) throws Exception {
        log.debug("Adding resource usage for tenant {}",tenantId);
        VirtualResourceUsage resourceUsage=getVirtualResourceUsageByNsiId(nsiId, true);
        adminService.addUsedResourcesInTenant(tenantId, resourceUsage);
    }

    public void removeResourceUpdate(String nsiId, String tenantId) throws Exception {
        log.debug("Removing resource usage for tenant {}",tenantId);
        VirtualResourceUsage resourceUsage=getVirtualResourceUsageByNsiId(nsiId, true);
        adminService.removeUsedResourcesInTenant(tenantId, resourceUsage);
    }

    public void modifyResourceUsageUpdate(String nsiId, String tenantId) throws Exception {
        log.debug("Modifying resource usage for tenant {}",tenantId);
        VirtualResourceUsage newResourceUsage=getVirtualResourceUsageByNsiId(nsiId, true);
        VirtualResourceUsage oldResourceUsage=getVirtualResourceUsageByNsiId(nsiId, false);
        adminService.removeUsedResourcesInTenant(tenantId, oldResourceUsage);
        adminService.addUsedResourcesInTenant(tenantId, newResourceUsage);
    }

}
