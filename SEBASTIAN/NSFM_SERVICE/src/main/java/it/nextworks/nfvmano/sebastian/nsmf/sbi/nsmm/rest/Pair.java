/*
 * nsmm
 * NorthBound Interface of Network Service Mesh Manager for 5GZORRO project. The NSMM provides API to manage resouces on the VIMs in order to establish secure intra-domain connections between services. In details: - a set of endpoints, called network-resources, is used to manage network resources on the selected vim to provide an external point of connectivity with a VPN server (wireguard). These network-resources considering OpenStack as a VIM include:   - networks and subnets   - routers and interfaces toward a floating network, to allow the creation of service-access-points   - configuration of the gateway VM included in the NSD, which provides the VPN service - a set of endpoints, called vpn-connections, is design to manage the VPN connections between remote peers.  The NSMM manages resources on a single domain and it is invoked by the slicer of the same domain to create all the network resources before the network service instantiation. After the creation of the network service, it is invoked to configure the gateway. Finally, the ISSM request to the slicer of each domain the creation of a secure channel which is forwarded to the NSMM that creates the VPN connection between the two gateways
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package it.nextworks.nfvmano.sebastian.nsmf.sbi.nsmm.rest;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.java.JavaClientCodegen", date = "2022-03-17T09:30:29.316+01:00[Europe/Rome]")
public class Pair {
    private String name = "";
    private String value = "";

    public Pair (String name, String value) {
        setName(name);
        setValue(value);
    }

    private void setName(String name) {
        if (!isValidString(name)) return;

        this.name = name;
    }

    private void setValue(String value) {
        if (!isValidString(value)) return;

        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    private boolean isValidString(String arg) {
        if (arg == null) return false;
        if (arg.trim().isEmpty()) return false;

        return true;
    }
}
