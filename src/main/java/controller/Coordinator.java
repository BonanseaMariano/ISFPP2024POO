package controller;

import exceptions.InvalidConexionException;
import exceptions.InvalidEquipoException;
import gui.Frame;
import logic.Logic;
import logic.Red;
import models.*;

import java.util.*;

public class Coordinator {
    private Red red;
    private Logic logic;
    private Frame frame;

    public Red getRed() {
        return red;
    }

    public void setRed(Red red) {
        this.red = red;
    }

    public Logic getLogic() {
        return logic;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public Frame getGui() {
        return frame;
    }

    public void setGui(Frame gui) {
        this.frame = gui;
    }

    public void addConnection(Conexion conexion) {
        try {
            red.addConexion(conexion);
            logic.addEdge(conexion);
        } catch (InvalidConexionException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteConnection(Conexion conexion) {
        try {
            red.deleteConexion(conexion);
            logic.deleteEdge(conexion);
        } catch (InvalidConexionException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifyConnection(Conexion conexion) {
        red.modifyConnection(conexion);
    }


    public void addEquipo(Equipo equipo) {
        try {
            red.addEquipo(equipo);
            logic.addVertex(equipo);
        } catch (InvalidEquipoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteEquipo(Equipo equipo) {
        try {
            red.deleteEquipo(equipo);
            logic.deleteVertex(equipo);
        } catch (InvalidEquipoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifyEquipo(Equipo equipo) {
        red.modifyEquipo(equipo);
    }

    public void addUbicacion(Ubicacion ubicacion) {
        red.addUbicacion(ubicacion);
    }

    public void deleteUbicacion(Ubicacion ubicacion) {
        red.deleteUbicacion(ubicacion);
    }

    public void modifyUbicacion(Ubicacion ubicacion) {
        red.modifyUbicacion(ubicacion);
    }

    public void addTipoCable(TipoCable tipoCable) {
        red.addTipoCable(tipoCable);
    }

    public void deleteTipoCable(TipoCable tipoCable) {
        red.deleteTipoCable(tipoCable);
    }

    public void modifyTipoCable(TipoCable tipoCable) {
        red.modifyTipoCable(tipoCable);
    }

    public void addTipoPuerto(TipoPuerto TipoPuerto) {
        red.addTipoPuerto(TipoPuerto);
    }

    public void deleteTipoPuerto(TipoPuerto TipoPuerto) {
        red.deleteTipoPuerto(TipoPuerto);
    }

    public void modifyTipoPuerto(TipoPuerto TipoPuerto) {
        red.modifyTipoPuerto(TipoPuerto);
    }

    public void addTipoEquipo(TipoEquipo tipoEquipo) {
        red.addTipoEquipo(tipoEquipo);
    }

    public void deleteTipoEquipo(TipoEquipo tipoEquipo) {
        red.deleteTipoEquipo(tipoEquipo);
    }

    public void modifyTipoEquipo(TipoEquipo tipoEquipo) {
        red.modifyTipoEquipo(tipoEquipo);
    }


    public List<Conexion> shortestPath(Equipo equipo1, Equipo equipo2) {
        return logic.shortestPath(equipo1, equipo2);
    }

    public Double maxBandwith(List<Conexion> sPath) {
        return logic.maxBandwith(sPath);
    }

    public boolean ping(String ip) {
        return logic.ping(ip);
    }

    public Map<String, Boolean> pingRange(Collection<String> ips) {
        return logic.pingRange(ips);
    }

    public Map<Equipo, Boolean> mapStatus() {
        return logic.mapStatus();
    }

    //Red
    public List<Equipo> getEquipos() {
        return red.getEquipos();
    }

    public List<Conexion> getConexiones() {
        return red.getConexiones();
    }

    //Logic
    public Map<String, Equipo> getVertexMap() {
        Map<String, Equipo> vertexMap = new TreeMap<>();
        for (Equipo equipo : logic.getGraph().vertexSet()) {
            vertexMap.put(equipo.getCodigo(), equipo);
        }
        return vertexMap;
    }
}
