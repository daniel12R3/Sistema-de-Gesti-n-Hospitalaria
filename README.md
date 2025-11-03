# Proyecto: Sistema de Gestión Hospitalaria – Conexión Java + Oracle Database

## Descripción
Este proyecto implementa un **Sistema de Gestión Hospitalaria** que permite administrar información clínica básica de pacientes, médicos, citas y consultas médicas mediante una base de datos Oracle 11g XE.  
Se desarrolló una aplicación Java que se conecta a Oracle utilizando **JDBC**, permitiendo ejecutar consultas y recorrer los registros de manera eficiente.

## Objetivos

- Administrar información clínica de un hospital, incluyendo pacientes, médicos, áreas, citas y historiales clínicos.  
- Implementar la conexión entre Java y Oracle Database utilizando JDBC.  
- Validar la integridad de la base de datos y la correcta interacción con la aplicación Java.  

## Estructura del Proyecto

Proyecto/
│
├── SQL/
│ └── CreaciónTablas.sql # Script de creación de tablas en Oracle
│
├── diagrama-MER/
│ └── MER.pdf # Diagrama Entidad-Relación
│
├── src/
│ └── ConexionOracle.java # Clase Java que establece la conexión con Oracle
│
├── ojdbc8.jar # Driver JDBC para Oracle
│
├── Entregable 1.pdf # Documento que resume el laboratorio
│
└── README.md # Este archivo

## Requisitos

- **Java JDK 8 o superior**  
- **Oracle Database 11g XE**  
- **Driver JDBC `ojdbc8.jar`**  
- **IDE recomendado:** IntelliJ IDEA  


## Configuración y Ejecución en IntelliJ

1. **Importar el proyecto**
   - Abrir IntelliJ IDEA y seleccionar `File > Open` para abrir la carpeta raíz del proyecto.

2. **Añadir el driver JDBC**
   - Ir a `File > Project Structure > Libraries` y agregar `ojdbc8.jar` al proyecto.

3. **Configurar la conexión en `ConexionOracle.java`**

```java
String url = "jdbc:oracle:thin:@localhost:1521:XE";
String user = "system";
String password = "Oracle123";
Compilar y ejecutar

Ejecutar la clase ConexionOracle.java desde IntelliJ.

La aplicación establecerá la conexión y mostrará la información de las tablas en la consola.

#Base de Datos
Entidades Principales
PACIENTE: Información de pacientes (nombre, apellido, DNI, teléfono, dirección).
MÉDICO: Información de médicos (nombre, apellido, especialidad, teléfono, área asignada).
ÁREA: Representa las diferentes áreas del hospital.
CITA: Registro de citas médicas de pacientes con médicos.
HISTORIAL_CLÍNICO: Historial de diagnósticos y tratamientos de pacientes.

Relaciones
Un Área puede tener muchos Médicos (1:N).
Un Médico puede atender muchas Citas (1:N).
Un Paciente puede tener muchas Citas (1:N).
Un Paciente puede tener muchos registros en Historial Clínico (1:N).

Resultados Obtenidos:
Instalación y configuración exitosa de Oracle 11g XE en Windows 10.
Creación de tablas y definición de relaciones en SQL.
Implementación de conexión Java-Oracle mediante JDBC.
Ejecución de consultas y recorrido de registros con ResultSet.
Validación de la integridad de los datos y de la interacción aplicación-base de datos.

Estudiantes:
Daniel Alejandro Millones
Piero Coronado

Laboratorio de Conexión Java – Oracle Database
