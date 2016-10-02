### Processo seletivo Hotmart - Desenvolvedor
Você deve criar uma aplicação web que realize upload de arquivos e liste todos os arquivos enviados utilizando a linguagem de programação Java. O site deve consumir APIs REST para realizar todo o trabalho.


### Tecnologias utilizadas nesse projeto

Backend
* Spring Boot 1.4
* Spring MVC 4 RESTFul Web Services
* OAuth 2.0 Authorization Framework
* Apache Commons FileUpload 1.3.2
* Apache Log4j 2
* Swagger 2.5
* Maven

Frontend
* AngularJS 1.4
* JQuery File Upload (https://blueimp.github.io/jQuery-File-Upload/)
* Bootstrap

Desenvolvido no Eclipse Mars.1 Release (4.5.1)

### Pontos importantes:

Os arquivos são enviados para o diretório `upload-dir` na raiz do projeto.

Para rodar a aplicação:
`$ mvn spring-boot:run`

Para acessar a aplicação: `localhost:8080/`
