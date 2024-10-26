/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package gui;

import controller.Coordinator;

import javax.swing.*;

/**
 *
 * @author lucia
 */
public class PingDialog extends javax.swing.JDialog {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonPing;
    private javax.swing.JComboBox<String> jComboBox;
    private javax.swing.JLabel tituloJ;
    private javax.swing.JPanel jPanelBG;
    private javax.swing.JPanel jPanelBox;
    private Coordinator coordinator;
    // End of variables declaration//GEN-END:variables

    /**
     * Creates new form Pin
     */
    public PingDialog(java.awt.Frame parent, boolean modal, Coordinator coordinator) {
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
        tituloJ = new javax.swing.JLabel();
        jComboBox = new javax.swing.JComboBox<>();
        jButtonPing = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ping Status");

        jPanelBG.setName("bg"); // NOI18N

        jPanelBox.setLayout(new java.awt.GridBagLayout());

        tituloJ.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloJ.setText("Equipo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 212;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(89, 72, 0, 78);
        jPanelBox.add(tituloJ, gridBagConstraints);

        jComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(coordinator.getEquiposIps()));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 174;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 72, 0, 78);
        jPanelBox.add(jComboBox, gridBagConstraints);

        jButtonPing.setText("Realizar Ping");
        jButtonPing.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonPing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String conexionText = "El equipo " + jComboBox.getSelectedItem().toString() + " se encuentra: ";

                if(coordinator.ping(jComboBox.getSelectedItem().toString())) {
                    conexionText += "Conectado";
                }else{
                    conexionText += "Desconectado";
                }
                JOptionPane.showMessageDialog(
                        null,
                        conexionText,
                        "Ping Status",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 139, 112, 0);
        jPanelBox.add(jButtonPing, gridBagConstraints);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanelBG);
        jPanelBG.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanelBG, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initStyles() {
        this.setLocationRelativeTo(null);
    }

    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
       /* try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PingDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PingDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PingDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PingDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PingDialog dialog = new PingDialog(new javax.swing.JFrame(), true,);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }*/

}
