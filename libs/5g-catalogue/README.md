# 5G Apps & Services Catalogue

NFV-SDN Catalogue capable of storing and  versioning:

- Network Service Descriptors (NSDs)
- Virtual Network Function Packages (VNF Packages)
- Physical Network Function Descriptors (PNFs)
- Multi-access Edge Computing App Packages (MEC App Packages)

#### In roadmap
- Software Defined Network App Packages (SDN App Packages)

## Getting Started

### Hardware requirements
- 4 vCPU@1GHz
- 4GB RAM
- 20GB storage

### Prerequisites

* [Oracle-Java8] - Oracle version 8 is preferred, otherwise [OpenJDK-8] + [OpenJFX]
* [Maven] - version 3.3.9 is required
* [PostgresSQL] - as internal DB 
* [Apache Kafka] - as internal message bus, configured with the following topics: PRIVATEcatalogue-onboarding-local, PRIVATEcatalogue-onboarding-remote, PUBLICcatalogue-onboarding-local, PUBLICcatalogue-onboarding-remote. For properly connecting the 5gcatalogue-app to the Kafka bus you have modify the [application.properties](https://github.com/nextworks-it/5g-catalogue/blob/master/5gcatalogue-app/src/main/resources/application.properties) file as follows:

```
kafka.bootstrap-servers=<kafka-ip-address>:9092
kafka.skip.send=false
```

### Used Libraries

| Lib | REPOSITORY |
| ------ | ------ |
| NfvManoLibsSol001Common | [README](https://github.com/nextworks-it/nfv-sol-libs) |
| NfvManoLibsSol001Descriptors | [README](https://github.com/nextworks-it/nfv-sol-libs) |
| NfvManoLibsCommon | [README](https://github.com/nextworks-it/nfv-ifa-libs) |
| NfvManoLibsDescriptors | [README](https://github.com/nextworks-it/nfv-ifa-libs) |
| NfvManoLibsCatalogueIf | [README](https://github.com/nextworks-it/nfv-ifa-libs) |
| J-OSMClient | [README](https://github.com/girtel/J-OSMClient) |
| OpenStack4j | [README](https://github.com/ContainX/openstack4j) |

### Configuration
For properly configuring the 5G Apps & Services Catalogue, the [application.properties](https://github.com/nextworks-it/5g-catalogue/blob/master/5gcatalogue-app/src/main/resources/application.properties) file has to be modified according to the environment where the catalogue is deployed. See instructions at [HOWTOCONFIGURE](https://github.com/nextworks-it/5g-catalogue/blob/master/HOWTOCONFIGURE.md)

### Installing

Run the "bootstrap" script for setting up the environment:

- Create the Catalogue log folder, "/var/log/5gcatalogue"
- Install PostgresSQL (if not present) and create the Catalogue DB, "cataloguedb"
- Clone and install "NfvManoLibsSol001Common" and "NfvManoLibsSol001Descriptors" libs
- Clone and install "NfvManoLibsCommon", "NfvManoLibsDescriptors" and NfvManoLibsCatalogueIf libs

```
$ ./bootstrap.sh env-dep
```
### Compiling

Compile the 5G Apps & Services Catalogue application:

```
$ ./bootstrap.sh compile-app
```

### Running

Run the 5G Apps & Services Catalogue application:

```
$ ./bootstrap.sh run-app
```

#### NOTE

For installing, compiling and executing in sequence:

```
$ ./bootstrap.sh all
```

## Versioning

For the versions available, see tags on this repository. 

## Authors

**Francesca Moscatelli**, **Giacomo Bernini**, **Giada Landi**, **Leonardo Agueci**, **Gino Carrozzo**   [Nextworks S.r.l.](http://www.nextworks.it)

## License

This project is licensed under the Apache 2.0 License - see the [LICENSE](LICENSE) file for details

