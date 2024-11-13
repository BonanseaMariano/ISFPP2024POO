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

### 3. Modificar el Archivo de Configuración

Edita el archivo de configuración `config.properties` ubicado en `src/main/resources/` para establecer el idioma y otros
parámetros:

```properties
labels=labels
language=en
country=US
```

como por ejemplo:

- Configuración de español españa:
  ```properties
    labels=labels
    language=es
    country=ES
    ```
- Configuración de ingles estado unidense:
  ```properties
  labels=labels
  language=en
  country=US
  ```

### 4. Seleccionar el Tipo de DAO en la Configuración del Factory

Edita la configuración de la clase `Factory` para seleccionar el tipo de DAO que deseas utilizar:

- **Archivo**:
    ```properties
    TIPO_CABLE=data.implementations.file.DAOTipoCableImplFile
    TIPO_EQUIPO=data.implementations.file.DAOTipoEquipoImplFile
    TIPO_PUERTO=data.implementations.file.DAOTipoPuertoImplFile
    UBICACION=data.implementations.file.DAOUbicacionImplFile
    EQUIPO=data.implementations.file.DAOEquipoImplFile
    CONEXION=data.implementations.file.DAOConexionImplFile
    ```
- **SQLite**:
    ```properties
    TIPO_CABLE=data.implementations.sqlite.DAOTipoCableImplSqlite
    TIPO_EQUIPO=data.implementations.sqlite.DAOTipoEquipoImplSqlite
    TIPO_PUERTO=data.implementations.sqlite.DAOTipoPuertoImplSqlite
    UBICACION=data.implementations.sqlite.DAOUbicacionImplSqlite
    EQUIPO=data.implementations.sqlite.DAOEquipoImplSqlite
    CONEXION=data.implementations.sqlite.DAOConexionImplSqlite
    ```

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
![Manual_Usuario_PantallaPrincipalGUI.png](Manual%20de%20usuario%2FManual_Usuario_PantallaPrincipalGUI.png)

- ### 6.1 Activar/Desactivar Modo Simulación

Estando en la pantalla principal para cambiar del modo simulación al modo normal primero debemos dirigirnos
a la parte superior izquierda en donde dice modo.
![Manual_Usuario_ModoSimulacionPaso1.png](Manual%20de%20usuario%2FManual_Usuario_ModoSimulacionPaso1.png)
Una vez hecho esto nos saldra un checkbox que dice "Simulacion", lo apretamos y con eso ya cambiarimos el modo.
![Manual_Usuario_ModoSimulacionPaso2.png](Manual%20de%20usuario%2FManual_Usuario_ModoSimulacionPaso2.png)
Para volver al modo simulacion simplemente debemos repretir el proceso.

- ### 6.2 ABM para Equipos

Para realizar cualquiera de las siguientes acciones con equipos:

- Agregar un equipo
- Borrar un equipo
- Modificar un equipo
- Agregar un puerto a un equipo
- Borrar un puerto de un equipo
- Modificar un puerto de un equipo
- Agregar una ip a un equipo
- Borrar una ip de un equipo
- Modificar una ip de un equipo

Una vez abierta la aplicacion y con la interfaz grafica de usuario a la vista lo primero que debemos hacer es apretar el
boton que dice
Equipos en el lado derecho de la ventana como indica la siguiente imagen
![Manual_Usuario_EquipoABM.png](Manual%20de%20usuario%2FManual_Usuario_EquipoABM.png)<br>

Una vez hecho esto se abrira la siguiente ventana
![Manual_Usuario_EquipoABMPaso2.png](Manual%20de%20usuario%2FManual_Usuario_EquipoABMPaso2.png)<br>

- ### 6.2.1 Agregar un Equipo
  Una vez abierta la ventana de equipos para agregar un equipo debemos apretar el boton que dice "Agregar" en la parte
  posterior derecha.
  ![Manual_Usuario_EquipoAgregarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_EquipoAgregarPaso1.png)<br>
  Este boton nos abrira otra ventana como la siguiente donde podremos agregar la informacion de nuestro equipo.
  ![Manual_Usuario_EquipoAgregarPaso2.png](Manual%20de%20usuario%2FManual_Usuario_EquipoAgregarPaso2.png)<br>
  Una vez ingresados los datos de nuestro equipo apretamos en el boton que dice "Aceptar".
  ![Manual_Usuario_EquipoAgregarPaso3.png](Manual%20de%20usuario%2FManual_Usuario_EquipoAgregarPaso3.png)<br>
  Si el equipo se pudo agregar correctamente mostrara el siguiente mensaje, le damos al boton de "OK".
  ![Manual_Usuario_EquipoAgregarPaso4.png](Manual%20de%20usuario%2FManual_Usuario_EquipoAgregarPaso4.png)<br>
  En el caso de que nos muestre un cartel de error nos dejará modificar la información cargada para modificarla y volver
  a intentarlo.
  Una vez que nos muestre el cartel de que se pudo agregar podremos comprobar en la ventana de equipos que ya se
  encuentra cargado.

- ### 6.2.2 Modificar un Equipo
  Una vez abierta la ventana de equipos para modificar un equipo lo primero que debemos hacer es seleccionar la fila del
  equipo que deseamos modificar y a continuacion apretar el boton que dice "Modificar" en la parte posterior central.
  ![Manual_Usuario_EquipoModificarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_EquipoModificarPaso1.png)<br>
  Esto nos abrira una ventana como la siguiente donde podremos modificar todos los datos del equipo salvo por el codigo.
  ![Manual_Usuario_EquipoModificarPaso2.png](Manual%20de%20usuario%2FManual_Usuario_EquipoModificarPaso2.png)<br>
  Una vez modificados los datos apretamos el boton de "Aceptar" para confirmar la modificacion.
  ![Manual_Usuario_EquipoModificarPaso3.png](Manual%20de%20usuario%2FManual_Usuario_EquipoModificarPaso3.png)<br>
  Si no hubo ningun problema con las modificaciones realizadas mostrara el siguiente cartel y lo cerramos apretando en
  el boton de "OK".
  ![Manual_Usuario_EquipoModificarPaso4.png](Manual%20de%20usuario%2FManual_Usuario_EquipoModificarPaso4.png)<br>
  Una vez hecho todo esto el equipo se mostrara en la ventana de equipos con los datos modificados.

