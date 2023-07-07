/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewClientWhitelist;

import viewClientDarklist.*;
import flag.entidadeFlag;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Eduardo.Fernando
 */
public class FlagCreator {
    
    
    
    
    
    
    public static void createFlag(List<entidadeFlag> flag,String Dia) throws IOException{
    
new File("C:\\teste\\Nova pasta\\true.txt").delete();
      
               final  String mensagem = String.format("Solicitacion para el cambio en Darklist\n"
                     + "\n"
                     + "El user: %s "
                     + "\n"
                     + "Fecha del Darklist : %s\n", flag.get(0).getAutor(),Dia);

                
 FileUtils.write(new File("C:\\teste\\Nova pasta\\true.txt"),mensagem, StandardCharsets.UTF_8,true);
      



     flag.forEach(x->{
     
     
             
         try {
             
           
             
             FileUtils.write(new File("C:\\teste\\Nova pasta\\true.txt"),
                     
                        "\n"+
                        "Hogar: "+x.getId()+"\n"+
                        "Fecha Inicio: "+x.getDataAbertura()+"\n"+
                        "Fecha Cerramiento:"+x.getDataFechamento()+"\n"+
                        "Obs. : "+x.getComment()+"\n"+
                        "Autor : "+x.getAutor()+"\n"+
                        "Cambio Relizado : "+x.getTipoCambio()+"\n\n"
                     ,
                     
                     StandardCharsets.UTF_8,true);
         } catch (IOException ex) {
             Logger.getLogger(FlagCreator.class.getName()).log(Level.SEVERE, null, ex);
         }
     
     
     
     });
     
     
  
    
    }
    
    
    
    public static void main(String[] args) throws IOException {
        
    
        
        
    }
    
    
}
