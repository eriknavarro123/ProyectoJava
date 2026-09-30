import java.util.Random;

// --- SALA PONT -----
// Sala con puente, el personaje hace una tirada de agilidad pa cruzar
class SalaPont extends Sala {

    private static final Random rand = new Random();

    public SalaPont(Tresor tresor, Monstre monstre) {
        super(tresor, monstre);
        // El simbolo de sala pont explorada es = (solo izquierda a derecha)
        this.simbol = '=';
    }
    public boolean[] getDireccionsPermeses() {
    	return new boolean[]{false, false, true, true};
    }
    // El personaje hace tirada de agilidad, si falla recibe 1 de dano
    @Override
    public boolean intentarSortir(Personatge personatge) {
        // Numero aleatorio entre 1 y 12
        int tirada = rand.nextInt(12) + 1;
        // Si el numero es menor o igual a la agilidad tiene exito
        if (tirada <= personatge.getAgilitat()) {
            System.out.println("Tirada d'agilitat: " + tirada + " vs agilitat " + personatge.getAgilitat() + " -> EXIT!");
            return true;
        } else {
            // Si falla, pierde 1 punto de vida y se queda
            System.out.println("Tirada d'agilitat: " + tirada + " vs agilitat " + personatge.getAgilitat() + " -> FALLIDA! Caus del pont i perds 1 vida.");
            personatge.rebreDany(1);
//            
            return false;
        }
    }
}