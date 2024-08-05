FROM openjdk:19-alpine
LABEL authors="José Alisson"

# Defina o argumento para a versão do Maven
ARG MAVEN_VERSION=3.8.6

# Instale dependências necessárias e o Maven
RUN apk update && \
    apk add --no-cache wget tar bash && \
    wget https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    tar xzf apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-${MAVEN_VERSION} /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn && \
    rm apache-maven-${MAVEN_VERSION}-bin.tar.gz

# Defina variáveis de ambiente para o Maven
ENV MAVEN_HOME=/opt/maven
ENV PATH=${MAVEN_HOME}/bin:${PATH}

ARG URL
ARG USERNAME
ARG PASSWORD
ARG API_MAIN

ENV DATABASE_URL=${URL}
ENV DATABASE_USERNAME=${USERNAME}
ENV DATABASE_PASSWORD=${PASSWORD}
ENV API_MAIN=${API_MAIN}

EXPOSE 8080

WORKDIR /main

COPY . .

RUN mvn clean package -DskipTests

CMD ["java", "-jar", "target/joliny-pedidos-0.0.1-SNAPSHOT.jar"]
