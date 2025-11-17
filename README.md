# 🏥 Sistema de Gestión Hospitalaria – Java + Oracle Database 💻

## 🎯 Objetivo del Proyecto
El objetivo de este proyecto es desarrollar un **Sistema de Gestión Hospitalaria** que permita administrar de manera eficiente la información clínica de **pacientes, médicos, áreas, citas y pagos**, automatizando los procesos administrativos de una clínica y facilitando la visualización y control de información mediante **Java** y **Oracle Database**.

---

## 📄 Descripción
Este proyecto implementa un sistema que:

- Gestiona pacientes, médicos, especialidades, citas y pagos.  
- Conecta Java con **Oracle 11g XE** mediante **JDBC**.  
- Permite ejecutar consultas y recorrer registros de manera eficiente.  
- Implementa validaciones de datos y control de integridad en la base de datos.

---

## 🎯 Objetivos Específicos
- Administrar información clínica de un hospital (pacientes, médicos, áreas, citas y pagos).  
- Implementar la conexión entre Java y Oracle Database utilizando JDBC.  
- Validar la integridad de la base de datos y la correcta interacción con la aplicación Java.  

---

## 📂 Estructura del Repositorio

├─ SQLtablas/ # Scripts de creación de la base de datos Oracle
│ └─ CreacionDeTablas.sql
├─ diagrama-DER/ # Diagrama entidad-relación del sistema
│ └─ DER.pdf
├─ sistema-gestion-hospitalaria/ # Proyecto Java completo
│ ├─ src/ # Código fuente
│ │ ├─ app/ # Clases principales y menú de la app
│ │ ├─ config/ # Configuración de conexión a Oracle
│ │ ├─ model/ # Clases de modelos (Paciente, Médico, etc.)
│ │ ├─ dao/ # Clases de acceso a la base de datos
│ │ └─ service/ # Lógica de negocio y validaciones
│ ├─ lib/ # Librerías externas necesarias (JARs)
│ ├─ .gitignore # Archivos y carpetas ignoradas por Git
│ └─ BD_SQL_oracle.iml # Archivo de configuración IntelliJ (opcional)
├─ Entregable 2.pdf # Documento del proyecto con explicación y capturas
└─ README.md # Este archivo


> ⚠️ **Nota:** No se incluyen `out/` ni `.idea/` ya que son específicos del entorno local.

---

## 🔧 Requisitos

- **Java JDK 8 o superior**  
- **Oracle Database 11g XE**  
- Driver JDBC: `ojdbc8.jar`  
- IDE recomendado: **IntelliJ IDEA**  

---

## ⚙️ Configuración y Ejecución

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tuusuario/daniel12R3.git
   
2. Abrir el proyecto en IntelliJ IDEA. 
3. Configurar la conexión a la base de datos en:
                      config/ConexionOracle.java
   con tus credenciales.
   
5. Ejecutar la aplicación desde:
                   app/Main.java

🏗️ Arquitectura del Sistema (MVC Simplificado)
1. Modelo (model/)

- Representa las entidades principales: Paciente, Medico, Especialidad, Cita, PagoPaciente, PagoMedico.
- Cada clase contiene sus atributos y métodos getters/setters.

2. DAO (dao/)

- Acceso a la base de datos Oracle.
- Funcionalidades:
     - Insertar registros (insertar)
     - Buscar por ID (buscarPorId)
     - Listar registros (listar)
     - Eliminar registros (eliminar)
- Uso de PreparedStatement para seguridad y manejo de tipos DATE/TIMESTAMP.

3. Service (service/)

- Implementa validaciones y reglas de negocio:
    - Campos obligatorios
    - Verificación de existencia de pacientes y médicos
    - Control de solapamiento de horarios de citas
    - Manejo de pagos y acumulados por médico o paciente

4. Main (app/)

- Interfaz de usuario mediante consola.
- Menús para:
     - Registrar, listar, actualizar y eliminar médicos y pacientes
     - Gestionar citas y pagos
     - Consultas detalladas (ej.: total ganado por médico, historial de pagos)

📝 Funcionalidades Principales

   - 👤 Gestión de Pacientes
   - 🩺 Gestión de Médicos
   - 🏷️ Gestión de Especialidades
   - 📅 Gestión de Citas
   - 💰 Registro de Pagos de Pacientes y Médicos
   - 📊 Consultas y reportes básicos

📚 Base de Datos

   - Script SQL: SQLtablas/CreacionDeTablas.sql
   - Diagrama DER: diagrama-DER/DER.pdf

El diagrama DER muestra cómo las entidades se relacionan en la base de datos y facilita la comprensión del diseño.

📄 Entregables

   - Entregable 2.pdf → Documento del proyecto con explicación de la estructura, capturas, pruebas y resultados.


Estudiantes:
- Daniel Alejandro Millones
- Piero Coronado

Laboratorio de Conexión Java – Oracle Database
