# Projeto API com Java, Spring Boot, JPA e SQL Server  

## Descrição  
Este projeto consiste em uma aplicação web para o monitoramento e gerenciamento de uma loja de produtos. Ele consiste no desenvolvimento de uma API RESTful utilizando **Java**, **Spring Boot**(Spring Data JPA e Spring Web) e **SQL Server** como banco de dados para a persistência dos dados.
Nele, é permitido as operações de CRUD em um sistema que envolve entidades como usuários, produtos, pedidos, pagamentos e categorias dos produtos. 
Como teste para as requisições HTTP e validação dos endpoints implementados, foi utilizado o **Postman**.

O projeto segue uma arquitetura baseada em camadas, o que garante organização, facilidade de manutenção e escalabilidade.  

---

## Funcionalidades  
- Operações CRUD completas para as entidades:  
  - **Usuários**  
  - **Produtos**  
  - **Categorias**
  - **Pedidos**  
  - **Pagamentos**
- Relacionamentos entre entidades gerenciados com **JPA**.  
- Mapeamento Objeto-Relacional com validações e constraints.
- Organização por camadas: `controllers`, `services`, `dto's`, `repositories` e `models`.  
- Tratamento de exceções personalizado.
- Interface gráfica em HTML, CSS e Javascript

---

## Tecnologias Utilizadas  
- **Java**: Linguagem principal do projeto.  
- **Spring Boot**: Framework para simplificar a configuração e desenvolvimento de aplicações Java.  
- **JPA (Java Persistence API)**: Gerenciamento e mapeamento objeto-relacional.  
- **SQL Server**: Banco de dados utilizado para armazenar e gerenciar as informações.  
- **Postman**: Ferramenta para testar os endpoints da API.
- **HTML**: Utilizado para criar a estrutura das páginas
- **CSS**: Utilizado para estilizar as páginas.
- **Javascript**: Linguagem para fazer a ponte do backend para o frontend e para dar dinâmica à interface em HTML/CSS.

---

## Estrutura do Projeto  
A estrutura do projeto foi organizada da seguinte forma:  
```
src/main/java/com.lorenzozagallo.jpa
├── config             # Apenas dados para fazer os testes
├── controllers        # Controladores responsáveis pelos endpoints
│   ├── exceptions     # Tratamento de exceções
├── cors               # Habilita que um servidor relaxe a política de mesma origem
├── dtos               # Objetos de transferência de dados  
├── models             # Modelos das entidades  
│   ├── enums          # Enumeradores utilizados nas entidades  
│   ├── pk             # Chaves primárias compostas  
├── repositories       # Interfaces para acesso ao banco de dados  
├── services           # Regras de negócio e lógica de aplicação  
│   ├── exceptions     # Tratamento de exceções  
└── main               # Classe principal para inicialização da aplicação  


src/main/resources
└── properties         # Configurações da conexão com o JPA e o Database  

src/main/front-end
├── scripts            # Javascript para realizar a conexão com o backend  
├── styles             # Estilização do html
└── index              # Estrutura do frontend
```  

---

## Pré-requisitos  
Certifique-se de ter os seguintes recursos instalados na sua máquina:  
- **Java 17+**
- **Maven**
- **SQL Server** (ou algum banco de dados de sua preferência, só mudar no properties)
- **Postman** (opcional, mas recomendado para testes)

---

## Como Executar o Projeto  

1. **Clone este repositório**:  
   ```bash  
   git clone https://github.com/seu-usuario/nome-do-repositorio.git  
   cd nome-do-repositorio  
   ```  

2. **Configure o Banco de Dados**:  
   - Crie um banco de dados no SQL Server.  
   - Atualize as configurações de conexão no arquivo `application.properties` ou `application.yml` na pasta `src/main/resources`.  

3. **Compile e execute o projeto**:  
   ```bash  
   mvn spring-boot:run  
   ```  

4. **Teste os Endpoints**:  
   - Importe o arquivo de coleção no Postman (caso exista).
   - Utilize os endpoints disponíveis para realizar as operações CRUD.
  ```
  {
  "info": {
    "name": "API Collection - Users and Products",
    "description": "Coleção para testar endpoints de usuários e produtos.",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Criar um Usuário",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json",
            "type": "text"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"name\": \"Bob Brown\",\n  \"email\": \"bob@gmail.com\",\n  \"phone\": \"977557755\",\n  \"password\": \"123456\"\n}"
        },
        "url": {
          "raw": "http://localhost:8080/users",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["users"]
        }
      }
    },
    {
      "name": "Criar um Produto",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json",
            "type": "text"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"name\": \"The Lord of the Rings\",\n  \"description\": \"Lorem ipsum dolor sit amet, consectetur.\",\n  \"price\": 90.5,\n  \"imgUrl\": \"\"\n}"
        },
        "url": {
          "raw": "http://localhost:8080/products",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["products"]
        }
      }
    }
  ]
}

  ```

---

## Contribuições  
Contribuições são bem-vindas! Sinta-se à vontade para abrir uma issue ou criar um pull request com melhorias ou correções.
