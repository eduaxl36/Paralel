/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import static Adapter.Adapter.Remote;
import dao.DarkDao;
import dao.WhiteDao;
import static datechooser.beans.PermanentBean.dispose;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import msgs.Pbar;
import operations.RemoteDarklistOperations;
import viewClientDarklist.DarklistManagerViewClient;
import static viewClientDarklist.DarklistManagerViewClient.Adaptador;
import static viewClientDarklist.DarklistManagerViewClient.lblDtProd;
import static viewClientDarklist.DarklistManagerViewClient.tbMainViewLst;
import static viewClientDarklist.DarklistManagerViewClient.txt_filtro;
import viewClientDarklist.MenuFile;
import static viewClientDarklist.MenuFile.TableDatasDark;
import static viewClientDarklist.MenuFile.tbDataLog;
import viewClientDarklist.Visualize;
import static viewClientDarklist.Visualize.lblModoFecha;

/**
 *
 * @author Eduardo.Fernando
 */
public class MenuFileController {

    private final MainViewController MainController;
    private final VizualizeController visualizeController;

    public static File SelectedFile;

    public MenuFileController() throws Exception {

        MainController = new MainViewController();
        visualizeController = new VizualizeController();

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

    public void abrirVisualizacaoRetroativa() throws Exception {

        int row = TableDatasDark.getSelectedRow();

        String data = TableDatasDark.getValueAt(row, 0).toString();
        String arquivo = TableDatasDark.getValueAt(row, 1).toString();

        if (Remote instanceof RemoteDarklistOperations) {
            DarklistManagerViewClient.lblmode.setText("Darklist");
        } else {
            DarklistManagerViewClient.lblmode.setText("WhiteList");

        }

        int resposta = JOptionPane.showConfirmDialog(null, "Para el historico del Lista, esta disponible solo para visualizacion!, quiere abrir ?",
                "Confirmacion",
                JOptionPane.YES_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {

            try {

                Remote.downloadArquivoLst(TableDatasDark.getValueAt(TableDatasDark.getSelectedRow(), 1).toString());

                new Visualize().setVisible(true);

                visualizeController.loadListEditMode(data, arquivo);
                lblModoFecha.setText(data);

            } catch (Exception ex) {
                Logger.getLogger(MenuFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void abrirVisualizacaoRetroativaLog() {

        try {

            DefaultTableModel df = (DefaultTableModel) tbMainViewLst.getModel();
            df.setNumRows(0);

            int row = tbDataLog.getSelectedRow();

            String arquivo = tbDataLog.getValueAt(row, 1).toString();

            new Thread() {

                public void run() {

                    try {

                        Pbar.Progresso.setVisible(true);

                        SelectedFile = new File(Adaptador.getPastaTempLogFile() + tbDataLog.getValueAt(tbDataLog.getSelectedRow(), 1));

                        lblModoFecha.setText(tbDataLog.getValueAt(tbDataLog.getSelectedRow(), 1).toString());

                        MainController.carregarLogAlteracoes();

                        DarklistManagerViewClient.lblDtProd.setText(arquivo.substring(0, 8));

                        Pbar.Progresso.setVisible(false);

                    } catch (Exception ex) {
                        Logger.getLogger(MenuFile.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }.start();

        } catch (Exception ex) {
            Logger.getLogger(MenuFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void abrirListaCorrente() {

        try {
            DefaultTableModel df = (DefaultTableModel) tbMainViewLst.getModel();
            df.setNumRows(0);

            new Thread() {

                @Override
                public void run() {

                    try {

                        Pbar.Progresso.setVisible(true);

                        Remote.downloadArquivoLst(TableDatasDark.getValueAt(TableDatasDark.getSelectedRow(), 1).toString());

                        SelectedFile = new File(Adaptador.getPastaTempFile());

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

                        LocalDate DataFt = LocalDate.parse(lblDtProd.getText(), formatter);

                        if (Remote instanceof RemoteDarklistOperations) {
                            new DarkDao(DataFt, SelectedFile, tbMainViewLst).carregarLista();

                        } else {

                            new WhiteDao(DataFt, SelectedFile, tbMainViewLst).carregarLista();

                        }

                        Pbar.Progresso.setVisible(false);

                    } catch (Exception ex) {
                        Logger.getLogger(MenuFile.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }.start();

        } catch (Exception ex) {
            Logger.getLogger(MenuFile.class.getName()).log(Level.SEVERE, null, ex);
        }

        dispose();

    }

    public void inpecaoEventoCliqueList() throws Exception {

        int row = TableDatasDark.getSelectedRow();
        String data = TableDatasDark.getValueAt(row, 0).toString();

        String dataP = DarklistManagerViewClient.lblDtProd.getText();
        if (Remote instanceof RemoteDarklistOperations) {
            DarklistManagerViewClient.lblmode.setText("Darklist");
        } else {
            DarklistManagerViewClient.lblmode.setText("WhiteList");

        }

        if (!(data.equals(dataP))) {

            abrirVisualizacaoRetroativa();

        } else {

            abrirListaCorrente();

        }

    }

    public void tableListListener() throws Exception {

        Map<String, String> MapDark = Remote.obterListaArquivosDataArquivo();

        TreeMap MapaOrdenado = new TreeMap(MapDark);

        SwingUtilities.invokeLater(() -> {

            DefaultTableModel tableModel = (DefaultTableModel) TableDatasDark.getModel();

            MapaOrdenado.forEach((data, coluna) -> tableModel.addRow(new Object[]{data, coluna}));

        });

    }

    public void tableLogListener() throws Exception {

        Map<String, String> MapLog = Remote.obterListaArquivosLogDataArquivo();

        TreeMap MapaOrdenado = new TreeMap(MapLog);

        SwingUtilities.invokeLater(() -> {

            DefaultTableModel tableModel = (DefaultTableModel) tbDataLog.getModel();

            MapaOrdenado.forEach((data, coluna) -> tableModel.addRow(new Object[]{data, coluna}));

        });

    }

}
