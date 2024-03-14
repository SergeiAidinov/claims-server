![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Debian](https://img.shields.io/badge/Debian-D70A53?style=for-the-badge&logo=debian&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)

# Содержание
 - [Общее описание](#description)
 - [Аутентификация](#auth) 
 - [Properties](#properties)

<a name="description"></a> 
<h3>Общее описание</h3>
Взаимодействие пользователя с приложением осуществляется через Swagger, который открывается по адресу: http://localhost:8080/swagger-ui/index.html 
При первом запуске приложения с помощью Liquibase создается база данных, в которой помимо таблиц создаются функции для заполнения ее тестовыми данными. 
Также создаются три клиента – героя Алана Милна со следующими паролями и ролями:<br>

| Логин  |Пароль | Роли                 |
| :---:  | :---: |          :---:       |
| winnie | 1234  | USER, ADMIN, OPERATOR|
| eyeore | asdf  | USER:ADMIN           |    
| owl    | zxcv  | USER:OPERATOR        |   
|piglet  | qwer  | USER                 |   


Шифрование паролей при сохранении в базу данных не предусмотрено, поскольку не требовалось техническим заданием. 

<h4>Начальная страница запуска приложения:</h4>

![Screenshot](https://github.com/SergeiAidinov/images/blob/main/claims-server_1.png)


<a name="auth"></a> 
<h3>Аутентификация</h3>

Для того чтобы войти в приложение, необходимо вызвать эндпойнт login Контроллера аутентификации и ввести логин и пароль и кликнуть на кнопку “Execute”:<br>
<br>
![Screenshot](https://github.com/SergeiAidinov/images/blob/main/claims-server_2.png?raw=true)
<br><br>
После этого на экране отобразятся access token и refresh token, как это показано на рисунке ниже:<br>
<br><br>
![Screenshot](https://github.com/SergeiAidinov/images/blob/main/claims-server_3.png?raw=true)
<br><br>
Затем необходимо открыть окно аутентификации Swagger, ввести в нем слово “Bearer” через пробел вставить access token <b>без кавычек,</b> после чего кликнуть на кнопку “Authorize”, как это показано на рисунке ниже: <br>
<br><br>
![Screenshot](https://github.com/SergeiAidinov/images/blob/main/claims-server_4.png?raw=true)
<br><br>


## Properties

| Property Name                     | Description                                                                                                                                                                                                                           |
|-----------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| app.items-per-page                        | Количество записей на странице при пагинации                                                                                                                                                                                                       |
| app.token-retention-in-minutes | Время жизни access token в минутах                                                                                                                                                                                             |
| app.stopped-token-clean-interval-in-milliseconds                    | Частота очистки хранилища неиспользуемых токенов <br>в миллисекундах                                                                                                                                                                  |

