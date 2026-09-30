// Clase ke representa un tesoro del jogo
public class Tresor {

    // Atributos del tesoro
    private String nom;
    private int valor;      // valor en monedas de oro sin decimales
    private double pes;     // peso con decimales

    // Constructor del tesoro con todos sus datos
    public Tresor(String nom, int valor, double pes) {
        this.nom = nom;
        this.valor = valor;
        this.pes = pes;
    }

    // Getters pa acceder a los atributos del tesoro
    public String getNom() {
    	return nom; 
    	}
    public int getValor() {
    	return valor; 
    	}
    public double getPes() {
    	return pes; 
    	}

    // Cuando mostramos un tesoro por consola aparece su nombre y su valor en monedas de oro
    @Override
    public String toString() {
        return nom + " (" + valor + " monedes d'or)";
    }
}