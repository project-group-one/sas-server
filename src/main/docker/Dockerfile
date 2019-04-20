FROM openjdk:8-jdk-alpine
VOLUME /tmp
VOLUME /heap
        VOLUME /data
ADD sas.jar app.jar
ADD ./static/upload/ ./static/upload/
RUN sh -c 'touch /app.jar'
ENV JVM_OPTS="-Xmx2048m"
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "exec java $JVM_OPTS $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -XX:NativeMemoryTracking=summary -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/heap -verbose:gc -Xloggc:gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -jar /app.jar" ]