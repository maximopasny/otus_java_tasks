#!/usr/bin/env bash

#MEMORY="-Xms64m -Xmx64m -XX:MaxMetaspaceSize=24m"
#REMOTE_DEBUG="-agentlib:jdwp=transport=dt_socket,address=14025,server=y,suspend=n"
#GC_LOG=" -verbose:hw_04 -Xloggc:./logs/gc_%p.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M"
#JMX="-Dcom.sun.management.jmxremote.port=15025 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
#DUMP="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./dumps/"
#
#### Serial (DefNew) | Serial Mark Sweep Compact
## GC="-XX:+UseSerialGC"
#
#### Parallel Scavenge (PSYoungGen) | Serial Mark Sweep Compact (PSOldGen)
## GC="-XX:+UseParallelGC"
#
#### Parallel Scavenge (PSYoungGen) | Parallel Mark Sweep Compact (ParOldGen)
## GC="-XX:+UseParallelOldGC"
#
#### Parallel (ParNew) | Serial Mark Sweep Compact
## GC="-XX:+UseParNewGC"
#
#### Serial (DefNew) | Concurrent Mark Sweep
## GC="-XX:-UseParNewGC -XX:+UseConcMarkSweepGC"
#
#### Parallel (ParNew) | Concurrent Mark Sweep
## GC="-XX:+UseParNewGC -XX:+UseConcMarkSweepGC"
#
#### Garbage First (G1)
#GC="-XX:+UseG1GC"
#
#java ${REMOTE_DEBUG} ${MEMORY} ${GC} ${GC_LOG} ${JMX} ${DUMP} -jar target/task-05-test-framework-1.0-SNAPSHOT-jar-with-dependencies.jar > target/jvm.out

mvn clean install
echo 'start1'
java -jar target/task-05-gc-1.0-SNAPSHOT-jar-with-dependencies.jar  -verbose:gc -agentlib:jdwp=transport=dt_socket,address=14000,server=y,suspend=n -Xms1024m -Xmx102m -XX:MaxMetaspaceSize=1024m -XX:+UseSerialGC > UseSerialGC.txt
echo 'start2'
java -jar target/task-05-gc-1.0-SNAPSHOT-jar-with-dependencies.jar  -verbose:gc -agentlib:jdwp=transport=dt_socket,address=14000,server=y,suspend=n -Xms1024m -Xmx1024m -XX:MaxMetaspaceSize=1024m -XX:+UseParallelGC > UseParallelGC.txt
echo 'start3'
java -jar target/task-05-gc-1.0-SNAPSHOT-jar-with-dependencies.jar  -verbose:gc -agentlib:jdwp=transport=dt_socket,address=14000,server=y,suspend=n -Xms1024m -Xmx1024m -XX:MaxMetaspaceSize=1024m -XX:+UseParallelOldGC > UseParallelOldGC.txt
echo 'start4'
java -jar target/task-05-gc-1.0-SNAPSHOT-jar-with-dependencies.jar  -verbose:gc -agentlib:jdwp=transport=dt_socket,address=14000,server=y,suspend=n -Xms1024m -Xmx1024m -XX:MaxMetaspaceSize=1024m -XX:+UseG1GC > UseG1GC.txt
echo 'start5'
java -jar target/task-05-gc-1.0-SNAPSHOT-jar-with-dependencies.jar  -verbose:gc -agentlib:jdwp=transport=dt_socket,address=14000,server=y,suspend=n -Xms1024m -Xmx1024m -XX:MaxMetaspaceSize=1024m -XX:+UseConcMarkSweepGC > UseConcMarkSweepGC.txt
echo 'done'
