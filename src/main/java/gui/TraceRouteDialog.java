/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package gui;

import controller.Coordinator;
import models.Conexion;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author lucia
 */
public class TraceRouteDialog extends javax.swing.JDialog {


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton;
    private javax.swing.JComboBox<String> jComboBoxEquipo2;
    private javax.swing.JComboBox<String> jComboBoxEquipo1;
    private javax.swing.JLabel titleEquipo1;
    private javax.swing.JLabel titleEquipo2;
    private javax.swing.JPanel jPanelBG;
    private javax.swing.JPanel jPanelBox;
    // End of variables declaration//GEN-END:variables

    private Coordinator coordinator;
    /**
     * Creates new form TraceRoute
     */
    public TraceRouteDialog(java.awt.Frame parent, boolean modal, Coordinator coordinator) {
        super(parent, modal);
        this.coordinator = coordinator;
        initComponents();
        initStyles();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelBG = new javax.swing.JPanel();
        jPanelBox = new javax.swing.JPanel();
        titleEquipo1 = new javax.swing.JLabel();
        titleEquipo2 = new javax.swing.JLabel();
        jComboBoxEquipo2 = new javax.swing.JComboBox<>();
        jComboBoxEquipo1 = new javax.swing.JComboBox<>();
        jButton = new javax.swing.JButton();
        String [] equipos = coordinator.getEquiposKeys();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("TraceRoute");

        jPanelBG.setVerifyInputWhenFocusTarget(false);

        jPanelBox.setLayout(new java.awt.GridBagLayout());

        titleEquipo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleEquipo1.setText("Equipo 1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 65;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(85, 45, 0, 0);
        jPanelBox.add(titleEquipo1, gridBagConstraints);

        titleEquipo2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleEquipo2.setText("Equipo 2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 65;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(85, 10, 0, 33);
        jPanelBox.add(titleEquipo2, gridBagConstraints);

        jComboBoxEquipo2.setModel(new javax.swing.DefaultComboBoxModel<>(equipos));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 10, 0, 33);
        jPanelBox.add(jComboBoxEquipo2, gridBagConstraints);

        jComboBoxEquipo1.setModel(new javax.swing.DefaultComboBoxModel<>(equipos));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 45, 0, 0);
        jPanelBox.add(jComboBoxEquipo1, gridBagConstraints);

        jButton.setText("Buscar");
        jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                traceRoute(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 9, 110, 0);
        jPanelBox.add(jButton, gridBagConstraints);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanelBG);
        jPanelBG.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanelBG, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void traceRoute(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String routeText = "La ruta mas corta entre los equipos " + jComboBoxEquipo1.getSelectedItem().toString() + " y " + jComboBoxEquipo2.getSelectedItem().toString() + " es:\n";
        try {
            List<Conexion> listConexionaux = coordinator.shortestPath(coordinator.getEquipo(jComboBoxEquipo1.getSelectedItem().toString()),coordinator.getEquipo(jComboBoxEquipo2.getSelectedItem().toString()));
            for (Conexion c : listConexionaux) {
                routeText += c.getEquipo1().getCodigo() +" -> "+ c.getEquipo2().getCodigo() + "\n";
            }

            routeText += "\n y su velocidad maxima es de: " + coordinator.maxBandwith(listConexionaux).toString() + " MB";
            JOptionPane.showMessageDialog(
                    null,
                    routeText,
                    "Ruta mas corta",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "No existe una ruta entre ambos equipos",
                    "Ruta mas corta",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

    }//GEN-LAST:event_jButton1ActionPerformed


    private void initStyles() {
        this.setLocationRelativeTo(null);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
}
