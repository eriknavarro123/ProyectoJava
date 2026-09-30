// ===== SALA CURATIVA (AMPLIACION 2) =====
// Sala ke cura al personaje cuando la explora, simbolo perp
class SalaCurativa extends Sala {

    public SalaCurativa(Tresor tresor, Monstre monstre) {
        super(tresor, monstre);
        // El simbolo de sala curativa explorada es perp (derecha-izquierda y arriba)
        this.simbol = 0x22A5; // simbolo ⊥ en unicode
    }

    // La sala curativa siempre deja salir
    @Override
    public boolean intentarSortir(Personatge personatge) {
        return true;
    }
    public boolean[] getDireccionsPermeses() {
    	return new boolean[]{true, false, true, true};
    }
    // Metodo especial ke cura al personaje cuando explora esta sala
    public void curarPersonatge(Personatge personatge) {
        int curacioMax = 20 - personatge.getVida();
        if (curacioMax > 0) {
            int curacio = Math.min(3, curacioMax); // cura hasta 3 puntos de vida
            personatge.curar(curacio);
            System.out.println("La sala curativa et restaura " + curacio + " punts de vida!");
        } else {
            System.out.println("La sala curativa brilla, pero ja tens la vida al maxim.");
        }
    }
}