#!/bin/sh

# Configura a URL global para o CLI do FGA usar
export FGA_API_URL="http://${OPENFGA_HTTP_HOST}:${OPENFGA_HTTP_PORT}"

# Esperar o servidor responder
until curl -s "$FGA_API_URL/healthz" | grep -q "SERVING"; do
  echo "Aguardando OpenFGA em $FGA_API_URL/healthz..."
  sleep 2
done

echo "Verificando Stores existentes..."
EXISTING=$(fga store list --api-url ${FGA_API_URL} | jq -r '.stores[0].id' 2>/dev/null)

if [ ! -z "$EXISTING" ] && [ "$EXISTING" != "null" ]; then
  echo "Store já existe: $EXISTING"
  STORE_ID=$EXISTING
else
  echo "Criando Store..."
  STORE_ID=$(fga store create --name "KinflasyStore" --api-url ${FGA_API_URL} | jq -r '.store.id')
fi

echo "OPENFGA_STORE_ID=$STORE_ID" > /shared/.env.fga
echo "Store configurada: $STORE_ID"