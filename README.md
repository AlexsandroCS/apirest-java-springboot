# APIREST usando Java com SpringBoot

## Introdução

Desenvolvi uma API REST utilizando Java e Spring Boot. A API foi desenvovida para fazer um CRUD em uma entidade de `usuario` que estará protegida com JWT, utilizando Spring Security. A API conta com banco de dados em memória H2 onde o banco de dados é reiniciado toda vez que a aplicação é inicializada.

A API além de ter sua entidade principal `usuario`, foi construída com mais 2 entidades que vão fazer um relacionamento básico com a entidade de `usuario`.

## Tecnologias empregadas na API

- Java na versão: 17.0.11
- Spring Boot na versão: 3.3.4
- Banco de dados H2

### Gerenciador de dependências
- Maven

  #### Dependências usadas
  - Java Persistence API
  - Spring Boot Starter Validation
  - Spring Boot Starter Web
  - H2 Database
  - Spring Boot Starter
  - Spring Boot Starter Security
  - Java JWT

### Programas usados e recomendado para teste
- IntelliJ IDEA
- Postman

---

## Endpoints: Usuario
A entidade `usuario` conta com 4 endpoint para cada operação do CRUD.

Requisições para a API devem seguir os padrões:
| Type | Endpoint | Descrição | 
|---|---|---|
| `GET` | `http://localhost:8080/api/usuario/id` | Retorna informações de um registro específico. |
| `POST` | `http://localhost:8080/api/usuario` | Utilizado para criar um novo registro. |
| `PUT` | `http://localhost:8080/api/usuario/id` | Atualizar dados de um registro do sistema. |
| `DELETE` | `http://localhost:8080/api/usuario/id` | Remove o registro específicado do sistema. |

Todos endpoint exceto o **Criar usuário** contém `Autenticação` e `Autorização`, isso se dá por conta da criação do primeiro usuário, tendo em vista que o nosso banco de dados é resetado toda vez que a API é executada, senti a necessidade de deixar esse endpoint sem as permissões o que acaba deixando a experiência de criação de usuário mais real.

## Endpoint: login
Temos o endpoint de login onde e necessário se conectar com um usuário existente para capturar o **`token`** que permite a `Autorização` em todos os outros endpoint da API.
  - O token foi criado para durar apenas 1 hora, logo após esse período, se o tester tentar acessar os endpoint com o token inválido sempre retornará erro tratado.
  
  #### Observação Auth Type
  - Logo após capturar o **token**, quando estiverem acessando outro endpoint é necessário utilizar o token na Authorization: **`Bearer Token`**, caso contrário pode ocorrer status: **403**

Requisições para a API devem seguir os padrões:
| Type | Endpoint | Descrição | 
|---|---|---|
| `POST` | `http://localhost:8080/api/login` | Retorna um token válido por 1 hora. |

Temos o endpoint de login onde e necessário se conectar com um usuário existente para capturar o **`token`** que permite a `Autorização` em todos os outros endpoint da API.
  - O token foi criado para durar apenas 1 hora, logo após esse período, se o tester tentar acessar os endpoint com o token inválido sempre retornará erro tratado.


## Endpoints: Produto
A entidade de `produto` conta com 2 endpoint, sendo um para criar produto e o outro para capturar produto pelo seu id.

| Type | Endpoint | Descrição | 
|---|---|---|
| `POST` | `http://localhost:8080/api/produto` | Utilizado para criar o registro de um produto. |
| `GET` | `http://localhost:8080/api/produto/id` | Retorna informações sobre um registro específico. |


## Endpoints: Pedido
A entidade de `pedid` conta com 2 endpoint, sendo um para criar um pedido e o outro para listar os pedidos feitos com suas relações entre os 3 endpoint.

| Type | Endpoint | Descrição | 
|---|---|---|
| `POST` | `http://localhost:8080/api/pedido` | Utilizado para criar o registro de um pedido. |
| `GET` | `http://localhost:8080/api/pedido/id` | Retorna uma lista sobre os pedido com suas relações entre os 3 endpoint. |

---

# Segue a estrutura dos Endpoints no postman



## Usuario

### POST - Criar usuário
```json
{
    "nome": "Alexsandro",
    "email": "AlexsandroCS@gmail.com",
    "senha": "123123123a"
}
```

### GET - Login
```json
body:
{
    "email": "AlexsandroCS@gmail.com",
    "senha": "123123123a"
}

response:
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJDU29yZG5hc3hlbEEiLCJzdWIiOiJBbGV4c2FuZHJvLmFjczk4QGdtYWlsLmNvbSIsImlkVXN1YXJpbyI6MSwibm9tZVVzdWFyaW8iOiJBbGV4c2FuZHJvIiwiZXhwIjoxNzI4NDQ0MzAwfQ.rN4THSymcfgK39Y-pFrSrwOu2DgH-ghEmMdmbMBDrwI"
}
```

### GET - Capturar usuário
```json
Url com o id específico para captura:
Inserir Barer Token na Autorização:
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJDU29yZG5hc3hlbEEiLCJzdWIiOiJBbGV4c2FuZHJvLmFjczk4QGdtYWlsLmNvbSIsImlkVXN1YXJpbyI6MSwibm9tZVVzdWFyaW8iOiJBbGV4c2FuZHJvIiwiZXhwIjoxNzI4NDQ0MzAwfQ.rN4THSymcfgK39Y-pFrSrwOu2DgH-ghEmMdmbMBDrwI"
}

response:
{
    "id": 1,
    "nome": "Alexsandro",
    "email": "Alexsandro.acs98@gmail.com",
    "dataCriacao": "2024-10-09T02:24:55.869response+00:00"
}
```

