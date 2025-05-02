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