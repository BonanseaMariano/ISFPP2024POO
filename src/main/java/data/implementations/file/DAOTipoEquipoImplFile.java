package data.implementations.file;

import data.interfaces.DAOTipoEquipo;
import models.TipoEquipo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static utils.Constatnts.DELIMITER;

/**
 * Implementation of the DAOTipoEquipo interface for file-based storage.
 */
public class DAOTipoEquipoImplFile implements DAOTipoEquipo {
    /**
     * The name of the file where TipoEquipo objects are stored.
     */
    private final String filename;

    /**
     * The list of TipoEquipo objects.
     */
    private List<TipoEquipo> list;

    /**
     * Flag indicating whether the list needs to be updated from the file.
     */
    private boolean actualizar;

    /**
     * Constructor that initializes the filename and sets the actualizar flag to true.
     */
    public DAOTipoEquipoImplFile() {
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        filename = rb.getString("tiposEquipos");
        actualizar = true;
    }

    /**
     * Reads TipoEquipo objects from a file.
     *
     * @param file the file to read from
     * @return a list of TipoEquipo objects
     */
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

    /**
     * Writes a list of TipoEquipo objects to a file.
     *
     * @param list the list of TipoEquipo objects to write
     * @param file the file to write to
     */
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

    /**
     * Adds a new TipoEquipo object to the list and writes the updated list to the file.
     *
     * @param tipoEquipo the TipoEquipo object to add
     */
    @Override
    public void create(TipoEquipo tipoEquipo) {
        list.add(tipoEquipo);
        writeToFile(list, filename);
        actualizar = true;
    }

    /**
     * Reads the list of TipoEquipo objects from the file if the actualizar flag is true.
     *
     * @return the list of TipoEquipo objects
     */
    @Override
    public List<TipoEquipo> read() {
        if (actualizar) {
            list = readFromFile(filename);
            actualizar = false;
        }
        return list;
    }

    /**
     * Updates a TipoEquipo object in the list and writes the updated list to the file.
     *
     * @param t the TipoEquipo object to update
     */
    @Override
    public void update(TipoEquipo t) {
        int pos = list.indexOf(t);
        list.set(pos, t);
        writeToFile(list, filename);
        actualizar = true;
    }

    /**
     * Removes a TipoEquipo object from the list and writes the updated list to the file.
     *
     * @param tipoEquipo the TipoEquipo object to remove
     */
    @Override
    public void delete(TipoEquipo tipoEquipo) {
        list.remove(tipoEquipo);
        writeToFile(list, filename);
        actualizar = true;
    }
}