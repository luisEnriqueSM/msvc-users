# msvc-users


# Comandos Docker para levantar el contenedor de msvc-users


```bash
# Limpiar, generar Jar file y omitir tests
.\mvnw clean package -DskipTests

# Construir imagen
docker build -t msvc-users:v1 .

# Correr contenedor
docker run -d -P --name msvc-users --network springcloud msvc-users:v1

#Nueva instacia de products
docker run -d -P --network springcloud msvc-products:v1

# Bajar imagen de Mysql
docker pull mysql:8.0.42

# Correr contenedor de Mysql
docker run -d -p 3306:3306 --network springcloud -e MYSQL_ROOT_PASSWORD=sasa1234 -e MYSQL_DATABASE=db_springboot_cloud --name mysql8 mysql:8.0.42

# Construir imagen Zipkin
docker build -t zipkin-server:v1 .

# Correr contenedor Zipkin
docker run -d -p 9411:9411 --name zipkin-server --network springcloud -e STORAGE_TYPE=mysql -e MYSQL_USER=zipkin -e MYSQL_PASS=zipkin -e MYSQL_HOST=mysql8 zipkin-server:v1
```

## Componentes de relacionados de la solución:

- ### msvc-eureka-server: https://github.com/luisEnriqueSM/msvc-eureka-server
- ### msvc-products: https://github.com/luisEnriqueSM/msvc-products
- ### msvc-items: https://github.com/luisEnriqueSM/msvc-items
- ### msvc-gateway-server: https://github.com/luisEnriqueSM/msvc-gateway-server
- ### msvc-oauth: https://github.com/luisEnriqueSM/msvc-oauth
- ### msvc-users: https://github.com/luisEnriqueSM/msvc-users
- ### msvc-config-server: https://github.com/luisEnriqueSM/msvc-config-server
- ### msvc-docker-compose: https://github.com/luisEnriqueSM/msvc-docker-compose
