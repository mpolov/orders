# orders

poc-like-app for spring-data way of working with database.

Key entities:
* [Product](https://github.com/mpolov/orders/blob/main/src/main/java/com/polov/shop/orders/model/Product.java)
* [Order](https://github.com/mpolov/orders/blob/main/src/main/java/com/polov/shop/orders/model/Order.java)

Business logic implemented in the app is placed in [OrderService](https://github.com/mpolov/orders/blob/main/src/main/java/com/polov/shop/orders/service/OrderService.java)

Developed in eclipse. Mapstruct used, an APT extension needed (e.g. m2e-apt, find it in eclipse marketplace).
Should be compatible with other mainstream IDEs, just import maven project from SCM repo.

Useful links (once app is running on localhost):
* OpenAPI: http://localhost:8080/v3/api-docs.yaml
* SwaggerUI: http://localhost:8080/swagger-ui/index.html
* H2 Console: http://localhost:8080/h2-console