package data.implementations.file;

import data.interfaces.DAOTipoCable;
import models.TipoCable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static utils.Constatnts.DELIMITER;

/**
 * Implementation of the DAOTipoCable interface for file-based storage.
 */
public class DAOTipoCableImplFile implements DAOTipoCable {
    /**
     * The filename where the TipoCable data is stored.
     */
    private final String filename;

    /**
     * List of TipoCable objects.
     */
    private List<TipoCable> list;

    /**
     * Flag indicating whether the data needs to be updated.
     */
    private boolean actualizar;

    /**
     * Constructs a new DAOTipoCableImplFile instance.
     * Initializes the filename and sets the actualizar flag to true.
     */
    public DAOTipoCableImplFile() {
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        filename = rb.getString("tiposCables");
        actualizar = true;
    }

    /**
     * Reads TipoCable data from a file.
     *
     * @param file the file to read from
     * @return a list of TipoCable objects
     */
    private List<TipoCable> readFromFile(String file) {
        List<TipoCable> list = new ArrayList<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter(DELIMITER);
            while (inFile.hasNext()) {
                String codigo = inFile.next();
                String descripcion = inFile.next();
                int velocidad = inFile.nextInt();
                list.add(new TipoCable(codigo, descripcion, velocidad));
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
     * Writes TipoCable data to a file.
     *
     * @param list the list of TipoCable objects to write
     * @param file the file to write to
     */
    private void writeToFile(List<TipoCable> list, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(file);
            for (TipoCable e : list) {
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
     * Creates a new TipoCable object and writes it to the file.
     *
     * @param tipoCable the TipoCable object to create
     */
    @Override
    public void create(TipoCable tipoCable) {
        list.add(tipoCable);
        writeToFile(list, filename);
        actualizar = true;
    }

    /**
     * Reads all TipoCable objects from the file.
     *
     * @return a list of TipoCable objects
     */
    @Override
    public List<TipoCable> read() {
        if (actualizar) {
            list = readFromFile(filename);
            actualizar = false;
        }
        return list;
    }

    /**
     * Updates an existing TipoCable object and writes the changes to the file.
     *
     * @param o the original TipoCable object
     * @param n the new TipoCable object
     */
    @Override
    public void update(TipoCable o, TipoCable n) {
        int pos = list.indexOf(o);
        list.set(pos, n);
        writeToFile(list, filename);
        actualizar = true;
    }

    /**
     * Deletes a TipoCable object and writes the changes to the file.
     *
     * @param tipoCable the TipoCable object to delete
     */
    @Override
    public void delete(TipoCable tipoCable) {
        list.remove(tipoCable);
        writeToFile(list, filename);
        actualizar = true;
    }
}