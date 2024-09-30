package data.implementations;

import data.CargarParametrosArchivos;
import data.interfaces.DAOEquipo;
import data.interfaces.DAOTipoEquipo;
import data.interfaces.DAOTipoPuerto;
import data.interfaces.DAOUbicacion;
import models.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static utils.Constatnts.DELIMITER;

public class DAOEquipoImplFile implements DAOEquipo {
    private static DAOTipoEquipo daoTipoEquipo;
    private static DAOUbicacion daoUbicacion;
    private static DAOTipoPuerto daoTipoPuerto;

    public DAOEquipoImplFile() {
        daoTipoEquipo = new DAOTipoEquipoImplFile();
        daoUbicacion = new DAOUbicacionImplFile();
        daoTipoPuerto = new DAOTipoPuertoImplFile();
    }

    @Override
    public TreeMap<String, Equipo> cargarMapa() {
        String filename = CargarParametrosArchivos.getArchivoEquipos();
        TreeMap<String, Equipo> equipos = new TreeMap<>();
        Scanner read;

        try {
            read = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo " + filename);
            return equipos;
        }
        read.useDelimiter(DELIMITER);

        String codigo;
        String descripcion;
        String marca;
        String modelo;
        TipoEquipo tipoEquipo;
        Ubicacion ubicacion;


        while (read.hasNext()) {
            List<Puerto> puertos = new ArrayList<>();
            List<String> direccionesIp = new ArrayList<>();

            codigo = read.next();
            descripcion = read.next();
            marca = read.next();
            modelo = read.next();
            tipoEquipo = daoTipoEquipo.cargarMapa().get(read.next());
            ubicacion = daoUbicacion.cargarMapa().get(read.next());
            String puertoString = read.next();
            String direccionIpString = read.next();

            //Puertos
            for (String puerto : puertoString.split(",")) {
                int cantidad;
                TipoPuerto tipoPuerto;

                String[] puertoAttributes = puerto.split("/");

                try {
                    cantidad = Integer.parseInt(puertoAttributes[0]);
                    tipoPuerto = daoTipoPuerto.cargarMapa().get(puertoAttributes[1]);
                } catch (NumberFormatException | NullPointerException e) {
                    System.out.println("Error al cargar los puertos del equipo " + codigo);
                    continue;
                }

                puertos.add(new Puerto(cantidad, tipoPuerto));
            }

            //Direcciones IP
            Collections.addAll(direccionesIp, direccionIpString.split(","));

            Equipo equipo = new Equipo(codigo, descripcion, marca, modelo, tipoEquipo, ubicacion, puertos.getFirst(), direccionesIp);
            for (int i = 1; i < puertos.size(); i++) {
                equipo.agregarPuerto(puertos.get(i));
            }

            equipos.put(codigo, equipo);
        }

        return equipos;
    }
}
