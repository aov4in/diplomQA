[![Build status](https://ci.appveyor.com/api/projects/status/re977hbtuimitg3o?svg=true)](https://ci.appveyor.com/project/aov4in/diplomqa)
## Документы
[План тестирования](/documentation/Plan.md)

[Отчет тестирования](/documentation/Report.md)

## Инструкция по запуску

1. Необходимо предварительно установить и настроить Intellij Idea, Docker (в зависимости от вашей системы настройка может отличаться) и плагин Docker к Intellij Idea

2. Скопировать данный репозиторий `git clone https://github.com/aov4in/diplomQA.git`

3. Перейти в папку, в которую вы склонировали репозиторий, командой `cd PATH`.

4. Запустить контейнеры командой `docker-compose up -d`

5. Запустить SUT контейнер в отдельном окне терминала.
   При тестировании на mysql командой `java -jar ./artifacts/aqa-shop.jar --spring.profiles.active=mysql`
   При тестировании на postgres командой `java -jar ./artifacts/aqa-shop.jar --spring.profiles.active=postgres`

6. В третьем окне терминала запустить тесты.
   Для запуска на mysql командой `gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app` (для windows) или `./gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app` (для линукс).
   Для запуска на postgres командой `gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app` (для windows) или `./gradlew test -Ddb.url=jdbc:postgresql://localhost:5432/app` (для линукс).

7. Дополнительно можно передать логин и пароль, добавив `-Duser=_user_` и `-Dpass=_pass_`, например `./gradlew test -Duser=_user_ -Dpass=_pass_`. Стандартные логин\пароль - **app\pass**.

8. Если вы хотите изменить страницу, на которой производятся тесты, дополнительно добавьте к `gradlew test` аттрибут `-Ddb.url=_url_`

9. Стандартно тесты запускаются без отображения браузера. Для возвращения отображения окна браузера необходимо добавить к `gradlew test` аттрибут `-Dselenide.headless=false`

10. Для включения Allure Report необходимо после первого теста ввести команду `gradlew allureReport`, после которой произойдет скачивание Allure.

11. Для отображение самого отчёта ввести команду `gradlew allureServe` (после запуска второго и последующих тестов можно пропустить 10 пункт).

## Сборка в Appveyor
Для проведения CI в Appveyor:

* Создаем файл .appveyor.yml в своем проекте
* Заливаем изменения сделанные в проекте на GitHub
* На сайте https://ci.appveyor.com авторизуемся и привязываем свой проект на GitHub к Appveyor
* Запускаем сборку https://ci.appveyor.com
* Т.к. есть падающие тесты, сборка проходит со статусом _failing_
  [![Build status](https://ci.appveyor.com/api/projects/status/re977hbtuimitg3o?svg=true)](https://ci.appveyor.com/project/aov4in/diplomqa)