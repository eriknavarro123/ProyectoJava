import java.util.Random;
import java.util.Scanner;

// Contiene el mapa, tresors, los monstros y el bucle principal
public class Masmorra {

    // Dimensiones de la mazmorra 5x5
    private static final int FILES = 5;
    private static final int COLUMNES = 5;

    // El mapa de la mazmorra como array de Salas
    private static Sala[][] mapa = new Sala[FILES][COLUMNES];

    // El personaje del juego
    private static Personatge personatge;

    // Generador aleatorio pa crear salas, tresors y monstros
    private static final Random rand = new Random();

    // Scanner pa leer las opciones 
    private static final Scanner scanner = new Scanner(System.in);

    // Array con todos los tresors disponibles en el jogo
    private static final Tresor[] TRESORS_DISPONIBLES = {
        new Tresor("Espasa Magica", 50, 3.5),
        new Tresor("Escut d'Or", 80, 7.0),
        new Tresor("Poció de Vida", 30, 0.5),
        new Tresor("Anell Magic", 120, 0.1),
        new Tresor("Armadura de Plata", 100, 15.0),
        new Tresor("Daga Envenenada", 45, 1.2),
        new Tresor("Botes de Velocitat", 60, 1.5),
        new Tresor("Capa d'Invisibilitat", 200, 0.3),
        new Tresor("Collaret Sagrat", 90, 0.2),
        new Tresor("Bagueta de Foc", 70, 0.8),
        new Tresor("Pergami Antic", 25, 0.1),
        new Tresor("Gema Misteriosa", 150, 0.5)
    };

    // Array con todos los monstros disponibles en el juego
    private static final String[][] MONSTROS_DADES = {
        {"Goblin", "8", "1"},
        {"Esquelet", "4", "1"},
        {"Zombie", "4", "2"},
        {"Aranya Gegant", "6", "1"},
        {"Rata Gegant", "5", "0"},
        {"Fantasma", "15", "1"},
        {"Trol", "15", "3"},
        {"Vampir", "6", "2"},
        {"Orc", "10", "2"},
        {"Bruixa", "7", "1"}
    };

    // Metodo principal q inicia el juego
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║    DUNGEON QUEST - JUEGO     ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println();

        // Crea el personaje preguntando al usuario
        crearPersonatge();

        // Crea la mazmorra con todas sus salas
        crearMasmorra();

        // Pone al personaje en la sala superior izquierda
        personatge.setPosicio(0, 0);

        System.out.println("\nBenvingut/da a la masmorra, " + personatge.getNom() + "!");
        System.out.println("Troba la sortida i sobreviu!\n");

