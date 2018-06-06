#!/bin/bash

# Check sudo usage
if [[ $(id -u) != 0 ]]; then
    echo "Root permissions required."
    exit 1
fi

# Install deps
apt-get install openjdk-8-jre -y
apt-get install rabbitmq-server
apt-get install postgresql -y

# Start rabbit
service rabbitmq-server start || true

# Configure & start postgres
service postgresql start || true
sudo -u postgres bash -c 'psql -c "ALTER USER postgres PASSWORD '"'postgres'"';"
                          psql -c "CREATE DATABASE sebastian;"'
service postgresql restart

