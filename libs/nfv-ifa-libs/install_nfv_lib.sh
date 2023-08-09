#!/bin/bash

PWD=`pwd`

while getopts ':hd:' option; do
	case "${option}" in
		d) PWD=$OPTARG
		   ;;
		h) echo "USAGE: ./install_nfv_lib.sh [-d DIR_LIBS]"
		   echo "       DIR_LIBS: Directory where libs are stored. In case parameter is missing, DIR_LIBS=PWD"
		   exit
		   ;;
	esac
done



DIR_LIBS=${PWD}

DIR_LIBS_COMMON=NFV_MANO_LIBS_COMMON/
DIR_LIBS_DESC=NFV_MANO_LIBS_DESCRIPTORS/
DIR_LIBS_CAT=NFV_MANO_LIBS_CATALOGUES_IF/
DIR_LIBS_IFA7_GRANT=NFV_MANO_LIBS_IFA07_GRANT_IF/
DIR_LIBS_IFA7_LCM=NFV_MANO_LIBS_IFA07_VNF_LCM_IF/
DIR_LIBS_IFA13_LCM=NFV_MANO_LIBS_IFA13_NS_LCM_IF/
DIR_LIBS_MONIT=NFV_MANO_LIBS_MONIT_IF/
DIR_LIBS_VNF_IND=NFV_MANO_LIBS_VNF_INDICATOR_IF/
DIR_LIBS_VNF_CONF=NFV_MANO_LIBS_VNF_CONFIG_IF/
DIR_LIBS_SW_IMAGE=NFV_MANO_LIBS_SW_IMAGES_IF/
DIR_LIBS_ORVI=NFV_MANO_LIBS_IFA05_VR_MGT_IF/
DIR_LIBS_POLICY=NFV_MANO_LIBS_POLICY_MGT_IF/
DIR_LIBS_NST=NFV_MANO_LIBS_TEMPLATES/

mvn_install() {
	cd "$1"
	mvn clean install
	if [ "$?" -ne 0 ]; then
		echo "Failed $2 installation!"
		exit 1
	else
		echo "Installed $2"
	fi
}

mvn_install_doc() {
	cd "$1"
	mvn clean javadoc:jar install
	if [ "$?" -ne 0 ]; then
		echo "Failed $2 installation!"
		exit 1
	else
		echo "Installed $2 with javadoc"
	fi
}

echo "Starting installation"

cd $DIR_LIBS

mvn_install_doc $DIR_LIBS_COMMON "NFV libs common"
cd ..
mvn_install_doc $DIR_LIBS_DESC "NFV libs descriptors"
cd ..
mvn_install_doc $DIR_LIBS_CAT "NFV libs catalogues"
cd ..
mvn_install $DIR_LIBS_IFA7_GRANT "NFV libs IFA7 grant"
cd ..
mvn_install $DIR_LIBS_IFA7_LCM "NFV libs IFA7 LCM"
cd ..
mvn_install_doc $DIR_LIBS_IFA13_LCM "NFV libs IFA13 LCM"
cd ..
mvn_install $DIR_LIBS_MONIT "NFV libs monitoring"
cd ..
mvn_install $DIR_LIBS_VNF_IND "NFV libs VNF indicators"
cd ..
mvn_install_doc $DIR_LIBS_VNF_CONF "NFV libs VNF configuration"
cd ..
mvn_install $DIR_LIBS_SW_IMAGE "NFV libs SW images management"
cd ..
mvn_install $DIR_LIBS_ORVI "NFV libs OrVi"
cd ..
mvn_install $DIR_LIBS_POLICY "NFV libs Policy management"
cd ..
mvn_install $DIR_LIBS_NST "NFV libs Network Slice template"

echo "All NFV libs installed!"

exit #
