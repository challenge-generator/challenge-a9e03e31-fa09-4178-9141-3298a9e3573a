# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `src/main/java/com/trading/events/infrastructure/config/ResilienceConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/trading/events/EventsApplication.java` — `reactor.core.publisher`: El import reactor.core.publisher.Sinks pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/trading/events/EventsApplication.java` — `reactor.core.scheduler`: El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/trading/events/domain/port/EventProcessor.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/trading/events/application/EventProcessingService.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/trading/events/application/EventProcessingService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/trading/events/application/EventProcessingService.java` — `reactor.core.scheduler`: El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/trading/events/infrastructure/adapter/ReactiveEventProcessor.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/trading/events/infrastructure/adapter/ReactiveEventProcessor.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/trading/events/infrastructure/adapter/ReactiveEventProcessor.java` — `reactor.core.scheduler`: El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/trading/events/infrastructure/config/ThreadPoolConfig.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/trading/events/infrastructure/controller/EventController.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/trading/events/infrastructure/controller/EventController.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/trading/events/infrastructure/adapter/EventPublisher.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/trading/events/infrastructure/adapter/EventPublisher.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/trading/events/application/EventProcessingServiceTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/trading/events/infrastructure/adapter/ReactiveEventProcessorTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/trading/events/application/EventProcessingService.java` — `Event.eventId`: Se invoca `eventId` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/trading/events/application/EventProcessingService.java` — `Event.accountId`: Se invoca `accountId` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/trading/events/infrastructure/adapter/ReactiveEventProcessor.java` — `Event.eventId`: Se invoca `eventId` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/trading/events/infrastructure/adapter/ReactiveEventProcessor.java` — `Event.accountId`: Se invoca `accountId` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/trading/events/infrastructure/controller/EventController.java` — `BatchEventRequest.accountId`: Se invoca `accountId` sobre `BatchEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/trading/events/infrastructure/controller/EventController.java` — `BatchEventRequest.eventType`: Se invoca `eventType` sobre `BatchEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/trading/events/infrastructure/controller/EventController.java` — `EventProcessingService.processEventWithConcurrency`: Se invoca `processEventWithConcurrency` sobre `EventProcessingService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/trading/events/infrastructure/controller/EventController.java` — `BatchEventRequest.events`: Se invoca `events` sobre `BatchEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/trading/events/infrastructure/controller/EventController.java` — `BatchEventRequest.maxConcurrency`: Se invoca `maxConcurrency` sobre `BatchEventRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/trading/events/infrastructure/adapter/EventPublisher.java` — `Event.eventId`: Se invoca `eventId` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/trading/events/infrastructure/adapter/EventPublisher.java` — `Event.accountId`: Se invoca `accountId` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/trading/events/infrastructure/adapter/EventPublisher.java` — `Event.eventType`: Se invoca `eventType` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/trading/events/application/EventProcessingServiceTest.java` — `Event.status`: Se invoca `status` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/trading/events/application/EventProcessingServiceTest.java` — `Event.id`: Se invoca `id` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/trading/events/application/EventProcessingServiceTest.java` — `Event.accountId`: Se invoca `accountId` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/trading/events/application/EventProcessingServiceTest.java` — `EventProcessingService.processEventsInBatch`: Se invoca `processEventsInBatch` sobre `EventProcessingService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/trading/events/application/EventProcessingServiceTest.java` — `Event.eventType`: Se invoca `eventType` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/trading/events/infrastructure/adapter/ReactiveEventProcessorTest.java` — `Event.status`: Se invoca `status` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/trading/events/infrastructure/adapter/ReactiveEventProcessorTest.java` — `Event.id`: Se invoca `id` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/trading/events/infrastructure/adapter/ReactiveEventProcessorTest.java` — `Event.accountId`: Se invoca `accountId` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollador, Tecnología Java, Advanced

### Brecha de conocimiento
Gestiona los conceptos de concurrencia y paralelismo en su lenguaje de programación, con el fin de prevenir procesos bloqueantes. Identifica las diferencias entre un proceso y un hilo de ejecución.

### Misión / candidato
Candidato con experiencia avanzada, trabaja en proyectos Backend robustos, necesita dominar threading, sincronización y event loops para optimizar rendimiento en aplicaciones empresariales.

### Reto
- Tema: Gestión de Concurrencia y Paralelismo: Optimizando el Eventos
- Seniority: advanced-l2
- Tipo: practical
- Título: Optimización de Concurrencia y Paralelismo en Eventos
- Tiempo estimado: 8 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Identificación de Puntos de Concurrencia — objetivo: Mapear las áreas del sistema donde la concurrencia es crítica y puede introducir bloqueos. — entregable (NO resolver): Mapa de concurrencia con puntos críticos documentados y propuestas de solución.
- Fase 2: Implementación de Hilos y Sincronización — objetivo: Implementar hilos de ejecución para manejar eventos concurrentes y asegurar la sincronización adecuada. — entregable (NO resolver): Implementación de hilos y sincronización con pruebas de rendimiento.
- Fase 3: Optimización y Escalabilidad — objetivo: Optimizar la implementación para manejar un mayor volumen de eventos y asegurar la escalabilidad del sistema. — entregable (NO resolver): Implementación optimizada con pruebas de carga y resultados de rendimiento.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.trading.events</groupId>
    <artifactId>events-processing</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>events-processing</name>
    <description>Sistema de procesamiento de eventos para trading con manejo de concurrencia y paralelismo</description>

    <properties>
        <java.version>21</java.version>
        <reactor.version>3.6.8</reactor.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- Reactor -->
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-core</artifactId>
            <version>${reactor.version}</version>
        </dependency>

        <!-- Resilience4j -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-reactor</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-test</artifactId>
            <version>${reactor.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.11.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/trading/events/EventsApplication.java ===
package com.trading.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@SpringBootApplication
@EnableAsync
public class EventsApplication {
    private static final int MAX_CONCURRENT_EVENTS = 100;
    private final Semaphore eventSemaphore = new Semaphore(MAX_CONCURRENT_EVENTS);

    public static void main(String[] args) {
        SpringApplication.run(EventsApplication.class, args);
    }

    /**
     * Configura un scheduler dedicado para procesamiento de eventos con tamaño de pool fijo.
     * Esto asegura que los eventos se procesen en hilos dedicados sin bloquear el event loop principal.
     */
    @Bean
    public Executor eventProcessingExecutor() {
        return Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() * 2,
            r -> {
                Thread t = new Thread(r);
                t.setName("event-processing-thread-" + t.getId());
                t.setDaemon(true);
                return t;
            }
        );
    }

    /**
     * Sink reactivo para la emisión de eventos en un modelo publish-subscribe.
     * Permite que múltiples suscriptores consuman eventos de manera concurrente.
     */
    @Bean
    public Sinks.Many<Object> eventSink() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    /**
     * Semáforo para controlar la concurrencia en el procesamiento de eventos.
     * Limita el número de eventos que pueden procesarse simultáneamente para evitar saturar recursos.
     */
    @Bean
    public Semaphore eventSemaphore() {
        return eventSemaphore;
    }

    /**
     * Configura un scheduler para operaciones de I/O no bloqueantes.
     * Ideal para operaciones que involucran llamadas a servicios externos.
     */
    @Bean
    public Scheduler ioScheduler() {
        return Schedulers.newBoundedElastic(
            Runtime.getRuntime().availableProcessors() * 2,
            Integer.MAX_VALUE,
            "io-scheduler"
        );
    }
}

