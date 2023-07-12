/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package viewClientDarklist;

import com.formdev.flatlaf.FlatDarkLaf;
import controller.MainViewController;
import controller.MenuFileController;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Eduardo.Fernando
 */
public final class MenuFile extends javax.swing.JFrame {

    private final MainViewController MainController;
    private final MenuFileController MenuController;

    /**
     * Creates new form DarklistListFiles
     *
     * @throws java.lang.Exception
     */
    public MenuFile() throws Exception {

        initComponents();
UIManager.setLookAndFeel(new FlatDarkLaf());
        MainController = new MainViewController();
        MenuController = new MenuFileController();
        MenuController.tableListListener();
        MenuController.tableLogListener();

    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnMain = new javax.swing.JPanel();
        tbPane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableDatasDark = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbDataLog = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Selecion de Archivos");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        pnMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbPane.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbPanePropertyChange(evt);
            }
        });

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableDatasDark.setAutoCreateRowSorter(true);
        TableDatasDark.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Arquivo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableDatasDark.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableDatasDarkMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableDatasDark);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 440, 210));

        tbPane.addTab("Lista", jPanel1);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbDataLog.setAutoCreateRowSorter(true);
        tbDataLog.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Arquivo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbDataLog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbDataLogMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbDataLog);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 440, 210));

        tbPane.addTab("Log", jPanel2);

        pnMain.add(tbPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 260));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnMain, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbDataLogMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataLogMousePressed
       

        try {

            DarklistManagerViewClient.lblmode.setText("Log View");
            MainController.carregarLogAlteracoes();
            this.dispose();
          
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_tbDataLogMousePressed


    private void TableDatasDarkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableDatasDarkMouseClicked

        try {

            MenuController.inpecaoEventoCliqueList();
            DarklistManagerViewClient.btnView.setEnabled(true);
            this.dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_TableDatasDarkMouseClicked

    private void tbPanePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbPanePropertyChange

        // TODO add your handling code here:
    }//GEN-LAST:event_tbPanePropertyChange

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws UnsupportedLookAndFeelException {
 UIManager.setLookAndFeel(new FlatDarkLaf());
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MenuFile().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(MenuFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTable TableDatasDark;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnMain;
    public static javax.swing.JTable tbDataLog;
    public static javax.swing.JTabbedPane tbPane;
    // End of variables declaration//GEN-END:variables
}
