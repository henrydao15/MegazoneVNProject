#!/bin/bash
chmod 777 nacos.sh;
chmod 777 kuberixcrm.sh;
chmod 777 mysql.sh;
chmod -R 777 ./;
docker network create --driver=bridge kuberixcrm_network
docker-compose up -d
echo " ------------ The system is booting, the boot process takes about 10 minutes, please visit the system after 10 minutes.。  ------------ ";
