# Sistema de Gerenciamento de Pedidos (API RESTful)

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![MicrosoftSQLServer](https://img.shields.io/badge/Microsoft%20SQL%20Server-CC2927?style=for-the-badge&logo=microsoft%20sql%20server&logoColor=white)

## 💡 Sobre o Projeto

Este projeto é uma API RESTful completa para gerenciamento de pedidos de uma loja, simulando um ecossistema real de e-commerce.

O objetivo principal foi desenvolver uma aplicação robusta utilizando as melhores práticas do ecossistema **Spring Boot 3** e **Java 21**, focando em:
* **Arquitetura em Camadas** (Controllers, Services, Repositories).
* **Tratamento de Exceções Global** para respostas HTTP adequadas.
* **Padrão DTO** (Data Transfer Object) para segurança e desacoplamento da entidade.
* **Injeção de Dependência** via construtor (Best Practice).
* **Mapeamento Objeto-Relacional (ORM)** complexo (Many-to-Many com atributos extras).

## 🛠 Tecnologias Utilizadas

* **Java 21** (LTS)
* **Spring Boot 3.4.2**
* **Spring Data JPA / Hibernate**
* **Banco de Dados:** SQL Server (Produção) / H2 Database (Testes)
* **Maven** (Gerenciamento de dependências)

## 📐 Modelo de Domínio

O sistema resolve o desafio de **Associações Many-to-Many com atributos extras** (na classe `OrderItem`), garantindo a integridade dos dados entre Pedidos e Produtos.

Principais Entidades:
* `User` (Cliente)
* `Order` (Pedido)
* `Product` (Produto)
* `Category` (Categoria)
* `Payment` (Pagamento 1:1)

## 🚀 Como Executar

### Pré-requisitos
* Java 21 ou superior instalado.
* Maven.
* SQL Server (Opcional - o projeto está configurado para rodar com H2 em memória por padrão para testes rápidos).

### Passos
1. Clone o repositório:
```bash
git clone [https://github.com/SEU-USUARIO/NOME-DO-REPO.git](https://github.com/SEU-USUARIO/NOME-DO-REPO.git)
````

2.  Entre na pasta:

<!-- end list -->

```bash
cd workshop-springboot3-jpa
```

3.  Execute a aplicação:

<!-- end list -->

```bash
./mvnw spring-boot:run
```

4.  Acesse o Console do Banco de Dados (H2):
      * URL: `http://localhost:8080/h2-console`
      * JDBC URL: `jdbc:h2:mem:testdb`
      * User: `sa`
      * Password: (vazio)

## Endpoints Principais

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/workshop/products` | Lista todos os produtos |
| GET | `/workshop/orders/{id}` | Busca pedido por ID (com itens) |
| POST | `/workshop/orders` | Cria um novo pedido |
| POST | `/workshop/users` | Cria um novo usuário |

## 👨‍💻 Autor

**Lorenzo Zagallo**

* [LinkedIn](https://www.linkedin.com/in/lorenzo-zagallo-07654a2b9/)