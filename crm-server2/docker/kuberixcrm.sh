#!/bin/sh
#chkconfig: 2345 80 05
#description: kuberixcrm
server_names=('gateway' 'work' 'oa' 'authorization' 'admin' 'bi' 'crm' 'job' 'examine' 'hrm')
echo "Mysql init..."
sleep 180s
case "$1" in
start)
    # shellcheck disable=SC2164
    cd /opt
    # shellcheck disable=SC2039
    for value in "${server_names[@]}"
    do
        # shellcheck disable=SC2164
        cd /opt/package/$value
        sh 72crm.sh start
        sleep 10s;
    done
    echo "Kuberix CRM startup"
    tail -f /dev/null
    ;;
stop)
    # shellcheck disable=SC2164
    cd /opt
    # shellcheck disable=SC2039
    for value in "${server_names[@]}"
    do
        sh package/$value/72crm.sh stop;
        sleep 0.5s;
    done
    ;;
restart)
    # shellcheck disable=SC2164
    cd /opt
    # shellcheck disable=SC2039
    for value in "${server_names[@]}"
    do
        sh package/$value/72crm.sh stop;
        sleep 0.5s;
    done

    for value in "${server_names[@]}"
    do
        # shellcheck disable=SC2164
        cd /opt/package/$value
        sh 72crm.sh start
        sleep 10s;
    done
    echo "Kuberix CRM startup"
    tail -f /dev/null
    ;;
*)
    echo "start|stop|restart"
    ;;
esac
exit $?
