#!/usr/bin/env bash

export slicer="192.168.217.2"
export vertical_group="media"
export vertical_tenant="NXW"
export SLICER_SCRIPTS_FOLDER=$(pwd)/scripts
export SLICER_DESCRIPTORS_FOLDER=$(pwd)/descriptors

login_admin(){
 echo login_admin
 curl -i -X POST -d username=admin -d password=admin -c ${SLICER_SCRIPTS_FOLDER}/admin_credentials http://$slicer:8082/login

}

login_tenant(){
    echo login_tenant
    curl -i -X POST -d username=$vertical_tenant -d password=$vertical_tenant -c ${SLICER_SCRIPTS_FOLDER}/tenant_credentials http://$slicer:8082/login
}

create_vs_group(){
    echo create_vs_group
    curl -b ${SLICER_SCRIPTS_FOLDER}/admin_credentials -X POST http://$slicer:8082/vs/admin/group/$vertical_group

}

create_vs_tenant(){
    echo create_vs_tenant
    envsubst < ${SLICER_SCRIPTS_FOLDER}/create_tenant_template.json > ${SLICER_SCRIPTS_FOLDER}/create_tenant.json
    curl -b ${SLICER_SCRIPTS_FOLDER}/admin_credentials  -d @${SLICER_SCRIPTS_FOLDER}/create_tenant.json -X POST http://$slicer:8082/vs/admin/group/$vertical_group/tenant --header "Content-Type:application/json"
}


create_vs_sla(){
    echo create_vs_sla
    curl -b ${SLICER_SCRIPTS_FOLDER}/admin_credentials -d @${SLICER_SCRIPTS_FOLDER}/create_sla.json -X POST http://$slicer:8082/vs/admin/group/$vertical_group/tenant/$vertical_tenant/sla --header "Content-Type:application/json"

}


create_vs_vsb(){
    echo create_vs_vsb
    export current_VSB=$(curl -b ${SLICER_SCRIPTS_FOLDER}/admin_credentials -d @${SLICER_DESCRIPTORS_FOLDER}/vsb_vCDN_nbi.json -X POST http://$slicer:8082/vs/catalogue/vsblueprint --header "Content-Type:application/json")

}

create_vs_vsd(){
    echo create_vs_vsd
    envsubst < ${SLICER_DESCRIPTORS_FOLDER}/vsd_vCDN_template.json > ${SLICER_DESCRIPTORS_FOLDER}/vsd_vCDN.json
    export current_VSD=$(curl -b ${SLICER_SCRIPTS_FOLDER}/tenant_credentials -d @${SLICER_DESCRIPTORS_FOLDER}/vsd_vCDN.json -X POST http://$slicer:8082/vs/catalogue/vsdescriptor --header "Content-Type:application/json")



}

instantiate_vs(){
    echo instantiate_vs
    envsubst < ${SLICER_SCRIPTS_FOLDER}/req_instantiate_vCDN_template.json > ${SLICER_SCRIPTS_FOLDER}/req_instantiate_vCDN.json
    curl -b ${SLICER_SCRIPTS_FOLDER}/tenant_credentials -d @${SLICER_SCRIPTS_FOLDER}/req_instantiate_vCDN.json -X POST http://$slicer:8082/vs/basic/vslcm/vs --header "Content-Type:application/json"

}
test_vs_vCDN(){
    #see TIMEO/test/bluespace/scripts/nfvo_functions.sh
    create_tenant
    onboard_pnfd
    create_pnf
    create_vim
    create_vnfm
    #------
    login_admin
    create_vs_group
    create_vs_tenant
    create_vs_sla
    create_vs_vsb
    login_tenant
    create_vs_vsd

}



