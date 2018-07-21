FROM java:8-jre-alpine
VOLUME /tmp

ADD *.jar app.jar
EXPOSE 18007

ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar