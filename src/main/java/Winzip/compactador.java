/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Winzip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JOptionPane;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 *
 * @author Eduardo.Fernando
 */
public class compactador {

    static final int TAMANHO_BUFFER = 4096; // 4kb
    //método para compactar arquivo

    public static void compactarParaZip(String arqSaida, String arqEntrada)
            throws Exception {
        int cont;
        byte[] dados = new byte[TAMANHO_BUFFER];

        BufferedInputStream origem = null;
        FileInputStream streamDeEntrada = null;
        FileOutputStream destino = null;
        ZipOutputStream saida = null;
        ZipEntry entry = null;

        try {
            destino = new FileOutputStream(new File(arqSaida));
            saida = new ZipOutputStream(new BufferedOutputStream(destino));
            File file = new File(arqEntrada);
            streamDeEntrada = new FileInputStream(file);
            origem = new BufferedInputStream(streamDeEntrada, TAMANHO_BUFFER);
            entry = new ZipEntry(file.getName());
            saida.putNextEntry(entry);

            while ((cont = origem.read(dados, 0, TAMANHO_BUFFER)) != -1) {
                saida.write(dados, 0, cont);
            }
            origem.close();
            saida.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static void compact(String zip, String target, String internalFolder) throws Exception {

        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        parameters.setFileNameInZip("Eduardo Fernando");

        if ("".length() > 0) {
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);

            parameters.setPassword("");
        }

        ZipFile zipFile = new ZipFile(zip);
        parameters.setRootFolderInZip(internalFolder);

        File targetFile = new File(target);
        if (targetFile.isFile()) {
            zipFile.addFile(targetFile, parameters);
        } else if (targetFile.isDirectory()) {
            zipFile.addFolder(targetFile, parameters);
        }

    }

    public static void unzip(String zip, String target) throws Exception {

        String source = zip;
        String destination = target;
        String password = "mypassword";

        try {
            ZipFile zipFile = new ZipFile(source);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            zipFile.extractAll(destination);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

    }

}
