# Installation guide

### 0.Cloning
The Service-Slice Orchestrator (SS-O) can be made up and running cloning the necessary dependencies. They can be cloned from:

- 5g-catalogue at branch master (https://github.com/nextworks-it/5g-catalogue)
- blueprint-catalogue at branch slicene (https://github.com/nextworks-it/slicer-catalogue)
- identity-management at branch master (https://github.com/nextworks-it/slicer-identity-mgmt)
- nfv-ifa-libs at branch slicenet (https://github.com/nextworks-it/nfv-ifa-libs)
- nfv-sol-libs at branch master (https://github.com/nextworks-it/nfv-sol-libs)
- nfvo-driver at branch slicenet (https://github.com/nextworks-it/nfvo-drivers)
- nmro-driver at branch master (https://gitlab.com/slicenet/nmro-driver)
- qoe-rest-client at branch master (https://gitlab.com/slicenet/qoe-rest-client)
- this repository at branch slicenet

### 1.Setup dependencies
Once downloaded all the dependencies at the correct branches, is needed to move the build_sso.sh script available into this repository, at the above directory.

### 2.Build the application
The installation script, once executed, builds first the dependencies and eventually the SS-O.
The built files are two jars: one is located under the NSMF_APP and the other one under the VSMF_APP. The former is the NSP, while the  latter the DSP. To exececute them is simply needed 'java -jar <name_file>. The DSP will listen on 8081 port, while the NSP on 8082 port. 

### 3.Credentials
There is one pre-generated bootstrap user with credentials admin/admin for DSP and NSP. 
Using this user, you can create more (basic-level) users.
