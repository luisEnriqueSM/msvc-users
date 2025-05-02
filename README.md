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