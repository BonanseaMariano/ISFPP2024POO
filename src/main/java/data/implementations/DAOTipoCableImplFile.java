package data.implementations;

import data.interfaces.DAOTipoCable;
import models.TipoCable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static utils.Constatnts.DELIMITER;

public class DAOTipoCableImplFile implements DAOTipoCable {
    private final String filename;
    private List<TipoCable> list;
    private boolean actualizar;

    public DAOTipoCableImplFile() {
        ResourceBundle rb = ResourceBundle.getBundle("config");
        filename = rb.getString("tiposCables");
        actualizar = true;
    }

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

    @Override
    public void create(TipoCable tipoCable) {
        list.add(tipoCable);
        writeToFile(list, filename);
        actualizar = true;
    }

    @Override
    public List<TipoCable> read() {
        if (actualizar) {
            list = readFromFile(filename);
            actualizar = false;
        }
        return list;
    }

    @Override
    public void update(TipoCable tipoCable) {
        int pos = list.indexOf(tipoCable);
        list.set(pos, tipoCable);
        writeToFile(list, filename);
        actualizar = true;
    }

    @Override
    public void delete(TipoCable tipoCable) {
        list.remove(tipoCable);
        writeToFile(list, filename);
        actualizar = true;
    }
}
