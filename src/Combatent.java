// Interfaz que deven implementar todos los que pueden pelear
// tanto personajes como monstros tienen que tener estos metodos
public interface Combatent {

    // calcula el danyo que hace en este turno
    int calcularAtac();

    // resta vida al personaje o monstro
    void rebreDany(int quantitat);

    // dize si sige vivo (vida > 0)
    boolean estaViu();
}