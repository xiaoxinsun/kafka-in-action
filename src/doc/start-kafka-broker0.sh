#/bin/bash

#mac
#export KAF_HOME=/Users/billsun/workspace/kafka_2.12-2.8.2

#wsl
export KAF_HOME=/mnt/c/workspace/kafka_2.13-2.8.2
export LOG_DIR=/mnt/c/Users/xiaox/IdeaProjects/kafka-in-action/logs/server0

$KAF_HOME/bin/kafka-server-start.sh -daemon server0.properties
