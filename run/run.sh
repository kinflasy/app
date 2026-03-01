
# Gerar artefato .jar
# Construir e subir os containers
mvn clean install # && docker-compose up --build
docker compose up -d --build # && mvn spring-boot:run -Dspring-boot.run.profiles=dev
# mvn clean spring-boot:run -Dspring-boot.run.profiles=dev

# docker stop db
