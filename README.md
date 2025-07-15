# OdontoSystem - Sistema de Gestão Odontológica

Odonto é um sistema completo para gestão de clínicas odontológicas, desenvolvido em Java com Spring Boot e PostgreSQL. Ele permite o gerenciamento de consultas, pacientes, dentistas, agendas, tipos de consulta e muito mais, com foco em segurança, usabilidade e performance.

## Funcionalidades
- Cadastro, edição e exclusão de pacientes, dentistas e tipos de consulta
- Agendamento, reagendamento, conclusão, cancelamento e remoção de consultas
- Controle de disponibilidade de horários (agenda)
- Autenticação JWT e segurança de endpoints
- Filtros por data, status e dentista
- Dashboard com resumo de atendimentos e faturamento
- API RESTful documentada e fácil de integrar

## Tecnologias Utilizadas
- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- JWT (JSON Web Token)
- Maven
- Docker (opcional)

## Requisitos
- Java 17 ou superior
- PostgreSQL 13+
- Maven 3.8+

## Instalação

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/arthur/odontoSystemBackend.git
   cd odonto
   ```
2. **Configure o banco de dados:**
   - Crie um banco chamado `odontoSystem` no PostgreSQL.
   - Atualize as credenciais em `src/main/resources/application.properties` se necessário.
3. **Instale as dependências:**
   ```bash
   mvn clean install
   ```
4. **Execute a aplicação:**
   ```bash
   mvn spring-boot:run
   ```
   A API estará disponível em `http://localhost:8080/api`.

## Uso

### Autenticação
- Realize login via `/api/auth/login` para obter um token JWT.
- Para conseguir realizar o login, se você não tiver um usuário no banco, crie pelo Postman fazendo uma requisição do tipo POST para `/api/auth/register` e envie como corpo da requisição:
   ```bash
   {
      "nome": "Seu nome",
      "email": "seuemail@gmail.com",
      "senha": "suasenha"
   }
- Envie o token no header `Authorization: Bearer <token>` para acessar os endpoints protegidos.

### Exemplos de Endpoints
- **Listar pacientes:** `GET /api/pacientes`
- **Agendar consulta:** `POST /api/consultas`
- **Reagendar consulta:** `PUT /api/consultas/{id}/reagendar`
- **Cancelar consulta:** `PUT /api/consultas/{id}/cancelar`
- **Dashboard:** `GET /api/dashboard/resumo`

Veja a documentação OpenAPI/Swagger (se disponível) para detalhes completos dos endpoints.

## Estrutura do Projeto
```
odonto/
├── src/
│   ├── main/
│   │   ├── java/com/renato/odonto/...
│   │   └── resources/
│   └── test/
├── pom.xml
└── README.md
```

## Contribuição
1. Fork este repositório
2. Crie uma branch: `git checkout -b minha-feature`
3. Faça suas alterações e commit: `git commit -m 'Minha feature'`
4. Push para sua branch: `git push origin minha-feature`
5. Abra um Pull Request

## Licença
Este projeto está licenciado sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## Contato
- Desenvolvedor: Renato (ou seu nome)
- Email: seu.email@dominio.com
- LinkedIn: [Seu LinkedIn](https://www.linkedin.com/in/seu-usuario)

---

> Feito com ❤️ para facilitar a gestão odontológica! 