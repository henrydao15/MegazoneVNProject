#!/bin/sh

echo "Waiting Eureka server setup..."
sleep 60s

cd /opt/app/work
java -Xms128m -Xmx512m -jar kuberix_work-0.0.1-SNAPSHOT.jar >> /dev/null 2>&1 &

cd /opt/app/oa
java -Xms128m -Xmx512m -jar kuberix_oa-0.0.1-SNAPSHOT.jar >> /dev/null 2>&1 &

cd /opt/app/authorization
java -Xms128m -Xmx512m -jar kuberix_authorization-0.0.1-SNAPSHOT.jar >> /dev/null 2>&1 &

cd /opt/app/admin
java -Xms128m -Xmx512m -jar kuberix_admin-0.0.1-SNAPSHOT.jar >> /dev/null 2>&1 &

cd /opt/app/bi
java -Xms128m -Xmx512m -jar kuberix_bi-0.0.1-SNAPSHOT.jar >> /dev/null 2>&1 &

cd /opt/app/crm
java -Xms128m -Xmx512m -jar kuberix_crm-0.0.1-SNAPSHOT.jar >> /dev/null 2>&1 &

cd /opt/app/job
java -Xms128m -Xmx512m -jar kuberix_job-0.0.1-SNAPSHOT.jar >> /dev/null 2>&1 &

cd /opt/app/examine
java -Xms128m -Xmx512m -jar kuberix_examine-0.0.1-SNAPSHOT.jar >> /dev/null 2>&1 &

cd /opt/app/hrm
java -Xms128m -Xmx512m -jar kuberix_hrm-0.0.1-SNAPSHOT.jar >> /dev/null 2>&1 &

cd /opt/app/gateway
java -Xms128m -Xmx512m -jar kuberix_gateway-0.0.1-SNAPSHOT.jar >> /dev/null 2>&1 &

echo "Kuberix CRM starting..."

tail -f /dev/null
