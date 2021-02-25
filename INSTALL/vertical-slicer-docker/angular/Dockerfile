FROM node:12
WORKDIR /home
RUN apt-get update && apt-get install -y \
    git \
    apache2

RUN git clone --branch slicer_merge git://10.0.2.10:/git/KD/slicer
WORKDIR /usr/src/app/app-ui
RUN cp -r /home/slicer/angular-slicer/slicer/. .
#COPY ./environment/environments.ts /usr/src/app/app-ui/src/app/environments
RUN npm install
RUN npm install -g @angular/cli

RUN ng build --commonChunk=true --namedChunks=true --optimization=true --sourceMap=false
RUN cp -r dist/* /var/www/html

EXPOSE 80

CMD apachectl -D FOREGROUND

