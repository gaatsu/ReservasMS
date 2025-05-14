# Sistema de Reserva de Salas

Este é um sistema de reserva de salas desenvolvido usando microserviços com Spring Boot.

## Estrutura do Projeto

O projeto é composto pelos seguintes microserviços:

1. **usuario-service**: Gerencia usuários do sistema
2. **sala-service**: Gerencia as salas disponíveis
3. **reserva-service**: Gerencia as reservas de salas
4. **api-gateway**: Gateway de API para roteamento e segurança

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- PostgreSQL
- Docker
- Maven

## Arquitetura

O projeto segue a arquitetura hexagonal (também conhecida como Ports and Adapters), que separa a lógica de negócios da infraestrutura técnica. Cada microserviço é dividido em camadas:

- **Domain**: Contém as entidades e regras de negócio
- **Application**: Contém os casos de uso e serviços de aplicação
- **Infrastructure**: Contém as implementações técnicas (repositórios, APIs, etc.)
- **Interfaces**: Contém os adaptadores de entrada (controllers, eventos, etc.)

## Configuração do Ambiente

### Pré-requisitos

- Java 17 ou superior
- Maven
- Docker e Docker Compose
- PostgreSQL

### Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL para cada microserviço:
   - `usuario_db`
   - `sala_db`
   - `reserva_db`

2. Configure as credenciais no arquivo `application.yml` de cada serviço.

### Executando o Projeto

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/reservas-salas-microservices.git
cd reservas-salas-microservices
```

2. Compile o projeto:
```bash
mvn clean package
```

3. Execute os serviços usando Docker Compose:
```bash
docker-compose up
```

## Endpoints da API

### Usuário Service (Porta 8081)

- `POST /usuarios`: Criar usuário
- `GET /usuarios`: Listar usuários
- `GET /usuarios/{id}`: Buscar usuário por ID
- `PUT /usuarios/{id}`: Atualizar usuário
- `DELETE /usuarios/{id}`: Deletar usuário

### Sala Service (Porta 8082)

- `POST /salas`: Criar sala
- `GET /salas`: Listar salas
- `GET /salas/{id}`: Buscar sala por ID
- `PUT /salas/{id}`: Atualizar sala
- `DELETE /salas/{id}`: Deletar sala

### Reserva Service (Porta 8083)

- `POST /reservas`: Criar reserva
- `GET /reservas`: Listar reservas
- `GET /reservas/{id}`: Buscar reserva por ID
- `PUT /reservas/{id}`: Atualizar reserva
- `DELETE /reservas/{id}`: Deletar reserva
- `GET /reservas/usuario/{usuarioId}`: Listar reservas por usuário
- `GET /reservas/sala/{salaId}`: Listar reservas por sala

## Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes. 