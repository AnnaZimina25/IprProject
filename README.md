# IprProject
UI and API tests
Сборка проекта: mvn install (корень проекта)
Перед запуском тестов добавить пароли пользователей в переменные окружения: bigLebovski=password_value;heisenberg=password_value
Для запуска тестов по тегу mvn test -Dtags=tag_name_1,tag_name_2
Для формирования отчета mvn allure:serve
