#!/bin/bash

if [ -z "$1" ]
then
   echo -e "Please provide a valid docker-compose file.\nUsage example: '$0 docker-compose.yml'";
   exit 1
fi

mkdir -p ~/.docker/
touch ~/.docker/config.json
echo "{\"proxies\":{\"default\":{\"httpProxy\":\"http://163.162.8.137:9777\",\"httpsProxy\":\"http://163.162.8.137:9777\",\"noProxy\":\"localhost,127.0.0.1\"}}}" > ~/.docker/config.json

docker-compose -f "$1" up -d --build