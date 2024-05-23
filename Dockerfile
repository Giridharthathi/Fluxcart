# Use Alpine Linux as base image
FROM alpine:latest
#

#ENV MYSQL_ROOT_PASSWORD=root@123
#ENV MYSQL_DATABASE=BiteSpeed
#ENV MYSQL_USER=root
#ENV MYSQL_PASSWORD=root@123
# Install OpenJDK 17
RUN apk add --no-cache openjdk17

# Install MySQL
#RUN apk add --no-cache mysql mysql-client

# Set up MySQL data directory
#RUN mkdir -p /var/lib/mysql
ADD target/fluxcart-bitespeed.jar fluxcart-bitespeed.jar

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-17-jdk-amd64

ENV PATH="$PATH:$JAVA_HOME/bin"
# Expose MySQL port
#EXPOSE 3306 8080
EXPOSE  8080
# Start MySQL service
#CMD ["mysqld", "--user=mysql", "--datadir=/var/lib/mysql", "--port=3306"]

ENTRYPOINT ["java","-jar","/fluxcart-bitespeed.jar"]
