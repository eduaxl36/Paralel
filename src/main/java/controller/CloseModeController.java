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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang.time.DateUtils;
import static viewClientDarklist.CloseMode.dateChooserCombo;
import static viewClientDarklist.CloseMode.lblUserName;
import static viewClientDarklist.CloseMode.txt_Comment;
import viewClientDarklist.DarklistManagerViewClient;
import static viewClientDarklist.DarklistManagerViewClient.Adaptador;
import static viewClientDarklist.DarklistManagerViewClient.lblDtProd;
import static viewClientDarklist.DarklistManagerViewClient.tbMainViewLst;
import static viewClientDarklist.DarklistManagerViewClient.txt_filtro;

/**
 *
 * @author eduardo.fernando
 */
public class CloseModeController {

    private String oldValueComment;
    private final MainViewController MainController;
    private final DefaultTableModel Modelo;

    public CloseModeController() throws Exception {
        this.MainController = new MainViewController();
        Modelo = (DefaultTableModel) tbMainViewLst.getModel();
    }

    public String tratarComentario(String Comment) {

        String ValTemp = Comment.replaceAll("\"", "");

        return "\"" + ValTemp + "\"";

    }

    

    
    public void obterDadosSelecionadosTabela() throws Exception {

        LocalDate datafn = LocalDate.parse(tbMainViewLst.getValueAt(tbMainViewLst.getSelectedRow(), 2).toString());

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(java.util.Date.from(datafn.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        dateChooserCombo.setSelectedDate(calendar);

        txt_Comment.setText("" + tbMainViewLst.getValueAt(tbMainViewLst.getSelectedRow(), 3));

        lblUserName.setText(DarklistManagerViewClient.Adaptador.usuarioAtivo());

        oldValueComment = txt_Comment.getText();

    }

    public long calcularDiferencaEmMinutos(LocalDate data1, LocalDate data2) {

        LocalDateTime dateTime1 = LocalDateTime.of(data1, LocalTime.MIN);
        LocalDateTime dateTime2 = LocalDateTime.of(data2, LocalTime.MIN);
        return ChronoUnit.DAYS.between(dateTime1, dateTime2);
    }

    public boolean identificarAlteracaoNoComentario(String NovoValor) {

        return oldValueComment.equals(NovoValor);

    }

    public boolean verificarFechamentoInferiorAbertura() throws ParseException {

        LocalDate dataInicio = LocalDate.parse(tbMainViewLst.getValueAt(tbMainViewLst.getSelectedRow(), 1).toString());

        LocalDate dataf = DateUtils.toCalendar(dateChooserCombo.getSelectedDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return dataf.isBefore(dataInicio);

    }

    public void realizarAlteracoes() {

        int resposta = JOptionPane.showConfirmDialog(null, "Desea realmente cambiar la fecha de cerramiento?",
                "Confirmacion",
                JOptionPane.YES_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {

            try {
                if (fecharLinha()) {

                    txt_filtro.setText("");
                    new MainTableUtil(tbMainViewLst).ajustarFormataColunasTabelaConteudo();
                    MainController.filtrarTabelaCriterio();

                }

            } catch (Exception ex) {

                ex.printStackTrace();

            }

        } else if (resposta == JOptionPane.NO_OPTION) {

        }

    }

    public boolean fecharLinha() throws ParseException {

        txt_filtro.setText("");

        LocalDate dataInicio = LocalDate.parse(tbMainViewLst.getValueAt(tbMainViewLst.getSelectedRow(), 1).toString());

        Date RawProductionDate = new SimpleDateFormat("yyyyMMdd").parse(lblDtProd.getText());
        LocalDate ProducionDate = RawProductionDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate DataFinal = DateUtils.toCalendar(dateChooserCombo.getSelectedDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        boolean MaiorOUMaiorAbertura = ProducionDate.isEqual(dataInicio) || ProducionDate.isAfter(dataInicio);

        boolean MenorQueFechamentoOUIGUAL = ProducionDate.isEqual(DataFinal) || ProducionDate.isBefore(DataFinal);

        boolean validator = (MaiorOUMaiorAbertura && MenorQueFechamentoOUIGUAL);
        
//        boolean ValidarDigitoDom = validarNumeroOitoDigitos(oldValueComment)
        
        

        long Dif = calcularDiferencaEmMinutos(ProducionDate, DataFinal);

        if (Dif >= -1) {

            String status = definidorDeAcoes(validator);

            if (!verificarFechamentoInferiorAbertura()) {

                Modelo.setValueAt(DataFinal.toString(), NumeroOriginalSelecionadoTabela, 2);
                Modelo.setValueAt(validator, NumeroOriginalSelecionadoTabela, 4);
                Modelo.setValueAt("En Aprobacion", NumeroOriginalSelecionadoTabela, 5);
                Modelo.setValueAt(Adaptador.usuarioAtivo(), NumeroOriginalSelecionadoTabela, 6);
                Modelo.setValueAt(tratarComentario(txt_Comment.getText()), NumeroOriginalSelecionadoTabela, 3);
                Modelo.setValueAt(calcularDiferencaEmMinutos(ProducionDate, DataFinal), NumeroOriginalSelecionadoTabela, 7);
                Modelo.setValueAt(status, NumeroOriginalSelecionadoTabela, 8);

                return true;

            } else {
                JOptionPane.showMessageDialog(null, "No se puede cerrar con una fecha inferior al del inicio!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "No se puede cerrar con una fecha inferior al de la ultima produccion!", "Error", JOptionPane.ERROR_MESSAGE);
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
