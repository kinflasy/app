# Usar a imagem oficial do Ubuntu como base
FROM ubuntu:latest

# Configurar variáveis de ambiente
ENV APP_NAME=${APP_NAME}
ENV APP_VERSION=${APP_VERSION}
ENV JAR_FILE=target/${APP_NAME}.jar

# Definir o fuso horário para Brasília (BRT)
ENV TZ=America/Sao_Paulo

# Configurar o fuso horário no sistema
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

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

# Definir credenciais do Git
# ENV GIT_USER=${GIT_USER}
# ENV GIT_EMAIL=${GIT_EMAIL}
# RUN git config --global user.name "${GIT_USER}" && git config --global user.email "${GIT_EMAIL}"

# Copiar o projeto
COPY . .

# Definir as permissões necessárias
RUN chown -R dev:dev ..

# Switch para o usuário "dev"
USER dev

# Expor a porta em que a aplicação vai rodar
EXPOSE 8080

# Definir Bash como o shell padrão no container
ENTRYPOINT ["/bin/bash"]