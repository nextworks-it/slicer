# Download base image Alpine JDK 8
FROM openjdk:8-alpine

# Define variables
ARG catalogue_server_port=8083
ARG nfv_sol_libs_version=master
ARG nfv_sol_libs_repo=https://github.com/nextworks-it/nfv-sol-libs.git
ARG nfv_ifa_libs_version=master
ARG nfv_ifa_libs_repo=https://github.com/nextworks-it/nfv-ifa-libs.git
ARG mano_id=DEFAULT_MANO
ARG mano_type=DUMMY
ARG mano_site=DEFAULT_SITE
ARG mano_ip=127.0.0.1
ARG mano_port=9999
ARG mano_username=admin
ARG mano_password=admin
ARG mano_project=admin
ARG public_catalogue_id=DEFAULT_CAT
ARG public_catalogue_url=127.0.0.1
ARG proxy_enabled=false

# Update Ubuntu software repository
RUN apk update

# Install git
RUN apk add git

# Install Maven 3.3.9
RUN wget http://apache-mirror.rbc.ru/pub/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz \
    && tar -xvzpf apache-maven-3.3.9-bin.tar.gz \
    && mkdir -p /opt/maven/3.3.9 \
    && mv apache-maven-3.3.9/* /opt/maven/3.3.9/ \
    && rm -rf apache-maven-3.3.9 \
    && rm apache-maven-3.3.9-bin.tar.gz \
    && ln -s /opt/maven/3.3.9/ /opt/maven/current
ENV MAVEN_HOME=/opt/maven/current 
ENV PATH=$PATH:$MAVEN_HOME/bin

RUN mkdir /home/5g-catalogue
COPY . /home/5g-catalogue/

# Proxy configuration for Maven
RUN cp /home/5g-catalogue/deployments/docker/5g-catalogue-app/templates/settings.xml /opt/maven/current/conf/
RUN if [ "$proxy_enabled" = "false" ] ; then rm /opt/maven/current/conf/settings.xml; fi

# Install nfvo-sol-libs
WORKDIR /home
RUN git clone ${nfv_sol_libs_repo} nfv-sol-libs
WORKDIR /home/nfv-sol-libs
RUN git checkout ${nfv_sol_libs_version}
WORKDIR /home/nfv-sol-libs/NFV_MANO_SOL001_LIBS_COMMON
RUN mvn clean install
WORKDIR /home/nfv-sol-libs/NFV_MANO_SOL001_LIBS_DESCRIPTORS
RUN mvn clean install
WORKDIR /home/nfv-sol-libs/NFV_MANO_SOL006_LIBS_COMMON
RUN mvn clean install
WORKDIR /home/nfv-sol-libs/NFV_MANO_SOL006_LIBS_DESCRIPTORS
RUN mvn clean install

# Install nfvo-ifa-libs
WORKDIR /home
RUN git clone ${nfv_ifa_libs_repo} nfv-ifa-libs
WORKDIR /home/nfv-ifa-libs
RUN git checkout ${nfv_ifa_libs_version}
WORKDIR /home/nfv-ifa-libs/NFV_MANO_LIBS_COMMON
RUN mvn clean install
WORKDIR /home/nfv-ifa-libs/NFV_MANO_LIBS_DESCRIPTORS
RUN mvn clean install
WORKDIR /home/nfv-ifa-libs/NFV_MANO_LIBS_CATALOGUES_IF
RUN mvn clean install

# Install 5G Apps and Services Catalogue App
RUN rm /home/5g-catalogue/5gcatalogue-app/src/main/resources/manoConfigurations/*.json
RUN cp /home/5g-catalogue/deployments/docker/5g-catalogue-app/templates/mano.json /home/5g-catalogue/5gcatalogue-app/src/main/resources/manoConfigurations/
RUN sed -i "s|_MANO_ID_|${mano_id}|g" /home/5g-catalogue/5gcatalogue-app/src/main/resources/manoConfigurations/mano.json \
    && sed -i "s|_MANO_TYPE_|${mano_type}|g" /home/5g-catalogue/5gcatalogue-app/src/main/resources/manoConfigurations/mano.json \
    && sed -i "s|_MANO_SITE_|${mano_site}|g" /home/5g-catalogue/5gcatalogue-app/src/main/resources/manoConfigurations/mano.json \
    && sed -i "s|_MANO_IP_|${mano_ip}|g" /home/5g-catalogue/5gcatalogue-app/src/main/resources/manoConfigurations/mano.json \
    && sed -i "s|_MANO_PORT_|${mano_port}|g" /home/5g-catalogue/5gcatalogue-app/src/main/resources/manoConfigurations/mano.json \
    && sed -i "s|_MANO_USERNAME_|${mano_username}|g" /home/5g-catalogue/5gcatalogue-app/src/main/resources/manoConfigurations/mano.json \
    && sed -i "s|_MANO_PASSWORD_|${mano_password}|g" /home/5g-catalogue/5gcatalogue-app/src/main/resources/manoConfigurations/mano.json \
    && sed -i "s|_MANO_PROJECT_|${mano_project}|g" /home/5g-catalogue/5gcatalogue-app/src/main/resources/manoConfigurations/mano.json
RUN rm /home/5g-catalogue/5gcatalogue-app/src/main/resources/catalogueConfigurations/*.json
RUN cp /home/5g-catalogue/deployments/docker/5g-catalogue-app/templates/publicCatalogue.json /home/5g-catalogue/5gcatalogue-app/src/main/resources/catalogueConfigurations/
RUN sed -i "s|_PUBLIC_CATALOGUE_ID_|${public_catalogue_id}|g" /home/5g-catalogue/5gcatalogue-app/src/main/resources/catalogueConfigurations/publicCatalogue.json \
    && sed -i "s|_PUBLIC_CATALOGUE_URL_|${public_catalogue_url}|g" /home/5g-catalogue/5gcatalogue-app/src/main/resources/catalogueConfigurations/publicCatalogue.json
RUN cp -r /home/5g-catalogue/deployments/docker/5g-catalogue-app/logos /home/logos
RUN cp /home/5g-catalogue/deployments/docker/5g-catalogue-app/templates/application.properties /home/5g-catalogue/5gcatalogue-app/src/main/resources/

WORKDIR /home/5g-catalogue
RUN mvn clean install

# Configure environment
RUN mkdir -p /var/log/5gcatalogue/ \
    && chmod a+rw /var/log/5gcatalogue/

EXPOSE ${catalogue_server_port}

WORKDIR /home/5g-catalogue/5gcatalogue-app
CMD mvn spring-boot:run
