#!/bin/bash

# Check if the first argument is the latest version
function is-latest {
        if [[ -z $1 || -z $2 ]]; then
            return 255
        fi
        max=($(sort-ver "$1" "$2"))
        if [[ "${max[0]}" == "$1" ]]; then
            return 0
        else
            return 1
        fi
}

# Sort a list (as args) of version numbers
function sort-ver {
    a="";
    for i in "$@"; do
        a+=$(printf "\n%s" "$i")
    done
    printf "%s" "$a" | sort -V -r
}

# Acquire sudo
sudo -v || (echo "Super user capabilities required."; exit 1)

# Install deps

# Java
if ! type java > /dev/null; then
    echo "Installing Java runtime"
    sudo apt-get install openjdk-8-jdk -y
else
    version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo "Java version $version"
    if is-latest "$version" 1.8; then
        echo "Java >=8 already present."
    else
        echo "Java is present, but in a version < 8."
        echo "Please install Java 8 manually and configure it accordingly."
    fi
fi
if ! type rabbitmq-server > /dev/null; then
    echo "Installing rabbitmq"
    sudo apt-get install rabbitmq-server -y
else
    echo "Rabbitmq already present."
fi
if ! type psql > /dev/null; then
    echo "Installing postgresql"
    sudo apt-get install postgresql -y
else
    echo "postgresql already present."
fi
if ! type mvn > /dev/null; then
    echo "Installing maven"
    sudo apt-get install maven -y
else
    echo "Maven already present."
fi

# Start rabbit
sudo service rabbitmq-server start || true

# Configure & start postgres
sudo service postgresql start || true
sudo -u postgres bash -c 'psql -c "ALTER USER postgres PASSWORD '"'postgres'"';"
                          psql -c "CREATE DATABASE sebastian;"' 2>/dev/null

echo "****************"
echo "Please run './install_libs.sh <FOLDER>' to complete the installation"
echo "****************"
