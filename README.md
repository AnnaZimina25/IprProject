# Тестовый проект для защиты ИПР IprProject
## UI тесты почтового сервиса mail.ru и API тесты
### Для запуска тестов:
*В IDE установите настройки maven*

**Working directory:**
```
{Your project directory}
```

**Environment variables:**
```
bigLebovski=LEBOVSKI_USER_PASSWORD;heisenberg=HEISENBERG_USER_PASSWORD
```

**Maven команды:**

1) Сборка проекта
````
mvn clean install -DskipTests
````
2) Запуск тестов по тегам
````
mvn test -Dtags=YOUR_TEST_TAG_1,YOUR_TEST_TAG_2
````
3) Формирование allure отчета
````
mvn allure:serve
````
