package data.implementations;

import data.interfaces.DAOUbicacion;
import models.TipoEquipo;
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
    private List<Ubicacion> list;
    private boolean actualizar;

    public DAOUbicacionImplFile() {
        ResourceBundle rb = ResourceBundle.getBundle("config");
        filename = rb.getString("ubicaciones");
        actualizar = true;
    }

    private List<Ubicacion> readFromFile(String file) {
        List<Ubicacion> list = new ArrayList<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter(DELIMITER);
            while (inFile.hasNext()) {
                String codigo = inFile.next();
                String descripcion = inFile.next();
                list.add(new Ubicacion(codigo, descripcion));
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Error opening file.");
            fileNotFoundException.printStackTrace();
        } catch (NoSuchElementException noSuchElementException) {
            System.err.println("Error in file record structure");
            noSuchElementException.printStackTrace();
        } catch (IllegalStateException illegalStateException) {
            System.err.println("Error reading from file.");
            illegalStateException.printStackTrace();
        } finally {
            if (inFile != null)
                inFile.close();
        }
        return list;
    }

    private void writeToFile(List<Ubicacion> list, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(file);
            for (Ubicacion e : list) {
                outFile.format("%s;%s;\n", e.getCodigo(), e.getDescripcion());
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Error creating file.");
        } catch (FormatterClosedException formatterClosedException) {
            System.err.println("Error writing to file.");
        } finally {
            if (outFile != null)
                outFile.close();
        }
    }

    @Override
    public void create(Ubicacion ubicacion) {
        list.add(ubicacion);
        writeToFile(list, filename);
        actualizar = true;
    }

    @Override
    public List<Ubicacion> read() {
        if (actualizar) {
            list = readFromFile(filename);
            actualizar = false;
        }
        return list;
    }

    @Override
    public void update(Ubicacion ubicacion) {
        int pos = list.indexOf(ubicacion);
        list.set(pos, ubicacion);
        writeToFile(list, filename);
        actualizar = true;
    }

    @Override
    public void delete(Ubicacion ubicacion) {
        list.remove(ubicacion);
        writeToFile(list, filename);
        actualizar = true;
    }

}
