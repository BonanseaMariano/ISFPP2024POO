package data.implementations;

import data.interfaces.DAOTipoCable;
import models.TipoCable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static utils.Constatnts.DELIMITER;

public class DAOTipoCableImplFile implements DAOTipoCable {
    private final String filename;

    public DAOTipoCableImplFile() {
        ResourceBundle rb = ResourceBundle.getBundle("config");
        filename = rb.getString("tiposCables");
    }

    @Override
    public void create(TipoCable tipoCable) {

    }

    @Override
    public Map<String, TipoCable> read() {
        Map<String, TipoCable> tiposCables = new ConcurrentHashMap<>();
        Scanner read;

        try {
            read = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo " + filename);
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

    @Override
    public void update(TipoCable tipoCable) {

    }

    @Override
    public void delete(TipoCable tipoCable) {

    }
}
