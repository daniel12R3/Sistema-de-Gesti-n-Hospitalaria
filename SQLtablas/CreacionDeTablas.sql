-- ========================================
-- TABLA PACIENTE
-- ========================================
CREATE TABLE Paciente (
    idPaciente NUMBER PRIMARY KEY,
    DNI CHAR(8) UNIQUE,
    nombre VARCHAR2(100),
    apellido VARCHAR2(100),
    telefono VARCHAR2(15),
    direccion VARCHAR2(200),
    tipoPago VARCHAR2(15)
);

-- Secuencia para idPaciente
CREATE SEQUENCE seq_paciente START WITH 1 INCREMENT BY 1;

-- Trigger para autoincremento
CREATE OR REPLACE TRIGGER trg_paciente
BEFORE INSERT ON Paciente
FOR EACH ROW
BEGIN
  IF :NEW.idPaciente IS NULL THEN
    SELECT seq_paciente.NEXTVAL INTO :NEW.idPaciente FROM dual;
  END IF;
END;
/

-- ========================================
-- TABLA ESPECIALIDAD
-- ========================================
CREATE TABLE Especialidad (
    idEspecialidad NUMBER PRIMARY KEY,
    nombreEspecialidad VARCHAR2(100)
);

CREATE SEQUENCE seq_especialidad START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trg_especialidad
BEFORE INSERT ON Especialidad
FOR EACH ROW
BEGIN
  IF :NEW.idEspecialidad IS NULL THEN
    SELECT seq_especialidad.NEXTVAL INTO :NEW.idEspecialidad FROM dual;
  END IF;
END;
/

-- ========================================
-- TABLA MEDICO
-- ========================================
CREATE TABLE Medico (
    idMedico NUMBER PRIMARY KEY,
    nombre VARCHAR2(100),
    apellido VARCHAR2(100),
    CMP VARCHAR2(20) UNIQUE,
    idEspecialidad NUMBER,
    CONSTRAINT fk_medico_especialidad FOREIGN KEY (idEspecialidad) REFERENCES Especialidad(idEspecialidad)
);

CREATE SEQUENCE seq_medico START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trg_medico
BEFORE INSERT ON Medico
FOR EACH ROW
BEGIN
  IF :NEW.idMedico IS NULL THEN
    SELECT seq_medico.NEXTVAL INTO :NEW.idMedico FROM dual;
  END IF;
END;
/

-- ========================================
-- TABLA CITA
-- ========================================
CREATE TABLE Cita (
    idCita NUMBER PRIMARY KEY,
    fecha DATE,
    horaInicio TIMESTAMP,
    horaFin TIMESTAMP,
    estado VARCHAR2(20),
    idPaciente NUMBER,
    idMedico NUMBER,
    CONSTRAINT fk_cita_paciente FOREIGN KEY (idPaciente) REFERENCES Paciente(idPaciente),
    CONSTRAINT fk_cita_medico FOREIGN KEY (idMedico) REFERENCES Medico(idMedico)
);

CREATE SEQUENCE seq_cita START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trg_cita
BEFORE INSERT ON Cita
FOR EACH ROW
BEGIN
  IF :NEW.idCita IS NULL THEN
    SELECT seq_cita.NEXTVAL INTO :NEW.idCita FROM dual;
  END IF;
END;
/

-- ========================================
-- TABLA PAGOPACIENTE
-- ========================================
CREATE TABLE PagoPaciente (
    idPagoPaciente NUMBER PRIMARY KEY,
    monto NUMBER(6,2),
    fechaPago DATE,
    tipoPago VARCHAR2(20),
    idCita NUMBER UNIQUE,
    CONSTRAINT fk_pagopaciente_cita FOREIGN KEY (idCita) REFERENCES Cita(idCita)
);

CREATE SEQUENCE seq_pagopaciente START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trg_pagopaciente
BEFORE INSERT ON PagoPaciente
FOR EACH ROW
BEGIN
  IF :NEW.idPagoPaciente IS NULL THEN
    SELECT seq_pagopaciente.NEXTVAL INTO :NEW.idPagoPaciente FROM dual;
  END IF;
END;
/

-- ========================================
-- TABLA PAGOMEDICO
-- ========================================
CREATE TABLE PagoMedico (
    idPagoMedico NUMBER PRIMARY KEY,
    montoTotal NUMBER(6,2),
    periodoPago VARCHAR2(20),
    fechaGeneracion DATE,
    idMedico NUMBER,
    CONSTRAINT fk_pagomedico_medico FOREIGN KEY (idMedico) REFERENCES Medico(idMedico)
);

CREATE SEQUENCE seq_pagomedico START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trg_pagomedico
BEFORE INSERT ON PagoMedico
FOR EACH ROW
BEGIN
  IF :NEW.idPagoMedico IS NULL THEN
    SELECT seq_pagomedico.NEXTVAL INTO :NEW.idPagoMedico FROM dual;
  END IF;
END;
/
