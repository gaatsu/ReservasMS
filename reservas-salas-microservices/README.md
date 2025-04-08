# Sistema de Reserva de Salas - Arquitetura de Microserviços

Este projeto implementa um sistema de reserva de salas utilizando uma arquitetura de microserviços. O sistema permite gerenciar salas, usuários e suas reservas de forma distribuída.

## Arquitetura

O sistema é composto pelos seguintes microserviços:

1. **API Gateway (Porta 8080)**
   - Ponto de entrada único para todas as requisições
   - Interface web para interação com o usuário
   - Roteamento de requisições para os microserviços apropriados
   - Configuração:
     - Servidor na porta 8080
     - Templates Thymeleaf habilitados

2. **Serviço de Usuários (Porta 8081)**
   - Gerenciamento de usuários
   - Endpoints:
     - GET /api/usuarios - Lista todos os usuários
     - POST /api/usuarios - Cria um novo usuário
     - PUT /api/usuarios/{id} - Atualiza um usuário
     - DELETE /api/usuarios/{id} - Remove um usuário
   - Configuração:
     - Banco de dados PostgreSQL
     - Integração com Kafka para eventos
     - JPA com atualização automática do schema

3. **Serviço de Salas (Porta 8082)**
   - Gerenciamento de salas
   - Endpoints:
     - GET /api/salas - Lista todas as salas
     - POST /api/salas - Cria uma nova sala
     - PUT /api/salas/{id} - Atualiza uma sala
     - DELETE /api/salas/{id} - Remove uma sala
   - Configuração:
     - Banco de dados PostgreSQL
     - Integração com Kafka para eventos
     - JPA com atualização automática do schema

4. **Serviço de Reservas (Porta 8083)**
   - Gerenciamento de reservas
   - Endpoints:
     - GET /api/reservas - Lista todas as reservas
     - POST /api/reservas - Cria uma nova reserva
     - DELETE /api/reservas/{id} - Remove uma reserva
   - Configuração:
     - Banco de dados PostgreSQL
     - Integração com Kafka para eventos
     - JPA com atualização automática do schema

## Tecnologias Utilizadas

- **Backend:**
  - Java 17
  - Spring Boot
  - Spring Data JPA
  - Spring Web
  - Thymeleaf (templates)

- **Banco de Dados:**
  - PostgreSQL

- **Comunicação:**
  - REST APIs
  - Kafka (para eventos)

- **Containerização:**
  - Docker
  - Docker Compose

## Funcionalidades

1. **Gerenciamento de Usuários**
   - Cadastro de usuários
   - Edição de dados de usuários
   - Remoção de usuários
   - Listagem de usuários

2. **Gerenciamento de Salas**
   - Cadastro de salas
   - Edição de dados das salas
   - Remoção de salas
   - Listagem de salas
   - Verificação de disponibilidade

3. **Gerenciamento de Reservas**
   - Criação de reservas
   - Cancelamento de reservas
   - Listagem de reservas
   - Verificação de conflitos de horário

## Configuração do Docker Compose

O projeto utiliza Docker Compose para orquestrar os seguintes serviços:

1. **PostgreSQL (Porta 5432)**
   - Banco de dados principal
   - Configurado com usuário e senha padrão
   - Dados persistidos em volume Docker

2. **Kafka (Porta 9092)**
   - Broker de mensagens para eventos
   - Configurado com Zookeeper
   - Suporte a múltiplos listeners

3. **Serviços da Aplicação**
   - Cada serviço em seu próprio container
   - Dependências entre serviços gerenciadas
   - Rede Docker dedicada

## Como Executar

### Usando os Scripts PowerShell

O projeto inclui scripts PowerShell para facilitar a execução:

1. **Iniciar os serviços:**
   ```powershell
   .\start-services.ps1
   ```

2. **Parar os serviços:**
   ```powershell
   .\stop-services.ps1
   ```

### Execução Manual

1. Clone o repositório
2. Navegue até a pasta do projeto
3. Execute o comando:
   ```bash
   docker-compose up -d
   ```
4. Acesse a aplicação em: http://localhost:8080/reservas

## Estrutura do Projeto

```
reservas-salas-microservices/
├── api-gateway/           # Serviço de API Gateway
├── usuario-service/       # Serviço de Usuários
├── sala-service/         # Serviço de Salas
├── reserva-service/      # Serviço de Reservas
├── start-services.ps1    # Script para iniciar os serviços
├── stop-services.ps1     # Script para parar os serviços
└── docker-compose.yml    # Configuração dos containers
```

## Observações

- O sistema utiliza comunicação síncrona entre os serviços via REST APIs
- Eventos são publicados no Kafka para comunicação assíncrona
- Cada serviço possui seu próprio banco de dados PostgreSQL
- A interface web é servida pelo API Gateway usando Thymeleaf

## Pré-requisitos

- Docker
- Docker Compose
- Java 17 (para desenvolvimento)
- Maven (para desenvolvimento)
- PowerShell (para Windows)
- Git (opcional)

## Troubleshooting

Se encontrar problemas:

1. Verifique os logs dos containers:
   ```powershell
   docker-compose logs -f
   ```

2. Verifique o status dos containers:
   ```powershell
   docker-compose ps
   ```

3. Reinicie os serviços:
   ```powershell
   .\stop-services.ps1
   .\start-services.ps1
   ```

4. Para remover todos os volumes e dados:
   ```powershell
   docker-compose down -v
   ```

## Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request 