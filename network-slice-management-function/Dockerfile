# Download base image ubuntu 18
#FROM ubuntu:20.04 AS nsmf-builder
#ARG GITLAB_USER
#ARG GITLAB_TOKEN
#ARG NSMF_BRANCH=master
#ARG VS_COMMON_BRANCH=master
#non interactive tzdata config
#ENV DEBIAN_FRONTEND=noninteractive
# Update Ubuntu software repository and install prerequisites
#RUN apt-get update && apt-get install -y \
#    git \
#    systemd \
#    wget \
#    openjdk-8-jdk \
#    sudo
#RUN echo 10.30.7.30 gitlab.nextworks.it >> /etc/hosts

#RUN useradd -ms /bin/bash ubuntu
# Install Maven 3.3.9
#RUN wget http://apache-mirror.rbc.ru/pub/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz \
#    && tar -xvzpf apache-maven-3.3.9-bin.tar.gz \
#    && mkdir -p /opt/maven/3.3.9 \
#    && mv apache-maven-3.3.9/* /opt/maven/3.3.9/ \
#    && rm -rf apache-maven-3.3.9 \
#    && rm apache-maven-3.3.9-bin.tar.gz \
#    && ln -s /opt/maven/3.3.9/ /opt/maven/current
#ENV MAVEN_HOME=/opt/maven/current
#ENV PATH=$PATH:$MAVEN_HOME/bin
#RUN mkdir /root/.ssh/
# Get all the component
#RUN echo "Host gitlab.nextworks.it\n\tStrictHostKeyChecking no\n" >> /root/.ssh/config
#USER root

########################
## The idea is to clone only the NSMF source code and using the referenced packages, build the NSMF APP (For ingenious)

#ADD "https://gitlab.nextworks.it/api/v4/projects/157/repository/branches/$NSMF_BRANCH?private_token=$GITLAB_TOKEN" /tmp/nsmf-cache
#RUN git clone --branch $NSMF_BRANCH https://$GITLAB_USER:$GITLAB_TOKEN@gitlab.nextworks.it/kd_nextworks/network-slice-management-function.git /home/ubuntu/nsmf


#RUN cd /home/ubuntu/nsmf/nsmf-service && mvn clean install -DskipTests
#RUN cd /home/ubuntu/nsmf/nsmf-app && mvn clean install -DskipTests
####################################
### BUILD PART SKIP FOR NOW ########
####################################

FROM ubuntu:20.04 

#non interactive tzdata config
ENV DEBIAN_FRONTEND=noninteractive
# Update Ubuntu software repository and install prerequisites
RUN apt-get update && apt-get install -y openjdk-8-jdk
COPY ./nsmf-app/target/nsmf-app-0.0.1.jar /home/ubuntu/
#ENTRYPOINT ["bash", "-c", "/usr/bin/java", "-jar, /home/ubuntu/nssmf-app-0.0.1.jar"]
