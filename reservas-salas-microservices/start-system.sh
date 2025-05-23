#!/bin/bash

echo "🚀 Iniciando Sistema de Reservas de Salas - Microserviços"
echo "========================================================"

echo ""
echo "📋 Arquitetura do Sistema:"
echo "  ├── 🌐 Frontend Service (localhost:3000)"
echo "  ├── 🔗 API Gateway (localhost:8080)"
echo "  ├── 👥 Usuario Service (localhost:8081)"
echo "  ├── 🏢 Sala Service (localhost:8082)"
echo "  ├── 📅 Reserva Service (localhost:8083)"
echo "  ├── 🐘 PostgreSQL (localhost:5432)"
echo "  └── 📨 Kafka (localhost:9092)"

echo ""
echo "🔧 Verificando dependências..."

# Verificar se Docker está instalado
if ! command -v docker &> /dev/null; then
    echo "❌ Docker não está instalado. Por favor, instale o Docker primeiro."
    exit 1
fi

# Verificar se Docker Compose está instalado
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose não está instalado. Por favor, instale o Docker Compose primeiro."
    exit 1
fi

echo "✅ Dependências verificadas com sucesso!"

echo ""
echo "🛠️ Construindo e iniciando os containers..."
echo "Isso pode levar alguns minutos na primeira execução..."

# Parar containers existentes
echo "🔄 Parando containers existentes..."
docker-compose down

# Construir e iniciar
echo "🏗️ Construindo e iniciando containers..."
docker-compose up --build -d

echo ""
echo "⏳ Aguardando serviços ficarem disponíveis..."
sleep 30

echo ""
echo "🔍 Verificando status dos serviços..."

# Função para verificar se um serviço está rodando
check_service() {
    local service_name=$1
    local port=$2
    local max_attempts=30
    local attempt=1

    while [ $attempt -le $max_attempts ]; do
        if curl -s "http://localhost:$port" > /dev/null 2>&1; then
            echo "✅ $service_name está rodando (localhost:$port)"
            return 0
        fi
        sleep 2
        attempt=$((attempt + 1))
    done
    echo "❌ $service_name não está respondendo (localhost:$port)"
    return 1
}

# Verificar serviços
check_service "Frontend Service" 3000
check_service "API Gateway" 8080
check_service "Usuario Service" 8081
check_service "Sala Service" 8082
check_service "Reserva Service" 8083

echo ""
echo "🎉 Sistema iniciado com sucesso!"
echo ""
echo "📍 URLs de Acesso:"
echo "  🌐 Interface Web: http://localhost:3000"
echo "  🔗 API Gateway: http://localhost:8080"
echo ""
echo "📍 APIs Diretas:"
echo "  👥 Usuários: http://localhost:8081/api/usuarios"
echo "  🏢 Salas: http://localhost:8082/api/salas"
echo "  📅 Reservas: http://localhost:8083/api/reservas"
echo ""
echo "📖 Para parar o sistema, execute: docker-compose down"
echo "📊 Para ver logs, execute: docker-compose logs -f"
echo ""
echo "Happy coding! 🚀" 