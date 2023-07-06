/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Production.ProductionOperations;
import Util.MainTableUtil;
import dao.ListDao;
import dao.LogDao;
import flag.CambioFlagView;
import flag.FlagOperations;
import static flag.FlagOperations.Flags;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import msgs.Pbar;
import org.apache.commons.io.FileUtils;
import pathManager.Manager;
import static sftp.Inicializacao.Remote;
import viewClient.CloseMode;
import static viewClient.CloseMode.instanciaMudancaAdicao;
import viewClient.DarklistManagerViewClient;
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
public final class MainViewController {

    private int Incremental;
    private int FinalNumber;

    private ListDao Darklist;

    private final DateTimeFormatter Formatador;

    static boolean ValidadorNovosCambios = true;
    static boolean NaoPermitirCambio = true;
    static boolean ValidadorAprovacao = false;

    private final MainTableUtil UtilMainTable;

    public static int NumeroOriginalSelecionadoTabela;

    public ActionListener AbrirAdicao = (ActionEvent e) -> instanciaAdicaoChecker();
    
    public ActionListener AbrirCloseMode = (ActionEvent e) -> instanciaMudancaChecker();

    public static final String PATH_LOG_DIARIO = Manager.getRoot().get("caminho_local_temp_producao_dia");

    public File SelectedFile;

    public void verificaCliquesTabelaAcoes(java.awt.event.MouseEvent evt) {

        ///RESETAR 
        FinalNumber = 0;
        Incremental = 0;

        //
        if (SwingUtilities.isRightMouseButton(evt)) {

            if (tbMainViewDarkList.getSelectedRow() > -1) {

                try {
                    long domselecionado = Long.parseLong(tbMainViewDarkList.getValueAt(tbMainViewDarkList.getSelectedRow(), 0).toString());
                    LocalDate dataSelecioanda = LocalDate.parse(tbMainViewDarkList.getValueAt(tbMainViewDarkList.getSelectedRow(), 1).toString());

                    SelectedFile = new File(Manager.getRoot().get("caminho_local_temp_darkFile"));

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

                    UtilMainTable.mostrarMenuFlutuante(evt.getComponent(), evt.getX(), evt.getY(), new MainViewController().AbrirCloseMode, new MainViewController().AbrirAdicao, tbMainViewDarkList, ValidadorAprovacao, ValidadorNovosCambios, NaoPermitirCambio);
                } catch (IOException ex) {
                    Logger.getLogger(DarklistManagerViewClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(DarklistManagerViewClient.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
    }

    public void carregarLogAlteracoes() throws IOException, Exception {

        DarklistManagerViewClient.btnView.setEnabled(false);
        
        int LinhaSelecionada = MenuFile.tbDataLog.getSelectedRow();

        String obterValorSelecionado = MenuFile.tbDataLog.getValueAt(LinhaSelecionada, 1).toString();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");

        String LogLocal = Manager.getRoot().get("caminho_local_temp_logFile") + obterValorSelecionado;

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

        Flags.clear();

        UtilMainTable = new MainTableUtil(tbMainViewDarkList);

        anularEnterDentroFiltro();

        new ProductionOperations().downloadUltimoProducaoLiteral();

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
                    SelectedFile = new File(PATH_LOG_DIARIO);

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

    public void abrirFlagView() {

        try {

            new FlagOperations().obterListaDeFlags(lblDtProd.getText());
            new FlagOperations().gerarFlag(lblDtProd.getText());

        } catch (Exception ex) {
            Logger.getLogger(DarklistManagerViewClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        new CambioFlagView().setVisible(true);

    }

    public void filtrarTabelaCriterio() {
        String searchText = txt_filtro.getText();

        if (searchText.isEmpty()) {
            tbMainViewDarkList.setRowSorter(null);
        } else {
            DefaultTableModel model = (DefaultTableModel) tbMainViewDarkList.getModel();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            tbMainViewDarkList.setRowSorter(sorter);

            List<RowSorter.SortKey> sortKeys = new ArrayList<>();
            sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
            sorter.setSortKeys(sortKeys);

            RowFilter<DefaultTableModel, Integer> filter = RowFilter.regexFilter("(?i)" + searchText, 0);
            sorter.setRowFilter(filter);
        }
    }

}
