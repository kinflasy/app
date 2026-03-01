#!/bin/sh

# Configura a URL global para o CLI do FGA usar
export FGA_API_URL="http://openfga:${OPENFGA_HTTP_PORT}"

# Esperar o servidor responder
until curl -s "$FGA_API_URL/healthz" | grep -q "SERVING"; do
  # response = curl -s "$FGA_API_URL/healthz"
  echo $(curl -s \"$FGA_API_URL/healthz\")
  echo "Aguardando OpenFGA em $FGA_API_URL/healthz..."
  sleep 2
done

# echo "Criando Store..."
# STORE_OUTPUT=$(fga store create --name "KinflasyStore")
# STORE_ID=$(echo $STORE_OUTPUT | jq -r '.id')

echo "Configurando OpenFGA..."

# 1. Criar a Store e capturar a ID
STORE_ID=$(fga store create --name "KinflasyStore" --api-url ${FGA_API_URL} | jq -r '.store.id')
echo "STORE_ID=$STORE_ID" > /shared/.env.fga

# 2. Escrever o Modelo e capturar a ID do Modelo de Autorização
# Assumindo que o arquivo model.fga está mapeado no container
MODEL_ID=$(fga model write --store-id=$STORE_ID --file /mnt/model.fga --api-url ${FGA_API_URL} | jq -r '.authorization_model_id')

# echo "OPENFGA_AUTHORIZATION_MODEL_ID=$MODEL_ID" >> /shared/.env.fga
# echo "OPENFGA_STORE_ID=$STORE_ID" >> /shared/.env.fga

# echo "Configuração finalizada com sucesso!"

echo "OPENFGA_STORE_ID=$STORE_ID" > /shared/.env.fga
echo "OPENFGA_AUTHORIZATION_MODEL_ID=$MODEL_ID" >> /shared/.env.fga

echo "Configuração finalizada: Store $STORE_ID e Model $MODEL_ID"