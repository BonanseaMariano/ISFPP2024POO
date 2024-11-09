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
redes locales.

### Limitaciones y exclusiones

- No incluirá herramientas avanzadas de análisis de tráfico en tiempo real.
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
- Emitir **alertas** ante problemas como desconexión de dispositivos o fallos en la red.

# Estructuras de datos utilizadas

- **Listas**: Para almacenar colecciones de dispositivos de red.
- **Mapas (HashMap)**: Para asociar identificadores únicos a dispositivos de red.
- **Grafos**: Para representar la topología de la red.
- **Colas**: Para gestionar tareas en cola, como procesos de hilos al momento de realizar pings.

# Persistencia de datos

Utilizamos dos formas:

- **Archivos de texto**: Para almacenar configuraciones y logs.
- **Base de datos SQLite**: Para almacenar datos de manera estructurada y realizar consultas complejas.

Se emplearon patrones de diseño **Factory** y **DAO** para intercambiar entre ambas implementaciones.

# Patrones de diseño utilizados

- **Facade**: Proporciona una interfaz simplificada a un conjunto de interfaces.
- **Singleton**: Asegura una única instancia de una clase.
- **DAO (Data Access Object)**: Abstrae y encapsula el acceso a la fuente de datos.
- **Factory**: Permite la creación de objetos sin especificar la clase exacta.
- **Model View Controller (MVC)**: Separa la aplicación en modelo, vista y controlador.

# [Documentación Javadoc](https://bonanseamariano.github.io/ISFPP2024POO/)

# [Manual de Usuario](Manual%20de%20Usuario.md)

# Errores detectados, posibles mejoras y extensiones

- No se verifica la cantidad de puertos específicos, solo la cantidad total.
- Al agregar un nuevo tipo de puerto con 0 entradas y eliminar los puertos existentes, se permite la eliminación incluso
  con conexiones activas.

# Conclusiones

Este proyecto ha permitido crear un sistema robusto y escalable para la gestión de redes de computadoras, aplicando
técnicas y patrones de diseño eficientes.

### Principales logros:

- **Modelado eficiente de la red**: Utilizando listas, mapas y grafos.
- **Persistencia de datos flexible**: Con archivos de texto y bases de datos SQLite.
- **Interfaz gráfica funcional**: Desarrollada con Java Swing.
- **Patrones de diseño**: Mejoran la estructura y organización del código.

### Retos y aprendizajes:

- **Gestión de concurrencia**: Desafíos en la sincronización y gestión de recursos compartidos.
- **Optimización de rendimiento**: Identificación y resolución de cuellos de botella.

### Futuras mejoras:

- **Ampliación de funcionalidades**: Incluir análisis de tráfico en tiempo real y mejorar la interfaz gráfica.
- **Integración con otras tecnologías**: Ampliar capacidades del sistema con tecnologías avanzadas de monitoreo y
  gestión de redes.

# Diagramas de Clases UML:

## Capa Models:

![Bonansea-Rivero_ModelsUML.png](UML%2FBonansea-Rivero_ModelsUML.png)

## Capa Logic:

![Bonansea-Rivero_LogicUML.png](UML%2FBonansea-Rivero_LogicUML.png)

## Capa Data:

![Bonansea-Rivero_DataUML.png](UML%2FBonansea-Rivero_DataUML.png)

## Capa GUI:

![Bonansea-Rivero_GUIUML.png](UML%2FBonansea-Rivero_GUIUML.png)