# orders_service

# Перед запуском:
<ol>
    <li>Открыть PgAdmin</li>
    <li>Создать пользователя:<br> 
            username=postgres<br>
            password=postgres<br>
    </li>
    <li>Создать новую базу postgres</li>
    <li>Назначить owner`ом пользователя postgres</li>
    <li>Для запуска теста нужно создать базу test, c тем же owner`ом</li>        
</ol>
<br>
Если уже есть пустая ненужная база, то можно просто поправить файлы <br>
main/resources/application.properties <br>
test/resources/application.properties <br>
<br>

# Запуск приложения
<ul>
    <li>Либо из IDE. OrdersServiceApplication</li>
    <li>Либо переходим в корень проекта и выполняем mvn spring-boot:run</li>
</ul>

# Как пользоваться
Для добавления заданий для Call-центра:<br>
PUT /orders<br>
{<br>
    "id" : номер заказа (целое число)<br>
    "status" : статус заказа (IN_PROGRESS/DELIVERED/RESCHEDULE)<br>
}<br>

Для отображения заданий для Call-центра:<br>
GET /orders - возвращает список всех заказов<br>
GET /orders/filter?status={status} - возвращает список всех заказов по указанному статусу<br>
GET /orders/{id} - возвращает список всех заказов по указанному id<br>
GET /orders/search?id={id} - поиск по указанному id<br>