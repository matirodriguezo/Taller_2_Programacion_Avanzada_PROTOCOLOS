DROP DATABASE IF EXISTS prgavz;
CREATE DATABASE prgavz;
USE prgavz;

#login
CREATE TABLE user(
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50),
    pass VARCHAR(50),
    rol VARCHAR(50)
);

INSERT INTO user(usuario,pass,rol) VALUES ('Leo','1234','Profesor');

#ALUMNOS
CREATE TABLE alumno (
    id_alumno INT AUTO_INCREMENT PRIMARY KEY,
    rut       VARCHAR(20) NOT NULL UNIQUE,
    nombre    VARCHAR(80) NOT NULL,
    apellido  VARCHAR(80) NOT NULL,
    curso     VARCHAR(20)
);

INSERT INTO alumno (rut, nombre, apellido, curso) VALUES
('20.111.111-1','Camila','Rojas','7°A'),
('20.222.222-2','Matías','González','7°A'),
('20.333.333-3','Valentina','Lagos','7°B'),
('20.444.444-4','Diego','Mora','8°A'),
('20.555.555-5','Antonia','Pérez','8°A'),
('20.666.666-6','Benjamín','Muñoz','8°B'),
('20.777.777-7','Isidora','Castro','1°M'),
('20.888.888-8','Tomás','Reyes','1°M'),
('20.999.999-9','Sofía','Cifuentes','2°M'),
('21.000.000-0','Ignacio','Soto','2°M');

#TIPOS DE PROTOCOLO
CREATE TABLE IF NOT EXISTS tipo_protocolo (
    id_tipo_protocolo INT AUTO_INCREMENT PRIMARY KEY,
    nombre_protocolo  VARCHAR(600) NOT NULL,
    descripcion       TEXT
);

INSERT INTO tipo_protocolo (nombre_protocolo, descripcion) VALUES
('Protocolo de actuación frente a la detección de situaciones de vulneración de derechos de los estudiantes',       'Hechos de bullying entre estudiantes'),
('Protocolo de actuación frente a situaciones de hecho puntual agresión entre pares, acoso escolar o bullying y ciberbullying',    'Golpes, empujones u otras agresiones'),
('Protocolo de actuación frente a situación de discriminación','Discriminacion'),
('Protocolo de actuación frente a violencia de un estudiante a un adulto','adulto'),
('Protocolo de actuación frente a hecho puntual de agresión de un adulto miembro de la comunidad educativa (funcionario o apoderado) a un niño, niña o adolescente (NNA)','miembro comunidad agresion'),
('Protocolo general en caso de desregulación de un estudiante(DEC)','DEC'),
('Protocolo de actuación frente a hecho puntual de agresión entre miembros adultos de la comunidad escolar','hecho puntual'),
('Protocolo de accidentes escolares.','accidentes escolares'),
('Protocolo de actuación frente a agresiones sexuales y hechos de connotación sexual que atenten contra la integridad de los estudiantes.','connotacion sexual'),
('Protocolo frente a situaciones de connotación o agresión sexual entre estudiantes','csx entre estudiantes'),
('Protocolos de detección y actuación frente a situaciones de autolesiones, ideación o eventual riesgo suicida en el contexto escolar.','deteccion'),
('Protocolo de actuación para abordar situaciones relacionadas a drogas y alcohol en el establecimiento.','alcohol'),
('Protocolo de retención y apoyo a estudiantes , padres, madres y embarazadas.','retencion y apoyo'),
('Protocolo de actuación ante manifestaciones e incidentes al interior del colegio y el perímetro cercano.','Manifestaciones'),
('Protocolo de actuación frente a fallecimiento de un miembro de la comunidad educativa.','fallecimiento'),
('Protocolo sobre utilización de grupo de whatsapp por apoderados.','grupo whatsapp'),
('Protocolo frente al uso de celulares y otros aparatos tecnológicos.','Uso de celulares');

#PROTOCOLO
CREATE TABLE IF NOT EXISTS protocolo(
    id_protocolo       INT AUTO_INCREMENT PRIMARY KEY,
    fecha_hora         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado             ENUM('NUEVO','EN_INVESTIGACION','CERRADO','ANULADO') NOT NULL DEFAULT 'NUEVO',
    id_tipo            INT NOT NULL,
    id_usuario_creador INT NOT NULL,
    INDEX idx_protocolo_tipo(id_tipo),
    INDEX idx_protocolo_estado(estado)
);

#PROTOCOLO_ALUMNO
CREATE TABLE IF NOT EXISTS protocolo_alumno(
    id_protocolo INT NOT NULL,
    id_alumno    INT NOT NULL,
    PRIMARY KEY (id_protocolo, id_alumno)
);

#HISTORIAL DE OBSERVACIONES
CREATE TABLE IF NOT EXISTS observacion_protocolo (
    id_observacion INT AUTO_INCREMENT PRIMARY KEY,
    id_protocolo   INT NOT NULL,
    id_usuario     INT NOT NULL,
    fecha_hora     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    texto          TEXT NOT NULL,
    tipo           ENUM('INICIAL','SEGUIMIENTO','ANULACION') NOT NULL DEFAULT 'SEGUIMIENTO',
    INDEX idx_obs_protocolo(id_protocolo)
);

SELECT * FROM tipo_protocolo;
SELECT * FROM alumno;
