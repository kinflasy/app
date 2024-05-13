# Usar a imagem oficial do Ubuntu como base
FROM maven:3.9.6-eclipse-temurin-17

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
RUN apt-get install -y git

# Copiar o projeto
COPY . .

# Definir as permissões necessárias
RUN chown -R dev:dev ..

# Switch para o usuário "dev"
USER dev

# Expor a porta em que a aplicação vai rodar
EXPOSE 8080
EXPOSE 35729

# Definir Bash como o shell padrão no container
ENTRYPOINT ["/bin/bash"]
