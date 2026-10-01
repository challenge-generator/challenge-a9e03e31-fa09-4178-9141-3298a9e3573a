# Optimización de Concurrencia y Paralelismo en Eventos

El sistema de procesamiento de eventos en una plataforma de trading necesita manejar un alto volumen de operaciones simultáneas para mantener la eficiencia y la rapidez en las ejecuciones. El sistema debe asegurar que los eventos se procesen de manera concurrente y paralela para evitar bloqueos y asegurar la fluidez de las operaciones. Los eventos provienen de múltiples fuentes (mercados, usuarios, sistemas internos) y deben ser procesados en tiempo real para actualizar los estados de las cuentas y los balances. El sistema debe manejar la concurrencia de manera eficiente para evitar cuellos de botella y asegurar que las operaciones se completen en el menor tiempo posible.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Gestión de Concurrencia y Paralelismo: Optimizando el Eventos |
| **Nivel** | advanced-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 8 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Identificación de Puntos de Concurrencia

**Objetivo:** Mapear las áreas del sistema donde la concurrencia es crítica y puede introducir bloqueos.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Analiza el flujo de eventos desde su origen hasta su procesamiento final.
- Identifica los puntos donde múltiples eventos pueden llegar simultáneamente y cómo esto puede afectar el rendimiento.
- Documenta las áreas críticas y propone posibles soluciones para manejar la concurrencia de manera eficiente.

**Entregable:** Mapa de concurrencia con puntos críticos documentados y propuestas de solución.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la naturaleza de los eventos y cómo interactúan entre sí.
- Piensa en cómo los eventos pueden ser agrupados o desacoplados para mejorar el rendimiento.

</details>

### Fase 2: Implementación de Hilos y Sincronización

**Objetivo:** Implementar hilos de ejecución para manejar eventos concurrentes y asegurar la sincronización adecuada.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Crea hilos de ejecución para manejar diferentes flujos de eventos de manera concurrente.
- Implementa mecanismos de sincronización para asegurar que los recursos compartidos se accedan de manera segura.
- Prueba la implementación para asegurar que los eventos se procesan de manera eficiente y sin bloqueos.

**Entregable:** Implementación de hilos y sincronización con pruebas de rendimiento.

<details>
<summary>Pistas de conocimiento</summary>

- Usa mecanismos de sincronización como semáforos o bloqueos para asegurar la seguridad de los recursos compartidos.
- Considera el uso de colas para desacoplar los eventos y mejorar el rendimiento.

</details>

### Fase 3: Optimización y Escalabilidad

**Objetivo:** Optimizar la implementación para manejar un mayor volumen de eventos y asegurar la escalabilidad del sistema.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Analiza el rendimiento de la implementación actual y identifica áreas de mejora.
- Implementa mejoras para optimizar el procesamiento de eventos y asegurar que el sistema pueda manejar un mayor volumen de eventos.
- Realiza pruebas de carga para verificar la escalabilidad de la implementación.

**Entregable:** Implementación optimizada con pruebas de carga y resultados de rendimiento.

<details>
<summary>Pistas de conocimiento</summary>

- Considera el uso de técnicas de paralelismo para mejorar el rendimiento.
- Evalúa el uso de tecnologías de escalabilidad como contenedores o servicios en la nube.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es la concurrencia y cómo se diferencia del paralelismo en el contexto de este reto?
- **paraQueSirve**: ¿Para qué sirve manejar la concurrencia y el paralelismo en el procesamiento de eventos?
- **comoSeUsa**: ¿Cómo se pueden implementar hilos y mecanismos de sincronización para manejar eventos concurrentes?
- **erroresComunes**: ¿Cuáles son los errores comunes al manejar concurrencia y paralelismo y cómo se pueden evitar?
- **queDecisionesImplica**: ¿Qué decisiones implica la optimización de la concurrencia y el paralelismo en el procesamiento de eventos?

## Criterios de Evaluacion

- Identificación correcta de puntos de concurrencia crítica.
- Implementación efectiva de hilos y sincronización.
- Optimización y escalabilidad del sistema para manejar un mayor volumen de eventos.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
