
#  End-to-end network slice orchestration framework  
## Introduction
The **end-to-end network slice orchestration framework** has been designed, developed, tested and documented within the scope of EU-funded **[Ingenious project](https://ingenious-iot.eu/web/)**.  
![](https://media.licdn.com/dms/image/C4D16AQFBhVIuyoJfbA/profile-displaybackgroundimage-shrink_200_800/0/1646398512720?e=2147483647&v=beta&t=ghh06H2HtZ9DgedziM7bDf3wLZQFx3WlB9v_F-Gws9k "")
 INGENIOUS aims to design and evaluate the Next-Generation IoT (NG-IoT) solution, with emphasis on 5G and the development of Edge and Cloud computing extensions for IoT, as well as providing smart networking and data management solutions with Artificial Intelligence and Machine Learning (AI/ML). The project embraces the 5G Infrastructure Association (5G IA) and Alliance for Internet of Things Innovation (AIOTI) vision for empowering smart manufacturing and smart mobility verticals.
 

The **end-to-end network slice orchestration framework** briefly consists of software stack for the management of end-to-end network slice instances in 3GPP and non-3GPP network. 
It is composed of the Network Slice Management Function (NSMF) and different Network Subslice Management Functions (NSSMFs). The former for the management of Network Slice Template (NST), the lifecycle of end-to-end network slice instances, while the former for the lifecycle management of each network sub-slice, belonging to the  RAN (O-RAN, Flexible RAN NSSMF and Emulated RAN) and a commercial 5GCore.

Briefly, starting from the root of the repository is available: 
- early Software Prototype of the **NSMF**, available in the network-slice-management-function directory. 
- early Software Prototype of the mentioned **NSMFs**, available in the network-sub-slice-management-function. 

# HW and SW requirements
### Hardware requirements
The  minimum hardware for running the whole end-to-end network slice orchestration framework are the following: 
- 8 vCPU
- 8GB RAM
- 50GB disk space

### Software requirements
The aforementioned software components needs the following software requirements:
- Java 8 version
- maven 3.6 version 
- postgres database (not needed in case of Docker deployment)
- (Optional) Docker version 20.10.12, build e91ed57
- (Optional) docker-compose version 1.29.2, build 5becea4c
- (Optional) Commercial 5GC installation in case the slicing operations are performed on top of this 5GC

# Configuration
The configuration of the  **end-to-end network slice orchestration framework** depends on the kind of deployment. 

In case the  **end-to-end network slice orchestration framework** runs using the built jar file, then is needed to configure the corresponding application.properties file. In the current version of the application.properties file of the NSMF, it is configured for interacting with the Flexible RAN NSSMF and commercial 5GCORE NSSMF. However, it is possibile to interact with either different or more segments adding or removing entries from the application.properties, respectively.

In case  **end-to-end network slice orchestration framework** should run on Docker containers, then the docker-compose.yaml located at https://github.com/nextworks-it/slicer/tree/ingenious/network-slice-management-function should be updated. For each NSMF and NSSMF a specific docker container is available. 

# Installation 

##### Library installation

As first step, is necessary to install the libraries for the NSMF and NSSMFs. The libraries are available within the *lib* directory and must be built in the following order:

1. nfv-ifa-libs
2. nfv-sol-libs
3. vs-common-libs
4. 5g-catalogue
5. nssmf-record

Is possible to build the library either using maven or (in some cases) the installation script available within the library. In the former case, the command is the following:
`mvn clean install -DskipTests=true -Dcheckstyle.skip -Dmaven.javadoc.skip=true`

##### NSSMF dependency installation
After to have installed the libraries, in order to install the NSSMF the following dependencies must be installed:
1. nssmf-service
2. osm-nssmf-handler

These dependencies are available in the `slicer/network-sub-slice-management-function/` directory.

##### NSMF and NSSMF installation
Finally, at the root of each NSMF and NSSMF maven project execute the following command: 
`mvn clean install -DskipTests=true -Dcheckstyle.skip -Dmaven.javadoc.skip=true`

The root of the NSMF maven project is located at `slicer/network-slice-management-function/`
The same approach is used for installing the needed NSSMFs.

### (Optional) Docker image installation
In case the docker deployment is chosen, then is possible to install the docker images of the  **end-to-end network slice orchestration framework** using the following command:
`sudo docker build . -t <image_name>:<image_version>`

For example:
`sudo docker build . -t nsmf:0.0.1-ingenious`

where `image_name` and `image_version` are mentioned within the docker-compose.yaml file. 

### Installation verification
At this point, either the jar files or the Docker images are available for running the e2e network slice orchestration framework. In the former cases, the jar files should be available in the corresponding target directories, while in the latter case the Docker images can be listed with `sudo docker image ls` command. Indeed, the output is similar to below:

```
REPOSITORY                             TAG       IMAGE ID       CREATED       SIZE
nsmf:0.0.1-ingenious                   0.0.1     ffffff6a1b65   3 days ago    636MB
nssmf-core:0.0.1-ingenious             0.0.1     ffffff46615b    6 days ago   628MB
phy-mac-ran-nssmf:0.0.1-ingenious      0.0.1      ffffabcdef12   3 days ago   628MB
```

In this particular, case the **end-to-end network slice orchestration framework** is composed by the NSMF + the Core and Flexible RAN NSSMFs.

# Usage
In the following section is described how to interact with the e2e network slice orchestration framework.

In case of the installation with jar files, to run the e2e network slice orchestration framework for each component is needed to execute:
 `java -jar <jar_file_name>`

where `jar_file_name` is the filename as output of compiled Java source code.

In case the Docker deployment has been chosen, then with the below command: 
 ` sudo docker-compose up -d`
 
is possibile to make up and running the wholee2e network slice orchestration framework.

At this point the slice orchestrator should be up and running.
A Postman collection is included in this repository for interacting with the e2e network slice orchestration framework. 

# Authors
Giacomo Bernini, Pietro Piscione [Nextworks S.r.l.](http://www.nextworks.it)

# License
This project is licensed under the Apache 2.0 License - see the [LICENSE](https://www.apache.org/licenses/LICENSE-2.0) file for details.


