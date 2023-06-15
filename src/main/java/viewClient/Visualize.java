/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package viewClient;


import static View.ViewDarkAdd.removeTableLine;
import com.formdev.flatlaf.FlatLightLaf;
import dao.DarklistDao1;
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
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
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
import static sftp.ConnectionFactory.downloadDarklistFileEdition;

/**
 *
 * @author Eduardo.Fernando
 */
public class Visualize extends javax.swing.JFrame {

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

    public static void saveLog() throws FileNotFoundException {

        File logFile = new File("c:/teste/log.csv");

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

   
  public static void loadDarkListEditMode(String Data,String DarklistFile) throws IOException, Exception {

      

        downloadDarklistFileEdition(DarklistFile);
      
      
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");

        new DarklistDao1(LocalDate.parse(Data, fmt).plusDays(1), new File("C:\\teste\\Nova pasta\\"+DarklistFile)).getStatus().forEach(x -> {

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
//       tbMainViewDarkList.getColumnModel().getColumn(0).setCellRenderer(new CentralizedCellRenderer());
//        tbMainViewDarkList.getColumnModel().getColumn(1).setCellRenderer(new CentralizedCellRenderer());
//        tbMainViewDarkList.getColumnModel().getColumn(2).setCellRenderer(new CentralizedCellRenderer());
//        tbMainViewDarkList.getColumnModel().getColumn(3).setCellRenderer(new CentralizedCellRenderer());
//        tbMainViewDarkList.getColumnModel().getColumn(4).setCellRenderer(new CentralizedCellRenderer());
//
//
//        UIManager.put("Table.background", new Color(240, 240, 240));
//        UIManager.put("Table.selectionBackground", new Color(0, 120, 215));
//        UIManager.put("Table.selectionForeground", Color.WHITE);

//        autoResizeColumns();

    }
  

    public Visualize() throws IOException, Exception {

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

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pd.png"))); // NOI18N
        jMenuItem1.setText("jMenuItem1");

        setResizable(false);

        tbMainViewDarkList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hogar", "Fecha Inicio", "Fecha Fin", "Obs"
            }
        ));
        tbMainViewDarkList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tbMainViewDarkList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbMainViewDarkListMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tbMainViewDarkList);

        javax.swing.GroupLayout MainPnLayout = new javax.swing.GroupLayout(MainPn);
        MainPn.setLayout(MainPnLayout);
        MainPnLayout.setHorizontalGroup(
            MainPnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPnLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 939, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        MainPnLayout.setVerticalGroup(
            MainPnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPnLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        testes();

        // TODO add your handling code here:
    }//GEN-LAST:event_tbMainViewDarkListMouseEntered

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
                    new Visualize().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Visualize.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Visualize.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPn;
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tbMainViewDarkList;
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
