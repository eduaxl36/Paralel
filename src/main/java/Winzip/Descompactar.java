/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Winzip;


import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import net.lingala.zip4j.model.FileHeader;

/**
 *
 * @author Eduardo.Fernando
 */
public class Descompactar {

    
public static void extractToFolderFileEspecific(File zip, String destinationFolderPath,String Arquivo) throws net.lingala.zip4j.exception.ZipException {
        ZipFile zipFile = new ZipFile(zip);
        List<FileHeader> fileHeaders = zipFile.getFileHeaders();
        for(FileHeader fileHeader : fileHeaders) {
            
            if(fileHeader.getFileName().equals(Arquivo)){
            
                if (fileHeader.isDirectory()) {
                System.out.println(destinationFolderPath + fileHeader.getFileName());
                File f = new File(destinationFolderPath + fileHeader.getFileName());
                f.mkdirs();  // mkdirs() is different from mkdir()
                zipFile.extractFile(fileHeader, f.toString());
            }else {
                zipFile.extractFile(fileHeader, destinationFolderPath);
            }  
            
            
            }

        }
    }    
    
public static void extractToFolder(File zip, String destinationFolderPath) throws net.lingala.zip4j.exception.ZipException {
        ZipFile zipFile = new ZipFile(zip);
        List<FileHeader> fileHeaders = zipFile.getFileHeaders();
        for(FileHeader fileHeader : fileHeaders) {
            if (fileHeader.isDirectory()) {
                System.out.println(destinationFolderPath + fileHeader.getFileName());
                File f = new File(destinationFolderPath + fileHeader.getFileName());
                f.mkdirs();  // mkdirs() is different from mkdir()
                zipFile.extractFile(fileHeader, f.toString());
            }else {
                zipFile.extractFile(fileHeader, destinationFolderPath);
            }
        }
    }
    public static void ExtractSingleFile(String zip, String arquivo, String destino) {
        try {

            ZipFile zipFile = new ZipFile(zip);

            zipFile.extractFile(arquivo, destino);

        } catch (ZipException e) {
          JOptionPane.showMessageDialog(null, "Falha ao tentar extrair o arquivo "+ e);
        }
    }
  public static void ExtractAllFile(String zip,String destino) {
        try {

            ZipFile zipFile = new ZipFile(zip);

            zipFile.extractAll(destino);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao tentar extrair o arquivo "+ e);
        }
    }


    public static void main(String[] args) throws Exception {
        
     
        
    }
  
  
}
