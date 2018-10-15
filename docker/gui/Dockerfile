FROM ubuntu:16.04
MAINTAINER MarcoC <m.capitani@nextworks.it>

ENV POSTGRES_VERSION 9.4

# Installing packages
RUN apt-get update 
RUN DEBIAN_FRONTEND=noninteractive apt-get -y install software-properties-common supervisor apache2 php7.0
RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-keys B97B0AFCAA1A47F044F244A07FCC7D46ACCC4CF8
RUN apt-get update
RUN apt-get clean

# Apache configuration
ADD apache2.conf /etc/apache2/apache2.conf
ADD ssi.conf /etc/apache2/conf-available/ssi.conf
ADD 000-default.conf /etc/apache2/sites-available/000-default.conf

RUN a2enconf ssi
RUN a2enmod include

# Scripts
ADD supervisord-apache2.conf /etc/supervisor/conf.d/supervisord-apache2.conf
ADD supervisord-apache2.sh /supervisord-apache2.sh
ADD start.sh /start.sh
RUN chmod 755 /*.sh

VOLUME ["/www"]

# PHP application folder
RUN mkdir -p /www && rm -fr /var/www && ln -s /www /var/www

EXPOSE 80 8082
CMD ["/start.sh"]