=
SISTEMA DE INSPECTORÍA EQUIPO 3 PROTOCOLOS – README OFICIAL
=

Este documento describe el funcionamiento del Sistema de Inspectoría:
cómo iniciar sesión, cómo navegar entre los módulos y cómo utilizar cada
función. La idea es que cualquier persona incluido el profesor pueda
probar el sistema en minutos.

=
1. TECNOLOGÍAS UTILIZADAS
=

✔ Java JSP + Servlets  
✔ Apache Tomcat  
✔ MVC  
✔ Bootstrap 5  
✔ MySQL Workbench  

=
2. OBJETIVO DEL SISTEMA
=

El sistema permite gestionar protocolos de Inspectoría:

• Registrar protocolos nuevos  
• Actualizar protocolos existentes  
• Agregar o quitar alumnos involucrados  
• Cambiar el estado (Nuevo, Investigación, Cerrado, Anulado)  
• Ver el historial de actualizaciones  
• Gestionar tipos de protocolos  
• Control de sesión seguro  

=
3. LOGIN – CÓMO INGRESAR AL SISTEMA
=

URL inicial:
------------------------------------
http://localhost:8080/MavenProject1/login.jsp
------------------------------------

Credenciales:
------------------------------------
Usuario: Leo  
Contraseña: 1234  
------------------------------------

Al ingresar:
✔ Se crea la sesión "usuarioLogeado".
✔ Se guarda la hora de inicio.
✔ Se muestra arriba a la derecha: Usuario + Hora Login.

=
4. CÓMO NAVEGAR POR EL SISTEMA (FLUJO CONCRETO)
=

A continuación, se explica el flujo real de navegación tal como lo verá
al probar el sistema.

=
5. ALUMNOS DE LA BASE DE DATOS
=

La siguiente tabla muestra los alumnos existentes en la BD.
Esto es importante para saber qué nombres usar
al registrar protocolos o al actualizar uno existente.
--------------------------------------------------------------
 id_alumno |     rut        |   nombre   |   apellido   | curso
--------------------------------------------------------------
    1      | 20.111.111-1   | Camila     | Rojas        | 7°A
    2      | 20.222.222-2   | Matías     | González     | 7°A
    3      | 20.333.333-3   | Valentina  | Lagos        | 7°B
    4      | 20.444.444-4   | Diego      | Mora         | 8°A
    5      | 20.555.555-5   | Antonia    | Pérez        | 8°A
    6      | 20.666.666-6   | Benjamín   | Muñoz        | 8°B
    7      | 20.777.777-7   | Isidora    | Castro       | 8°B
    8      | 20.888.888-8   | Tomás      | Reyes        | 1°M
    9      | 20.999.999-9   | Sofía      | Cifuentes    | 2°M
   10      | 21.000.000-0   | Ignacio    | Soto         | 2°M
--------------------------------------------------------------

----------------------------------------------------------
(1) LOGIN
----------------------------------------------------------
Ingresa las credenciales → clic en “Iniciar sesión”.

Si son correctas → va automáticamente al **INDEX**.

----------------------------------------------------------
(2) INDEX – Menú principal
----------------------------------------------------------
Aquí verá dos botones:

• **CRUD Inspectoría** → Módulo completo de protocolos  
• **Cerrar sesión**

Para comenzar a trabajar, debe entrar a **CRUD Inspectoría**.

----------------------------------------------------------
(3) MENÚ DE PROTOCOLOS
----------------------------------------------------------
Desde este menú puede acceder a todas las funciones:

• Registrar nuevo protocolo  
• Listar/Buscar protocolos  
• Actualizar un protocolo  
• Cerrar protocolo  
• Anular un protocolo  
• Ver tipos de protocolo  
• Volver al menú anterior  

----------------------------------------------------------
(4) REGISTRAR NUEVO PROTOCOLO
----------------------------------------------------------
Aquí podrá:

• Elegir el tipo de protocolo (lista desplegable)  
• Escribir la descripción de lo ocurrido  
• Agregar alumnos involucrados (buscador por nombre o RUT)  
• Guardar el registro  

El sistema crea el protocolo en estado **NUEVO**.

----------------------------------------------------------
(5) LISTAR / BUSCAR PROTOCOLOS
----------------------------------------------------------
Permite filtrar por:

• Alumno (buscándolo por **nombre**, **apellido** o **RUT**)  
• Rango de fechas  
• Tipo de protocolo  
• Estado  

Desde esta tabla puede:

• Ver detalle  
• Registrar actualización  
• Cambiar estado  
• Anular  
• Cerrar protocolo

----------------------------------------------------------
(6) VER DETALLE DE PROTOCOLO
----------------------------------------------------------
Se ve toda la información:

• Datos del protocolo  
• Lista de alumnos involucrados  
• Historial de observaciones  
• Estado actual  

Botones disponibles:

• Registrar nueva actualización  
• Cambiar estado  
• Anular protocolo  

----------------------------------------------------------
(7) REGISTRAR ACTUALIZACIÓN
----------------------------------------------------------
Aquí puede:

• Escribir una nueva observación  
• Agregar alumnos adicionales  
• Guardar la actualización → aparece en el historial  

----------------------------------------------------------
(8) TIPOS DE PROTOCOLO
----------------------------------------------------------
Tabla de tipos de protocolo existentes:

• ID  
• Nombre  
• Descripción  

----------------------------------------------------------
(9) CERRAR SESIÓN
----------------------------------------------------------

✔ invalida la sesión  
✔ limpia la caché  
✔ vuelve al login.jsp  


==========================================================
5. ESTRUCTURA DEL PROYECTO
==========================================================

/src
   /controlador
       - ControladorLogin.java
       - ControladorProtocolos.java

   /modelo
       - Usuario.java
       - Protocolo.java
       - ObservacionProtocolo.java
       - TipoProtocolo.java
       - Alumno.java
       - DAO + conexiones

/JSP (vistas):
   - login.jsp
   - index.jsp
   - menuProtocolos.jsp
   - registrarNuevo.jsp
   - listarProtocolos.jsp
   - verDetalleProtocolo.jsp
   - registrarActualizacion.jsp
   - anularProtocolo.jsp
   - tiposProtocolos.jsp
   - buscarProtocoloActualizar.jsp
   - irAlCRUDinspectoria.jsp

==========================================================
6. NOTAS FINALES 
==========================================================

• Todo el sistema está operativo y probado.  
• El flujo está diseñado para ser simple y directo.  
• La tabla de alumnos permite probar rápidamente el módulo.  
• Validación de sesión.  

==========================================================
FIN DEL DOCUMENTO

==========================================================


