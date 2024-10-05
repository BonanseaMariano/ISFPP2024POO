package data.implementations;

import data.CargarParametrosArchivos;
import data.interfaces.DAOTipoEquipo;
import models.TipoEquipo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import static utils.Constatnts.DELIMITER;

public class DAOTipoEquipoImplFile implements DAOTipoEquipo {

    @Override
    public void create(TipoEquipo tipoEquipo) {

    }

    @Override
    public Map<String, TipoEquipo> read() {
        String filename = CargarParametrosArchivos.getArchivoTiposEquipos();
        Map<String, TipoEquipo> tiposEquipos = new TreeMap<>();
        Scanner read;

        try {
            read = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo " + filename);
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

    @Override
    public void update(TipoEquipo tipoEquipo) {

    }

    @Override
    public void delete(TipoEquipo tipoEquipo) {

    }
}
