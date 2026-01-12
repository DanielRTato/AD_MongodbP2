# Práctica 2 — Spring Boot + MongoDB

Este proyecto es la práctica 2: una pequeña aplicación Spring Boot que gestiona países y presidentes usando MongoDB. El objetivo de este README es explicar la arquitectura del proyecto, cómo compilar/ejecutar, y cómo preparar la entrega (archivo comprimido y PDF con la explicación).

---

## Resumen del proyecto

- Funcionalidad: importar datos desde JSON (presidentes y países) al iniciar la aplicación, listar, modificar y borrar los documentos en MongoDB.
- Tecnologías principales: Java 21, Spring Boot, Spring Data MongoDB, Gson (para leer JSON), Maven.

Contrato mínimo (qué hace cada cosa)
- Entradas: archivos JSON en `src/main/resources` (`presidentes.json`, `paises.json`).
- Salidas: datos guardados en MongoDB, logs por consola con las operaciones realizadas.
- Errores esperables: FileNotFound para recursos faltantes; se validan existencias de ficheros antes de leer.

---

## Estructura principal del proyecto

- `src/main/java/org/codewith/practica2_mongodb/`
  - `Practica2MongodbApplication.java` — clase principal Spring Boot; llama a la secuencia de import/ejecución en `@PostConstruct`.
  - `Secuencia.java` — orquesta la importación, listado, modificación y borrado de datos (usa los servicios).
  - `service/`
    - `PaisService.java` — operaciones sobre países (importar, listar, borrar, modificar).
    - `PresidenteService.java` — operaciones sobre presidentes (importar, listar, borrar).
  - `model/`
    - `Pais.java` — modelo con campos: `id`, `nome`, `organizacion`, `partidos` (List<String>), `presidenteId`.
    - `Presidente.java` — modelo con campos: `id`, `nome`, `idade`, `partido`.
  - `repository/`
    - `PaisRepository.java`, `PresidenteRepository.java` — interfaces Spring Data para MongoDB.
  - `config/` — configuración del proyecto (Mongo y OpenAPI, si aplica).

- `src/main/resources/`
  - `application.properties` — configuración de conexión a MongoDB.
  - `presidentes.json`, `paises.json` — datos de ejemplo que se importan al arrancar.

---

## Cambios y correcciones que se aplicaron (nota importante)

Durante la revisión del proyecto se realizaron estos cambios para corregir warnings y problemas prácticos:

1. `Pais.partidos` cambiado de `List[]` a `List<String>` para evitar raw types y mapear correctamente el JSON.
2. `PresidenteService.importarDesdeJSON` y `PaisService.importarDesdeJSON` leen recursos desde classpath (usando `ClassPathResource`) para que `presidentes.json` y `paises.json` sean encontrados cuando están en `src/main/resources`.
3. Se eliminó la dependencia no usada `PresidenteRepository` del constructor de `PaisService` (no se usaba en ese servicio).
4. Se eliminó un `;` innecesario dentro del try-with-resources en `PresidenteService`.
5. Se comentó `System.exit(200);` en `Practica2MongodbApplication` para evitar que la JVM termine inmediatamente al arrancar (útil durante pruebas).

Estas modificaciones son locales y están justificadas para mejorar seguridad de tipos y para que la importación funcione correctamente desde recursos empaquetados.

---

## Requisitos

- Java 17+ (probado con Java 21).
- Maven (o usar el wrapper `mvnw` incluido).
- MongoDB accesible en `localhost:27017` (si tu `application.properties` contiene credenciales, asegúrate de que coincidan). Por defecto el proyecto intenta conectarse a `localhost:27017`.

---

## Cómo compilar

En PowerShell, desde la raíz del proyecto:

```powershell
# Usando el wrapper incluido (recomendado)
.\mvnw -DskipTests package

# O con maven instalado
mvn -DskipTests package
```

Esto generará el artefacto en `target/`.

---

## Cómo ejecutar

Opción 1 — Ejecutar desde Maven (desarrollo):

```powershell
.\mvnw spring-boot:run
```

Opción 2 — Ejecutar el jar generado:

```powershell
java -jar target\practica2_mongodb-<version>.jar
```

Notas:
- Al arrancar, la aplicación ejecuta la secuencia `Secuencia.ejecutar()` (importa JSON, lista, modifica y borra). Si no quieres que lo haga, edita `Practica2MongodbApplication.java` y comenta la llamada a `secuencia.ejecutar()`.
- Si faltan los ficheros `presidentes.json` o `paises.json` en `src/main/resources`, la importación lanzará una excepción FileNotFound. Asegúrate de que ambos ficheros existan.

---

## Salida esperada / comprobación rápida

Al arrancar deberías ver por consola mensajes similares a:

- "--- Importar datos desde JSON ---"
- Listados de presidentes y países (por `listarPresidentes()` y `listarPaises()`).
- Mensajes confirmando actualización o borrado de datos.

Para comprobar manualmente:
1. Arranca la app.
2. Revisa la consola para ver que se importan los JSON.
3. Conecta a MongoDB y lista las colecciones `presidentes` y `paises`.

---

## Preparar la entrega

La entrega debe incluir:
- El código fuente completo del proyecto (todo el directorio del proyecto).
- Un PDF que explique el proyecto (arquitectura, decisiones, instrucciones para ejecutar, resultados de pruebas).

Pasos sugeridos para generar la entrega:

1. Generar el PDF `explicacion_proyecto.pdf` a partir de un archivo `EXPLICACION.md` o `README.md` (puedes usar `pandoc` o crear el PDF manualmente):

```powershell
# usando pandoc (si lo tienes instalado)
pandoc EXPLICACION.md -o explicacion_proyecto.pdf --pdf-engine=xelatex
```

2. Empaquetar todo en un ZIP:

```powershell
Compress-Archive -Path .\* -DestinationPath ..\entrega_practica2_mongodb.zip -Force
```

Asegúrate de que el ZIP incluya el PDF `explicacion_proyecto.pdf`.

---

## Sugerencia para el contenido del PDF (mínimo requerido)

Incluye en el PDF:
- Descripción breve del proyecto.
- Estructura del proyecto y explicación de los ficheros más importantes.
- Lista de cambios realizados y motivos (los puntos listados más arriba).
- Instrucciones de compilación y ejecución (comandos exactos usados).
- Capturas de salida o logs demostrando que la importación funciona.
- Posibles mejoras o limitaciones.

---

## Checklist final antes de entregar

- [ ] Compilar sin errores: `.\mvnw -DskipTests package`
- [ ] Verificar que `presidentes.json` y `paises.json` están en `src/main/resources`
- [ ] Ejecutar la app y comprobar importación (logs en consola)
- [ ] Crear `explicacion_proyecto.pdf` y colocarlo en la raíz del proyecto
- [ ] Generar `entrega_practica2_mongodb.zip` que incluya código y PDF

---

Si quieres, puedo:
- Generar `EXPLICACION.md` con un texto base para que lo conviertas a PDF.
- Crear automáticamente el ZIP de entrega que incluya el PDF (si me pides que cree el PDF desde `EXPLICACION.md`).

Dime cómo quieres que continúe: generar `EXPLICACION.md`, crear el PDF aquí (requiere pandoc/LaTeX), o crear directamente el ZIP de entrega.
