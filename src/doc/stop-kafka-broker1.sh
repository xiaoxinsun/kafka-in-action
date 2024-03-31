
#/bin/bash

#mac
#export KAF_HOME=/Users/billsun/workspace/kafka_2.12-2.8.2

#wsl
export KAF_HOME=/mnt/c/workspace/kafka_2.13-2.8.2

$KAF_HOME/bin/kafka-server-stop.sh server1.properties
