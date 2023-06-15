/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.StringTokenizer;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import javax.swing.text.View;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Eduardo.Fernando
 */
public class UtilTable {

    private static UtilTable Instancia;
    
    private  UtilTable (){
    
    }
    
    public static UtilTable getInstance(){
    
    Instancia = new UtilTable();
    return Instancia;
    
    }
    
    
    public void realizaAjuste(JTable Tabela) {

        Tabela.setAutoResizeMode(Tabela.AUTO_RESIZE_OFF);
        Tabela.setPreferredSize(null);
        autoResizeTable(Tabela, true, 2);

    }

    public int autoResizeTable(JTable aTable, boolean includeColumnHeaderWidth, int columnPadding) {

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
}
