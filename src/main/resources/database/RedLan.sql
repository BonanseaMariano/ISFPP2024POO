create table tipos_cables
(
    codigo      VARCHAR(10)
        primary key,
    descripcion VARCHAR(30),
    velocidad   INTEGER
);

create table tipos_equipos
(
    codigo      VARCHAR(10)
        primary key,
    descripcion VARCHAR(30)
);

create table tipos_puertos
(
    codigo      VARCHAR(10)
        primary key,
    descripcion VARCHAR(30),
    velocidad   INTEGER
);

create table ubicaciones
(
    codigo      VARCHAR(10)
        primary key,
    descripcion VARCHAR(30)
);

create table equipos
(
    codigo      VARCHAR(10)
        primary key,
    marca       VARCHAR(30),
    modelo      VARCHAR(30),
    tipo_equipo VARCHAR(10)
        references tipos_equipos,
    ubicacion   VARCHAR(10)
        references ubicaciones,
    estado      integer default 0
);

create table conexiones
(
    equipo1      VARCHAR(10)
        references equipos,
    equipo2      VARCHAR(10)
        references equipos,
    tipo_cable   VARCHAR(10)
        references tipos_cables,
    tipo_puerto1 VARCHAR(10)
        references tipos_puertos,
    tipo_puerto2 VARCHAR(10)
        references tipos_puertos,
    primary key (equipo1, equipo2)
);

create table direcciones_ip
(
    ip     VARCHAR(11)
        primary key,
    equipo VARCHAR(10)
        references equipos
);

create table puertos
(
    equipo      VARCHAR(10)
        references equipos,
    tipo_puerto VARCHAR(10)
        references tipos_puertos,
    cantidad    INTEGER,
    primary key (equipo, tipo_puerto)
);

