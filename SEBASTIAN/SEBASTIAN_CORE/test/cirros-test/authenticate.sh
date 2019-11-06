function usage {
    echo "Usage: $0 USERNAME PASSWORD"
}

if [[ -z $1 || -z $2 ]]; then
    usage
    exit 1
fi

curl -i -X POST -d username="$1" -d password="$2" -c /tmp/seb_cookie http://localhost:8082/login
