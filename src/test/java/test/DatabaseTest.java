package test;

import data.implementations.file.*;
import data.implementations.sqlite.*;
import data.interfaces.*;
import models.*;

public class DatabaseTest {
    //Para correrlo hay que hacerlo en partes (primero tipos y ubicaciones, desp equipos y finalmente conexiones)
    //Pasa los datos de los archivos a la base de datos sqlite
    public static void main(String[] args) {

        DAOTipoEquipo tipoEquipoDAOFile = new DAOTipoEquipoImplFile();
        DAOTipoEquipo tipoEquipoDAOSqlite = new DAOTipoEquipoImplSqlite();
        DAOTipoPuerto tipoPuertoDAOFile = new DAOTipoPuertoImplFile();
        DAOTipoPuerto tipoPuertoDAOSqlite = new DAOTipoPuertoImplSqlite();
        DAOTipoCable tipoCableDAOFile = new DAOTipoCableImplFile();
        DAOTipoCable tipoCableDAOSqlite = new DAOTipoCableImplSqlite();
        DAOUbicacion ubicacionDAOFile = new DAOUbicacionImplFile();
        DAOUbicacion ubicacionDAOSqlite = new DAOUbicacionImplSqlite();
        DAOEquipo equipoDAOFile = new DAOEquipoImplFile();
        DAOEquipo equipoDAOSqlite = new DAOEquipoImplSqlite();
        DAOConexion conexionDAOFile = new DAOConexionImplFile();
        DAOConexion conexionDAOSqlite = new DAOConexionImplSqlite();


        for (TipoEquipo te : tipoEquipoDAOFile.read())
            tipoEquipoDAOSqlite.create(te);

        for (TipoPuerto tp : tipoPuertoDAOFile.read())
            tipoPuertoDAOSqlite.create(tp);

        for (TipoCable tc : tipoCableDAOFile.read())
            tipoCableDAOSqlite.create(tc);

        for (Ubicacion u : ubicacionDAOFile.read())
            ubicacionDAOSqlite.create(u);

        for (Equipo e : equipoDAOFile.read())
            equipoDAOSqlite.create(e);

        for (Conexion c : conexionDAOFile.read())
            conexionDAOSqlite.create(c);
    }
}
