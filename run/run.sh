
# Gerar artefato .jar
mvn clean install

# Construir e subir os containers
docker-compose up --build
