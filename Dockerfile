FROM openjdk:11
WORKDIR /tmp/out/artifacts/SecondoProgettoASD_jar
ENTRYPOINT ["java", "-jar", "SecondoProgettoASD.jar"]