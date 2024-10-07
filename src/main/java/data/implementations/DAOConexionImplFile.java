package data.implementations;

import data.interfaces.DAOConexion;
import models.Conexion;
import models.Equipo;
import models.TipoCable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static utils.Constatnts.DELIMITER;

public class DAOConexionImplFile implements DAOConexion {
    private final String filename;

    private static Map<String, TipoCable> daoTipoCable;
    private static Map<String, Equipo> daoEquipo;

    public DAOConexionImplFile() {
        ResourceBundle rb = ResourceBundle.getBundle("config");
        filename = rb.getString("conexiones");
    }


    @Override
    public List<Conexion> cargarConexiones() {
        List<Conexion> conexiones = new ArrayList<>();
        daoTipoCable = new DAOTipoCableImplFile().read();
        daoEquipo = new DAOEquipoImplFile().read();
        Scanner read;

        try {
            read = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo " + filename);
            return conexiones;
        }
        read.useDelimiter(DELIMITER);

        Equipo pc1;
        Equipo pc2;
        TipoCable tipoCable;

        while (read.hasNext()) {
            pc1 = daoEquipo.get(read.next());
            pc2 = daoEquipo.get(read.next());
            tipoCable = daoTipoCable.get(read.next());
            conexiones.add(new Conexion(tipoCable, pc1, pc2));
        }
        read.close();
        return conexiones;
    }
}
