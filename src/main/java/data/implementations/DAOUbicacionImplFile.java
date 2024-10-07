package data.implementations;

import data.interfaces.DAOUbicacion;
import models.Ubicacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static utils.Constatnts.DELIMITER;

public class DAOUbicacionImplFile implements DAOUbicacion {
    private final String filename;

    public DAOUbicacionImplFile() {
        ResourceBundle rb = ResourceBundle.getBundle("config");
        filename = rb.getString("ubicaciones");
    }


    @Override
    public void create(Ubicacion ubicacion) {

    }

    @Override
    public Map<String, Ubicacion> read() {
        Map<String, Ubicacion> ubicaciones = new ConcurrentHashMap<>();
        Scanner read;

        try {
            read = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo");
            return ubicaciones;
        }

        read.useDelimiter(DELIMITER);

        String codigo;
        String descripcion;

        while (read.hasNext()) {
            codigo = read.next();
            descripcion = read.next();
            ubicaciones.put(codigo, new Ubicacion(codigo, descripcion));
        }

        read.close();
        return ubicaciones;
    }

    @Override
    public void update(Ubicacion ubicacion) {

    }

    @Override
    public void delete(Ubicacion ubicacion) {

    }

    @Override
    public void agregarUbicacion(Ubicacion ubicacion) {
        String data = ubicacion.getCodigo() + DELIMITER + ubicacion.getDescripcion() + "\n";

        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
