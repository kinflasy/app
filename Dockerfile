# --- Etapa 1: Build (Compilação) ---
FROM maven:3.9.12-eclipse-temurin-21-alpine AS build

# Define o diretório de trabalho para o build
WORKDIR /build

# Copia o arquivo de definição de módulos (pom.xml pai e filhos)
# Copiamos os poms primeiro para aproveitar o cache de camadas do Docker
COPY pom.xml .
COPY execution/pom.xml execution/
COPY libs/ libs/
COPY apis/ apis/

# Baixa as dependências (sem rodar os testes ainda) para cache
RUN mvn dependency:go-offline -B

# Copia o código fonte de todos os módulos
COPY . .

# Compila o projeto e gera o .jar no módulo de execução
# Pula os testes para acelerar o build do container
RUN mvn clean package -DskipTests

# --- Etapa 2: Runtime (Execução) ---
FROM eclipse-temurin:21-alpine-3.23

# Instalamos dos2unix para corrigir finais de linha do Windows
RUN apk add --no-cache dos2unix

WORKDIR /app

# Copia apenas o .jar gerado na etapa anterior
COPY --from=build /build/execution/target/*.jar app.jar

# Copia o script de entrada
COPY run/entrypoint.sh /entrypoint.sh

# Remove caracteres \r (CRLF), dá permissão e limpa o utilitário
RUN dos2unix /entrypoint.sh && \
    chmod +x /entrypoint.sh

# O Entrypoint agora chama o script em vez do java diretamente
ENTRYPOINT ["/bin/sh", "/entrypoint.sh"]