# 🚀 Encurtador de URL

## plicação de encurtamento de URLs desenvolvida com foco em **performance, escalabilidade e arquitetura limpa**, utilizando **Spring Boot**, **Redis** e **Cassandra**.

---

## 🧱 Arquitetura

O projeto segue o padrão **Hexagonal Architecture (Ports & Adapters)**, garantindo:

- 🔌 Baixo acoplamento entre camadas
- 🧪 Facilidade de testes
- 🔄 Flexibilidade para troca de tecnologias

### 📂 Estrutura
```
application
     ├── usecase # Regras de negócio
     ├── ports
     │ ├── input # Entradas (interfaces)
     │ └── output # Saídas (interfaces)
     ├── model # Modelos de domínio
   adapter
     ├── input # Controllers (HTTP)
     └── output # Integrações (DB, Redis, etc)
```

---

## ⚙️ Tecnologias

- ☕ Java 17
- 🌱 Spring Boot
- 🧠 Redis (cache)
- 🗄️ Cassandra (armazenamento distribuído)
- 🧪 JUnit + Mockito (testes)

---

## 🔥 Funcionalidades

- ✅ Encurtar URLs
- 🔁 Redirecionamento via código curto
- ⚡ Cache com Redis para alta performance
- 💾 Persistência com Cassandra

---

## 📡 Endpoints

### ➕ Criar URL encurtada
```POST /url?originalUrl=https://google.com```
**Response:**
```abc123```

---

### 🔀 Redirecionar URL
```GET /url/{code}```

**Response:**
- `302 FOUND`
- Redirect para URL original

---

## 🧠 Como funciona

1. URL original é recebida
2. Um código único é gerado (Hashids)
3. A URL é salva no Cassandra
4. O código é armazenado no Redis (cache)
5. No acesso:
    - Busca primeiro no Redis ⚡
    - Se não encontrar, busca no Cassandra 🗄️

---

## 🐳 Rodando com Docker

### Redis

```bash
docker run -d -p 6379:6379 redis
```
### Cassandra
```bash
docker run -d -p 9042:9042 cassandra
```
### API
```bash
docker build -t encurta-url .
docker run -p 8080:8080 encurta-url
```
### ⚙️ Configuração
Exemplo ```application.properties```
```properties
spring.redis.host=localhost
spring.redis.port=6379

spring.cassandra.contact-points=localhost
spring.cassandra.port=9042
spring.cassandra.keyspace-name=encurtaurl
```

## 🧪 Testes
### Rodar testes: ```mvn test```

Cobertura inclui:
 - ✅ UseCases
 - ✅ Controllers
 - ✅ Adapters

# 👨‍💻 Autor
## Desenvolvido por Bruno Euflausino