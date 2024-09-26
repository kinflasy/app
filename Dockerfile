# Usar a imagem oficial do Ubuntu como base
FROM openjdk:17-jdk-alpine

# Criar diretório de trabalho
WORKDIR /app

COPY .mvn/ .mvn
COPY pom.xml .

RUN apk add maven
RUN mvn dependency:go-offline -B

COPY src ./src

CMD ["mvn", "spring-boot:run"]
