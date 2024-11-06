# Programacion Orientada a Objetos - ISFPP 2024 "Red Lan"

## <u>Alumnos</u>:

### Bonansea Camaño, Mariano Nicolas y Rivero, Lucia Jazmin.

# Contenido

[Introducción y objetivos](#introducción-y-objetivos)

[Alcance del proyecto](#alcance-del-proyecto)

[Herramientas y tecnologías](#herramientas-y-tecnologías)

[Requerimientos del sistema](#requerimientos-del-sistema)

[Diagrama de clases](#diagrama-de-clases-uml)

[Estructuras de datos utilizadas](#estructuras-de-datos-utilizadas)

[Persistencia de datos](#persistencia-de-datos)

[Patrones de diseño utilizados](#patrones-de-diseño-utilizados)

[Documentacion Javadoc](#documentación-javadoc)

[Errores detectados, posibles mejoras y extensiones](#errores-detectados-posibles-mejoras-y-extensiones)

[Conclusiones](#conclusiones)

[Diagramas de Clases UML](#diagramas-de-clases-uml)

# Introducción y objetivos

En este proyecto se desarrollará un sistema para la gestión de una red de computadoras, que permitirá la administración
de dispositivos conectados, el estado actual y la identificación de problemas y soluciones en la red. El sistema estará
orientado a la gestión y organización de las redes locales.

### Limitaciones y exclusiones:

- No se incluirán herramientas avanzadas de análisis de tráfico en tiempo real.
- La interfaz gráfica será básica, centrada en funcionalidad más que en diseño.
- Toda la carga de datos será de forma manual por medio del sistema.

### Objetivo general:

Desarrollar un sistema de gestión de red de computadoras que permita la configuración y monitoreo de dispositivos de
red, con capacidad de detectar y resolver problemas básicos, utilizando programación orientada a objetos (POO) y un
enfoque modular y escalable.

### Objetivos específicos:

- implementar clases y objetos para la representación de dispositivos de red (routers, switches, computadoras).
- Crear una interfaz gráfica sencilla para la gestión de equipos y visualización del estado de la red.
- Utilizar estructuras de datos (como listas, grafos) para modelar y representar topologías de red.
- Implementar herencia y polimorfismo en la gestión de distintos tipos de dispositivos.
- Desarrollar la funcionalidad de hilos para manejar procesos simultáneos, como el monitoreo continuo de los equipos.
- Persistir la información de los dispositivos y configuraciones en una base de datos o archivos.
- Diseñar e implementar patrones de diseño adecuados como Singleton o Factory para la creación de dispositivos y gestión
  de conexiones.
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

- **Java (versión 22)**: Java es un lenguaje de programación de propósito general, concurrente, orientado a objetos y
  basado en clases. La versión 22 incluye mejoras en el rendimiento, nuevas características del lenguaje y
  actualizaciones de seguridad.

**Interfaz gráfica:**

- **JavaSwing**: Java Swing es una biblioteca de GUI (interfaz gráfica de usuario) para Java. Proporciona un conjunto de
  componentes gráficos (como botones, cuadros de texto, tablas, etc.) que permiten crear interfaces de usuario ricas y
  personalizables.

**Gestión de datos:**

- **Archivos de extensión txt y SQLite**:
    - **Archivos de extensión txt**: Se utilizarán archivos de texto para almacenar configuraciones y logs de manera
      sencilla y accesible.
    - **SQLite**: Es una biblioteca que proporciona una base de datos SQL ligera y embebida. Es ideal para aplicaciones
      de escritorio y móviles debido a su bajo peso y facilidad de uso.

**Herramientas adicionales**:

- **JUnit**: Es un framework de pruebas unitarias para Java. Permite escribir y ejecutar pruebas automatizadas para
  asegurar que el código funciona correctamente.
- **JAVADOC**: Es una herramienta que genera documentación API en formato HTML a partir de comentarios en el código
  fuente de Java. Facilita la creación de documentación detallada y navegable para el proyecto.

**Bibliotecas externas**:

- **JGraph y JGraphx**: Son bibliotecas para la representación y manipulación de grafos en Java. Permiten crear
  diagramas de red visuales, lo cual es esencial para la visualización de la topología de la red en el proyecto.

Estas herramientas y tecnologías se seleccionaron para asegurar que el sistema sea robusto, escalable y fácil de
mantener, proporcionando una base sólida para el desarrollo y la gestión de la red de computadoras.

# Requerimientos del sistema

- El sistema debe permitir la **creación, modificación y eliminación** de dispositivos de red.
- El sistema debe persistir los datos en una base de datos local o en archivos de configuración.
- Debe poder simular conexiones entre dispositivos y representar la topología de red gráficamente.
- El sistema debe emitir **alertas automáticas** cuando se detecten problemas como la desconexión de dispositivos o
  fallos en la red.

# Estructuras de datos utilizadas

En este proyecto se han utilizado diversas estructuras de datos para modelar y gestionar la red de computadoras de
manera eficiente:

- **Listas**: Se utilizan para almacenar y gestionar colecciones de dispositivos de red, como routers, switches y
  computadoras. Las listas permiten un acceso secuencial y son útiles para iterar sobre los dispositivos.

- **Mapas (HashMap)**: Se emplean para asociar identificadores únicos a dispositivos de red, facilitando la búsqueda y
  recuperación rápida de información sobre un dispositivo específico.

- **Grafos**: Los grafos son fundamentales para representar la topología de la red. Cada nodo del grafo representa un
  dispositivo de red, y cada arista representa una conexión entre dos dispositivos. Esto permite modelar y analizar
  rutas de comunicación y detectar problemas de conectividad.

- **Colas**: Se utilizan para gestionar tareas en cola, como las alertas automáticas y las solicitudes de monitoreo de
  dispositivos. Las colas aseguran que las tareas se procesen en el orden en que se reciben.

Estas estructuras de datos se seleccionaron para optimizar el rendimiento y la eficiencia del sistema, permitiendo una
gestión robusta y escalable de la red de computadoras.

# Persistencia de datos

En este proyecto se han utilizado dos formas para mantener la persistencia de los datos:

- **Archivos de texto**: Se utilizan archivos de texto para almacenar configuraciones y logs de manera sencilla y
  accesible. Esta forma de persistencia es útil para datos que no requieren una estructura compleja y pueden ser
  fácilmente leídos y escritos.

- **Base de datos SQLite**: SQLite es una base de datos SQL ligera y embebida que permite almacenar datos de manera
  estructurada y realizar consultas complejas. Es ideal para aplicaciones de escritorio debido a su bajo peso y
  facilidad de uso.

Es posible intercambiar entre ambas implementaciones gracias a los patrones de diseño **Factory** y **DAO (Data Access
Object)**. El patrón **Factory** permite crear instancias de las clases de persistencia de manera dinámica, mientras que
el patrón **DAO** abstrae y encapsula el acceso a la fuente de datos, proporcionando una interfaz para realizar
operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sin importar el tipo de almacenamiento utilizado.

# Patrones de diseño utilizados

- **Facade**: Proporciona una interfaz simplificada a un conjunto de interfaces en un subsistema, facilitando su uso.

- **Singleton**: Asegura que una clase tenga una única instancia y proporciona un punto de acceso global a ella.

- **DAO (Data Access Object)**: Abstrae y encapsula el acceso a la fuente de datos, proporcionando una interfaz para
  realizar operaciones CRUD.

- **Factory**: Permite la creación de objetos sin especificar la clase exacta del objeto que se va a crear. Proporciona
  una interfaz para crear objetos en una superclase, pero permite a las subclases alterar el tipo de objetos que se
  crearán.

- **Model View Controller (MVC)**: Separa la aplicación en tres componentes principales: el modelo (gestión de datos),
  la vista (interfaz de usuario) y el controlador (lógica de negocio), facilitando la gestión y escalabilidad del
  código.

# [Documentación Javadoc](https://bonanseamariano.github.io/ISFPP2024POO/)

# Errores detectados, posibles mejoras y extensiones

- No se verifica que la cantidad de puertos esté asociada a cada tipo de puerto en específico, sino que se considera la
  cantidad total de puertos de manera general.
- Al agregar un nuevo tipo de puerto con 0 entradas y eliminar los puertos existentes, se permite la eliminación incluso
  si hay conexiones activas, lo que puede resultar en que un equipo esté conectado a otros equipos sin tener puertos
  disponibles.

# Conclusiones

El desarrollo de este proyecto ha permitido la creación de un sistema robusto y escalable para la gestión de redes de
computadoras. A lo largo del proceso, se han implementado diversas técnicas y patrones de diseño que han contribuido a
la eficiencia y mantenibilidad del código.

### Principales logros:

- **Modelado eficiente de la red**: Utilizando estructuras de datos como listas, mapas y grafos, se ha logrado una
  representación precisa y eficiente de la topología de la red.
- **Persistencia de datos flexible**: La implementación de persistencia a través de archivos de texto y bases de datos
  SQLite, junto con el uso de patrones de diseño como Factory y DAO, ha permitido una gestión flexible y eficiente de
  los datos.
- **Interfaz gráfica funcional**: A través de Java Swing, se ha desarrollado una interfaz gráfica que facilita la
  gestión y monitoreo de los dispositivos de red.
- **Patrones de diseño**: La aplicación de patrones de diseño como Singleton, Facade, Factory y DAO ha mejorado la
  estructura y organización del código, facilitando su mantenimiento y escalabilidad.

### Retos y aprendizajes:

- **Gestión de concurrencia**: La implementación de hilos para el monitoreo continuo de los dispositivos ha presentado
  desafíos en la sincronización y gestión de recursos compartidos.
- **Optimización de rendimiento**: A lo largo del desarrollo, se han identificado y abordado diversos cuellos de botella
  que afectaban el rendimiento del sistema, mejorando así su eficiencia.

### Futuras mejoras:

- **Ampliación de funcionalidades**: Incluir herramientas avanzadas de análisis de tráfico en tiempo real y mejorar la
  interfaz gráfica para una mejor experiencia de usuario.
- **Integración con otras tecnologías**: Explorar la integración con tecnologías de monitoreo y gestión de redes más
  avanzadas para ampliar las capacidades del sistema.

En resumen, este proyecto ha proporcionado una valiosa experiencia en el desarrollo de sistemas de gestión de redes,
aplicando principios de programación orientada a objetos y patrones de diseño que han resultado en un sistema eficiente
y escalable.

# Diagramas de Clases UML:

## Capa Models:

![Bonansea-Rivero_ModelsUML.png](UML%2FBonansea-Rivero_ModelsUML.png)

## Capa Logic:

![Bonansea-Rivero_LogicUML.png](UML%2FBonansea-Rivero_LogicUML.png)

## Capa Data:

![Bonansea-Rivero_DataUML.png](UML%2FBonansea-Rivero_DataUML.png)

## Capa GUI:

![Bonansea-Rivero_GUIUML.png](UML%2FBonansea-Rivero_GUIUML.png)