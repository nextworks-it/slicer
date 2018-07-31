#!/bin/bash

curl -v -X POST -d @createSpr1Vnfd.json http://localhost:8081/nfvo/vnfdManagement/vnfPackage --header "Content-Type:application/json"

curl -v -X POST -d @createSpr21Vnfd.json http://localhost:8081/nfvo/vnfdManagement/vnfPackage --header "Content-Type:application/json"

curl -v -X POST -d @createSpr22Vnfd.json http://localhost:8081/nfvo/vnfdManagement/vnfPackage --header "Content-Type:application/json"

curl -v -X POST -d @createWebServerVnfd.json http://localhost:8081/nfvo/vnfdManagement/vnfPackage --header "Content-Type:application/json"

curl -v -X POST -d @createCdnNsd.json http://localhost:8081/nfvo/nsdManagement/nsd --header "Content-Type:application/json"