- ### 6.2.3 Borrar un Equipo
  Una vez abierta la ventana de equipos para borrar un equipo lo primero que debemos hacer es seleccionar la fila del
  equipo que deseamos modificar y a continuacion apretar el boton que dice "Borrar" en la parte posterior derecha de la
  ventana.
  ![Manual_Usuario_EquipoBorrarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_EquipoBorrarPaso1.png)<br>
  Esta accion nos mostrara un cartel para confirmar la eliminacion del equipo, le damos click al boton que dice "YES".
  ![Manual_Usuario_EquipoBorrarPaso2.png](Manual%20de%20usuario%2FManual_Usuario_EquipoBorrarPaso2.png)<br>
  Y si no hubo ningun error en la eliminacion nos deberia mostrar el siguiente cartel para corroborarlo y le damos a "
  OK" para cerrarlo.
  ![Manual_Usuario_EquipoBorrarPaso3.png](Manual%20de%20usuario%2FManual_Usuario_EquipoBorrarPaso3.png)<br>
  Con esto hecho podremos comprobar que el equipo ya no se encuentra en la tabla de equipos.

- ### 6.2.4 ABM de puertos de un equipo
  Para agregar, modificar o borrar un puerto de un equipo lo primero que debemos hacer es buscarlo en la tabla de
  equipos y estando en esta fila
  seleccionar sobre la casilla que dice "Ver puertos" en la columna de "Puertos del equipo".
  ![Manual_Usuario_EquipoAgregarPuertoPaso1.png](Manual%20de%20usuario%2FManual_Usuario_EquipoAgregarPuertoPaso1.png)<br>
  Esta accion nos abrira una ventana como la siguiente donde podremos realizar la accion deseada.
  ![Manual_Usuario_EquipoABTPuerto.png](Manual%20de%20usuario%2FManual_Usuario_EquipoABTPuerto.png)<br>

- ### 6.2.4.1 Agregar un puerto a un equipo
  Con la ventana de puertos de un equipo abierta, deberemos dirigirnos a la parte inferior izquierda y apretar el boton
  que dice "Agregar".
  ![Manual_Usuario_EquipoAgregarPuertoPaso2.png](Manual%20de%20usuario%2FManual_Usuario_EquipoAgregarPuertoPaso2.png)<br>
  Esto nos abrira otra ventana como la siguiente donde podremos especificar el tipo de puerto y la cantidad que tiene,
  una vez cargados los campos apretamos en el boton que dice "OK" para agregarlo.
  ![Manual_Usuario_EquipoAgregarPuertoPaso3.png](Manual%20de%20usuario%2FManual_Usuario_EquipoAgregarPuertoPaso3.png)<br>
  Si todo salio bien nos saldra una ventana como la siguiente para confirmanos esto, aqui apretaremos el boton que
  dice "Ok" para cerrarla.
  ![Manual_Usuario_EquipoAgregarPuertoPaso4.png](Manual%20de%20usuario%2FManual_Usuario_EquipoAgregarPuertoPaso4.png)<br>
  Una vez realizado todo esto podremos confirmar que el puerto fue agregado en la ventana de puertos del equipo.

- ### 6.2.4.2 Borrar un puerto de un equipo
  Con la ventana de puertos de un equipo abierta, para borrar un puerto debemos posicionar sobre la fila del puerto que
  deseamos borrar y clickearla,
  una vez hecho esto nos marcara como que esta seleccionada la fila, ahora deberemos dirigirnos a la parte inferior
  derecha y apretar el boton que dice "Borrar".
  ![Manual_Usuario_EquipoBorrarPuertoPaso1.png](Manual%20de%20usuario%2FManual_Usuario_EquipoBorrarPuertoPaso1.png)<br>
  Esto nos abrira una nueva ventana pidiendonos confirmar la eliminacion del puerto y le damos click a "YES".
  ![Manual_Usuario_EquipoBorrarPuertoPaso2.png](Manual%20de%20usuario%2FManual_Usuario_EquipoBorrarPuertoPaso2.png)<br>
  Si no hubo ningun inconveniente con la eliminacion del puerto nos mostrara una ventana como la siguiente siguiente
  donde le haremos click al boton de "OK" para cerrarla.
  ![Manual_Usuario_EquipoBorrarPuertoPaso3.png](Manual%20de%20usuario%2FManual_Usuario_EquipoBorrarPuertoPaso3.png)<br>
  Con todo esto hecho si no hubieron errores podremos divisar que el puerto ya no se encuentra en la ventana de puertos.
  Importante: si el equipo posee un puerto y deseamos eliminarnos nos saldra un cartel de error que no nos permitira
  realizar esta acción, esto es debido
  a que despues de agregar un puerto a un equipo no puede tener menos de 1 puerto.

- ### 6.2.4.3 Modificar un puerto de un equipo
  Con la ventana de puertos de un equipo abierta, para modificar un puerto debemos posicionarnos sobre la fila del
  puerto que deseamos modificar y clickearla
  , una vez hecho esto deberemos dirigirnos a la parte inferior central y apretar el boton que dice "Modificar".
  ![Manual_Usuario_EquipoModificarPuertoPaso1.png](Manual%20de%20usuario%2FManual_Usuario_EquipoModificarPuertoPaso1.png)<br>
  Esto nos abrira otra ventana como la siguiente donde podremos cambiar la cantidad de puertos y el tipo de dichos
  puertos, una vez modificados apretamos en el boton que dice "OK" para completar la acción.
  ![Manual_Usuario_EquipoModificarPuertoPaso2.png](Manual%20de%20usuario%2FManual_Usuario_EquipoModificarPuertoPaso2.png)<br>
  Si todo salio bien nos saldra una ventana como la siguiente para confirmanos esto, aqui apretaremos el boton que
  dice "Ok" para cerrarla.  
  ![Manual_Usuario_EquipoModificarPuertoPaso3.png](Manual%20de%20usuario%2FManual_Usuario_EquipoModificarPuertoPaso3.png)<br> 
  Una vez realizado todo esto podremos confirmar que el puerto fue modificado en la ventana de puertos del equipo.

