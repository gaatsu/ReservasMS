@echo off
chcp 65001 >nul
cls

echo 🚀 Iniciando Sistema de Reservas de Salas - Microserviços
echo ========================================================

echo.
echo 📋 Arquitetura do Sistema:
echo   ├── 🌐 Frontend Service (localhost:3000)
echo   ├── 🔗 API Gateway (localhost:8080)
echo   ├── 👥 Usuario Service (localhost:8081)
echo   ├── 🏢 Sala Service (localhost:8082)
echo   ├── 📅 Reserva Service (localhost:8083)
echo   ├── 🐘 PostgreSQL (localhost:5432)
echo   └── 📨 Kafka (localhost:9092)

echo.
echo 🔧 Verificando dependências...

REM Verificar se Docker está instalado
docker --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker não está instalado. Por favor, instale o Docker primeiro.
    pause
    exit /b 1
)

REM Verificar se Docker Compose está instalado
docker-compose --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker Compose não está instalado. Por favor, instale o Docker Compose primeiro.
    pause
    exit /b 1
)

echo ✅ Dependências verificadas com sucesso!

echo.
echo 🛠️ Construindo e iniciando os containers...
echo Isso pode levar alguns minutos na primeira execução...

REM Parar containers existentes
echo 🔄 Parando containers existentes...
docker-compose down

REM Construir e iniciar
echo 🏗️ Construindo e iniciando containers...
docker-compose up --build -d

echo.
echo ⏳ Aguardando serviços ficarem disponíveis...
timeout /t 30 /nobreak >nul

echo.
echo 🎉 Sistema iniciado com sucesso!
echo.
echo 📍 URLs de Acesso:
echo   🌐 Interface Web: http://localhost:3000
echo   🔗 API Gateway: http://localhost:8080
echo.
echo 📍 APIs Diretas:
echo   👥 Usuários: http://localhost:8081/api/usuarios
echo   🏢 Salas: http://localhost:8082/api/salas
echo   📅 Reservas: http://localhost:8083/api/reservas
echo.
echo 📖 Para parar o sistema, execute: docker-compose down
echo 📊 Para ver logs, execute: docker-compose logs -f
echo.
echo Happy coding! 🚀
echo.
pause 