# Sistema de Reservas de Salas - Microserviços

## Arquitetura

Este é um sistema de reservas de salas implementado em microserviços com:

### Backend
- **API Gateway**: Gateway que gerencia as rotas entre microserviços (Spring Boot - Porta 8080)
- **Usuario Service**: Microserviço de gestão de usuários (Spring Boot - Porta 8081)
- **Sala Service**: Microserviço de gestão de salas (Spring Boot - Porta 8082)
- **Reserva Service**: Microserviço de gestão de reservas (Spring Boot - Porta 8083)

### Frontend
- **Frontend Service**: Interface web estática servida via Nginx (Porta 3000)
  - HTML/CSS/JavaScript moderno
  - Comunicação direta com os microserviços via proxy
  - Interface responsiva com Bootstrap

### Infraestrutura
- **PostgreSQL**: Banco de dados (Porta 5432)
- **Apache Kafka**: Sistema de mensageria para comunicação entre microserviços
- **Nginx**: Servidor web para o frontend com proxy reverso

## Estrutura do Projeto

```
reservas-salas-microservices/
├── api-gateway/           # API Gateway (Spring Boot)
├── usuario-service/       # Microserviço de Usuários
├── sala-service/         # Microserviço de Salas
├── reserva-service/      # Microserviço de Reservas
├── frontend-service/     # Frontend Estático
│   ├── public/          # Arquivos HTML/CSS/JS
│   ├── Dockerfile       # Container Nginx
│   └── nginx.conf       # Configuração do proxy
└── docker-compose.yml    # Orquestração dos containers
```

## Migração do Frontend

### ✅ Concluído - Front-end Separado do API Gateway

O front-end foi migrado com sucesso do API Gateway para um serviço dedicado:

**Antes**: 
- Frontend como template Thymeleaf dentro do API Gateway
- Localização: `api-gateway/src/main/resources/templates/reservas.html`

**Depois**:
- Frontend como aplicação web estática independente
- Localização: `frontend-service/public/`
- Tecnologia: HTML + CSS + JavaScript moderno
- Servidor: Nginx com proxy reverso

### Benefícios da Migração

1. **Separação de Responsabilidades**: Frontend e backend completamente desacoplados
2. **Escalabilidade**: Frontend pode ser escalado independentemente
3. **Tecnologia Moderna**: Uso de JavaScript moderno com Fetch API
4. **Performance**: Nginx otimizado para servir conteúdo estático
5. **Flexibilidade**: Facilita futuras migrações para frameworks como React, Vue, etc.

## Como Executar

### Pré-requisitos
- Docker
- Docker Compose

### Execução

1. **Clone o repositório**
```bash
git clone <repo-url>
cd reservas-salas-microservices
```

2. **Execute com Docker Compose**
```bash
docker-compose up --build
```

3. **Acesse a aplicação**
- **Frontend**: http://localhost:3000
- **API Gateway**: http://localhost:8080
- **Banco de dados**: localhost:5432

### Acessos Diretos aos Microserviços

- **Usuários**: http://localhost:8081/api/usuarios
- **Salas**: http://localhost:8082/api/salas  
- **Reservas**: http://localhost:8083/api/reservas

## Funcionalidades

### Interface Web (localhost:3000)
- ✅ Listar reservas, salas e usuários
- ✅ Criar novas reservas
- ✅ Criar novos usuários
- ✅ Criar novas salas
- ✅ Editar salas e usuários
- ✅ Deletar reservas, salas e usuários
- ✅ Visualização de status de disponibilidade das salas

### APIs REST

#### Usuários (Porta 8081)
- `GET /api/usuarios` - Listar usuários
- `POST /api/usuarios` - Criar usuário
- `PUT /api/usuarios/{id}` - Atualizar usuário
- `DELETE /api/usuarios/{id}` - Deletar usuário

#### Salas (Porta 8082)
- `GET /api/salas` - Listar salas
- `POST /api/salas` - Criar sala
- `PUT /api/salas/{id}` - Atualizar sala
- `DELETE /api/salas/{id}` - Deletar sala

#### Reservas (Porta 8083)
- `GET /api/reservas` - Listar reservas
- `POST /api/reservas` - Criar reserva
- `DELETE /api/reservas/{id}` - Deletar reserva

## Tecnologias Utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Cloud Gateway**
- **PostgreSQL**
- **Apache Kafka**
- **Docker**

### Frontend
- **HTML5**
- **CSS3**
- **JavaScript ES6+**
- **Bootstrap 5**
- **Bootstrap Icons**
- **Nginx**

## Próximos Passos

### Melhorias Sugeridas
1. **Autenticação e Autorização**: Implementar JWT/OAuth2
2. **Monitoring**: Adicionar Prometheus + Grafana
3. **Logs Centralizados**: ELK Stack ou Fluentd
4. **Testes**: Testes unitários e de integração
5. **CI/CD**: Pipeline de deploy automatizado
6. **Framework Frontend**: Migração para React/Vue.js para uma SPA completa

### Estrutura de Produção
- Load Balancer (nginx/HAProxy)
- Service Discovery (Eureka/Consul)
- Configuration Server (Spring Cloud Config)
- Circuit Breaker (Hystrix/Resilience4j)
- API Rate Limiting
- CORS Configuration
- HTTPS/SSL

## Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes. 