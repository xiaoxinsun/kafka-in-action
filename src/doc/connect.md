
export KAF_HOME=/mnt/c/workspace/kafka_2.13-2.8.2
cd $KAF_HOME/bin
./connect-standalone.sh ../config/connect-standalone.properties /mnt/c/users/xiaox/IdeaProjects/kafka-in-action/src/doc/alert-source.properties /mnt/c/users/xiaox/IdeaProjects/kafka-in-action/src/doc/alert-sink.properties