### PUT - Editar usuário
```json
Url com o id específico de edição:
Inserir Barer Token na Autorização:
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJDU29yZG5hc3hlbEEiLCJzdWIiOiJBbGV4c2FuZHJvLmFjczk4QGdtYWlsLmNvbSIsImlkVXN1YXJpbyI6MSwibm9tZVVzdWFyaW8iOiJBbGV4c2FuZHJvIiwiZXhwIjoxNzI4NDQ0MzAwfQ.rN4THSymcfgK39Y-pFrSrwOu2DgH-ghEmMdmbMBDrwI"
}

body:
{
    "nome": "Alex",
    "email": "Alex1198@gmail.com",
    "senha": "3213212222b"
}

response:
{
    "id": 1,
    "nome": "Alexsandro",
    "email": "Alexsandro.acs98@gmail.com",
    "dataCriacao": "2024-10-09T02:24:55.869+00:00"
}
```

### DELETE - Deletar usuário
```json
Url com o id específico de exclusão:
inserir Barer Token na Autorização:
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJDU29yZG5hc3hlbEEiLCJzdWIiOiJBbGV4c2FuZHJvLmFjczk4QGdtYWlsLmNvbSIsImlkVXN1YXJpbyI6MSwibm9tZVVzdWFyaW8iOiJBbGV4c2FuZHJvIiwiZXhwIjoxNzI4NDQ0MzAwfQ.rN4THSymcfgK39Y-pFrSrwOu2DgH-ghEmMdmbMBDrwI"
}

Possíveis response:
{
    "Mensagem": "Usuário foi deletado com sucesso!",
    "Mensagem": "Não é possível deletar usuário!"
}
```

## Produto

### POST - Criar Produto
```json
inserir Barer Token na Autorização:
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJDU29yZG5hc3hlbEEiLCJzdWIiOiJBbGV4c2FuZHJvLmFjczk4QGdtYWlsLmNvbSIsImlkVXN1YXJpbyI6MSwibm9tZVVzdWFyaW8iOiJBbGV4c2FuZHJvIiwiZXhwIjoxNzI4NDQ0MzAwfQ.rN4THSymcfgK39Y-pFrSrwOu2DgH-ghEmMdmbMBDrwI"
}

body:
{
    "produto": "Carro 2",
    "valor": "334.55"
}

response:
{
    "produto": "Carro 2",
    "valor": "334.55"
}
```

### GET - Capturar Produto Específico
```json
inserir Barer Token na Autorização:
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJDU29yZG5hc3hlbEEiLCJzdWIiOiJBbGV4c2FuZHJvLmFjczk4QGdtYWlsLmNvbSIsImlkVXN1YXJpbyI6MSwibm9tZVVzdWFyaW8iOiJBbGV4c2FuZHJvIiwiZXhwIjoxNzI4NDQ0MzAwfQ.rN4THSymcfgK39Y-pFrSrwOu2DgH-ghEmMdmbMBDrwI"
}

response:
{
    "produto": "Carro 2",
    "valor": "334.55"
}
```

## Pedido
### POST - Criar Pedido
Em usuárioId precisa colocar o id de um usuário existente.
Em produtoIds precisa colocar o id ou vários ids, separado por vírgula se os produtos existirem.

```json
inserir Barer Token na Autorização:
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJDU29yZG5hc3hlbEEiLCJzdWIiOiJBbGV4c2FuZHJvLmFjczk4QGdtYWlsLmNvbSIsImlkVXN1YXJpbyI6MSwibm9tZVVzdWFyaW8iOiJBbGV4c2FuZHJvIiwiZXhwIjoxNzI4NDQ0MzAwfQ.rN4THSymcfgK39Y-pFrSrwOu2DgH-ghEmMdmbMBDrwI"
}

body:
{
    "usuarioId": 3,
    "produtoIds": [1, 3]
}
// Exemplo: "produtoIds": [1,2,5,10,22]

response:
{
    "id": 2,
    "dataPedido": "Wed Oct 09 00:42:27 BRT 2024",
    "usuario": {
        "id": 3,
        "nome": "Alexsandro",
        "email": "Alex1198@gmail.com",
        "dataCriacao": "2024-10-09T03:42:27.012+00:00"
    },
    "produtos": [
        {
            "id": 1,
            "produto": "Carro 2",
            "valor": "334.55"
        },
        {
            "id": 3,
            "produto": "Notebook ",
            "valor": "3134.55"
        }
    ]
}
```

### GET - Listar Pedidos
```json
inserir Barer Token na Autorização:
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJDU29yZG5hc3hlbEEiLCJzdWIiOiJBbGV4c2FuZHJvLmFjczk4QGdtYWlsLmNvbSIsImlkVXN1YXJpbyI6MSwibm9tZVVzdWFyaW8iOiJBbGV4c2FuZHJvIiwiZXhwIjoxNzI4NDQ0MzAwfQ.rN4THSymcfgK39Y-pFrSrwOu2DgH-ghEmMdmbMBDrwI"
}
response:
[
    {
        "id": 2,
        "dataPedido": "2024-10-09 00:42:27.012",
        "usuario": {
            "id": 3,
            "nome": "Alexsandro",
            "email": "Alex1198@gmail.com",
            "dataCriacao": "2024-10-09T03:39:45.876+00:00"
        },
        "produtos": [
            {
                "id": 1,
                "produto": "Carro 2",
                "valor": "334.55"
            },
            {
                "id": 3,
                "produto": "Notebook ",
                "valor": "3134.55"
            }
        ]
    }
]
```

### Contato

**Email:** alexsandro.acs98@gmail.com

**Linkedin:** https://www.linkedin.com/in/alexsandrocs/

**Site:** https://www.alexsandrocs.com
