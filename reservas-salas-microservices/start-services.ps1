# Definir a codificação UTF-8 para o console
$OutputEncoding = [System.Text.Encoding]::UTF8
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$PSDefaultParameterValues['*:Encoding'] = 'utf8'
$env:PYTHONIOENCODING = "utf-8"

# Forçar a página de código do console para UTF-8
$null = [Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$null = chcp 65001

Write-Host "Iniciando os servicos do sistema de reservas de salas..." -ForegroundColor Yellow

# Navegar para o diretorio do projeto
Set-Location $PSScriptRoot

# Parar containers existentes
Write-Host "`nParando containers existentes..." -ForegroundColor Yellow
docker-compose down

# Iniciar os containers
Write-Host "`nIniciando containers Docker..." -ForegroundColor Yellow
docker-compose up -d

# Aguardar um pouco para os containers iniciarem
Write-Host "`nAguardando containers iniciarem..." -ForegroundColor Yellow
Start-Sleep -Seconds 10

# Mostrar status dos containers
Write-Host "`nStatus dos servicos:" -ForegroundColor Cyan
docker-compose ps

Write-Host "`nPara ver os logs de um servico especifico, use:" -ForegroundColor Yellow
Write-Host "docker-compose logs -f [nome-do-servico]" -ForegroundColor Yellow
Write-Host "`nExemplo: docker-compose logs -f api-gateway" -ForegroundColor Yellow

Write-Host "`nA aplicacao estara disponivel em: http://localhost:8080/reservas" -ForegroundColor Green 