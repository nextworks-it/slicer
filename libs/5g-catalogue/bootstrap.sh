#!/bin/bash

BANNER_H="~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
BANNER_S="\n\n\n\n\n\n"
GITUSER=$USER

function log()
{
    echo $BANNER_H
    echo $1
    echo -e $BANNER_S
}

function myhelp ()
{
    echo "Usage: $0 <CMD>"
    echo "-----"
    echo " CMD := compile-app | run-app | all |"
    echo "        env-dep"
}

function prepareEnv()
{
    log "PREPARING ENVIRONMENT"

    if [ ! -d  /var/log/5gcatalogue/ ]; then
        sudo mkdir  /var/log/5gcatalogue/
        sudo chmod a+rw /var/log/5gcatalogue/
    fi

    which psql
    if [ "$?" -gt "0" ]; then
        log "POSTGRES not installed... Installing"
        sudo apt-get install postgresql -y
    fi
    sudo -u postgres psql -U postgres -d postgres -c "alter user postgres with password 'postgres';"
    sudo -u postgres createdb cataloguedb

    log "BUILDING NFV-LIB DEPENDENCIES..."
    if [ ! -d  ../nfv-sol-libs/ ]; then
        cd ..
        git clone https://github.com/nextworks-it/nfv-sol-libs.git
        cd nfv-sol-libs
        git checkout v3.1.1
        cd ../5g-catalogue
    fi

    cd ../nfv-sol-libs/NFV_MANO_SOL001_LIBS_COMMON
    mvn clean install
    cd ../NFV_MANO_SOL001_LIBS_DESCRIPTORS
    mvn clean install
    cd ../../5g-catalogue    

log "CLONING SUBMODULES..."
    git submodule update --init
}

function compileApp()
{
    log "COMPILING 5G APPs & SERVICEs CATALOGUE"
    mvn clean install
}

function runApp()
{
    log "RUNNING 5G APPs & SERVICEs CATALOGUE"
    cd 5gcatalogue-app
    mvn spring-boot:run
    cd ../
}


case $1 in
    (env-dep)
        prepareEnv
        ;;
    (compile-app)
        compileApp
        ;;
    (run-app)
        runApp
        ;;
    (all)
        prepareEnv;
        compileApp;
        runApp;
        ;;

    (*)
        echo "Invalid option '$1'!!!!"
        myhelp;
        exit -1
        ;;
esac