// === ARCHIVO: src/main/resources/application.yml ===
server:
  port: 8080
  shutdown: graceful

spring:
  main:
    web-application-type: reactive
    allow-bean-definition-overriding: true

  threads:
    virtual:
      enabled: true

logging:
  level:
    root: INFO
    org.springframework: INFO
    com.trading.events: DEBUG
    reactor.netty: INFO
    io.github.resilience4j: DEBUG

management:
  endpoints:
    web:
      exposure:
        include: health,metrics,prometheus,info
  endpoint:
    health:
      show-details: always
  metrics:
    tags:
      application: ${spring.application.name}

# Configuración de pools de hilos para manejo de concurrencia
app:
  concurrency:
    event-processing:
      thread-pool-size: 16
      queue-capacity: 100
      max-concurrent-events: 100
    io-operations:
      thread-pool-size: 8
      queue-capacity: 50
      max-concurrent-requests: 50

# Configuración de timeouts para operaciones críticas
resilience4j:
  circuitbreaker:
    configs:
      default:
        slidingWindowType: COUNT_BASED
        slidingWindowSize: 10
        failureRateThreshold: 50
        waitDurationInOpenState: 5s
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        registerHealthIndicator: true
    instances:
      eventProcessing:
        baseConfig: default
        failureRateThreshold: 30
        waitDurationInOpenState: 10s

  retry:
    configs:
      default:
        maxAttempts: 3
        waitDuration: 100ms
        enableExponentialBackoff: true
        exponentialBackoffMultiplier: 2
    instances:
      externalService:
        baseConfig: default

  bulkhead:
    configs:
      default:
        maxConcurrentCalls: 20
        maxWaitDuration: 10ms
    instances:
      eventProcessing:
        maxConcurrentCalls: 50
        maxWaitDuration: 5ms

  thread-pool-bulkhead:
    configs:
      default:
        maxThreadPoolSize: 10
        coreThreadPoolSize: 5
        queueCapacity: 20
    instances:
      eventProcessing:
        baseConfig: default
        maxThreadPoolSize: 20
        coreThreadPoolSize: 10
        queueCapacity: 50

// === ARCHIVO: src/main/java/com/trading/events/domain/model/Event.java ===
package com.trading.events.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa un evento de trading que debe ser procesado concurrentemente.
 * Cada evento contiene información sobre una operación financiera que afecta
 * cuentas y balances en tiempo real.
 */
