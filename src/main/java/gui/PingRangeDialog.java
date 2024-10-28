/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package gui;

import controller.Coordinator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author lucia
 */
public class PingRangeDialog extends javax.swing.JDialog {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton;
    private javax.swing.JComboBox<String> jComboBoxPing2;
    private javax.swing.JComboBox<String> jComboBoxPing1;
    private javax.swing.JLabel jTitlePing1;
    private javax.swing.JLabel jTitlePing2;
    private javax.swing.JPanel jPanelBG;
    private javax.swing.JPanel jPanelBox;
    // End of variables declaration//GEN-END:variables

    private Coordinator coordinator;
    /**
     * Creates new form TraceRoute
     */
    public PingRangeDialog(java.awt.Frame parent, boolean modal, Coordinator coordinator) {
        super(parent, modal);
        this.coordinator = coordinator;
        initComponents();
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
        jTitlePing1 = new javax.swing.JLabel();
        jTitlePing2 = new javax.swing.JLabel();
        jComboBoxPing2 = new javax.swing.JComboBox<>();
        jComboBoxPing1 = new javax.swing.JComboBox<>();
        jButton = new javax.swing.JButton();
        String[] ips = coordinator.getEquiposIps();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ping Range");

        jPanelBG.setVerifyInputWhenFocusTarget(false);

        jPanelBox.setLayout(new java.awt.GridBagLayout());

        jTitlePing1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTitlePing1.setText("Ping 1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 65;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(85, 45, 0, 0);
        jPanelBox.add(jTitlePing1, gridBagConstraints);

        jTitlePing2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTitlePing2.setText("Ping 2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 65;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(85, 10, 0, 33);
        jPanelBox.add(jTitlePing2, gridBagConstraints);

        jComboBoxPing2.setModel(new javax.swing.DefaultComboBoxModel<>(ips));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 10, 0, 33);
        jPanelBox.add(jComboBoxPing2, gridBagConstraints);

        jComboBoxPing1.setModel(new javax.swing.DefaultComboBoxModel<>(ips));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 45, 0, 0);
        jPanelBox.add(jComboBoxPing1, gridBagConstraints);

        jButton.setText("Hacer Ping");
        jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rangePings(evt);
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

    private void rangePings(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String pingRangeText = "El estado de los pings es de: ";
        String ip1 = (String) jComboBoxPing1.getSelectedItem();
        String ip2 = (String) jComboBoxPing2.getSelectedItem();
        Collection<String> ipsCollection = new ArrayList<>();

        ipsCollection.add(ip1);
        if (!ip1.equals(ip2)) {
            for(String ip : coordinator.getEquiposIps()) {
                if (ip.compareTo(ip1) > 0 && ip.compareTo(ip2) < 0) {
                    ipsCollection.add(ip);
                }
            }

            ipsCollection.add(ip2);
        }

        for(Map.Entry   <String, Boolean> entry : coordinator.pingRange(ipsCollection).entrySet()) {
            pingRangeText += "\n" + entry.getKey() + ": " + (entry.getValue() ? "Conectado" : "Desconectado");
        }

        JOptionPane.showMessageDialog(
                null,
                pingRangeText,
                "Ping Range Status",
                JOptionPane.INFORMATION_MESSAGE
        );

    }//GEN-LAST:event_jButton1ActionPerformed
}
