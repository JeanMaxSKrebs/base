package tempo;

public class Meses {

    private String nomePortugues;
    private String nomeIngles;

    public Meses(String nomePortugues, String nomeIngles) {
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
