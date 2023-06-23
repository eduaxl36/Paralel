/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package viewClient;

import flag.CambioFlagView;
import Util.MainTableUtil;
import Util.Util;
import br.com.kantar.pathManager.Manager;
import br.com.kantar.sftp.FlagOperations;
import static br.com.kantar.sftp.FlagOperations.Flags;
import br.com.kantar.sftp.SFTPOperations;
import com.formdev.flatlaf.FlatLightLaf;
import dao.DarklistDao1;
import dao.LogDao;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.apache.commons.io.FileUtils;
import sftp.ConfiguracoesSFTPModel;
import sftp.RemoteOperations;
import static viewClient.CloseMode.instanciaMudancaAdicao;
import static viewClient.MenuFile.SelectedFile;
import static viewClient.ViewDarkAdd.instanciaAbertaAdicao;

/**
 *
 * @author Eduardo.Fernando
 */
public class DarklistManagerViewClient extends javax.swing.JFrame {

    
    private  int Incremental;
    private  int FinalNumber;
    
    static boolean validador = true;
    static boolean validador2 = true;
    static boolean validador3 = false;
    
    static int NumeroOriginalSelecionadoTabela;

    private final ActionListener AbrirAdicao = (ActionEvent e) -> instanciaAdicaoChecker();
    private final ActionListener AbrirCloseMode = (ActionEvent e) -> instanciaMudancaChecker();

    private final MainTableUtil UtilMainTable;

    public static final String PATH_LOG_DIARIO = Manager.getRoot().get("caminho_local_temp_producao_dia");

    private static RemoteOperations Remote;
    
     public void instanciaAdicaoChecker(){
    
    
        if (!instanciaAbertaAdicao) {
            instanciaAbertaAdicao = true;
            new ViewDarkAdd().setVisible(true);
        } else {
//            JOptionPane.showMessageDialog(null, "A instância já está aberta.");
        }
    
    
    }
    
    public void instanciaMudancaChecker(){
    
    
        if (!instanciaMudancaAdicao) {
            instanciaMudancaAdicao = true;
            new CloseMode().setVisible(true);
        } else {
//            JOptionPane.showMessageDialog(null, "A instância já está aberta.");
        }
    
    
    }
   
    public  static boolean validarSeProucaoJaFoi(long Domicilio) {
        

        for (int i = 0; i < tbMainViewDarkList.getRowCount(); i++) {

            if (Long.parseLong(tbMainViewDarkList.getValueAt(i, 0).toString()) == Domicilio) {

                if (tbMainViewDarkList.getValueAt(i, 4).toString().equals("true")) {

                    return true;

                }

            }

        }

        return false;

    }

    public  static void carregarLogAlteracoes() throws IOException, Exception {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
        
        Remote.downloadArquivoLista(SelectedFile.getName());

        new LogDao(LocalDate.parse(lblDtProd.getText(), fmt).plusDays(1), SelectedFile).Logs().forEach(x -> {

            DefaultTableModel df = (DefaultTableModel) tbMainViewDarkList.getModel();

            if (!(x.getAlteracaoRealizada().contains("a"))) {

                df.addRow(new Object[]{
                    x.getId(),
                    x.getDataAbertura(),
                    x.getDataFechamento(),
                    x.getComentario(),
                    x.isStatus(),
                    "-",
                    "-",
                    x.getDiferencaDatas(),
                    "-"

                });

            } else {

                df.addRow(new Object[]{
                    x.getId(),
                    x.getDataAbertura(),
                    x.getDataFechamento(),
                    x.getComentario(),
                    x.isStatus(),
                    x.getDescStatus(),
                    x.getAutorAlteracao(),
                    x.getDiferencaDatas(),
                    x.getAlteracaoRealizada()

                });

            }

        });

        new Util(tbMainViewDarkList).ajustarFormataColunasTabelaConteudo();

    }

    public static void CarregarDarkList(File DarkFile) throws IOException, Exception {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
                
        new DarklistDao1(LocalDate.parse(lblDtProd.getText(), fmt).plusDays(1), DarkFile).getStatus().forEach((var x) -> {

            DefaultTableModel df = (DefaultTableModel) tbMainViewDarkList.getModel();

            String allowChange = "No permitido cambios";

            if (x != null) {

                if (x.isStatus()) {

                    allowChange = "Listo para Cambio";

                }

                df.addRow(new Object[]{
                    x.getId(),
                    x.getDataAbertura(),
                    x.getDataFechamento(),
                    x.getComentario(),
                    x.isStatus(),
                    allowChange

                });

            }

        });

        new Util(tbMainViewDarkList).ajustarFormataColunasTabelaConteudo();

    }

