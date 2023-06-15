/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package viewClient;

import View.*;
import static View.DarklistManagerView.loadDarkList;
import static View.DarklistManagerView.tbMainViewDarkList;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import sftp.ConfiguracoesSFTPModel;
import static sftp.ConnectionFactory.downloadDarklistFile;
import sftp.SFTPConnection;

/**
 *
 * @author Eduardo.Fernando
 */
public class MenuFiles extends javax.swing.JFrame {

    public static File SelectedFile;
    UtilTable ut;
    Progresso P;

    public void testee() {
        // Criação do JFrame flutuante com o JProgressBar
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

        // Código do processo de carregamento (substitua pelo seu código real)
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(3000); // Simulação de um processo de 3 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Código real para carregar a lista
            // Fecha o JFrame flutuante após o carregamento
            progressFrame.dispose();

            // Restante do código após o carregamento
            // ...
        });
    }

    public void addTableMouseListener() {



        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {

                    int row = jTable1.getSelectedRow();
                    String data = jTable1.getValueAt(row, 0).toString();
                    String arquivo = jTable1.getValueAt(row, 1).toString();
  
                    
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

                            
             new Thread(){
             
             
             public void run(){
             
                try {
                        DefaultTableModel df = (DefaultTableModel) tbMainViewDarkList.getModel();
                        df.setNumRows(0);
                        downloadDarklistFile(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
                        SelectedFile = new File("C:\\teste\\Nova pasta\\spdark.lst");
                        progressFrame.setVisible(true);
                        
                       loadDarkList();
                       
                       progressFrame.setVisible(false);;
      

                    } catch (IOException ex) {
                        Logger.getLogger(MenuFiles.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(MenuFiles.class.getName()).log(Level.SEVERE, null, ex);
                    }
             
             
             
             
             }
             
             
             
             }.start();
                            
                 

                    dispose();
                    ut = UtilTable.getInstance();
                    ut.realizaAjuste(jTable1);

                }

            }
        });

    }

    public MenuFiles() throws Exception {
        initComponents();

        ConfiguracoesSFTPModel sd = new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22);
        SFTPConnection ser = new SFTPConnection(sd);

        ser.obterSessao();

        ser.Conexao();

        Map<String, String> fileMap = ser.getDarkList();

        SwingUtilities.invokeLater(() -> {

            DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();

            // Iterar pelo Map e adicionar os dados ao DefaultTableModel
            fileMap.forEach((data, coluna) -> tableModel.addRow(new Object[]{data, coluna}));

        });

        addTableMouseListener();
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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 290, 130));

        tbPane.addTab("Darklist Files", jPanel1);

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

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 290, 130));

        tbPane.addTab("Log Files", jPanel2);

        pnMain.add(tbPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 310, 180));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(MenuFiles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuFiles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuFiles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuFiles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MenuFiles().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(MenuFiles.class.getName()).log(Level.SEVERE, null, ex);
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
