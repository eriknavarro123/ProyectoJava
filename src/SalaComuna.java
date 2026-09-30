// ===== SALA COMUNA =====
// La sala normal, siempre deja salir al personaje
class SalaComuna extends Sala {

    public SalaComuna(Tresor tresor, Monstre monstre) {
        super(tresor, monstre);
        // El simbolo de sala comun explorada es + (se puede ir en cualquier direccion)
        this.simbol = '+';
    }

    // La sala comun siempre deja salir, retorna true siempre
    @Override
    public boolean intentarSortir(Personatge personatge) {
        return true;
    }
	
    public boolean[] getDireccionsPermeses() {
    	return new boolean[]{true, true, true, true};
    }
}
