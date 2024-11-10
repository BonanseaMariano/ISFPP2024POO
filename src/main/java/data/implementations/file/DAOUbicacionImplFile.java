package data.implementations.file;

import data.interfaces.DAOUbicacion;
import models.Ubicacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static utils.Constatnts.DELIMITER;

/**
 * Implementation of the DAOUbicacion interface for file-based storage.
 */
public class DAOUbicacionImplFile implements DAOUbicacion {
    /**
     * The name of the file where Ubicacion objects are stored.
     */
    private final String filename;

    /**
     * The list of Ubicacion objects.
     */
    private List<Ubicacion> list;

    /**
     * Flag indicating whether the list needs to be updated from the file.
     */
    private boolean actualizar;

    /**
     * Constructor that initializes the filename and sets the actualizar flag to true.
     */
    public DAOUbicacionImplFile() {
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        filename = rb.getString("ubicaciones");
        actualizar = true;
    }

    /**
     * Reads Ubicacion objects from a file.
     *
     * @param file the file to read from
     * @return a list of Ubicacion objects
     */
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

    /**
     * Writes a list of Ubicacion objects to a file.
     *
     * @param list the list of Ubicacion objects to write
     * @param file the file to write to
     */
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

    /**
     * Adds a new Ubicacion object to the list and writes the updated list to the file.
     *
     * @param ubicacion the Ubicacion object to add
     */
    @Override
    public void create(Ubicacion ubicacion) {
        list.add(ubicacion);
        writeToFile(list, filename);
        actualizar = true;
    }

    /**
     * Reads the list of Ubicacion objects from the file if the actualizar flag is true.
     *
     * @return the list of Ubicacion objects
     */
    @Override
    public List<Ubicacion> read() {
        if (actualizar) {
            list = readFromFile(filename);
            actualizar = false;
        }
        return list;
    }

    /**
     * Updates a Ubicacion object in the list and writes the updated list to the file.
     *
     * @param t the Ubicacion object to update
     */
    @Override
    public void update(Ubicacion t) {
        int pos = list.indexOf(t);
        list.set(pos, t);
        writeToFile(list, filename);
        actualizar = true;
    }

    /**
     * Removes a Ubicacion object from the list and writes the updated list to the file.
     *
     * @param ubicacion the Ubicacion object to remove
     */
    @Override
    public void delete(Ubicacion ubicacion) {
        list.remove(ubicacion);
        writeToFile(list, filename);
        actualizar = true;
    }
}