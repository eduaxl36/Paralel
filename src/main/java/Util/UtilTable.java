package Util;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import dao.DarkDao;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import javax.swing.text.View;
import org.apache.commons.lang.StringUtils;
import static viewClientDarklist.DarklistManagerViewClient.lblDtProd;

/**
 *
 * @author Eduardo.Fernando
 */
public class UtilTable {

    private static UtilTable Instancia;

    JTable Tabela;
    DefaultTableModel Modelo;

    int numeroIncremental;
    int numeroFinal;

    public UtilTable(JTable Tabela) {
        
        this.Tabela = Tabela;
        Modelo = (DefaultTableModel) Tabela.getModel();

    }

    public UtilTable() {
    }

    public static UtilTable getInstance() {

        Instancia = new UtilTable();
        return Instancia;

    }

    public void cleanTable(JTable Tabela) {

        DefaultTableModel df = (DefaultTableModel) Tabela.getModel();

        df.setNumRows(0);

        realizaAjuste(Tabela);

    }

    public void realizaAjuste(JTable Tabela) {

        Tabela.setAutoResizeMode(Tabela.AUTO_RESIZE_OFF);
        Tabela.setPreferredSize(null);
        autoResizeTable(Tabela, true, 2);

    }

    public int autoResizeTable(JTable aTable, boolean includeColumnHeaderWidth, int columnPadding) {

        try {
            int columnCount = aTable.getColumnCount();
            int tableWidth = 0;

            Dimension cellSpacing = aTable.getIntercellSpacing();

            if (columnCount > 0) // must have columns !
            {
                // STEP ONE : Work out the column widths

                int columnWidth[] = new int[columnCount];

                for (int i = 0; i < columnCount; i++) {
                    columnWidth[i] = getMaxColumnWidth(aTable, i, true, columnPadding);

                    tableWidth += columnWidth[i];
                }

                // account for cell spacing too
                tableWidth += ((columnCount - 1) * cellSpacing.width);

                // STEP TWO : Dynamically resize each column
                // try changing the size of the column names area
                JTableHeader tableHeader = aTable.getTableHeader();

                Dimension headerDim = tableHeader.getPreferredSize();

                // headerDim.height = tableHeader.getHeight();
                headerDim.width = tableWidth;
                tableHeader.setPreferredSize(headerDim);

                TableColumnModel tableColumnModel = aTable.getColumnModel();
                TableColumn tableColumn;

                for (int i = 0; i < columnCount; i++) {
                    tableColumn = tableColumnModel.getColumn(i);

                    tableColumn.setPreferredWidth(columnWidth[i]);
                }

            }

            return (tableWidth);
        } catch (Exception e) {

            return 0;

        }

    }

    /*
 * @param JTable aTable, the JTable to autoresize the columns on
 * @param int columnNo, the column number, starting at zero, to calculate the maximum width on
 * @param boolean includeColumnHeaderWidth, use the Column Header width as a minimum width
 * @param int columnPadding, how many extra pixels do you want on the end of each column
 * @returns The table width, just in case the caller wants it...
     */
    public static int getMaxColumnWidth(JTable aTable, int columnNo,
            boolean includeColumnHeaderWidth,
            int columnPadding) {

        try {

            TableColumn column = aTable.getColumnModel().getColumn(columnNo);
            Component comp = null;
            int maxWidth = 0;

            if (includeColumnHeaderWidth) {
                TableCellRenderer headerRenderer = column.getHeaderRenderer();
                if (headerRenderer != null) {
                    comp = headerRenderer.getTableCellRendererComponent(aTable, column.getHeaderValue(), false, false, 0, columnNo);

                    if (comp instanceof JTextComponent) {
                        JTextComponent jtextComp = (JTextComponent) comp;

                        String text = jtextComp.getText();
                        Font font = jtextComp.getFont();
                        FontMetrics fontMetrics = jtextComp.getFontMetrics(font);

                        maxWidth = SwingUtilities.computeStringWidth(fontMetrics, text);
                    } else {
                        maxWidth = comp.getPreferredSize().width;
                    }
                } else {
                    try {
                        String headerText = (String) column.getHeaderValue();
                        JLabel defaultLabel = new JLabel(headerText);

                        //Igor
                        //ako je u table modelu kao ime kolone stvalje html code
                        //treba izracunati max duzinu text na sljedeci nacin
                        View view = (View) defaultLabel.getClientProperty("html");
                        if (view != null) {
                            Document d = view.getDocument();
                            if (d instanceof StyledDocument) {
                                StyledDocument doc = (StyledDocument) d;
                                int length = doc.getLength();
                                headerText = StringUtils.leftPad("", length + 5);
                            }
                        }
                        //END Igor

                        Font font = defaultLabel.getFont();
                        FontMetrics fontMetrics = defaultLabel.getFontMetrics(font);

                        maxWidth = SwingUtilities.computeStringWidth(fontMetrics, headerText);
                    } catch (ClassCastException ce) {
                        // Can't work out the header column width..
                        maxWidth = 0;
                    }
                }
            }

            TableCellRenderer tableCellRenderer;
            // Component comp;
            int cellWidth = 0;

            for (int i = 0; i < aTable.getRowCount(); i++) {
                tableCellRenderer = aTable.getCellRenderer(i, columnNo);

                comp = tableCellRenderer.getTableCellRendererComponent(aTable, aTable.getValueAt(i, columnNo), false, false, i, columnNo);
                //textarea na prvo mjesto jer je takodjer descendant od JTextComponent
                if (comp instanceof JTextArea) {
                    JTextComponent jtextComp = (JTextComponent) comp;

                    String text = getMaximuWrapedString(jtextComp.getText());
                    Font font = jtextComp.getFont();
                    FontMetrics fontMetrics = jtextComp.getFontMetrics(font);

                    int textWidth = SwingUtilities.computeStringWidth(fontMetrics, text);

                    maxWidth = Math.max(maxWidth, textWidth);
                } else if (comp instanceof JTextComponent) {
                    JTextComponent jtextComp = (JTextComponent) comp;

                    String text = jtextComp.getText();
                    Font font = jtextComp.getFont();
                    FontMetrics fontMetrics = jtextComp.getFontMetrics(font);

                    int textWidth = SwingUtilities.computeStringWidth(fontMetrics, text);

                    maxWidth = Math.max(maxWidth, textWidth);
                } else {
                    cellWidth = comp.getPreferredSize().width;

                    // maxWidth = Math.max ( headerWidth, cellWidth );
                    maxWidth = Math.max(maxWidth, cellWidth);
                }
            }

            return (maxWidth + 5 + columnPadding);

        } catch (Exception e) {

            return 0;

        }

    }