- ### 6.2.5 ABM de IPs de un equipo
  Para agregar, modificar o borrar una ip de un equipo lo primero que debemos hacer es buscarlo en la tabla de equipos y
  estando en esta fila
  seleccionar sobre la casilla que dice "Ver ips" en la columna de "ips del equipo".
  ![Manual_Usuario_EquipoAgregarIPPaso1.png](Manual%20de%20usuario%2FManual_Usuario_EquipoAgregarIPPaso1.png)<br>
  Esta accion nos abrira una ventana como la siguiente donde podremos realizar la accion deseada.
  ![Manual_Usuario_EquipoABMIP.png](Manual%20de%20usuario%2FManual_Usuario_EquipoABMIP.png)<br>

- ### 6.2.5.1 Agregar una ip a un equipo
  Con la ventana de IPs de un equipo abierta, deberemos dirigirnos a la parte inferior izquierda y apretar el boton que
  dice "Agregar".
  ![Manual_Usuario_EquipoAgregarIPPaso2.png](Manual%20de%20usuario%2FManual_Usuario_EquipoAgregarIPPaso2.png)<br>
  Esto nos abrira otra ventana como la siguiente donde podremos escribir la ip de nuestro equipo separando con un punto
  cada tres numeros (Ej: 255.255.255.255), una vez escrita la ip apretamos en el boton que dice "OK" para agregarla.
  ![Manual_Usuario_EquipoAgregarIPPaso3.png](Manual%20de%20usuario%2FManual_Usuario_EquipoAgregarIPPaso3.png)<br>
  Si todo salio bien nos saldra una ventana como la siguiente para confirmanos esto, aqui apretaremos el boton que
  dice "Ok" para cerrarla.
  ![Manual_Usuario_EquipoAgregarIPPaso4.png](Manual%20de%20usuario%2FManual_Usuario_EquipoAgregarIPPaso4.png)<br>
  Una vez realizado todo esto podremos confirmar que la ip fue agregada en la ventana de IPs del equipo.

- ### 6.2.5.2 Borrar una ip de un equipo
  Con la ventana de IPs de un equipo abierta, para borrar una ip debemos posicionarnos sobre la fila de la ip que
  deseamos borrar y clickearla,
  una vez hecho esto nos marcara como que esta seleccionada la fila, ahora deberemos dirigirnos a la parte inferior
  derecha y apretar el boton que dice "Borrar".
  ![Manual_Usuario_EquipoBorrarIPPaso1.png](Manual%20de%20usuario%2FManual_Usuario_EquipoBorrarIPPaso1.png)<br>
  Esto nos abrira una nueva ventana pidiendonos confirmar la eliminacion de la ip y le damos click a "YES".
  ![Manual_Usuario_EquipoBorrarIPPaso2.png](Manual%20de%20usuario%2FManual_Usuario_EquipoBorrarIPPaso2.png)<br>
  Si no hubo ningun inconveniente con la eliminacion de la ip nos nos mostrara una ventana como la siguiente donde le
  haremos click al boton de "OK" para cerrarla.
  ![Manual_Usuario_EquipoBorrarIPPaso3.png](Manual%20de%20usuario%2FManual_Usuario_EquipoBorrarIPPaso3.png)<br>
  Con todo esto hecho si no hubieron errores podremos divisar que la ip ya no se encuentra en la ventana de IPs del
  equipo.

- ### 6.2.5.3 Modificar una ip de un equipo
  Con la ventana de IPs de un equipo abierta, para modificar una ip debemos posicionarnos sobre la fila de la ip que
  deseamos modificar y clickearla
  , una vez hecho esto deberemos dirigirnos a la parte inferior central y apretar el boton que dice "Modificar".
  ![Manual_Usuario_EquipoModificarIPPaso1.png](Manual%20de%20usuario%2FManual_Usuario_EquipoModificarIPPaso1.png)<br>
  Esto nos abrira otra ventana como la siguiente donde podremos cambiar la ip siguiendo el mismo formato, una vez
  modificada apretamos en el boton que dice "OK" para completar la acción.
  ![Manual_Usuario_EquipoModificarIPPaso2.png](Manual%20de%20usuario%2FManual_Usuario_EquipoModificarIPPaso2.png)<br>
  Si todo salio bien nos saldra una ventana como la siguiente para confirmanos esto, aqui apretaremos el boton que
  dice "Ok" para cerrarla.
  ![Manual_Usuario_EquipoModificarIPPaso3.png](Manual%20de%20usuario%2FManual_Usuario_EquipoModificarIPPaso3.png)<br>
  Una vez realizado todo esto podremos confirmar que la ip fue modificada en la ventana de IPs del equipo.

- ### 6.3 ABM para Conexiones
  Una vez abierta la aplicacion y con la interfaz grafica de usuario a la vista lo primero que debemos hacer es apretar
  el boton que dice
  "Conexion" en el lado derecho de la ventana como indica la siguiente imagen.
  ![Manual_Usuario_ConexionABMPaso1.png](Manual%20de%20usuario%2FManual_Usuario_ConexionABMPaso1.png)<br>
  Una vez hecho esto se abrira la siguiente ventana
  ![Manual_Usuario_ConexionABMPaso2.png](Manual%20de%20usuario%2FManual_Usuario_ConexionABMPaso2.png)<br>

