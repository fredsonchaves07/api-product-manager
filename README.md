# Product Manager API

![Build status](https://github.com/fredsonchaves07/api-product-manager/actions/workflows/cd-prod-workflow.yml/badge.svg)

## 📌 Conteúdo

- [Sobre](#-sobre)
- [Tecnologias](#-tecnologias)
- [Funcionalidades](#-funcionalidades)
- [Instalação e configuração](#%EF%B8%8F-instalação-e-configuração)
  - [Executando com Docker](#executando-com-docker)
  - [Instalação de Dependências](#instalação-de-dependências)
  - [Executando Testes](#executando-os-testes-da-aplicação)
- [Executando a aplicação](#%EF%B8%8F-executando-a-aplicação)
- [Documentação](#-documentação)
- [Exemplos de Requisições](#-exemplos-de-requisições)
- [Bugs](#-bugs)
- [Contribuição](#-contribuições)
- [Licença](#%EF%B8%8F-licença)

## 🚀 Sobre

Este repositório contém o código-fonte da **API RESTful de gerenciamento de produtos**, desenvolvida com foco em boas
práticas de design e arquitetura de software. A API segue os princípios REST e foi construída com **TDD (Test-Driven
Development)**, garantindo cobertura de testes desde a regra de negócio até os endpoints públicos.

🔗 A aplicação também está disponível em ambiente cloud:
> 🌐 https://api-product-manager.onrender.com/api/v1

## 💻 Tecnologias

- [Java 21](https://adoptium.net/temurin/releases/)
- [Maven 3.9.8](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Spring Boot](https://spring.io/projects/spring-boot/)
- [H2Database](https://www.h2database.com/)
- [JUnit5](https://junit.org/junit5/)
- [Swagger](https://swagger.io/specification/)

## ✨ Funcionalidades

- ✅ Cadastro, listagem, atualização e remoção de produtos
- ✅ Validações de domínio aplicadas à entidade `Product`, como:
  - Nome não pode ser nulo;
  - Preço deve ser maior ou igual a zero;
  - Quantidade de estoque deve ser maior ou igual a zero.
- ✅ Retorno de erros no
  formato [RFC 7807 - Problem Details for HTTP APIs](https://datatracker.ietf.org/doc/html/rfc7807)
- ✅ Estrutura padronizada de resposta da API (`ApiResponse`)
- ✅ Redirecionamento automático para documentação Swagger
- ✅ Implementação com **TDD**, com testes unitários em todas as camadas (DAO, Service, Controller)
- ✅ Deploy contínuo com Docker + Render (Cloud)
- ✅ Organização de código com boas práticas: camadas bem definidas, uso de DTO, Factory, Builder, DAO

## 🛠️ Instalação e configuração

Para executar o projeto em ambiente de desenvolvimento, certifique-se de ter as ferramentas listadas na
seção [tecnologias](#-tecnologias) instaladas.

### Executando com Docker

```bash
docker compose up --build
```

Acesse a API pela URL [localhost:8080/api/v1](localhost:8080/api/v1).

### Instalação de dependências

```bash
mvn clean install -DskipTests
```

### Executando os testes da aplicação

Todos os testes foram implementados utilizando a abordagem TDD.

```bash
mvn test
```

## ⚙️ Executando a aplicação

Após instalar as dependências, você pode rodar a aplicação com:

```bash
mvn spring-boot:run
```

Ou se preferir, via .jar:
