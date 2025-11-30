# 🏥 Sistema de Gestión Hospitalaria – Entregable Final (Entregable 3) 💻

> ⚠️ Este README corresponde al **Entregable 3 – Versión Final** del Sistema de Gestión Hospitalaria.  
> Contiene todas las funcionalidades, procedimientos, triggers, vistas y ejemplos de ejecución.  
> Se recomienda usar este README como guía principal para ejecutar y probar el proyecto.

**Integrantes:**  
- MillonesVasquez, Daniel  
- Coronado Quispe, Piero  

**Fecha de entrega:** Sábado – Semana 15  
**Repositorio GitHub:** [https://github.com/daniel12R3/Sistema-de-Gesti-n-Hospitalaria.git](https://github.com/daniel12R3/Sistema-de-Gesti-n-Hospitalaria.git)  

---

## 🎯 Objetivo del Proyecto

Desarrollar un **Sistema de Gestión Hospitalaria** que permita administrar de manera eficiente la información clínica de pacientes, médicos, áreas, citas y pagos, automatizando los procesos administrativos de una clínica y facilitando la visualización y control de información mediante **Java y Oracle Database**.

---

## 📄 Descripción

El sistema permite:

- Gestionar pacientes, médicos, especialidades, citas y pagos.  
- Conectar Java con Oracle 11g XE mediante JDBC.  
- Ejecutar consultas y recorrer registros de manera eficiente.  
- Implementar validaciones y control de integridad mediante triggers y restricciones.  
- Automatizar procesos y reglas de negocio mediante procedimientos almacenados.  
- Visualizar información de manera rápida mediante vistas.  

---

## 📂 Estructura del Repositorio


├─ SQLtablas/                       # Scripts de creación de la base de datos Oracle
│  └─ CreacionDeTablas.sql
├─ diagrama-DER/                     # Diagrama entidad-relación del sistema
│  └─ DER.pdf
├─ sistema-gestion-hospitalaria/     # Proyecto Java completo
│  ├─ src/
│  │  ├─ app/                        # Clases principales y menú de la app
│  │  ├─ config/                     # Configuración de conexión a Oracle
│  │  ├─ model/                      # Clases de modelos (Paciente, Médico, etc.)
│  │  ├─ dao/                        # Clases de acceso a la base de datos
│  │  └─ service/                    # Lógica de negocio y validaciones
│  ├─ lib/                           # Librerías externas necesarias (JARs)
│  ├─ .gitignore                     # Archivos y carpetas ignoradas por Git
│  └─ BD_SQL_oracle.iml              # Archivo de configuración IntelliJ (opcional)
├─ Entregable2.pdf                   # Documento del proyecto del Entregable 2
├─ README - Entregable 2.md          # README correspondiente al Entregable 2
├─ Entregable3.pdf                   # Documento del proyecto final (Entregable 3)
└─ README - Entregable 3.md          # README correspondiente al Entregable 3 (este archivo)



⚠️ No se incluyen `out/` ni `.idea/` ya que son específicos del entorno local.

---

## 🔧 Requisitos

- Java JDK 8 o superior  
- Oracle Database 11g XE  
- Driver JDBC: ojdbc8.jar  
- IDE recomendado: IntelliJ IDEA  

---

## ⚙️ Configuración y Ejecución

1. Clonar el repositorio:  
git clone https://github.com/daniel12R3/Sistema-de-Gesti-n-Hospitalaria.git

2. Abrir el proyecto en IntelliJ IDEA.
3. Configurar la conexión a la base de datos en: config/ConexionOracle.java con tus credenciales.
4. Ejecutar la aplicación desde: app/Main.java

## 🏗️ Arquitectura del Sistema (MVC Simplificado)

**Modelo (`model/`)**  
- Entidades: `Paciente`, `Medico`, `Especialidad`, `Cita`, `PagoPaciente`, `PagoMedico`.  
- Métodos `getters/setters`.  

**DAO (`dao/`)**  
- Inserción, búsqueda, listado y eliminación de registros.  
- Uso de `PreparedStatement` para seguridad y manejo de tipos `DATE/TIMESTAMP`.  

**Service (`service/`)**  
- Validaciones y reglas de negocio:  
  - Campos obligatorios  
  - Existencia de pacientes y médicos  
  - Control de solapamiento de horarios de citas  
  - Manejo de pagos y acumulados  

**Main (`app/`)**  
- Interfaz de usuario por consola.  
- Menús para registrar, listar, actualizar y eliminar médicos/pacientes, gestionar citas/pagos y generar reportes.  

---

## 📝 Funcionalidades Principales

### 👤 Gestión de Pacientes  
### 🩺 Gestión de Médicos  
### 🏷️ Gestión de Especialidades  
### 📅 Gestión de Citas  
### 💰 Registro de Pagos de Pacientes y Médicos  
### 📊 Consultas y reportes básicos  

---

## 2. Procedimientos Almacenados
```sql
2.1 Gestión de Citas
CREATE OR REPLACE PROCEDURE sp_insertar_cita(...);
CREATE OR REPLACE PROCEDURE sp_actualizar_estado(...);
CREATE OR REPLACE PROCEDURE sp_reprogramar_cita(...);
CREATE OR REPLACE PROCEDURE sp_eliminar_cita(...);

2.2 Gestión de Pacientes
CREATE OR REPLACE PROCEDURE sp_insertar_paciente(...);
CREATE OR REPLACE PROCEDURE sp_actualizar_paciente(...);
CREATE OR REPLACE PROCEDURE sp_eliminar_paciente(...);
CREATE OR REPLACE PROCEDURE sp_buscar_paciente(...);

2.3 Gestión de Médicos
CREATE OR REPLACE PROCEDURE sp_insertar_medico(...);
CREATE OR REPLACE PROCEDURE sp_actualizar_medico(...);
CREATE OR REPLACE PROCEDURE sp_eliminar_medico(...);

2.4 Gestión de Especialidades
CREATE OR REPLACE PROCEDURE sp_insertar_especialidad(...);
CREATE OR REPLACE PROCEDURE sp_actualizar_especialidad(...);
CREATE OR REPLACE PROCEDURE sp_eliminar_especialidad(...);

2.5 Gestión de Pagos
CREATE OR REPLACE PROCEDURE SP_INSERTAR_PAGO_PACIENTE(...);
CREATE OR REPLACE PROCEDURE sp_insertar_pagomedico(...);

3. Triggers
-- Estado por defecto de citas
CREATE OR REPLACE TRIGGER trg_default_estado
BEFORE INSERT ON CITA
FOR EACH ROW
BEGIN
    IF :NEW.estado IS NULL THEN
        :NEW.estado := 'PENDIENTE';
    END IF;
END;
/

-- Validación CMP único
CREATE OR REPLACE TRIGGER trg_cmp_unico
BEFORE INSERT OR UPDATE ON medico
FOR EACH ROW
DECLARE
    v_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count
    FROM medico
    WHERE cmp = :NEW.cmp
      AND idmedico != NVL(:OLD.idmedico, -1);
    IF v_count > 0 THEN
        RAISE_APPLICATION_ERROR(-20010, 'CMP duplicado');
    END IF;
END;
/

-- Log de cambios de estado de citas
CREATE OR REPLACE TRIGGER trg_log_update_cita
AFTER UPDATE OF estado ON CITA
FOR EACH ROW
BEGIN
    INSERT INTO LOG_CITA(idcita, estado_anterior, estado_nuevo, fecha_mod, usuario)
    VALUES(:old.idcita, :old.estado, :new.estado, SYSDATE, USER);
END;
/

4. Vistas
CREATE OR REPLACE VIEW vw_citas_detalle AS
SELECT c.idcita,
       TO_CHAR(c.fecha, 'YYYY-MM-DD') AS fecha_txt,
       c.horainicio,
       c.horafin,
       c.estado,
       p.nombre || ' ' || p.apellido AS paciente,
       m.nombre || ' ' || m.apellido AS medico
FROM CITA c
JOIN PACIENTE p ON c.idpaciente = p.idpaciente
JOIN MEDICO m ON c.idmedico = m.idmedico
ORDER BY c.fecha DESC, c.horainicio;

CREATE OR REPLACE VIEW vw_resumen_pagos_paciente AS
SELECT p.idpaciente,
       p.nombre || ' ' || p.apellido AS paciente,
       NVL(SUM(pp.monto),0) AS total
FROM PACIENTE p
LEFT JOIN CITA c ON p.idpaciente = c.idpaciente
LEFT JOIN PAGOPACIENTE pp ON c.idcita = pp.idcita
GROUP BY p.idpaciente, p.nombre, p.apellido;
```

5. Funcionalidades Avanzadas en la Aplicación (Java)
5.1 Invocación de Procedimientos
CallableStatement cstmt = conn.prepareCall("{call sp_insertar_cita(?, ?, ?, ?, ?, ?)}");
cstmt.setDate(1, java.sql.Date.valueOf(fecha));
cstmt.setString(2, horaInicio);
cstmt.setString(3, horaFin);
cstmt.setString(4, estado);
cstmt.setInt(5, idPaciente);
cstmt.setInt(6, idMedico);
cstmt.execute();

5.2 Mostrar resultados de Vistas
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM vw_citas_detalle");
while(rs.next()){
    System.out.println(rs.getInt("idcita") + " - " + rs.getString("paciente") + " - " + rs.getString("medico"));
}

5.3 Validaciones en el Servicio

  - Evitar duplicidad de DNI o CMP
  - Validar pagos mayores a 0
  - Controlar pagos duplicados por cita y fecha
  - Validar fecha y hora de citas

6. Documentación y GitHub

Scripts SQL, proyecto Java y documentación con ejemplos:
https://github.com/daniel12R3/Sistema-de-Gesti-n-Hospitalaria.git

Incluye ejemplos de ejecución, capturas de resultados y pruebas de funcionalidad.

7. Conclusiones
  - Automatización completa de la gestión hospitalaria mediante procedimientos, triggers y vistas.
  - Integración con Java para invocación de procedimientos y visualización de vistas.
  - Garantía de integridad de datos mediante validaciones y restricciones.
  - Proyecto listo para ejecución, despliegue y futuras mejoras.
