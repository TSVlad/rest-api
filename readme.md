Тестовое задание

Rest API для работы с пользователями и их телефонной книжкой.

Технологии: Java, Spring Boot, JUnit

Проект собирался в Intellij IDEA

Структура проекта:

![img](/images/18.PNG)

Так как в условии задачи прописан запрет на использование бд, 
dao здесь представляет собственный класс, хранящий всю информацию о 
пользователях и книгах

Entity - классы, которые в реальном проекте являлись бы сущностями бд. 
Пользователь, книга и запись в книге

Rest - Два rest контролера, один обрабатывает запросы по пользователям, 
другой - по записям в книгах

Service - сервисы для работы с пользователем и записями в книге

Запрос информации обо всех пользователях:

![img](/images/1.PNG)

Запрос на создание нового пользователя:

![img](/images/2.PNG)

![img](/images/3.PNG)

Запрос на редактирование существующего пользователя:

![img](/images/4.PNG)

![img](/images/5.PNG)

Запрос на получение пользователя по id:

![img](/images/6.PNG)

Запрос на удаление пользователя:

![img](/images/7.PNG)

![img](/images/8.PNG)

Запрос на получения всех записей из книжки пользователя:

![img](/images/9.PNG)

Запрос на получение записи из книги пользователя по id:

![img](/images/10.PNG)

Сохранение новой записи в книге пользователя:

![img](/images/11.PNG)

![img](/images/12.PNG)

Запрос на удаление записи из книги пользователя:

![img](/images/13.PNG)

![img](/images/14.PNG)

Запрос на получение пользователя по части имени:

![img](/images/15.PNG)

Запрос на получение записи в книжке по номеру:

![img](/images/16.PNG)

Для каждого метода были написаны unit тесты:

![img](/images/17.PNG)