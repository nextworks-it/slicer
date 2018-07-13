cwd=$(pwd)

docker run -d --name seb-tests -p 80:80 -p 5432:5432 -p 8082 -v "$cwd"/SEBASTIAN:/www -v "$cwd"/SEBASTIAN/target:/sebastian nextworks/sebastian-tests
