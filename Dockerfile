FROM circleci/openjdk:8-jdk
COPY ./build/libs/*.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8087
CMD exec java $JAVA_OPTS -Dserver.port=$PORT -jar tempvs-profile.jar
