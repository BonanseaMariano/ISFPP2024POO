package data.implementations;

import data.CargarParametrosArchivos;
import data.interfaces.DAOTipoPuerto;
import models.TipoPuerto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import static utils.Constatnts.DELIMITER;

public class DAOTipoPuertoImplFile implements DAOTipoPuerto {
    @Override
    public void create(TipoPuerto tipoPuerto) {

    }

    @Override
    public Map<String, TipoPuerto> read() {
        String filename = CargarParametrosArchivos.getArchivoTiposPuertos();
        Map<String, TipoPuerto> tiposPuertos = new TreeMap<>();
        Scanner read;

        try {
            read = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo " + filename);
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

    @Override
    public void update(TipoPuerto tipoPuerto) {

    }

    @Override
    public void delete(TipoPuerto tipoPuerto) {

    }
}
