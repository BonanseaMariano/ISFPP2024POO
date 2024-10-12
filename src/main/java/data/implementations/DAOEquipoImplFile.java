package data.implementations;

import data.interfaces.DAOEquipo;
import data.interfaces.DAOTipoEquipo;
import data.interfaces.DAOTipoPuerto;
import data.interfaces.DAOUbicacion;
import models.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static utils.Constatnts.DELIMITER;

public class DAOEquipoImplFile implements DAOEquipo {
    private final String filename;
    private List<Equipo> list;
    private boolean actualizar;
    private Hashtable<String, TipoEquipo> tiposEquipos;
    private Hashtable<String, Ubicacion> ubicaciones;
    private Hashtable<String, TipoPuerto> tiposPuertos;

    public DAOEquipoImplFile() {
        tiposEquipos = loadTipoEquipos();
        ubicaciones = loadUbicaciones();
        tiposPuertos = loadTipoPuertos();
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        filename = rb.getString("equipos");
        actualizar = true;
    }

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

                //Puertos
                for (String puerto : puertoString.split("/")) {
                    int cantidad;
                    TipoPuerto tipoPuerto;

                    String[] puertoAttributes = puerto.split(",");

                    try {
                        tipoPuerto = tiposPuertos.get(puertoAttributes[0]);
                        cantidad = Integer.parseInt(puertoAttributes[1]);
                    } catch (NumberFormatException | NullPointerException e) {
                        System.out.println("Error al cargar los puertos del equipo " + codigo);
                        continue;
                    }

                    puertos.add(new Puerto(cantidad, tipoPuerto));
                }

                //Direcciones IP
                Collections.addAll(direccionesIp, direccionIpString.split(","));

                Equipo equipo = new Equipo(codigo, descripcion, marca, modelo, tipoEquipo, ubicacion, puertos.getFirst(), direccionesIp.getFirst(), estado);

                //Si tiene mas de un puerto
                if (puertos.size() > 1) {
                    for (int i = 1; i < puertos.size(); i++) {
                        equipo.agregarPuerto(puertos.get(i));
                    }
                }

                //Si tiene mas de una direccion IP
                if (direccionesIp.size() > 1) {
                    for (int i = 1; i < direccionesIp.size(); i++) {
                        equipo.agregarDireccionIp(direccionesIp.get(i));
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

    private void writeToFile(List<Equipo> list, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(file);
            for (Equipo e : list) {
                StringBuilder puertos = new StringBuilder();
                for (Puerto p : e.getPuertos()) {
                    puertos.append(p.getCantidad()).append("/").append(p.getTipoPuerto().getCodigo()).append(",");
                }
                // Remove the last comma
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

                outFile.format("%s;%s;%s;%s;%s;%s;%s;%s;\n",
                        e.getCodigo(),
                        e.getDescripcion(),
                        e.getMarca(),
                        e.getModelo(),
                        e.getTipoEquipo().getCodigo(),
                        e.getUbicacion().getCodigo(),
                        puertos,
                        direccionesIp);
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
    public void create(Equipo equipo) {
        list.add(equipo);
        writeToFile(list, filename);
        actualizar = true;
    }

    @Override
    public List<Equipo> read() {
        if (actualizar) {
            list = readFromFile(filename);
            actualizar = false;
        }
        return list;
    }

    @Override
    public void update(Equipo equipo) {
        int pos = list.indexOf(equipo);
        list.set(pos, equipo);
        writeToFile(list, filename);
        actualizar = true;
    }

    @Override
    public void delete(Equipo equipo) {
        list.remove(equipo);
        writeToFile(list, filename);
        actualizar = true;
    }

    private Hashtable<String, TipoEquipo> loadTipoEquipos() {
        Hashtable<String, TipoEquipo> tiposEquipos = new Hashtable<>();
        DAOTipoEquipo tipoEquipoDAO = new DAOTipoEquipoImplFile();
        List<TipoEquipo> ds = tipoEquipoDAO.read();
        for (TipoEquipo d : ds)
            tiposEquipos.put(d.getCodigo(), d);
        return tiposEquipos;
    }

    private Hashtable<String, Ubicacion> loadUbicaciones() {
        Hashtable<String, Ubicacion> ubicaciones = new Hashtable<>();
        DAOUbicacion ubicacionDAO = new DAOUbicacionImplFile();
        List<Ubicacion> ds = ubicacionDAO.read();
        for (Ubicacion d : ds)
            ubicaciones.put(d.getCodigo(), d);
        return ubicaciones;
    }

    private Hashtable<String, TipoPuerto> loadTipoPuertos() {
        Hashtable<String, TipoPuerto> tiposPuertos = new Hashtable<>();
        DAOTipoPuerto tipoPuertoDAO = new DAOTipoPuertoImplFile();
        List<TipoPuerto> ds = tipoPuertoDAO.read();
        for (TipoPuerto d : ds)
            tiposPuertos.put(d.getCodigo(), d);
        return tiposPuertos;
    }
}
