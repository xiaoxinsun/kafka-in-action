#/bin/bash

export KAF_HOME=/Users/billsun/workspace/kafka_2.12-2.8.2

$KAF_HOME/bin/kafka-server-start.sh -daemon $KAF_HOME/config/server1.properties
