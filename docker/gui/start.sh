#!/bin/bash

service apache2 start

echo 'Starting supervisor'
exec supervisord -n