- ### 6.3.1 Agregar una conexion
  Con la ventana de Conexiones abierta, deberemos dirigirnos a la parte inferior izquierda y apretar el boton que dice "
  Agregar".
  ![Manual_Usuario_ConexionAgregarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_ConexionAgregarPaso1.png)<br>
  Esto nos abrira otra ventana como la siguiente donde podremos elegir los equipos de la conexion, el tipo de puerto y
  el tipo de cable que los conecta,
  una vez puesta esta informacion apretamos en el boton que dice "OK" para agregarla.
  ![Manual_Usuario_ConexionAgregarPaso2.png](Manual%20de%20usuario%2FManual_Usuario_ConexionAgregarPaso2.png)<br>
  Si todo salio bien nos saldra una ventana como la siguiente para confirmanos esto, aqui apretaremos el boton que
  dice "Ok" para cerrarla.
  ![Manual_Usuario_ConexionAgregarPaso3.png](Manual%20de%20usuario%2FManual_Usuario_ConexionAgregarPaso3.png)<br>
  Una vez realizado todo esto podremos confirmar que la conexion fue agregada en la ventana de Conexiones.

- ### 6.3.2 Borrar una conexion
  Con la ventana de Conexiones abierta, para borrar una conexion nos debemos posicionar sobre la fila de la conexion que
  deseamos borrar y clickearla,
  una vez hecho esto nos marcara como que esta seleccionada la fila, ahora deberemos dirigirnos a la parte inferior
  derecha y apretar el boton que dice "Borrar".
  ![Manual_Usuario_ConexionBorrarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_ConexionBorrarPaso1.png)<br>
  Esto nos abrira una nueva ventana pidiendonos confirmar la eliminacion del puerto y le damos click a "YES".
  ![Manual_Usuario_ConexionBorrarPaso2.png](Manual%20de%20usuario%2FManual_Usuario_ConexionBorrarPaso2.png)<br>
  Si no hubo ningun inconveniente con la eliminacion de la conexion nos nos mostrara una ventana como la siguiente donde
  le haremos click al boton de "OK" para cerrarla.
  ![Manual_Usuario_ConexionBorrarPaso3.png](Manual%20de%20usuario%2FManual_Usuario_ConexionBorrarPaso3.png)<br>
  Con todo esto hecho si no hubieron errores podremos divisar que la conexion ya no se encuentra en la ventana de
  conexiones.

- ### 6.3.2 Modificar una conexion
  Una vez abierta la ventana de conexion, para modificar una conexion lo primero que debemos hacer es seleccionar la
  fila de la conexion
  que deseamos modificar y a continuacion apretar el boton que dice "Modificar" en la parte posterior central.
  ![Manual_Usuario_ConexionModificarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_ConexionModificarPaso1.png)<br>
  Esto nos abrira una ventana como la siguiente donde podremos modificar el puerto de los equipos y el cable que los
  conecta, una vez modificada
  apretamos el boton que dice "OK" para confirmar el cambio.
  ![Manual_Usuario_ConexionModificarPaso2.png](Manual%20de%20usuario%2FManual_Usuario_ConexionModificarPaso2.png)<br>
  Si no hubo ningun problema con las modificaciones realizadas mostrara el siguiente cartel y lo cerramos apretando en
  el boton de "OK".
  ![Manual_Usuario_ConexionModificarPaso3.png](Manual%20de%20usuario%2FManual_Usuario_ConexionModificarPaso3.png)<br>
  Una vez hecho todo esto la conexion se mostrara en la ventana de conexiones con los datos modificados.

- ### 6.4 ABM para Tipos de Equipos
  Una vez abierta la aplicacion y con la interfaz grafica de usuario a la vista lo primero que debemos hacer es apretar
  el boton que dice
  "Tipos de equipos" en el lado derecho de la ventana como indica la siguiente imagen.
  ![Manual_Usuario_TipoEquipoABMPaso1.png](Manual%20de%20usuario%2FManual_Usuario_TipoEquipoABMPaso1.png)<br>
  Una vez hecho esto se abrira la siguiente ventana
  ![Manual_Usuario_TipoEquipoABMPaso2.png](Manual%20de%20usuario%2FManual_Usuario_TipoEquipoABMPaso2.png)<br>

- ### 6.4.1 Agregar un Tipo de Equipo
  Con la ventana de tipo de equipos abierta, deberemos dirigirnos a la parte inferior izquierda y apretar el boton que
  dice "Agregar".
  ![Manual_Usuario_TipoEquipoAgregarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_TipoEquipoAgregarPaso1.png)<br>
  Esto nos abrira otra ventana como la siguiente donde podremos agregar el codigo del tipo de equipo y la descripcion,
  una vez escrita esta informacion apretamos en el boton que dice "OK" para agregarlo.
  ![Manual_Usuario_TipoEquipoAgregarPaso2.png](Manual%20de%20usuario%2FManual_Usuario_TipoEquipoAgregarPaso2.png)<br>
  Si todo salio bien nos saldra una ventana como la siguiente para confirmanos esto, aqui apretaremos el boton que
  dice "Ok" para cerrarla.
  ![Manual_Usuario_TipoEquipoAgregarPaso3.png](Manual%20de%20usuario%2FManual_Usuario_TipoEquipoAgregarPaso3.png)<br>
  Una vez realizado todo esto podremos confirmar que la el tipo de equipo fue agregado en la ventana de tipo de equipos.

- ### 6.4.2 Borrar un Tipo de Equipo
  Con la ventana de tipo de equipos abierta, para borrar un tipo de equipo nos debemos posicionar sobre la fila de la
  conexion que deseamos borrar y clickearla,
  una vez hecho esto nos marcara como que esta seleccionada la fila, ahora deberemos dirigirnos a la parte inferior
  derecha y apretar el boton que dice "Borrar".
  ![Manual_Usuario_TipoEquipoBorrarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_TipoEquipoBorrarPaso1.png)<br>
  Esto nos abrira una nueva ventana pidiendonos confirmar la eliminacion del tipo de equipo y le damos click a "YES".
  ![Manual_Usuario_TipoEquipoBorrarPaso2.png](Manual%20de%20usuario%2FManual_Usuario_TipoEquipoBorrarPaso2.png)<br>
  Si no hubo ningun inconveniente con la eliminacion del tipo de equipo nos mostrara una ventana como la siguiente donde
  le haremos click al boton de "OK" para cerrarla.
  ![Manual_Usuario_TipoEquipoBorrarPaso3.png](Manual%20de%20usuario%2FManual_Usuario_TipoEquipoBorrarPaso3.png)<br>
  Con todo esto hecho si no hubieron errores podremos divisar que el tipo de equipo ya no se encuentra en la ventana de
  tipos de equipos.

