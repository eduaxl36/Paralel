/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import br.com.kantar.pathManager.Manager;
import dao.DarklistDao1;
import static datechooser.beans.PermanentBean.dispose;
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
import viewClient.DarklistManagerViewClient;
import static viewClient.DarklistManagerViewClient.carregarLogAlteracoes;
import static viewClient.DarklistManagerViewClient.lblDtProd;
import static viewClient.DarklistManagerViewClient.tbMainViewDarkList;
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

  
    public static File SelectedFile;

    public MenuFileController() throws Exception {


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

    public static void acaoParaLog() {

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

                        carregarLogAlteracoes();

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

                public void run() {

                    try {
                       
                      

                        Pbar.Progresso.setVisible(true);

                          DarklistManagerViewClient.Remote.downloadArquivoLst(TableDatasDark.getValueAt(TableDatasDark.getSelectedRow(), 1).toString());

                        SelectedFile = new File(Manager.getRoot().get("caminho_local_temp_darkFile"));

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

                        LocalDate DataFt = LocalDate.parse(lblDtProd.getText(), formatter);

                        new DarklistDao1(DataFt, SelectedFile, tbMainViewDarkList).carregarDarkList();

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

        Map<String, String> MapDark =   DarklistManagerViewClient.Remote.obterListaArquivosDataArquivo();

        SwingUtilities.invokeLater(() -> {

            DefaultTableModel tableModel = (DefaultTableModel) TableDatasDark.getModel();

            MapDark.forEach((data, coluna) -> tableModel.addRow(new Object[]{data, coluna}));

        });

    }

    public static void tableLogListener() throws Exception {

        Map<String, String> MapLog =   DarklistManagerViewClient.Remote.obterListaArquivosLogDataArquivo();

        SwingUtilities.invokeLater(() -> {

            DefaultTableModel tableModel = (DefaultTableModel) tbDataLog.getModel();

            MapLog.forEach((data, coluna) -> tableModel.addRow(new Object[]{data, coluna}));

        });

    }

}
