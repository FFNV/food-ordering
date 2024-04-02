Описание приложения "Product Cloud"

"Product Cloud" - это веб-приложение для управления продуктами и заказами. Оно разработано для упрощения процесса заказа продуктов для клиентов и управления этими заказами внутри системы.

Функциональность:

Регистрация и аутентификация пользователей: Пользователи могут зарегистрироваться в системе, чтобы создавать и управлять своими заказами. Для безопасной аутентификации используется Spring Security.

Управление продуктами: можно добавлять, удалять и обновлять информацию о продуктах.

Оформление заказов: Зарегистрированные пользователи могут создавать заказы, выбирая необходимые продукты из списка и указывая информацию о доставке, включая адрес, способ оплаты и дополнительные сведения.

Отображение заказов: Пользователи могут просматривать и отслеживать статус своих текущих и предыдущих заказов.

Скидки и промокоды: Система поддерживает использование скидочных кодов для получения скидок на заказы.

Для запуска проекта используй команду:
```shell
docker run -p8080:8080 ffnv/productcloud:0.0.6-SNAPSHOT 
```          