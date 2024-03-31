#/bin/bash

export CONFLUENT_HOME=/mnt/c/workspace/confluent-7.5.0

$CONFLUENT_HOME/bin/schema-registry-start $CONFLUENT_HOME/etc/schema-registry/schema-registry.properties