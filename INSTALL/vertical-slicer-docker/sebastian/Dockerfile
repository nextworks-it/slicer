# Download base image ubuntu 18
FROM ubuntu:18.04

#non interactive tzdata config
ENV DEBIAN_FRONTEND=noninteractive
# Update Ubuntu software repository and install prerequisites
RUN apt-get update && apt-get install -y \
    git \
    ssh \
    systemd \
    wget \
    openjdk-8-jdk \
    sudo


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


RUN useradd -ms /bin/bash ubuntu


# Get all the component
COPY ./git_cmd.sh /home/ubuntu/git_cmd.sh
RUN chmod +x /home/ubuntu/git_cmd.sh
USER ubuntu
#RUN  chown ubuntu:ubuntu /home/ubuntu/.ssh/*

#COPY ./git_cmd.sh /home/ubuntu/git_cmd.sh

#RUN git -c http.sslVerify=false clone --branch feat-librefactor git@10.30.7.30:kd_nextworks/nfv-ifa-libs /home/ubuntu/nfv-ifa-libs
RUN /home/ubuntu/git_cmd.sh nfv-ifa-libs /home/ubuntu/nfv-ifa-libs feat-5gzorro-hackathon
RUN /home/ubuntu/git_cmd.sh slicer-identity-mgmt /home/ubuntu/identity-management master
RUN /home/ubuntu/git_cmd.sh slicer-catalogue /home/ubuntu/blueprint-catalogue master
RUN /home/ubuntu/git_cmd.sh nfv-sol-libs /home/ubuntu/nfv-sol-libs master
RUN /home/ubuntu/git_cmd.sh nfvo-drivers /home/ubuntu/nfvo-driver feat-5gzorro-hackathon
RUN /home/ubuntu/git_cmd.sh slicer /home/ubuntu/slicer master
RUN /home/ubuntu/git_cmd.sh  5g-catalogue /home/ubuntu/5g-catalogue master
RUN /home/ubuntu/git_cmd.sh config-manager-client /home/ubuntu/config-manager-client master
COPY ./sebastian/sebastian_application.properties /home/ubuntu/slicer/sebastian_application.properties
COPY ./environment/environments.ts /home/ubuntu/slicer/angular-slicer/slicer/src/app/environments/environments.ts

# Build all repo
USER root
RUN cd /home/ubuntu/nfv-ifa-libs && mvn clean install
RUN cd /home/ubuntu/identity-management && mvn clean install
RUN cd /home/ubuntu/nfv-sol-libs && ./install_nfv_sol_libs.sh
RUN cd /home/ubuntu/5g-catalogue/OSMr4PlusDataModel && mvn clean install
RUN cd /home/ubuntu/config-manager-client/ConfigManagerClient/ && mvn clean install
RUN cd /home/ubuntu/nfvo-driver && mvn clean install
RUN cd /home/ubuntu/blueprint-catalogue && mvn clean install
RUN cd /home/ubuntu/slicer/5GEVE_REST_CLIENTS && mvn clean install
RUN cd /home/ubuntu/slicer/SEBASTIAN && mvn clean install -DskipTests



RUN rm -rf /var/log/sebastian && mkdir /var/log/sebastian
RUN chown -R ubuntu /var/log/sebastian/
