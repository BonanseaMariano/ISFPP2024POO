# Programación Orientada a Objetos - ISFPP 2024 "Red Lan"

## Alumnos:

### Bonansea Camaño, Mariano Nicolas y Rivero, Lucia Jazmin.

# Contenido

- [Introducción y objetivos](#introducción-y-objetivos)
- [Alcance del proyecto](#alcance-del-proyecto)
- [Herramientas y tecnologías](#herramientas-y-tecnologías)
- [Requerimientos del sistema](#requerimientos-del-sistema)
- [Diagrama de clases](#diagrama-de-clases-uml)
- [Estructuras de datos utilizadas](#estructuras-de-datos-utilizadas)
- [Persistencia de datos](#persistencia-de-datos)
- [Patrones de diseño utilizados](#patrones-de-diseño-utilizados)
- [Documentación Javadoc](#documentación-javadoc)
- [Manual de Usuario](#manual-de-usuario)
- [Errores detectados, posibles mejoras y extensiones](#errores-detectados-posibles-mejoras-y-extensiones)
- [Conclusiones](#conclusiones)
- [Diagramas de Clases UML](#diagramas-de-clases-uml)

# Introducción y objetivos

Este proyecto desarrolla un sistema para la gestión de una red de computadoras, permitiendo la administración de
dispositivos conectados, monitoreo del estado actual y resolución de problemas en la red. El sistema se enfocará en
redes locales, proporcionando herramientas para la configuración, supervisión y mantenimiento de la infraestructura de
red. El sistema se enfocará en redes locales. Este proyecto corresponde a una Instancia supervisada de formación
práctica profesional de la materia
**Programación Orientada a Objetos** de la carrera **Licenciatura en Programación** de la **Universidad Nacional de la
Patagonia San Juan Bosco - Sede Puerto Madryn** desarrollado por los alumnos **Bonansea Camaño, Mariano Nicolas** y *
*Rivero, Lucia Jazmin**.

### Limitaciones y exclusiones

- No incluirá herramientas avanzadas de análisis de tráfico en tiempo real.
- No incluirá herramientas de solución de problemas automatizadas, unicamente de deteccion de problemas.
- La interfaz gráfica será básica, centrada en funcionalidad.
- La carga de datos será manual.

### Objetivo general

Desarrollar un sistema de gestión de redes que permita la configuración y monitoreo de dispositivos, con capacidad de
detectar y resolver problemas básicos, utilizando programación orientada a objetos (POO) y un enfoque modular y
escalable.

### Objetivos específicos

- Implementar clases y objetos para dispositivos de red (routers, switches, computadoras).
- Crear una interfaz gráfica sencilla para la gestión de equipos y visualización del estado de la red.
- Utilizar estructuras de datos (listas, grafos) para modelar topologías de red.
- Implementar herencia y polimorfismo en la gestión de distintos dispositivos.
- Desarrollar funcionalidad de hilos para el monitoreo continuo de equipos.
- Persistir información de dispositivos y configuraciones en una base de datos o archivos.
- Diseñar e implementar patrones de diseño como Singleton o Factory para la creación de dispositivos y gestión de
  conexiones.
- Implementar consultas como:
    - Conexión entre equipos y velocidad máxima de transmisión.
    - Realizar ping a equipos y mostrar un mapa del estado actual.
    - Detección de problemas de conectividad y visualización del alcance de la conexión.

# Alcance del proyecto

El sistema implementará:

- **Gestión de equipos de red**: Creación, edición y eliminación de dispositivos (computadoras, switches, routers).
- **Monitoreo de la red**: Visualización del estado de los dispositivos conectados.
- **Simulación de conexiones**: Capacidad de simular conexiones y rutas de comunicación.
- **Persistencia de datos**: Guardar configuraciones y estado de equipos en archivos o una base de datos.
- **Visualización de topología**: Representación gráfica de la red con nodos y aristas (grafos).

# Herramientas y tecnologías

**Lenguaje de programación:**

- **Java (versión 22)**: Lenguaje de programación de propósito general, concurrente, orientado a objetos y basado en
  clases.

**Interfaz gráfica:**

- **Java Swing**: Biblioteca de GUI para Java, proporciona componentes gráficos personalizables.

**Gestión de datos:**

- **Archivos de texto y SQLite**:
    - **Archivos de texto**: Para almacenar configuraciones y logs.
    - **SQLite**: Base de datos SQL ligera y embebida, ideal para aplicaciones de escritorio y móviles.

**Herramientas adicionales:**

- **JUnit**: Framework de pruebas unitarias para Java.
- **JAVADOC**: Herramienta para generar documentación API en formato HTML.

**Bibliotecas externas:**

- **JGraph y JGraphx**: Bibliotecas para la representación y manipulación de grafos en Java.

# Requerimientos del sistema

- Permitir la **creación, modificación y eliminación** de dispositivos de red.
- Persistir datos en una base de datos local o archivos de configuración.
- Simular conexiones entre dispositivos y representar la topología de red gráficamente.
- Permitir realizar diversas consultas sobre la red, como **ping** y **detección de problemas**.

# Estructuras de datos utilizadas

- **Listas**: Para almacenar colecciones de dispositivos de red.
- **Mapas**: Para asociar identificadores únicos a dispositivos de red.
- **Grafos**: Para representar la topología de la red.
- **Colas**: Para gestionar tareas en cola, como procesos de hilos al momento de realizar pings.

# Persistencia de datos

Se emplearon patrones de diseño **Factory** y **DAO** para intercambiar entre dos tipos de persistencia de datos:

- **Archivos de texto**: Para almacenar configuraciones y logs, asi como tambien alternativa para el almacenamiento de
  datos.
- **Base de datos SQLite**: Para almacenar datos de manera estructurada y realizar consultas complejas.

# Patrones de diseño utilizados

* ### Facade

  El patrón Facade proporciona una interfaz simplificada a un conjunto de interfaces en un subsistema. En este proyecto,
  se utiliza para simplificar la interacción con la base de datos y otros componentes complejos.

* ### Singleton

  El patrón Singleton asegura que una clase tenga solo una instancia y proporciona un punto de acceso global a ella. En
  este proyecto, se utiliza para generar instancias únicas de ciertos objetos en el proyecto, como el coordinador.

* ### DAO (Data Access Object)

  El patrón DAO abstrae y encapsula el acceso a la fuente de datos. En este proyecto, se utiliza para separar la lógica
  de acceso a datos de la lógica de negocio.

* ### Factory

  El patrón Factory permite la creación de objetos sin especificar la clase exacta del objeto que se va a crear. En este
  proyecto, se utiliza para crear diferentes tipos de dispositivos de red.

* ### Model View Controller (MVC)

  El patrón MVC separa la aplicación en modelo, vista y controlador. En este proyecto, se utiliza para estructurar la
  interfaz gráfica y la lógica de negocio.

* ### Observer

  El patrón Observer permite que un objeto notifique a otros objetos sobre cambios en su estado. En este proyecto, se
  utiliza para actualizar la interfaz gráfica cuando se agregan o modifican equipos.

# [Documentación Javadoc](https://bonanseamariano.github.io/ISFPP2024POO/)

# [Manual de Usuario](Manual%20de%20Usuario.md)

# Consideraciones

- El funcionamiento de la aplicacion en los modos **simulacion** y **real** difiere unicamente en el apartado de
  consultas, específicamente en la consulta de **ping**, **ping range** y **traceroute**. Los apartados de procesos
  **CRUD** con los dispositivos y la visualización de la topología de la red son iguales en ambos modos y solo reflejan
  sus cambios para la simulacion.
- Además de los loggers de información utilizados durante las operaciones sobre los datos de la aplicación, también se
  han incluido loggers de DEBUG para facilitar la visualización de la sincronización entre la **lógica** y la **red**
  administrada por el **coordinador**.
- El peso de los arcos del grafo de lógica se calcula como: Tipo de cable de mayor ancho de banda (BW) - $x_i$,
  donde $x$ es el BW del tipo de cable.
- En una red local no existen bucles, por lo que la ruta entre equipos siempre es única o no existe.
- No existen conexiones de un equipo consigo mismo.
- Las rutas entre equipos se calculan independientemente del estado de los equipos. Posteriormente, se evalúa si la ruta
  tiene problemas y en qué equipos, pero la ruta existe independientemente de si hay un equipo desactivado entre el
  resto.
- Se considera que la velocidad ingresada como dato para los cables está en MB.
- Se contempla que dos equipos no pueden tener más de una conexión entre sí.
- Un equipo puede conectarse a otro independientemente del tipo de puerto disponible, siempre que tenga al menos un
  puerto libre. No se verifica si el tipo de puerto corresponde con el de la conexión, pero sí se valida la cantidad
  total de puertos.

# Errores detectados

- No se verifica la cantidad de puertos específicos de un tipo, solo la cantidad total.
- Al agregar un nuevo tipo de puerto con 0 entradas y eliminar los puertos existentes, se permite la eliminación incluso
  con conexiones activas.
- Al utilizar el **DAO** de archivos de Texto para agregar un equipo, asi como tambien la carga inicial, aparecera un
  error
  por consola al momento de cargar los
  puertos (ya que al parsear un espacio vacío a un numero se lanza una excepción). Esto no altera de ninguna manera el
  funcionamiento de la aplicación.
- Por motivos de sincronización con la base de datos, no se valida que un equipo tenga al menos un puerto al cargar sus
  datos. Primero se deben cargar las características del equipo para luego visualizarlo en la tabla y poder seleccionar
  el apartado **ver puertos** y operar sobre ellos. La misma situación se aplica a las IPs del equipo.

# Conclusiones

Este proyecto ha permitido crear un sistema robusto y escalable para la gestión de redes de computadoras, aplicando
técnicas y patrones de diseño eficientes. A través de la implementación de diversas funcionalidades, se ha logrado un
sistema que no solo facilita la administración de dispositivos de red, sino que también permite el monitoreo y la
resolución de problemas de manera efectiva. La utilización de patrones de diseño como Singleton, Factory, DAO, y
Observer ha sido crucial para mantener una arquitectura modular y fácil de mantener. Además, la integración de una
interfaz gráfica desarrollada con Java Swing proporciona una experiencia de usuario intuitiva y accesible, mientras que
la persistencia de datos mediante archivos de texto y bases de datos SQLite asegura la fiabilidad y consistencia de la
información almacenada.

### Principales logros:

- **Modelado eficiente de la red**: Utilizando listas, mapas y grafos.
- **Persistencia de datos flexible**: Con archivos de texto y bases de datos SQLite.
- **Interfaz gráfica funcional**: Desarrollada con Java Swing.
- **Patrones de diseño**: Mejoran la estructura y organización del código.

### Retos y aprendizajes:

- **Gestión de concurrencia**: Desafíos en la sincronización y gestión de recursos compartidos.
- **Persistencia de datos**: Implementar bases de datos presenta una mayor complejidad, debiendo aprovecharse las
  herramientas que ofrece un DBMS.
- **Optimización de rendimiento**: Identificación y resolución de cuellos de botella.

### Futuras mejoras:

- **Ampliación de funcionalidades**: Incluir análisis de tráfico en tiempo real y mejorar la interfaz gráfica.
- **Integración con otras tecnologías**: Ampliar capacidades del sistema con tecnologías avanzadas de monitoreo y
  gestión de redes.

# Diagramas de Clases UML:

A continuación se presentan los diagramas de clases UML correspondientes a las capas de la aplicación.

## Capa Models:

![Bonansea-Rivero_ModelsUML.png](UML%2FBonansea-Rivero_ModelsUML.png)

## Capa Logic:

![Bonansea-Rivero_LogicUML.png](UML%2FBonansea-Rivero_LogicUML.png)

## Capa Data:

![Bonansea-Rivero_DataUML.png](UML%2FBonansea-Rivero_DataUML.png)

## Capa GUI:

![Bonansea-Rivero_GUIUML.png](UML%2FBonansea-Rivero_GUIUML.png)