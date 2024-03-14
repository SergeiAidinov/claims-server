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
Также создаются три клиента – героя Алана Милна со следующими паролями и ролями. 
Шифрование паролей при собрании в базу данных не предусмотрено, поскольку не требовалось техническим заданием. 


![Screenshot](https://github.com/SergeiAidinov/images/blob/main/Screenshot.png?raw=true)


<a name="auth"></a> 
<h3>Аутентификация</h3>

Для того, чтобы войти в приложение, необходимо вызвать эндпойнт login Контроллера аутентификации и ввести логин и пароль, после чего на экране отобразятся access token и refresh token, как это показано на рисунке ниже:
![Screenshot]https://github.com/SergeiAidinov/images/blob/main/claims-server_2.png?raw=true)
Затем необходимо открыть окно аутентификации Swagger, ввести в нем слово “Bearer” через пробел вставить access token без кавычек, после чего кликнуть на кнопку “Auntheficate”, как это показано на рисунке ниже: 


## Properties

| Property Name                     | Description                                                                                                                                                                                                                           |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| app.apiKey                        | API KEY for using weather API                                                                                                                                                                                                         |
| app.cache.retention.timeInMinutes | Time (in minutes) between cache updates                                                                                                                                                                                               |
| app.cache.size                    | Max quantity of the cities, whose weather reports kept in cache                                                                                                                                                                       |
| app.cache.mode                    | Mode of cache update: <b>on_demand</b> (when user requests weather report) or <b>polling</b> (in equal periods, defined with property 'app.cache.retention.timeInMinutes'                                                             |
| app.language                      | language of weather reports:  af, al, ar, az, bg, ca, cz, da, de, el, en, eu, fa, fi, fr, gl, he, hi, hr, hu, id, it, ja, kr, la, lt, mk, no, nl, pl, pt, pt_br, ro, ru, sv, sk, sl, sp, es, sr, th, tr, ua, uk, vi, zh_cn, zh_tw, zu |
| app.units                         | System of units of measurement: <br/>standard, metric, imperial                                                                                                                                                                       |






Приложение открывается по адресу: http://localhost:8080/swagger-ui/index.html
