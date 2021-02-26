FROM node:12
WORKDIR /home
RUN apt-get update && apt-get install -y \
    git \
    apache2
#RUN  chown ubuntu:ubuntu /home/ubuntu/.ssh/*


# Get all the component
COPY ./git_cmd.sh /root/git_cmd.sh
RUN chmod +x /root/git_cmd.sh

RUN /root/git_cmd.sh vertical-slicer /home/slicer
WORKDIR /usr/src/app/app-ui
RUN cp -r /home/slicer/angular-slicer/slicer/. .

COPY ./environment/environments.ts /usr/src/app/app-ui/src/app/environments
RUN npm install
RUN npm install -g @angular/cli
RUN ng build --commonChunk=true --namedChunks=true --optimization=true --sourceMap=false
RUN cp -r dist/* /var/www/html

EXPOSE 80

CMD apachectl -D FOREGROUND


