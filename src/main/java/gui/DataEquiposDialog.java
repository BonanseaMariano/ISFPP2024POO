package gui;

import controller.Coordinator;
import exceptions.InvalidEquipoException;
import models.Equipo;
import models.Puerto;
import models.TipoEquipo;
import models.Ubicacion;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A dialog for displaying and editing equipment data.
 * <p>
 * This class extends `javax.swing.JDialog` and provides a user interface for viewing and modifying
 * the details of an equipment (`Equipo`). It includes fields for the equipment's code, description,
 * brand, model, type, ports, IP addresses, and status.
 */
public class DataEquiposDialog extends javax.swing.JDialog {
    // Width of the dialog
    private static final int WIDTH_DIALOG = 400;
    // Height of the dialog
    private static final int HEIGHT_DIALOG = 400;
    // Checkbox for the equipment's status
    private javax.swing.JCheckBox EstadoCB;
    // Text field for the equipment's code
    private javax.swing.JTextField codigoTF;
    // Text field for the equipment's description
    private javax.swing.JTextField descripcionTF;
    // Text field for the equipment's brand
    private javax.swing.JTextField marcaTF;
    // Text field for the equipment's model
    private javax.swing.JTextField modeloTF;
    // Combo box for selecting the equipment's type
    private JComboBox<TipoEquipo> tipoEquipoCB;
    // Combo box for selecting the equipment's location
    private JComboBox<Ubicacion> ubicacionCB;
    // The coordinator responsible for managing the application's logic and data flow
    private final Coordinator coordinator;
    // The equipment object that holds the details of the equipment being managed
    private final Equipo oldEquipo;
    // The new equipment object that will hold the updated details or new ones of the equipment
    private Equipo newEquipo;
    // A flag that indicates whether the dialog is in editing mode
    private boolean isEditing = false;


    /**
     * Constructs a new DataEquiposDialog.
     * <p>
     * This constructor initializes the dialog with the given parent, modality, coordinator, and equipment.
     * It sets up the components, styles, and content of the dialog, and makes it visible.
     *
     * @param parent      the parent dialog of this dialog
     * @param modal       specifies whether dialog blocks user input to other top-level windows when shown
     * @param coordinator the coordinator responsible for managing the application's logic and data flow
     * @param equipo      the equipment object that holds the details of the equipment being managed
     */
    public DataEquiposDialog(java.awt.Dialog parent, boolean modal, Coordinator coordinator, Equipo equipo) {
        super(parent, modal);
        this.coordinator = coordinator;
        this.oldEquipo = equipo;
        this.newEquipo = new Equipo();
        initComponents();
        initStyle();
        initContent();
        this.setVisible(true);
    }

