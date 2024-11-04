package data.implementations.file;

import data.interfaces.DAOEquipo;
import data.interfaces.DAOTipoEquipo;
import data.interfaces.DAOTipoPuerto;
import data.interfaces.DAOUbicacion;
import models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static utils.Constatnts.DELIMITER;

/**
 * Implementation of the DAOEquipo interface for file-based storage.
 */
public class DAOEquipoImplFile implements DAOEquipo {
    /**
     * The filename where the Equipo data is stored.
     */
    private final String filename;

    /**
     * List of Equipo objects.
     */
    private List<Equipo> list;

    /**
     * Flag indicating whether the data needs to be updated.
     */
    private boolean actualizar;

    /**
     * Hashtable mapping equipment types to their codes.
     */
    private Hashtable<String, TipoEquipo> tiposEquipos;

    /**
     * Hashtable mapping locations to their codes.
     */
    private Hashtable<String, Ubicacion> ubicaciones;

    /**
     * Hashtable mapping port types to their codes.
     */
    private Hashtable<String, TipoPuerto> tiposPuertos;

    /**
     * Constructs a new DAOEquipoImplFile instance.
     * Initializes the hashtables and loads data from the resource bundle.
     */
    public DAOEquipoImplFile() {
        tiposEquipos = loadTipoEquipos();
        ubicaciones = loadUbicaciones();
        tiposPuertos = loadTipoPuertos();
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        filename = rb.getString("equipos");
        actualizar = true;
    }

