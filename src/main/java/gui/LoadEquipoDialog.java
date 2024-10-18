package gui;

import controller.Coordinator;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadEquipoDialog extends JDialog {
    private JTextField codigoField;
    private JTextField descripcionField;
    private JTextField marcaField;
    private JTextField modeloField;
    private JComboBox<TipoEquipo> tipoEquipoComboBox;
    private JComboBox<Ubicacion> ubicacionComboBox;
    private JTextField cantidadPuertosField;
    private JComboBox<TipoPuerto> tipoPuertoComboBox;
    private JTextField direccionIpField;
    private JCheckBox estadoCheckBox;
    private JButton agregarButton;
    private JButton cancelarButton;
    private Coordinator coordinator;

    public LoadEquipoDialog(JFrame parent) {
        super(parent, true); // Set the dialog to be modal
        initComponents();
        layoutComponents();
        addEventHandlers();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        codigoField = new JTextField(20);
        descripcionField = new JTextField(20);
        marcaField = new JTextField(20);
        modeloField = new JTextField(20);
        tipoEquipoComboBox = new JComboBox<>();
        ubicacionComboBox = new JComboBox<>();
        cantidadPuertosField = new JTextField(20);
        tipoPuertoComboBox = new JComboBox<>();
        direccionIpField = new JTextField(20);
        estadoCheckBox = new JCheckBox("Estado");
        agregarButton = new JButton("Agregar");
        cancelarButton = new JButton("Cancelar");
    }

    private void layoutComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Código:"), gbc);

        gbc.gridx = 1;
        panel.add(codigoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Descripción:"), gbc);

        gbc.gridx = 1;
        panel.add(descripcionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Marca:"), gbc);

        gbc.gridx = 1;
        panel.add(marcaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Modelo:"), gbc);

        gbc.gridx = 1;
        panel.add(modeloField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Tipo de Equipo:"), gbc);

        gbc.gridx = 1;
        panel.add(tipoEquipoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Ubicación:"), gbc);

        gbc.gridx = 1;
        panel.add(ubicacionComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Cantidad de Puertos:"), gbc);

        gbc.gridx = 1;
        panel.add(cantidadPuertosField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Tipo de Puerto:"), gbc);

        gbc.gridx = 1;
        panel.add(tipoPuertoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(new JLabel("Dirección IP:"), gbc);

        gbc.gridx = 1;
        panel.add(direccionIpField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(estadoCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(agregarButton, gbc);

        gbc.gridx = 1;
        panel.add(cancelarButton, gbc);

        add(panel);
    }

    private void addEventHandlers() {
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para agregar el equipo
                String codigo = codigoField.getText();
                String descripcion = descripcionField.getText();
                String marca = marcaField.getText();
                String modelo = modeloField.getText();
                TipoEquipo tipoEquipo = (TipoEquipo) tipoEquipoComboBox.getSelectedItem();
                Ubicacion ubicacion = (Ubicacion) ubicacionComboBox.getSelectedItem();
                int cantidadPuertos = Integer.parseInt(cantidadPuertosField.getText());
                TipoPuerto tipoPuerto = (TipoPuerto) tipoPuertoComboBox.getSelectedItem();
                String direccionIp = direccionIpField.getText();
                boolean estado = estadoCheckBox.isSelected();
                // Aquí puedes agregar la lógica para manejar los datos del equipo
                dispose();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        // Simulación de datos para los JComboBox
        Coordinator coordinator = new Coordinator(); // Assuming Coordinator is properly initialized

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoadEquipoDialog dialog = new LoadEquipoDialog(null);
                dialog.setVisible(true);
            }
        });
    }

    protected void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }
}