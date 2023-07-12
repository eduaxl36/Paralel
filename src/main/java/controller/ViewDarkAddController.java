/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static viewClientDarklist.DarklistManagerViewClient.Adaptador;
import static viewClientDarklist.DarklistManagerViewClient.lblDtProd;
import static viewClientDarklist.DarklistManagerViewClient.tbMainViewLst;
import viewClientDarklist.ViewDarkAdd;
import static viewClientDarklist.ViewDarkAdd.InitialHouseHoldDate;
import static viewClientDarklist.ViewDarkAdd.TxtComment;
import static viewClientDarklist.ViewDarkAdd.Txt_HouseHold;
import static viewClientDarklist.ViewDarkAdd.finalHouseHoldDate;
import static viewClientDarklist.ViewDarkAdd.instanciaAbertaAdicao;
import static viewClientDarklist.ViewDarkAdd.lblUserName;

/**
 *
 * @author eduardo.fernando
 */
public class ViewDarkAddController {

    private JDialog modalDialo;
    private final DefaultTableModel Formatter;

    public ViewDarkAddController() {

        lblUserName.setText(Adaptador.usuarioAtivo());
        JDialog modalDialog = new JDialog();
        modalDialog.setModal(true);
        Formatter = (DefaultTableModel) tbMainViewLst.getModel();

    }

    public static boolean validarNumeroOitoDigitos(String input) {
        String regex = "^\\d{3,8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean validarFakeString(String input) {
        String regex = "^\"\\S.*\"\\z";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public boolean validarSeProucaoJaFoi(long Domicilio) {

        for (int i = 0; i < tbMainViewLst.getRowCount(); i++) {

            if (Long.parseLong(tbMainViewLst.getValueAt(i, 0).toString()) == Domicilio) {

                if (tbMainViewLst.getValueAt(i, 4).toString().equals("true")) {

                    return true;

                }

            }

        }

        return false;

    }

    public long calcularDiferencaEmMinutos(LocalDate data1, LocalDate data2) {
        LocalDateTime dateTime1 = LocalDateTime.of(data1, LocalTime.MIN);
        LocalDateTime dateTime2 = LocalDateTime.of(data2, LocalTime.MIN);

        return ChronoUnit.DAYS.between(dateTime1, dateTime2);
    }

    public LocalDate obterDataProducao() throws Exception {

        Date RawProductionDate = new SimpleDateFormat("yyyyMMdd").parse(lblDtProd.getText());

        LocalDate ProducionDate = RawProductionDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return ProducionDate;

    }

    public LocalDate obterDataFinalTratada() throws Exception {

        LocalDate datafinal = LocalDate.parse(finalHouseHoldDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return datafinal;

    }

    public LocalDate obterDataInicialTratada() throws Exception {

        LocalDate datainicial = LocalDate.parse(InitialHouseHoldDate.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return datainicial;

    }

    public boolean adicionaLinhaTabela() throws ParseException, Exception {

        boolean ValidaFake = validarFakeString("\"" + TxtComment.getText() + "\"");

        if (ValidaFake) {

            boolean ValidaIntegridadeDom = validarNumeroOitoDigitos(Txt_HouseHold.getText());

            if (ValidaIntegridadeDom) {

                boolean allowInsertion = obterDataInicialTratada().isAfter(obterDataProducao()) || obterDataInicialTratada().equals(obterDataProducao());

                if (!allowInsertion) {

                    JOptionPane.showMessageDialog(null, "No se puede inserir un hogar\ncon una fecha inferior al produccion -4(dias) atual!", "Erro al añadir", JOptionPane.ERROR_MESSAGE);

                } else {

                    boolean NegarInsercao = obterDataFinalTratada().isBefore(obterDataInicialTratada());

                    if (NegarInsercao) {

                        JOptionPane.showMessageDialog(null, "La fecha final no puede ser inferior al fecha de inicial!", "Error al añadir", JOptionPane.ERROR_MESSAGE);

                    } else {

                        DateTimeFormatter dfw = DateTimeFormatter.ofPattern("yyyyMMdd");

                        boolean ValidadorExistencia = validarSeProucaoJaFoi(Long.parseLong(Txt_HouseHold.getText()));

                        if (ValidadorExistencia == false) {

                            Formatter.addRow(new Object[]{
                                Txt_HouseHold.getText(),
                                obterDataInicialTratada(),
                                obterDataFinalTratada(),
                                "\"" + TxtComment.getText() + "\"",
                                true,
                                "Nueva Linea/En Aprobacion",
                                lblUserName.getText(),
                                calcularDiferencaEmMinutos(LocalDate.parse(lblDtProd.getText(), dfw), obterDataFinalTratada()),
                                "Hogar puesto en lista"

                            });

                            return true;
                        } else {

                            JOptionPane.showMessageDialog(null, "El hogar selecioando ya esta presente en lista con el status true!", "Error al añadir", JOptionPane.ERROR_MESSAGE);

                        }

                    }

                }

            } else {

                JOptionPane.showMessageDialog(null, "Numero de digitos del hogar debe tener de 1 a 8 y accepta solamente numeros!", "Error al añadir", JOptionPane.ERROR_MESSAGE);

            }

        }
        else{
        
             JOptionPane.showMessageDialog(null, "Obs debe tener contenido!", "Error al añadir", JOptionPane.ERROR_MESSAGE);

        
        
        }

        return false;
    }

    public void acionaAdicaoValidada() throws Exception {

        int resposta = JOptionPane.showConfirmDialog(modalDialo, "Desea añadir el logar en lista?",
                "Confirmacion",
                JOptionPane.YES_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {

            try {

                if (!adicionaLinhaTabela()) {
                    new ViewDarkAdd().dispose();

                } else {

                    instanciaAbertaAdicao = false;

                }

            } catch (ParseException ex) {
                Logger.getLogger(ViewDarkAdd.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (resposta == JOptionPane.NO_OPTION) {

        }

    }

}
