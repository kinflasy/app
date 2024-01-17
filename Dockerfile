# Usar a imagem oficial do Ubuntu como base
FROM ubuntu:latest

# Configurar variáveis de ambiente
ENV APP_NAME=${APP_NAME}
ENV APP_VERSION=${APP_VERSION}
ENV JAR_FILE=target/${APP_NAME}.jar

# Adicionar um novo usuário chamado "dev"
RUN useradd -ms /bin/bash dev

# Criar diretório de trabalho
WORKDIR /home/dev/api

# Instalar o Maven
RUN apt-get update
RUN apt-get install -y openjdk-17-jdk
RUN apt-get install -y maven
RUN apt-get install -y git

# Definir JAVA_HOME
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

# Copiar o projeto
COPY . .

# Baixar as dependências do projeto e gerar o pacote JAR
# RUN mvn clean install

# Expor a porta em que a aplicação vai rodar
EXPOSE 8080

# Definir as permissões necessárias
RUN chown -R dev:dev ..

# Ativar repositório do Git
RUN git config --global --add safe.directory /home/dev/api

# Switch para o usuário "dev"
USER dev

# Definir Bash como o shell padrão no container
ENTRYPOINT ["/bin/bash"]