- ### 6.4.3 Modificar un Tipo de Equipo
  Una vez abierta la ventana de tipos de equipos, para modificar un tipo de equipo lo primero que debemos hacer es
  seleccionar la fila del tipo de equipo
  que deseamos modificar y a continuacion apretar el boton que dice "Modificar" en la parte posterior central.
  ![Manual_Usuario_TipoEquipoModificarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_TipoEquipoModificarPaso1.png)<br>
  Esto nos abrira una ventana como la siguiente donde podremos modificar la descripcion del tipo de equipo,una vez
  modificada
  apretamos el boton que dice "OK" para confirmar el cambio.
  ![Manual_Usuario_TipoEquipoModificarPaso2.png](Manual%20de%20usuario%2FManual_Usuario_TipoEquipoModificarPaso2.png)<br>
  Si no hubo ningun problema con las modificaciones realizadas mostrara el siguiente cartel y lo cerramos apretando en
  el boton de "OK".
  ![Manual_Usuario_TipoEquipoModificarPaso3.png](Manual%20de%20usuario%2FManual_Usuario_TipoEquipoModificarPaso3.png)<br>
  Una vez hecho todo esto el tipo de equipo se mostrara en la ventana de tipos de equipos con los datos modificados.

- ### 6.5 ABM para Tipos de Cables
  Una vez abierta la aplicacion y con la interfaz grafica de usuario a la vista lo primero que debemos hacer es apretar
  el boton que dice
  "Tipos de cables" en el lado derecho de la ventana como indica la siguiente imagen.
  ![Manual_Usuario_TipoCable_ABM.png](Manual%20de%20usuario%2FManual_Usuario_TipoCable_ABM.png)<br>
  Una vez hecho esto se abrira la siguiente ventana
  ![Manual_Usuario_TipoCableABMPaso2.png](Manual%20de%20usuario%2FManual_Usuario_TipoCableABMPaso2.png)<br>

- ### 6.5.1 Agregar un Tipo de Cable
  Con la ventana de tipo de cables abierta, deberemos dirigirnos a la parte inferior izquierda y apretar el boton que
  dice "Agregar".
  ![Manual_Usuario_TipoCableAgregarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_TipoCableAgregarPaso1.png)<br>
  Esto nos abrira otra ventana como la siguiente donde podremos agregar el codigo del tipo de cable, la descripcion del
  cable y su velocidad,
  una vez escrita esta informacion apretamos en el boton que dice "OK" para agregarlo.
  ![Manual_Usuario_TipoCableAgregarPaso2.png](Manual%20de%20usuario%2FManual_Usuario_TipoCableAgregarPaso2.png)<br>
  Si todo salio bien nos saldra una ventana como la siguiente para confirmanos esto, aqui apretaremos el boton que
  dice "Ok" para cerrarla.
  ![Manual_Usuario_TipoCableAgregarPaso3.png](Manual%20de%20usuario%2FManual_Usuario_TipoCableAgregarPaso3.png)<br>
  Una vez realizado todo esto podremos confirmar que la el tipo de cable fue agregado en la ventana de tipo de cable.

- ### 6.5.2 Borrar un Tipo de Cable
  Con la ventana de tipo de cables abierta, para borrar un tipo de cable nos debemos posicionar sobre la fila del cable
  que deseamos borrar y clickearla,
  una vez hecho esto nos marcara como que esta seleccionada la fila, ahora deberemos dirigirnos a la parte inferior
  derecha y apretar el boton que dice "Borrar".
  ![Manual_Usuario_TipoCableBorrarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_TipoCableBorrarPaso1.png)<br>
  Esto nos abrira una nueva ventana pidiendonos confirmar la eliminacion del tipo de cable y le damos click a "YES".
  ![Manual_Usuario_TipoCableBorrarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_TipoCableBorrarPaso1.png)<br>
  Si no hubo ningun inconveniente con la eliminacion del tipo de cable nos mostrara una ventana como la siguiente donde
  le haremos click al boton de "OK" para cerrarla.
  ![Manual_Usuario_TipoCableBorrarPaso3.png](Manual%20de%20usuario%2FManual_Usuario_TipoCableBorrarPaso3.png)<br>
  Con todo esto hecho si no hubieron errores podremos divisar que el tipo de cable ya no se encuentra en la ventana de
  tipos de cables.

- ### 6.5.3 Modificar un Tipo de Cable
  Una vez abierta la ventana de tipos de cables, para modificar un tipo de cable lo primero que debemos hacer es
  seleccionar la fila del tipo de cable
  que deseamos modificar y a continuacion apretar el boton que dice "Modificar" en la parte posterior central.
  ![Manual_Usuario_TipoCableModificarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_TipoCableModificarPaso1.png)<br>
  Esto nos abrira una ventana como la siguiente donde podremos modificar la descripcion del tipo de cable,una vez
  modificada
  apretamos el boton que dice "OK" para confirmar el cambio.
  ![Manual_Usuario_TipoCableModificarPaso2.png](Manual%20de%20usuario%2FManual_Usuario_TipoCableModificarPaso2.png)<br>
  Si no hubo ningun problema con las modificaciones realizadas mostrara el siguiente cartel y lo cerramos apretando en
  el boton de "OK".
  ![Manual_Usuario_TipoCableModificarPaso3.png](Manual%20de%20usuario%2FManual_Usuario_TipoCableModificarPaso3.png)<br>
  Una vez hecho todo esto el tipo de cable se mostrara en la ventana de tipos de cables con los datos modificados.

