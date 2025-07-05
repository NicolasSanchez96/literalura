# Literalura - Biblioteca de Libros y Autores

Literalura es una aplicación en Java con Spring Boot que permite buscar libros desde la API pública de Gutendex, guardar libros y autores en una base de datos local, y consultar estadísticas sobre ellos, como cantidad de libros por idioma y autores vivos en un año determinado.

---

## Funcionalidades

- Buscar libros por título desde la API Gutendex y guardarlos localmente.
- Listar todos los libros buscados y guardados.
- Mostrar autores de los libros guardados.
- Consultar qué autores estaban vivos en un año específico.
- Mostrar la cantidad de libros en un idioma determinado.

---

## Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- Base de datos H2 (en memoria) o configurada según el entorno
- API REST pública Gutendex para búsqueda de libros
- JSON processing con Jackson
- HTTP Client nativo de Java

---

## Cómo usar la aplicación

1. Clonar este repositorio y abrirlo en tu IDE favorito.
2. Configurar la base de datos si querés usar otra distinta a H2.
3. Ejecutar la aplicación Spring Boot (`LiteraluraApplication`).
4. Interactuar con la consola siguiendo el menú que aparece.
5. Elegir opciones para buscar libros, listar libros, autores vivos y estadísticas.

---

## Estructura principal del proyecto

- `model/` - Clases de entidad `Libro` y `Autor`.
- `repository/` - Interfaces de repositorios JPA para libros y autores.
- `service/` - Servicios para integrar con la API Gutendex y gestionar datos.
- `dto/` - Clases para mapear respuestas JSON de Gutendex.
- `LiteraluraApplication` - Clase principal que ejecuta el menú y la lógica de consola.

---

## Ejemplo de uso

Al correr la aplicación, verás un menú con opciones como:

=== Menú ===

Buscar libro por título

Listar todos los libros buscados

Listar autores de libros buscados

Listar autores vivos en un año

Mostrar cantidad de libros por idioma

Salir
Elija una opción:


Ingresá el número de la opción y seguí las instrucciones para probar funcionalidades.

---

## Notas

- La búsqueda de libros utiliza la API pública Gutendex.
- La base de datos guarda los libros y autores para consultas posteriores.
- La funcionalidad de autores vivos usa consultas derivadas para eficiencia.

---

## Autor

Nicolás Sanchez

---



