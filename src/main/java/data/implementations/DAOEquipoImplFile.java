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
    public void create(Equipo equipo) {

    }

    @Override
    public Map<String, Equipo> read() {
        String filename = CargarParametrosArchivos.getArchivoEquipos();
        Map<String, Equipo> equipos = new TreeMap<>();
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
            tipoEquipo = daoTipoEquipo.read().get(read.next());
            ubicacion = daoUbicacion.read().get(read.next());
            String puertoString = read.next();
            String direccionIpString = read.next();

            //Puertos
            for (String puerto : puertoString.split(",")) {
                int cantidad;
                TipoPuerto tipoPuerto;

                String[] puertoAttributes = puerto.split("/");

                try {
                    cantidad = Integer.parseInt(puertoAttributes[0]);
                    tipoPuerto = daoTipoPuerto.read().get(puertoAttributes[1]);
                } catch (NumberFormatException | NullPointerException e) {
                    System.out.println("Error al cargar los puertos del equipo " + codigo);
                    continue;
                }

                puertos.add(new Puerto(cantidad, tipoPuerto));
            }

            //Direcciones IP
            Collections.addAll(direccionesIp, direccionIpString.split(","));

            Equipo equipo = new Equipo(codigo, descripcion, marca, modelo, tipoEquipo, ubicacion, puertos.getFirst(), direccionesIp.getFirst());
            //Si tiene mas de un puerto
            if (puertos.size() > 1) {
                for (int i = 1; i < puertos.size(); i++) {
                    equipo.agregarPuerto(puertos.get(i));
                }
            }

            //Si tiene mas de una direccion IP
            if (direccionesIp.size() > 1) {
                for (int i = 1; i < direccionesIp.size(); i++) {
                    equipo.agregarDireccionIp(direccionesIp.get(i));
                }
            }

            //Cargo todos los equipos en el mapa
            equipos.put(codigo, equipo);
        }

        return equipos;
    }

    @Override
    public void update(Equipo equipo) {

    }

    @Override
    public void delete(Equipo equipo) {

    }
}
