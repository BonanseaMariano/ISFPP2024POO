<h1 style="text-align: center;">ISFPP 2024 - Programacion Orientada a Objetos - "Red Lan"</h1>

<h2 style="text-align: center;"><u>Alumnos</u>:</h2>
<h3 style="text-align: center;">Bonansea Mariano y Rivero Lucia</h3>
<h3 style="text-align: center;">2024</h3>

# Contenido

[Introducción y objetivos](#introducción-y-objetivos)

[Alcance del proyecto](#alcance-del-proyecto)

[Requerimientos del sistema](#requerimientos-del-sistema)

[Diagrama de clases](#diagrama-de-clases-uml)

# Introducción y objetivos

En este proyecto se desarrollará un sistema para la gestión de una red de computadoras, que permitirá la administración de dispositivos conectados, el estado actual y la identificación de problemas y soluciones en la red. El sistema estará orientado a la gestión y organización de las redes locales.

### Limitaciones y exclusiones:

- No se incluirán herramientas avanzadas de análisis de tráfico en tiempo real.
- La interfaz gráfica será básica, centrada en funcionalidad más que en diseño.
- Toda la carga de datos será de forma manual por medio del sistema.

### Objetivo general:

Desarrollar un sistema de gestión de red de computadoras que permita la configuración y monitoreo de dispositivos de red, con capacidad de detectar y resolver problemas básicos, utilizando programación orientada a objetos (POO) y un enfoque modular y escalable.

### Objetivos específicos:

- implementar clases y objetos para la representación de dispositivos de red (routers, switches, computadoras).
- Crear una interfaz gráfica sencilla para la gestión de equipos y visualización del estado de la red.
- Utilizar estructuras de datos (como listas, grafos) para modelar y representar topologías de red.
- Implementar herencia y polimorfismo en la gestión de distintos tipos de dispositivos.
- Desarrollar la funcionalidad de hilos para manejar procesos simultáneos, como el monitoreo continuo de los equipos.
- Persistir la información de los dispositivos y configuraciones en una base de datos o archivos.
- Diseñar e implementar patrones de diseño adecuados como Singleton o Factory para la creación de dispositivos y gestión de conexiones.
- Implementar consultas tales como:
    - La conexión entre dos equipos y la velocidad máxima de la transmisión entre los mismos.
    - Realizar el ping a distintos equipos y mostrar un mapa del estado actual de los equipos conectados a la red.
    - Detección de problemas de conectividad y visualización del alcance de la conexión.

# Alcance del proyecto

El sistema implementará las siguientes características:

- **Gestión de equipos de red**: Creación, edición y eliminación de dispositivos (computadoras, switches, routers).
- **Monitoreo de la red**: Visualización del estado de los dispositivos conectados, como su conectividad y velocidad.
- **Simulación de conexiones**: Capacidad de simular conexiones entre dispositivos y rutas de comunicación.
- **Persistencia de datos**: Guardar la configuración de los equipos y su estado en archivos o una base de datos.
- **Visualización de topología**: Representación gráfica de la red utilizando nodos y aristas (grafos).

# Herramientas y tecnologías:

**Lenguaje de programación:**
- **Java (versión 22)**: Java es un lenguaje de programación de propósito general, concurrente, orientado a objetos y basado en clases. La versión 22 incluye mejoras en el rendimiento, nuevas características del lenguaje y actualizaciones de seguridad.

**Interfaz gráfica:**
- **JavaSwing**: Java Swing es una biblioteca de GUI (interfaz gráfica de usuario) para Java. Proporciona un conjunto de componentes gráficos (como botones, cuadros de texto, tablas, etc.) que permiten crear interfaces de usuario ricas y personalizables.

**Gestión de datos:**
- **Archivos de extensión txt y SQLite**:
  - **Archivos de extensión txt**: Se utilizarán archivos de texto para almacenar configuraciones y logs de manera sencilla y accesible.
  - **SQLite**: Es una biblioteca que proporciona una base de datos SQL ligera y embebida. Es ideal para aplicaciones de escritorio y móviles debido a su bajo peso y facilidad de uso.

**Herramientas adicionales**:
  - **JUnit**: Es un framework de pruebas unitarias para Java. Permite escribir y ejecutar pruebas automatizadas para asegurar que el código funciona correctamente.
  - **JAVADOC**: Es una herramienta que genera documentación API en formato HTML a partir de comentarios en el código fuente de Java. Facilita la creación de documentación detallada y navegable para el proyecto.

**Bibliotecas externas**:
  - **JGraph y JGraphx**: Son bibliotecas para la representación y manipulación de grafos en Java. Permiten crear diagramas de red visuales, lo cual es esencial para la visualización de la topología de la red en el proyecto.

Estas herramientas y tecnologías se seleccionaron para asegurar que el sistema sea robusto, escalable y fácil de mantener, proporcionando una base sólida para el desarrollo y la gestión de la red de computadoras.

# Requerimientos del sistema

- El sistema debe permitir la **creación, modificación y eliminación** de dispositivos de red.
- El sistema debe persistir los datos en una base de datos local o en archivos de configuración.
- Debe poder simular conexiones entre dispositivos y representar la topología de red gráficamente.
- El sistema debe emitir **alertas automáticas** cuando se detecten problemas como la desconexión de dispositivos o fallos en la red.

# Diagrama de Clases UML:

!https://prod-files-secure.s3.us-west-2.amazonaws.com/2b427356-17ac-4269-b8fe-85cae6cfbe6f/e91475ed-d3b4-4af9-8aa5-5d21675aac8c/image1.png