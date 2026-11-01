#!/bin/sh
set -e

SECRETS_DIR=/app/secrets
mkdir -p "$SECRETS_DIR"

if [ ! -f "$SECRETS_DIR/app.key" ] || [ ! -f "$SECRETS_DIR/app.pub" ]; then
    echo "Gerando par de chaves RSA para JWT (primeira execucao)..."
    openssl genpkey -algorithm RSA -pkeyopt rsa_keygen_bits:2048 -out "$SECRETS_DIR/app.key"
    openssl rsa -pubout -in "$SECRETS_DIR/app.key" -out "$SECRETS_DIR/app.pub"
fi

if [ -z "$APP_ENCRYPTION_KEY" ]; then
    if [ ! -f "$SECRETS_DIR/encryption.key" ]; then
        echo "Gerando chave de criptografia das credenciais de hosts Docker (primeira execucao)..."
        openssl rand -base64 32 > "$SECRETS_DIR/encryption.key"
    fi
    APP_ENCRYPTION_KEY=$(cat "$SECRETS_DIR/encryption.key")
fi

export APP_ENCRYPTION_KEY

exec java -jar app.jar \
    --jwt.private.key="file:$SECRETS_DIR/app.key" \
    --jwt.public.key="file:$SECRETS_DIR/app.pub"
