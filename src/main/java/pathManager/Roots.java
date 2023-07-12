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
 
    
    DARK_PRODUCAO_DIARIA_DIA_TEMP(Manager.getRoot().get("caminho_local_temp_producao_dia"),"DARKLIST"),
    DARK_PASTA_TEMP_LOG_FILE(Manager.getRoot().get("caminho_local_temp_logFile"),"DARKLIST"),
    DARK_PASTA_TEMP_FILE(Manager.getRoot().get("caminho_local_temp_darkFile"),"DARKLIST"),
    DARK_ARQUIVO_TEMP_FLAG(Manager.getRoot().get("caminho_local_temp_flag"),"DARKLIST"),
    DARK_ARQUIVO_LST_SERVIDOR(Manager.getRoot().get("caminho_oficial_temp_darkFile_servidor"),"DARKLIST"),
    DARK_PASTA_LST_SERVIDOR(Manager.getRoot().get("caminho_oficial_temp_darkFile_folder_only_servidor"),"DARKLIST"),
    DARK_PASTA_PRODUCAO_OFICIAL(Manager.getRoot().get("caminho_oficial_out_telepanel_panama_servidor"),"DARKLIST"),
    DARK_PASTA_FTP_PRODUCAO_DIARIA(Manager.getRoot().get("caminho_ftp_producao_dia"),"DARKLIST"),
    DARK_LITERAL_FTP_PRODUCAO_DIARIA(Manager.getRoot().get("caminho_ftp_producao_dia_completo_arquivo"),"DARKLIST"),
    DARK_PASTA_FTP_DARKLIST(Manager.getRoot().get("caminho_ftp_darklist"),"DARKLIST"),
    DARK_PASTA_FTP_LOG(Manager.getRoot().get("caminho_ftp_log"),"DARKLIST"),
    DARK_PASTA_FTP_FLAG(Manager.getRoot().get("caminho_ftp_flag"),"DARKLIST"), 
    
    
    PRODUCAO_DIARIA_DIA_TEMP_WHITE(Manager.getRoot().get("caminho_local_temp_producao_dia_white"),"WHITE"),
    PASTA_TEMP_LOG_FILE_WHITE(Manager.getRoot().get("caminho_local_temp_logFile_white"),"WHITE"),
    PASTA_TEMP_FILE_WHITE(Manager.getRoot().get("caminho_local_temp_white"),"WHITE"),
    ARQUIVO_TEMP_FLAG_WHITE(Manager.getRoot().get("caminho_local_temp_flag_white"),"WHITE"),
    ARQUIVO_LST_SERVIDOR_WHITE(Manager.getRoot().get("caminho_oficial_temp_darkFile_servidor_white"),"WHITE"),
    PASTA_LST_SERVIDOR_WHITE(Manager.getRoot().get("caminho_oficial_temp_darkFile_folder_only_servidor_white"),"WHITE"),
    PASTA_PRODUCAO_OFICIAL_WHITE(Manager.getRoot().get("caminho_oficial_out_telepanel_panama_servidor_white"),"WHITE"),
    PASTA_FTP_PRODUCAO_DIARIA_WHITE(Manager.getRoot().get("caminho_ftp_producao_dia_white"),"WHITE"),
    LITERAL_FTP_PRODUCAO_DIARIA_WHITE(Manager.getRoot().get("caminho_ftp_producao_dia_completo_arquivo_white"),"WHITE"),
    PASTA_FTP_WHITE(Manager.getRoot().get("caminho_ftp_darklist_white"),"WHITE"),
    PASTA_FTP_LOG_WHITE(Manager.getRoot().get("caminho_ftp_log_white"),"WHITE"),
    PASTA_FTP_FLAG_WHITE(Manager.getRoot().get("caminho_ftp_flag_white"),"WHITE");  
    
    
    
    
    
    
    
    
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
