# NFV-MANO SOL LIBS

This repository contains ETSI GS NFV SOL compliant libraries.

Available libraries:

- [ETSI GS NFV SOL001 v2.5.1](https://www.etsi.org/deliver/etsi_gs/NFV-SOL/001_099/001/02.05.01_60/gs_NFV-SOL001v020501p.pdf) for representing TOSCA Network Service Descriptors (NSDs) and Virtual Network Function Descriptors (VNFDs)

## Getting Started

### Prerequisites

* [Oracle-Java8] - Oracle version 8 is preferred, otherwise [OpenJDK-8] + [OpenJFX]
* [Maven] - version >= 3.3.9 is required

### Installing

Run the "install_nfv_sol_libs" script for installing all available libraries:

```
$ ./install_nfv_sol_libs.sh
```

If you want to install a specific library run:

```
$ ./install_nfv_sol_libs.sh [-d FOLDER_NAME]
```

## Versioning

For the versions available, see tags on this repository. 

## Authors

* **Francesca Moscatelli**, **Giada Landi** - [Nextworks S.r.l.](http://www.nextworks.it)

## License

This project is licensed under the Apache 2.0 License - see the [LICENSE](LICENSE) file for details


