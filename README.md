# Productos PostgreSQL - Spring Boot

Proyecto de ejemplo que conecta una aplicación Spring Boot con PostgreSQL, comprueba la conexión al iniciar y expone endpoints REST para consultar productos.

## Tecnologías

- Java 17 o superior
- Spring Boot 4.2.0
- Spring Web MVC (`spring-boot-starter-webmvc`)
- Spring Data JPA (`spring-boot-starter-data-jpa`)
- PostgreSQL JDBC Driver
- Maven 3.6.3 o superior

## Estructura del proyecto

```text
productos-postgresql/
├── database/
│   ├── 01_crear_base_datos.sql
│   └── 02_crear_tabla_y_datos.sql
├── src/
│   └── main/
│       ├── java/com/ejemplo/productos/
│       │   ├── ProductosApplication.java
│       │   ├── controller/ProductoController.java
│       │   ├── entity/Producto.java
│       │   └── repository/ProductoRepository.java
│       └── resources/application.properties
├── compose.yaml
├── pom.xml
└── README.md
```

## 1. Requisitos previos

Instala Java 17 o superior, Maven 3.6.3 o superior y PostgreSQL. Comprueba las instalaciones desde una terminal:

```bash
java -version
mvn -version
```

## 2. Crear la base de datos y cargar los productos

Puedes hacerlo con pgAdmin o con `psql`.

### Opción A: pgAdmin

1. Abre pgAdmin y conéctate a tu servidor PostgreSQL.
2. Abre el Query Tool conectado a la base de datos `postgres`.
3. Ejecuta el contenido de `database/01_crear_base_datos.sql` para crear `tienda_ropa`.
4. En el árbol de pgAdmin, selecciona la base de datos `tienda_ropa` y abre un nuevo Query Tool.
5. Ejecuta el contenido de `database/02_crear_tabla_y_datos.sql`.
6. Comprueba los registros ejecutando `SELECT * FROM productos;`.

> El segundo script ejecuta `TRUNCATE TABLE ... RESTART IDENTITY` antes de insertar los cinco ejemplos. Por ello, cada vez que lo ejecutes se reemplazarán los datos existentes de esa tabla.

### Opción B: psql

Ejecuta el primer archivo conectado a `postgres`:

```bash
psql -U postgres -d postgres -f database/01_crear_base_datos.sql
```

Después ejecuta el segundo conectado a `tienda_ropa`:

```bash
psql -U postgres -d tienda_ropa -f database/02_crear_tabla_y_datos.sql
```

## 3. Configurar las credenciales

Abre `src/main/resources/application.properties`. Por defecto, el proyecto utiliza:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tienda_ropa
spring.datasource.username=postgres
spring.datasource.password=postgres
```

Cambia el usuario y la contraseña para que coincidan con tu instalación de PostgreSQL. También puedes sobrescribir los valores usando estas variables de entorno:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

Por ejemplo, en PowerShell:

```powershell
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="tu_clave"
$env:DB_URL="jdbc:postgresql://localhost:5432/tienda_ropa"
```

En CMD:

```cmd
set DB_USERNAME=postgres
set DB_PASSWORD=tu_clave
set DB_URL=jdbc:postgresql://localhost:5432/tienda_ropa
```

No subas contraseñas reales a GitHub. Para un trabajo local, puedes dejar los valores de ejemplo y cambiarlos según tu entorno.

## 4. Ejecutar el proyecto

Desde la carpeta raíz (donde está `pom.xml`), ejecuta:

```bash
mvn spring-boot:run
```

Al iniciar, la clase `ProductosApplication` obtiene una conexión del `DataSource` y ejecuta una validación. Cuando la conexión funciona, la consola mostrará exactamente:

```text
Conexión a PostgreSQL exitosa
```

Si ocurre un problema, se imprimirá un mensaje como:

```text
Error al conectar con PostgreSQL: ...
```

La aplicación está configurada para no detenerse antes de ejecutar esta comprobación cuando PostgreSQL no está disponible. Revisa el mensaje, el estado del servidor, el puerto, el nombre de la base de datos y las credenciales.

También puedes generar el JAR y ejecutarlo:

```bash
mvn clean package
java -jar target/productos-postgresql-1.0.0.jar
```

## 5. Probar los endpoints

Con la aplicación iniciada, abre estas direcciones en el navegador o en Postman:

| Método | URL | Resultado |
|---|---|---|
| GET | `http://localhost:8080/api/productos` | Lista todos los productos |
| GET | `http://localhost:8080/api/productos/1` | Consulta el producto con ID 1 |

La respuesta se devuelve en formato JSON. Si el ID solicitado no existe, el segundo endpoint devuelve HTTP 404.

## 6. (Opcional) Ejecutar PostgreSQL con Docker

Si tienes Docker Compose instalado, puedes iniciar PostgreSQL con el archivo incluido:

```bash
docker compose up -d
```

El contenedor crea automáticamente la base `tienda_ropa` y el usuario `postgres` con contraseña `postgres`. A continuación, ejecuta el archivo `database/02_crear_tabla_y_datos.sql` dentro de esa base de datos y arranca Spring Boot con Maven.

Para detener el contenedor:

```bash
docker compose down
```

## 7. Subir el proyecto a GitHub

1. Crea un repositorio vacío en GitHub, por ejemplo `productos-postgresql`.
2. Abre una terminal en la carpeta del proyecto y ejecuta estos comandos, reemplazando la URL por la de tu repositorio:

```bash
git init
git add .
git commit -m "Proyecto Spring Boot conectado a PostgreSQL"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/productos-postgresql.git
git push -u origin main
```

Al finalizar, comparte el enlace de tu repositorio de GitHub.
