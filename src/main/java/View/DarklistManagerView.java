/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import static View.DarklistListFiles.SelectedFile;
import static View.ViewDarkAdd.removeTableLine;
import com.formdev.flatlaf.FlatLightLaf;
import dao.DarklistDao1;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import org.apache.commons.io.FileUtils;
import static sftp.ConnectionFactory.downloadLogdia;
import static sftp.ConnectionFactory.uploadLogdia;

/**
 *
 * @author Eduardo.Fernando
 */
public class DarklistManagerView extends javax.swing.JFrame {

    /**
     * Creates new form DarklistManagerView
     */
    public static TableRowSorter<DefaultTableModel> sorter;
    static JMenuItem menuItem3;
    JPopupMenu popupMenu;
    static boolean validador = true;
    static boolean validador2 = true;

    public static void autoResizeColumns() {
        for (int column = 0; column < tbMainViewDarkList.getColumnCount(); column++) {
            TableColumn tableColumn = tbMainViewDarkList.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < tbMainViewDarkList.getRowCount(); row++) {
                TableCellRenderer cellRenderer = tbMainViewDarkList.getCellRenderer(row, column);
                Component c = tbMainViewDarkList.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + tbMainViewDarkList.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                // Verificar se o tamanho excede o máximo permitido
                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }

            tableColumn.setPreferredWidth(preferredWidth);
            tbMainViewDarkList.getColumnModel().getColumn(0).setCellRenderer(new CentralizedCellRenderer());
            tbMainViewDarkList.getColumnModel().getColumn(1).setCellRenderer(new CentralizedCellRenderer());
            tbMainViewDarkList.getColumnModel().getColumn(2).setCellRenderer(new CentralizedCellRenderer());
            tbMainViewDarkList.getColumnModel().getColumn(3).setCellRenderer(new CentralizedCellRenderer());
            tbMainViewDarkList.getColumnModel().getColumn(4).setCellRenderer(new CentralizedCellRenderer());
            tbMainViewDarkList.getColumnModel().getColumn(5).setCellRenderer(new CentralizedCellRenderer());
            tbMainViewDarkList.getColumnModel().getColumn(6).setCellRenderer(new CentralizedCellRenderer());
            tbMainViewDarkList.getColumnModel().getColumn(7).setCellRenderer(new CentralizedCellRenderer());
            tbMainViewDarkList.getColumnModel().getColumn(8).setCellRenderer(new CentralizedCellRenderer());

            UIManager.put("Table.background", new Color(240, 240, 240));
            UIManager.put("Table.selectionBackground", new Color(0, 120, 215));
            UIManager.put("Table.selectionForeground", Color.WHITE);
        }
    }

    public static void exportToCSV(JTable table, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            TableModel model = table.getModel();
            int rowCount = model.getRowCount();
            int columnCount = model.getColumnCount();

            // Escrever os nomes das colunas
            for (int column = 0; column < columnCount; column++) {
                writer.append(model.getColumnName(column));
                if (column < columnCount - 1) {
                    writer.append(";");
                }
            }
            writer.append("\n");

            // Escrever os dados das células
            for (int row = 0; row < rowCount; row++) {
                for (int column = 0; column < columnCount; column++) {
                    writer.append(String.valueOf(model.getValueAt(row, column)));
                    if (column < columnCount - 1) {
                        writer.append(";");
                    }
                }
                writer.append("\n");
            }

            System.out.println("JTable exportado para CSV com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao exportar JTable para CSV: " + e.getMessage());
        }
    }



    public static boolean validarSeProucaoJaFoi(long Domicilio) {

        for (int i = 0; i < tbMainViewDarkList.getRowCount(); i++) {

            if (Long.parseLong(tbMainViewDarkList.getValueAt(i, 0).toString()) == Domicilio) {

                if (tbMainViewDarkList.getValueAt(i, 4).toString().equals("true")) {

                    return true;

                }

            }

        }

        return false;

    }

    private void showPopupMenu(Component component, int x, int y) {

        popupMenu = new JPopupMenu();

        // Adicione os itens de menu desejados
        JMenuItem menuItem1 = new JMenuItem("Añadir hogar en Darklist");

        ImageIcon icon = new ImageIcon(getClass().getResource("/img/darklist.png"));
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/img/allow.png"));
        ImageIcon icon3 = new ImageIcon(getClass().getResource("/img/delete.png"));
        menuItem1.setIcon(icon);

        popupMenu.add(menuItem1);

        popupMenu.addSeparator();

        JMenuItem menuItem2 = new JMenuItem("Cerrar Hogar");
        menuItem2.setIcon(icon2);
        popupMenu.add(menuItem2);
        popupMenu.addSeparator();
        menuItem3 = new JMenuItem("Borrar Linea");
        popupMenu.add(menuItem3);
        menuItem3.setIcon(icon3);

        menuItem3.setEnabled(validador);
        menuItem2.setEnabled(validador2);

        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new CloseMode().setVisible(true);

            }
        });

        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new ViewDarkAdd().setVisible(true);

            }
        });

        menuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                removeTableLine();

            }
        });

        popupMenu.show(component, x, y);
    }

    public void testes() {
        tbMainViewDarkList.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int columnIndex = tbMainViewDarkList.columnAtPoint(e.getPoint());

                sorter.setSortKeys(List.of(new RowSorter.SortKey(columnIndex, SortOrder.ASCENDING)));
            }
        });
        tbMainViewDarkList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {

                    String Verificador = tbMainViewDarkList.getValueAt(tbMainViewDarkList.getSelectedRow(), 5).toString();

                    if (Verificador.equals("No permitido cambios")) {

                        validador2 = false;

                    } else {

                        validador2 = true;

                    }

                    if (Verificador.equals("Nueva Linea")) {

                        validador = true;

                    } else {

                        validador = false;

                    }

                    showPopupMenu(e.getComponent(), e.getX(), e.getY());

                }
            }
        });

    }

    public static void loadDarkList() throws IOException, Exception {

        downloadLogdia();

        lblDtProd.setText(FileUtils.readFileToString(new File("C:\\teste\\Nova pasta\\logDia.txt")));

        sorter = new TableRowSorter<>((DefaultTableModel) tbMainViewDarkList.getModel());

        tbMainViewDarkList.setRowSorter(sorter);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");

        new DarklistDao1(LocalDate.parse(lblDtProd.getText(), fmt).plusDays(1), SelectedFile).getStatus().forEach(x -> {

            DefaultTableModel df = (DefaultTableModel) tbMainViewDarkList.getModel();

            String allowChange = "No permitido cambios";

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

        });

        tbMainViewDarkList.getColumnModel().getColumn(1).setCellRenderer(new CentralizedCellRenderer());
        tbMainViewDarkList.getColumnModel().getColumn(2).setCellRenderer(new CentralizedCellRenderer());
        tbMainViewDarkList.getColumnModel().getColumn(3).setCellRenderer(new CentralizedCellRenderer());
        tbMainViewDarkList.getColumnModel().getColumn(4).setCellRenderer(new CentralizedCellRenderer());
        tbMainViewDarkList.getColumnModel().getColumn(5).setCellRenderer(new CentralizedCellRenderer());
        tbMainViewDarkList.getColumnModel().getColumn(6).setCellRenderer(new CentralizedCellRenderer());
        tbMainViewDarkList.getColumnModel().getColumn(7).setCellRenderer(new CentralizedCellRenderer());
        tbMainViewDarkList.getColumnModel().getColumn(8).setCellRenderer(new CentralizedCellRenderer());

        UIManager.put("Table.background", new Color(240, 240, 240));
        UIManager.put("Table.selectionBackground", new Color(0, 120, 215));
        UIManager.put("Table.selectionForeground", Color.WHITE);

        autoResizeColumns();

    }

    public DarklistManagerView() throws IOException {

        FlatLightLaf.install();

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

        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jMenuItem1 = new javax.swing.JMenuItem();
        MainPn = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbMainViewDarkList = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel("\uf0e7");
        txt_filtro = new javax.swing.JTextField();
        cbTipo = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblDtProd = new javax.swing.JLabel();

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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbMainViewDarkListMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tbMainViewDarkList);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("      Buscar");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
        });

        cbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Buscar Por :" }));

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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(261, 261, 261))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)))
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
                .addContainerGap(16, Short.MAX_VALUE))
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

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        sorter = new TableRowSorter<>((DefaultTableModel) tbMainViewDarkList.getModel());
        tbMainViewDarkList.setRowSorter(sorter);

        String filterText = txt_filtro.getText();
        RowFilter<DefaultTableModel, Object> filter = null;

        try {
            // Cria um filtro com base no texto inserido no campo de filtro
            filter = RowFilter.regexFilter(filterText, 4); // 4 é o índice da coluna "Status"
        } catch (java.util.regex.PatternSyntaxException ex) {
            ex.printStackTrace();
        }

        sorter.setRowFilter(filter);
    }//GEN-LAST:event_jLabel1MousePressed

    private void tbMainViewDarkListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMainViewDarkListMouseEntered

        testes();

        // TODO add your handling code here:
    }//GEN-LAST:event_tbMainViewDarkListMouseEntered

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     
        try {
            String arquivoSalvoLog = "c:/teste/"+lblDtProd.getText() + "_log.csv";
            
            
            exportToCSV(tbMainViewDarkList, arquivoSalvoLog);
            
            uploadLogdia(lblDtProd.getText());
        } catch (Exception ex) {
            Logger.getLogger(DarklistManagerView.class.getName()).log(Level.SEVERE, null, ex);
        }

   
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

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
        try {

            new Thread() {

                public void run() {

                    try {
                        progressFrame.setVisible(true);

                        new DarklistListFiles().setVisible(true);

                        progressFrame.setVisible(false);
                    } catch (Exception ex) {
                        Logger.getLogger(DarklistManagerView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }.start();

            // TODO add your handling code here:
        } catch (Exception ex) {
            Logger.getLogger(DarklistManagerView.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

//     PlasticLookAndFeel.setPlasticTheme(new ExperienceBlue());
//            UIManager.setLookAndFeel(new PlasticLookAndFeel());
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(DarklistManagerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(DarklistManagerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(DarklistManagerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(DarklistManagerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new DarklistManagerView().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(DarklistManagerView.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lblDtProd;
    public static javax.swing.JTable tbMainViewDarkList;
    public static javax.swing.JTextField txt_filtro;
    // End of variables declaration//GEN-END:variables
}

class CentralizedCellRenderer extends DefaultTableCellRenderer {

    private static final Color RED = Color.RED;
    private static final Color WHITE = Color.WHITE;

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (component instanceof JLabel) {
            ((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER);
        }

        boolean status = (boolean) table.getValueAt(row, table.getColumn("En Darklist?").getModelIndex());
        if (status) {
            component.setBackground(RED);
            component.setForeground(WHITE);
        } else {
            // Restaura as cores padrão para as células não afetadas
            component.setBackground(table.getBackground());
            component.setForeground(table.getForeground());
        }

        if (isSelected) {
            component.setBackground(table.getSelectionBackground());
            component.setForeground(table.getSelectionForeground());
        }

        return component;
    }
}
