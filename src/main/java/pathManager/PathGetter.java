
package pathManager;
import com.google.gson.annotations.SerializedName;

public class PathGetter {

     @SerializedName("processo")
    private String processo;
      @SerializedName("caminho")
    private String caminho;

    public PathGetter(String processo, String caminho) {
        this.processo = processo;
        this.caminho = caminho;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    @Override
    public String toString() {
        return "PathGetter{" + "processo=" + processo + ", caminho=" + caminho + '}';
    }
}
