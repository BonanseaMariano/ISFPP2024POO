package data.implementations.file;

import data.interfaces.DAOTipoPuerto;
import models.TipoPuerto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static utils.Constatnts.DELIMITER;

/**
 * Implementation of the DAOTipoPuerto interface for file-based storage.
 */
public class DAOTipoPuertoImplFile implements DAOTipoPuerto {
    /**
     * The name of the file where TipoPuerto objects are stored.
     */
    private final String filename;

    /**
     * The list of TipoPuerto objects.
     */
    private List<TipoPuerto> list;

    /**
     * Flag indicating whether the list needs to be updated from the file.
     */
    private boolean actualizar;

    /**
     * Constructor that initializes the filename and sets the actualizar flag to true.
     */
    public DAOTipoPuertoImplFile() {
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        filename = rb.getString("tiposPuertos");
        actualizar = true;
    }

    /**
     * Reads TipoPuerto objects from a file.
     *
     * @param file the file to read from
     * @return a list of TipoPuerto objects
     */
    private List<TipoPuerto> readFromFile(String file) {
        List<TipoPuerto> list = new ArrayList<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter(DELIMITER);
            while (inFile.hasNext()) {
                String codigo = inFile.next();
                String descripcion = inFile.next();
                int velocidad = inFile.nextInt();
                list.add(new TipoPuerto(codigo, descripcion, velocidad));
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
     * Writes a list of TipoPuerto objects to a file.
     *
     * @param list the list of TipoPuerto objects to write
     * @param file the file to write to
     */
    private void writeToFile(List<TipoPuerto> list, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(file);
            for (TipoPuerto e : list) {
                outFile.format("%s;%s;%s;\n", e.getCodigo(), e.getDescripcion(), e.getVelocidad());
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
     * Adds a new TipoPuerto object to the list and writes the updated list to the file.
     *
     * @param tipoPuerto the TipoPuerto object to add
     */
    @Override
    public void create(TipoPuerto tipoPuerto) {
        list.add(tipoPuerto);
        writeToFile(list, filename);
        actualizar = true;
    }

    /**
     * Reads the list of TipoPuerto objects from the file if the actualizar flag is true.
     *
     * @return the list of TipoPuerto objects
     */
    @Override
    public List<TipoPuerto> read() {
        if (actualizar) {
            list = readFromFile(filename);
            actualizar = false;
        }
        return list;
    }

    /**
     * Updates an existing TipoPuerto object in the list and writes the updated list to the file.
     *
     * @param o the existing TipoPuerto object
     * @param n the new TipoPuerto object
     */
    @Override
    public void update(TipoPuerto o, TipoPuerto n) {
        int pos = list.indexOf(o);
        list.set(pos, n);
        writeToFile(list, filename);
        actualizar = true;
    }

    /**
     * Removes a TipoPuerto object from the list and writes the updated list to the file.
     *
     * @param tipoPuerto the TipoPuerto object to remove
     */
    @Override
    public void delete(TipoPuerto tipoPuerto) {
        list.remove(tipoPuerto);
        writeToFile(list, filename);
        actualizar = true;
    }
}