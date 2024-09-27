package data;

import models.*;

import java.util.TreeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Dato {
    private static final String DELIMITER = "\\s*;\\s*";

    private static TreeMap<String, TipoEquipo> tiposEquipos;
    private static TreeMap<String, TipoCable> tiposCables;
    private static TreeMap<String, TipoPuerto> tiposPuertos;

    public Dato() {
        tiposEquipos = cargarTiposEquipos("tiposEquipos.txt");
        tiposCables = cargarTiposCables("tiposCables.txt");
        tiposPuertos = cargarTiposPuertos("tiposPuertos.txt");
    }

    public static TreeMap<String, TipoEquipo> cargarTiposEquipos(String filename) {
        TreeMap<String, TipoEquipo> tiposEquipos = new TreeMap<>();
        Scanner read;

        try {
            read = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de tipos de cables.");
            return tiposEquipos;
        }
        read.useDelimiter(DELIMITER);

        String codigo;
        String descripcion;

        while (read.hasNext()) {
            codigo = read.next();
            descripcion = read.next();
            tiposEquipos.put(codigo, new TipoEquipo(codigo, descripcion));
        }

        read.close();
        return tiposEquipos;
    }


    public static TreeMap<String, TipoPuerto> cargarTiposPuertos(String filename) {
        TreeMap<String, TipoPuerto> tiposPuertos = new TreeMap<>();
        Scanner read;

        try {
            read = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de tipos de cables.");
            return tiposPuertos;
        }

        read.useDelimiter(DELIMITER);

        String codigo;
        String descripcion;
        int velocidad;

        while (read.hasNext()) {
            codigo = read.next();
            descripcion = read.next();
            velocidad = read.nextInt();
            tiposPuertos.put(codigo, new TipoPuerto(codigo, descripcion, velocidad));
        }

        read.close();
        return tiposPuertos;
    }


    public static TreeMap<String, TipoCable> cargarTiposCables(String filename) {
        TreeMap<String, TipoCable> tiposCables = new TreeMap<>();
        Scanner read;

        try {
            read = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de tipos de cables.");
            return tiposCables;
        }

        read.useDelimiter(DELIMITER);

        String codigo;
        String descripcion;
        int velocidad;

        while (read.hasNext()) {
            codigo = read.next();
            descripcion = read.next();
            velocidad = read.nextInt();
            tiposCables.put(codigo, new TipoCable(codigo, descripcion, velocidad));
        }

        read.close();
        return tiposCables;
    }

    //TODO terminar
    public static TreeMap<String, Equipo> cargarEquipos(String filename) {
        TreeMap<String, Equipo> equipos = new TreeMap<>();
        Scanner read;

        try {
            read = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de equipos.");
            return equipos;
        }
        read.useDelimiter(DELIMITER);

        String codigo;
        String descripcion;
        TipoEquipo tipoEquipo;
        Ubicacion ubicacion;
        List<Puerto> puertos;
        String modelo;
        List<Conexion> conexiones;


        while (read.hasNext()) {

        }

        return equipos;
    }

    //TODO hacer metodo que retorne la lista de conexiones entre equipos


}
