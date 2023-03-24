#!/bin/sh
mvn clean -Dmaven.test.skip=true package

rm -rf jars/
mkdir jars/

cp docker-entrypoint.sh jars/docker-entrypoint.sh

mkdir jars/admin/
unzip admin/target/admin.zip -d jars/admin

mkdir jars/authorization/
unzip authorization/target/authorization.zip -d jars/authorization

mkdir jars/bi/
unzip bi/target/bi.zip -d jars/bi

mkdir jars/crm/
unzip crm/target/crm.zip -d jars/crm

mkdir jars/examine/
unzip examine/target/examine.zip -d jars/examine

mkdir jars/gateway/
unzip gateway/target/gateway.zip -d jars/gateway

mkdir jars/hrm/
unzip hrm/target/hrm.zip -d jars/hrm

mkdir jars/job/
unzip job/target/job.zip -d jars/job

mkdir jars/oa/
unzip oa/target/oa.zip -d jars/oa

mkdir jars/work/
unzip work/target/work.zip -d jars/work

docker build -t kuberix-crm .
