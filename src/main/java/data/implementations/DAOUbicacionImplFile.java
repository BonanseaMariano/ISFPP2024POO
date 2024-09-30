package data.implementations;

import data.CargarParametrosArchivos;
import data.interfaces.DAOUbicacion;
import models.Ubicacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import static utils.Constatnts.DELIMITER;

public class DAOUbicacionImplFile implements DAOUbicacion {

    @Override
    public Map<String, Ubicacion> cargarMapa() {
        Map<String, Ubicacion> ubicaciones = new TreeMap<>();
        String filename = CargarParametrosArchivos.getArchivoUbicaciones();
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
    public void agregarUbicacion(Ubicacion ubicacion) {
        String filename = CargarParametrosArchivos.getArchivoUbicaciones();
        String data = ubicacion.getCodigo() + DELIMITER + ubicacion.getDescripcion() + "\n";

        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
