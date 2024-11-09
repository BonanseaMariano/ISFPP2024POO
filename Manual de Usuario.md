# Manual de Usuario - Red Lan

## Contenido

1. [Requisitos Previos](#requisitos-previos)
2. [Instrucciones de Instalación y Ejecución](#instrucciones-de-instalación-y-ejecución)
    1. [Clonar el Repositorio](#1-clonar-el-repositorio)
    2. [Importar el Proyecto en tu IDE](#2-importar-el-proyecto-en-tu-ide)
    3. [Configurar el Archivo de Configuración](#3-configurar-el-archivo-de-configuración)
    4. [Seleccionar el Tipo de DAO en la Configuración del Factory](#4-seleccionar-el-tipo-de-dao-en-la-configuración-del-factory)
    5. [Ejecutar la Clase Principal](#5-ejecutar-la-clase-principal)
    6. [Interfaz Gráfica](#6-interfaz-gráfica)
        1. [Activar/Desactivar Modo Simulación](#61-activar-desactivar-modo-simulación)
        2. [ABM para Equipos](#62-abm-para-equipos)
        3. [ABM para Conexiones](#63-abm-para-conexiones)
        4. [ABM para Tipos de Equipos](#64-abm-para-tipos-de-equipos)
        5. [ABM para Tipos de Cables](#65-abm-para-tipos-de-cables)
        6. [ABM para Ubicaciones](#66-abm-para-ubicaciones)
        7. [ABM para Tipos de Puertos](#67-abm-para-tipos-de-puertos)
        8. [Ruta entre dos Equipos](#68-ruta-entre-dos-equipos)
        9. [Ping a una IP](#69-ping-a-una-ip)
        10. [Ping a un Rango de IPs](#610-ping-a-un-rango-de-ips)
        11. [Mapa de Estado de la Red](#611-mapa-de-estado-de-la-red)
        12. [Problemas de Conexión](#612-problemas-de-conexión)
    7. [Persistencia de Datos](#7-persistencia-de-datos)
3. [Enlaces Útiles](#enlaces-útiles)

## Requisitos Previos

- **Java 22**: Asegúrate de tener instalada la versión 22 de Java en tu sistema.
- **IDE**: Utiliza un entorno de desarrollo integrado (IDE) como IntelliJ IDEA, Eclipse, o NetBeans.

## Instrucciones de Instalación y Ejecución

### 1. Clonar el Repositorio

Clona el repositorio en tu máquina local:

```bash
git clone https://github.com/BonanseaMariano/ISFPP2024POO.git
cd ISFPP2024POO
```

### 2. Importar el Proyecto en tu IDE

Importa el proyecto clonado en tu IDE preferido.

### 3. Configurar el Archivo de Configuración

Edita el archivo de configuración `config.properties` ubicado en `src/main/resources/` para establecer el idioma y otros
parámetros:

```properties
language=en
country=US
labels=messages
```

### 4. Seleccionar el Tipo de DAO en la Configuración del Factory

Edita la configuración de la clase `Factory` para seleccionar el tipo de DAO que deseas utilizar:

- **Archivo**: `DAOEquipoImplFile`, `DAOConexionImplFile`
- **SQLite**: `DAOEquipoImplSqlite`, `DAOConexionImplSqlite`

### 5. Ejecutar la Clase Principal

Ejecuta la clase principal `RedLan` desde tu IDE:

- Ruta del archivo: `src/main/java/app/RedLan.java`
- Método principal: `public static void main(String[] args)`

```java
public class RedLan {
    public static void main(String[] args) {
        RedLan redLan = new RedLan();
        redLan.setUp();
    }
}
```

### 6. Interfaz Gráfica

Una vez ejecutada la aplicación, se abrirá la interfaz gráfica de usuario (GUI) de Red Lan. A continuación, se detallan
las funcionalidades disponibles:

- ### 6.1 Activar/Desactivar Modo Simulación

Aquí se incluirá una imagen que muestre cómo activar o desactivar el modo simulación.

- ### 6.2 ABM para Equipos

Aquí se incluirá una imagen que muestre cómo realizar ABM (Alta, Baja, Modificación) para equipos.

- ### 6.3 ABM para Conexiones

Aquí se incluirá una imagen que muestre cómo realizar ABM para conexiones.

- ### 6.4 ABM para Tipos de Equipos

Aquí se incluirá una imagen que muestre cómo realizar ABM para tipos de equipos.

- ### 6.5 ABM para Tipos de Cables

Aquí se incluirá una imagen que muestre cómo realizar ABM para tipos de cables.

- ### 6.6 ABM para Ubicaciones

Aquí se incluirá una imagen que muestre cómo realizar ABM para ubicaciones.

- ### 6.7 ABM para Tipos de Puertos

Aquí se incluirá una imagen que muestre cómo realizar ABM para tipos de puertos.

- ### 6.8 Ruta entre dos Equipos

Aquí se incluirá una imagen que muestre cómo visualizar la ruta entre dos equipos.

- ### 6.9 Ping a una IP

Aquí se incluirá una imagen que muestre cómo realizar un ping a una IP.

- ### 6.10 Ping a un Rango de IPs

Aquí se incluirá una imagen que muestre cómo realizar un ping a un rango de IPs.

- ### 6.11 Mapa de Estado de la Red

Aquí se incluirá una imagen que muestre el mapa de estado de la red.

- ### 6.12 Problemas de Conexión

Aquí se incluirá una imagen que muestre cómo visualizar problemas de conexión.

### 7. Persistencia de Datos

El sistema permite la persistencia de datos mediante archivos de texto o una base de datos SQLite. Configura el tipo de
DAO en la clase `Factory` como se mencionó anteriormente.

## Enlaces Útiles

- [Repositorio del Proyecto](https://github.com/BonanseaMariano/ISFPP2024POO)
- [Documentación Javadoc](https://bonanseamariano.github.io/ISFPP2024POO/)




