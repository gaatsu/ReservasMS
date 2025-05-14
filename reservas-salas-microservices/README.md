# Sistema de Reserva de Salas

Sistema de reserva de salas desenvolvido com arquitetura de microserviços usando Spring Boot.

## Arquitetura

O sistema é composto pelos seguintes microserviços:

- **api-gateway**: Gateway de API para roteamento e autenticação
- **usuario-service**: Gerenciamento de usuários
- **sala-service**: Gerenciamento de salas
- **reserva-service**: Gerenciamento de reservas
- **eureka-server**: Servidor de descoberta de serviços

### Estrutura dos Microserviços

Cada microserviço segue a arquitetura hexagonal (ports and adapters) com as seguintes camadas:

- **Domain**: Contém as entidades e regras de negócio
  - `model/`: Entidades do domínio
  - `repository/`: Interfaces dos repositórios
  - `service/`: Serviços do domínio
  - `event/`: Eventos do domínio

- **Application**: Contém a lógica de aplicação
  - `service/`: Serviços de aplicação

- **Interfaces**: Contém as interfaces de entrada
  - `rest/`: Controladores REST

- **Infrastructure**: Contém implementações técnicas
  - `persistence/`: Implementações de persistência
  - `event/`: Implementações de eventos

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.3
- Spring Cloud
- Spring Security
- Spring Data JPA
- PostgreSQL
- Maven
- Docker
- JWT para autenticação

## Pré-requisitos

- Java 17 ou superior
- Maven
- Docker e Docker Compose
- PostgreSQL

## Configuração do Ambiente

1. Clone o repositório:
```bash
git clone [URL_DO_REPOSITÓRIO]
cd reservas-salas-microservices
```

2. Configure as variáveis de ambiente:
   - Copie o arquivo `.env.example` para `.env`
   - Ajuste as variáveis conforme necessário

3. Inicie os serviços com Docker Compose:
```bash
docker-compose up -d
```

## Executando o Projeto

1. Inicie o Eureka Server:
```bash
cd eureka-server
./mvnw spring-boot:run
```

2. Inicie o API Gateway:
```bash
cd api-gateway
./mvnw spring-boot:run
```

3. Inicie os microserviços:
```bash
# Em terminais separados
cd usuario-service
./mvnw spring-boot:run

cd sala-service
./mvnw spring-boot:run

cd reserva-service
./mvnw spring-boot:run
```

## Endpoints da API

### Usuário Service
- `POST /api/usuarios`: Criar usuário
- `GET /api/usuarios`: Listar usuários
- `GET /api/usuarios/{id}`: Buscar usuário por ID
- `PUT /api/usuarios/{id}`: Atualizar usuário
- `DELETE /api/usuarios/{id}`: Deletar usuário
- `GET /api/usuarios/ativos`: Listar usuários ativos
- `GET /api/usuarios/matricula/{matricula}`: Buscar por matrícula

### Sala Service
- `POST /api/salas`: Criar sala
- `GET /api/salas`: Listar salas
- `GET /api/salas/{id}`: Buscar sala por ID
- `PUT /api/salas/{id}`: Atualizar sala
- `DELETE /api/salas/{id}`: Deletar sala
- `GET /api/salas/ativas`: Listar salas ativas

### Reserva Service
- `POST /api/reservas`: Criar reserva
- `GET /api/reservas`: Listar reservas
- `GET /api/reservas/{id}`: Buscar reserva por ID
- `PUT /api/reservas/{id}`: Atualizar reserva
- `DELETE /api/reservas/{id}`: Deletar reserva
- `GET /api/reservas/usuario/{usuarioId}`: Listar reservas por usuário
- `GET /api/reservas/sala/{salaId}`: Listar reservas por sala

## Eventos do Domínio

O sistema utiliza eventos do domínio para comunicação entre os microserviços:

### Usuário Service
- `UsuarioCriadoEvent`
- `UsuarioAtualizadoEvent`
- `UsuarioDeletadoEvent`

### Sala Service
- `SalaCriadaEvent`
- `SalaAtualizadaEvent`
- `SalaDeletadaEvent`

## Segurança

O sistema utiliza JWT para autenticação. Para acessar os endpoints protegidos:

1. Faça login para obter o token JWT
2. Inclua o token no header das requisições:
```
Authorization: Bearer [seu_token_jwt]
```

## Monitoramento

- Eureka Dashboard: `http://localhost:8761`
- API Gateway: `http://localhost:8080`

## Desenvolvimento

### Estrutura de Diretórios
```
reservas-salas-microservices/
├── api-gateway/
├── usuario-service/
├── sala-service/
├── reserva-service/
└── eureka-server/
```

### Comandos Úteis

```bash
# Limpar e instalar dependências
./mvnw clean install

# Executar testes
./mvnw test

# Executar com perfil específico
./mvnw spring-boot:run -Dspring.profiles.active=dev
```

## Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes. 