        // Bucle principal del jogo
        bucleJoc();
    }

    // Pregunta los datos del personaje al usuario y lo crea
    private static void crearPersonatge() {
        System.out.print("Crea el nom del teu personatge: ");
        String nom = scanner.nextLine().trim();
        if (nom.isEmpty()) nom = "Heroi";

        // Genera stats aleatorios dentro de los rangos correctos
        int vida = rand.nextInt(16) + 5;      // entre 5 y 20
        int atac = rand.nextInt(4) + 1;        // entre 1 y 4
        int agilitat = rand.nextInt(8) + 4;    // entre 4 y 11
        int forsa = rand.nextInt(8) + 4;       // entre 4 y 11

        // Crea el personaje con los stats generados
        personatge = new Personatge(nom, vida, atac, agilitat, forsa);

        System.out.println("\nPersonatge creat!");
        
        System.out.println(personatge);
        System.out.println();
    }

    // Crea la mazmorra llenando cada sala aleatoriamente con los porcentajes correctos
    private static void crearMasmorra() {
        for (int i = 0; i < FILES; i++) {
            for (int j = 0; j < COLUMNES; j++) {
                // Genera un tesoro o null aleatoriamente (50% de que haya tesoro)
                Tresor tresor = null;
                if (rand.nextInt(100) < 50) {
                    // Escoge un tesoro aleatorio de la lista
                    tresor = TRESORS_DISPONIBLES[rand.nextInt(TRESORS_DISPONIBLES.length)];
                }

                // Genera un monstro o null aleatoriamente (40% de q haya monstruo)
                Monstre monstre = null;
                if (rand.nextInt(100) < 40) {
                    // Escoge un monstro aleatorio de la lista
                    String[] dades = MONSTROS_DADES[rand.nextInt(MONSTROS_DADES.length)];
                    monstre = new Monstre(dades[0], Integer.parseInt(dades[1]), Integer.parseInt(dades[2]));
                }

             // Decide q tipo de sala crear segun los porcentajes:
             // 30% SalaComuna, 15% SalaPont, 20% SalaTeranyina, 15% SalaTrampa, 20% SalaCurativa
             int tipusSala = rand.nextInt(100);

             if (tipusSala < 30) {
                 // 30% SalaComuna - la sala normal
                 mapa[i][j] = new SalaComuna(tresor, monstre);

             } else if (tipusSala < 45) {
                 // 15% SalaPont - sala con puente, requiere agilidad
                 mapa[i][j] = new SalaPont(tresor, monstre);

             } else if (tipusSala < 65) {
                 // 20% SalaTeranyina - sala con telaraña, requiere fuerza
                 mapa[i][j] = new SalaTeranyina(tresor, monstre);

             } else if (tipusSala < 80) {
                 // 15% SalaTrampa - ampliacion 1, hace dano al intentar salir
                 mapa[i][j] = new SalaTrampa(tresor, monstre);

             } else {
                 // 20% SalaCurativa - ampliacion 2, cura al explorar
                 mapa[i][j] = new SalaCurativa(tresor, monstre);
             }
            }
        }

        System.out.println("Masmorra creada! (5x5)");
    }

    // Muestra el mapa de la mazmorra por consola
    // * sala explorada, - sala sin explorar, & posicion del personaje
    private static void mostrarMapa() {
        System.out.println();
        System.out.println("\n=== MAPA DE LA MASMORRA ===");
        System.out.println("Llegenda: [ ] = tu, · = desconeguda");
        System.out.println("           + = qualsevol dir, | = amunt/avall, '=' = esquerra/dreta");
        System.out.println("PV: " + personatge.getVida() +"     T = dreta/esq/avall, ⊥ = dreta/esq/amunt, # = trampa, (⊥) = curativa");
        System.out.println();

        // Imprime el borde superior del mapa
        System.out.print("  ");
        for (int j = 1; j < COLUMNES + 1 ; j++) System.out.print(" " + j + " ");
        System.out.println();

        for (int i = 0; i < FILES; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < COLUMNES; j++) {
            	
                if (i == personatge.getFila() && j == personatge.getColumna()) {
                	char sim = mapa[i][j].getSimbol();
                	System.out.print("["+ sim + "]");
                } else if (mapa[i][j].isExplorada()) {
                    // Si la sala esta explorada muestra su simbolo segun el tipo
                    char sim = mapa[i][j].getSimbol();
                    if (mapa[i][j] instanceof SalaTrampa) {
                        System.out.print(" # ");
                    } else if (mapa[i][j] instanceof SalaCurativa) {
                        System.out.print("(⊥)");
                    } else {
                        System.out.print(" " + sim + " " );
                    }
                } else {
                    // Sala sin explorar
                    System.out.print(" · ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Muestra las opciones disponibles al jugador segun el estado de la sala
    private static void mostrarOpcions(Sala salaActual) {
        System.out.println("=== OPCIONS ===");
        System.out.println("M - Moure");

        // Solo puede explorar si la sala no ha sido explorada
        if (!salaActual.isExplorada()) {
            System.out.println("E - Explorar");
        }

        // Solo puede atacar si hay un monstro vivo en la sala
        if (salaActual.getMonstre() != null && salaActual.getMonstre().estaViu()) {
            System.out.println("A - Atacar (" + salaActual.getMonstre() + ")");
        }

        System.out.println("P - Veure Personatge");
        System.out.println();
    }

    // Muestra las direcciones posibles segun el tipo de sala
    private static void mostrarDireccionsDisponibles(Sala sala, int fila, int col) {
        System.out.println("Cap a on vols anar?");

        // Segun el tipo de sala, se permiten diferentes direcciones
        // + SalaComuna: cualquier direccion
        // = SalaPont: solo izquierda a derecha (Este/Oeste)
        // | SalaTeranyina: solo arriba y abajo (Norte/Sur)
        // # SalaTrampa: derecha-izquierda y abajo (Este/Oeste/Sur)
        // ⊥ SalaCurativa: derecha-izquierda y arriba (Este/Oeste/Norte)

        boolean[] dirs = getDireccionsPermeses(sala);
        // dirs[0]=Norte, dirs[1]=Sur, dirs[2]=Este, dirs[3]=Oeste

        if (dirs[0]) System.out.println("W - ↑");
        if (dirs[1] && fila < FILES - 1) System.out.println("S - ↓");
        if (dirs[2] && col < COLUMNES - 1) System.out.println("D - →");
        if (dirs[3] && col > 0) System.out.println("A - ←");
        System.out.println("X - Cancel·lar");
        System.out.println();
    }
     
    // Retorna un array de booleanos con las direcciones permitidas segun el tipo de sala
    private static boolean[] getDireccionsPermeses(Sala sala) {
    	return sala.getDireccionsPermeses();
        // [Norte, Sur, Este, Oeste]
//        if (sala instanceof SalaComuna) {
//            // + pots anar en cualquier direccio
//            return new boolean[]{true, true, true, true};
//        } else if (sala instanceof SalaPont) {
//            // = solo de izquierda a derecha (Este/Oeste) - los puentes son horizontales
//            return new boolean[]{false, false, true, true};
//        } else if (sala instanceof SalaTeranyina) {
//            // I solo arriba y abajo
//            return new boolean[]{true, true, false, false};
//        } else if (sala instanceof SalaTrampa) {
//            // T derecha-izquierda y abajo (como indica el enunciado), tambien es la sala trampa
//            return new boolean[]{false, true, true, true};
//        } else if (sala instanceof SalaCurativa) {
//            // perp derecha-izquierda y arriba, ademas es sala curativa
//            return new boolean[]{true, false, true, true};
//        }
        // Por defecto permite todo
//        return new boolean[]{true, true, true, true};
    }

    // Bucle principal del juego donde se procesan las acciones del jugador
    private static void bucleJoc() {
        boolean jocAcabat = false;

        while (!jocAcabat) {
            // Muestra el mapa y el estado de la sala actual
            mostrarMapa();

            int fila = personatge.getFila();
            int col = personatge.getColumna();
            Sala salaActual = mapa[fila][col];

            // Informa del tipo de sala en la ke esta el personaje
            System.out.println("Sala actual: " + getSalaNom(salaActual) + " [" + (fila + 1) + "," + (col + 1) + "]");
            if (salaActual.getMonstre() != null && salaActual.getMonstre().estaViu()) {
                System.out.println("      ¡¡¡PERILL!!!");

                System.out.println("⚠ Hi ha un " + salaActual.getMonstre() + " aqui!");
            }

            // Muestra las opciones disponibles
            mostrarOpcions(salaActual);

            // Lee la opcion del usuario
            System.out.print("Tria una opcio: ");
            String opcio = scanner.nextLine().trim().toUpperCase();

            System.out.println();

            // Procesa la opcion elegida
            switch (opcio) {
                case "M":
                    // Mover al personaje
                    jocAcabat = processarMoviment(salaActual, fila, col);
                    break;

                case "E":
                    // Explorar la sala si no ha sido explorada
                    if (!salaActual.isExplorada()) {
                        personatge.explorar(salaActual);
                    } else {
                        System.out.println("Ja has explorat aquesta sala.");
                    }
                    break;

                case "A":
                    // Atacar al monstro si hay uno vivo
                    if (salaActual.getMonstre() != null && salaActual.getMonstre().estaViu()) {
                        personatge.atacar(salaActual.getMonstre());
                        // Si el monstro ha muerto, lo elimina de la sala
                        if (!salaActual.getMonstre().estaViu()) {
                            salaActual.retirarMonstre();
                        }
                    } else {
                        System.out.println("No hi ha cap monstre a la sala.");
                    }
                    break;

                case "P":
                    // Muestra los datos del personaje
                    System.out.println(personatge);
                    break;

                default:
                    System.out.println("Opcio no valida.");
            }

            System.out.println();

            // Comprueba si el personaje ha muerto (solo si no ha ganado ya)
            if (!jocAcabat && !personatge.estaViu()) {
                jocAcabat = true;
                mostrarFinalDerrota();
            }
        }
    }

    // Procesa el movimiento del personaje y retorna si el jogo ha acabado
    private static boolean processarMoviment(Sala salaActual, int fila, int col) {
        // Muestra las direcciones posibles segun el tipo de sala
        mostrarDireccionsDisponibles(salaActual, fila, col);

        System.out.print("Direccio: ");
        String dir = scanner.nextLine().trim().toUpperCase();

        if (dir.equals("X")) {
            System.out.println("Moviment cancel·lat.");
            return false;
        }

        // Comprueba ke la direccion es valida pa este tipo de sala
        boolean[] dirsPermeses = getDireccionsPermeses(salaActual);
        boolean dirValida = false;
        int novaFila = fila;
        int novaCol = col;

        switch (dir) {
            case "W":
                if (dirsPermeses[0]) { novaFila = fila - 1; dirValida = true; }
                break;
            case "S":
            	
                if (dirsPermeses[1]) { novaFila = fila + 1; dirValida = true; }
                break;
            case "D":
                if (dirsPermeses[2]) { novaCol = col + 1; dirValida = true; }
                break;
            case "A":
                if (dirsPermeses[3]) { novaCol = col - 1; dirValida = true; }
                break;
            default:
                System.out.println("Direccio no valida.");
                return false;
        }

        // Comprueba ke la direccion esta permitida por el tipo de sala
        if (!dirValida) {
            System.out.println("No pots anar en aquesta direccio des d'aquest tipus de sala!");
            return false;
        }

        // Intenta salir de la sala actual (puede fallar en SalaPont o SalaTeranyina)
        boolean potSortir = salaActual.intentarSortir(personatge);

        

        if (!potSortir) {
            System.out.println("No has pogut sortir de la sala.");
            return false;
        }

        // Comprueba si el personaje sale por los bordes de la mazmorra
        boolean sortidaMasmorra = novaFila < 0 || novaFila >= FILES || novaCol < 0 || novaCol >= COLUMNES;

        // Comprueba si hay un monstro vivo al intentar huir de la sala
        if (salaActual.getMonstre() != null && salaActual.getMonstre().estaViu()) {
            int penalitzacio = salaActual.getMonstre().getPenalitzacioFugida();
            if (penalitzacio > 0) {
                System.out.println("El " + salaActual.getMonstre().getNom() +
                    " t'ataca per la fugida! Perds " + penalitzacio + " punts de vida.");
                personatge.rebreDany(penalitzacio);
                personatge.registrarMortMonstre();
            }
        }
//si el personaje sale de la mazmorra gana
        if (sortidaMasmorra) {
            mostrarFinalVictoria();
            return true;
        }

        // Mueve el personaje a la nueva sala
        personatge.setPosicio(novaFila, novaCol);
        System.out.println("T'has mogut a [" + (novaFila +1) + "," + (novaCol + 1) + "].");

        return false;
    }

    // Retorna el nombre legible del tipo de sala
    private static String getSalaNom(Sala sala) {
        if (sala instanceof SalaPont) return "Sala Pont (=)";
        if (sala instanceof SalaTeranyina) return "Sala Teranyina (|)";
        if (sala instanceof SalaTrampa) return "Sala Trampa (T) [AMPLIACIO]";
        if (sala instanceof SalaCurativa) return "Sala Curativa (⊥) [AMPLIACIO]";
        return "Sala Comuna (+)";
    }

    // Calcula el porcentaje de salas exploradas
    private static double calcularPercentatgeExplorat() {
        int explorades = 0;
        for (int i = 0; i < FILES; i++) {
            for (int j = 0; j < COLUMNES; j++) {
                if (mapa[i][j].isExplorada()) explorades++;
            }
        }
        return (double) explorades / (FILES * COLUMNES) * 100.0;
    }

    // Muestra el mensaje de victoria cuando el personaje sale de la mazmorra
    private static void mostrarFinalVictoria() {
    	System.out.println();
    	System.out.println("╔══════════════════════════════╗");
        System.out.println("║         ¡VICTORIA!           ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println();
        if (personatge.getVida() <= 0) {
            System.out.println("Quasi no ho aconsegueixes... amb la poca vida que tens");//si una tramopa o monstruo te deja a 0 PV o menos
            System.out.println("Has sortit de la masmorra... Un increible miracle!");    //al salir de la mazmorra ganas igualmente
        } else {
            System.out.println("Has aconseguit sortir de la masmorra!");
        }
        System.out.println();
        System.out.println("=== RESULTATS FINALS ===");
        System.out.println("Personatge: " + personatge.getNom());
        System.out.println("Experiencia: " + personatge.getExperiencia());
        System.out.println("Total de tresors: " + personatge.getNumTresors());
        System.out.println("Total de monedes d'or: " + personatge.getTotalOr());
        System.out.println("Vida restant: " + personatge.getVida());
        System.out.println("Percentatge explorat: " + calcularPercentatgeExplorat());
    }

    // Muestra el mensaje de derrota cuando el personaje muere
    private static void mostrarFinalDerrota() {
    	System.out.println();
    	System.out.println("╔══════════════════════════════╗");
        System.out.println("║          GAME OVER           ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println();
        System.out.println(personatge.getNom() + " ha mort a la masmorra...");
        System.out.println();
        System.out.println("=== RESULTATS FINALS ===");
        System.out.println("Experiencia perduda : " + personatge.getExperiencia());
        System.out.println("Causa de la mort: " + personatge.getCausaMort());
        System.out.println("Percentatge explorat: " + calcularPercentatgeExplorat());
    }
}