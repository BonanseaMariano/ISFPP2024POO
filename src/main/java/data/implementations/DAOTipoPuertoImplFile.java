package data.implementations;

import data.CargarParametrosArchivos;
import data.interfaces.DAOTipoPuerto;
import models.TipoPuerto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

import static utils.Constatnts.DELIMITER;

public class DAOTipoPuertoImplFile implements DAOTipoPuerto {
    @Override
    public TreeMap<String, TipoPuerto> cargarMapa() {
        String filename = CargarParametrosArchivos.getArchivoTiposPuertos();
        TreeMap<String, TipoPuerto> tiposPuertos = new TreeMap<>();
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
}
