package data.implementations;

import data.interfaces.DAOTipoPuerto;
import models.TipoPuerto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static utils.Constatnts.DELIMITER;

public class DAOTipoPuertoImplFile implements DAOTipoPuerto {
    private final String filename;
    private List<TipoPuerto> list;
    private boolean actualizar;

    public DAOTipoPuertoImplFile() {
        ResourceBundle rb = ResourceBundle.getBundle("config");
        filename = rb.getString("tiposPuertos");
        actualizar = true;
    }

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

    @Override
    public void create(TipoPuerto tipoPuerto) {
        list.add(tipoPuerto);
        writeToFile(list, filename);
        actualizar = true;
    }

    @Override
    public List<TipoPuerto> read() {
        if (actualizar) {
            list = readFromFile(filename);
            actualizar = false;
        }
        return list;
    }

    @Override
    public void update(TipoPuerto tipoPuerto) {
        int pos = list.indexOf(tipoPuerto);
        list.set(pos, tipoPuerto);
        writeToFile(list, filename);
        actualizar = true;
    }

    @Override
    public void delete(TipoPuerto tipoPuerto) {
        list.remove(tipoPuerto);
        writeToFile(list, filename);
        actualizar = true;
    }
}
