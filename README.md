# 🚀 Desafio Rest Assured
O repositório foi criado com o objetivo de realizar um desafio do curso de Quality Assurance da plataforma Digital Innovation One, explorando testes da API [Restful-booker](https://restful-booker.herokuapp.com/apidoc/index.html#api-Booking) manualmente utilizando a plataforma Postman, e de forma automatizada com o Rest Assured, além da geração de relatórios de testes utilizando o Allure Framework e a criação de novos endpoints com JSON Server.

## 💻 Enpoints

### [Restful-booker](https://restful-booker.herokuapp.com/apidoc/index.html#api-Booking)
- Auth
    
    - **POST** CreateToken: ``/auth``
        
        Criação de token de autenticação para que o usuário possa consiga ter acesso aos métodos PUT e DELETE.

- Booking

    - **GET** GetBookingIds: ``/booking``

        Retorna os IDs de todos os Bookings registrados na API.
        
    - **GET** GetBookingIds: ``/booking/:id``

        Retorna um Booking específico de acordo com o ID fornecido.

    - **POST** CreateBooking: ``/booking``

        Cria um novo Booking na API.

    - **PUT** UpdateBooking: ``/booking/:id``

        Atualiza integralmente um Booking existente na API.

    - **PATCH** PartialUpdateBooking: ``/booking/:id``

        Atualiza parcialmente um Booking existente na API.
    
    - **DELETE** DeleteBooking: ``/booking/:id``

        Exclui um Booking existente na API.

        
### JSON Server
- BLA

    - **GET** GetBookingIds: ``/booking``

        Retorna os IDs de todos os Bookings registrados na API.
        
    - **GET** GetBookingIds: ``/booking/:id``

        Retorna um Booking específico de acordo com o ID fornecido.
    

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