/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Util.MainTableUtil;
import static controller.MainViewController.NumeroOriginalSelecionadoTabela;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang.time.DateUtils;
import viewClient.CloseMode;
import static viewClient.CloseMode.calcularDiferencaEmMinutos;
import static viewClient.CloseMode.dateChooserCombo;
import static viewClient.CloseMode.lblUserName;
import static viewClient.CloseMode.txt_Comment;
import static viewClient.DarklistManagerViewClient.lblDtProd;
import static viewClient.DarklistManagerViewClient.tbMainViewDarkList;
import static viewClient.DarklistManagerViewClient.txt_filtro;

/**
 *
 * @author eduardo.fernando
 */
public class CloseModeController {

    public static String oldValueComment;
    private MainViewController MainController;

    public CloseModeController() throws Exception {
        this.MainController = new MainViewController();
    }

      public  String tratarComentario(String Comment) {

        String ValTemp = Comment.replaceAll("\"", "");

        return "\"" + ValTemp + "\"";

    }
    
    public void inicializacoes() throws Exception{
    

        LocalDate datafn = LocalDate.parse(tbMainViewDarkList.getValueAt(tbMainViewDarkList.getSelectedRow(), 2).toString());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(java.util.Date.from(datafn.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        dateChooserCombo.setSelectedDate(calendar);

        txt_Comment.setText("" + tbMainViewDarkList.getValueAt(tbMainViewDarkList.getSelectedRow(), 3));

        String username = System.getProperty("user.name");
        lblUserName.setText(username);
        CloseModeController.oldValueComment = txt_Comment.getText();
    
    
    }
    
    
    
    
    
    public boolean identificarAlteracaoNoComentario(String NovoValor) {

        return oldValueComment.equals(NovoValor);

    }

    public boolean verificarFechamentoInferiorAbertura() throws ParseException {

        LocalDate dataInicio = LocalDate.parse(tbMainViewDarkList.getValueAt(tbMainViewDarkList.getSelectedRow(), 1).toString());

        LocalDate dataf = DateUtils.toCalendar(dateChooserCombo.getSelectedDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return dataf.isBefore(dataInicio);

    }

    public void cambiar() {

        int resposta = JOptionPane.showConfirmDialog(null, "Desea realmente cambiar la fecha de cerramiento?",
                "Confirmacion",
                JOptionPane.YES_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {

            try {
                if (fecharLinha()) {

                    txt_filtro.setText("");
                    new MainTableUtil(tbMainViewDarkList).ajustarFormataColunasTabelaConteudo();
                    MainController.filtrarTabelaCriterio();
                   

                }

            } catch (ParseException ex) {
                Logger.getLogger(CloseMode.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (resposta == JOptionPane.NO_OPTION) {

            JOptionPane.showMessageDialog(null, "Você selecionou 'Não'.");

        }

    }

    public boolean fecharLinha() throws ParseException {
        txt_filtro.setText("");

        LocalDate dataInicio = LocalDate.parse(tbMainViewDarkList.getValueAt(tbMainViewDarkList.getSelectedRow(), 1).toString());
        Date RawProductionDate = new SimpleDateFormat("yyyyMMdd").parse(lblDtProd.getText());
        LocalDate ProducionDate = RawProductionDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        String username = System.getProperty("user.name");

        LocalDate dataf = DateUtils.toCalendar(dateChooserCombo.getSelectedDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        boolean MaiorOUMaiorAbertura = ProducionDate.isEqual(dataInicio) || ProducionDate.isAfter(dataInicio);
        boolean MenorQueFechamentoOUIGUAL = ProducionDate.isEqual(dataf) || ProducionDate.isBefore(dataf);

        boolean validator = (MaiorOUMaiorAbertura && MenorQueFechamentoOUIGUAL);

        String status = definidorDeAcoes(validator);

        DefaultTableModel df = (DefaultTableModel) tbMainViewDarkList.getModel();

        if (!verificarFechamentoInferiorAbertura()) {

            df.setValueAt(dataf.toString(), NumeroOriginalSelecionadoTabela, 2);
            df.setValueAt(validator, NumeroOriginalSelecionadoTabela, 4);
            df.setValueAt("En Aprobacion", NumeroOriginalSelecionadoTabela, 5);
            df.setValueAt(username, NumeroOriginalSelecionadoTabela, 6);
            df.setValueAt(tratarComentario(txt_Comment.getText()), NumeroOriginalSelecionadoTabela, 3);
            df.setValueAt(calcularDiferencaEmMinutos(ProducionDate, dataf), NumeroOriginalSelecionadoTabela, 7);
            df.setValueAt(status, NumeroOriginalSelecionadoTabela, 8);

            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se puede cerrar con una fecha inferior al del inicio!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    public String definidorDeAcoes(boolean tipo) {

        boolean identificarAlteracaoNoComentario = identificarAlteracaoNoComentario(txt_Comment.getText());
        String val = "";

        if (tipo == false) {

            val = "Hogar en Produccion";

        } else {

            val = "Cambio en fecha";

        }

        if (identificarAlteracaoNoComentario == false) {

            val += "/Cambio en Descritivo";

        }

        return val;

    }

}
