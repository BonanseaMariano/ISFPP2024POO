package data.implementations;

import data.interfaces.DAOConexion;
import models.Conexion;
import models.Equipo;
import models.TipoCable;
import models.TipoPuerto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static utils.Constatnts.DELIMITER;

public class DAOConexionImplFile implements DAOConexion {
    private final String filename;
    private List<Conexion> list;
    private boolean actualizar;
    private Hashtable<String, TipoCable> tiposCables;
    private Hashtable<String, Equipo> equipos;
    private Hashtable<String, TipoPuerto> tiposPuertos;

    public DAOConexionImplFile() {
        tiposCables = loadTiposCables();
        equipos = loadEquipos();
        tiposPuertos = loadTiposPuertos();
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        filename = rb.getString("conexiones");
        actualizar = true;
    }


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

    private void writeToFile(List<Conexion> list, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(file);
            for (Conexion e : list) {
                outFile.format("%s;%s;%s;%s;%s;\n", e.getEquipo1(), e.getPuerto1(), e.getEquipo2(), e.getPuerto2(), e.getTipoCable());
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
    public void create(Conexion conexion) {
        list.add(conexion);
        writeToFile(list, filename);
        actualizar = true;
    }

    @Override
    public List<Conexion> read() {
        if (actualizar) {
            list = readFromFile(filename);
            actualizar = false;
        }
        return list;
    }

    @Override
    public void update(Conexion conexion) {
        int pos = list.indexOf(conexion);
        list.set(pos, conexion);
        writeToFile(list, filename);
        actualizar = true;
    }

    @Override
    public void delete(Conexion conexion) {
        list.remove(conexion);
        writeToFile(list, filename);
        actualizar = true;
    }

    private Hashtable<String, TipoCable> loadTiposCables() {
        Hashtable<String, TipoCable> tiposCables = new Hashtable<>();
        List<TipoCable> list = new DAOTipoCableImplFile().read();
        for (TipoCable e : list) {
            tiposCables.put(e.getCodigo(), e);
        }
        return tiposCables;
    }

    private Hashtable<String, Equipo> loadEquipos() {
        Hashtable<String, Equipo> equipos = new Hashtable<>();
        List<Equipo> list = new DAOEquipoImplFile().read();
        for (Equipo e : list) {
            equipos.put(e.getCodigo(), e);
        }
        return equipos;
    }

    private Hashtable<String, TipoPuerto> loadTiposPuertos() {
        Hashtable<String, TipoPuerto> tiposPuertos = new Hashtable<>();
        List<TipoPuerto> list = new DAOTipoPuertoImplFile().read();
        for (TipoPuerto e : list) {
            tiposPuertos.put(e.getCodigo(), e);
        }
        return tiposPuertos;
    }
}
