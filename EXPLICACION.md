# Explicación del proyecto — Práctica 2 (Spring Boot + MongoDB)

Autor: Practica2_Mongodb
Fecha: 2026-01-12

## 1. Resumen

Esta práctica implementa una pequeña aplicación en Java (Spring Boot) que gestiona dos entidades principales: Presidentes y Países. Los datos se importan desde archivos JSON empaquetados (`src/main/resources/presidentes.json` y `paises.json`) y se almacenan en MongoDB usando Spring Data MongoDB.

El objetivo de la entrega era: entregar el código fuente y un PDF que documente el proyecto (arquitectura, decisiones, cómo ejecutar y pruebas realizadas). Este documento (EXPLICACION.md) contiene la explicación que se debe convertir a PDF para la entrega.

---

## 2. Estructura del proyecto

- `pom.xml` — dependencias y configuración Maven.
- `src/main/java/org/codewith/practica2_mongodb/`
  - `Practica2MongodbApplication.java` — clase principal Spring Boot; ejecuta la secuencia de importación en `@PostConstruct`.
  - `Secuencia.java` — orquesta el flujo: borrar datos, importar desde JSON, listar, modificar, borrar y volver a listar.
  - `service/`
    - `PaisService.java` — operaciones sobre países (importar, listar, borrar, modificar organización).
    - `PresidenteService.java` — operaciones sobre presidentes (importar, listar, borrar).
  - `model/`
    - `Pais.java` — modelo (id, nome, organizacion, partidos, presidenteId).
    - `Presidente.java` — modelo (id, nome, idade, partido).
  - `repository/`
    - `PaisRepository.java`, `PresidenteRepository.java` — interfaces Spring Data MongoDB.
  - `config/` — configuración adicional (Mongo config y OpenAPI config si aplicable).
- `src/main/resources/`
  - `application.properties` — configuración de la conexión a MongoDB y propiedades de la app.
  - `presidentes.json`, `paises.json` — datos de ejemplo importados al arranque.

---

## 3. Modelado de datos

- Presidente
  - `id` (String) — identificador del documento.
  - `nome` (String) — nombre.
  - `idade` (int) — edad.
  - `partido` (String) — partido político.

- Pais
  - `id` (String) — identificador del documento.
  - `nome` (String) — nombre del país.
  - `organizacion` (String) — organización a la que pertenece (por ejemplo, ONU, UE, etc.).
  - `partidos` (List<String>) — lista de partidos políticos (cambio realizado para evitar raw types).
  - `presidenteId` (String) — referencia (id) al presidente actual.

---

## 4. Flujo de ejecución (qué hace la aplicación al arrancar)

1. Spring Boot arranca y carga el contexto.
2. `Practica2MongodbApplication` ejecuta, en `@PostConstruct`, el método `secuencia.ejecutar()`.
3. `Secuencia.ejecutar()` realiza las siguientes acciones (en orden):
   - Borra todas las colecciones/entidades actuales (llama a `borrarPasises()` y `borrarPresidentes()`).
   - Importa datos desde los JSON empaquetados (usando `PresidenteService.importarDesdeJSON("presidentes.json")` y `PaisService.importarDesdeJSON("paises.json")`).
   - Lista presidentes y países y los imprime por consola.
   - Modifica la organización de un país concreto (`modificarOrganizacionPais("pais3", "ASDESA")`).
   - Lista de nuevo y finalmente borra los datos otra vez.

Nota: la importación lee los recursos desde el classpath (`src/main/resources`), por lo que los ficheros deben estar empaquetados o presentes en `resources`.

---

## 5. Decisiones de diseño y correcciones aplicadas

Durante la revisión se aplicaron cambios concretos para mejorar robustez y evitar warnings del compilador/IDE:

- Parametrización de `Pais.partidos`: se cambió de `List[]` a `List<String>` (evita raw types y facilita el mapeo desde JSON).
- `PresidenteService.importarDesdeJSON` y `PaisService.importarDesdeJSON` ahora usan `ClassPathResource` para leer desde `src/main/resources` (funciona tanto en IDE como con el JAR empaquetado).
- Eliminación de una dependencia no usada en `PaisService` (el `PresidenteRepository` que no se empleaba allí) para eliminar warnings y simplificar la inyección de Spring.
- Se comentó `System.exit(200)` del `@PostConstruct` para evitar que la JVM termine inmediatamente durante el desarrollo y pruebas.
- Corrección de un `;` innecesario en un `try-with-resources`.

Motivaciones:
- Evitar warnings del compilador (raw types) y fallos en tiempo de ejecución por recursos no encontrados.
- Facilitar la ejecución local y dentro del JAR.

---




