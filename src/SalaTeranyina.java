import java.util.Random;
// ===== SALA TERANYINA ===== 
// Sala con telaraña, el personaje tiene ke hacer una tirada de fuerza para salir
class SalaTeranyina extends Sala {

    private static final Random rand = new Random();

    public SalaTeranyina(Tresor tresor, Monstre monstre) {
        super(tresor, monstre);
        // El simbolo de sala teranyina explorada es | (solo arriba y abajo)
        this.simbol = '|';
    }
    public boolean[] getDireccionsPermeses() {
    	return new boolean[]{true, true, false, false};
    }
    // El personaje hace una tirada de fuerza, si tiene exito sale, sino se queda
    @Override
    public boolean intentarSortir(Personatge personatge) {
        // Numero aleatorio entre 1 y 12
        int tirada = rand.nextInt(12) + 1;
        // Si el numero es menor o igual a la fuerza del personaje tiene exito
        if (tirada <= personatge.getForsa()) {
            System.out.println("Tirada de força: " + tirada + " vs força " + personatge.getForsa() + " -> EXIT!");
            return true;
        } else {
            System.out.println("Tirada de força: " + tirada + " vs força " + personatge.getForsa() + " -> FALLIDA! La teranyina et reté.");
            return false;
        }
    }
}