public record Event(
    UUID eventId,
    String accountId,
    EventType eventType,
    BigDecimal amount,
    LocalDateTime timestamp,
    EventStatus status,
    String marketSource) {

    /**
     * Tipos de eventos soportados en el sistema.
     */
    public enum EventType {
        DEPOSIT,
        WITHDRAWAL,
        TRADE_EXECUTION,
        FEE_APPLIED,
        INTEREST_CALCULATION
    }

    /**
     * Estados posibles de un evento durante su procesamiento.
     */
    public enum EventStatus {
        RECEIVED,
        PROCESSING,
        COMPLETED,
        FAILED
    }

    /**
     * Crea un nuevo evento con el estado inicial RECEIVED.
     */
    public static Event createNewEvent(String accountId, EventType eventType,
                                     BigDecimal amount, String marketSource) {
        return new Event(
            UUID.randomUUID(),
            accountId,
            eventType,
            amount,
            LocalDateTime.now(),
            EventStatus.RECEIVED,
            marketSource
        );
    }

    /**
     * Marca el evento como PROCESSING si está en estado RECEIVED.
     * @return Nuevo evento con estado actualizado o lanza excepción si no es válido transicionar.
     * @throws IllegalStateException si el evento no está en estado RECEIVED
     */
    public Event markAsProcessing() {
        if (this.status != EventStatus.RECEIVED) {
            throw new IllegalStateException("Event cannot transition to PROCESSING from " + this.status);
        }
        return new Event(
            this.eventId,
            this.accountId,
            this.eventType,
            this.amount,
            this.timestamp,
            EventStatus.PROCESSING,
            this.marketSource
        );
    }

    /**
     * Marca el evento como COMPLETED si está en estado PROCESSING.
     * @return Nuevo evento con estado actualizado
     * @throws IllegalStateException si el evento no está en estado PROCESSING
     */
    public Event markAsCompleted() {
        if (this.status != EventStatus.PROCESSING) {
            throw new IllegalStateException("Event cannot transition to COMPLETED from " + this.status);
        }
        return new Event(
            this.eventId,
            this.accountId,
            this.eventType,
            this.amount,
            this.timestamp,
            EventStatus.COMPLETED,
            this.marketSource
        );
    }

    /**
     * Marca el evento como FAILED si está en estado PROCESSING.
     * @return Nuevo evento con estado actualizado
     * @throws IllegalStateException si el evento no está en estado PROCESSING
     */
    public Event markAsFailed() {
        if (this.status != EventStatus.PROCESSING) {
            throw new IllegalStateException("Event cannot transition to FAILED from " + this.status);
        }
        return new Event(
            this.eventId,
            this.accountId,
            this.eventType,
            this.amount,
            this.timestamp,
            EventStatus.FAILED,
            this.marketSource
        );
    }

    /**
     * Valida que el evento tenga datos consistentes para procesamiento.
     * @throws IllegalArgumentException si algún campo es inválido
     */
    public void validate() {
        if (accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException("Account ID cannot be null or empty");
        }
        if (eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (marketSource == null || marketSource.isBlank()) {
            throw new IllegalArgumentException("Market source cannot be null or empty");
        }
    }
}

// === ARCHIVO: src/main/java/com/trading/events/domain/port/EventProcessor.java ===
package com.trading.events.domain.port;

import com.trading.events.domain.model.Event;
import reactor.core.publisher.Mono;

/**
 * Puerto que define el contrato para procesar eventos de trading de manera concurrente.
 * Las implementaciones deben manejar la concurrencia y paralelismo de forma eficiente.
 */
public interface EventProcessor {

    /**
     * Procesa un evento de trading de forma asíncrona y concurrente.
     *
     * @param event Evento a procesar
     * @return Mono que emite el evento procesado o un error si ocurre alguna falla
     */
    Mono<Event> processEvent(Event event);

    /**
     * Procesa múltiples eventos en paralelo, limitando el grado de concurrencia.
     *
     * @param events Flujo de eventos a procesar
     * @param maxConcurrency Número máximo de eventos a procesar concurrentemente
     * @return Flujo de eventos procesados
     */
    default Mono<Void> processEventsInParallel(Iterable<Event> events, int maxConcurrency) {
        return Mono.fromRunnable(() -> {
            if (maxConcurrency <= 0) {
                throw new IllegalArgumentException("Max concurrency must be positive");
            }
        }).thenMany(reactor.core.publisher.Flux.fromIterable(events)
            .flatMap(this::processEvent, maxConcurrency)
        ).then();
    }

    /**
     * Verifica si el procesador está disponible para aceptar nuevos eventos.
     *
     * @return true si el procesador puede aceptar eventos, false en caso contrario
     */
    default boolean isAvailable() {
        return true;
    }
}

// === ARCHIVO: src/main/java/com/trading/events/domain/exception/EventProcessingException.java ===
package com.trading.events.domain.exception;


import com.trading.events.domain.model.Event;
/**
 * Excepción personalizada para manejar errores durante el procesamiento de eventos.
 * Se lanza cuando ocurre un error que impide completar el procesamiento de un evento.
 */
public class EventProcessingException extends RuntimeException {

    private final String eventId;
    private final String accountId;

    public EventProcessingException(String eventId, String accountId, String message) {
        super(message);
        this.eventId = eventId;
        this.accountId = accountId;
    }

    public EventProcessingException(String eventId, String accountId, String message, Throwable cause) {
        super(message, cause);
        this.eventId = eventId;
        this.accountId = accountId;
    }

    public String getEventId() {
        return eventId;
    }

    public String getAccountId() {
            return accountId;
    }

    /**
     * Crea una excepción para eventos que no pueden ser procesados por problemas de validación.
     */
    public static EventProcessingException forInvalidEvent(String eventId, String accountId, String reason) {
        return new EventProcessingException(
            eventId,
            accountId,
            "Event " + eventId + " for account " + accountId + " is invalid: " + reason
        );
    }

    /**
     * Crea una excepción para eventos que fallan durante el procesamiento.
     */
    public static EventProcessingException forProcessingFailure(String eventId, String accountId, Throwable cause) {
        return new EventProcessingException(
            eventId,
            accountId,
            "Failed to process event " + eventId + " for account " + accountId,
            cause
        );
    }

    /**
     * Crea una excepción para eventos que no pueden actualizar el estado de la cuenta.
     */
    public static EventProcessingException forAccountUpdateFailure(String eventId, String accountId, Throwable cause) {
        return new EventProcessingException(
            eventId,
            accountId,
            "Failed to update account " + accountId + " for event " + eventId,
            cause
        );
    }
}

// === ARCHIVO: src/main/java/com/trading/events/application/EventProcessingService.java ===
package com.trading.events.application;

import com.trading.events.domain.exception.EventProcessingException;
import com.trading.events.domain.model.Event;
import com.trading.events.domain.port.EventProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EventProcessingService {

    private static final Logger log = LoggerFactory.getLogger(EventProcessingService.class);
    private static final int DEFAULT_BATCH_SIZE = 100;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MS = 100;

    private final EventProcessor eventProcessor;
    private final ExecutorService dedicatedExecutor;
    private final Semaphore concurrencyLimiter;
    private final AtomicInteger activeEvents;
    private final AtomicLong totalProcessed;
    private final AtomicLong totalFailed;
    private final ConcurrentHashMap<String, Instant> processingStartTimes;

    public EventProcessingService(EventProcessor eventProcessor, ExecutorService executor) {
        this.eventProcessor = eventProcessor;
        this.dedicatedExecutor = executor;
        this.concurrencyLimiter = new Semaphore(50);
        this.activeEvents = new AtomicInteger(0);
        this.totalProcessed = new AtomicLong(0);
        this.totalFailed = new AtomicLong(0);
        this.processingStartTimes = new ConcurrentHashMap<>();
    }

    public Mono<Event> processEvent(Event event) {
        return Mono.fromCallable(() -> {
            if (!concurrencyLimiter.tryAcquire()) {
                log.warn("Concurrency limit reached for event: {}", event.eventId());
                throw EventProcessingException.forProcessingFailure(
                    event.eventId(),
                    event.accountId(),
                    new RejectedExecutionException("Concurrency limit exceeded")
                );
            }
            return event;
        })
        .doOnNext(e -> {
            activeEvents.incrementAndGet();
            processingStartTimes.put(e.eventId(), Instant.now());
            log.debug("Event {} started processing. Active: {}", e.eventId(), activeEvents.get());
        })
        .flatMap(eventProcessor::processEvent)
        .doOnSuccess(result -> {
            activeEvents.decrementAndGet();
            totalProcessed.incrementAndGet();
            processingStartTimes.remove(result.eventId());
            log.info("Event {} completed successfully. Active: {}, Total: {}",
                result.eventId(), activeEvents.get(), totalProcessed.get());
        })
        .doOnError(error -> {
            activeEvents.decrementAndGet();
            totalFailed.incrementAndGet();
            log.error("Event {} failed. Active: {}, Total Failed: {}",
                event.eventId(), activeEvents.get(), totalFailed.get(), error);
        })
        .doFinally(signal -> concurrencyLimiter.release())
        .retryWhen(Retry.backoff(MAX_RETRY_ATTEMPTS, Duration.ofMillis(RETRY_DELAY_MS))
            .filter(throwable -> throwable instanceof EventProcessingException)
            .doBeforeRetry(retrySignal -> {
                log.warn("Retrying event {} attempt {}", event.eventId(), retrySignal.totalRetries() + 1);
            }));
    }

    public Flux<Event> processBatch(List<Event> events) {
        return Flux.fromIterable(events)
            .flatMap(this::processEvent, 10)
            .collectList()
            .flatMapMany(processed -> {
                log.info("Batch processed: {} events", processed.size());
                return Flux.fromIterable(processed);
            });
    }

    public Mono<List<Event>> processWithParallelism(List<Event> events, int maxConcurrency) {
        return Flux.fromIterable(events)
            .parallel(maxConcurrency)
            .runOn(Schedulers.boundedElastic())
            .flatMap(this::processEvent)
            .sequential()
            .collectList();
    }

    public Mono<Void> processEventsAsync(List<Event> events) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (Event event : events) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    processEvent(event).block();
                } catch (Exception e) {
                    log.error("Async processing failed for event: {}", event.eventId(), e);
                }
            }, dedicatedExecutor);
            futures.add(future);
        }

        return Mono.fromFuture(
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
        ).then();
    }

    public Mono<Integer> processWithBackpressure(List<Event> events, int bufferSize) {
        return Flux.fromIterable(events)
            .buffer(bufferSize)
            .flatMap(batch -> processBatch(batch).then(Mono.just(batch.size())))
            .reduce(Integer::sum);
    }

    public int getActiveEventCount() {
        return activeEvents.get();
    }

    public long getTotalProcessed() {
        return totalProcessed.get();
    }

    public long getTotalFailed() {
        return totalFailed.get();
    }

    public ConcurrentHashMap<String, Instant> getProcessingTimes() {
        return new ConcurrentHashMap<>(processingStartTimes);
    }

    public void shutdown() {
        log.info("Shutting down EventProcessingService");
        dedicatedExecutor.shutdown();
        try {
            if (!dedicatedExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                dedicatedExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            dedicatedExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

// === ARCHIVO: src/main/java/com/trading/events/infrastructure/adapter/ReactiveEventProcessor.java ===
package com.trading.events.infrastructure.adapter;

import com.trading.events.domain.exception.EventProcessingException;
import com.trading.events.domain.model.Event;
import com.trading.events.domain.port.EventProcessor;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.RejectedExecutionException;
import java.util.function.Function;

@Component
public class ReactiveEventProcessor implements EventProcessor {

    private static final Logger log = LoggerFactory.getLogger(ReactiveEventProcessor.class);
    private static final int MAX_CONCURRENT_PROCESSING = 20;
    private static final Duration PROCESSING_TIMEOUT = Duration.ofSeconds(30);
    private static final int RETRY_ATTEMPTS = 2;

    private final io.github.resilience4j.circuitbreaker.CircuitBreaker circuitBreaker;

    public ReactiveEventProcessor(io.github.resilience4j.circuitbreaker.CircuitBreaker circuitBreaker) {
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    @CircuitBreaker(name = "eventProcessing", fallbackMethod = "processEventFallback")
    public Mono<Event> processEvent(Event event) {
        return Mono.just(event)
            .doOnSubscribe(s -> log.debug("Starting processing for event: {}", event.eventId()))
            .map(this::validateEvent)
            .map(this::markAsProcessing)
            .flatMap(this::executeProcessing)
            .timeout(PROCESSING_TIMEOUT)
            .doOnSuccess(result -> log.info("Event {} processed successfully at {}",
                result.eventId(), Instant.now()))
            .doOnError(error -> log.error("Event {} processing failed: {}",
                event.eventId(), error.getMessage(), error));
    }

    private Event validateEvent(Event event) {
        try {
            event.validate();
        } catch (IllegalArgumentException e) {
            throw EventProcessingException.forInvalidEvent(
                event.eventId(),
                event.accountId(),
                e.getMessage()
            );
        }
        return event;
    }

    private Event markAsProcessing(Event event) {
        return event.markAsProcessing();
    }

    private Mono<Event> executeProcessing(Event event) {
        return Mono.fromCallable(() -> {
            simulateProcessingWork(event);
            return event.markAsCompleted();
        })
        .subscribeOn(Schedulers.boundedElastic())
        .publishOn(Schedulers.parallel())
        .retryWhen(reactor.retry.Retry.backoff(RETRY_ATTEMPTS, Duration.ofMillis(50))
            .filter(throwable -> throwable instanceof RejectedExecutionException));
    }

    private void simulateProcessingWork(Event event) {
        try {
            Thread.sleep(10 + (long) (Math.random() * 50));
            if (Math.random() < 0.01) {
                throw new RuntimeException("Random processing error");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new EventProcessingException(
                event.eventId(),
                event.accountId(),
                "Processing interrupted",
                e
            );
        }
    }

    private Mono<Event> processEventFallback(Event event, Throwable cause) {
        log.warn("Circuit breaker fallback triggered for event: {}, cause: {}",
            event.eventId(), cause.getMessage());
        return Mono.just(event.markAsFailed());
    }

    @Override
    public Mono<Void> processEventsInParallel(Iterable<Event> events, int maxConcurrency) {
        return Flux.fromIterable(events)
            .flatMap(this::processEvent, maxConcurrency)
            .then()
            .doOnSuccess(v -> log.info("All events processed in parallel with concurrency: {}", maxConcurrency));
    }

    public Flux<Event> processEventStream(Flux<Event> eventStream) {
        return eventStream
            .window(Duration.ofSeconds(1))
            .flatMap(window -> window
                .flatMap(this::processEvent)
                .collectList()
                .filter(list -> !list.isEmpty())
                .flatMapMany(processed -> {
                    log.info("Processed batch of {} events", processed.size());
                    return Flux.fromIterable(processed);
                }));
    }

    public Mono<Event> processWithCircuitBreaker(Event event) {
        return Mono.defer(() -> processEvent(event))
            .transformDeferred(mono ->
                io.github.resilience4j.reactor.MonoCircuitBreaker.of(circuitBreaker, mono)
            );
    }

    public Mono<Event> processWithRetryAndCircuitBreaker(Event event) {
        return processEvent(event)
            .retryWhen(reactor.retry.Retry.backoff(3, Duration.ofSeconds(1))
                .filter(throwable -> throwable instanceof EventProcessingException)
                .doBeforeRetry(signal -> {
                    log.info("Retrying event {} attempt {}",
                        event.eventId(), signal.totalRetries() + 1);
                }))
            .transformDeferred(mono ->
                io.github.resilience4j.reactor.MonoCircuitBreaker.of(circuitBreaker, mono)
            );
    }

    public Function<Event, Mono<Event>> createProcessingPipeline() {
        return event -> processEvent(event)
            .subscribeOn(Schedulers.boundedElastic())
            .publishOn(Schedulers.parallel())
            .cache(1);
    }

    @Override
    public boolean isAvailable() {
        return circuitBreaker.getState() != io.github.resilience4j.circuitbreaker.CircuitBreaker.State.OPEN;
    }
}

// === ARCHIVO: src/main/java/com/trading/events/infrastructure/config/ThreadPoolConfig.java ===
package com.trading.events.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;
import java.util.stream.IntStream;

@Configuration
@EnableAsync
public class ThreadPoolConfig {

    private static final Logger log = LoggerFactory.getLogger(ThreadPoolConfig.class);

    @Bean(name = "eventProcessingExecutor", destroyMethod = "shutdown")
    public ExecutorService eventProcessingExecutor() {
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
        int maxPoolSize = corePoolSize * 4;
        long keepAliveTime = 60L;

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            new ThreadFactory() {
                private final AtomicInteger counter = new AtomicInteger(1);
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r, "event-processor-" + counter.getAndIncrement());
                    thread.setDaemon(true);
                    thread.setPriority(Thread.NORM_PRIORITY);
                    return thread;
                }
            },
            new ThreadPoolExecutor.CallerRunsPolicy()
        );

        executor.allowCoreThreadTimeOut(true);
        log.info("EventProcessingExecutor initialized with core={}, max={}", corePoolSize, maxPoolSize);
        return executor;
    }

    @Bean(name = "ioBoundExecutor", destroyMethod = "shutdown")
    public ExecutorService ioBoundExecutor() {
        int corePoolSize = 50;
        int maxPoolSize = 200;
        long keepAliveTime = 30L;

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(5000),
            new ThreadFactory() {
                private final AtomicInteger counter = new AtomicInteger(1);
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r, "io-worker-" + counter.getAndIncrement());
                    thread.setDaemon(true);
                    return thread;
                }
            },
            new ThreadPoolExecutor.AbortPolicy()
        );

        log.info("IO-bound executor initialized with core={}, max={}", corePoolSize, maxPoolSize);
        return executor;
    }

    @Bean(name = "scheduledExecutor", destroyMethod = "shutdown")
    public ScheduledExecutorService scheduledExecutor() {
        int poolSize = 4;

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(poolSize, r -> {
            Thread thread = new Thread(r, "scheduled-worker");
            thread.setDaemon(true);
            return thread;
        });

        log.info("Scheduled executor initialized with pool size={}", poolSize);
        return executor;
    }

    @Bean(name = "asyncTaskExecutor")
    public ThreadPoolTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("async-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();

        log.info("Async task executor initialized");
        return executor;
    }

    @Bean(name = "parallelProcessingExecutor")
    public ForkJoinPool parallelProcessingExecutor() {
        int parallelism = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(
            parallelism,
            ForkJoinPool.defaultForkJoinWorkerThreadFactory,
            null,
            true
        );
        log.info("ForkJoinPool initialized with parallelism={}", parallelism);
        return pool;
    }

    @ConfigurationProperties(prefix = "thread-pool")
    public static class ThreadPoolProperties {
        private int eventCoreSize = 8;
        private int eventMaxSize = 32;
        private int ioCoreSize = 50;
        private int ioMaxSize = 200;
        private int queueCapacity = 1000;
        private long keepAliveSeconds = 60;

        public int getEventCoreSize() { return eventCoreSize; }
        public void setEventCoreSize(int eventCoreSize) { this.eventCoreSize = eventCoreSize; }
        public int getEventMaxSize() { return eventMaxSize; }
        public void setEventMaxSize(int eventMaxSize) { this.eventMaxSize = eventMaxSize; }
        public int getIoCoreSize() { return ioCoreSize; }
        public void setIoCoreSize(int ioCoreSize) { this.ioCoreSize = ioCoreSize; }
        public int getIoMaxSize() { return ioMaxSize; }
        public void setIoMaxSize(int ioMaxSize) { this.ioMaxSize = ioMaxSize; }
        public int getQueueCapacity() { return queueCapacity; }
        public void setQueueCapacity(int queueCapacity) { this.queueCapacity = queueCapacity; }
        public long getKeepAliveSeconds() { return keepAliveSeconds; }
        public void setKeepAliveSeconds(long keepAliveSeconds) { this.keepAliveSeconds = keepAliveSeconds; }
    }

    @Bean
    public ThreadPoolProperties threadPoolProperties() {
        return new ThreadPoolProperties();
    }
}

