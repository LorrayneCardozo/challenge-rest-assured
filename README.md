# 🚀 Desafio Rest Assured
O repositório foi criado com o objetivo de realizar um desafio do curso de Quality Assurance da plataforma Digital Innovation One, explorando testes da API [Restful-booker](https://restful-booker.herokuapp.com/apidoc/index.html#api-Booking) manualmente utilizando a plataforma Postman, e de forma automatizada com o Rest Assured e JUnit, além da geração de relatórios de testes utilizando o Allure Framework e a criação de novos endpoints com JSON Server.


## ⚠️ Primeiros passos
Inicialmente, é necessário clonar o repositório utilizando o comando:

```bash
git clone https://github.com/LorrayneCardozo/challenge-rest-assured.git
```

## 🟠 Postman
A Collection ``Restful-booker.postman_collection.json`` criada na plataforma *Postman* para testar manualmente os endpoints do [Restful-booker](https://restful-booker.herokuapp.com/apidoc/index.html#api-Booking) pode ser encontrada na pasta:
```bash
cd postman
```

## ⚙️ Rodar suíte de testes
Para rodar a suíte contendo todos os testes criados, é necessário entrar na pasta:
```bash
cd project
```
E, em seguida, executar o comando:
```bash
mvn test
```

## 📊 Gerar relatório de testes com Allure Framework
Para gerar o relatório de testes utilizando o Allure Framework é necessário, após executar a suíte de testes, rodar os seguintes comandos:

```bash
mvn site
```

```bash
mvn allure:report
```
O relatório  ``index.html``, que pode ser visualizado com o navegador de sua preferência, estará dentro do diretório: 
```bash
project\target\site\allure-maven-plugin
```

## 💻 JSON Server

Para executar o JSON Server, é necessário entrar na pasta:
```bash
cd json_server
```

E, em seguida, executar o comendo:
```bash
npm run start-server
```
O servidor estará rodando na porta ``localhost:3000``.

### ◾️ Enpoints
        
- ``/users``

    Apenas o usuário consegue ler, alterar ou deletar suas informações.

    | Campo     | Tipo   | Descrição                         |
    | :-        | :-     | :-                                |
    | id        | Number | Identificador único de um usuário |
    | name      | String | Primeiro nome do usuário          |
    | lastname  | String | Sobrenome do usuário              |
    | birthdate | Date   | Data de nascimento do usuário     |
    | email     | String | E-mail do usuário                 |
    | password  | String | Senha criada pelo usuário         |
                
- ``/payment``

    Ninguém pode alterar ou deletar, mas todos podem ler.
    
    | Campo     | Tipo   | Descrição                                   |
    | :-        | :-     | :-                                          |
    | id        | Number | Identificador único de um tipo de pagamento |
    | type      | String | Tipo do pagamento                           |

- ``/booking``

    Todos podem ler, mas apenas o usuário (userId) pode alterar ou deletar dados.

    | Campo           | Tipo    | Descrição                                             |
    | :-              | :-      | :-                                                    |
    | id              | Number  | Identificador único de um booking                     |
    | userId          | Number  | Identificador único do usuário pertencente            |
    | payment         | Object  | Subobjeto que contém as informações de pagamento      |
    | paymentid       | Number  | Identificador único de um tipo de pagamento           |
    | totalprice      | Number  | Preço total do booking                                |
    | depositpaid     | Boolean | Se o depósito foi pago ou não                         |
    | bookingdates    | Object  | Subobjeto que contém as datas de check-in e check-out |
    | checkin         | Boolean | Data em que o hóspede está fazendo check-in           |
    | checkout        | Boolean | Data em que o hóspede está fazendo check-out          |
    | additionalneeds | Boolean | Qualquer outra necessidade que o hóspede tenha        |
    
