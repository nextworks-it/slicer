# Download base image ubuntu 18
FROM ubuntu:18.04

#non interactive tzdata config
ENV DEBIAN_FRONTEND=noninteractive
# Update Ubuntu software repository and install prerequisites
RUN apt-get update && apt-get install -y \
    git \
    systemd \
    wget \
    openjdk-8-jdk \
    rabbitmq-server \
    sudo \
    postgresql 

#postgres will be moved out
RUN service postgresql start && \
    sudo -u postgres psql -c "ALTER USER postgres PASSWORD 'postgres';" && \
    sudo -u postgres psql -c "CREATE DATABASE sebastian;"
EXPOSE 8082

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

# Proxy configuration for Maven (from 5Gcatalogue deploy)
# COPY templates/settings.xml /opt/maven/current/conf/.
# RUN if [ "$proxy_enabled" = "false" ] ; then rm /opt/maven/current/conf/settings.xml; fi

RUN useradd -ms /bin/bash ubuntu

# Get all the component
USER ubuntu
RUN git clone --branch dev_5growth git://10.0.2.10:/git/KD/nfv-ifa-libs /home/ubuntu/nfv-ifa-libs
RUN git clone git://10.0.2.10:/git/KD/identity-management /home/ubuntu/identity-management
RUN git clone --branch slicer_merge git://10.0.2.10:/git/KD/blueprint-catalogue /home/ubuntu/blueprint-catalogue
RUN git clone git://10.0.2.10:/git/KD/nfv-sol-libs /home/ubuntu/nfv-sol-libs
RUN git clone --branch feat-osm-driver git://10.0.2.10:/git/KD/nfvo-driver /home/ubuntu/nfvo-driver
RUN git clone --branch slicer_merge git://10.0.2.10:/git/KD/slicer /home/ubuntu/slicer
RUN git clone git://10.0.2.10:/git/KD/5g-catalogue /home/ubuntu/5g-catalogue
RUN git clone git://10.0.2.10:/git/KD/timeo /home/ubuntu/timeo
COPY ./sebastian/sebastian_application.properties /home/ubuntu/slicer/sebastian_application.properties
COPY ./environment/environments.ts /home/ubuntu/slicer/angular-slicer/slicer/src/app/environments/environments.ts

# Build all repo
USER root
RUN cd /home/ubuntu/nfv-ifa-libs && mvn clean install
RUN cd /home/ubuntu/identity-management && mvn clean install
RUN cd /home/ubuntu/nfv-sol-libs && ./install_nfv_sol_libs.sh
RUN cd /home/ubuntu/5g-catalogue/OSMr4PlusDataModel && mvn clean install
RUN cd /home/ubuntu/timeo/ConfigManagerApi && mvn clean install
RUN cd /home/ubuntu/nfvo-driver && mvn clean install
RUN cd /home/ubuntu/blueprint-catalogue && mvn clean install
RUN cd /home/ubuntu/slicer/5GEVE_REST_CLIENTS && mvn clean install
RUN service rabbitmq-server start && service postgresql start && \
    cd /home/ubuntu/slicer/SEBASTIAN && mvn clean install

#Deployment Slicer
#RUN sed -i "s|file:/dev/random|"file:/dev/./urandom"|g" /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/java.security

# Create service file for the VerticalSlicer
#RUN echo "[Unit]" > /home/ubuntu/slicer.service \
#    && echo "Description=Sebastian" >> /home/ubuntu/slicer.service \
#    && echo "After=syslog.target network-online.target" >> /home/ubuntu/slicer.service \
#    && echo "" >> /home/ubuntu/slicer.service \
#    && echo "[Service]" >> /home/ubuntu/slicer.service \
#    && echo "User=root" >> /home/ubuntu/slicer.service \
#    && echo "User=ubuntu" >> /home/ubuntu/slicer.service \
#    && echo "Restart=on-failure" >> /home/ubuntu/slicer.service \
#    && echo "ExecStart=/usr/bin/java -jar /home/ubuntu/slicer/SEBASTIAN/SEBASTIAN_CORE/target/SEBASTIAN_CORE-0.0.2-SNAPSHOT.jar --spring.config.location=/home/ubuntu/slicer/sebastian_application.properties" >> /home/ubuntu/slicer.service \
#    && echo "RestartSec=3" >> /home/ubuntu/slicer.service \
#    && echo "" >> /home/ubuntu/slicer.service \
#    && echo "[Install]" >> /home/ubuntu/slicer.service \
#    && echo "WantedBy=multi-user.target" >> /home/ubuntu/slicer.service


RUN rm -rf /var/log/sebastian && mkdir /var/log/sebastian
RUN chown -R ubuntu /var/log/sebastian/
# Linking file to the services list
#RUN systemctl enable /home/ubuntu/slicer.service