// === ARCHIVO: src/main/java/com/trading/events/infrastructure/controller/EventController.java ===
package com.trading.events.infrastructure.controller;

import com.trading.events.domain.model.Event;
import com.trading.events.domain.model.Event.EventStatus;
import com.trading.events.domain.model.Event.EventType;
import com.trading.events.domain.port.EventProcessor;
import com.trading.events.domain.exception.EventProcessingException;
import com.trading.events.application.EventProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    private final EventProcessingService eventProcessingService;
    private final EventProcessor eventProcessor;
    private final AtomicLong eventsReceived = new AtomicLong(0);
    private final AtomicLong eventsProcessed = new AtomicLong(0);
    private final AtomicLong eventsFailed = new AtomicLong(0);
    private final ConcurrentHashMap<String, Event> processedEvents = new ConcurrentHashMap<>();

    public EventController(EventProcessingService eventProcessingService, EventProcessor eventProcessor) {
        this.eventProcessingService = eventProcessingService;
        this.eventProcessor = eventProcessor;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Map<String, Object>>> receiveEvent(@RequestBody EventRequest request) {
        eventsReceived.incrementAndGet();
        log.info("Evento recibido: accountId={}, type={}", request.accountId(), request.eventType());

        return Mono.fromCallable(() -> Event.createNewEvent(request.accountId(), 
                EventType.valueOf(request.eventType())))
            .flatMap(event -> {
                try {
                    event.validate();
                } catch (IllegalArgumentException e) {
                    eventsFailed.incrementAndGet();
                    return Mono.error(new EventProcessingException(
                        event.eventId(), event.accountId(), "Invalid event: " + e.getMessage()));
                }
                return eventProcessingService.processEventWithConcurrency(event);
            })
            .doOnSuccess(result -> {
                eventsProcessed.incrementAndGet();
                processedEvents.put(result.eventId(), result);
                log.info("Evento procesado exitosamente: {}", result.eventId());
            })
            .doOnError(error -> {
                eventsFailed.incrementAndGet();
                log.error("Error al procesar evento: {}", error.getMessage());
            })
            .map(event -> ResponseEntity.ok(Map.of(
                "eventId", event.eventId(),
                "accountId", event.accountId(),
                "status", event.status().toString(),
                "timestamp", event.timestamp().toString()
            )))
            .onErrorResume(EventProcessingException.class, ex -> 
                Mono.just(ResponseEntity.badRequest().body(Map.of(
                    "error", ex.getMessage(),
                    "eventId", ex.getEventId() != null ? ex.getEventId() : "unknown",
                    "accountId", ex.getAccountId() != null ? ex.getAccountId() : "unknown"
                ))));
    }

    @PostMapping(value = "/batch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Map<String, Object>>> receiveBatch(@RequestBody BatchEventRequest request) {
        log.info("Recibido batch de {} eventos con concurrencia máxima {}", 
                request.events().size(), request.maxConcurrency());

        int maxConcurrency = request.maxConcurrency() > 0 ? request.maxConcurrency() : 10;

        return Flux.fromIterable(request.events())
            .flatMap(eventReq -> Mono.fromCallable(() -> 
                Event.createNewEvent(eventReq.accountId(), EventType.valueOf(eventReq.eventType()))
            ).flatMap(event -> {
                try {
                    event.validate();
                } catch (IllegalArgumentException e) {
                    return Mono.error(new EventProcessingException(
                        event.eventId(), event.accountId(), "Invalid event: " + e.getMessage()));
                }
                return eventProcessingService.processEventWithConcurrency(event);
            }), maxConcurrency)
            .collectList()
            .doOnSuccess(results -> {
                long successCount = results.stream().filter(e -> e.status() == EventStatus.COMPLETED).count();
                long failedCount = results.size() - successCount;
                eventsReceived.addAndGet(results.size());
                eventsProcessed.addAndGet(successCount);
                eventsFailed.addAndGet(failedCount);
                results.forEach(e -> processedEvents.put(e.eventId(), e));
                log.info("Batch procesado: {} exitosos, {} fallidos", successCount, failedCount);
            })
            .map(results -> ResponseEntity.ok(Map.of(
                "total", results.size(),
                "processed", results.stream().filter(e -> e.status() == EventStatus.COMPLETED).count(),
                "failed", results.stream().filter(e -> e.status() == EventStatus.FAILED).count()
            )));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Map<String, Object>>> getEventStatus(@RequestParam String eventId) {
        return Mono.fromCallable(() -> processedEvents.get(eventId))
            .map(event -> {
                if (event == null) {
                    return ResponseEntity.notFound().<Map<String, Object>>build();
                }
                return ResponseEntity.ok(Map.of(
                    "eventId", event.eventId(),
                    "accountId", event.accountId(),
                    "type", event.eventType().toString(),
                    "status", event.status().toString(),
                    "timestamp", event.timestamp().toString()
                ));
            });
    }

    @GetMapping(value = "/monitor", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Map<String, Object>>> getMonitorStats() {
        return Mono.just(ResponseEntity.ok(Map.of(
            "eventsReceived", eventsReceived.get(),
            "eventsProcessed", eventsProcessed.get(),
            "eventsFailed", eventsFailed.get(),
            "processedEventsCount", processedEvents.size(),
            "timestamp", Instant.now().toString()
        )));
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Map<String, Object>> eventStream() {
        return Flux.interval(java.time.Duration.ofSeconds(2))
            .map(tick -> Map.of(
                "eventsReceived", eventsReceived.get(),
                "eventsProcessed", eventsProcessed.get(),
                "eventsFailed", eventsFailed.get(),
                "timestamp", Instant.now().toString()
            ));
    }

    public record EventRequest(String accountId, String eventType) {}

    public record BatchEventRequest(java.util.List<EventRequest> events, int maxConcurrency) {}
}

// === ARCHIVO: src/main/java/com/trading/events/infrastructure/adapter/EventPublisher.java ===
package com.trading.events.infrastructure.adapter;


import com.trading.events.domain.model.EventType;
import com.trading.events.domain.model.Event;
import com.trading.events.domain.model.Event.EventStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

@Component
public class EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(EventPublisher.class);

    private final Sinks.Many<Event> eventSink;
    private final CopyOnWriteArrayList<PublishedEvent> publishedEvents;
    private final AtomicLong totalPublished;
    private final AtomicLong totalAcknowledged;
    private volatile Consumer<Event> eventAcknowledgmentHandler;

    public EventPublisher() {
        this.eventSink = Sinks.many().multicast().directBestEffort();
        this.publishedEvents = new CopyOnWriteArrayList<>();
        this.totalPublished = new AtomicLong(0);
        this.totalAcknowledged = new AtomicLong(0);
    }

    public Mono<Boolean> publishEvent(Event event) {
        return Mono.fromRunnable(() -> {
            log.debug("Publicando evento: {}", event.eventId());
            Sinks.EmitResult result = eventSink.emitNext(event, Sinks.EmitFailureHandler.FAIL_FAST);
            
            if (result == Sinks.EmitResult.OK) {
                totalPublished.incrementAndGet();
                publishedEvents.add(new PublishedEvent(
                    event.eventId(), 
                    event.accountId(), 
                    event.eventType(), 
                    Instant.now(),
                    true
                ));
                log.info("Evento publicado exitosamente: {}", event.eventId());
            } else {
                log.warn("Falló la publicación del evento: {}, causa: {}", event.eventId(), result);
                publishedEvents.add(new PublishedEvent(
                    event.eventId(), 
                    event.accountId(), 
                    event.eventType(), 
                    Instant.now(),
                    false
                ));
            }
        });
    }

    public Mono<Boolean> publishEvents(List<Event> events) {
        return Flux.fromIterable(events)
            .flatMap(this::publishEvent, 5)
            .then(Mono.just(true))
            .onErrorResume(e -> {
                log.error("Error en publicación masiva: {}", e.getMessage());
                return Mono.just(false);
            });
    }

    public Flux<Event> eventStream() {
        return eventSink.asFlux();
    }

    public Mono<Void> acknowledgeEvent(String eventId) {
        return Mono.fromRunnable(() -> {
            totalAcknowledged.incrementAndGet();
            if (eventAcknowledgmentHandler != null) {
                publishedEvents.stream()
                    .filter(e -> e.eventId().equals(eventId))
                    .findFirst()
                    .ifPresent(e -> {
                        Event event = Event.createNewEvent(e.accountId(), e.eventType());
                        eventAcknowledgmentHandler.accept(event.markAsCompleted());
                    });
            }
            log.debug("Evento reconocido: {}", eventId);
        });
    }

    public void setAcknowledgmentHandler(Consumer<Event> handler) {
        this.eventAcknowledgmentHandler = handler;
    }

    public Mono<PublisherStats> getStats() {
        return Mono.fromCallable(() -> new PublisherStats(
            totalPublished.get(),
            totalAcknowledged.get(),
            publishedEvents.size(),
            eventSink.currentSubscriberCount()
        ));
    }

    public Mono<List<PublishedEvent>> getRecentEvents(int limit) {
        return Mono.fromCallable(() -> 
            publishedEvents.stream()
                .skip(Math.max(0, publishedEvents.size() - limit))
                .toList()
        );
    }

    public record PublishedEvent(
        String eventId,
        String accountId,
        com.trading.events.domain.model.Event.EventType eventType,
        Instant publishedAt,
        boolean success
    ) {}

    public record PublisherStats(
        long totalPublished,
        long totalAcknowledged,
        int queuedEvents,
        int activeSubscribers
    ) {}
}

// === ARCHIVO: src/main/java/com/trading/events/infrastructure/config/ResilienceConfig.java ===
package com.trading.events.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .failureRateThreshold(50)
            .waitDurationInOpenState(Duration.ofSeconds(30))
            .slidingWindowSize(10)
            .minimumNumberOfCalls(5)
            .permittedNumberOfCallsInHalfOpenState(3)
            .automaticTransitionFromOpenToHalfOpenEnabled(true)
            .build();
        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
            .maxAttempts(3)
            .waitDuration(Duration.ofMillis(500))
            .retryExceptions(Exception.class)
            .build();
        return RetryRegistry.of(config);
    }
}

