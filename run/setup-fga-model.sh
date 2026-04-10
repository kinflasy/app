#!/bin/sh

# Carregar STORE_ID do arquivo
if [ -f /shared/.env.fga ]; then
  export $(cat /shared/.env.fga | xargs)
  echo "STORE_ID carregado: $OPENFGA_STORE_ID"
else
  echo "Erro: /shared/.env.fga não encontrado!"
  exit 1
fi

# Configurar a URL global para o CLI do FGA usar
export FGA_API_URL="http://${OPENFGA_HTTP_HOST}:${OPENFGA_HTTP_PORT}"

# Esperar o servidor responder
until curl -s "$FGA_API_URL/healthz" | grep -q "SERVING"; do
  # response = curl -s "$FGA_API_URL/healthz"
  echo $(curl -s \"$FGA_API_URL/healthz\")
  echo "Aguardando OpenFGA em $FGA_API_URL/healthz..."
  sleep 2
done

echo "Criando Model..."
MODEL_ID=$(fga model write --store-id=$OPENFGA_STORE_ID --file /mnt/model.fga --api-url ${FGA_API_URL} | jq -r '.authorization_model_id')
echo "OPENFGA_AUTHORIZATION_MODEL_ID=$MODEL_ID" >> /shared/.env.fga
echo "Model configurado: $MODEL_ID"