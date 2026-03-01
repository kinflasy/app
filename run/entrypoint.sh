#!/bin/sh

# Se o arquivo de IDs do FGA existir, exporta as variáveis para o ambiente
if [ -f "$FGA_ENV_FILE" ]; then
  echo "Carregando configurações do OpenFGA de $FGA_ENV_FILE..."
  export $(grep -v '^#' "$FGA_ENV_FILE" | xargs)
fi

# Inicia a aplicação Java
exec java -jar /app/app.jar