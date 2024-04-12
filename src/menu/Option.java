package menu;

public class Option {

    private String nomePortugues;
    private String nomeIngles;

    
    public Option() {
        this.nomePortugues = "Opção";
        this.nomeIngles = "Option";
    }
    
    public Option(String nomePortugues, String nomeIngles) {
        this.nomePortugues = nomePortugues;
        this.nomeIngles = nomeIngles;
    }

    public String getNomePortugues() {
        return nomePortugues;
    }

    public String getNomeIngles() {
        return nomeIngles;
    }
}
