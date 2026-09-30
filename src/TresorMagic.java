// AMPLIACION 2: Tesoro magico que da vida al personaje cuando lo encuentra
// es un tipo especial de tresor que hereda de Tresor
// cuando el personaje lo coge recupera vida
public class TresorMagic extends Tresor {

    // cuanta vida da al personaje cuando lo recoge
    private int vidaRecuperada;

    // constructor del tesoro magico
    public TresorMagic(String nom, int valor, double pes, int vidaRecuperada) {
        super(nom, valor, pes);
        this.vidaRecuperada = vidaRecuperada;
    }

    // devuelve cuanta vida recupera
    public int getVidaRecuperada() {
        return vidaRecuperada;
    }

    // aplica el efecto de curacion al personaje
    // el personaje no puede superar los 20 puntos de vida maximos
    public void aplicarEfecte(Personatge p) {
        int vidaAntes = p.getVida();
        // sumamos la vida curada pero no puede pasar de 20
        int novaVida = Math.min(20, vidaAntes + vidaRecuperada);
        int vidaCurada = novaVida - vidaAntes;
        // usamos rebreDany con negativo no funciona, asi que modificamos directamente
        // como la clase Personatge no tiene setVida, simulamos la curacion con un dany negativo
        // la solucio es que rebreDany resta, asique usamos el truco de restar un negativo
        // pero Java no permite eso directamente, asi que calculamos el danyio negativo correcto
        int danyParaCurar = -(vidaCurada); // truco: "danyio negativo" = curar
        // como Personatge.rebreDany hace vida -= quantitat, si passamos negativo, suma
        p.rebreDany(danyParaCurar);
        System.out.println("El tresor magic et cura " + vidaCurada + " punts de vida!");
    }

    // al mostrar el tesoro magico indicamos que cura vida
    @Override
    public String toString() {
        return super.toString() + " [MAGIC: +\" + vidaRecuperada + \" vida]";
    }
}