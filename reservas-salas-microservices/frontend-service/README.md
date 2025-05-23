# Frontend Service - Sistema de Reserva de Salas

Este é o frontend do Sistema de Reserva de Salas, desenvolvido com React, TypeScript e Material-UI.

## Requisitos

- Node.js 14.x ou superior
- npm 6.x ou superior

## Instalação

1. Clone o repositório
2. Navegue até o diretório do frontend:
   ```bash
   cd frontend-service
   ```
3. Instale as dependências:
   ```bash
   npm install
   ```

## Executando o projeto

Para iniciar o servidor de desenvolvimento:

```bash
npm start
```

O aplicativo estará disponível em `http://localhost:3000`.

## Build

Para criar uma versão de produção:

```bash
npm run build
```

Os arquivos de build serão gerados no diretório `build`.

## Tecnologias utilizadas

- React
- TypeScript
- Material-UI
- React Router
- Axios
- date-fns

## Estrutura do projeto

```
src/
  ├── components/     # Componentes reutilizáveis
  ├── pages/         # Páginas da aplicação
  ├── routes.tsx     # Configuração de rotas
  ├── theme.ts       # Configuração do tema
  ├── App.tsx        # Componente principal
  └── index.tsx      # Ponto de entrada
```

## Funcionalidades

- Gerenciamento de usuários
- Gerenciamento de salas
- Gerenciamento de reservas
- Interface responsiva
- Tema personalizado 