    public DarklistManagerViewClient() throws IOException, Exception {

        Remote = new RemoteOperations(new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22));
      
        UtilMainTable = new MainTableUtil();

        Flags.clear();

        FlatLightLaf.install();

        initComponents();

        anularEnterDentroFiltro();

        Remote.downloadNumeralDia();
        
        lblDtProd.setText(FileUtils.readFileToString(new File(PATH_LOG_DIARIO)));
        

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jMenuItem1 = new javax.swing.JMenuItem();
        MainPn = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbMainViewDarkList = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txt_filtro = new javax.swing.JTextField();
        cbTipo = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblDtProd = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pd.png"))); // NOI18N
        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbMainViewDarkList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hogar", "Fecha Inicio", "Fecha Fin", "Obs", "En Darklist?", "Puede Cambiar?", "Log", "Cerramiento<>Propduccion", "Cambio Realizado"
            }
        ));
        tbMainViewDarkList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tbMainViewDarkList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMainViewDarkListMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbMainViewDarkListMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbMainViewDarkListMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbMainViewDarkList);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        txt_filtro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_filtroKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_filtroKeyTyped(evt);
            }
        });

        cbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hogar", "Fecha Inicial", "Fecha Final", "Observacion", "En Dark?" }));
        cbTipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTipoItemStateChanged(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/upload.png"))); // NOI18N
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton1.setOpaque(true);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pastas.png"))); // NOI18N
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton2.setOpaque(true);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Ultima fecha de produccion");

        lblDtProd.setForeground(new java.awt.Color(0, 0, 0));
        lblDtProd.setText("YYYYMMDD");

        jLabel2.setText("Current Mode : ");

        lblmode.setBackground(new java.awt.Color(0, 0, 0));
        lblmode.setForeground(new java.awt.Color(0, 0, 0));
        lblmode.setText("...");

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/look.png"))); // NOI18N
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(94, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblDtProd, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbTipo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 301, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(28, 28, 28)
                                .addComponent(lblmode, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(95, 95, 95))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lblmode))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(txt_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblDtProd))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout MainPnLayout = new javax.swing.GroupLayout(MainPn);
        MainPn.setLayout(MainPnLayout);
        MainPnLayout.setHorizontalGroup(
            MainPnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPnLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(MainPnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        MainPnLayout.setVerticalGroup(
            MainPnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPnLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbMainViewDarkListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMainViewDarkListMouseEntered


    }//GEN-LAST:event_tbMainViewDarkListMouseEntered

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int resposta = JOptionPane.showConfirmDialog(null, "Desea enviar los cambios para la equipe regional?",
                "Confirmacion",
                JOptionPane.YES_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {

            JFrame progressFrame = new JFrame() {
                {
                    // Configura��es do JFrame
                    setTitle("Aguardando processo");
                    setSize(300, 100);
                    setUndecorated(true);
                    setLocationRelativeTo(null);

                    // Cria��o do JProgressBar
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

                        String arquivoSalvoLog = Manager.getRoot().get("caminho_local_temp_logFile") + lblDtProd.getText() + "_log.csv";

                        JOptionPane.showMessageDialog(null, arquivoSalvoLog);
                        
                        
                        new Util(tbMainViewDarkList).exportarConteudoParaCsv(tbMainViewDarkList, arquivoSalvoLog);
                       

                        new SFTPOperations().uploadLogFile(lblDtProd.getText());


                        progressFrame.setVisible(false);
                    } catch (Exception ex) {
                        Logger.getLogger(MenuFile.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }.start();

        } else if (resposta == JOptionPane.NO_OPTION) {

            JOptionPane.showMessageDialog(null, "Voc� selecionou 'N�o'.");

        }


    }//GEN-LAST:event_jButton1ActionPerformed

    public void anularEnterDentroFiltro() {

        txt_filtro.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();

                }
            }
        });

    }


    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        JFrame progressFrame = new JFrame() {
            {

                setTitle("Aguardando processo");
                setSize(300, 100);
                setUndecorated(true);
                setLocationRelativeTo(null);

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
        try {

            new Thread() {

                public void run() {

                    try {
                        progressFrame.setVisible(true);

                        new MenuFile().setVisible(true);

                        progressFrame.setVisible(false);
                    } catch (Exception ex) {
                        Logger.getLogger(DarklistManagerViewClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }.start();

        } catch (Exception ex) {
            Logger.getLogger(DarklistManagerViewClient.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        try {

            new FlagOperations().obterListaDeFlags(lblDtProd.getText());
            new FlagOperations().gerarFlag(lblDtProd.getText());

        } catch (Exception ex) {
            Logger.getLogger(DarklistManagerViewClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        new CambioFlagView().setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed


    private void cbTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTipoItemStateChanged

        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoItemStateChanged

    private void tbMainViewDarkListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMainViewDarkListMouseClicked
         
            ///RESETAR 
            
            FinalNumber = 0;
            Incremental = 0;

            //
            
            if (SwingUtilities.isRightMouseButton(evt)) {

                if (tbMainViewDarkList.getSelectedRow() > -1) {

                    try {
                        long domselecionado = Long.parseLong(tbMainViewDarkList.getValueAt(tbMainViewDarkList.getSelectedRow(), 0).toString());
                        LocalDate dataSelecioanda = LocalDate.parse(tbMainViewDarkList.getValueAt(tbMainViewDarkList.getSelectedRow(), 1).toString());
                        
                        
                        NumeroOriginalSelecionadoTabela=
                                
                                new Util().obterNumeroDaLinhaTabelaSelecionadaOriginal(domselecionado,
                                        
                                        dataSelecioanda,
                                        txt_filtro,
                                        Incremental,
                                        FinalNumber,
                                        SelectedFile);
                        
                        
                        String Verificador = tbMainViewDarkList.getValueAt(tbMainViewDarkList.getSelectedRow(), 5).toString();
                        
                        validador3 = Verificador.toLowerCase().matches(".*apro.*");
                        
                        validador2 = !Verificador.equals("No permitido cambios");
                        
                        validador = Verificador.contains("Nueva Linea/Aprobacion");
                        
                        UtilMainTable.mostrarMenuFlutuante(evt.getComponent(), evt.getX(), evt.getY(), AbrirCloseMode, AbrirAdicao, tbMainViewDarkList, validador3, validador, validador2);
                    } catch (IOException ex) {
                        Logger.getLogger(DarklistManagerViewClient.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
    }//GEN-LAST:event_tbMainViewDarkListMouseClicked

    private void tbMainViewDarkListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMainViewDarkListMousePressed
        try {
       

            // TODO add your handling code here:
        } catch (Exception ex) {
            Logger.getLogger(DarklistManagerViewClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tbMainViewDarkListMousePressed

    private void txt_filtroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtroKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        } else {
            filterTable();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_txt_filtroKeyPressed

    private void txt_filtroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtroKeyTyped

        // TODO add your handling code here:
    }//GEN-LAST:event_txt_filtroKeyTyped
    public static void filterTable() {
        String searchText = txt_filtro.getText();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) tbMainViewDarkList.getModel());
        tbMainViewDarkList.setRowSorter(sorter);

        // Verifica se o sorter j� est� inicializado
        if (sorter.getSortKeys().isEmpty()) {
            // Define uma ordem de classifica��o padr�o para evitar o erro de refer�ncia nula
            sorter.setSortKeys(List.of(new RowSorter.SortKey(cbTipo.getSelectedIndex(), SortOrder.ASCENDING)));
        }

        // Define o filtro para a coluna 2 (�ndice 1)
        sorter.setRowFilter(RowFilter.regexFilter(searchText, cbTipo.getSelectedIndex()));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new DarklistManagerViewClient().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(DarklistManagerViewClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(DarklistManagerViewClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPn;
    public static javax.swing.JComboBox<String> cbTipo;
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lblDtProd;
    public static final javax.swing.JLabel lblmode = new javax.swing.JLabel();
    public static javax.swing.JTable tbMainViewDarkList;
    public static javax.swing.JTextField txt_filtro;
    // End of variables declaration//GEN-END:variables
}
