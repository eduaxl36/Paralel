/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package viewClient;

import static View.ViewDarkAdd.removeTableLine;
import com.formdev.flatlaf.FlatLightLaf;
import dao.DarklistDao1;
import dao.LogDao;
import flag.entidadeFlag;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import sftp.ConnectionFactory;
import static sftp.ConnectionFactory.downloadLogdia;
import static sftp.ConnectionFactory.uploadLogdia;
import static viewClient.FlagCreator.createFlag;
import static viewClient.MenuFile.SelectedFile;

/**
 *
 * @author Eduardo.Fernando
 */
public class DarklistManagerViewClient extends javax.swing.JFrame {

    public static int DefinitionNumber;
    public static int SortNumber;
    public static int SelectedRowReal = 0;

    public static int defineNumber(int numero) {

        DefinitionNumber = numero;
        return numero;

    }

    public static TableRowSorter<DefaultTableModel> sorter;
    static JMenuItem menuItem3;
    JPopupMenu popupMenu;
    static boolean validador = true;
    static boolean validador2 = true;
    static boolean validador3 = false;

    public static List<entidadeFlag> Flags = new ArrayList<>();

    public static List<entidadeFlag> criarFlag() throws IOException {

        for (int i = 0; i < tbMainViewDarkList.getRowCount(); i++) {
            int dom = Integer.parseInt(tbMainViewDarkList.getValueAt(i, 0).toString());
            String value = String.valueOf(tbMainViewDarkList.getValueAt(i, 6));
            String comment = String.valueOf(tbMainViewDarkList.getValueAt(i, 3));
            String result = value != null && !value.isEmpty() ? value : "-";
            String valueTp = String.valueOf(tbMainViewDarkList.getValueAt(i, 8));
            String resultTp = valueTp != null && !valueTp.isEmpty() ? valueTp : "-";

            LocalDate DataAbertura = LocalDate.parse(tbMainViewDarkList.getValueAt(i, 1).toString());
            LocalDate DataFechamento = LocalDate.parse(tbMainViewDarkList.getValueAt(i, 2).toString());

            if (!(result.equals("null"))) {

                Flags.add(
                        new entidadeFlag(
                                dom,
                                result,
                                resultTp,
                                DataAbertura,
                                DataFechamento,
                                false,
                                comment)
                );

            }

        }

           createFlag(Flags) ;
        return Flags;
    }

    public static int getFreezeTable(long Dom, LocalDate DataAbertura) throws IOException {

        lblDtProd.setText(FileUtils.readFileToString(new File("C:\\teste\\Nova pasta\\logDia.txt")));

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");

        new DarklistDao1(LocalDate.parse(lblDtProd.getText(), fmt).plusDays(1), SelectedFile).getStatus().forEach((var x) -> {

            if (x != null && x.getId() != 0) {

                if (x.getId() == Dom && DataAbertura.isEqual(x.getDataAbertura())) {
                    SortNumber = DefinitionNumber;
                }

            }

            DefinitionNumber++;
        });
        return SortNumber;

    }

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

        JMenuItem menuItem1 = new JMenuItem("Añadir hogar en Darklist");

        JMenuItem menuItem4 = null;

        ImageIcon icon = new ImageIcon(getClass().getResource("/img/darklist.png"));
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/img/allow.png"));
        ImageIcon icon3 = new ImageIcon(getClass().getResource("/img/delete.png"));
        ImageIcon icon4 = new ImageIcon(getClass().getResource("/img/allow.png"));
        menuItem1.setIcon(icon);

        popupMenu.add(menuItem1);

        popupMenu.addSeparator();

        JMenuItem menuItem2 = new JMenuItem("Cambiar");
        menuItem2.setIcon(icon2);
        popupMenu.add(menuItem2);
        popupMenu.addSeparator();
        menuItem3 = new JMenuItem("Borrar Linea");
        popupMenu.add(menuItem3);
        menuItem3.setIcon(icon3);

        popupMenu.addSeparator();
        menuItem4 = new JMenuItem("Aprovar Cambio");
        popupMenu.add(menuItem4);
        menuItem4.setIcon(icon4);

        menuItem4.setEnabled(validador3);

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

        menuItem4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tbMainViewDarkList.setValueAt("Cambio Aprovado", tbMainViewDarkList.getSelectedRow(), 5);

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

              
                    
                    if (Verificador.toLowerCase().matches(".*apro.*")) {

                        validador3 = true;

                    } else {

                        validador3 = false;

                    }

                    if (Verificador.equals("No permitido cambios")) {

                        validador2 = false;

                    } else {

                        validador2 = true;

                    }

