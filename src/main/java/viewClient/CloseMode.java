/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package viewClient;

import Util.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang.time.DateUtils;
import static viewClient.DarklistManagerViewClient.filterTable;
import static viewClient.DarklistManagerViewClient.lblDtProd;
import static viewClient.DarklistManagerViewClient.tbMainViewDarkList;
import static viewClient.DarklistManagerViewClient.txt_filtro;
import static viewClient.DarklistManagerViewClient.NumeroOriginalSelecionadoTabela;

/**
 *
 * @author Eduardo.Fernando
 */
public class CloseMode extends javax.swing.JFrame {

    static String oldValueComment;
    public static boolean instanciaMudancaAdicao = false;

    public static boolean identificarAlteracaoNoComentario(String NovoValor) {

        return oldValueComment.equals(NovoValor);

    }

    public static String definidorDeAcoes(boolean tipo) {

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

    public static long calcularDiferencaEmMinutos(LocalDate data1, LocalDate data2) {
        LocalDateTime dateTime1 = LocalDateTime.of(data1, LocalTime.MIN);
        LocalDateTime dateTime2 = LocalDateTime.of(data2, LocalTime.MIN);

        return ChronoUnit.DAYS.between(dateTime1, dateTime2);
    }

    /**
     * Creates new form CloseMode
     */
    public CloseMode() {
        initComponents();

        ImageIcon img = new ImageIcon(getClass().getResource("/img/chave2.png"));

        this.setIconImage(img.getImage());
        this.setTitle("      Cambiar fecha cerramiento");
        LocalDate datafn = LocalDate.parse(tbMainViewDarkList.getValueAt(tbMainViewDarkList.getSelectedRow(), 2).toString());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(java.util.Date.from(datafn.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        dateChooserCombo.setSelectedDate(calendar);

        txt_Comment.setText("" + tbMainViewDarkList.getValueAt(tbMainViewDarkList.getSelectedRow(), 3));

        String username = System.getProperty("user.name");
        lblUserName.setText(username);
        oldValueComment = new String(txt_Comment.getText());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PnMain = new javax.swing.JPanel();
        PnMain1 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_Comment = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        dateChooserCombo = new datechooser.beans.DateChooserCombo();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        PnMain.setBackground(new java.awt.Color(255, 255, 255));
        PnMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PnMain1.setBackground(new java.awt.Color(255, 255, 255));
        PnMain1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        PnMain.add(PnMain1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 170));

        jSeparator2.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator2.setForeground(new java.awt.Color(204, 204, 204));
        PnMain.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 50, 420, 10));

        jLabel7.setText("Cambiado Por  : ");
        PnMain.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 100, 20));

        lblUserName.setText("TESTE");
        PnMain.add(lblUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 170, 20));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/obs.png"))); // NOI18N
        jLabel6.setText(" Obs:");
        PnMain.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 100, 50));
        PnMain.add(txt_Comment, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 260, 30));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/chave2.png"))); // NOI18N
        jButton1.setIconTextGap(6);
        jButton1.setOpaque(true);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        PnMain.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 40, 30));

        dateChooserCombo.setCurrentView(new datechooser.view.appearance.AppearancesList("Contrast",
            new datechooser.view.appearance.ViewAppearance("custom",
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12),
                    new java.awt.Color(187, 187, 187),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12),
                    new java.awt.Color(187, 187, 187),
                    new java.awt.Color(0, 0, 255),
                    true,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12),
                    new java.awt.Color(0, 0, 255),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12),
                    new java.awt.Color(128, 128, 128),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12),
                    new java.awt.Color(187, 187, 187),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12),
                    new java.awt.Color(187, 187, 187),
                    new java.awt.Color(255, 0, 0),
                    false,
                    false,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                (datechooser.view.BackRenderer)null,
                false,
                true)));
    dateChooserCombo.setLocale(new java.util.Locale("pt", "BR", ""));
    PnMain.add(dateChooserCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 210, 30));

    jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/datafinal.png"))); // NOI18N
    jLabel4.setText("   Cerramiento");
    PnMain.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 130, 30));

    jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    jLabel1.setText("               Cambio de datos");
    PnMain.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 250, 30));

    jSeparator3.setBackground(new java.awt.Color(204, 204, 204));
    jSeparator3.setForeground(new java.awt.Color(204, 204, 204));
    PnMain.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 410, 10));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(PnMain, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(PnMain, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    public static boolean verificarFechamentoInferiorAbertura() throws ParseException {

        LocalDate dataInicio = LocalDate.parse(tbMainViewDarkList.getValueAt(tbMainViewDarkList.getSelectedRow(), 1).toString());

        LocalDate dataf = DateUtils.toCalendar(dateChooserCombo.getSelectedDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return dataf.isBefore(dataInicio);

    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        int resposta = JOptionPane.showConfirmDialog(null, "Desea realmente cambiar la fecha de cerramiento?",
                "Confirmacion",
                JOptionPane.YES_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {

            try {
                if (fecharLinha()) {

                    this.dispose();
                    txt_filtro.setText("");
                    new Util(tbMainViewDarkList).ajustarFormataColunasTabelaConteudo();
                    filterTable();

                }

            } catch (ParseException ex) {
                Logger.getLogger(CloseMode.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (resposta == JOptionPane.NO_OPTION) {

            JOptionPane.showMessageDialog(null, "Você selecionou 'Não'.");

        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed


        instanciaMudancaAdicao=false;

        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    public static String tratarComentario(String Comment) {

        String ValTemp = Comment.replaceAll("\"", "");

        return "\"" + ValTemp + "\"";

    }

    /**
     * @param args the command line arguments
     */
    public static boolean fecharLinha() throws ParseException {
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

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CloseMode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CloseMode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CloseMode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CloseMode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new CloseMode().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JPanel PnMain;
    public static javax.swing.JPanel PnMain1;
    public static datechooser.beans.DateChooserCombo dateChooserCombo;
    public static javax.swing.JButton jButton1;
    public static javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel jLabel4;
    public static javax.swing.JLabel jLabel6;
    public static javax.swing.JLabel jLabel7;
    public static javax.swing.JSeparator jSeparator2;
    public static javax.swing.JSeparator jSeparator3;
    public static javax.swing.JLabel lblUserName;
    public static javax.swing.JTextField txt_Comment;
    // End of variables declaration//GEN-END:variables
}
