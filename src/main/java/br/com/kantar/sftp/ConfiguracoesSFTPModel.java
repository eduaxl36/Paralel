/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.kantar.sftp;

/**
 *
 * @author Eduardo.Fernando
 */
public class ConfiguracoesSFTPModel {
    
  private String OwnerFTP;
  private int TipoFtp;
  private String User;
  private String Senha;
  private String Host;
  private int Porta;


    public ConfiguracoesSFTPModel() {
    }

    public ConfiguracoesSFTPModel(String OwnerFTP, int TipoFtp, String User, String Senha, String Host, int Porta) {
        this.OwnerFTP = OwnerFTP;
        this.TipoFtp = TipoFtp;
        this.User = User;
        this.Senha = Senha;
        this.Host = Host;
        this.Porta = Porta;
    }

    public String getOwnerFTP() {
        return OwnerFTP;
    }

    public void setOwnerFTP(String OwnerFTP) {
        this.OwnerFTP = OwnerFTP;
    }

    public int getTipoFtp() {
        return TipoFtp;
    }

    public void setTipoFtp(int TipoFtp) {
        this.TipoFtp = TipoFtp;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String Senha) {
        this.Senha = Senha;
    }

    public String getHost() {
        return Host;
    }

    public void setHost(String Host) {
        this.Host = Host;
    }

    public int getPorta() {
        return Porta;
    }

    public void setPorta(int Porta) {
        this.Porta = Porta;
    }

    @Override
    public String toString() {
        return "ModelFTPConnection{" + "OwnerFTP=" + OwnerFTP + ", TipoFtp=" + TipoFtp + ", User=" + User + ", Senha=" + Senha + ", Host=" + Host + ", Porta=" + Porta + ", PrivateKey="  + '}';
    }

  
  
  
    
}
