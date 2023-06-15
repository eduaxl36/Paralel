/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package viewClient;

import View.*;
import java.awt.BorderLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import sftp.ConfiguracoesSFTPModel;
import static sftp.ConnectionFactory.downloadDarklistFile;
import static sftp.ConnectionFactory.downloadLoglistFile;
import sftp.SFTPConnection;
import static viewClient.DarklistManagerViewClient.tbMainViewDarkList;
import static viewClient.Visualize.loadDarkListEditMode;

/**
 *
 * @author Eduardo.Fernando
 */
public class MenuFile extends javax.swing.JFrame {
    
    public static File SelectedFile;
    UtilTable ut;

    /**
     * Creates new form DarklistListFiles
     */
    public void addTableMouseListenerLog() {
        jTable2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                
                try {
                    DefaultTableModel df = (DefaultTableModel) tbMainViewDarkList.getModel();
                    df.setNumRows(0);
                    int row = jTable2.getSelectedRow();
                    String data = jTable2.getValueAt(row, 0).toString();
                    String arquivo = jTable2.getValueAt(row, 1).toString();
                    JFrame progressFrame = new JFrame() {
                        {
                            // Configurações do JFrame
                            setTitle("Aguardando processo");
                            setSize(300, 100);
                            setUndecorated(true);
                            setLocationRelativeTo(null);

                            // Criação do JProgressBar
                            JProgressBar progressBar = new JProgressBar();
                            progressBar.setIndeterminate(true); // Configura o modo indeterminado

                            // Adiciona o JProgressBar ao JFrame
                            getContentPane().add(progressBar, BorderLayout.CENTER);

                            // Configura o comportamento ao fechar o JFrame
                            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                            // Exibe o JFrame
                            setVisible(true);
                        }
                    };
                    
                    new Thread() {
                        
                        public void run() {
                            
                            try {
                                
                                progressFrame.setVisible(true);
                                downloadLoglistFile(jTable2.getValueAt(jTable2.getSelectedRow(), 1).toString());
                                SelectedFile = new File("C:\\teste\\Nova pasta\\" + jTable2.getValueAt(jTable2.getSelectedRow(), 1));
                                DarklistManagerViewClient.loadLog();
                                DarklistManagerViewClient.lblmode.setText("Log View");
                                DarklistManagerViewClient.lblDtProd.setText(arquivo.substring(0, 8));
                                progressFrame.setVisible(false);
                            } catch (Exception ex) {
                                Logger.getLogger(MenuFile.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        }
                        
                    }.start();
                    
                } catch (Exception ex) {
                    Logger.getLogger(MenuFile.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                dispose();
                ut = UtilTable.getInstance();
                ut.realizaAjuste(jTable1);
                
            }
        });
    }
    
    public void addTableMouseListener() {

//        
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                
                int row = jTable1.getSelectedRow();
                String data = jTable1.getValueAt(row, 0).toString();
                String arquivo = jTable1.getValueAt(row, 1).toString();
                
                String dataP = DarklistManagerViewClient.lblDtProd.getText();
                DarklistManagerViewClient.lblmode.setText("Darklist");
                
                if (!(data.equals(dataP))) {
                    
                    int resposta = JOptionPane.showConfirmDialog(null, "Para el historico del dark, esta disponible solo para visualizacion!, quiere abrir ?",
                            "Confirmacion",
                            JOptionPane.YES_OPTION);
                    
                    if (resposta == JOptionPane.YES_OPTION) {
                        
                        try {
                            
                            new Visualize().setVisible(true);
                            loadDarkListEditMode(data, arquivo);
                            
                        } catch (Exception ex) {
                            Logger.getLogger(MenuFile.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                } else {
                    
                    try {
                        DefaultTableModel df = (DefaultTableModel) tbMainViewDarkList.getModel();
                        df.setNumRows(0);
                        
                        JFrame progressFrame = new JFrame() {
                            {
                                // Configurações do JFrame
                                setTitle("Aguardando processo");
                                setSize(300, 100);
                                setUndecorated(true);
                                setLocationRelativeTo(null);

                                // Criação do JProgressBar
                                JProgressBar progressBar = new JProgressBar();
                                progressBar.setIndeterminate(true); // Configura o modo indeterminado

                                // Adiciona o JProgressBar ao JFrame
                                getContentPane().add(progressBar, BorderLayout.CENTER);

                                // Configura o comportamento ao fechar o JFrame
                                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                                // Exibe o JFrame
                                setVisible(true);
                            }
                        };
                        
                        new Thread() {
                            
                            public void run() {
                                
                                try {
                                    
                                    progressFrame.setVisible(true);
                                    downloadDarklistFile(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
                                    SelectedFile = new File("C:\\teste\\Nova pasta\\spdark.lst");
                                    DarklistManagerViewClient.loadDarkList();
                                    
                                    progressFrame.setVisible(false);
                                } catch (Exception ex) {
                                    Logger.getLogger(MenuFile.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                            }
                            
                        }.start();
                        
                    } catch (Exception ex) {
                        Logger.getLogger(MenuFile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    dispose();
                    ut = UtilTable.getInstance();
                    ut.realizaAjuste(jTable1);
                    
                }
                
            }
        });
        
    }
    
    public MenuFile() throws Exception {
        initComponents();
        
        ConfiguracoesSFTPModel sd = new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22);
        SFTPConnection ser = new SFTPConnection(sd);
        
        ser.obterSessao();
        
        ser.Conexao();
        
        Map<String, String> fileMap = ser.getDarkList();
        
        SwingUtilities.invokeLater(() -> {
            
            DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
            
            fileMap.forEach((data, coluna) -> tableModel.addRow(new Object[]{data, coluna}));
            
        });
        
        Map<String, String> fileMapLog = ser.getLog();
        
        SwingUtilities.invokeLater(() -> {
            
            DefaultTableModel tableModel = (DefaultTableModel) jTable2.getModel();
            
            fileMapLog.forEach((data, coluna) -> tableModel.addRow(new Object[]{data, coluna}));
            
        });
        
        addTableMouseListener();
        addTableMouseListenerLog();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnMain = new javax.swing.JPanel();
        tbPane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        pnMain.setBackground(new java.awt.Color(255, 255, 255));
        pnMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 440, 210));

        tbPane.addTab("Darklist's", jPanel1);

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable2.setAutoCreateRowSorter(true);
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 440, 210));

        tbPane.addTab("Alterações", jPanel2);

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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JPanel pnMain;
    private javax.swing.JTabbedPane tbPane;
    // End of variables declaration//GEN-END:variables
}
