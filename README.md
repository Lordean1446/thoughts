# Thoughts API

Este é um projeto de estudo e prática com Java e Spring Boot. A aplicação é uma API REST para cadastro de usuários e pensamentos, permitindo que pessoas compartilhem ideias e opiniões.

## Sobre o Projeto

- Desenvolvido como parte do meu aprendizado em Java e Spring Boot.
- Utiliza boas práticas como DTOs, validação de dados, tratamento centralizado de erros e documentação automática.
- O objetivo principal foi aprender a criar uma API completa, do zero, com recursos modernos e organização de código.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **ModelMapper**
- **Bean Validation**
- **Swagger/OpenAPI** (documentação automática)
- **SLF4J** (logging)

## Como Executar

1. **Pré-requisitos:**  
   - Java 21  
   - Maven  
   - PostgreSQL rodando (ou use Docker)

2. **Configuração:**  
   - Edite o arquivo `src/main/resources/application.properties` com os dados do seu banco.

3. **Rodando o projeto:**  
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```
   Ou, se preferir, use o Docker Compose:
   ```bash
   docker-compose up
   ```

## Principais Funcionalidades

- Cadastro, consulta, atualização e remoção de usuários
- Cadastro, consulta, atualização e remoção de pensamentos
- Validação dos dados de entrada (ex: e-mail válido, senha mínima)
- Tratamento centralizado de erros (respostas padronizadas)
- Logging das operações principais
- Documentação automática da API

## Endpoints

### Usuários

- `GET /api/users` — Lista todos os usuários
- `POST /api/users/register` — Cadastra um novo usuário
- `GET /api/users/{id}` — Consulta usuário por ID
- `PUT /api/users/{id}` — Atualiza dados do usuário
- `DELETE /api/users/{id}` — Remove usuário

### Pensamentos

- `GET /api/thoughts` — Lista todos os pensamentos
- `POST /api/thoughts/{userId}` — Cadastra um novo pensamento para um usuário
- `GET /api/thoughts/{id}` — Consulta pensamento por ID
- `PUT /api/thoughts/{id}` — Atualiza pensamento
- `DELETE /api/thoughts/{id}` — Remove pensamento

## Tratamento de Erros

- Erros de validação e exceções são tratados de forma centralizada.
- As respostas de erro são padronizadas, facilitando o entendimento por quem consome a API.
- Exemplo de resposta de erro:
  ```json
  {
    "status": 400,
    "message": "O email deve ser válido."
  }
  ```

- Respostas de sucesso também são padronizadas:
  ```json
  {
    "status": 201,
    "message": "Usuário criado com sucesso!",
    "data": { ... }
  }
  ```

## Documentação da API

Acesse a documentação interativa em:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
ou  
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Observações

- Este projeto foi feito para aprender e praticar.  
- Ainda há espaço para melhorias, como autenticação com Spring Security e testes automatizados.
- Fique à vontade para sugerir melhorias ou contribuir!

## Observação sobre o Front-end

O arquivo `index.html` do front-end está configurado para usar um usuário de teste com:

```js
const FAKE_USER_ID = 1;
```

Portanto, para testar as funcionalidades, crie primeiro um usuário (por exemplo, via Postman ou Swagger) e utilize o ID 1 para experimentar o cadastro e listagem de pensamentos. Caso o primeiro usuário criado tenha outro ID, ajuste o valor de `FAKE_USER_ID` no código do front-end conforme necessário.

## Licença

Este projeto está licenciado sob os termos da licença MIT. Veja o arquivo [LICENSE](./LICENSE) para mais detalhes.
