/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import static Adapter.Adapter.Remote;
import static Adapter.Adapter.localOperations;
import Util.MainTableUtil;
import viewClientDarklist.CambioFlagView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import msgs.Pbar;
import static operations.RemoteOperations.Flags;
import org.apache.commons.io.FileUtils;
import pathManager.Roots;
import viewClientDarklist.CloseMode;
import static viewClientDarklist.CloseMode.instanciaMudancaAdicao;
import viewClientDarklist.DarklistManagerViewClient;
import static viewClientDarklist.DarklistManagerViewClient.Adaptador;
import static viewClientDarklist.DarklistManagerViewClient.cbTipo;
import static viewClientDarklist.DarklistManagerViewClient.lblDtProd;
import static viewClientDarklist.DarklistManagerViewClient.tbMainViewLst;
import static viewClientDarklist.DarklistManagerViewClient.txt_filtro;
import viewClientDarklist.MenuFile;
import viewClientDarklist.ViewDarkAdd;
import static viewClientDarklist.ViewDarkAdd.instanciaAbertaAdicao;

/**
 *
 * @author eduardo.fernando
 */
public final class MainViewController {

    private int Incremental;

    private int FinalNumber;

    private final DateTimeFormatter Formatador;

    static boolean ValidadorNovosCambios = true;

    static boolean NaoPermitirCambio = true;

    static boolean ValidadorAprovacao = false;

    private final MainTableUtil UtilMainTable;

    public static int NumeroOriginalSelecionadoTabela;

    public ActionListener AbrirAdicao = (ActionEvent e) -> instanciaAdicaoChecker();

    public ActionListener AbrirCloseMode = (ActionEvent e) -> instanciaMudancaChecker();

    public File SelectedFile;

    public void loadLogFTP() {

        Remote.uploadLogAlteracoes(tbMainViewLst, lblDtProd.getText());

    }

    public void loadFlagObserver() throws Exception {

        Remote.uploadFlag();

    }

