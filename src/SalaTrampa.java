// ===== SALA TRAMPA (AMPLIACION 1) =====
// Sala con trampa q hace dano fijo al intentar salir, simbolo T
class SalaTrampa extends Sala {

    public SalaTrampa(Tresor tresor, Monstre monstre) {
        super(tresor, monstre);
        // El simbolo de sala trampa explorada es T (derecha-izquierda y abajo) pero en el mapa 
        //se vera como # para saber que es trampa
        this.simbol = '#';
    }
    public boolean[] getDireccionsPermeses() {
    	return new boolean[]{false, true, true, true};
    }
    // La trampa hace 2 puntos de dano al intentar salir pero deja pasar
    @Override
    public boolean intentarSortir(Personatge personatge) {
        System.out.println("TRAMPA! La sala activa una trampa i perds 2 punts de vida!");
        personatge.rebreDany(2);
        return true; // Deja salir pero haciendo dano
    }
}