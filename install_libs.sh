function usage {
    echo "USAGE: $0 FOLDER"
    echo "Arguments:"
    echo "FOLDER: the folder in which the libs repo should be cloned"
}

if [[ -z "$1" ]]; then
    echo "No folder specified."
    usage
    exit 1
fi

cd "$1" || ( echo "Invalid folder $1."; usage; exit 1 )

echo "***  Cloning the repo in $1  ***"
echo ""
git clone https://github.com/nextworks-it/nfv-libs || ( echo "Could not clone the repo"; exit 1 )
echo ""
echo "***  Repo cloned.  ***"
echo ""

echo "***  Installing the libs...  ***"
echo ""

cd nfv-libs
mvn clean install || ( echo "Could not install the libs"; exit 1 )
echo ""
echo "***  All done!  ***"
