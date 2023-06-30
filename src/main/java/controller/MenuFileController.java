/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import pathManager.Manager;
import dao.ListDao;
import static datechooser.beans.PermanentBean.dispose;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import msgs.Pbar;
import static sftp.Inicializacao.Remote;
import viewClient.DarklistManagerViewClient;
import static viewClient.DarklistManagerViewClient.lblDtProd;
import static viewClient.DarklistManagerViewClient.tbMainViewDarkList;
import static viewClient.DarklistManagerViewClient.txt_filtro;
import viewClient.MenuFile;
import static viewClient.MenuFile.TableDatasDark;
import static viewClient.MenuFile.tbDataLog;
import viewClient.Visualize;
import static viewClient.Visualize.loadDarkListEditMode;

/**
 *
 * @author Eduardo.Fernando
 */
public class MenuFileController {

    private MainViewController MainController;

    public static File SelectedFile;

    public MenuFileController() throws Exception {

        MainController = new MainViewController();

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

    public static void acaoParaDarkRetroativo() {

        int row = TableDatasDark.getSelectedRow();

        String data = TableDatasDark.getValueAt(row, 0).toString();
        String arquivo = TableDatasDark.getValueAt(row, 1).toString();

        DarklistManagerViewClient.lblmode.setText("Darklist");

        int resposta = JOptionPane.showConfirmDialog(null, "Para el historico del dark, esta disponible solo para visualizacion!, quiere abrir ?",
                "Confirmacion",
                JOptionPane.YES_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {

            try {

                new Visualize().setVisible(true);
                loadDarkListEditMode(data, arquivo);

            } catch (Exception ex) {
                Logger.getLogger(MenuFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void acaoParaLog() {

        try {

            DefaultTableModel df = (DefaultTableModel) tbMainViewDarkList.getModel();
            df.setNumRows(0);

            int row = tbDataLog.getSelectedRow();

            String arquivo = tbDataLog.getValueAt(row, 1).toString();

            new Thread() {

                public void run() {

                    try {

                        Pbar.Progresso.setVisible(true);

                        SelectedFile = new File(Manager.getRoot().get("caminho_local_temp_logFile") + tbDataLog.getValueAt(tbDataLog.getSelectedRow(), 1));

                        MainController.carregarLogAlteracoes();

                        DarklistManagerViewClient.lblmode.setText("Log View");

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

    public static void acaoParaLstAtual() {

        try {
            DefaultTableModel df = (DefaultTableModel) tbMainViewDarkList.getModel();
            df.setNumRows(0);

            new Thread() {

                @Override
                public void run() {

                    try {

                        Pbar.Progresso.setVisible(true);

                        Remote.downloadArquivoLst(TableDatasDark.getValueAt(TableDatasDark.getSelectedRow(), 1).toString());

                        SelectedFile = new File(Manager.getRoot().get("caminho_local_temp_darkFile"));

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

                        LocalDate DataFt = LocalDate.parse(lblDtProd.getText(), formatter);

                        new ListDao(DataFt, SelectedFile, tbMainViewDarkList).carregarDarkList();

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

    public static void inpecaoEventoCliqueList() {

        int row = TableDatasDark.getSelectedRow();
        String data = TableDatasDark.getValueAt(row, 0).toString();

        String dataP = DarklistManagerViewClient.lblDtProd.getText();
        DarklistManagerViewClient.lblmode.setText("Darklist");

        if (!(data.equals(dataP))) {

            acaoParaDarkRetroativo();

        } else {

            acaoParaLstAtual();

        }

    }

    public static void tableListListener() throws Exception {

        Map<String, String> MapDark = Remote.obterListaArquivosDataArquivo();

        SwingUtilities.invokeLater(() -> {

            DefaultTableModel tableModel = (DefaultTableModel) TableDatasDark.getModel();

            MapDark.forEach((data, coluna) -> tableModel.addRow(new Object[]{data, coluna}));

        });

    }

    public static void tableLogListener() throws Exception {

        Map<String, String> MapLog = Remote.obterListaArquivosLogDataArquivo();

        SwingUtilities.invokeLater(() -> {

            DefaultTableModel tableModel = (DefaultTableModel) tbDataLog.getModel();

            MapLog.forEach((data, coluna) -> tableModel.addRow(new Object[]{data, coluna}));

        });

    }

}