// === ARCHIVO: src/test/java/com/trading/events/application/EventProcessingServiceTest.java ===
package com.trading.events.application;

import com.trading.events.domain.exception.EventProcessingException;
import com.trading.events.domain.model.Event;
import com.trading.events.domain.model.Event.EventStatus;
import com.trading.events.domain.model.Event.EventType;
import com.trading.events.domain.port.EventProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para EventProcessingService - Concurrencia y Sincronización")
class EventProcessingServiceTest {

    @Mock
    private EventProcessor eventProcessor;

    @InjectMocks
    private EventProcessingService eventProcessingService;

    private Event testEvent;
    private Event testEvent2;
    private Event testEvent3;

    @BeforeEach
    void setUp() {
        testEvent = Event.createNewEvent("ACC-001", EventType.ACCOUNT_UPDATE);
        testEvent2 = Event.createNewEvent("ACC-002", EventType.TRADE_EXECUTION);
        testEvent3 = Event.createNewEvent("ACC-003", EventType.RISK_ALERT);
    }

    @Test
    @DisplayName("Debe procesar un evento individual correctamente")
    void shouldProcessSingleEventSuccessfully() {
        when(eventProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    Event event = invocation.getArgument(0);
                    return Mono.just(event.markAsCompleted());
                });