                    if (Verificador.contains("Nueva Linea/Aprobacion")) {

                        validador = true;

                    } else {

                        validador = false;

                    }

                    showPopupMenu(e.getComponent(), e.getX(), e.getY());

                }
            }
        });

    }

    public static void loadLog() throws IOException, Exception {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");

        ConnectionFactory.downloadLoglistFile(SelectedFile.getName());

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

    public static void loadDarkList() throws IOException, Exception {

        lblDtProd.setText(FileUtils.readFileToString(new File("C:\\teste\\Nova pasta\\logDia.txt")));

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");

        new DarklistDao1(LocalDate.parse(lblDtProd.getText(), fmt).plusDays(1), SelectedFile).getStatus().forEach((var x) -> {

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

    public DarklistManagerViewClient() throws IOException, Exception {

        Flags.clear();

        FlatLightLaf.install();

        initComponents();

        testes();

        downloadLogdia();

        lblDtProd.setText(FileUtils.readFileToString(new File("C:\\teste\\Nova pasta\\logDia.txt")));

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

                        String arquivoSalvoLog = "C:\\teste\\Nova pasta\\" + lblDtProd.getText() + "_log.csv";

                        exportToCSV(tbMainViewDarkList, arquivoSalvoLog);

                        uploadLogdia(lblDtProd.getText());
                      

                        progressFrame.setVisible(false);
                    } catch (Exception ex) {
                        Logger.getLogger(MenuFile.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }.start();

            // Coloque aqui o código que deseja executar quando a opção for "Sim"
            // Coloque aqui o código que deseja executar quando a opção for "Sim"
        } else if (resposta == JOptionPane.NO_OPTION) {
            // Caso a opção selecionada seja "Não"
            JOptionPane.showMessageDialog(null, "Você selecionou 'Não'.");

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

                        new MenuFile().setVisible(true);

                        progressFrame.setVisible(false);
                    } catch (Exception ex) {
                        Logger.getLogger(DarklistManagerViewClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }.start();

            // TODO add your handling code here:
        } catch (Exception ex) {
            Logger.getLogger(DarklistManagerViewClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        try {
            criarFlag();
        } catch (IOException ex) {
            Logger.getLogger(DarklistManagerViewClient.class.getName()).log(Level.SEVERE, null, ex);
        }
           

        new CambioFlagView().setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    public static void buscarLinhaFiltrada() {

        for (int i = 0; i < tbMainViewDarkList.getRowCount(); i++) {

        }

    }


    private void cbTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTipoItemStateChanged

        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoItemStateChanged

    private void tbMainViewDarkListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMainViewDarkListMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbMainViewDarkListMouseClicked

    private void tbMainViewDarkListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMainViewDarkListMousePressed
        try {
            SortNumber = 0;
            DefinitionNumber = 0;
            if (tbMainViewDarkList.getSelectedRow() > -1) {
                long domselecionado = Long.parseLong(tbMainViewDarkList.getValueAt(tbMainViewDarkList.getSelectedRow(), 0).toString());
                LocalDate dataSelecioanda = LocalDate.parse(tbMainViewDarkList.getValueAt(tbMainViewDarkList.getSelectedRow(), 1).toString());

                getFreezeTable(domselecionado, dataSelecioanda);

            }

            // TODO add your handling code here:
        } catch (Exception ex) {
            Logger.getLogger(DarklistManagerViewClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tbMainViewDarkListMousePressed

    private void txt_filtroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtroKeyPressed
        filterTable();

        // TODO add your handling code here:
    }//GEN-LAST:event_txt_filtroKeyPressed

    private void txt_filtroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtroKeyTyped

        // TODO add your handling code here:
    }//GEN-LAST:event_txt_filtroKeyTyped
    public static void filterTable() {
        String searchText = txt_filtro.getText();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) tbMainViewDarkList.getModel());
        tbMainViewDarkList.setRowSorter(sorter);

        // Verifica se o sorter já está inicializado
        if (sorter.getSortKeys().isEmpty()) {
            // Define uma ordem de classificação padrão para evitar o erro de referência nula
            sorter.setSortKeys(List.of(new RowSorter.SortKey(cbTipo.getSelectedIndex(), SortOrder.ASCENDING)));
        }

        // Define o filtro para a coluna 2 (índice 1)
        sorter.setRowFilter(RowFilter.regexFilter(searchText, cbTipo.getSelectedIndex()));
    }

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

class CentralizedCellRenderers extends DefaultTableCellRenderer {

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
