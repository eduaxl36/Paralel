/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pathManager;

/**
 *
 * @author eduardo.fernando
 */
public enum Roots {
 
    
    PRODUCAO_DIARIA_DIA_TEMP(Manager.getRoot().get("caminho_local_temp_producao_dia"),"DARKLIST"),
    PASTA_TEMP_LOG_FILE(Manager.getRoot().get("caminho_local_temp_logFile"),"DARKLIST"),
    PASTA_TEMP_DARK_FILE(Manager.getRoot().get("caminho_local_temp_darkFile"),"DARKLIST"),
    PASTA_TEMP_WHITE_FILE(Manager.getRoot().get("caminho_local_temp_whiteFile"),"WHITE"),
    ARQUIVO_TEMP_FLAG(Manager.getRoot().get("caminho_local_temp_flag"),"DARKLIST"),
    ARQUIVO_LST_SERVIDOR(Manager.getRoot().get("caminho_oficial_temp_darkFile_servidor"),"DARKLIST"),
    PASTA_LST_SERVIDOR(Manager.getRoot().get("caminho_oficial_temp_darkFile_folder_only_servidor"),"DARKLIST"),
    PASTA_PRODUCAO_OFICIAL(Manager.getRoot().get("caminho_oficial_out_telepanel_panama_servidor"),"DARKLIST"),
    PASTA_FTP_PRODUCAO_DIARIA(Manager.getRoot().get("caminho_ftp_producao_dia"),"DARKLIST"),
    LITERAL_FTP_PRODUCAO_DIARIA(Manager.getRoot().get("caminho_ftp_producao_dia_completo_arquivo"),"DARKLIST"),
    PASTA_FTP_DARKLIST(Manager.getRoot().get("caminho_ftp_darklist"),"DARKLIST"),
    PASTA_FTP_LOG(Manager.getRoot().get("caminho_ftp_log"),"DARKLIST"),
    PASTA_FTP_FLAG(Manager.getRoot().get("caminho_ftp_flag"),"DARKLIST");
    
    private String Caminho;
    private String Processo;

    private Roots(String Caminho, String Processo) {
        this.Caminho = Caminho;
        this.Processo = Processo;
    }

    public String getCaminho() {
        return Caminho;
    }

    public void setCaminho(String Caminho) {
        this.Caminho = Caminho;
    }

    public String getProcesso() {
        return Processo;
    }

    public void setProcesso(String Processo) {
        this.Processo = Processo;
    }

    @Override
    public String toString() {
        return "Roots{" + "Caminho=" + Caminho + ", Processo=" + Processo + '}';
    }
    
   
    
    

    
    
    
}
