
# 5G Apps & Services Catalogue

## Instruction for configuring the 5G Apps & Services Catalogue application

For properly configuring the 5G Apps & Services Catalogue, the [application.properties](https://github.com/nextworks-it/5g-catalogue/blob/master/5gcatalogue-app/src/main/resources/application.properties)
has to be modified.

### POSTGRES
This is the name of the db created and cofigured when running the ./bootstrap.sh env-dep command
```
spring.datasource.url=jdbc:postgresql://localhost:5432/cataloguedb
```
Username configured for cataloguedb
```	
spring.datasource.username=postgres
```
Password configured for cataloguedb
```
spring.datasource.password=postgres
```
If you want to not drop db data when the application is restarted, this has to be put in "update" mode
```
spring.jpa.hibernate.ddl-auto=create-drop
```

### BINDING TOMCAT ON MANAGEMENT IP ADDRESS
Server is running by default on 0.0.0.0, port can be changed
```
server.port=8083
```

### KAFKA
Kafka broker address
```
kafka.bootstrap-servers=localhost:9092
```
If, for testing purpose, you need to disable the send of messages in the bus, put to "true"
```
kafka.skip.send=false
```
Kafka topics to be configured in the bus
```
kafkatopic.local=${catalogue.scope}catalogue-onboarding-local
kafkatopic.remote=${catalogue.scope}catalogue-onboarding-remote
```

### API DOC
Api doc directory
```
springfox.documentation.swagger.v2.path=/api-docs
```

### STORAGE
Directory where the descriptors and packages are stored, **to be changed** depending on the ENV
```
catalogue.storageRootDir=/home/nextworks/catalogueStorage
```

### MANO PLUGINS CONFIGURATION
If, for testing purpose, you need to disable MANO plugins, put to "true"
```
catalogue.dummyMANO=false
```
Directory where OSMR3 translated descriptors and packages are stored, **to be changed** depending on the ENV
```
catalogue.osmr3.localDir=/home/nextworks/osmr3
```
Directory where OSMR4 and OSMR5 translated descriptors and packages are stored, **to be changed** depending on the ENV
```
catalogue.osmr4.localDir=/home/nextworks/osmr4
```
Directory where you have to put the configuration JSON files for running MANO Plugins, an example is given [here](https://github.com/nextworks-it/5g-catalogue/tree/master/5gcatalogue-app/src/main/resources/manoConfigurations)
```
catalogue.manoPluginsConfigurations=/manoConfigurations
```
If, for testing purpose, you need to skip MANO Plugins configuration put to "false"
``` 
catalogue.mano.localPluginsConfig=true
```
Directory where you can put the logo to be associated with the translated descriptors, **to be changed** depending on the ENV
```
catalogue.logo=/home/nextworks/images/nxw_logo.png
```

### VIM PLUGINS CONFIGURATION
VIM Plugin still under development, "OS" type will be supported
```
catalogue.defaultVIMType=DUMMY
catalogue.vimPluginsConfiguration=/vimConfigurations
catalogue.skipVIMPluginsConfig=true
```

### 5G CATALOGUE
Catalogue's scope can be "PUBLIC" or "PRIVATE", if "PRIVATE" a configuration file with PUBLIC Catalogue information has to be put in the following directory
```
catalogue.scope=PUBLIC
```
[configuration example](https://github.com/nextworks-it/5g-catalogue/tree/master/5gcatalogue-app/src/main/resources/catalogueConfigurations)
```
catalogue.catalogueConfiguration=/catalogueConfigurations
```

### KEYCLOAK
Keycloak information as configured in the identity server
```
keycloak.realm=5gcity
keycloak.auth-server-url=http://10.20.8.21:8080/auth/
keycloak.resource=5gcatalogue-be
keycloak.public-client=false
keycloak.principal-attribute=preferred_username
keycloak.credentials.secret=444c517a-d474-4cc8-928f-606414856e1f
keycloak.use-resource-role-mappings=true
```
