# Sprint3

Um aplicativo Android para gerenciar bairros, cidades, estados e usuários.

## Integrantes
- Fernando Paparelli Aracena rm551022
- Filipe de Oliveira Mendes rm98959
- Miron Gonçalves Martins rm551801
- Victor Luca do Nascimento Queiroz rm551886
- Vinicius Pedro de Souza rm550907

## Descrição

O **Sprint3** é um aplicativo Android que permite o cadastro e gerenciamento de bairros, cidades, estados e usuários. Utiliza Kotlin e segue uma arquitetura MVC simples.

## Funcionalidades

- **Cadastro de Bairros**: Nome e zona.
- **Cadastro de Cidades**: Nome, código IBGE e número DDD.
- **Cadastro de Estados**: Sigla e nome.
- **Cadastro de Usuários**: Nome, CPF, RG, login, senha e data de nascimento.

## Estrutura

- **Modelos**:
  - `Bairro`: Representa um bairro.
  - `Cidade`: Representa uma cidade.
  - `Estado`: Representa um estado.
  - `User`: Representa um usuário.

- **Repositórios**:
  - `BairroRepository`: Gerencia bairros.
  - `CidadeRepository`: Gerencia cidades.
  - `EstadoRepository`: Gerencia estados.
  - `UserRepository`: Gerencia usuários.

## API

Base URL: `http://10.0.2.2:8080`

- **Bairros**
  - `POST /bairros`: Cadastra um bairro.
  - `GET /bairros`: Lista bairros.

- **Cidades**
  - `POST /cidades`: Cadastra uma cidade.
  - `GET /cidades`: Lista cidades.

- **Estados**
  - `POST /estados`: Cadastra um estado.
  - `GET /estados`: Lista estados.

- **Usuários**
  - `POST /clientes`: Cadastra um usuário.
  - `GET /clientes`: Lista usuários.

## Execução

1. Clone o repositório.
2. Abra o projeto no Android Studio.
3. Compile e execute o aplicativo.