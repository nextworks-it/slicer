#!/bin/bash

function usage {
    printf "usage: %s [CONTAINER]\n    CONTAINER = {TEST,GUI}, default: GUI;\n        the container to start\n", "$0"
}

cwd=$(pwd)

if [ "$(basename "$cwd")" != sebastian ]; then
    printf "The script should be run from the root of the git repo, i.e. the folder containing it.\nCurrent dir: %s\n", "$cwd"
    exit 1
fi

printf "Checking image existence\n"

if ! { docker images | awk '{print $1}' | grep nextworks/sebastian-tests > /dev/null; }; then
    printf "Creating test image.\n"
    pushd docker/test
    make
    popd
fi

if ! { docker images | awk '{print $1}' | grep nextworks/sebastian-gui > /dev/null; }; then
    printf "Creating gui image.\n"
    pushd docker/gui
    make
    popd
fi

printf "Images present\n"

if [ -z "$1" ] || [ "$1" == GUI ]; then
    cn=sebastian-gui
elif [ "$1" == TEST ]; then
    cn=sebastian-tests
else
    printf "Unknown container %s\n", "$1"
    usage
fi

if docker ps -a | awk '{print $NF}' | grep sebastian-tests > /dev/null; then
    echo "Deleting previous container."
    docker rm sebastian-tests || { printf "Couldn't stop container sebastian-tests.\n"; exit 1; }
fi

if docker ps -a | awk '{print $NF}' | grep sebastian-gui > /dev/null; then
    echo "Deleting previous container."
    docker rm sebastian-gui || { printf "Couldn't stop container sebastian-gui.\n"; exit 1; }
fi

if [ "$cn" == sebastian-tests ]; then
    docker run -d --name "$cn" -p 80:80 -p 5432:5432 -p 8082:8082 -v "$cwd"/SEBASTIAN_WEB_GUI:/www -v "$cwd"/SEBASTIAN/target:/sebastian nextworks/"$cn"
    docker cp docker/test/application.properties "$cn":/sebastian/application.properties
fi

if [ "$cn" == sebastian-gui ]; then
    docker run -d --name "$cn" -p 80:80 -v "$cwd"/SEBASTIAN_WEB_GUI:/www nextworks/"$cn"
fi
