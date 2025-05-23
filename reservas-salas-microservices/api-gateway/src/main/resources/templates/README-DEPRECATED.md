# ⚠️ DEPRECADO - Template Thymeleaf

## Status: **DESCONTINUADO** 

Este template foi **migrado** para o **Frontend Service** e não deve mais ser usado.

### Migração Realizada

- **De**: `api-gateway/src/main/resources/templates/reservas.html` (Thymeleaf)
- **Para**: `frontend-service/public/` (HTML/CSS/JS estático)

### Motivos da Migração

1. **Separação de Responsabilidades**: Frontend e backend agora são independentes
2. **Escalabilidade**: Frontend pode ser escalado separadamente
3. **Performance**: Nginx otimizado para servir conteúdo estático
4. **Tecnologia Moderna**: JavaScript moderno em vez de templates server-side
5. **Flexibilidade**: Facilita futuras migrações para frameworks SPA

### Novo Acesso

- **Frontend Novo**: http://localhost:3000
- **API Gateway**: http://localhost:8080 (somente APIs)

### Ação Requerida

**Para desenvolvedores**: Use o novo frontend service em `frontend-service/public/`

**Para usuários**: Acesse http://localhost:3000 em vez de http://localhost:8080/reservas

---

📅 **Data da Migração**: Janeiro 2025  
🔄 **Status**: Migração Completa  
🗑️ **Remoção Planejada**: Próxima versão major 