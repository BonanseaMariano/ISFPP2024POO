CREATE TABLE ubicaciones
(
    codigo      VARCHAR(10) PRIMARY KEY,
    descripcion VARCHAR(30)
);

CREATE TABLE tipos_cables
(
    codigo      VARCHAR(10) PRIMARY KEY,
    descripcion VARCHAR(30),
    velocidad   INTEGER
);

CREATE TABLE tipos_puertos
(
    codigo      VARCHAR(10) PRIMARY KEY,
    descripcion VARCHAR(30),
    velocidad   INTEGER
);

CREATE TABLE tipos_equipos
(
    codigo      VARCHAR(10) PRIMARY KEY,
    descripcion VARCHAR(30)
);

CREATE TABLE equipos
(
    codigo      VARCHAR(10) PRIMARY KEY,
    marca       VARCHAR(30),
    modelo      VARCHAR(30),
    tipo_equipo VARCHAR(10),
    ubicacion   VARCHAR(10),
    FOREIGN KEY (tipo_equipo) REFERENCES tipos_equipos (codigo),
    FOREIGN KEY (ubicacion) REFERENCES ubicaciones (codigo)
);

CREATE TABLE direcciones_ip
(
    ip     VARCHAR(11) PRIMARY KEY,
    equipo VARCHAR(10),
    FOREIGN KEY (equipo) REFERENCES equipos (codigo)
);

CREATE TABLE puertos
(
    equipo      VARCHAR(10),
    tipo_puerto VARCHAR(10),
    cantidad    INTEGER,
    FOREIGN KEY (equipo) REFERENCES equipos (codigo),
    FOREIGN KEY (tipo_puerto) REFERENCES tipos_puertos (codigo),
    PRIMARY KEY (equipo, tipo_puerto)
);

CREATE TABLE conexiones
(
    equipo1      VARCHAR(10),
    equipo2      VARCHAR(10),
    tipo_cable   VARCHAR(10),
    tipo_puerto1 VARCHAR(10),
    tipo_puerto2 VARCHAR(10),
    FOREIGN KEY (equipo1) REFERENCES equipos (codigo),
    FOREIGN KEY (equipo2) REFERENCES equipos (codigo),
    FOREIGN KEY (tipo_cable) REFERENCES tipos_cables (codigo),
    FOREIGN KEY (tipo_puerto1) REFERENCES tipos_puertos (codigo),
    FOREIGN KEY (tipo_puerto2) REFERENCES tipos_puertos (codigo),
    PRIMARY KEY (equipo1, equipo2)
);
