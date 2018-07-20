cwd=$(pwd)

if docker ps -a | awk '{print $NF}' | grep seb-tests > /dev/null; then
    echo "Deleting previous container."
    docker rm seb-tests || exit 1
fi

docker run -d --name seb-tests -p 80:80 -p 5432:5432 -p 8082:8082 -v "$cwd"/SEBASTIAN_WEB_GUI:/www -v "$cwd"/SEBASTIAN/target:/sebastian nextworks/sebastian-tests
docker cp docker/application.properties seb-tests:/sebastian/application.properties
