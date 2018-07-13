FROM ubuntu:16.04
MAINTAINER MarcoC <m.capitani@nextworks.it>

ENV POSTGRES_VERSION 9.4

# Installing packages
RUN apt-get update 
RUN DEBIAN_FRONTEND=noninteractive apt-get -y install software-properties-common supervisor apache2 php7.0
RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-keys B97B0AFCAA1A47F044F244A07FCC7D46ACCC4CF8
RUN echo "deb http://apt.postgresql.org/pub/repos/apt/ trusty-pgdg main $POSTGRES_VERSION" > /etc/apt/sources.list.d/pgdg.list
RUN apt-get update
RUN DEBIAN_FRONTEND=noninteractive apt-get -y install postgresql-$POSTGRES_VERSION postgresql-client-$POSTGRES_VERSION postgresql-contrib-$POSTGRES_VERSION php7.0-pgsql
RUN DEBIAN_FRONTEND=noninteractive apt-get -y install rabbitmq-server
RUN DEBIAN_FRONTEND=noninteractive apt-get -y install openjdk-8-jdk
RUN apt-get clean
RUN echo "host    all    all    0.0.0.0/0    md5" >> /etc/postgresql/$POSTGRES_VERSION/main/pg_hba.conf
RUN echo "listen_addresses='*'" >> /etc/postgresql/$POSTGRES_VERSION/main/postgresql.conf
RUN systemctl enable postgresql
RUN service postgresql start

# JAVA env vars
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV PATH $JAVA_HOME/bin:$PATH

# Scripts
ADD supervisord-apache2.conf /etc/supervisor/conf.d/supervisord-apache2.conf
ADD supervisord-apache2.sh /supervisord-apache2.sh
ADD supervisord-postgresql.conf /etc/supervisor/conf.d/supervisord-postgresql.conf
ADD supervisord-postgresql.sh /supervisord-postgresql.sh
ADD start.sh /start.sh
RUN chmod 755 /*.sh

VOLUME ["/www", "/sebastian"]

# PHP application folder
RUN mkdir -p /www && rm -fr /var/www/html && ln -s /www /var/www/html

# PostgreSQL data folders
RUN mkdir -p /var/lib/postgresql && mkdir -p /var/log/postgresql
VOLUME ["/var/log/postgresql", "/var/lib/postgresql"]

EXPOSE 80 5432 8082
CMD ["/start.sh"]