    /**
     * Initializes the style of the dialog.
     * <p>
     * This method sets the dialog's location to be centered relative to its parent,
     * sets the title of the dialog to "Datos del Equipo", and specifies the default close operation.
     */
    private void initStyle() {
        this.setLocationRelativeTo(null);
        this.setTitle("Datos del Equipo");
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Initializes the content of the dialog.
     * <p>
     * This method sets the fields with the equipment's data if the equipment is not null.
     * It copies the ports and IP addresses from the current equipment to the new equipment.
     * If the equipment is null, it creates a new equipment object.
     */
    private void initContent() {
        if (oldEquipo != null) { // If the equipment is not null, set the fields with the equipment's data
            codigoTF.setText(oldEquipo.getCodigo());
            codigoTF.setEditable(false);
            descripcionTF.setText(oldEquipo.getDescripcion());
            marcaTF.setText(oldEquipo.getMarca());
            modeloTF.setText(oldEquipo.getModelo());
            tipoEquipoCB.setSelectedItem(oldEquipo.getTipoEquipo());
            ubicacionCB.setSelectedItem(oldEquipo.getUbicacion());
            EstadoCB.setSelected(oldEquipo.isEstado());
            // Copy the ports and IP addresses from the current equipment to the new equipment
            for (Puerto p : oldEquipo.getPuertos()) {
                newEquipo.addPuerto(p);
            }
            // Copy the IP addresses from the current equipment to the new equipment
            for (String ip : oldEquipo.getDireccionesIp()) {
                newEquipo.addIP(ip);
            }
            isEditing = true;
        }
    }

    /**
     * Initializes the components of the dialog.
     * <p>
     * This method sets up the main panel, content panel, and various UI components such as text fields,
     * labels, buttons, and combo boxes. It also configures the layout and adds action listeners to the buttons.
     */
    private void initComponents() {
        // Main panel of the dialog
        JPanel bg = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margin between components

        // Initialize components
        codigoTF = new javax.swing.JTextField(15);
        descripcionTF = new javax.swing.JTextField(15);
        marcaTF = new javax.swing.JTextField(15);
        modeloTF = new javax.swing.JTextField(15);
        tipoEquipoCB = new JComboBox<>();
        ubicacionCB = new JComboBox<>();
        JToggleButton puertosBT = new JToggleButton("Puertos Equipo");
        JButton ipsBT = new JButton("Direcciones IP Equipo");
        EstadoCB = new javax.swing.JCheckBox();
        JLabel codigoLbl = new JLabel("Codigo:");
        JLabel descripcionLbl = new JLabel("Descripcion:");
        JLabel marcaLbl = new JLabel("Marca:");
        JLabel modeloLbl = new JLabel("Modelo:");
        JLabel tipoEquipoLbl = new JLabel("TipoEquipo:");
        JLabel ubicacionLbl = new JLabel("Ubicación:");
        JLabel puertosLbl = new JLabel("Puertos:");
        JLabel ipsLbl = new JLabel("Direcciones IP:");
        JLabel estadoLbl = new JLabel("Estado:");
        JButton aceptarBT = new JButton("Aceptar");
        JButton cancelarBT = new JButton("Cancelar");

        // Load the equipment types into the combo box
        List<TipoEquipo> tiposEquipos = coordinator.getTiposEquipos().values().stream().toList();
        DefaultComboBoxModel<TipoEquipo> comboBoxModel = new DefaultComboBoxModel<>();
        for (TipoEquipo tipoEquipo : tiposEquipos) {
            comboBoxModel.addElement(tipoEquipo);
        }
        tipoEquipoCB.setModel(comboBoxModel);

        // Load the locations into the combo box
        List<Ubicacion> ubicaciones = coordinator.getUbicaciones().values().stream().toList();
        DefaultComboBoxModel<Ubicacion> ubicacionModel = new DefaultComboBoxModel<>();
        for (Ubicacion ubicacion : ubicaciones) {
            ubicacionModel.addElement(ubicacion);
        }
        ubicacionCB.setModel(ubicacionModel);

        // Set custom renderers
        tipoEquipoCB.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof TipoEquipo) {
                    setText(((TipoEquipo) value).getCodigo());
                }
                return c;
            }
        });

        ubicacionCB.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Ubicacion) {
                    setText(((Ubicacion) value).getCodigo());
                }
                return c;
            }
        });

        // Add components to bg with GridBagConstraints
        gbc.anchor = GridBagConstraints.WEST;

        // Row 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        bg.add(codigoLbl, gbc);
        gbc.gridx = 1;
        bg.add(codigoTF, gbc);

        // Row 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        bg.add(descripcionLbl, gbc);
        gbc.gridx = 1;
        bg.add(descripcionTF, gbc);

        // Row 3
        gbc.gridx = 0;
        gbc.gridy = 2;
        bg.add(marcaLbl, gbc);
        gbc.gridx = 1;
        bg.add(marcaTF, gbc);

        // Row 4
        gbc.gridx = 0;
        gbc.gridy = 3;
        bg.add(modeloLbl, gbc);
        gbc.gridx = 1;
        bg.add(modeloTF, gbc);

        // Row 5
        gbc.gridx = 0;
        gbc.gridy = 4;
        bg.add(tipoEquipoLbl, gbc);
        gbc.gridx = 1;
        bg.add(tipoEquipoCB, gbc);

        // Row 6
        gbc.gridx = 0;
        gbc.gridy = 5;
        bg.add(ubicacionLbl, gbc);
        gbc.gridx = 1;
        bg.add(ubicacionCB, gbc);

        // Row 7
        gbc.gridx = 0;
        gbc.gridy = 6;
        bg.add(puertosLbl, gbc);
        gbc.gridx = 1;
        bg.add(puertosBT, gbc);

        // Row 8
        gbc.gridx = 0;
        gbc.gridy = 7;
        bg.add(ipsLbl, gbc);
        gbc.gridx = 1;
        bg.add(ipsBT, gbc);

        // Row 9
        gbc.gridx = 0;
        gbc.gridy = 8;
        bg.add(estadoLbl, gbc);
        gbc.gridx = 1;
        bg.add(EstadoCB, gbc);

        // Row 10 - Buttons
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(aceptarBT);
        buttonPanel.add(cancelarBT);
        bg.add(buttonPanel, gbc);

        // Set layout for the dialog
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        // Set the preferred size of the dialog
        setPreferredSize(new java.awt.Dimension(WIDTH_DIALOG, HEIGHT_DIALOG));

        pack();

        // Add action listeners
        puertosBT.addActionListener(e -> puertosBTActionPerformed());
        ipsBT.addActionListener(e -> ipsBTActionPerformed());
        aceptarBT.addActionListener(e -> aceptarBTActionPerformed());
        cancelarBT.addActionListener(e -> cancelarBTActionPerformed());
    }

    /**
     * Handles the action event for the accept button.
     * <p>
     * This method sets the properties of the new equipment object based on the input fields.
     * If the dialog is in editing mode, it attempts to modify the existing equipment.
     * Otherwise, it attempts to add a new equipment. In both cases, it handles any
     * `InvalidEquipoException` that may be thrown and displays an error message.
     * Finally, it closes the dialog.
     */
    private void aceptarBTActionPerformed() {
        newEquipo.setCodigo(codigoTF.getText());
        newEquipo.setDescripcion(descripcionTF.getText());
        newEquipo.setMarca(marcaTF.getText());
        newEquipo.setModelo(modeloTF.getText());
        newEquipo.setTipoEquipo((TipoEquipo) tipoEquipoCB.getSelectedItem());
        newEquipo.setUbicacion((Ubicacion) ubicacionCB.getSelectedItem());
        newEquipo.setEstado(EstadoCB.isSelected());

        if (isEditing) {
            try {
                coordinator.modifyEquipo(oldEquipo, newEquipo);
            } catch (InvalidEquipoException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                this.dispose();
            }
            JOptionPane.showMessageDialog(this, "Equipo modificado correctamente", "Equipo modificado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                coordinator.addEquipo(newEquipo);
            } catch (InvalidEquipoException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                this.dispose();
            }
            JOptionPane.showMessageDialog(this, "Equipo agregado correctamente", "Equipo agregado", JOptionPane.INFORMATION_MESSAGE);
        }
        this.dispose();
    }

    /**
     * Handles the action event for the cancel button.
     * <p>
     * This method closes the dialog when the cancel button is pressed.
     */
    private void cancelarBTActionPerformed() {
        this.dispose();
    }

    /**
     * Handles the action event for the ports button.
     * <p>
     * This method opens a new dialog for managing the equipment's ports.
     */
    private void puertosBTActionPerformed() {
        new TablePuertosEquipoDialog(this, true, coordinator, newEquipo);
    }

    /**
     * Handles the action event for the IP addresses button.
     * <p>
     * This method opens a new dialog for managing the equipment's IP addresses.
     */
    private void ipsBTActionPerformed() {
        new TableIpsEquipoDialog(this, true, coordinator, newEquipo);
    }

}
