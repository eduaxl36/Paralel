/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import dao.ListDao;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import static viewClient.DarklistManagerViewClient.lblDtProd;
import static viewClient.DarklistManagerViewClient.tbMainViewDarkList;

/**
 *
 * @author eduardo.fernando
 */
public final class MainTableUtil {

    int numeroIncremental;
    int numeroFinal;
    JTable Tabela;

    DefaultTableModel Modelo;

    JPopupMenu PopMenu;

    JMenuItem MenuItem1;
    JMenuItem MenuItem2;
    JMenuItem MenuItem3;
    JMenuItem MenuItem4;

    ImageIcon Img1;
    ImageIcon Img2;
    ImageIcon Img3;
    ImageIcon Img4;

    public MainTableUtil(JTable Tabela) {

        PopMenu = new JPopupMenu();
        this.Tabela = Tabela;
        Modelo = (DefaultTableModel) Tabela.getModel();

        montaMenuCompleto();

    }

    public void construirLabels() {

        MenuItem1 = new JMenuItem("Anadir hogar en Darklist");
        MenuItem2 = new JMenuItem("Cambiar");
        MenuItem3 = new JMenuItem("Borrar Linea");
        MenuItem4 = new JMenuItem("Aprovar Cambio");

    }

    public void montarIcones() {

        Img1 = new ImageIcon(getClass().getResource("/img/darklist.png"));
        Img2 = new ImageIcon(getClass().getResource("/img/allow.png"));
        Img3 = new ImageIcon(getClass().getResource("/img/delete.png"));
        Img4 = new ImageIcon(getClass().getResource("/img/allow.png"));

    }

    public void adaptarIconesMenu() {

        MenuItem1.setIcon(Img1);
        MenuItem2.setIcon(Img2);
        MenuItem3.setIcon(Img3);
        MenuItem4.setIcon(Img4);

    }

    public void ajustaMenuComSeparadores() {

        PopMenu.add(MenuItem1);
        PopMenu.addSeparator();
        PopMenu.add(MenuItem2);
        PopMenu.addSeparator();
        PopMenu.add(MenuItem3);
        PopMenu.addSeparator();
        PopMenu.add(MenuItem4);
        PopMenu.addSeparator();

    }

    public void montaMenuCompleto() {

        montarIcones();
        construirLabels();
        adaptarIconesMenu();
        ajustaMenuComSeparadores();

    }

    public void mostrarMenuFlutuante(Component component,
            int x, int y,
            ActionListener Acao1,
            ActionListener Acao2,
            JTable Tabela,
            boolean AprovacaoCambio,
            boolean AprovacaoNuevaLinea,
            boolean NegarCambio
    ) {

        MenuItem2.addActionListener((e) -> {
            Acao1.actionPerformed(e);

        });
        MenuItem1.addActionListener((e) -> {
            Acao2.actionPerformed(e);

        });

        MenuItem3.addActionListener((ActionEvent e) -> removerLinha());

        MenuItem4.addActionListener((ActionEvent e) -> Tabela.setValueAt("Cambio Aprovado", Tabela.getSelectedRow(), 5));

        MenuItem4.setEnabled(AprovacaoCambio);

        MenuItem3.setEnabled(AprovacaoNuevaLinea);

        MenuItem2.setEnabled(NegarCambio);

        PopMenu.show(component, x, y);
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
        
        new ListDao(LocalDate.parse(lblDtProd.getText(), fmt).plusDays(1), ArquivoSelecionado,Tabela).getStatus().forEach((var x) -> {

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

        
        if(Tabela.getRowCount()>-1){

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
        
    
    }
    
    
}
