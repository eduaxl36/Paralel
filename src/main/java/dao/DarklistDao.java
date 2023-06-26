/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Entities.Darklist;
import Util.Util;
import br.com.kantar.pathManager.Manager;
import br.com.kantar.sftp.SFTPOperations;
import com.google.common.collect.Table;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import msgs.Pbar;
import static viewClient.DarklistManagerViewClient.lblDtProd;
import static viewClient.DarklistManagerViewClient.tbMainViewDarkList;
import viewClient.MenuFile;

/**
 *
 * @author Eduardo.Fernando
 */
public class DarklistDao {

    private LocalDate DataProducao;
    private List<Long> Domicilios;
    private final JTable Tabela;
    private File LstFile;

        public DarklistDao(LocalDate DataProducao, JTable Tabela, File LstFile) {
        this.DataProducao = DataProducao;
        this.Tabela = Tabela;
        this.LstFile = LstFile;
    }


    
    
    public DarklistDao(LocalDate DataProducao, JTable Tabela) {
        this.DataProducao = DataProducao;
        this.Tabela = Tabela;
    }

    public DarklistDao(LocalDate DataProducao, List<Long> Domicilios, JTable Tabela, File LstFile) {
        this.DataProducao = DataProducao;
        this.Domicilios = Domicilios;
        this.Tabela = Tabela;
        this.LstFile = LstFile;
    }

    
       
    
    public LocalDate retornaDataFechamento(String ProduccionDate) {

        return LocalDate.parse(ProduccionDate).plusDays(1);

    }

    public File abrirArquivo() {

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Arquivo DarkList", "lst");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            return chooser.getSelectedFile();

        }

        return null;
    }

    public boolean verificartSeEstaEmDark(LocalDate DataFechamento) {

        boolean producaoAntesFechamento = this.DataProducao.isBefore(DataFechamento);
        boolean producaoIgualFechamento = this.DataProducao.isEqual(DataFechamento);

        return producaoAntesFechamento || producaoIgualFechamento;
    }

    public Darklist retornaObjetoDark(String Raw) {

        String[] RawLines = Raw.split(",");

        if (RawLines[0].trim().matches("\\d{1,}")) {

            Long Domicilio = Long.valueOf(RawLines[0].trim());
            String Abertura = RawLines[3].trim();
            String Fechamento = RawLines[4].trim().replaceAll("-1", "20501231");
            String Observacao = RawLines[7].trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate AberturaAsLc = LocalDate.parse(Abertura, formatter);
            LocalDate FechamentoAsLc = LocalDate.parse(Fechamento, formatter);

            return new Darklist(
                    Domicilio,
                    AberturaAsLc,
                    FechamentoAsLc,
                    Observacao,
                    verificartSeEstaEmDark(FechamentoAsLc)
            );

        }

        return null;
    }

    public List<Darklist> relacaoCompletaDarklists() throws FileNotFoundException, IOException {

        List<Darklist> Darks = new ArrayList<>();

        this.DataProducao.plusDays(1);

        FileReader Fr = new FileReader(this.LstFile);
        BufferedReader bf = new BufferedReader(Fr);
        String Linha = bf.readLine();

        while (Linha != null) {

            if (!(Linha.contains("[NumItems]"))) {

                Darks.add(retornaObjetoDark(Linha));

            }

            Linha = bf.readLine();

        }

        return Darks;
    }

    public List<Darklist> domiciliosEmDarklist() throws IOException {

        List<Darklist> Darks = relacaoCompletaDarklists().stream()
                .filter(x -> this.Domicilios.contains(x.getId()))
                .filter(x -> x.isStatus() == true)
                .collect(Collectors.toList());

        return Darks;

    }

    public void uploadLogAlteracoes() {
        int resposta = JOptionPane.showConfirmDialog(null, "Desea enviar los cambios para la equipe regional?", "Confirmacion", JOptionPane.YES_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            new Thread(() -> {
                try {
                    Pbar.Progresso.setVisible(true);
                    String arquivoSalvoLog = Manager.getRoot().get("caminho_local_temp_logFile") + DataProducao.toString().replaceAll("-", "") + "_log.csv";
                    new Util(Tabela).exportarConteudoParaCsv(arquivoSalvoLog);
                    new SFTPOperations().uploadLogFile(DataProducao.toString().replaceAll("-", ""));
                    Pbar.Progresso.setVisible(false);
                } catch (Exception ex) {
                    Logger.getLogger(MenuFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
        } else if (resposta == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, "Voc� selecionou 'N�o'.");
        }
    }

    public void CarregarDarkList(File DarkFile) throws IOException, Exception {

        relacaoCompletaDarklists().forEach((var x) -> {
            
            
            JOptionPane.showMessageDialog(null, this);
            
            DefaultTableModel df = (DefaultTableModel) Tabela.getModel();

            String allowChange = "No permitido cambios";

            if (x != null) {

                if (x.isStatus()) {

                    allowChange = "Listo para Cambio";

                }

                df.addRow(new Object[]{
                    x.getId(),
                    x.getDataAbertura(),
                    x.getDataFechamento(),
                    x.getComentario(),
                    x.isStatus(),
                    allowChange

                });

            }

        });

        new Util(Tabela).ajustarFormataColunasTabelaConteudo();

    }

}
