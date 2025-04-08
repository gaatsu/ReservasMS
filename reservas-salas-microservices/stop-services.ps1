# Definir a codificação UTF-8 para o console
$OutputEncoding = [System.Text.Encoding]::UTF8
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$PSDefaultParameterValues['*:Encoding'] = 'utf8'
$env:PYTHONIOENCODING = "utf-8"

# Forçar a página de código do console para UTF-8
$null = [Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$null = chcp 65001

Write-Host "Parando os servicos do sistema de reservas de salas..." -ForegroundColor Yellow

# Navegar para o diretorio do projeto
Set-Location $PSScriptRoot

# Parar os containers
Write-Host "`nParando containers Docker..." -ForegroundColor Yellow
docker-compose down

# Verificar se todos os containers foram parados
$containers = docker ps -a --filter "name=reservas" --format "{{.Names}}"
if ($containers) {
    Write-Host "`nAviso: Alguns containers ainda estao em execucao:" -ForegroundColor Red
    $containers | ForEach-Object {
        Write-Host "- $_" -ForegroundColor Red
    }
    Write-Host "`nPara forcar a parada de todos os containers, execute:" -ForegroundColor Yellow
    Write-Host "docker-compose down --remove-orphans" -ForegroundColor Yellow
} else {
    Write-Host "`nTodos os containers foram parados com sucesso!" -ForegroundColor Green
}

Write-Host "`nPara remover todos os volumes e dados, execute:" -ForegroundColor Yellow
Write-Host "docker-compose down -v" -ForegroundColor Yellow 