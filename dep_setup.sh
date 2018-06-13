#!/bin/bash

# Check sudo usage
if [[ $(id -u) != 0 ]]; then
    echo "Root permissions required."
    exit 1
fi

# Install deps

# Java
if ! type java > /dev/null; then
    echo "Installing Java runtime"
    apt-get install openjdk-8-jdk -y
else
    version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo "Java version $version"
    if [[ "$version" > "1.8" || "$version" == "1.8" ]]; then
        echo "Java >=8 already present."
    else
        echo "Java is present, but in a version < 8."
        echo "Please install Java 8 manually and configure it accordingly."
    fi
if ! type rabbitmq-server > /dev/null; then
    echo "Installing rabbitmq"
    apt-get install rabbitmq-server -y
fi
if ! type psql > /dev/null; then
    echo "Installing postgresql"
    apt-get install postgresql -y
fi
if ! type mvn > /dev/null; then
    echo "Installing maven"
    apt-get install maven
fi

# Start rabbit
service rabbitmq-server start || true

# Configure & start postgres
service postgresql start || true
sudo -u postgres bash -c 'psql -c "ALTER USER postgres PASSWORD '"'postgres'"';"
                          psql -c "CREATE DATABASE timeo;"'
service postgresql restart

echo "****************"
echo "Please run './install_libs.sh <FOLDER>' to complete the installation"
echo "****************"

