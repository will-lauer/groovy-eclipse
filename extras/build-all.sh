#!/bin/bash

# rm -fr ~/.m2/repository

pwd=`pwd`

echo Building groovy-eclipse-batch and installing to maven local
cd ${pwd}/groovy-eclipse-batch-builder
ant extract-create-install

#echo Installing groovy-eclipse-compiler to maven local
#cd ${pwd}/groovy-eclipse-compiler
#mvn clean install

echo Running integration tests...
cd ${pwd}/groovy-eclipse-compiler-tests
mvn clean install
