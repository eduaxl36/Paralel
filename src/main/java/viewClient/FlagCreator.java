/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewClient;

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
    
    
    public static void createFlag(List<entidadeFlag> flag) throws IOException{
    
    
     flag.forEach(x->{
     
     
             
         try {
             FileUtils.write(new File("c:/teste/ss.txt"),
                     
                        x.getId()+";"+
                        x.getDataAbertura()+";"+
                        x.getDataFechamento()+";"+
                        x.getComment()+";"+
                        x.getAutor()+";"+
                        x.getTipoCambio()+"\n"
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
