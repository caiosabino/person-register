# person-register

API Spring Boot para cadastro de pessoas e enderecos com persistencia em PostgreSQL e publicacao de eventos no RabbitMQ.

## Stack
- Java 21
- Spring Boot 3.4
- PostgreSQL 16
- RabbitMQ 3.13 (management)
- Docker Compose

## Perfis de execucao
- `local` (padrao): usa `localhost` para Postgres/RabbitMQ.
- `docker`: usa hostnames da rede compose (`postgres` e `rabbitmq`).

Arquivos:
- `src/main/resources/application.yml`
- `src/main/resources/application-local.yml`
- `src/main/resources/application-docker.yml`

## Subir com Docker
```bash
docker compose up -d --build
```

Servicos:
- API: `http://localhost:8080`
- RabbitMQ Management: `http://localhost:15672`
- Postgres: `localhost:5432`

## Acessar RabbitMQ localmente
Com os containers `rabbitmq` e `app` no ar:

- Management UI: `http://localhost:15672`
- Usuario: `guest`
- Senha: `guest`
- Vhost: `/`

Portas locais:
- AMQP (aplicacao): `localhost:5672`
- Management UI: `localhost:15672`

## Rodar local
Suba apenas dependencias:
```bash
docker compose up -d postgres rabbitmq
```

Rode a app no IntelliJ ou:
```bash
./gradlew bootRun
```

## Variaveis de ambiente
Principais variaveis:
- `DB_HOST` (local: `localhost`, docker: `postgres`)
- `DB_PORT` (default `5432`)
- `DB_NAME` (default `person_register`)
- `DB_USER` (default `postgres`)
- `DB_PASSWORD` (default `postgres`)
- `RABBITMQ_HOST` (local: `localhost`, docker: `rabbitmq`)
- `RABBITMQ_PORT` (default `5672`)
- `RABBITMQ_USER` (default `guest`)
- `RABBITMQ_PASSWORD` (default `guest`)
- `RABBITMQ_LISTENER_PERSON_REGISTER_ENABLED` (default `false` no perfil docker)

## Endpoints
Base URL: `http://localhost:8080/api/person`

- `GET /api/person?page=0&size=10`
- `GET /api/person/id/{id}`
- `GET /api/person/document/{document}`
- `POST /api/person`
- `PUT /api/person`
- `DELETE /api/person/document/{document}`

## Exemplo de POST (curl)
```bash
curl --location 'http://localhost:8080/api/person' \
  --header 'Content-Type: application/json' \
  --data-raw '{
    "person": {
      "document": 12345678901,
      "name": "Caio Sabino",
      "documentType": "CPF",
      "email": "caio.sabino@example.com",
      "phone": "11999999999",
      "birthDate": "1995-08-20"
    },
    "address": {
      "street": "Rua das Flores",
      "neighborhood": "Centro",
      "city": "Sao Paulo",
      "number": "100",
      "complement": "Apto 101"
    }
  }'
```

## Collection Postman
Collection pronta para importacao:
- `src/main/resources/collection/person-register.postman_collection.json`

## Limpar dados do banco (docker)
```bash
docker compose down -v
docker compose up -d
```
