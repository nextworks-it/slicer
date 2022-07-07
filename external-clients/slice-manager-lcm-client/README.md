# slice-manager-lcm-client

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>io.swagger</groupId>
    <artifactId>slice-manager-lcm-client</artifactId>
    <version>1.0.1</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "io.swagger:slice-manager-lcm-client:1.0.1"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

* target/slice-manager-lcm-client-1.0.1.jar
* target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import io.swagger.client.slice_manager.v2.invoker.*;
import io.swagger.client.slice_manager.v2.invoker.auth.*;
import io.swagger.client.slice_manager.v2.model.*;
import io.swagger.client.slice_manager.v2.api.ApplicationInstanceApi;

import java.io.File;
import java.util.*;

public class ApplicationInstanceApiExample {

    public static void main(String[] args) {
        

        ApplicationInstanceApi apiInstance = new ApplicationInstanceApi();
        
        String networkServiceInstanceId = Arrays.asList("networkServiceInstanceId_example"); // String | 
        
        try {
            apiInstance.deleteNetworkServiceInstance(networkServiceInstanceId);
        } catch (ApiException e) {
            System.err.println("Exception when calling ApplicationInstanceApi#deleteNetworkServiceInstance");
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to */api/v1.0*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*ApplicationInstanceApi* | [**deleteNetworkServiceInstance**](docs/ApplicationInstanceApi.md#deleteNetworkServiceInstance) | **DELETE** /network_service_instance/{network_service_instance_id} | Delete a network service instance
*ApplicationInstanceApi* | [**getNetworkServiceInstance**](docs/ApplicationInstanceApi.md#getNetworkServiceInstance) | **GET** /network_service_instance/{network_service_instance_id} | Get individual network service instance information
*ApplicationInstanceApi* | [**getNetworkServiceInstances**](docs/ApplicationInstanceApi.md#getNetworkServiceInstances) | **GET** /network_service_instance | Get network service instances information
*ApplicationInstanceApi* | [**networkServiceInstanceReaction**](docs/ApplicationInstanceApi.md#networkServiceInstanceReaction) | **POST** /network_service_instance/{network_service_instance_id}/reaction | Perform reaction on individual network service instance
*ApplicationInstanceApi* | [**postNetworkServiceInstance**](docs/ApplicationInstanceApi.md#postNetworkServiceInstance) | **POST** /network_service_instance | Create a new network service instance
*ApplicationInstanceApi* | [**scaleNetworkServiceInstance**](docs/ApplicationInstanceApi.md#scaleNetworkServiceInstance) | **POST** /network_service_instance/{network_service_instance_id}/scale | Scale individual network service instance
*EdgeCloudComputeChunkApi* | [**deleteComputeChunk**](docs/EdgeCloudComputeChunkApi.md#deleteComputeChunk) | **DELETE** /compute_chunk/{compute_chunk_id} | Delete a Compute Chunk
*EdgeCloudComputeChunkApi* | [**getComputeChunk**](docs/EdgeCloudComputeChunkApi.md#getComputeChunk) | **GET** /compute_chunk/{compute_chunk_id} | Get individual Compute Chunk information
*EdgeCloudComputeChunkApi* | [**getComputeChunks**](docs/EdgeCloudComputeChunkApi.md#getComputeChunks) | **GET** /compute_chunk | Get Compute Chunks information
*EdgeCloudComputeChunkApi* | [**modifyCpuComputeChunk**](docs/EdgeCloudComputeChunkApi.md#modifyCpuComputeChunk) | **PUT** /compute_chunk/{compute_chunk_id}/cpus | Modify an OpenStack project CPU quota
*EdgeCloudComputeChunkApi* | [**modifyRamComputeChunk**](docs/EdgeCloudComputeChunkApi.md#modifyRamComputeChunk) | **PUT** /compute_chunk/{compute_chunk_id}/ram | Modify an OpenStack project RAM quota
*EdgeCloudComputeChunkApi* | [**modifyStorageComputeChunk**](docs/EdgeCloudComputeChunkApi.md#modifyStorageComputeChunk) | **PUT** /compute_chunk/{compute_chunk_id}/storage | Modify an OpenStack project storage quota
*EdgeCloudComputeChunkApi* | [**postComputeChunk**](docs/EdgeCloudComputeChunkApi.md#postComputeChunk) | **POST** /compute_chunk | Create a new Compute Chunk
*EdgeCloudComputeResourceApi* | [**deleteCompute**](docs/EdgeCloudComputeResourceApi.md#deleteCompute) | **DELETE** /compute/{compute_id} | Delete a compute
*EdgeCloudComputeResourceApi* | [**getCompute**](docs/EdgeCloudComputeResourceApi.md#getCompute) | **GET** /compute/{compute_id} | Get individual compute information
*EdgeCloudComputeResourceApi* | [**getComputes**](docs/EdgeCloudComputeResourceApi.md#getComputes) | **GET** /compute | Get computes information
*EdgeCloudComputeResourceApi* | [**postCompute**](docs/EdgeCloudComputeResourceApi.md#postCompute) | **POST** /compute | Register a new compute resource
*EdgeCloudNetworkChunkApi* | [**deleteNetworkChunk**](docs/EdgeCloudNetworkChunkApi.md#deleteNetworkChunk) | **DELETE** /network_chunk/{network_chunk_id} | Delete a Network Chunk
*EdgeCloudNetworkChunkApi* | [**getNetworkChunk**](docs/EdgeCloudNetworkChunkApi.md#getNetworkChunk) | **GET** /network_chunk/{network_chunk_id} | Get individual Network Chunk information
*EdgeCloudNetworkChunkApi* | [**getNetworkChunks**](docs/EdgeCloudNetworkChunkApi.md#getNetworkChunks) | **GET** /network_chunk | Get Network Chunks information
*EdgeCloudNetworkChunkApi* | [**postNetworkChunk**](docs/EdgeCloudNetworkChunkApi.md#postNetworkChunk) | **POST** /network_chunk | Create a new Network Chunk
*EdgeCloudNetworkResourceApi* | [**deletePhysicalNetwork**](docs/EdgeCloudNetworkResourceApi.md#deletePhysicalNetwork) | **DELETE** /physical_network/{physical_network_id} | Delete a edge/cloud network
*EdgeCloudNetworkResourceApi* | [**getPhysicalNetwork**](docs/EdgeCloudNetworkResourceApi.md#getPhysicalNetwork) | **GET** /physical_network/{physical_network_id} | Get individual edge/cloud network information
*EdgeCloudNetworkResourceApi* | [**getPhysicalNetworks**](docs/EdgeCloudNetworkResourceApi.md#getPhysicalNetworks) | **GET** /physical_network | Get edge/cloud networks information
*EdgeCloudNetworkResourceApi* | [**postPhysicalNetwork**](docs/EdgeCloudNetworkResourceApi.md#postPhysicalNetwork) | **POST** /physical_network | Register a new edge/cloud network resource
*GenericNetworkSliceTemplateGstApi* | [**deleteSlic3Temp**](docs/GenericNetworkSliceTemplateGstApi.md#deleteSlic3Temp) | **DELETE** /slic3_template/{slic3_temp_id} | Delete a generic slice template
*GenericNetworkSliceTemplateGstApi* | [**getSlic3Temp**](docs/GenericNetworkSliceTemplateGstApi.md#getSlic3Temp) | **GET** /slic3_template/{slic3_temp_id} | Get individual generic slice template information
*GenericNetworkSliceTemplateGstApi* | [**getSlic3Temps**](docs/GenericNetworkSliceTemplateGstApi.md#getSlic3Temps) | **GET** /slic3_template | Get generic slice templates information
*GenericNetworkSliceTemplateGstApi* | [**postSlic3Temp**](docs/GenericNetworkSliceTemplateGstApi.md#postSlic3Temp) | **POST** /slic3_template | Create a new generic slice template
*NetworkServiceApi* | [**deleteNetworkService**](docs/NetworkServiceApi.md#deleteNetworkService) | **DELETE** /network_service/{network_service_id} | Delete a network service
*NetworkServiceApi* | [**getNetworkService**](docs/NetworkServiceApi.md#getNetworkService) | **GET** /network_service/{network_service_id} | Network service information retrieval
*NetworkServiceApi* | [**getNetworkServices**](docs/NetworkServiceApi.md#getNetworkServices) | **GET** /network_service | Gets network_services information
*NetworkServiceApi* | [**postNetworkService**](docs/NetworkServiceApi.md#postNetworkService) | **POST** /network_service | Register a new network service resource
*NetworkSliceCollectionOfChunksApi* | [**addChunkSlic3**](docs/NetworkSliceCollectionOfChunksApi.md#addChunkSlic3) | **PUT** /slic3/{slic3_id}/add_chunks | Add chunks to individual Slic3
*NetworkSliceCollectionOfChunksApi* | [**delChunkSlic3**](docs/NetworkSliceCollectionOfChunksApi.md#delChunkSlic3) | **PUT** /slic3/{slic3_id}/del_chunks | Remove chunks from individual Slic3
*NetworkSliceCollectionOfChunksApi* | [**deleteSlic3**](docs/NetworkSliceCollectionOfChunksApi.md#deleteSlic3) | **DELETE** /slic3/{slic3_id} | Delete a slice
*NetworkSliceCollectionOfChunksApi* | [**getSlic3**](docs/NetworkSliceCollectionOfChunksApi.md#getSlic3) | **GET** /slic3/{slic3_id} | Get individual slice information
*NetworkSliceCollectionOfChunksApi* | [**getSlic3s**](docs/NetworkSliceCollectionOfChunksApi.md#getSlic3s) | **GET** /slic3 | Get slice(s) information
*NetworkSliceCollectionOfChunksApi* | [**postSlic3**](docs/NetworkSliceCollectionOfChunksApi.md#postSlic3) | **POST** /slic3 | Create a new slice
*NetworkSliceInstanceNestBasedApi* | [**deleteSlic3Instance**](docs/NetworkSliceInstanceNestBasedApi.md#deleteSlic3Instance) | **DELETE** /slic3_instance/{slic3_instance_id} | Delete a slice instance
*NetworkSliceInstanceNestBasedApi* | [**getSlic3Instance**](docs/NetworkSliceInstanceNestBasedApi.md#getSlic3Instance) | **GET** /slic3_instance/{slic3_instance_id} | Get individual slice instance information
*NetworkSliceInstanceNestBasedApi* | [**getSlic3Instances**](docs/NetworkSliceInstanceNestBasedApi.md#getSlic3Instances) | **GET** /slic3_instance | Get slice instance(s) information
*NetworkSliceInstanceNestBasedApi* | [**postSlic3Instance**](docs/NetworkSliceInstanceNestBasedApi.md#postSlic3Instance) | **POST** /slic3_instance | Create a new slice instance
*NetworkSliceTypeNestApi* | [**deleteSlic3Type**](docs/NetworkSliceTypeNestApi.md#deleteSlic3Type) | **DELETE** /slic3_type/{slic3_type_id} | Delete a network slice type
*NetworkSliceTypeNestApi* | [**getSlic3Type**](docs/NetworkSliceTypeNestApi.md#getSlic3Type) | **GET** /slic3_type/{slic3_type_id} | Get individual network slice type information
*NetworkSliceTypeNestApi* | [**getSlic3TypeChunks**](docs/NetworkSliceTypeNestApi.md#getSlic3TypeChunks) | **GET** /slic3_type/{slic3_type_id}/slice_blueprint | Get individual network slice type blueprint information
*NetworkSliceTypeNestApi* | [**getSlic3Types**](docs/NetworkSliceTypeNestApi.md#getSlic3Types) | **GET** /slic3_type | Get network slice types information
*NetworkSliceTypeNestApi* | [**postSlic3Type**](docs/NetworkSliceTypeNestApi.md#postSlic3Type) | **POST** /slic3_type | Create a new network slice type
*RadioAccessNetworkChunkApi* | [**deleteRadioChunk**](docs/RadioAccessNetworkChunkApi.md#deleteRadioChunk) | **DELETE** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id} | Delete a radio chunk
*RadioAccessNetworkChunkApi* | [**getRadioChunk**](docs/RadioAccessNetworkChunkApi.md#getRadioChunk) | **GET** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id} | Get individual radio chunk information
*RadioAccessNetworkChunkApi* | [**getRadioChunks**](docs/RadioAccessNetworkChunkApi.md#getRadioChunks) | **GET** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk | Get radio chunks information
*RadioAccessNetworkChunkApi* | [**getadioChunkTopology**](docs/RadioAccessNetworkChunkApi.md#getadioChunkTopology) | **GET** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}/chunk_topology | Get individual radio chunk topology information
*RadioAccessNetworkChunkApi* | [**postRadioChunk**](docs/RadioAccessNetworkChunkApi.md#postRadioChunk) | **POST** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk | Create a new radio chunk
*RadioAccessNetworkChunkApi* | [**putRadioChunk**](docs/RadioAccessNetworkChunkApi.md#putRadioChunk) | **PUT** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id} | Edit individual radio chunk
*RadioAccessNetworkChunkApi* | [**validateRadioChunk**](docs/RadioAccessNetworkChunkApi.md#validateRadioChunk) | **POST** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/validate | Validate radio chunk topology
*RadioAccessNetworkResourceApi* | [**deleteCell**](docs/RadioAccessNetworkResourceApi.md#deleteCell) | **DELETE** /ran_infrastructure/{ran_infrastructure_id}/ran_topology/cell/{cell_id} | Delete a cell
*RadioAccessNetworkResourceApi* | [**deleteCellRelationship**](docs/RadioAccessNetworkResourceApi.md#deleteCellRelationship) | **DELETE** /ran_infrastructure/{ran_infrastructure_id}/ran_topology/cell/{cell_id}/nsa_relationship/{nsa_cell_id} | Delete a cell relationship
*RadioAccessNetworkResourceApi* | [**deleteRANInfrastructure**](docs/RadioAccessNetworkResourceApi.md#deleteRANInfrastructure) | **DELETE** /ran_infrastructure/{ran_infrastructure_id} | Delete a RAN infrastructure
*RadioAccessNetworkResourceApi* | [**getConfiguredRadioPhys**](docs/RadioAccessNetworkResourceApi.md#getConfiguredRadioPhys) | **GET** /ran_infrastructure/{ran_infrastructure_id}/configured_ran_topology | Get RAN infrastructures configured radio interfaces
*RadioAccessNetworkResourceApi* | [**getRANInfrastructure**](docs/RadioAccessNetworkResourceApi.md#getRANInfrastructure) | **GET** /ran_infrastructure/{ran_infrastructure_id} | Get individual RAN infrastructure information
*RadioAccessNetworkResourceApi* | [**getRANInfrastructures**](docs/RadioAccessNetworkResourceApi.md#getRANInfrastructures) | **GET** /ran_infrastructure | Get RAN infrastructures information
*RadioAccessNetworkResourceApi* | [**getRANTopology**](docs/RadioAccessNetworkResourceApi.md#getRANTopology) | **GET** /ran_infrastructure/{ran_infrastructure_id}/ran_topology | Get individual RAN infrastructure topology information
*RadioAccessNetworkResourceApi* | [**getRANboxes**](docs/RadioAccessNetworkResourceApi.md#getRANboxes) | **GET** /ran_infrastructure/{ran_infrastructure_id}/box | Get RAN boxes information
*RadioAccessNetworkResourceApi* | [**getRFports**](docs/RadioAccessNetworkResourceApi.md#getRFports) | **GET** /ran_infrastructure/{ran_infrastructure_id}/box/{box_id}/rf_port | Get RF ports information
*RadioAccessNetworkResourceApi* | [**postBoxCell**](docs/RadioAccessNetworkResourceApi.md#postBoxCell) | **POST** /ran_infrastructure/{ran_infrastructure_id}/ran_topology/{box_id}/cell | Creates a new cell
*RadioAccessNetworkResourceApi* | [**postRANInfrastructure**](docs/RadioAccessNetworkResourceApi.md#postRANInfrastructure) | **POST** /ran_infrastructure | Register a new RAN infrastructure resource
*RadioAccessNetworkResourceApi* | [**putBoxCellConfig**](docs/RadioAccessNetworkResourceApi.md#putBoxCellConfig) | **PUT** /ran_infrastructure/{ran_infrastructure_id}/ran_topology/cell/{cell_id} | Set cell config
*RadioAccessNetworkResourceApi* | [**putBoxCellRelationship**](docs/RadioAccessNetworkResourceApi.md#putBoxCellRelationship) | **PUT** /ran_infrastructure/{ran_infrastructure_id}/ran_topology/cell/{cell_id}/nsa_relationship/{nsa_cell_id} | Set cell relationship
*RadioAccessNetworkResourceApi* | [**putBoxConfig**](docs/RadioAccessNetworkResourceApi.md#putBoxConfig) | **PUT** /ran_infrastructure/{ran_infrastructure_id}/box/{box_id} | Set cellular box config
*RadioAccessNetworkResourceApi* | [**putInterfaceType**](docs/RadioAccessNetworkResourceApi.md#putInterfaceType) | **PUT** /ran_infrastructure/{ran_infrastructure_id}/ran_topology/{interface_id}/type | Set physical interface type
*RadioAccessNetworkResourceApi* | [**putLTEConfiguration**](docs/RadioAccessNetworkResourceApi.md#putLTEConfiguration) | **PUT** /ran_infrastructure/{ran_infrastructure_id}/ran_topology/{interface_id}/lte_config | Set physical interface LTE configuration
*RadioAccessNetworkResourceApi* | [**putRFPortConfig**](docs/RadioAccessNetworkResourceApi.md#putRFPortConfig) | **PUT** /ran_infrastructure/{ran_infrastructure_id}/box/{box_id}/rf_port/{rf_port_id} | Set RF port config
*RadioAccessNetworkResourceApi* | [**putWirelessConfiguration**](docs/RadioAccessNetworkResourceApi.md#putWirelessConfiguration) | **PUT** /ran_infrastructure/{ran_infrastructure_id}/ran_topology/{interface_id}/wifi_config | Set physical interface WiFi configuration
*RadioServiceApi* | [**deleteRadioService**](docs/RadioServiceApi.md#deleteRadioService) | **DELETE** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}/radio_service/{radio_service_id} | Delete a radio service
*RadioServiceApi* | [**getRadioService**](docs/RadioServiceApi.md#getRadioService) | **GET** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}/radio_service/{radio_service_id} | Get individual radio service information
*RadioServiceApi* | [**getRadioServices**](docs/RadioServiceApi.md#getRadioServices) | **GET** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}/radio_service | Get radio services information
*RadioServiceApi* | [**postRadioService**](docs/RadioServiceApi.md#postRadioService) | **POST** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}/radio_service | Create a new radio service
*RadioServiceApi* | [**putRadioService**](docs/RadioServiceApi.md#putRadioService) | **PUT** /ran_infrastructure/{ran_infrastructure_id}/radio_chunk/{radio_chunk_id}/radio_service/{radio_service_id} | Edit individual radio service interfaces
*ResourceTelemetryApi* | [**deleteTelemetryServer**](docs/ResourceTelemetryApi.md#deleteTelemetryServer) | **DELETE** /telemetry/{telemetry_server_id} | Delete a telemetry server
*ResourceTelemetryApi* | [**deleteTelemetryTarget**](docs/ResourceTelemetryApi.md#deleteTelemetryTarget) | **DELETE** /telemetry/{telemetry_server_id}/target/{target_uid} | Delete a telemetry target
*ResourceTelemetryApi* | [**getTelemetryServer**](docs/ResourceTelemetryApi.md#getTelemetryServer) | **GET** /telemetry/{telemetry_server_id} | Get individual telemetry server information
*ResourceTelemetryApi* | [**getTelemetryServers**](docs/ResourceTelemetryApi.md#getTelemetryServers) | **GET** /telemetry | Get telemetry servers information
*ResourceTelemetryApi* | [**getTelemetryTarget**](docs/ResourceTelemetryApi.md#getTelemetryTarget) | **GET** /telemetry/{telemetry_server_id}/target/{target_uid} | Get individual telemetry target information
*ResourceTelemetryApi* | [**getTelemetryTargets**](docs/ResourceTelemetryApi.md#getTelemetryTargets) | **GET** /telemetry/{telemetry_server_id}/target | Get telemetry targets information
*ResourceTelemetryApi* | [**postTelemetryServer**](docs/ResourceTelemetryApi.md#postTelemetryServer) | **POST** /telemetry | Register a new telemetry server resource
*ResourceTelemetryApi* | [**postTelemetryTarget**](docs/ResourceTelemetryApi.md#postTelemetryTarget) | **POST** /telemetry/{telemetry_server_id}/target | Register a new telemetry target
*ResourceTelemetryApi* | [**putTelemetryTarget**](docs/ResourceTelemetryApi.md#putTelemetryTarget) | **PUT** /telemetry/{telemetry_server_id}/target/{target_uid} | Modify telemetry target information
*UserApi* | [**deleteUser**](docs/UserApi.md#deleteUser) | **DELETE** /user/{user_id} | Delete an user
*UserApi* | [**getUser**](docs/UserApi.md#getUser) | **GET** /user/{user_id} | Get individual user information
*UserApi* | [**getUsers**](docs/UserApi.md#getUsers) | **GET** /user | Get users information
*UserApi* | [**postUser**](docs/UserApi.md#postUser) | **POST** /user | Register a new user


## Documentation for Models

 - [BoxCellInput](docs/BoxCellInput.md)
 - [ComputeChunkInput](docs/ComputeChunkInput.md)
 - [ComputeChunkNewCPUInput](docs/ComputeChunkNewCPUInput.md)
 - [ComputeChunkNewRAMInput](docs/ComputeChunkNewRAMInput.md)
 - [ComputeChunkNewStorageInput](docs/ComputeChunkNewStorageInput.md)
 - [ComputeInput](docs/ComputeInput.md)
 - [ErrorMessage](docs/ErrorMessage.md)
 - [GetRANInfrastructureTopo](docs/GetRANInfrastructureTopo.md)
 - [GetRadioChunkTopo](docs/GetRadioChunkTopo.md)
 - [GetUser](docs/GetUser.md)
 - [NetworkChunkInput](docs/NetworkChunkInput.md)
 - [NetworkServiceInput](docs/NetworkServiceInput.md)
 - [NetworkServiceInstanceInput](docs/NetworkServiceInstanceInput.md)
 - [NetworkServiceInstanceReactionInput](docs/NetworkServiceInstanceReactionInput.md)
 - [NetworkServiceInstanceScaleInput](docs/NetworkServiceInstanceScaleInput.md)
 - [PhysicalNetworkInput](docs/PhysicalNetworkInput.md)
 - [PostBoxCell](docs/PostBoxCell.md)
 - [PostCompute](docs/PostCompute.md)
 - [PostComputeChunk](docs/PostComputeChunk.md)
 - [PostNetworkChunk](docs/PostNetworkChunk.md)
 - [PostNetworkService](docs/PostNetworkService.md)
 - [PostNetworkServiceInstance](docs/PostNetworkServiceInstance.md)
 - [PostPhysicalNetwork](docs/PostPhysicalNetwork.md)
 - [PostRANInfrastructure](docs/PostRANInfrastructure.md)
 - [PostRadioChunk](docs/PostRadioChunk.md)
 - [PostRadioService](docs/PostRadioService.md)
 - [PostSlic3](docs/PostSlic3.md)
 - [PostSlic3Chunks](docs/PostSlic3Chunks.md)
 - [PostSlic3Instance](docs/PostSlic3Instance.md)
 - [PostSlic3Temp](docs/PostSlic3Temp.md)
 - [PostSlic3Type](docs/PostSlic3Type.md)
 - [PostSlic3TypeChunks](docs/PostSlic3TypeChunks.md)
 - [PostTelemetryServer](docs/PostTelemetryServer.md)
 - [PostTelemetryTarget](docs/PostTelemetryTarget.md)
 - [PostUser](docs/PostUser.md)
 - [PutBoxCellConfigInput](docs/PutBoxCellConfigInput.md)
 - [PutBoxConfigInput](docs/PutBoxConfigInput.md)
 - [PutLTEConfigurationInput](docs/PutLTEConfigurationInput.md)
 - [PutRFPortConfigInput](docs/PutRFPortConfigInput.md)
 - [PutRadioChunk](docs/PutRadioChunk.md)
 - [PutTelemetryTargetInput](docs/PutTelemetryTargetInput.md)
 - [PutWifiConfigurationInput](docs/PutWifiConfigurationInput.md)
 - [RANInfrastructureInput](docs/RANInfrastructureInput.md)
 - [RadioChunkInput](docs/RadioChunkInput.md)
 - [RadioServiceInput](docs/RadioServiceInput.md)
 - [Slic3Input](docs/Slic3Input.md)
 - [Slic3InstanceInput](docs/Slic3InstanceInput.md)
 - [Slic3Put](docs/Slic3Put.md)
 - [Slic3TempInput](docs/Slic3TempInput.md)
 - [Slic3TypeInput](docs/Slic3TypeInput.md)
 - [TelemetryServerInput](docs/TelemetryServerInput.md)
 - [TelemetryTargetInput](docs/TelemetryTargetInput.md)
 - [UserInput](docs/UserInput.md)


## Documentation for Authorization

All endpoints do not require authorization.
Authentication schemes defined for the API:


## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author



