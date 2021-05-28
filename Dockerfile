FROM openjdk:11
COPY . /tmp
WORKDIR /tmp/out/production/SecondoProgettoASD
ENTRYPOINT ["java","Time"]