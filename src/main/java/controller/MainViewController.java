/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Util.MainTableUtil;
import dao.ListDao;
import dao.LogDao;
import static flag.FlagOperations.Flags;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import msgs.Pbar;
import org.apache.commons.io.FileUtils;
import pathManager.Manager;
import sftp.ConfiguracoesSFTPModel;
import sftp.RemoteOperations;
import viewClient.CloseMode;
import static viewClient.CloseMode.instanciaMudancaAdicao;
import viewClient.DarklistManagerViewClient;
import static viewClient.DarklistManagerViewClient.cbTipo;
import static viewClient.DarklistManagerViewClient.lblDtProd;
import static viewClient.DarklistManagerViewClient.tbMainViewDarkList;
import static viewClient.DarklistManagerViewClient.txt_filtro;
import viewClient.MenuFile;
import viewClient.ViewDarkAdd;
import static viewClient.ViewDarkAdd.instanciaAbertaAdicao;

/**
 *
 * @author eduardo.fernando
 */
public class MainViewController {

    private int Incremental;
    private int FinalNumber;

    private ListDao Darklist;

    private DateTimeFormatter Formatador;

    static boolean ValidadorNovosCambios = true;
    static boolean NaoPermitirCambio = true;
    static boolean ValidadorAprovacao = false;

    private final MainTableUtil UtilMainTable;

    static int NumeroOriginalSelecionadoTabela;

    public ActionListener AbrirAdicao = (ActionEvent e) -> instanciaAdicaoChecker();
    public ActionListener AbrirCloseMode = (ActionEvent e) -> instanciaMudancaChecker();

    public static final String PATH_LOG_DIARIO = Manager.getRoot().get("caminho_local_temp_producao_dia");

    public static RemoteOperations Remote;
    
     public  File SelectedFile;

    
    
   
    public void carregarLogAlteracoes() throws IOException, Exception {

        
        int LinhaSelecionada=    MenuFile.tbDataLog.getSelectedRow();
     
        String obterValorSelecionado = MenuFile.tbDataLog.getValueAt(LinhaSelecionada, 1).toString();
             
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
        
        String LogLocal = Manager.getRoot().get("caminho_local_temp_logFile")+obterValorSelecionado ;
        
        Remote.downloadArquivoLogHistorico(obterValorSelecionado);
        
        new LogDao(LocalDate.parse(lblDtProd.getText(), fmt).plusDays(1), new File(LogLocal), tbMainViewDarkList)
                .preencherTabela();

    }

    public void abrirMenuArquivos() {

        try {

            new Thread() {

                public void run() {

                    try {
                        Pbar.Progresso.setVisible(true);

                        new MenuFile().setVisible(true);

                        Pbar.Progresso.setVisible(false);

                    } catch (Exception ex) {
                        Logger.getLogger(DarklistManagerViewClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }.start();

        } catch (Exception ex) {
            Logger.getLogger(DarklistManagerViewClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public MainViewController() throws Exception {

        Formatador = DateTimeFormatter.ofPattern("yyyyMMdd");

        Remote = new RemoteOperations(new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22));

        Flags.clear();

        UtilMainTable = new MainTableUtil(tbMainViewDarkList);

        anularEnterDentroFiltro();

        Remote.downloadNumeralDia();

        lblDtProd.setText(FileUtils.readFileToString(new File(PATH_LOG_DIARIO)));

    }

    public void cliqueTabela(java.awt.event.MouseEvent evt) {

        ///RESETAR 
        FinalNumber = 0;
        Incremental = 0;

        //
        if (SwingUtilities.isRightMouseButton(evt)) {

            if (tbMainViewDarkList.getSelectedRow() > -1) {

                try {
                    long domselecionado = Long.parseLong(tbMainViewDarkList.getValueAt(tbMainViewDarkList.getSelectedRow(), 0).toString());
                    LocalDate dataSelecioanda = LocalDate.parse(tbMainViewDarkList.getValueAt(tbMainViewDarkList.getSelectedRow(), 1).toString());

                    NumeroOriginalSelecionadoTabela
                            = new MainTableUtil(tbMainViewDarkList).obterNumeroDaLinhaTabelaSelecionadaOriginal(domselecionado,
                                    dataSelecioanda,
                                    txt_filtro,
                                    Incremental,
                                    FinalNumber,
                                    SelectedFile);

                    String Verificador = tbMainViewDarkList.getValueAt(tbMainViewDarkList.getSelectedRow(), 5).toString();

                    ValidadorAprovacao = Verificador.toLowerCase().matches(".*apro.*");

                    NaoPermitirCambio = !Verificador.equals("No permitido cambios");

                    ValidadorNovosCambios = Verificador.contains("Nueva Linea/Aprobacion");

                    UtilMainTable.mostrarMenuFlutuante(evt.getComponent(), evt.getX(), evt.getY(), AbrirCloseMode, AbrirAdicao, tbMainViewDarkList, ValidadorAprovacao, ValidadorNovosCambios, NaoPermitirCambio);
                } catch (IOException ex) {
                    Logger.getLogger(DarklistManagerViewClient.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
    }

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

    public void instanciaAdicaoChecker() {

        if (!instanciaAbertaAdicao) {
            instanciaAbertaAdicao = true;
            new ViewDarkAdd().setVisible(true);
        }
        else{
        
            System.out.println("dsadas");
        
        }

    }

    public void instanciaMudancaChecker() {

        if (!instanciaMudancaAdicao) {
            instanciaMudancaAdicao = true;
            new CloseMode().setVisible(true);
        }

    }

    

    public static void filtrarTabelaCriterio() {

        try {
            String searchText = txt_filtro.getText();
            TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) tbMainViewDarkList.getRowSorter();

            // Verifica se o sorter não está nulo e se está inicializado
            if (sorter == null) {
                // Se o sorter for nulo, cria um novo e define-o como o RowSorter da tabela
                sorter = new TableRowSorter<>((DefaultTableModel) tbMainViewDarkList.getModel());
                tbMainViewDarkList.setRowSorter(sorter);
            }

            // Verifica se o sorter já está inicializado
            if (sorter.getSortKeys().isEmpty()) {
                // Define uma ordem de classificação padrão para evitar o erro de referência nula
                sorter.setSortKeys(List.of(new RowSorter.SortKey(cbTipo.getSelectedIndex(), SortOrder.ASCENDING)));

                // Define o filtro para a coluna selecionada (índice baseado no ComboBox)
                sorter.setRowFilter(RowFilter.regexFilter(searchText, cbTipo.getSelectedIndex()));

            }
        } catch (Exception e) {
        }

    }



}
