/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flag;

import br.com.kantar.pathManager.Manager;
import flag.entidadeFlag;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import viewClient.DarklistManagerViewClient;

import static viewClient.DarklistManagerViewClient.tbMainViewDarkList;

import viewClient.FlagCreator;

/**
 *
 * @author eduardo.fernando
 */
public class FlagOperations {

    public static final String CAMINHO_FLAG = Manager.getRoot().get("caminho_local_temp_flag");

    public static Set<entidadeFlag> Flags = new LinkedHashSet<>();

   
    public  Set<entidadeFlag> obterListaDeFlags(String dia) throws IOException {

        for (int i = 0; i < tbMainViewDarkList.getRowCount(); i++) {
            
            int dom = Integer.parseInt(tbMainViewDarkList.getValueAt(i, 0).toString());
            String value = String.valueOf(tbMainViewDarkList.getValueAt(i, 6));
            String comment = String.valueOf(tbMainViewDarkList.getValueAt(i, 3));
            String result = value != null && !value.isEmpty() ? value : "-";
            String valueTp = String.valueOf(tbMainViewDarkList.getValueAt(i, 8));
            String resultTp = valueTp != null && !valueTp.isEmpty() ? valueTp : "-";

            LocalDate DataAbertura = LocalDate.parse(tbMainViewDarkList.getValueAt(i, 1).toString());
            LocalDate DataFechamento = LocalDate.parse(tbMainViewDarkList.getValueAt(i, 2).toString());

            if (!(result.equals("null"))) {

                Flags.add(
                        new entidadeFlag(
                                dom,
                                result,
                                resultTp,
                                DataAbertura,
                                DataFechamento,
                                false,
                                comment)
                );

            }

        }

       
        return Flags;
    }


    public  void gerarFlag(String Dia) throws IOException {

        Set<entidadeFlag> flag = obterListaDeFlags(Dia);
        new File(CAMINHO_FLAG).delete();
        ArrayList<entidadeFlag> list = new ArrayList<>(flag);
        final String mensagem = String.format("""
                                              Solicitacion para el cambio en Darklist
                                              
                                              El user: %s 
                                              Fecha del Darklist : %s
                                              """, list.get(0).getAutor(), Dia);

        FileUtils.write(new File(CAMINHO_FLAG), mensagem, StandardCharsets.UTF_8, true);

        flag.forEach(x -> {

            try {

                FileUtils.write(new File(CAMINHO_FLAG),
                        """
                        
                        Hogar: """ + x.getId() + "\n"
                        + "Fecha Inicio: " + x.getDataAbertura() + "\n"
                        + "Fecha Cerramiento:" + x.getDataFechamento() + "\n"
                        + "Obs. : " + x.getComment() + "\n"
                        + "Autor : " + x.getAutor() + "\n"
                        + "Cambio Relizado : " + x.getTipoCambio() + "\n\n",

                         StandardCharsets.UTF_8, true);
            } catch (IOException ex) {
                Logger.getLogger(FlagCreator.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }
    
    
    
    public void uploadFlag() throws Exception{
    
    
    DarklistManagerViewClient.Remote.uploadFlag();
    
    
    }
    
    
    


}
