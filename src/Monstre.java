import java.util.Random;

// Clase ke representa un monstro del jogo
public class Monstre implements Combatent {

    // Atributos del monstro
    private String nom;
    private int vida;
    private int penalitzacioFugida;  // numero entre 0 y 3 ke se resta al personaje si huye
    private int valorExperiencia;    // igual a vida * 2

    // Generador de numeros aleatorios pa los ataques
    private static final Random rand = new Random();

    // Constructor del monstro
    public Monstre(String nom, int vida, int penalitzacioFugida) {
        this.nom = nom;
        this.vida = vida;
        this.penalitzacioFugida = penalitzacioFugida;
        // El valor de experiencia es la vida multiplicada por 2
        this.valorExperiencia = vida * 2;
    }

    // Getters pa acceder a los datos del monstro
    public String getNom() { return nom; }
    public int getVida() { return vida; }
    public int getPenalitzacioFugida() { return penalitzacioFugida; }
    public int getValorExperiencia() { return valorExperiencia; }

    // Calcula un ataque aleatorio entre 1 y la vida actual del monstro
    @Override
    public int calcularAtac() {
        // Si la vida es 0 o menos no ataca
        if (vida <= 0) return 0;
        return rand.nextInt(vida) + 1;
    }

    // Resta dano a la vida del monstro
    @Override
    public void rebreDany(int quantitat) {
        vida -= quantitat;
        // La vida no puede bajar de 0
        if (vida < 0) vida = 0;
    }

    // Retorna si el monstro esta vivo o no
    @Override
    public boolean estaViu() {
        return vida > 0;
    }

    // Muestra el nombre y vida del monstro por consola
    @Override
    public String toString() {
        return nom + " (Vida: " + vida + ")";
    }
}