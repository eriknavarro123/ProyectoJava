import java.util.Random;

// Clase ke representa al personaje del jogo
public class Personatge implements Combatent {

    // Atributos del personaje
    private String nom;
    private int vida;           // numero entre 5 y 20
    private int atac;           // numero entre 1 y 4
    private int experiencia;    // empieza en 0
    private int agilitat;       // numero entre 4 y 11
    private int forsa;          // numero entre 4 y 11
    private int fila;           // posicion en la mazmorra
    private int columna;        // posicion en la mazmorra

    // Equipamiento del personaje, array de tresors de tamaño igual a la fuerza
    private Tresor[] equipament;
    private int numTresors;     // cantidad actual de tresors equipados

    // Para saber la causa de muerte en caso de morir
    private String causaMort;
//    private boolean mortPontRecent; // si la ultima caida del puente fue fatal

    // Generador de numeros aleatorios pa los ataques
    private static final Random rand = new Random();

    // Constructor del personaje con todos sus stats
    public Personatge(String nom, int vida, int atac, int agilitat, int forsa) {
        this.nom = nom;
        // Asegurar q los valores estan en los rangos correctos
        this.vida = Math.max(5, Math.min(20, vida));
        this.atac = Math.max(1, Math.min(4, atac));
        this.agilitat = Math.max(4, Math.min(11, agilitat));
        this.forsa = Math.max(4, Math.min(11, forsa));
        this.experiencia = 0;
        this.fila = 1 ;
        this.columna = 1;
        // El equipamiento tiene tamaño igual a la fuerza del personaje
        this.equipament = new Tresor[forsa];
        this.numTresors = 0;
        this.causaMort = "monstre";
//        this.mortPontRecent = false;
    }

    // Getters pa acceder a los atributos del personaje
    public String getNom() { 
    	return nom; 
    	}
    public int getVida() { 
    	return vida; 
    	}
    public int getAtac() { 
    	return atac; 
    	}
    public int getExperiencia() { 
    	return experiencia; 
    	}
    public int getAgilitat() { 
    	return agilitat; 
    	}
    public int getForsa() { 
    	return forsa; 
    	}
    public int getFila() { 
    	return fila; 
    	}
    public int getColumna() { 
    	return columna; 
    	}
    public Tresor[] getEquipament() { 
    	return equipament; 
    	}
    public int getNumTresors() { 
    	return numTresors;
    	}
    public String getCausaMort() {
    	return causaMort; 
    	}

    // Actualiza la posicion del personaje en la mazmorra
    public void setPosicio(int fila, int columna) {
        this.fila = fila;
        this.columna = columna  ;
    }

    // Calcula un ataque aleatorio entre 1 y el valor de ataque del personaje
    @Override
    public int calcularAtac() {
        return rand.nextInt(atac) + 1;
    }

    // Resta dano a la vida del personaje
    @Override
    public void rebreDany(int quantitat) {
        vida -= quantitat;
        if (vida < 0) vida = 0;
    }

    // Cura al personaje sumando vida (no puede superar 20)
    public void curar(int quantitat) {
        vida = Math.min(20, vida + quantitat);
    }

    // Retorna si el personaje esta vivo
    @Override
    public boolean estaViu() {
        return vida > 0;
    }

    // Suma experiencia al personaje cuando mata un monstro
    public void sumarExperiencia(int exp) {
        experiencia += exp;
    }
//
//    
//    }

    
    public void registrarMortMonstre() {
        this.causaMort = "monstre";
    }

    // El personaje ataca al monstro y el monstro contraataca si sigue vivo
    public void atacar(Monstre m) {
        // El personaje calcula su daño
        int danyPersonatge = calcularAtac();
        System.out.println(nom + " ataca per " + danyPersonatge + " punts de dany!");

        // El personaje hace el daño al monstruo
        m.rebreDany(danyPersonatge);
        System.out.println("Vida del " + m.getNom() + ": " + m.getVida());

        // Si el monstro no ha muerto, el monstro contraataca
        if (m.estaViu()) {
            int danyMonstre = m.calcularAtac();
            System.out.println(m.getNom() + " contraataca per " + danyMonstre + " punts de dany!");
            rebreDany(danyMonstre);
            registrarMortMonstre(); // actualiza causa de muerte por si muere aqui
            System.out.println("Vida de " + nom + ": " + vida);
        } else {
            // El monstro ha muerto, el personaje gana experiencia
            System.out.println(m.getNom() + " ha estat derrotat!");
            sumarExperiencia(m.getValorExperiencia());
            System.out.println("Guanyes " + m.getValorExperiencia() + " punts d'experiencia! Total: " + experiencia);
        }
    }

    // El personaje explora la sala en la q se encuentra y recoge el tesoro si hay sitio
    public void explorar(Sala sala) {
        // Marca la sala como explorada
        sala.setExplorada(true);

        // Si la sala es curativa, activa su efecto de curacion (ampliacion 2)
        if (sala instanceof SalaCurativa) {
            ((SalaCurativa) sala).curarPersonatge(this);
        }

        // Si la sala tiene tesoro, intenta cogerlo
        Tresor tresor = sala.getTresor();
        if (tresor != null) {
            // Comprueba si hay sitio en el equipamiento
            if (numTresors < equipament.length) {
                equipament[numTresors] = tresor;
                numTresors++;
                sala.retirarTresor(); // quita el tesoro de la sala
                System.out.println("Has trobat: " + tresor + "!");
            } else {
                System.out.println("Has trobat " + tresor + " pero el teu equipament esta ple!");
            }
        } else {
            System.out.println("La sala no te cap tresor.");
        }

        // Informa si hay un monstro en la sala
        if (sala.getMonstre() != null && sala.getMonstre().estaViu()) {
            System.out.println("Atencio! Hi ha un " + sala.getMonstre() + " a la sala!");
        }
    }

    // Calcula el total de monedas de oro en el equipamiento
    public int getTotalOr() {
        int total = 0;
        for (int i = 0; i < numTresors; i++) {
            total += equipament[i].getValor();
        }
        return total;
    }

    // Muestra el personaje por consola con todos sus datos
    @Override
    public String toString() {
        String result = "=== PERSONATGE ===\n";
        result += "Nom: " + nom + "\n";
        result += "Vida: " + vida + "\n";
        result += "Agilitat: " + agilitat + "\n";
        result += "Força: " + forsa + "\n";
        result += "Equipament (" + numTresors + "/" + equipament.length + "): ";

        if (numTresors == 0) {
            result += "buit";
        } else {
            for (int i = 0; i < numTresors; i++) {
                result += equipament[i];
                if (i < numTresors - 1) result += ", ";
            }
        }

        return result;
        
        
        
    }
}