    /**
     * Reads Equipo data from a file.
     *
     * @param file the file to read from
     * @return a list of Equipo objects
     */
    private List<Equipo> readFromFile(String file) {
        List<Equipo> list = new ArrayList<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter(DELIMITER);
            while (inFile.hasNext()) {
                List<Puerto> puertos = new ArrayList<>();
                List<String> direccionesIp = new ArrayList<>();

                String codigo = inFile.next();
                String descripcion = inFile.next();
                String marca = inFile.next();
                String modelo = inFile.next();
                TipoEquipo tipoEquipo = tiposEquipos.get(inFile.next());
                Ubicacion ubicacion = ubicaciones.get(inFile.next());
                String puertoString = inFile.next();
                String direccionIpString = inFile.next();
                Boolean estado = inFile.nextBoolean();

                // Puertos
                for (String puerto : puertoString.split("/")) {
                    int cantidad;
                    TipoPuerto tipoPuerto;

                    String[] puertoAttributes = puerto.split(",");

                    try {
                        cantidad = Integer.parseInt(puertoAttributes[0]);
                        tipoPuerto = tiposPuertos.get(puertoAttributes[1]);
                    } catch (NumberFormatException | NullPointerException e) {
                        System.out.println("Error al cargar los puertos del equipo " + codigo);
                        continue;
                    }

                    puertos.add(new Puerto(cantidad, tipoPuerto));
                }

                // Direcciones IP
                Collections.addAll(direccionesIp, direccionIpString.split(","));

                Equipo equipo = new Equipo(codigo, descripcion, marca, modelo, tipoEquipo, ubicacion, puertos.get(0), direccionesIp.get(0), estado);

                // Si tiene mas de un puerto
                if (puertos.size() > 1) {
                    for (int i = 1; i < puertos.size(); i++) {
                        equipo.addPuerto(puertos.get(i));
                    }
                }

                // Si tiene mas de una direccion IP
                if (direccionesIp.size() > 1) {
                    for (int i = 1; i < direccionesIp.size(); i++) {
                        equipo.addIP(direccionesIp.get(i));
                    }
                }
                list.add(equipo);
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
     * Writes Equipo data to a file.
     *
     * @param list the list of Equipo objects to write
     * @param file the file to write to
     */
    private void writeToFile(List<Equipo> list, String file) {
        Formatter outFile = null;
        boolean isFirst = true;
        try {
            outFile = new Formatter(file);
            for (Equipo e : list) {
                StringBuilder puertos = new StringBuilder();
                for (Puerto p : e.getPuertos()) {
                    puertos.append(p.getCantidad()).append(",").append(p.getTipoPuerto().getCodigo()).append("/");
                }
                // Remove the last slash
                if (!puertos.isEmpty()) {
                    puertos.setLength(puertos.length() - 1);
                }

                StringBuilder direccionesIp = new StringBuilder();
                for (String ip : e.getDireccionesIp()) {
                    direccionesIp.append(ip).append(",");
                }
                // Remove the last comma
                if (!direccionesIp.isEmpty()) {
                    direccionesIp.setLength(direccionesIp.length() - 1);
                }
                if (!isFirst) {
                    outFile.format("\n");
                } else {
                    isFirst = false;
                }
                outFile.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;",
                        e.getCodigo(),
                        e.getDescripcion(),
                        e.getMarca(),
                        e.getModelo(),
                        e.getTipoEquipo().getCodigo(),
                        e.getUbicacion().getCodigo(),
                        puertos,
                        direccionesIp,
                        e.isEstado());
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
     * Creates a new Equipo object and writes it to the file.
     *
     * @param equipo the Equipo object to create
     */
    @Override
    public void create(Equipo equipo) {
        list.add(equipo);
        writeToFile(list, filename);
        actualizar = true;
    }

    /**
     * Reads all Equipo objects from the file.
     *
     * @return a list of Equipo objects
     */
    @Override
    public List<Equipo> read() {
        if (actualizar) {
            list = readFromFile(filename);
            actualizar = false;
        }
        return list;
    }

    /**
     * Updates an existing Equipo object and writes the changes to the file.
     *
     * @param o the original Equipo object
     * @param n the new Equipo object
     */
    @Override
    public void update(Equipo o, Equipo n) {
        int pos = list.indexOf(o);
        list.set(pos, n);
        writeToFile(list, filename);
        actualizar = true;
    }

    /**
     * Deletes a Equipo object and writes the changes to the file.
     *
     * @param equipo the Equipo object to delete
     */
    @Override
    public void delete(Equipo equipo) {
        list.remove(equipo);
        writeToFile(list, filename);
        actualizar = true;
    }

    /**
     * Loads equipment types from the file.
     *
     * @return a hashtable mapping equipment types to their codes
     */
    private Hashtable<String, TipoEquipo> loadTipoEquipos() {
        Hashtable<String, TipoEquipo> tiposEquipos = new Hashtable<>();
        DAOTipoEquipo tipoEquipoDAO = new DAOTipoEquipoImplFile();
        List<TipoEquipo> ds = tipoEquipoDAO.read();
        for (TipoEquipo d : ds)
            tiposEquipos.put(d.getCodigo(), d);
        return tiposEquipos;
    }

    /**
     * Loads locations from the file.
     *
     * @return a hashtable mapping locations to their codes
     */
    private Hashtable<String, Ubicacion> loadUbicaciones() {
        Hashtable<String, Ubicacion> ubicaciones = new Hashtable<>();
        DAOUbicacion ubicacionDAO = new DAOUbicacionImplFile();
        List<Ubicacion> ds = ubicacionDAO.read();
        for (Ubicacion d : ds)
            ubicaciones.put(d.getCodigo(), d);
        return ubicaciones;
    }

    @Override
    public void createIp(Equipo equipo, String IP) {
        Equipo newEquipo = cloneEquipo(equipo);
        newEquipo.getDireccionesIp().add(IP);
        update(equipo, newEquipo);
    }

    @Override
    public void deleteIp(Equipo equipo, String IP) {
        Equipo newEquipo = cloneEquipo(equipo);
        newEquipo.getDireccionesIp().remove(IP);
        update(equipo, newEquipo);
    }

    @Override
    public void updateIp(Equipo equipo, String oldIP, String newIP) {
        Equipo newEquipo = cloneEquipo(equipo);
        newEquipo.getDireccionesIp().remove(oldIP);
        newEquipo.getDireccionesIp().add(newIP);
        update(equipo, newEquipo);
    }

    @Override
    public void createPort(Equipo equipo, Puerto puerto) {
        Equipo newEquipo = new Equipo();
        newEquipo.getPuertos().add(puerto);
        update(equipo, newEquipo);
    }

    @Override
    public void deletePort(Equipo equipo, Puerto puerto) {
        Equipo newEquipo = cloneEquipo(equipo);
        newEquipo.getPuertos().remove(puerto);
        update(equipo, newEquipo);
    }

    @Override
    public void updatePort(Equipo equipo, Puerto oldPort, Puerto newPort) {
        Equipo newEquipo = cloneEquipo(equipo);
        newEquipo.getPuertos().remove(oldPort);
        newEquipo.getPuertos().add(newPort);
        update(equipo, newEquipo);
    }

    private Equipo cloneEquipo(Equipo equipo) {
        Equipo newEquipo = new Equipo();
        newEquipo.setCodigo(equipo.getCodigo());
        newEquipo.setUbicacion(equipo.getUbicacion());
        newEquipo.setDescripcion(equipo.getDescripcion());
        newEquipo.setTipoEquipo(equipo.getTipoEquipo());
        for(String IP : equipo.getDireccionesIp()) {
            newEquipo.getDireccionesIp().add(IP);
        }
        for (Puerto puerto : equipo.getPuertos()) {
            newEquipo.getPuertos().add(puerto);
        }
        return newEquipo;
    }

    /**
     * Loads port types from the file.
     *
     * @return a hashtable mapping port types to their codes
     */
    private Hashtable<String, TipoPuerto> loadTipoPuertos() {
        Hashtable<String, TipoPuerto> tiposPuertos = new Hashtable<>();
        DAOTipoPuerto tipoPuertoDAO = new DAOTipoPuertoImplFile();
        List<TipoPuerto> ds = tipoPuertoDAO.read();
        for (TipoPuerto d : ds)
            tiposPuertos.put(d.getCodigo(), d);
        return tiposPuertos;
    }

}