#!/bin/bash

service postgresql start
service apache2 start

su postgres -c 'psql -c "ALTER USER postgres PASSWORD '"'postgres'"';"; psql -c "CREATE DATABASE sebastian;"'

echo 'Postgresql set up done'

supervisord
echo 'Supervisor started'

cd sebastian
java -jar Sebastian-0.0.2-SNAPSHOT.jar