        Mono<Event> result = eventProcessingService.processEvent(testEvent);

        StepVerifier.create(result)
                .expectNextMatches(event -> event.status() == EventStatus.COMPLETED)
                .verifyComplete();

        verify(eventProcessor, times(1)).processEvent(any(Event.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el procesamiento falla")
    void shouldThrowExceptionWhenProcessingFails() {
        when(eventProcessor.processEvent(any(Event.class)))
                .thenReturn(Mono.error(new EventProcessingException(
                        testEvent.id(),
                        testEvent.accountId(),
                        "Error en procesamiento"
                )));

        StepVerifier.create(eventProcessingService.processEvent(testEvent))
                .expectError(EventProcessingException.class)
                .verify();
    }

    @Test
    @DisplayName("Debe procesar múltiples eventos en paralelo")
    void shouldProcessMultipleEventsInParallel() throws InterruptedException {
        when(eventProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    Thread.sleep(50);
                    Event event = invocation.getArgument(0);
                    return Mono.just(event.markAsCompleted());
                });

        List<Event> events = List.of(testEvent, testEvent2, testEvent3);
        CountDownLatch latch = new CountDownLatch(events.size());
        AtomicInteger processedCount = new AtomicInteger(0);

        long startTime = System.currentTimeMillis();

        Flux.fromIterable(events)
                .flatMap(event -> eventProcessingService.processEvent(event)
                        .doOnSuccess(e -> {
                            processedCount.incrementAndGet();
                            latch.countDown();
                        })
                        .onErrorResume(e -> Mono.empty()))
                .blockLast();

        latch.await(5, TimeUnit.SECONDS);
        long duration = System.currentTimeMillis() - startTime;

        assertThat(processedCount.get()).isEqualTo(3);
        assertThat(duration).isLessThan(200);
        verify(eventProcessor, times(3)).processEvent(any(Event.class));
    }

    @Test
    @DisplayName("Debe respetar el límite de concurrencia configurado")
    void shouldRespectConcurrencyLimit() {
        AtomicInteger concurrentExecutions = new AtomicInteger(0);
        AtomicInteger maxConcurrent = new AtomicInteger(0);

        when(eventProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    int current = concurrentExecutions.incrementAndGet();
                    maxConcurrent.set(Math.max(maxConcurrent.get(), current));
                    Thread.sleep(100);
                    concurrentExecutions.decrementAndGet();
                    Event event = invocation.getArgument(0);
                    return Mono.just(event.markAsCompleted());
                });

        List<Event> events = List.of(
                Event.createNewEvent("ACC-001", EventType.ACCOUNT_UPDATE),
                Event.createNewEvent("ACC-002", EventType.TRADE_EXECUTION),
                Event.createNewEvent("ACC-003", EventType.RISK_ALERT),
                Event.createNewEvent("ACC-004", EventType.ACCOUNT_UPDATE),
                Event.createNewEvent("ACC-005", EventType.TRADE_EXECUTION)
        );

        eventProcessingService.processEventsInBatch(events)
                .as(StepVerifier::create)
                .expectNextCount(5)
                .verifyComplete();

        assertThat(maxConcurrent.get()).isLessThanOrEqualTo(3);
    }