- ### 6.6 ABM para Ubicaciones
  Una vez abierta la aplicacion y con la interfaz grafica de usuario a la vista lo primero que debemos hacer es apretar
  el boton que dice
  "Ubicaciones" en el lado derecho de la ventana como indica la siguiente imagen.
  ![Manual_Usuario_UbicacionABMPaso1.png](Manual%20de%20usuario%2FManual_Usuario_UbicacionABMPaso1.png)<br>
  Una vez hecho esto se abrira la siguiente ventana
  ![Manual_Usuario_UbicacionABMPaso2.png](Manual%20de%20usuario%2FManual_Usuario_UbicacionABMPaso2.png)<br>

- ### 6.6.1 Agregar una Ubicacion
  Con la ventana de ubicaciones abierta, deberemos dirigirnos a la parte inferior izquierda y apretar el boton que
  dice "Agregar".
  ![Manual_Usuario_UbicacionAgregarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_UbicacionAgregarPaso1.png)<br>
  Esto nos abrira otra ventana como la siguiente donde podremos agregar el codigo de la ubicacion y la descripcion,
  una vez escrita esta informacion apretamos en el boton que dice "OK" para agregarla.
  ![Manual_Usuario_UbicacionAgregarPaso2.png](Manual%20de%20usuario%2FManual_Usuario_UbicacionAgregarPaso2.png)<br>
  Si todo salio bien nos saldra una ventana como la siguiente para confirmanos esto, aqui apretaremos el boton que
  dice "Ok" para cerrarla.
  ![Manual_Usuario_UbicacionAgregarPaso3.png](Manual%20de%20usuario%2FManual_Usuario_UbicacionAgregarPaso3.png)<br>
  Una vez realizado todo esto podremos confirmar que la el tipo de cable fue agregado en la ventana de tipo de cable.

- ### 6.6.2 Borrar una Ubicacion
  Con la ventana de ubicaciones abierta, para borrar un tipo de cable nos debemos posicionar sobre la fila de la
  ubicacion que deseamos borrar y clickearla,
  una vez hecho esto nos marcara como que esta seleccionada la fila, ahora deberemos dirigirnos a la parte inferior
  derecha y apretar el boton que dice "Borrar".
  ![Manual_Usuario_UbicacionBorrarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_UbicacionBorrarPaso1.png)<br>
  Esto nos abrira una nueva ventana pidiendonos confirmar la eliminacion de la ubicacion y le damos click a "OK".
  ![Manual_Usuario_UbicacionBorrarPaso2.png](Manual%20de%20usuario%2FManual_Usuario_UbicacionBorrarPaso2.png)<br>
  Si no hubo ningun inconveniente con la eliminacion de la ubicacion nos mostrara una ventana como la siguiente donde le
  haremos click al boton de "OK" para cerrarla.
  ![Manual_Usuario_UbicacionBorrarPaso3.png](Manual%20de%20usuario%2FManual_Usuario_UbicacionBorrarPaso3.png)<br>
  Con todo esto hecho si no hubieron errores podremos divisar que la ubicacion ya no se encuentra en la ventana de
  ubicaciones.

- ### 6.6.3 Modificar una Ubicacion
  Una vez abierta la ventana de ubicaciones, para modificar un tipo de cable lo primero que debemos hacer es seleccionar
  la fila de la ubicacion
  que deseamos modificar y a continuacion apretar el boton que dice "Modificar" en la parte posterior central.
  ![Manual_Usuario_UbicacionModificarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_UbicacionModificarPaso1.png)<br>
  Esto nos abrira una ventana como la siguiente donde podremos modificar la descripcion de la ubicacion,una vez
  modificada
  apretamos el boton que dice "OK" para confirmar el cambio.
  ![Manual_Usuario_UbicacionModificarPaso2.png](Manual%20de%20usuario%2FManual_Usuario_UbicacionModificarPaso2.png)<br>
  Si no hubo ningun problema con la modificacion realizada mostrara el siguiente cartel y lo cerramos apretando en el
  boton de "OK".
  ![Manual_Usuario_UbicacionModificarPaso3.png](Manual%20de%20usuario%2FManual_Usuario_UbicacionModificarPaso3.png)<br>
  Una vez hecho todo esto la ubicaion se mostrara en la ventana de Ubicaciones con los datos modificados.

- ### 6.7 ABM para Tipos de Puertos
  Una vez abierta la aplicacion y con la interfaz grafica de usuario a la vista lo primero que debemos hacer es apretar
  el boton que dice
  "Tipos de puertos" en el lado derecho de la ventana como indica la siguiente imagen.
  ![Manual_Usuario_TipoPuertoABMPaso1.png](Manual%20de%20usuario%2FManual_Usuario_TipoPuertoABMPaso1.png)<br>
  Una vez hecho esto se abrira la siguiente ventana
  ![Manual_Usuario_TipoPuertoABMPaso2.png](Manual%20de%20usuario%2FManual_Usuario_TipoPuertoABMPaso2.png)<br>

- ### 6.7.1 Agregar un Tipo de Puerto
  Con la ventana de tipos de puertos abierta, deberemos dirigirnos a la parte inferior izquierda y apretar el boton que
  dice "Agregar".
  ![Manual_Usuario_TipoPuertoAgregarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_TipoPuertoAgregarPaso1.png)<br>
  Esto nos abrira otra ventana como la siguiente donde podremos agregar el codigo del cable, la descripcion y la
  velocidad en MB,
  una vez escrita esta informacion apretamos en el boton que dice "OK" para agregarla.
  ![Manual_Usuario_TipoPuertoAgregarPaso2.png](Manual%20de%20usuario%2FManual_Usuario_TipoPuertoAgregarPaso2.png)<br>
  Si todo salio bien nos saldra una ventana como la siguiente para confirmanos esto, aqui apretaremos el boton que
  dice "Ok" para cerrarla.
  ![Manual_Usuario_TipoPuertoAgregarPaso3.png](Manual%20de%20usuario%2FManual_Usuario_TipoPuertoAgregarPaso3.png)<br>
  Una vez realizado todo esto podremos confirmar que el tipo de puerto fue agregado en la ventana de tipos de puertos.

