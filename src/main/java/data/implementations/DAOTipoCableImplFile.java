package data.implementations;

import data.CargarParametrosArchivos;
import data.interfaces.DAOTipoCable;
import models.TipoCable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import static utils.Constatnts.DELIMITER;

public class DAOTipoCableImplFile implements DAOTipoCable {
    @Override
    public Map<String, TipoCable> cargarMapa() {
        String filename = CargarParametrosArchivos.getArchivoTiposCables();
        Map<String, TipoCable> tiposCables = new TreeMap<>();
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
}
