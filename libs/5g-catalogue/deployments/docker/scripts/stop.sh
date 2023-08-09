#!/bin/bash

if [ -z "$1" ]
then
   echo -e "Please provide a valid docker-compose file.\nUsage example: '$0 docker-compose.yml [SERVICE]'";
   exit 1
fi

docker-compose -f "$1" stop $2