    public void acaoLoadAlteracoes() {

        try {
            
            Remote.obterListaDeFlags(lblDtProd.getText());
            Remote.gerarFlag(lblDtProd.getText());
            loadLogFTP();
            loadFlagObserver();

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public void realizaOperacaoEmListaLocal() throws Exception {

        localOperations.montarLista();

        localOperations.criarDataProducao();

        localOperations.subirLabelCorrespondente();
        
        localOperations.subirListaCorrespondente(lblDtProd.getText());

    }

    
    public void atualizarLista() {

        try {

            realizaOperacaoEmListaLocal();

            Remote.obterListaDeFlags(lblDtProd.getText());

            Remote.contestarFlag(lblDtProd.getText());
            
            loadFlagObserver();

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public void verificaCliquesTabelaAcoes(java.awt.event.MouseEvent evt) {

        ///RESETAR 
        FinalNumber = 0;
        Incremental = 0;

        if (SwingUtilities.isRightMouseButton(evt)) {

            if (tbMainViewLst.getSelectedRow() > -1) {

                try {
                    long domselecionado = Long.parseLong(tbMainViewLst.getValueAt(tbMainViewLst.getSelectedRow(), 0).toString());
                    LocalDate dataSelecioanda = LocalDate.parse(tbMainViewLst.getValueAt(tbMainViewLst.getSelectedRow(), 1).toString());

                    SelectedFile = new File(Adaptador.getPastaTempFile());

                    NumeroOriginalSelecionadoTabela
                            = new MainTableUtil(tbMainViewLst).obterNumeroDaLinhaTabelaSelecionadaOriginal(domselecionado,
                                    dataSelecioanda,
                                    txt_filtro,
                                    Incremental,
                                    FinalNumber,
                                    SelectedFile);

                    String Verificador = tbMainViewLst.getValueAt(tbMainViewLst.getSelectedRow(), 5).toString();

                    ValidadorAprovacao = Verificador.toLowerCase().matches(".*apro.*");

                    NaoPermitirCambio = !Verificador.equals("No permitido cambios");

                    ValidadorNovosCambios = Verificador.contains("Nueva");

                    UtilMainTable.mostrarMenuFlutuante(evt.getComponent(), evt.getX(), evt.getY(), AbrirCloseMode, AbrirAdicao, tbMainViewLst, ValidadorAprovacao, ValidadorNovosCambios, NaoPermitirCambio);

                } catch (Exception ex) {

                    ex.printStackTrace();
                }

            }

        }
    }

    public void carregarLogAlteracoes() throws Exception {

        DarklistManagerViewClient.btnView.setEnabled(false);

        int LinhaSelecionada = MenuFile.tbDataLog.getSelectedRow();

        String obterValorSelecionado = MenuFile.tbDataLog.getValueAt(LinhaSelecionada, 1).toString();

        String LogLocal = Adaptador.getPastaTempLogFile() + obterValorSelecionado;

        Remote.downloadArquivoLogHistorico(obterValorSelecionado);

        localOperations.carregarLog(LogLocal);

    }

    public void abrirMenuArquivos() {

        try {

            new Thread() {

                @Override
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

        Flags.clear();

        UtilMainTable = new MainTableUtil(tbMainViewLst);

        anularEnterDentroFiltro();

        Adaptador.iniciaConexao();

        Remote.downloadDiaProducaoNumeralLabel();

        lblDtProd.setText(FileUtils.readFileToString(new File(Roots.PRODUCAO_DIARIA_DIA_TEMP_WHITE.getCaminho())));

    }

    public void cliqueTabela(java.awt.event.MouseEvent evt) {

        ///RESETAR 
        FinalNumber = 0;
        Incremental = 0;

        //
        if (SwingUtilities.isRightMouseButton(evt)) {

            if (tbMainViewLst.getSelectedRow() > -1) {

                try {
                    long domselecionado = Long.parseLong(tbMainViewLst.getValueAt(tbMainViewLst.getSelectedRow(), 0).toString());
                    LocalDate dataSelecioanda = LocalDate.parse(tbMainViewLst.getValueAt(tbMainViewLst.getSelectedRow(), 1).toString());
                    SelectedFile = new File(Roots.DARK_PRODUCAO_DIARIA_DIA_TEMP.getCaminho());
                    NumeroOriginalSelecionadoTabela
                            = new MainTableUtil(tbMainViewLst).obterNumeroDaLinhaTabelaSelecionadaOriginal(domselecionado,
                                    dataSelecioanda,
                                    txt_filtro,
                                    Incremental,
                                    FinalNumber,
                                    SelectedFile);

                    String Verificador = tbMainViewLst.getValueAt(tbMainViewLst.getSelectedRow(), 5).toString();

                    ValidadorAprovacao = Verificador.toLowerCase().matches(".*apro.*");

                    NaoPermitirCambio = !Verificador.equals("No permitido cambios");

                    ValidadorNovosCambios = Verificador.contains("Nueva Linea/Aprobacion");

                    UtilMainTable.mostrarMenuFlutuante(evt.getComponent(), evt.getX(), evt.getY(), AbrirCloseMode, AbrirAdicao, tbMainViewLst, ValidadorAprovacao, ValidadorNovosCambios, NaoPermitirCambio);
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

            try {
                instanciaAbertaAdicao = true;
                new ViewDarkAdd().setVisible(true);
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void instanciaMudancaChecker() {

        if (!instanciaMudancaAdicao) {
            try {
                instanciaMudancaAdicao = true;
                new CloseMode().setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void abrirFlagView() throws Exception {

        Remote.obterListaDeFlags(lblDtProd.getText());
        Remote.gerarFlag(lblDtProd.getText());
        new CambioFlagView().setVisible(true);

    }

    public void filtrarTabelaCriterio() {
        String searchText = txt_filtro.getText();

        if (searchText.isEmpty()) {
            tbMainViewLst.setRowSorter(null);
        } else {
            DefaultTableModel model = (DefaultTableModel) tbMainViewLst.getModel();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            tbMainViewLst.setRowSorter(sorter);

            List<RowSorter.SortKey> sortKeys = new ArrayList<>();
            sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
            sorter.setSortKeys(sortKeys);

            RowFilter<DefaultTableModel, Integer> filter = RowFilter.regexFilter("(?i)" + searchText, cbTipo.getSelectedIndex());
            sorter.setRowFilter(filter);
        }
    }

}
