FROM openjdk:8-jre-alpine
ADD build/libs/fint-consumer-administrasjon-kodeverk-*.jar /data/app.jar
CMD ["java", "-jar", "/data/app.jar"]