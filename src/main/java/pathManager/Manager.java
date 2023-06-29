package pathManager;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Manager {

    
    public static Map<String,String>getRoot(){
    
    Map PathList = new LinkedHashMap();
    
    
        // Cria uma instância do Gson
        Gson gson = new Gson();

        try (FileReader reader = new FileReader("config/config.json")) {
           
            // Lê o arquivo JSON e converte para um array de objetos PathGetter
            PathGetter[] configs = gson.fromJson(reader, PathGetter[].class);

            // Exibe os dados lidos
            for (PathGetter config : configs) {
                
                PathList.put(config.getProcesso(), config.getCaminho());
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    
    return PathList;
    
    }
    
    
    public static void main(String[] args) {
        
        System.out.println(getRoot().get("caminho_local_temp_producao_dia"));
        
        
    }
}
