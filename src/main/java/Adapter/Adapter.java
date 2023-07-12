/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Adapter;

import operations.LocalDarklistOperations;
import operations.LocalOperations;
import operations.LocalWhiteOperations;
import operations.RemoteDarklistOperations;
import operations.RemoteOperations;
import operations.RemoteWhitelistOperations;

/**
 *
 * @author eduardo.fernando
 */
public abstract class Adapter {

    public static RemoteOperations Remote;

    public static LocalOperations localOperations;

    public abstract String getPastaTempFile();

    public abstract String getPastaTempLogFile();

    public abstract void iniciaConexao() throws Exception;

    public String usuarioAtivo() {

        return System.getProperty("user.name");

    }

}