    @Test
    @DisplayName("Debe validar eventos antes del procesamiento")
    void shouldValidateEventsBeforeProcessing() {
        Event invalidEvent = Event.createNewEvent("", EventType.UNKNOWN);

        assertThatThrownBy(() -> eventProcessingService.processEvent(invalidEvent))
                .isInstanceOf(IllegalArgumentException.class);

        verify(eventProcessor, never()).processEvent(any(Event.class));
    }

    @Test
    @DisplayName("Debe manejar errores de cuenta correctamente")
    void shouldHandleAccountErrorsCorrectly() {
        when(eventProcessor.processEvent(any(Event.class)))
                .thenReturn(Mono.error(EventProcessingException.forAccountUpdateFailure(
                        testEvent.id(),
                        testEvent.accountId(),
                        new RuntimeException("Conexión fallida")
                )));

        StepVerifier.create(eventProcessingService.processEvent(testEvent))
                .expectErrorMatches(error ->
                        error instanceof EventProcessingException &&
                        ((EventProcessingException) error).getAccountId().equals(testEvent.accountId()))
                .verify();
    }

    @Test
    @DisplayName("Debe procesar eventos con diferentes tipos correctamente")
    void shouldProcessEventsWithDifferentTypes() {
        when(eventProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0).markAsCompleted()));

        List<Event> events = List.of(
                Event.createNewEvent("ACC-001", EventType.ACCOUNT_UPDATE),
                Event.createNewEvent("ACC-002", EventType.TRADE_EXECUTION),
                Event.createNewEvent("ACC-003", EventType.RISK_ALERT)
        );

        Flux<Event> results = eventProcessingService.processEventsInBatch(events);

        StepVerifier.create(results)
                .assertNext(event -> assertThat(event.eventType()).isEqualTo(EventType.ACCOUNT_UPDATE))
                .assertNext(event -> assertThat(event.eventType()).isEqualTo(EventType.TRADE_EXECUTION))
                .assertNext(event -> assertThat(event.eventType()).isEqualTo(EventType.RISK_ALERT))
                .verifyComplete();
    }

    @Test
    @DisplayName("Debe verificar disponibilidad del procesador antes de procesar")
    void shouldCheckProcessorAvailabilityBeforeProcessing() {
        when(eventProcessor.isAvailable()).thenReturn(false);

        Mono<Event> result = eventProcessingService.processEvent(testEvent);

        StepVerifier.create(result)
                .expectErrorMatches(error ->
                        error instanceof EventProcessingException &&
                        error.getMessage().contains("no disponible"))
                .verify();

        verify(eventProcessor, never()).processEvent(any(Event.class));
    }
}

// === ARCHIVO: src/test/java/com/trading/events/infrastructure/adapter/ReactiveEventProcessorTest.java ===
package com.trading.events.infrastructure.adapter;

import com.trading.events.domain.exception.EventProcessingException;
import com.trading.events.domain.model.Event;
import com.trading.events.domain.model.Event.EventStatus;
import com.trading.events.domain.model.Event.EventType;
import com.trading.events.domain.port.EventProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas de integración para ReactiveEventProcessor - Comportamiento Reactivo y Escalabilidad")
class ReactiveEventProcessorTest {

    @Mock
    private EventProcessor delegateProcessor;

    private ReactiveEventProcessor reactiveEventProcessor;
    private Event testEvent;
    private Event testEvent2;
    private Event testEvent3;

    @BeforeEach
    void setUp() {
        reactiveEventProcessor = new ReactiveEventProcessor(delegateProcessor);
        testEvent = Event.createNewEvent("ACC-001", EventType.ACCOUNT_UPDATE);
        testEvent2 = Event.createNewEvent("ACC-002", EventType.TRADE_EXECUTION);
        testEvent3 = Event.createNewEvent("ACC-003", EventType.RISK_ALERT);
    }

    @Test
    @DisplayName("Debe procesar evento de forma reactiva retorna Mono")
    void shouldProcessEventReactivelyReturningMono() {
        when(delegateProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    Event event = invocation.getArgument(0);
                    return Mono.just(event.markAsCompleted());
                });

        Mono<Event> result = reactiveEventProcessor.processEvent(testEvent);

        StepVerifier.create(result)
                .expectNextMatches(event ->
                        event.status() == EventStatus.COMPLETED &&
                        event.id().equals(testEvent.id()))
                .verifyComplete();