    /**
     * racuna maximalnu duzinu najduzeg stringa wrapped texta
     *
     * @param str
     * @return
     */
    private static String getMaximuWrapedString(String str) {
        StringTokenizer strT = new StringTokenizer(str, "\n");
        String max = "";
        String s = "";
        while (strT.hasMoreTokens()) {
            s = strT.nextToken();
            if (s.length() > max.length()) {
                max = s;
            }
        }
        return max;
    }

    /*
    
    ESTA FUNCIONALIDADE FOI CRIADA PARA REALIZAR ALTERAÇÕES NA TABELA FILTRADA E 
    PRESERVAR OM INDEXROW DO TABLE MODEL ORIGINAL
        
     */
    public int obterNumeroDaLinhaTabelaSelecionadaOriginal(
            long Dom,
            LocalDate DataAbertura,
            JTextField DtProducao,
            int NumeroIncremental,
            int NumeroFinal,
            File ArquivoSelecionado
    ) throws IOException {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");

        this.numeroFinal = NumeroFinal;
        this.numeroIncremental = NumeroIncremental;

        new DarkDao(LocalDate.parse(lblDtProd.getText(), fmt).plusDays(1), ArquivoSelecionado, Tabela).getStatus().forEach((var x) -> {

            if (x != null && x.getId() != 0) {

                if (x.getId() == Dom && DataAbertura.isEqual(x.getDataAbertura())) {
                    numeroFinal = numeroIncremental;
                }

            }

            numeroIncremental++;
        });
        return numeroFinal;

    }

    public void removerLinha() {

        Modelo.removeRow(Tabela.getSelectedRow());

    }

    public void limparTabela() {

        DefaultTableModel df = (DefaultTableModel) Tabela.getModel();

        df.setNumRows(0);

        ajustarFormataColunasTabelaConteudo();

    }

    public void exportarConteudoParaCsv(String filePath) {

        try (FileWriter writer = new FileWriter(filePath)) {
            TableModel model = Tabela.getModel();
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

            // Escrever os dados das c�lulas
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

    public void ajustarFormataColunasTabelaConteudo() {

        try {
            if (Tabela.getRowCount() > -1) {

                class RenderizadorInternoCentralizador extends DefaultTableCellRenderer {

                    private static final Color RED = Color.RED;
                    private static final Color WHITE = Color.WHITE;

                    @Override
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

                for (int column = 0; column < Tabela.getColumnCount(); column++) {
                    TableColumn tableColumn = Tabela.getColumnModel().getColumn(column);
                    int preferredWidth = tableColumn.getMinWidth();
                    int maxWidth = tableColumn.getMaxWidth();

                    for (int row = 0; row < Tabela.getRowCount(); row++) {
                        TableCellRenderer cellRenderer = Tabela.getCellRenderer(row, column);
                        Component c = Tabela.prepareRenderer(cellRenderer, row, column);
                        int width = c.getPreferredSize().width + Tabela.getIntercellSpacing().width;
                        preferredWidth = Math.max(preferredWidth, width);

                        // Verificar se o tamanho excede o m�ximo permitido
                        if (preferredWidth >= maxWidth) {
                            preferredWidth = maxWidth;
                            break;
                        }
                    }

                    tableColumn.setPreferredWidth(preferredWidth);

                    Tabela.getColumnModel().getColumn(0).setCellRenderer(new RenderizadorInternoCentralizador());
                    Tabela.getColumnModel().getColumn(1).setCellRenderer(new RenderizadorInternoCentralizador());
                    Tabela.getColumnModel().getColumn(2).setCellRenderer(new RenderizadorInternoCentralizador());
                    Tabela.getColumnModel().getColumn(3).setCellRenderer(new RenderizadorInternoCentralizador());
                    Tabela.getColumnModel().getColumn(4).setCellRenderer(new RenderizadorInternoCentralizador());
                    Tabela.getColumnModel().getColumn(5).setCellRenderer(new RenderizadorInternoCentralizador());
                    Tabela.getColumnModel().getColumn(6).setCellRenderer(new RenderizadorInternoCentralizador());
                    Tabela.getColumnModel().getColumn(7).setCellRenderer(new RenderizadorInternoCentralizador());
                    Tabela.getColumnModel().getColumn(8).setCellRenderer(new RenderizadorInternoCentralizador());

                    UIManager.put("Table.background", new Color(240, 240, 240));
                    UIManager.put("Table.selectionBackground", new Color(0, 120, 215));
                    UIManager.put("Table.selectionForeground", Color.WHITE);
                }

            }

        } catch (Exception e) {

        }

    }

}
