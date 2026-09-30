// Clase abstracta ke representa una sala de la mazmorra
// Cada tipo de sala tendra su propia logica para intentar salir
public abstract class Sala {

    // Atributos de cada sala
    protected Tresor tresor;     // tesoro q puede tener la sala o null
    protected Monstre monstre;   // monstro ke puede tener la sala o null
    protected boolean explorada; // si el personaje ya la ha explorado
    protected char simbol;       // simbolo ke se muestra en el mapa cuando esta explorada

    // Constructor ke inicializa la sala con tesoro y monstro opcionales
    public Sala(Tresor tresor, Monstre monstre) {
        this.tresor = tresor;
        this.monstre = monstre;
        this.explorada = false;
    }

    // Metodo abstracto ke cada tipo de sala implementa diferente
    // Decide si el personaje puede salir de la sala o no
    public abstract boolean intentarSortir(Personatge personatge);

    // Getters y setters pa acceder a los atributos
    public Tresor getTresor() { 
    	return tresor; 
    	}
    public Monstre getMonstre() { 
    	return monstre; 
    	}
    public boolean isExplorada() { 
    	return explorada; 
    	}
    public char getSimbol() { 
    	return simbol; 
    	}
    public abstract  boolean[] getDireccionsPermeses();

    // Marca la sala como explorada
    public void setExplorada(boolean explorada) { 
    	this.explorada = explorada; 
    	}

    // Elimina el tesoro de la sala cuando el personaje lo recoge
    public void retirarTresor() { 
    	this.tresor = null; 
    	}

    // Elimina el monstro de la sala cuando muere
    public void retirarMonstre() { 
    	this.monstre = null; 
    	}
}


