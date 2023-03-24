#!/bin/bash

COMMAND="$1"

if [[ "$COMMAND" != "start" ]] && [[ "$COMMAND" != "stop" ]] && [[ "$COMMAND" != "restart" ]]; then
	echo "Usage: $0 start | stop | restart"
	exit 0
fi

APP_BASE_PATH=$(cd `dirname $0`; pwd)

function start()
{
    JAVA_OPTS=-Dspring.profiles.include=core,test
    if [[ "${project.artifactId}" == "kuberix_gateway" ]]; then
    	JAVA_OPTS=
    fi
    nohup java -Xms128m -Xmx512m -jar ${JAVA_OPTS} ${project.artifactId}-${project.version}.jar >> /dev/null 2>&1 &
    echo "Starting"
}

function stop()
{
    P_ID=`ps -ef | grep -w ${project.artifactId}-${project.version}.jar | grep -v "grep" | awk '{print $2}'`
    kill $P_ID
    echo "Stopping"
}

function restart()
{
    P_ID=`ps -ef | grep -w ${project.artifactId}-${project.version}.jar | grep -v "grep" | awk '{print $2}'`
    start
    sleep 25s
    kill $P_ID
    echo "Restarting"
}

if [[ "$COMMAND" == "start" ]]; then
	start
elif [[ "$COMMAND" == "stop" ]]; then
    stop
else
    restart
fi