        verify(delegateProcessor, times(1)).processEvent(any(Event.class));
    }

    @Test
    @DisplayName("Debe procesar eventos en paralelo con flux")
    void shouldProcessEventsInParallelWithFlux() {
        when(delegateProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    Thread.sleep(50);
                    return Mono.just(invocation.getArgument(0).markAsCompleted());
                });

        List<Event> events = List.of(testEvent, testEvent2, testEvent3);

        Flux<Event> flux = reactiveEventProcessor.processEventsInParallel(events, 3);

        StepVerifier.create(flux)
                .expectNextCount(3)
                .verifyComplete();

        verify(delegateProcessor, times(3)).processEvent(any(Event.class));
    }

    @Test
    @DisplayName("Debe escalar horizontalmente con mayor concurrencia")
    void shouldScaleHorizontallyWithHigherConcurrency() throws InterruptedException {
        AtomicInteger activeProcessing = new AtomicInteger(0);
        AtomicInteger maxConcurrent = new AtomicInteger(0);

        when(delegateProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    activeProcessing.incrementAndGet();
                    maxConcurrent.set(Math.max(maxConcurrent.get(), activeProcessing.get()));
                    Thread.sleep(100);
                    activeProcessing.decrementAndGet();
                    return Mono.just(invocation.getArgument(0).markAsCompleted());
                });

        List<Event> events = List.of(
                Event.createNewEvent("ACC-001", EventType.ACCOUNT_UPDATE),
                Event.createNewEvent("ACC-002", EventType.TRADE_EXECUTION),
                Event.createNewEvent("ACC-003", EventType.RISK_ALERT),
                Event.createNewEvent("ACC-004", EventType.ACCOUNT_UPDATE),
                Event.createNewEvent("ACC-005", EventType.TRADE_EXECUTION),
                Event.createNewEvent("ACC-006", EventType.RISK_ALERT)
        );

        long startTimeLowConcurrency = System.currentTimeMillis();
        reactiveEventProcessor.processEventsInParallel(events, 2).blockLast();
        long durationLowConcurrency = System.currentTimeMillis() - startTimeLowConcurrency;

        reset(delegateProcessor);
        activeProcessing.set(0);
        maxConcurrent.set(0);

        when(delegateProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    activeProcessing.incrementAndGet();
                    maxConcurrent.set(Math.max(maxConcurrent.get(), activeProcessing.get()));
                    Thread.sleep(100);
                    activeProcessing.decrementAndGet();
                    return Mono.just(invocation.getArgument(0).markAsCompleted());
                });

        long startTimeHighConcurrency = System.currentTimeMillis();
        reactiveEventProcessor.processEventsInParallel(events, 6).blockLast();
        long durationHighConcurrency = System.currentTimeMillis() - startTimeHighConcurrency;

        assertThat(durationHighConcurrency).isLessThan(durationLowConcurrency);
    }

    @Test
    @DisplayName("Debe manejar errores en el flujo reactivo")
    void shouldHandleErrorsInReactiveFlow() {
        when(delegateProcessor.processEvent(any(Event.class)))
                .thenReturn(Mono.error(new EventProcessingException(
                        testEvent.id(),
                        testEvent.accountId(),
                        "Error de procesamiento"
                )));

        Mono<Event> result = reactiveEventProcessor.processEvent(testEvent);

        StepVerifier.create(result)
                .expectError(EventProcessingException.class)
                .verify();
    }

    @Test
    @DisplayName("Debe propagar errores en procesamiento paralelo")
    void shouldPropagateErrorsInParallelProcessing() {
        when(delegateProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    Event event = invocation.getArgument(0);
                    if (event.accountId().contains("002")) {
                        return Mono.error(EventProcessingException.forProcessingFailure(
                                event.id(),
                                event.accountId(),
                                new RuntimeException("Error")
                        ));
                    }
                    return Mono.just(event.markAsCompleted());
                });

        List<Event> events = List.of(testEvent, testEvent2, testEvent3);

        StepVerifier.create(reactiveEventProcessor.processEventsInParallel(events, 3))
                .expectNextCount(2)
                .expectError(EventProcessingException.class)
                .verify();
    }

    @Test
    @DisplayName("Debe verificar disponibilidad del procesador")
    void shouldCheckProcessorAvailability() {
        when(delegateProcessor.isAvailable()).thenReturn(true);

        boolean available = reactiveEventProcessor.isAvailable();

        assertThat(available).isTrue();
        verify(delegateProcessor, times(1)).isAvailable();
    }

    @Test
    @DisplayName("Debe retornar false cuando el procesador no está disponible")
    void shouldReturnFalseWhenProcessorNotAvailable() {
        when(delegateProcessor.isAvailable()).thenReturn(false);

        boolean available = reactiveEventProcessor.isAvailable();

        assertThat(available).isFalse();
    }

    @Test
    @DisplayName("Debe procesar eventos con backpressure correctamente")
    void shouldHandleBackpressureCorrectly() {
        AtomicInteger processedCount = new AtomicInteger(0);

        when(delegateProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    processedCount.incrementAndGet();
                    return Mono.just(invocation.getArgument(0).markAsCompleted());
                });

        Flux<Event> eventFlux = Flux.fromIterable(List.of(
                Event.createNewEvent("ACC-001", EventType.ACCOUNT_UPDATE),
                Event.createNewEvent("ACC-002", EventType.TRADE_EXECUTION),
                Event.createNewEvent("ACC-003", EventType.RISK_ALERT),
                Event.createNewEvent("ACC-004", EventType.ACCOUNT_UPDATE)
        )).flatMap(event ->
                reactiveEventProcessor.processEvent(event)
                        .onErrorResume(e -> Mono.empty())
        );

        StepVerifier.create(eventFlux)
                .expectNextCount(4)
                .verifyComplete();

        assertThat(processedCount.get()).isEqualTo(4);
    }

    @Test
    @DisplayName("Debe mantener orden de procesamiento cuando se especifica")
    void shouldMaintainProcessingOrderWhenSpecified() {
        AtomicInteger orderCounter = new AtomicInteger(0);

        when(delegateProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    int order = orderCounter.incrementAndGet();
                    Event event = invocation.getArgument(0);
                    Thread.sleep(50 * (4 - order));
                    return Mono.just(event.markAsCompleted());
                });

        List<Event> events = List.of(testEvent, testEvent2, testEvent3);

        List<Event> results = reactiveEventProcessor.processEventsInParallel(events, 1)
                .collectList()
                .block();

        assertThat(results).hasSize(3);
    }

    @Test
    @DisplayName("Debe manejar timeout en procesamiento de eventos")
    void shouldHandleTimeoutInEventProcessing() {
        when(delegateProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    Thread.sleep(500);
                    return Mono.just(invocation.getArgument(0).markAsCompleted());
                });

        Mono<Event> result = reactiveEventProcessor.processEvent(testEvent)
                .timeout(Duration.ofMillis(100));

        StepVerifier.create(result)
                .expectErrorMatches(error ->
                        error instanceof java.util.concurrent.TimeoutException)
                .verify();
    }

    @Test
    @DisplayName("Debe implementar retry para fallos transitorios")
    void shouldImplementRetryForTransientFailures() {
        AtomicInteger attemptCount = new AtomicInteger(0);
        AtomicBoolean completed = new AtomicBoolean(false);

        when(delegateProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    int attempts = attemptCount.incrementAndGet();
                    if (attempts < 3) {
                        return Mono.error(new RuntimeException("Fallo transitorio"));
                    }
                    completed.set(true);
                    return Mono.just(invocation.getArgument(0).markAsCompleted());
                });

        Mono<Event> result = reactiveEventProcessor.processEvent(testEvent)
                .retryWhen(companion -> companion.take(3));

        StepVerifier.create(result)
                .expectNextMatches(event -> event.status() == EventStatus.COMPLETED)
                .verifyComplete();

        assertThat(completed.get()).isTrue();
        assertThat(attemptCount.get()).isEqualTo(3);
    }
}
```