- ### 6.7.2 Borrar un Tipo de Puerto
  Con la ventana del tipos de puertos abierta, para borrar un tipo de puerto nos debemos posicionar sobre la fila del
  tipo de puerto que deseamos borrar y clickearla,
  una vez hecho esto nos marcara como que esta seleccionada la fila, ahora deberemos dirigirnos a la parte inferior
  derecha y apretar el boton que dice "Borrar".
  ![Manual_Usuario_TipoPuertoBorrarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_TipoPuertoBorrarPaso1.png)<br>
  Esto nos abrira una nueva ventana pidiendonos confirmar la eliminacion de la ubicacion y le damos click a "OK".
  ![Manual_Usuario_TipoPuertoBorrarPaso2.png](Manual%20de%20usuario%2FManual_Usuario_TipoPuertoBorrarPaso2.png)<br>
  Si no hubo ningun inconveniente con la eliminacion del tipo de puerto nos mostrara una ventana como la siguiente donde
  le haremos click al boton de "OK" para cerrarla.
  ![Manual_Usuario_TipoPuertoBorrarPaso3.png](Manual%20de%20usuario%2FManual_Usuario_TipoPuertoBorrarPaso3.png)<br>
  Con todo esto hecho si no hubieron errores podremos divisar que el tipo de puerto ya no se encuentra en la ventana de
  tipos de puertos.

- ### 6.7.3 Modificar un Tipo de Puerto
  Una vez abierta la ventana de tipos de puertos, para modificar un tipo de puerto lo primero que debemos hacer es
  seleccionar la fila del tipo de puerto
  que deseamos modificar y a continuacion apretar el boton que dice "Modificar" en la parte posterior central.
  ![Manual_Usuario_TipoPuertoModificarPaso1.png](Manual%20de%20usuario%2FManual_Usuario_TipoPuertoModificarPaso1.png)<br>
  Esto nos abrira una ventana como la siguiente donde podremos modificar la descripcion del tipo de puerto,una vez
  modificada
  apretamos el boton que dice "OK" para confirmar el cambio.
  ![Manual_Usuario_TipoPuertoModificarPaso2.png](Manual%20de%20usuario%2FManual_Usuario_TipoPuertoModificarPaso2.png)<br>
  Si no hubo ningun problema con la modificacion realizada mostrara el siguiente cartel y lo cerramos apretando en el
  boton de "OK".
  ![Manual_Usuario_TipoPuertoModificarPaso3.png](Manual%20de%20usuario%2FManual_Usuario_TipoPuertoModificarPaso3.png)<br>
  Una vez hecho todo esto el tipo de puerto se mostrara en la ventana de tipos de puertos con los datos modificados.

- ### 6.8 Ruta entre dos Equipos
  Una vez abierta la aplicacion y con la interfaz grafica de usuario a la vista lo primero que debemos hacer es apretar
  el boton que dice
  "Tipos de puertos" en el lado derecho de la ventana como indica la siguiente imagen.
  ![Manual_Usuario_RutaEntreEquiposPaso1.png](Manual%20de%20usuario%2FManual_Usuario_RutaEntreEquiposPaso1.png)<br>

- ### 6.8.1 Ruta entre dos Equipos modo Simulasion
  Una vez hecho esto se abrira la siguiente ventana donde seleccionaremos los codigos de los dos equipos que queremos
  saber la ruta y clickeamos el boton que dice "Buscar".
  ![Manual_Usuario_RutaEntreEquiposPaso2Simulasion.png](Manual%20de%20usuario%2FManual_Usuario_RutaEntreEquiposPaso2Simulasion.png)<br>
  Esto nos mostrara una ventana como la siguiente que especificara la conexion entre los distintos equipos que forman la
  ruta entre ambos equipos (si esta existe, si no mostrara un cartel que especifica que no hay ruta entre ambos)
  ![Manual_Usuario_RutaEntreEquiposPaso3Simulasion.png](Manual%20de%20usuario%2FManual_Usuario_RutaEntreEquiposPaso3Simulasion.png)<br>

- ### 6.8.2 Ruta entre dos Equipos modo Real
  Una vez hecho esto se abrira la siguiente ventana donde tendremos que indicar la ip o direccion de la cual queremos
  saber la ruta desde nuestro equipo hasta esta.
  ![Manual_Usuario_RutaEntreEquiposPaso2Real.png](Manual%20de%20usuario%2FManual_Usuario_RutaEntreEquiposPaso2Real.png)<br>
  una vez hecho esto hacemos click sobre el boton de la parte inferior que dice "Iniciar ruta" para comenzar.
  ![Manual_Usuario_RutaEntreEquiposPaso3Real.png](Manual%20de%20usuario%2FManual_Usuario_RutaEntreEquiposPaso3Real.png)<br>
  A continuacion nos mostrara en tiempo real la ruta entre nuestro equipo y la direccion ip seleccionada.
  ![Manual_Usuario_RutaEntreEquiposPaso4Real.png](Manual%20de%20usuario%2FManual_Usuario_RutaEntreEquiposPaso4Real.png)<br>

- ### 6.9 Ping a una IP
  Una vez abierta la aplicacion y con la interfaz grafica de usuario a la vista lo primero que debemos hacer es apretar
  el boton que dice
  "Ping a una ip" en el lado derecho de la ventana como indica la siguiente imagen.
  ![Manual_Usuario_PingIPPaso1.png](Manual%20de%20usuario%2FManual_Usuario_PingIPPaso1.png)<br>

