set -e
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
deps_folder=$PWD

echo "***  Cloning the NFV-LIBS repo in $1  ***"
echo ""
	git clone --branch feat-librefactor https://github.com/nextworks-it/nfv-libs 
echo ""
echo "***  Repo cloned.  ***"
echo ""

echo "***  Cloning the SLICER-IDENTITY-MGMT repo in $1  ***"
echo ""
	git clone https://github.com/nextworks-it/slicer-identity-mgmt 
echo ""
echo "***  Repo cloned.  ***"
echo ""

echo "***  Cloning the NFVO-SOL-LIBS repo in $1  ***"
echo ""
	git clone https://github.com/nextworks-it/nfv-sol-libs 
echo ""
echo "***  Repo cloned.  ***"
echo ""


echo "***  Cloning the NFVO-DRIVERS repo in $1  ***"
echo ""
	git clone https://github.com/nextworks-it/nfvo-drivers
echo ""
echo "***  Repo cloned.  ***"
echo ""

echo "***  Cloning the SLICER-CATALOGUE repo in $1  ***"
echo ""
	git clone --branch functional_split https://github.com/nextworks-it/slicer-catalogue 
echo ""
echo "***  Repo cloned.  ***"
echo ""

echo "***  Installing the libs...  ***"
echo ""

echo "*** INSTALLING nfv-libs ***"
	cd $deps_folder/nfv-libs
	mvn clean install 
echo ""

echo "*** INSTALLING slicer-indentity-mgmt ***"
	cd $deps_folder/slicer-identity-mgmt
	mvn clean install 
echo ""

echo "*** INSTALLING nfv-sol-libs ***"
	cd $deps_folder/nfv-sol-libs
 	./install_nfv_sol_libs.sh 
echo ""


echo "*** INSTALLING nfvo-drivers ***"
	cd $deps_folder/nfvo-drivers
	mvn clean install 
echo ""


echo "*** INSTALLING slicer-catalogue ***"
	cd $deps_folder/slicer-catalogue
	mvn clean install 
echo ""


