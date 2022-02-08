# Backend CashOnline

Ejercicio de CashOnline.

Fue creado utilizando como database Mysql 5.7.

## Instalacion

1. Crear la base de datos cash_db

```sql
CREATE DATABASE cash_db;
```

2. Configurar el usuario y contraseña para la base de datos editando el archivo de configuracion

```bash
nano src/main/resources/application.properties
```

3. Instalar las dependencias

```bash
mvn install
```

## Uso

Correr el siguiente comando levanta el proyecto en localhost:8080

```bash
mvn spring-boot:run
```

## Test
```bash
mvn test
```

## Notas

* La coleccion de Postman es la siguiente: postman.json. La cambie para agregar la url para cargar loans.
* El test de la paginacion de loans no coincide con el docx.
* Los post que crean recursos devuelven el codigo 201.
* Fun fact: la carga de datos se hace sola. Pero se ejecuta la segunda vez que levantan el proyecto.


