package it.nextworks.nfvmano.sebastian.security;

import it.nextworks.nfvmano.sebastian.admin.elements.Sla;
import it.nextworks.nfvmano.sebastian.admin.elements.Tenant;
import it.nextworks.nfvmano.sebastian.admin.elements.VirtualResourceUsage;

import java.util.List;

/**
 * Created by Marco Capitani on 29/06/18.
 *
 * @author Marco Capitani <m.capitani AT nextworks.it>
 */
public class SecurityTenant extends Tenant {

    private Tenant baseTenant;
    public String role;

    SecurityTenant(Tenant baseTenant, String role) {
        super(
                baseTenant.getGroup(),
                baseTenant.getUsername(),
                baseTenant.getPassword()

        );
        this.baseTenant = baseTenant;
        this.role = role;
    }

    @Override
    public List<Sla> getSla() {
        return baseTenant.getSla();
    }

    @Override
    public List<String> getVsdId() {
        return baseTenant.getVsdId();
    }

    @Override
    public List<String> getVsiId() {
        return baseTenant.getVsiId();
    }

    @Override
    public VirtualResourceUsage getAllocatedResources() {
        return baseTenant.getAllocatedResources();
    }
}
