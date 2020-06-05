###ONCE CLONED THE REPOSITORIES, MOVE THIS SCRIPT IN THE PARENT DIRECTORY AND EXECUTE IT

echo "BUILDING NFV IFA LIBS"
cd nfv-ifa-libs/
mvn install
cd ..

echo "BUILDING SOL LIBS"
cd nfv-sol-libs/
./install_nfv_sol_libs.sh
cd ..

echo "BUILDING 5G CATALOGUE"
cd 5g-catalogue
mvn install
cd ..

echo "BUILDING QOE REST CLIENT"
cd qoe-rest-client/
mvn install
cd ..

echo "Building NFVO_LCM_ABSTRACT_DRIVER"
cd nfvo-driver/NFVO_LCM_ABSTRACT_DRIVER
mvn install
cd ../..


echo "Building NFVO_LCM_ABSTRACT_DRIVER"
cd nfvo-driver/NFVO_CATALOGUE_ABSTRACT_DRIVER
mvn install
cd ../..

echo "BUILDING NMRO DRIVER"
cd nmro-driver/
mvn install
cd ..

echo "BUILDING NFVO DRIVER"
cd nfvo-driver/
mvn install
cd ..

echo "BUILDING IDENTITY MANAGEMENT"
cd identity-management
mvn install
cd ..

echo "INSTALLING BLUEPRINT CATALOGUE"
cd blueprint-catalogue
mvn install -DskipTests
cd ..

echo "GOING TO BUILD SSO"
cd slicer/SEBASTIAN
mvn clean install

echo "BUILDING VSMF"
cd VSMF_APP/
mvn install
cd ..

echo "BUILDING NSMF"
cd NSMF_APP/
mvn install
cd ../../..

