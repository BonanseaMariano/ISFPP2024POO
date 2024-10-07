package data.implementations;

import data.interfaces.DAOTipoPuerto;
import models.TipoPuerto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static utils.Constatnts.DELIMITER;

public class DAOTipoPuertoImplFile implements DAOTipoPuerto {
    private final String filename;

    public DAOTipoPuertoImplFile() {
        ResourceBundle rb = ResourceBundle.getBundle("config");
        filename = rb.getString("tiposPuertos");
    }

    @Override
    public void create(TipoPuerto tipoPuerto) {

    }

    @Override
    public Map<String, TipoPuerto> read() {
        Map<String, TipoPuerto> tiposPuertos = new ConcurrentHashMap<>();
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
