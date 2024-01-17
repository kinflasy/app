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

# Instalar o Zsh
RUN apt-get install -y zsh

# Copiar as configurações padrão do Zsh e Dev Container para o usuário dev
COPY /config/* /home/dev/
COPY .devcontainer.json /home/dev/

# Copiar o projeto
COPY . .

# Baixar as dependências do projeto e gerar o pacote JAR
# RUN mvn clean install

# Expor a porta em que a aplicação vai rodar
EXPOSE 8080

# Definir as permissões necessárias
RUN chown -R dev:dev ..

# Switch para o usuário "dev"
USER dev

# Definir Zsh como o shell padrão no contêiner
ENTRYPOINT ["/usr/bin/zsh"]