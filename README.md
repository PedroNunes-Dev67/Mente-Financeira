# 💰🧠 Mente Financeira

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-green?style=for-the-badge&logo=springboot)
![Docker](https://img.shields.io/badge/Docker-Container-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![H2](https://img.shields.io/badge/H2-Database-blue?style=for-the-badge)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203.0-85EA2D?style=for-the-badge&logo=swagger)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

> API REST para gerenciamento completo despesas de um usuário.

---

## 📋 Sobre o Projeto 


### ✨ Principais Características

- ✅ **CRUD Completo** - Criar, listar, atualizar e deletar recursos no sistema
- ✅ **JWT** - Autenticação e autorização de usuários baseado em JWT pelo Spring Security
- ✅ **Validações Robustas** - Bean Validation para garantir integridade dos dados
- ✅ **Tratamento de Exceções** - Respostas HTTP padronizadas e mensagens de erro claras
- ✅ **Documentação Interativa** - Swagger UI para testar endpoints facilmente
- ✅ **Perfis de Ambiente** - Configurações separadas para desenvolvimento e produção
- ✅ **Persistência em H2 Database** - Banco de dados para testes
---

## 🚀 Tecnologias Utilizadas

### Back-end
- **Java 21** - Linguagem de programação
- **Spring Boot 3.5** - Framework para desenvolvimento de aplicações
- **Spring Data JPA** - Persistência de dados
- **Hibernate** - ORM (Object-Relational Mapping)
- **Spring Security** - Autenticação e autorização

### Banco de Dados
- **H2 Database** - Banco de dados de teste relacional

### Documentação
- **SpringDoc OpenAPI 3** - Geração automática de documentação
- **Swagger UI** - Interface interativa para testes

### Validação
- **Bean Validation (Jakarta)** - Validação de dados de entrada

### Build & Deploy
- **Maven** - Gerenciamento de dependências
---

## 🛠️ Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina:

- [Java JDK 21+](https://www.oracle.com/java/technologies/downloads/)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)

---

## 🔧 Como Executar o Projeto

### 1️⃣ Clone o repositório

```bash
git clone https://github.com/PedroNunes-Dev67/Menter-Financeira.git
cd Menter-Financeira
```

### 2️⃣ Execute a aplicação

```bash
# Usando Maven
mvn spring-boot:run

# Ou compilando o JAR
mvn clean package
java -jar target/Mente-Financeira-0.0.1-SNAPSHOT.jar
```

### 3️⃣ Acesse a documentação Swagger

Abra seu navegador e acesse:

```
http://localhost:8080/swagger-ui.html
```
