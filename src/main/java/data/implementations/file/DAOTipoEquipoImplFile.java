package data.implementations.file;

import data.interfaces.DAOTipoEquipo;
import models.TipoEquipo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static utils.Constatnts.DELIMITER;

public class DAOTipoEquipoImplFile implements DAOTipoEquipo {
    private final String filename;
    private List<TipoEquipo> list;
    private boolean actualizar;

    public DAOTipoEquipoImplFile() {
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        filename = rb.getString("tiposEquipos");
        actualizar = true;
    }

    private List<TipoEquipo> readFromFile(String file) {
        List<TipoEquipo> list = new ArrayList<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter(DELIMITER);
            while (inFile.hasNext()) {
                String codigo = inFile.next();
                String descripcion = inFile.next();
                list.add(new TipoEquipo(codigo, descripcion));
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

    private void writeToFile(List<TipoEquipo> list, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(file);
            for (TipoEquipo e : list) {
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
    public void create(TipoEquipo tipoEquipo) {
        list.add(tipoEquipo);
        writeToFile(list, filename);
        actualizar = true;
    }

    @Override
    public List<TipoEquipo> read() {
        if (actualizar) {
            list = readFromFile(filename);
            actualizar = false;
        }
        return list;
    }

    @Override
    public void update(TipoEquipo o, TipoEquipo n) {
        int pos = list.indexOf(o);
        list.set(pos, n);
        writeToFile(list, filename);
        actualizar = true;
    }

    @Override
    public void delete(TipoEquipo tipoEquipo) {
        list.remove(tipoEquipo);
        writeToFile(list, filename);
        actualizar = true;
    }
}
