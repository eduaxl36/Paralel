/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Robot;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author eduardo.fernando
 */
public class HandleTo {

    private final List<String> Enderecos;

    public HandleTo() throws IOException {
        Enderecos = Arrays.asList(FileUtils.readFileToString(new File("config/Email/EmailAdressesTO.txt")).split("\n"));

    }

    public String getEnderecos() {
        return Enderecos.toString().replaceAll("\\[|\\]", "").replaceAll("\\s{1,}", "");
    }

    public static void main(String[] args) throws IOException {

        System.out.println(new HandleTo().getEnderecos());

    }
}
