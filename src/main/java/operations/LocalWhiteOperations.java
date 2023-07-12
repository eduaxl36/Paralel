/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operations;

import Adapter.Adapter;
import static Adapter.Adapter.Remote;
import Adapter.AdapterWhite;
import Exporter.ExporterWhite;
import dao.LogDao;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import pathManager.Roots;
import static viewClientDarklist.DarklistManagerViewClient.lblDtProd;
import static viewClientDarklist.DarklistManagerViewClient.tbMainViewLst;

/**
 *
 * @author eduardo.fernando
 */
public class LocalWhiteOperations implements LocalOperations {

    @Override
    public String captarUltimoDiaProducao() {

        List<LocalDate> datas = new ArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        File pasta = new File(Roots.PASTA_PRODUCAO_OFICIAL_WHITE.getCaminho());

        File[] arquivos = pasta.listFiles((dir, nome) -> nome.matches("\\d{8}\\.vsl"));

        for (File x : arquivos) {

            datas.add(LocalDate.parse(x.getName().substring(0, 8), formatter));

        }

        Collections.sort(datas);

        return Collections.max(datas).toString().replaceAll("-", "");

    }

    @Override
    public void criarDataProducao() throws IOException, Exception {

        FileUtils.write(new File(Roots.PRODUCAO_DIARIA_DIA_TEMP_WHITE.getCaminho()), captarUltimoDiaProducao());

    }

    @Override
    public void carregarLog(String LogLocalFile) throws Exception {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");

        new LogDao(LocalDate.parse(lblDtProd.getText(), fmt).plusDays(1), new File(LogLocalFile), tbMainViewLst)
                .preencherTabela();
    }

    @Override
    public void subirListaCorrespondente(String Dia) {

        try {
            Remote.uploadUltimoDiaDaListaLiteral(Dia);
        } catch (Exception ex) {
            Logger.getLogger(LocalWhiteOperations.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void subirLabelCorrespondente() {

        try {
            Remote.uploadDiaProducaoNumeralLabel();
        } catch (Exception ex) {
            Logger.getLogger(LocalWhiteOperations.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void montarLista() {
        try {

            String Arquivo = new ExporterWhite().montarDarkList(tbMainViewLst);

            FileUtils.writeStringToFile(new File("c:/teste/teste.txt"), Arquivo);

        } catch (IOException ex) {
            Logger.getLogger(AdapterWhite.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws Exception {

        Adapter aa = new AdapterWhite();
        aa.iniciaConexao();

        new LocalWhiteOperations().criarDataProducao();
        new LocalWhiteOperations().subirListaCorrespondente(new LocalWhiteOperations().captarUltimoDiaProducao());
        new LocalWhiteOperations().subirLabelCorrespondente();

    }

}
