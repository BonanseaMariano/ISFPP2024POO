package data.implementations.file;

import data.interfaces.DAOConexion;
import models.Conexion;
import models.Equipo;
import models.TipoCable;
import models.TipoPuerto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static utils.Constatnts.DELIMITER;

/**
 * Implementation of the DAOConexion interface for file-based storage.
 */
public class DAOConexionImplFile implements DAOConexion {
    /**
     * The filename where the Conexion data is stored.
     */
    private final String filename;

    /**
     * List of Conexion objects.
     */
    private List<Conexion> list;

    /**
     * Flag indicating whether the data needs to be updated.
     */
    private boolean actualizar;

    /**
     * Hashtable mapping cable types to their codes.
     */
    private Hashtable<String, TipoCable> tiposCables;

    /**
     * Hashtable mapping equipment to their codes.
     */
    private Hashtable<String, Equipo> equipos;

    /**
     * Hashtable mapping port types to their codes.
     */
    private Hashtable<String, TipoPuerto> tiposPuertos;

    /**
     * Constructs a new DAOConexionImplFile instance.
     * Initializes the hashtables and loads data from the resource bundle.
     */
    public DAOConexionImplFile() {
        tiposCables = loadTiposCables();
        equipos = loadEquipos();
        tiposPuertos = loadTiposPuertos();
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        filename = rb.getString("conexiones");
        actualizar = true;
    }

    /**
     * Reads Conexion data from a file.
     *
     * @param file the file to read from
     * @return a list of Conexion objects
     */
    private List<Conexion> readFromFile(String file) {
        List<Conexion> list = new ArrayList<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter(DELIMITER);
            while (inFile.hasNext()) {
                Equipo pc1 = equipos.get(inFile.next());
                TipoPuerto puerto1 = tiposPuertos.get(inFile.next());
                Equipo pc2 = equipos.get(inFile.next());
                TipoPuerto puerto2 = tiposPuertos.get(inFile.next());
                TipoCable tipoCable = tiposCables.get(inFile.next());
                list.add(new Conexion(tipoCable, pc1, puerto1, pc2, puerto2));
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
     * Writes Conexion data to a file.
     *
     * @param list the list of Conexion objects to write
     * @param file the file to write to
     */
    private void writeToFile(List<Conexion> list, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(file);
            for (Conexion e : list) {
                outFile.format("%s;%s;%s;%s;%s;\n", e.getEquipo1().getCodigo(), e.getPuerto1().getCodigo(), e.getEquipo2().getCodigo(), e.getPuerto2().getCodigo(), e.getTipoCable().getCodigo());
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
     * Creates a new Conexion object and writes it to the file.
     *
     * @param conexion the Conexion object to create
     */
    @Override
    public void create(Conexion conexion) {
        list.add(conexion);
        writeToFile(list, filename);
        actualizar = true;
    }

    /**
     * Reads all Conexion objects from the file.
     *
     * @return a list of Conexion objects
     */
    @Override
    public List<Conexion> read() {
        if (actualizar) {
            list = readFromFile(filename);
            actualizar = false;
        }
        return list;
    }

    /**
     * Updates an existing Conexion object and writes the changes to the file.
     *
     * @param o the original Conexion object
     * @param n the new Conexion object
     */
    @Override
    public void update(Conexion o, Conexion n) {
        int pos = list.indexOf(o);
        list.set(pos, n);
        writeToFile(list, filename);
        actualizar = true;
    }

    /**
     * Deletes a Conexion object and writes the changes to the file.
     *
     * @param conexion the Conexion object to delete
     */
    @Override
    public void delete(Conexion conexion) {
        list.remove(conexion);
        writeToFile(list, filename);
        actualizar = true;
    }

    /**
     * Loads cable types from the file.
     *
     * @return a hashtable mapping cable types to their codes
     */
    private Hashtable<String, TipoCable> loadTiposCables() {
        Hashtable<String, TipoCable> tiposCables = new Hashtable<>();
        List<TipoCable> list = new DAOTipoCableImplFile().read();
        for (TipoCable e : list) {
            tiposCables.put(e.getCodigo(), e);
        }
        return tiposCables;
    }

    /**
     * Loads equipment from the file.
     *
     * @return a hashtable mapping equipment to their codes
     */
    private Hashtable<String, Equipo> loadEquipos() {
        Hashtable<String, Equipo> equipos = new Hashtable<>();
        List<Equipo> list = new DAOEquipoImplFile().read();
        for (Equipo e : list) {
            equipos.put(e.getCodigo(), e);
        }
        return equipos;
    }

    /**
     * Loads port types from the file.
     *
     * @return a hashtable mapping port types to their codes
     */
    private Hashtable<String, TipoPuerto> loadTiposPuertos() {
        Hashtable<String, TipoPuerto> tiposPuertos = new Hashtable<>();
        List<TipoPuerto> list = new DAOTipoPuertoImplFile().read();
        for (TipoPuerto e : list) {
            tiposPuertos.put(e.getCodigo(), e);
        }
        return tiposPuertos;
    }
}