/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Robot;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author eduardo.fernando
 */
public class HandleAnexos {

    private ModelSender Anexo;
    
    private String Tipo;

    public HandleAnexos(String Tipo) {
        this.Tipo = Tipo;
    }

    
    public String getAnexo() throws IOException {

        // Exemplo de conversão de JSON para objeto Java
        String Json = FileUtils.readFileToString(new File("config/Email/AnexosEmail.json"));
        
        ModelSender Modelo = new ModelSender();

        Gson gson = new Gson();

        ModelSender[] dataArray = gson.fromJson(Json, ModelSender[].class);

        for (ModelSender data : dataArray) {
            
            if(Tipo.equals(data.getTipo())){
            
            Modelo.setTipo(Tipo);
            Modelo.setCaminho(data.getCaminho());
            
            }

        }

        return Modelo.getCaminho();
    }

    public static void main(String[] args) throws IOException {

        System.out.println(new HandleAnexos("Darklist").getAnexo());

    }

}
