package gui;

import controller.Coordinator;
import exceptions.InvalidEquipoException;
import models.Equipo;
import models.Puerto;
import models.TipoEquipo;
import models.Ubicacion;
import observer.Observer;
import observer.Subject;
import utils.LoggerUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * DataEquiposDialog is a dialog for adding or editing equipment data.
 * <p>
 * This dialog allows the user to input the details of an equipment, such as the code, description, brand, model,
 * type, location, and status. It provides fields for entering the data and buttons for accepting or canceling the operation.
 * The dialog is used to add a new equipment or modify an existing one. It also notifies observers of the changes made.
 */
public class DataEquiposDialog extends javax.swing.JDialog implements Subject {
    /**
     * Width of the dialog
     */
    private static final int WIDTH_DIALOG = 400;
    /**
     * Height of the dialog
     */
    private static final int HEIGHT_DIALOG = 400;
    /**
     * Checkbox for the equipment's status
     */
    private javax.swing.JCheckBox EstadoCB;
    /**
     * Text field for the equipment's code
     */
    private javax.swing.JTextField codigoTF;
    /**
     * Text field for the equipment's description
     */
    private javax.swing.JTextField descripcionTF;
    /**
     * Text field for the equipment's brand
     */
    private javax.swing.JTextField marcaTF;
    /**
     * Text field for the equipment's model
     */
    private javax.swing.JTextField modeloTF;
    /**
     * Combo box for selecting the equipment's type
     */
    private JComboBox<TipoEquipo> tipoEquipoCB;
    /**
     * Combo box for selecting the equipment's location
     */
    private JComboBox<Ubicacion> ubicacionCB;
    /**
     * List of observers registered with the subject
     */
    private final List<Observer> observers = new ArrayList<>();
    /**
     * The coordinator responsible for managing the application's logic and data flow
     */
    private final Coordinator coordinator;
    /**
     * Resource bundle for internationalization
     */
    private ResourceBundle rb;
    /**
     * The equipment object that holds the details of the equipment being managed
     */
    private final Equipo equipo;
    /**
     * A flag that indicates whether the dialog is in editing mode
     */
    private boolean isEditing;

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
        this.rb = coordinator.getResourceBundle();
        if (equipo != null) {
            this.equipo = equipo;
            isEditing = true;
        } else {
            this.equipo = new Equipo();
            isEditing = false;
        }
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
        this.setTitle(rb.getString("TableEquipos_dataTitle"));
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
        if (isEditing) { // If the dialog is in editing mode
            codigoTF.setText(equipo.getCodigo());
            codigoTF.setEnabled(false); // Disable the code field
            descripcionTF.setText(equipo.getDescripcion());
            marcaTF.setText(equipo.getMarca());
            modeloTF.setText(equipo.getModelo());
            tipoEquipoCB.setSelectedItem(equipo.getTipoEquipo());
            ubicacionCB.setSelectedItem(equipo.getUbicacion());
            EstadoCB.setSelected(equipo.isEstado());
        } else {
            codigoTF.setEnabled(true); // Enable the code field
        }
    }

    /**
     * Initializes the components of the dialog.
     * <p>
     * This method sets up the main panel, content panel, and various UI components such as text fields,
     * labels, buttons, and combo boxes. It also configures the layout and adds action listeners to the buttons.
     */
    private void initComponents() {
        // Panel principal del diálogo
        JPanel bg = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margen entre componentes

        // Inicializar componentes
        codigoTF = new javax.swing.JTextField(15);
        descripcionTF = new javax.swing.JTextField(15);
        marcaTF = new javax.swing.JTextField(15);
        modeloTF = new javax.swing.JTextField(15);
        tipoEquipoCB = new JComboBox<>();
        ubicacionCB = new JComboBox<>();
        EstadoCB = new javax.swing.JCheckBox();
        JLabel codigoLbl = new JLabel(rb.getString("TableDialog_idColumn") + ":");
        JLabel descripcionLbl = new JLabel(rb.getString("TableDialog_descriptionColumn") + ":");
        JLabel marcaLbl = new JLabel(rb.getString("TableEquipos_brandColumn") + ":");
        JLabel modeloLbl = new JLabel(rb.getString("TableEquipos_modelColumn") + ":");
        JLabel tipoEquipoLbl = new JLabel(rb.getString("TableEquipos_deviceTypeColumn") + ":");
        JLabel ubicacionLbl = new JLabel(rb.getString("TableEquipos_locationColumn") + ":");
        JLabel estadoLbl = new JLabel(rb.getString("TableEquipos_statusColumn") + ":");
        JButton aceptarBT = new JButton(rb.getString("TableDialog_acceptButton"));
        JButton cancelarBT = new JButton(rb.getString("TableDialog_cancelButton"));

        // Cargar los tipos de equipos en el combo box
        List<TipoEquipo> tiposEquipos = coordinator.getTiposEquipos().values().stream().toList();
        DefaultComboBoxModel<TipoEquipo> comboBoxModel = new DefaultComboBoxModel<>();
        for (TipoEquipo tipoEquipo : tiposEquipos) {
            comboBoxModel.addElement(tipoEquipo);
        }
        tipoEquipoCB.setModel(comboBoxModel);

        // Cargar las ubicaciones en el combo box
        List<Ubicacion> ubicaciones = coordinator.getUbicaciones().values().stream().toList();
        DefaultComboBoxModel<Ubicacion> ubicacionModel = new DefaultComboBoxModel<>();
        for (Ubicacion ubicacion : ubicaciones) {
            ubicacionModel.addElement(ubicacion);
        }
        ubicacionCB.setModel(ubicacionModel);

        // Establecer renderizadores personalizados
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

        // Añadir componentes a bg con GridBagConstraints
        gbc.anchor = GridBagConstraints.WEST;

        // Fila 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        bg.add(codigoLbl, gbc);
        gbc.gridx = 1;
        bg.add(codigoTF, gbc);

        // Fila 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        bg.add(descripcionLbl, gbc);
        gbc.gridx = 1;
        bg.add(descripcionTF, gbc);

        // Fila 3
        gbc.gridx = 0;
        gbc.gridy = 2;
        bg.add(marcaLbl, gbc);
        gbc.gridx = 1;
        bg.add(marcaTF, gbc);

        // Fila 4
        gbc.gridx = 0;
        gbc.gridy = 3;
        bg.add(modeloLbl, gbc);
        gbc.gridx = 1;
        bg.add(modeloTF, gbc);

        // Fila 5
        gbc.gridx = 0;
        gbc.gridy = 4;
        bg.add(tipoEquipoLbl, gbc);
        gbc.gridx = 1;
        bg.add(tipoEquipoCB, gbc);

        // Fila 6
        gbc.gridx = 0;
        gbc.gridy = 5;
        bg.add(ubicacionLbl, gbc);
        gbc.gridx = 1;
        bg.add(ubicacionCB, gbc);

        // Fila 7
        gbc.gridx = 0;
        gbc.gridy = 6;
        bg.add(estadoLbl, gbc);
        gbc.gridx = 1;
        bg.add(EstadoCB, gbc);

        // Fila 8 - Botones
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(aceptarBT);
        buttonPanel.add(cancelarBT);
        bg.add(buttonPanel, gbc);

        // Establecer el layout para el diálogo
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

        // Establecer el tamaño preferido del diálogo
        setPreferredSize(new java.awt.Dimension(WIDTH_DIALOG, HEIGHT_DIALOG));

        pack();

        // Añadir action listeners
        aceptarBT.addActionListener(_ -> aceptarBTActionPerformed());
        cancelarBT.addActionListener(_ -> cancelarBTActionPerformed());
    }

    /**
     * Handles the action event for the accept button.
     * <p>
     * This method collects the data from the input fields, validates it, and either adds a new equipment
     * or modifies an existing one. It displays appropriate messages for success or error.
     */
    private void aceptarBTActionPerformed() {
        // Validate that the code and description fields are not empty
        if (codigoTF.getText().isEmpty() || descripcionTF.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, rb.getString("TableDialog_allFieldsRequired"), rb.getString("TableDialog_error"), JOptionPane.ERROR_MESSAGE);
            this.dispose();
            return;
        }

        Equipo newEquipo = new Equipo(); // Create a new equipment object
        // Set the new equipment's details from the input fields
        newEquipo.setCodigo(codigoTF.getText());
        newEquipo.setDescripcion(descripcionTF.getText());
        newEquipo.setMarca(marcaTF.getText());
        newEquipo.setModelo(modeloTF.getText());
        newEquipo.setTipoEquipo((TipoEquipo) tipoEquipoCB.getSelectedItem());
        newEquipo.setUbicacion((Ubicacion) ubicacionCB.getSelectedItem());
        newEquipo.setEstado(EstadoCB.isSelected());

        // Copy the ports and IP addresses from the current equipment to the new equipment
        for (Puerto puerto : equipo.getPuertos()) {
            newEquipo.addPuerto(puerto);
        }
        for (String ip : equipo.getDireccionesIp()) {
            newEquipo.addIP(ip);
        }

        if (!isEditing) {
            // Add the new equipment
            try {
                coordinator.addEquipo(newEquipo);
            } catch (InvalidEquipoException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), rb.getString("TableDialog_error"), JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, rb.getString("TableEquipos_addedSuccess"), rb.getString("TableEquipos_addTitle"), JOptionPane.INFORMATION_MESSAGE);

        } else {
            // Modify the old equipment
            try {
                coordinator.modifyEquipo(newEquipo);
            } catch (InvalidEquipoException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), rb.getString("TableDialog_error"), JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, rb.getString("TableEquipos_modifiedSuccess"), rb.getString("TableEquipos_modifyTitle"), JOptionPane.INFORMATION_MESSAGE);
        }
        notifyObservers(newEquipo); // Notify observers of the change
        // Close the dialog
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
     * Adds an observer to the list of observers.
     *
     * @param observer the observer to add
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the list of observers.
     *
     * @param observer the observer to remove
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers of the change.
     *
     * @param equipo the equipment object that holds the details of the equipment being managed
     */
    @Override
    public void notifyObservers(Equipo equipo) {
        SwingUtilities.invokeLater(() -> {
            for (Observer observer : observers) {
                observer.update(equipo);
            }
        });
    }
}