- ### 6.9.1 Ping a una IP modo Simulasion
  Una vez hecho esto se abrira la siguiente ventana donde seleccionaremos la ip que queremos consultar si esta activa y
  le damos click al boton que dice "ping".
  ![Manual_Usuario_PingIPPaso2Simulasion.png](Manual%20de%20usuario%2FManual_Usuario_PingIPPaso2Simulasion.png)<br>
  Esto nos mostrara una ventana como la siguiente que indicara si el equipo con la ip especificada se encuentra activo o
  inactivo.
  ![Manual_Usuario_PingIPPaso3Simulasion.png](Manual%20de%20usuario%2FManual_Usuario_PingIPPaso3Simulasion.png)<br>

- ### 6.9.2 Ping a una IP modo Real
  Una vez hecho esto se abrira la siguiente ventana donde tendremos que indicar la ip o direccion a la cual deseamos
  hacer ping e indicar la cantidad de pings a realizar.
  ![Manual_Usuario_PingIPPaso2Real.png](Manual%20de%20usuario%2FManual_Usuario_PingIPPaso2Real.png)<br>
  una vez hecho esto hacemos click sobre el boton de la parte inferior que dice "Iniciar ping" para comenzar.
  ![Manual_Usuario_PingIPPaso3Real.png](Manual%20de%20usuario%2FManual_Usuario_PingIPPaso3Real.png)<br>
  A continuacion nos mostrara en tiempo real la especificacion del ping realizado a la direccion ip seleccionada.
  ![Manual_Usuario_PingRangoPaso4Real.png](Manual%20de%20usuario%2FManual_Usuario_PingRangoPaso4Real.png)<br>

- ### 6.10 Ping a un Rango de IPs
  Una vez abierta la aplicacion y con la interfaz grafica de usuario a la vista lo primero que debemos hacer es apretar
  el boton que dice
  "Ping a un rango de IPs" en el lado derecho de la ventana como indica la siguiente imagen.
  ![Manual_Usuario_PingRangoPaso1.png](Manual%20de%20usuario%2FManual_Usuario_PingRangoPaso1.png)<br>

- ### 6.10.1 Ping a un Rango de IPs modo Simulasion
  Una vez hecho esto se abrira la siguiente ventana donde tendremos que seleccionar las ips entre las cuales que
  queremos hacer el ping para saber si se encuentran activas y le damos click al boton que dice "ping".
  ![Manual_Usuario_PingRangoPaso2Simulasion.png](Manual%20de%20usuario%2FManual_Usuario_PingRangoPaso2Simulasion.png)<br>
  Esto nos mostrara una ventana como la siguiente que indicara si cada ip especificada se encuentra activa o inactiva.
  ![Manual_Usuario_PingRangoPaso3Simulasion.png](Manual%20de%20usuario%2FManual_Usuario_PingRangoPaso3Simulasion.png)<br>

- ### 6.10.2 Ping a un Rango de IPs modo Real
  Una vez hecho esto se abrira la siguiente ventana donde tendremos que indicar las ip o direcciones a las cuales
  deseamos que se haga un ping de las ips entre estas.
  ![Manual_Usuario_PingRangoPaso2Real.png](Manual%20de%20usuario%2FManual_Usuario_PingRangoPaso2Real.png)<br>
  una vez hecho esto hacemos click sobre el boton de la parte inferior que dice "Iniciar ping" para comenzar.
  ![Manual_Usuario_PingRangoPaso3Real.png](Manual%20de%20usuario%2FManual_Usuario_PingRangoPaso3Real.png)<br>
  A continuacion nos mostrara en tiempo real si cada ip entre ese rango se encuentra activa o inactiva.
  ![Manual_Usuario_PingRangoPaso4Real.png](Manual%20de%20usuario%2FManual_Usuario_PingRangoPaso4Real.png)<br>

- ### 6.11 Mapa de Estado de la Red
  Una vez abierta la aplicacion y con la interfaz grafica de usuario a la vista lo primero que debemos hacer es apretar
  el boton que dice
  "Mapa de estado de la red" en el lado derecho de la ventana como indica la siguiente imagen.
  ![Manual_Usuario_MapaEstadoPaso1.png](Manual%20de%20usuario%2FManual_Usuario_MapaEstadoPaso1.png)<br>
  Este boton nos abrira una ventana como la siguiente que nos mostrara el codigo de todos los equipos de la red y su
  estado en esta misma.
  ![Manual_Usuario_MapaEstadoPaso2.png](Manual%20de%20usuario%2FManual_Usuario_MapaEstadoPaso2.png)<br>

- ### 6.12 Problemas de Conexión
  Una vez abierta la aplicacion y con la interfaz grafica de usuario a la vista lo primero que debemos hacer es apretar
  el boton que dice
  "Problemas de conexion" en el lado derecho de la ventana como indica la siguiente imagen.
  ![Manual_Usuario_ProblemasConexionPaso1.png](Manual%20de%20usuario%2FManual_Usuario_ProblemasConexionPaso1.png)<br>
  Este boton nos abrira una ventana que nos pedira ingresar el codigo de un equipo de la red, una vez seleccionado
  deberemos hacer click en el boton que dice "OK.
  ![Manual_Usuario_ProblemasConexionPaso2.png](Manual%20de%20usuario%2FManual_Usuario_ProblemasConexionPaso2.png)<br>
  A continuacion nos mostrara todos los equipos a los que el equipo que seleccionamos puede conectarse.
  ![Manual_Usuario_ProblemasConexionPaso3.png](Manual%20de%20usuario%2FManual_Usuario_ProblemasConexionPaso3.png)<br>

### 7. Persistencia de Datos

El sistema permite la persistencia de datos mediante archivos de texto o una base de datos SQLite. Configura el tipo de
DAO en la clase `Factory` como se mencionó anteriormente.

## Enlaces Útiles

- [Repositorio del Proyecto](https://github.com/BonanseaMariano/ISFPP2024POO)
- [Documentación Javadoc](https://bonanseamariano.github.io/ISFPP2024POO/)




