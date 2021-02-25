# Download base image ubuntu 18
FROM ubuntu:18.04

# Update Ubuntu software repository and install prerequisites
RUN apt-get update && apt-get install -y \
    nodejs \
    npm 

RUN npm install -g npm@latest

COPY slicer /home/ubuntu/slicer/
RUN cd /home/ubuntu/slicer && npm install

RUN npm install -g @angular/cli 

EXPOSE 4200 80 8082

#docker run -p 8080:8080 -p 4200:4200 -p 80:80 --detach --name angular-gui angular-gui