# --- Etapa 1: Build (Compilação) ---
FROM maven:3.9.15-amazoncorretto-21-alpine AS build

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
FROM sapmachine:21-jre-alpine-3.23

# Instalar dos2unix para corrigir finais de linha do Windows
RUN apk add --no-cache dos2unix

WORKDIR /app

# Copiar apenas o .jar gerado na etapa anterior
COPY --from=build /build/execution/target/*.jar app.jar

# Copiar o arquivo de modelo FGA
COPY model.fga /app/model.fga

# Copiar o script de entrada
COPY /run/entrypoint.sh /entrypoint.sh

# Instalar dependências e baixar FGA CLI
RUN apk add --no-cache curl && \
    curl -sSfL https://github.com/openfga/cli/releases/download/v0.7.12/fga_0.7.12_linux_amd64.tar.gz | tar -xz -C /usr/local/bin fga && \
    \
    # Transformar o modelo FGA para JSON usando o CLI e salvar como model.json \
    fga model transform --file /app/model.fga --input-format fga --output-format json > /app/model.json && \
    \
    # Remover caracteres \r (CRLF), dar permissão e limpar o utilitário \
    dos2unix /entrypoint.sh && \
    chmod +x /entrypoint.sh

# Chamar o script em vez do Java diretamente
ENTRYPOINT ["/bin/sh", "/entrypoint.sh"]